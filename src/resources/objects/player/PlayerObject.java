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
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import resources.objects.intangible.IntangibleObject;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.tool.SurveyTool;
import resources.objects.waypoint.WaypointObject;
import resources.objects.creature.CreatureObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.DraftSchematic;
import engine.resources.objects.Quest;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent(version=11)
public class PlayerObject extends IntangibleObject {
	
	// PLAY 3
	
	private String title;
	private String profession;
	private int professionIcon;
	private List<Integer> flagsList = new ArrayList<Integer>();
	private List<Integer> profileList = new ArrayList<Integer>();
	private List<String> titleList = new ArrayList<String>();
	private long bornDate = 0;
	private int totalPlayTime = 0;
	private byte[] collections = new byte[] { };
	private int highestSetBit = 0;
	private int flagBitmask = 0;
	private boolean showHelmet = true;
	private boolean showBackpack = true;
	
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
	
	private String biography = "";
	private String spouse;
	private String holoEmote;
	private int holoEmoteUses;
	
	private int lotsRemaining = 10;
	
	@NotPersistent
	private PlayerMessageBuilder messageBuilder;
	
	@NotPersistent
	private long lastPlayTimeUpdate = System.currentTimeMillis();
	
	private Map<String, Integer> factionStandingMap = new TreeMap<String, Integer>();
	
	private WaypointObject lastSurveyWaypoint;
	private SurveyTool lastUsedSurveyTool;
	private ResourceContainerObject recentContainer;
	
