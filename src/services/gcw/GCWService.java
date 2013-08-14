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

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.session.IoSession;
import org.python.google.common.collect.ArrayListMultimap;
import org.python.google.common.collect.Multimap;

import protocol.swg.GcwGroupsRsp;
import protocol.swg.GcwRegionsReq;
import protocol.swg.GcwRegionsRsp;
import protocol.swg.GetMapLocationsMessage;
import protocol.swg.GetMapLocationsResponseMessage;

import resources.common.Factions;
import resources.common.Opcodes;
import resources.common.PvpStatus;
import resources.gcw.CurrentServerGCWZoneHistory;
import resources.gcw.CurrentServerGCWZonePercent;
import resources.gcw.OtherServerGCWZonePercent;
import resources.objects.creature.CreatureObject;
import resources.objects.guild.GuildObject;

import main.NGECore;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point2D;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

@SuppressWarnings("unused")

public class GCWService implements INetworkDispatch {
	
	private NGECore core;
	private GuildObject object;
	private Map<String, Map<String, CurrentServerGCWZonePercent>> zoneMap = new TreeMap<String, Map<String, CurrentServerGCWZonePercent>>();
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	protected final Object objectMutex = new Object();
	
	public GCWService(final NGECore core) {
		this.core = core;
		this.object = this.core.guildService.getGuildObject();
		
		core.scriptService.callScript("scripts/", "gcwzones", "addZones", core);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { checkFactionalPresence(); }
			
			private void checkFactionalPresence() {
				for (String planet : zoneMap.keySet()) {
					for (String zoneName : zoneMap.get(planet).keySet()) {
						CurrentServerGCWZonePercent zone = zoneMap.get(planet).get(zoneName);
						
						if (zone.getRadius() > 0) {
							List<SWGObject> players = core.simulationService.get(core.terrainService.getPlanetByName(planet), zone.getPosition().x, zone.getPosition().z, (int) zone.getRadius());
							
							for (SWGObject player : players) {
								if (!core.terrainService.isWater(player.getPlanetId(), player.getPosition().x, player.getPosition().z)) {
									if ((player instanceof CreatureObject)) {
										if ((((CreatureObject) player).getFactionStatus() & PvpStatus.Overt) != 0) {
											adjustZone(planet, zoneName, ((CreatureObject) player).getFaction(), 1);
										}
									}
								}
							}
						}
					}
				}
			}
		}, 0, 3, TimeUnit.SECONDS);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { updateGCWZones(); }
			
			private void updateGCWZones() {
				Map<String, CurrentServerGCWZonePercent> zoneUpdates = new TreeMap<String, CurrentServerGCWZonePercent>();
				Map<String, CurrentServerGCWZonePercent> totalUpdates = new TreeMap<String, CurrentServerGCWZonePercent>();
				Multimap<String, CurrentServerGCWZoneHistory> zoneHistoryUpdates = ArrayListMultimap.create();
				Multimap<String, CurrentServerGCWZoneHistory> totalHistoryUpdates = ArrayListMultimap.create();
				
				int galaxyPercent = 0;
				
				for (String planet : zoneMap.keySet()) {
					if (!planet.equals("galaxy")) {
						int planetPercent = 0;
						
						for (String zone : zoneMap.get(planet).keySet()) {
							planetPercent += zoneMap.get(planet).get(zone).getPercent();
							
							if (zoneMap.get(planet).get(zone).getPercent() != object.getCurrentServerGCWZonePercentMap().get(zone).getPercent()) {
								zoneUpdates.put(zone, zoneMap.get(planet).get(zone));
								zoneHistoryUpdates.put(zone, new CurrentServerGCWZoneHistory(object.getCurrentServerGCWZonePercentMap().get(zone)));
							}
						}
						
						planetPercent = ((planetPercent / (zoneMap.get(planet).size() * 100)) * 100);
						
						if (planetPercent != object.getCurrentServerGCWTotalPercentMap().get(planet).getPercent()) {
							CurrentServerGCWZonePercent planetObject = object.getCurrentServerGCWTotalPercentMap().get(planet);
							planetObject.setPercent(planetPercent);
							totalUpdates.put(planet, planetObject);
							totalHistoryUpdates.put(planet, new CurrentServerGCWZoneHistory(object.getCurrentServerGCWTotalPercentMap().get(planet)));
						}
						
						galaxyPercent += planetPercent;
					}
				}
				
				galaxyPercent = ((galaxyPercent / ((object.getCurrentServerGCWTotalPercentMap().size() - 1) * 100)) * 100);
				
				if (galaxyPercent != object.getCurrentServerGCWTotalPercentMap().get("galaxy").getPercent()) {
					CurrentServerGCWZonePercent galaxyObject = object.getCurrentServerGCWTotalPercentMap().get("galaxy");
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
			}
		}, 0, 1, TimeUnit.MINUTES);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { updateOtherGalaxiesGCWZones(); }
			
			private void updateOtherGalaxiesGCWZones() {
				updateGalaxiesWideGCWZones();
			}
			
			private void updateGalaxiesWideGCWZones() {
				List<OtherServerGCWZonePercent> galaxyWideZones = new ArrayList<OtherServerGCWZonePercent>();
				galaxyWideZones.addAll(object.getOtherServerGCWZonePercentMap().get("SWG"));
				
				for (int i = 0; i < galaxyWideZones.size(); i++) {
					OtherServerGCWZonePercent zone = galaxyWideZones.get(i);
					int percent = object.getCurrentServerGCWZonePercentMap().get(zone.getZone()).getPercent();
					
					if (zone.getPercent() != percent) {
						zone.setPercent(percent);
						galaxyWideZones.set(i, zone);
					}
				}
				
				if (!galaxyWideZones.equals(object.getOtherServerGCWZonePercentMap().get("SWG"))) {
					object.getOtherServerGCWZonePercentMap().replaceValues("SWG", galaxyWideZones);
				}
			}
		}, 0, 15, TimeUnit.MINUTES);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { decrementZoneStrength(); }
			
			private void decrementZoneStrength() {
				for (String planet : zoneMap.keySet()) {
					if (!planet.equals("galaxy")) {
						for (String zone : zoneMap.get(planet).keySet()) {
							zoneMap.get(planet).get(zone).removeGCWPoints(1);
							object.getCurrentServerGCWZonePercentMap().get(zone).removeGCWPoints(1);
						}
					}
				}
			}
		}, 0, 1, TimeUnit.MINUTES);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { resetWeakGCWZones(); }
			
			private void resetWeakGCWZones() {
				for (String planet : zoneMap.keySet()) {
					for (String zoneName : zoneMap.get(planet).keySet()) {
						CurrentServerGCWZonePercent zone = zoneMap.get(planet).get(zoneName);
						
						if (zone.getGCWPoints() == 0 && zone.getPercent() != 50) {
							zone.setPercent(50);
							zoneMap.get(planet).put(zoneName, zone);
						}
					}
				}
			}
		}, 2, 2, TimeUnit.DAYS);
	}
	
	public void addZone(String planet, String zone, float x, float y, float radius, int group, float weight) {
		if (!zoneMap.containsKey(planet)) {
			zoneMap.put(planet, new TreeMap<String, CurrentServerGCWZonePercent>());
		}
		
		zoneMap.get(planet).put(zone, new CurrentServerGCWZonePercent(new Point2D(x, y), radius, ((new SimpleBufferAllocator()).allocate(4, false).order(ByteOrder.BIG_ENDIAN).putInt(group).flip().getInt()), weight));
		
		if (planet != "galaxy") {
			object.getCurrentServerGCWZonePercentMap().put(zone, zoneMap.get(planet).get(zone));
			object.getOtherServerGCWZonePercentMap().put("SWG", new OtherServerGCWZonePercent(zone));
		}
		
		if (!object.getCurrentServerGCWTotalPercentMap().containsKey(planet)) {
			object.getCurrentServerGCWTotalPercentMap().put(planet, new CurrentServerGCWZonePercent(new Point2D(0, 0), 0, 0, 0));
			object.getOtherServerGCWTotalPercentMap().put("SWG", new OtherServerGCWZonePercent(planet));
		}
	}
	
	public void adjustZone(String planet, String zone, String faction, int amount) {
		synchronized(objectMutex) {
			CurrentServerGCWZonePercent zoneObject = zoneMap.get(planet).get(zone);
			int newPercent = ((amount / zoneObject.getGCWPoints()) * 100);
			
			switch (faction) {
				case "rebel": {
					zoneMap.get(planet).get(zone).setPercent(((zoneObject.getPercent() - newPercent) < 0) ? 0 : (zoneObject.getPercent() - newPercent));
					zoneMap.get(planet).get(zone).addGCWPoints(amount);
				}
				case "imperial": {
					zoneMap.get(planet).get(zone).setPercent(((zoneObject.getPercent() + newPercent) > 100) ? 0 : (zoneObject.getPercent() + newPercent));
					zoneMap.get(planet).get(zone).addGCWPoints(amount);
				}
			}
		}
	}
	
	public void adjustZone(String zone, String faction, int amount) {
		for (String planet : zoneMap.keySet()) {
			if (!planet.equals("galaxy")) {
				if (zoneMap.get(planet).containsKey(zone)) {
					adjustZone(planet, zone, faction, amount);
				}
			}
		}
	}
	
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		swgOpcodes.put(Opcodes.GcwRegionsReq, new INetworkRemoteEvent() {
			
			@Override
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
				
				session.write((new GcwRegionsRsp(zoneMap)).serialize());
				session.write((new GcwGroupsRsp(zoneMap)).serialize());
				
			}
			
		});
		
	}
	
	@Override
	public void shutdown() {
		
	}
	
}
