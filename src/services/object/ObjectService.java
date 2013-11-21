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
package services.object;

import java.io.IOException;
import java.nio.ByteOrder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import resources.common.*;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.python.core.Py;
import org.python.core.PyObject;

import com.sleepycat.persist.EntityCursor;

import protocol.swg.ChatOnGetFriendsList;
import protocol.swg.CmdSceneReady;
import protocol.swg.CmdStartScene;
import protocol.swg.HeartBeatMessage;
import protocol.swg.ParametersMessage;
import protocol.swg.SelectCharacter;
import protocol.swg.ServerTimeMessage;
import protocol.swg.UnkByteFlag;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.CrcStringTableVisitor;
import engine.clientdata.visitors.WorldSnapshotVisitor;
import engine.clientdata.visitors.WorldSnapshotVisitor.SnapshotChunk;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.container.Traverser;
import engine.resources.database.DatabaseConnection;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import main.NGECore;
import resources.objects.building.BuildingObject;
import resources.objects.cell.CellObject;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.guild.GuildObject;
import resources.objects.player.PlayerObject;
import resources.objects.staticobject.StaticObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.waypoint.WaypointObject;
import resources.objects.weapon.WeaponObject;

@SuppressWarnings("unused")

public class ObjectService implements INetworkDispatch {

	private Map<Long, SWGObject> objectList = new ConcurrentHashMap<Long, SWGObject>();
	
	private NGECore core;
	
	private DatabaseConnection databaseConnection;

	private AtomicLong highestId = new AtomicLong();
	
	private Random random = new Random();
	
