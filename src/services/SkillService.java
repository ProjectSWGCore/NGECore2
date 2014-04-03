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

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.python.core.Py;
import org.python.core.PyObject;

import protocol.swg.ExpertiseRequestMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.objectControllerObjects.SetProfessionTemplate;
import protocol.swg.objectControllerObjects.UiPlayEffect;
import resources.common.Console;
import resources.common.FileUtilities;
import resources.common.ObjControllerOpcodes;
import resources.common.Opcodes;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import main.NGECore;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

@SuppressWarnings("unused")
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
						boolean isTitle = ((Boolean) skillTable.getObject(s, 4));
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
						
						boolean speciesSkill = skill.matches("^species.+$");
						
						if (isTitle == true) {
							core.playerService.addPlayerTitle(player, skill);
						}
						
						if (isProfession) {
							return;
						}

						//exempt species skills from being returned -- they're marked godOnly but really meant to be granted
						if ((!speciesSkill) && (godOnly  || isHidden)) {
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
						
						if (skill.contains("expertise")) {
							if (FileUtilities.doesFileExist("scripts/expertise/" + skill + ".py")) {
								PyObject method = core.scriptService.getMethod("scripts/expertise/", skill, "addAbilities");
								
								if (method != null && method.isCallable()) {
									method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(player));
								}
							}
						} else {
							for (String ability : abilities) {
								creature.addAbility(ability);
							}
							
							// When leveling, add all new unadded expertise abilities
							// It's up to the script to not add abilities that are already added
							for (String expertiseName : creature.getSkills()) {
								if (expertiseName.startsWith("expertise")) {
									if (FileUtilities.doesFileExist("scripts/expertise/" + expertiseName + ".py")) {
										PyObject method = core.scriptService.getMethod("scripts/expertise/", expertiseName, "addAbilities");
										
										if (method != null && method.isCallable()) {
											method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(player));
										}
									}
								}
							}
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

		if (player == null) {
			return;
		}
		
		if (!creature.hasSkill(skill)) {
			return;
		}
		
		try {
			skillTable = ClientFileManager.loadFile("datatables/skill/skills.iff", DatatableVisitor.class);
			
			for (int s = 0; s < skillTable.getRowCount(); s++) {
				if (skillTable.getObject(s, 0) != null) {
					if (((String) skillTable.getObject(s, 0)).equals(skill)) {
						String parent = ((String) skillTable.getObject(s, 1));
						//int graphType = ((Integer) skillTable.getObject(s, 2));
						boolean godOnly = ((Boolean) skillTable.getObject(s, 3));
						boolean isTitle = ((Boolean) skillTable.getObject(s, 4));
						boolean isHidden = ((Boolean) skillTable.getObject(s, 6));
						int pointsRequired = ((Integer) skillTable.getObject(s, 8));
						String[] abilities = ((String) skillTable.getObject(s, 21)).split(",");
						String[] skillMods = ((String) skillTable.getObject(s, 22)).split(",");
						String[] schematicsGranted = ((String) skillTable.getObject(s, 23)).split(",");
						String[] schematicsRevoked = ((String) skillTable.getObject(s, 24)).split(",");
						
						if (isTitle) {
							core.playerService.removePlayerTitle(player, skill);
							Console.println("Removed title: " + skill);
						}
						
						if (pointsRequired > 0) {
							//creature.addExpertisePoints(pointsRequired);
						}
						
						if (skill.contains("expertise")) {
							if (FileUtilities.doesFileExist("scripts/expertise/" + skill + ".py")) {
								PyObject method = core.scriptService.getMethod("scripts/expertise/", skill, "removeAbilities");
								
								if (method != null && method.isCallable()) {
									method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(player));
								}
							}
						} else {
							for (String ability : abilities) {
								creature.removeAbility(ability);
							}
						}									
						
						for (String skillMod : skillMods) {
							if(skillMod.split("=").length == 2) core.skillModService.deductSkillMod(creature, skillMod.split("=")[0], new Integer(skillMod.split("=")[1]));
						}
						
						for (String schematic : schematicsGranted) {
							//player.getDraftSchematicList().remove(new DraftSchematic());
						}
						
						for (String schematic : schematicsRevoked) {
							//player.getDraftSchematicList().add(new DraftSchematic());
						}
						
						creature.removeSkill(skill);
					}
				}
			}
		}  catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		swgOpcodes.put(Opcodes.ExpertiseRequestMessage, (session, buffer) -> {

			buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);
			buffer.position(0);
			
			ExpertiseRequestMessage expertise = new ExpertiseRequestMessage();
			expertise.deserialize(buffer);

			Client client = core.getClient(session);
			if(client == null) {
				System.out.println("NULL Client");
				return;
			}

			if(client.getParent() == null)
				return;
			
			CreatureObject creature = (CreatureObject) client.getParent();
			PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
			
			if(player == null)
				return;
			
			for(String expertiseName : expertise.getExpertiseSkills()) {
				if(expertiseName.startsWith("expertise_") && ((caluclateExpertisePoints(creature) - 1) >= 0) && validExpertiseSkill(player, expertiseName)) { // Prevent possible glitches/exploits
					addSkill(creature, expertiseName);
				}
			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.SET_PROFESSION_TEMPLATE, (session, buffer) -> {
			
			buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);

			SetProfessionTemplate profTemplate = new SetProfessionTemplate();
			profTemplate.deserialize(buffer);
			String profession = profTemplate.getProfession();
			
			Client client = core.getClient(session);
			if(client == null) {
				System.out.println("NULL Client");
				return;
			}

			if(client.getParent() == null)
				return;
			
			CreatureObject creature = (CreatureObject) client.getParent();
			PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
			
			//System.out.println(profession);
			if(player == null || player.getProfession().equals(profession) || profession == null)
				return;
			
			core.playerService.respec(creature, profession);

		});
		
	}

	public int caluclateExpertisePoints(CreatureObject creature)
	{
		int expertisePoints = 0;
		try 
		{
			DatatableVisitor table = ClientFileManager.loadFile("datatables/player/player_level.iff", DatatableVisitor.class);		
			for (int i = 0; i < creature.getLevel(); ++i) expertisePoints += (int) table.getObject(i, 5);
			for (String skill : creature.getSkills()) if(skill.startsWith("expertise_")) expertisePoints--;
		}
		catch (Exception e) { e.printStackTrace(); }	
		return expertisePoints;
	}
	
	public boolean validExpertiseSkill(PlayerObject player, String skill)
	{
		try 
		{
			DatatableVisitor table = ClientFileManager.loadFile("datatables/expertise/expertise.iff", DatatableVisitor.class);
			String profession;
			
			switch(player.getProfession())
			{
				case "trader_0a":
					profession = "trader_dom";
					break;
					
				case "trader_0b":
					profession = "trader_struct";
					break;
					
				case "trader_0c":
					profession = "trader_mun";
					break;
					
				case "trader_0d":
					profession = "trader_eng";
					break;
						
				default:
					profession = player.getProfession().replace("_1a", "");
					break;
			}

			for (int s = 0; s < table.getRowCount(); s++) 
			{	
				if (table.getObject(s, 0) != null && ((String) table.getObject(s, 0)).equals(skill))
				{
					if(((String)table.getObject(s, 7)).equals(profession) || ((String)table.getObject(s, 7)).equals("all")) return true;
					else return false;
				}
			}		
		}
		catch (Exception e) { e.printStackTrace(); }
		return false;
	}
	
	public void resetExpertise(CreatureObject creature) {
		List<String> skills = new ArrayList<String>(creature.getSkills().get());
		skills.stream().filter(s -> s.contains("expertise")).forEach(s -> removeSkill(creature, s));
	}
	
	public void sendRespecWindow(CreatureObject creature) {
		if(creature.getClient() == null)
			return;
		UiPlayEffect ui = new UiPlayEffect(creature.getObjectID(), "showMediator=ws_professiontemplateselect");
		ObjControllerMessage objController = new ObjControllerMessage(0x0B, ui);
		creature.getClient().getSession().write(objController.serialize());
	}
	
	@Override
	public void shutdown() {
		
	}
	
}
