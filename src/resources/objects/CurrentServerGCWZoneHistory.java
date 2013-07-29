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
package resources.objects;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

public class CurrentServerGCWZoneHistory extends ListObject {
	
	private byte unknown1 = 0;
	private String zone = "";
	private int lastUpdateTime = ((int) System.currentTimeMillis());
	private int percent = 50;
	
	public CurrentServerGCWZoneHistory(String zone, int percent) {
		this.zone = zone;
		this.percent = percent;
	}
	
	public CurrentServerGCWZoneHistory(String zone) {
		this.zone = zone;
	}
	
	public byte getUnknown1() {
		synchronized(objectMutex) {
			return unknown1;
		}
	}
	
	public void setUnknown1(byte unknown1) {
		synchronized(objectMutex) {
			this.unknown1 = unknown1;
		}
	}
	
	public String getZone() {
		synchronized(objectMutex) {
			return zone;
		}
	}
	
	public int getLastUpdateTime() {
		synchronized(objectMutex) {
			return lastUpdateTime;
		}
	}
	
	public int getPercent() {
		synchronized(objectMutex) {
			return percent;
		}
	}
	
	public void setPercent(int percent) {
		synchronized(objectMutex) {
			this.percent = percent;
			this.lastUpdateTime = ((int) System.currentTimeMillis());
		}
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = bufferPool.allocate((8 + zone.length() + 2), false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.put(getAsciiString(zone));
			buffer.putInt(lastUpdateTime);
			buffer.putInt(percent);
			return buffer.array();
		}
	}
	
}
