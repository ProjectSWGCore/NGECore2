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
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import main.NGECore;
import resources.common.Console;
import resources.common.Opcodes;
import resources.common.SpawnPoint;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import services.sui.SUIService.ListBoxType;
import services.sui.SUIService.MessageBoxType;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import services.sui.SUIWindow;

public class TravelService implements INetworkDispatch {
	
	private NGECore core;
	private Map<Planet, Vector<TravelPoint>> travelMap = new ConcurrentHashMap<Planet, Vector<TravelPoint>>();
	private Map<Planet, Map<String, Integer>> fareMap = new ConcurrentHashMap<Planet, Map<String, Integer>>();
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
				
				Client client = core.getClient(session);

				if(client == null || client.getSession() == null)
					return;

				SWGObject object = client.getParent();

				if(object == null)
					return;
				
				TravelPoint travelPoint = getNearestTravelPoint(object);
				
				if(travelPoint == null)
					return;
				
				if(travelPoint.isStarport()) {
				
					List<Planet> planetList = core.terrainService.getPlanetList();
					
					for (Planet planet : planetList) {
						String planetString = planet.getName();
						
						if (request.getPlanet().equals(planetString)) {
							
							Vector<TravelPoint> correctTravelPoints = new Vector<TravelPoint>();
							
							for(TravelPoint tp : travelMap.get(planet)) {
								if(tp.isStarport() || tp.getPlanetName().equalsIgnoreCase(object.getPlanet().getName())) {
									correctTravelPoints.add(tp);
								}
							}
							PlanetTravelPointListResponse response = new PlanetTravelPointListResponse(planetString, correctTravelPoints);
							client.getSession().write(response.serialize());
							
							break;
							
						} 
					}
				
				} else {
					
					Planet planet = object.getPlanet();
					
					PlanetTravelPointListResponse response = new PlanetTravelPointListResponse(planet.getName(), travelMap.get(planet));
					client.getSession().write(response.serialize());
					
				}
				
			}
		});
		
	}
	
	public TravelPoint getNearestTravelPoint(SWGObject obj, int distance) {
		TravelPoint returnedPoint = null;
		Vector<TravelPoint> planetTp = travelMap.get(obj.getPlanet());

		synchronized(travelMap) {
			for (TravelPoint tp : planetTp) {
				//System.out.println("Distance for point " + tp.getName() + " is " + tp.getLocation().getDistance2D(obj.getWorldPosition()));
				if (tp.getLocation().getDistance2D(obj.getWorldPosition()) <= distance) {
					if (returnedPoint != null) {
						float returnPointDistance = returnedPoint.getLocation().getDistance2D(obj.getWorldPosition());
						float tpDistance = tp.getLocation().getDistance2D(obj.getWorldPosition());
						//Console.println("Current point: " + returnedPoint.getName());
						//Console.println("Return point distance: " + returnPointDistance + " for " + returnedPoint.getName());
						//Console.println("tp point distance: " + tpDistance + " for " + tp.getName());
						if (tpDistance < returnPointDistance) {
							returnedPoint = tp;
						}
					} else {
						returnedPoint = tp;
					}
				}
			}
		}
		// Some transport obj's were used as deco (ex: ACLO bunker on Talus shared_player_transport). A null check should be performed before tp is used.
		/*if (returnedPoint == null) {
			//System.out.println("Could not find travel point for " + obj.getTemplate() + " at " + obj.getPlanet().name + " Position: " + obj.getPosition().x + " " + obj.getPosition().z);
			//Console.println("Travel Point NULL: " + returnedPoint.getName());
		}*/

		return returnedPoint;
	}
	
	public TravelPoint getNearestTravelPoint(SWGObject obj) {
		return getNearestTravelPoint(obj, 125);
	}
	
	public TravelPoint getTravelPointByName(String planet, String tpName) {
		Planet planetObj = core.terrainService.getPlanetByName(planet.toLowerCase());
		
		Vector<TravelPoint> tpMap = travelMap.get(planetObj);
		
		TravelPoint returnedPoint = null;
		
		synchronized(travelMap) {
			for (TravelPoint tp : tpMap) {
				if (tp.getName().equals(tpName)) {
					returnedPoint = tp;
				}
			}
		}
		
		if (returnedPoint == null) { System.out.println("Couldn't find a travelpoint w/ name " + tpName); }
		return returnedPoint;
	}
	
	public void addPlanet(Planet planet) {
		Vector<TravelPoint> travelPointVector = new Vector<TravelPoint>();
		travelMap.put(planet, travelPointVector);
		core.scriptService.callScript("scripts/", "static_travel_points", "addPoints", core, planet);
	}

	public void addTravelPoint(Planet planet, String name, float x, float y, float z) {
		// z should be y for 2d map
		TravelPoint travelPoint = new TravelPoint(name, x, y, z, 150);

		travelPoint.setPlanetName(WordUtils.capitalize(planet.getName()));
		
		synchronized(travelMap) {
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
		planetList.forEach(this::addPlanet);
		populateTravelFares();
	}
	
	public SWGObject createTravelTicket(String departurePlanet, String departureLoc, String arrivalPlanet, String arrivalLoc) {
		
		Planet planet = core.terrainService.getPlanetByName(departurePlanet.toLowerCase());
		SWGObject travelTicket = core.objectService.createObject("object/tangible/travel/travel_ticket/base/shared_base_travel_ticket.iff", planet);
		
		travelTicket.setStringAttribute("@obj_attr_n:travel_departure_planet", WordUtils.capitalize(departurePlanet));
		travelTicket.setStringAttribute("@obj_attr_n:travel_departure_point", departureLoc);
		
		travelTicket.setStringAttribute("@obj_attr_n:travel_arrival_planet", WordUtils.capitalize(arrivalPlanet));
		travelTicket.setStringAttribute("@obj_attr_n:travel_arrival_point", arrivalLoc);
		
		travelTicket.setAttachment("objType", "ticket");
		
		return travelTicket;
	}
	
	public void purchaseTravelTicket(SWGObject player, String departurePlanet, String departureLoc, String arrivalPlanet, String arrivalLoc, String roundTrip) {
		CreatureObject creatureObj = (CreatureObject) player;
		int fare = fareMap.get(player.getPlanet()).get(arrivalPlanet);
		if (roundTrip.equals("1")) {
			fare += fareMap.get(player.getPlanet()).get(arrivalPlanet);
		}
		int playerBankCredits = creatureObj.getBankCredits();
		if (playerBankCredits < fare) {
			int cashDifference = fare - playerBankCredits;
			
			if (cashDifference > creatureObj.getCashCredits()) {
				SUIWindow window = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_OK, "STAR WARS GALAXIES", "You do not have enough to purchase that."
						, player, null, 0);
				core.suiService.openSUIWindow(window);
				return;
			}
			
			creatureObj.setBankCredits(0);
			creatureObj.setCashCredits(creatureObj.getCashCredits() - cashDifference);
		} else {
			creatureObj.setBankCredits(playerBankCredits - fare);
		}
		
		if (roundTrip.equals("1")) {
			SWGObject tripTicket = createTravelTicket(arrivalPlanet, arrivalLoc, departurePlanet, departureLoc);
			creatureObj.getSlottedObject("inventory").add(tripTicket);
		}
		
		SWGObject travelTicket = createTravelTicket(departurePlanet, departureLoc, arrivalPlanet, arrivalLoc);
		
		creatureObj.getSlottedObject("inventory").add(travelTicket);
		//Console.println("Total cost: " + fare);
		SUIWindow window = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_OK, "STAR WARS GALAXIES", "@travel:ticket_purchase_complete", player, null, 0);
		core.suiService.openSUIWindow(window);

		creatureObj.sendSystemMessage("You successfully make a payment of " + fare + " credits to the Galactic Travel Commission.", (byte) 0);
	}
	
	// This returns all ticket objects in a players inventory
	public Vector<SWGObject> getTicketList(SWGObject player) {

		final Vector<SWGObject> ticketList = new Vector<SWGObject>();
		final TravelPoint nearestPoint = getNearestTravelPoint(player);
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
					//System.out.println("Nearest Point: " + nearestPoint.getName());
					if (objType.equals(ticket) && obj.getStringAttribute("@obj_attr_n:travel_departure_point").equals(nearestPoint.getName())) {
						ticketList.add(obj);
					}
				}
			}
		});
		return ticketList;
	}
	
	// TravelPoint = the travelpoint player is going to travel to!
	public void doTransport(SWGObject actor, TravelPoint tp) {
		
		Planet planet = core.terrainService.getPlanetByName(tp.getPlanetName());
		Point3D spawnLocation = SpawnPoint.getRandomPosition(tp.getShuttle().getPosition(), 5, 20, actor.getPlanetId());
		core.simulationService.transferToPlanet(actor, planet, spawnLocation, new Quaternion(0, 0, 0, 0), null);
		
	}

	
	public void sendTicketWindow(final CreatureObject creature, SWGObject rangeObject) {
		
		if (creature == null)
			return;
		
		Map<Long, String> ticketData = new HashMap<Long, String>();
		Vector<SWGObject> invTickets = getTicketList(creature);
		
		for (SWGObject ticket : invTickets) {
			ticketData.put(ticket.getObjectId(), ticket.getStringAttribute("@obj_attr_n:travel_arrival_planet") + " -- " 
					+ ticket.getStringAttribute("@obj_attr_n:travel_arrival_point"));
		}
		
		final SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "Select Destination", "Select Destination", ticketData, creature, rangeObject, 10);
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {

			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {
				// return list = int number of selected row
				int index = Integer.parseInt(returnList.get(0));
				
				SWGObject selectedTicket = core.objectService.getObject(window.getObjectIdByIndex(index));
				
				TravelPoint arrivalPoint = getTravelPointByName(selectedTicket.getStringAttribute("@obj_attr_n:travel_arrival_planet").toLowerCase(), 
						selectedTicket.getStringAttribute("@obj_attr_n:travel_arrival_point"));
				
				TravelPoint departurePoint = getNearestTravelPoint(creature);
				
				if (departurePoint.isShuttleAvailable() == true) {
					selectedTicket.getContainer().remove(selectedTicket);
					core.objectService.destroyObject(selectedTicket);
					doTransport(creature, arrivalPoint);
				} else {
					creature.sendSystemMessage("@travel:shuttle_not_available ", (byte) 0);
				}
			}
		});
		
		core.suiService.openSUIWindow(window);
		
	}
	
	private void populateTravelFares() {
		try {
			DatatableVisitor travelFares = ClientFileManager.loadFile("datatables/travel/travel.iff", DatatableVisitor.class);

			for (int f = 0; f < travelFares.getRowCount(); f++) {
				Planet planet = core.terrainService.getPlanetByName((String) travelFares.getObject(f, 0));
				if (travelMap.containsKey(planet)) {
					int corellia = ((int) travelFares.getObject(f, 1));
					int dantooine = ((int) travelFares.getObject(f, 2));
					int dathomir = ((int) travelFares.getObject(f, 3));
					int endor = ((int) travelFares.getObject(f, 4));
					int lok = ((int) travelFares.getObject(f, 5));
					int naboo = ((int) travelFares.getObject(f, 6));
					int rori = ((int) travelFares.getObject(f, 7));
					int talus = ((int) travelFares.getObject(f, 8));
					int tatooine = ((int) travelFares.getObject(f, 9));
					int yavin4 = ((int) travelFares.getObject(f, 10));
					int mustafar = ((int) travelFares.getObject(f, 11));
					int kashyyyk_main = ((int) travelFares.getObject(f, 12));
					
					Map<String, Integer> planetFares = new ConcurrentHashMap<String, Integer>();
					
					planetFares.put("corellia", corellia);
					planetFares.put("dantooine", dantooine);
					planetFares.put("dathomir", dathomir);
					planetFares.put("endor", endor);
					planetFares.put("lok", lok);
					planetFares.put("naboo", naboo);
					planetFares.put("rori", rori);
					planetFares.put("talus", talus);
					planetFares.put("tatooine", tatooine);
					planetFares.put("yavin4", yavin4);
					planetFares.put("mustafar", mustafar);
					planetFares.put("kashyyyk_main", kashyyyk_main);
					
					fareMap.put(planet, planetFares);
					Console.println("Loaded the travel fares for " + planet.getName());
				}
			}
		}
		catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Planet, Vector<TravelPoint>> getTravelMap() {
		return this.travelMap;
	}
	
	@Override
	public void shutdown() {
		
	}
}
