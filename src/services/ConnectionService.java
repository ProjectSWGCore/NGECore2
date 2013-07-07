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
import java.util.concurrent.ScheduledExecutorService;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.AccountFeatureBits;
import protocol.swg.ClientIdMsg;
import protocol.swg.ClientPermissionsMessage;
import protocol.swg.ConnectionServerLagResponse;
import protocol.swg.GalaxyLoopTimesResponse;
import protocol.swg.GameServerLagResponse;
import protocol.swg.LoginClientId;

import engine.clients.Client;
import engine.resources.common.Utilities;
import engine.resources.database.DatabaseConnection;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.common.*;



public class ConnectionService implements INetworkDispatch {

	private NGECore core;
	private DatabaseConnection databaseConnection;
	@SuppressWarnings("unused")
	private DatabaseConnection databaseConnection2;

	public ConnectionService(NGECore core) {

		this.core = core;
		this.databaseConnection = core.getDatabase1();
		this.databaseConnection2 = core.getDatabase2();
	
	}
	
	@Override
	public void insertOpcodes(Map<Integer,INetworkRemoteEvent> swgOpcodes, Map<Integer,INetworkRemoteEvent> objControllerOpcodes) {

		swgOpcodes.put(Opcodes.ClientIdMsg, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				ClientIdMsg clientIdMsg = new ClientIdMsg();
				data.position(0);
				clientIdMsg.deserialize(data);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				if(client == null) System.out.println("NULL Client");
				client.setSession(session);
                ResultSet resultSet;
	            PreparedStatement preparedStatement;
				System.out.println(clientIdMsg.getSessionKey().length);

	            try {
	
		            preparedStatement = databaseConnection.preparedStatement("SELECT * FROM sessions WHERE key=?");
		            preparedStatement.setBytes(1, clientIdMsg.getSessionKey());
		            resultSet = preparedStatement.executeQuery();
		            
		            if (resultSet.next()) {
		            	client.setAccountId(resultSet.getInt("accountId"));
		            	AccountFeatureBits accountFeatureBits = new AccountFeatureBits();
		            	ClientPermissionsMessage clientPermissionsMessage = new ClientPermissionsMessage();
		            	session.write(accountFeatureBits.serialize());
		            	session.write(clientPermissionsMessage.serialize());
		                preparedStatement.close();
		                
		            }
	                
	            } catch (SQLException e) {
	
	                e.printStackTrace();
	
	            } 
				
			}
			
			
			
		});
		
		swgOpcodes.put(Opcodes.RequestGalaxyLoopTimes, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {

				GalaxyLoopTimesResponse response = new GalaxyLoopTimesResponse(0);
				session.write(response.serialize());
				
			}
			
		});

		swgOpcodes.put(Opcodes.LagRequest, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {

				ConnectionServerLagResponse connectionReponse = new ConnectionServerLagResponse();
				session.write(connectionReponse.serialize());
				GameServerLagResponse gameReponse = new GameServerLagResponse();
				session.write(gameReponse.serialize());

			}
			
		});

		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
