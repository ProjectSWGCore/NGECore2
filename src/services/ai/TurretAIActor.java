/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package services.ai;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import main.NGECore;
import net.engio.mbassy.listener.Handler;
import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.datatables.Options;
import resources.objects.cell.CellObject;
import resources.objects.creature.CreatureObject;
import resources.objects.installation.InstallationObject;
import resources.objects.tangible.TangibleObject;
import services.ai.states.TurretAIState;
import services.ai.states.TurretAIState.StateResult;
import services.ai.states.AIState;
import services.ai.states.TurretAttackState;
import services.ai.states.TurretIdleState;
import services.combat.CombatEvents.DamageTaken;
import services.spawn.MobileTemplate;
import tools.DevLog;
import resources.datatables.FactionStatus;

import java.util.Random;

@SuppressWarnings("unused")
public class TurretAIActor {
	
	private TangibleObject creature;
	private Point3D spawnPosition;
	private volatile TurretAIState currentState;
	private TangibleObject followObject;
	private Vector<Point3D> movementPoints = new Vector<Point3D>();
	private MobileTemplate mobileTemplate;
	private ScheduledExecutorService scheduler;
	private Map<CreatureObject, Integer> damageMap = new ConcurrentHashMap<CreatureObject, Integer>();
	private volatile boolean hasReachedPosition;
	private long lastAttackTimestamp;
	private ScheduledFuture<?> regenTask;
	private ScheduledFuture<?> aggroCheckTask;
	private ScheduledFuture<?> factionCheckTask;
	private boolean isStalking = false;
	private byte milkState = 0;
	private boolean hasBeenHarvested = false;
	private Vector<Point3D> patrolPoints = new Vector<Point3D>();
	private int patrolPointIndex = 0;
	private String loiterDestType = "LOITER";
	private Point3D loiterDestination;
	private Point3D originPosition;
	private float minLoiterDist;
	private float maxLoiterDist;
	private String waitState = "NO";
	private long waitStartTime = 0L;
	private TurretAIState intendedPrimaryTurretAIState;
	private Point3D lastPositionBeforeStateChange;
	private boolean patrolLoop = true; // default
	private ScheduledFuture<?> recoveryFuture;

	
	public TurretAIActor(TangibleObject creature, Point3D spawnPosition, ScheduledExecutorService scheduler) {
		this.creature = creature;
		this.spawnPosition = spawnPosition;
		setLastPositionBeforeStateChange(spawnPosition); // just to make sure it's initialized
		this.scheduler = scheduler;
		creature.getEventBus().subscribe(this);
		this.currentState = new TurretIdleState();
		setIntendedPrimaryTurretAIState(this.currentState); // to switch back to after aggro
		
		if(creature.getOption(Options.AGGRESSIVE)) {
			aggroCheckTask = scheduler.scheduleAtFixedRate(() -> {
				try {
					
					if(creature == null || creature.getObservers().isEmpty() || creature.isInCombat() || isStalking)
						return;
					if (creature.getAttachment("tamed")!=null){
						DevLog.debugoutai(this, "Charon", "Pet AI", "aggroCheckTask tamed==1");
						return;
					}
					
					if (creature.getCustomName().contains("baby"))
						DevLog.debugoutai(this, "Charon", "Pet AI", "baby aggroCheckTask should not be here " +creature.getAttachment("tamed"));
					
					creature.getObservers().stream().map(Client::getParent).filter(obj -> obj.inRange(creature.getWorldPosition(), 60)).forEach((obj) -> {
						if(new Random().nextFloat() <= 0.5 || creature.isInCombat() || isStalking) {
							/*if(mobileTemplate.isStalker()) {
								setFollowObject((CreatureObject) obj);
								setCurrentState(new StalkState());
							} else */
							
							if (creature.getAttachment("IsBeingTamed")!=null)
								return;
							
							if (obj instanceof CreatureObject){
								CreatureObject addedObject = (CreatureObject) obj;
								if (addedObject.getCalledPet()!=null){
									CreatureObject calledPet = addedObject.getCalledPet();
									if (calledPet.getPosture() != 13 && calledPet.getPosture() != 14){
										addDefender(calledPet);	
									}
								}
								if (addedObject.getPosture() != 13 && addedObject.getPosture() != 14){
									addDefender(addedObject);	
								}
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}, 0, 2000, TimeUnit.MILLISECONDS);
		}
		if(creature.getFaction().length()>0 || !creature.getOption(Options.AGGRESSIVE)) {
			factionCheckTask = scheduler.scheduleAtFixedRate(() -> {
				//System.out.println("Turret faction thread");
				try {
					
					if(creature == null || creature.getFactionStatus()!=FactionStatus.Combatant || creature.getObservers().isEmpty() || creature.isInCombat())
						return;
																
					creature.getObservers().stream().map(Client::getParent).filter(obj -> obj.inRange(creature.getWorldPosition(), 60)).forEach((obj) -> {
						if(new Random().nextFloat() <= 0.5 || creature.isInCombat()) {
//							DevLog.debugout("Charon", "TURRET CHECK faction creature ", "added " + creature.getFaction());
//							DevLog.debugout("Charon", "TURRET CHECK faction obj", "added " + ((TangibleObject)obj).getFaction());
//							DevLog.debugout("Charon", "TURRET CHECK factionCheckTask", "added " + obj.getCustomName());
//							DevLog.debugout("Charon", "TURRET CHECK isFactionEnemy", "res " + NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj));
							if (obj instanceof CreatureObject && NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj)){
								CreatureObject addedObject = (CreatureObject) obj;
								if (addedObject.getCalledPet()!=null){
									CreatureObject calledPet = addedObject.getCalledPet();
									if (calledPet.getPosture() != 13 && calledPet.getPosture() != 14){
										addDefender(calledPet);	
									}
								}
								if (addedObject.getPosture() != 13 && addedObject.getPosture() != 14 && ! addedObject.getOption(Options.INVULNERABLE)){
									addDefender(addedObject);	
//									DevLog.debugout("Charon", "TURRET faction creature ", "added " + creature.getFaction());
//									DevLog.debugout("Charon", "TURRET faction obj", "added " + ((TangibleObject)obj).getFaction());
//									DevLog.debugout("Charon", "TURRET factionCheckTask", "added " + obj.getCustomName());
								}
					
							}					
						}
					});
					
					NGECore.getInstance().simulationService.getAllNearNonSameFactionNPCs(60, creature).forEach((obj) -> {
						if(new Random().nextFloat() <= 0.5 || creature.isInCombat()) {
//							DevLog.debugout("Charon", "TURRET CHECK faction creature ", "added " + creature.getFaction());
//							DevLog.debugout("Charon", "TURRET CHECK faction obj", "added " + ((TangibleObject)obj).getFaction());
//							DevLog.debugout("Charon", "TURRET CHECK factionCheckTask", "added " + obj.getCustomName());
//							DevLog.debugout("Charon", "TURRET CHECK isFactionEnemy", "res " + NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj));
							if (obj instanceof CreatureObject && NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj)){
								CreatureObject addedObject = (CreatureObject) obj;
								if (addedObject.getCalledPet()!=null){
									CreatureObject calledPet = addedObject.getCalledPet();
									if (calledPet.getPosture() != 13 && calledPet.getPosture() != 14 & ! calledPet.getOption(Options.INVULNERABLE)){
										addDefender(calledPet);	
									}
								}
								if (addedObject.getPosture() != 13 && addedObject.getPosture() != 14 & ! addedObject.getOption(Options.INVULNERABLE)){
									addDefender(addedObject);	
//									DevLog.debugout("Charon", "TURRET faction creature ", "added " + creature.getFaction());
//									DevLog.debugout("Charon", "TURRET faction obj", "added " + ((TangibleObject)obj).getFaction());
//									DevLog.debugout("Charon", "TURRET factionCheckTask", "added " + obj.getCustomName());
								}
							}
						}
					});
					
					NGECore.getInstance().simulationService.getAllNearNonSameFactionTANOs(60, creature).forEach((obj) -> {
						if(new Random().nextFloat() <= 0.5 || creature.isInCombat()) {
//							DevLog.debugout("Charon", "TANO CHECK faction creature ", "added " + creature.getFaction());
//							DevLog.debugout("Charon", "TANO CHECK faction obj", "added " + ((TangibleObject)obj).getFaction());
//							DevLog.debugout("Charon", "TANO CHECK factionCheckTask", "added " + obj.getCustomName());
//							DevLog.debugout("Charon", "TANO CHECK isFactionEnemy", "res " + NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj));
							if (obj instanceof TangibleObject && !(obj instanceof CreatureObject) && NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj)){
								TangibleObject addedObject = (TangibleObject) obj;					
								addDefender(addedObject);	
								DevLog.debugoutai(this, "Charon", "TANO faction creature ", "added " + creature.getFaction());
								DevLog.debugoutai(this, "Charon", "TANO faction obj", "added " + ((TangibleObject)obj).getFaction());
								DevLog.debugoutai(this, "Charon", "TANO factionCheckTask", "added " + obj.getCustomName());							
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}, 0, 1000, TimeUnit.MILLISECONDS);

		}
	}

	public TangibleObject getCreature() {
		return creature;
	}

	public void setCreature(CreatureObject creature) {
		this.creature = creature;
	}

	public Point3D getSpawnPosition() {
		return spawnPosition;
	}

	public void setSpawnPosition(Point3D spawnPosition) {
		this.spawnPosition = spawnPosition;
	}
	
//	public void addDefender(CreatureObject defender) {
//		addDefender(defender, false);
//	}
	public void addDefender(TangibleObject defender) {
		addDefender(defender, false);
	}

		
	public void addDefender(TangibleObject defender, boolean isAssisting) {
		creature.addDefender(defender);
		if(followObject == null)
			setFollowObject(defender);
		setCurrentState(new TurretAttackState());
//		if(!isAssisting) {
//			NGECore.getInstance().simulationService.get(creature.getPlanet(), creature.getWorldPosition().x, creature.getWorldPosition().z, 38).stream().filter((obj) -> 
//				obj instanceof CreatureObject && 
//				obj.getAttachment("AI") != null && 
//				((TurretAIActor) obj.getAttachment("AI")).getMobileTemplate().getSocialGroup().equals(getMobileTemplate().getSocialGroup()) &&
//				obj.inRange(creature.getWorldPosition(), ((TurretAIActor) obj.getAttachment("AI")).getMobileTemplate().getAssistRange())
//			).forEach((obj) -> ((TurretAIActor) obj.getAttachment("AI")).addDefender(defender, true));
//		}
	}
	
	public void removeDefender(TangibleObject defender) {
		creature.removeDefender(defender);
		damageMap.remove(defender);
		defender.removeDefender(creature);
		if(followObject == defender) {
			setFollowObject(getHighestDamageDealer());
			if(creature.getDefendersList().size() == 0)
				setCurrentState(new TurretIdleState());
		}
	}

	public CreatureObject getHighestDamageDealer() {
		CreatureObject highestDamageDealer = null;
		highestDamageDealer = damageMap.keySet().stream().max((c1, c2) -> damageMap.get(c1) - damageMap.get(c2)).orElse(null);
		// return first defender if no damage has been dealt
		if(highestDamageDealer == null) {
			for(TangibleObject tangible : creature.getDefendersList().toArray(new TangibleObject[]{})) {
				if(tangible instanceof CreatureObject)
					return (CreatureObject) tangible;
			}
		}
		return highestDamageDealer;
	}

	public TurretAIState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(TurretAIState currentState) {
		try {
			if(currentState.getClass() == this.currentState.getClass())
				return;
			if(this.currentState != null) 
				doStateAction(this.currentState.onExit(this));
			this.currentState = currentState;
			doStateAction(currentState.onEnter(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TangibleObject getFollowObject() {
		return followObject;
	}

	public void setFollowObject(TangibleObject followObject) {
		this.followObject = followObject;
	}

	public Vector<Point3D> getMovementPoints() {
		return movementPoints;
	}

	public void setMovementPoints(Vector<Point3D> movementPoints) {
		this.movementPoints = movementPoints;
	}

	public MobileTemplate getMobileTemplate() {
		return mobileTemplate;
	}

	public void setMobileTemplate(MobileTemplate mobileTemplate) {
		this.mobileTemplate = mobileTemplate;
	}
		
	public void scheduleRecovery() {
		TurretAIState caughtAIState = currentState;
		recoveryFuture = scheduler.schedule(() -> { 
			try {
				if (caughtAIState.getClass().equals(currentState.getClass()))
					doStateAction(currentState.recover(TurretAIActor.this));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 2000, TimeUnit.MILLISECONDS);
	}
	
	public void setNextPosition(Point3D position) {
		movementPoints.add(0, position);
	}
	
	@Handler
	public void handleDamage(DamageTaken event) {
		CreatureObject attacker = (CreatureObject)event.attacker;
		if(damageMap.containsKey(attacker))
			damageMap.put(attacker, damageMap.get(attacker) + event.damage);
		else 
			damageMap.put(attacker, event.damage);
	}
	
	public Map<CreatureObject, Integer> getDamageMap() {
		return damageMap;
	}

	public boolean hasReachedPosition() {
		return hasReachedPosition;
	}

	public void setHasReachedPosition(boolean hasReachedPosition) {
		this.hasReachedPosition = hasReachedPosition;
	}
	
	public void faceObject(SWGObject object) {
		// Null checks due to a null error at: float direction =
		if (object == null) DevLog.debugout("Charon", "AI Actor", "faceObject object is NULL"); 
		if (creature == null) DevLog.debugout("Charon", "AI Actor", "faceObject creature is NULL"); 
		if (object.getWorldPosition() == null) DevLog.debugout("Charon", "AI Actor", "faceObject object's position is NULL"); 
		if (creature.getWorldPosition() == null) DevLog.debugout("Charon", "AI Actor", "faceObject creature's position is NULL"); 
		
		float direction = (float) Math.atan2(object.getWorldPosition().x - creature.getWorldPosition().x, object.getWorldPosition().z - creature.getWorldPosition().z);
		if(direction < 0)
			direction = (float) (2 * Math.PI + direction);
		if(Math.abs(direction - creature.getRadians()) < 0.05)
			return;
		Quaternion quaternion = new Quaternion((float) Math.cos(direction / 2), 0, (float) Math.sin(direction / 2), 0);
        if (quaternion.y < 0.0f && quaternion.w > 0.0f) {
        	quaternion.y *= -1;
        	quaternion.w *= -1;
        }
		if(creature.getContainer() instanceof CellObject)
			NGECore.getInstance().simulationService.moveObject(creature, creature.getPosition(), quaternion, creature.getMovementCounter(), 0, (CellObject) creature.getContainer());
		else
			NGECore.getInstance().simulationService.moveObject(creature, creature.getPosition(), quaternion, creature.getMovementCounter(), 0, null);

	}

	public long getLastAttackTimestamp() {
		return lastAttackTimestamp;
	}

	public void setLastAttackTimestamp(long lastAttackTimestamp) {
		this.lastAttackTimestamp = lastAttackTimestamp;
	}
	
	public long getTimeSinceLastAttack() {
		return System.currentTimeMillis() - getLastAttackTimestamp();
	}
	
	public void doStateAction(byte result) {
		
		if (creature==null){
			destroyActor();
			return;
		}
		
		switch(result) {
		
			case StateResult.DEAD:
				destroyActor();
				break;
			case StateResult.FINISHED:
			case StateResult.UNFINISHED:
				return;
			case StateResult.IDLE:
				setCurrentState(new TurretIdleState());
				return;
							
			//case StateResult.NONE:
		//		System.out.println("State action returned error result");
		
		}
		
	}
	
	public void scheduleDespawn() {	
		// Sometimes these tasks are null?
		
		try {
			if (aggroCheckTask!=null)
				aggroCheckTask.cancel(true);
			if (factionCheckTask!=null)
				factionCheckTask.cancel(true);
		} catch(Exception e) {
			
		}
		
		try {
			regenTask.cancel(true);
		} catch(Exception e) {
			
		}
		
	}
	
	public void destroyActor(){
		creature.getEventBus().unsubscribe(this);
		// Make sure to kill all AI helper threads
		if (aggroCheckTask!=null)
			aggroCheckTask.cancel(true);
		if (factionCheckTask!=null)
			factionCheckTask.cancel(true);		
		if (regenTask!=null)
			regenTask.cancel(true);
	}
	
	public void cancelAggro(){
		try {
			aggroCheckTask.cancel(true);
		} catch(Exception e) {			
		}
	}
	
	public ScheduledFuture<?> getRegenTask() {
		return regenTask;
	}

	public void setRegenTask(ScheduledFuture<?> regenTask) {
		this.regenTask = regenTask;
	}
	
	public boolean isStalking() {
		return isStalking;
	}

	public void setStalking(boolean isStalking) {
		this.isStalking = isStalking;
	}

	public byte getMilkState() {
		synchronized(creature.getMutex()) {
			return milkState;
		}
	}

	public void setMilkState(byte milkState) {
		synchronized(creature.getMutex()) {
			this.milkState = milkState;
		}
	}

	public boolean hasBeenHarvested() {
		synchronized(creature.getMutex()) {
			return hasBeenHarvested;
		}
	}

	public void setHasBeenHarvested(boolean hasBeenHarvested) {
		synchronized(creature.getMutex()) {
			this.hasBeenHarvested = hasBeenHarvested;
		}
	}
	
	public void setPatrolPoints(Vector<Point3D> patrolPoints){
		this.patrolPoints = patrolPoints;
		//setMovementPoints(patrolPoints);
	}

	public int getPatrolPointIndex() {
		return patrolPointIndex;
	}

	public void setPatrolPointIndex(int patrolPointIndex) {
		this.patrolPointIndex = patrolPointIndex;
	}

	public Point3D getLoiterDestination() {
		return loiterDestination;
	}

	public void setLoiterDestination(Point3D loiterDestination) {
		this.loiterDestination = loiterDestination;
	}

	public Point3D getOriginPosition() {
		return originPosition;
	}

	public void setOriginPosition(Point3D originPosition) {
		this.originPosition = originPosition;
	}

	public String getLoiterDestType() {
		return loiterDestType;
	}

	public void setLoiterDestType(String loiterDestType) {
		this.loiterDestType = loiterDestType;
	}

	public float getMinLoiterDist() {
		return minLoiterDist;
	}

	public void setMinLoiterDist(float minLoiterDist) {
		this.minLoiterDist = minLoiterDist;
	}

	public float getMaxLoiterDist() {
		return maxLoiterDist;
	}

	public void setMaxLoiterDist(float maxLoiterDist) {
		this.maxLoiterDist = maxLoiterDist;
	}

	public String getWaitState() {
		return waitState;
	}

	public void setWaitState(String waitState) {
		this.waitState = waitState;
	}

	public long getWaitStartTime() {
		return waitStartTime;
	}

	public void setWaitStartTime(long waitStartTime) {
		this.waitStartTime = waitStartTime;
	}

	public Vector<Point3D> getPatrolPoints() {
		return patrolPoints;
	}

	public TurretAIState getIntendedPrimaryTurretAIState() {
		return intendedPrimaryTurretAIState;
	}

	public void setIntendedPrimaryTurretAIState(TurretAIState intendedPrimaryTurretAIState) {
		this.intendedPrimaryTurretAIState = intendedPrimaryTurretAIState;
	}

	public Point3D getLastPositionBeforeStateChange() {
		return lastPositionBeforeStateChange;
	}

	public void setLastPositionBeforeStateChange(
			Point3D lastPositionBeforeStateChange) {
		this.lastPositionBeforeStateChange = lastPositionBeforeStateChange;
	}

	public boolean isPatrolLoop() {
		return patrolLoop;
	}

	public void setPatrolLoop(boolean patrolLoop) {
		this.patrolLoop = patrolLoop;
	}
	
}
