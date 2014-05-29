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
package resources.objects.guild;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.mina.core.buffer.IoBuffer;

import main.NGECore;
import resources.gcw.CurrentServerGCWZoneHistory;
import resources.gcw.CurrentServerGCWZonePercent;
import resources.gcw.OtherServerGCWZonePercent;
import resources.guild.Guild;
import resources.objects.ObjectMessageBuilder;
import resources.objects.SWGMap;
import resources.objects.SWGMultiMap;
import resources.objects.SWGSet;
import resources.objects.universe.UniverseObject;
import services.collections.ServerFirst;
import engine.clients.Client;
import engine.resources.objects.Baseline;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class GuildObject extends UniverseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private transient GuildMessageBuilder messageBuilder = new GuildMessageBuilder(this);
	
	public GuildObject(NGECore core, long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
		otherVariables = getOtherVariables(); // FIXME temp
	}
	
	public GuildObject() {
		super();
	}
	
	public void initAfterDBLoad() {
		super.init();
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		baseline.put("nextInstanceId", (long) 0);
		baseline.put("zoneMap", new TreeMap<String, Map<String, CurrentServerGCWZonePercent>>());
		baseline.put("serverFirst", new HashMap<String, ServerFirst>());
		baseline.put("guilds", new TreeMap<Integer, Guild>());
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("guildList", new SWGSet<String>(this, 3, 4, false));
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("currentServerGCWZonePercentMap", new SWGMap<String, CurrentServerGCWZonePercent>(this, 6, 2, true));
		baseline.put("currentServerGCWTotalPercentMap", new SWGMap<String, CurrentServerGCWZonePercent>(this, 6, 3, true));
		baseline.put("currentServerGCWZoneHistoryMap", new SWGMultiMap<String, CurrentServerGCWZoneHistory>(this, 6, 4, true));
		baseline.put("currentServerGCWTotalHistoryMap", new SWGMultiMap<String, CurrentServerGCWZoneHistory>(this, 6, 5, true));
		baseline.put("otherServerGCWZonePercentMap", new SWGMultiMap<String, OtherServerGCWZonePercent>(this, 6, 6, true));
		baseline.put("otherServerGCWTotalPercentMap", new SWGMultiMap<String, OtherServerGCWZonePercent>(this, 6, 7, true));
		baseline.put("8", 5);
		return baseline;
	}
	
	@SuppressWarnings("unchecked")
	public SWGSet<String> getGuildList() {
		return (SWGSet<String>) getBaseline(3).get("guildList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, CurrentServerGCWZonePercent> getCurrentServerGCWZonePercentMap() {
		return (SWGMap<String, CurrentServerGCWZonePercent>) getBaseline(6).get("currentServerGCWZonePercentMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, CurrentServerGCWZonePercent> getCurrentServerGCWTotalPercentMap() {
		return (SWGMap<String, CurrentServerGCWZonePercent>) getBaseline(6).get("currentServerGCWTotalPercentMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMultiMap<String, CurrentServerGCWZoneHistory> getCurrentServerGCWZoneHistoryMap() {
		return (SWGMultiMap<String, CurrentServerGCWZoneHistory>) getBaseline(6).get("currentServerGCWZoneHistoryMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMultiMap<String, CurrentServerGCWZoneHistory> getCurrentServerGCWTotalHistoryMap() {
		return (SWGMultiMap<String, CurrentServerGCWZoneHistory>) getBaseline(6).get("currentServerGCWTotalHistoryMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMultiMap<String, OtherServerGCWZonePercent> getOtherServerGCWZonePercentMap() {
		return (SWGMultiMap<String, OtherServerGCWZonePercent>) getBaseline(6).get("otherServerGCWZonePercentMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMultiMap<String, OtherServerGCWZonePercent> getOtherServerGCWTotalPercentMap() {
		return (SWGMultiMap<String, OtherServerGCWZonePercent>) getBaseline(6).get("otherServerGCWTotalPercentMap");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, CurrentServerGCWZonePercent>> getZoneMap() {
		return (Map<String, Map<String, CurrentServerGCWZonePercent>>) otherVariables.get("zoneMap");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, ServerFirst> getServerFirst() {
		return (Map<String, ServerFirst>) otherVariables.get("serverFirst");
	}
	
	public boolean addServerFirst(String collectionName, ServerFirst player) {
		synchronized(objectMutex) {
			if (!getServerFirst().containsKey(collectionName)) {
				getServerFirst().put(collectionName, player);
				return true;
			}
			
			return false;
		}
	}
	
	public long getNextInstanceId() {
		synchronized(objectMutex) {
			otherVariables.set("nextInstanceId", (long) (((long) otherVariables.get("nextInstanceId")) + 1L));
			return (long) otherVariables.get("nextInstanceId");
		}
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap<Integer, Guild> getGuilds() {
		synchronized(objectMutex) {
			return (TreeMap<Integer, Guild>) otherVariables.get("guilds");
		}
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, false);
	}
	
	@Override
	public ObjectMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new GuildMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(getBaseline(3).getBaseline());
			destination.getSession().write(getBaseline(6).getBaseline());
		}
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		notifyClients(getBaseline(viewType).createDelta(updateType, buffer.array()), false);
	}
	
}
