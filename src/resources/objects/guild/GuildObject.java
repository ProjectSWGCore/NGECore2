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

import java.util.Map;
import java.util.TreeMap;

import org.apache.mina.core.buffer.IoBuffer;

import main.NGECore;

import resources.gcw.CurrentServerGCWZoneHistory;
import resources.gcw.CurrentServerGCWZonePercent;
import resources.gcw.OtherServerGCWZonePercent;
import resources.guild.Guild;
import resources.objects.SWGList;
import resources.objects.SWGMap;
import resources.objects.SWGMultiMap;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;

import engine.clients.Client;
import engine.resources.objects.IPersistent;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Entity
public class GuildObject extends SWGObject implements IPersistent {
	
	protected NGECore core;
	@NotPersistent
	private GuildMessageBuilder messageBuilder = new GuildMessageBuilder(this);

	private Map<String, Map<String, CurrentServerGCWZonePercent>> zoneMap = new TreeMap<String, Map<String, CurrentServerGCWZonePercent>>();
	@NotPersistent
	private Transaction txn;
	
	// GILD 3
	private float complexity = 0x803F0F00;
	private String STFFile = "string_id_table";
	private int STFSpacer = 0;
	private String STFName = "";
	private String customName = "";
	private int volume = 0;
	private SWGList<Guild> guildList = new SWGList<Guild>(messageBuilder, 3, 4);
	
	// GILD 6
	private int serverId = 0x00000041;
	//private String STFName = "string_id_table";
	private int unknown1 = 0;
	private short unknown2 = 0;
	private SWGMap<String, CurrentServerGCWZonePercent> currentServerGCWZonePercentMap = new SWGMap<String, CurrentServerGCWZonePercent>(messageBuilder, 6, 2);
	private SWGMap<String, CurrentServerGCWZonePercent> currentServerGCWTotalPercentMap = new SWGMap<String, CurrentServerGCWZonePercent>(messageBuilder, 6, 3);
	private SWGMultiMap<String, CurrentServerGCWZoneHistory> currentServerGCWZoneHistoryMap = new SWGMultiMap<String, CurrentServerGCWZoneHistory>(messageBuilder, 6, 4);
	private SWGMultiMap<String, CurrentServerGCWZoneHistory> currentServerGCWTotalHistoryMap = new SWGMultiMap<String, CurrentServerGCWZoneHistory>(messageBuilder, 6, 5);
	private SWGMultiMap<String, OtherServerGCWZonePercent> otherServerGCWZonePercentMap = new SWGMultiMap<String, OtherServerGCWZonePercent>(messageBuilder, 6, 6);
	private SWGMultiMap<String, OtherServerGCWZonePercent> otherServerGCWTotalPercentMap = new SWGMultiMap<String, OtherServerGCWZonePercent>(messageBuilder, 6, 7);
	private int unknown3 = 5;
	
	public GuildObject(NGECore core, long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
		this.core = core;
	}
	
	public float getComplexity() {
		synchronized(objectMutex) {
			return complexity;
		}
	}
	
	public void setComplexity(float complexity) {
		synchronized(objectMutex) {
			this.complexity = complexity;
		}
		
		notifyAll(messageBuilder.buildComplexity(complexity));
	}
	
	public String getSTFFile() {
		synchronized(objectMutex) {
			return STFFile;
		}
	}
	
	public void setSTFFile(String STFFile) {
		synchronized(objectMutex) {
			this.STFFile = STFFile;
		}
		
		notifyAll(messageBuilder.buildSTF(STFFile, STFSpacer, STFName));
	}
	
	public int getSTFSpacer() {
		synchronized(objectMutex) {
			return STFSpacer;
		}
	}
	
	public void setSTFSpacer(int STFSpacer) {
		synchronized(objectMutex) {
			this.STFSpacer = STFSpacer;
		}
		
		notifyAll(messageBuilder.buildSTF(STFFile, STFSpacer, STFName));
	}
	
