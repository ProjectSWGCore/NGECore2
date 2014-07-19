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

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import main.NGECore;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.sleepycat.persist.EntityCursor;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.MeshVisitor;
import engine.clientdata.visitors.PortalVisitor;
import engine.clientdata.visitors.PortalVisitor.Cell;
import engine.clients.Client;
import engine.resources.common.Event;
import engine.resources.common.Mesh3DTriangle;
import engine.resources.common.RGB;
import engine.resources.common.Ray;
import engine.resources.container.Traverser;
import engine.resources.database.ODBCursor;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.scene.quadtree.QuadTree;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import protocol.swg.CmdStartScene;
import protocol.swg.HeartBeatMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.OpenedContainerMessage;
import protocol.swg.UpdateTransformMessage;
import protocol.swg.UpdateTransformWithParentMessage;
import protocol.swg.chat.ChatFriendsListUpdate;
import protocol.swg.chat.ChatOnChangeFriendStatus;
import protocol.swg.chat.ChatOnGetFriendsList;
import protocol.swg.objectControllerObjects.DataTransform;
import protocol.swg.objectControllerObjects.DataTransformWithParent;
import protocol.swg.objectControllerObjects.TargetUpdate;
import resources.objects.building.BuildingObject;
import resources.objects.cell.CellObject;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.harvester.HarvesterObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.common.*;
import resources.common.collidables.AbstractCollidable;
import resources.datatables.DisplayType;
import resources.datatables.Locomotion;
import resources.datatables.Options;
import resources.datatables.PlayerFlags;
import resources.datatables.Posture;
import services.ai.LairActor;
import services.chat.ChatRoom;
import toxi.geom.Line3D;
import toxi.geom.Ray3D;
import toxi.geom.Vec3D;
import toxi.geom.mesh.TriangleMesh;
import wblut.geom.WB_AABB;
import wblut.geom.WB_AABBNode;
import wblut.geom.WB_AABBTree;
import wblut.geom.WB_Distance;
import wblut.geom.WB_Intersection;
import wblut.geom.WB_Point3d;
import wblut.geom.WB_Ray;
import wblut.geom.WB_Transform;
import wblut.geom.WB_Vector3d;
import wblut.hemesh.HE_Mesh;
import wblut.hemesh.HE_Vertex;
import wblut.math.WB_Epsilon;
import wblut.math.WB_M44;

@SuppressWarnings("unused")

public class SimulationService implements INetworkDispatch {
	
