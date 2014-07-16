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

import main.NGECore;
import resources.objects.creature.CreatureObject;
import resources.objects.weapon.WeaponObject;
import services.ai.AIActor;
import tools.DevLog;

public class AttackState extends AIState {

	@Override
	public byte onEnter(AIActor actor) {
		DevLog.debugout("Charon", "AI Attack State", "onEnter");
		CreatureObject creature = actor.getCreature();
		if(creature.getPosture() == 14)
			return StateResult.DEAD;
		if(!creature.isInCombat() || creature.getDefendersList().size() == 0 || actor.getFollowObject() == null)
			return StateResult.FINISHED;
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
		DevLog.debugout("Charon", "AI Attack State", "onExit");
		return StateResult.FINISHED;
	}

	@Override
	public byte move(AIActor actor) {
		CreatureObject creature = actor.getCreature();
		if(creature.getPosture() == 14)
			return StateResult.DEAD;
		actor.getMovementPoints().clear();
		if(actor.getFollowObject() != null) {
			if(actor.getSpawnPosition().getWorldPosition().getDistance(creature.getWorldPosition()) > 128 || NGECore.getInstance().terrainService.isWater(creature.getPlanetId(), actor.getFollowObject().getWorldPosition())) {
				actor.removeDefender(actor.getFollowObject());
				//actor.scheduleMovement();
				return StateResult.UNFINISHED;
			}
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
				if(actor.getFollowObject().getWorldPosition().getDistance(creature.getWorldPosition()) > maxDistance)
					actor.setNextPosition(actor.getFollowObject().getPosition());
				else {
					//recover(actor);
					actor.faceObject(actor.getFollowObject());
					actor.scheduleMovement();
					return StateResult.UNFINISHED;
			}
			} catch (Exception e){
				DevLog.debugout("Charon", "AI Attack State Exception move method", "actor " + actor);
				DevLog.debugout("Charon", "AI Attack State Exception move method", "actor.getFollowObject() " + actor.getFollowObject());
				DevLog.debugout("Charon", "AI Attack State Exception move method", "actor.getFollowObject().getWorldPosition() " + actor.getFollowObject().getWorldPosition());
				DevLog.debugout("Charon", "AI Attack State Exception move method", "creature.getWorldPosition() " + creature.getWorldPosition());
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
			//actor.scheduleRecovery();
			return StateResult.UNFINISHED;
		}
		NGECore core = NGECore.getInstance();
		if(creature.getPosture() == 14)
			return StateResult.DEAD;
		if(!creature.isInCombat() || creature.getDefendersList().size() == 0 || actor.getFollowObject() == null)
		{
			if (creature.getLookAtTarget() != 0)
				creature.setLookAtTarget(0);
			if (creature.getIntendedTarget() != 0)
				creature.setIntendedTarget(0);
			actor.setFollowObject(null);
			actor.setCurrentState(new RetreatState());
			return StateResult.FINISHED;
		}
		CreatureObject target = actor.getFollowObject();
		if(target != actor.getHighestDamageDealer() && actor.getHighestDamageDealer() != null) {
			actor.setFollowObject(actor.getHighestDamageDealer());
			target = actor.getFollowObject();
		}
		if(target == null) {
			DevLog.debugout("Charon", "AI Attack State", "null target"); 
			actor.scheduleRecovery();
			return StateResult.UNFINISHED;
		}
		if(target.getPosture() == 13 || target.getPosture() == 14 || target.isInStealth()) {
 
			actor.setFollowObject(actor.getHighestDamageDealer());			
			target = actor.getFollowObject();
			if(target == null)
			{
				if (creature.getLookAtTarget() != 0)
					creature.setLookAtTarget(0);
				if (creature.getIntendedTarget() != 0)
					creature.setIntendedTarget(0);
				
			}
			actor.setFollowObject(null);
			actor.removeDefender(target);

			actor.setCurrentState(new RetreatState());
			return StateResult.FINISHED;
		}
		if(target.getWorldPosition().getDistance(creature.getWorldPosition()) > 128 || target.getPosture() == 13 || target.getPosture() == 14) {
			actor.removeDefender(target);
			actor.scheduleRecovery();
			return StateResult.UNFINISHED;
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
		if (creature.getLookAtTarget() != target.getObjectId())
			creature.setLookAtTarget(target.getObjectId());
		if (creature.getIntendedTarget() != target.getObjectId())
			creature.setIntendedTarget(target.getObjectId());
		
		if(attacks.size() == 0) {
			core.commandService.callCommand(creature, actor.getMobileTemplate().getDefaultAttack(), target, "");
		} else {
			Random rand = new Random();
			if(rand.nextFloat() <= 0.33f) {
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
