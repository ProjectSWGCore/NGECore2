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
import java.nio.ByteOrder;
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
import resources.quest.Quest;
import main.NGECore;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.common.StringUtilities;
import engine.resources.objects.SWGObject;
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
		
		try {
			if (!parseQuestData(questName))
				return false;
		} catch (ParserConfigurationException | SAXException | IOException e) { e.printStackTrace(); }
		
		if (questName.endsWith(".qst"))
			questName = questName.split(".qst")[0];
		
		ProsePackage prose = new ProsePackage("@quest/ground/system_message:quest_received");
		
		// Format for stf string if quest resides in multiple folders beyond base one
		if (questName.contains("/")) {
			String[] split = questName.split("/");
			questName = split[split.length - 1];
		}
		
		prose.setToCustomString("@quest/ground/" + questName + ":journal_entry_title");
		creo.sendSystemMessage(new OutOfBand(prose), DisplayType.Quest);
		
		PlayMusicMessage music = new PlayMusicMessage("sound/ui_npe2_quest_received.snd", creo.getObjectID(), 0, true);
		creo.getClient().getSession().write(music.serialize());
		
		ghost.getQuestJournal().add(quest);
		ghost.setActiveQuest(quest.getName());
		
		return true;
	}
	
	public void sendQuestWindow(CreatureObject reciever, String questName) {
		if (questName.endsWith(".qst"))
			questName = questName.split(".qst")[0];
		
		if (!questName.startsWith("quest/"))
			questName = "quest/" + questName;
		
		ObjControllerMessage objMsg = new ObjControllerMessage(11, new ForceActivateQuest(reciever.getObjectID(), CRC.StringtoCRC(questName)));
		reciever.getClient().getSession().write(objMsg.serialize());
	}
	
	public QuestData getQuestData(String questFile) {
		if (!questFile.endsWith(".qst"))
			questFile = questFile + ".qst";
		
		if (questMap.containsKey(questFile))
			return questMap.get(questFile);
		
		else return null;
	}
	
	private boolean parseQuestData(String questFile) throws ParserConfigurationException, SAXException, IOException {

		if (!questFile.endsWith(".qst"))
			questFile = questFile + ".qst";
		
		if (questMap.containsKey(questFile)) return true;
		
		File questXml = new File("clientdata/quest/" + questFile);
		if (!questXml.exists()) return false;
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		
		QuestData dataObj = new QuestData(questFile);
		SWGQuestDataHandler questHandler = new SWGQuestDataHandler(dataObj);
		parser.parse(questXml, questHandler);
		
		questMap.put(questFile, dataObj);
		
		System.out.println("Loaded quest file " + questFile);
		return true;
	}
}
