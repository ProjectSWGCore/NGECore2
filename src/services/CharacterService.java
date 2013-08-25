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
import java.util.Map;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.container.CreatureContainerPermissions;
import engine.resources.container.CreaturePermissions;
import engine.resources.database.DatabaseConnection;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.common.*;

import protocol.swg.ClientCreateCharacter;
import protocol.swg.ClientRandomNameRequest;
import protocol.swg.ClientRandomNameResponse;
import protocol.swg.ClientVerifyAndLockNameRequest;
import protocol.swg.ClientVerifyAndLockNameResponse;
import protocol.swg.CreateCharacterSuccess;
import protocol.swg.HeartBeatMessage;

import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;

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
					if (randomNameRequest.getSharedRaceTemplate().contains("wookie")) {
						name = nameGenerator.compose(4);
					} else {
						name = nameGenerator.compose(2) + " " + nameGenerator.compose(3);
					}
					
					try {
						if (checkForDuplicateName(getfirstName(name, randomNameRequest.getSharedRaceTemplate()))) {
							name = null;
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
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				ClientVerifyAndLockNameRequest message = new ClientVerifyAndLockNameRequest();
				message.deserialize(data);
				String approved_flag = "name_approved";
				boolean isDeclined = false;
				String firstName = getfirstName(message.getName(), message.getRaceTemplate());
				String lastName = getlastName(message.getName(), message.getRaceTemplate());
				if(message.getName() != null) System.out.println(message.getName());

				int length = firstName.length();
				try {
					if (checkForDuplicateName(firstName)) {
						
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
				
				try {
					if (checkForDuplicateName(getfirstName(clientCreateCharacter.getName(), clientCreateCharacter.getRaceTemplate()))) {
						return;
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				
				// TODO: Add starting location and items in a script
				String raceTemplate = clientCreateCharacter.getRaceTemplate();
				// should set to some planet and starting location
				String sharedRaceTemplate = raceTemplate.replace("player/", "player/shared_");
				
				CreatureObject object = (CreatureObject) core.objectService.createObject(sharedRaceTemplate, core.terrainService.getPlanetByName("naboo"));
				object.setContainerPermissions(CreaturePermissions.CREATURE_PERMISSIONS);
				object.setCustomization(clientCreateCharacter.getCustomizationData());
				object.setCustomName(clientCreateCharacter.getName());
				object.setHeight(clientCreateCharacter.getScale());
				object.setPersistent(true);
				object.setPosition(new Point3D(-5567, 0, -32));
				object.setCashCredits(100);
				object.setBankCredits(1000);
				//object.setPosition(new Point3D(0, 0, 0));
				object.setOrientation(new Quaternion(1, 0, 0, 0));
				object.createTransaction(core.getCreatureODB().getEnvironment());
				
				PlayerObject player = (PlayerObject) core.objectService.createObject("object/player/shared_player.iff", object.getPlanet());
				object._add(player);
				player.setProfession(clientCreateCharacter.getProfession());
				player.setProfessionWheelPosition(clientCreateCharacter.getProfessionWheelPosition());
				if(clientCreateCharacter.getHairObject().length() > 0) {
					String sharedHairTemplate = clientCreateCharacter.getHairObject().replace("/hair_", "/shared_hair_");
					TangibleObject hair = (TangibleObject) core.objectService.createObject(sharedHairTemplate, object.getPlanet());
					if(clientCreateCharacter.getHairCustomization().length > 0)
						hair.setCustomization(clientCreateCharacter.getHairCustomization());
					object._add(hair);
				}
				
				TangibleObject inventory = (TangibleObject) core.objectService.createObject("object/tangible/inventory/shared_character_inventory.iff", object.getPlanet());
				inventory.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
				TangibleObject appInventory = (TangibleObject) core.objectService.createObject("object/tangible/inventory/shared_appearance_inventory.iff", object.getPlanet());
				appInventory.setContainerPermissions(CreaturePermissions.CREATURE_PERMISSIONS);
				TangibleObject datapad = (TangibleObject) core.objectService.createObject("object/tangible/datapad/shared_character_datapad.iff", object.getPlanet());
				datapad.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
				TangibleObject bank = (TangibleObject) core.objectService.createObject("object/tangible/bank/shared_character_bank.iff", object.getPlanet());
				bank.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
				TangibleObject missionBag = (TangibleObject) core.objectService.createObject("object/tangible/mission_bag/shared_mission_bag.iff", object.getPlanet());
				missionBag.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
				
				object._add(inventory);
				object._add(appInventory);
				object._add(datapad);
				object._add(bank);
				object._add(missionBag);
				TangibleObject backpack = (TangibleObject) core.objectService.createObject("object/tangible/wearables/backpack/shared_backpack_galactic_marine.iff", object.getPlanet());
				inventory._add(backpack);
				//object.addObjectToEquipList(datapad);
				//object.addObjectToEquipList(inventory);
				WeaponObject weapon = (WeaponObject) core.objectService.createObject("object/weapon/ranged/rifle/shared_rifle_a280.iff", object.getPlanet());
				WeaponObject defaultWeapon = (WeaponObject) core.objectService.createObject("object/weapon/creature/shared_creature_default_weapon.iff", object.getPlanet());

				//object.addObjectToEquipList(defaultWeapon);

				object._add(defaultWeapon);

				//object.addObjectToEquipList(weapon);

				object._add(weapon);
				object.setWeaponId(weapon.getObjectID());

				core.scriptService.callScript("scripts/", "demo", "CreateStartingCharacter", object);


				
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
				session.write(core.loginService.getLoginClusterStatus().serialize());
				
				session.write(success.serialize());	
								
			}
			
		});

	}

	private boolean checkForDuplicateName(String firstName) throws SQLException
	{
		firstName = firstName.replace("'", "''");
		firstName = firstName.toLowerCase();
		PreparedStatement ps = databaseConnection.preparedStatement("SELECT id FROM characters WHERE LOWER(\"firstName\") ='" + firstName + "'");
		ResultSet resultSet = ps.executeQuery();
		
		boolean bool = resultSet.next();
		resultSet.getStatement().close();
		return bool;
	}

		
	private String getfirstName(String Name, String RaceTemplate)
	{
		String[] splitName;
		if (RaceTemplate != "object/creature/player/wookiee_male.iff" | RaceTemplate != "object/creature/player/wookiee_female.iff")
		{ // wookies don't have lastNames
			if (Name.contains(" "))
			{
				splitName = Name.split(" ", 2);
				return splitName[0];
			}
			else
			{
				return Name;
			}
		}
		else
		{
			return Name;
		}
	}
	
	private String getlastName(String Name, String RaceTemplate)
	{
		String[] splitName;
		if (RaceTemplate != "object/creature/player/wookiee_male.iff" && RaceTemplate != "object/creature/player/wookiee_female.iff")
		{ // wookies don't have lastNames
			if (Name.contains(" "))
			{
				splitName = Name.split(" ", 2);
				return splitName[1];
			}
			else
			{
				return "";
			}
		}
		else
		{
			return "";
		}
	}

	private String checkForReservedName(String firstName, String lastName) throws SQLException
	{
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

}
