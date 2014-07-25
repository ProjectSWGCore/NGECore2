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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import engine.clients.Client;
import engine.protocol.soe.Disconnect;
import engine.resources.database.DatabaseConnection;
import engine.resources.service.ILoginProvider;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import engine.resources.service.LocalDbLoginProvider;
import engine.resources.service.VBLoginProvider;
import protocol.swg.CharacterCreationDisabled;
import protocol.swg.DeleteCharacterMessage;
import protocol.swg.DeleteCharacterReplyMessage;
import protocol.swg.EnumerateCharacterId;
import protocol.swg.ErrorMessage;
import protocol.swg.LoginClientId;
import protocol.swg.LoginClientToken;
import protocol.swg.LoginClusterStatus;
import protocol.swg.LoginEnumCluster;
import protocol.swg.ServerNowEpochTime;
import protocol.swg.StationIdHasJediSlot;
import resources.common.*;
import resources.datatables.PlayerFlags;
import resources.guild.Guild;
import resources.objects.creature.CreatureObject;

@SuppressWarnings("unused")

public class LoginService implements INetworkDispatch{
	
	private int sessionKeyLength = 0;
	private NGECore core;
	private DatabaseConnection databaseConnection1;
	private DatabaseConnection databaseConnection2;
	private Random random;
	private ILoginProvider LoginProvider;
	
	public LoginService(NGECore core) {
		this.core = core;
		this.sessionKeyLength = core.getConfig().getInt("LOGIN.SESSION_KEY_SIZE");
		this.databaseConnection1 = core.getDatabase1();
		this.databaseConnection2 = core.getDatabase2();
		this.random = new Random();
	    if (databaseConnection2 == null) {
	    	LoginProvider = new LocalDbLoginProvider(databaseConnection1);
	    } else {
			LoginProvider = new VBLoginProvider(databaseConnection1, databaseConnection2);
	    }
	}
	
	public void insertTimedEventBindings(ScheduledExecutorService executor) {

	}
	
	public void insertOpcodes(Map<Integer,INetworkRemoteEvent> swgOpcodes, Map<Integer,INetworkRemoteEvent> objControllerOpcodes) {

		swgOpcodes.put(Opcodes.LoginClientId, new INetworkRemoteEvent() {

			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				if (session == null) {
					System.out.println("Login attempted with NULL session!");
					return;
				}
				
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				LoginClientId clientID = new LoginClientId();
				data.position(0);
				clientID.deserialize(data);
				
				String user        = clientID.getAccountName();
				String pass        = clientID.getPassword();
				int id             = LoginProvider.getAccountId(user, pass, session.getRemoteAddress().toString());
				System.out.println("LOGIN: " + user + " login attempt "+session.getRemoteAddress().toString()+" returned " + id);
				
				if (id < 0)  {
					
					String err = "";
					switch (id) {
						case -1:
							err = "Database error";
							break;
						case -2:
							err = "invalid user or password";
							break;
						case -3:
							err = "user is banned";
							break;
					}
					
					// No matter what, this will always show that the login server is unavailable at this time. Removing the error message will just keep the connecting message up.
					ErrorMessage errMsg = new ErrorMessage("Wrong Password", "The username or password you entered was incorrect.");
					session.write(errMsg.serialize());
					
					Disconnect disconnect = new Disconnect((Integer)session.getAttribute("connectionId"), 6);
					session.write(disconnect);
					
					// Closing the session for some reason will not show any error messages, even if write messages are queued.. 
					//As result, the infinite "Connection to the login server is now open..." loop., also it randomly denies any logins from that client for some reason
					return;
				}
				
				Client client = new Client(session.getRemoteAddress());
				client.setAccountName(user);
				client.setPassword(pass);
				client.setSessionKey(generateSessionKey());
				client.setAccountId(id);
				//client.setAccountEmail(email);
				client.setSession(session);
				client.setGM(checkForGmPermission(id));
				
				core.addClient(session, client);
				
				if (!core.getActiveConnectionsMap().containsKey(session)) {
					Disconnect disconnect = new Disconnect((Integer)session.getAttribute("connectionId"), 6);
					session.write(disconnect);
					ErrorMessage errMsg = new ErrorMessage("Error Logging In", "Your client could not be added to the connections at this time.");
					session.write(errMsg.serialize());
					session.close(false);
					
					System.out.println(client.getAccountName() + " was not added to active connections map.");
					return;				
				}
				/*if(!checkIfAccountExistInGameDB(id)) {
					createAccountForGameDB(id, user, email, encryptPass);
				}*/
				
				persistSession(client);

				LoginEnumCluster servers = getLoginCluster();
				LoginClusterStatus serverStatus = getLoginClusterStatus(client);
				EnumerateCharacterId characters = getEnumerateCharacterId(id);

				LoginClientToken clientToken = new LoginClientToken(client.getSessionKey(), id, user);
				CharacterCreationDisabled charCreationDisabled = new CharacterCreationDisabled(0);
				StationIdHasJediSlot jediSlot = new StationIdHasJediSlot(false);
				ServerNowEpochTime time = new ServerNowEpochTime((int) (System.currentTimeMillis() / 1000));
				
				if (client.getSession().containsAttribute("tradeSession") == true) {
					client.getSession().removeAttribute("tradeSession");
				}
				
				session.write(time.serialize());
				session.write(clientToken.serialize());
				session.write(servers.serialize());
				session.write(charCreationDisabled.serialize());
				session.write(serverStatus.serialize());
				session.write(jediSlot.serialize());
				session.write(characters.serialize());
				
				System.out.println(user + " successfully logged in.");
			}

		});
		
