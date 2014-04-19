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
import java.util.concurrent.TimeUnit;

import main.NGECore;
import net.engio.mbassy.listener.Handler;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.objects.cell.CellObject;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import services.ai.states.AIState;
import services.ai.states.AIState.StateResult;
import services.ai.states.AttackState;
import services.ai.states.DeathState;
import services.ai.states.IdleState;
import services.ai.states.RetreatState;
import services.combat.CombatEvents.DamageTaken;
import services.spawn.MobileTemplate;

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
	
	public AIActor(CreatureObject creature, Point3D spawnPosition, ScheduledExecutorService scheduler) {
		this.creature = creature;
		this.spawnPosition = spawnPosition;
		this.scheduler = scheduler;
		creature.getEventBus().subscribe(this);
		this.currentState = new IdleState();
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
		creature.addDefender(defender);
		if(followObject == null)
			setFollowObject(defender);
		setCurrentState(new AttackState());
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
		scheduler.schedule(() -> { 
			try {
				doStateAction(currentState.move(AIActor.this));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 500, TimeUnit.MILLISECONDS);
	}
	
	public void scheduleRecovery() {
		scheduler.schedule(() -> { 
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
		
		switch(result) {
		
			case StateResult.DEAD:
				setCurrentState(new DeathState());
			case StateResult.FINISHED:
			case StateResult.UNFINISHED:
				return;
			case StateResult.IDLE:
				setCurrentState(new IdleState());
			//case StateResult.NONE:
		//		System.out.println("State action returned error result");
		
		}
		
	}
	
	public void scheduleDespawn() {
		scheduler.schedule(() -> {
			
			damageMap.clear();
			followObject = null;
			creature.setAttachment("AI", null);
			NGECore.getInstance().objectService.destroyObject(creature);
			
		}, 30000, TimeUnit.MILLISECONDS);
	}
	
}
