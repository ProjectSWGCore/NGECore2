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

import java.util.Vector;

public class QuestData {

	private String name;

	private Vector<QuestTask> tasks;
	private int tasksAvailableId;
	
	public QuestData() { }
	
	public QuestData(String questName) {

		if (questName.endsWith(".qst")) { 

			if (questName.contains("/")) {
				String[] split = questName.split("/");
				questName = split[split.length - 1];
			}
			
			questName = questName.split(".qst")[0];
		}
		
		this.name = questName;
		this.tasks = new Vector<QuestTask>();
	}
	
	public String getName() {
		return name;
	}

	public Vector<QuestTask> getTasks() {
		return tasks;
	}

	public int getTasksAvailableId() {
		return tasksAvailableId;
	}

	public void setTasksAvailableId(int tasksAvailableId) {
		this.tasksAvailableId = tasksAvailableId;
	}

}
