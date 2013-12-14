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
package resources.z.exp.objects.mission;

import org.apache.mina.core.buffer.IoBuffer;

import main.NGECore;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

import resources.common.Stf;
import resources.common.UString;
import resources.z.exp.mission.MissionLocation;
import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.intangible.IntangibleObject;
import resources.z.exp.objects.waypoint.WaypointObject;

@Persistent
public class MissionObject extends IntangibleObject {
	
	@NotPersistent
	private MissionMessageBuilder messageBuilder;
	
	public MissionObject(NGECore core, long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
		baseline3.set("waypoint", (WaypointObject) core.objectService.createObject("object/waypoint/base/shared_base_waypoint.iff", planet));
	}
	
	public MissionObject() { 
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
		baseline.put("difficultyLevel", 0);
		baseline.put("startLocation", new MissionLocation(new Point3D(0, 0, 0), 0, ""));
		baseline.put("creator", new UString());
		baseline.put("creditReward", 0);
		baseline.put("targetObject", 0);
		baseline.put("description", new Stf());
		baseline.put("title", new Stf());
		baseline.put("repeatCounter", 0);
		baseline.put("missionType", "");
		baseline.put("targetName", "");
		baseline.put("waypoint", new WaypointObject());
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	public int getDifficultyLevel() {
		synchronized(objectMutex) {
			return (int) baseline3.get("difficultyLevel");
		}
	}
	
	public void setDifficultyLevel(int difficultyLevel) {
		synchronized(objectMutex) {
			baseline3.set("difficultyLevel", difficultyLevel);
		}
	}
	
	public MissionLocation getStartLocation() {
		synchronized(objectMutex) {
			return (MissionLocation) baseline3.get("startLocation");
		}
	}
	
	public void setStartLocation(Point3D position, SWGObject object, Planet planet) {
		setStartLocation(position, object, planet.getName());
	}
	
	public void setStartLocation(Point3D position, SWGObject object, String planet) {
		synchronized(objectMutex) {
			MissionLocation startLocation = new MissionLocation(position.clone(), object.getObjectID(), planet);
			baseline3.set("startLocation", startLocation);
		}
	}
	
	public String getCreator() {
		synchronized(objectMutex) {
			return (String) baseline3.get("creator");
		}
	}
	
	public void setCreator(String creator) {
		synchronized(objectMutex) {
			baseline3.set("creator", creator);
		}
	}
	
	public int getCreditReward() {
		synchronized(objectMutex) {
			return (int) baseline3.get("creditReward");
		}
	}
	
	public void setCreditReward(int creditReward) {
		synchronized(objectMutex) {
			baseline3.set("creditReward", creditReward);
		}
	}
	
	public MissionLocation getDestination() {
		synchronized(objectMutex) {
			return (MissionLocation) baseline3.get("destination");
		}
	}

	public void setDestination(Point3D position, SWGObject object, Planet planet) {
		setDestination(position, object, planet.getName());
	}
	
	public void setDestination(Point3D position, SWGObject object, String planet) {
		synchronized(objectMutex) {
			MissionLocation destination = new MissionLocation(position.clone(), object.getObjectID(), planet);
			baseline3.set("destination", destination);
		}
	}
	
	public int getTargetObject() {
		synchronized(objectMutex) {
			return (int) baseline3.get("targetObject");
		}
	}
	
	public void setTargetObject(int targetObject) {
		synchronized(objectMutex) {
			baseline3.set("targetObject", targetObject);
		}
	}
	
	public Stf getDescription() {
		synchronized(objectMutex) {
			return (Stf) baseline3.get("description");
		}
	}
	
	public void setDescription(String stfFilename, String string) {
		synchronized(objectMutex) {
			Stf description = new Stf(stfFilename, 0, string);
			baseline3.set("description", description);
		}
	}
	
	public Stf getTitle() {
		synchronized(objectMutex) {
			return (Stf) baseline3.get("title");
		}
	}
	
	public void setTitle(String stfFilename, String string) {
		synchronized(objectMutex) {
			Stf title = new Stf(stfFilename, 0, string);
			baseline3.set("title", title);
		}
	}
	
	public int getRepeatCounter() {
		synchronized(objectMutex) {
			return (int) baseline3.get("repeatCounter");
		}
	}
	
	public void setRepeatCounter(int repeatCounter) {
		synchronized(objectMutex) {
			baseline3.set("repeatCounter", repeatCounter);
		}
	}
	
	public void incrementRepeatCounter(int increase) {
		setRepeatCounter(getRepeatCounter() + 1);
	}
	
	public void decrementRepeatCounter(int decrease) {
		int repeatCounter = getRepeatCounter() + 1;
		setRepeatCounter((repeatCounter < 0) ? 0 : repeatCounter);
	}
	
	public String getMissionType() {
		synchronized(objectMutex) {
			return (String) baseline3.get("missionType");
		}
	}
	
	public void setMissionType(String missionType) {
		synchronized(objectMutex) {
			baseline3.set("missionType", missionType);
		}
	}
	
	public String getTargetName() {
		synchronized(objectMutex) {
			return (String) baseline3.get("targetName");
		}
	}
	
	public void setTargetName(String targetName) {
		synchronized(objectMutex) {
			baseline3.set("targetName", targetName);
		}
	}
	
	public WaypointObject getWaypoint() {
		synchronized(objectMutex) {
			return (WaypointObject) baseline3.get("waypoint");
		}
	}
	
	public void setWaypoint(WaypointObject waypoint) {
		synchronized(objectMutex) {
			baseline3.set("waypoint", waypoint);
		}
	}
	
	// A new mission listing is just one big delta to one of the player's
	// empty, pre-existing MissionObjects
	public void updateMission() {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.createDelta();
		}
		
		if (getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(buffer);
		}
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		getGrandparent().getClient().getSession().write(buffer);
	}
	
	@Override
	public MissionMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new MissionMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(baseline3.getBaseline());
			destination.getSession().write(baseline6.getBaseline());
			destination.getSession().write(baseline8.getBaseline());
			destination.getSession().write(baseline9.getBaseline());
		}
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		switch (viewType) {
			case 1:
			case 4:
			case 3:
			case 6:
			case 7:
			case 8:
			case 9:
				if (getGrandparent().getClient() != null) {
					buffer = getBaseline(viewType).createDelta(updateType, buffer.array());
					getGrandparent().getClient().getSession().write(buffer);
				}
			default:
				return;
		}
	}
	
}
