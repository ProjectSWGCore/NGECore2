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
import com.sleepycat.persist.model.Persistent;

import resources.objects.intangible.IntangibleObject;
import resources.objects.waypoint.WaypointObject;
import engine.clients.Client;
import engine.resources.objects.IPersistent;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent(version=0)
public class MissionObject extends IntangibleObject implements IPersistent {

	private float destX;
	private float destY;
	private float destZ;
	private float startX;
	private float startY;
	private float startZ;
	private String startPlanet;
	private int difficultyLevel; // Difficulty level displayed in details
	private String destinationPlanet;
	private int repeatCount; // increases for each player using the mission; used for redisplaying on mission term too
	private int reward;
	private String creator;
	private String description; // TODO: Base off of a new Object, SWGString?
	private String title; // TODO: Base off of a new Object, SWGString?
	private String missionTargetName; // Target object I guess?
	private String descId;
	private String titleId;
	private String type;
	private String missionTemplateObject;
	private WaypointObject attachedWaypoint;

	@NotPersistent
	MissionMessageBuilder messageBuilder = new MissionMessageBuilder(this);
	
	@NotPersistent
	private Transaction txn;
	
	public MissionObject() {
		super();
	}
	
	public MissionObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0), template);
	}

	public float getMissionStartX() {
		synchronized(objectMutex) {
			return startX;
		}
	}

	public float getMissionStartY() {
		synchronized(objectMutex) {
			return startY;
		}
	}

	public float getMissionStartZ() {
		synchronized(objectMutex) {
			return startZ;
		}
	}

	public void setMissionStart(float x, float y, float z, String planet) {
		synchronized(objectMutex) {
			this.startZ = z;
			this.startX = x;
			this.startY = y;
			this.startPlanet = planet;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildStartLocationDelta(x, z, y, planet));
		}
	}

	public float getMissionDestinationX() {
		synchronized(objectMutex) {
			return destX;
		}
	}

	public float getMissionDestinationY() {
		synchronized(objectMutex) {
			return destY;
		}
	}

	public float getMissionDestinationZ() {
		synchronized(objectMutex) {
			return destZ;
		}
	}

	public void setMissionDestination(float x, float y, float z, String planet) {
		synchronized(objectMutex) {
			this.destZ = z;
			this.destY = y;
			this.destZ = z;
			this.destinationPlanet = planet;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildDestinationDelta(x, z, y, planet));
		}
	}

	public String getMissionDestinationPlanet() {
		synchronized(objectMutex) {
			return destinationPlanet;
		}
	}

	public int getMissionLevel() {
		synchronized(objectMutex) {
			return difficultyLevel;
		}
	}

	public void setMissionLevel(int missionLevel) {
		synchronized(objectMutex) {
			this.difficultyLevel = missionLevel;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildDifficultyLevelDelta(missionLevel));
		}
	}

	public String getMissionStartPlanet() {
		synchronized(objectMutex) {
			return startPlanet;
		}
	}

	public int getMissionRepeatCounter() {
		synchronized(objectMutex) {
			return repeatCount;
		}
	}

	public void setRepeatCount(int missionRepeatCounter) {
		synchronized(objectMutex) {
			this.repeatCount = missionRepeatCounter;
		}
	}

	public int getCreditReward() {
		synchronized(objectMutex) {
			return reward;
		}
	}

	public void setCreditReward(int missionCredits) {
		synchronized(objectMutex) {
			this.reward = missionCredits;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildCreditsRewardDelta(missionCredits));
		}
	}

	public String getCreator() {
		synchronized(objectMutex) {
			return creator;
		}
	}

	public void setCreator(String missionCreator) {
		synchronized(objectMutex) {
			this.creator = missionCreator;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildCreatorNameDelta(missionCreator));
		}
	}

	public String getMissionDescription() {
		synchronized(objectMutex) {
			return description;
		}
	}

	public void setMissionDescription(String missionDescription, String id) {
		synchronized(objectMutex) {
			this.description = missionDescription;
			this.descId = id;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildMissionDescriptionDelta(missionDescription, id));
		}
	}

	public String getMissionTitle() {
		synchronized(objectMutex) {
			return title;
		}
	}

	public void setMissionTitle(String missionTitle, String id) {
		synchronized(objectMutex) {
			this.title = missionTitle;
			this.titleId = id;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildMissionTitleDelta(missionTitle, id));
		}
	}

	public String getMissionTargetName() {
		synchronized(objectMutex) {
			return missionTargetName;
		}
	}

	public void setMissionTargetName(String missionTargetName) {
		synchronized(objectMutex) {
			this.missionTargetName = missionTargetName;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildTargetNameDelta(missionTargetName));
		}
	}

	public WaypointObject getAttachedWaypoint() {
		synchronized(objectMutex) {
			return attachedWaypoint;
		}
	}

	public void setAttachedWaypoint(WaypointObject missionAttachedWaypoint) {
		synchronized(objectMutex) {
			this.attachedWaypoint = missionAttachedWaypoint;
		}
	}

	public String getMissionTemplateObject() {
		synchronized(objectMutex) {
			return missionTemplateObject;
		}
	}

	public void setMissionTemplateObject(String missionTemplateObject) {
		synchronized(objectMutex) {
			this.missionTemplateObject = missionTemplateObject;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildTargetObjectIffDelta(missionTemplateObject));
		}
	}

	public String getMissionDescId() {
		synchronized(objectMutex) {
			return descId;
		}
	}

	public String getMissionTitleId() {
		synchronized(objectMutex) {
			return titleId;
		}
	}

	public String getMissionType() {
		synchronized(objectMutex) {
			return type;
		}
	}

	public void setMissionType(String missionType) {
		synchronized(objectMutex) {
			this.type = missionType;
		}
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildMissionTypeDelta(missionType));
		}
	}
	
	public void sendMissionDelta() {
		if (getContainer() != null && getContainer().getClient() != null && getContainer().getClient().getSession() != null) {
			getContainer().getClient().getSession().write(messageBuilder.buildMissionDelta());
		}
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
		destination.getSession().write(messageBuilder.buildBaseline8());
		destination.getSession().write(messageBuilder.buildBaseline9());
	}

}
