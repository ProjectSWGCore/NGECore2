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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import resources.common.*;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.python.core.Py;
import org.python.core.PyObject;

import com.sleepycat.persist.EntityCursor;

import protocol.swg.CmdSceneReady;
import protocol.swg.CmdStartScene;
import protocol.swg.HeartBeatMessage;
import protocol.swg.ParametersMessage;
import protocol.swg.SelectCharacter;
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
import resources.objects.player.PlayerObject;
import resources.objects.staticobject.StaticObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;

@SuppressWarnings("unused")

public class ObjectService implements INetworkDispatch {

	private List<SWGObject> objectList = Collections.synchronizedList(new ArrayList<SWGObject>());
	
	private NGECore core;
	
	private DatabaseConnection databaseConnection;

	private AtomicLong highestId = new AtomicLong();
	
	private Random random = new Random();
	
	private Map<String, PyObject> serverTemplates = new ConcurrentHashMap<String, PyObject>();
	
	public ObjectService(final NGECore core) {
		this.core = core;
		databaseConnection = core.getDatabase1();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
		    public void run() {
		    	synchronized(objectList) {
		    		for(SWGObject obj : objectList) {
		    			
		    			if(obj.getSlottedObject("ghost") != null) {
		    				((CreatureObject) obj).createTransaction(core.getCreatureODB().getEnvironment());
		    				core.getCreatureODB().put((CreatureObject) obj, Long.class, CreatureObject.class, ((CreatureObject) obj).getTransaction());
		    			}
		    			
		    		}
		    	}
		    }
		});
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
			
		} else {
			
			return null;
			
		}
		object.setPlanetId(planet.getID());

		String serverTemplate = Template.replace(".iff", "");
		// check if template is empty(4 default lines) to reduce RAM usage(saves about 500 MB of RAM)
		try {
			int numberOfLines = FileUtilities.getNumberOfLines("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", "") + serverTemplate.split("shared_" , 2)[1] + ".py");
			
			if(numberOfLines > 4) {
				if(serverTemplates.containsKey(Template)) {
					PyObject func = serverTemplates.get(Template);
					func.__call__(Py.java2py(core), Py.java2py(object));
				} else {
					PyObject func = core.scriptService.getMethod("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", ""), serverTemplate.split("shared_" , 2)[1], "setup");
					func.__call__(Py.java2py(core), Py.java2py(object));
					serverTemplates.put(Template, func);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//core.scriptService.callScript("scripts/" + serverTemplate.split("shared_" , 2)[0].replace("shared_", ""), "setup", serverTemplate.split("shared_" , 2)[1], core, object);
		
		objectList.add(object);

		return object;
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
				
		SWGObject object = null;
		
		synchronized(objectList) {
			
			Iterator<SWGObject> it = objectList.iterator();
					
			while(it.hasNext()) {
						
				SWGObject swgObject = it.next();
				if(swgObject.getObjectID() == objectID) {
						object = swgObject;
						break;
				}
			}
		}
			
		return object;
		
	}
	
	public List<SWGObject> getObjectList() { return objectList; }
	
	public void destroyObject(SWGObject object) {
		
		object.viewChildren(object, true, true, new Traverser() {
			@Override
			public void process(SWGObject obj) {
				objectList.remove(obj);
			}
		});
		objectList.remove(object);
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
			
			Iterator<SWGObject> it = objectList.iterator();
					
			while(it.hasNext()) {
				SWGObject obj = it.next();
				if(obj.getCustomName() == null)
					continue;
				if(obj.getCustomName().equals(customName))
					return it.next();
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
		Random random = new Random();
		
		long objectID = random.nextLong();
		
		if(getObject(objectID) != null)
			return generateObjectID();
		
		if(core.getCreatureODB().contains(new Long(objectID), Long.class, CreatureObject.class))
			return generateObjectID();

		return objectID;
		
		/*long newId = highestId.incrementAndGet();
		PreparedStatement ps2;

		try {
			ps2 = databaseConnection.preparedStatement("UPDATE highestid SET id=" + newId + " WHERE id=" + highestId.get());
			ps2.executeUpdate();
			highestId.set(newId);
			ps2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(getObject(highestId.longValue()) == null)
			return highestId.longValue();
		else
			return generateObjectID();*/
		

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
				if(client == null)
					return;
				CreatureObject creature = null;
				if(getObject(objectId) == null) {
					
					if(getCreatureFromDB(objectId) == null)
						return;
					
					creature = getCreatureFromDB(objectId);
					
				} else {
					
					creature = (CreatureObject) getObject(objectId);

				}

				creature.setClient(client);
				Planet planet = core.terrainService.getPlanetByID(creature.getPlanetId());
				creature.setPlanet(planet);
				client.setParent(creature);
				
				objectList.add(creature);
				creature.viewChildren(creature, true, true, new Traverser() {

					@Override
					public void process(SWGObject object) {
						//System.out.println(object.getTemplate());
						objectList.add(object);
					}
					
				});
				
				creature.viewChildren(creature, true, true, new Traverser() {

					@Override
					public void process(SWGObject object) {
						if(object.getParentId() != 0 && object.getContainer() == null)
							object.setParent(getObject(object.getParentId()));
						object.getContainerInfo(object.getTemplate());
					}
					
				});
				if(creature.getParentId() != 0) {
					SWGObject parent = getObject(creature.getParentId());
					parent._add(creature);
				}

				Point3D position = creature.getWorldPosition();
		
				
				//UnkByteFlag unkByteFlag = new UnkByteFlag();
				//session.write(unkByteFlag.serialize());
				
				//ParametersMessage parameters = new ParametersMessage();
				//session.write(parameters.serialize());

				core.chatService.loadMailHeaders(client);
				
				HeartBeatMessage heartBeat = new HeartBeatMessage();
				session.write(heartBeat.serialize());

				CmdStartScene startScene = new CmdStartScene((byte) 0, objectId, creature.getPlanet().getPath(), creature.getTemplate(), position.x, position.y, position.z, System.currentTimeMillis() / 1000, creature.getRadians());
				session.write(startScene.serialize());
				
				core.simulationService.handleZoneIn(client);
				creature.makeAware(creature);
				//CmdSceneReady cmdSceneReady = new CmdSceneReady();
				//session.write(cmdSceneReady.serialize());

				//core.simulationService.teleport(creature, new Point3D(position.x, core.terrainService.getHeight(creature.getPlanetId(), position.x, position.z), position.z), creature.getOrientation());
			}
			
		});
		
	}

	public void shutdown() {
		
	}

	public void loadSnapshotObjects(Planet planet) {
		
		WorldSnapshotVisitor visitor = planet.getSnapshotVisitor();
		int counter = 0;
		for(SnapshotChunk chunk : visitor.getChunks()) {
			++counter;
			SWGObject obj = createObject(visitor.getName(chunk.nameId), chunk.id, planet, new Point3D(chunk.xPosition, chunk.yPosition, chunk.zPosition), new Quaternion(chunk.orientationW, chunk.orientationX, chunk.orientationY, chunk.orientationZ));
			if(obj != null) {
				obj.setisInSnapshot(true);
				obj.setParentId(chunk.parentId);
			}
			//System.out.print("\rLoading Object [" + counter + "/" +  visitor.getChunks().size() + "] : " + visitor.getName(chunk.nameId));
        }
		visitor.dispose();
		synchronized(objectList) {
			for(SWGObject obj : objectList) {
				obj.getTemplateData().dispose();
				if(obj.getParentId() != 0 && getObject(obj.getParentId()) != null) {
					SWGObject parent = getObject(obj.getParentId());
					parent.add(obj);
				}
			}
		}
		
	}
	
}
