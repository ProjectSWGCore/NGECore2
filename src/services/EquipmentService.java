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
package services;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.TreeMap;

import org.python.antlr.ast.Str;
import org.python.core.Py;
import org.python.core.PyObject;

import resources.datatables.FactionStatus;
import resources.objects.creature.CreatureObject;
import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.objects.player.PlayerObject;
import services.equipment.BonusSetTemplate;
import services.spawn.MobileTemplate;

public class EquipmentService implements INetworkDispatch {
	
	private NGECore core;
	private Map<String, BonusSetTemplate> bonusSetTemplates = new ConcurrentHashMap<String, BonusSetTemplate>();
	
	public EquipmentService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {

	}
	
	private int getWeaponCriticalChance(CreatureObject actor, SWGObject item) {
		int weaponCriticalChance = 0;
		String weaponCriticalSkillMod = (core.scriptService.getMethod("scripts/equipment/", "weapon_critical", item.getStringAttribute("cat_wpn_damage.wpn_category")).__call__().asString());
		
		if(weaponCriticalSkillMod.contains(" "))
			weaponCriticalSkillMod.replace(" ", "");
		
		if(weaponCriticalSkillMod.contains("-"))
			weaponCriticalSkillMod.replace("-", "");		
		
		if(actor.getSkillMod(weaponCriticalSkillMod) != null)
			weaponCriticalChance = actor.getSkillModBase(weaponCriticalSkillMod);
		
		return weaponCriticalChance;
	}
	
	private void addWeaponCriticalChance(CreatureObject actor, SWGObject item) {
		actor.addSkillMod("display_only_critical", getWeaponCriticalChance(actor, item));
	}
	
	private void deductWeaponCriticalChance(CreatureObject actor, SWGObject item) {
		actor.deductSkillMod("display_only_critical", getWeaponCriticalChance(actor, item));
	}	
	
	public String getFormalProfessionName(String stfName) {
		String formalName = "";

		switch (stfName) {
		case "force_sensitive_1a":	formalName = "Jedi"; break;
		case "bounty_hunter_1a":	formalName = "Bounty Hunter"; break;
		case "officer_1a":			formalName = "Officer"; break;
		case "smuggler_1a":			formalName = "Smuggler"; break;
		case "entertainer_1a":		formalName = "Entertainer"; break;
		case "spy_1a":				formalName = "Spy"; break;
		case "medic_1a":			formalName = "Medic"; break;
		case "commando_1a":			formalName = "Commando"; break;
		
		default:					formalName = "Trader"; break;	// Ziggy: Trader profession names are a bit irregular, so this is used.

		}
		return formalName;
	}
	
	public boolean canEquip(CreatureObject actor, SWGObject item) {
		boolean result = true;
		
		if (item == null)
			return false;
		
		if (item.getAttributes().toString().contains("cat_armor"))
			if (actor.getLevel() >= 22)
				result = true;
			else
				return false;

		if (item.getStringAttribute("class_required") != null) {
			String profession = ((PlayerObject) actor.getSlottedObject("ghost")).getProfession();
			if (item.getStringAttribute("class_required").contentEquals(getFormalProfessionName(profession)) || item.getStringAttribute("class_required").contentEquals("None"))
				result = true;
			else
				return false;
		}
		
		if (item.getStringAttribute("faction_restriction") != null) 
			if (item.getStringAttribute("faction_restriction").toLowerCase().contentEquals(actor.getFaction()) && actor.getFactionStatus() >= FactionStatus.Combatant)
				result = true;
			else
				return false;			
		
		if (item.getAttributes().containsKey("required_combat_level"))
			if (actor.getLevel() >= item.getIntAttribute("required_combat_level"))
				result = true;
			else
				return false;
		
		return result;
	}
	
	private float calculateArmorProtection (float protection, String slotName) {
		return protection *= (Float.parseFloat(core.scriptService.getMethod("scripts/equipment/", "slot_protection", slotName).__call__().asString()) / 100);
	}
	
	private void addArmorProtection(CreatureObject actor, String protectionType, float protection, String slotName) {
		actor.addSkillMod(protectionType, (int) (calculateArmorProtection(protection, slotName)));
	}
	
