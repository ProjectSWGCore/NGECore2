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

import java.util.HashMap;
import java.util.Map;

import resources.common.FileUtilities;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.skills.SkillMod;
import main.NGECore;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class SkillModService implements INetworkDispatch {
	
	private NGECore core;
	private DatatableVisitor visitor;
	private Map<String, Integer> skillModMap = new HashMap<String, Integer>();
	
	public SkillModService(NGECore core) {
		this.core = core;
		
		try {
			visitor = ClientFileManager.loadFile("datatables/expertise/skill_mod_listing.iff", DatatableVisitor.class);
			
			for (int i = 0; i < visitor.getRowCount(); i++) {
				if (visitor.getObject(i, 0) != null) {
					skillModMap.put((String) visitor.getObject(i, 0), i);
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @description Adds a skillMod.
	 * @param creature Creature to add to.
	 * @param name Name of the skillMod
	 * @param base Base to add to it.
	 * @param modifier Modifier to add to it.
	 */
	public void addSkillMod(CreatureObject creature, String name, int base, int modifier) {
		int divisor = (skillModMap.get(name) == null) ? 0 : (Integer) visitor.getObject(skillModMap.get(name), 5);
		
		if (FileUtilities.doesFileExist("scripts/skillMods/" + name + ".py")) {
			core.scriptService.callScript("scripts/skillMods/", name, "add", core, creature, new SkillMod(base, modifier), divisor);
		}
		
		synchronized(creature.getMutex()) {
			SkillMod skillMod = creature.getSkillMods().get(name);
			
			if (base > 0 || modifier > 0) {
				if (skillMod == null) {
					creature.getSkillMods().put(name, new SkillMod(base, modifier));
				} else {
					skillMod.addBase(base);
					skillMod.addModifier(modifier);
					creature.getSkillMods().put(name, skillMod);
				}
			}
		}
	}
	
	public void addSkillMod(CreatureObject creature, String name, int base) {
		addSkillMod(creature, name, base, 0);
	}
	
	public void addSkillMod(CreatureObject creature, String name, SkillMod skillMod) {
		addSkillMod(creature, name, skillMod.getBase(), skillMod.getModifier());
	}
	
	public SkillMod getSkillMod(CreatureObject actor, String name) {
		return actor.getSkillMods().get(name);
	}
	
	public int getSkillModBase(CreatureObject creature, String name) {
		return creature.getSkillModBase(name);
	}
	
	public int getSkillModModifier(CreatureObject creature, String name) {
		return creature.getSkillModModifier(name);
	}
	
	public float getSkillModValue(CreatureObject creature, String name) {
		int divisor = (skillModMap.get(name) == null) ? 0 : (Integer) visitor.getObject(skillModMap.get(name), 5);
		boolean percent = (skillModMap.get(name) == null) ? false : (Boolean) visitor.getObject(skillModMap.get(name), 6);
		String profession = (skillModMap.get(name) == null || creature.getSlottedObject("ghost") == null) ? null : (String) visitor.getObject(skillModMap.get(name), 1);
		return ((profession == null || !profession.equals(((PlayerObject) creature.getSlottedObject("ghost")).getProfession())) ? 0.0f : creature.getSkillModValue(name, divisor, percent));
	}
	
	public void deductSkillMod(CreatureObject creature, String name, SkillMod skillMod) {
		deductSkillMod(creature, name, skillMod.getBase(), skillMod.getModifier());
	}
	
	public void deductSkillMod(CreatureObject creature, String name, int base) {
		deductSkillMod(creature, name, base, 0);
	}	
	
	public void deductSkillMod(CreatureObject creature, String name, int base, int modifier) {
		int divisor = (skillModMap.get(name) == null) ? 0 : (Integer) visitor.getObject(skillModMap.get(name), 5);
		
		if (FileUtilities.doesFileExist("scripts/skillMods/" + name + ".py")) {
			core.scriptService.callScript("scripts/skillMods/", name, "deduct", core, creature, new SkillMod(base, modifier), divisor);
		}
		
		synchronized(creature.getMutex()) {
			SkillMod skillMod = creature.getSkillMods().get(name);
			
			if (skillMod == null || (base <= 0 && modifier <= 0)) {
				return;
			} else {
				skillMod.deductBase(base);
				skillMod.deductModifier(modifier);
				
				if (skillMod.getBase() > 0 || skillMod.getModifier() > 0) {
					creature.getSkillMods().put(name, skillMod);
				} else {
					removeSkillMod(creature, name);
				}
			}
		}
	}
	
	public void removeSkillMod(CreatureObject creature, String name) {
		creature.getSkillMods().remove(name);
	}
	
	public DatatableVisitor getVisitor() {
		return visitor;
	}
	
	public Map<String, Integer> getSkillModMap() {
		return skillModMap;
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}
	
	@Override
	public void shutdown() {
		
	}
	
}
