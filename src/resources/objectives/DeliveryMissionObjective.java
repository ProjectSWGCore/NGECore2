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
package resources.objectives;

import engine.resources.common.CRC;
import main.NGECore;
import resources.common.OutOfBand;
import resources.datatables.DisplayType;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.waypoint.WaypointObject;
import services.mission.MissionObjective;

public class DeliveryMissionObjective extends MissionObjective {

	private static final long serialVersionUID = 1L;

	private long deliveryObjectId;
	private long missionGiverId;
	private long dropOffNpcId;
	
	public DeliveryMissionObjective(MissionObject parent) {
		super(parent);
	}

	@Override
	public void activate(NGECore core, CreatureObject player) {

		if (isActivated())
			return;
		
		this.missionGiverId = parent.getStartLocation().getObjectId();
		this.dropOffNpcId = parent.getDestinationLocation().getObjectId();
		
		if (getObjectivePhase() == 0) {
			WaypointObject waypoint = getMissionObject().getWaypoint();
			waypoint.setPlanetCRC(CRC.StringtoCRC(player.getPlanet().name));
			waypoint.setPosition(parent.getStartLocation().getLocation());
			waypoint.setName("@" + parent.getTitle().getStfFilename() + ":" + "m" + parent.getMissionId() + "t");
			waypoint.setColor(WaypointObject.ORANGE);
			waypoint.setActive(true);
			getMissionObject().setWaypoint(waypoint);

		} else if (getObjectivePhase() == 1) {
			WaypointObject waypoint = getMissionObject().getWaypoint();
			waypoint.setPlanetCRC(CRC.StringtoCRC(player.getPlanet().name));
			waypoint.setPosition(parent.getDestinationLocation().getLocation());
			waypoint.setName("@" + parent.getTitle().getStfFilename() + ":" + "m" + parent.getMissionId() + "t");
			waypoint.setColor(WaypointObject.ORANGE);
			waypoint.setActive(true);
			getMissionObject().setWaypoint(waypoint);
		}

		setActive(true);
	}

	@Override
	public void complete(NGECore core, CreatureObject player) {

		int reward = getMissionObject().getCreditReward();
		
		player.addBankCredits(reward);
		
		player.sendSystemMessage(OutOfBand.ProsePackage("@mission/mission_generic:success_w_amount", reward), DisplayType.Broadcast);
	}

	@Override
	public void abort(NGECore core, CreatureObject player) {
		TangibleObject deliveryObject = (TangibleObject) core.objectService.getObject(deliveryObjectId);
		if (deliveryObject != null)
			core.objectService.destroyObject(deliveryObject);
	}
	
	@Override
	public void update(NGECore core, CreatureObject player) {
		
		setObjectivePhase(getObjectivePhase() + 1);

		switch(getObjectivePhase()) {
			case 1:
				WaypointObject waypoint = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", parent.getPlanet());
				waypoint.setPosition(parent.getDestinationLocation().getLocation());
				waypoint.setName("@" + parent.getTitle().getStfFilename() + ":" + "m" + parent.getMissionId() + "t");
				waypoint.setColor(WaypointObject.ORANGE);
				waypoint.setActive(true);
				waypoint.setPlanetCRC(CRC.StringtoCRC(player.getPlanet().name));
				getMissionObject().setTargetName("Dropoff Location");
				getMissionObject().setWaypoint(waypoint);
				break;
			case 2:
				WaypointObject returnWp = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", parent.getPlanet());
				returnWp.setPosition(parent.getStartLocation().getLocation());
				returnWp.setName("@" + parent.getTitle().getStfFilename() + ":" + "m" + parent.getMissionId() + "t");
				returnWp.setColor(WaypointObject.ORANGE);
				returnWp.setActive(true);
				returnWp.setPlanetCRC(CRC.StringtoCRC(player.getPlanet().name));
				getMissionObject().setTargetName("Return");
				getMissionObject().setWaypoint(returnWp);
				break;
		}
	}
	
	public boolean createDeliveryItem(NGECore core, CreatureObject player) {

		if (player == null)
			return false;
		
		TangibleObject inventory = (TangibleObject) player.getSlottedObject("inventory");
		
		if (inventory == null)
			return false;
		
		TangibleObject deliveryObject = (TangibleObject) core.objectService.createObject("object/tangible/mission/shared_mission_datadisk.iff", player.getPlanet());
		
		if (deliveryObject != null && inventory.add(deliveryObject)) {
			setDeliveryObject(deliveryObject.getObjectID());
			return true;
		}
		else 
			return false;
	}
	
	public long getDeliveryObject() { return deliveryObjectId; }
	
	public void setDeliveryObject(long object) { this.deliveryObjectId = object; }

	public long getMissionGiver() { return missionGiverId; }

	public void setMissionGiver(long missionGiver) { this.missionGiverId = missionGiver; }

	public long getDropOffNpc() { return dropOffNpcId; }

	public void setDropOffNpc(long dropOffNpc) { this.dropOffNpcId = dropOffNpc; }

}