	public String getSTFName() {
		synchronized(objectMutex) {
			return STFName;
		}
	}
	
	public void setSTFName(String STFName) {
		synchronized(objectMutex) {
			this.STFName = STFName;
		}
		
		notifyAll(messageBuilder.buildSTF(STFFile, STFSpacer, STFName));
	}
	
	public String getCustomName() {
		synchronized(objectMutex) {
			return customName;
		}
	}
	
	public void setCustomName(String customName) {
		synchronized(objectMutex) {
			this.customName = customName;
		}
		
		notifyAll(messageBuilder.buildCustomName(customName));
	}
	
	public int getVolume() {
		synchronized(objectMutex) {
			return volume;
		}
	}
	
	public void setVolume(int volume) {
		synchronized(objectMutex) {
			this.volume = volume;
		}
		
		notifyAll(messageBuilder.buildVolume(volume));
	}
	
	public SWGList<Guild> getGuildList() {
		return guildList;
	}
	
	public int getServerId() {
		synchronized(objectMutex) {
			return serverId;
		}
	}
	
	public void setServerId(int serverId) {
		synchronized(objectMutex) {
			this.serverId = serverId;
		}
		
		notifyAll(messageBuilder.buildServerId(serverId));
	}
	
	public int getUnknown1() {
		synchronized(objectMutex) {
			return unknown1;
		}
	}
	
	public void setUnknown1(int unknown1) {
		synchronized(objectMutex) {
			this.unknown1 = unknown1;
		}
		
		notifyAll(messageBuilder.buildUnknowns(unknown1, unknown2));
	}
	
	public short getUnknown2() {
		synchronized(objectMutex) {
			return unknown2;
		}
	}
	
	public void setUnknown2(short unknown2) {
		synchronized(objectMutex) {
			this.unknown2 = unknown2;
		}
		
		notifyAll(messageBuilder.buildUnknowns(unknown1, unknown2));
	}
	
	public SWGMap<String, CurrentServerGCWZonePercent> getCurrentServerGCWZonePercentMap() {
		return currentServerGCWZonePercentMap;
	}
	
	public SWGMap<String, CurrentServerGCWZonePercent> getCurrentServerGCWTotalPercentMap() {
		return currentServerGCWTotalPercentMap;
	}
	
	public SWGMultiMap<String, CurrentServerGCWZoneHistory> getCurrentServerGCWZoneHistoryMap() {
		return currentServerGCWZoneHistoryMap;
	}
	
	public SWGMultiMap<String, CurrentServerGCWZoneHistory> getCurrentServerGCWTotalHistoryMap() {
		return currentServerGCWTotalHistoryMap;
	}
	
	public SWGMultiMap<String, OtherServerGCWZonePercent> getOtherServerGCWZonePercentMap() {
		return otherServerGCWZonePercentMap;
	}
	
	public SWGMultiMap<String, OtherServerGCWZonePercent> getOtherServerGCWTotalPercentMap() {
		return otherServerGCWTotalPercentMap;
	}
	
	public int getUnknown3() {
		synchronized(objectMutex) {
			return unknown3;
		}
	}
	
	public void setUnknown3(int unknown3) {
		synchronized(objectMutex) {
			this.unknown3 = unknown3;
		}
		
		notifyAll(messageBuilder.buildUnknown3(unknown3));
	}
	
	public Map<String, Map<String, CurrentServerGCWZonePercent>> getZoneMap() {
		synchronized(objectMutex) {
			return zoneMap;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
	}
	
	private void notifyAll(IoBuffer buffer) {
		synchronized(core.getActiveConnectionsMap()) {
			for (Client client : core.getActiveConnectionsMap().values()) {
				client.getSession().write(buffer);
			}
		}
	}
	
	public Transaction getTransaction() {
		return txn;
	}
	
	public void createTransaction(Environment env) {
		txn = env.beginTransaction(null, null);
	}
	
}
