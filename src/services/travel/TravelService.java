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
package services.travel;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.PlanetTravelPointListRequest;
import protocol.swg.PlanetTravelPointListResponse;
import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import main.NGECore;
import resources.common.Opcodes;
import services.map.*;

public class TravelService implements INetworkDispatch {
	
	private NGECore core;
	
	private Map<Planet, Vector<TravelPoint>> travelMap = new ConcurrentHashMap<Planet, Vector<TravelPoint>>();
	
	public TravelService(NGECore core) {
		this.core = core;
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		swgOpcodes.put(Opcodes.PlanetTravelPointListRequest, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				PlanetTravelPointListRequest request = new PlanetTravelPointListRequest();
				request.deserialize(data);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));

				if(client == null || client.getSession() == null)
					return;

				SWGObject object = client.getParent();

				if(object == null)
					return;
				
				List<Planet> planetList = core.terrainService.getPlanetList();
				
				for (Planet planet : planetList) {
					String planetString = planet.getName();
					
					if (request.getPlanet().equals(planetString)) {
						
						PlanetTravelPointListResponse response = new PlanetTravelPointListResponse(planetString, travelMap.get(planet), planetList);
						client.getSession().write(response.serialize());
						//System.out.println("Sent the list for the planet " + planet.getName());
						
					}
				}
				//PlanetTravelPointListResponse response = new PlanetTravelPointListResponse(request.getPlanet(), travelMap.get(request.getPlanet()));
				//client.getSession().write(response.serialize());
			}
			
		});
		
	}
	
	public void addPlanet(Planet planet) {
		Vector<TravelPoint> travelPointVector = new Vector<TravelPoint>();
		travelMap.put(planet, travelPointVector);
		core.scriptService.callScript("scripts/", "addPoints", "static_travel_points", core, planet);
	}


	public void addTravelPoint(Planet planet, String name, float x, float y) {
		TravelPoint travelPoint = new TravelPoint(name, x, y, 0, 100);
		
		travelPoint.setPlanetName(WordUtils.capitalize(planet.getName()));
		
		if (travelMap.containsKey(planet)) {
			
			travelMap.get(planet).add(travelPoint);
			System.out.println("Added travel point " + travelPoint.getName());
			
		} else {
			Vector<TravelPoint> travelPointVector = new Vector<TravelPoint>();
			travelMap.put(planet, travelPointVector);
			travelMap.get(planet).add(travelPoint);
			System.out.println("Added planet " + planet.getName() + " to TravelMap.");
			System.out.println("Added travel point " + travelPoint.getName());
		}
		
		
	}
	
	public void loadTravelPoints() {
		List<Planet> planetList = core.terrainService.getPlanetList();
		for (Planet planet : planetList) {
			addPlanet(planet);
		}
	}
	
	public SWGObject createTravelTicket(TravelPoint departurePoint, TravelPoint arrivalPoint, Planet planet) {
		
		SWGObject travelTicket = core.objectService.createObject("object/tangible/travel/travel_ticket/base/shared_base_travel_ticket.iff", planet);

		travelTicket.setStringAttribute("@obj_attr_n:travel_departure_planet", departurePoint.getPlanetName());
		travelTicket.setStringAttribute("@obj_attr_n:travel_departure_point", departurePoint.getName());
		
		travelTicket.setStringAttribute("@obj_attr_n:travel_arrival_planet", arrivalPoint.getPlanetName());
		travelTicket.setStringAttribute("@obj_attr_n:travel_arrival_point", arrivalPoint.getName());
		
		return travelTicket;
	}
	
	public void addTicketToPlayer(SWGObject player, SWGObject travelTicket) {
		SWGObject playerInventory = player.getSlottedObject("inventory");
		playerInventory._add(travelTicket);
	}
	
	@Override
	public void shutdown() {
		
	}
}
