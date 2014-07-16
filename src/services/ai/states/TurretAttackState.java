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
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.ai.AIActor;
import services.ai.TurretAIActor;
import tools.DevLog;

public class TurretAttackState extends TurretAIState {

	@Override
	public byte onEnter(TurretAIActor actor) {
//		DevLog.debugout("Charon", "Turret AI Attack State", "onEnter");
		TangibleObject creature = actor.getCreature();
		
//		DevLog.debugout("Charon", "Turret AI Attack State", "onEnter creature.getConditionDamage() " + creature.getConditionDamage());
//		DevLog.debugout("Charon", "Turret AI Attack State", "onEnter creature.getMaximumCondition() " + creature.getMaximumCondition());
		
		if(creature.getConditionDamage()>=creature.getMaximumCondition())
			return StateResult.DEAD;
		
		
//		DevLog.debugout("Charon", "Turret AI Attack State", "onEnter creature.getDefendersList().size() " + creature.getDefendersList().size());
//		DevLog.debugout("Charon", "Turret AI Attack State", "onEnter actor.getFollowObject() " + actor.getFollowObject());
		if(!creature.isInCombat() || creature.getDefendersList().size() == 0 || actor.getFollowObject() == null)
			return StateResult.FINISHED;
		
		actor.scheduleRecovery();
		return StateResult.UNFINISHED;
	}

	@Override
	public byte onExit(TurretAIActor actor) {
		// TODO Auto-generated method stub

//		actor.getCreature().setLookAtTarget(0);
//		actor.getCreature().setIntendedTarget(0);
		actor.setFollowObject(null);
//		DevLog.debugout("Charon", "AI Attack State", "onExit");
		return StateResult.FINISHED;
	}

	@Override
	public byte move(TurretAIActor actor) {
		
		return StateResult.FINISHED;
		
	}

	@Override
	public byte recover(TurretAIActor actor) {
		TangibleObject creature = actor.getCreature();
		float maxDistance = 0;
		WeaponObject weapon = null;
		//weapon = (WeaponObject) creature.getSlottedObject("default_weapon");
		
		long weaponID = (long) creature.getAttachment("TurretWeapon");
		weapon = (WeaponObject) NGECore.getInstance().objectService.getObject(weaponID);
		//DevLog.debugout("Charon", "AI Attack State", "weapontype " + weapon.getTemplate()); 
		if(weapon == null)
			return StateResult.FINISHED;
		

		maxDistance = weapon.getMaxRange() - 1;
		
		if(actor.getTimeSinceLastAttack() < weapon.getAttackSpeed() * 1000) {
			//actor.scheduleRecovery();
			return StateResult.UNFINISHED;
		}
		NGECore core = NGECore.getInstance();
		if(creature.getConditionDamage()>creature.getMaximumCondition())
			return StateResult.DEAD;
		
		if(!creature.isInCombat() || creature.getDefendersList().size() == 0 || actor.getFollowObject() == null)
		{
//			creature.setLookAtTarget(0);
//			creature.setIntendedTarget(0);
			actor.setFollowObject(null);
			actor.setCurrentState(new TurretIdleState());
			return StateResult.FINISHED;
		}
		
		TangibleObject target = actor.getFollowObject();
		if(target != actor.getHighestDamageDealer() && actor.getHighestDamageDealer() != null) {
			actor.setFollowObject(actor.getHighestDamageDealer());
			target = actor.getFollowObject();
		}
		if(target == null) {
			DevLog.debugout("Charon", "AI Attack State", "null target"); 
			actor.scheduleRecovery();
			return StateResult.UNFINISHED;
		}
		
		if (target instanceof CreatureObject){
			CreatureObject targetCreature = (CreatureObject) target;
			if(targetCreature.getPosture() == 13 || targetCreature.getPosture() == 14 || targetCreature.isInStealth()) {
				 
				actor.setFollowObject(actor.getHighestDamageDealer());			
				target = (CreatureObject) actor.getFollowObject();
				if(target == null)
				{
//					creature.setLookAtTarget(0);
//					creature.setIntendedTarget(0);
					
				}
				actor.setFollowObject(null);
				actor.removeDefender(target);

				actor.setCurrentState(new TurretIdleState());
				return StateResult.FINISHED;
			}
			if(targetCreature.getWorldPosition().getDistance(creature.getWorldPosition()) > 128 || targetCreature.getPosture() == 13 || targetCreature.getPosture() == 14) {
				actor.removeDefender(target);
				actor.scheduleRecovery();
				return StateResult.UNFINISHED;
			}
		}
		
//		DevLog.debugout("Charon", "AI Attack State", "prior to attacks dist " + target.getWorldPosition().getDistance(creature.getWorldPosition())); 
//		DevLog.debugout("Charon", "AI Attack State", "prior to attacks maxDistance " + maxDistance); 
		
		if(target.getWorldPosition().getDistance(creature.getWorldPosition()) > maxDistance) {
			actor.scheduleRecovery();
			return StateResult.UNFINISHED;
		}
		//actor.faceObject(target); 
		core.commandService.callCommand(creature, "rangedShot", target, "");
		
		actor.setLastAttackTimestamp(System.currentTimeMillis());
		actor.scheduleRecovery();
		return StateResult.UNFINISHED;
	}

}
