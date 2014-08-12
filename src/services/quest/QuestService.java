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

import java.util.Map;

import protocol.swg.PlayMusicMessage;
import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.datatables.DisplayType;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.quest.Quest;
import main.NGECore;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class QuestService implements INetworkDispatch {

	@SuppressWarnings("unused")
	private NGECore core;
	
	public QuestService(NGECore core) {
		this.core = core;
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {

	}

	@Override
	public void shutdown() { }
	
	public void beginQuest(String questName, CreatureObject questReciever) {
		
	}
	
	public void adminActivateQuest(CreatureObject creo, String questName) {
		
		PlayerObject ghost = creo.getPlayerObject();
		
		if (ghost == null)
			return;
		
		if (questName.endsWith(".qst"))
			questName = questName.split(".qst")[0];
		
		Quest quest = new Quest(questName, creo.getObjectID());
		
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
		
	}
}
