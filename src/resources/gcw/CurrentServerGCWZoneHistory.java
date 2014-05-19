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
package resources.gcw;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.Delta;

public class CurrentServerGCWZoneHistory extends Delta implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 1L;
	private int lastUpdateTime;
	private int percent;
	
	public CurrentServerGCWZoneHistory(CurrentServerGCWZonePercent zone) {
		this.percent = zone.getPercent().intValue();
		this.lastUpdateTime = zone.getLastUpdateTime();
	}
	
	public CurrentServerGCWZoneHistory() {
		
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
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = createBuffer(8);
			buffer.putInt(lastUpdateTime);
			buffer.putInt(percent);
			return buffer.array();
		}
	}
	
	public CurrentServerGCWZoneHistory clone() {
		try {
			return (CurrentServerGCWZoneHistory) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
