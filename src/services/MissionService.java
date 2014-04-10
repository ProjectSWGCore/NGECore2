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
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.objectControllerObjects.MissionListRequest;
import resources.common.Console;
import resources.common.TerminalType;
import resources.common.ObjControllerOpcodes;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.tangible.TangibleObject;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class MissionService implements INetworkDispatch {

	private NGECore core;
	
	public MissionService(NGECore core) {
		this.core = core;
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		objControllerOpcodes.put(ObjControllerOpcodes.MISSION_LIST_REQUEST, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				MissionListRequest request = new MissionListRequest();
				request.deserialize(data);
				
				Client client = core.getClient(session);

				if(client == null || client.getSession() == null)
					return;

				SWGObject object = client.getParent();

				if(object == null)
					return;
				
				SWGObject terminal = core.objectService.getObject(request.getTerminalId());
				
				int terminalType = (int) terminal.getAttachment("terminalType");
				
				if (terminalType == TerminalType.MISSION_GENERIC) {
					populateMissions(core.objectService.getObject(request.getObjectId()), TerminalType.MISSION_GENERIC);
				} else if (terminalType == TerminalType.MISSION_BOUNTYHUNTER) {

				} else if (terminalType == TerminalType.MISSION_ENTERTAINER) {

				} else if (terminalType == TerminalType.MISSION_ARTISAN) {

				} else {
					Console.println("ERROR: Unsupported terminal " + terminal.getObjectId());
				}
			}
			
		});
	}
	
	private void populateMissions(final SWGObject player, int type) {
		CreatureObject creature = (CreatureObject) player;
		TangibleObject missionBag = (TangibleObject) creature.getSlottedObject("mission_bag");
		
		final AtomicInteger deliveryCount = new AtomicInteger();
		final AtomicInteger destroyCount = new AtomicInteger();

		final int deliveryEntries = 30;
		
		final Random ran = new Random();
		
		final int bagSize = core.objectService.objsInContainer(creature, missionBag);
		Console.println("Delivery Entries: " + deliveryEntries);
		if (bagSize != 12) {
			for(int missionsAdded = 0; missionsAdded < 12; missionsAdded++) {
				MissionObject mission = (MissionObject) core.objectService.createObject("object/mission/shared_mission_object.iff", creature.getPlanet());
				
				int textId = ran.nextInt(deliveryEntries);
				
				Console.println("Random Int Value: " + textId);
				Console.println("Now the value is: " + textId);
				
				mission.setMissionLevel(5);
				
				mission.setMissionStart(0, 0, 0, player.getPlanet().name);
				
				mission.setMissionCreator("Waverunner");
				mission.setMissionCredits(9000);
				
				// TODO: Base this off of textId + f
				mission.setTargetTemplateObject("object/tangible/mission/shared_mission_datadisk.iff");
				// TODO: Base this off of textId + l
				mission.setMissionTargetName("Osskscosco");
				
				mission.setMissionDescription("mission/mission_deliver_neutral_easy", "m" + textId + "d");
				mission.setMissionTitle("mission/mission_deliver_neutral_easy", "m" + textId + "t");
				
				mission.setMissionType("deliver");
				
				mission.setMissionDestination(-5962, 0, -6259, player.getPlanet().name);
				deliveryCount.getAndIncrement();
				
				
				missionBag._add(mission);
				Console.println("Added mission " + missionsAdded);
			}
			return;
		}
		
		missionBag.viewChildren(player, false, false, new Traverser() {

			@Override
			public void process(SWGObject child) {
				if (deliveryCount.get() == 6)
					return;
				
				MissionObject mission = (MissionObject) child;
				int textId = ran.nextInt(deliveryEntries);
				
				Console.println("Random Int Value: " + textId);
				Console.println("Now the value is: " + textId);
				
				mission.setMissionLevel(5);
				
				mission.setMissionStart(0, 0, 0, player.getPlanet().name);
				
				mission.setMissionCreator("Waverunner");
				mission.setMissionCredits(9000);
				
				// TODO: Base this off of textId + f
				mission.setTargetTemplateObject("object/tangible/mission/shared_mission_datadisk.iff");
				// TODO: Base this off of textId + l
				mission.setMissionTargetName("Osskscosco");
				
				mission.setMissionDescription("mission/mission_deliver_neutral_easy", "m" + textId + "d");
				mission.setMissionTitle("mission/mission_deliver_neutral_easy", "m" + textId + "t");
				
				mission.setMissionType("deliver");
				
				mission.setMissionDestination(-5962, 0, -6259, player.getPlanet().name);
				deliveryCount.getAndIncrement();
			}
			
		});
		
		Console.println("Outside of the traverser now!");
	}
	
	// May want to create a map for the server with needed info
	public int getNumberOfEntries(String stf) {
		try {
			DatatableVisitor stfFile = ClientFileManager.loadFile(stf, DatatableVisitor.class);

			for (int s = 0; s < stfFile.getRowCount(); s++) {
				String name = ((String) stfFile.getObject(s, 3));
				Console.println("Name: " + name);
				if (name.equals("number_of_entries")) {
					int entryCount = ((int) stfFile.getObject(s, 4));
					Console.println("Entry Count: " + entryCount);
					return entryCount;
				}
			}
		}
		catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public void shutdown() {

	}

}
