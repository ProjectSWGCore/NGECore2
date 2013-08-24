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
package services.gcw;

import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.python.google.common.collect.ArrayListMultimap;
import org.python.google.common.collect.Multimap;

import protocol.swg.GcwGroupsRsp;
import protocol.swg.GcwRegionsReq;
import protocol.swg.GcwRegionsRsp;

import resources.common.FactionStatus;
import resources.common.Opcodes;
import resources.gcw.CurrentServerGCWZoneHistory;
import resources.gcw.CurrentServerGCWZonePercent;
import resources.gcw.OtherServerGCWZonePercent;
import resources.objects.creature.CreatureObject;
import resources.objects.guild.GuildObject;

import main.NGECore;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point2D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class GCWService implements INetworkDispatch {
	
	private NGECore core;
	private GuildObject object;
	private Map<String, Map<String, CurrentServerGCWZonePercent>> zoneMap() { return object.getZoneMap(); }
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	protected final Object objectMutex = new Object();
	
	public GCWService(final NGECore core) {
		this.core = core;
		this.object = this.core.guildService.getGuildObject();
		
		scheduler.schedule(new Runnable() {
			@Override public void run() { afterInitialisation(); }
			
			private void afterInitialisation() {
				try {
					core.scriptService.callScript("scripts/", "gcwzones", "addZones");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, 1, TimeUnit.SECONDS);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { checkFactionalPresence(); }
			
			private void checkFactionalPresence() {
				try {
					List<SWGObject> flagged = getSFPlayers();
					
					for (SWGObject player : flagged) {
						if (player.getParentId() == 0) {
							if (!core.terrainService.isWater(player.getPlanetId(), player.getPosition().x, player.getPosition().z)) {
								String planet = player.getPlanet().getName();
								boolean inContestedRegion = false;
								
								if (zoneMap().containsKey(planet)) {
									for (String zoneName : zoneMap().get(planet).keySet()) {
										CurrentServerGCWZonePercent zone = zoneMap().get(planet).get(zoneName).clone();
										
										if (zone.getRadius() > 0) {
											List<SWGObject> objects = core.simulationService.get(core.terrainService.getPlanetByName(planet), zone.getPosition().x, zone.getPosition().z, (int) zone.getRadius());
											
											if (objects.contains(player)) {
												inContestedRegion = true;
												
												if ((player instanceof CreatureObject)) {
													if (((CreatureObject) player).getFactionStatus() == FactionStatus.SpecialForces) {
														adjustZone(planet, zoneName, ((CreatureObject) player).getFaction(), 1);
														break;
													}
												}
											}
										}
									}
								}
								
								if (!inContestedRegion) {
									if ((player instanceof CreatureObject)) {
										if (((CreatureObject) player).getFactionStatus() == FactionStatus.SpecialForces) {
											String zone = "";
											
											for (Entry<String, CurrentServerGCWZonePercent> entry : zoneMap().get(planet).entrySet()) {
												if (entry.getValue().getType() == 1) {
													zone = entry.getKey();
													break;
												}
											}
											
											if (zone.length() != 0) {
												adjustZone(planet, zone, ((CreatureObject) player).getFaction(), 1);
											}
										}
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 5, 10, TimeUnit.SECONDS);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { updateGCWZones(); }
			
			private void updateGCWZones() {
				try {
					Map<String, CurrentServerGCWZonePercent> zoneUpdates = new TreeMap<String, CurrentServerGCWZonePercent>();
					Map<String, CurrentServerGCWZonePercent> totalUpdates = new TreeMap<String, CurrentServerGCWZonePercent>();
					Multimap<String, CurrentServerGCWZoneHistory> zoneHistoryUpdates = ArrayListMultimap.create();
					Multimap<String, CurrentServerGCWZoneHistory> totalHistoryUpdates = ArrayListMultimap.create();
					
					BigDecimal galaxyPercent = new BigDecimal("0.0", MathContext.DECIMAL128);
					
					for (String planet : zoneMap().keySet()) {
						if (!planet.equals("galaxy")) {
							BigDecimal planetPercent = new BigDecimal("0.0", MathContext.DECIMAL128);
							
							for (String zone : zoneMap().get(planet).keySet()) {
								BigDecimal zonePercent;
								
								zonePercent = zoneMap().get(planet).get(zone).getPercent();
								zonePercent = zonePercent.multiply(zoneMap().get(planet).get(zone).getWeight());
								zonePercent = zonePercent.divide((new BigDecimal("100.0", MathContext.DECIMAL128)));
								planetPercent = planetPercent.add(zonePercent);
								
								if (((int) zoneMap().get(planet).get(zone).getPercent().intValue()) != ((int) object.getCurrentServerGCWZonePercentMap().get(zone).getPercent().intValue())) {
									zoneUpdates.put(zone, zoneMap().get(planet).get(zone).clone());
									zoneHistoryUpdates.put(zone, new CurrentServerGCWZoneHistory(object.getCurrentServerGCWZonePercentMap().get(zone)));
								}
							}
							
							//System.out.println("Percent for " + planet + ": " + planetPercent.doubleValue());
							
							galaxyPercent = galaxyPercent.add(planetPercent);
							
							if (((int) planetPercent.intValue()) != ((int) object.getCurrentServerGCWTotalPercentMap().get(planet).getPercent().intValue())) {
								CurrentServerGCWZonePercent planetObject = object.getCurrentServerGCWTotalPercentMap().get(planet).clone();
								planetObject.setPercent(planetPercent);
								totalUpdates.put(planet, planetObject);
								totalHistoryUpdates.put(planet, new CurrentServerGCWZoneHistory(object.getCurrentServerGCWTotalPercentMap().get(planet)));
							}
						}
					}
					
					galaxyPercent = galaxyPercent.divide((new BigDecimal((Integer.toString((object.getCurrentServerGCWTotalPercentMap().size() - 1))), MathContext.DECIMAL128)));

					//System.out.println("Percent for galaxy: " + galaxyPercent.doubleValue());
					
					if (((int) galaxyPercent.intValue()) != ((int) object.getCurrentServerGCWTotalPercentMap().get("galaxy").getPercent().intValue())) {
						CurrentServerGCWZonePercent galaxyObject = object.getCurrentServerGCWTotalPercentMap().get("galaxy").clone();
						galaxyObject.setPercent(galaxyPercent);
						totalUpdates.put("galaxy", galaxyObject);
						totalHistoryUpdates.put("galaxy", new CurrentServerGCWZoneHistory(object.getCurrentServerGCWTotalPercentMap().get("galaxy")));
					}
					
					if (zoneUpdates.size() > 0) {
						object.getCurrentServerGCWZonePercentMap().putAll(zoneUpdates);
						object.getCurrentServerGCWZoneHistoryMap().putAll(zoneHistoryUpdates);
						
						if (totalUpdates.size() > 0) {
							object.getCurrentServerGCWTotalPercentMap().putAll(totalUpdates);
							object.getCurrentServerGCWTotalHistoryMap().putAll(totalHistoryUpdates);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1, 1, TimeUnit.MINUTES);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { updateOtherGalaxiesGCWZones(); }
			
			private void updateOtherGalaxiesGCWZones() {
				updateGalaxiesWideGCWZones();
			}
			
			private void updateGalaxiesWideGCWZones() {
				try {
					List<OtherServerGCWZonePercent> galaxyWideZones = new ArrayList<OtherServerGCWZonePercent>();
					galaxyWideZones.addAll(object.getOtherServerGCWZonePercentMap().get("SWG"));
					
					for (int i = 0; i < galaxyWideZones.size(); i++) {
						OtherServerGCWZonePercent zone = galaxyWideZones.get(i).clone();
						BigDecimal percent = object.getCurrentServerGCWZonePercentMap().get(zone.getZone()).getPercent();
						
						if (((int) zone.getPercent()) != percent.intValue()) {
							zone.setPercent(percent.intValue());
							galaxyWideZones.set(i, zone);
						}
					}
					
					if (!galaxyWideZones.equals(object.getOtherServerGCWZonePercentMap().get("SWG"))) {
						object.getOtherServerGCWZonePercentMap().replaceValues("SWG", galaxyWideZones);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 15, 15, TimeUnit.MINUTES);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { decrementZoneStrength(); }
			
			private void decrementZoneStrength() {
				try {
					for (String planet : zoneMap().keySet()) {
						if (!planet.equals("galaxy")) {
							for (String zone : zoneMap().get(planet).keySet()) {
								zoneMap().get(planet).get(zone).removeGCWPoints(1);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1, 1, TimeUnit.MINUTES);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { resetWeakGCWZones(); }
			
			private void resetWeakGCWZones() {
				try {
					for (String planet : zoneMap().keySet()) {
						for (String zoneName : zoneMap().get(planet).keySet()) {
							CurrentServerGCWZonePercent zone = zoneMap().get(planet).get(zoneName).clone();
							
							if ((zone.getGCWPoints() == 1) && (((int) zone.getPercent().intValue()) != 50)) {
								zone.setPercent((new BigDecimal("50.0", MathContext.DECIMAL128)));
								zoneMap().get(planet).put(zoneName, zone);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 2, 2, TimeUnit.DAYS);
	}
	
	public void addZone(String planet, String zone, float x, float z, float radius, int weight, int type) {
		CurrentServerGCWZonePercent zoneObject;
		
		if (!zoneMap().containsKey(planet)) {
			zoneMap().put(planet, new TreeMap<String, CurrentServerGCWZonePercent>());
			object.getCurrentServerGCWTotalPercentMap().put(planet, new CurrentServerGCWZonePercent(new Point2D(0, 0), 0, 0, 0));
			object.getCurrentServerGCWTotalHistoryMap().put(planet, new CurrentServerGCWZoneHistory(object.getCurrentServerGCWTotalPercentMap().get(planet).clone()));
			object.getOtherServerGCWTotalPercentMap().put("SWG", new OtherServerGCWZonePercent(planet));
		}
		
		if (zoneMap().get(planet).containsKey(zone)) {
			zoneObject = zoneMap().get(planet).get(zone);
			
			if (zoneObject.getPosition().x != x) {
				zoneObject.getPosition().x = x;
			}
			
			if (zoneObject.getPosition().z != z) {
				zoneObject.getPosition().z = z;
			}
			
			if (zoneObject.getRadius() != radius) {
				zoneObject.setRadius(radius);
			}
			
			if (zoneObject.getWeight().unscaledValue().intValue() != weight) {
				zoneObject.setWeight(weight);
			}
			
			if (zoneObject.getType() != type) {
				zoneObject.setType(type);
			}
		} else {
			zoneObject = new CurrentServerGCWZonePercent(new Point2D(x, z), radius, weight, type);
			
			zoneMap().get(planet).put(zone, zoneObject);
			
			if (!planet.equals("galaxy")) {
				object.getCurrentServerGCWZonePercentMap().put(zone, zoneMap().get(planet).get(zone).clone());
				object.getCurrentServerGCWZoneHistoryMap().put(zone, new CurrentServerGCWZoneHistory(zoneMap().get(planet).get(zone).clone()));
				object.getOtherServerGCWZonePercentMap().put("SWG", new OtherServerGCWZonePercent(zone));
			}
		}
	}
	
	public void adjustZone(String planet, String zone, String faction, int amount) {
		synchronized(objectMutex) {
			CurrentServerGCWZonePercent zoneObject = zoneMap().get(planet).get(zone).clone();
			BigDecimal newPercent = new BigDecimal(Double.toString(((((double) amount) / ((double) zoneObject.getGCWPoints())) * ((double) 100.0))), MathContext.DECIMAL128);
			
			switch (faction) {
				case "rebel": {
					newPercent = newPercent.multiply(zoneObject.getPercent()).divide((new BigDecimal("100.0", MathContext.DECIMAL128)));
					zoneMap().get(planet).get(zone).setPercent(((zoneObject.getPercent().subtract(newPercent).intValue() < 0) ? (new BigDecimal("0.0", MathContext.DECIMAL128)) : (zoneObject.getPercent().subtract(newPercent))));
					zoneMap().get(planet).get(zone).addGCWPoints(amount);
					break;
				}
				case "imperial": {
					newPercent = newPercent.multiply(((new BigDecimal("100.0", MathContext.DECIMAL128).subtract(zoneObject.getPercent())))).divide((new BigDecimal("100.0", MathContext.DECIMAL128)));
					zoneMap().get(planet).get(zone).setPercent(((zoneObject.getPercent().subtract(newPercent).intValue() > 100) ? (new BigDecimal("100.0", MathContext.DECIMAL128)) : (zoneObject.getPercent().add(newPercent))));
					zoneMap().get(planet).get(zone).addGCWPoints(amount);
					break;
				}
			}
			
		}
	}
	
	public void adjustZone(String zone, String faction, int amount) {
		for (String planet : zoneMap().keySet()) {
			if (!planet.equals("galaxy")) {
				if (zoneMap().get(planet).containsKey(zone)) {
					adjustZone(planet, zone, faction, amount);
				}
			}
		}
	}
	
	public List<SWGObject> getSFPlayers() {
		List<SWGObject> flagged = new ArrayList<SWGObject>();
		
		synchronized(core.getActiveConnectionsMap()) {
			for (Client client : core.getActiveConnectionsMap().values()) {
				if (client.getParent() != null && client.getParent() instanceof CreatureObject) {
					flagged.add(client.getParent());
				}
			}
		}
		
		Iterator<SWGObject> it = flagged.iterator();
		
		while(it.hasNext()) {
			SWGObject obj = it.next();
			if (((CreatureObject) obj).getFactionStatus() != 2) {
				it.remove();
			}
		}

		return flagged;
	}
	
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		swgOpcodes.put(Opcodes.GcwRegionsReq, new INetworkRemoteEvent() {
			
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				GcwRegionsReq gcwRegionsReq = new GcwRegionsReq();
				gcwRegionsReq.deserialize(data);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				
				if (client == null || client.getSession() == null) {
					return;
				}
				
				SWGObject object = client.getParent();
				
				if (object == null) {
					return;
				}
				
				session.write((new GcwRegionsRsp(zoneMap())).serialize());
				session.write((new GcwGroupsRsp(zoneMap())).serialize());
				
			}
			
		});
		
	}
	
	@Override
	public void shutdown() {
		
	}
	
}
