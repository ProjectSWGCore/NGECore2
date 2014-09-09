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

public class QuestTask {
	private String type; // Tells the server what to do when they get the task
	private String name;
	private String journalEntryTitle;
	private String journalEntryDescription;
	private String planet;
	// private String prerequisiteTasks;
	// private String exclusionTasks; 
	private String musicOnActivate;
	private String musicOnComplete;
	private String musicOnFail;
	private String target;
	private String parameter; // TODO: What does this do?
	private String grantQuestOnComplete;
	private String grantQuestOnFail;
	private String interiorWaypointAppearance;
	private String buildingCellName;
	private String waypointName;
	private String signalName;
	private String signalsOnFail;
	private String signalsOnComplete;
	private String waitMarkerTemplate;
	private String waitMarkerPlanetName;
	private String waitMarkerCellName;
	private String commMessageText;
	private String npcAppearanceTemplate; // NPC Appearance Server Template
	private String creatureType;
	private int count;
	private int minDistance;
	private int maxDistance;
	private String relativeOffsetX;
	private String relativeOffsetY;
	private String tasksOnComplete;
	private String serverTemplate;
	private String itemName;
	private String tasksOnFail;
	private String retrieveMenuText;
	private String msgBoxPrompt;
	private String msgBoxTitle;
	private String msgBoxSound;
	private String lootName;
	
	private boolean visible;
	private boolean grantQuestOnCompleteShowSystemMessage;
	private boolean grantQuestOnFailShowSystemMessage;
	private boolean allowRepeats;
	private boolean createsWaypoint;
	private boolean showSystemMessages;
	
	private int id;
	private int waitMarkerCreate;
	private int timeToComplete;
	private int numRequired;
	private int dropPercent;
	private int minTime;
	private int maxTime;
	private int msgBoxWidth;
	private int msgBoxHeight;
	private int msgBoxLength;
	
	private long waitMarkerBuilding;
	
	private float LocationX;
	private float LocationY;
	private float LocationZ;
	private float waitMarkerX;
	private float waitMarkerY;
	private float waitMarkerZ;
	private float radius;

	public QuestTask() {}

