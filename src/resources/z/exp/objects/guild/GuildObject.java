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
package resources.z.exp.objects.guild;

import java.util.Map;
import java.util.TreeMap;

import org.apache.mina.core.buffer.IoBuffer;

import resources.gcw.CurrentServerGCWZoneHistory;
import resources.gcw.CurrentServerGCWZonePercent;
import resources.gcw.OtherServerGCWZonePercent;
import resources.z.exp.guild.Guild;
import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.SWGList;
import resources.z.exp.objects.SWGMap;
import resources.z.exp.objects.SWGMultiMap;
import resources.z.exp.objects.universe.UniverseObject;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;

import engine.clients.Client;
import engine.resources.objects.IPersistent;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Entity
public class GuildObject extends UniverseObject implements IPersistent {
	
	@NotPersistent
	private GuildMessageBuilder messageBuilder = new GuildMessageBuilder(this);
	
	private Map<String, Map<String, CurrentServerGCWZonePercent>> zoneMap = new TreeMap<String, Map<String, CurrentServerGCWZonePercent>>();
	
	@NotPersistent
	private Transaction txn;
	
	public GuildObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
	}
	
	public GuildObject() {
		super();
	}
	
	@Override
	public void initializeBaselines() {
		super.initializeBaselines();
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		baseline.put("nextUpdateTime", 1321383613);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("guildList", new SWGList<Guild>(this, 3, 4, false));
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
	public SWGList<Guild> getGuildList() {
		return (SWGList<Guild>) baseline3.get("guildList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, CurrentServerGCWZonePercent> getCurrentServerGCWZonePercentMap() {
		return (SWGMap<String, CurrentServerGCWZonePercent>) baseline6.get("currentServerGCWZonePercentMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, CurrentServerGCWZonePercent> getCurrentServerGCWTotalPercentMap() {
		return (SWGMap<String, CurrentServerGCWZonePercent>) baseline6.get("currentServerGCWTotalPercentMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMultiMap<String, CurrentServerGCWZoneHistory> getCurrentServerGCWZoneHistoryMap() {
		return (SWGMultiMap<String, CurrentServerGCWZoneHistory>) baseline6.get("currentServerGCWZoneHistoryMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMultiMap<String, CurrentServerGCWZoneHistory> getCurrentServerGCWTotalHistoryMap() {
		return (SWGMultiMap<String, CurrentServerGCWZoneHistory>) baseline6.get("currentServerGCWTotalHistoryMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMultiMap<String, OtherServerGCWZonePercent> getOtherServerGCWZonePercentMap() {
		return (SWGMultiMap<String, OtherServerGCWZonePercent>) baseline6.get("otherServerGCWZonePercentMap");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMultiMap<String, OtherServerGCWZonePercent> getOtherServerGCWTotalPercentMap() {
		return (SWGMultiMap<String, OtherServerGCWZonePercent>) baseline6.get("otherServerGCWTotalPercentMap");
	}
	
	public Map<String, Map<String, CurrentServerGCWZonePercent>> getZoneMap() {
		synchronized(objectMutex) {
			return zoneMap;
		}
	}
	
	public int getNextUpdateTime() {
		synchronized(objectMutex) {
			return (int) otherVariables.get("nextUpdateTime");
		}
	}
	
	public int setNextUpdateTime(long nextUpdateTime) {
		synchronized(objectMutex) {
			otherVariables.set("nextUpdateTime", (int) nextUpdateTime);
			return getNextUpdateTime();
		}
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, false);
	}
	
	@Override
	public GuildMessageBuilder getMessageBuilder() {
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
			//destination.getSession().write(baseline3.getBaseline());
			//destination.getSession().write(baseline6.getBaseline());
		}
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		notifyClients(baseline6.createDelta(updateType, buffer.array()), false);
	}
	
	public Transaction getTransaction() {
		return txn;
	}
	
	public void createTransaction(Environment env) {
		txn = env.beginTransaction(null, null);
	}
	
}
