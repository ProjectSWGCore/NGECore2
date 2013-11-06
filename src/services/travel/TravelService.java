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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.PlanetTravelPointListRequest;
import protocol.swg.PlanetTravelPointListResponse;
import engine.clients.Client;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.quadtree.QuadTree;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import main.NGECore;
import resources.common.Opcodes;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import services.map.*;
import services.sui.SUIService.ListBoxType;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import services.sui.SUIWindow;

public class TravelService implements INetworkDispatch {
	
	private NGECore core;
	private Map<Planet, Vector<TravelPoint>> travelMap = new ConcurrentHashMap<Planet, Vector<TravelPoint>>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
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
						break;
						
					}
				}
			}
		});
		
	}
	
	public TravelPoint getNearestTravelPoint() {
		TravelPoint returnedPoint = null;
		
		
		return returnedPoint;
	}
	
	public TravelPoint getTravelPointByName(String planet, String tpName) {
		Planet planetObj = core.terrainService.getPlanetByName(planet.toLowerCase());
		
		Vector<TravelPoint> tpMap = travelMap.get(planetObj);
		
		TravelPoint returnedPoint = null;
		
		for (TravelPoint tp : tpMap) {
			if (tp.getName().equals(tpName)) {
				returnedPoint = tp;
			}
		}
		if (returnedPoint == null) { System.out.println("Couldn't find a travelpoint w/ name " + tpName); }
		return returnedPoint;
	}
	
	public void addPlanet(Planet planet) {
		Vector<TravelPoint> travelPointVector = new Vector<TravelPoint>();
		travelMap.put(planet, travelPointVector);
		core.scriptService.callScript("scripts/", "addPoints", "static_travel_points", core, planet);
	}

	public void addShuttleToPoint(SWGObject obj) {
		Vector<TravelPoint> travelPoints = travelMap.get(obj.getPlanet());
		
		for (TravelPoint tp : travelPoints) {
			float distancePoint = tp.getLocation().getDistance2D(obj.getPosition());
			System.out.println("Distance from point " + tp.getName() + ": " + distancePoint);
			
			// Travel points must be within 50 meters of shuttles!
			if (distancePoint <= (float) 50) {
				tp.setShuttle((ShuttleObject) obj);
			}
			
		}
	}
	
	public void addTravelPoint(Planet planet, String name, float x, float y, float z) {
		// z should be y for 2d map
		TravelPoint travelPoint = new TravelPoint(name, x, y, z, 100);

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
	
	public void removeTravelPointByName(Planet planet, String name){
		if (travelMap.containsKey(planet)) {
			Vector<TravelPoint> travelVector = travelMap.get(planet);

			for(TravelPoint point : travelVector) {
				if (point.getName() == name) {
					travelMap.get(planet).removeElement(point);
					break;
				}
			}
		}
	}
	
	public void loadTravelPoints() {
		List<Planet> planetList = core.terrainService.getPlanetList();
		for (Planet planet : planetList) {
			addPlanet(planet);
		}
	}
	
	public SWGObject createTravelTicket(String departurePlanet, String departureLoc, String arrivalPlanet, String arrivalLoc) {
		
		Planet planet = core.terrainService.getPlanetByName(departurePlanet.toLowerCase());
		SWGObject travelTicket = core.objectService.createObject("object/tangible/travel/travel_ticket/base/shared_base_travel_ticket.iff", planet);

		travelTicket.setStringAttribute("@obj_attr_n:travel_departure_planet", WordUtils.capitalize(departurePlanet));
		travelTicket.setStringAttribute("@obj_attr_n:travel_departure_point", departureLoc);
		
		travelTicket.setStringAttribute("@obj_attr_n:travel_arrival_planet", WordUtils.capitalize(arrivalPlanet));
		travelTicket.setStringAttribute("@obj_attr_n:travel_arrival_point", arrivalLoc);
		
		travelTicket.setAttachment("objType", "ticket");
		System.out.println("Object type: " + travelTicket.getAttachment("objType"));
		
		return travelTicket;
	}
	
	public void purchaseTravelTicket(SWGObject player, String departurePlanet, String departureLoc, String arrivalPlanet, String arrivalLoc) {
		CreatureObject creatureObj = (CreatureObject) player;
		
		SWGObject travelTicket = createTravelTicket(departurePlanet, departureLoc, arrivalPlanet, arrivalLoc);
		
		creatureObj.getSlottedObject("inventory").add(travelTicket);
		travelTicket.isSubChildOf(player);
		
		creatureObj.sendSystemMessage("@travel:ticket_purchase_complete", (byte) 0);
		System.out.println("Created travel ticket: " + departurePlanet + " " + departureLoc + " " + arrivalPlanet + " " + arrivalLoc);
	}
	
	// This returns all ticket objects in a players inventory
	public Vector<SWGObject> getTicketList(SWGObject player) {

		final Vector<SWGObject> ticketList = new Vector<SWGObject>();

		TangibleObject playerInventory = (TangibleObject) player.getSlottedObject("inventory");

		playerInventory.viewChildren(player, false, false, new Traverser() {
			//                              ^-topDown-^-recursive
			// topDown - If true, objects that are at the top of the tree are viewed first.
			// 	the "viewer" needs to be whatever object wants to know this information (player)
			@Override
			public void process(SWGObject obj) {
				if (obj.getAttachment("objType") != null) {
					String objType = (String) obj.getAttachment("objType");
					String ticket = "ticket";
					if (objType.equals(ticket)) {
						ticketList.add(obj);
					}
				}
			}
		});
		return ticketList;
	}
	
	// TravelPoint = the travelpoint player is going to travel to!
	public void doTransport(SWGObject actor, TravelPoint tp) {
		
		if (tp.getPlanetName().toLowerCase().equals(actor.getPlanet().name)) { 
			core.simulationService.teleport(actor, tp.getSpawnLocation().getPosition(), tp.getSpawnLocation().getOrientation(), 0); 
		} 
		
		else { core.simulationService.transferToPlanet(actor, core.terrainService.getPlanetByName(tp.getPlanetName()), tp.getSpawnLocation().getPosition(), tp.getSpawnLocation().getOrientation(), actor); }
	}

	
	public void sendTicketWindow(final CreatureObject creature) {
		
		if (creature == null)
			return;
		
		Map<Long, String> ticketData = new HashMap<Long, String>();
		Vector<SWGObject> invTickets = getTicketList(creature);
		
		for (SWGObject ticket : invTickets) {
			ticketData.put(ticket.getObjectId(), ticket.getStringAttribute("@obj_attr_n:travel_arrival_planet") + " -- " 
					+ ticket.getStringAttribute("@obj_attr_n:travel_arrival_point"));
		}
		
		final SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "Select Destination", "Select Destination", ticketData, creature, null, 0);
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {

			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {
				// return list = int number of selected row
				int index = Integer.parseInt(returnList.get(0));
				
				SWGObject selectedTicket = core.objectService.getObject(window.getObjectIdByIndex(index));
				System.out.println("Selected ticket obj: " + window.getObjectIdByIndex(index));
				
				TravelPoint arrivalPoint = getTravelPointByName(selectedTicket.getStringAttribute("@obj_attr_n:travel_arrival_planet").toLowerCase(), 
						selectedTicket.getStringAttribute("@obj_attr_n:travel_arrival_point"));
				
				TravelPoint departurePoint = getTravelPointByName(creature.getPlanet().name, selectedTicket.getStringAttribute("@obj_attr_n:travel_departure_point"));
				
				if (departurePoint.getShuttle() == null) {
					System.out.println("TravelPoint has no associated shuttle!");
					return;
				}
				
				if (departurePoint.getShuttle().isInPort()) {
					selectedTicket.getContainer().remove(selectedTicket);
					core.objectService.destroyObject(selectedTicket);
					doTransport(creature, arrivalPoint);
				}
				else {
					creature.sendSystemMessage("The next shuttle arrives in " + departurePoint.getShuttle().getNextShuttleTime() + " seconds.", (byte) 0);
				}
			}
		});
		
		core.suiService.openSUIWindow(window);
		
	}
	
	@Override
	public void shutdown() {
		
	}
}
