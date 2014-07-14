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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.Baseline;
import engine.resources.objects.Delta;
import engine.resources.objects.IDelta;
import engine.resources.objects.SWGObject;

/* A SWGMap element should extend Delta or implement IDelta */

public class SWGMap<K, V> implements Map<K, V>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private Map<K, V> map = new TreeMap<K, V>();
	private transient int updateCounter = 0;
	private byte viewType;
	private short updateType;
	private boolean addByte;
	protected transient Object objectMutex = new Object();
	private transient SWGObject object;
	
	public SWGMap() { }
	
	public SWGMap(SWGObject object, int viewType, int updateType, boolean addByte) {
		this.viewType = (byte) viewType;
		this.updateType = (short) updateType;
		this.addByte = addByte;
		this.object = object;
	}
	
	public SWGMap(Map<K, V> m) {
		if (m instanceof SWGMap) {
			this.object = ((SWGMap<K, V>) m).object;
			this.viewType = ((SWGMap<K, V>) m).viewType;
			this.updateType = ((SWGMap<K, V>) m).updateType;
			map.putAll(m);
		}
	}
	
	public void init(SWGObject object) {
		objectMutex = new Object();
		updateCounter = 0;
		this.object = object;
		
		for (Object item : map.values()) {				
			try {
				if (item instanceof IDelta) {
					item.getClass().getMethod("init", new Class[] { SWGObject.class }).invoke(item, new Object[] { object });
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void clear() {
		throw new UnsupportedOperationException();
	}
	
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		synchronized(objectMutex) {
			return map.computeIfAbsent(key, mappingFunction);
		}
	}
	
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		synchronized(objectMutex) {
			return map.computeIfPresent(key, remappingFunction);
		}
	}
	
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		synchronized(objectMutex) {
			return map.compute(key, remappingFunction);
		}
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
	
	public void forEach(BiConsumer<? super K, ? super V> action) {
		map.forEach(action);
	}
	
	public V get(Object key) {
		synchronized(objectMutex) {
			return map.get(key);
		}
	}
	
	public V getOrDefault(Object key, V defaultValue) {
		synchronized(objectMutex) {
			return map.getOrDefault(key, defaultValue);
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
	
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		System.err.println("SWGMap::merge: Not implemented.");
		return null;
	}
	
	public V put(K key, V value) {
		synchronized(objectMutex) {
			if (valid(key) && valid(value)) {
				if (map.containsKey(key)) {
					V oldValue = map.put(key, value);
					
					if (oldValue != null) {
						queue(item(2, key, Baseline.toBytes(value), true, true));
					}
					
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
				
				if (key != null && valid(key) && value != null && valid(value)) {
					if (map.containsKey(key)) {
						map.put(key, value);
						buffer.add(item(2, key, Baseline.toBytes(value), true, true));
					} else {
						map.put(key, value);
						buffer.add(item(0, key, Baseline.toBytes(value), true, true));
					
					}
				}
			}
			
			if (buffer.size() > 0) {
				queue(buffer);
			}
		}
	}
	
	public V putIfAbsent(K key, V value) {
		if (!map.containsKey(key)) {
			return put(key, value);
		}
		
		return null;
	}
	
	public V remove(Object key) {
		synchronized(objectMutex) {
			if (valid(key)) {
				V value = map.remove(key);
				
				if (value != null) {
					queue(item(1, key, Baseline.toBytes(value), true, true));
				}
				
				return value;
			}
			
			return null;
		}
	}
	
	public boolean remove(Object key, Object value) {
		synchronized(objectMutex) {
			if (valid(key) && valid(value)) {
				boolean removed = map.remove(key, value);
				
				if (removed) {
					queue(item(1, key, Baseline.toBytes(value), true, true));
				}
				
				return removed;
			}
			
			return false;
		}
	}
	
	public V replace(K key, V value) {
		synchronized(objectMutex) {
			if (map.containsKey(key)) {
				return put(key, value);
			}
			
			return null;
		}
	}
	
	public boolean replace(K key, V oldValue, V newValue) {
		synchronized(objectMutex) {
			if (map.containsKey(key) && map.containsValue(oldValue)) {
				put(key, newValue);
				return true;
			}
			
			return false;
		}
	}
	
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		System.err.println("SWGMap::replaceAll: Not implemented.");
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
		if (useIndex && ((index instanceof IDelta) && !valid(index))) {
			throw new IllegalArgumentException();
		}
		
		int size = 1 + ((useIndex) ? (Baseline.toBytes(index).length) : 0) + ((useData) ? data.length : 0);
		
		IoBuffer buffer = Delta.createBuffer(size);
		buffer.put((byte) type);
		if (useIndex) buffer.put(Baseline.toBytes(index));
		if (useData) buffer.put(data);
		buffer.flip();
		
		updateCounter++;
		
		return buffer.array();
	}
	
	private void queue(byte[] data) {
		IoBuffer buffer = Delta.createBuffer(data.length + 8);
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
		
		IoBuffer buffer = Delta.createBuffer((size + 8));
		buffer.putInt(data.size());
		buffer.putInt(updateCounter);
		for (byte[] queued : data) buffer.put(queued);
		buffer.flip();
		
		object.sendListDelta(viewType, updateType, buffer);
	}
	
}
