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
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.ClientIdMsg;
import protocol.swg.ExpertiseRequestMessage;
import protocol.swg.ServerTimeMessage;
import resources.common.FileUtilities;
import resources.common.Opcodes;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;

import main.NGECore;

import engine.clients.Client;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

@SuppressWarnings("unused")

public class PlayerService implements INetworkDispatch {
	
	private NGECore core;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

	public PlayerService(final NGECore core) {
		this.core = core;
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				ServerTimeMessage time = new ServerTimeMessage(System.currentTimeMillis() / 1000);
				IoBuffer packet = time.serialize();
				synchronized(core.getActiveConnectionsMap()) {
					for(Client c : core.getActiveConnectionsMap().values()) {
						if(c.getParent() != null) {
							c.getSession().write(packet);
						}
					}
				}
			}
			
		}, 0, 30, TimeUnit.SECONDS);
	}
	
	public void postZoneIn(final CreatureObject creature) {
		
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				
				PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
				player.setTotalPlayTime(player.getTotalPlayTime() + 30);
				player.setLastPlayTimeUpdate(System.currentTimeMillis());
				
			}
			
		}, 30, 30, TimeUnit.SECONDS);
		
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				
				if(creature.getAction() < creature.getMaxAction())
					creature.setAction(creature.getAction() + 200);
				
			}
			
		}, 0, 1, TimeUnit.SECONDS);

		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				
				if(creature.getHealth() < creature.getMaxHealth() && creature.getCombatFlag() == 0)
					creature.setHealth(creature.getHealth() + 300);
				
			}
			
		}, 0, 1, TimeUnit.SECONDS);

	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		swgOpcodes.put(Opcodes.CmdSceneReady, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
				
			}
			
		});
		
		swgOpcodes.put(Opcodes.ExpertiseRequestMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
				buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);
				buffer.position(0);
				
				ExpertiseRequestMessage expertise = new ExpertiseRequestMessage();
				expertise.deserialize(buffer);

				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				if(client == null) {
					System.out.println("NULL Client");
					return;
				}

				if(client.getParent() == null)
					return;
				
				CreatureObject creature = (CreatureObject) client.getParent();
				
				for(String expertiseName : expertise.getExpertiseSkills()) {
					handleExpertiseSkillBox(creature, expertiseName);
				}
				
				
			}
			
		});

		
	}
	
	public void handleExpertiseSkillBox(CreatureObject creature, String expertiseBox) {
		
		if(!FileUtilities.doesFileExist("scripts/expertise/" + expertiseBox + ".py"))
			return;
		
		core.scriptService.callScript("scripts/expertise", "addExpertisePoint", expertiseBox, core, creature);
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
}
