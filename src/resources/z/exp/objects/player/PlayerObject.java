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
package resources.z.exp.objects.player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.mina.core.buffer.IoBuffer;

import resources.datatables.Professions;
import resources.z.exp.gcw.RegionDefender;
import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.SWGList;
import resources.z.exp.objects.SWGMap;
import resources.z.exp.objects.intangible.IntangibleObject;
import resources.z.exp.objects.waypoint.WaypointObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import resources.z.exp.craft.DraftSchematic;
import resources.z.exp.quest.Quest;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class PlayerObject extends IntangibleObject {
	
	@NotPersistent
	private PlayerMessageBuilder messageBuilder;
	
	@NotPersistent
	private long lastPlayTimeUpdate = System.currentTimeMillis();
	
	public PlayerObject(long objectID, Planet planet) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0), "object/player/shared_player.iff");
		setServerId(0x8A);
	}
	
	public PlayerObject() {
		super();
	}
	
	@Override
	public void initializeBaselines() {
		super.initializeBaselines();
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
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
		baseline.put("bornDate", Integer.parseInt(new SimpleDateFormat("yyyymmdd", Locale.ENGLISH).format(Calendar.getInstance().getTime())));
		baseline.put("totalPlayTime", 0);
		baseline.put("professionIcon", 0);
		baseline.put("profession", "trader_1a");
		baseline.put("gcwPoints", 0);
		baseline.put("pvpKills", 0);
		baseline.put("lifetimeGcwPoints", (long) 0);
		baseline.put("lifetimePvpKills", 0);
		baseline.put("16", new SWGList<Byte>(this, 3, 16, false)); // Misc data size (varies, 0 in tutorial) <- collections?) <- this isn't a SWGList, but it'll work until we need it
		baseline.put("17", new SWGList<Byte>(this, 3, 17, false)); // Misc ints (2 ints in tutorial, 5 bytes other times...)
		baseline.put("18", (byte) 0); // Unknown Flag (regularly changed in tutorial)
		baseline.put("19", (byte) 0); // Unknown Flag (regularly changed in tutorial)
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("adminFlags", (byte) 0);
		baseline.put("currentRank", 0);
		baseline.put("rankProgress", (float) 0);
		baseline.put("highestRebelRank", 0);
		baseline.put("highestImperialRank", 0);
		baseline.put("nextUpdateTime", 0); // TODO set correctly
		baseline.put("home", "");
		baseline.put("citizenship", (byte) 0); // Homeless/Citizen/Militia/Mayor?
		baseline.put("cityRegionDefender", new RegionDefender());
		baseline.put("guildRegionDefender", new RegionDefender());
		baseline.put("12", 0); // General?
		baseline.put("13", 0); // Guild Rank Title?
		baseline.put("14", 0); // Citizen Rank Title?
		baseline.put("15", 0); // All random guesses, always seem to be 0
		baseline.put("16", 0);
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
		baseline.put("questJournal", new SWGList<Quest>(this, 8, 6, false));
		baseline.put("professionWheelPosition", "");
		return baseline;
	}
	
	@Override
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		baseline.put("experimentationFlag", 0);
		baseline.put("craftingStage", 0);
		baseline.put("nearestCraftingStation", 0);
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
		// The others are unknown, commented below.
		// it won't send the rest and won't have to by using a lower object count.
		// If we figure all these structs out all we have to do is add them the
		// same way as above
		/*
		buffer.putInt(0); // Related to stomach in CU
		buffer.putInt(0); // Related to stomach in CU
		buffer.putInt(0); // Been seen as 0x0A
		buffer.putInt(3); // Also been seen as 0x0A and 0x02 and 0x18
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0); // Unused Waypoint List?
		buffer.putInt(0); // Update Counter?
		buffer.putInt(2); // Unknown, sometimes a number and sometimes 0
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(object.getJediState());
		buffer.putShort((short) 0);
		*/
		return baseline;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getFlagsList() {
		return (List<Integer>) baseline3.get("flagsList");
	}
	
	public void setFlagsList(int flag1, int flag2, int flag3, int flag4) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			List<Integer> flagsList = getFlagsList();
			flagsList.set(0, flag1);
			flagsList.set(1, flag2);
			flagsList.set(2, flag3);
			flagsList.set(3, flag4);
			buffer = baseline3.set("flagsList", flagsList);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getProfileFlagsList() {
		return (List<Integer>) baseline3.get("profileFlagsList");
	}
	
	public void setProfileFlagsList(int flag1, int flag2, int flag3, int flag4) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			List<Integer> profileFlagsList = getProfileFlagsList();
			profileFlagsList.set(0, flag1);
			profileFlagsList.set(1, flag2);
			profileFlagsList.set(2, flag3);
			profileFlagsList.set(3, flag4);
			buffer = baseline3.set("profileFlagsList", profileFlagsList);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public String getTitle() {
		synchronized(objectMutex) {
			return (String) baseline3.get("title");
		}
	}

	public void setTitle(String title) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("title", title);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getBornDate() {
		synchronized(objectMutex) {
			return (int) baseline3.get("bornDate");
		}
	}
	
	public long getLastPlayTimeUpdate() {
		synchronized(objectMutex) {
			return (long) baseline3.get("lastPlayTimeUpdate");
		}
	}
	
	public void setLastPlayTimeUpdate(long lastPlayTimeUpdate) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("lastPlayTimeUpdate", lastPlayTimeUpdate);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getTotalPlayTime() {
		synchronized(objectMutex) {
			return (int) baseline3.get("totalPlayTime");
		}
	}
	
	public void setTotalPlayTime(int totalPlayTime) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("totalPlayTime", totalPlayTime);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getProfessionIcon() {
		synchronized(objectMutex) {
			return (int) baseline3.get("professionIcon");
		}
	}
	
	public void setProfessionIcon(int professionIcon) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("professionIcon", professionIcon);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public String getProfession() {
		synchronized(objectMutex) {
			return (String) baseline3.get("profession");
		}
	}
	
	public void setProfession(String profession) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("profession", profession);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
		
		setProfessionIcon(Professions.get(profession));
	}
	
	public int getGcwPoints() {
		synchronized(objectMutex) {
			return (int) baseline3.get("gcwPoints");
		}
	}
	
	public void addGcwPoints(int gcwPoints) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("gcwPoints", (((int) baseline3.get("gcwPoints")) + gcwPoints));
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public void resetGcwPoints() {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("gcwPoints", 0);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getPvpKills() {
		synchronized(objectMutex) {
			return (int) baseline3.get("pvpKills");
		}
	}
	
	public void addPvpKill() {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("pvpKills", (((int) baseline3.get("pvpKills")) + 1));
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public void resetPvpKills() {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("pvpKills", 0);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public long getLifetimeGcwPoints() {
		synchronized(objectMutex) {
			return (long) baseline3.get("lifetimeGcwPoints");
		}
	}
	
	public void addLifetimeGcwPoints(long gcwPoints) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("lifetimeGcwPoints", (((long) baseline3.get("lifetimeGcwPoints")) + gcwPoints));
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getLifetimePvpKills() {
		synchronized(objectMutex) {
			return (int) baseline3.get("lifetimePvpKills");
		}
	}
	
	public void addLifetimePvpKills(int pvpKills) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("lifetimePvpKills", (((int) baseline3.get("lifetimePvpKills")) + pvpKills));
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public byte getAdminFlag() {
		synchronized(objectMutex) {
			return (byte) baseline6.get("adminFlag");
		}
	}
	
	public void setAdminFlag(byte adminFlag) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("adminFlag", adminFlag);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getCurrentRank() {
		synchronized(objectMutex) {
			return (int) baseline6.get("currentRank");
		}
	}
	
	public void setCurrentRank(int currentRank) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("currentRank", currentRank);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public float getRankProgress() {
		synchronized(objectMutex) {
			return (float) baseline6.get("rankProgress");
		}
	}
	
	public void setRankProgress(float rankProgress) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("rankProgress", rankProgress);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getHighestRebelRank() {
		synchronized(objectMutex) {
			return (int) baseline6.get("highestRebelRank");
		}
	}
	
	public void setHighestIRank(int highestRebelRank) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("highestRebelRank", highestRebelRank);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getHighestImperialRank() {
		synchronized(objectMutex) {
			return (int) baseline6.get("highestImperialRank");
		}
	}
	
	public void setHighestImperialRank(int highestImperialRank) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("highestImperialRank", highestImperialRank);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getNextUpdateTime() {
		synchronized(objectMutex) {
			return (int) baseline6.get("nextUpdateTime");
		}
	}
	
	public void setNextUpdateTime(int nextUpdateTime) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("nextUpdateTime", nextUpdateTime);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public String getHome() {
		synchronized(objectMutex) {
			return (String) baseline6.get("home");
		}
	}

	public void setHome(String home) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("home", home);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public byte getCitizenship() {
		synchronized(objectMutex) {
			return (byte) baseline6.get("citizenship");
		}
	}

	public void setCitizenship(byte citizenship) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("citizenship", citizenship);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public RegionDefender getCityRegionDefender() {
		synchronized(objectMutex) {
			return (RegionDefender) baseline6.get("cityRegionDefender");
		}
	}
	
	public void setCityRegionDefender(RegionDefender cityRegionDefender) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("cityRegionDefender", cityRegionDefender);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public RegionDefender getGuildRegionDefender() {
		synchronized(objectMutex) {
			return (RegionDefender) baseline6.get("guildRegionDefender");
		}
	}
	
	public void setGuildRegionDefender(RegionDefender guildRegionDefender) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("guildRegionDefender", guildRegionDefender);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, Integer> getXpList() {
		return (SWGMap<String, Integer>) baseline8.get("xpList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<Long, WaypointObject> getWaypoints() {
		return (SWGMap<Long, WaypointObject>) baseline8.get("waypoints");
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
		synchronized(objectMutex) {
			return (int) baseline8.get("currentForcePower");
		}
	}
	
	public void setCurrentForcePower(int currentForcePower) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline8.set("currentForcePower", currentForcePower);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getMaxForcePower() {
		synchronized(objectMutex) {
			return (int) baseline8.get("maxForcePower");
		}
	}
	
	public void setMaxForcePower(int maxForcePower) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline8.set("maxForcePower", maxForcePower);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Byte> getCurrentFSQuestList() {
		return (SWGList<Byte>) baseline8.get("currentFSQuestList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Byte> getCompletedFSQuestList() {
		return (SWGList<Byte>) baseline8.get("completedFSQuestList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Quest> getQuestJournal() {
		return (SWGList<Quest>) baseline8.get("questJournal");
	}
	
	public String getProfessionWheelPosition() {
		synchronized(objectMutex) {
			return (String) baseline8.get("professionWheelPosition");
		}
	}
	
	public void setProfessionWheelPosition(String professionWheelPosition) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline8.set("professionWheelPosition", professionWheelPosition);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getExperimentationFlag() {
		synchronized(objectMutex) {
			return (int) baseline9.get("experimentationFlag");
		}
	}
	
	public void setExperimentationFlag(int experimentationFlag) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("experimentationFlag", experimentationFlag);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getCraftingStage() {
		synchronized(objectMutex) {
			return (int) baseline9.get("craftingStage");
		}
	}
	
	public void setCraftingStage(int craftingStage) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("craftingStage", craftingStage);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public long getNearestCraftingStation() {
		synchronized(objectMutex) {
			return (long) baseline9.get("nearestCraftingStation");
		}
	}
	
	public void setNearestCraftingStation(long nearestCraftingStation) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("nearestCraftingStation", nearestCraftingStation);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<DraftSchematic> getDraftSchematicList() {
		return (SWGList<DraftSchematic>) baseline9.get("draftSchematicList");
	}
	
	public int getExperimentationPoints() {
		synchronized(objectMutex) {
			return (int) baseline9.get("experimentationPoints");
		}
	}
	
	public void setExperimentationPoints(int experimentationPoints) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("experimentationPoints", experimentationPoints);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getAccomplishmentCounter() {
		synchronized(objectMutex) {
			return (int) baseline9.get("accomplishmentCounter");
		}
	}
	
	public void setAccomplishmentCounter(int accomplishmentCounter) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("accomplishmentCounter", accomplishmentCounter);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<String> getFriendList() {
		return (SWGList<String>) baseline9.get("friendList");
	}
	
	public void friendAdd(String friend) {
		getFriendList().add(friend);
	}
	
	public void friendRemove(String friend) {
		getFriendList().remove(friend);
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<String> getIgnoreList() {
		return (SWGList<String>) baseline9.get("ignoreList");
	}
	
	public void ignoreAdd(String name) {
		getIgnoreList().add(name);
	}
	
	public void ignoreRemove(String name) {
		getIgnoreList().remove(name);
	}
	
	public int getLanguageId() {
		synchronized(objectMutex) {
			return (int) baseline9.get("languageId");
		}
	}
	
	public void setLanguageId(int languageId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("languageId", languageId);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getCurrentStomach() {
		synchronized(objectMutex) {
			return (int) baseline9.get("currentStomach");
		}
	}
	
	public void setCurrentStomach(int currentStomach) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("currentStomach", currentStomach);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getMaxStomach() {
		synchronized(objectMutex) {
			return (int) baseline9.get("maxStomach");
		}
	}
	
	public void setMaxStomach(int maxStomach) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("maxStomach", maxStomach);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getCurrentDrink() {
		synchronized(objectMutex) {
			return (int) baseline9.get("currentDrink");
		}
	}
	
	public void setCurrentDrink(int currentDrink) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("currentDrink", currentDrink);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getMaxDrink() {
		synchronized(objectMutex) {
			return (int) baseline9.get("maxDrink");
		}
	}
	
	public void setMaxDrink(int maxDrink) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("maxDrink", maxDrink);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getCurrentConsumable() {
		synchronized(objectMutex) {
			return (int) baseline9.get("currentConsumable");
		}
	}
	
	public void setCurrentConsumable(int currentConsumable) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("currentConsumable", currentConsumable);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
	}
	
	public int getMaxConsumable() {
		synchronized(objectMutex) {
			return (int) baseline9.get("maxConsumable");
		}
	}
	
	public void setMaxConsumable(int maxConsumable) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline9.set("maxConsumable", maxConsumable);
		}
		
		if (getContainer() != null) {
			getContainer().notifyObservers(buffer, true);
		}
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
			//if(destination.getParent().getObjectID() == getParentId()) {				// only send to self
				//destination.getSession().write(getBaseline(3).getBaseline());
				//destination.getSession().write(getBaseline(6).getBaseline());
				//destination.getSession().write(getBaseline(8).getBaseline());
				//destination.getSession().write(getBaseline(9).getBaseline());
			//}
		}
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		if (getContainer() != null) {
			getContainer().notifyObservers(getBaseline(viewType).createDelta(updateType, buffer.array()), true);
		}
	}
	
}
