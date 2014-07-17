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
package services.pet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

import org.apache.commons.collections.CollectionUtils;

import protocol.swg.UpdateContainmentMessage;
import main.NGECore;
import resources.common.OutOfBand;
import resources.datatables.DisplayType;
import resources.datatables.Options;
import resources.datatables.Posture;
import resources.datatables.State;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import tools.DevLog;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class MountService implements INetworkDispatch {
	
	private NGECore core;
	
	public MountService(NGECore core) {
		this.core = core;
	}
	
	// Creature mounts are considered pets as well
	// Pet abilities and petId is in PLAY9
	
	// The functions for vehicles, mounts and pets are similar, but not the same
	// Hence the multiple services.
	
	public void generateVehicle(CreatureObject actor, SWGObject deed, String vehicleTemplate, String pcdTemplate) {
		if (actor == null || vehicleTemplate == null || pcdTemplate == null) {
			return;
		}
		
		if (actor.getSlottedObject("ghost") == null) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:failed_to_call_vehicle"), DisplayType.Broadcast);
			return;
		}
		
		SWGObject datapad = actor.getSlottedObject("datapad");
		
		if (datapad == null) {
			return;
		}
		
		// Unsure if these are the right attributes.  It doesn't generate the vehicle if the datapad has max # of vehicles.
		//if (datapad.getIntAttribute("data_size") >= datapad.getIntAttribute("datapad_slots")) {
			//actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:has_max_vehicle"), DisplayType.Broadcast);
			//return;
		//}
		
		if (actor.getTefTime() > 0){
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:prose_cant_generate_yet", actor.getTefTime()), DisplayType.Broadcast);
			return;
		}
		
		SWGObject pcd = core.objectService.createObject(pcdTemplate, actor.getPlanet());
		
		if (pcd == null) {
			return;
		}
		
		if (pcd.getSlottedObject("inventory") == null) {
			pcd.add(core.objectService.createObject("object/tangible/inventory/shared_character_inventory.iff", pcd.getPlanet()));
		}
		
		CreatureObject vehicle = (CreatureObject) core.objectService.createObject(vehicleTemplate, actor.getPlanet());
		
		if (vehicle == null) {
			return;
		}
		
		vehicle.setOptions(Options.MOUNT | Options.ATTACKABLE, true);
		
		vehicle.setOwnerId(actor.getObjectID());
		
		pcd.setAttachment("companionId", vehicle.getObjectID());
		
		pcd.getSlottedObject("inventory").add(vehicle);
		
		datapad.add(pcd);
		
		if (deed != null) {
			core.objectService.destroyObject(deed);
		}
		
		actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:device_added"), DisplayType.Broadcast);
		
		call(actor, pcd);
	}
	
	public void generateMount(CreatureObject actor, SWGObject deed, String vehicleTemplate, String pcdTemplate) {
		return; // NOT IMPLEMENTED
	}
	
	public void call(CreatureObject actor, SWGObject pcd) {
		if (actor == null) {
			return;
		}
		
		if (pcd == null) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cant_call"), DisplayType.Broadcast);
			return;
		}
		
		if (isMounted(actor)) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cannot_call_another_rideable"), DisplayType.Broadcast);
			return;
		}
		
		if (actor.isInCombat()) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cannot_call_in_combat"), DisplayType.Broadcast);
			return;
		}
		
		if (actor.getContainer() != null) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cannot_call_indoors"), DisplayType.Broadcast);
			return;
		}
		
		if (actor.getPosture() == Posture.Dead) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cannot_call_while_dead"), DisplayType.Broadcast);
			return;
		}
		
		if (pcd.getSlottedObject("inventory") == null) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cant_call"), DisplayType.Broadcast);
			return;
		}
		
		if (pcd.getStringAttribute("required_faction") != null && pcd.getStringAttribute("required_faction").length() > 0) {
			if (!actor.getFaction().equals(pcd.getStringAttribute("required_faction"))) {
				actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:officer_faction"), DisplayType.Broadcast);
				return;
			}
		}
		
		PlayerObject player = (PlayerObject) actor.getSlottedObject("ghost");
		
		if (player == null) {
			return;
		}
		
		CreatureObject mount = (CreatureObject) core.objectService.getObject((Long) pcd.getAttachment("companionId"));
		
		if (mount == null) {
			// Somehow the vehicle object has got lost
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cant_call"), DisplayType.Broadcast);
			return;
		}
		
		mount.setAttachment("pcdAppearanceFilename", pcd.getTemplateData().getAttribute("appearanceFilename"));
		
		mount.setFaction(actor.getFaction());
		mount.setFactionStatus(actor.getFactionStatus());
		mount.setOwnerId(actor.getObjectID());
		
		if (pcd.getTemplate().contains("vehicle")) {
			callVehicle(actor, pcd, player, mount);
		} else {
			callMount(actor, pcd, player, mount);
		}
	}
	
	private void callVehicle(CreatureObject actor, SWGObject pcd, PlayerObject player, CreatureObject mount) {		
		if ((mount.getLevel() - actor.getLevel()) > 5) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cant_call_level"), DisplayType.Broadcast);
			return;
		}
		
		// FIXME Movement skillmod should always be used instead of CREO4 speed vars directly.  Movement skillmod should NEVER be 0 unless rooted.  Currently it is, which is wrong.
		//if (actor.getSkillModBase("movement") == 0) {
			//actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cant_call_vehicle_rooted"), DisplayType.Broadcast);
			//return;
		//}
		
		if (actor.getPlanet().getName().contains("kashyyyk") && !actor.getPlanet().getName().contains("_main")) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:vehicle_restricted_scene"), DisplayType.Broadcast);
			//mount_restricted_scene for creature mounts
			return;
		}
		
		if (player.isCallingCompanion()) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cant_call_1sec"), DisplayType.Broadcast);
			return;
		}
		
		core.petService.storeAll(actor);
		storeAll(actor);
		
		
		player.setCallingCompanion(true);
		
		if (actor.getTefTime() > 0) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:call_vehicle_delay", actor.getTefTime()), DisplayType.Broadcast);
		}
		
		try {
			while (actor.getTefTime() > 0) {
					Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		SWGObject datapad = actor.getSlottedObject("datapad");
		
		if (datapad == null) {
			return;
		}
		
		datapad.viewChildren(actor, false, false, new Traverser() {
			
			public void process(SWGObject pcd) {
				if (pcd.getAttachment("companionId") != null && mount.getObjectID() == ((Long) pcd.getAttachment("companionId"))) {
					if (pcd.getSlottedObject("inventory") != null) {
						LongAdder adder = new LongAdder();
						pcd.getSlottedObject("inventory").viewChildren(actor, false, false, (obj) -> adder.increment());
						
						if (adder.intValue() == 1) {
							pcd.getSlottedObject("inventory").remove(mount);
							mount.setPosition(actor.getPosition().clone());
							mount.setOrientation(actor.getOrientation().clone());
							mount.setPlanet(actor.getPlanet());
							DevLog.debugout("Charon", "Mount Service", "PROCESS MOUNT " + mount.getTemplate()); 
							core.simulationService.add(mount, actor.getWorldPosition().x, actor.getWorldPosition().z, false);
						}
					}
				}
			}
			
		});
		
		player.setCallingCompanion(false);
		
		// Make the vehicle visible
		
		List<SWGObject> newAwareObjects = core.simulationService.get(actor.getPlanet(), actor.getWorldPosition().x, actor.getWorldPosition().z, 512);
		ArrayList<SWGObject> oldAwareObjects = new ArrayList<SWGObject>(actor.getAwareObjects());
		@SuppressWarnings("unchecked") Collection<SWGObject> updateAwareObjects = CollectionUtils.intersection(oldAwareObjects, newAwareObjects);
		
		for(int i = 0; i < newAwareObjects.size(); i++) {
			SWGObject obj = newAwareObjects.get(i);
			//System.out.println(obj.getTemplate());
			if(!updateAwareObjects.contains(obj) && obj != actor && !actor.getAwareObjects().contains(obj) &&  obj.getContainer() != actor && obj.isInQuadtree()) {						
				if(obj.getAttachment("bigSpawnRange") == null && obj.getWorldPosition().getDistance2D(actor.getWorldPosition()) > 200)
					continue;						
				actor.makeAware(obj);
				if(obj.getClient() != null)
					obj.makeAware(actor);
			}
		}
		
	}
	
	private void callMount(CreatureObject actor, SWGObject pcd, PlayerObject player, CreatureObject mount) {
		return; // NOT IMPLEMENTED
	}
	
	public void damage(CreatureObject mount) {
		// alter components to progressively reduce the painting.
		//stfs
		//customization_fading_veh
		//customization_gone_veh
		
		if (mount == null) {
			return;
		}
		
		CreatureObject owner = (CreatureObject) core.objectService.getObject(mount.getOwnerId());
		
		if (!mount.getTemplate().contains("vehicle")) {
			dismount(owner, mount);
			store(owner, mount);
			mount.setConditionDamage(0);
			return;
		}
		
		if (mount.getConditionDamage() >= mount.getMaximumCondition()) {
			disable(mount);
		}
	}
	
	public void disable(CreatureObject mount) {
		mount.setOptions(Options.DISABLED, true);
		
		mount.viewChildren(mount, false, false, new Traverser() {
			
			public void process(SWGObject object) {
				if (object == null || !(object instanceof CreatureObject)) {
					return;
				}
				
				CreatureObject rider = (CreatureObject) object;
				
				dismount(rider, mount);
				
				rider.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:veh_disabled"), DisplayType.Broadcast);
			}
			
		});
	}
	
	public boolean canRepair(CreatureObject actor, CreatureObject mount) {
		if (actor == null || mount == null) {
			return false;
		}
		
		if (!mount.getOption(Options.MOUNT)) {
			return false;
		}
		
		if (!mount.getTemplate().contains("vehicle")) {
			return false;
		}
		
		if (mount.getOwnerId() != actor.getObjectID()) {
			return false;
		}
		
		if (mount.getOption(Options.DISABLED)) {
			return false;
		}
		
		if (mount.getConditionDamage() == 0) {
			return false;
		}
		
		List<SWGObject> surroundings = core.simulationService.get(mount.getPlanet(), mount.getPosition().x, mount.getPosition().z, 32);
		
		for (SWGObject object : surroundings) {
			if (object instanceof BuildingObject) {
				if (object.getTemplate().contains("garage")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void repair(CreatureObject repairer, CreatureObject mount) {
		if (repairer == null || mount == null) {
			return;
		}
		
		if (!mount.getOption(Options.MOUNT)) {
			return;
		}
		
		if (core.objectService.getObject(mount.getOwnerId()) != repairer) {
			return;
		}
		
		if (mount.getOption(Options.DISABLED)) {
			repairer.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cannot_repair_disabled"), DisplayType.Broadcast);
			return;
		}
		
		if (mount.getConditionDamage() == 0) {
			repairer.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:undamaged_vehicle"), DisplayType.Broadcast);
			return;
		}
		
		// This should produce an SUI window regarding cost and cost might be determined differently.
		// But at the moment it's not important.
		
		if (!canRepair(repairer, mount)) {
			repairer.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:repair_unrecognized_garages"), DisplayType.Broadcast);
			return;
		}
		
		int cost = mount.getConditionDamage();
		
		if (repairer.getCashCredits() < cost) {
			repairer.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:repair_failed_due_to_funds"), DisplayType.Broadcast);
			return;
		}
		
		mount.setConditionDamage(0);
		
		mount.setOptions(Options.DISABLED, false);
		
		repairer.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:repaired_to_max"), DisplayType.Broadcast);
	}
	
	public void mount(CreatureObject rider, CreatureObject mount) {
		// Check if mount may be mounted // This is already checked in canMount
		
		if (rider == null) {
			return;
		}
		
		if (mount == null) {
			return;
		}
		
		if (!mount.getOption(Options.MOUNT)) {
			return;
		}
		
		if (mount.getOption(Options.DISABLED))  {
			rider.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cant_mount_veh_disabled"), DisplayType.Broadcast);
			return;
		}
		
		if (rider.isInStealth()) {
			rider.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:no_mount_stealth"), DisplayType.Broadcast);
			return;
		}
		
		if (!canMount(rider, mount)) {
			rider.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cant_mount"), DisplayType.Broadcast);
			return;
		}
		
		mount._add(rider);
		mount.notifyObservers(new UpdateContainmentMessage(rider.getObjectID(), mount.getObjectID(), 4), true);
		core.simulationService.remove(rider, rider.getWorldPosition().x, rider.getWorldPosition().z, false);
		
		rider.setState(State.RidingMount, true);
		mount.setState(State.MountedCreature, true);
		
		// For some reason SOE decided the mount would need the following posture to be set for the character to have the driving or riding animation.
		mount.setPosture((mount.getTemplate().contains("vehicle")) ? Posture.DrivingVehicle : Posture.RidingCreature);
		
		if (!mount.getSlotNameForObject(rider).equals("rider1")) {
			core.buffService.addBuffToCreature(rider, "vehicle_passenger", mount);
		}
	}
	
	public CreatureObject getMount(SWGObject pcd) {
		if (pcd == null) {
			return null;
		}
		
		if (pcd.getAttachment("companionId") == null) {
			return null;
		}
		
		CreatureObject mount = (CreatureObject) core.objectService.getObject((Long) pcd.getAttachment("companionId"));
		
		if (mount == null) {
			return null;
		}
		
		if (mount.getContainer() != null) {
			return null;
		}
		
		return mount;
	}
	
	public CreatureObject getCompanion(CreatureObject actor) {
		if (actor == null) {
			return null;
		}
		
		if (isMounted(actor)) {
			return (CreatureObject) actor.getContainer();
		}
		
		SWGObject datapad = actor.getSlottedObject("datapad");
		
		if (datapad == null) {
			return null;
		}
		
		final List<CreatureObject> companions = new ArrayList<CreatureObject>();
		
		datapad.viewChildren(actor, false, false, new Traverser() {
			
			public void process(SWGObject pcd) {
				if (pcd.getAttachment("companionId") != null) {
					if (pcd.getSlottedObject("inventory") != null) {
						LongAdder adder = new LongAdder();
						pcd.getSlottedObject("inventory").viewChildren(actor, false, false, (obj) -> adder.increment());
						
						if (adder.intValue() == 0) {
							CreatureObject companion = (CreatureObject) core.objectService.getObject((Long) pcd.getAttachment("companionId"));
							companions.add(companion);
						}
					}
				}
			}
			
		});
		
		if (companions.size() > 0) {
			return companions.get(0);
		} else {
			return null;
		}
	}
	
	public boolean isMounted(CreatureObject rider) {
		if (rider.getContainer() == null) {
			return false;
		}
		
		if (!(rider.getContainer() instanceof CreatureObject)) {
			return false;
		}
		
		CreatureObject mount = (CreatureObject) rider.getContainer();
		
		if (!mount.getOption(Options.MOUNT)) {
			return false;
		}
		
		return true;
	}
	
	public boolean isMounted(SWGObject actor, CreatureObject mount) {
		return actor.getContainer() == mount;
	}
	
	public boolean canMount(CreatureObject rider, CreatureObject mount) {
		if (mount == null) {
			return false;
		}
		
		if (!mount.getOption(Options.MOUNT)) {
			return false;
		}
		
		if (isMounted(rider)) {
			return false;
		}
		
		if (rider.getObjectID() == mount.getOwnerId()) {
			return true;
		}
		
		// It's a potential passenger
		
		// See if there's driver first
		if (mount.getSlottedObject("rider") == null) {
			return false;
		}
		
		// Check if there are any passenger slots left
		
		LongAdder adder = new LongAdder();
		
		try {
			mount.viewChildren(mount, false, false, (object) -> adder.increment());
		} catch(Exception ex) {
			
		}
		
		int passengers = adder.intValue();
		
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/mount/saddle_appearance_map.iff", DatatableVisitor.class);
			
			for (int i = 0; i < visitor.getRowCount(); i++) {
				if (visitor.getObject(i, 2).equals(mount.getAttachment("pcdAppearanceFilename"))) { // saddle_appearance_filename
					if (passengers >= (int) visitor.getObject(i, 1)) { // saddle_capacity
						return false;
					}
					
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CreatureObject owner = (CreatureObject) NGECore.getInstance().objectService.getObject(mount.getOwnerId());
		
		if (owner == null) {
			return false;
		}
		
		if (owner.isAttackableBy(rider)) {
			return false;
		}
		
		if (rider.getGroupId() == 0 || rider.getGroupId() != owner.getGroupId()) {
			return false;
		}
		
		return true;
	}
	
	public void dismount(CreatureObject rider, CreatureObject mount) {

		if (rider == null || mount == null) {
			return;
		}
		
		if (!isMounted(rider)) {
			return;
		}
		
		if (!mount.getOption(Options.MOUNT)) {
			return;
		}
		
		// Dismount all passengers
		if (rider.getObjectID() == mount.getOwnerId()) {
			
			mount.viewChildren(mount, false, false, new Traverser()
			{
				public void process(SWGObject passenger)
				{
					if (passenger.getObjectId() != mount.getOwnerId()) dismount((CreatureObject) passenger, mount);
				}
				
			});
		}
		
		// Remove rider from mount
		mount._remove(rider);

		// Set mount states and stuff
		if (rider.getObjectID() == mount.getOwnerId()) {
			mount.setStateBitmask(mount.getOptionsBitmask() & ~State.MountedCreature);
			mount.setState(State.MountedCreature, false);
		}
		
		// Set rider states and stuff
		rider.setStateBitmask(rider.getOptionsBitmask() & ~State.RidingMount);
		rider.setState(State.RidingMount, false);
		rider.setPosture(Posture.Upright);
			
		// Update observers and quadtree
		mount.notifyObservers(new UpdateContainmentMessage(rider.getObjectID(), 0, -1), true);
		core.simulationService.teleport(rider, mount.getWorldPosition(), mount.getOrientation(), 0);
		core.simulationService.add(rider, mount.getWorldPosition().x, mount.getWorldPosition().z, false);
		
		core.buffService.clearBuffs(mount);
		core.buffService.removeBuffFromCreature(rider, rider.getBuffByName("vehicle_passenger"));
		
		// Store mount if it's a creature
		if (!mount.getTemplate().contains("vehicle") && rider.getObjectID() == mount.getOwnerId()) {
			store(rider, mount);
		}
	}
	
	/*
	 * They seem to have been stored in the inventory of the pcd object.
	 * Judging by the fact pcds have inventories and many other things don't.
	 */
	public void store(CreatureObject storer, CreatureObject mount) {
		if (mount == null) {
			System.err.println("MountService:store(): mount is null; this should never be the case.");
			return;
		}
		
		if (mount.getContainer() != null) {
			return;
		}
		
		if (mount.getOwnerId() == 0) {
			return;
		}
		
		CreatureObject owner = (CreatureObject) core.objectService.getObject(mount.getOwnerId());
		
		if (owner == null || storer == null || owner != storer) {
			return;
		}
		
		PlayerObject player = (PlayerObject) owner.getSlottedObject("ghost");
		
		if (player.isCallingCompanion()) {
			storer.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:cant_store_1sec"), DisplayType.Broadcast);
			return;
		}
		
		if (isMounted(owner, mount)) {
			//storer.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:must_dismount"), DisplayType.Broadcast);
			//return;
			dismount(storer, mount);
		}
		
		if (owner.getTefTime() > 0) {
			owner.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:prose_cant_store_yet", owner.getTefTime()), DisplayType.Broadcast);
			return;
		}
		
		SWGObject datapad = owner.getSlottedObject("datapad");
		
		if (datapad == null) {
			return;
		}
		
		datapad.viewChildren(owner, false, false, new Traverser() {
			
			public void process(SWGObject pcd) {
				if (pcd.getAttachment("companionId") != null && mount.getObjectID() == ((Long) pcd.getAttachment("companionId"))) {
					if (pcd.getSlottedObject("inventory") != null) {
						LongAdder adder = new LongAdder();
						pcd.getSlottedObject("inventory").viewChildren(owner, false, false, (obj) -> adder.increment());
						
						if (adder.intValue() == 0) {
							core.simulationService.remove(mount, mount.getWorldPosition().x, mount.getWorldPosition().z, true);
							pcd.getSlottedObject("inventory").add(mount);
						}
					}
				}
			}
			
		});
	}
	
	public void storeAll(CreatureObject actor) {
		if (actor == null) {
			return;
		}
		
		while (getCompanion(actor) != null) {
			SWGObject mount = getCompanion(actor);
			
			SWGObject datapad = actor.getSlottedObject("datapad");
			
			if (datapad == null) {
				return;
			}
			
			datapad.viewChildren(actor, false, false, new Traverser() {
				
				public void process(SWGObject pcd) {
					if (pcd.getAttachment("companionId") != null && mount.getObjectID() == ((Long) pcd.getAttachment("companionId"))) {
						if (pcd.getSlottedObject("inventory") != null) {
							LongAdder adder = new LongAdder();
							pcd.getSlottedObject("inventory").viewChildren(actor, false, false, (obj) -> adder.increment());
							
							if (adder.intValue() == 0) {
								core.simulationService.remove(mount, mount.getWorldPosition().x, mount.getWorldPosition().z, true);
								pcd.getSlottedObject("inventory").add(mount);
							}
						}
					}
				}
				
			});
		}
	}
	
	public void destroy(CreatureObject destroyer, SWGObject pcd) {
		if (destroyer == null || pcd == null || pcd.getGrandparent() == null) {
			return;
		}
		
		if (destroyer.getObjectID() != pcd.getGrandparent().getObjectID()) {
			return;
		}
		
		if (pcd.getAttachment("companionId") == null) {
			return;
		}
		
		CreatureObject mount = (CreatureObject) core.objectService.getObject((Long) pcd.getAttachment("companionId"));
		
		dismount(destroyer, mount);
		
		store(destroyer, mount);
		
		String type = mount.getTemplate();
		
		core.objectService.destroyObject(mount);
		core.objectService.destroyObject(pcd);
		
		if (type.contains("vehicle")) {
			destroyer.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:vehicle_released"), DisplayType.Broadcast);
		} else {
			destroyer.sendSystemMessage(OutOfBand.ProsePackage("@pet/pet_menu:pet_released"), DisplayType.Broadcast);
		}
	}
	
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}
	
	public void shutdown() {
		
	}

}
