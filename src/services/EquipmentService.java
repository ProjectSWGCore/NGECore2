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

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.python.core.Py;
import org.python.core.PyObject;

import resources.objects.creature.CreatureObject;
import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class EquipmentService implements INetworkDispatch {
	
	private NGECore core;

	public EquipmentService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		
	}
	
	public void weaponCriticalToDisplay(CreatureObject actor, SWGObject item, boolean add) {
		
		if (add == true) {

			switch(item.getStringAttribute("cat_wpn_damage.wpn_category")) {

				case "Rifle": actor.addSkillMod("display_only_critical", actor.getSkillModBase("expertise_critical_rifle") * 100); // rifle
				case "Carbine": actor.addSkillMod("display_only_critical", actor.getSkillModBase("expertise_critical_carbine") * 100); // carbine
				case "Pistol": actor.addSkillMod("display_only_critical", actor.getSkillModBase("expertise_critical_pistol") * 100); // pistol
				case "One-Handed Melee": actor.addSkillMod("display_only_critical", ((actor.getSkillModBase("expertise_critical_1h") + actor.getSkillModBase("expertise_critical_melee")) * 100)); // one-handed
				case "Two-Handed Melee": actor.addSkillMod("display_only_critical", ((actor.getSkillModBase("expertise_critical_2h") + actor.getSkillModBase("expertise_critical_melee")) * 100)); // two-handed
				case "Unarmed": actor.addSkillMod("display_only_critical", ((actor.getSkillModBase("expertise_critical_unarmed") + actor.getSkillModBase("expertise_critical_melee")) * 100)); // unarmed
				case "Polearm": actor.addSkillMod("display_only_critical", ((actor.getSkillModBase("expertise_critical_polearm") + actor.getSkillModBase("expertise_critical_melee")) * 100)); // polearm
				case "Free Targeting Heavy Weapon": actor.addSkillMod("display_only_critical", actor.getSkillModBase("expertise_critical_heavy") * 100); // Heavy


			}
			
		}
		
		if (add == false) {

			switch(item.getStringAttribute("cat_wpn_damage.wpn_category")) {

				case "Rifle": actor.deductSkillMod("display_only_critical", actor.getSkillModBase("expertise_critical_rifle") * 100); // rifle
				case "Carbine": actor.deductSkillMod("display_only_critical", actor.getSkillModBase("expertise_critical_carbine") * 100); // carbine
				case "Pistol": actor.deductSkillMod("display_only_critical", actor.getSkillModBase("expertise_critical_pistol") * 100); // pistol
				case "One-Handed Melee": actor.deductSkillMod("display_only_critical", ((actor.getSkillModBase("expertise_critical_1h") + actor.getSkillModBase("expertise_critical_melee")) * 100)); // one-handed
				case "Two-Handed Melee": actor.deductSkillMod("display_only_critical", ((actor.getSkillModBase("expertise_critical_2h") + actor.getSkillModBase("expertise_critical_melee")) * 100)); // two-handed
				case "Unarmed": actor.deductSkillMod("display_only_critical", ((actor.getSkillModBase("expertise_critical_unarmed") + actor.getSkillModBase("expertise_critical_melee")) * 100)); // unarmed
				case "Polearm": actor.deductSkillMod("display_only_critical", ((actor.getSkillModBase("expertise_critical_polearm") + actor.getSkillModBase("expertise_critical_melee")) * 100)); // polearm
				case "Free Targeting Heavy Weapon": actor.deductSkillMod("display_only_critical", actor.getSkillModBase("expertise_critical_heavy") * 100); // Heavy
			}
		}
	}
	
	public void equip(CreatureObject actor, SWGObject item) {

		//if(replacedItem != null)
		//	unequip(actor, replacedItem);
		
		String template = ((item.getAttachment("customServerTemplate") == null) ? item.getTemplate() : (item.getTemplate().split("shared_")[0] + "shared_" + ((String) item.getAttachment("customServerTemplate")) + ".iff"));
		String serverTemplate = template.replace(".iff", "");
		PyObject func = core.scriptService.getMethod("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", ""), serverTemplate.split("shared_" , 2)[1], "equip");
		if(func != null)
			func.__call__(Py.java2py(core), Py.java2py(actor), Py.java2py(item));
		
		// TODO: add health/action bonus from crafted weapon with augmentations (also seen with cybernetics)
		// TODO: faction restrictions
		// TODO: Species restrictions?
		// TODO: Gender restrictions?
		// TODO: crit enhancement from crafted weapons
		// TODO: Jedi robes Force Protection Intensity
		// TODO: check for armor category in order to add resistance to certain DoT types
		// TODO: if weapon, add the weapon specific crit to display_only_critical
		// TODO: bio-link
		
		if (actor.getSlotNameForObject(item).contentEquals("hold_r") == true)
			weaponCriticalToDisplay(actor, item, true);
		
		Map<String, Object> attributes = new TreeMap<String, Object>(item.getAttributes());
		
		for(Entry<String, Object> e : attributes.entrySet()) {
			
			if(e.getKey().startsWith("cat_skill_mod_bonus.@stat_n:")) {
				core.skillModService.addSkillMod(actor, e.getKey().replace("cat_skill_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
			}	
			
		}
		
		if(!actor.getEquipmentList().contains(item))
			actor.addObjectToEquipList(item);
		
	}
	
	public void unequip(CreatureObject actor, SWGObject item) {
		
		String template = ((item.getAttachment("customServerTemplate") == null) ? item.getTemplate() : (item.getTemplate().split("shared_")[0] + "shared_" + ((String) item.getAttachment("customServerTemplate")) + ".iff"));
		String serverTemplate = template.replace(".iff", "");
		PyObject func = core.scriptService.getMethod("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", ""), serverTemplate.split("shared_" , 2)[1], "unequip");
		if(func != null)
			func.__call__(Py.java2py(core), Py.java2py(actor), Py.java2py(item));

		if (actor.getSlotNameForObject(item).contentEquals("hold_r") == true)
			weaponCriticalToDisplay(actor, item, false);
		
		Map<String, Object> attributes = new TreeMap<String, Object>(item.getAttributes());
		
		for(Entry<String, Object> e : attributes.entrySet()) {
			
			if(e.getKey().startsWith("cat_skill_mod_bonus.@stat_n:")) {
				core.skillModService.deductSkillMod(actor, e.getKey().replace("cat_skill_mod_bonus.@stat_n:", ""), Integer.parseInt((String) e.getValue()));
			}				
			
		}
		
		if(actor.getEquipmentList().contains(item))
			actor.removeObjectFromEquipList(item);

	}


}
