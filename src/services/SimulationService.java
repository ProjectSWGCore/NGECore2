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
import engine.resources.common.Ray;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.scene.quadtree.QuadTree;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import protocol.swg.ChatFriendsListUpdate;
import protocol.swg.ChatOnChangeFriendStatus;
import protocol.swg.ChatOnGetFriendsList;
import protocol.swg.CmdStartScene;
import protocol.swg.HeartBeatMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.OpenedContainerMessage;
import protocol.swg.UpdateTransformMessage;
import protocol.swg.UpdateTransformWithParentMessage;
import protocol.swg.objectControllerObjects.DataTransform;
import protocol.swg.objectControllerObjects.DataTransformWithParent;
import protocol.swg.objectControllerObjects.TargetUpdate;
import resources.objects.building.BuildingObject;
import resources.objects.cell.CellObject;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.common.*;
import resources.common.collidables.AbstractCollidable;
import resources.datatables.PlayerFlags;
import resources.datatables.Posture;
import services.ai.LairActor;
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
		EntityCursor<BuildingObject> cursor = core.getBuildingODB().getCursor(Long.class, BuildingObject.class);
		
		Iterator<BuildingObject> it = cursor.iterator();
		
		while(it.hasNext()) {
			final BuildingObject building = (BuildingObject) core.objectService.getObject(it.next().getObjectID());
			if(building == null)
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
	
	public boolean add(SWGObject object, float x, float y) {
		return add(object, x, y, false);
	}
		
	public boolean add(SWGObject object, float x, float y, boolean notifyObservers) {
		object.setIsInQuadtree(true);
		boolean success = quadTrees.get(object.getPlanet().getName()).put(x, y, object);
		if(success) {
			Vector<SWGObject> childObjects = (Vector<SWGObject>) object.getAttachment("childObjects");
			if(childObjects != null) {
				addChildObjects(object, childObjects);
				object.setAttachment("childObjects", null);
			}
			if(notifyObservers) {
				Point3D pos = new Point3D(x, 0, y);
				Collection<SWGObject> newAwareObjects = get(object.getPlanet(), x, y, 512);
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
		
	public boolean move(SWGObject object, int oldX, int oldY, int newX, int newY) {
		if(quadTrees.get(object.getPlanet().getName()).remove(oldX, oldY, object)) {
			return quadTrees.get(object.getPlanet().getName()).put(newX, newY, object);
		}
			
		return false;
	}
	
	public boolean move(SWGObject object, float oldX, float oldY, float newX, float newY) {
		long startTime = System.nanoTime();
		if(quadTrees.get(object.getPlanet().getName()).remove(oldX, oldY, object)) {
			boolean success = quadTrees.get(object.getPlanet().getName()).put(newX, newY, object);
			return success;
		}
		System.out.println("Move failed.");
		return false;
	}
		
	public List<SWGObject> get(Planet planet, float x, float y, int range) {
		List<SWGObject> list = quadTrees.get(planet.getName()).get((int)x, (int)y, range);
		return list;
	}
	
	public boolean remove(SWGObject object, float x, float y) {
		return remove(object, x, y, false);
	}
		
	public boolean remove(SWGObject object, float x, float y, boolean notifyObservers) {
		if (object == null || !object.isInQuadtree()) {
			return false;
		}
		
		boolean success = quadTrees.get(object.getPlanet().getName()).remove(x, y, object);
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
				
				CreatureObject object = (CreatureObject) client.getParent();
				Point3D newPos;
				Point3D oldPos;
				synchronized(object.getMutex()) {
					newPos = new Point3D(dataTransform.getXPosition(), dataTransform.getYPosition(), dataTransform.getZPosition());
					if(Float.isNaN(newPos.x) || Float.isNaN(newPos.y) || Float.isNaN(newPos.z)) 
						return;
					oldPos = object.getPosition();
					//Collection<Client> oldObservers = object.getObservers();
					//Collection<Client> newObservers = new HashSet<Client>();
					if(object.getContainer() == null)
						move(object, oldPos.x, oldPos.z, newPos.x, newPos.z);
					Quaternion newOrientation = new Quaternion(dataTransform.getWOrientation(), dataTransform.getXOrientation(), dataTransform.getYOrientation(), dataTransform.getZOrientation());
					object.setPosition(newPos);
					object.setOrientation(newOrientation);
					object.setMovementCounter(dataTransform.getMovementCounter());
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
	
				List<SWGObject> newAwareObjects = get(object.getPlanet(), newPos.x, newPos.z, 512);
				ArrayList<SWGObject> oldAwareObjects = new ArrayList<SWGObject>(object.getAwareObjects());
				Collection<SWGObject> updateAwareObjects = CollectionUtils.intersection(oldAwareObjects, newAwareObjects);
				object.notifyObservers(utm, false);

				for(int i = 0; i < oldAwareObjects.size(); i++) {
					SWGObject obj = oldAwareObjects.get(i);
					if(!updateAwareObjects.contains(obj) && obj != object && obj.getWorldPosition().getDistance2D(newPos) > 200 && obj.isInQuadtree() /*&& obj.getParentId() == 0*/) {
						if(obj.getAttachment("bigSpawnRange") != null && obj.getWorldPosition().getDistance2D(newPos) < 512)
							continue;
						object.makeUnaware(obj);
						if(obj.getClient() != null)
							obj.makeUnaware(object);
					} else if(obj != object && obj.getWorldPosition().getDistance2D(newPos) > 200 && obj.isInQuadtree() && obj.getAttachment("bigSpawnRange") == null) {
						object.makeUnaware(obj);
						if(obj.getClient() != null)
							obj.makeUnaware(object);
					}
				}
				for(int i = 0; i < newAwareObjects.size(); i++) {
					SWGObject obj = newAwareObjects.get(i);
					//System.out.println(obj.getTemplate());
					if(!updateAwareObjects.contains(obj) && obj != object && !object.getAwareObjects().contains(obj) &&  obj.getContainer() != object && obj.isInQuadtree()) {						
						if(obj.getAttachment("bigSpawnRange") == null && obj.getWorldPosition().getDistance2D(newPos) > 200)
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
				
				Point3D newPos = new Point3D(dataTransform.getXPosition(), dataTransform.getYPosition(), dataTransform.getZPosition());
				if(Float.isNaN(newPos.x) || Float.isNaN(newPos.y) || Float.isNaN(newPos.z))
					return;
				Point3D oldPos = object.getPosition();
				Quaternion newOrientation = new Quaternion(dataTransform.getWOrientation(), dataTransform.getXOrientation(), dataTransform.getYOrientation(), dataTransform.getZOrientation());

				UpdateTransformWithParentMessage utm = new UpdateTransformWithParentMessage(object.getObjectID(), dataTransform.getCellId(), dataTransform.getTransformedX(), dataTransform.getTransformedY(), dataTransform.getTransformedZ(), dataTransform.getMovementCounter(), (byte) dataTransform.getMovementAngle(), dataTransform.getSpeed());

				
				if(object.getContainer() != parent) {
					remove(object, oldPos.x, oldPos.z);
					if(object.getContainer() != null)
						object.getContainer()._remove(object);
					parent._add(object);
				}
				object.setPosition(newPos);
				object.setOrientation(newOrientation);
				object.setMovementCounter(dataTransform.getMovementCounter());
				object.notifyObservers(utm, false);
				
				checkForCollidables(object);

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
			Collection<SWGObject> updateAwareObjects = CollectionUtils.intersection(oldAwareObjects, newAwareObjects);
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
		PlayerObject ghost = (PlayerObject) object.getSlottedObject("ghost");
		
		if(object.getAttachment("proposer") != null)
			object.setAttachment("proposer", null);
		
		//session.suspendWrite();
		final long objectId = object.getObjectID();
		
		if(!ghost.isSet(PlayerFlags.LD))
			ghost.toggleFlag(PlayerFlags.LD);
		
		for (CreatureObject opponent : new ArrayList<CreatureObject>(object.getDuelList())) {
			if (opponent != null) {
				core.combatService.handleEndDuel(object, opponent, true);
			}
		}
		
		/*
		object.createTransaction(core.getCreatureODB().getEnvironment());
		core.getCreatureODB().put(object, Long.class, CreatureObject.class, object.getTransaction());
		object.getTransaction().commitSync();*/
		
		ScheduledFuture<?> disconnectTask = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				if(core.objectService.getObject(objectId).getAttachment("disconnectTask") != null)
					core.connectionService.disconnect(client);
			}
		}, 1, TimeUnit.MINUTES);
		core.removeClient(session);
		
		object.setAttachment("disconnectTask", disconnectTask);
		for(TangibleObject obj : new Vector<TangibleObject>(object.getDefendersList())) 
			object.removeDefender(obj);	// temp fix for being stuck in combat

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
				if(obj.getAttachment("bigSpawnRange") == null && obj.getWorldPosition().getDistance(pos) > 200)
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
				
		if (!object.hasSkill(ghost.getProfessionWheelPosition())) {
			object.showFlyText("cbt_spam", "skill_up", (float) 2.5, new RGB(154, 205, 50), 0);
			object.playEffectObject("clienteffect/skill_granted.cef", "");
			object.playMusic("sound/music_acq_bountyhunter.snd");
			core.skillService.addSkill(object, ghost.getProfessionWheelPosition());
		}
		
		if(object.getGroupId() != 0 && core.objectService.getObject(object.getGroupId()) instanceof GroupObject)
			object.makeAware(core.objectService.getObject(object.getGroupId()));
		
		if(object.getPosture() == Posture.Dead)
			core.playerService.sendCloningWindow(object, false);
	}
		
	public void transferToPlanet(SWGObject object, Planet planet, Point3D newPos, Quaternion newOrientation, SWGObject newParent) {
		
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

		CmdStartScene startScene = new CmdStartScene((byte) 0, object.getObjectID(), object.getPlanet().getPath(), object.getTemplate(), newPos.x, newPos.y, newPos.z, System.currentTimeMillis() / 1000, object.getRadians());
		session.write(startScene.serialize());
		
		handleZoneIn(client);
		object.makeAware(object);

	}

	public void openContainer(SWGObject requester, SWGObject container) {
		
		if(container.getPermissions().canView(requester, container)) {
			OpenedContainerMessage opm = new OpenedContainerMessage(container.getObjectID());
			if(requester.getClient() != null && requester.getClient().getSession() != null && !(container instanceof CreatureObject))
				requester.getClient().getSession().write(opm.serialize());
		}
		
	}
	
	public void teleport(SWGObject obj, Point3D position, Quaternion orientation, long cellId) {
		
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
		}
			
	}
	
	public boolean checkLineOfSight(SWGObject obj1, SWGObject obj2) {
		long startTime = System.nanoTime();
		if(obj1.getPlanet() != obj2.getPlanet())
			return false;
		
		if(obj1.getGrandparent() != null || obj2.getGrandparent() != null) {
			
			if(obj1.getGrandparent() == obj2.getGrandparent())
				return checkLineOfSightInBuilding(obj1, obj2, obj1.getGrandparent());
			else if(obj1.getGrandparent() != null && obj2.getGrandparent() != null)
				return false; 
			
		}
		
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
		
		if(creature.getPosture() == 2 || creature.getPosture() == 13)
			height = 0.3f;
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
