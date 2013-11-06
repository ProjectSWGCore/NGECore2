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

import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;

import main.NGECore;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class SkillService implements INetworkDispatch {
	
	private NGECore core;

	public SkillService(NGECore core) {
		this.core = core;
	}
	
	public void addSkill(CreatureObject creature, String skill) {
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		DatatableVisitor skillTable;
		
		if (player == null) {
			return;
		}
		
		if (creature.hasSkill(skill)) {
			return;
		}
		
		try {
			skillTable = ClientFileManager.loadFile("datatables/skill/skills.iff", DatatableVisitor.class);
			
			for (int s = 0; s < skillTable.getRowCount(); s++) {
				if (skillTable.getObject(s, 0) != null) {
					if (((String) skillTable.getObject(s, 0)).equals(skill)) {
						//String parent = ((String) skillTable.getObject(s, 1));
						//int graphType = ((Integer) skillTable.getObject(s, 2));
						boolean godOnly = ((Boolean) skillTable.getObject(s, 3));
						//boolean isTitle = ((Boolean) skillTable.getObject(s, 4));
						boolean isProfession = ((Boolean) skillTable.getObject(s, 5));
						boolean isHidden = ((Boolean) skillTable.getObject(s, 6));
						int pointsRequired = ((Integer) skillTable.getObject(s, 8));
						int skillsRequiredCount = ((Integer) skillTable.getObject(s, 9));
						String[] skillsRequired = ((String) skillTable.getObject(s, 10)).split(",");
						String[] preclusionSkills = ((String) skillTable.getObject(s, 11)).split(",");
						String xpType = ((String) skillTable.getObject(s, 12));
						int xpCost = ((Integer) skillTable.getObject(s, 13));
						//int xpCap = ((Integer) skillTable.getObject(s, 14));
						String[] statsRequired = ((String) skillTable.getObject(s, 17)).split(",");
						String speciesRequired = (String) skillTable.getObject(s, 18);
						String[] abilities = ((String) skillTable.getObject(s, 21)).split(",");
						String[] skillMods = ((String) skillTable.getObject(s, 22)).split(",");
						String[] schematicsGranted = ((String) skillTable.getObject(s, 23)).split(",");
						String[] schematicsRevoked = ((String) skillTable.getObject(s, 24)).split(",");
						
						if (isProfession) {
							return;
						}
						
						if (godOnly || isHidden) {
							return;
						}
						
						if (pointsRequired > 0) {
							//if (creature.getExpertisePoints() > pointsRequired) {
								//creature.deductExpertisePoints(pointsRequired);
							//} else {
								//return;
							//}
						}
						
						if (creature.getSkills().size() < skillsRequiredCount) {
							return;
						}
						
						for (String skillName : skillsRequired) {
							if (skillName != "" && !creature.hasSkill(skillName)) {
								System.out.println("Skill Name: " + skillName);
								return;
							}
						}
						
						for (String skillName : preclusionSkills) {
							if (creature.hasSkill(skillName)) {
								return;
							}
						}
						
						for (String stat : statsRequired) {
							if ((stat != "") && (creature.getSkillMod(stat) == null  || creature.getSkillMod(stat).getBase() < 1)) {
								return;
							}
						}
						
						if (!creature.getStfName().contains(speciesRequired)) {
							return;
						}
						
						if (xpType != null || xpType != "") {
							if (xpCost > 0) {
								if (player.getXp(xpType) >= xpCost) {
									//player.setXp(xpType, (player.getXp(xpType) - xpCost));
								} else {
									//return;
								}
							}
						}
						
						for (String ability : abilities) {
							creature.addAbility(ability);
						}
						
						for (String skillMod : skillMods) {
							if (skillMod != "" && skillMod != null && skillMod.contains("=")) {
								core.skillModService.addSkillMod(creature, skillMod.split("=")[0], new Integer(skillMod.split("=")[1]));
							}
						}
						
						for (String schematic : schematicsGranted) {
							//player.getDraftSchematicList().add(new DraftSchematic());
						}
						
						for (String schematic : schematicsRevoked) {
							//player.getDraftSchematicList().remove(new DraftSchematic());
						}
						
						creature.addSkill(skill);
					}
				}
			}
		}  catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void removeSkill(CreatureObject creature, String skill) {
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		DatatableVisitor skillTable;
		
		if (creature.getClient() == null) {
			return;
		}
		
		if (!creature.hasSkill(skill)) {
			return;
		}
		
		try {
			skillTable = ClientFileManager.loadFile("datatables/skills/skills.iff", DatatableVisitor.class);
			
			for (int s = 0; s < skillTable.getRowCount(); s++) {
				if (skillTable.getObject(s, 0) != null) {
					if (((String) skillTable.getObject(s, 0)).equals(skill)) {
						String parent = ((String) skillTable.getObject(s, 1));
						//int graphType = ((Integer) skillTable.getObject(s, 2));
						boolean godOnly = ((Boolean) skillTable.getObject(s, 3));
						//boolean isTitle = ((Boolean) skillTable.getObject(s, 4));
						boolean isHidden = ((Boolean) skillTable.getObject(s, 6));
						int pointsRequired = ((Integer) skillTable.getObject(s, 8));
						String[] abilities = ((String) skillTable.getObject(s, 21)).split(",");
						String[] skillMods = ((String) skillTable.getObject(s, 22)).split(",");
						String[] schematicsGranted = ((String) skillTable.getObject(s, 23)).split(",");
						String[] schematicsRevoked = ((String) skillTable.getObject(s, 24)).split(",");
						
						if (pointsRequired > 0) {
							//creature.addExpertisePoints(pointsRequired);
						}
						
						for (String ability : abilities) {
							creature.removeAbility(ability);
						}
						
						for (String skillMod : skillMods) {
							core.skillModService.deductSkillMod(creature, skillMod.split("=")[0], new Integer(skillMod.split("=")[1]));
						}
						
						for (String schematic : schematicsGranted) {
							//player.getDraftSchematicList().remove(new DraftSchematic());
						}
						
						for (String schematic : schematicsRevoked) {
							//player.getDraftSchematicList().add(new DraftSchematic());
						}
					}
				}
			}
		}  catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}
	
	@Override
	public void shutdown() {
		
	}
	
}
