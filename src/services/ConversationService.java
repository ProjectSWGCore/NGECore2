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
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.python.core.Py;
import org.python.core.PyObject;

import protocol.swg.ObjControllerMessage;
import protocol.swg.objectControllerObjects.NpcConversationMessage;
import protocol.swg.objectControllerObjects.NpcConversationOptions;
import protocol.swg.objectControllerObjects.StartNpcConversation;
import protocol.swg.objectControllerObjects.StopNpcConversation;
import resources.common.ConversationOption;
import resources.common.OutOfBand;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import main.NGECore;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class ConversationService implements INetworkDispatch {
	
	private NGECore core;
	private Map<CreatureObject, PyObject> conversationHandlers = new ConcurrentHashMap<CreatureObject, PyObject>();

	public ConversationService(NGECore core) {
		this.core = core;
	}
	
	public void handleStartConversation(CreatureObject player, TangibleObject npc) {
		if(npc.getAttachment("conversationFile") == null)
			return;
		
		if(player.getConversingNpc() != null && player.getConversingNpc() != npc)
			handleEndConversation(player, player.getConversingNpc());
		
		player.setConversingNpc(npc);
		sendStartConversation(player, npc);
		core.scriptService.callScript("scripts/conversation/", (String) npc.getAttachment("conversationFile"), "startConversation", core, player, npc);
	}
	
	public void handleConversationSelection(CreatureObject player, int selectionId) {
		
		TangibleObject npc = player.getConversingNpc();
		
		if(npc == null) {
			System.out.println("npc is null");			
			return;
		}
		
		PyObject func = conversationHandlers.get(player);
		
		if(func == null) {
			System.out.println("handler func is null");
			return;
		}
		
		conversationHandlers.remove(player);
		func.__call__(Py.java2py(core), Py.java2py(player), Py.java2py(npc), Py.java2py(selectionId));
		
	}
	
	public void handleEndConversation(CreatureObject player, TangibleObject npc) {
		
		conversationHandlers.remove(player);
		player.setConversingNpc(null);
		core.scriptService.callScript("scripts/conversation/", (String) npc.getAttachment("conversationFile"), "endConversation", core, player, npc);
		
	}
	
	public void sendStartConversation(CreatureObject player, TangibleObject npc) {
		if(player.getClient() == null)
			return;
		
		StartNpcConversation startConvo = new StartNpcConversation(player.getObjectID(), npc.getObjectID());
		ObjControllerMessage objController = new ObjControllerMessage(0x0B, startConvo);
		player.getClient().getSession().write(objController.serialize());
	}
	
	public void sendStopConversation(CreatureObject player, TangibleObject npc, String stfFile, String stfLabel) {
		if(player.getClient() == null)
			return;
		
		StopNpcConversation stopConvo = new StopNpcConversation(player.getObjectID(), npc.getObjectID(), stfFile, stfLabel);
		ObjControllerMessage objController = new ObjControllerMessage(0x0B, stopConvo);
		player.getClient().getSession().write(objController.serialize());
	}

	public void sendConversationMessage(CreatureObject player, TangibleObject npc, OutOfBand outOfBand) {
		if(player.getClient() == null)
			return;
		
		NpcConversationMessage convoMessage = new NpcConversationMessage(player.getObjectID(), outOfBand);
		ObjControllerMessage objController = new ObjControllerMessage(0x0B, convoMessage);
		player.getClient().getSession().write(objController.serialize());
	}
	
	public void sendConversationOptions(CreatureObject player, TangibleObject npc, Vector<ConversationOption> options, PyObject handler) {
		if(player.getClient() == null)
			return;
		
		conversationHandlers.put(player, handler);
		
		NpcConversationOptions convoOptions = new NpcConversationOptions(player.getObjectID(), npc.getObjectID());
		options.forEach(convoOptions::addOption);
		ObjControllerMessage objController = new ObjControllerMessage(0x0B, convoOptions);
		player.getClient().getSession().write(objController.serialize());
	}


	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0,
			Map<Integer, INetworkRemoteEvent> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	

}
