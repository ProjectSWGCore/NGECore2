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
public class TableAndKey extends Delta {
	
	private String table = "";
	private int unused = 0;
	private String key = "";
	
	public TableAndKey(String table, String key) {
		this.table = table;
		this.table = key;
	}
	
	public TableAndKey() {
		
	}
	
	public String getTable() {
		synchronized(objectMutex) {
			return table;
		}
	}
	
	public TableAndKey setTable(String table) {
		synchronized(objectMutex) {
			this.table = table;
			return this;
		}
	}
	
	public String getKey() {
		synchronized(objectMutex) {
			return key;
		}
	}
	
	public TableAndKey setKey(String key) {
		synchronized(objectMutex) {
			this.key = key;
			return this;
		}
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = bufferPool.allocate(4 + getAsciiString(table).length + getAsciiString(key).length, false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.put(getAsciiString(table));
			buffer.putInt(unused);
			buffer.put(getAsciiString(key));
			return buffer.array();
		}
	}
	
}