	private void deductArmorProtection(CreatureObject actor, String protectionType, float protection, String slotName) {
		actor.deductSkillMod(protectionType, (int) calculateArmorProtection(protection, slotName)); 
	}
	
	private void addForceProtection(CreatureObject actor, SWGObject item) {
		actor.addSkillMod("kinetic", getForceProtection(item));
		actor.addSkillMod("energy", getForceProtection(item));
		actor.addSkillMod("heat", getForceProtection(item));
		actor.addSkillMod("cold", getForceProtection(item));
		actor.addSkillMod("acid", getForceProtection(item));
		actor.addSkillMod("electricity", getForceProtection(item));
	}	
	
	private void deductForceProtection(CreatureObject actor, SWGObject item) {
		actor.deductSkillMod("kinetic", getForceProtection(item));
		actor.deductSkillMod("energy", getForceProtection(item));
		actor.deductSkillMod("heat", getForceProtection(item));
		actor.deductSkillMod("cold", getForceProtection(item));
		actor.deductSkillMod("acid", getForceProtection(item));
		actor.deductSkillMod("electricity", getForceProtection(item));
	}	
	
	private int getForceProtection(SWGObject item) {
		return core.scriptService.getMethod("scripts/equipment/", "force_protection", item.getAttachment("type") + "_" + item.getStringAttribute("protection_level")).__call__().asInt();
	}
	
	public void equip(CreatureObject actor, SWGObject item) {
		String template = ((item.getAttachment("customServerTemplate") == null) ? item.getTemplate() : (item.getTemplate().split("shared_")[0] + "shared_" + ((String) item.getAttachment("customServerTemplate")) + ".iff"));
		String serverTemplate = template.replace(".iff", "");
		PyObject func = core.scriptService.getMethod("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", ""), serverTemplate.split("shared_" , 2)[1], "equip");
		if(func != null)
			func.__call__(Py.java2py(core), Py.java2py(actor), Py.java2py(item));

		if (item.getStringAttribute("protection_level") != null)
			addForceProtection(actor, item);
		
		// TODO: faction restrictions - You had to be a Combatant as minimum in order to EQUIP an item.
		// TODO: Species restrictions
		// TODO: Gender restrictions
		// TODO: crit enhancement from crafted weapons
		// TODO: check for armor category in order to add resistance to certain DoT types
		// TODO: Calculate actual armor values (REMINDER: Check if the player is wearing a jedi robe/cloak (look for force protection intensity). If they are, they shouldn't receive any additional protection from other items with armour.)
		// TODO: bio-link (assign it by objectID with setAttachment and then just display the customName for that objectID).
		
		if(item.getStringAttribute("cat_wpn_damage.wpn_category") != null)
			addWeaponCriticalChance(actor, item);
				
		Map<String, Object> attributes = new TreeMap<String, Object>(item.getAttributes());
		
