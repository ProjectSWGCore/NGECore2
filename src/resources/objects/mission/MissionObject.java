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
	private String missionCreator = "";
	private String missionDescription = "";
	private String missionTitle = "";
	private String missionTargetName = ""; // Target object I guess?
	private String missionDescId = "";
	private String missionTitleId = "";
	private String missionType;
	private WaypointObject missionAttachedWaypoint;
	
	private String missionTemplateObject;

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
			return missionStartX;
		}
	}

	public float getMissionStartY() {
		synchronized(objectMutex) {
			return missionStartY;
		}
	}

	public float getMissionStartZ() {
		synchronized(objectMutex) {
			return missionStartZ;
		}
	}

	public void setMissionStart(float x, float y, float z, String planet) {
		synchronized(objectMutex) {
			this.missionStartZ = z;
			this.missionStartX = x;
			this.missionStartY = y;
			this.missionStartPlanet = planet;
		}
		
		notifyObservers(messageBuilder.buildStartLocationDelta(x, z, y, planet), false);
	}

	public float getMissionDestinationX() {
		synchronized(objectMutex) {
			return missionDestinationX;
		}
	}

	public float getMissionDestinationY() {
		synchronized(objectMutex) {
			return missionDestinationY;
		}
	}

	public float getMissionDestinationZ() {
		synchronized(objectMutex) {
			return missionDestinationZ;
		}
	}

	public void setMissionDestination(float x, float y, float z, String planet) {
		synchronized(objectMutex) {
			this.missionDestinationZ = z;
			this.missionDestinationY = y;
			this.missionDestinationZ = z;
			this.missionDestinationPlanet = planet;
		}
	}

	public String getMissionDestinationPlanet() {
		synchronized(objectMutex) {
			return missionDestinationPlanet;
		}
	}

	public int getMissionLevel() {
		synchronized(objectMutex) {
			return missionLevel;
		}
	}

	public void setMissionLevel(int missionLevel) {
		synchronized(objectMutex) {
			this.missionLevel = missionLevel;
		}
	}

	public String getMissionStartPlanet() {
		synchronized(objectMutex) {
			return missionStartPlanet;
		}
	}

	public int getMissionRepeatCounter() {
		synchronized(objectMutex) {
			return missionRepeatCounter;
		}
	}

	public void setMissionRepeatCounter(int missionRepeatCounter) {
		synchronized(objectMutex) {
			this.missionRepeatCounter = missionRepeatCounter;
		}
	}

	public int getMissionCredits() {
		synchronized(objectMutex) {
			return missionCredits;
		}
	}

	public void setMissionCredits(int missionCredits) {
		synchronized(objectMutex) {
			this.missionCredits = missionCredits;
		}
		notifyObservers(messageBuilder.buildCreditsRewardDelta(missionCredits), false);
	}

	public String getMissionCreator() {
		synchronized(objectMutex) {
			return missionCreator;
		}
	}

	public void setMissionCreator(String missionCreator) {
		synchronized(objectMutex) {
			this.missionCreator = missionCreator;
		}
		notifyObservers(messageBuilder.buildCreatorNameDelta(missionCreator), false);
	}

	public String getMissionDescription() {
		synchronized(objectMutex) {
			return missionDescription;
		}
	}

	public void setMissionDescription(String missionDescription, String id) {
		synchronized(objectMutex) {
			this.missionDescription = missionDescription;
			this.missionDescId = id;
		}
		notifyObservers(messageBuilder.buildMissionDescriptionDelta(missionDescription, id), false);
	}

	public String getMissionTitle() {
		synchronized(objectMutex) {
			return missionTitle;
		}
	}

	public void setMissionTitle(String missionTitle, String id) {
		synchronized(objectMutex) {
			this.missionTitle = missionTitle;
			this.missionTitleId = id;
		}
		notifyObservers(messageBuilder.buildMissionTitleDelta(missionTitle, id), false);
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
		notifyObservers(messageBuilder.buildTargetNameDelta(missionTargetName), false);
	}

	public WaypointObject getMissionAttachedWaypoint() {
		synchronized(objectMutex) {
			return missionAttachedWaypoint;
		}
	}

	public void setMissionAttachedWaypoint(WaypointObject missionAttachedWaypoint) {
		synchronized(objectMutex) {
			this.missionAttachedWaypoint = missionAttachedWaypoint;
		}
	}

	public String getTargetTemplateObject() {
		synchronized(objectMutex) {
			return missionTemplateObject;
		}
	}

	public void setTargetTemplateObject(String missionTemplateObject) {
		synchronized(objectMutex) {
			this.missionTemplateObject = missionTemplateObject;
		}
		notifyObservers(messageBuilder.buildTargetObjectIffDelta(missionTemplateObject), false);
	}

	public String getMissionDescId() {
		synchronized(objectMutex) {
			return missionDescId;
		}
	}

	public String getMissionTitleId() {
		synchronized(objectMutex) {
			return missionTitleId;
		}
	}

	public String getMissionType() {
		synchronized(objectMutex) {
			return missionType;
		}
	}

	public void setMissionType(String missionType) {
		synchronized(objectMutex) {
			this.missionType = missionType;
		}
		notifyObservers(messageBuilder.buildMissionTypeDelta(missionType), false);
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
