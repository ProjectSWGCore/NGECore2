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
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
* Using NGEngine to work with NGECore2 is making a combined work based on NGEngine.
* Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
******************************************************************************/
package resources.objects.guild;

import java.util.ArrayList;
import java.util.List;

import resources.objects.CurrentServerGCWZoneInfo;
import resources.objects.CurrentServerGCWZonePercent;
import resources.objects.Guild;
import resources.objects.OtherServerGCWZonePercent;

import com.sleepycat.persist.model.NotPersistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;


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
	private int serverId = 0x41000000;
	//private String STFName = "string_id_table";
	private int unknown1 = 0;
	private short unknown2 = 0;

	private List<CurrentServerGCWZonePercent> currentServerGCWZonePercentList = new ArrayList<CurrentServerGCWZonePercent>();
	@NotPersistent
	private int currentServerGCWZonePercentListUpdateCounter = 0;

	private List<CurrentServerGCWZonePercent> currentServerGCWTotalPercentList = new ArrayList<CurrentServerGCWZonePercent>();
	@NotPersistent
	private int currentServerGCWTotalPercentListUpdateCounter = 0;

	private List<CurrentServerGCWZoneInfo> currentServerGCWZoneInfoList = new ArrayList<CurrentServerGCWZoneInfo>();
	@NotPersistent
	private int currentServerGCWZoneInfoListUpdateCounter = 0;
	
	private List<CurrentServerGCWZoneInfo> currentServerGCWTotalInfoList = new ArrayList<CurrentServerGCWZoneInfo>();
	@NotPersistent
	private int currentServerGCWTotalInfoListUpdateCounter = 0;

	private List<OtherServerGCWZonePercent> otherServerGCWZonePercentList = new ArrayList<OtherServerGCWZonePercent>();
	@NotPersistent
	private int otherServerGCWZonePercentListUpdateCounter = 0;

	private List<OtherServerGCWZonePercent> otherServerGCWTotalPercentList = new ArrayList<OtherServerGCWZonePercent>();
	@NotPersistent
	private int otherServerGCWTotalPercentListUpdateCounter = 0;
	
	@NotPersistent
	private GuildMessageBuilder messageBuilder;
	
	public GuildObject() {
		super();
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
	
	public List<CurrentServerGCWZonePercent> getCurrentServerGCWZonePercentList() {
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
	
	public List<CurrentServerGCWZonePercent> getCurrentServerGCWTotalPercentList() {
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
	
	public List<CurrentServerGCWZoneInfo> getCurrentServerGCWZoneInfoList() {
		return currentServerGCWZoneInfoList;
	}
	
	public int getCurrentServerGCWZoneInfoListUpdateCounter() {
		synchronized(objectMutex) {
			return currentServerGCWZoneInfoListUpdateCounter;
		}
	}
	
	public void setCurrentServerGCWZoneInfoListUpdateCounter(int currentServerGCWZoneInfoListUpdateCounter) {
		synchronized(objectMutex) {
			this.currentServerGCWZoneInfoListUpdateCounter = currentServerGCWZoneInfoListUpdateCounter;
		}
	}
	
	public List<CurrentServerGCWZoneInfo> getCurrentServerGCWTotalInfoList() {
		return currentServerGCWTotalInfoList;
	}
	
	public int getCurrentServerGCWTotalInfoListUpdateCounter() {
		synchronized(objectMutex) {
			return currentServerGCWTotalInfoListUpdateCounter;
		}
	}
	
	public void setCurrentServerGCWTotalInfoListUpdateCounter(int currentServerGCWTotalInfoListUpdateCounter) {
		synchronized(objectMutex) {
			this.currentServerGCWTotalInfoListUpdateCounter = currentServerGCWTotalInfoListUpdateCounter;
		}
	}
	
	public List<OtherServerGCWZonePercent> getOtherServerGCWZonePercentList() {
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
	
	public List<OtherServerGCWZonePercent> getOtherServerGCWTotalPercentList() {
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
