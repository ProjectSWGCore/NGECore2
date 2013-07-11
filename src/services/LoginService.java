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
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import engine.clients.Client;
import engine.protocol.soe.Disconnect;
import engine.resources.database.DatabaseConnection;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

import protocol.swg.CharacterCreationDisabled;
import protocol.swg.ClientUIErrorMessage;
import protocol.swg.DeleteCharacterMessage;
import protocol.swg.DeleteCharacterReplyMessage;
import protocol.swg.EnumerateCharacterId;
import protocol.swg.LoginClientId;
import protocol.swg.LoginClientToken;
import protocol.swg.LoginClusterStatus;
import protocol.swg.LoginEnumCluster;
import protocol.swg.ServerNowEpochTime;
import protocol.swg.StationIdHasJediSlot;
import resources.common.*;

import resources.objects.creature.CreatureObject;

@SuppressWarnings("unused")

public class LoginService implements INetworkDispatch{
	
	private int sessionKeyLength = 0;
	private NGECore core;
	private DatabaseConnection databaseConnection;
	private DatabaseConnection databaseConnection2;
	private Random random;
	
	public LoginService(NGECore core) {
		this.core = core;
		this.sessionKeyLength = core.getConfig().getInt("LOGIN.SESSION_KEY_SIZE");
		this.databaseConnection = core.getDatabase1();
		this.databaseConnection2 = core.getDatabase2();
		this.random = new Random();
	}
	
	public void insertTimedEventBindings(ScheduledExecutorService executor) {

	}
	
	public void insertOpcodes(Map<Integer,INetworkRemoteEvent> swgOpcodes, Map<Integer,INetworkRemoteEvent> objControllerOpcodes) {

		swgOpcodes.put(Opcodes.LoginClientId, new INetworkRemoteEvent() {

			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				LoginClientId clientID = new LoginClientId();
				data.position(0);
				clientID.deserialize(data);
				
				String err         = "";
				String user        = clientID.getAccountName();
				String pass        = clientID.getPassword();
				int id             = getAccountID(user);
				if (id == -1)  err = "Invalid Username";
				//String email       = getAccountEmail(user);
				//String encryptPass = getAccountPassword(user);
				
				if (err == "")
					err = canUserLogin(user, /*email*/"", pass, /*encryptPass*/"", id, session.getRemoteAddress().toString());
				
				if (err != "") {
					System.out.println(user + " failed login because " + err);
					ClientUIErrorMessage errMsg = new ClientUIErrorMessage("Invalid Login", err);
					session.write(errMsg.serialize());
					Disconnect disconnect = new Disconnect((Integer)session.getAttribute("connectionId"), 6);
					session.write(disconnect);
					session.close(false);
					return;
				}
				
				System.out.println(user + " successfully logged in.");
				
				Client client = new Client(session.getRemoteAddress());
				client.setAccountName(user);
				client.setPassword(pass);
				client.setSessionKey(generateSessionKey());
				client.setAccountId(id);
				//client.setAccountEmail(email);
				client.setSession(session);
				
				core.addClient((Integer)session.getAttribute("connectionId"), client);
				
				/*if(!checkIfAccountExistInGameDB(id)) {
					createAccountForGameDB(id, user, email, encryptPass);
				}*/
				
				persistSession(client);

				LoginEnumCluster servers = getLoginCluster();
				LoginClusterStatus serverStatus = getLoginClusterStatus();
				EnumerateCharacterId characters = getEnumerateCharacterId(id);

				LoginClientToken clientToken = new LoginClientToken(client.getSessionKey(), id, user);
				CharacterCreationDisabled charCreationDisabled = new CharacterCreationDisabled(0);
				StationIdHasJediSlot jediSlot = new StationIdHasJediSlot(false);
				ServerNowEpochTime time = new ServerNowEpochTime((int) (System.currentTimeMillis() / 1000));
				
				session.write(time.serialize());
				session.write(clientToken.serialize());
				session.write(servers.serialize());
				session.write(charCreationDisabled.serialize());
				session.write(serverStatus.serialize());
				session.write(jediSlot.serialize());
				session.write(characters.serialize());
			}

		});
		
		swgOpcodes.put(Opcodes.DeleteCharacterMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);

				DeleteCharacterMessage packet = new DeleteCharacterMessage();
				packet.deserialize(data);
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				
                PreparedStatement preparedStatement;
	               
