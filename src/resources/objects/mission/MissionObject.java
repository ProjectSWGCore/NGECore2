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

import java.io.Serializable;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.intangible.IntangibleObject;
import resources.objects.waypoint.WaypointObject;
import services.mission.MissionLocation;
import services.mission.MissionObjective;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.common.Stf;
import engine.resources.common.UString;
import engine.resources.objects.Baseline;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class MissionObject extends IntangibleObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private transient MissionMessageBuilder messageBuilder = new MissionMessageBuilder(this);
	
	private MissionObjective objective;
	
	public MissionObject() {
		super();
	}
	
	public MissionObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0), template);
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		//baseline.put("objective", new DefaultMissionObjective(this));
		baseline.put("bountyMarkId", (long) 0);
		baseline.put("missionId", 0);
		baseline.put("missionType", "");
		return baseline;
	}
	
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("difficultyLevel", 0);
		baseline.put("startLocation", new MissionLocation(new Point3D(0,0,0), 0, "tatooine"));
		baseline.put("creator", new UString(""));
		baseline.put("creditReward", 0);
		baseline.put("destinationLocation", new MissionLocation(new Point3D(0,0,0), 0, "tatooine"));
		baseline.put("templateObject", 0);
		baseline.put("description", new Stf("", 0, ""));
		baseline.put("title", new Stf("", 0, ""));
		baseline.put("repeatCounter", 0);
		baseline.put("missionType", 0);
		baseline.put("targetName", "");
		baseline.put("waypoint", (WaypointObject) NGECore.getInstance().objectService.createObject("object/waypoint/base/shared_base_waypoint.iff", NGECore.getInstance().terrainService.getPlanetByID(1)));
		return baseline;
	}
	
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("2", -1);
		return baseline;
	}
	
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	@Override
	public void initAfterDBLoad() {
		super.init();
		messageBuilder = new MissionMessageBuilder(this);
	}
	
	public int getDifficultyLevel() {
		return (int) getBaseline(3).get("difficultyLevel");
	}
	
	public void setDifficultyLevel(int level) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("difficultyLevel", level));
		}
	}
	
	public MissionLocation getStartLocation() {
		return (MissionLocation) getBaseline(3).get("startLocation");
	}
	
	public void setStartLocation(MissionLocation startLocation) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("startLocation", startLocation));
		}
	}
	
	public String getCreator() {
		return ((UString) getBaseline(3).get("creator")).get();
	}
	
	public void setCreator(String creator) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("creator", new UString(creator)));
		}
	}
	
	public int getCreditReward() {
		return (int) getBaseline(3).get("creditReward");
	}
	
	public void setCreditReward(int credits) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("creditReward", credits));
		}
	}
	
	public MissionLocation getDestinationLocation() {
		return (MissionLocation) getBaseline(3).get("destinationLocation");
	}
	
	public void setDestinationLocation(MissionLocation destinationLocation) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("destinationLocation", destinationLocation));
		}
	}
	
	public int getTemplateObject() {
		return (int) getBaseline(3).get("templateObject");
	}
	
	public void setTemplateObject(int objCRC) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("templateObject", objCRC));
		}
	}
	
	public Stf getDescription() {
		return (Stf) getBaseline(3).get("description");
	}
	
	public void setDescription(String description) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("description", new Stf(description)));
		}
	}
	
	public Stf getTitle() {
		return (Stf) getBaseline(3).get("title");
	}
	
	public void setTitle(String title) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("title", new Stf(title)));
		}
	}
	
	public int getRepeatCounter() {
		return (int) getBaseline(3).get("repeatCounter");
	}
	
	public void setRepeatCounter(int repeatCounter) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("repeatCounter", repeatCounter));
		}
	}
	
	public void incrementRepeatCounter() {
		setRepeatCounter(getRepeatCounter() + 1);
	}
	
	public void decrementRepeatCounter() {
		int repeatCounter = getRepeatCounter() - 1;
		setRepeatCounter((repeatCounter < 0) ? 0 : repeatCounter);
	}
	
	public int getMissionTypeCRC() {
		return (int) getBaseline(3).get("missionType");
	}
	
	public String getMissionType() {
		return (String) otherVariables.get("missionType");
	}
	
	public void setMissionType(String missionType) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("missionType", CRC.StringtoCRC(missionType)));
			otherVariables.set("missionType", missionType);
		}
	}
	
	public String getTargetName() {
		return (String) getBaseline(3).get("targetName");
	}
	
	public void setTargetName(String targetName) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("targetName", targetName));
		}
	}
	
	public WaypointObject getWaypoint() {
		return (WaypointObject) getBaseline(3).get("waypoint");
	}
	
	public void setWaypoint(WaypointObject waypoint) {
		if (getGrandparent() != null && getGrandparent().getClient() != null) {
			getGrandparent().getClient().getSession().write(getBaseline(3).set("waypoint", waypoint));
		}
	}
	
	public MissionObjective getObjective() {
		/*if (otherVariables.get("objective") instanceof DefaultMissionObjective) {
			return null;
		} else {
			return (MissionObjective) otherVariables.get("objective");
		}*/
		return objective;
	}
	
	public void setObjective(MissionObjective objective) {
		//otherVariables.set("objective", objective);
		this.objective = objective;
	}
	
	public long getBountyMarkId() {
		return (long) otherVariables.get("bountyMarkId");
	}
	
	public void setBountyMarkId(long bountyMarkId) {
		otherVariables.set("bountyMarkId", bountyMarkId);
	}
	
	public int getMissionId() {
		return (int) otherVariables.get("missionId");
	}
	
	public void setMissionId(int missionId) {
		otherVariables.set("missionId", missionId);
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(getBaseline(3).getBaseline());
			destination.getSession().write(getBaseline(6).getBaseline());
			destination.getSession().write(getBaseline(8).getBaseline());
			destination.getSession().write(getBaseline(9).getBaseline());
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
	
	public MissionMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null)
				messageBuilder = new MissionMessageBuilder(this);
			
			return messageBuilder;
		}
	}
	
}
