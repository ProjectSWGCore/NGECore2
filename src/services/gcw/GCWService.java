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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
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
import resources.common.Opcodes;
import resources.common.OutOfBand;
import resources.common.collidables.CollidableCircle;
import resources.datatables.DisplayType;
import resources.datatables.FactionStatus;
import resources.datatables.GcwRank;
import resources.datatables.GcwType;
import resources.gcw.CurrentServerGCWZoneHistory;
import resources.gcw.CurrentServerGCWZonePercent;
import resources.gcw.OtherServerGCWZonePercent;
import resources.objects.creature.CreatureObject;
import resources.objects.guild.GuildObject;
import resources.objects.player.PlayerObject;
import main.NGECore;
import engine.clients.Client;
import engine.resources.database.ODBCursor;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point2D;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class GCWService implements INetworkDispatch {
	
	private NGECore core;
	
	private GuildObject object;
	
	private static final int[] MOD2 = { 10250, 7250, 7250, 6750, 6750, 6750, 6750, 6750, 6750, 6750, 6750, 6750 };
	private static final int[] DECAY1 = { 0, 0, 0, 0, 0, 0, 2727, 2744, 2769, 2799, 2823, 2857 };
	private static final int[] DECAY2 = { 0, 0, 0, 0, 0, 0, 6000, 6500, 7200, 8200, 9600, 12000 };
	private static final int[] CONST2 = { 0, 0, 0, 0, 0, 0, 12200, 12200, 12900, 14100, 16200, 18700 };
	private static final int[] CONST4 = { 0, 0, 0, 0, 0, 0, 1950, 3550, 5600, 7800, 9350, 11100 };
	
	private Map<String, Map<String, CurrentServerGCWZonePercent>> zoneMap() { return object.getZoneMap(); }
	private Map<Planet, List<PvPZone>> pvpZones = new ConcurrentHashMap<Planet, List<PvPZone>>();
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	protected final Object objectMutex = new Object();
	
	public GCWService(final NGECore core) {
		this.core = core;
		this.object = this.core.guildService.getGuildObject();
		
		scheduler.schedule(new Runnable() {
			@Override public void run() { afterInitialisation(); }
			
			private void afterInitialisation() {
				try {
					core.scriptService.callScript("scripts/", "gcwzones", "addZones", core);
					core.scriptService.callScript("scripts/", "pvpzones", "addZones", core);
					updateNextUpdateTime();
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
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { try { gcwUpdate(); } catch (Exception e) { e.printStackTrace(); } }
			
			private void gcwUpdate() {
				int nextUpdateTime = (int) calculateNextUpdateTime();
				
				ODBCursor cursor = core.getSWGObjectODB().getCursor();
				
				while (cursor.hasNext()) {
					SWGObject object = (SWGObject) cursor.next();
					
					if (object == null) {
						continue;
					}
					
					if (core.objectService.getObject(object.getObjectID()) == null) {
						object = core.objectService.getObject(object.getObjectID());
					}
					
					if (!(object instanceof CreatureObject)) {
						continue;
					}
					
					CreatureObject creature = (CreatureObject) object;
					
					if (creature.getSlottedObject("ghost") == null) {
						continue;
					}
					
					PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
					
					// TODO update statistics
					
					if (core.factionService.isPvpFaction(creature.getFaction())) {
						updateGcwRank(creature);
						player.addLifetimeGcwPoints(player.getGcwPoints());
						player.addLifetimePvpKills(player.getPvpKills());
						player.resetGcwPoints();
						player.resetPvpKills();
						
						if (creature.getFaction().equals("rebel") && player.getCurrentRank() > player.getHighestRebelRank()) {
							player.setHighestRebelRank(player.getCurrentRank());
						}
						
						if (creature.getFaction().equals("imperial") && player.getCurrentRank() > player.getHighestImperialRank()) {
							player.setHighestImperialRank(player.getCurrentRank());
						}
						
						player.setNextUpdateTime(nextUpdateTime);
					}
				}
				
				cursor.close();
			}
			
		}, calculateNextUpdateTime(), 6048000, TimeUnit.SECONDS);
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
	
	public void addGcwPoints(CreatureObject actor, int gcwPoints, byte gcwType) {
		if (actor == null) {
			return;
		}
		
		if (gcwPoints == 0) {
			return;
		}
		
		if (!core.factionService.isPvpFaction(actor.getFaction())) {
			return;
		}
		
		if (actor.getSlottedObject("ghost") == null) {
			return;
		}
		
		PlayerObject player = (PlayerObject) actor.getSlottedObject("ghost");
		
		int gcwBonus = actor.getSkillModBase("flush_with_success");
		
		gcwPoints += ((gcwPoints * gcwBonus) / 100);
		
		String planet = actor.getPlanet().getName();
		
		String prefix = ((planet.startsWith("space")) ? (planet + "_space") : planet);
		
		switch (gcwType) {
			case GcwType.Enemy:
				adjustZone(planet, prefix + "_pve", actor.getFaction(), gcwPoints);
				actor.sendSystemMessage(OutOfBand.ProsePackage("@gcw:gcw_rank_generic_point_grant", gcwPoints), DisplayType.Broadcast);
				break;
			case GcwType.Player:
				adjustZone(planet, prefix + "_pvp", actor.getFaction(), gcwPoints);
				break;
		}
		
		String zone = getCurrentGcwRegion(actor);
		
		if (zone != null) {
			adjustZone(planet, zone, actor.getFaction(), gcwPoints);
		}
		
		player.setGcwPoints(player.getGcwPoints() + gcwPoints);
	}
	
	/* Ported from javascript.
	 * Written for us by Hendrik of http://swg.activeframe.de.
	 */
	private double helper(int points, int rank) {
		int faktor = points / (MOD2[rank-1] - 250 * rank);
		int rt = points / (1 + faktor) - DECAY1[rank-1];
		
		if (rank >= 7) {
			if (points > DECAY2[rank-1] && rt > 0) {
				rt = rt * (((17 - rank) / (14 - rank)) + (CONST2[rank-1] / (points - CONST4[rank-1])));
			}
		}
		
		return Math.floor(rt);
	}
	
	/* Ported from javascript.
	 * Written for us by Hendrik of http://swg.activeframe.de.
	 */
	public void updateGcwRank(CreatureObject actor) {
		if (actor == null) {
			return;
		}
		
		if (actor.getFaction().length() == 0) {
			return;
		}
		
		if (actor.getSlottedObject("ghost") == null) {
			return;
		}
		
		PlayerObject player = (PlayerObject) actor.getSlottedObject("ghost");
		
		int oldrank = player.getCurrentRank();
		int oldprogress = (int) ((player.getRankProgress() > 100) ? 100 : player.getRankProgress());
		int oldpoints = player.getGcwPoints();
		int newrank = oldrank;
		int newprogress = oldprogress;
		int oldranktotal = 0;
		int oldrankprogress = 0;
		int newranktotal = 0;
		int newrankprogress = 0;
		
		oldrankprogress = oldprogress * 50;
		oldranktotal = 5000 * (oldrank - 1) + oldrankprogress;
		
		newprogress = (int) helper(oldpoints, oldrank);
		
		if (newprogress < -2000) {
			newprogress = -2000;
		}
		
		newranktotal = oldranktotal + newprogress;
		
		if (oldrank == GcwRank.LIEUTENANT && newprogress < 0 && newranktotal < 30000) {
			newranktotal = 29999;
		}
		
		newrank = (int) (Math.floor(newranktotal / 5000) + 1);
		
		if (newrank > GcwRank.GENERAL) {
			newrank = GcwRank.GENERAL;
			newranktotal = 12 * 5000 - 1;
		}
		
		newrankprogress = newranktotal - (newrank - 1) * 5000;
		newprogress = newrankprogress * 100 / 5000;
		
		player.setCurrentRank(newrank);
		player.setRankProgress((float) Math.floor(newprogress));
		
		if (newrank > oldrank) {
			core.scriptService.callScript("scripts/collections/", "gcwrank_" + actor.getFaction(), "handleRankUp", core, actor, newrank);
		} else {
			core.scriptService.callScript("scripts/collections/", "gcwrank_" + actor.getFaction(), "handleRankDown", actor, newrank);
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

		while (it.hasNext()) {
			SWGObject obj = it.next();
			if (((CreatureObject) obj).getFactionStatus() != 2) {
				it.remove();
			}
		}

		return flagged;
	}
	
	public String getCurrentGcwRegion(CreatureObject actor) {
		try {
			String planet = actor.getPlanet().getName();
			
			if (zoneMap().containsKey(planet)) {
				for (String zoneName : zoneMap().get(planet).keySet()) {
					CurrentServerGCWZonePercent zone = zoneMap().get(planet).get(zoneName).clone();
					
					if (zone.getRadius() > 0) {
						List<SWGObject> objects = core.simulationService.get(core.terrainService.getPlanetByName(planet), zone.getPosition().x, zone.getPosition().z, (int) zone.getRadius());
						
						if (objects.contains(actor)) {
							return zoneName;
						}
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}
	
	public int calculateNextUpdateTime() {
		Calendar c = Calendar.getInstance();
		Date now = new Date();
		
		c.setTime(now);
		
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && c.get(Calendar.HOUR_OF_DAY) >= 7) {
			c.add(Calendar.DAY_OF_YEAR, 7);
		}
		
		while (c.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
			c.add(Calendar.DAY_OF_WEEK, 1);
		}
		
		c.set(Calendar.HOUR_OF_DAY, 7);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return (int) ((c.getTimeInMillis() - now.getTime()) / 1000L);
	}
	
	public void updateNextUpdateTime() {
		ODBCursor cursor = core.getSWGObjectODB().getCursor();
		
		while (cursor.hasNext()) {
			SWGObject object = (SWGObject) cursor.next();
			
			if (object == null) {
				continue;
			}
			
			if (core.objectService.getObject(object.getObjectID()) == null) {
				object = core.objectService.getObject(object.getObjectID());
			}
			
			if (!(object instanceof CreatureObject)) {
				continue;
			}
			
			CreatureObject creature = (CreatureObject) object;
			
			if (creature.getSlottedObject("ghost") == null) {
				continue;
			}
			
			PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
			
			player.setNextUpdateTime(calculateNextUpdateTime());
		}
		
		cursor.close();
	}
	
	public long calculateResetTime() {
		Calendar c = Calendar.getInstance();
		Date now = new Date();
		
		c.setTime(now);
		
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		
		if (weekday != Calendar.THURSDAY) {
			int days = ((Calendar.WEDNESDAY - weekday + 2) % 7);
			c.add(Calendar.DAY_OF_YEAR, days);
		} else if (c.get(Calendar.HOUR_OF_DAY) >= 7) {
			c.add(Calendar.DAY_OF_YEAR, 7);
		}
		
		c.set(Calendar.HOUR_OF_DAY, 7);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTimeInMillis();
	}
	
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		swgOpcodes.put(Opcodes.GcwRegionsReq, new INetworkRemoteEvent() {
			
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				GcwRegionsReq gcwRegionsReq = new GcwRegionsReq();
				gcwRegionsReq.deserialize(data);
				
				Client client = core.getClient(session);
				
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
	
	public void addPvPZone(String planetName, float x, float z, float radius) {
		Planet planet = core.terrainService.getPlanetByName(planetName);
		if(planet == null)
			return;
		if(pvpZones.get(planet) == null)
			pvpZones.put(planet, new ArrayList<PvPZone>());
		CollidableCircle area = new CollidableCircle(new Point3D(x, 0, z), radius, planet);
		PvPZone zone = new PvPZone(planet, area);
		pvpZones.get(planet).add(zone);
		core.simulationService.addCollidable(area, x, z);
	}
	
	public boolean isInPvpZone(CreatureObject actor) {
		List<PvPZone> zones = pvpZones.get(actor.getPlanet());
		if(zones == null)
			return false;
		return zones.stream().anyMatch(z -> z.getArea().doesCollide(actor));
	}
	
	@Override
	public void shutdown() {
		
	}
	
}
