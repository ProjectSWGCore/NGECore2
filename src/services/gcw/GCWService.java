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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import resources.objects.CurrentServerGCWZoneHistory;
import resources.objects.CurrentServerGCWZonePercent;
import resources.objects.OtherServerGCWZonePercent;
import resources.objects.guild.GuildObject;

import main.NGECore;

import engine.clients.Client;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class GCWService implements INetworkDispatch {
	
	private NGECore core;
	private GuildObject object;
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private List<String> zoneList = Arrays.asList(new String[] { "corellia_airspace", "corellia_pve", "corellia_pvp", "dantooine_airspace", "dantooine_pve", "dantooine_pvp", "dathomir_airspace", "dathomir_pve", "dathomir_pvp", "endor_airspace", "endor_pve", "endor_pvp", "endor_pvp_battlefield", "gcw_region_corellia_1", "gcw_region_corellia_10", "gcw_region_corellia_11", "gcw_region_corellia_12", "gcw_region_corellia_13", "gcw_region_corellia_14", "gcw_region_corellia_2", "gcw_region_corellia_3", "gcw_region_corellia_4", "gcw_region_corellia_5", "gcw_region_corellia_6", "gcw_region_corellia_7", "gcw_region_corellia_8", "gcw_region_corellia_9", "gcw_region_dantooine_1", "gcw_region_dantooine_10", "gcw_region_dantooine_11", "gcw_region_dantooine_12", "gcw_region_dantooine_13", "gcw_region_dantooine_14", "gcw_region_dantooine_15", "gcw_region_dantooine_16", "gcw_region_dantooine_17", "gcw_region_dantooine_2", "gcw_region_dantooine_3", "gcw_region_dantooine_4", "gcw_region_dantooine_5", "gcw_region_dantooine_6", "gcw_region_dantooine_7", "gcw_region_dantooine_8", "gcw_region_dantooine_9", "gcw_region_dathomir_1", "gcw_region_dathomir_10", "gcw_region_dathomir_11", "gcw_region_dathomir_12", "gcw_region_dathomir_13", "gcw_region_dathomir_2", "gcw_region_dathomir_3", "gcw_region_dathomir_4", "gcw_region_dathomir_5", "gcw_region_dathomir_6", "gcw_region_dathomir_7", "gcw_region_dathomir_8", "gcw_region_dathomir_9", "gcw_region_endor_1", "gcw_region_endor_10", "gcw_region_endor_11", "gcw_region_endor_12", "gcw_region_endor_13", "gcw_region_endor_14", "gcw_region_endor_15", "gcw_region_endor_16", "gcw_region_endor_2", "gcw_region_endor_3", "gcw_region_endor_4", "gcw_region_endor_5", "gcw_region_endor_6", "gcw_region_endor_7", "gcw_region_endor_8", "gcw_region_endor_9", "gcw_region_lok_1", "gcw_region_lok_10", "gcw_region_lok_11", "gcw_region_lok_12", "gcw_region_lok_13", 	"gcw_region_lok_14", "gcw_region_lok_2", "gcw_region_lok_3", "gcw_region_lok_4", "gcw_region_lok_5", "gcw_region_lok_6", "gcw_region_lok_7", "gcw_region_lok_8", "gcw_region_lok_9", "gcw_region_naboo_1", "gcw_region_naboo_10", "gcw_region_naboo_11", "gcw_region_naboo_12", "gcw_region_naboo_13", "gcw_region_naboo_14", "gcw_region_naboo_2", "gcw_region_naboo_3", "gcw_region_naboo_4", "gcw_region_naboo_5", "gcw_region_naboo_6", "gcw_region_naboo_7", "gcw_region_naboo_8", "gcw_region_naboo_9", "gcw_region_rori_1", "gcw_region_rori_10", "gcw_region_rori_11", "gcw_region_rori_12", "gcw_region_rori_13", "gcw_region_rori_2", "gcw_region_rori_3", "gcw_region_rori_4", "gcw_region_rori_5", "gcw_region_rori_6", "gcw_region_rori_7", "gcw_region_rori_8", "gcw_region_rori_9", "gcw_region_talus_1", "gcw_region_talus_10", "gcw_region_talus_11", "gcw_region_talus_12", "gcw_region_talus_13", "gcw_region_talus_14", "gcw_region_talus_15", "gcw_region_talus_16", "gcw_region_talus_2", "gcw_region_talus_3", "gcw_region_talus_4", "gcw_region_talus_5", "gcw_region_talus_6", "gcw_region_talus_7", "gcw_region_talus_8", "gcw_region_talus_9", "gcw_region_tatooine_1", "gcw_region_tatooine_10", "gcw_region_tatooine_11", "gcw_region_tatooine_12", "gcw_region_tatooine_13", "gcw_region_tatooine_2", "gcw_region_tatooine_3", "gcw_region_tatooine_4", "gcw_region_tatooine_5", "gcw_region_tatooine_6", "gcw_region_tatooine_7", "gcw_region_tatooine_8", "gcw_region_tatooine_9", "gcw_region_yavin4_1", "gcw_region_yavin4_10", "gcw_region_yavin4_11", "gcw_region_yavin4_12", "gcw_region_yavin4_13", "gcw_region_yavin4_14", "gcw_region_yavin4_15", "gcw_region_yavin4_16", "gcw_region_yavin4_17", "gcw_region_yavin4_18", "gcw_region_yavin4_2", "gcw_region_yavin4_3", "gcw_region_yavin4_4", "gcw_region_yavin4_5", "gcw_region_yavin4_6", "gcw_region_yavin4_7", "gcw_region_yavin4_8", "gcw_region_yavin4_9", "lok_airspace", "lok_pve", "lok_pvp", "naboo_airspace", "naboo_pve", "naboo_pvp", "rori_airspace", "rori_pve", "rori_pvp", "space_corellia_space_pve", "space_corellia_space_pvp", "space_dantooine_space_pve", "space_dantooine_space_pvp", "space_dathomir_space_pve", "space_dathomir_space_pvp", "space_endor_space_pve", "space_endor_space_pvp", "space_lok_space_pve", "space_lok_space_pvp", "space_naboo_space_pve", "space_naboo_space_pvp", "space_tatooine_space_pve", "space_tatooine_space_pvp", "space_yavin4_space_pve", "space_yavin4_space_pvp", "talus_airspace", "talus_pve", "talus_pvp", "tatooine_airspace", "tatooine_pve", "tatooine_pvp", "yavin4_airspace", "yavin4_pve", "yavin4_pvp", "yavin4_pvp_battlefield" });
	private List<String> totalList = Arrays.asList(new String[] { "corellia", "dantooine", "dathomir", "endor", "galaxy", "lok", "naboo", "rori", "talus", "tatooine", "yavin4" });
	
	public GCWService(final NGECore core) {
		this.core = core;
		this.object = this.core.guildService.getGuildObject();
		
		for (int i = 0; i < zoneList.size(); i++) {
			object.getCurrentServerGCWZonePercentList().add(new CurrentServerGCWZonePercent(zoneList.get(i)));
			object.getCurrentServerGCWZoneHistoryList().add(new CurrentServerGCWZoneHistory(zoneList.get(i)));
			object.getOtherServerGCWZonePercentList().add(new OtherServerGCWZonePercent(zoneList.get(i)));
		}
		
		for (int i = 0; i < totalList.size(); i++) {
			object.getCurrentServerGCWTotalPercentList().add(new CurrentServerGCWZonePercent(totalList.get(i)));
			object.getCurrentServerGCWTotalHistoryList().add(new CurrentServerGCWZoneHistory(totalList.get(i)));
			object.getOtherServerGCWTotalPercentList().add(new OtherServerGCWZonePercent(totalList.get(i)));
		}
		
		// Update the GCW Zones every minute as in live
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				//object.sendGCWUpdate();
				//addGCWPointsToZonesWithFactionalPresence(new Client());
				//removeGCWPointFromZonesWithNoFactionalPresence(new Client());
			}
			
		}, 1, 1, TimeUnit.MINUTES);
		
		// Update other server GCW Zones every 15 minutes (roughly like live I think)
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				/*
				for (int i = 0; i < object.getOtherServerGCWZonePercentList().size(); i++) {
					object.getOtherServerGCWZonePercentList().get(i).setPercent(object.getCurrentServerGCWZonePercentList().get(i).getPercent());
					object.getOtherServerGCWZonePercentList().set(i, object.getOtherServerGCWZonePercentList().get(i));
				}
					
				for (int i = 0; i < object.getOtherServerGCWTotalPercentList().size(); i++) {
					object.getOtherServerGCWTotalPercentList().get(i).setPercent(object.getCurrentServerGCWTotalPercentList().get(i).getPercent());
					object.getOtherServerGCWZonePercentList().set(i, object.getOtherServerGCWZonePercentList().get(i));
				}
				*/
			}
			
		//}, 15, 15, TimeUnit.MINUTES);
		}, 2, 5, TimeUnit.MINUTES);
		
		// Reset weak GCW Zones every 2 days as in live
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				/*
				resetWeakGCWZones();
				*/
			}
			
		//}, 2, 2, TimeUnit.DAYS);
		}, 2, 5, TimeUnit.MINUTES);
	}
	
	private void addGCWPointsToZonesWithFactionalPresence(Client client) {

	}
	
	private void removeGCWPointFromZonesWithNoFactionalPresence(Client client) {

	}
	
	private void resetWeakGCWZones() {
		for (int i = 0; i < object.getCurrentServerGCWZonePercentList().size(); i++) {
			CurrentServerGCWZonePercent zone = object.getCurrentServerGCWZonePercentList().get(i);
			
			if (zone.getGCWPoints() == 0 && zone.getPercent() != 50) {
				zone.setPercent(50, object.getCurrentServerGCWZoneHistoryList());
				object.getCurrentServerGCWZonePercentList().set(i, zone);
			}
		}
		
		for (int i = 0; i < object.getCurrentServerGCWTotalPercentList().size(); i++) {
			CurrentServerGCWZonePercent zone = object.getCurrentServerGCWTotalPercentList().get(i);

			if (zone.getGCWPoints() == 0 && zone.getPercent() != 50) {
				zone.setPercent(50, object.getCurrentServerGCWTotalHistoryList());
				object.getCurrentServerGCWTotalPercentList().set(i, zone);
			}
		}
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		
	}

}
