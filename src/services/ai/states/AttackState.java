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
package services.ai.states;

import java.util.Random;
import java.util.Vector;

import engine.resources.scene.Point3D;
import main.NGECore;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.ai.AIActor;
import tools.DevLog;

public class AttackState extends AIState {

	@Override
	public byte onEnter(AIActor actor) {
		DevLog.debugoutai(actor, "Charon", "AI Attack State", "onEnter");
		
		CreatureObject creature = actor.getCreature();
		if (creature == null || actor==null)
			return StateResult.DEAD;
		DevLog.debugoutai(actor, "Charon", "AI Attack State", "creature.isInCombat() " + creature.isInCombat());
		DevLog.debugoutai(actor, "Charon", "AI Attack State", "creature.getDefendersList().size() == 0 " + (creature.getDefendersList().size() == 0));
		DevLog.debugoutai(actor, "Charon", "AI Attack State", "actor.getFollowObject() == null " + (actor.getFollowObject() == null));
		if(creature.getPosture() == 14)
			return StateResult.DEAD;
		if(!creature.isInCombat() || creature.getDefendersList().size() == 0 || actor.getFollowObject() == null)
			return StateResult.FINISHED;
		creature.setLookAtTarget(actor.getFollowObject().getObjectID());
		creature.setIntendedTarget(actor.getFollowObject().getObjectID());
		actor.scheduleMovement();
		actor.scheduleRecovery();
		return StateResult.UNFINISHED;
	}

	@Override
	public byte onExit(AIActor actor) {
		// TODO Auto-generated method stub

		actor.getCreature().setLookAtTarget(0);
		actor.getCreature().setIntendedTarget(0);
		actor.setFollowObject(null);
		DevLog.debugoutai(actor, "Charon", "AI Attack State", "onExit");
		return StateResult.FINISHED;
	}

	@Override
	public byte move(AIActor actor) {
		CreatureObject creature = actor.getCreature();
		if (creature==null)
			return StateResult.FINISHED;
		
		if(creature.getPosture() == 14)
			return StateResult.DEAD;
		actor.getMovementPoints().clear();
		if(actor.getFollowObject() != null) {
			Point3D checkPosition = actor.getSpawnPosition().getWorldPosition(); // IdleState default
			if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
				checkPosition = creature.getWorldPosition();
			if (actor.getIntendedPrimaryAIState().getClass().equals(FollowState.class))
				checkPosition = creature.getWorldPosition();
			
			try {
				if(checkPosition.getDistance(creature.getWorldPosition()) > 128 || NGECore.getInstance().terrainService.isWater(creature.getPlanetId(), actor.getFollowObject().getWorldPosition())) {
					actor.removeDefender(actor.getFollowObject());
					//actor.scheduleMovement();
					//return StateResult.UNFINISHED;
					return StateResult.FINISHED;
				}
			} catch (Exception ex){} // When the followobject turns becomes null inside the if scope an npe can occur
			
			float maxDistance = 0;
			if(creature.getWeaponId() != 0) {
				WeaponObject weapon = (WeaponObject) NGECore.getInstance().objectService.getObject(creature.getWeaponId());
				if(weapon != null)
					maxDistance = weapon.getMaxRange() - 1;
			} else if(creature.getSlottedObject("default_weapon") != null) {
				WeaponObject weapon = (WeaponObject) creature.getSlottedObject("default_weapon");
				if(weapon != null)
					maxDistance = weapon.getMaxRange() - 1;
			}
			try{
				if(actor.getFollowObject().getWorldPosition().getDistance(creature.getWorldPosition()) > maxDistance){
					actor.setNextPosition(actor.getFollowObject().getPosition());
					// find los pos
				} else {
					//recover(actor);
					actor.faceObject(actor.getFollowObject());
					actor.scheduleMovement();
					return StateResult.UNFINISHED;
			}
			} catch (Exception e){
				if (actor!=null)
					DevLog.debugoutai(actor, "Charon", "AI Attack State Exception move method", "actor " + actor);
				if (actor.getFollowObject()!=null)
				DevLog.debugoutai(actor, "Charon", "AI Attack State Exception move method", "actor.getFollowObject() " + actor.getFollowObject());
				if (actor.getFollowObject()!=null)
				DevLog.debugoutai(actor, "Charon", "AI Attack State Exception move method", "actor.getFollowObject().getWorldPosition() " + actor.getFollowObject().getWorldPosition());
				if (creature!=null)
				DevLog.debugoutai(actor, "Charon", "AI Attack State Exception move method", "creature.getWorldPosition() " + creature.getWorldPosition());
			}

		}
		else
			return StateResult.FINISHED;
		doMove(actor);
		actor.scheduleMovement();
		return StateResult.UNFINISHED;
	}

