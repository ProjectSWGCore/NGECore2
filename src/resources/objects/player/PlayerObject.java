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
package resources.objects.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resources.objects.waypoint.WaypointObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.DraftSchematic;
import engine.resources.objects.Quest;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class PlayerObject extends SWGObject {
	
	// PLAY 3
	
	private String title;
	private String profession;
	private List<Integer> flagsList = new ArrayList<Integer>();
	private List<Integer> profileList = new ArrayList<Integer>();
	private int bornDate = 0;
	private int totalPlayTime = 0;
	
	// PLAY 6
	
	private String home;	// Player City Name
	
	// PLAY 8
	
	private Map<String, Integer> xpList = new HashMap<String, Integer>();
	@NotPersistent
	private int xpListUpdateCounter = 0;

	private List<WaypointObject> waypoints = new ArrayList<WaypointObject>();
	@NotPersistent
	private int waypointListUpdateCounter = 0;

	private int currentForcePower = 0;	// unused in NGE
	private int maxForcePower = 0;		// unused in NGE
	
	private List<Byte> currentFSQuestList = new ArrayList<Byte>();	// unused in NGE
	private List<Byte> completedFSQuestList = new ArrayList<Byte>();	// unused in NGE
	
	private List<Quest> questJournal = new ArrayList<Quest>();
	@NotPersistent
	private int questJournalUpdateCounter = 0;

	private String professionWheelPosition;
	
	// PLAY 9
	
	private int experimentationFlag = 0;
	private int craftingStage = 0;
	private long nearestCraftingStation = 0;
	
	private List<DraftSchematic> draftSchematicList = new ArrayList<DraftSchematic>();
	@NotPersistent
	private int draftSchematicListUpdateCounter = 0;

	private int experimentationPoints = 0;
	private int accomplishmentCounter = 0;
	
	private List<String> friendList = new ArrayList<String>();
	@NotPersistent
	private int friendListUpdateCounter = 0;
	private List<String> ignoreList = new ArrayList<String>();
	@NotPersistent
	private int ignoreListUpdateCounter = 0;
	
	private int languageId = 0;			// unused in NGE
	private int currentStomach = 0;		// unused in NGE
	private int maxStomach = 0;			// unused in NGE
	private int currentDrink = 0;		// unused in NGE
	private int maxDrink = 0;			// unused in NGE
	private int currentConsumable = 0;	// unused
	private int maxConsumable = 0;		// unused

	// TODO: research new NGE vars between maxConsumable and jediState
	
	private int jediState = 0; 			// unused in NGE
	
	@NotPersistent
	private PlayerMessageBuilder messageBuilder;
	
	@NotPersistent
	private long lastPlayTimeUpdate = System.currentTimeMillis();
	
	
	
	public PlayerObject() {
		super();
		messageBuilder = new PlayerMessageBuilder(this);
	}
	
	public PlayerObject(long objectID, Planet planet) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0), "object/player/shared_player.iff");
		messageBuilder = new PlayerMessageBuilder(this);
	}

	public String getTitle() {
		synchronized(objectMutex) {
			return title;
		}
	}

	public void setTitle(String title) {
		synchronized(objectMutex) {
			this.title = title;
		}
		
		notifyObservers(messageBuilder.buildTitleDelta(title), true);
	}

	public String getProfession() {
		synchronized(objectMutex) {
			return profession;
		}
	}

	public void setProfession(String profession) {
		synchronized(objectMutex) {
			this.profession = profession;
		}
	}

	public List<Integer> getFlagsList() {
		return flagsList;
	}

	public List<Integer> getProfileList() {
		return profileList;
	}

	public int getBornDate() {
		synchronized(objectMutex) {
			return bornDate;
		}
	}

	public void setBornDate(int bornDate) {
		synchronized(objectMutex) {
			this.bornDate = bornDate;
		}
	}

	public int getTotalPlayTime() {
		synchronized(objectMutex) {
			return totalPlayTime;
		}
	}

	public void setTotalPlayTime(int totalPlayTime) {
		synchronized(objectMutex) {
			this.totalPlayTime = totalPlayTime;
		}
		getContainer().notifyObservers(messageBuilder.buildTotalPlayTimeDelta(totalPlayTime), true);
	}

	public String getHome() {
		synchronized(objectMutex) {
			return home;
		}
	}

	public void setHome(String home) {
		synchronized(objectMutex) {
			this.home = home;
		}
	}

	public Map<String, Integer> getXpList() {
		return xpList;
	}

	public List<WaypointObject> getWaypoints() {
		return waypoints;
	}

	public int getWaypointListUpdateCounter() {
		synchronized(objectMutex) {
			return waypointListUpdateCounter;
		}
	}
	
	public void setWaypointListUpdateCounter(int count) {
		synchronized(objectMutex){
			this.waypointListUpdateCounter = count;
		}
	}
	
	public void waypointUpdate(WaypointObject waypoint) {
		synchronized(objectMutex) {
			getContainer().getClient().getSession().write(messageBuilder.buildWaypointUpdateDelta(waypoint));
		}
	}
	
	public void waypointRemove(WaypointObject waypoint) {
		synchronized(objectMutex) {
			getContainer().getClient().getSession().write(messageBuilder.buildWaypointRemoveDelta(waypoint));
		}
	}
	
	public void waypointAdd(WaypointObject waypoint) {
		synchronized(objectMutex) {
			getContainer().getClient().getSession().write(messageBuilder.buildWaypointAddDelta(waypoint));
		}
	}
	
	public WaypointObject getWaypointFromList(WaypointObject waypoint) {
		synchronized(objectMutex) {
			for(WaypointObject wp : waypoints) {
				if(wp.getObjectID() == waypoint.getObjectID())
					return wp;
			}
		}
		return null;
	}
	
	public int getCurrentForcePower() {
		synchronized(objectMutex) {
			return currentForcePower;
		}
	}

	public void setCurrentForcePower(int currentForcePower) {
		synchronized(objectMutex) {
			this.currentForcePower = currentForcePower;
		}
	}

	public int getMaxForcePower() {
		synchronized(objectMutex) {
			return maxForcePower;
		}
	}

	public void setMaxForcePower(int maxForcePower) {
		synchronized(objectMutex) {
			this.maxForcePower = maxForcePower;
		}
	}

	public List<Byte> getCurrentFSQuestList() {
		return currentFSQuestList;
	}

	public List<Byte> getCompletedFSQuestList() {
		return completedFSQuestList;
	}

	public List<Quest> getQuestJournal() {
		return questJournal;
	}

	public String getProfessionWheelPosition() {
		synchronized(objectMutex) {
			return professionWheelPosition;
		}
	}

	public void setProfessionWheelPosition(String professionWheelPosition) {
		synchronized(objectMutex) {
			this.professionWheelPosition = professionWheelPosition;
		}
	}

	public int getExperimentationFlag() {
		synchronized(objectMutex) {
			return experimentationFlag;
		}
	}

	public void setExperimentationFlag(int experimentationFlag) {
		synchronized(objectMutex) {
			this.experimentationFlag = experimentationFlag;
		}
	}

	public int getCraftingStage() {
		synchronized(objectMutex) {
			return craftingStage;
		}
	}

	public void setCraftingStage(int craftingStage) {
		synchronized(objectMutex) {
			this.craftingStage = craftingStage;
		}
	}

	public long getNearestCraftingStation() {
		synchronized(objectMutex) {
			return nearestCraftingStation;
		}
	}

	public void setNearestCraftingStation(long nearestCraftingStation) {
		synchronized(objectMutex) {
			this.nearestCraftingStation = nearestCraftingStation;
		}
	}

	public List<DraftSchematic> getDraftSchematicList() {
		return draftSchematicList;
	}

	public int getExperimentationPoints() {
		synchronized(objectMutex) {
			return experimentationPoints;
		}
	}

	public void setExperimentationPoints(int experimentationPoints) {
		synchronized(objectMutex) {
			this.experimentationPoints = experimentationPoints;
		}
	}

	public int getAccomplishmentCounter() {
		synchronized(objectMutex) {
			return accomplishmentCounter;
		}
	}

	public void setAccomplishmentCounter(int accomplishmentCounter) {
		synchronized(objectMutex) {
			this.accomplishmentCounter = accomplishmentCounter;
		}
	}

	public List<String> getFriendList() {
		return friendList;
	}
	
	public int getFriendListUpdateCounter() {
		synchronized(objectMutex) {
			return friendListUpdateCounter;
		}
	}
	
	public void setFriendListUpdateCounter(int friendListUpdateCounter) {
		synchronized(objectMutex) {
			this.friendListUpdateCounter = friendListUpdateCounter;
		}
	}

	public void friendAdd(String friend) {
		synchronized(objectMutex) {
			setFriendListUpdateCounter(getFriendListUpdateCounter() + 1);
			getContainer().getClient().getSession().write(messageBuilder.buildFriendAddDelta(friend));
		}
	}
	
	public void friendRemove(String friend) {
		synchronized(objectMutex) {
			setFriendListUpdateCounter(getFriendListUpdateCounter() + 1);
			getContainer().getClient().getSession().write(messageBuilder.buildFriendRemoveDelta(friend));
		}
	}
	
	public List<String> getIgnoreList() {
		return ignoreList;
	}

	public int getIgnoreListUpdateCounter() {
		synchronized(objectMutex) {
			return ignoreListUpdateCounter;
		}
	}
	
	public void setIgnoreListUpdateCounter(int ignoreListUpdateCounter) {
		synchronized(objectMutex) {
			this.ignoreListUpdateCounter = ignoreListUpdateCounter;
		}
	}

	
	public int getLanguageId() {
		synchronized(objectMutex) {
			return languageId;
		}
	}

	public void setLanguageId(int languageId) {
		synchronized(objectMutex) {
			this.languageId = languageId;
		}
	}

	public int getCurrentStomach() {
		synchronized(objectMutex) {
			return currentStomach;
		}
	}

	public void setCurrentStomach(int currentStomach) {
		synchronized(objectMutex) {
			this.currentStomach = currentStomach;
		}
	}

	public int getMaxStomach() {
		synchronized(objectMutex) {
			return maxStomach;
		}
	}

	public void setMaxStomach(int maxStomach) {
		synchronized(objectMutex) {
			this.maxStomach = maxStomach;
		}
	}

	public int getCurrentDrink() {
		synchronized(objectMutex) {
			return currentDrink;
		}
	}

	public void setCurrentDrink(int currentDrink) {
		synchronized(objectMutex) {
			this.currentDrink = currentDrink;
		}
	}

	public int getMaxDrink() {
		synchronized(objectMutex) {
			return maxDrink;
		}
	}

	public void setMaxDrink(int maxDrink) {
		synchronized(objectMutex) {
			this.maxDrink = maxDrink;
		}
	}

	public int getCurrentConsumable() {
		synchronized(objectMutex) {
			return currentConsumable;
		}
	}

	public void setCurrentConsumable(int currentConsumable) {
		synchronized(objectMutex) {
			this.currentConsumable = currentConsumable;
		}
	}

	public int getMaxConsumable() {
		synchronized(objectMutex) {
			return maxConsumable;
		}
	}

	public void setMaxConsumable(int maxConsumable) {
		synchronized(objectMutex) {
			this.maxConsumable = maxConsumable;
		}
	}

	public int getJediState() {
		synchronized(objectMutex) {
			return jediState;
		}
	}

	public void setJediState(int jediState) {
		synchronized(objectMutex) {
			this.jediState = jediState;
		}
	}

	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null)
			return;
		
		//if(destination.getParent().getObjectID() == getParentId()) {				// only send to self
			destination.getSession().write(messageBuilder.buildBaseline3());
			destination.getSession().write(messageBuilder.buildBaseline6());
			destination.getSession().write(messageBuilder.buildBaseline8());
			destination.getSession().write(messageBuilder.buildBaseline9());
		//}


	}

	public long getLastPlayTimeUpdate() {
		synchronized(objectMutex) {
			return lastPlayTimeUpdate;
		}
	}

	public void setLastPlayTimeUpdate(long lastPlayTimeUpdate) {
		synchronized(objectMutex) {
			this.lastPlayTimeUpdate = lastPlayTimeUpdate;
		}
	}
	
}
