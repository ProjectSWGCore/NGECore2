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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.ObjControllerMessage;
import protocol.swg.objectControllerObjects.MissionAbort;
import protocol.swg.objectControllerObjects.MissionAcceptRequest;
import protocol.swg.objectControllerObjects.MissionAcceptResponse;
import protocol.swg.objectControllerObjects.MissionListRequest;
import resources.common.BountyListItem;
import resources.common.ObjControllerOpcodes;
import services.mission.CommonerSpawn;
import resources.objectives.BountyMissionObjective;
import resources.objectives.DeliveryMissionObjective;
import resources.objectives.DestroyMissionObjective;
import resources.objectives.SurveyMissionObjective;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import engine.clientdata.StfTable;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.common.NameGen;
import engine.resources.container.Traverser;
import engine.resources.database.ODBCursor;
import engine.resources.database.ObjectDatabase;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class MissionService implements INetworkDispatch {

	private NGECore core;
	private NameGen nameGenerator;
	// Use a HashMap for obtaining number of entries so we aren't using a visitor all the time.
	private Map<String, Integer> entryCounts = new ConcurrentHashMap<String, Integer>();
	private Map<Long, BountyListItem> bountyMap = new ConcurrentHashMap<Long, BountyListItem>();
	private Map<String, Vector<CommonerSpawn>> commonerMap = new ConcurrentHashMap<String, Vector<CommonerSpawn>>();
	private ObjectDatabase bountiesODB;
	private Random ran = new Random();
	
	public MissionService(NGECore core) {
		this.core = core;
		try { nameGenerator = new NameGen("names.txt"); } 
		catch (IOException e) { e.printStackTrace(); }
		
		loadMissionEntryCounts();
		
		bountiesODB = core.getBountiesODB();
		
		cleanupBounties(); // delete any characters from the bounties odb if they no longer exist.
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		objControllerOpcodes.put(ObjControllerOpcodes.MISSION_ABORT, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				Client client = core.getClient(session);

				if(client == null || client.getSession() == null)
					return;

				CreatureObject creature = (CreatureObject) client.getParent();

				if(creature == null)
					return;
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				MissionAbort abort = new MissionAbort();
				abort.deserialize(data);
				
				MissionObject mission = (MissionObject) core.objectService.getObject(abort.getMissionId());
				
				if (mission == null)
					return;
				
				if (mission.getGrandparent().getObjectId() != creature.getObjectId())
					return;
				
				handleMissionAbort(creature, mission);
			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.MISSION_LIST_REQUEST, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				Client client = core.getClient(session);

				if(client == null || client.getSession() == null)
					return;

				CreatureObject object = (CreatureObject) client.getParent();

				if(object == null)
					return;
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				MissionListRequest request = new MissionListRequest();
				request.deserialize(data);
				
				SWGObject terminal = core.objectService.getObject(request.getTerminalId());
				
				if (terminal.getAttachment("terminalType") == null)
					return;

				int terminalType = (int) terminal.getAttachment("terminalType");
				
				switch(terminalType) {
					case TerminalType.GENERIC:
						handleMissionListRequest(object, request.getTickCount(), TerminalType.GENERIC);
						break;
					
					case TerminalType.BOUNTY:
						if (!object.hasSkill("class_bountyhunter_phase1_novice")) { object.sendSystemMessage("@mission/mission_generic:not_bounty_hunter_terminal", (byte) 0); } 
						else { handleMissionListRequest(object, request.getTickCount(), TerminalType.BOUNTY); }
						break;
					
					case TerminalType.ARTISAN:
						//handleMissionListRequest(object, request.getTickCount(), TerminalType.ARTISAN);
						break;
					
					case TerminalType.ENTERTAINER:
						//handleMissionListRequest(object, request.getTickCount(), TerminalType.ENTERTAINER);
						break;

					default: break;
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
					MissionAcceptResponse response = new MissionAcceptResponse(object.getObjectId(), mission.getObjectId(), request.getTerminalType(), (byte) 1);
					object.getClient().getSession().write(new ObjControllerMessage(0x0B, response).serialize());
				} else {
					MissionAcceptResponse response = new MissionAcceptResponse(object.getObjectId(), mission.getObjectId(), request.getTerminalType(), (byte) 0);
					object.getClient().getSession().write(new ObjControllerMessage(0x0B, response).serialize());
				}
			}
		});
	}

	public void handleMissionAbort(CreatureObject creature, MissionObject mission) {
		handleMissionAbort(creature, mission, false);
	}
	
	/**
	 * Aborts the target mission for the specified player. If silent is true then MissionObjective.abort is not called.
	 * @param creature Target player used in MissionObjective.abort
	 * @param mission Mission to abort
	 * @param silent If true, called the MissionObjective.abort() method, otherwise it simply destroys the mission (false)
	 */
	public void handleMissionAbort(CreatureObject creature, MissionObject mission, boolean silent) {
		
		creature.getPlayerObject().removeActiveMission(mission.getObjectID());
		
		MissionObjective objective = mission.getObjective();
		
		if (objective != null && !silent)
			objective.abort(core, creature);
		
		core.objectService.destroyObject(mission.getObjectId());
	}

	private void loadMissionEntryCounts() {
		File missionFolder = new File("./clientdata/string/en/mission");
		File[] missionFiles = missionFolder.listFiles();
		
		//int missionStrings = 0;
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
						//System.out.println("!NO ENTRY COUNT FOR " + missionStf);
						continue;
					}
				} catch (Exception e) { e.printStackTrace(); }
				//missionStrings++;
			}
		}
		//System.out.println("Loaded " + missionStrings + " mission entry counts.");
	}
	
	
	private int getRandomStringEntry(String missionStf) {
		int ranEntryNum = 1;
		
		if (entryCounts.get(missionStf) != null) { ranEntryNum = ran.nextInt(entryCounts.get(missionStf)); } 
		//else { System.out.println(missionStf + " was not found in entryCount and is using entry #1"); }
		if (missionStf.equals("mission/mission_deliver_neutral_hard") && ranEntryNum == 1) // m1s value is "mission_success_mail", so dont even use the mission
			return 2;
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
				
				if (typeOneCount.get() < 5) {
					
					if (type == TerminalType.GENERIC)
						randomDeliveryMission(player, mission);
					
					else if (type == TerminalType.BOUNTY)
						randomBountyMission(player, mission);
					
					else if (type == TerminalType.ARTISAN)
						return;
						//randomSurveyMission(player, mission);

					else
						return;
					
					mission.incrementRepeatCounter();
					typeOneCount.incrementAndGet();
					
				} else if (typeTwoCount.get() < 5) {
					
					if (type == TerminalType.GENERIC) // destroy
						return;
					
					else if (type == TerminalType.ARTISAN) // crafting
						return;
					
					else if (type == TerminalType.BOUNTY)
						randomBountyMission(player, mission);
					
					else
						return;
					
					mission.incrementRepeatCounter();
					typeTwoCount.incrementAndGet();
				}
			}
		});
	}
	
	public void handleMissionComplete(CreatureObject creature, MissionObject mission) {
		
		creature.getPlayerObject().removeActiveMission(mission.getObjectID());
		
		MissionObjective objective = mission.getObjective();
		
		if (objective == null)
			return;
		
		objective.complete(core, creature);
		
		core.objectService.destroyObject(mission.getObjectId());
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
		
		if (mission.getMissionType().equals("bounty")) {
			if (hasBountyMission.get()) {
				creature.sendSystemMessage("@bounty_hunter:already_have_target", (byte) 0);
				return false;
			}
		}
		
		if (missionCount.get() == 2) {
			creature.sendSystemMessage("@mission/mission_generic:too_many_missions", (byte) 0);
			return false;
		}
		
		if (mission.getPlanetId() != creature.getPlanetId()) {
			mission.setPlanet(creature.getPlanet());
			mission.setPlanetId(creature.getPlanetId());
		}
		
		creature.getPlayerObject().addActiveMission(mission.getObjectID());
		missionBag.transferTo(creature, datapad, mission);
		createMissionObjective(creature, mission);
		return true;
	}
	
	private MissionObjective createMissionObjective(CreatureObject creature, MissionObject mission) {
		return createMissionObjective(creature, mission, false);
	}
	
	private MissionObjective createMissionObjective(CreatureObject creature, MissionObject mission, boolean silent) {
		switch(mission.getMissionType()) {

			case "deliver":
				DeliveryMissionObjective deliveryObjective = new DeliveryMissionObjective(mission);
				
				mission.setObjective(deliveryObjective);
				
				if (!silent)
					deliveryObjective.activate(core, creature);
				
				return deliveryObjective;
			
			case "destroy":
				DestroyMissionObjective destroyObjective = new DestroyMissionObjective(mission);
				
				mission.setObjective(destroyObjective);
				
				if (!silent)
					destroyObjective.activate(core, creature);
				
				return destroyObjective;
			
			case "bounty":
				BountyMissionObjective bountyObjective = new BountyMissionObjective(mission);
				
				mission.setObjective(bountyObjective);
				
				if (!silent)
					bountyObjective.activate(core, creature);
				
				return bountyObjective;
			
			case "survey":
				SurveyMissionObjective surveyObjective = new SurveyMissionObjective(mission);
				
				mission.setObjective(surveyObjective);
				
				if (!silent)
					surveyObjective.activate(core, creature);
				
				return surveyObjective;
			default:
				return null;
		}
	}
	/*private void randomSurveyMission(SWGObject player, MissionObject mission) {
		
		byte generalType = (byte) (ran.nextInt(3) == 0 ? 1 : 0);
		
		// TODO: Change way of getting resources for planet. Possibly a map, would solve future concurrency problems.
		Vector<GalacticResource> resources = core.resourceService.getSpawnedResourcesByPlanetAndType(player.getPlanetId(), generalType); 
		if (resources == null || resources.size() == 0)
			return;
		
		// The level and reward calculations are taken from EMU. Can hardly find anything related to survey missions from NGE.
		int maxLevel = 50;
		int minLevel = 50;
		float surveySkill = core.skillModService.getSkillModValue((CreatureObject) player, "surveying");
		
		if (surveySkill > 30) { maxLevel += 10; }
		if (surveySkill > 50) { maxLevel += 10; }
		if (surveySkill > 70) { maxLevel += 10; }
		if (surveySkill > 90) { maxLevel += 10; }
		if (surveySkill > 100) { maxLevel += 5; } //Max mission level is 95.
		
		int randNumber = (maxLevel == minLevel ? 0 : ran.nextInt(maxLevel - minLevel)); // prevent bound error (can't randomize 0)

		int randLevel = minLevel + (5 * randNumber) / 5;
		
		if (randLevel > maxLevel)
			randLevel = maxLevel;
		
		GalacticResource resource = resources.get(ran.nextInt(resources.size() - 1));
		
		if (resource == null)
			return;
		
		ResourceRoot family = resource.getResourceRoot();

		mission.setMissionType("survey");

		String missionStf = "mission/mission_npc_survey_neutral_easy";

		mission.setMissionId(getRandomStringEntry(missionStf));
		mission.setDescription("@" + missionStf + ":" + "m" + mission.getMissionId() + "d");
		mission.setTitle("@" + missionStf + ":" + "m" + mission.getMissionId() + "t");
		
		mission.setCreator(nameGenerator.compose(2) + " " + nameGenerator.compose(3));
		
		mission.setDifficultyLevel(randLevel);
		
		mission.setStartLocation(player.getPosition(), 0, player.getPlanet().name);
		
		mission.setCreditReward(400 + (randLevel - minLevel) * 20 + ran.nextInt(100));
		
		mission.setTemplateObject(CRC.StringtoCRC(ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[family.getContainerType()])); // This should be the resource container obj
		mission.setTargetName(family.getResourceType() + family.getResourceClass());
		
	}*/
	
	private void randomDeliveryMission(SWGObject player, MissionObject mission) {
		if (!commonerMap.containsKey(player.getPlanet().name))
			return;
		
		Vector<CommonerSpawn> spawns = commonerMap.get(player.getPlanet().name);
		Point3D playerLocation = player.getWorldPosition();
		MissionLocation startLocation = null;
		MissionLocation destinationLocation = null;
		
		Random randomSpawn = new Random(10);
		// TODO: There is probably a much better way of doing this.
		for (CommonerSpawn spawn : spawns) {
			if (randomSpawn.nextInt() >= 5) { 
				continue;
			}
			
			float distance = playerLocation.getDistance2D(spawn.getLocation());
			if (distance <= 500 && startLocation == null) {
				startLocation = new MissionLocation(spawn.getLocation(), spawn.getObjectId(), player.getPlanet().name);
			} else if (distance <= 2500 && destinationLocation == null) {
				destinationLocation = new MissionLocation(spawn.getLocation(), spawn.getObjectId(), player.getPlanet().name);
			}
			
			if (destinationLocation != null && startLocation != null)
				break;
		}
		
		if (startLocation == null || destinationLocation == null)
			return;
		
		mission.setMissionType("deliver");
		
		String[] difficulties = { "easy", "medium", "hard" };

		String missionStf = "mission/mission_deliver_neutral_" + difficulties[ran.nextInt(difficulties.length)];

		mission.setMissionId(getRandomStringEntry(missionStf));
		mission.setDescription("@" + missionStf + ":" + "m" + mission.getMissionId() + "d");
		mission.setTitle("@" + missionStf + ":" + "m" + mission.getMissionId() + "t");
		
		mission.setCreator(nameGenerator.compose(2) + " " + nameGenerator.compose(3));
		
		mission.setDifficultyLevel(5);
		
		mission.setStartLocation(startLocation);
		mission.setDestinationLocation(destinationLocation);
		mission.setCreditReward((int) (200 + ((startLocation.getLocation().getDistance2D(destinationLocation.getLocation()) / 10))));
		
		mission.setTemplateObject(CRC.StringtoCRC("object/tangible/mission/shared_mission_datadisk.iff"));
		mission.setTargetName("Datadisk");

	}
	
	private void randomBountyMission(SWGObject player, MissionObject mission) {
		BountyListItem bountyTarget = null;
		
		if (bountyMap.size() > 0) {
			boolean gotBounty = false;
			int attempts = 0;
			while (!gotBounty && attempts < 5) {
				bountyTarget = getRandomBounty();

				if (bountyTarget == null || bountyTarget.getAssignedHunters().size() >= 3 || bountyTarget.getCreditReward() < 20000 || bountyTarget.getObjectID() == player.getObjectId()) {
					attempts++;
					continue;
				}
				else
					gotBounty = true;
			}
			if (attempts >= 5 && !gotBounty)
				return;
		}
		if (bountyTarget == null)
			return;
		
		mission.setMissionType("bounty");
		
		String missionStf = "mission/mission_bounty_jedi";
		
		if (!bountyTarget.getProfession().equals("")) { // TODO: Smuggler mission checks.
			if (bountyTarget.getFaction().equals("rebel")) {
				mission.setTargetName("Rebel Bounty");
				mission.setMissionId(2);
				mission.setCreator("The Galactic Empire");
			} else if (bountyTarget.getFaction().equals("imperial")) {
				mission.setTargetName("Imperial Bounty");
				mission.setMissionId(1);
				mission.setCreator("The Alliance");
			}
			mission.setTitle("@" + missionStf + ":" + "m" + mission.getMissionId() + "t");
			mission.setDescription("@" + missionStf + ":" + "m" + mission.getMissionId() + "d");
		} else {
			// TODO: Dead code, but place-holder for implementation of smuggler missions.
			if (bountyTarget.getFaction().equals("neutral")) {
				mission.setMissionId(3);
			}
			else if (bountyTarget.getFaction().equals("rebel")) {
				mission.setMissionId(2);
			}
			else if (bountyTarget.getFaction().equals("imperial")) {
				mission.setMissionId(1);
			}
			mission.setTitle("@" + missionStf + ":" + "m" + mission.getMissionId() + "ts");
			mission.setDescription("@" + missionStf + ":" + "m" + mission.getMissionId() + "ds");
		}
		
		mission.setDifficultyLevel(95 + bountyTarget.getFailedAttempts());
		
		mission.setCreditReward(bountyTarget.getCreditReward());
		
		mission.setTemplateObject(CRC.StringtoCRC("object/tangible/mission/shared_mission_bounty_jedi_target.iff"));
		
		mission.setBountyMarkId(bountyTarget.getObjectID());
		
	}
	
	public enum TerminalType {;
		public static final int GENERIC = 1;
		public static final int BOUNTY = 2;
		public static final int ENTERTAINER = 3;
		public static final int ARTISAN  = 4;
		public static final int EXPLORER = 5;
	}
	
	public Map<Long, BountyListItem> getBountyMap() {
		return this.bountyMap;
	}
	
	public BountyListItem getRandomBounty() {
		int bountyListId = ran.nextInt(bountyMap.size());
		List<Long> bounties = new ArrayList<Long>(bountyMap.keySet());

		return bountyMap.get(bounties.get(bountyListId));
	}
	
	public BountyListItem getBountyListItem(long objectId) {
		if (bountyMap.containsKey(objectId))
			return bountyMap.get(objectId);
		else return null;
	}
	
	public BountyListItem createNewBounty(CreatureObject bountyTarget, long placer, int reward) {
		PlayerObject player = (PlayerObject) bountyTarget.getSlottedObject("ghost");
		if (player == null)
			return null;
		
		if (getBountyListItem(bountyTarget.getObjectID()) != null)
			return null;
		
		BountyListItem bounty = new BountyListItem(bountyTarget.getObjectID(), reward, core.playerService.getFormalProfessionName(player.getProfession()), bountyTarget.getFaction(), bountyTarget.getCustomName());
		
		if (placer != 0)
			bounty.getBountyPlacers().add(placer);
		
		bountiesODB.put(bountyTarget.getObjectID(), bounty);
		
		bountyMap.put(bountyTarget.getObjectID(), bounty);

		//System.out.println("Created bounty of " + reward + " to " + bounty.getName());
		return bounty;
	}
	
	public boolean addToExistingBounty(long bountyTarget, long placer, int amountToAdd) {

		BountyListItem bounty = getBountyListItem(bountyTarget);
		
		if (bounty == null)
			return false;
		
		bounty.addBounty(amountToAdd);
		
		if (placer != 0 && !bounty.getBountyPlacers().contains(placer))
			bounty.getBountyPlacers().add(placer);

		bountiesODB.put(bounty.getObjectID(), bounty);
		
		//System.out.println("Added bounty of " + amountToAdd + " to " + bounty.getName());
		return true;
	}
	
	public void addCommoner(SWGObject commoner) {
		CommonerSpawn commonerSpawn = new CommonerSpawn(commoner.getObjectId(), commoner.getPosition());
		if (commonerMap.containsKey(commoner.getPlanet().name))
			commonerMap.get(commoner.getPlanet().name).add(commonerSpawn);
		else {
			commonerMap.put(commoner.getPlanet().name, new Vector<CommonerSpawn>());
			commonerMap.get(commoner.getPlanet().name).add(commonerSpawn);
		}
		
	}
	
	public boolean removeBounty(long bountyTarget, boolean listRemove) {
		if (listRemove)
			bountyMap.remove(bountyTarget);
		bountiesODB.remove(bountyTarget);
		return true;
	}
	
	public boolean removeBounty(long bountyTarget) {
		return removeBounty(bountyTarget, true);
	}
	
	private void cleanupBounties() {
		List<BountyListItem> bounties = new ArrayList<BountyListItem>();
		ODBCursor cursor = bountiesODB.getCursor();
		while(cursor.hasNext()) {
			BountyListItem bounty = (BountyListItem) cursor.next();
			
			if (bounty != null && !core.characterService.playerExists(bounty.getObjectID())) {
				bounties.add(bounty);
			}
		}
		bounties.stream().mapToLong(b -> b.getObjectID()).forEach(bountiesODB::remove);
		if (bounties.size() != 0)
			System.out.println("Removed " + bounties.size() + " bounties.");
	}
	
	@Override
	public void shutdown() {
	}
}