		swgOpcodes.put(Opcodes.DeleteCharacterMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {

				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);

				DeleteCharacterMessage packet = new DeleteCharacterMessage();
				packet.deserialize(data);
				Client client = core.getClient(session);
				
                		PreparedStatement preparedStatement;
                		
                		preparedStatement = databaseConnection1.preparedStatement("DELETE FROM characters WHERE \"id\"=? AND \"galaxyId\"=? AND \"accountId\"=?");
                		preparedStatement.setLong(1, packet.getcharId());
                		preparedStatement.setInt(2, packet.getgalaxyId());
                		preparedStatement.setInt(3, (int) client.getAccountId());
                		boolean resultSet = preparedStatement.execute();   
                		
                		//TODO: send deletecharacter failed
                		if(!resultSet) {
                			CreatureObject object = (CreatureObject) core.objectService.getObject(packet.getcharId());
                			
                			if (object == null)
                				object = (CreatureObject) core.objectService.getCreatureFromDB(packet.getcharId());
                			
                			if (object != null) {
                				if (object.isInQuadtree() && object.getClient() != null) {
                					core.connectionService.disconnect(object.getClient());
                				} 
                				
                				if (object.isInQuadtree()) {
                					core.simulationService.remove(object, object.getPosition().x, object.getPosition().z, true);
                					if (core.missionService.getBountyListItem(object.getObjectID()) != null) {
                						core.missionService.removeBounty(object.getObjectID(), true);
                					}
                				} else {
                					if (core.getBountiesODB().contains(object.getObjectID())) 
                						core.getBountiesODB().remove(object.getObjectID());
                				}
                				
                				if (object.getGuildId() != 0)
                					core.guildService.getGuildById(object.getGuildId()).getMembers().remove(object.getObjectID());
                				
                				Guild guild = core.guildService.getGuildById(((CreatureObject) object).getGuildId());
                				
                				if(object.getGuildId() != 0 && object.getObjectID() == guild.getLeader()){
                					core.guildService.handleGuildDisband(object, guild);
                				}
                				
                				core.objectService.destroyObject(object);
                			}
                			
            				try {
            					core.getSWGObjectODB().remove(object.getObjectID());
            				} catch (Exception e) {
                    			DeleteCharacterReplyMessage reply = new DeleteCharacterReplyMessage(1);
                    			session.write(reply.serialize());
                    			preparedStatement.close();
                    			return;
            				}
            				
                			DeleteCharacterReplyMessage reply = new DeleteCharacterReplyMessage(0);
                			session.write(reply.serialize());
                		}
                		
                		preparedStatement.close();
                		
			}
			
		});

	}
	
	public void shutdown() {
		
	}
	
	public boolean checkForGmPermission(int id) {
		try {
			if (core.getConfig().getInt("ADMIN") > 0) {
				return true;
			}
		} catch (Exception e) {
			
		}
		
		PreparedStatement preparedStatement;
		
		try {
			preparedStatement = databaseConnection1.preparedStatement("SELECT * FROM accounts WHERE id=" + id + "");
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
				return resultSet.getBoolean("gmflag");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Saves session data to DB so Zone Server can link sessions to accounts.
	 * @param client Client that needs a session save.
	 */
	private void persistSession(Client client) throws SQLException {

	    PreparedStatement ps = databaseConnection1.preparedStatement("INSERT INTO sessions (\"key\", \"accountId\") VALUES (?, ?)");
	    ps.setBytes(1, client.getSessionKey());
	    ps.setLong(2, client.getAccountId());
	    ps.executeUpdate();
	    ps.close(); 
				
	}
	
	/**
	 * Generates random Session Key.
	 * @return random Session Key.
	 */
	private byte [] generateSessionKey() {
		byte [] bytes = new byte[sessionKeyLength];
		random.nextBytes(bytes);
		return bytes;
	}
	
	/**
	 * Generates EnumerateCharacterId packet.
	 * @param id Account ID of client.
	 * @return EnumerateCharacterId packet.
	 */
	private EnumerateCharacterId getEnumerateCharacterId(int id) {
		
		ResultSet resultSet;
		EnumerateCharacterId enumerateCharacterId = new EnumerateCharacterId();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = databaseConnection1.preparedStatement("SELECT * FROM characters WHERE \"accountId\"=" + id + "");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next() && !resultSet.isClosed()) {
				
				String characterName = resultSet.getString("firstName").replaceAll("\\s",""); ;
				String lastName = resultSet.getString("lastName").replaceAll("\\s","");
				if (lastName != null && lastName.length() > 0) characterName += " " + lastName;
				enumerateCharacterId.addCharacter(
						characterName,
						resultSet.getInt("appearance"),
						resultSet.getLong("Id"),
						resultSet.getInt("galaxyId"),
						resultSet.getInt("statusId"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enumerateCharacterId;
	}
	
	/**
	 * Generates LoginEnumCluster packet.
	 * @return LoginEnumCluster packet.
	 */
	public LoginEnumCluster getLoginCluster() {
		LoginEnumCluster servers = new LoginEnumCluster(9);
		PreparedStatement preparedStatement;
		try {
			preparedStatement = databaseConnection1.preparedStatement("SELECT * FROM galaxies");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next() && !resultSet.isClosed())
				servers.addServer(resultSet.getInt("id"), resultSet.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return servers;
	}
	
	/**
	 * Generates LoginClusterStatus packet.
	 * @return LoginClusterStatus packet.
	 */
	public LoginClusterStatus getLoginClusterStatus(Client client) {
		LoginClusterStatus clusterStatus = new LoginClusterStatus();
		ResultSet resultSet;
		try {
			PreparedStatement preparedStatement	= databaseConnection1.preparedStatement("SELECT * FROM \"connectionServers\"");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next() && !resultSet.isClosed())
				clusterStatus.addServer(
						resultSet.getInt("id"),
						resultSet.getString("address"),
						resultSet.getInt("port"),
						resultSet.getInt("pingPort"),
						100,
						resultSet.getInt("statusId"),
						1,
						core.getActiveZoneClients(),
						client);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clusterStatus;
	}
	
	/**
	 * Checks if User has correct client version.
	 * @param version Client Version String
	 */
	private boolean checkVersion(String version) {
		System.out.println("Version Received: " + version);
		return true;
	}

}
	
