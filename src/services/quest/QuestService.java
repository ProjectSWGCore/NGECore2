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
package services.quest;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.CommPlayerMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.PlayMusicMessage;
import protocol.swg.objectControllerObjects.ForceActivateQuest;
import protocol.swg.objectControllerObjects.QuestTaskCounterMessage;
import protocol.swg.objectControllerObjects.QuestTaskTimerMessage;
import protocol.swg.objectControllerObjects.ShowLootBox;
import resources.common.Console;
import resources.common.ObjControllerOpcodes;
import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.common.collidables.CollidableCircle;
import resources.common.collidables.QuestCollidable;
import resources.datatables.DisplayType;
import resources.objects.SWGMap;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.waypoint.WaypointObject;
import resources.quest.Quest;
import main.NGECore;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class QuestService implements INetworkDispatch {

	private NGECore core;
	private Map<String, QuestData> questMap = new ConcurrentHashMap<String, QuestData>();
	private Map<String, QuestList> questRewardMap = new ConcurrentHashMap<String, QuestList>();

	public QuestService(NGECore core) {
		this.core = core;
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		objControllerOpcodes.put(ObjControllerOpcodes.QUEST_CHANGE_ACTIVE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				// This packet seems to not expect a response from the server. It's sent to server whenever changing active quest.
			}
			
		});
	}

	public boolean doesPlayerHaveQuest(CreatureObject creo, String questName) {
		PlayerObject ghost = creo.getPlayerObject();
		
		if (ghost == null) {
			try { throw new Exception("Ghost is null! Cannot determine if player has quest or not."); } 
			catch (Exception e) {e.printStackTrace();}
		}

		SWGMap<Integer, Quest> questJournal = ghost.getQuestJournal();
		
		if (questJournal.size() == 0)
			return false;
		
		return questJournal.containsKey(CRC.StringtoCRC("quest/" + questName));
	}
	
	public boolean isQuestCompleted(CreatureObject creo, String questName) {
		PlayerObject ghost = creo.getPlayerObject();
		
		if (ghost == null) {
			try { throw new Exception("Ghost is null! Cannot determine if player completed quest or not."); } 
			catch (Exception e) {e.printStackTrace();}
		}
		
		SWGMap<Integer, Quest> questJournal = ghost.getQuestJournal();
		
		if (questJournal.size() == 0)
			return false;
		
		if (!questJournal.containsKey(CRC.StringtoCRC("quest/" + questName)))
			return false;
		else 
			return questJournal.get(CRC.StringtoCRC("quest/") + questName).isCompleted();
	}
	
	public void sendQuestWindow(CreatureObject reciever, String questName) {
		if (reciever == null || reciever.getClient() == null || reciever.getClient().getSession() == null)
			return;
		
		ObjControllerMessage objMsg = new ObjControllerMessage(11, new ForceActivateQuest(reciever.getObjectID(), CRC.StringtoCRC("quest/" + questName)));
		reciever.getClient().getSession().write(objMsg.serialize());
	}
	
	public void activateNextTask(CreatureObject quester, String questName) {
		
		if (quester.getPlayerObject().getQuestJournal().containsKey(CRC.StringtoCRC("quest/" + questName)) && !quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)).isCompleted()) {
			activateNextTask(quester, quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)));
		}
	}
	
	public void activateNextTask(CreatureObject quester, Quest quest) {
		if (quester == null || quester.getClient() == null || quester.getClient().getSession() == null)
			return;
		
		QuestData qData = getQuestData(quest.getName());
		
		if (qData == null)
			return;
		
		PlayerObject player = quester.getPlayerObject();
		
		if (player == null)
			return;
		
		int activeStep = quest.getActiveTask();
		
		QuestTask task = qData.getTasks().get(activeStep);
		
		String[] splitType = task.getType().split("\\.");
		String type = splitType[splitType.length - 1];

		switch (type) {
		
		// quest.task.ground.go_to_location
		case "go_to_location":
			Point3D location = new Point3D(task.getLocationX(), task.getLocationY(), task.getLocationZ());
			WaypointObject wpGoTo = createWaypoint(task.getWaypointName(), location, task.getPlanet());
			player.getWaypoints().put(wpGoTo.getObjectID(), wpGoTo);
			
			quest.setWaypointId(wpGoTo.getObjectID());
			break;
	
		// quest.task.ground.comm_player
		case "comm_player":
			
			CommPlayerMessage message = new CommPlayerMessage(quester.getObjectID(), OutOfBand.ProsePackage(task.getCommMessageText()), task.getNpcAppearanceTemplate());
			quester.getClient().getSession().write(message.serialize());
			
			break;
		
		// quest.task.ground.retrieve_item
		case "retrieve_item":
			player.getQuestRetrieveItemTemplates().put(task.getServerTemplate(), new QuestItem(quest.getName(), activeStep));

			// TODO: add item count
			ObjControllerMessage itemCount = new ObjControllerMessage(11, new QuestTaskCounterMessage(quester.getObjectID(), quest.getCrcName(), "@quest/groundquests:retrieve_item_counter"));
			quester.getClient().getSession().write(itemCount.serialize());
			
			WaypointObject wpRetrieve = createWaypoint(task.getWaypointName(), new Point3D(task.getLocationX(), task.getLocationY(), task.getLocationZ()), task.getPlanet());
			player.getWaypoints().put(wpRetrieve.getObjectID(), wpRetrieve);
			
			quest.setWaypointId(wpRetrieve.getObjectID());
			break;
		
		// quest.task.ground.timer
		case "timer":
			//System.out.println("Max time: " + task.getMaxTime() + " Min time: " + task.getMinTime());
			AtomicInteger time = new AtomicInteger(new Random(task.getMaxTime()).nextInt((task.getMaxTime() - task.getMinTime()) + task.getMinTime()));
			
			ScheduledFuture<?> taskTimer = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
				Console.println("Timer is: " + time.get());
				if (time.get() == 0) {
					//quest.incrementQuestStep();
					completeActiveTask((CreatureObject) core.objectService.getObject(quester.getObjectID()), quest.getName());
				} else {
					ObjControllerMessage timer = new ObjControllerMessage(11, new QuestTaskTimerMessage(quester.getObjectID(), quest.getCrcName(), "@quest/groundquests:timer_timertext", time.getAndDecrement()));
					quester.getClient().getSession().write(timer.serialize());
				}
			}, 1, 1, TimeUnit.SECONDS);
			
			quest.setTimer(taskTimer);
			break;
			
		// quest.task.ground.show_message_box
		case "show_message_box":
			break;
		
		default:
			//System.out.println("Don't know what to do for quest task: " + type);
			break;
				
		}

		if (!task.isVisible()) {
			completeActiveTask(quester, quest);
			return;
		}
		
	}
	
	public void completeActiveTask(CreatureObject quester, String questName) {	
		if (quester.getPlayerObject().getQuestJournal().containsKey(CRC.StringtoCRC("quest/" + questName)) && !quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)).isCompleted()) {
			completeActiveTask(quester, quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)));
		}
	}
	
	public void completeActiveTask(CreatureObject quester, Quest quest) {
		PlayerObject player = quester.getPlayerObject();
		if (player == null)
			return;
		
		QuestData qData = getQuestData(quest.getName());
		
		int activeStep = quest.getActiveTask();
		
		QuestTask task = qData.getTasks().get(activeStep);
		
		//if (quest.getWaypointId() != 0)
		//core.objectService.destroyObject(quest.getWaypointId());
	
		if (quest.getTimer() != null)
			quest.getTimer().cancel(true);
		
		if (activeStep + 1 >= qData.getTasks().size()) {
			QuestList listItem = questRewardMap.get(quest.getName());

			if (listItem.isCompleteWhenTasksComplete())
				completeQuest(player, quest); // Force complete packet sent if the quest isn't auto completed (window shows up, typical for exclusive reward items)
			/*else
				sendQuestCompleteWindow(quester, quest.getName());*/
			
			if (task.getGrantQuestOnComplete() != null && !task.getGrantQuestOnComplete().equals(""))
				activateQuest(quester, task.getGrantQuestOnComplete());

			return;
		}
		
		if (task.getGrantQuestOnComplete() != null && !task.getGrantQuestOnComplete().equals(""))
			activateQuest(quester, task.getGrantQuestOnComplete());
		
		quest.incrementQuestStep();
		
		player.getContainer().getClient().getSession().write(player.getBaseline(8).createDelta(7));
		
		activateNextTask(quester, quest);
		
	}
	
	public void activateQuest(CreatureObject quester, String questString) {
		Quest quest = new Quest(questString, quester.getObjectID());
		
		PlayerObject player = quester.getPlayerObject();
		
		if (player == null)
			return;
		
		QuestList info = questRewardMap.get(quest.getName());
		if (info == null) {
			info = getQuestListDatatable(quest.getName());
		}
		
		ProsePackage prose = new ProsePackage("@quest/ground/system_message:quest_received");
		prose.setToCustomString(info.getJournalEntryTitle());
		quester.sendSystemMessage(new OutOfBand(prose), DisplayType.Quest);
		
		PlayMusicMessage music = new PlayMusicMessage("sound/ui_npe2_quest_received.snd", quester.getObjectID(), 0, true);
		quester.getClient().getSession().write(music.serialize());
		
		player.getQuestJournal().put(quest.getCrc(), quest);
		player.setActiveQuest(quest.getName());
		
		activateNextTask(quester, quest);
	}
	
	public void completeQuest(PlayerObject player, Quest quest) {
		if (player == null || quest == null)
			return;
		
		CreatureObject creo = (CreatureObject) player.getContainer();
		
		if (!quest.isCompleted()) {
			quest.setCompleted(true);
		}
		
		QuestList info = getQuestList(quest.getName());
		
		ArrayList<Long> recievedItems = new ArrayList<Long>();
		TangibleObject inventory = (TangibleObject) creo.getSlottedObject("inventory");
		
		if (inventory == null)
			return;
		
		boolean rewardSound = false;
		
		PlayMusicMessage completeSound = new PlayMusicMessage("sound/ui_npe2_quest_completed.snd", creo.getObjectID(), 0, true);
		creo.getClient().getSession().write(completeSound.serialize());
		
		// TODO: Give other awards - exclusive loots, faction points
		// Receiving quest awards (faction, item, exp, credits) - http://youtu.be/-L7Rz1WpmGM?t=14m50s
		
		// Give quest items
		
		if (info.getRewardLootName() != null && !info.getRewardLootName().equals("")) {
			SWGObject rewardLoot = core.objectService.createObject(core.scriptService.getMethod("scripts/quests/rewards/", info.getRewardLootName(), "run").__call__().asString(), creo.getPlanet());
			inventory.add(rewardLoot);
			recievedItems.add(rewardLoot.getObjectID());
		}
		
		// TODO: Other item awards -- put above this comment
		if (recievedItems.size() != 0) {

			for (long item : recievedItems) {
				creo.sendSystemMessage(OutOfBand.ProsePackage("@quest/ground/system_message:placed_in_inventory", "TO", item), DisplayType.Broadcast);
			}
			
			ObjControllerMessage objMessage = new ObjControllerMessage(11, new ShowLootBox(creo.getObjectID(), recievedItems));
			creo.getClient().getSession().write(objMessage.serialize());
			
			if (!rewardSound) {
				PlayMusicMessage itemSound = new PlayMusicMessage("sound/ui_npe2_quest_reward.snd", creo.getObjectID(), 0, true);
				creo.getClient().getSession().write(itemSound.serialize());
				rewardSound = true;
			}
		}
		
		// Give credits
		if (info.getRewardBankCredits() != 0) {
			creo.addBankCredits(info.getRewardBankCredits());
			creo.sendSystemMessage(OutOfBand.ProsePackage("@base_player:prose_transfer_success", "DI", info.getRewardBankCredits()), DisplayType.Broadcast);
			
			if (!rewardSound) {
				PlayMusicMessage credSound = new PlayMusicMessage("sound/ui_npe2_quest_credits.snd", creo.getObjectID(), 0, true);
				creo.getClient().getSession().write(credSound.serialize());
				rewardSound = true;
			}
		}
		
		// Give experience - TODO: Give specific type of experience.
		if (info.getRewardExperienceAmount() != 0 && info.getTier() != -1 && !(info.getTier() > 6)) {
			int experienceAward = 0;
			try {
				DatatableVisitor experienceVisitor = ClientFileManager.loadFile("datatables/quest/quest_experience", DatatableVisitor.class);
				
				for (int r = 0; r < experienceVisitor.getRowCount(); r++) {
					if ((int) experienceVisitor.getObject(r, 0) == creo.getLevel()) {
						experienceAward = (int) experienceVisitor.getObject(r, info.getTier());
					}
				}
			} catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
			if (experienceAward != 0)
				core.playerService.giveExperience(creo, experienceAward);
		} else {
			core.playerService.giveExperience(creo, info.getRewardExperienceAmount());
		}

		// Update quest & client
		quest.setRecievedAward(true);
		creo.getClient().getSession().write(player.getBaseline(8).createDelta(7));
	}
	
	public void sendQuestAcceptWindow(CreatureObject reciever, String questName) {
		
	}
	
	public void sendQuestCompleteWindow(CreatureObject reciever, String questName) {
		
	}
	
	public String getQuestItemRadialName(CreatureObject quester, String template) {
		String radialName = "";
		
		PlayerObject player = quester.getPlayerObject();
		if (player == null)
			return radialName;
		
		if (player.getQuestRetrieveItemTemplates().size() == 0)
			return radialName;

		if (!player.getQuestRetrieveItemTemplates().containsKey(template))
			return radialName;
		
		QuestItem item = player.getQuestRetrieveItemTemplates().get(template);
		QuestData data = getQuestData(item.getQuestName());
		
		return data.getTasks().get(item.getTask()).getRetrieveMenuText();
	}
	
	public void handleQuestItemRadialSelection(CreatureObject quester, SWGObject target) {
		if (quester == null || target == null)
			return;
		
		PlayerObject player = quester.getPlayerObject();
		if (player == null)
			return;
		
		if (!player.getQuestRetrieveItemTemplates().containsKey(target.getTemplate()))
			return;

		QuestItem item = player.getQuestRetrieveItemTemplates().get(target.getTemplate());
		completeActiveTask(quester, item.getQuestName());

		player.getQuestRetrieveItemTemplates().remove(target.getTemplate(), item);
		
	}
	
	public WaypointObject createWaypoint(String name, Point3D location, String planet) {
		WaypointObject waypoint = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", core.terrainService.getPlanetByName(planet));
		if (waypoint == null)
			return null;
		
		waypoint.setActive(true);
		waypoint.setColor(WaypointObject.BLUE);
		waypoint.setName(name);
		waypoint.setPlanetCrc(CRC.StringtoCRC(planet));
		waypoint.setStringAttribute("", ""); //This simply allows the attributes to display (nothing else has to be done)
		waypoint.setPosition(location);
		
		return waypoint;
	}
	
	public QuestData getQuestData(String questName) {
		if (questMap.containsKey(questName)) 
			return questMap.get(questName);
		else {
			QuestData data = getQuestDatatable(questName); // parsing puts it in quest map
			questMap.put(questName, data);
			if (!questRewardMap.containsKey(questName)) {
				QuestList listing = getQuestList(questName);
				questRewardMap.put(questName, listing);
			}
			return data;
		}
	}
	
	public QuestList getQuestList(String questName) {
		if (questRewardMap.containsKey(questName))
			return questRewardMap.get(questName);
		else {
			QuestList list = getQuestListDatatable(questName); // parsing puts it in quest map
			questRewardMap.put(questName, list);
			return list;
		}
	}
	
	public boolean adminActivateQuest(CreatureObject creo, String questName) {
		
		PlayerObject ghost = creo.getPlayerObject();
		
		if (ghost == null)
			return false;
		
		if (questName.endsWith(".qst")) {
			File questXml = new File("clientdata/quest/" + questName);
			if (!questXml.exists())
				return false;
			questName = questName.split(".qst")[0];
		} else {
			File questXml = new File("clientdata/quest/" + questName + ".qst");
			if (!questXml.exists())
				return false;
		}
		
		if (questName.contains("/")) {
			String[] split = questName.split("/");
			questName = split[split.length - 1];
		}
		
		QuestData questData = getQuestData(questName);
		
		if (questData == null)
			return false;
		
		activateQuest(creo, questName);
		return true;
	}
	
	private QuestData getQuestDatatable(String quest) {
		
		QuestData data = new QuestData(quest);
		
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/questtask/quest/" + quest + ".iff", DatatableVisitor.class);
			
			for (int r = 0; r < visitor.getRowCount(); r++) {
				
				// Each row is a new task. TODO: Complete
				
				QuestTask task = new QuestTask();
				
				if (visitor.getObjectByColumnNameAndIndex("ATTACH_SCRIPT", r) != null) task.setType((String) visitor.getObjectByColumnNameAndIndex("ATTACH_SCRIPT", r));
				if (visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_TITLE", r) != null) task.setJournalEntryTitle((String) visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_TITLE", r));
				if (visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_DESCRIPTION", r) != null) task.setJournalEntryDescription((String) visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_DESCRIPTION", r));
				if (visitor.getObjectByColumnNameAndIndex("IS_VISIBLE", r) != null) task.setVisible((boolean) visitor.getObjectByColumnNameAndIndex("IS_VISIBLE", r));
				// PREREQUISTE_TASKS
				// EXCLUSION_TASKS
				if (visitor.getObjectByColumnNameAndIndex("ALLOW_REPEATS", r) != null) task.setAllowRepeats((boolean) visitor.getObjectByColumnNameAndIndex("ALLOW_REPEATS", r));
				if (visitor.getObjectByColumnNameAndIndex("TASKS_ON_COMPLETE", r) != null) task.setTasksOnComplete((String) visitor.getObjectByColumnNameAndIndex("TASKS_ON_COMPLETE", r));
				if (visitor.getObjectByColumnNameAndIndex("TASKS_ON_FAIL", r) != null) task.setTasksOnFail((String) visitor.getObjectByColumnNameAndIndex("TASKS_ON_FAIL", r));
				if (visitor.getObjectByColumnNameAndIndex("SHOW_SYSTEM_MESSAGES", r) != null) task.setShowSystemMessages((boolean) visitor.getObjectByColumnNameAndIndex("SHOW_SYSTEM_MESSAGES", r));
				if (visitor.getObjectByColumnNameAndIndex("MUSIC_ON_ACTIVATE", r) != null) task.setMusicOnActivate((String) visitor.getObjectByColumnNameAndIndex("MUSIC_ON_ACTIVATE", r));
				if (visitor.getObjectByColumnNameAndIndex("TARGET", r) != null) task.setTarget((String) visitor.getObjectByColumnNameAndIndex("TARGET", r));
				if (visitor.getObjectByColumnNameAndIndex("PARAMETER", r) != null) task.setParameter((String) visitor.getObjectByColumnNameAndIndex("PARAMETER", r));
				if (visitor.getObjectByColumnNameAndIndex("GRANT_QUEST_ON_COMPLETE", r) != null) task.setGrantQuestOnComplete((String) visitor.getObjectByColumnNameAndIndex("GRANT_QUEST_ON_COMPLETE", r));
				if (visitor.getObjectByColumnNameAndIndex("GRANT_QUEST_ON_FAIL", r) != null) task.setGrantQuestOnFail((String) visitor.getObjectByColumnNameAndIndex("GRANT_QUEST_ON_FAIL", r));
				if (visitor.getObjectByColumnNameAndIndex("TASK_NAME", r) != null) task.setName((String) visitor.getObjectByColumnNameAndIndex("TASK_NAME", r));
				if (visitor.getObjectByColumnNameAndIndex("MUSIC_ON_COMPLETE", r) != null) task.setMusicOnComplete((String) visitor.getObjectByColumnNameAndIndex("MUSIC_ON_COMPLETE", r));
				if (visitor.getObjectByColumnNameAndIndex("MUSIC_ON_FAILURE", r) != null) task.setMusicOnFail((String) visitor.getObjectByColumnNameAndIndex("MUSIC_ON_FAILURE", r));
				if (visitor.getObjectByColumnNameAndIndex("GRANT_QUEST_ON_FAIL_SHOW_SYSTEM_MESSAGE", r) != null) task.setGrantQuestOnFailShowSystemMessage((boolean) visitor.getObjectByColumnNameAndIndex("GRANT_QUEST_ON_FAIL_SHOW_SYSTEM_MESSAGE", r));
				if (visitor.getObjectByColumnNameAndIndex("CREATE_WAYPOINT", r) != null) task.setCreatesWaypoint((boolean) visitor.getObjectByColumnNameAndIndex("CREATE_WAYPOINT", r));
				if (visitor.getObjectByColumnNameAndIndex("PLANET_NAME", r) != null) task.setPlanet((String) visitor.getObjectByColumnNameAndIndex("PLANET_NAME", r));
				if (visitor.getObjectByColumnNameAndIndex("LOCATION_X", r) != null) task.setLocationX(Float.parseFloat((String) visitor.getObjectByColumnNameAndIndex("LOCATION_X", r)));
				if (visitor.getObjectByColumnNameAndIndex("LOCATION_Y", r) != null) task.setLocationY(Float.parseFloat((String) visitor.getObjectByColumnNameAndIndex("LOCATION_Y", r)));
				if (visitor.getObjectByColumnNameAndIndex("LOCATION_Z", r) != null) task.setLocationZ(Float.parseFloat((String) visitor.getObjectByColumnNameAndIndex("LOCATION_Z", r)));
				if (visitor.getObjectByColumnNameAndIndex("WAYPOINT_NAME", r) != null) task.setWaypointName((String) visitor.getObjectByColumnNameAndIndex("WAYPOINT_NAME", r));
				if (visitor.getObjectByColumnNameAndIndex("GRANT_QUEST_ON_COMPLETE_SHOW_SYSTEM_MESSAGE", r) != null) task.setGrantQuestOnCompleteShowSystemMessage((boolean) visitor.getObjectByColumnNameAndIndex("GRANT_QUEST_ON_COMPLETE_SHOW_SYSTEM_MESSAGE", r));
				if (visitor.getObjectByColumnNameAndIndex("SIGNALS_ON_COMPLETE", r) != null) task.setSignalsOnComplete((String) visitor.getObjectByColumnNameAndIndex("SIGNALS_ON_COMPLETE", r));
				if (visitor.getObjectByColumnNameAndIndex("INTERIOR_WAYPOINT_APPERANCE", r) != null) task.setInteriorWaypointAppearance((String) visitor.getObjectByColumnNameAndIndex("INTERIOR_WAYPOINT_APPERANCE", r));
				if (visitor.getObjectByColumnNameAndIndex("SIGNALS_ON_FAIL", r) != null) task.setSignalsOnFail((String) visitor.getObjectByColumnNameAndIndex("SIGNALS_ON_FAIL", r));
				if (visitor.getObjectByColumnNameAndIndex("COMM_MESSAGE_TEXT", r) != null) task.setCommMessageText((String) visitor.getObjectByColumnNameAndIndex("COMM_MESSAGE_TEXT", r));
				if (visitor.getObjectByColumnNameAndIndex("NPC_APPEARANCE_SERVER_TEMPLATE", r) != null) task.setNpcAppearanceTemplate(convertToSharedFile((String) visitor.getObjectByColumnNameAndIndex("NPC_APPEARANCE_SERVER_TEMPLATE", r)));
				if (visitor.getObjectByColumnNameAndIndex("SERVER_TEMPLATE", r) != null) task.setServerTemplate(convertToSharedFile((String) visitor.getObjectByColumnNameAndIndex("SERVER_TEMPLATE", r)));
				if (visitor.getObjectByColumnNameAndIndex("NUM_REQUIRED", r) != null) task.setNumRequired((int) visitor.getObjectByColumnNameAndIndex("NUM_REQUIRED", r));
				if (visitor.getObjectByColumnNameAndIndex("ITEM_NAME", r) != null) task.setItemName((String) visitor.getObjectByColumnNameAndIndex("ITEM_NAME", r));
				if (visitor.getObjectByColumnNameAndIndex("DROP_PERCENT", r) != null) task.setDropPercent((int) visitor.getObjectByColumnNameAndIndex("DROP_PERCENT", r));
				if (visitor.getObjectByColumnNameAndIndex("RETRIEVE_MENU_TEXT", r) != null) task.setRetrieveMenuText((String) visitor.getObjectByColumnNameAndIndex("RETRIEVE_MENU_TEXT", r));
				if (visitor.getObjectByColumnNameAndIndex("MIN_TIME", r) != null) task.setMinTime((int) visitor.getObjectByColumnNameAndIndex("MIN_TIME", r));
				if (visitor.getObjectByColumnNameAndIndex("MAX_TIME", r) != null) task.setMaxTime((int) visitor.getObjectByColumnNameAndIndex("MAX_TIME", r));
				if (visitor.getObjectByColumnNameAndIndex("RADIUS", r) != null && visitor.getObjectByColumnNameAndIndex("RADIUS", r) != "") task.setRadius(Float.parseFloat((String) visitor.getObjectByColumnNameAndIndex("RADIUS", r)));
				//if (visitor.getObjectByColumnNameAndIndex("", r)) 

				if (task.getType().equals("quest.task.ground.go_to_location")) {
					QuestCollidable collision = new QuestCollidable(new Point3D(task.getLocationX(), task.getLocationY(), task.getLocationZ()), task.getRadius(), core.terrainService.getPlanetByName(task.getPlanet()), quest, r);
					collision.setCallback(core.scriptService.getMethod("scripts/quests/events/", "go_to_location", "run"));
					core.simulationService.addCollidable(collision, task.getLocationX(), task.getLocationZ());
				}
				
				data.getTasks().add(task);
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	private QuestList getQuestListDatatable(String quest) {
		QuestList qList = new QuestList();
		
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/questlist/quest/" + quest + ".iff", DatatableVisitor.class);
			// Should only have 1 row with info

			// not the full list.
			if (visitor.getObjectByColumnNameAndIndex("LEVEL", 0) != null) qList.setLevel((int) visitor.getObjectByColumnNameAndIndex("LEVEL", 0));
			if (visitor.getObjectByColumnNameAndIndex("TIER", 0) != null) qList.setTier((int) visitor.getObjectByColumnNameAndIndex("TIER", 0));
			if (visitor.getObjectByColumnNameAndIndex("TYPE", 0) != null) qList.setType((String) visitor.getObjectByColumnNameAndIndex("TYPE", 0));
			if (visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_TITLE", 0) != null) qList.setJournalEntryTitle((String) visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_TITLE", 0));
			if (visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_DESCRIPTION", 0) != null) qList.setJournalEntryDescription((String) visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_DESCRIPTION", 0));
			if (visitor.getObjectByColumnNameAndIndex("CATEGORY", 0) != null) qList.setCategory((String) visitor.getObjectByColumnNameAndIndex("CATEGORY", 0));
			if (visitor.getObjectByColumnNameAndIndex("visible", 0) != null) qList.setVisible((String) visitor.getObjectByColumnNameAndIndex("visible", 0));
			if (visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_COMPLETION_SUMMARY", 0) != null) qList.setJournalEntryCompletionSummary((String) visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_COMPLETION_SUMMARY", 0));
			if (visitor.getObjectByColumnNameAndIndex("PREREQUISITE_QUESTS", 0) != null) qList.setPrerequisteQuests((String) visitor.getObjectByColumnNameAndIndex("PREREQUISITE_QUESTS", 0));
			if (visitor.getObjectByColumnNameAndIndex("EXCLUSION_QUESTS", 0) != null) qList.setExclusionQuests((String) visitor.getObjectByColumnNameAndIndex("EXCLUSION_QUESTS", 0));
			if (visitor.getObjectByColumnNameAndIndex("ALLOW_REPEATS", 0) != null) qList.setAllowRepeats((boolean) visitor.getObjectByColumnNameAndIndex("ALLOW_REPEATS", 0));
			if (visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_EXPERIENCE_TYPE", 0) != null) qList.setRewardExperienceType((String) visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_EXPERIENCE_TYPE", 0));
			if (visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_EXPERIENCE_AMOUNT", 0) != null) qList.setRewardExperienceAmount((int) visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_EXPERIENCE_AMOUNT", 0));
			if (visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_FACTION_NAME", 0) != null) qList.setRewardFactionType((String) visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_FACTION_NAME", 0));
			if (visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_FACTION_AMOUNT", 0) != null) qList.setRewardFactionAmount((int) visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_FACTION_AMOUNT", 0));
			if (visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_BANK_CREDITS", 0) != null) qList.setRewardBankCredits((int) visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_BANK_CREDITS", 0));
			if (visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_LOOT_NAME", 0) != null) qList.setRewardLootName((String) visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_LOOT_NAME", 0));
			if (visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_LOOT_COUNT", 0) != null) qList.setRewardLootCount((int) visitor.getObjectByColumnNameAndIndex("QUEST_REWARD_LOOT_COUNT", 0));
			if (visitor.getObjectByColumnNameAndIndex("COMPLETE_WHEN_TASKS_COMPLETE", 0) != null) qList.setCompleteWhenTasksComplete((boolean) visitor.getObjectByColumnNameAndIndex("COMPLETE_WHEN_TASKS_COMPLETE", 0));

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return qList;
	}
	
	//  useful for custom quest scripts, and for the some of the datatables that are all screwed up
	/*private QuestData parseXmlQuestData(Quest quest) {
		try {

		if (questMap.containsKey(quest.getName())) return null;

		String filePath = quest.getFilePath();
		
		File questXml = new File("clientdata/" + filePath);
		if (!questXml.exists()) return null;

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		
		QuestData dataObj = new QuestData(filePath);
		SWGQuestDataHandler questHandler = new SWGQuestDataHandler(dataObj);
		parser.parse(questXml, questHandler);
		
		questMap.put(quest.getName(), dataObj);
		
		//System.out.println("Loaded quest file " + filePath);
		
		return dataObj;
		
		} catch(SAXException | ParserConfigurationException | IOException e) { }
		
		return null;
	}*/
	
	private String convertToSharedFile(String template) {
		String[] split = template.split("/");
		String file = split[split.length - 1];

		return template.replace(file, "shared_" + file);
	}
	
	@Override
	public void shutdown() { }
	
}
