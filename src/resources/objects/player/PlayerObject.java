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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;

import resources.craft.DraftSchematic;
import resources.datatables.AdminTag;
import resources.datatables.Citizenship;
import resources.datatables.Professions;
import resources.gcw.RegionDefender;
import resources.harvest.SurveyTool;
import resources.objects.SWGList;
import resources.objects.SWGMap;
import resources.objects.SWGSet;
import resources.objects.intangible.IntangibleObject;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.waypoint.WaypointObject;
import resources.quest.Quest;
import services.quest.QuestItem;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.common.StringUtilities;
import engine.resources.objects.Baseline;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class PlayerObject extends IntangibleObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private transient PlayerMessageBuilder messageBuilder;
	
	private transient long lastPlayTimeUpdate = System.currentTimeMillis();
	
	private transient WaypointObject lastSurveyWaypoint;
	private transient SurveyTool lastUsedSurveyTool;
	private transient ResourceContainerObject recentContainer;
	
	private transient boolean callingCompanion = false;
	private long bountyMissionId; // Bad because you could have 2 missions
	
	public PlayerObject(long objectID, Planet planet) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0), "object/player/shared_player.iff");
	}
	
	public PlayerObject() {
		super();
	}
	
	@Override
	public void initAfterDBLoad() {
		super.init();
		lastPlayTimeUpdate = System.currentTimeMillis();
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		baseline.put("ownedVendors", new Vector<Long>());
		baseline.put("chatChannels", new ArrayList<Integer>());
		baseline.put("factionStandingMap", new TreeMap<String, Integer>());
		baseline.put("biography", "");
		baseline.put("titleList", new ArrayList<String>());
		baseline.put("spouse", "");
		baseline.put("bindLocation", (long) 0);
		baseline.put("lotsRemaining", 10);
		baseline.put("holoEmote", "");
		baseline.put("holoEmoteUses", 0);
		baseline.put("activeMissions", new ArrayList<Long>()); // TODO: Look at MissionCriticalObject in CREO4, could use that instead of this
		baseline.put("questRetrieveItemTemplates", new TreeMap<String, QuestItem>());
		baseline.put("activeQuestName", "");
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		List<Integer> flagsList = new ArrayList<Integer>();
		flagsList.add(0);
		flagsList.add(0);
		flagsList.add(0);
		flagsList.add(0);
		baseline.put("flagsList", flagsList);
		List<Integer> profileFlagsList = new ArrayList<Integer>();
		profileFlagsList.add(0);
		profileFlagsList.add(0);
		profileFlagsList.add(0);
		profileFlagsList.add(0);
		baseline.put("profileFlagsList", profileFlagsList);
		baseline.put("title", "");
		baseline.put("bornDate", (int) TimeUnit.DAYS.convert((System.currentTimeMillis() - 978220800000L), TimeUnit.MILLISECONDS));
		baseline.put("totalPlayTime", 0);
		baseline.put("professionIcon", 0);
		baseline.put("profession", "trader_0a");
		baseline.put("gcwPoints", 0);
		baseline.put("pvpKills", 0);
		baseline.put("lifetimeGcwPoints", (long) 0);
		baseline.put("lifetimePvpKills", 0);
		baseline.put("collections", new BitSet()); 
		baseline.put("guildRanks", new BitSet());  //"17", new SWGList<Byte>(this, 3, 17, false)); // Misc ints (2 ints in tutorial, 5 bytes other times...)
		baseline.put("showHelmet", true);
		baseline.put("showBackpack", true);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("adminTag", (byte) AdminTag.NONE);
		baseline.put("currentRank", 0);
		baseline.put("rankProgress", (float) 0);
		baseline.put("highestRebelRank", 0);
		baseline.put("highestImperialRank", 0);
		baseline.put("nextUpdateTime", 0);
		baseline.put("home", "");
		baseline.put("citizenship", Citizenship.Homeless);
		baseline.put("cityRegionDefender", new RegionDefender());
		baseline.put("guildRegionDefender", new RegionDefender());
		baseline.put("12", (long) 0); // General?
		baseline.put("13", 0); // Guild Rank Title?
		baseline.put("14", (short) 0); // Citizen Rank Title? 6 bytes
		baseline.put("speederElevation", 1);
		baseline.put("vehicleAttackCommand", ""); 
		return baseline;
	}
	
	@Override
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		baseline.put("xpList", new SWGMap<String, Integer>(this, 8, 0, true));
		baseline.put("waypoints", new SWGMap<Long, WaypointObject>(this, 8, 1, true));
		baseline.put("currentForcePower", 100);
		baseline.put("maxForcePower", 100);
		baseline.put("currentFSQuestList", new SWGList<Byte>(this, 8, 4, false));
		baseline.put("completedFSQuestList", new SWGList<Byte>(this, 8, 5, false));
		baseline.put("activeQuest", 0);
		baseline.put("questJournal", new SWGMap<Integer, Quest>(this, 8, 7, true));
		baseline.put("professionWheelPosition", "");
		return baseline;
	}
	
	@Override
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		baseline.put("experimentationFlag", 0);
		baseline.put("craftingStage", 0);
		baseline.put("nearestCraftingStation", (long) 0);
		baseline.put("draftSchematicList", new SWGList<DraftSchematic>(this, 9, 3, true));
		baseline.put("4", new SWGList<Byte>(this, 9, 4, false)); // Might or might not be a list, but at the very least it's two 0 ints that are part of the same delta.
		baseline.put("experimentationPoints", 0);
		baseline.put("accomplishmentCounter", 0);
		baseline.put("friendList", new SWGList<String>(this, 9, 7, false));
		baseline.put("ignoreList", new SWGList<String>(this, 9, 8, false));
		baseline.put("languageId", 0);
		baseline.put("currentStomach", 0);
		baseline.put("maxStomach", 100);
		baseline.put("currentDrink", 0);
		baseline.put("maxDrink", 100);
		baseline.put("currentConsumable", 0);
		baseline.put("maxConsumable", 100);
		baseline.put("waypointList", new SWGMap<Long, WaypointObject>(this, 9, 16, false));
		baseline.put("defendersList", new SWGSet<Long>(this, 9, 17, false));
		baseline.put("killMeterPoints", 0);
		baseline.put("19", 0);
		baseline.put("pet", (long) 0);
		baseline.put("petAbilities", new SWGList<String>(this, 9, 21, false));
		baseline.put("activePetAbilities", new SWGList<String>(this, 9, 22, false));
		baseline.put("23", (byte) 0); // Seen as 1 or 2 sometimes // Gets set to 0x02 sometimes
		baseline.put("24", 0); // Seen as 4
		baseline.put("25", (long) 0); // Bitmask starts with 0x20 ends with 0x40
		baseline.put("26", (long) 0); // Changed from 6 bytes to 9
		baseline.put("27", (byte) 0); // Changed from 6 bytes to 9
		baseline.put("28", (long) 0); // Seen as 856
		baseline.put("29", (long) 0); // Seen as 8559
		baseline.put("residenceTime", 0); // Assumption.  Date format of some sort.  Seen as Saturday 28th May 2011
		return baseline;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getFlagsList() {
		return (List<Integer>) getBaseline(3).get("flagsList");
	}
	
	public void setFlagsList(int flag1, int flag2, int flag3, int flag4) {
		List<Integer> flagsList = getFlagsList();
		flagsList.set(0, flag1);
		flagsList.set(1, flag2);
		flagsList.set(2, flag3);
		flagsList.set(3, flag4);
		notifyClients(getBaseline(3).set("flagsList", flagsList), true);
	}
	
	public int getFlagBitmask() {
		return getFlagsList().get(0);
	}
	
	public void setFlagBitmask(int flagBitmask) {
		getFlagsList().set(0, getFlagsList().get(0) | flagBitmask);
		notifyClients(getBaseline(3).set("flagsList", getFlagsList()), true);
	}
	
	public void clearFlagBitmask(int flagBitmask) {
		getFlagsList().set(0, (getFlagsList().get(0) & ~flagBitmask));
		notifyClients(getBaseline(3).set("flagsList", getFlagsList()), true);
	}
	
	public boolean isSet(int flags) {
		return ((getFlagsList().get(0) & flags) == flags);
	}
	
	public void toggleFlag(int flag) {
		if ((getFlagsList().get(0) & flag) == flag) {
			clearFlagBitmask(flag);
		} else {
			setFlagBitmask(flag);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getProfileFlagsList() {
		return (List<Integer>) getBaseline(3).get("profileFlagsList");
	}
	
	@Deprecated
	public List<Integer> getProfileList() {
		return getProfileFlagsList();
	}
	
	public void setProfileFlagsList(int flag1, int flag2, int flag3, int flag4) {
		List<Integer> profileFlagsList = getProfileFlagsList();
		profileFlagsList.set(0, flag1);
		profileFlagsList.set(1, flag2);
		profileFlagsList.set(2, flag3);
		profileFlagsList.set(3, flag4);
		notifyClients(getBaseline(3).set("profileFlagsList", profileFlagsList), true);
	}
	
	public String getBiography() {
		return (String) otherVariables.get("biography");
	}
	
	public void setBiography(String biography) {
		otherVariables.set("biography", biography);
	}
	
	public String getTitle() {
		return (String) getBaseline(3).get("title");
	}
	
	public void setTitle(String title) {
		if (!getTitleList().isEmpty() && getTitleList().contains(title)) {
			notifyClients(getBaseline(3).set("title", title), true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getTitleList() {
		return (List<String>) otherVariables.get("titleList");
	}
	
	public void setTitleList(List<String> titleList) {
		otherVariables.set("titleList", titleList);
	}
	
	public int getBornDate() {
		return (int) getBaseline(3).get("bornDate");
	}
	
	public void setBornDate(int bornDate) {
		getBaseline(3).set("bornDate", bornDate);
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
	
	public int getTotalPlayTime() {
		return (int) getBaseline(3).get("totalPlayTime");
	}
	
	public void setTotalPlayTime(int totalPlayTime) {
		if (getContainer() != null && getContainer().getClient() != null) {
			getContainer().getClient().getSession().write(getBaseline(3).set("totalPlayTime", totalPlayTime));
		}
	}
	
	public int getProfessionIcon() {
		return (int) getBaseline(3).get("professionIcon");
	}
	
	public void setProfessionIcon(int professionIcon) {
		notifyClients(getBaseline(3).set("professionIcon", professionIcon), true);
	}
	
	public String getProfession() {
		return (String) getBaseline(3).get("profession");
	}
	
	public void setProfession(String profession) {
		notifyClients(getBaseline(3).set("profession", profession), true);
		setProfessionIcon(Professions.get(profession));
	}
	
	public int getGcwPoints() {
		return (int) getBaseline(3).get("gcwPoints");
	}
	
	public void setGcwPoints(int gcwPoints) {
		notifyClients(getBaseline(3).set("gcwPoints", gcwPoints), true);
	}
	
	public void resetGcwPoints() {
		notifyClients(getBaseline(3).set("gcwPoints", 0), true);
	}
	
	public int getPvpKills() {
		return (int) getBaseline(3).get("pvpKills");
	}
	
	public void addPvpKill() {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = getBaseline(3).set("pvpKills", (((int) getBaseline(3).get("pvpKills")) + 1));
		}
		
		notifyClients(buffer, true);
	}
	
	public void resetPvpKills() {
		notifyClients(getBaseline(3).set("pvpKills", 0), true);
	}
	
	public long getLifetimeGcwPoints() {
		return (long) getBaseline(3).get("lifetimeGcwPoints");
	}
	
	public void addLifetimeGcwPoints(long gcwPoints) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = getBaseline(3).set("lifetimeGcwPoints", (((long) getBaseline(3).get("lifetimeGcwPoints")) + gcwPoints));
		}
		
		notifyClients(buffer, true);
	}
	
	public int getLifetimePvpKills() {
		return (int) getBaseline(3).get("lifetimePvpKills");
	}
	
	public void addLifetimePvpKills(int pvpKills) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = getBaseline(3).set("lifetimePvpKills", (((int) getBaseline(3).get("lifetimePvpKills")) + pvpKills));
		}
		
		notifyClients(buffer, true);
	}
	
	public byte[] getCollections() {
		return ((BitSet) getBaseline(3).get("collections")).toByteArray();
	}
	
	public void setCollections(byte[] collections) {
		getBaseline(3).set("collections", BitSet.valueOf(collections));
	}
	
	public int getHighestSetBit() {
		return ((BitSet) getBaseline(3).get("collections")).length();
	}
	
	public boolean getGuildRank(int slotIndex) {
		return ((BitSet) getBaseline(3).get("guildRanks")).get(slotIndex);
	}
	
	public void toggleGuildRank(int slotIndex) {
		BitSet guildRanks = (BitSet) getBaseline(3).get("guildRanks");
		guildRanks.set(slotIndex, !guildRanks.get(slotIndex));
		getBaseline(3).set("guildRanks", guildRanks);
	}
	
	public void clearGuildRanks() {
		BitSet guildRanks = (BitSet) getBaseline(3).get("guildRanks");
		guildRanks.clear();
		getBaseline(3).set("guildRanks", guildRanks);
	}
	
	public boolean isShowingHelmet() {
		return (boolean) getBaseline(3).get("showHelmet");
	}
	
	public void setShowHelmet(boolean showHelmet) {
		notifyClients(getBaseline(3).set("showHelmet", showHelmet), true);
	}
	
	public boolean isShowingBackpack() {
		return (boolean) getBaseline(3).get("showBackpack");
	}
	
	public void setShowBackpack(boolean showBackpack) {
		notifyClients(getBaseline(3).set("showBackpack", showBackpack), true);
	}
	
	public byte getAdminTagValue() {
		return (byte) getBaseline(6).get("adminTag");
	}
	
	public void setAdminTagValue(byte adminTag) {
		notifyClients(getBaseline(6).set("adminTag", (byte) adminTag), true);
	}
	
	public int getCurrentRank() {
		synchronized(objectMutex) {
			return (int) getBaseline(6).get("currentRank");
		}
	}
	
	public void setCurrentRank(int currentRank) {
		notifyClients(getBaseline(6).set("currentRank", currentRank), true);
	}
	
	public float getRankProgress() {
		return (float) getBaseline(6).get("rankProgress");
	}
	
	public void setRankProgress(float rankProgress) {
		notifyClients(getBaseline(6).set("rankProgress", rankProgress), true);
	}
	
	public int getHighestRebelRank() {
		return (int) getBaseline(6).get("highestRebelRank");
	}
	
	public void setHighestRebelRank(int highestRebelRank) {
		notifyClients(getBaseline(6).set("highestRebelRank", highestRebelRank), true);
	}
	
	public int getHighestImperialRank() {
		return (int) getBaseline(6).get("highestImperialRank");
	}
	
	public void setHighestImperialRank(int highestImperialRank) {
		notifyClients(getBaseline(6).set("highestImperialRank", highestImperialRank), true);
	}
	
	public int getNextUpdateTime() {
		return (int) getBaseline(6).get("nextUpdateTime");
	}
	
	public void setNextUpdateTime(int nextUpdateTime) {
		notifyClients(getBaseline(6).set("nextUpdateTime", nextUpdateTime), true);
	}
	
	public String getHome() {
		return (String) getBaseline(6).get("home");
	}
	
	public void setHome(String home) {
		notifyClients(getBaseline(6).set("home", home), true);
	}
	
	public byte getCitizenship() {
		return (byte) getBaseline(6).get("citizenship");
	}
	
	public void setCitizenship(byte citizenship) {
		notifyClients(getBaseline(6).set("citizenship", citizenship), true);
	}
	
	public RegionDefender getCityRegionDefender() {
		return (RegionDefender) getBaseline(6).get("cityRegionDefender");
	}
	
	public void setCityRegionDefender(RegionDefender cityRegionDefender) {
		notifyClients(getBaseline(6).set("cityRegionDefender", cityRegionDefender), true);
	}
	
	public RegionDefender getGuildRegionDefender() {
		return (RegionDefender) getBaseline(6).get("guildRegionDefender");
	}
	
	public void setGuildRegionDefender(RegionDefender guildRegionDefender) {
		notifyClients(getBaseline(6).set("guildRegionDefender", guildRegionDefender), true);
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, Integer> getXpList() {
		return (SWGMap<String, Integer>) getBaseline(8).get("xpList");
	}
	
	public int getXp(String type) {
		return ((!getXpList().containsKey(type)) ? 0 : getXpList().get(type));
	}
	
	public void setXp(String type, int amount) {
		getXpList().put(type, amount);
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<Long, WaypointObject> getWaypoints() {
		return (SWGMap<Long, WaypointObject>) getBaseline(8).get("waypoints");
	}
	
	public void waypointUpdate(WaypointObject waypoint) {
		getWaypoints().put(waypoint.getObjectID(), waypoint);
	}
	
	public void waypointRemove(WaypointObject waypoint) {
		getWaypoints().remove(waypoint.getObjectID());
	}
	
	public void waypointAdd(WaypointObject waypoint) {
		getWaypoints().put(waypoint.getObjectID(), waypoint);
	}
	
	public WaypointObject getWaypointFromList(WaypointObject waypoint) {
		synchronized(objectMutex) {
			if (getWaypoints().containsKey(waypoint.getObjectID())) {
				return getWaypoints().get(waypoint.getObjectID());
			}
		}
		
		return null;
	}
	
	public int getCurrentForcePower() {
		return (int) getBaseline(8).get("currentForcePower");
	}
	
	public void setCurrentForcePower(int currentForcePower) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(8).set("currentForcePower", currentForcePower));
		}
	}
	
	public int getMaxForcePower() {
		return (int) getBaseline(8).get("maxForcePower");
	}
	
	public void setMaxForcePower(int maxForcePower) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(8).set("maxForcePower", maxForcePower));
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Byte> getCurrentFSQuestList() {
		return (SWGList<Byte>) getBaseline(8).get("currentFSQuestList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Byte> getCompletedFSQuestList() {
		return (SWGList<Byte>) getBaseline(8).get("completedFSQuestList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<Integer, Quest> getQuestJournal() {
		return (SWGMap<Integer, Quest>) getBaseline(8).get("questJournal");
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap<String, QuestItem> getQuestRetrieveItemTemplates() {
		return (TreeMap<String, QuestItem>) otherVariables.get("questRetrieveItemTemplates");
	}
	
	public int getActiveQuest() {
		return (int) getBaseline(8).get("activeQuest");
	}
	
	public String getActiveQuestName() {
		return (String) otherVariables.get("activeQuestName");
	}
	
	public void setActiveQuest(String questName) {
		otherVariables.set("activeQuestName", questName);
		getBaseline(8).set("activeQuest", CRC.StringtoCRC("quest/" + questName));
	}
	
	public Quest getQuest(String questName) {
		return getQuestJournal().get(CRC.StringtoCRC("quest/" + questName));
	}
	
	public Quest getQuest(int questCrc) {
		return getQuestJournal().get(questCrc);
	}
	
	public String getProfessionWheelPosition() {
		return (String) getBaseline(8).get("professionWheelPosition");
	}
	
	public void setProfessionWheelPosition(String professionWheelPosition) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(8).set("professionWheelPosition", professionWheelPosition));
		}
	}
	
	public int getExperimentationFlag() {
		return (int) getBaseline(9).get("experimentationFlag");
	}
	
	public void setExperimentationFlag(int experimentationFlag) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("experimentationFlag", experimentationFlag));
		}
	}
	
	public int getCraftingStage() {
		return (int) getBaseline(9).get("craftingStage");
	}
	
	public void setCraftingStage(int craftingStage) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("craftingStage", craftingStage));
		}
	}
	
	public long getNearestCraftingStation() {
		return (long) getBaseline(9).get("nearestCraftingStation");
	}
	
	public void setNearestCraftingStation(long nearestCraftingStation) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("nearestCraftingStation", nearestCraftingStation));
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<DraftSchematic> getDraftSchematicList() {
		return (SWGList<DraftSchematic>) getBaseline(9).get("draftSchematicList");
	}
	
	public int getExperimentationPoints() {
		return (int) getBaseline(9).get("experimentationPoints");
	}
	
	public void setExperimentationPoints(int experimentationPoints) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("experimentationPoints", experimentationPoints));
		}
	}
	
	public int getAccomplishmentCounter() {
		return (int) getBaseline(9).get("accomplishmentCounter");
	}
	
	public void setAccomplishmentCounter(int accomplishmentCounter) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("accomplishmentCounter", accomplishmentCounter));
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<String> getFriendList() {
		return (SWGList<String>) getBaseline(9).get("friendList");
	}
	
	public void friendAdd(String friend) {
		getFriendList().add(friend);
	}
	
	public void friendRemove(String friend) {
		getFriendList().remove(friend);
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<String> getIgnoreList() {
		return (SWGList<String>) getBaseline(9).get("ignoreList");
	}
	
	public void ignoreAdd(String name) {
		getIgnoreList().add(name);
	}
	
	public void ignoreRemove(String name) {
		getIgnoreList().remove(name);
	}
	
	public int getLanguageId() {
		return (int) getBaseline(9).get("languageId");
	}
	
	public void setLanguageId(int languageId) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("languageId", languageId));
		}
	}
	
	public int getCurrentStomach() {
		return (int) getBaseline(9).get("currentStomach");
	}
	
	public void setCurrentStomach(int currentStomach) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("currentStomach", currentStomach));
		}
	}
	
	public int getMaxStomach() {
		return (int) getBaseline(9).get("maxStomach");
	}
	
	public void setMaxStomach(int maxStomach) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("maxStomach", maxStomach));
		}
	}
	
	public int getCurrentDrink() {
		return (int) getBaseline(9).get("currentDrink");
	}
	
	public void setCurrentDrink(int currentDrink) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("currentDrink", currentDrink));
		}
	}
	
	public int getMaxDrink() {
		return (int) getBaseline(9).get("maxDrink");
	}
	
	public void setMaxDrink(int maxDrink) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("maxDrink", maxDrink));
		}
	}
	
	public int getCurrentConsumable() {
		return (int) getBaseline(9).get("currentConsumable");
	}
	
	public void setCurrentConsumable(int currentConsumable) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("currentConsumable", currentConsumable));
		}
	}
	
	public int getMaxConsumable() {
		return (int) getBaseline(9).get("maxConsumable");
	}
	
	public void setMaxConsumable(int maxConsumable) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("maxConsumable", maxConsumable));
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGSet<Long> getDefendersList() {
		return (SWGSet<Long>) getBaseline(8).get("defendersList");
	}
	
	public int getKillMeterPoints() {
		return (int) getBaseline(9).get("killMeterPoints");
	}
	
	public void setKillMeterPoints(int points) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("killMeterPoints", points));
		}
	}
	
	public long getPet() {
		return (long) getBaseline(9).get("pet");
	}
	
	public void setPet(long pet) {
		if (getClient() != null) {
			getClient().getSession().write(getBaseline(9).set("pet", pet));
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<String> getPetAbilities() {
		return (SWGList<String>) getBaseline(9).get("petAbilities");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<String> getActivePetAbilities() {
		return (SWGList<String>) getBaseline(9).get("activePetAbilities");
	}
		
	public int getJediState() {
		return 0;
	}
	
	public void setJediState(int jediState) {
		
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Integer> getFactionStandingMap() {
		return (Map<String, Integer>) otherVariables.get("factionStandingMap");
	}
	
	public int getFactionStanding(String faction) {
		synchronized(objectMutex) {
			return ((getFactionStandingMap().containsKey(faction)) ? getFactionStandingMap().get(faction) : 0);
		}
	}
	
	public void setFactionStanding(String faction, int factionStanding) {
		synchronized(objectMutex) {
			getFactionStandingMap().put(faction, ((factionStanding < -5000) ? -5000 : ((factionStanding > 5000) ? 5000 : factionStanding)));
		}
	}
	
	public void modifyFactionStanding(String faction, int modifier) {
		synchronized(objectMutex) {
			int factionStanding = (((getFactionStandingMap().containsKey(faction)) ? getFactionStandingMap().get(faction) : 0) + modifier);
			getFactionStandingMap().put(faction, ((factionStanding < -5000) ? -5000 : ((factionStanding > 5000) ? 5000 : factionStanding)));
		}
	}
	
	public String getSpouseName() {
		return (String) otherVariables.get("spouse");
	}
	
	public void setSpouseName(String spouse) {
		otherVariables.set("spouse", spouse);
	}
	
	public long getBindLocation() {
		return (long) otherVariables.get("bindLocation");
	}
	
	public void setBindLocation(long bindLocation) {
		otherVariables.set("bindLocation", bindLocation);
	}
	
	public int getLotsRemaining() {
		return (int) otherVariables.get("lotsRemaining");
	}
	
	public void setLotsRemaining(int lotsRemaining) {
		otherVariables.set("lotsRemaining", lotsRemaining);
	}
	
	public boolean addLots(int lots) {
		otherVariables.set("lotsRemaining", getLotsRemaining() + lots);
		return true;
	}
	
	public boolean deductLots(int lots) {
		if (getLotsRemaining() - lots >= 0) {
			setLotsRemaining(getLotsRemaining() - lots);
			return true;
		}
		
		return false;
	}
	
	public String getHoloEmote() {
		return (String) otherVariables.get("holoEmote");
	}
	
	public void setHoloEmote(String holoEmote) {
		otherVariables.set("holoEmote", holoEmote);
	}
	
	public int getHoloEmoteUses() {
		return (int) otherVariables.get("holoEmoteUses");
	}
	
	public void setHoloEmoteUses(int holoEmoteUses) {
		otherVariables.set("holoEmoteUses", holoEmoteUses);
	}
	
	public WaypointObject getLastSurveyWaypoint() {
		return lastSurveyWaypoint;
	}
	
	public void setLastSurveyWaypoint(WaypointObject lastSurveyWaypoint) {
		this.lastSurveyWaypoint = lastSurveyWaypoint;
	}
	
	public SurveyTool getLastUsedSurveyTool() {
		synchronized(objectMutex) {
			return lastUsedSurveyTool;
		}
	}
	
	public void setLastUsedSurveyTool(SurveyTool lastUsedSurveyTool) {
		synchronized(objectMutex) {
			this.lastUsedSurveyTool = lastUsedSurveyTool;
		}
	}
	
	public ResourceContainerObject getRecentContainer() {
		synchronized(objectMutex) {
			return recentContainer;
		}
	}
	
	public void setRecentContainer(ResourceContainerObject recentContainer) {
		synchronized(objectMutex) {
			this.recentContainer = recentContainer;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getJoinedChatChannels() {
		return (List<Integer>) otherVariables.get("chatChannels");
	}
	
	public void addChannel(int roomId) {
		getJoinedChatChannels().add((Integer) roomId);
	}
	
	public void removeChannel(Integer roomId) {
		if (getJoinedChatChannels().contains(roomId)) {
			getJoinedChatChannels().remove(roomId);
		}
	}
	
	public boolean isMemberOfChannel(int roomId) {
		if (getJoinedChatChannels().contains(roomId)) {
			return true;
		}
		
		return false;
	}
	
	public void addActiveMission(long missionObjId) {
		getActiveMissions().add(missionObjId);
	}
	
	public void removeActiveMission(long missionObjId) {
		getActiveMissions().remove(missionObjId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> getActiveMissions() {
		return (List<Long>) otherVariables.get("activeMissions");
	}
	
	public boolean isCallingCompanion() {
		synchronized(objectMutex) {
			return callingCompanion;
		}
	}
	
	public void setCallingCompanion(boolean callingCompanion) {
		synchronized(objectMutex) {
			this.callingCompanion = callingCompanion;
		}
	}
	
	public long getBountyMissionId() {
		synchronized(objectMutex) {
			return bountyMissionId;
		}
	}
	
	public void setBountyMissionId(long bountyMissionId) {
		synchronized(objectMutex) {
			this.bountyMissionId = bountyMissionId;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Vector<Long> getOwnedVendors() {
		return (Vector<Long>) otherVariables.get("ownedVendors");
	}
	
	public void addVendor(long vendorId) {
		getOwnedVendors().add(vendorId);
	}
	
	public void removeVendor(long vendorId) {
		getOwnedVendors().remove(vendorId);
	}
	
	public int getAmountOfVendors() {
		return getOwnedVendors().size();
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, notifySelf);
		}
	}
	
	@Override
	public PlayerMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new PlayerMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(getBaseline(3).getBaseline());
			destination.getSession().write(getBaseline(6).getBaseline());
			if (destination.getParent().getObjectID() == getContainer().getObjectID()) {
				destination.getSession().write(getBaseline(8).getBaseline());
				destination.getSession().write(getBaseline(9).getBaseline());
			}
		}
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {

		switch (viewType) {
			case 3:
			case 6:
				if (getContainer() != null) {
					getContainer().notifyObservers(getBaseline(viewType).createDelta(updateType, buffer.array()), true);
				}
				
				break;
			case 8:
				switch (updateType) {
				case 7:
					IoBuffer newBuffer = getBaseline(viewType).createDelta(updateType, buffer.array());
					getContainer().getClient().getSession().write(newBuffer.array());
					break;
				}
				break;
			default:
				if (getContainer() != null && getContainer().getClient() != null) {
					getContainer().getClient().getSession().write(getBaseline(viewType).createDelta(updateType, buffer.array()));
					//StringUtilities.printBytes(getBaseline(viewType).createDelta(updateType, buffer.array()).array());
				}
		}
	}
	
}
