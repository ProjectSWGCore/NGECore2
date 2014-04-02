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
import java.nio.ByteOrder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;



import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.container.CreatureContainerPermissions;
import engine.resources.container.CreaturePermissions;
import engine.resources.container.Traverser;
import engine.resources.database.DatabaseConnection;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.common.*;
import resources.datatables.Options;
import resources.datatables.Professions;
import protocol.swg.ClientCreateCharacter;
import protocol.swg.ClientMfdStatusUpdateMessage;
import protocol.swg.ClientRandomNameRequest;
import protocol.swg.ClientRandomNameResponse;
import protocol.swg.ClientVerifyAndLockNameRequest;
import protocol.swg.ClientVerifyAndLockNameResponse;
import protocol.swg.CreateCharacterSuccess;
import protocol.swg.HeartBeatMessage;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import resources.visitors.ProfessionTemplateVisitor;

@SuppressWarnings("unused")

public class CharacterService implements INetworkDispatch {

	private NGECore core;
	private DatabaseConnection databaseConnection;
	private DatabaseConnection databaseConnection2;
	private engine.resources.common.NameGen nameGenerator;
	private static final String allowedCharsRegex = "['-]?[A-Za-z]('[a-zA-Z]|-[a-zA-Z]|[a-zA-Z])*['-]?$";

