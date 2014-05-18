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

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.AString;
import engine.resources.objects.Baseline;
import engine.resources.objects.Delta;

public class RegionDefender extends Delta {
	
	private static final long serialVersionUID = 1L;
	
	private AString region;
	private byte byte1 = 0;
	private byte byte2 = 0;
	
	public RegionDefender(String region, byte byte1, byte byte2) {
		this.region = new AString(region);
		this.byte1 = byte1;
		this.byte2 = byte2;
	}
	
	public RegionDefender(String region) {
		this.region = new AString(region);
	}
	
	public RegionDefender() {
		this.region = new AString();
	}
	
	public String getRegion() {
		synchronized(objectMutex) {
			return region.get();
		}
	}
	
	public void setRegion(String region) {
		synchronized(objectMutex) {
			this.region = new AString(region);
		}
	}
	
	public byte getByte1() {
		synchronized(objectMutex) {
			return byte1;
		}
	}
	
	public void setByte1(byte byte1) {
		synchronized(objectMutex) {
			this.byte1 = byte1;
		}
	}
	
	public byte getByte2() {
		synchronized(objectMutex) {
			return byte2;
		}
	}
	
	public void setByte2(byte byte2) {
		synchronized(objectMutex) {
			this.byte2 = byte2;
		}
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = Baseline.createBuffer(region.getBytes().length + 2);
			buffer.put(region.getBytes());
			buffer.put(byte1);
			buffer.put(byte2);
			return buffer.array();
		}
	}
	
}
