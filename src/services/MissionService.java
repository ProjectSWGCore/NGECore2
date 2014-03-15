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
import java.util.concurrent.ConcurrentHashMap;
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
import resources.objects.waypoint.WaypointObject;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
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
	
	@SuppressWarnings("unused")
	private void populateMissions(final SWGObject player, int type) {
		CreatureObject creature = (CreatureObject) player;
		TangibleObject missionBag = (TangibleObject) creature.getSlottedObject("mission_bag");
		
		final AtomicInteger deCount = new AtomicInteger();
		final AtomicInteger dCount = new AtomicInteger();
		//createDefaultMissions(creature);
		missionBag.viewChildren(creature, true, false, new Traverser() {

			@Override
			public void process(SWGObject child) {
				MissionObject mission = (MissionObject) child;
				if (deCount.get() < 5) {
					mission.setMissionType("destroy");
					mission.setMissionStart(200, 0, 200, "tatooine");
					deCount.getAndIncrement();
					Console.println("Destroy missions: " + String.valueOf(deCount.get()));
				}
				else if (dCount.get() < 5) {
					mission.setMissionType("deliver");
					mission.setMissionStart(0, 0, 0, "tatooine");
					dCount.getAndIncrement();
					Console.println("Deliver missions: " + String.valueOf(dCount.get()));
				} else {
					return;
				}
				mission.setCreditReward(1337);
				mission.setCreator("Waverunner");
				mission.setMissionLevel(5);
				mission.setMissionDescription("mission/mission_destroy_neutral_hard_creature_tatooine", "m17d");
				mission.setMissionTitle("mission/mission_destroy_neutral_hard_creature_tatooine", "m17t");
				mission.setMissionDestination(400, 0, 400, "tatooine");
				mission.setMissionTemplateObject(CRC.StringtoCRC("object/tangible/lair/base/shared_objective_power_generator.iff"));
				mission.setMissionTargetName("@lair_n:tatooine_giant_worrt_lair_neutral_medium");
				mission.setRepeatCount(1);
				/*WaypointObject wp = new WaypointObject();
				wp.setName("@mission/mission_destroy_neutral_hard_creature_tatooine:m17t");
				wp.setColor(WaypointObject.ORANGE);
				wp.setPosition(new Point3D(10, 10, 10));
				wp.setCellId(0);
				wp.setPlanet(core.terrainService.getPlanetByName("tatooine"));*/
				mission.sendMissionDelta();
				
			}
			
		});
		
		//Console.println("Sent deltas");
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
	
	public void createDefaultMissions(CreatureObject player) {
		for(int missionsAdded = 0; missionsAdded <= 10; missionsAdded++) {
			MissionObject mission = (MissionObject) core.objectService.createObject("object/mission/shared_mission_object.iff", player.getPlanet());
			
			mission.setMissionLevel(0);
			mission.setMissionStart(0, 0, 0, null);
			mission.setCreator("");
			mission.setCreditReward(0);
			
			mission.setMissionDestination(0, 0, 0, null);
			
			//mission.setMissionTemplateObject(missionTemplateObject);
			mission.setMissionDescription("", "");
			mission.setMissionTitle("", "");
			
			mission.setMissionType("destroy"); // 0x19C9FAC1
			
			mission.setMissionTargetName("");

			TangibleObject missionBag = (TangibleObject) player.getSlottedObject("mission_bag");
			missionBag._add(mission);

			Console.println("Added mission " + missionsAdded);
		}
		Console.println("Successfully created default missions");
	}
	
	@Override
	public void shutdown() {

	}

}
