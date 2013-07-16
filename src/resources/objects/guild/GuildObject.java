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

import java.util.ArrayList;
import java.util.List;

import resources.objects.GCWZone;
import resources.objects.Guild;

import com.sleepycat.persist.model.NotPersistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class GuildObject extends SWGObject {
	
	// GILD 3
	private float complexity = 0x803F0F00;
	private String STFFile = "string_id_table";
	private int STFSpacer = 0;
	private String STFName = "";
	private String customName = "";
	private int volume = 0;
	private List<Guild> guildList = new ArrayList<Guild>();
	@NotPersistent
	private int guildListUpdateCounter = 0;
	
	// GILD 6
	private int serverId = 0x00000041;
	//private String STFName = "string_id_table";
	private int unknown1 = 0;
	private short unknown2 = 0;

	private List<GCWZone> currentServerGCWZonePercentList = new ArrayList<GCWZone>();
	@NotPersistent
	private int currentServerGCWZonePercentListUpdateCounter = 0;

	private List<GCWZone> currentServerGCWTotalPercentList = new ArrayList<GCWZone>();
	@NotPersistent
	private int currentServerGCWTotalPercentListUpdateCounter = 0;

	private List<GCWZone> currentServerGCWZoneHistoryList = new ArrayList<GCWZone>();
	@NotPersistent
	private int currentServerGCWZoneHistoryListUpdateCounter = 0;
	
	private List<GCWZone> currentServerGCWTotalHistoryList = new ArrayList<GCWZone>();
	@NotPersistent
	private int currentServerGCWTotalHistoryListUpdateCounter = 0;

	private List<GCWZone> otherServerGCWZonePercentList = new ArrayList<GCWZone>();
	@NotPersistent
	private int otherServerGCWZonePercentListUpdateCounter = 0;

	private List<GCWZone> otherServerGCWTotalPercentList = new ArrayList<GCWZone>();
	@NotPersistent
	private int otherServerGCWTotalPercentListUpdateCounter = 0;
	
	@NotPersistent
	private GuildMessageBuilder messageBuilder;
	
	public GuildObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
		messageBuilder = new GuildMessageBuilder(this);
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
	}
	
	public List<Guild> getGuildList() {
		return guildList;
	}
	
	public int getGuildListUpdateCounter() {
		synchronized(objectMutex) {
			return guildListUpdateCounter;
		}
	}
	
	public void setGuildListUpdateCounter(int guildListUpdateCounter) {
		synchronized(objectMutex) {
			this.guildListUpdateCounter = guildListUpdateCounter;
		}
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
	}
	
	public List<GCWZone> getCurrentServerGCWZonePercentList() {
		return currentServerGCWZonePercentList;
	}
	
	public int getCurrentServerGCWZonePercentListUpdateCounter() {
		synchronized(objectMutex) {
			return currentServerGCWZonePercentListUpdateCounter;
		}
	}
	
	public void setCurrentServerGCWZonePercentListUpdateCounter(int currentServerGCWZonePercentListUpdateCounter) {
		synchronized(objectMutex) {
			this.currentServerGCWZonePercentListUpdateCounter = currentServerGCWZonePercentListUpdateCounter;
		}
	}
	
	public List<GCWZone> getCurrentServerGCWTotalPercentList() {
		return currentServerGCWTotalPercentList;
	}
	
	public int getCurrentServerGCWTotalPercentListUpdateCounter() {
		synchronized(objectMutex) {
			return currentServerGCWTotalPercentListUpdateCounter;
		}
	}
	
	public void setCurrentServerGCWTotalPercentListUpdateCounter(int currentServerGCWTotalPercentListUpdateCounter) {
		synchronized(objectMutex) {
			this.currentServerGCWTotalPercentListUpdateCounter = currentServerGCWTotalPercentListUpdateCounter;
		}
	}
	
	public List<GCWZone> getCurrentServerGCWZoneHistoryList() {
		return currentServerGCWZoneHistoryList;
	}
	
	public int getCurrentServerGCWZoneHistoryListUpdateCounter() {
		synchronized(objectMutex) {
			return currentServerGCWZoneHistoryListUpdateCounter;
		}
	}
	
	public void setCurrentServerGCWZoneHistoryListUpdateCounter(int currentServerGCWZoneHistoryListUpdateCounter) {
		synchronized(objectMutex) {
			this.currentServerGCWZoneHistoryListUpdateCounter = currentServerGCWZoneHistoryListUpdateCounter;
		}
	}
	
	public List<GCWZone> getCurrentServerGCWTotalHistoryList() {
		return currentServerGCWTotalHistoryList;
	}
	
	public int getCurrentServerGCWTotalHistoryListUpdateCounter() {
		synchronized(objectMutex) {
			return currentServerGCWTotalHistoryListUpdateCounter;
		}
	}
	
	public void setCurrentServerGCWTotalHistoryListUpdateCounter(int currentServerGCWTotalHistoryListUpdateCounter) {
		synchronized(objectMutex) {
			this.currentServerGCWTotalHistoryListUpdateCounter = currentServerGCWTotalHistoryListUpdateCounter;
		}
	}
	
	public List<GCWZone> getOtherServerGCWZonePercentList() {
		return otherServerGCWZonePercentList;
	}
	
	public int getOtherServerGCWZonePercentListUpdateCounter() {
		synchronized(objectMutex) {
			return otherServerGCWZonePercentListUpdateCounter;
		}
	}
	
	public void setOtherServerGCWZonePercentListUpdateCounter(int otherServerGCWZonePercentListUpdateCounter) {
		synchronized(objectMutex) {
			this.otherServerGCWZonePercentListUpdateCounter = otherServerGCWZonePercentListUpdateCounter;
		}
	}
	
	public List<GCWZone> getOtherServerGCWTotalPercentList() {
		return otherServerGCWTotalPercentList;
	}
	
	public int getOtherServerGCWTotalPercentListUpdateCounter() {
		synchronized(objectMutex) {
			return otherServerGCWTotalPercentListUpdateCounter;
		}
	}
	
	public void setOtherServerGCWTotalPercentListUpdateCounter(int otherServerGCWTotalPercentListUpdateCounter) {
		synchronized(objectMutex) {
			this.otherServerGCWTotalPercentListUpdateCounter = otherServerGCWTotalPercentListUpdateCounter;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
	}

	
}