		for(Entry<String, Object> e : attributes.entrySet()) {
			if(e.getKey().startsWith("cat_skill_mod_bonus.@stat_n:")) {
				core.skillModService.addSkillMod(actor, e.getKey().replace("cat_skill_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
			}
			
			if(e.getKey().startsWith("cat_stat_mod_bonus.@stat_n:")) {
				core.skillModService.addSkillMod(actor, e.getKey().replace("cat_stat_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
			}			
			
			if(e.getKey().startsWith("cat_armor_standard_protection")) {
				addArmorProtection(actor, e.getKey().replace("cat_armor_standard_protection.armor_eff_", ""), Float.parseFloat((String) e.getValue()), actor.getSlotNameForObject(item));
			}	
			
			if(e.getKey().startsWith("cat_armor_special_protection")) {
				addArmorProtection(actor, e.getKey().replace("cat_armor_special_protection.special_protection_type_", ""), Float.parseFloat((String) e.getValue()), actor.getSlotNameForObject(item));
			}		
						
			if(e.getKey().startsWith("cat_attrib_mod_bonus.attr_health")) {
				actor.setMaxHealth(actor.getMaxHealth() + Integer.parseInt((String) e.getValue()));
			}
			
			if(e.getKey().startsWith("cat_attrib_mod_bonus.attr_action")) {
				actor.setMaxAction(actor.getMaxAction() + Integer.parseInt((String) e.getValue()));
			}
			
		}
		
		if(item.getStringAttribute("proc_name") != null) core.buffService.addBuffToCreature(actor, item.getStringAttribute("proc_name").replace("@ui_buff:", ""), actor);
		

		if(!actor.getEquipmentList().contains(item))
			actor.addObjectToEquipList(item);
		
		if(item.getAttachment("setBonus") != null)
		{
			BonusSetTemplate bonus = bonusSetTemplates.get((String)item.getAttachment("setBonus"));
			bonus.callScript(actor);
		}
}

	public void unequip(CreatureObject actor, SWGObject item) {
		String template = ((item.getAttachment("customServerTemplate") == null) ? item.getTemplate() : (item.getTemplate().split("shared_")[0] + "shared_" + ((String) item.getAttachment("customServerTemplate")) + ".iff"));
		String serverTemplate = template.replace(".iff", "");
		PyObject func = core.scriptService.getMethod("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", ""), serverTemplate.split("shared_" , 2)[1], "unequip");
		if(func != null)
			func.__call__(Py.java2py(core), Py.java2py(actor), Py.java2py(item));

		if (item.getStringAttribute("protection_level") != null)
			deductForceProtection(actor, item);
		
		if(item.getStringAttribute("cat_wpn_damage.wpn_category") != null)		
			deductWeaponCriticalChance(actor, item);
		
		Map<String, Object> attributes = new TreeMap<String, Object>(item.getAttributes());
		
		for(Entry<String, Object> e : attributes.entrySet()) {
			
			if(e.getKey().startsWith("cat_skill_mod_bonus.@stat_n:")) {
				core.skillModService.deductSkillMod(actor, e.getKey().replace("cat_skill_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
			}			
			if(e.getKey().startsWith("cat_stat_mod_bonus.@stat_n:")) {
				core.skillModService.deductSkillMod(actor, e.getKey().replace("cat_stat_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
			}	
			
			if(e.getKey().startsWith("cat_armor_standard_protection")) {
				deductArmorProtection(actor, e.getKey().replace("cat_armor_standard_protection.armor_eff_", ""), Float.parseFloat((String) e.getValue()), actor.getSlotNameForObject(item));
			}	
			
			if(e.getKey().startsWith("cat_armor_special_protection")) {
				deductArmorProtection(actor, e.getKey().replace("cat_armor_special_protection.special_protection_type_", ""), Float.parseFloat((String) e.getValue()), actor.getSlotNameForObject(item));
			}		
			
			if(e.getKey().startsWith("cat_attrib_mod_bonus.attr_health")) {
				actor.setMaxHealth(actor.getMaxHealth() - Integer.parseInt((String) e.getValue()));
			}		
			if(e.getKey().startsWith("cat_attrib_mod_bonus.attr_action")) {
				actor.setMaxAction(actor.getMaxAction() - Integer.parseInt((String) e.getValue()));
			}
			
		}	
		
		if(item.getAttachment("unity") != null) {
			actor.sendSystemMessage("@unity:cannot_remove_ring", (byte) 0);
			return;
		}
		
		if(item.getStringAttribute("proc_name") != null) core.buffService.removeBuffFromCreatureByName(actor, item.getStringAttribute("proc_name").replace("@ui_buff:", ""));
		
		
		if(actor.getEquipmentList().contains(item))
			actor.removeObjectFromEquipList(item);

		if(item.getAttachment("setBonus") != null)
		{
			BonusSetTemplate bonus = bonusSetTemplates.get((String)item.getAttachment("setBonus"));
			bonus.callScript(actor);
		}
	}

	public void addBonusSetTemplate(BonusSetTemplate bonusSet)
	{
		bonusSetTemplates.put(bonusSet.getName(), bonusSet);
	}
	
	public void loadBonusSets() {
	    Path p = Paths.get("scripts/equipment/bonus_sets/");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	core.scriptService.callScript("scripts/equipment/bonus_sets/", file.getFileName().toString().replace(".py", ""), "addBonusSet", core);
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
