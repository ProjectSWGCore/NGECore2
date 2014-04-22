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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.objectControllerObjects.MissionAcceptRequest;
import protocol.swg.objectControllerObjects.MissionAcceptResponse;
import protocol.swg.objectControllerObjects.MissionListRequest;
import resources.common.Console;
import resources.common.SpawnPoint;
import resources.common.ObjControllerOpcodes;
import resources.objectives.DeliveryMissionObjective;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.tangible.TangibleObject;
import engine.clientdata.StfTable;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.common.NameGen;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
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
		
		loadMissionEntryCounts();
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		objControllerOpcodes.put(ObjControllerOpcodes.MISSION_LIST_REQUEST, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				Client client = core.getClient(session);

				if(client == null || client.getSession() == null)
					return;

				SWGObject object = client.getParent();

				if(object == null)
					return;
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				MissionListRequest request = new MissionListRequest();
				request.deserialize(data);
				
				SWGObject terminal = core.objectService.getObject(request.getTerminalId());
				
				if (terminal.getAttachment("terminalType") == null)
					return;

				int terminalType = (int) terminal.getAttachment("terminalType");
				
				if (terminalType == TerminalType.GENERIC) {
					handleMissionListRequest(core.objectService.getObject(request.getObjectId()), request.getTickCount(), TerminalType.GENERIC);
				} else if (terminalType == TerminalType.BOUNTYHUNTER) {

				} else if (terminalType == TerminalType.ENTERTAINER) {

				} else if (terminalType == TerminalType.ARTISAN) {

				} else {
					Console.println("ERROR: Unsupported terminal " + terminal.getObjectId());
				}
			}
			
		});

		objControllerOpcodes.put(ObjControllerOpcodes.MISSION_GENERIC_REQUEST, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				Client client = core.getClient(session);

				if(client == null || client.getSession() == null)
					return;

				SWGObject object = client.getParent();

				if(object == null)
					return;
				
				CreatureObject creature = (CreatureObject) object;
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				MissionAcceptRequest request = new MissionAcceptRequest();
				request.deserialize(data);
				
				MissionObject mission = (MissionObject) core.objectService.getObject(request.getMissionObjId());
				
				if (mission == null)
					return;
				
				if (handleMissionAccept(creature, mission)) {
					//MissionAcceptResponse response = new MissionAcceptResponse();
					//session.write(response.serialize());
				}
			}

		});
	}

	private void loadMissionEntryCounts() {
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
	
	
	private int getRandomStringEntry(String missionStf) {
		int ranEntryNum = 1;
		
		if (entryCounts.get(missionStf) != null) { ranEntryNum = ran.nextInt(entryCounts.get(missionStf)); } 
		else { System.out.println(missionStf + " was not found in entryCount and is using entry #1"); }
		
		if (ranEntryNum == 0)
			return 1;
		else
			return ranEntryNum;
	}

	private void handleMissionListRequest(final SWGObject player, int requestCounter, int type) {
		CreatureObject creature = (CreatureObject) player;
		
		TangibleObject missionBag = (TangibleObject) creature.getSlottedObject("mission_bag");
		
		if (missionBag == null || creature == null)
			return;
		
		final AtomicInteger typeOneCount = new AtomicInteger();
		final AtomicInteger typeTwoCount = new AtomicInteger();
		
		missionBag.viewChildren(creature, true, false, new Traverser() {

			@Override
			public void process(SWGObject obj) {
				
				if (obj instanceof MissionObject == false)
					return;
				
				MissionObject mission = (MissionObject) obj;
				
				if (typeOneCount.get() < 4) {
					
					if (type == TerminalType.GENERIC)
						randomDeliveryMission(player, mission);
					
					else if (type == TerminalType.BOUNTYHUNTER)
						randomBountyMission(player, mission);
					
					else if (type == TerminalType.ARTISAN)
						return;

					else
						return;
					
					mission.setRepeatCount(requestCounter);
					typeOneCount.incrementAndGet();
					
				} else if (typeTwoCount.get() < 4) {
					
					if (type == TerminalType.GENERIC)
						randomDestroyMission(player, mission);
					
					else if (type == TerminalType.BOUNTYHUNTER)
						return;
					
					else if (type == TerminalType.ARTISAN)
						return;
					
					else
						return;
					
					mission.setRepeatCount(requestCounter);
					typeTwoCount.incrementAndGet();
				}
			}
		});
	}
	
	private boolean handleMissionAccept(CreatureObject creature, MissionObject mission) {
		SWGObject missionBag = creature.getSlottedObject("mission_bag");
		
		if (mission.getContainer() != missionBag)
			return false;
		
		TangibleObject datapad = (TangibleObject) creature.getSlottedObject("datapad");
		
		if (datapad == null)
			return false;
		
		AtomicInteger missionCount = new AtomicInteger();
		AtomicBoolean hasBountyMission = new AtomicBoolean(false);
		datapad.viewChildren(creature, true, false, new Traverser() {

			@Override
			public void process(SWGObject obj) {
				if (obj instanceof MissionObject) {
					missionCount.incrementAndGet();
					
					if (((MissionObject) obj).getMissionType().equals("bounty"))
						hasBountyMission.set(true);
				}
			}
		});
		
		if (hasBountyMission.get() && mission.getMissionType().equals("bounty")) // Can only have 1 bounty mission
			return false;
		
		System.out.println("Player has " + missionCount.get() + " missions.");
		
		if (missionCount.get() == 2) {
			creature.sendSystemMessage("@mission/mission_generic:too_many_missions", (byte) 0);
			return false;
		}
		if (missionBag.transferTo(creature, datapad, mission)) {
			createMissionObjective(creature, mission);
			return true;
		} else { return false; }
	}
	
	private void createMissionObjective(CreatureObject creature, MissionObject mission) {
		String type = mission.getMissionType();
		
		switch(type) {

			case "deliver":
				DeliveryMissionObjective objective = new DeliveryMissionObjective(mission);
				
				mission.setObjective(objective);
				
				objective.activate();
				break;
			
			default:
				break;
		}
	}

	private void randomDeliveryMission(SWGObject player, MissionObject mission) {
		mission.setMissionType("deliver");
		
		String[] difficulties = { "easy", "medium", "hard" };

		String missionStf = "mission/mission_deliver_neutral_" + difficulties[ran.nextInt(difficulties.length)];
		int strId = getRandomStringEntry(missionStf);
		
		mission.setMissionDescription(missionStf, "m" + String.valueOf(strId) + "d");
		mission.setMissionTitle(missionStf, "m" + String.valueOf(strId) + "t");
		
		mission.setCreator(nameGenerator.compose(2) + " " + nameGenerator.compose(3));
		
		mission.setMissionLevel(5);
		
		Point3D startLocation = SpawnPoint.getRandomPosition(player.getPosition(), (float) 50, (float) 300, player.getPlanetId());
		Point3D destination = SpawnPoint.getRandomPosition(startLocation, (float) 50, (float) 500, player.getPlanetId());
		
		mission.setStartLocation(startLocation, player.getPlanet().name);
		mission.setDestination(destination, player.getPlanet().name);
		
		mission.setCreditReward((int) (50 * ((player.getWorldPosition().getDistance2D(destination) / 10))));
		
		mission.setMissionTemplateObject(CRC.StringtoCRC("object/tangible/mission/shared_mission_datadisk.iff"));
		mission.setMissionTargetName("Datadisk");
	}
	
	private void randomDestroyMission(SWGObject player, MissionObject mission) {

	}
	
	private void randomBountyMission(SWGObject player, MissionObject mission) {
		
	}
	public enum TerminalType {;
		public static final int GENERIC = 1;
		public static final int BOUNTYHUNTER = 2;
		public static final int ENTERTAINER = 3;
		public static final int ARTISAN  = 4;
		public static final int EXPLORER = 5;
	}
	
	@Override
	public void shutdown() {

	}
}
