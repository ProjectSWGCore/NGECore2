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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.xml.sax.SAXException;

import protocol.swg.CommPlayerMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.PlayMusicMessage;
import protocol.swg.objectControllerObjects.ForceActivateQuest;
import resources.common.ObjControllerOpcodes;
import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.datatables.DisplayType;
import resources.objects.SWGList;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.waypoint.WaypointObject;
import resources.quest.Quest;
import main.NGECore;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.common.StringUtilities;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

@SuppressWarnings("unused")
public class QuestService implements INetworkDispatch {

	private NGECore core;
	private Map<String, QuestData> questMap = new ConcurrentHashMap<String, QuestData>();
	
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

	@Override
	public void shutdown() { }
	
	public boolean doesPlayerHaveQuest(CreatureObject creo, String questName) {
		PlayerObject ghost = creo.getPlayerObject();
		
		if (ghost == null) {
			try { throw new Exception("Ghost is null! Cannot determine if player has quest or not."); } 
			catch (Exception e) {e.printStackTrace();}
		}

		SWGList<Quest> questJournal = ghost.getQuestJournal();
		
		if (questJournal.size() == 0)
			return false;
		
		for (Quest quest : questJournal) {
			if (quest.getName().equals(questName))
				return true;
		}
		
		return false;
	}
	
	public boolean isQuestCompleted(CreatureObject creo, String questName) {
		PlayerObject ghost = creo.getPlayerObject();
		
		if (ghost == null) {
			try { throw new Exception("Ghost is null! Cannot determine if player completed quest or not."); } 
			catch (Exception e) {e.printStackTrace();}
		}
		
		SWGList<Quest> questJournal = ghost.getQuestJournal();
		
		if (questJournal.size() == 0)
			return false;
		
		for (Quest quest : questJournal) {
			if (quest.getName().equals(questName)) 
				return quest.isCompleted();
		}
		
		return false;
	}
	
	public boolean adminActivateQuest(CreatureObject creo, String questName) {
		
		PlayerObject ghost = creo.getPlayerObject();
		
		if (ghost == null)
			return false;
		
		Quest quest = new Quest(questName, creo.getObjectID());
		
		File questXml = new File("clientdata/" + quest.getFilePath());
		if (!questXml.exists()) return false;
		
		QuestData questData = getQuestData(quest);
		
		if (questData == null)
			return false;
		
		//ProsePackage prose = new ProsePackage("@quest/ground/system_message:quest_received");
		//prose.setToCustomString(questData.getTasks().get(0).getJournalEntryTitle());
		
		//creo.sendSystemMessage(new OutOfBand(prose), DisplayType.Quest);
		
		PlayMusicMessage music = new PlayMusicMessage("sound/ui_npe2_quest_received.snd", creo.getObjectID(), 0, true);
		creo.getClient().getSession().write(music.serialize());
		
		QuestTask activeTask = questData.getTasks().get(0);
		
		
		/*if (activeTask.createsWaypoint()) {
			WaypointObject waypoint = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", core.terrainService.getPlanetByName(activeTask.getPlanet()));
			Point3D position = new Point3D(activeTask.getLocationX(), activeTask.getLocationY(), activeTask.getLocationZ());
			waypoint.setPosition(position);
			waypoint.setName(activeTask.getWaypointName());
			waypoint.setColor(WaypointObject.BLUE);
			waypoint.setActive(true);
			waypoint.setStringAttribute("", "");
			
			ghost.getWaypoints().put(waypoint.getObjectID(), waypoint);
		}*/
		
		ghost.getQuestJournal().add(quest);
		ghost.setActiveQuest(quest.getCrc());
		
		beginNextQuestStep(creo, quest);
		return true;
	}
	
	public void sendQuestWindow(CreatureObject reciever, Quest quest) {

		ObjControllerMessage objMsg = new ObjControllerMessage(11, new ForceActivateQuest(reciever.getObjectID(), CRC.StringtoCRC(quest.getCrcName())));
		reciever.getClient().getSession().write(objMsg.serialize());
	}
	
	public void beginNextQuestStep(CreatureObject quester, Quest quest) {
		QuestData qData = getQuestData(quest);
		
		if (qData == null)
			return;
		
		// TODO: Get QuestTask for the active step
		QuestTask task = qData.getTasks().get(0);
		
		String[] splitType = task.getType().split("\\.");
		String type = splitType[splitType.length - 1];
		
		switch (type) {
		
		case "comm_player":
			CommPlayerMessage message = new CommPlayerMessage(quester.getObjectID(), OutOfBand.ProsePackage(task.getCommMessageText()), task.getCommAppearanceTemplate());
			quester.getClient().getSession().write(message.serialize());
			break;
		
		default:
			System.out.println("Don't know what to do for quest step: " + type);
			break;
				
		}
		
	}
	
	public QuestData getQuestData(Quest quest) {

		if (questMap.containsKey(quest.getName())) 
			return questMap.get(quest.getName());
		else {
			QuestData data = getQuestDatatable(quest); // parsing puts it in quest map
			return data;
		}
	}
	
	private QuestData getQuestDatatable(Quest quest) {
		
		QuestData data = new QuestData(quest.getFilePath());
		
		// visitor.getObjectByColumnNameAndIndex("JOURNAL_ENTRY_TITLE", r)
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/questtask/quest/" + quest.getName() + ".iff", DatatableVisitor.class);
			
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
				if (visitor.getObjectByColumnNameAndIndex("NPC_APPEARANCE_SERVER_TEMPLATE", r) != null) task.setCommAppearanceTemplate(convertToSharedFile((String) visitor.getObjectByColumnNameAndIndex("NPC_APPEARANCE_SERVER_TEMPLATE", r)));
				if (visitor.getObjectByColumnNameAndIndex("SERVER_TEMPLATE", r) != null) task.setServerTemplate(convertToSharedFile((String) visitor.getObjectByColumnNameAndIndex("SERVER_TEMPLATE", r)));
				if (visitor.getObjectByColumnNameAndIndex("NUM_REQUIRED", r) != null) task.setNumRequired((int) visitor.getObjectByColumnNameAndIndex("NUM_REQUIRED", r));
				if (visitor.getObjectByColumnNameAndIndex("ITEM_NAME", r) != null) task.setItemName((String) visitor.getObjectByColumnNameAndIndex("ITEM_NAME", r));
				if (visitor.getObjectByColumnNameAndIndex("DROP_PERCENT", r) != null) task.setDropPercent((int) visitor.getObjectByColumnNameAndIndex("DROP_PERCENT", r));

				data.getTasks().add(task);
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		questMap.put(quest.getName(), data);
		return data;
	}
	
	//  useful for custom quest scripts, and for the some of the datatables that are all screwed up
	private QuestData parseXmlQuestData(Quest quest) {
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
	}
	
	private String convertToSharedFile(String template) {
		String[] split = template.split("/");
		String file = split[split.length - 1];

		return template.replace(file, "shared_" + file);
	}
}
