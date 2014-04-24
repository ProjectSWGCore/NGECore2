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

import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import main.NGECore;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.waypoint.WaypointObject;
import services.mission.MissionObjective;

public class DeliveryMissionObjective extends MissionObjective {

	private TangibleObject deliveryObject;
	private NGECore core = NGECore.getInstance();
	
	public DeliveryMissionObjective(MissionObject parent) {
		super(parent);
	}

	@Override
	public void activate() {

		if (isActivated())
			return;
		
		String template = "object/mobile/shared_dressed_commoner_tatooine_sullustan_male_06.iff";
		Point3D startLoc = parent.getStartLocation();

		CreatureObject missionGiver = (CreatureObject) core.staticService.spawnObject(template, parent.getPlanet().name, 0, startLoc.x, startLoc.y, startLoc.z, 0, 1);
		missionGiver.setAttachment("conversationFile", "missions/deliver");
		missionGiver.setAttachment("radial_filename", "object/conversation");
		missionGiver.setAttachment("assignedMission", getMissionObject());
		missionGiver.setOptionsBitmask(264);
		
		if (getObjectivePhase() == 0) {
			WaypointObject waypoint = (WaypointObject) core.objectService.createObject("object/waypoint/shared_waypoint.iff", parent.getPlanet());
			waypoint.setPosition(startLoc);
			waypoint.setName("@mission/" + parent.getMissionTitle() + ":" + parent.getMissionId());
			waypoint.setColor(WaypointObject.ORANGE);
			waypoint.setActive(true);
			getMissionObject().setAttachedWaypoint(waypoint);
		}

		setActive(true);
	}

	@Override
	public void update() {
	}

	@Override
	public void complete() {
	}

	@Override
	public void drop() {
	}
	
	public boolean createDeliveryItem() {

		SWGObject player = getMissionObject().getGrandparent();
		
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
}
