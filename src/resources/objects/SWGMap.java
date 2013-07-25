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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.StringUtilities;

import com.sleepycat.persist.model.NotPersistent;

@SuppressWarnings("unused")

public class SWGMap<K, V> implements Map<K, V> {
	
	private Map<K, V> map = new TreeMap<K, V>();
	@NotPersistent
	private int updateCounter;
	
	private ObjectMessageBuilder messageBuilder;
	private byte viewType;
	private short updateType;
	
	protected final Object objectMutex = new Object();
	
	public SWGMap(ObjectMessageBuilder messageBuilder, int viewType, int updateType) {
		this.messageBuilder = messageBuilder;
		this.viewType = (byte) viewType;
		this.updateType = (short) updateType;
	}
	
	public void clear() {
		throw new UnsupportedOperationException();
	}
	
	public boolean containsKey(Object key) {
		synchronized(objectMutex) {
			return map.containsKey(key);
		}
	}
	
	public boolean containsValue(Object value) {
		synchronized(objectMutex) {
			return map.containsValue(value);
		}
	}
	
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		synchronized(objectMutex) {
			return map.entrySet();
		}
	}
	
	public V get(Object key) {
		synchronized(objectMutex) {
			return map.get(key);
		}
	}
	
	public boolean isEmpty() {
		synchronized(objectMutex) {
			return map.isEmpty();
		}
	}
	
	public Set<K> keySet() {
		synchronized(objectMutex) {
			return map.keySet();
		}
	}
	
	public V put(K key, V value) {
		synchronized(objectMutex) {
			if (key instanceof String && value instanceof IListObject) {
				if (map.containsKey(key)) {
					// Changing an existing map
					V oldValue = map.put(key, value);
					
					queue(item(2, key, ((IListObject) value).getBytes(), true, true));
					
					return oldValue;
				} else {
					// Adding to the map
					V oldValue = map.put(key, value);
					
					queue(item(0, key, ((IListObject) value).getBytes(), true, true));
					
					return oldValue;
				}
			}
			
			return null;
		}
	}
	
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}
	
	public V remove(Object arg0) {
		throw new UnsupportedOperationException();
	}
	
	public int size() {
		synchronized(objectMutex) {
			return map.size();
		}
	}
	
	public Collection<V> values() {
		synchronized(objectMutex) {
			return map.values();
		}
	}
	
	public int getUpdateCounter() {
		return updateCounter;
	}
	
	private byte[] item(int type, K index, byte[] data, boolean useIndex, boolean useData) {
		if (index instanceof String) {
			int size = 1 + ((useIndex) ? (2 + ((String) index).getBytes().length) : 0) + ((useData) ? data.length : 0);
				
			IoBuffer buffer = messageBuilder.bufferPool.allocate((size), false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.put((byte) type);
			if (useIndex) buffer.put(((index instanceof String) ? StringUtilities.getAsciiString((String) index) : Integer.toString(0).getBytes()));
			if (useData) buffer.put(data);
				
			updateCounter++;
				
			return buffer.array();
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void queue(byte[] data) {
		IoBuffer buffer = messageBuilder.bufferPool.allocate((data.length + 8), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(updateCounter);
		buffer.put(data);
		messageBuilder.sendListDelta(viewType, updateType, buffer);
	}
	
	private void queue(List<byte[]> data) {
		int size = 0;
		
		for (byte[] queued : data) {
			size += queued.length;
		}
		
		IoBuffer buffer = messageBuilder.bufferPool.allocate((size + 8), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(data.size());
		buffer.putInt(updateCounter);
		for (byte[] queued : data) buffer.put(queued);
		
		messageBuilder.sendListDelta(viewType, updateType, buffer);
	}
	
}
