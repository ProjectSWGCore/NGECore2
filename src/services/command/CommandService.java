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
package services.command;

import java.nio.ByteOrder;
import java.util.Map;
import java.util.Vector;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.common.*;

import protocol.swg.objectControllerObjects.CommandEnqueue;

import resources.objects.creature.CreatureObject;

public class CommandService implements INetworkDispatch  {
	
	private Vector<BaseSWGCommand> commandLookup = new Vector<BaseSWGCommand>();
	private NGECore core;
	
	public CommandService(NGECore core) {
		this.core = core;
	}


	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		objControllerOpcodes.put(ObjControllerOpcodes.COMMAND_QUEUE_ENQUEUE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));

				if(client == null) {
					System.out.println("NULL Client");
					return;
				}

				CommandEnqueue commandEnqueue = new CommandEnqueue();
				commandEnqueue.deserialize(data);
				
				//System.out.println(commandEnqueue.getCommandArguments());

				
				BaseSWGCommand command = getCommandByCRC(commandEnqueue.getCommandCRC());
				
				if(command == null)
					return;
				
				//if(command.getCommandCRC() == CRC.StringtoCRC("transferitemmisc"))
					//System.out.println(commandEnqueue.getCommandArguments());
				
				// TODO: command filters for state, posture etc.
				
				if(client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				
				CreatureObject actor = (CreatureObject) client.getParent();

				SWGObject target = core.objectService.getObject(commandEnqueue.getTargetID());
				
				//if(target == null)
					//System.out.println("NULL Target");
				
				core.scriptService.callScript("scripts/commands/", command.getCommandName(), "run", core, actor, target, commandEnqueue.getCommandArguments());
				
			}
			
			
		});
			
		
	}
	
	public void registerCommand(String name) {
		
		BaseSWGCommand command = new BaseSWGCommand(name);
		commandLookup.add(command);
	}

	public BaseSWGCommand getCommandByCRC(int CRC) {
		
		Vector<BaseSWGCommand> commands = new Vector<BaseSWGCommand>(commandLookup); 	// copy for thread safety
		
		for(BaseSWGCommand command : commands) {
			if(command.getCommandCRC() == CRC)
				return command;
		}
		return null;

	}
	
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
