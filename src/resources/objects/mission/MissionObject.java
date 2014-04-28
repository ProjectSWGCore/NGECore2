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

import resources.common.BountyListItem;
import resources.objects.intangible.IntangibleObject;
import resources.objects.waypoint.WaypointObject;
import services.mission.MissionObjective;
import engine.clients.Client;
import engine.resources.objects.IPersistent;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent(version=1)
public class MissionObject extends IntangibleObject implements IPersistent {

	private Point3D destination;
	private Point3D startLocation;
	private String startPlanet = "";
	private int difficultyLevel = 0; // Difficulty level displayed in details
	private String destinationPlanet = "";
	private int repeatCount = 0; // increases for each player using the mission; used for redisplaying on mission term too
	private int reward = 0;
	private String creator = "";
	private String description = "";
	private String title = "";
	private String missionTargetName = ""; // Target object I guess?
	private int missionId = 0;
	private String type = "";
	private int missionTemplateObject = 0;
	private WaypointObject attachedWaypoint;
	
	// Server variables
	private MissionObjective objective;
	private long bountyObjId;
	
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
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildDifficultyLevelDelta(missionLevel));
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
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildRepeatCounterDelta(missionRepeatCounter));
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
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildCreditsRewardDelta(missionCredits));
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
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildCreatorNameDelta(missionCreator));
		}
	}

	public String getMissionDescription() {
		synchronized(objectMutex) {
			return description;
		}
	}

	public void setMissionDescription(String missionDescription) {
		setMissionDescription(missionDescription, "");
	}
	
	public void setMissionDescription(String missionDescription, String additionalParam) {
		synchronized(objectMutex) {
			this.description = missionDescription;
		}
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildMissionDescriptionDelta(missionDescription, missionId, additionalParam));
		}
	}

	public String getMissionTitle() {
		synchronized(objectMutex) {
			return title;
		}
	}

	public void setMissionTitle(String missionTitle) {
		setMissionTitle(missionTitle, "");
	}

	public void setMissionTitle(String missionTitle, String additionalParam) {
		synchronized(objectMutex) {
			this.title = missionTitle;
		}
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildMissionTitleDelta(missionTitle, missionId, additionalParam));
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
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildTargetNameDelta(missionTargetName));
		}
	}

	public WaypointObject getAttachedWaypoint() {
		synchronized(objectMutex) {
			return attachedWaypoint;
		}
	}

	public void setAttachedWaypoint(WaypointObject waypoint) {
		synchronized(objectMutex) {
			this.attachedWaypoint = waypoint;
		}
		
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildWaypointDelta(waypoint));
		}
	}

	public int getMissionTemplateObject() {
		synchronized(objectMutex) {
			return missionTemplateObject;
		}
	}

	public void setMissionTemplateObject(int missionTemplateObject) {
		synchronized(objectMutex) {
			this.missionTemplateObject = missionTemplateObject;
		}
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildTargetObjectIffDelta(missionTemplateObject));
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
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildMissionTypeDelta(missionType));
		}
	}

	public MissionObjective getObjective() {
		synchronized(objectMutex) {
			return objective;
		}
	}

	public void setObjective(MissionObjective objective) {
		synchronized(objectMutex) {
			this.objective = objective;
		}
	}

	public Point3D getStartLocation() {
		synchronized(objectMutex) {
			return startLocation;
		}
	}
	
	public Point3D getDestination() {
		synchronized(objectMutex) {
			return destination;
		}
	}

	public void setStartLocation(Point3D startLocation, String planet) {
		synchronized(objectMutex) {
			this.startLocation = startLocation;
			this.startPlanet = planet;
		}
		
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildStartLocationDelta(startLocation, planet));
		}
	}
	
	public void setDestination(Point3D destination, String planet) {
		synchronized(objectMutex) {
			this.destination = destination;
			this.destinationPlanet = planet;
		}
		
		if (getGrandparent() != null && getGrandparent().getClient() != null && getGrandparent().getClient().getSession() != null) {
			getGrandparent().getClient().getSession().write(messageBuilder.buildDestinationDelta(destination, planet));
		}
	}

	public int getMissionId() {
		return missionId;
	}

	public void setMissionId(int missionId) {
		this.missionId = missionId;
	}

	public long getBountyObjId() {
		return bountyObjId;
	}

	public void setBountyObjId(long bountyObjId) {
		this.bountyObjId = bountyObjId;
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