	public CharacterService(NGECore core) {

		this.core = core;
		this.databaseConnection = core.getDatabase1();
		this.databaseConnection2 = core.getDatabase2();
		try {
			nameGenerator = new engine.resources.common.NameGen("names.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertOpcodes(Map<Integer,INetworkRemoteEvent> swgOpcodes, Map<Integer,INetworkRemoteEvent> objControllerOpcodes) {

		swgOpcodes.put(Opcodes.ClientRandomNameRequest, new INetworkRemoteEvent() {
			
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				ClientRandomNameRequest randomNameRequest = new ClientRandomNameRequest();
				ClientRandomNameResponse response;
				String name = null;
				
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				randomNameRequest.deserialize(data);
				
				
				while (name == null) {
					
					String firstName ="";
					String lastName = "";
					if (randomNameRequest.getSharedRaceTemplate().contains("wookie")) {
						firstName = nameGenerator.compose(4);
						
					} else {
						firstName = nameGenerator.compose(2);
						lastName = nameGenerator.compose(3);
					}
					
					try {
						//it's fine here to just use 0 as accountId because we don't want reserved names to be random generated for anyone.
						if (!checkForDuplicateName(firstName, 0)) {
							name = firstName + ((lastName.length() > 0 ) ?  " " + lastName : "");
						}
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
				
				response = new ClientRandomNameResponse(randomNameRequest.getSharedRaceTemplate(), name);
				session.write(response.serialize());
			}
			
		});

		swgOpcodes.put(Opcodes.ClientVerifyAndLockNameRequest, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				Client client = core.getClient(session);
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				ClientVerifyAndLockNameRequest message = new ClientVerifyAndLockNameRequest();
				message.deserialize(data);
				String approved_flag = "name_approved";
				boolean isDeclined = false;
				String firstName = getfirstName(message.getName(), message.getRaceTemplate());
				String lastName = getlastName(message.getName(), message.getRaceTemplate());
				//if(message.getName() != null) System.out.println(message.getName());

				int length = firstName.length();
				try {
					if (client == null) {
						System.out.println("NULL client in ClientVerifyAndLockNameRequest, something is still bust");
						return;
					}
					if (checkForDuplicateName(firstName, client.getAccountId())) {
						
						approved_flag = "name_declined_in_use";
						isDeclined = true;
					} 
					if ((!isDeclined) && (length < 3 || length > 15 || lastName.length() > 20)) {
						
						approved_flag = "name_declined_racially_inappropriate";
						isDeclined = true;
					}
					
					if (!isDeclined) {
						String rv = checkForReservedName(firstName, lastName);
						if (rv != null) {
							approved_flag = rv;
							isDeclined = true;
						}
					}
					
					if (!isDeclined)
					{ 
						if (!firstName.matches(allowedCharsRegex) || (!lastName.matches(allowedCharsRegex) && lastName != "")) {
							approved_flag = "name_declined_syntax";
							isDeclined = true;
							
						}
					}
					
					if (!isDeclined) {
						approved_flag = "name_approved";
					}
					
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				ClientVerifyAndLockNameResponse response = new ClientVerifyAndLockNameResponse(firstName, approved_flag);
				session.write(response.serialize());
			}
			
			
		});
		
		swgOpcodes.put(Opcodes.LagReport, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {

			}
			
		});
		
		swgOpcodes.put(Opcodes.ClientCreateCharacter, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {

				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				ClientCreateCharacter clientCreateCharacter = new ClientCreateCharacter();
				clientCreateCharacter.deserialize(data);
				
				engine.resources.config.Config config = new engine.resources.config.Config();
				config.setFilePath("nge.cfg");
				if (!(config.loadConfigFile())) {
					config = engine.resources.config.DefaultConfig.getConfig();
				}
				
				int galaxyId = config.getInt("GALAXY_ID");
				Client client = core.getClient(session);
				
				try {
					if (client == null || checkForDuplicateName(getfirstName(clientCreateCharacter.getName(), clientCreateCharacter.getRaceTemplate()), client.getAccountId())) {
						return;
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
				// TODO: Add starting location and items in a script
				String raceTemplate = clientCreateCharacter.getRaceTemplate();
				// should set to some planet and starting location
				String sharedRaceTemplate = raceTemplate.replace("player/", "player/shared_");
				
				CreatureObject object = (CreatureObject) core.objectService.createObject(sharedRaceTemplate, core.terrainService.getPlanetByName("tatooine"));
				object.setContainerPermissions(CreaturePermissions.CREATURE_PERMISSIONS);
				object.setCustomization(clientCreateCharacter.getCustomizationData());
				object.setCustomName(clientCreateCharacter.getName());
				object.setHeight(clientCreateCharacter.getScale());
				object.setPersistent(true);
				object.setPosition(SpawnPoint.getRandomPosition(new Point3D(3528, 0, -4804), (float) 0.5, 3, core.terrainService.getPlanetByName("tatooine").getID()));
				object.setCashCredits(100);
				object.setBankCredits(1000);
				object.setOptionsBitmask(Options.ATTACKABLE);
				//object.setPosition(new Point3D(0, 0, 0));
				object.setOrientation(new Quaternion(1, 0, 0, 0));
				object.setLevel((short) 1);
				float luck = (((((float) (core.scriptService.getMethod("scripts/roadmap/", clientCreateCharacter.getProfession(), "getLuck").__call__().asInt()) + (core.scriptService.getMethod("scripts/roadmap/", object.getStfName(), "getLuck").__call__().asInt())) / ((float) 90)) * ((float) object.getLevel())) - ((float) object.getSkillModBase("luck")));
				float precision = (((((float) (core.scriptService.getMethod("scripts/roadmap/", clientCreateCharacter.getProfession(), "getPrecision").__call__().asInt()) + (core.scriptService.getMethod("scripts/roadmap/", object.getStfName(), "getPrecision").__call__().asInt())) / ((float) 90)) * ((float) object.getLevel())) - ((float) object.getSkillModBase("precision")));
				float strength = (((((float) (core.scriptService.getMethod("scripts/roadmap/", clientCreateCharacter.getProfession(), "getStrength").__call__().asInt()) + (core.scriptService.getMethod("scripts/roadmap/", object.getStfName(), "getStrength").__call__().asInt())) / ((float) 90)) * ((float) object.getLevel())) - ((float) object.getSkillModBase("strength")));
				float constitution = (((((float) (core.scriptService.getMethod("scripts/roadmap/", clientCreateCharacter.getProfession(), "getConstitution").__call__().asInt()) + (core.scriptService.getMethod("scripts/roadmap/", object.getStfName(), "getConstitution").__call__().asInt())) / ((float) 90)) * ((float) object.getLevel())) - ((float) object.getSkillModBase("constitution")));
				float stamina = (((((float) (core.scriptService.getMethod("scripts/roadmap/", clientCreateCharacter.getProfession(), "getStamina").__call__().asInt()) + (core.scriptService.getMethod("scripts/roadmap/", object.getStfName(), "getStamina").__call__().asInt())) / ((float) 90)) * ((float) object.getLevel())) - ((float) object.getSkillModBase("stamina")));
				float agility = (((((float) (core.scriptService.getMethod("scripts/roadmap/", clientCreateCharacter.getProfession(), "getAgility").__call__().asInt()) + (core.scriptService.getMethod("scripts/roadmap/", object.getStfName(), "getAgility").__call__().asInt())) / ((float) 90)) * ((float) object.getLevel())) - ((float) object.getSkillModBase("agility")));
				if (luck >= 1) core.skillModService.addSkillMod(object, "luck", (int) luck);
				if (precision >= 1) core.skillModService.addSkillMod(object, "precision", (int) precision);
				if (strength >= 1) core.skillModService.addSkillMod(object, "strength", (int) strength);
				if (constitution >= 1) core.skillModService.addSkillMod(object, "constitution", (int) constitution);
				if (stamina >= 1) core.skillModService.addSkillMod(object, "stamina", (int) stamina);
				if (agility >= 1) core.skillModService.addSkillMod(object, "agility", (int) agility);

				core.skillModService.addSkillMod(object, "language_basic_comprehend", 100);
				core.skillModService.addSkillMod(object, "language_basic_speak", 100);
				core.skillModService.addSkillMod(object, "creature_harvesting", 25);
				core.skillModService.addSkillMod(object, "language_wookiee_comprehend", 100);
				
				
				object.createTransaction(core.getCreatureODB().getEnvironment());
				
				PlayerObject player = (PlayerObject) core.objectService.createObject("object/player/shared_player.iff", object.getPlanet());
				object._add(player);
				core.skillService.addSkill(object, "species_" + object.getStfName());
				player.setProfession(clientCreateCharacter.getProfession());
				player.setProfessionIcon(Professions.get(clientCreateCharacter.getProfession()));
				player.setProfessionWheelPosition(clientCreateCharacter.getProfessionWheelPosition());
				if(clientCreateCharacter.getHairObject().length() > 0) {
					String sharedHairTemplate = clientCreateCharacter.getHairObject().replace("/hair_", "/shared_hair_");
					TangibleObject hair = (TangibleObject) core.objectService.createObject(sharedHairTemplate, object.getPlanet());
					if(clientCreateCharacter.getHairCustomization().length > 0)
						hair.setCustomization(clientCreateCharacter.getHairCustomization());
					object._add(hair);
					object.addObjectToEquipList(hair);
				}
				
				player.setBornDate((int) System.currentTimeMillis());

				TangibleObject inventory = (TangibleObject) core.objectService.createObject("object/tangible/inventory/shared_character_inventory.iff", object.getPlanet());
				inventory.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
				TangibleObject appInventory = (TangibleObject) core.objectService.createObject("object/tangible/inventory/shared_appearance_inventory.iff", object.getPlanet());
				appInventory.setContainerPermissions(CreaturePermissions.CREATURE_PERMISSIONS);
				TangibleObject datapad = (TangibleObject) core.objectService.createObject("object/tangible/datapad/shared_character_datapad.iff", object.getPlanet());
				datapad.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
				datapad.setStaticObject(false);
				TangibleObject bank = (TangibleObject) core.objectService.createObject("object/tangible/bank/shared_character_bank.iff", object.getPlanet());
				bank.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
				bank.setStaticObject(false);
				TangibleObject missionBag = (TangibleObject) core.objectService.createObject("object/tangible/mission_bag/shared_mission_bag.iff", object.getPlanet());
				missionBag.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
				missionBag.setStaticObject(false);
				
				object._add(inventory);
				object._add(appInventory);
				object._add(datapad);
				object._add(bank);
				object._add(missionBag);
				
				/*for(int missionsAdded = 0; missionsAdded < 12; missionsAdded++) {
					MissionObject mission = (MissionObject) core.objectService.createObject("object/mission/shared_mission_object.iff", object.getPlanet());

					missionBag._add(mission);
					Console.println("Added empty mission " + missionsAdded);
				}*/
				
				object.addAbility("startDance");
				object.addAbility("startDance+Basic");
				
				object.addObjectToEquipList(datapad);
				object.addObjectToEquipList(inventory);
				object.addObjectToEquipList(bank);
				object.addObjectToEquipList(missionBag);
				object.addObjectToEquipList(appInventory);
				
				WeaponObject defaultWeapon = (WeaponObject) core.objectService.createObject("object/weapon/creature/shared_creature_default_weapon.iff", object.getPlanet());
				defaultWeapon.setDamageType("@obj_attr_n:armor_eff_kinetic");
				defaultWeapon.setStringAttribute("cat_wpn_damage.damage", "0-0");
				defaultWeapon.setMaxDamage(100);
				defaultWeapon.setMinDamage(50);
				
				object.addObjectToEquipList(defaultWeapon);

				object._add(defaultWeapon);
				object.setWeaponId(defaultWeapon.getObjectID());
				
				createStarterClothing(object, sharedRaceTemplate, clientCreateCharacter.getStarterProfession());
				//core.scriptService.callScript("scripts/", "demo", "CreateStartingCharacter", core, object);
				
				core.getCreatureODB().put(object, Long.class, CreatureObject.class, object.getTransaction());
				// might not need to commit transaction but better safe than sorry
				object.getTransaction().commitSync();

				PreparedStatement ps = databaseConnection.preparedStatement("INSERT INTO characters (id, \"firstName\", \"lastName\", \"accountId\", \"galaxyId\", \"statusId\", appearance, gmflag) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setLong(1, object.getObjectID());
				ps.setString(2, clientCreateCharacter.getFirstName());
				ps.setString(3, clientCreateCharacter.getLastName());
				ps.setInt(4, (int) client.getAccountId()); // accountID
				ps.setInt(5, galaxyId);
				ps.setInt(6, 1);
				ps.setInt(7, CRC.StringtoCRC(raceTemplate));
				ps.setBoolean(8, false);
				ps.executeUpdate();
				ps.close();
				CreateCharacterSuccess success = new CreateCharacterSuccess(object.getObjectID());
				session.write(new HeartBeatMessage().serialize());
				session.write(core.loginService.getLoginCluster().serialize());
				session.write(core.loginService.getLoginClusterStatus(client).serialize());
				
				session.write(success.serialize());
			}
			
		});
		
		swgOpcodes.put(Opcodes.NewbieTutorialResponse, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
			}
			
		});
		
		swgOpcodes.put(Opcodes.SetJediSlotInfo, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
			}
			
		});

	}

	private boolean checkForDuplicateName(String firstName, long accountId) throws SQLException
	{
		firstName = firstName.replace("'", "''");
		firstName = firstName.toLowerCase();
		PreparedStatement ps = databaseConnection.preparedStatement("SELECT id FROM characters WHERE LOWER(\"firstName\")=?");
		ps.setString(1, firstName);
		//System.out.println(ps.toString());
		ResultSet resultSet = ps.executeQuery();
	
		boolean isDuplicate = resultSet.next();
		resultSet.getStatement().close();
		if (isDuplicate) { return true; }
		
		//FIXME: this is a bit lazy... but it's only temporary :p
		PreparedStatement psc = databaseConnection.preparedStatement("SELECT * FROM pg_tables WHERE \"tablename\"=?");
		psc.setString(1, "temp_reserved_char_names");
		ResultSet resultSetC = psc.executeQuery();
		boolean tableExists = resultSetC.next();
		resultSetC.getStatement().close();
		if (!tableExists) { return false; } 
		
		PreparedStatement ps2 = databaseConnection.preparedStatement("SELECT \"accountId\" FROM temp_reserved_char_names WHERE \"accountId\"!=? AND LOWER(\"firstName\")=?");
		ps2.setLong(1, accountId);
		ps2.setString(2, firstName.toLowerCase());
		ResultSet resultSet2 = ps2.executeQuery();
		boolean isReserved = resultSet2.next();
		resultSet2.getStatement().close();
		
		return isReserved;
	}
	
	
	private String getfirstName(String Name, String RaceTemplate)
	{
		if (RaceTemplate.contains("/wookiee_") ||
				!Name.contains(" ")) 
		{ // wookiees don't have lastNames
				return Name;
		}
			
		return Name.split(" ", 2)[0];

	}
	
	private String getlastName(String Name, String RaceTemplate)
	{
		if (RaceTemplate.contains("/wookiee_") ||
			!Name.contains(" ")) 
		{ // wookiees don't have lastNames
			return "";
		}
		
		return Name.split(" ", 2)[1];

	}

	private String checkForReservedName(String firstName, String lastName) throws SQLException
	{
		
		PreparedStatement psc = databaseConnection.preparedStatement("SELECT * FROM pg_tables WHERE \"tablename\"=?");
		psc.setString(1, "reservednames");
		ResultSet rsc = psc.executeQuery();
		if (!rsc.next()) {
			return null;
		}
		
		firstName = firstName.toLowerCase();
		lastName = lastName.toLowerCase();
		
		firstName.replaceAll("'", "");
		firstName.replaceAll("-", "");
		lastName.replaceAll("'", "");
		lastName.replaceAll("-", "");
		
		String combinedName = firstName + " " + lastName;
		combinedName.replaceAll("\\s*$", "");
		
		String query = "SELECT \"type\" FROM \"reservednames\" WHERE (\"match_firstname\" = TRUE AND ? ~ \"pattern\") OR (\"match_both_names\" = TRUE and ? ~ \"pattern\") ";
		if (lastName != "")
		{
			query = query + " OR (\"match_lastname\" = TRUE and ? ~ \"pattern\")  ";
		}
		
		PreparedStatement ps = databaseConnection.preparedStatement(query);
		ps.setString(1, firstName);
		ps.setString(2, combinedName);
		if (lastName != "")
		{
			ps.setString(3, lastName);
		}
		
		ResultSet resultSet = ps.executeQuery();
		
		if (resultSet.next())
		{
			
			return resultSet.getString("type");
			
		}
		
		return null;
	}

	
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public int getNumberOfCharacters(int accountId) {
		PreparedStatement preparedStatement;
		int characters = 0;
		try {
			preparedStatement = databaseConnection.preparedStatement("SELECT * FROM characters WHERE \"accountId\"=" + accountId + "");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next() && !resultSet.isClosed())
				characters++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return characters;
	}
	
	/**
	 * Checks the database for if the name of the player exists. The name is
	 * formated automatically in the method for checking the database, so no conversion is required.
	 * @param name Name to check for in the database
	 * @return Returns True if the player exists
	 */
	public boolean playerExists(String name) {
		if (!name.equals("")) {
			if (name.contains(" ")) {
				name = name.split(" ")[0];
			}
			name = name.toLowerCase();

			try {
				PreparedStatement ps = databaseConnection.preparedStatement("SELECT id FROM characters WHERE LOWER(\"firstName\")=?");
				ps.setString(1, name);
				ResultSet resultSet = ps.executeQuery();
				
				boolean isDuplicate = resultSet.next();
				resultSet.getStatement().close();
				if (isDuplicate) { return true; }
				else { return false; }
			} 
			
			catch (SQLException e) { e.printStackTrace(); }
		}
		return false;
	}
	
	/**
	 * Checks the database for if the object ID of the player exists.
	 * @param objectId Object ID to check for in the database
	 * @return Returns True if the player exists
	 */
	public boolean playerExists(long objectId) {

		try {
			PreparedStatement ps = databaseConnection.preparedStatement("SELECT id FROM characters WHERE id=?");
			ps.setLong(1, objectId);
			ResultSet resultSet = ps.executeQuery();
			
			boolean isDuplicate = resultSet.next();
			resultSet.getStatement().close();
			if (isDuplicate) { return true; }
			else { return false; }
		} 
		
		catch (SQLException e) { e.printStackTrace(); }
		return false;
	}

	
	private void createStarterClothing(CreatureObject creature, String raceTemplate, String profession) {
		try {
			ProfessionTemplateVisitor visitor = ClientFileManager.loadFile("creation/profession_defaults_" + profession + ".iff", ProfessionTemplateVisitor.class);
			TangibleObject inventory = (TangibleObject) creature.getSlottedObject("inventory");

			if (inventory == null)
				return;

			for(String item : visitor.getItems(raceTemplate)) {
				TangibleObject createdItem = (TangibleObject) core.objectService.createObject(item, creature.getPlanet());

				if (createdItem == null)
					return;

				creature._add(createdItem);
				creature.addObjectToEquipList(createdItem);
			}
		} 
		catch (InstantiationException | IllegalAccessException e) { e.printStackTrace();}
	}
}
