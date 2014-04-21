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
package services.mission;

import java.io.File;
import java.io.IOException;
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
import resources.objectives.DeliveryMissionObjective;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.waypoint.WaypointObject;
import engine.clientdata.ClientFileManager;
import engine.clientdata.StfTable;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.common.NameGen;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class MissionService implements INetworkDispatch {

	private NGECore core;
	private NameGen nameGenerator;
	// Use a HashMap for obtaining number of entries so we aren't using a visitor all the time.
	private ConcurrentHashMap<String, Integer> entryCounts = new ConcurrentHashMap<String, Integer>();
	private Random ran = new Random();
	
	public MissionService(NGECore core) {
		this.core = core;
		
		try { nameGenerator = new NameGen("names.txt"); } 
		catch (IOException e) { e.printStackTrace(); }
		
		populateMissionEntryCount();
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

		debugMissionDelta(creature);
	}
	
	private void populateMissionEntryCount() {
		File missionFolder = new File("./clientdata/string/en/mission");
		File[] missionFiles = missionFolder.listFiles();
		
		int missionStrings = 0;
		for (int i = 0; i < missionFiles.length; i++) {
			if (missionFiles[i].getName().contains("_easy") || missionFiles[i].getName().contains("_medium") || missionFiles[i].getName().contains("_hard")) {

				String missionStf = "clientdata" + missionFiles[i].getAbsolutePath().split("clientdata")[1].replace("\\", "/");
				try {
					StfTable stf = new StfTable(missionStf);
					boolean gotCount = false;

					for (int s = 1; s < stf.getRowCount(); s++) {

						if (stf.getStringById(s).getKey() != null && stf.getStringById(s).getKey().equals("number_of_entries")) {
							String finalStr = missionStf.split("clientdata/string/en/")[1].replace(".stf", "");
							entryCounts.put(finalStr, Integer.parseInt(stf.getStringById(s).getValue()));
							gotCount = true;
						}
					}
					if (gotCount == false) {
						System.out.println("!NO ENTRY COUNT FOR " + missionStf);
						continue;
					}
				} catch (Exception e) { e.printStackTrace(); }
				missionStrings++;
			}
		}
		System.out.println("Loaded " + missionStrings + " mission entry counts.");
	}
	
	public void debugMissionDelta(CreatureObject creature) {
		TangibleObject missionBag = (TangibleObject) creature.getSlottedObject("mission_bag");
		
		final AtomicInteger destroyCount = new AtomicInteger();
		final AtomicInteger deliverCount = new AtomicInteger();
		
		missionBag.viewChildren(creature, true, false, new Traverser() {

			@Override
			public void process(SWGObject child) {
				
				if (child instanceof MissionObject == false)
					return;

				MissionObject mission = (MissionObject) child;
				if (destroyCount.get() < 5) {
					mission.setMissionType("destroy");
					
					String missionStf = "mission/mission_destroy_neutral_easy_creature";
					int strId = getRandomStringEntry(missionStf);
					
					mission.setMissionDescription(missionStf, "m" + String.valueOf(strId) + "d");
					mission.setMissionTitle(missionStf, "m" + String.valueOf(strId) + "t");
					
					destroyCount.getAndIncrement();
				}
				else if (deliverCount.get() < 5) {
					mission.setMissionType("deliver");
					
					String missionStf = "mission/mission_deliver_neutral_easy";
					int strId = getRandomStringEntry(missionStf);
					
					mission.setMissionDescription(missionStf, "m" + String.valueOf(strId) + "d");
					mission.setMissionTitle(missionStf, "m" + String.valueOf(strId) + "t");
					
					deliverCount.getAndIncrement();
				} 
				else { return; }

				mission.setCreator(nameGenerator.compose(2) + " " + nameGenerator.compose(3));
				
				mission.setMissionLevel(1);
				mission.setMissionStart((float) 3377.148, (float) -4753.221, (float) 0, "tatooine");
				mission.setCreditReward(69);
				mission.setMissionDestination((float) 3447.129, (float) -5070.057, (float) 0, "tatooine");
				mission.setMissionTemplateObject(CRC.StringtoCRC("object/tangible/mission/shared_mission_datadisk.iff"));
				mission.setRepeatCount(1);
				
			}
			
		});
	}
	
	private int getRandomStringEntry(String missionStf) {
		int ranEntryNum = 1;
		
		if (entryCounts.get(missionStf) != null) { ranEntryNum = ran.nextInt(entryCounts.get(missionStf)); } 
		else { System.out.println(missionStf + " was not found in entryCount and is using entry #1"); }
		
		if (ranEntryNum == 0)
			return 1;
		else
			return ranEntryNum;
	}
	
	private void randomDeliveryMission(MissionObject mission) {
		mission.setMissionType("deliver");
		
		String missionStf = "mission/mission_deliver_neutral_easy";
		int strId = getRandomStringEntry(missionStf);
		
		mission.setMissionDescription(missionStf, "m" + String.valueOf(strId) + "d");
		mission.setMissionTitle(missionStf, "m" + String.valueOf(strId) + "t");
		
		//mission.setObjective(new DeliveryMissionObjective(mission));
	}
	
	@Override
	public void shutdown() {

	}

}
