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
import resources.objects.tangible.TangibleObject;
import services.ai.states.AIState;
import services.ai.states.AIState.StateResult;
import services.ai.states.AttackState;
import services.ai.states.DeathState;
import services.ai.states.FollowState;
import services.ai.states.IdleState;
import services.ai.states.LoiterState;
import services.ai.states.PatrolState;
import services.ai.states.RetreatState;
import services.combat.CombatEvents.DamageTaken;
import services.spawn.MobileTemplate;
import tools.DevLog;
import resources.datatables.FactionStatus;

import java.util.Random;

public class AIActor {
	
	private CreatureObject creature;
	private Point3D spawnPosition;
	private volatile AIState currentState;
	private CreatureObject followObject;
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
	private AIState intendedPrimaryAIState;
	private Point3D lastPositionBeforeStateChange;
	private ScheduledFuture<?> movementFuture;
	private ScheduledFuture<?> recoveryFuture;
	private ScheduledFuture<?> despawnFuture;

	public AIActor(CreatureObject creature, Point3D spawnPosition, ScheduledExecutorService scheduler) {
		this.creature = creature;
		this.spawnPosition = spawnPosition;
		setLastPositionBeforeStateChange(spawnPosition); // just to make sure it's initialized
		this.scheduler = scheduler;
		creature.getEventBus().subscribe(this);
		this.currentState = new IdleState();
		setIntendedPrimaryAIState(this.currentState); // to switch back to after aggro
		regenTask = scheduler.scheduleAtFixedRate(() -> {
			try {
				if(creature.getHealth() < creature.getMaxHealth() && !creature.isInCombat() && creature.getPosture() != 13 && creature.getPosture() != 14)
					creature.setHealth(creature.getHealth() + (36 + creature.getLevel() * 4));
				if(creature.getAction() < creature.getMaxAction() && creature.getPosture() != 14) {
					if(!creature.isInCombat())
						creature.setAction(creature.getAction() + (15 + creature.getLevel() * 5));
					else
						creature.setAction(creature.getAction() + ((15 + creature.getLevel() * 5) / 2));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 0, 1000, TimeUnit.MILLISECONDS);
		if(creature.getOption(Options.AGGRESSIVE)) {
			aggroCheckTask = scheduler.scheduleAtFixedRate(() -> {
				try {
					
					if(creature == null || creature.getObservers().isEmpty() || creature.isInCombat() || isStalking)
						return;
					if (creature.getAttachment("tamed")!=null){
						DevLog.debugout("Charon", "Pet AI", "aggroCheckTask tamed==1");
						return;
					}
					
					if (creature.getCustomName().contains("baby"))
						DevLog.debugout("Charon", "Pet AI", "baby aggroCheckTask should not be here " +creature.getAttachment("tamed"));
					
					creature.getObservers().stream().map(Client::getParent).filter(obj -> obj.inRange(creature.getWorldPosition(), 15)).forEach((obj) -> {
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
			}, 0, 5000, TimeUnit.MILLISECONDS);
		}
		if(creature.getFaction().length()>0 || !creature.getOption(Options.AGGRESSIVE)) {
			factionCheckTask = scheduler.scheduleAtFixedRate(() -> {
				try {
					
					if(creature == null || creature.getFactionStatus()!=FactionStatus.Combatant || creature.getObservers().isEmpty() || creature.isInCombat())
						return;
																
					creature.getObservers().stream().map(Client::getParent).filter(obj -> obj.inRange(creature.getWorldPosition(), 15)).forEach((obj) -> {
						if(new Random().nextFloat() <= 0.5 || creature.isInCombat()) {
							DevLog.debugout("Charon", "CHECK faction creature ", "added " + creature.getFaction());
							DevLog.debugout("Charon", "CHECK faction obj", "added " + ((TangibleObject)obj).getFaction());
							DevLog.debugout("Charon", "CHECK factionCheckTask", "added " + obj.getCustomName());
							DevLog.debugout("Charon", "CHECK isFactionEnemy", "res " + NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj));
							if (obj instanceof CreatureObject && NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj)){
								CreatureObject addedObject = (CreatureObject) obj;
								if (addedObject.getCalledPet()!=null){
									CreatureObject calledPet = addedObject.getCalledPet();
									if (calledPet.getPosture() != 13 && calledPet.getPosture() != 14){
										addDefender(calledPet);	
									}
								}
								if (addedObject.getPosture() != 13 && addedObject.getPosture() != 14){
									addDefender(addedObject);	
									DevLog.debugout("Charon", "faction creature ", "added " + creature.getFaction());
									DevLog.debugout("Charon", "faction obj", "added " + ((TangibleObject)obj).getFaction());
									DevLog.debugout("Charon", "factionCheckTask", "added " + obj.getCustomName());
								}
							}
						}
					});
					
					NGECore.getInstance().simulationService.getAllNearNPCs(15, creature).forEach((obj) -> {
						if(new Random().nextFloat() <= 0.5 || creature.isInCombat()) {
							DevLog.debugout("Charon", "CHECK faction creature ", "added " + creature.getFaction());
							DevLog.debugout("Charon", "CHECK faction obj", "added " + ((TangibleObject)obj).getFaction());
							DevLog.debugout("Charon", "CHECK factionCheckTask", "added " + obj.getCustomName());
							DevLog.debugout("Charon", "CHECK isFactionEnemy", "res " + NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj));
							if (obj instanceof CreatureObject && NGECore.getInstance().factionService.isFactionEnemy((TangibleObject)creature, (TangibleObject)obj)){
								CreatureObject addedObject = (CreatureObject) obj;
								if (addedObject.getCalledPet()!=null){
									CreatureObject calledPet = addedObject.getCalledPet();
									if (calledPet.getPosture() != 13 && calledPet.getPosture() != 14){
										addDefender(calledPet);	
									}
								}
								if (addedObject.getPosture() != 13 && addedObject.getPosture() != 14){
									addDefender(addedObject);	
									DevLog.debugout("Charon", "faction creature ", "added " + creature.getFaction());
									DevLog.debugout("Charon", "faction obj", "added " + ((TangibleObject)obj).getFaction());
									DevLog.debugout("Charon", "factionCheckTask", "added " + obj.getCustomName());
								}
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}, 0, 5000, TimeUnit.MILLISECONDS);

		}
	}

	public CreatureObject getCreature() {
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
	
	public void addDefender(CreatureObject defender) {
		addDefender(defender, false);
	}

		
	public void addDefender(CreatureObject defender, boolean isAssisting) {
		creature.addDefender(defender);
		if(followObject == null)
			setFollowObject(defender);
		setCurrentState(new AttackState());
		if(!isAssisting) {
			NGECore.getInstance().simulationService.get(creature.getPlanet(), creature.getWorldPosition().x, creature.getWorldPosition().z, 38).stream().filter((obj) -> 
				obj instanceof CreatureObject && 
				obj.getAttachment("AI") != null && 
				((AIActor) obj.getAttachment("AI")).getMobileTemplate().getSocialGroup().equals(getMobileTemplate().getSocialGroup()) &&
				obj.inRange(creature.getWorldPosition(), ((AIActor) obj.getAttachment("AI")).getMobileTemplate().getAssistRange())
			).forEach((obj) -> ((AIActor) obj.getAttachment("AI")).addDefender(defender, true));
		}
	}
	
	public void removeDefender(CreatureObject defender) {
		creature.removeDefender(defender);
		damageMap.remove(defender);
		defender.removeDefender(creature);
		if(followObject == defender) {
			setFollowObject(getHighestDamageDealer());
			if(creature.getDefendersList().size() == 0)
				setCurrentState(new RetreatState());
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

	public AIState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(AIState currentState) {
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

	public CreatureObject getFollowObject() {
		return followObject;
	}

	public void setFollowObject(CreatureObject followObject) {
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
	
	public void scheduleMovement() {
		movementFuture = scheduler.schedule(() -> { 
			try {
				if (creature==null){
					destroyActor();
					return;
				}
				doStateAction(currentState.move(AIActor.this));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 500, TimeUnit.MILLISECONDS);
	}
	
	public void scheduleRecovery() {
		recoveryFuture = scheduler.schedule(() -> { 
			try {
				doStateAction(currentState.recover(AIActor.this));
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
		CreatureObject attacker = event.attacker;
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
				setCurrentState(new DeathState());
				break;
			case StateResult.FINISHED:
			case StateResult.UNFINISHED:
				return;
			case StateResult.IDLE:
				setCurrentState(new IdleState());
				return;
			case StateResult.PATROL:
				setCurrentState(new PatrolState());
				return;
			case StateResult.LOITER:
				setCurrentState(new LoiterState());
				return;
			case StateResult.FOLLOW:
				setCurrentState(new FollowState());
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
		
		despawnFuture = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					damageMap.clear();
					followObject = null;
					creature.setAttachment("AI", null);
					NGECore.getInstance().objectService.destroyObject(creature);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 2, TimeUnit.MINUTES);
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
		if (movementFuture!=null){
			movementFuture.cancel(true); 
			movementFuture = null;
		}
		if (movementFuture!=null){
			recoveryFuture.cancel(true);			
			recoveryFuture = null;
		}
		if (despawnFuture!=null){
			despawnFuture.cancel(true);			
			despawnFuture = null;
		}		
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

	public AIState getIntendedPrimaryAIState() {
		return intendedPrimaryAIState;
	}

	public void setIntendedPrimaryAIState(AIState intendedPrimaryAIState) {
		this.intendedPrimaryAIState = intendedPrimaryAIState;
	}

	public Point3D getLastPositionBeforeStateChange() {
		return lastPositionBeforeStateChange;
	}

	public void setLastPositionBeforeStateChange(
			Point3D lastPositionBeforeStateChange) {
		this.lastPositionBeforeStateChange = lastPositionBeforeStateChange;
	}
	
}
