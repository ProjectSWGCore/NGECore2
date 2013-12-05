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
package resources.objects.mission;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.model.NotPersistent;

import resources.objects.tangible.TangibleObject;
import resources.objects.waypoint.WaypointObject;
import engine.clients.Client;
import engine.resources.objects.IPersistent;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class MissionObject extends SWGObject implements IPersistent {

	private String stfName;
	private String customName;
	private int volume;

	private float missionComplexity;
	private float missionDestinationX;
	private float missionDestinationY;
	private float missionDestinationZ;
	private float missionStartX;
	private float missionStartY;
	private float missionStartZ;
	private String missionStartPlanet;
	private int missionLevel; // Difficulty level displayed in details
	private String missionDestinationPlanet;
	private int missionRepeatCounter; // increases for each player using the mission; used for redisplaying on mission term too
	private int missionCredits;
	private String missionCreator;
	private String missionDescription;
	private String missionTitle;
	private String missionTargetName; // target/destination
	private String missionDescId;
	private String missionTitleId;
	private String missionType;
	private WaypointObject missionAttachedWaypoint;
	
	private String missionTemplateObject;

	@NotPersistent
	MissionMessageBuilder messageBuilder = new MissionMessageBuilder(this);
	
	@NotPersistent
	private Transaction txn;

	
	public MissionObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0), template);
	}
	
	public String getStfName() {
		return stfName;
	}

	public void setStfName(String stfName) {
		this.stfName = stfName;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public float getMissionComplexity() {
		return missionComplexity;
	}

	public void setMissionComplexity(float missionComplexity) {
		this.missionComplexity = missionComplexity;
	}

	public float getMissionStartX() {
		return missionStartX;
	}

	public void setMissionStartX(float missionStartX) {
		this.missionStartX = missionStartX;
	}

	public float getMissionStartY() {
		return missionStartY;
	}

	public void setMissionStartY(float missionStartY) {
		this.missionStartY = missionStartY;
	}

	public float getMissionStartZ() {
		return missionStartZ;
	}

	public void setMissionStartZ(float missionStartZ) {
		this.missionStartZ = missionStartZ;
	}

	public float getMissionDestinationX() {
		return missionDestinationX;
	}

	public void setMissionDestinationX(float missionDestinationX) {
		this.missionDestinationX = missionDestinationX;
	}

	public float getMissionDestinationY() {
		return missionDestinationY;
	}

	public void setMissionDestinationY(float missionDestinationY) {
		this.missionDestinationY = missionDestinationY;
	}

	public float getMissionDestinationZ() {
		return missionDestinationZ;
	}

	public void setMissionDestinationZ(float missionDestinationZ) {
		this.missionDestinationZ = missionDestinationZ;
	}

	public String getMissionDestinationPlanet() {
		return missionDestinationPlanet;
	}

	public void setMissionDestinationPlanet(String missionDestinationPlanet) {
		this.missionDestinationPlanet = missionDestinationPlanet;
	}

	public int getMissionLevel() {
		return missionLevel;
	}

	public void setMissionLevel(int missionLevel) {
		this.missionLevel = missionLevel;
	}

	public String getMissionStartPlanet() {
		return missionStartPlanet;
	}

	public void setMissionStartPlanet(String missionStartPlanetCRC) {
		this.missionStartPlanet = missionStartPlanetCRC;
	}

	public int getMissionRepeatCounter() {
		return missionRepeatCounter;
	}

	public void setMissionRepeatCounter(int missionRepeatCounter) {
		this.missionRepeatCounter = missionRepeatCounter;
	}

	public int getMissionCredits() {
		return missionCredits;
	}

	public void setMissionCredits(int missionCredits) {
		this.missionCredits = missionCredits;
	}

	public String getMissionCreator() {
		return missionCreator;
	}

	public void setMissionCreator(String missionCreator) {
		this.missionCreator = missionCreator;
	}

	public String getMissionDescription() {
		return missionDescription;
	}

	public void setMissionDescription(String missionDescription) {
		this.missionDescription = missionDescription;
	}

	public String getMissionTitle() {
		return missionTitle;
	}

	public void setMissionTitle(String missionTitle) {
		this.missionTitle = missionTitle;
	}

	public String getMissionTargetName() {
		return missionTargetName;
	}

	public void setMissionTargetName(String missionTargetName) {
		this.missionTargetName = missionTargetName;
	}

	public WaypointObject getMissionAttachedWaypoint() {
		return missionAttachedWaypoint;
	}

	public void setMissionAttachedWaypoint(WaypointObject missionAttachedWaypoint) {
		this.missionAttachedWaypoint = missionAttachedWaypoint;
	}

	public String getMissionTemplateObject() {
		return missionTemplateObject;
	}

	public void setMissionTemplateObject(String missionTemplateObject) {
		this.missionTemplateObject = missionTemplateObject;
	}

	public String getMissionDescId() {
		return missionDescId;
	}

	public void setMissionDescId(String missionDescId) {
		this.missionDescId = missionDescId;
	}

	public String getMissionTitleId() {
		return missionTitleId;
	}

	public void setMissionTitleId(String missionTitleId) {
		this.missionTitleId = missionTitleId;
	}

	public String getMissionType() {
		return missionType;
	}

	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}

	public Transaction getTransaction() {
		return txn;
	}
	
	public void createTransaction(Environment env) {
		txn = env.beginTransaction(null, null);
	}

	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null) {
			System.out.println("NULL session");
			return;
		}
		
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
	}

}