	Map<String, QuadTree<SWGObject>> quadTrees;
	Map<String, QuadTree<AbstractCollidable>> collidableQuadTrees;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);	
	private NGECore core;
	private Map<String, MeshVisitor> cellMeshes = new ConcurrentHashMap<String, MeshVisitor>(); 
	
	public SimulationService(NGECore core) {
		this.core = core;
		TerrainService terrainService = core.terrainService;
		quadTrees = new ConcurrentHashMap<String, QuadTree<SWGObject>>();
		collidableQuadTrees = new ConcurrentHashMap<String, QuadTree<AbstractCollidable>>();
		
		for (int i = 0; i < core.terrainService.getPlanetList().size(); i++) {
			quadTrees.put(terrainService.getPlanetList().get(i).getName(), new QuadTree<SWGObject>(-8192, -8192, 8192, 8192));
			collidableQuadTrees.put(terrainService.getPlanetList().get(i).getName(), new QuadTree<AbstractCollidable>(-8192, -8192, 8192, 8192));
		}
		
		core.commandService.registerCommand("opencontainer");
		core.commandService.registerCommand("transferitem");
		core.commandService.registerCommand("transferitemarmor");
		core.commandService.registerCommand("transferitemweapon");
		core.commandService.registerCommand("transferitemmisc");
		core.commandService.registerCommand("equip");
		core.commandService.registerCommand("prone");
		core.commandService.registerCommand("stand");
		core.commandService.registerCommand("sitserver");
		core.commandService.registerCommand("kneel");
		core.commandService.registerCommand("serverdestroyobject");
		core.commandService.registerGmCommand("giveitem");
		core.commandService.registerGmCommand("object");
		core.commandService.registerCommand("getattributesbatch");
		core.commandService.registerCommand("pvp");
		core.commandService.registerCommand("setcurrentskilltitle");
		core.commandService.registerCommand("tip");
		core.commandService.registerCommand("faction");
		core.commandService.registerGmCommand("setspeed");
		core.commandService.registerCommand("waypoint");
		core.commandService.registerCommand("setwaypointactivestatus");
		core.commandService.registerCommand("setwaypointname");
		core.commandService.registerCommand("getfriendlist");
		core.commandService.registerCommand("deathblow");
		core.commandService.registerCommand("endduel");
		core.commandService.registerCommand("duel");
		core.commandService.registerCommand("purchaseticket");
		core.commandService.registerCommand("boardshuttle");
		core.commandService.registerCommand("getplayerid");
		core.commandService.registerCommand("inspire");
		core.commandService.registerGmCommand("setgodmode");
		core.commandService.registerCommand("requestwaypointatposition");
		core.commandService.registerCommand("meditate");
		core.commandService.registerGmCommand("server");
		core.commandService.registerCommand("toggleawayfromkeyboard");
		core.commandService.registerCommand("lfg");
		core.commandService.registerCommand("newbiehelper");
		core.commandService.registerCommand("roleplay");
		core.commandService.registerAlias("afk", "toggleawayfromkeyboard");
		core.commandService.registerCommand("toggledisplayingfactionrank");
		core.commandService.registerCommand("editbiography");
		core.commandService.registerCommand("setbiography");
		core.commandService.registerCommand("requestbiography");
		core.commandService.registerCommand("eject");
		core.commandService.registerGmCommand("broadcast");
		core.commandService.registerGmCommand("teleporttarget");
		core.commandService.registerGmCommand("getplayerid");
		core.commandService.registerCommand("npcconversationselect");
		core.commandService.registerCommand("npcconversationstop");

	}
	
	public void insertSnapShotObjects() {
		List<SWGObject> objectList = new ArrayList<SWGObject>(core.objectService.getObjectList().values());
		for(SWGObject obj : objectList) {
			if(obj.getParentId() == 0 && /*(*/obj.isInSnapshot() /*|| obj.getAttachment("isBuildout") != null)*/)
				add(obj, obj.getPosition().x, obj.getPosition().z);
		}
	}
	
	public void insertPersistentBuildings() {
		ODBCursor cursor = core.getSWGObjectODB().getCursor();
		
		while(cursor.hasNext()) {
			Object next = cursor.next();
			if (next == null) continue;
			SWGObject building = core.objectService.getObject(((SWGObject) next).getObjectID());
			if(building == null || (!(building instanceof BuildingObject) && !(building instanceof HarvesterObject)))
				continue;
			if(building.getAttachment("hasLoadedServerTemplate") == null)
				core.objectService.loadServerTemplate(building);
			add(building, building.getPosition().x, building.getPosition().z);
		}
		cursor.close();
	}

	
	public void addCollidable(AbstractCollidable collidable, float x, float y) {
		collidableQuadTrees.get(collidable.getPlanet().getName()).put(x, y, collidable);
	}
	
	public void removeCollidable(AbstractCollidable collidable, float x, float y) {
		collidableQuadTrees.get(collidable.getPlanet().getName()).remove(x, y, collidable);
	}
	
	public List<AbstractCollidable> getCollidables(Planet planet, float x, float y, float range) {
		return collidableQuadTrees.get(planet.getName()).get(x, y, range);
	}
	
	public boolean add(SWGObject object, float x, float z) {
		return add(object, x, z, false);
	}
		
	public boolean add(SWGObject object, float x, float z, boolean notifyObservers) {
		object.setIsInQuadtree(true);
		boolean success = quadTrees.get(object.getPlanet().getName()).put(x, z, object);
		if(success) {
			@SuppressWarnings("unchecked") Vector<SWGObject> childObjects = (Vector<SWGObject>) object.getAttachment("childObjects");
			if(childObjects != null) {
				addChildObjects(object, childObjects);
			}
			
			if(notifyObservers) {
				Point3D pos = new Point3D(x, 0, z);
				Collection<SWGObject> newAwareObjects = get(object.getPlanet(), x, z, 512);
				for(Iterator<SWGObject> it = newAwareObjects.iterator(); it.hasNext();) {
					SWGObject obj = it.next();
					if((obj.getAttachment("bigSpawnRange") == null && obj.getWorldPosition().getDistance2D(pos) > 200) || obj == object)
						continue;
					//if(!obj.isInSnapshot())
					//	System.out.println(obj.getTemplate());
					if(object.getClient() != null)
						object.makeAware(obj);
					if(obj.getClient() != null)
						obj.makeAware(object);
				}
			}
		}
		return success;
	}
	
	public void addChildObjects(SWGObject object, Vector<SWGObject> childObjects) {
		
		for(SWGObject childObject : childObjects) {
			if(childObject.getAttachment("cellNumber") == null)
				add(childObject, childObject.getWorldPosition().x, childObject.getWorldPosition().z, true);
			else {
				BuildingObject building = (BuildingObject) object;
				CellObject cell = building.getCellByCellNumber((Integer) childObject.getAttachment("cellNumber"));
				if(cell == null)
					continue;
				cell.add(childObject);
			}
				
		}
		
	}
	
	public boolean checkForObject(int distance, SWGObject value ){
		AtomicBoolean checkObject = new AtomicBoolean();
		core.simulationService.get(value.getPlanet(), value.getWorldPosition().x, value.getWorldPosition().z, distance).stream().forEach((objecta) -> { 
			if (objecta instanceof BuildingObject)
				checkObject.set(true);
			}
		);
		return checkObject.get();
	}
	
	public Vector<CreatureObject> getAllNearNPCs(int distance, SWGObject value ){
		Vector<CreatureObject> foundCreatures = new Vector<CreatureObject>();
		core.simulationService.get(value.getPlanet(), value.getWorldPosition().x, value.getWorldPosition().z, distance).stream().forEach((objecta) -> { 
			if (objecta instanceof CreatureObject)
				foundCreatures.add((CreatureObject)objecta);
			}
		);
		return foundCreatures;
	}
		
	public boolean move(SWGObject object, int oldX, int oldZ, int newX, int newZ) {
		if(quadTrees.get(object.getPlanet().getName()).remove(oldX, oldZ, object)) {
			return quadTrees.get(object.getPlanet().getName()).put(newX, newZ, object);
		}
			
		return false;
	}
	
	public boolean move(SWGObject object, float oldX, float oldZ, float newX, float newZ) {
		long startTime = System.nanoTime();
		if(quadTrees.get(object.getPlanet().getName()).remove(oldX, oldZ, object)) {
			boolean success = quadTrees.get(object.getPlanet().getName()).put(newX, newZ, object);
			return success;
		}
		// Note: This sysout keeps getting spammed, so the quadtree remove fails for some reason
		// The NPC does move though
		//System.out.println("Move failed.");
		return false;
	}
		
	public List<SWGObject> get(Planet planet, float x, float z, int range) {
		List<SWGObject> list = quadTrees.get(planet.getName()).get((int)x, (int)z, range);
		return list;
	}
	
	public boolean remove(SWGObject object, float x, float z) {
		return remove(object, x, z, false);
	}
		
	public boolean remove(SWGObject object, float x, float z, boolean notifyObservers) {
		if (object == null || !object.isInQuadtree()) {
			return false;
		}
		
		boolean success = quadTrees.get(object.getPlanet().getName()).remove(x, z, object);
		object.setIsInQuadtree(success);
		if(success && notifyObservers) {
			HashSet<Client> oldObservers = new HashSet<Client>(object.getObservers());
			for(Iterator<Client> it = oldObservers.iterator(); it.hasNext();) {
				Client observerClient = it.next();
				if(observerClient.getParent() != null) {
					observerClient.getParent().makeUnaware(object);
				}
			}
		}
		return success;
	}

	@Override
	public void insertOpcodes(Map<Integer,INetworkRemoteEvent> swgOpcodes, Map<Integer,INetworkRemoteEvent> objControllerOpcodes) {

		objControllerOpcodes.put(ObjControllerOpcodes.DATA_TRANSFORM, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				DataTransform dataTransform = new DataTransform();
				dataTransform.deserialize(data);
				//System.out.println("Movement Counter: " + dataTransform.getMovementCounter());
				Client client = core.getClient(session);

				if(client == null) {
					System.out.println("NULL Client");
					return;
				}
				
				if(client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				
				CreatureObject creature = (CreatureObject) client.getParent();
				
				CreatureObject object = creature;
				
				if (core.mountService.isMounted(creature) && creature.getObjectID() == ((CreatureObject) creature.getContainer()).getOwnerId()) {
					object = (CreatureObject) object.getContainer();
				}
				
				Point3D newPos;
				Point3D oldPos;
				
				synchronized(object.getMutex()) {
					newPos = new Point3D(dataTransform.getXPosition(), dataTransform.getYPosition(), dataTransform.getZPosition());
					if(Float.isNaN(newPos.x) || Float.isNaN(newPos.y) || Float.isNaN(newPos.z)) 
						return;
					oldPos = object.getPosition();
				}
				
				if (object instanceof CreatureObject && object.getOption(Options.MOUNT)) {
					if (!checkLineOfSight(object, newPos)) {
						newPos = oldPos;
					}
				}
				
				synchronized(object.getMutex()) {
					//Collection<Client> oldObservers = object.getObservers();
					//Collection<Client> newObservers = new HashSet<Client>();
					if(object.getContainer() == null)
						move(object, oldPos.x, oldPos.z, newPos.x, newPos.z);
					Quaternion newOrientation = new Quaternion(dataTransform.getWOrientation(), dataTransform.getXOrientation(), dataTransform.getYOrientation(), dataTransform.getZOrientation());
					object.setPosition(newPos);
					creature.setPosition(newPos);
					object.setOrientation(newOrientation);
					creature.setOrientation(newOrientation);
					object.setMovementCounter(dataTransform.getMovementCounter());
					creature.setMovementCounter(dataTransform.getMovementCounter());
				}
				
				synchronized(creature.getMutex()) {
					if (dataTransform.getSpeed() > 0.0f) {
						switch (creature.getLocomotion()) {
							case Locomotion.Prone:
								creature.setLocomotion(Locomotion.Crawling);
								break;
							case Locomotion.ClimbingStationary:
								creature.setLocomotion(Locomotion.Climbing);
								break;
							case Locomotion.Standing:
							case Locomotion.Running:
							case Locomotion.Walking:
								if (dataTransform.getSpeed() >= (creature.getRunSpeed() * (creature.getSpeedMultiplierBase() + creature.getSpeedMultiplierMod()))) {
									creature.setLocomotion(Locomotion.Running);
								} else {
									creature.setLocomotion(Locomotion.Walking);
								}
								
								break;
							case Locomotion.Sneaking:
							case Locomotion.CrouchSneaking:
							case Locomotion.CrouchWalking:
								if (dataTransform.getSpeed() >= (creature.getRunSpeed() * (creature.getSpeedMultiplierBase() + creature.getSpeedMultiplierMod()))) {
									creature.setLocomotion(Locomotion.CrouchSneaking);
								} else {
									creature.setLocomotion(Locomotion.CrouchWalking);
								}
								
								break;
						}
					} else {
						switch (creature.getLocomotion()) {
							case Locomotion.Crawling:
								creature.setLocomotion(Locomotion.Prone);
								break;
							case Locomotion.Climbing:
								creature.setLocomotion(Locomotion.ClimbingStationary);
								break;
							case Locomotion.Running:
							case Locomotion.Walking:
								creature.setLocomotion(Locomotion.Standing);
								break;
							case Locomotion.CrouchSneaking:
							case Locomotion.CrouchWalking:
								creature.setLocomotion(Locomotion.Sneaking);
								break;
						}
					}
				}
				
				if(object.getContainer() != null) {
					object.getContainer()._remove(object);
					add(object, newPos.x, newPos.z);
				}
				
				//object.setParentId(0);
				//object.setParent(null);
			//	System.out.println("Parsed Height: " + core.terrainService.getHeight(object.getPlanetId(), dataTransform.getXPosition(), dataTransform.getZPosition())
				//		 + " should be: " + dataTransform.getYPosition());
				UpdateTransformMessage utm = new UpdateTransformMessage(object.getObjectID(), dataTransform.getTransformedX(), dataTransform.getTransformedY(), dataTransform.getTransformedZ(), dataTransform.getMovementCounter(), (byte) dataTransform.getMovementAngle(), dataTransform.getSpeed());
				object.notifyObservers(utm, false);
				
				List<SWGObject> newAwareObjects = get(creature.getPlanet(), newPos.x, newPos.z, 512);
				ArrayList<SWGObject> oldAwareObjects = new ArrayList<SWGObject>(creature.getAwareObjects());
				@SuppressWarnings("unchecked") Collection<SWGObject> updateAwareObjects = CollectionUtils.intersection(oldAwareObjects, newAwareObjects);
				
				for(int i = 0; i < oldAwareObjects.size(); i++) {
					SWGObject obj = oldAwareObjects.get(i);
					if(!updateAwareObjects.contains(obj) && obj != creature && obj.getWorldPosition().getDistance2D(newPos) > 200 && obj.isInQuadtree() /*&& obj.getParentId() == 0*/) {
						if(obj.getAttachment("bigSpawnRange") != null && obj.getWorldPosition().getDistance2D(newPos) < 512)
							continue;
						creature.makeUnaware(obj);
						if(obj.getClient() != null)
							obj.makeUnaware(creature);
					} else if(obj != creature && obj.getWorldPosition().getDistance2D(newPos) > 200 && obj.isInQuadtree() && obj.getAttachment("bigSpawnRange") == null) {
						creature.makeUnaware(obj);
						if(obj.getClient() != null)
							obj.makeUnaware(creature);
					}
				}
				
				for(int i = 0; i < newAwareObjects.size(); i++) {
					SWGObject obj = newAwareObjects.get(i);
					//System.out.println(obj.getTemplate());
					if(!updateAwareObjects.contains(obj) && obj != creature && !creature.getAwareObjects().contains(obj) &&  obj.getContainer() != creature && obj.isInQuadtree()) {						
						if(obj.getAttachment("bigSpawnRange") == null && obj.getWorldPosition().getDistance2D(newPos) > 200)
							continue;						
						creature.makeAware(obj);
						if(obj.getClient() != null)
							obj.makeAware(creature);
					}
				}
				
				checkForCollidables(object);
				object.setAttachment("lastValidPosition", object.getPosition());
				MoveEvent event = new MoveEvent();
				event.object = object;
				object.getEventBus().publish(event);
				
			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.DATA_TRANSFORM_WITH_PARENT, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				DataTransformWithParent dataTransform = new DataTransformWithParent();
				dataTransform.deserialize(data);
				Client client = core.getClient(session);

				if(core.objectService.getObject(dataTransform.getCellId()) == null)
					return;

				SWGObject parent = core.objectService.getObject(dataTransform.getCellId());
				
				if(client == null) {
					System.out.println("NULL Client");
					return;
				}
				
				if(client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				
				CreatureObject object = (CreatureObject) client.getParent();
				
				if (core.mountService.isMounted(object)) {
					object.sendSystemMessage(OutOfBand.ProsePackage("@pet_menu:cant_mount"), DisplayType.Broadcast);
					core.mountService.dismount(object, (CreatureObject) object.getContainer());
				}
				
				Point3D newPos = new Point3D(dataTransform.getXPosition(), dataTransform.getYPosition(), dataTransform.getZPosition());
				newPos.setCell((CellObject) parent);
				if(Float.isNaN(newPos.x) || Float.isNaN(newPos.y) || Float.isNaN(newPos.z))
					return;
				Point3D oldPos = object.getPosition();
				Quaternion newOrientation = new Quaternion(dataTransform.getWOrientation(), dataTransform.getXOrientation(), dataTransform.getYOrientation(), dataTransform.getZOrientation());
				
				UpdateTransformWithParentMessage utm = new UpdateTransformWithParentMessage(object.getObjectID(), dataTransform.getCellId(), dataTransform.getTransformedX(), dataTransform.getTransformedY(), dataTransform.getTransformedZ(), dataTransform.getMovementCounter(), (byte) dataTransform.getMovementAngle(), dataTransform.getSpeed());
				
				if(object.getContainer() != parent) {
					remove(object, oldPos.x, oldPos.z);
					if(object.getContainer() != null)
						object.getContainer()._remove(object);
					if (object.getClient() == null)
						System.err.println("Client is null!  This is a very strange error.");
					//if (object.getClient() != null && object.getClient().isGM() && parent != null && parent instanceof CellObject && parent.getContainer() != null)
						//object.sendSystemMessage("BuildingId - Dec: " + parent.getContainer().getObjectID() + " Hex: " + Long.toHexString(parent.getContainer().getObjectID()) + " CellNumber: " + ((CellObject) parent).getCellNumber(), DisplayType.Broadcast);
					parent._add(object);
				}
				object.setPosition(newPos);
				object.setOrientation(newOrientation);
				object.setMovementCounter(dataTransform.getMovementCounter());
				object.notifyObservers(utm, false);
				
				checkForCollidables(object);
				object.setAttachment("lastValidPosition", object.getPosition());
				
				synchronized(object.getMutex()) {
					if (dataTransform.getSpeed() > 0.0f) {
						switch (object.getLocomotion()) {
							case Locomotion.Prone:
								object.setLocomotion(Locomotion.Crawling);
								break;
							case Locomotion.ClimbingStationary:
								object.setLocomotion(Locomotion.Climbing);
								break;
							case Locomotion.Standing:
							case Locomotion.Running:
							case Locomotion.Walking:
								if (dataTransform.getSpeed() >= (object.getRunSpeed() * (object.getSpeedMultiplierBase() + object.getSpeedMultiplierMod()))) {
									object.setLocomotion(Locomotion.Running);
								} else {
									object.setLocomotion(Locomotion.Walking);
								}
								
								break;
							case Locomotion.Sneaking:
							case Locomotion.CrouchSneaking:
							case Locomotion.CrouchWalking:
								if (dataTransform.getSpeed() >= (object.getRunSpeed() * (object.getSpeedMultiplierBase() + object.getSpeedMultiplierMod()))) {
									object.setLocomotion(Locomotion.CrouchSneaking);
								} else {
									object.setLocomotion(Locomotion.CrouchWalking);
								}
								
								break;
						}
					} else {
						switch (object.getLocomotion()) {
							case Locomotion.Crawling:
								object.setLocomotion(Locomotion.Prone);
								break;
							case Locomotion.Climbing:
								object.setLocomotion(Locomotion.ClimbingStationary);
								break;
							case Locomotion.Running:
							case Locomotion.Walking:
								object.setLocomotion(Locomotion.Standing);
								break;
							case Locomotion.CrouchSneaking:
							case Locomotion.CrouchWalking:
								object.setLocomotion(Locomotion.Sneaking);
								break;
						}
					}
				}
			}
			
		});
		
		swgOpcodes.put(Opcodes.ClientOpenContainerMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				System.out.println("Open Container Request");
			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.lookAtTarget, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {

				data.order(ByteOrder.LITTLE_ENDIAN);

				Client client = core.getClient(session);

				if(client == null) {
					System.out.println("NULL Client");
					return;
				}

				if(client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				CreatureObject object = (CreatureObject) client.getParent();

				TargetUpdate targetUpdate = new TargetUpdate();
				targetUpdate.deserialize(data);

				object.setLookAtTarget(targetUpdate.getTargetId());

			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.intendedTarget, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {

				data.order(ByteOrder.LITTLE_ENDIAN);

				Client client = core.getClient(session);

				if(client == null) {
					System.out.println("NULL Client");
					return;
				}

				if(client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				CreatureObject object = (CreatureObject) client.getParent();

				TargetUpdate targetUpdate = new TargetUpdate();
				targetUpdate.deserialize(data);

				object.setIntendedTarget(targetUpdate.getTargetId());

			}

		});
		
	}
	
	public void moveObject(SWGObject object, Point3D newPosition, Quaternion newOrientation, int movementCounter, float speed, CellObject cell) {
		
		if(Float.isNaN(newPosition.x) || Float.isNaN(newPosition.y) || Float.isNaN(newPosition.z))
			return;

		if(cell == null) {
			
			Point3D oldPos;
			synchronized(object.getMutex()) {
				oldPos = object.getPosition();
				if(object.getContainer() == null)
					move(object, oldPos.x, oldPos.z, newPosition.x, newPosition.z);
				object.setPosition(newPosition);
				object.setOrientation(newOrientation);
				object.setMovementCounter(movementCounter + 1);
			}
			if(object.getContainer() != null && newPosition != oldPos) {
				object.getContainer()._remove(object);
				add(object, newPosition.x, newPosition.z);
			} 
			
			UpdateTransformMessage utm = new UpdateTransformMessage(object.getObjectID(), (short) (newPosition.x * 4 + 0.5), (short) (newPosition.y * 4 + 0.5), (short) (newPosition.z * 4 + 0.5), movementCounter + 1, getSpecialDirection(newOrientation), speed);

			List<SWGObject> newAwareObjects = get(object.getPlanet(), newPosition.x, newPosition.z, 512);
			ArrayList<SWGObject> oldAwareObjects = new ArrayList<SWGObject>(object.getAwareObjects());
			@SuppressWarnings("unchecked") Collection<SWGObject> updateAwareObjects = CollectionUtils.intersection(oldAwareObjects, newAwareObjects);
			object.notifyObservers(utm, false);

			for(int i = 0; i < oldAwareObjects.size(); i++) {
				SWGObject obj = oldAwareObjects.get(i);
				if(!updateAwareObjects.contains(obj) && obj != object && obj.getWorldPosition().getDistance2D(newPosition) > 200 && obj.isInQuadtree() /*&& obj.getParentId() == 0*/) {
					if(obj.getAttachment("bigSpawnRange") != null && obj.getWorldPosition().getDistance2D(newPosition) < 512)
						continue;
					object.makeUnaware(obj);
					if(obj.getClient() != null)
						obj.makeUnaware(object);
				} else if(obj != object && obj.getWorldPosition().getDistance2D(newPosition) > 200 && obj.isInQuadtree() && obj.getAttachment("bigSpawnRange") == null) {
					object.makeUnaware(obj);
					if(obj.getClient() != null)
						obj.makeUnaware(object);
				}
			}
			for(int i = 0; i < newAwareObjects.size(); i++) {
				SWGObject obj = newAwareObjects.get(i);
				//System.out.println(obj.getTemplate());
				if(!updateAwareObjects.contains(obj) && obj != object && !object.getAwareObjects().contains(obj) &&  obj.getContainer() != object && obj.isInQuadtree()) {						
					if(obj.getAttachment("bigSpawnRange") == null && obj.getWorldPosition().getDistance2D(newPosition) > 200)
						continue;						
					object.makeAware(obj);
					if(obj.getClient() != null)
						obj.makeAware(object);
				}
			}
			
			checkForCollidables(object);
			MoveEvent event = new MoveEvent();
			event.object = object;
			object.getEventBus().publish(event);

			
		} else {
			
			newPosition.setCell(cell);
			Point3D oldPos = object.getPosition();
			object.setPosition(newPosition);
			object.setOrientation(newOrientation);
			object.setMovementCounter(movementCounter + 1);

			UpdateTransformWithParentMessage utm = new UpdateTransformWithParentMessage(object.getObjectID(), cell.getObjectID(), (short) (newPosition.x * 8 + 0.5), (short) (newPosition.y * 8 + 0.5), (short) (newPosition.z * 8 + 0.5), movementCounter + 1, getSpecialDirection(newOrientation), speed);
			
			if(object.getContainer() != cell) {
				remove(object, oldPos.x, oldPos.z);
				if(object.getContainer() != null)
					object.getContainer()._remove(object);
				cell._add(object);
			}
			object.notifyObservers(utm, false);
			
			checkForCollidables(object);

		}
		
	}
	
	public byte getSpecialDirection(Quaternion orientation) {
		byte movementAngle = (byte) 0.0f;
		float wOrient = orientation.w;
		float yOrient = orientation.y;
		float sq = (float) Math.sqrt(1- (orientation.w * orientation.w));
		
		if (sq != 0) {
			if (orientation.w > 0 && orientation.y < 0) {
				wOrient *= -1;
				yOrient *= -1;
			}
			movementAngle = (byte) ((yOrient / sq) * (2 * Math.acos(wOrient) / 0.06283f));
		}
		
		return movementAngle;
	}
	
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	/*public WB_AABBTree getAABBTree(SWGObject object, int collisionBlockFlag) {
		
		if(object.getMeshVisitor() == null || object.getTemplateData() == null) {
			System.out.println("NULL Mesh Visitor for: " + object.getTemplate());
			return null;
		}
		
		if(object.getTemplateData().getAttribute("collisionActionBlockFlags") != null) {
			int bit = (Integer) object.getTemplateData().getAttribute("collisionActionBlockFlags") & collisionBlockFlag;
			//if(bit == (Integer) object.getTemplateData().getAttribute("collisionActionBlockFlags"))
			//	return null;
		}
		
		Point3D position = object.getPosition();
		HE_Mesh mesh = object.getMeshVisitor().createMesh();
				
		if(mesh == null)
			return null;

		float angle = (float) (object.getRadians() * (180 / Math.PI));
		System.out.println("Angle: " + angle);

		Quaternion quat = object.getOrientation();
		
		//WB_Transform transform = new WB_Transform();
		//transform.addRotateZ(object.getRadians());
		
		//mesh = mesh.transform(transform);
	
		//mesh = mesh.move(position.x, position.z, position.y);

		
		WB_AABBTree aabbTree = new WB_AABBTree(mesh, mesh.numberOfFaces());
		return aabbTree;
	}*/
			
	public Ray convertRayToModelSpace(Point3D origin, Point3D end, SWGObject object) {
		
		Point3D position = object.getPosition();

		WB_M44 translateMatrix = new WB_M44(1, 0, 0, position.x, 0, 1, 0, position.y, 0, 0, 1, position.z, 0, 0, 0, 1);
		
		float radians = object.getRadians();
		float sin = (float) Math.sin(radians);
		float cos = (float) Math.cos(radians);

        WB_M44 rotationMatrix = new WB_M44(cos, 0, sin, 0, 0, 1, 0, 0, -sin, 0, cos, 0, 0, 0, 0, 1);

        WB_M44 modelSpace = translateMatrix.mult(rotationMatrix).inverse();
        
        float originX = (float) (modelSpace.m11 * origin.x + modelSpace.m12 * origin.y + modelSpace.m13 * origin.z + modelSpace.m14);
        float originY = (float) (modelSpace.m21 * origin.x + modelSpace.m22 * origin.y + modelSpace.m23 * origin.z + modelSpace.m24);
        float originZ = (float) (modelSpace.m31 * origin.x + modelSpace.m32 * origin.y + modelSpace.m33 * origin.z + modelSpace.m34);
        
        origin = new Point3D(originX, originY, originZ);
        
        float endX = (float) (modelSpace.m11 * end.x + modelSpace.m12 * end.y + modelSpace.m13 * end.z + modelSpace.m14);
        float endY = (float) (modelSpace.m21 * end.x + modelSpace.m22 * end.y + modelSpace.m23 * end.z + modelSpace.m24);
        float endZ = (float) (modelSpace.m31 * end.x + modelSpace.m32 * end.y + modelSpace.m33 * end.z + modelSpace.m34);
        
        end = new Point3D(endX, endY, endZ);
		Vector3D direction = new Vector3D(end.x - origin.x, end.y - origin.y, end.z - origin.z);
		if(direction.getX() > 0 && direction.getY() > 0 && direction.getZ() > 0)
			direction.normalize();
		
		return new Ray(origin, direction);
		
	}
	
	public Point3D convertPointToModelSpace(Point3D point, SWGObject object) {
		
		Point3D position = object.getPosition();

		WB_M44 translateMatrix = new WB_M44(1, 0, 0, position.x, 0, 1, 0, position.y, 0, 0, 1, position.z, 0, 0, 0, 1);
		
		float radians = object.getRadians();
		float sin = (float) Math.sin(radians);
		float cos = (float) Math.cos(radians);

        WB_M44 rotationMatrix = new WB_M44(cos, 0, sin, 0, 0, 1, 0, 0, -sin, 0, cos, 0, 0, 0, 0, 1);

        WB_M44 modelSpace = translateMatrix.mult(rotationMatrix).inverse();
        
        float x = (float) (modelSpace.m11 * point.x + modelSpace.m12 * point.y + modelSpace.m13 * point.z + modelSpace.m14);
        float y = (float) (modelSpace.m21 * point.x + modelSpace.m22 * point.y + modelSpace.m23 * point.z + modelSpace.m24);
        float z = (float) (modelSpace.m31 * point.x + modelSpace.m32 * point.y + modelSpace.m33 * point.z + modelSpace.m34);
        
        return new Point3D(x, y, z);

	}
	
	/*
	 * Moved this to ConnectionService which will disconnect them
	 * from the server if they don't send packets for 5 minutes or more
	 * like on live.
	 * 
	 * We had significant issues with client nulls due to us taking
	 * client disconnect requests too seriously.  It has a weird tendency
	 * to bluff and send a disconnect packet when it's not disconnecting
	 * and continues sending packets.
	 */
	public void handleDisconnect(final IoSession session) {
		final Client client = core.getClient(session);

		if(client == null)
			return;
		
		if(client.getParent() == null)
			return;

		final CreatureObject object = (CreatureObject) client.getParent();
		SWGObject container = object.getContainer();
		PlayerObject ghost = (PlayerObject) object.getSlottedObject("ghost");
		
		if(object.getAttachment("proposer") != null)
			object.setAttachment("proposer", null);
		
		final long objectId = object.getObjectID();
		
		if(!ghost.isSet(PlayerFlags.LD))
			ghost.toggleFlag(PlayerFlags.LD);
				
		try {
			if (core.mountService.isMounted(object)) {
				core.mountService.dismount(object, (CreatureObject) container);
			}
			
			core.mountService.storeAll(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			for (Integer roomId : ghost.getJoinedChatChannels()) {
				ChatRoom room = core.chatService.getChatRoom(roomId.intValue());
				
				if (room != null) {
					core.chatService.leaveChatRoom(object, roomId.intValue(), false);
				} else {
					// work-around for any channels that may have been deleted, or only spawn on server startup, that were added to the joined channels
					ghost.removeChannel(roomId);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ScheduledFuture<?> disconnectTask = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				SWGObject object = core.objectService.getObject(objectId);
				
				if (object.getAttachment("disconnectTask") != null) {
					core.connectionService.disconnect(client);
					/*
					try {
						Thread.sleep(900000)
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					// If connectionService fails for some reason, this will call disconnect again after 15 mins.
					// This should never actually be needed though:
					
					try {
						if (object != null && object.getAttachment("disconnectTask") != null && client != null) {
							core.connectionService.disconnect(client);
						}
					} catch (Exception e) {
						System.err.println("ConnectionService:disconnect(): Error disconnecting client.");
						e.printStackTrace();
					}
					*/
				}
			}
		}, 120, TimeUnit.SECONDS);
		
		core.removeClient(session);
		
		object.setAttachment("disconnectTask", disconnectTask);

	}

	public void handleZoneIn(Client client) {

		
		if(client.getParent() == null)
			return;
		
		CreatureObject object = (CreatureObject) client.getParent();
		Quaternion orientation = object.getOrientation();
		Point3D position = object.getPosition();
		
		Point3D pos = object.getWorldPosition();
				
		if(object.getParentId() != 0) {
			Collection<SWGObject> newAwareObjects = get(object.getPlanet(), pos.x, pos.z, 512);
			for(Iterator<SWGObject> it = newAwareObjects.iterator(); it.hasNext();) {
				SWGObject obj = it.next();
				if(obj.getAttachment("bigSpawnRange") == null && obj.getWorldPosition().getDistance2D(pos) > 200)
					continue;
				//System.out.println(obj.getTemplate());
				object.makeAware(obj);
				if(obj.getClient() != null)
					obj.makeAware(object);
			}
		} else {
			add(object, pos.x, pos.z, true);
		}
		
		PlayerObject ghost = (PlayerObject) object.getSlottedObject("ghost");
				
		core.weatherService.sendWeather(object);
		
		//core.chatService.joinChatRoom(object.getCustomName().toLowerCase(), "SWG." + core.getGalaxyName() + "." + object.getPlanet().getName());

		if (!object.hasSkill(ghost.getProfessionWheelPosition())) {
			object.showFlyText(OutOfBand.ProsePackage("@cbt_spam:skill_up"), 2.5f, new RGB(154, 205, 50), 0, true);
			object.playEffectObject("clienteffect/skill_granted.cef", "");
			object.playMusic("sound/music_acq_bountyhunter.snd");
			core.skillService.addSkill(object, ghost.getProfessionWheelPosition());
		}
		
		if(object.getGroupId() != 0 && core.objectService.getObject(object.getGroupId()) instanceof GroupObject)
			object.makeAware(core.objectService.getObject(object.getGroupId()));
		
		if(object.getPosture() == Posture.Dead)
			core.playerService.sendCloningWindow(object, false);
		
		ChatRoom zoneRoom = core.chatService.getChatRoomByAddress("SWG." + core.getGalaxyName() + "." + object.getPlanet().getName() + ".Planet");
		if (zoneRoom != null) {
			String chatName = object.getCustomName().toLowerCase();
			
			if (chatName.contains(" "))
				chatName = chatName.split(" ")[0];

			if (!zoneRoom.getUserList().contains(chatName)) {
				core.chatService.joinChatRoom(chatName, zoneRoom.getRoomId(), true);
			}
		}
		
	}
		
	public void transferToPlanet(SWGObject object, Planet planet, Point3D newPos, Quaternion newOrientation, SWGObject newParent) {
		
		if (planet == null)
			return;
		
		Client client = object.getClient();
		
		if(client == null)
			return;
		
		IoSession session = client.getSession();
		
		if(session == null)
			return;
		
		Point3D position = object.getPosition();
		
		if(object.getParentId() == 0 && object.getContainer() == null) {
			remove(object, position.x, position.z, true);
		} else {
			object.getContainer().remove(object);
		}

		/*HashSet<Client> oldObservers = new HashSet<Client>(object.getObservers());

		for(Client observerClient : oldObservers) {
			if(observerClient.getParent() != null) {
				observerClient.getParent().makeUnaware(object);
			}
		}*/
		
		
		synchronized(object.getMutex()) {
			object.getAwareObjects().removeAll(object.getAwareObjects());
		}
		
		object.setPlanet(planet);
		object.setPlanetId(planet.getID());
		object.setPosition(newPos);
		object.setOrientation(newOrientation);
		
		if(newParent != null && newParent instanceof CellObject)
			newParent._add(object);
		
		HeartBeatMessage heartBeat = new HeartBeatMessage();
		session.write(heartBeat.serialize());

		CmdStartScene startScene = new CmdStartScene((byte) 0, object.getObjectID(), object.getPlanet().getPath(), object.getTemplate(), newPos.x, newPos.y, newPos.z, core.getGalacticTime() / 1000, object.getRadians());
		session.write(startScene.serialize());
		
		handleZoneIn(client);
		object.makeAware(object);

	}

	public void openContainer(SWGObject requester, SWGObject container) {
		
		if(container.getPermissions().canView(requester, container)) {
			OpenedContainerMessage opm = new OpenedContainerMessage(container.getObjectID());
			if(requester.getClient() != null && requester.getClient().getSession() != null && !(container instanceof CreatureObject)){
				requester.getClient().getSession().write(opm.serialize());
			}
		}	
	}
	
	public void transform(TangibleObject obj, Point3D position)
	{
		Point3D oldPosition = obj.getPosition();
		Point3D newPosition = new Point3D(oldPosition.x + position.x, oldPosition.y + position.y, oldPosition.z + position.z);
		
		teleport(obj, newPosition, obj.getOrientation(), obj.getParentId());
	}
	
	public void transform(SWGObject obj, float rotation, Point3D axis)
	{
		rotation *= (Math.PI / 180);
		
		Quaternion oldRotation = obj.getOrientation();
		Quaternion newRotation = resources.common.MathUtilities.rotateQuaternion(oldRotation, rotation, axis);
		
		teleport(obj, obj.getPosition(), newRotation, obj.getParentId());
	}
	
	public void rotateToFaceTarget(SWGObject object, SWGObject target) {
		float radians = (float) (((float) Math.atan2(target.getPosition().z - object.getPosition().z, target.getPosition().x - object.getPosition().x)) - object.getRadians());
		transform(object, radians, object.getPosition());
	}
	
	public void faceTarget(SWGObject object, SWGObject target) {
		float direction = (float) Math.atan2(target.getWorldPosition().x - object.getWorldPosition().x, target.getWorldPosition().z - object.getWorldPosition().z);
		
		if (direction < 0) {
			direction = (float) (2 * Math.PI + direction);
		}
		
		if (Math.abs(direction - object.getRadians()) < 0.05) {
			return;
		}
		
		Quaternion quaternion = new Quaternion((float) Math.cos(direction / 2), 0, (float) Math.sin(direction / 2), 0);
        
		if (quaternion.y < 0.0f && quaternion.w > 0.0f) {
        	quaternion.y *= -1;
        	quaternion.w *= -1;
        }
		
		if (object.getContainer() instanceof CellObject) {
			NGECore.getInstance().simulationService.moveObject(object, object.getPosition(), quaternion, object.getMovementCounter(), 0, (CellObject) object.getContainer());
		} else {
			NGECore.getInstance().simulationService.moveObject(object, object.getPosition(), quaternion, object.getMovementCounter(), 0, null);
		}
	}
	
	public void teleport(SWGObject obj, Point3D position, Quaternion orientation, long cellId) {
		
		if (obj.getPosition().getDistance2D(position) > 150) {
			if (cellId != 0)
				transferToPlanet(obj, obj.getPlanet(), position, orientation, core.objectService.getObject(cellId));
			else
				transferToPlanet(obj, obj.getPlanet(), position, orientation, null);
			return;
		}

		if(cellId == 0) {
			if(position.x >= -8192 && position.x <= 8192 && position.z >= -8192 && position.z <= 8192) {
				obj.setMovementCounter(obj.getMovementCounter() + 1);
				DataTransform dataTransform = new DataTransform(new Point3D(position.x, position.y, position.z), orientation, obj.getMovementCounter(), obj.getObjectID());
				ObjControllerMessage objController = new ObjControllerMessage(0x1B, dataTransform);
				obj.notifyObservers(objController, true);
			}
		} else {
			obj.setMovementCounter(obj.getMovementCounter() + 1);
			DataTransformWithParent dataTransform = new DataTransformWithParent(new Point3D(position.x, position.y, position.z), orientation, obj.getMovementCounter(), obj.getObjectID(), cellId);
			ObjControllerMessage objController = new ObjControllerMessage(0x1B, dataTransform);
			obj.notifyObservers(objController, true);
			
			obj.setPosition(position);
			obj.setOrientation(orientation);
			
			// Shouldn't the parent be set?
		}
			
	}
	
	/* Check world line of sight between an object and coordinates, instead of between two objects
	 * Needed for vehicles.
	 */
	public boolean checkLineOfSight(SWGObject object, Point3D position2) {
		long startTime = System.nanoTime();
		
		float heightOrigin = 1.f;
		float heightDirection = 1.f;
		
		if (object instanceof CreatureObject) {
			heightOrigin = getHeightOrigin((CreatureObject) object);
		}
		
		Point3D position1 = object.getWorldPosition();
		
		Point3D origin = new Point3D(position1.x, position1.y + heightOrigin, position1.z);
		Point3D end = new Point3D(position2.x, position2.y + heightDirection, position2.z);
		float distance = position1.getDistance2D(position2);
		
		List<SWGObject> inRangeObjects = get(object.getPlanet(), position1.x, position1.z, (int) distance);
		
		for (SWGObject inRangeObject : inRangeObjects) {
			if (inRangeObject == object) {
				continue;
			}
			
			if (object.getTemplateData() != null && object.getTemplateData().getAttribute("collisionActionBlockFlags") != null) {
				int bit = (Integer) object.getTemplateData().getAttribute("collisionActionBlockFlags") & 255;
				
				if (bit == (Integer) object.getTemplateData().getAttribute("collisionActionBlockFlags")) {
					continue;
				}
			}
			
			Ray ray = convertRayToModelSpace(origin, end, object);
			
			MeshVisitor visitor = object.getMeshVisitor();
			
			if (visitor == null) {
				continue;
			}
			
			List<Mesh3DTriangle> tris = visitor.getTriangles();
			
			if (tris.isEmpty()) {
				continue;
			}
			
			for (Mesh3DTriangle tri : tris) {
				if (ray.intersectsTriangle(tri, distance) != null) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean checkLineOfSight(SWGObject obj1, SWGObject obj2) {
		long startTime = System.nanoTime();
		if(obj1.getPlanet() != obj2.getPlanet())
			return false;
		
		// If obj1 is container of obj2 vice versa
		if (obj1 == obj2.getContainer() || obj2 == obj1.getContainer() || obj1 == obj2.getGrandparent() || obj2 == obj1.getGrandparent()) {
			return true;
		}
		
		if(obj1.getGrandparent() != null || obj2.getGrandparent() != null) {
			
			if(obj1.getGrandparent() == obj2.getGrandparent())
				return checkLineOfSightInBuilding(obj1, obj2, obj1.getGrandparent());
			else if(obj1.getGrandparent() != null && obj2.getGrandparent() != null)
				return false; 
			
		}
		
		float heightOrigin = 1.f;
		float heightDirection = 1.f;
		
		if (obj2.getTemplate().equals("object/tangible/inventory/shared_character_inventory.iff")){
			obj2 = obj2.getContainer(); // LOS message fix on corpse
		}
		
		if(obj1 instanceof CreatureObject)
			heightOrigin = getHeightOrigin((CreatureObject) obj1);
		
		if(obj2 instanceof CreatureObject)
			heightDirection = getHeightOrigin((CreatureObject) obj2);
		
		Point3D position1 = obj1.getWorldPosition();
		Point3D position2 = obj2.getWorldPosition();

		Point3D origin = new Point3D(position1.x, position1.y + heightOrigin, position1.z);
		Point3D end = new Point3D(position2.x, position2.y + heightDirection, position2.z);
		float distance = position1.getDistance2D(position2);
		
		List<SWGObject> inRangeObjects = get(obj1.getPlanet(), position1.x, position1.z, (int) (distance + 10));

		for(SWGObject object : inRangeObjects) {
			
			if(object == obj1 || object == obj2)
				continue;
			
			if(object.getTemplateData() != null && object.getTemplateData().getAttribute("collisionActionBlockFlags") != null) {
				int bit = (Integer) object.getTemplateData().getAttribute("collisionActionBlockFlags") & 255;
				if(bit == (Integer) object.getTemplateData().getAttribute("collisionActionBlockFlags"))
					continue;
			}

			Ray ray = convertRayToModelSpace(origin, end, object);
						
			MeshVisitor visitor = object.getMeshVisitor();
			
			if(visitor == null)
				continue;
			
			List<Mesh3DTriangle> tris = visitor.getTriangles();
			
			if(tris.isEmpty())
				continue;
			
			for(Mesh3DTriangle tri : tris) {
				
				if(ray.intersectsTriangle(tri, distance) != null) {
					//System.out.println("Collision took: " + (System.nanoTime() - startTime) + " ns (collided)");
				//	System.out.println("Collided with " + object.getTemplate() + " X: " + object.getPosition().x + " Y: " + object.getPosition().y + " Z: " + object.getPosition().z);	
					return false;
				}
				
			}
						
		}
		

		if(obj1.getContainer() != null || obj2.getContainer() != null) {
			
			CellObject cell = null;
			
			if(obj1.getContainer() != null && obj1.getContainer() instanceof CellObject)
				cell = (CellObject) obj1.getContainer();
			else if(obj2.getContainer() != null && obj2.getContainer() instanceof CellObject)
				cell = (CellObject) obj2.getContainer();

			if(cell != null) 
				return checkLineOfSightWorldToCell(obj1, obj2, cell);
		}
		
		List<Vec3D> segments = new ArrayList<Vec3D>();
		Line3D.splitIntoSegments(new Vec3D(position1.x, position1.y + 1, position1.z), new Vec3D(position2.x, position2.y + 1, position2.z), (float) 1, segments, true);
		
		for(Vec3D segment : segments) {
			float y = segment.y;
			
			int height = (int) core.terrainService.getHeight(obj1.getPlanetId(), segment.x, segment.z); // round down to int
			
			if(height > y) {
				//System.out.println("Collision took: " + (System.nanoTime() - startTime) + " ns (terrain collision)");				
				return false;
			}
		}
		//System.out.println("Collision took: " + (System.nanoTime() - startTime) + " ns (did not collide)");

		return true;

	}
	
	public boolean checkLineOfSightInBuilding(SWGObject obj1, SWGObject obj2, SWGObject building) {
		
		PortalVisitor portalVisitor = building.getPortalVisitor();
		
		if(portalVisitor == null)
			return true;
		
		Point3D position1 = obj1.getPosition();
		Point3D position2 = obj2.getPosition();
		
		Point3D origin = new Point3D(position1.x, position1.y + 1, position1.z);
		Point3D end = new Point3D(position2.x, position2.y + 1, position2.z);
		
		Vector3D direction = new Vector3D(end.x - origin.x, end.y - origin.y, end.z - origin.z);
		
		if (direction.getNorm() != 0) {
			direction.normalize();
		} else {
			System.out.println("WARNING: checkLineOfSightInBuilding: Vector norm was 0.");
		}
		
		float distance = position1.getDistance2D(position2);
		Ray ray = new Ray(origin, direction);
		
		for(int i = 1; i < portalVisitor.cells.size(); i++) {
			
			Cell cell = portalVisitor.cells.get(i);
			try {
				
				MeshVisitor meshVisitor;
				if(!cellMeshes.containsKey(cell.mesh)) {
					meshVisitor = ClientFileManager.loadFile(cell.mesh, MeshVisitor.class);
					meshVisitor.getTriangles();
					cellMeshes.put(cell.mesh, meshVisitor);
				} else {
					meshVisitor = cellMeshes.get(cell.mesh);
				}
				
				if(meshVisitor == null)
					continue;
				
				List<Mesh3DTriangle> tris = meshVisitor.getTriangles();
				
				if(tris.isEmpty())
					continue;

				for(Mesh3DTriangle tri : tris) {
					
					if(ray.intersectsTriangle(tri, distance) != null) {
					//	System.out.println("Collision with: " + cell.name);
						return false;
					}
					
				}


			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
		
		return true;

	}
	
	public boolean checkLineOfSightWorldToCell(SWGObject obj1, SWGObject obj2, CellObject cell) {
		
		SWGObject building = cell.getContainer();
		
		if(building == null)
			return true;
		
		PortalVisitor portalVisitor = building.getPortalVisitor();
		
		if(portalVisitor == null)
			return true;
		
		float heightOrigin = 1.f;
		float heightDirection = 1.f;
		
		if(obj1 instanceof CreatureObject)
			heightOrigin = getHeightOrigin((CreatureObject) obj1);
		
		if(obj2 instanceof CreatureObject)
			heightDirection = getHeightOrigin((CreatureObject) obj2);
		
		Point3D position1 = obj1.getWorldPosition();
		Point3D position2 = obj2.getWorldPosition();

		Point3D origin = new Point3D(position1.x, position1.y + heightOrigin, position1.z);
		Point3D end = new Point3D(position2.x, position2.y + heightDirection, position2.z);

		Ray ray = convertRayToModelSpace(origin, end, building);
		
		if(cell.getCellNumber() >= portalVisitor.cellCount)
			return true;
		
		try {
			
			MeshVisitor meshVisitor = ClientFileManager.loadFile(portalVisitor.cells.get(cell.getCellNumber()).mesh, MeshVisitor.class);
			
			if(meshVisitor == null)
				return true;
			
			List<Mesh3DTriangle> tris = meshVisitor.getTriangles();
			
			if(tris.isEmpty())
				return true;

			for(Mesh3DTriangle tri : tris) {
				
				if(ray.intersectsTriangle(tri) != null) {
					return false;
				}
				
			}

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public float getHeightOrigin(CreatureObject creature) {
		
		float height = (float) (creature.getHeight()/* - 0.3*/);
		
		if(creature.getPosture() == 2 || creature.getPosture() == 13 || creature.getPosture() == 14)
			height = 0.45f;
		else if(creature.getPosture() == 1)
			height /= 2.f;
		
		return height;

	}
	
	public void notifyPlanet(Planet planet, IoBuffer packet) {
		
		ConcurrentHashMap<IoSession, Client> clients = core.getActiveConnectionsMap();
		
		for(Client client : clients.values()) {
			
			if(client.getParent() == null)
				continue;

			if(client.getParent().getPlanet() == null)
				continue;
			else if(client.getParent().getPlanet() == planet)
				client.getSession().write(packet);
			
		}
		
	}
	
	public void notifyAllClients(IoBuffer packet) {
		
		ConcurrentHashMap<IoSession, Client> clients = core.getActiveConnectionsMap();
		
		for(Client client : clients.values()) {
			
			if(client.getParent() == null)
				continue;

			client.getSession().write(packet);
			
		}

	}
	
	public void checkForCollidables(SWGObject object) {
		Point3D objectPos = object.getWorldPosition();
		List<AbstractCollidable> collidables = getCollidables(object.getPlanet(), objectPos.x, objectPos.z, 2050);
		
		for(AbstractCollidable collidable : collidables) {
			collidable.doCollisionCheck(object);
		}
	}
	
	public class MoveEvent implements Event {
		
		public SWGObject object;
		
	}

}
