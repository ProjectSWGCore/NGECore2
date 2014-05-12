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
package services.collections;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.Persistent;

import engine.resources.objects.Delta;

@Persistent
public class ServerFirst extends Delta implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;
	private String collection;
	private long time;
	private long objectId;
	
	public ServerFirst(String name, long objectId, String collection, long time) {
		this.name = name;
		this.objectId = objectId;
		this.time = time;
		this.collection = collection;
	}
	
	public ServerFirst() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public long getTime() {
		return time;
	}
	
	public long getObjectId() {
		return objectId;
	}
	
	public String getCollection() {
		return collection;
	}
	
	@Override
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = createBuffer(18 + collection.length() + (name.length() * 2));
			buffer.putInt((int) time / 1000);
			buffer.put(getAsciiString(collection));
			buffer.putLong(objectId);
			buffer.put(getUnicodeString(name));
			return buffer.array();
		}
	}
	
}
