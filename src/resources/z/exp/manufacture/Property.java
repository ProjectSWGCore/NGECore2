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
package resources.z.exp.manufacture;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.Persistent;

import resources.objects.Delta;

@Persistent
public class Property extends Delta {
	
	private int unused = 0;
	private String key = "";
	private float value = 0;
	
	public Property(String key, float value) {
		this.key = key;
		this.value = value;
	}
	
	public Property() {
		
	}
	
	public String getKey() {
		synchronized(objectMutex) {
			return key;
		}
	}
	
	public Property setKey(String key) {
		synchronized(objectMutex) {
			this.key = key;
			return this;
		}
	}
	
	public float getValue() {
		synchronized(objectMutex) {
			return value;
		}
	}
	
	public Property setValue(float value) {
		synchronized(objectMutex) {
			this.value = value;
			return this;
		}
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = bufferPool.allocate(8 + getAsciiString(key).length, false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.putInt(unused);
			buffer.put(getAsciiString(key));
			buffer.putFloat(value);
			return buffer.array();
		}
	}
	
}