	private byte godLevel = 0;
	
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
			if(!getTitleList().isEmpty() && getTitleList().contains(title)) {
				this.title = title;
			}

		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(messageBuilder.buildTitleDelta(title), true);
		}
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

	public long getBornDate() {
		synchronized(objectMutex) {
			return bornDate;
		}
	}

	public void setBornDate(long bornDate) {
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
		//notifyObservers(messageBuilder.buildTotalPlayTimeDelta(totalPlayTime), true);
		getContainer().getClient().getSession().write(messageBuilder.buildTotalPlayTimeDelta(totalPlayTime));
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
	
	public int getXp(String type) {
		synchronized(objectMutex) {
			return ((!xpList.containsKey(type)) ? 0 : xpList.get(type));
		}
	}
	
	// Temporary
	public void setXp(String type, int amount) {
		boolean xpExists;
		
		synchronized(objectMutex) {
			xpExists = xpList.containsKey(type);
			xpList.put(type, amount);
			//Console.println("Put " + type + " exp of " + amount + " in the map.");
			//Console.println("Map is now: " + xpList.get(type).intValue());
		}
		
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildXPListDelta(type, amount, xpExists));
			((CreatureObject) getContainer()).setXpBarValue(amount);
		}
		
	}
	
	public int getXpListUpdateCounter() {
		synchronized(objectMutex) {
			return xpListUpdateCounter;
		}
	}
	
	public void setXpListUpdateCounter(int xpListUpdateCounter) {
		synchronized(objectMutex) {
			this.xpListUpdateCounter = xpListUpdateCounter;
		}
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
			getWaypoints().remove(waypoint);
		}
	}
	
	public void waypointAdd(WaypointObject waypoint) {
		synchronized(objectMutex) {
			getContainer().getClient().getSession().write(messageBuilder.buildWaypointAddDelta(waypoint));
			getWaypoints().add(waypoint);
		}
	}
	
	public WaypointObject getWaypointFromList(WaypointObject waypoint) {
		if (waypoint == null)
			return null;
		
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
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildProfessionWheelPositionDelta(professionWheelPosition));
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
			friendList.add(friend);
			getContainer().getClient().getSession().write(messageBuilder.buildFriendAddDelta(getFriendList()));
		}
	}
	
	public void friendRemove(String friend) {
		synchronized(objectMutex) {
			setFriendListUpdateCounter(getFriendListUpdateCounter() + 1);
			friendList.remove(friend);
			getContainer().getClient().getSession().write(messageBuilder.buildFriendRemoveDelta(getFriendList()));
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
	
	public void ignoreAdd(String ignoreName) {
		synchronized(objectMutex) {
			setIgnoreListUpdateCounter(getIgnoreListUpdateCounter() + 1);
			ignoreList.add(ignoreName);
			getContainer().getClient().getSession().write(messageBuilder.buildIgnoreAddDelta(getIgnoreList()));
		}
	}
	
	public void ignoreRemove(String removeName) {
		synchronized(objectMutex) {
			setIgnoreListUpdateCounter(getIgnoreListUpdateCounter() + 1);
			ignoreList.remove(removeName);
			getContainer().getClient().getSession().write(messageBuilder.buildIgnoreRemoveDelta(getIgnoreList()));
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
	
	public Map<String, Integer> getFactionStandingMap() {
		return factionStandingMap;
	}
	
	public void setFactionStanding(String faction, int factionStanding) {
		synchronized(objectMutex) {
			factionStandingMap.put(faction, ((factionStanding < -5000) ? -5000 : ((factionStanding > 5000) ? 5000 : factionStanding)));
		}
	}
	
	public void modifyFactionStanding(String faction, int modifier) {
		synchronized(objectMutex) {
			int factionStanding = (((factionStandingMap.containsKey(faction)) ? factionStandingMap.get(faction) : 0) + modifier);
			factionStandingMap.put(faction, ((factionStanding < -5000) ? -5000 : ((factionStanding > 5000) ? 5000 : factionStanding)));
		}
	}
	
	public int getFactionStanding(String faction) {
		synchronized(objectMutex) {
			return ((factionStandingMap.containsKey(faction)) ? factionStandingMap.get(faction) : 0);
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null)
			return;
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
		if(destination.getParent().getObjectID() == getParentId()) {				// only send to self
			destination.getSession().write(messageBuilder.buildBaseline8());
			destination.getSession().write(messageBuilder.buildBaseline9());
		}


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

	public List<String> getTitleList() {
		return titleList;
	}
	
	public void setTitleList(List<String> titleList) {
		this.titleList = titleList;
	}
	
	public void setCollections(byte[] collections) {
		synchronized(objectMutex) {
			this.collections = collections;
			this.highestSetBit = BitSet.valueOf(collections).length();
		}
		
		notifyObservers(messageBuilder.buildCollectionsDelta(collections, getHighestSetBit()), true);
	}
	
	public byte[] getCollections() {
		synchronized(objectMutex) {
			return collections;
		}
	}
	
	public int getHighestSetBit() {
		synchronized(objectMutex) {
			return highestSetBit;
		}
	}
	
	public int getProfessionIcon() {
		synchronized(objectMutex) {
			return professionIcon;
		}
	}
	
	public void setProfessionIcon(int professionIcon) {
		synchronized(objectMutex) {
			this.professionIcon = professionIcon;
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(messageBuilder.buildProfessionIconDelta(professionIcon), true);
		}
	}
	
	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		synchronized(objectMutex) {
			this.biography = biography;
		}
	}

	public String getSpouseName() {
		synchronized(objectMutex) {
			return spouse;
		}
	}

	public void setSpouseName(String spouse) {
		synchronized(objectMutex) {
			this.spouse = spouse;
		}
	}

	public int getFlagBitmask() {
		synchronized(objectMutex) {
			return flagBitmask;
		}
	}

	public void setFlagBitmask(int flagBitmask) {
		synchronized(objectMutex) {
			this.flagBitmask |= flagBitmask;
		}

		if (getContainer() != null) {
			getContainer().notifyObservers(messageBuilder.buildFlagBitmask(this.flagBitmask), true);
		}
	}
	public void clearFlagBitmask(int flagBitmask) {
		synchronized(objectMutex) {
			// set flag bitmask to 0
			this.flagBitmask &= ~flagBitmask;
		}

		if (getContainer() != null) {
			getContainer().notifyObservers(messageBuilder.buildFlagBitmask(this.flagBitmask), true);
		}
	}
	
	public void toggleFlag(int flag) {
		if ((this.flagBitmask & flag) == flag) {
			clearFlagBitmask(flag);
		} else {
			setFlagBitmask(flag);
		}
	}
	
	/*
	 * @return True if specified flag(s) are enabled
	 */
	public boolean isSet(int flags) {
		synchronized(objectMutex) {
			return ((flagBitmask & flags) == flags);
		}
	}

	public String getHoloEmote() {
		return holoEmote;
	}

	public void setHoloEmote(String holoEmote) {
		this.holoEmote = holoEmote;
	}

	public int getHoloEmoteUses() {
		return holoEmoteUses;
	}

	public void setHoloEmoteUses(int holoEmoteUses) {
		this.holoEmoteUses = holoEmoteUses;
	}

	public boolean isShowHelmet() {
		return showHelmet;
	}

	public void setShowHelmet(boolean showHelmet) {
		synchronized(objectMutex) {
			this.showHelmet = showHelmet;
		}
		
		if (getContainer() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildShowHelmetDelta(showHelmet));
		}
	}

	public boolean isShowBackpack() {
		return showBackpack;
	}

	public void setShowBackpack(boolean showBackpack) {
		synchronized(objectMutex) {
			this.showBackpack = showBackpack;
		}
		
		if (getContainer() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildShowBackpackDelta(showBackpack));
		}
	}
	
	public WaypointObject getLastSurveyWaypoint() {
		return lastSurveyWaypoint;
	}

	public void setLastSurveyWaypoint(WaypointObject lastSurveyWaypoint) {
		this.lastSurveyWaypoint = lastSurveyWaypoint;
	}
	
	public SurveyTool getLastUsedSurveyTool() {
		synchronized(objectMutex) {
			return this.lastUsedSurveyTool;
		}
	}
	
	public void setLastUsedSurveyTool(SurveyTool surveyTool) {
		synchronized(objectMutex) {
			this.lastUsedSurveyTool = surveyTool;
		}
	}

	public ResourceContainerObject getRecentContainer() {
		return recentContainer;
	}

	public void setRecentContainer(ResourceContainerObject recentContainer) {
		this.recentContainer = recentContainer;
	}
	
	public void setLotsRemaining(int amount)
	{
		this.lotsRemaining = amount;
	}
	
	public boolean addLots(int amount)
	{
		this.lotsRemaining += amount;
		return true;
	}
	
	public boolean deductLots(int amount)
	{
		if(this.lotsRemaining - amount >= 0)
		{
			this.lotsRemaining -= amount;
			return true;
		}
		return false;
	}
	
	public int getLotsRemaining()
	{
		return this.lotsRemaining;
	}
	
	public byte getGodLevel() {
		return godLevel;
	}
	
	public void setGodLevel(int godLevel) {
		this.godLevel = (byte) godLevel;
		
		if (getContainer() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildGodLevelDelta((byte) godLevel));
		}
	}
	
}
