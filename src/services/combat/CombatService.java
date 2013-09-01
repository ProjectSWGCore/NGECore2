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

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;
import protocol.swg.objectControllerObjects.CombatAction;
import protocol.swg.objectControllerObjects.CombatSpam;
import protocol.swg.objectControllerObjects.CommandEnqueueRemove;
import protocol.swg.objectControllerObjects.StartTask;
import resources.common.FileUtilities;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.command.CombatCommand;
import main.NGECore;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class CombatService implements INetworkDispatch {
	
	private NGECore core;

	public CombatService(NGECore core) {
		this.core = core;
		CombatCommands.registerCommands(core);
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public void doCombat(CreatureObject attacker, TangibleObject target, WeaponObject weapon, CombatCommand command, int actionCounter) {
		
		
		if(!applySpecialCost(attacker, weapon, command))
			return;
		
		if((command.getAttackType() == 0 || command.getAttackType() == 1 || command.getAttackType() == 3) && !attemptCombat(attacker, target))
			return;

		// use preRun for delayed effects like officer orbital strike, grenades etc.
		if(FileUtilities.doesFileExist("scripts/commands/combat" + command.getCommandName() + ".py"))
			core.scriptService.callScript("scripts/commands/combat", command.getCommandName(), "preRun", core, attacker, target, command);
		
		if(command.getAttackType() == 1)
			doSingleTargetCombat(attacker, target, weapon, command, actionCounter);
		else if(command.getAttackType() == 0 || command.getAttackType() == 2 || command.getAttackType() == 3)
			doAreaCombat(attacker, target, weapon, command, actionCounter);
		
	}

	private void doAreaCombat(CreatureObject attacker, TangibleObject target, WeaponObject weapon, CombatCommand command, int actionCounter) {
		if(target instanceof CreatureObject) {
			doAreaCombat(attacker, (CreatureObject) target, weapon, command, actionCounter);
			return;
		}
	}

	private void doSingleTargetCombat(CreatureObject attacker, TangibleObject target, WeaponObject weapon, CombatCommand command, int actionCounter) {	
		if(target instanceof CreatureObject) {
			doSingleTargetCombat(attacker, (CreatureObject) target, weapon, command, actionCounter);
			return;
		}
		
		float damage = calculateDamage(attacker, target, weapon, command);
	}
	
	private void doAreaCombat(CreatureObject attacker, CreatureObject target, WeaponObject weapon, CombatCommand command, int actionCounter) {
		
		float x = attacker.getWorldPosition().x;
		float z = attacker.getWorldPosition().z;
		
		float dirX = target.getWorldPosition().x - x;
		float dirZ = target.getWorldPosition().z - z;
		
		float range = command.getConeLength();
		
		
		List<SWGObject> inRangeObjects = core.simulationService.get(attacker.getPlanet(), target.getWorldPosition().x, target.getWorldPosition().z, (int) range);
		
		for(SWGObject obj : inRangeObjects) {
			
			if(!(obj instanceof TangibleObject) || obj == attacker)
				continue;
			
			if(obj instanceof CreatureObject && (((CreatureObject) obj).getPosture() == 13 || ((CreatureObject) obj).getPosture() == 14))
				continue;

			if(command.getAttackType() == 0 && !isInConeAngle(attacker, obj, (int) command.getConeLength(), (int) command.getConeWidth(), dirX, dirZ))
				continue;
			
			if(!core.simulationService.checkLineOfSight(target, obj))
				continue;
			
			if(!attemptCombat(attacker, (TangibleObject) obj))
				continue;
			
			doSingleTargetCombat(attacker, (TangibleObject) obj, weapon, command, actionCounter);
			
		}
		
	}

	private void doSingleTargetCombat(CreatureObject attacker, CreatureObject target, WeaponObject weapon, CombatCommand command, int actionCounter) {	
				
		float damage = calculateDamage(attacker, target, weapon, command);
		
		byte hitType = getHitType(attacker, target, weapon, command);
		
		switch(hitType) {
		
			case HitType.MISS:
				damage = 0;
				break;
				
			case HitType.DODGE:
				damage = 0;
				break;

			case HitType.PARRY:
				damage = 0;
				break;
				
			case HitType.CRITICAL:
				damage *= 1.5f;
				break;

		}
		byte mitigationType = -1;
		if(hitType == HitType.CRITICAL || hitType == HitType.HIT || hitType == HitType.STRIKETHROUGH) {
			mitigationType = doMitigationRolls(attacker, target, weapon, command, hitType);
			
			if(mitigationType == HitType.GLANCE) {
				damage *= 0.4f;
			} else if(mitigationType == HitType.EVASION) {
				float evasionValue = (attacker.getSkillMod("combat_evasion_value").getBase() / 4) / 100;
				
				damage *= (1 - evasionValue);
				
			}
			
		}
		int damageBeforeArmor = (int) damage;
		damage *= (1 - getArmorReduction(attacker, target, weapon, command, hitType));
		int armorAbsorbed = (int) (damageBeforeArmor - damage);
		if(mitigationType == HitType.BLOCK) {
				
			float blockValue = (attacker.getSkillMod("strength_modified").getBase() + attacker.getSkillMod("combat_block_value").getBase()) / 2 + 25;
			damage -= blockValue;
			
		}

		if(damage > 0)
			applyDamage(attacker, target, (int) damage);
		
		sendCombatPackets(attacker, target, weapon, command, actionCounter, damage, armorAbsorbed, hitType);

		if(FileUtilities.doesFileExist("scripts/commands/combat" + command.getCommandName() + ".py"))
			core.scriptService.callScript("scripts/commands/combat", command.getCommandName(), "run", core, attacker, target, null);

	}
	


	private void sendCombatPackets(CreatureObject attacker, CreatureObject target, WeaponObject weapon, CombatCommand command, int actionCounter, float damage, int armorAbsorbed, int hitType) {
		
		String animationStr = command.getRandomAnimation(weapon);
		
		CombatAction combatAction = new CombatAction(CRC.StringtoCRC(animationStr), attacker.getObjectID(), weapon.getObjectID(), target.getObjectID(), command.getCommandCRC());
		ObjControllerMessage objController = new ObjControllerMessage(0x1B, combatAction);
		attacker.notifyObserversInRange(objController, true, 125);
		
		StartTask startTask = new StartTask(actionCounter, attacker.getObjectID(), command.getCommandCRC());
		ObjControllerMessage objController2 = new ObjControllerMessage(0x0B, startTask);
		attacker.getClient().getSession().write(objController2.serialize());
		
		CommandEnqueueRemove commandRemove = new CommandEnqueueRemove(attacker.getObjectID(), actionCounter);
		ObjControllerMessage objController3 = new ObjControllerMessage(0x0B, commandRemove);
		attacker.getClient().getSession().write(objController3.serialize());
		
		CombatSpam combatSpam = new CombatSpam(attacker.getObjectID(), target.getObjectID(), weapon.getObjectID(), (int) damage, armorAbsorbed, hitType);
		ObjControllerMessage objController4 = new ObjControllerMessage(0x1B, combatSpam);
		IoBuffer spam = objController4.serialize();
		attacker.getClient().getSession().write(spam);
		
		if(target.getClient() != null)
			target.getClient().getSession().write(spam);


	}

	private float getArmorReduction(CreatureObject attacker, CreatureObject target, WeaponObject weapon, CombatCommand command, byte hitType) {
		
		int elementalType = 1;
		
		if(command.getPercentFromWeapon() > 0) {
			
			// TODO: elemental mitigation and damage
			
			if(weapon.getStringAttribute("cat_wpn_damage.wpn_damage_type").equals("@obj_attr_n:armor_eff_kinetic"))
				elementalType = ElementalType.KINETIC;
			else if(weapon.getStringAttribute("cat_wpn_damage.wpn_damage_type").equals("@obj_attr_n:armor_eff_energy"))
				elementalType = ElementalType.ENERGY;

		} else {
			
			elementalType = command.getElementalType();
			
		}
		
		int baseArmor = 0;
		
		switch(elementalType) {
		
			case ElementalType.KINETIC:
				baseArmor = target.getSkillMod("kinetic").getBase();
			case ElementalType.ENERGY:
				baseArmor = target.getSkillMod("energy").getBase();
			case ElementalType.HEAT:
				baseArmor = target.getSkillMod("heat").getBase();
			case ElementalType.COLD:
				baseArmor = target.getSkillMod("cold").getBase();
			case ElementalType.ACID:
				baseArmor = target.getSkillMod("acid").getBase();
			case ElementalType.ELECTRICITY:
				baseArmor = target.getSkillMod("electricity").getBase();

		}
			
		float mitigation = (float) (90 * (1 - Math.exp(-0.000125 * baseArmor)) + baseArmor / 9000);
		
		if(hitType == HitType.STRIKETHROUGH) {
			
			float stMaxValue = attacker.getSkillMod("combat_strikethrough_value").getBase() / 2 + attacker.getSkillMod("luck_modified").getBase() / 10;
			if(stMaxValue > 99)
				stMaxValue = 99;
			float stMinValue = stMaxValue / 2;

			float stValue = new Random().nextInt((int) (stMaxValue - stMinValue + 1)) + stMinValue;
			stValue /= 100;
			stValue = 1 - stValue;
			mitigation *= stValue;
		}
		
		return mitigation / 100;
		
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
		
		if(attacker.getSkillMod("expertise_action_all") != null)
			actionCost *= attacker.getSkillMod("expertise_action_all").getBase();
		
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
	
	private float calculateDamage(CreatureObject attacker, CreatureObject target, WeaponObject weapon, CombatCommand command) {
		
		float rawDamage = command.getAddedDamage();
		
		if(command.getPercentFromWeapon() > 0 && weapon != attacker.getSlottedObject("default_weapon")) {
			
			float weaponMinDmg = weapon.getIntAttribute("cat_wpn_damage.wpn_damage_min");
			float weaponMaxDmg = weapon.getIntAttribute("cat_wpn_damage.wpn_damage_max");
			
			float weaponDmg = new Random().nextInt((int) (weaponMaxDmg - weaponMinDmg + 1)) + weaponMinDmg;
			weaponDmg *= command.getPercentFromWeapon();
			rawDamage += weaponDmg;
			
			if(weapon.isMelee() && attacker.getSkillMod("strength_modified") != null) {
				
				if(attacker.getSkillMod("strength_modified").getBase() > 0) {
					rawDamage += ((attacker.getSkillMod("strength_modified").getBase() / 100) * 33);
				}
				
			}
			
		} else if(command.getPercentFromWeapon() > 0) {
			
			float weaponMinDmg = 50;
			float weaponMaxDmg = 100;
			
			float weaponDmg = new Random().nextInt((int) (weaponMaxDmg - weaponMinDmg + 1)) + weaponMinDmg;
			weaponDmg *= command.getPercentFromWeapon();
			rawDamage += weaponDmg;
			
			if(weapon.isMelee() && attacker.getSkillMod("strength_modified") != null) {
				
				if(attacker.getSkillMod("strength_modified").getBase() > 0) {
					rawDamage += ((attacker.getSkillMod("strength_modified").getBase() / 100) * 33);
				}
				
			}
			
		}
		
		if(target.getSkillMod("damage_decrease_percentage") != null) {
			rawDamage *= (target.getSkillMod("damage_decrease_percentage").getBase() / 100);
		}
		
		return rawDamage;
		
	}

	
	private float calculateDamage(CreatureObject attacker, TangibleObject target, WeaponObject weapon, CombatCommand command) {
		
		float rawDamage = command.getAddedDamage();
		
		if(command.getPercentFromWeapon() > 0 && weapon != attacker.getSlottedObject("default_weapon")) {
			
			float weaponMinDmg = weapon.getIntAttribute("cat_wpn_damage.wpn_damage_min");
			float weaponMaxDmg = weapon.getIntAttribute("cat_wpn_damage.wpn_damage_max");
			
			float weaponDmg = new Random().nextInt((int) (weaponMaxDmg - weaponMinDmg + 1)) + weaponMinDmg;
			weaponDmg *= command.getPercentFromWeapon();
			rawDamage += weaponDmg;
			
			if(weapon.isMelee() && attacker.getSkillMod("strength_modified") != null) {
				
				if(attacker.getSkillMod("strength_modified").getBase() > 0) {
					rawDamage += ((attacker.getSkillMod("strength_modified").getBase() / 100) * 33);
				}
				
			}
			
		} else if(command.getPercentFromWeapon() > 0) {
			
			float weaponMinDmg = 50;
			float weaponMaxDmg = 100;
			
			float weaponDmg = new Random().nextInt((int) (weaponMaxDmg - weaponMinDmg + 1)) + weaponMinDmg;
			weaponDmg *= command.getPercentFromWeapon();
			rawDamage += weaponDmg;
			
			if(weapon.isMelee() && attacker.getSkillMod("strength_modified") != null) {
				
				if(attacker.getSkillMod("strength_modified").getBase() > 0) {
					rawDamage += ((attacker.getSkillMod("strength_modified").getBase() / 100) * 33);
				}
				
			}
			
		}
		
		return rawDamage;
		
	}
	
	public byte getHitType(CreatureObject attacker, CreatureObject target, WeaponObject weapon, CombatCommand command) {
		
		Random rand = new Random();
		float r;

		// negation rolls(parry, miss and dodge) can only roll on single target attacks, strikethrough also only rolls on single target attacks
		if(command.getAttackType() == 1) {
		
			if(weapon.isRanged()) {
				float missChance = 0.5f;
				if(attacker.getSkillMod("strength_modified").getBase() > 0) {
					float missNegation = (float) ((attacker.getSkillMod("strength_modified").getBase() / 100) * 0.1);
					if(missNegation > 0.4f)
						missNegation = 0.4f;
					missChance -= missNegation;
				}
				r = rand.nextFloat();
				if(r <= missChance)
					return HitType.MISS;
			}
			float dodgeChance = target.getSkillMod("display_only_dodge").getBase() / 10000;
	
			r = rand.nextFloat();
			if(r <= dodgeChance)
				return HitType.DODGE;
			
				
			WeaponObject weapon2 = (WeaponObject) core.objectService.getObject(((CreatureObject) target).getWeaponId());
			if(weapon2 != null && weapon2.isMelee()) {
				
				float parryChance = target.getSkillMod("display_only_parry").getBase() / 10000;
	
				r = rand.nextFloat();
				if(r <= parryChance)
					return HitType.PARRY;
					
			}
			
			float stChance = attacker.getSkillMod("display_only_strikethrough").getBase() / 10000;
			
			r = rand.nextFloat();
			if(r <= stChance)
				return HitType.STRIKETHROUGH;

		}

		float critChance = attacker.getSkillMod("display_only_critical").getBase() / 10000;
		
		r = rand.nextFloat();
		if(r <= critChance)
			return HitType.CRITICAL;
		
		// TODO: Punishing blow once AI is implemented
		
		return HitType.HIT;
		
	}
	
	public byte doMitigationRolls(CreatureObject attacker, CreatureObject target, WeaponObject weapon, CombatCommand command, byte hitType) {
		
		Random rand = new Random();
		float r;
					
		float blockChance = target.getSkillMod("display_only_block").getBase() / 10000;
			
		r = rand.nextFloat();
		if(r <= blockChance)
			return HitType.BLOCK;
			
		if(command.getAttackType() == 0 || command.getAttackType() == 2 || command.getAttackType() == 3) {
				
			float evasionChance = target.getSkillMod("display_only_evasion").getBase() / 10000;
				
			r = rand.nextFloat();
			if(r <= evasionChance)
				return HitType.EVASION;

		}
		
		if(hitType == HitType.HIT) {
			
			float glanceChance = target.getSkillMod("display_only_glancing_blow").getBase() / 10000;
			
			r = rand.nextFloat();
			if(r <= glanceChance)
				return HitType.GLANCE;

		}
			

		return -1;
		
	}
	
	public void applyDamage(CreatureObject attacker, CreatureObject target, int damage) {
		
		if(target.getHealth() - damage <= 0) {
			target.setHealth(1);
			target.setPosture((byte) 13);
			if(target.getSlottedObject("ghost") != null)
				attacker.sendSystemMessage("You incapacitate " + target.getCustomName() + ".", (byte) 0);
			return;
		}
		target.setHealth(target.getHealth() - damage);
		
	}
	
	private boolean isInConeAngle(CreatureObject attacker, SWGObject target, int coneLength, int coneWidth, float directionX, float directionZ) {
		
		float radius = coneWidth / 2;
		float angle = (float) (2 * Math.atan(coneLength / radius));
		
		float targetX = target.getWorldPosition().x - attacker.getWorldPosition().x;
		float targetZ = target.getWorldPosition().z - attacker.getWorldPosition().z;
		
		float targetAngle = (float) (Math.atan2(targetZ, targetX) -  Math.atan2(directionZ, directionX));
		
		float degrees = (float) (targetAngle * 180 / Math.PI);
		float coneAngle = angle / 2;
		
		if(degrees > coneAngle || degrees < -coneAngle)
			return false;
		
		return true;
				
	}
	
	public boolean attemptHeal(CreatureObject healer, CreatureObject target) {
		
		if(healer == target)
			return true;
		
		if(healer.getFaction().equals(target.getFaction())) {
			
			if(healer.getFactionStatus() < target.getFactionStatus())
				return false;
			
			return true;
			
		} else {
			
			if(target.getFactionStatus() == 0)
				return true;
			
			return false;
			
		}
		
	}
	
	public void doHeal(CreatureObject healer, CreatureObject target, WeaponObject weapon, CombatCommand command, int actionCounter) {
		
		if(!applySpecialCost(healer, weapon, command))
			return;
		
		if((command.getAttackType() == 0 || command.getAttackType() == 1 || command.getAttackType() == 3) && !attemptHeal(healer, target))	
			return;
		
		if(command.getAttackType() == 1)
			doSingleTargetHeal(healer, target, weapon, command, actionCounter);
		else if(command.getAttackType() == 0 || command.getAttackType() == 2 || command.getAttackType() == 3)
			doAreaHeal(healer, target, weapon, command, actionCounter);
		
	}
	
	private void doSingleTargetHeal(CreatureObject healer, CreatureObject target, WeaponObject weapon, CombatCommand command, int actionCounter) {
		
		int healAmount = command.getAddedDamage();
		
		if(healer.getSkillMod("expertise_healing_all") != null) {
			int healPotency = healer.getSkillMod("expertise_healing_all").getBase();
			if(healer.getSkillMod("expertise_healing_line_me_heal") != null)
				healPotency += healer.getSkillMod("expertise_healing_line_me_heal").getBase();
			if(healer.getSkillMod("expertise_healing_line_of_heal") != null)
				healPotency += healer.getSkillMod("expertise_healing_line_of_heal").getBase();
			if(healer.getSkillMod("expertise_healing_line_sm_heal") != null)
				healPotency += healer.getSkillMod("expertise_healing_line_sm_heal").getBase();
			if(healer.getSkillMod("expertise_healing_line_sp_heal") != null)
				healPotency += healer.getSkillMod("expertise_healing_line_sp_heal").getBase();
			healAmount += (healAmount * (healPotency / 100));
		}
		
		target.setHealth(target.getHealth() + healAmount);
		
		if(FileUtilities.doesFileExist("scripts/commands/combat" + command.getCommandName() + ".py"))
			core.scriptService.callScript("scripts/commands/combat", command.getCommandName(), "run", core, healer, target, null);
		
	}
	
	private void doAreaHeal(CreatureObject healer, CreatureObject target, WeaponObject weapon, CombatCommand command, int actionCounter) {
		
		float range = command.getConeLength();
		
		List<SWGObject> inRangeObjects = core.simulationService.get(healer.getPlanet(), target.getWorldPosition().x, target.getWorldPosition().z, (int) range);
		
		for(SWGObject obj : inRangeObjects) {
			
			if(!(obj instanceof CreatureObject))
				continue;
			
			if(obj instanceof CreatureObject && (((CreatureObject) obj).getPosture() == 14))
				continue;
			
			if(!core.simulationService.checkLineOfSight(target, obj))
				continue;
			
			if(!attemptHeal(healer, (CreatureObject) obj))
				continue;
			
			doSingleTargetHeal(healer, (CreatureObject) obj, weapon, command, actionCounter);
			
		}

		
	}

	

	public enum HitType{; 
	
		public static final byte MISS = 0;
		public static final byte DODGE = 1;
		public static final byte PARRY = 2;
		public static final byte STRIKETHROUGH = 3;
		public static final byte CRITICAL = 4;
		public static final byte PUNISHING = 5;
		public static final byte HIT = 6;
		public static final byte BLOCK = 7;
		public static final byte EVASION = 8;
		public static final byte GLANCE = 9;
	
	}
	
	public enum ElementalType {;
	
		public static final int KINETIC = 1;
		public static final int ENERGY = 2;
		public static final int BLAST = 4;
		public static final int STUN = 8;
		public static final int HEAT = 32;
		public static final int COLD = 64;
		public static final int ACID = 128;
		public static final int ELECTRICITY = 256;

	}

}
