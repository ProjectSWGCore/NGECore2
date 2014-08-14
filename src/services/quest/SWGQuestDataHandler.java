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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SWGQuestDataHandler extends DefaultHandler {
	
	private QuestData dataObj;
	
	public SWGQuestDataHandler(QuestData dataObj) {
		this.dataObj = dataObj;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		// These are all the currently known elements in the Quest.
		switch (qName) {
		
		case "tasks":
			dataObj.setTasksAvailableId(Integer.parseInt(attributes.getValue("availableId")));
			break;
		
		case "task":
			dataObj.getTasks().add(new QuestTask(attributes.getValue("type"), Integer.parseInt(attributes.getValue("id"))));
			break;
		
		case "data":
			parseTaskData(qName, attributes);
			break;
		
		case "list":
			parseListData(qName, attributes);
			break;
		
		case "quest":
			// TODO: Find out if there are more than 1 versions of quest (Right now just know 1.0)
			break;

		default:
			System.out.println("Couldn't find what to do with element " + qName);
			writeLog("No case found for elment: " + qName);
			break;
		}

	}
	
	private void parseListData(String qName, Attributes attributes) {
		// TODO: Parse list data
	}
	
	private void parseTaskData(String qName, Attributes attributes) {
		QuestTask task = dataObj.getTasks().get(dataObj.getTasks().size() - 1); // get newest task
		
		// This is by no means the full list of data. To add more task data, put in the value for "name" from .qst file as the case and set appropriate variable.
		switch (attributes.getValue("name")) {
		
		case "journalEntryTitle":
			task.setJournalEntryTitle(attributes.getValue("value"));
			break;
		
		case "journalEntryDescription":
			task.setJournalEntryDescription(attributes.getValue("value"));
			break;
		
		case "isVisible":
			task.setVisible(Boolean.parseBoolean(attributes.getValue("value")));
			break;
		
		case "allowRepeats":
			task.setAllowRepeats(Boolean.parseBoolean(attributes.getValue("value")));
			break;
			
		case "taskName":
			task.setName(attributes.getValue("value"));
			break;
			
		case "showSystemMessages":
			task.setShowSystemMessages(Boolean.parseBoolean(attributes.getValue("value")));
			break;
			
		case "grantQuestOnComplete":
			task.setGrantQuestOnComplete(attributes.getValue("value"));
			break;
			
		case "grantQuestOnCompleteShowSystemMessage":
			task.setGrantQuestOnCompleteShowSystemMessage(Boolean.parseBoolean(attributes.getValue("value")));
			break;
			
		case "grantQuestOnFail":
			task.setGrantQuestOnFail(attributes.getValue("value"));
			break;
			
		case "grantQuestOnFailShowSystemMessage":
			task.setGrantQuestOnFailShowSystemMessage(Boolean.parseBoolean(attributes.getValue("value")));
			break;
			
		case "createWaypoint":
			task.setCreatesWaypoint(Boolean.parseBoolean(attributes.getValue("value")));
			break;
			
		case "Planet":
			task.setPlanet(attributes.getValue("value"));
			break;
			
		case "LocationX(m)":
			task.setLocationX(Float.parseFloat(attributes.getValue("value")));
			break;
			
		case "LocationY(m)":
			task.setLocationY(Float.parseFloat(attributes.getValue("value")));
			break;
			
		case "LocationZ(m)":
			task.setLocationZ(Float.parseFloat(attributes.getValue("value")));
			break;
			
		case "interiorWaypointAppearance":
			task.setInteriorWaypointAppearance(attributes.getValue("value"));
			break;
		
		case "buildingCellName":
			task.setBuildingCellName(attributes.getValue("value"));
			break;
			
		case "waypointName":
			task.setWaypointName(attributes.getValue("value"));
			break;
			
		case "Signal Name":
			task.setSignalName(attributes.getValue("value"));
			break;
			
		case "signalsOnFail":
			task.setSignalsOnFail(attributes.getValue("value"));
			break;
			
		case "signalsOnComplete":
			task.setSignalsOnComplete(attributes.getValue("value"));
			break;
			
		case "Comm Message Text":
			task.setCommMessageText(attributes.getValue("value"));
			break;
			
		case "NPC Appearance Server Template":
			task.setCommAppearanceTemplate(attributes.getValue("value"));
			break;
			
		default:
			break;
		}
	}
	
	private void writeLog(String message) {
		BufferedWriter writer;
		try {
			writer = Files.newBufferedWriter(Paths.get("./logs/Quest" + ".txt"), StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
			PrintWriter out = new PrintWriter(writer);
			out.println(message);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
