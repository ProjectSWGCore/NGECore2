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
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import main.NGECore;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class InstanceService implements INetworkDispatch {
	
	private NGECore core;
	
	private Map<String, List<Instance>> instanceMap = new HashMap<String, List<Instance>>();
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public class Instance {
		
		private long instanceId = 0;
		private String name = "";
		private long startTime = System.currentTimeMillis();
		private Point3D instancePosition;
		private Point3D spawnPosition;
		private int duration = 0;
		private int lockoutTime = 0;
		private CreatureObject owner;
		private List<CreatureObject> participants = new CopyOnWriteArrayList<CreatureObject>();
		private List<CreatureObject> activeParticipants = new CopyOnWriteArrayList<CreatureObject>();
		private List<SWGObject> objectList = new CopyOnWriteArrayList<SWGObject>();
		private ScheduledFuture<?> task;
		private boolean faulty = false;
		private boolean closed = false;
		
		public Instance(long instanceId, String name, Point3D instancePosition, Point3D spawnPosition, int duration, int lockoutTime, CreatureObject owner) {
			this.instanceId = instanceId;
			this.name = name;
			this.instancePosition = instancePosition;
			this.spawnPosition = spawnPosition;
			this.duration = (duration * 1000);
			this.lockoutTime = lockoutTime;
			this.owner = owner;
			participants.add(owner);
		}
		
		public Instance() {
			
		}
		
		public synchronized long getInstanceId() {
			return instanceId;
		}
		
		public synchronized String getName() {
			return name;
		}
		
		public synchronized long getStartTime() {
			return startTime;
		}
		
		public synchronized long getTimeRemaining() {
			return ((closed) ? 0 : (((long) duration) - (System.currentTimeMillis() - startTime)));
		}
		
		public synchronized boolean isOpen() {
			if (closed || getTimeRemaining() <= 0) {
				return false;
			}
			
			return true;
		}
		
		public synchronized Point3D getInstancePosition() {
			return instancePosition;
		}
		
		public synchronized Point3D getSpawnPosition() {
			return spawnPosition;
		}
		
		public synchronized int getDuration() {
			return duration;
		}
		
		public synchronized int getLockoutTime() {
			return lockoutTime;
		}
		
		public synchronized CreatureObject getOwner() {
			return owner;
		}
		
		public List<CreatureObject> getParticipants() {
			return participants;
		}
		
		public List<CreatureObject> getActiveParticipants() {
			return participants;
		}
		
		public List<SWGObject> getObjectList() {
			return objectList;
		}
		
		public synchronized boolean addParticipant(CreatureObject participant) {
			if (activeParticipants.contains(participant)) {
				return true;
			}
			
			if (activeParticipants.size() >= 8) {
				return false;
			}
			
			activeParticipants.add(participant);
			participants.add(participant);
			
			return true;
		}
		
		public synchronized boolean isActiveParticipant(CreatureObject participant) {
			return activeParticipants.contains(participant);
		}
		
		public synchronized boolean isFormerParticipant(CreatureObject participant) {
			return participants.contains(participant);
		}
		
		public synchronized boolean removeParticipant(CreatureObject participant) {
			return activeParticipants.remove(participant);
		}
		
		public synchronized ScheduledFuture<?> getTask() {
			return task;
		}
		
		public synchronized void setTask(ScheduledFuture<?> task) {
			this.task = task;
		}
		
		public synchronized boolean isFaulty() {
			return faulty;
		}
		
		public synchronized void setFaulty(boolean faulty) {
			this.faulty = faulty;
		}
		
		public synchronized boolean isClosed() {
			return closed;
		}
		
		public synchronized void close() {
			objectList.stream().forEach(core.objectService::destroyObject);
			objectList.clear();
			closed = true;
		}
		
	}
	
	public InstanceService(NGECore core) {
		this.core = core;
		
		Calendar c = Calendar.getInstance();
		Date now = new Date();
		
		c.setTime(now);
		
		if (c.get(Calendar.HOUR_OF_DAY) >= 6) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		c.set(Calendar.HOUR_OF_DAY, 6);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		long initialDelay = (c.getTimeInMillis() - now.getTime());
		
		final Map<String, List<Instance>> reference = instanceMap;
		
        scheduler.scheduleAtFixedRate(new Runnable() {
			
			private Map<String, List<Instance>> instanceMap = reference;
			
			public void run() {
				for (List<Instance> instanceList : instanceMap.values()) {
					for (Instance instance : instanceList) {
						if ((instance.isClosed() || !instance.isOpen())) {
							instanceList.remove(instance);
						}
					}
				}
			}
			
		}, initialDelay, 86400000, TimeUnit.MILLISECONDS);
	}
	
	/*
	 * @description Create a brand new instance.
	 * 
	 * @param instanceName Buildout name of the instance to build.
	 * @param owner Creator's creature object.
	 */
	public synchronized Instance create(String instanceName, CreatureObject owner) {
		Instance instance = null;
		
		try {
			String planetName = core.scriptService.callScript("scripts/instances/", instanceName, "getTerrain").asString();
			Planet planet = core.terrainService.getPlanetByPath(planetName);
			Point3D spawnPosition = new Point3D();
			int duration = (core.scriptService.callScript("scripts/instances/", instanceName, "getDuration").asInt() * 60);
			int lockoutTime = core.scriptService.callScript("scripts/instances/", instanceName, "getLockoutTime").asInt();;
			
			core.scriptService.callScript("scripts/instances/", instanceName, "getSpawnPosition", spawnPosition);
			
			DatatableVisitor buildoutTable = ClientFileManager.loadFile("datatables/buildout/areas_" + planet.getName() + ".iff", DatatableVisitor.class);
			
			for (int i = 0; i < buildoutTable.getRowCount(); i++) {
				if (((String) buildoutTable.getObject(i, 0)).equals(instanceName)) {
					Point3D instancePosition = new Point3D((Float) buildoutTable.getObject(i, 1), 0, (Float) buildoutTable.getObject(i, 2));
					
					if (!instanceInUse(instanceName, instancePosition)) {
						instance = new Instance(core.guildService.getGuildObject().getNextInstanceId(), instanceName, instancePosition, spawnPosition, duration, lockoutTime, owner);
					}
				}
			}
			
			if (instance == null) {
				return null;
			}
			
			core.scriptService.callScript("scripts/instances/", instanceName, "setup", core, instance);
			
			final Instance reference = instance;
			
			instance.setTask(scheduler.scheduleAtFixedRate(new Runnable() {
				
				private Instance instance = reference;
				private boolean sentCloseWarning = false;
				
				public void run() {
					if (instance.getDuration() > 300 && instance.getTimeRemaining() <= 300 && !sentCloseWarning) {
						instance.getActiveParticipants().stream().forEach(p -> p.sendSystemMessage("@instance:five_minute_warning", (byte) 0));
						sentCloseWarning = true;
					}
					
					if (!instance.isOpen()) {
						instance.getActiveParticipants().stream().forEach(p -> p.sendSystemMessage("@instance:instance_time_out", (byte) 0));
						close(instance);
					}
					
					core.scriptService.callScript("scripts/instances/", instance.getName(), "run", core, instance);
				}
				
			}, 1, 1, TimeUnit.MINUTES));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if (!instanceMap.containsKey(instance.getName())) {
			instanceMap.put(instance.getName(), new ArrayList<Instance>());
		}
		
		instanceMap.get(instance.getName()).add(instance);
		
		return instance;
	}
	
	
	/*
	 * @description Queue them for an instance for 19 seconds, remove if none available.
	 * 
	 * @param instanceName Name of the instance .py script.
	 * @param creature Player's creature object to queue for the instance.
	 * 
	 * @return None.
	 */
	public void queue(String instanceName, CreatureObject creature) {
		Instance instance = null;
		
		if (creature.getBuffByName("instance_launching") != null) {
			creature.sendSystemMessage("@instance:instance_request_pending", (byte) 0);
			return;
		}
		
		core.buffService.addBuffToCreature(creature, "instance_launching");
		
		if (isLockedOut(instanceName, creature)) {
			creature.sendSystemMessage("@instance:lockout_notification", (byte) 0);
			core.buffService.removeBuffFromCreature(creature, creature.getBuffByName("instance_launching"));
			return;
		}
		
		instance = getActiveInstance(instanceName, creature);
		
		if (creature.getGroupId() > 0) {
			if (instance == null) {
				GroupObject group = (GroupObject) core.objectService.getObject(creature.getGroupId());
				
				if (group != null) {
					for (SWGObject member : group.getMemberList()) {
						instance = getActiveInstance(instanceName, (CreatureObject) member);
						
						if (instance != null) {
							break;
						}
					}
				}
			} else {
				GroupObject group = (GroupObject) core.objectService.getObject(creature.getGroupId());
				
				if (group != null) {
					for (SWGObject member : group.getMemberList()) {
						Instance instance2 = getActiveInstance(instanceName, (CreatureObject) member);
						
						if (!instance2.equals(instance)) {
							creature.sendSystemMessage("@instance:fail_invalid_lockout", (byte) 0);
							core.buffService.removeBuffFromCreature(creature, creature.getBuffByName("instance_launching"));
							return;
						}
					}
				}
			}
		}
		
		if (instance == null) {
			instance = create(instanceName, creature);

			if (instance == null) {
				creature.sendSystemMessage("@instance:all_full", (byte) 0);
				core.buffService.removeBuffFromCreature(creature, creature.getBuffByName("instance_launching"));
				return;
			}
		}
		
		if (instance.getActiveParticipants().size() >= 8) {
			creature.sendSystemMessage("@instance:fail_instance_full", (byte) 0);
			core.buffService.removeBuffFromCreature(creature, creature.getBuffByName("instance_launching"));
			return;
		}
		
		if (instance != null) {
			add(instance, creature);
		}
		
		core.buffService.removeBuffFromCreature(creature, creature.getBuffByName("instance_launching"));
	}
	
	/*
	 * @description Adds a player to an instance. Can be used directly to avoid queue.
	 * 
	 * @param instance Instance object.
	 * @param creature Player's creature object to add to the instance.
	 * 
	 * @return None.
	 */
	public void add(Instance instance, CreatureObject creature) {
		try {
			String terrain = core.scriptService.callScript("scripts/instances/", instance.getName(), "getTerrain").asString();
			Planet planet = core.terrainService.getPlanetByPath(terrain);
			int cellId = core.scriptService.callScript("scripts/instances/", instance.getName(), "getCellId").asInt();
			
			if (cellId == -1) {
				Point3D position = new Point3D();
				position.x = instance.getInstancePosition().x + instance.getSpawnPosition().x;
				position.y = instance.getInstancePosition().y + instance.getSpawnPosition().y;
				position.z = instance.getInstancePosition().z + instance.getSpawnPosition().z;
				
				core.simulationService.transferToPlanet(creature, planet, position, creature.getOrientation(), null);
			} else {
				String buildingName = core.scriptService.callScript("scripts/instances/", instance.getName(), "getBuilding").asString();
				BuildingObject building = null;
				
				Point3D buildingPosition = instance.getInstancePosition().clone();
				
				DatatableVisitor buildoutTable = ClientFileManager.loadFile("datatables/buildout/" + planet.getName() + "/" + instance.getName() + ".iff", DatatableVisitor.class);
				
				for (int i = 0; i < buildoutTable.getRowCount(); i++) {
					if (((Integer) buildoutTable.getObject(i, 3)) == CRC.StringtoCRC(buildingName)) {
						buildingPosition.x += ((Float) buildoutTable.getObject(i, 5));
						buildingPosition.y += ((Float) buildoutTable.getObject(i, 6));
						buildingPosition.z += ((Float) buildoutTable.getObject(i, 7));
					}
				}
				
				for (SWGObject object : core.objectService.getObjectList().values()) {
					if (object instanceof BuildingObject &&
						object.getTemplate().equals(buildingName) &&
						object.getPosition().x == buildingPosition.x &&
						object.getPosition().y == buildingPosition.y &&
						object.getPosition().z == buildingPosition.z) {
						building = (BuildingObject) object;
						break;
					}
				}
				
				if (building == null) {
					throw new Exception();
				}
				
				core.simulationService.transferToPlanet(creature, planet, instance.getSpawnPosition(), creature.getOrientation(), building.getCellByCellNumber(cellId));
			}
			
			instance.addParticipant(creature);
			
			core.scriptService.callScript("scripts/instances/", instance.getName(), "add", core, instance, creature);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @description Removes a player from an instance. Can be used directly.
	 * 
	 * @param instance Instance object.
	 * @param creature Player's creature object to add to the instance.
	 * 
	 * @return None.
	 */
	public void remove(Instance instance, CreatureObject creature) {
		try {
			if (creature.getBuffByName("instance_exiting") != null) {
				creature.sendSystemMessage("@instance:instance_request_pending", (byte) 0);
				return;
			}
			
			core.buffService.addBuffToCreature(creature, "instance_exiting");
			
			String exitTerrain = core.scriptService.callScript("scripts/instances/", instance.getName(), "getExitTerrain").asString();
			Point3D exitPosition = new Point3D();
			Planet exitPlanet = core.terrainService.getPlanetByPath(exitTerrain);
			
			core.scriptService.callScript("scripts/instances/", instance.getName(), "getExitPosition", exitPosition);
			
			core.scriptService.callScript("scripts/instances/", instance.getName(), "remove", core, instance, creature);
			
			instance.removeParticipant(creature);
			
			core.simulationService.transferToPlanet(creature, exitPlanet, exitPosition, creature.getOrientation(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		core.buffService.removeBuffFromCreature(creature, creature.getBuffByName("instance_exiting"));
	}
	
	/*
	 * @description Closes an instance.
	 * 
	 * @param instance Instance object.
	 * 
	 * @return None.
	 */
	public synchronized void close(Instance instance) {
		try {
			if (instance.isClosed()) {
				return;
			}
			
			String exitTerrain = core.scriptService.callScript("scripts/instances/", instance.getName(), "getExitTerrain").asString();
			Point3D exitPosition = new Point3D();
			Planet exitPlanet = core.terrainService.getPlanetByPath(exitTerrain);
			
			core.scriptService.callScript("scripts/instances/", instance.getName(), "getExitPosition", exitPosition);
			
			for (CreatureObject participant : instance.getActiveParticipants()) {
				instance.removeParticipant(participant);
				core.simulationService.transferToPlanet(participant, exitPlanet, exitPosition, participant.getOrientation(), null);
			}
			
			if (!instance.getTask().isCancelled()) {
				instance.getTask().cancel(true);
			}
			
			core.scriptService.callScript("scripts/instances/", instance.getName(), "destroy", core, instance);
			
			instance.close();
		} catch (Exception e) {
			e.printStackTrace();
			instance.setFaulty(true);
		}
	}
	
	/*
	 * @description Checks if an instance area is in use.
	 * 
	 * @param instanceName Buildout name of the instance.
	 * @param instancePosition Position of the instance in the game world.
	 * 
	 * @return True if instance is in use.
	 */
	public boolean instanceInUse(String instanceName, Point3D instancePosition) {
		if (instanceMap.containsKey(instanceName)) {
			for (Instance instance : instanceMap.get(instanceName)) {
				if (instance.isFaulty() || (instance.isOpen() &&
					instance.getInstancePosition().x == instancePosition.x &&
					instance.getInstancePosition().z == instancePosition.z)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * @description Gets an instance object from an instanceId.
	 * 
	 * @param instanceId Id of an instance you want an object for.
	 * 
	 * @return Instance object, or null if none found.
	 */
	public Instance getInstance(long instanceId) {
		for (List<Instance> instanceList : instanceMap.values()) {
			for (Instance instance : instanceList) {
				if (instance.getInstanceId() == instanceId) {
					return instance;
				}
			}
		}
		
		return null;
	}
	
	/*
	 * @description Checks if player is in an instance.
	 * 
	 * @param creature Player's creature object.
	 * 
	 * @return True if player is in an instance.
	 */
	public boolean isInInstance(CreatureObject creature) {
		for (List<Instance> instanceList : instanceMap.values()) {
			for (Instance instance : instanceList) {
				if (instance.isActiveParticipant(creature)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * @description Checks if player is in a specific instance.
	 * 
	 * @param instanceName Buildout name of the instance.
	 * @param creature Player's creature object.
	 * 
	 * @return True if player is in the specified instance.
	 */
	public boolean isInInstance(String instanceName, CreatureObject creature) {
		if (instanceMap.containsKey(instanceName)) {
			for (Instance instance : instanceMap.get(instanceName)) {
				if (instance.isActiveParticipant(creature)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * @description Checks if player is in a specific instance.
	 * 
	 * @param instance Instance object.
	 * @param creature Player's creature object.
	 * 
	 * @return True if player is in the specified instance.
	 */
	public boolean isInInstance(Instance instance, CreatureObject creature) {
		return instance.getActiveParticipants().contains(creature);
	}
	
	/*
	 * @description Finds the instance object a player is currently in.
	 * 
	 * @param creature Player's creature object.
	 * 
	 * @return Instance object of the player, or null if they aren't in one.
	 */
	public Instance getActiveInstance(CreatureObject creature) {
		for (List<Instance> instanceList : instanceMap.values()) {
			for (Instance instance : instanceList) {
				if (instance.isActiveParticipant(creature)) {
					return instance;
				}
			}
		}
		
		return null;
	}
	
	/*
	 * @description Finds the instance object of an instance a player is/has been in.
	 * 
	 * @param instanceName Buildout name of the instance.
	 * @param creature Player's creature object.
	 * 
	 * @return Instance object of the specified instance the specified player has entered since reset, or null if not found.
	 */
	public Instance getActiveInstance(String instanceName, CreatureObject creature) {
		if (instanceMap.containsKey(instanceName)) {
			for (Instance instance : instanceMap.get(instanceName)) {
				if (instance.isFormerParticipant(creature) && instance.getTimeRemaining() > 0) {
					return instance;
				}
			}
		}
		
		return null;
	}
	
	/*
	 * @description Checks if player has any instance lockouts.
	 * 
	 * @param creature Player's creature object.
	 * 
	 * @return True if player is locked out from any instances.
	 */
	public boolean hasInstanceLockout(CreatureObject creature) {
		for (List<Instance> instanceList : instanceMap.values()) {
			for (Instance instance : instanceList) {
				if (instance.isFormerParticipant(creature)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * @description Finds the instance object of an instance a player is/has been in.
	 * 
	 * @param instanceName Buildout name of the instance.
	 * @param creature Player's creature object.
	 * 
	 * @return Instance object of the specified instance the specified player has entered since reset, or null if not found.
	 */
	public List<Instance> getLockoutList(CreatureObject creature) {
		List<Instance> lockoutList = new ArrayList<Instance>();
		
		for (List<Instance> instanceList : instanceMap.values()) {
			instanceList.stream().filter(i -> i.isFormerParticipant(creature)).forEach(i -> lockoutList.add(i));
		}
		
		return lockoutList;
	}
	
	/*
	 * @description Checks if player is locked out of an instance.
	 * 
	 * @param instanceName Buildout name of the instance.
	 * @param creature Player's creature object.
	 * 
	 * @return True if player is locked out from the specified instance.
	 */
	public boolean isLockedOut(String instanceName, CreatureObject creature) {
		if (instanceMap.containsKey(instanceName)) {
			for (Instance instance : instanceMap.get(instanceName)) {
				if (instance.isFormerParticipant(creature) && instance.getTimeRemaining() <= 0) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * @description Shows the instance UI.
	 * 
	 * @param creature Player's creature object.
	 * 
	 * @return None.
	 */
	public void showInstances(CreatureObject creature) {
		// Stub for the /showi UI
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes,
			Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
