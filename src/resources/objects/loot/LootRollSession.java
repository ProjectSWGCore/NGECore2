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
package resources.objects.loot;

import java.util.ArrayList;
import java.util.List;

import engine.resources.scene.Planet;
import main.NGECore;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.tangible.TangibleObject;

/** 
 * @author Charon 
 */

public class LootRollSession {
	
	private String SessionID; // leaderName-SystemTime
	private GroupObject playerGroup; 
	private List<TangibleObject> droppedItems;
	private Planet sessionPlanet;
	private List<String> errorMessages; 
	
	public LootRollSession(){	
	}
	
	public LootRollSession(CreatureObject requester){
		long requesterGroupId = requester.getGroupId();
		if (requesterGroupId>0){
			this.playerGroup = (GroupObject) NGECore.getInstance().objectService.getObject(requesterGroupId);
			this.SessionID = playerGroup.getGroupLeader().getCustomName()+"-"+System.currentTimeMillis();
		} else {
			this.SessionID = requester.getCustomName()+"-"+System.currentTimeMillis();
		}
		droppedItems = new ArrayList<TangibleObject>();
		errorMessages = new ArrayList<String>();
		sessionPlanet = requester.getPlanet();
	}

	public List<TangibleObject> getDroppedItems() {
		return droppedItems;
	}

	public void addDroppedItem(TangibleObject droppedItem) {
		this.droppedItems.add(droppedItem);
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}
	
	public void generateSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public Planet getSessionPlanet() {
		return sessionPlanet;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void addErrorMessage(String errorMessage) {
		this.errorMessages.add(errorMessage);
	}
}
