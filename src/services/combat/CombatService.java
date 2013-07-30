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
package services.combat;

import java.util.Map;

import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.command.CombatCommand;

import main.NGECore;

import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class CombatService implements INetworkDispatch {
	
	private NGECore core;

	public CombatService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public void doCombat(CreatureObject attacker, TangibleObject target, WeaponObject weapon, CombatCommand command) {
		
		if(!attemptCombat(attacker, target))
			return;
		
		if(!applySpecialCost(attacker, weapon, command))
			return;
		
	}

	private boolean attemptCombat(CreatureObject attacker, TangibleObject target) {
		
		if(target.getDefendersList().contains(attacker) && attacker.getDefendersList().contains(target))
			return true;
		
		if(attacker.getStateBitmask() == 0x8000000)
			return false;
		
		if(!target.isAttackableBy(attacker))
			return false;
		
		target.addDefender(attacker);
		attacker.addDefender(target);
		
		return true;
		
	}
	
	private boolean applySpecialCost(CreatureObject attacker, WeaponObject weapon, CombatCommand command) {
		
		float actionCost = command.getActionCost();
		float healthCost = command.getHealthCost();
		
		if(actionCost == 0 && healthCost == 0)
			return true;
		
		float newAction = attacker.getAction() - actionCost;
		if(newAction <= 0)
			return false;
		
		float newHealth = attacker.getHealth() - healthCost;
		if(newHealth <= 0)
			return false;
		
		if(newAction != attacker.getAction())
			attacker.setAction((int) newAction);
		
		if(newHealth != attacker.getHealth())
			attacker.setHealth((int) newHealth);

		return true;
		
	}

}
