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
package resources.z.exp.objects;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.IDelta;
import resources.z.exp.objects.object.BaseObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

/* A SWGMap element should extend Delta or implement IDelta */

@Persistent
public class SWGMap<K, V> implements Map<K, V> {
	
	private Map<K, V> map = new TreeMap<K, V>();
	@NotPersistent
	private int updateCounter = 0;
	private BaseObject object;
	private byte viewType;
	private short updateType;
	private boolean addByte;
	@NotPersistent
	protected final Object objectMutex = new Object();
	
	public SWGMap() { }
	
	public SWGMap(BaseObject object, int viewType, int updateType, boolean addByte) {
		this.object = object;
		this.viewType = (byte) viewType;
		this.updateType = (short) updateType;
		this.addByte = addByte;
	}
	
	public SWGMap(Map<K, V> m) {
		if (m instanceof SWGMap) {
			this.object = ((SWGMap<K, V>) m).object;
			this.viewType = ((SWGMap<K, V>) m).viewType;
			this.updateType = ((SWGMap<K, V>) m).updateType;
			map.putAll(m);
		}
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
		return map.get(key);
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
			if (valid(key) && valid(value)) {
				if (map.containsKey(key)) {
					V oldValue = map.put(key, value);
					
					queue(item(2, key, Baseline.toBytes(value), true, true));
					
					return oldValue;
				} else {
					V oldValue = map.put(key, value);
					
					queue(item(0, key, Baseline.toBytes(value), true, true));
					
					return oldValue;
				}
			}
			
			return null;
		}
	}
	
	public void putAll(Map<? extends K, ? extends V> m) {
		synchronized(objectMutex) {
			List<byte[]> buffer = new ArrayList<byte[]>();
			
			for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
				K key = entry.getKey();
				V value = entry.getValue();
				
				if (valid(key) && valid(value)) {
					if (map.containsKey(key)) {
						if (map.put(key, value) != null) {
							buffer.add(item(2, key, Baseline.toBytes(value), true, true));
						}
					} else {
						if (map.put(key, value) != null) {
							buffer.add(item(0, key, Baseline.toBytes(value), true, true));
						}
					}
				}
			}
			
			if (buffer.size() > 0) {
				queue(buffer);
			}
		}
	}
	
	public V remove(Object key) {
		synchronized(objectMutex) {
			if (valid(key)) {
				V value = map.remove(key);
				
				queue(item(1, key, Baseline.toBytes(map.get(key)), true, true));
				
				return value;
			}
			
			return null;
		}
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
		synchronized(objectMutex) {
			return updateCounter;
		}
	}
	
	public Object getMutex() {
		return objectMutex;
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			byte[] objects = { };
			int size = 0;
			
			for (Entry<?, ?> entry : map.entrySet()) {
				byte[] key = Baseline.toBytes(entry.getKey());
				byte[] value = Baseline.toBytes(entry.getValue());
				size += ((addByte) ? 1 : 0) + key.length + value.length;
				
				IoBuffer buffer = Baseline.createBuffer(size);
				buffer.put(objects);
				if (addByte) buffer.put((byte) 0);
				buffer.put(key);
				buffer.put(value);
				buffer.flip();
				
				objects = buffer.array();
			}
			
			IoBuffer buffer = Baseline.createBuffer(8 + size);
			buffer.putInt(map.size());
			buffer.putInt(updateCounter);
			buffer.put(objects);
			buffer.flip();
			
			return buffer.array();
		}
	}
	
	private boolean valid(Object o) {
		if (o instanceof String || o instanceof Byte || o instanceof Short ||
		o instanceof Integer || o instanceof Float || o instanceof Long ||
		o instanceof IDelta) {
			return true;
		} else {
			return false;
		}
	}
	
	private byte[] item(int type, Object index, byte[] data, boolean useIndex, boolean useData) {
		if (useIndex && ((index instanceof IDelta) || !valid(index))) {
			throw new IllegalArgumentException();
		}
		
		int size = 1 + ((useIndex) ? (2 + Baseline.toBytes(index).length) : 0) + ((useData) ? data.length : 0);
		
		IoBuffer buffer = IoBuffer.allocate(size, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) type);
		if (useIndex) buffer.put(Baseline.toBytes(index));
		if (useData) buffer.put(data);
		buffer.flip();
		
		updateCounter++;
		
		return buffer.array();
	}

	private void queue(byte[] data) {
		IoBuffer buffer = IoBuffer.allocate(data.length + 8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(updateCounter);
		buffer.put(data);
		buffer.flip();
		object.sendListDelta(viewType, updateType, buffer);
	}
	
	private void queue(List<byte[]> data) {
		int size = 0;
		
		for (byte[] queued : data) {
			size += queued.length;
		}
		
		IoBuffer buffer = IoBuffer.allocate((size + 8), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(data.size());
		buffer.putInt(updateCounter);
		for (byte[] queued : data) buffer.put(queued);
		buffer.flip();
		
		object.sendListDelta(viewType, updateType, buffer);
	}
	
}