	            preparedStatement = databaseConnection.preparedStatement("DELETE FROM characters WHERE \"id\"=? AND \"galaxyId\"=? AND \"accountId\"=?");
	            preparedStatement.setLong(1, packet.getcharId());
	            preparedStatement.setInt(2, packet.getgalaxyId());
	            preparedStatement.setInt(3, (int) client.getAccountId());
	            boolean resultSet = preparedStatement.execute();   
	            //TODO: send deletecharacter failed
	            if(!resultSet) {
	            	core.getCreatureODB().delete(new Long(packet.getcharId()), Long.class, CreatureObject.class);
		            DeleteCharacterReplyMessage reply = new DeleteCharacterReplyMessage(0);          
					session.write(reply.serialize());
	            } 
	            preparedStatement.close();
	            
			}
			
		});

	}
	
	public void shutdown() {
		
	}
	
	/**
	 * Saves session data to DB so Zone Server can link sessions to accounts.
	 * @param client Client that needs a session save.
	 */
	private void persistSession(Client client) throws SQLException {

	    String sessionValues = "?, " + client.getAccountId();
	    PreparedStatement ps = databaseConnection.preparedStatement("INSERT INTO sessions (key, \"accountId\") VALUES (" + sessionValues + ")");
	    ps.setBytes(1, client.getSessionKey());
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
			preparedStatement = databaseConnection.preparedStatement("SELECT * FROM characters WHERE \"accountId\"=" + id + "");
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
			preparedStatement = databaseConnection.preparedStatement("SELECT * FROM galaxies");
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
	public LoginClusterStatus getLoginClusterStatus() {
		LoginClusterStatus clusterStatus = new LoginClusterStatus();
		ResultSet resultSet;
		try {
			PreparedStatement preparedStatement	= databaseConnection.preparedStatement("SELECT * FROM \"connectionServers\"");
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
						core.getActiveZoneClients());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clusterStatus;
	}
	
	/**
	 * Checks if account ID exists in game DB.
	 * @param id Account ID.
	 */
	private boolean checkIfAccountExistInGameDB(int id) {
		try {
			PreparedStatement preparedStatement = databaseConnection.preparedStatement("SELECT * FROM accounts WHERE id=?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Saves forum account to game DB if the account does not exist in the game DB already.
	 * @param id Account ID.
	 * @param user Username
	 * @param email User's email
	 * @param pass Hashed password
	 */
	private void createAccountForGameDB(int id, String user, String email, String pass) {
		try {
			PreparedStatement ps = databaseConnection.preparedStatement("INSERT INTO accounts (id, \"user\", \"pass\", \"email\") VALUES (?, ?, ?, ?)");
			ps.setInt(1, id);
			ps.setString(2, user);
			ps.setString(3, pass);
			ps.setString(4, email);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if user is allowed to login.
	 * @param id Account ID.
	 * @param user Username
	 * @param email User's email
	 * @param pass User password
	 * @param encryptPass encrypted password
	 * @param ip Users IP address 
	*/
	private String canUserLogin(String user, String email, String pass, String encryptPass, int id, String ip) {
		/*if ((new PHPBB3Auth()).phpbb_check_hash(pass, encryptPass)) {
			if(checkBanlistforUserAndIP(id, ip)) {
				return "You cannot login to the ProjectSWG server while being banned from ProjectSWG services";
			}
			return "";
		} else {
			return "Invalid Password";
		}*/
		
		try {
			PreparedStatement ps = databaseConnection.preparedStatement("SELECT * FROM accounts WHERE id=" + id + " AND \"pass\"='" + pass + "'");
			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
							
				return "";
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Invalid Password";

		
	}
	/**
	 * Checks if User has a forum account or IP ban.
	 * @param id Account ID.
	 * @param ip User's IP address.
	 */
	private boolean checkBanlistforUserAndIP(int id, String ip) {
		if (!ip.contains("/"))
			return false;
		String IP = ((ip.split("/", 2)[1]).split(", ", 2)[0]).split(":", 2)[0];
		try {
			PreparedStatement preparedStatement = databaseConnection2.preparedStatement("SELECT ban_id FROM phpbb_banlist WHERE ban_ip=?");
			preparedStatement.setString(1, IP);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				preparedStatement.close();
				preparedStatement = databaseConnection2.preparedStatement("SELECT ban_id FROM phpbb_banlist WHERE ban_userid=?");
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checks if User has correct client version.
	 * @param version Client Version String
	 */
	private boolean checkVersion(String version) {
		System.out.println("Version Received: " + version);
		return true;
	}
	
	/**
	 * Gets Account ID from forum DB by username.
	 * @param user Username.
	 */
	private int getAccountID(String user) {
		try {
			PreparedStatement pStatement = databaseConnection.preparedStatement("SELECT id FROM accounts WHERE \"user\"='" + user + "'");
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return -1;
	}
	
	/**
	 * Gets Account Email from forum DB by username.
	 * @param user Username.
	 */
	private String getAccountEmail(String user) {
		try {
			PreparedStatement pStatement = databaseConnection2.preparedStatement("SELECT user_email FROM phpbb_users WHERE username=?");
			pStatement.setString(1, user);
			ResultSet resultSet = pStatement.executeQuery();
			
			if (resultSet.next()) {
				return resultSet.getString("user_email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return "";
	}
	
	/**
	 * Gets hashed Account Password from forum DB by username.
	 * @param user Username.
	 */
	private String getAccountPassword(String user) {
		try {
			PreparedStatement pStatement = databaseConnection2.preparedStatement("SELECT user_password FROM phpbb_users WHERE username=?");
			pStatement.setString(1, user);
			ResultSet resultSet = pStatement.executeQuery();
			
			if (resultSet.next()) {
				return resultSet.getString("user_password");
			}
			pStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return "";
	}
	
}
