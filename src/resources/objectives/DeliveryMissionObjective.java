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

import engine.resources.scene.Point3D;
import main.NGECore;
import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.waypoint.WaypointObject;
import services.mission.MissionObjective;

public class DeliveryMissionObjective extends MissionObjective {

	private TangibleObject deliveryObject;
	private CreatureObject missionGiver;
	private CreatureObject dropOffNpc;
	
	public DeliveryMissionObjective(MissionObject parent) {
		super(parent);
	}

	@Override
	public void activate(NGECore core, CreatureObject player) {

		if (isActivated())
			return;
		
		String template = "object/mobile/shared_dressed_commoner_tatooine_sullustan_male_06.iff";
		
		Point3D startLoc = parent.getStartLocation();
		Point3D destination = parent.getDestination();

		// TODO: Randomize this process.
		CreatureObject missionGiver = (CreatureObject) core.staticService.spawnObject(template, parent.getPlanet().name, 0, startLoc.x, startLoc.y, startLoc.z, 0, 1);
		if (missionGiver == null)
			return;

		missionGiver.setCustomName("a commoner");
		missionGiver.setAttachment("conversationFile", "missions/deliver");
		missionGiver.setAttachment("radial_filename", "object/conversation");
		missionGiver.setAttachment("assignedMission", getMissionObject().getObjectId());
		missionGiver.setOptionsBitmask(264);
		setMissionGiver(missionGiver);

		CreatureObject dropOffNpc = (CreatureObject) core.staticService.spawnObject(template, parent.getPlanet().name, 0, destination.x, destination.y, destination.z, 0, 1);
		if (dropOffNpc == null)
			return;
		
		dropOffNpc.setCustomName("a commoner");
		dropOffNpc.setAttachment("conversationFile", "missions/deliver");
		dropOffNpc.setAttachment("radial_filename", "object/conversation");
		dropOffNpc.setAttachment("assignedMission", getMissionObject().getObjectId());
		dropOffNpc.setOptionsBitmask(264);
		setDropOffNpc(dropOffNpc);
		
		if (getObjectivePhase() == 0) {
			WaypointObject waypoint = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", parent.getPlanet());
			waypoint.setPosition(startLoc);
			waypoint.setName("@mission/" + parent.getMissionTitle() + ":" + "m" + parent.getMissionId() + "t");
			waypoint.setColor(WaypointObject.ORANGE);
			waypoint.setActive(true);
			getMissionObject().setAttachedWaypoint(waypoint);
		} else if (getObjectivePhase() == 1) {
			WaypointObject waypoint = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", parent.getPlanet());
			waypoint.setPosition(parent.getDestination());
			waypoint.setName("@mission/" + parent.getMissionTitle() + ":" + "m" + parent.getMissionId() + "t");
			waypoint.setColor(WaypointObject.ORANGE);
			waypoint.setActive(true);
			getMissionObject().setAttachedWaypoint(waypoint);
		}

		setActive(true);
	}

	@Override
	public void complete(NGECore core, CreatureObject player) {

		int reward = getMissionObject().getCreditReward();
		
		player.addBankCredits(reward);
		
		player.sendSystemMessage(new OutOfBand(new ProsePackage("@mission/mission_generic:success_w_amount", reward)), (byte) 0);
		
		abort(core, player);
		
		core.objectService.destroyObject(getMissionObject());
	}

	@Override
	public void abort(NGECore core, CreatureObject player) {
		if (deliveryObject != null)
			core.objectService.destroyObject(deliveryObject.getObjectId());
		
		if (missionGiver != null)
			core.objectService.destroyObject(missionGiver, 360);
		
		if (dropOffNpc != null)
			core.objectService.destroyObject(dropOffNpc, 360);
	}
	
	@Override
	public void update(NGECore core, CreatureObject player) {
		
		setObjectivePhase(getObjectivePhase() + 1);

		switch(getObjectivePhase()) {
			case 1:
				WaypointObject waypoint = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", parent.getPlanet());
				waypoint.setPosition(parent.getDestination());
				waypoint.setName("@mission/" + parent.getMissionTitle() + ":" + "m" + parent.getMissionId() + "t");
				waypoint.setColor(WaypointObject.ORANGE);
				waypoint.setActive(true);
				getMissionObject().setMissionTargetName("Dropoff Location");
				getMissionObject().setAttachedWaypoint(waypoint);
				System.out.println("Waypoint set to " + parent.getDestination().x + " and " + parent.getDestination().z);
				break;
			case 2:
				WaypointObject returnWp = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", parent.getPlanet());
				returnWp.setPosition(parent.getStartLocation());
				returnWp.setName("@mission/" + parent.getMissionTitle() + ":" + "m" + parent.getMissionId() + "t");
				returnWp.setColor(WaypointObject.ORANGE);
				returnWp.setActive(true);
				getMissionObject().setMissionTargetName("Return");
				getMissionObject().setAttachedWaypoint(returnWp);
				break;
		}
	}
	
	public boolean createDeliveryItem(NGECore core, CreatureObject player) {

		if (player == null)
			return false;
		
		TangibleObject inventory = (TangibleObject) player.getSlottedObject("inventory");
		
		if (inventory == null)
			return false;
		
		TangibleObject deliveryObject = (TangibleObject) core.objectService.createObject("object/tangible/mission/shared_mission_datadisk.iff", getMissionObject().getGrandparent().getPlanet());
		
		if (deliveryObject != null && inventory.add(deliveryObject)) {
			setDeliveryObject(deliveryObject);
			return true;
		}
		else 
			return false;
	}
	
	public TangibleObject getDeliveryObject() { return deliveryObject; }
	
	public void setDeliveryObject(TangibleObject object) { this.deliveryObject = object; }

	public CreatureObject getMissionGiver() { return missionGiver; }

	public void setMissionGiver(CreatureObject missionGiver) { this.missionGiver = missionGiver; }

	public CreatureObject getDropOffNpc() { return dropOffNpc; }

	public void setDropOffNpc(CreatureObject dropOffNpc) { this.dropOffNpc = dropOffNpc; }

}