	public QuestTask(String type, int id) {
		this.type = type;
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public String getJournalEntryTitle() {
		return journalEntryTitle;
	}

	public void setJournalEntryTitle(String journalEntryTitle) {
		this.journalEntryTitle = journalEntryTitle;
	}

	public String getJournalEntryDescription() {
		return journalEntryDescription;
	}

	public void setJournalEntryDescription(String journalEntryDescription) {
		this.journalEntryDescription = journalEntryDescription;
	}

	public String getTasksOnFail() {
		return tasksOnFail;
	}

	public int getId() {
		return id;
	}

	public String getPlanet() {
		return planet;
	}

	public void setPlanet(String planet) {
		this.planet = planet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMusicOnActivate() {
		return musicOnActivate;
	}

	public void setMusicOnActivate(String musicOnActivate) {
		this.musicOnActivate = musicOnActivate;
	}

	public String getMusicOnComplete() {
		return musicOnComplete;
	}

	public void setMusicOnComplete(String musicOnComplete) {
		this.musicOnComplete = musicOnComplete;
	}

	public String getMusicOnFail() {
		return musicOnFail;
	}

	public void setMusicOnFail(String musicOnFail) {
		this.musicOnFail = musicOnFail;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getGrantQuestOnComplete() {
		return grantQuestOnComplete;
	}

	public void setGrantQuestOnComplete(String grantQuestOnComplete) {
		this.grantQuestOnComplete = grantQuestOnComplete;
	}

	public String getGrantQuestOnFail() {
		return grantQuestOnFail;
	}

	public void setGrantQuestOnFail(String grantQuestOnFail) {
		this.grantQuestOnFail = grantQuestOnFail;
	}

	public String getInteriorWaypointAppearance() {
		return interiorWaypointAppearance;
	}

	public void setInteriorWaypointAppearance(String interiorWaypointAppearance) {
		this.interiorWaypointAppearance = interiorWaypointAppearance;
	}

	public String getBuildingCellName() {
		return buildingCellName;
	}

	public void setBuildingCellName(String buildingCellName) {
		this.buildingCellName = buildingCellName;
	}

	public String getWaypointName() {
		return waypointName;
	}

	public void setWaypointName(String waypointName) {
		this.waypointName = waypointName;
	}

	public String getSignalName() {
		return signalName;
	}

	public void setSignalName(String signalName) {
		this.signalName = signalName;
	}

	public String getSignalsOnFail() {
		return signalsOnFail;
	}

	public void setSignalsOnFail(String signalsOnFail) {
		this.signalsOnFail = signalsOnFail;
	}

	public String getSignalsOnComplete() {
		return signalsOnComplete;
	}

	public void setSignalsOnComplete(String signalsOnComplete) {
		this.signalsOnComplete = signalsOnComplete;
	}

	public String getWaitMarkerTemplate() {
		return waitMarkerTemplate;
	}

	public void setWaitMarkerTemplate(String waitMarkerTemplate) {
		this.waitMarkerTemplate = waitMarkerTemplate;
	}

	public String getWaitMarkerPlanetName() {
		return waitMarkerPlanetName;
	}

	public void setWaitMarkerPlanetName(String waitMarkerPlanetName) {
		this.waitMarkerPlanetName = waitMarkerPlanetName;
	}

	public String getWaitMarkerCellName() {
		return waitMarkerCellName;
	}

	public void setWaitMarkerCellName(String waitMarkerCellName) {
		this.waitMarkerCellName = waitMarkerCellName;
	}

	public String getCommMessageText() {
		return commMessageText;
	}

	public void setCommMessageText(String commMessageText) {
		this.commMessageText = commMessageText;
	}

	public String getNpcAppearanceTemplate() {
		return npcAppearanceTemplate;
	}

	public void setNpcAppearanceTemplate(String npcAppearanceTemplate) {
		this.npcAppearanceTemplate = npcAppearanceTemplate;
	}

	public String getCreatureType() {
		return creatureType;
	}

	public void setCreatureType(String creatureType) {
		this.creatureType = creatureType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(int minDistance) {
		this.minDistance = minDistance;
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public String getRelativeOffsetX() {
		return relativeOffsetX;
	}

	public void setRelativeOffsetX(String relativeOffsetX) {
		this.relativeOffsetX = relativeOffsetX;
	}

	public String getRelativeOffsetY() {
		return relativeOffsetY;
	}

	public void setRelativeOffsetY(String relativeOffsetY) {
		this.relativeOffsetY = relativeOffsetY;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isGrantQuestOnCompleteShowSystemMessage() {
		return grantQuestOnCompleteShowSystemMessage;
	}

	public void setGrantQuestOnCompleteShowSystemMessage(
			boolean grantQuestOnCompleteShowSystemMessage) {
		this.grantQuestOnCompleteShowSystemMessage = grantQuestOnCompleteShowSystemMessage;
	}

	public boolean isGrantQuestOnFailShowSystemMessage() {
		return grantQuestOnFailShowSystemMessage;
	}

	public void setGrantQuestOnFailShowSystemMessage(
			boolean grantQuestOnFailShowSystemMessage) {
		this.grantQuestOnFailShowSystemMessage = grantQuestOnFailShowSystemMessage;
	}

	public boolean getAllowRepeats() {
		return allowRepeats;
	}

	public void setAllowRepeats(boolean allowRepeats) {
		this.allowRepeats = allowRepeats;
	}

	public boolean showSystemMessages() {
		return showSystemMessages;
	}

	public void setShowSystemMessages(boolean showSystemMessages) {
		this.showSystemMessages = showSystemMessages;
	}

	public boolean createsWaypoint() {
		return createsWaypoint;
	}

	public void setCreatesWaypoint(boolean createWaypoint) {
		this.createsWaypoint = createWaypoint;
	}

	public int getWaitMarkerCreate() {
		return waitMarkerCreate;
	}

	public void setWaitMarkerCreate(int waitMarkerCreate) {
		this.waitMarkerCreate = waitMarkerCreate;
	}

	public int getTimeToComplete() {
		return timeToComplete;
	}

	public void setTimeToComplete(int timeToComplete) {
		this.timeToComplete = timeToComplete;
	}

	public long getWaitMarkerBuilding() {
		return waitMarkerBuilding;
	}

	public void setWaitMarkerBuilding(long waitMarkerBuilding) {
		this.waitMarkerBuilding = waitMarkerBuilding;
	}

	public float getLocationX() {
		return LocationX;
	}

	public void setLocationX(float locationX) {
		LocationX = locationX;
	}

	public float getLocationY() {
		return LocationY;
	}

	public void setLocationY(float locationY) {
		LocationY = locationY;
	}

	public float getLocationZ() {
		return LocationZ;
	}

	public void setLocationZ(float locationZ) {
		LocationZ = locationZ;
	}

	public float getWaitMarkerX() {
		return waitMarkerX;
	}

	public void setWaitMarkerX(float waitMarkerX) {
		this.waitMarkerX = waitMarkerX;
	}

	public float getWaitMarkerY() {
		return waitMarkerY;
	}

	public void setWaitMarkerY(float waitMarkerY) {
		this.waitMarkerY = waitMarkerY;
	}

	public float getWaitMarkerZ() {
		return waitMarkerZ;
	}

	public void setWaitMarkerZ(float waitMarkerZ) {
		this.waitMarkerZ = waitMarkerZ;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTasksOnFail(String tasksOnFail) {
		this.tasksOnFail = tasksOnFail;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTasksOnComplete() {
		return tasksOnComplete;
	}

	public void setTasksOnComplete(String tasksOnComplete) {
		this.tasksOnComplete = tasksOnComplete;
	}

	public String getServerTemplate() {
		return serverTemplate;
	}

	public void setServerTemplate(String serverTemplate) {
		this.serverTemplate = serverTemplate;
	}

	public int getNumRequired() {
		return numRequired;
	}

	public void setNumRequired(int numRequired) {
		this.numRequired = numRequired;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getDropPercent() {
		return dropPercent;
	}

	public void setDropPercent(int dropPercent) {
		this.dropPercent = dropPercent;
	}

	public String getRetrieveMenuText() {
		return retrieveMenuText;
	}

	public void setRetrieveMenuText(String retrieveMenuText) {
		this.retrieveMenuText = retrieveMenuText;
	}

	public int getMinTime() {
		return minTime;
	}

	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public String getMsgBoxPrompt() {
		return msgBoxPrompt;
	}

	public void setMsgBoxPrompt(String msgBoxPrompt) {
		this.msgBoxPrompt = msgBoxPrompt;
	}

	public String getMsgBoxTitle() {
		return msgBoxTitle;
	}

	public void setMsgBoxTitle(String msgBoxTitle) {
		this.msgBoxTitle = msgBoxTitle;
	}

	public String getMsgBoxSound() {
		return msgBoxSound;
	}

	public void setMsgBoxSound(String msgBoxSound) {
		this.msgBoxSound = msgBoxSound;
	}

	public int getMsgBoxWidth() {
		return msgBoxWidth;
	}

	public void setMsgBoxWidth(int msgBoxWidth) {
		this.msgBoxWidth = msgBoxWidth;
	}

	public int getMsgBoxHeight() {
		return msgBoxHeight;
	}

	public void setMsgBoxHeight(int msgBoxHeight) {
		this.msgBoxHeight = msgBoxHeight;
	}

	public int getMsgBoxLength() {
		return msgBoxLength;
	}

	public void setMsgBoxLength(int msgBoxLength) {
		this.msgBoxLength = msgBoxLength;
	}

	public String getLootName() {
		return lootName;
	}

	public void setLootName(String lootName) {
		this.lootName = lootName;
	}
}
