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
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.TreeMap;

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
	
	public boolean canEquip(CreatureObject actor, SWGObject item) {
		// TODO: Species restrictions
		// TODO: Gender restrictions
		
		boolean result = true;
		
		if (item == null)
			return false;
		
		if (item.getAttributes().toString().contains("cat_armor"))
		{
			if (actor.hasAbility("wear_all_armor")) result = true; // Change to "wear_all_armor" ability instead of lvl 22		
			else return false;
		}

		if (item.getStringAttribute("class_required") != null) {
			String profession = ((PlayerObject) actor.getSlottedObject("ghost")).getProfession();
			if (item.getStringAttribute("class_required").contentEquals(core.playerService.getFormalProfessionName(profession)) || item.getStringAttribute("class_required").contentEquals("None"))
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
		{
			if (actor.getLevel() >= item.getIntAttribute("required_combat_level"))
				result = true;
			else
				return false;
		}
		
		if(item.getAttachment("unity") != null) 
		{
			actor.sendSystemMessage("@unity:cannot_remove_ring", (byte) 0);
			return false;
		}
		
		return result;
	}
	
	public void equip(CreatureObject actor, SWGObject item) 
	{
		String template = ((item.getAttachment("customServerTemplate") == null) ? item.getTemplate() : (item.getTemplate().split("shared_")[0] + "shared_" + ((String) item.getAttachment("customServerTemplate")) + ".iff"));
		String serverTemplate = template.replace(".iff", "");
		
		PyObject func = core.scriptService.getMethod("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", ""), serverTemplate.split("shared_" , 2)[1], "equip");
		if(func != null) func.__call__(Py.java2py(core), Py.java2py(actor), Py.java2py(item));
		
		// TODO: bio-link (assign it by objectID with setAttachment and then just display the customName for that objectID).
		
		if(!actor.getEquipmentList().contains(item)) 
		{
			actor.addObjectToEquipList(item);
			processItemAtrributes(actor, item, true);
		}
}

	public void unequip(CreatureObject actor, SWGObject item) 
	{
		String template = ((item.getAttachment("customServerTemplate") == null) ? item.getTemplate() : (item.getTemplate().split("shared_")[0] + "shared_" + ((String) item.getAttachment("customServerTemplate")) + ".iff"));
		String serverTemplate = template.replace(".iff", "");
		
		PyObject func = core.scriptService.getMethod("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", ""), serverTemplate.split("shared_" , 2)[1], "unequip");
		if(func != null) func.__call__(Py.java2py(core), Py.java2py(actor), Py.java2py(item));

		
		if(actor.getEquipmentList().contains(item)) 
		{
			actor.removeObjectFromEquipList(item);
			processItemAtrributes(actor, item, false);
		}
	}
	
	public void processItemAtrributes(CreatureObject creature, SWGObject item, boolean equipping)
	{
		// TODO: crit enhancement from crafted weapons
		// TODO: check for armor category in order to add resistance to certain DoT types
		
		Map<String, Object> attributes = new TreeMap<String, Object>(item.getAttributes());	
		
		if(equipping)
		{
			if(item.getStringAttribute("cat_wpn_damage.wpn_category") != null) creature.addSkillMod("display_only_critical", getWeaponCriticalChance(creature, item));
			if(item.getStringAttribute("proc_name") != null) core.buffService.addBuffToCreature(creature, item.getStringAttribute("proc_name").replace("@ui_buff:", ""), creature);
			
			for(Entry<String, Object> e : attributes.entrySet()) 
			{	
				if(e.getKey().startsWith("cat_skill_mod_bonus.@stat_n:")) 
				{
					core.skillModService.addSkillMod(creature, e.getKey().replace("cat_skill_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
				}
				
				if(e.getKey().startsWith("cat_stat_mod_bonus.@stat_n:")) 
				{
					core.skillModService.addSkillMod(creature, e.getKey().replace("cat_stat_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
				}			
							
				if(e.getKey().startsWith("cat_attrib_mod_bonus.attr_health")) 
				{
					creature.setMaxHealth(creature.getMaxHealth() + Integer.parseInt((String) e.getValue()));
				}
				
				if(e.getKey().startsWith("cat_attrib_mod_bonus.attr_action")) 
				{
					creature.setMaxAction(creature.getMaxAction() + Integer.parseInt((String) e.getValue()));
				}
			}
		}
		else
		{
			if(item.getStringAttribute("cat_wpn_damage.wpn_category") != null) creature.deductSkillMod("display_only_critical", getWeaponCriticalChance(creature, item));
			if(item.getStringAttribute("proc_name") != null) core.buffService.removeBuffFromCreatureByName(creature, item.getStringAttribute("proc_name").replace("@ui_buff:", ""));
			
			for(Entry<String, Object> e : attributes.entrySet())
			{
				if(e.getKey().startsWith("cat_skill_mod_bonus.@stat_n:")) 
				{
					core.skillModService.deductSkillMod(creature, e.getKey().replace("cat_skill_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
				}			
				if(e.getKey().startsWith("cat_stat_mod_bonus.@stat_n:")) 
				{
					core.skillModService.deductSkillMod(creature, e.getKey().replace("cat_stat_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
				}	
				
				if(e.getKey().startsWith("cat_attrib_mod_bonus.attr_health")) 
				{
					creature.setMaxHealth(creature.getMaxHealth() - Integer.parseInt((String) e.getValue()));
				}		
				if(e.getKey().startsWith("cat_attrib_mod_bonus.attr_action")) 
				{
					creature.setMaxAction(creature.getMaxAction() - Integer.parseInt((String) e.getValue()));
				}	
			}	
		}
		
		calculateArmorProtection(creature, equipping);
		
		if(item.getAttachment("setBonus") != null)
		{
			BonusSetTemplate bonus = bonusSetTemplates.get((String)item.getAttachment("setBonus"));
			bonus.callScript(creature);
		}
	}

	private void calculateArmorProtection(CreatureObject creature, boolean equipping)
	{
		int wornArmourPieces = 0, forceProtection = 0;
		Map<String, Float> protection = new TreeMap<String, Float>();
		
		for(SWGObject item : new ArrayList<SWGObject>(creature.getEquipmentList()))
		{
			Map<String, Object> attributes = new TreeMap<String, Object>(item.getAttributes());
			boolean incPieceCount = false;
			
			if(item.getStringAttribute("protection_level") != null) 
			{
				forceProtection = getForceProtection(item);
				break;
			}
			
			for(Entry<String, Object> e : attributes.entrySet()) 
			{		
				if(e.getKey().startsWith("cat_armor_standard_protection")) 
				{
					String protectionType = e.getKey().replace("cat_armor_standard_protection.armor_eff_", "");
					float modifier = Float.parseFloat(core.scriptService.getMethod("scripts/equipment/", "slot_protection", creature.getSlotNameForObject(item)).__call__().asString()) / 100;
					Float protectionAmount = Float.parseFloat((String) e.getValue()) * modifier;
				
					if(protection.containsKey(protectionType)) protection.replace(protectionType, protection.get(protectionType) + protectionAmount);
					else protection.put(protectionType, protectionAmount);
					incPieceCount = true;
				}
				else if(e.getKey().startsWith("cat_armor_special_protection")) 
				{
					String protectionType = e.getKey().replace("cat_armor_special_protection.special_protection_type_", "");
					float modifier = Float.parseFloat(core.scriptService.getMethod("scripts/equipment/", "slot_protection", creature.getSlotNameForObject(item)).__call__().asString()) / 100;
					Float protectionAmount = Float.parseFloat((String) e.getValue()) * modifier;
					
					if(protection.containsKey(protectionType)) protection.replace(protectionType, protection.get(protectionType) + protectionAmount);
					else protection.put(protectionType, protectionAmount);
					incPieceCount = true;
				}
			}
			if(incPieceCount) wornArmourPieces++;
		}
		
		if(protection.size() == 0)
		{
			protection.put("kinetic", (float) 0);
			protection.put("energy", (float) 0);
			protection.put("heat", (float) 0);
			protection.put("cold", (float) 0);
			protection.put("acid", (float) 0);
			protection.put("electricity", (float) 0);
		}
		
		for(Entry<String, Float> e : protection.entrySet()) 
		{	
			core.skillModService.deductSkillMod(creature, e.getKey(), creature.getSkillModBase(e.getKey()));
			core.skillModService.addSkillMod(creature, e.getKey(), forceProtection);
			if(wornArmourPieces >= 3) core.skillModService.addSkillMod(creature, e.getKey(), (int) e.getValue().floatValue());
		}
	}
	
	private int getWeaponCriticalChance(CreatureObject actor, SWGObject item) {
		int weaponCriticalChance = 0;
		String weaponCriticalSkillMod = (core.scriptService.getMethod("scripts/equipment/", "weapon_critical", "weap_" + item.getStringAttribute("cat_wpn_damage.wpn_category").replace("@obj_attr_n:wpn_category_", "")).__call__().asString());
	
		if(actor.getSkillMod(weaponCriticalSkillMod) != null)
			weaponCriticalChance = actor.getSkillModBase(weaponCriticalSkillMod);
		
		return weaponCriticalChance;
	}
		
	private int getForceProtection(SWGObject item) {
		return core.scriptService.getMethod("scripts/equipment/", "force_protection", item.getAttachment("type") + "_" + item.getStringAttribute("protection_level")).__call__().asInt();
	}
	
	public void addBonusSetTemplate(BonusSetTemplate bonusSet)
	{
		bonusSetTemplates.put(bonusSet.getName(), bonusSet);
	}
	
	public void loadBonusSets() {
	    Path p = Paths.get("scripts/equipment/bonus_sets/");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() 
	    {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
	        {
	        	core.scriptService.callScript("scripts/equipment/bonus_sets/", file.getFileName().toString().replace(".py", ""), "addBonusSet", core);
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try 
        {
			Files.walkFileTree(p, fv);
		} 
        catch (IOException e) { e.printStackTrace(); }
	}
}
