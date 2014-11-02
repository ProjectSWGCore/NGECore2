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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
import protocol.swg.objectControllerObjects.ShowQuestAcceptWindow;
import protocol.swg.objectControllerObjects.ShowQuestCompletionWindow;
import resources.common.Console;
import resources.common.ObjControllerOpcodes;
import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.common.collidables.QuestCollidable;
import resources.datatables.DisplayType;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.waypoint.WaypointObject;
import resources.quest.Quest;
import services.sui.SUIService.MessageBoxType;
import services.sui.SUIWindow;
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
	private List<String> forceAcceptQuests = new ArrayList<String>();
	private Map<Integer, String> questCrcMap = new ConcurrentHashMap<Integer, String>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public QuestService(NGECore core) {
		this.core = core;
		
		try {
			DatatableVisitor forceQuestVisitor = ClientFileManager.loadFile("datatables/quest/force_accept_quests.iff", DatatableVisitor.class);
			
			for (int r = 0; r > forceQuestVisitor.getRowCount(); r++) {
				forceAcceptQuests.add((String) forceQuestVisitor.getObject(r, 0));
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		File quests = Paths.get("clientdata/datatables/questlist/quest").toFile();
		for (File fQuest : quests.listFiles()) {
			if (fQuest.isDirectory())
				continue;
			
			String questName = "quest/" + fQuest.getName().split(".iff")[0];
			questCrcMap.put(CRC.StringtoCRC(questName), questName);
		}
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
	
	public void activateNextTask(CreatureObject quester, String questName) {
		if (quester.getPlayerObject().getQuestJournal().containsKey(CRC.StringtoCRC("quest/" + questName)) && !quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)).isCompleted()) {
			activateNextTask(quester, quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)));
		}
	}
		
	public void activateNextTask(CreatureObject quester, Quest quest) {
		activateTask(quester, quest, quest.getActiveTask());
	}
	
	public void activateTask(CreatureObject quester, String questName, int taskId) {
		if (quester.getPlayerObject().getQuestJournal().containsKey(CRC.StringtoCRC("quest/" + questName)) && !quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)).isCompleted()) {
			activateTask(quester, quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)), taskId);
		}
	}
	
	public void activateTask(CreatureObject quester, Quest quest, int taskId) {
		if (quester == null || quester.getClient() == null || quester.getClient().getSession() == null)
			return;
		if (quest.isCompleted())
			return;
		QuestData qData = getQuestData(quest.getName());
		if (qData == null)
			return;
		PlayerObject player = quester.getPlayerObject();
		if (player == null)
			return;
		QuestTask task = qData.getTasks().get(taskId);

		if (task.getMinStartDelay() > 0 || task.getMaxStartDelay() > 0) {
			if (task.getScheduledTask() == null) {
				int min = task.getMinStartDelay();
				int max = task.getMaxStartDelay();
				if ((max - min) < 0)
					return;
				Random random = new Random();
				int delay = random.nextInt(max - min) + min;
				ScheduledFuture<?> scheduledTask = scheduler.schedule(new Runnable() {
					@Override
					public void run() {
						try {
							activateTask(quester, quest, taskId);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, (long) delay, TimeUnit.SECONDS);
				task.setScheduledTask(scheduledTask);
				return;
			}
			task.setScheduledTask(null);
		}
		
		if (task.getChanceToSkip() > 0) {
			if (new Random().nextInt(100) < task.getChanceToSkip()) {
				completeTask(quester, quest, taskId);
				return;
			}
		}
		
		quest.setQuestStep(taskId);
		player.getContainer().getClient().getSession().write(player.getBaseline(8).createDelta(7));
		
		String buffName = task.getBuffName();
		if (buffName != null && buffName != "")
			core.buffService.addBuffToCreature(quester, buffName, quester);
		
		if (task.dismountsPlayer()) {
			if (core.mountService.isMounted(quester)) {
				core.mountService.dismount(quester, (CreatureObject) quester.getContainer());
			}
		}
		
		if (task.createsWaypoint()) {
			Point3D location = new Point3D(task.getLocationX(), task.getLocationY(), task.getLocationZ());
			WaypointObject wpGoTo = createWaypoint(task.getWaypointName(), location, task.getPlanet());
			player.getWaypoints().put(wpGoTo.getObjectID(), wpGoTo);
			quest.setWaypointId(wpGoTo.getObjectID());
		}
		
		if (task.getTimerAmount() > 0) {
			// fail task at end of timer
		}
		
		// TODO
		// set Travel Block: bool TRAVEL_BLOCK, bool TRAVEL_BLOCK_ALLOW_LAUNCH
		// set Mount Block: int MOUNT_BLOCK_DURATION
		
		// quest.task.ground.type
		switch (task.getType()) {
		case "spawn":
		case "encounter":
			Random random = new Random();
			int count = task.getCount();			
			if (count == 0) {
				int minCount = task.getMinCount();
				int maxCount = task.getMaxCount();
				if (maxCount - minCount < 0) {
					System.err.println("MaxCount was less than MinCount for Quest Task: "+task.getName());
					return;
				}
				count = random.nextInt(maxCount - minCount) + minCount;
			}
			int minDistance = task.getMinDistance();
			int maxDistance = task.getMaxDistance();
			String creatureType = task.getCreatureType();
			int distance;
			float angle, dx, dy;
			for (int i = 0; i < count; i++) {
				distance = random.nextInt(maxDistance - minDistance) + minDistance;
				angle = random.nextFloat() * 2 * (float) Math.PI;
				dx = (float) Math.cos(angle) * distance;
				dy = (float) Math.sin(angle) * distance;
				Point3D pos = quester.getWorldPosition();
				core.spawnService.spawnCreature(creatureType, quester.getPlanet().getName(), 0, pos.x + dx, pos.y, pos.z + dy, 1, 0, 1, 0, 1);
			}
			break;
			
		case "go_to_location":
			return;
			
		case "comm_player":
			CommPlayerMessage message = new CommPlayerMessage(quester.getObjectID(), OutOfBand.ProsePackage(task.getCommMessageText()), task.getNpcAppearanceTemplate());
			quester.getClient().getSession().write(message.serialize());
			break;
		
		case "give_item":
			//String itemName = task.getItemToGive();
			try {
				// ?
				SWGObject item = core.objectService.createObject("object/tangible/smuggler/contraband/shared_contraband_general.iff", quester.getPlanet());
		 		quester.getSlottedObject("inventory").add(item);
			} catch (Exception e) {
				 e.printStackTrace();
			}
			return;
			
		case "retrieve_item":
			if (quest.getCounterMax() != task.getNumRequired()) {
				quest.setCounterMax(task.getNumRequired());
				quest.setCounterValue(0);
			}
			player.getQuestRetrieveItemTemplates().put(task.getServerTemplate(), new QuestItem(quest.getName(), taskId));
			ObjControllerMessage itemCount = new ObjControllerMessage(11, new QuestTaskCounterMessage(quester.getObjectID(), quest, "@quest/groundquests:retrieve_item_counter"));
			quester.getClient().getSession().write(itemCount.serialize());
			return;
		
		case "timer":
			// TODO: Fix timer not showing
			//System.out.println("Max time: " + task.getMaxTime() + " Min time: " + task.getMinTime());
			AtomicInteger time = new AtomicInteger(new Random(task.getMaxTime()).nextInt((task.getMaxTime() - task.getMinTime()) + task.getMinTime()));
			
			ScheduledFuture<?> taskTimer = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
				Console.println("Timer is: " + time.get());
				if (time.get() == 0) {
					//quest.incrementQuestStep();
					completeTask((CreatureObject) core.objectService.getObject(quester.getObjectID()), quest.getName(), taskId);
				} else {
					ObjControllerMessage timer = new ObjControllerMessage(11, new QuestTaskTimerMessage(quester.getObjectID(), quest.getCrcName(), "@quest/groundquests:timer_timertext", time.getAndDecrement()));
					quester.getClient().getSession().write(timer.serialize());
				}
			}, 1, 1, TimeUnit.SECONDS);
			quest.setTimer(taskTimer);
			return;
			
		case "wait_for_signal":
			core.scriptService.callScript("scripts/quests/events/signals/", task.getSignalName(), "wait", core, quester);
			return;

		case "show_message_box":
			SUIWindow msgBox = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_OK, task.getMsgBoxTitle(), task.getMsgBoxPrompt(), quester, null, 0);
			if (task.getMsgBoxSound() != null && !task.getMsgBoxSound().equals(""))
				quester.playMusic(task.getMsgBoxSound());
			core.suiService.openSUIWindow(msgBox);
			break;
		
		case "complete_quest":
			if (quest.getWaypointId() != 0) {
				player.getWaypoints().remove(quest.getWaypointId());
				core.objectService.destroyObject(quest.getWaypointId());
			}
			if (quest.getTimer() != null)
				quest.getTimer().cancel(true);
			break;
		
		case "reward":
			// TODO: System message? - Also check possibility of "Item" column being used for reward as well
			TangibleObject reward = (TangibleObject) core.objectService.createObject(getQuestRewardTemplate(task.getLootName()), core.terrainService.getPlanetByName(task.getPlanet()));
			if (reward == null)
				return;
			quester.getInventory().add(reward);
			break;
		
		case "nothing":
			break;
			
		default:
			//System.out.println("Don't know what to do for quest task: " + type);
			break;
		}
		
		completeTask(quester, quest, taskId);
	}
	
	public void completeActiveTask(CreatureObject quester, String questName) {
		if (quester.getPlayerObject().getQuestJournal().containsKey(CRC.StringtoCRC("quest/" + questName)) && !quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)).isCompleted()) {
			completeTask(quester, quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)), quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)).getActiveTask());
		}
	}
	
	public void completeActiveTask(CreatureObject quester, Quest quest) {
		completeTask(quester, quest, quest.getActiveTask());
	}
	
	public void completeTask(CreatureObject quester, String questName, int taskId) {	
		if (quester.getPlayerObject().getQuestJournal().containsKey(CRC.StringtoCRC("quest/" + questName)) && !quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)).isCompleted()) {
			completeTask(quester, quester.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)), taskId);
		}
	}
	
	public void completeTask(CreatureObject quester, Quest quest, int taskId) {
		PlayerObject player = quester.getPlayerObject();
		if (player == null)
			return;
		QuestData qData = getQuestData(quest.getName());
		QuestTask task = qData.getTasks().get(taskId);
		if (quest.getWaypointId() != 0) {
			player.getWaypoints().remove(quest.getWaypointId());
			core.objectService.destroyObject(quest.getWaypointId());
		}
		
		if (quest.getTimer() != null)
			quest.getTimer().cancel(true);
		
		if (task.showSystemMessages() && task.isVisible()) {
			ProsePackage prose = new ProsePackage("@quest/ground/system_message:quest_task_completed");
			prose.setToCustomString(task.getJournalEntryTitle());
			quester.sendSystemMessage(new OutOfBand(prose), DisplayType.Quest);
		}
		
		quest.completeQuestStep(taskId);
		player.getContainer().getClient().getSession().write(player.getBaseline(8).createDelta(7));
		
		if (task.getGrantQuestOnComplete() != null && !task.getGrantQuestOnComplete().equals("")) {
			if (task.isVisible())
				sendQuestAcceptWindow(quester, task.getGrantQuestOnComplete());
			else
				immediatlyActivateQuest(quester, task.getGrantQuestOnComplete());
		}
		
		if (task.getType().equals("complete_quest") || task.getQuestControlOnTaskCompletion() == 1) {
			quest.setCompleted();
			quester.getClient().getSession().write(player.getBaseline(8).createDelta(7));
			sendQuestCompleteWindow(quester, quest.getCrcName());
			return;
		}
	
		String list = task.getTasksOnComplete();		
		if (list.length() == 0)
			return;
		ArrayList<Integer> tasks = new ArrayList<>();
		for (String t : list.split(",")) {
			taskId = Integer.parseInt(t);
			if (qData.getTasks().get(taskId).getChanceToActivate() == 1) {
				tasks.add(taskId);
			} else {
				activateTask(quester, quest, taskId);
			}
		}
		if (tasks.size() > 0) {
			Random random = new Random();
			taskId = tasks.get(random.nextInt(tasks.size()));
			activateTask(quester, quest, taskId);
		}
	}
	
	public void immediatlyActivateQuest(CreatureObject quester, String questString) {
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
		quest.reset();
		player.getContainer().getClient().getSession().write(player.getBaseline(8).createDelta(7));
		activateNextTask(quester, quest);
	}
	
	public void completeQuest(PlayerObject player, Quest quest) {
		if (player == null || quest == null)
			return;
		
		CreatureObject creo = (CreatureObject) player.getContainer();
		
		if (!quest.isCompleted()) {
			quest.setCompleted();
			creo.getClient().getSession().write(player.getBaseline(8).createDelta(7));
		}
		
		QuestList info = getQuestList(quest.getName());
		//QuestTask task = getQuestData(quest.getName()).getTasks().get(quest.getActiveTask());
		
		ArrayList<Long> recievedItems = new ArrayList<Long>();
		TangibleObject inventory = (TangibleObject) creo.getSlottedObject("inventory");
		
		if (inventory == null)
			return;
		
		boolean rewardSound = false;
		
		PlayMusicMessage completeSound = new PlayMusicMessage("sound/ui_npe2_quest_completed.snd", creo.getObjectID(), 0, true);
		creo.getClient().getSession().write(completeSound.serialize());
		
		// TODO: Give other awards - exclusive loots, etc.
		// Receiving quest awards (faction, item, exp, credits) - http://youtu.be/-L7Rz1WpmGM?t=14m50s
		
		// Give quest items
		
		// Faction Reward
		if (info.getRewardFactionAmount() > 0) {
			int rewardFactionAmount = info.getRewardFactionAmount();
			int currentAmount = player.getFactionStanding(info.getRewardFactionType());
			player.setFactionStanding(info.getRewardFactionType(), currentAmount + rewardFactionAmount);
		}
		
		if (info.getRewardLootName() != null && !info.getRewardLootName().equals("")) {
			SWGObject rewardLoot = core.objectService.createObject(getQuestRewardTemplate(info.getRewardLootName()), creo.getPlanet());
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
		if (info.getRewardExperienceAmount() < 0 && info.getTier() != -1 && !(info.getTier() > 6)) {
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
		} else if (info.getRewardExperienceAmount() > 0) {
			core.playerService.giveExperience(creo, info.getRewardExperienceAmount());
		}
		
		// Update quest & client
		quest.setRecievedAward(true);
		creo.getClient().getSession().write(player.getBaseline(8).createDelta(7));
	}
	
	public void sendQuestWindow(CreatureObject reciever, String questName) {
		ObjControllerMessage objMsg = new ObjControllerMessage(0x0B, new ForceActivateQuest(reciever.getObjectID(), CRC.StringtoCRC("quest/" + questName)));
		reciever.getClient().getSession().write(objMsg.serialize());
	}
	public void sendQuestAcceptWindow(CreatureObject reciever, String questName) {
		ObjControllerMessage objController = new ObjControllerMessage(0x0B, new ShowQuestAcceptWindow(reciever.getObjectID(), questName));
		reciever.getClient().getSession().write(objController.serialize());
	}
	
	public void sendQuestCompleteWindow(CreatureObject reciever, String questName) {
		ObjControllerMessage objController = new ObjControllerMessage(0x0B, new ShowQuestCompletionWindow(reciever.getObjectID(), questName));
		reciever.getClient().getSession().write(objController.serialize());
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
	
	public void handleActivateSignal(CreatureObject actor, TangibleObject npc, String questName, String signalName) {
		if (actor.getPlayerObject().getQuestJournal().containsKey(CRC.StringtoCRC("quest/" + questName)) && !actor.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)).isCompleted()) {
			handleActivateSignal(actor, npc, actor.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)), actor.getPlayerObject().getQuestJournal().get(CRC.StringtoCRC("quest/" + questName)).getActiveTask());
		}
	}
	
	public void handleActivateSignal(CreatureObject actor, TangibleObject npc, Quest quest, String signalName) {
		for (QuestTask task : getQuestData(quest.getName()).getTasks()) {
			if (task.getSignalName().equals(signalName)) {
				handleActivateSignal(actor, npc, quest, task.getId());
				return;
			}
		}
	}
	
	public void handleActivateSignal(CreatureObject actor, TangibleObject npc, Quest quest, int taskId) {
		QuestTask task = getQuestData(quest.getName()).getTasks().get(taskId);
		if (task == null)
			return;
		
		if (core.scriptService.getMethod("scripts/quests/events/signals/", task.getSignalName(), "activate") != null) {
			core.scriptService.callScript("scripts/quests/events/signals/", task.getSignalName(), "activate", core, actor, quest);
			return;
		}
	}
	
	public void handleAddConversationScript(TangibleObject object, String script) {
		if (object == null)
			return;
		
		object.setAttachment("conversationFile", script);
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
	
	public String getQuestRewardTemplate(String lootName) {
		String rtrn = core.scriptService.getMethod("scripts/quests/rewards/", lootName, "run").__call__().asString();
		
		if (rtrn == null) {
			System.err.println("couldn't find quest reward template for lootName " + lootName);
		}
		
		return rtrn;
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
	
	public String getQuestStringByCrc(int crc) {
		return questCrcMap.get(crc);
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
		
		immediatlyActivateQuest(creo, questName);
		return true;
	}
	
	private QuestData getQuestDatatable(String quest) {
		
		QuestData data = new QuestData(quest);
		
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/questtask/quest/" + quest + ".iff", DatatableVisitor.class);
			
			for (int r = 0; r < visitor.getRowCount(); r++) {
				
				// Each row is a new task. TODO: Complete
				
				QuestTask task = new QuestTask();
				
				if (visitor.getObjectByColumnNameAndIndex("ATTACH_SCRIPT", r) != null) {
					String[] splitType = ((String) visitor.getObjectByColumnNameAndIndex("ATTACH_SCRIPT", r)).split("\\.");
					String type = splitType[splitType.length - 1];
					task.setType(type);
				}
				
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
				if (visitor.getObjectByColumnNameAndIndex("CHANCE_TO_ACTIVATE", r) != null) task.setChanceToActivate((int) visitor.getObjectByColumnNameAndIndex("CHANCE_TO_ACTIVATE", r));
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
				if (visitor.getObjectByColumnNameAndIndex("CREATURE_TYPE", r) != null) task.setCreatureType((String) visitor.getObjectByColumnNameAndIndex("CREATURE_TYPE", r));
				if (visitor.getObjectByColumnNameAndIndex("COUNT", r) != null) task.setCount((int) visitor.getObjectByColumnNameAndIndex("COUNT", r));
				if (visitor.getObjectByColumnNameAndIndex("MIN_COUNT", r) != null) task.setMinCount((int) visitor.getObjectByColumnNameAndIndex("MIN_COUNT", r));
				if (visitor.getObjectByColumnNameAndIndex("MAX_COUNT", r) != null) task.setMaxCount((int) visitor.getObjectByColumnNameAndIndex("MAX_COUNT", r));
				if (visitor.getObjectByColumnNameAndIndex("MIN_DISTANCE", r) != null) task.setMinDistance((int) visitor.getObjectByColumnNameAndIndex("MIN_DISTANCE", r));
				if (visitor.getObjectByColumnNameAndIndex("MAX_DISTANCE", r) != null) task.setMaxDistance((int) visitor.getObjectByColumnNameAndIndex("MAX_DISTANCE", r));
				if (visitor.getObjectByColumnNameAndIndex("RELATIVE_OFFSET_X", r) != null) task.setRelativeOffsetX(convertToSharedFile((String) visitor.getObjectByColumnNameAndIndex("RELATIVE_OFFSET_X", r)));
				if (visitor.getObjectByColumnNameAndIndex("RELATIVE_OFFSET_Y", r) != null) task.setRelativeOffsetY(convertToSharedFile((String) visitor.getObjectByColumnNameAndIndex("RELATIVE_OFFSET_Y", r)));
				if (visitor.getObjectByColumnNameAndIndex("SERVER_TEMPLATE", r) != null) task.setServerTemplate(convertToSharedFile((String) visitor.getObjectByColumnNameAndIndex("SERVER_TEMPLATE", r)));
				if (visitor.getObjectByColumnNameAndIndex("NUM_REQUIRED", r) != null) task.setNumRequired((int) visitor.getObjectByColumnNameAndIndex("NUM_REQUIRED", r));
				if (visitor.getObjectByColumnNameAndIndex("ITEM_NAME", r) != null) task.setItemName((String) visitor.getObjectByColumnNameAndIndex("ITEM_NAME", r));
				if (visitor.getObjectByColumnNameAndIndex("DROP_PERCENT", r) != null) task.setDropPercent((int) visitor.getObjectByColumnNameAndIndex("DROP_PERCENT", r));
				if (visitor.getObjectByColumnNameAndIndex("RETRIEVE_MENU_TEXT", r) != null) task.setRetrieveMenuText((String) visitor.getObjectByColumnNameAndIndex("RETRIEVE_MENU_TEXT", r));
				if (visitor.getObjectByColumnNameAndIndex("MIN_TIME", r) != null) task.setMinTime((int) visitor.getObjectByColumnNameAndIndex("MIN_TIME", r));
				if (visitor.getObjectByColumnNameAndIndex("MAX_TIME", r) != null) task.setMaxTime((int) visitor.getObjectByColumnNameAndIndex("MAX_TIME", r));
				if (visitor.getObjectByColumnNameAndIndex("RADIUS", r) != null && visitor.getObjectByColumnNameAndIndex("RADIUS", r) != "") task.setRadius(Float.parseFloat((String) visitor.getObjectByColumnNameAndIndex("RADIUS", r)));
				if (visitor.getObjectByColumnNameAndIndex("SIGNAL_NAME", r) != null) task.setSignalName((String) visitor.getObjectByColumnNameAndIndex("SIGNAL_NAME", r));
				if (visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_TITLE", r) != null) task.setMsgBoxTitle((String) visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_TITLE", r));
				if (visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_TEXT", r) != null) task.setMsgBoxPrompt((String) visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_TEXT", r));
				if (visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_SOUND", r) != null) task.setMsgBoxSound((String) visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_SOUND", r));
				if (visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_SIZE_WIDTH", r) != null) task.setMsgBoxWidth(Integer.parseInt((String) visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_SIZE_WIDTH", r)));
				if (visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_SIZE_HEIGHT", r) != null) task.setMsgBoxHeight(Integer.parseInt((String) visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_SIZE_HEIGHT", r)));
				if (visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_SIZE_WIDTH", r) != null) task.setMsgBoxWidth(Integer.parseInt((String) visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_SIZE_WIDTH", r)));
				if (visitor.getObjectByColumnNameAndIndex("LOOT_NAME", r) != null) task.setLootName((String) visitor.getObjectByColumnNameAndIndex("LOOT_NAME", r));
				if (visitor.getObjectByColumnNameAndIndex("TIMER_AMOUNT", r) != null) task.setTimerAmount((int) visitor.getObjectByColumnNameAndIndex("TIMER_AMOUNT", r));
				if (visitor.getObjectByColumnNameAndIndex("ITEM_TO_GIVE", r) != null) task.setItemToGive(convertToSharedFile((String) visitor.getObjectByColumnNameAndIndex("ITEM_TO_GIVE", r)));
				if (visitor.getObjectByColumnNameAndIndex("CHANCE_TO_SKIP", r) != null) task.setChanceToSkip((int) visitor.getObjectByColumnNameAndIndex("CHANCE_TO_SKIP", r));
				if (visitor.getObjectByColumnNameAndIndex("MIN_START_DELAY", r) != null) task.setMinStartDelay((int) visitor.getObjectByColumnNameAndIndex("MIN_START_DELAY", r));
				if (visitor.getObjectByColumnNameAndIndex("MAX_START_DELAY", r) != null) task.setMaxStartDelay((int) visitor.getObjectByColumnNameAndIndex("MAX_START_DELAY", r));
				if (visitor.getObjectByColumnNameAndIndex("BUFF_NAME", r) != null) task.setBuffName((String) visitor.getObjectByColumnNameAndIndex("BUFF_NAME", r));
				if (visitor.getObjectByColumnNameAndIndex("DISMOUNT_PLAYER", r) != null) task.setDismountPlayer((boolean) visitor.getObjectByColumnNameAndIndex("DISMOUNT_PLAYER", r));
				if (visitor.getObjectByColumnNameAndIndex("QUEST_CONTROL_ON_TASK_COMPLETION", r) != null) task.setQuestControlOnTaskCompletion((int) visitor.getObjectByColumnNameAndIndex("QUEST_CONTROL_ON_TASK_COMPLETION", r));

				// if (visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_LOCATION_X", r) != null) task.setMsgBoxWidth((String) visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_LOCATION_X", r));
				// if (visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_LOCATION_Y", r) != null) task.setMsgBoxWidth((String) visitor.getObjectByColumnNameAndIndex("MESSAGE_BOX_LOCATION_Y", r));

				//if (visitor.getObjectByColumnNameAndIndex("", r)) 

				if (task.getType().equals("go_to_location")) {
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
	
	public void loadEvents() {
		 FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
		 @Override
		 public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
		 {
			 core.scriptService.callScript("scripts/quests/events/signals/", file.getFileName().toString().replace(".py", ""), "setup", core);
			 return FileVisitResult.CONTINUE;
		 }
		 };
		 try { Files.walkFileTree(Paths.get("scripts/quests/events/signals/"), fv); }
		 catch (IOException e) { e.printStackTrace(); }
	}

	@Override
	public void shutdown() { }
	
}
