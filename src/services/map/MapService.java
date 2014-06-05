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
package services.map;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.GetMapLocationsMessage;
import protocol.swg.GetMapLocationsResponseMessage;
import resources.common.Opcodes;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Grid2D;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

import main.NGECore;


@SuppressWarnings("unused")

public class MapService implements INetworkDispatch {
		
	private NGECore core;
	private Map<Planet, Vector<MapLocation>> locationMap = new ConcurrentHashMap<Planet, Vector<MapLocation>>();
	private AtomicLong nextId = new AtomicLong(0);
	
	public MapService(NGECore core) {
		this.core = core;
	}
	
	public void insertOpcodes(Map<Integer,INetworkRemoteEvent> swgOpcodes, Map<Integer,INetworkRemoteEvent> objControllerOpcodes) {
		
		swgOpcodes.put(Opcodes.GetMapLocationsMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);

				GetMapLocationsMessage getMapLocations = new GetMapLocationsMessage();
				getMapLocations.deserialize(data);

				/*Planet planet = core.terrainService.getPlanetByName(getMapLocations.getPlanet());
				
				if(planet == null)
					return;*/
				
				Client client = core.getClient(session);

				if(client == null || client.getSession() == null)
					return;

				SWGObject object = client.getParent();

				if(object == null)
					return;


				GetMapLocationsResponseMessage response = new GetMapLocationsResponseMessage(getMapLocations.getPlanet(), locationMap.get(object.getPlanet()));
				session.write(response.serialize());
				
			}
			
		});
		
	}

	public void shutdown() {
		
	}
	
	public void addPlanet(Planet planet) {
		locationMap.put(planet, new Vector<MapLocation>());
		
		core.scriptService.callScript("scripts/", "static_map_locations", "addLocations", core, planet);
	}
	
	public void addLocation(Planet planet, String name, float x, float y, byte category, byte subcategory, byte active) {
		
		MapLocation location = new MapLocation(planet, nextId.incrementAndGet(), name, x, y, category, subcategory, active);
		locationMap.get(planet).add(location);
		
	}
	
	public String getClosestCityName(SWGObject object) {
		
		if(object.getPlanet() == null)
			object.setPlanet(core.terrainService.getPlanetByID(object.getPlanetId()));
		
		Vector<MapLocation> locations = locationMap.get(object.getPlanet());
		float closestDistance = Float.MAX_VALUE;
		String closestName = "";
		Point3D objPos = object.getWorldPosition();
		
		for(MapLocation location : locations) {
			
			if(location.getCategory() == 17) {
				Point3D position = new Point3D(location.getX(), 0, location.getY());
				if(position.getDistance2D(objPos) < closestDistance) {
					closestDistance = position.getDistance2D(objPos);
					closestName = location.getName();
				}
			}
			
		}

		return closestName;
		
	}
	
	public Vector<MapLocation> getPlanetMapLocations(Planet planet) {
		return locationMap.get(planet);
	}
	
}