	private Map<String, PyObject> serverTemplates = new ConcurrentHashMap<String, PyObject>();
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public ObjectService(final NGECore core) {
		this.core = core;
		databaseConnection = core.getDatabase1();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
		    public void run() {
		    	synchronized(objectList) {
		    		for(SWGObject obj : objectList.values()) {
		    			
		    			if(obj.getSlottedObject("ghost") != null) {
		    				((CreatureObject) obj).createTransaction(core.getCreatureODB().getEnvironment());
		    				core.getCreatureODB().put((CreatureObject) obj, Long.class, CreatureObject.class, ((CreatureObject) obj).getTransaction());
		    			}
		    			
		    		}
		    	}
		    }
		});
		long highestId;
		

		try {
			PreparedStatement ps = databaseConnection.preparedStatement("SELECT id FROM highestid WHERE id=(SELECT max(id) FROM highestid)");
			ResultSet result = ps.executeQuery();
			result.next();
			highestId = result.getInt("id");
			this.highestId.set(highestId);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public SWGObject createObject(String Template, long objectID, Planet planet, Point3D position, Quaternion orientation) {
		SWGObject object = null;
		CrcStringTableVisitor crcTable;
		try {
			crcTable = ClientFileManager.loadFile("misc/object_template_crc_string_table.iff", CrcStringTableVisitor.class);
			if(!crcTable.isValidCRC(CRC.StringtoCRC(Template))) {
				System.out.println("Invalid CRC for template:" + Template);
				return null;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if(objectID == 0)
			objectID = generateObjectID();
		
		if(Template.startsWith("object/creature")) {
			
			object = new CreatureObject(objectID, planet, position, orientation, Template);

		} else if(Template.startsWith("object/player")) {
			
			object = new PlayerObject(objectID, planet);
			
		} else if(Template.startsWith("object/tangible")) {
			
			object = new TangibleObject(objectID, planet, Template, position, orientation);

		} else if(Template.startsWith("object/weapon")) {
			
			object = new WeaponObject(objectID, planet, Template, position, orientation);

		} else if(Template.startsWith("object/building")){
			
			object = new BuildingObject(objectID, planet, position, orientation, Template);
			
		} else if(Template.startsWith("object/cell")) {
			
			object = new CellObject(objectID, planet);
			
		} else if(Template.startsWith("object/static")) {
			
			object = new StaticObject(objectID, planet, position, orientation, Template);
			
		} else if(Template.startsWith("object/guild")) {
			
			object = new GuildObject(core, objectID, planet, position, orientation, Template);
			
		} else if(Template.startsWith("object/group")) {
			
			object = new GroupObject(objectID);
			
		} else if(Template.startsWith("object/mobile")){
			
			object = new CreatureObject(objectID, planet, position, orientation, Template);
			
		} else if(Template.startsWith("object/waypoint")) {
			
			object = new WaypointObject(objectID, planet, position);
			
		}  else {
			
			return null;
			
		}
		
		object.setPlanetId(planet.getID());
		
		//loadServerTemplate(object);		
		
		objectList.put(objectID, object);

		return object;
	}
	
	public void loadServerTemplate(SWGObject object) {
		
		String template = object.getTemplate();
		String serverTemplate = template.replace(".iff", "");
		// check if template is empty(4 default lines) to reduce RAM usage(saves about 500 MB of RAM)
		try {
			int numberOfLines = FileUtilities.getNumberOfLines("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", "") + serverTemplate.split("shared_" , 2)[1] + ".py");
			
			if(numberOfLines > 4) {
				if(serverTemplates.containsKey(template)) {
					PyObject func = serverTemplates.get(template);
					func.__call__(Py.java2py(core), Py.java2py(object));
				} else {
					PyObject func = core.scriptService.getMethod("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", ""), serverTemplate.split("shared_" , 2)[1], "setup");
					func.__call__(Py.java2py(core), Py.java2py(object));
					serverTemplates.put(template, func);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public SWGObject createObject(String Template, Planet planet) {
		return createObject(Template, 0, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0));
	}
	
	public SWGObject createObject(String Template, Planet planet, float x, float z, float y) {
		return createObject(Template, 0, planet, new Point3D(x, y, z), new Quaternion(1, 0, 0, 0));
	}
	
	public void addObjectToScene(SWGObject object) {
		
		core.simulationService.add(object, object.getPosition().x, object.getPosition().z);
		
		// TODO: Get Objects in range and contained objects, send packets, add to observer lists
	}
	
	public SWGObject getObject(long objectID) {
		return objectList.get(objectID);
	}
	
	public Map<Long, SWGObject> getObjectList() { return objectList; }
	
	public void destroyObject(final SWGObject object, int seconds) {
		scheduler.schedule(new Runnable() {
			
			@Override
			public void run() {
				destroyObject(object);
			}
			
		}, seconds, TimeUnit.SECONDS);
	}
	
	public void destroyObject(SWGObject object) {
		if (object == null) {
			return;
		}
		
		if (object instanceof TangibleObject &&
		((TangibleObject) object).getRespawnTime() > 0) {
			final long objectId = object.getObjectID();
			final String Template = object.getTemplate();
			final Planet planet = object.getPlanet();
			final Point3D position = object.getPosition();
			final Quaternion orientation = object.getOrientation();
			
			scheduler.schedule(new Runnable() {
				
				@Override
				public void run() {
					createObject(Template, objectId, planet, position, orientation);
				}
				
			}, ((TangibleObject) object).getRespawnTime(), TimeUnit.SECONDS);
		}
		
		String filePath = "scripts/" + object.getTemplate().split("shared_" , 2)[0].replace("shared_", "") + object.getTemplate().split("shared_" , 2)[1].replace(".iff", "") + ".py";
		
		if (FileUtilities.doesFileExist(filePath)) {
			PyObject method = core.scriptService.getMethod("scripts/" + object.getTemplate().split("shared_" , 2)[0].replace("shared_", ""), object.getTemplate().split("shared_" , 2)[1].replace(".iff", ""), "destroy");
			
			if (method != null && method.isCallable()) {
				method.__call__(Py.java2py(core), Py.java2py(object));
			}
		}
		
		if (object == null) {
			return;
		}
		
		object.viewChildren(object, true, true, new Traverser() {
			@Override
			public void process(SWGObject obj) {
				objectList.remove(obj.getObjectID());
			}
		});
		objectList.remove(object.getObjectID());
		//core.simulationService.remove(object, object.getPosition().x, object.getPosition().y);
		
	}
	
	public void destroyObject(long objectID) {
		
		SWGObject object = getObject(objectID);
		if(object != null) {
			destroyObject(object);
		}
		
	}
	
	public SWGObject getObjectByCustomName(String customName) {
		
		synchronized(objectList) {
			
			for(SWGObject obj : objectList.values()) {
				if(obj.getCustomName() == null)
					continue;
				if(obj.getCustomName().equals(customName))
					return obj;
			}
			
		}
		
		EntityCursor<CreatureObject> cursor = core.getCreatureODB().getCursor(Long.class, CreatureObject.class);
		
		Iterator<CreatureObject> it = cursor.iterator();
		
		while(it.hasNext()) {
			if(it.next().getCustomName().equals(customName))
				return it.next();
		}

		return null;

	}
	
	public CreatureObject getCreatureFromDB(long objectId) {
		return core.getCreatureODB().get(new Long(objectId), Long.class, CreatureObject.class);
	}
	
	private long generateObjectID() {
		/*Random random = new Random();
		
		long objectID = random.nextInt();
		
		if(getObject(objectID) != null)
			return generateObjectID();
		
		if(core.getCreatureODB().contains(new Long(objectID), Long.class, CreatureObject.class))
			return generateObjectID();

		return objectID;*/
		
		long newId = highestId.incrementAndGet();
		PreparedStatement ps2;

		try {
			ps2 = databaseConnection.preparedStatement("UPDATE highestid SET id=" + newId + " WHERE id=" + (newId-1));
			ps2.executeUpdate();
			ps2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(getObject(newId) == null)
			return newId;
		else
			return generateObjectID();
		

	}
	
	public void useObject(CreatureObject creature, SWGObject object) {
		if (object == null) {
			return;
		}
		
		String filePath = "scripts/" + object.getTemplate().split("shared_" , 2)[0].replace("shared_", "") + object.getTemplate().split("shared_" , 2)[1].replace(".iff", "") + ".py";
		
		if (FileUtilities.doesFileExist(filePath)) {
			PyObject method = core.scriptService.getMethod("scripts/" + object.getTemplate().split("shared_" , 2)[0].replace("shared_", ""), object.getTemplate().split("shared_" , 2)[1].replace(".iff", ""), "useObject");
			
			if (method != null && method.isCallable()) {
				method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(object));
			}
		}
	}
	
	public void insertTimedEventBindings(ScheduledExecutorService executor) {
		
	}

	public void insertOpcodes(Map<Integer,INetworkRemoteEvent> swgOpcodes, Map<Integer,INetworkRemoteEvent> objControllerOpcodes) {
		
		swgOpcodes.put(Opcodes.SelectCharacter, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				SelectCharacter selectCharacter = new SelectCharacter();
				selectCharacter.deserialize(data);

				long objectId = selectCharacter.getCharacterId();
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				if(client == null) {
					System.out.println("NULL Client");
					return;
				}
				CreatureObject creature = null;
				if(getObject(objectId) == null) {
										
					creature = getCreatureFromDB(objectId);
					if(creature == null) {
						System.out.println("Cant get creature from db");
					}
					
				} else {
					
					creature = (CreatureObject) getObject(objectId);

				}

				creature.setClient(client);
				Planet planet = core.terrainService.getPlanetByID(creature.getPlanetId());
				creature.setPlanet(planet);
				client.setParent(creature);
				
				objectList.put(creature.getObjectID(), creature);
				
				creature.viewChildren(creature, true, true, new Traverser() {

					@Override
					public void process(SWGObject object) {
						objectList.put(object.getObjectID(), object);
					}
					
				});
				
				creature.viewChildren(creature, true, true, new Traverser() {

					@Override
					public void process(SWGObject object) {
						if(object.getParentId() != 0 && object.getContainer() == null)
							object.setParent(getObject(object.getParentId()));
						object.getContainerInfo(object.getTemplate());
						if(getObject(object.getObjectID()) == null)
							objectList.put(object.getObjectID(), object);
					}
					
				});				

				if(creature.getParentId() != 0) {
					SWGObject parent = getObject(creature.getParentId());
					parent._add(creature);
				}

				Point3D position = creature.getPosition();
		
				
				UnkByteFlag unkByteFlag = new UnkByteFlag();
				session.write(unkByteFlag.serialize());
				
				ParametersMessage parameters = new ParametersMessage();
				session.write(parameters.serialize());
				
				core.buffService.clearBuffs(creature);
				
				core.chatService.loadMailHeaders(client);
				
				HeartBeatMessage heartBeat = new HeartBeatMessage();
				session.write(heartBeat.serialize());

				CmdStartScene startScene = new CmdStartScene((byte) 0, objectId, creature.getPlanet().getPath(), creature.getTemplate(), position.x, position.y, position.z, System.currentTimeMillis() / 1000, 0);
				session.write(startScene.serialize());
				creature.makeAware(creature);
				
				creature.makeAware(core.guildService.getGuildObject());

				core.simulationService.handleZoneIn(client);
				
				CmdSceneReady sceneReady = new CmdSceneReady();
				client.getSession().write(sceneReady.serialize());
				
				PlayerObject ghost = (PlayerObject) creature.getSlottedObject("ghost");
				
				ChatOnGetFriendsList friendsListMessage = new ChatOnGetFriendsList(ghost);
				client.getSession().write(friendsListMessage.serialize());
				
				core.playerService.postZoneIn(creature);

			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.USE_OBJECT, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				buffer.order(ByteOrder.LITTLE_ENDIAN);
				
				CreatureObject creature = (CreatureObject) getObject(buffer.getLong());
				
				if (creature == null || creature.getClient() == null) {
					return;
				}
				
				buffer.skip(4);
				
				SWGObject object = getObject(buffer.getLong());
				
				if (object == null) {
					return;
				}
				
				useObject(creature, object);
			}
			
		});
		
	}

	public void shutdown() {
		
	}

	public void loadSnapshotObjects(Planet planet) {
		
		System.out.println("Loading client objects for: " + planet.getName());
		WorldSnapshotVisitor visitor = planet.getSnapshotVisitor();
		int counter = 0;
		for(SnapshotChunk chunk : visitor.getChunks()) {
			++counter;
			SWGObject obj = createObject(visitor.getName(chunk.nameId), chunk.id, planet, new Point3D(chunk.xPosition, chunk.yPosition, chunk.zPosition), new Quaternion(chunk.orientationW, chunk.orientationX, chunk.orientationY, chunk.orientationZ));
			if(obj != null) {
				obj.setisInSnapshot(true);
				obj.setParentId(chunk.parentId);
				if(obj instanceof CellObject) {
					((CellObject) obj).setCellNumber(chunk.cellNumber);
				}
			}
			//System.out.print("\rLoading Object [" + counter + "/" +  visitor.getChunks().size() + "] : " + visitor.getName(chunk.nameId));
        }
		visitor.dispose();
		synchronized(objectList) {
			for(SWGObject obj : objectList.values()) {
				if(obj.getParentId() != 0 && getObject(obj.getParentId()) != null) {
					SWGObject parent = getObject(obj.getParentId());
					parent.add(obj);
				}
			}
		}
		
		System.out.println("Finished loading client objects for: " + planet.getName());
		
	}
	/**
	 * Creates a child object and places it at a position and orientation offset from the parent object.
	 * @param parent The parent Object.
	 * @param template The template file of the child.
	 * @param position The position as an offset to the parent object.
	 * @param orientation The orientation as an offset to the parent object.
	 */
	public void createChildObject(SWGObject parent, String template, Point3D position, Quaternion orientation) {
		
		
		float radians = parent.getRadians();
		Point3D parentPos = parent.getWorldPosition();
		
		float x = (float) ((Math.cos(radians) * position.x) + (Math.sin(radians) * position.z));
		float y = position.y + parentPos.y;
		float z = (float) ((Math.cos(radians) * position.z) - (Math.sin(radians) * position.x));
		
		x += parentPos.x;
		z += parentPos.z;
		
		position = new Point3D(x, y, z);
		orientation = MathUtilities.rotateQuaternion(orientation, radians, new Point3D(0, 1, 0));
		SWGObject child = createObject(template, 0, parent.getPlanet(), position, orientation);
		
		core.simulationService.add(child, x, z);
		
	}
	
	public void createChildObject(SWGObject parent, String template, float x, float y, float z, float qy, float qw) {
		createChildObject(parent, template, new Point3D(x, y, z), new Quaternion(qw, 0, qy, 0));
	}

	
}
