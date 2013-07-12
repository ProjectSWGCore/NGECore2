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

import resources.objects.CurrentServerGCWZoneInfo;
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
	
	public GCWService(final NGECore core) {
		this.core = core;
		
		// The GCW object is also the guild object; someone at SOE got lazy
		object = this.core.guildService.getGuildObject();
		
		// Load GCW Zones
		// TODO load from the stf file in future
		String[] zones =
		{
			"corellia_airspace", "corellia_pve", "corellia_pvp", "dantooine_airspace",
			"dantooine_pve", "dantooine_pvp", "dathomir_airspace", "dathomir_pve", "dathomir_pvp",
			"endor_airspace", "endor_pve", "endor_pvp", "endor_pvp_battlefield",
			"gcw_region_corellia_1", "gcw_region_corellia_10", "gcw_region_corellia_11",
			"gcw_region_corellia_12", "gcw_region_corellia_13", "gcw_region_corellia_14",
			"gcw_region_corellia_2", "gcw_region_corellia_3", "gcw_region_corellia_4",
			"gcw_region_corellia_5", "gcw_region_corellia_6", "gcw_region_corellia_7",
			"gcw_region_corellia_8", "gcw_region_corellia_9", "gcw_region_dantooine_1",
			"gcw_region_dantooine_10", "gcw_region_dantooine_11", "gcw_region_dantooine_12",
			"gcw_region_dantooine_13", "gcw_region_dantooine_14", "gcw_region_dantooine_15",
			"gcw_region_dantooine_16", "gcw_region_dantooine_17", "gcw_region_dantooine_2", 
			"gcw_region_dantooine_3", "gcw_region_dantooine_4", "gcw_region_dantooine_5", 
			"gcw_region_dantooine_6", "gcw_region_dantooine_7", "gcw_region_dantooine_8", 
			"gcw_region_dantooine_9", "gcw_region_dathomir_1", "gcw_region_dathomir_10", 
			"gcw_region_dathomir_11", "gcw_region_dathomir_12", "gcw_region_dathomir_13", 
			"gcw_region_dathomir_2", "gcw_region_dathomir_3", "gcw_region_dathomir_4", 
			"gcw_region_dathomir_5", "gcw_region_dathomir_6", "gcw_region_dathomir_7", 
			"gcw_region_dathomir_8", "gcw_region_dathomir_9", "gcw_region_endor_1", 
			"gcw_region_endor_10", "gcw_region_endor_11", "gcw_region_endor_12", 
			"gcw_region_endor_13", "gcw_region_endor_14", "gcw_region_endor_15", 
			"gcw_region_endor_16", "gcw_region_endor_2", "gcw_region_endor_3", 
			"gcw_region_endor_4", "gcw_region_endor_5", "gcw_region_endor_6", 
			"gcw_region_endor_7", "gcw_region_endor_8", "gcw_region_endor_9", "gcw_region_lok_1", 
			"gcw_region_lok_10", "gcw_region_lok_11", "gcw_region_lok_12", "gcw_region_lok_13", 
			"gcw_region_lok_14", "gcw_region_lok_2", "gcw_region_lok_3", "gcw_region_lok_4", 
			"gcw_region_lok_5", "gcw_region_lok_6", "gcw_region_lok_7", "gcw_region_lok_8", 
			"gcw_region_lok_9", "gcw_region_naboo_1", "gcw_region_naboo_10",
			"gcw_region_naboo_11", "gcw_region_naboo_12", "gcw_region_naboo_13",
			"gcw_region_naboo_14", "gcw_region_naboo_2", "gcw_region_naboo_3", 
			"gcw_region_naboo_4", "gcw_region_naboo_5", "gcw_region_naboo_6", "gcw_region_naboo_7", 
			"gcw_region_naboo_8", "gcw_region_naboo_9", "gcw_region_rori_1", "gcw_region_rori_10", 
			"gcw_region_rori_11", "gcw_region_rori_12", "gcw_region_rori_13", "gcw_region_rori_2", 
			"gcw_region_rori_3", "gcw_region_rori_4", "gcw_region_rori_5", "gcw_region_rori_6", 
			"gcw_region_rori_7", "gcw_region_rori_8", "gcw_region_rori_9", "gcw_region_talus_1", 
			"gcw_region_talus_10", "gcw_region_talus_11", "gcw_region_talus_12", "gcw_region_talus_13", 
			"gcw_region_talus_14", "gcw_region_talus_15", "gcw_region_talus_16", "gcw_region_talus_2", 
			"gcw_region_talus_3", "gcw_region_talus_4", "gcw_region_talus_5", "gcw_region_talus_6", 
			"gcw_region_talus_7", "gcw_region_talus_8", "gcw_region_talus_9", "gcw_region_tatooine_1", 
			"gcw_region_tatooine_10", "gcw_region_tatooine_11", "gcw_region_tatooine_12", 
			"gcw_region_tatooine_13", "gcw_region_tatooine_2", "gcw_region_tatooine_3", 
			"gcw_region_tatooine_4", "gcw_region_tatooine_5", "gcw_region_tatooine_6", 
			"gcw_region_tatooine_7", "gcw_region_tatooine_8", "gcw_region_tatooine_9", 
			"gcw_region_yavin4_1", "gcw_region_yavin4_10", "gcw_region_yavin4_11",
			"gcw_region_yavin4_12", "gcw_region_yavin4_13", "gcw_region_yavin4_14", 
			"gcw_region_yavin4_15", "gcw_region_yavin4_16", "gcw_region_yavin4_17", 
			"gcw_region_yavin4_18", "gcw_region_yavin4_2", "gcw_region_yavin4_3", 
			"gcw_region_yavin4_4", "gcw_region_yavin4_5", "gcw_region_yavin4_6", 
			"gcw_region_yavin4_7", "gcw_region_yavin4_8", "gcw_region_yavin4_9", "lok_airspace", 
			"lok_pve", "lok_pvp", "naboo_airspace", "naboo_pve", "naboo_pvp", "rori_airspace", 
			"rori_pve", "rori_pvp", "space_corellia_space_pve", "space_corellia_space_pvp", 
			"space_dantooine_space_pve", "space_dantooine_space_pvp", "space_dathomir_space_pve", 
			"space_dathomir_space_pvp", "space_endor_space_pve", "space_endor_space_pvp", 
			"space_lok_space_pve", "space_lok_space_pvp", "space_naboo_space_pve", 
			"space_naboo_space_pvp", "space_tatooine_space_pve", "space_tatooine_space_pvp", 
			"space_yavin4_space_pve", "space_yavin4_space_pvp", "talus_airspace", "talus_pve", 
			"talus_pvp", "tatooine_airspace", "tatooine_pve", "tatooine_pvp", "yavin4_airspace", 
			"yavin4_pve", "yavin4_pvp", "yavin4_pvp_battlefield"
		};
		
		List<String> zoneList = Arrays.asList(zones);
		
		for (int i = 0; i < zoneList.size(); i++) {
			object.getCurrentServerGCWZonePercentList().add(new CurrentServerGCWZonePercent(zoneList.get(i)));
			object.getCurrentServerGCWZoneInfoList().add(new CurrentServerGCWZoneInfo(zoneList.get(i)));
			object.getOtherServerGCWZonePercentList().add(new OtherServerGCWZonePercent(zoneList.get(i)));
			object.setCurrentServerGCWZonePercentListUpdateCounter(object.getCurrentServerGCWZonePercentListUpdateCounter() + 1);
			object.setCurrentServerGCWZoneInfoListUpdateCounter(object.getCurrentServerGCWZoneInfoListUpdateCounter() + 1);
			object.setOtherServerGCWZonePercentListUpdateCounter(object.getOtherServerGCWZonePercentListUpdateCounter() + 1);
		}
		
		// Load GCW Totals
		// TODO load from the stf file in future
		String[] totals =
		{
			"corellia", "dantooine", "dathomir", "endor", "galaxy",
			"lok", "naboo", "rori", "talus", "tatooine", "yavin4"
		};
		
		List<String> totalList = Arrays.asList(totals);
		
		for (int i = 0; i < totalList.size(); i++) {
			object.getCurrentServerGCWTotalPercentList().add(new CurrentServerGCWZonePercent(totalList.get(i)));
			object.getCurrentServerGCWTotalInfoList().add(new CurrentServerGCWZoneInfo(totalList.get(i)));
			object.getOtherServerGCWTotalPercentList().add(new OtherServerGCWZonePercent(totalList.get(i)));
			object.setCurrentServerGCWTotalPercentListUpdateCounter(object.getCurrentServerGCWTotalPercentListUpdateCounter() + 1);
			object.setCurrentServerGCWTotalInfoListUpdateCounter(object.getCurrentServerGCWTotalInfoListUpdateCounter() + 1);
			object.setOtherServerGCWTotalPercentListUpdateCounter(object.getOtherServerGCWTotalPercentListUpdateCounter() + 1);
		}
		
		// Update the GCW Zones every minute as in live
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				synchronized(core.getActiveConnectionsMap()) {
					for (Client c : core.getActiveConnectionsMap().values()) {
						if (c.getParent() != null) {
							//
						}
					}
				}
			}
			
		}, 1, 1, TimeUnit.MINUTES);
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		
	}

}