	@Override
	public byte recover(AIActor actor) {
		CreatureObject creature = actor.getCreature();
		DevLog.debugoutai(actor, "Charon", "AI Attack Recover State", creature.getTemplate());
		float maxDistance = 0;
		WeaponObject weapon = null;
		if(creature.getWeaponId() != 0) {
			weapon = (WeaponObject) NGECore.getInstance().objectService.getObject(creature.getWeaponId());
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		} else if(creature.getSlottedObject("default_weapon") != null) {
			weapon = (WeaponObject) creature.getSlottedObject("default_weapon");
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		}
		if(weapon == null)
			return StateResult.FINISHED;
		if(actor.getTimeSinceLastAttack() < weapon.getAttackSpeed() * 1000) {
			actor.scheduleRecovery();
			return StateResult.UNFINISHED;
		}
		NGECore core = NGECore.getInstance();
		if(creature.getPosture() == 14)
			return StateResult.DEAD;
		
		
		TangibleObject target = actor.getFollowObject();
		if(target != actor.getHighestDamageDealer() && actor.getHighestDamageDealer() != null) {
			actor.setFollowObject(actor.getHighestDamageDealer());
			target = actor.getFollowObject();
		}
		if(target == null) {
			DevLog.debugoutai(actor, "Charon", "AI Attack State", "null target"); 
			actor.scheduleRecovery();
			return StateResult.UNFINISHED;
		}
		
		// If AI has no LOS to target, reposition it
		if (!core.simulationService.checkLineOfSight(target,creature)){
			actor.setLastTarget(target);
			actor.setCurrentState(new RepositionState()); 	
			return StateResult.FINISHED;
		}
		
		
		if(!creature.isInCombat() || creature.getDefendersList().size() == 0 || actor.getFollowObject() == null)
		{
			
//			System.out.println("creature.isInCombat() " + creature.isInCombat());
//			System.out.println("creature.getDefendersList().size() " + creature.getDefendersList().size());
//			System.out.println("actor.getFollowObject() " + actor.getFollowObject());
			creature.setLookAtTarget(0);
			creature.setIntendedTarget(0);
			actor.setFollowObject(null);		
			if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
				actor.setCurrentState(new RetreatState()); 	
			else {
				actor.setCurrentState(new RetreatState()); 
			}
			return StateResult.FINISHED;
		}
		
		
		if (target instanceof CreatureObject){
			CreatureObject targetCreature = (CreatureObject) target;
			if(targetCreature.getPosture() == 13 || targetCreature.getPosture() == 14 || targetCreature.isInStealth()) {
				 
				actor.setFollowObject(actor.getHighestDamageDealer());			
				target = (CreatureObject) actor.getFollowObject();
				if(target == null)
				{
					creature.setLookAtTarget(0);
					creature.setIntendedTarget(0);
					
				}
				actor.setFollowObject(null);
				actor.removeDefender(target);

				if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
					actor.setCurrentState(new RetreatState()); 	
				else {
					actor.setCurrentState(new RetreatState()); 
				}
				return StateResult.FINISHED;
			}
			if(targetCreature.getWorldPosition().getDistance(creature.getWorldPosition()) > 128 || targetCreature.getPosture() == 13 || targetCreature.getPosture() == 14) {
				actor.removeDefender(target);
				actor.scheduleRecovery();
				return StateResult.UNFINISHED;
			}
		} else {
			TangibleObject targetObject = (TangibleObject) target;
			if((targetObject.getConditionDamage()>=targetObject.getMaximumCondition())) {
				 
				actor.setFollowObject(actor.getHighestDamageDealer());			
				target = (CreatureObject) actor.getFollowObject();
				if(target == null)
				{
					creature.setLookAtTarget(0);
					creature.setIntendedTarget(0);
					
				}
				actor.setFollowObject(null);
				actor.removeDefender(target);

				if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
					actor.setCurrentState(new RetreatState()); 	
				else {
					actor.setCurrentState(new RetreatState()); 
				}
				return StateResult.FINISHED;
			}			
		}
		
		if(target.getWorldPosition().getDistance(creature.getWorldPosition()) > maxDistance) {
			actor.scheduleRecovery();
			return StateResult.UNFINISHED;
		}
		//actor.faceObject(target);
		
		Vector<String> attacks = actor.getMobileTemplate().getAttacks();
		
		// Pet
//		if (creature.getOwnerId()>0)
//			attacks = creature.getSpecialAttacks();
		
		creature.setLookAtTarget(target.getObjectId());
		creature.setIntendedTarget(target.getObjectId());
		
		if (attacks==null) {		
		//if(attacks.size() == 0) {
			core.commandService.callCommand(creature, actor.getMobileTemplate().getDefaultAttack(), target, "");
		} else {
			Random rand = new Random();
			if(rand.nextFloat() <= 0.33f) {
				if(attacks.size() > 0)
					core.commandService.callCommand(creature, attacks.get(rand.nextInt(attacks.size())), target, "");
			} else {
				core.commandService.callCommand(creature, actor.getMobileTemplate().getDefaultAttack(), target, "");
			}
		}
		actor.setLastAttackTimestamp(System.currentTimeMillis());
		actor.scheduleRecovery();
		return StateResult.UNFINISHED;
	}

}
