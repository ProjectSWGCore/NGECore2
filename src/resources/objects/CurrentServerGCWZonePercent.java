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

public class CurrentServerGCWZonePercent extends ListObject {
	
	private byte unknown1 = 0;
	private String zone = "";
	private int percent = 60;
	private int gcwPoints = 0;
	
	public CurrentServerGCWZonePercent(String zone) {
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
	
	public int getPercent() {
		synchronized(objectMutex) {
			return percent;
		}
	}
	
	public void setPercent(int percent, SWGList<CurrentServerGCWZoneHistory> historyList) {
		synchronized(objectMutex) {		
			int historyCount = 0;
			int firstHistoryIndex = historyList.get().size();
			int lastHistoryIndex = historyList.get().size();
			
			for (int i = 0; i < historyList.size(); i++) {
				if (historyList.get(i).getZone() == getZone()) {
					historyCount++;
					lastHistoryIndex = i;
					
					if (historyCount == 1) {
						firstHistoryIndex = i;
					}
				}
			}
			
			if (historyCount > 10) {
				historyList.remove(firstHistoryIndex);
			}
			
			historyList.add(lastHistoryIndex, new CurrentServerGCWZoneHistory(zone, percent));
			
			this.percent = percent;
		}
	}
	
	public int getGCWPoints() {
		synchronized(objectMutex) {
			return gcwPoints;
		}
	}
	
	public void addGCWPoints(int gcwPoints) {
		synchronized(objectMutex) {
			this.gcwPoints += gcwPoints;
		}
	}
	
	public void removeGCWPoints(int gcwPoints) {
		synchronized(objectMutex) {
			this.gcwPoints = (((this.gcwPoints - gcwPoints) < 0) ? 0 : this.gcwPoints - gcwPoints);
		}
	}

	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = bufferPool.allocate((4 + zone.length() + 2), false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.put(getAsciiString(zone));
			buffer.putInt(percent);
			return buffer.array();
		}
	}
	
}
