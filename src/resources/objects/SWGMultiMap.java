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
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mina.core.buffer.IoBuffer;
import org.python.google.common.collect.ArrayListMultimap;
import org.python.google.common.collect.Multimap;
import org.python.google.common.collect.Multiset;

import engine.resources.objects.Baseline;
import engine.resources.objects.Delta;
import engine.resources.objects.IDelta;
import engine.resources.objects.SWGObject;

public class SWGMultiMap<K, V> implements Multimap<K, V>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private Multimap<K, V> map = ArrayListMultimap.create();
	private transient int updateCounter = 0;
	private byte viewType;
	private short updateType;
	private boolean addByte;
	protected transient Object objectMutex = new Object();
	private transient SWGObject object;
	
	public SWGMultiMap() { }
	
	public SWGMultiMap(SWGObject object, int viewType, int updateType, boolean addByte) {
		this.viewType = (byte) viewType;
		this.updateType = (short) updateType;
		this.addByte = addByte;
		this.object = object;
	}
	
	public SWGMultiMap(Multimap<K, V> m) {
		if (m instanceof SWGMultiMap) {
			this.object = ((SWGMultiMap<K, V>) m).object;
			this.viewType = ((SWGMultiMap<K, V>) m).viewType;
			this.updateType = ((SWGMultiMap<K, V>) m).updateType;
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

	public Map<K, Collection<V>> asMap() {
		synchronized(objectMutex) {
			return map.asMap();
		}
	}
	
	public void clear() {
		throw new UnsupportedOperationException();
	}
	
	public boolean containsEntry(Object key, Object value) {
		synchronized(objectMutex) {
			return map.containsEntry(key, value);
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
	
	public Collection<Entry<K, V>> entries() {
		synchronized(objectMutex) {
			return map.entries();
		}
	}
	
	public Collection<V> get(K key) {
		synchronized(objectMutex) {
			return map.get(key);
		}
	}
	
	public boolean isEmpty() {
		synchronized(objectMutex) {
			return map.isEmpty();
		}
	}
	
	public Multiset<K> keys() {
		synchronized(objectMutex) {
			return map.keys();
		}
	}
	
	public Set<K> keySet() {
		synchronized(objectMutex) {
			return map.keySet();
		}
	}
	
	public boolean put(K key, V value) {
		synchronized(objectMutex) {
			if (valid(key) && valid(value)) {
				if (map.put(key, value)) {
					queue(item(0, key, Baseline.toBytes(value), true, true));
					return true;
				}
			}
			
			return false;
		}
	}
	
	public boolean putAll(K key, Iterable<? extends V> values) {
		synchronized(objectMutex) {
			if (valid(key)) {
				List<byte[]> buffer = new ArrayList<byte[]>();
				
				for (V value : values) {
					if (valid(value)) {
						if (map.put(key, value)) {
							buffer.add(item(0, key, Baseline.toBytes(value), true, true));
						}
					}
				}
				
				if (buffer.size() > 0) {
					queue(buffer);
					return true;
				}
			}
			
			return false;
		}
	}
	
	public boolean putAll(Multimap<? extends K, ? extends V> map) {
		synchronized(objectMutex) {
			List<byte[]> buffer = new ArrayList<byte[]>();
			
			for (Entry<? extends K, ? extends V> entry : map.entries()) {
				K key = entry.getKey();
				V value = entry.getValue();
				
				if (valid(key) && valid(value)) {
					if (this.map.put(key, value)) {
						buffer.add(item(0, key, Baseline.toBytes(value), true, true));
					}
				}
			}
			
			if (buffer.size() > 0) {
				queue(buffer);
				return true;
			}
			
			return false;
		}
	}
	
	public boolean remove(Object key, Object value) {
		synchronized(objectMutex) {
			if (valid(key)) {
				if (map.remove(key, value)) {
					queue(item(1, key, Baseline.toBytes(value), true, true));
					
					return true;
				}
			}
			
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<V> removeAll(Object key) {
		synchronized(objectMutex) {
			if (valid(key)) {
				Collection<V> collection = map.get((K) key);
				List<byte[]> buffer = new ArrayList<byte[]>();
				
				for (V value : map.get((K) key)) {
					if (map.remove(key, value)) {
						buffer.add(item(1, key, Baseline.toBytes(value), true, true));
					}
				}
				
				if (buffer.size() > 0) {
					queue(buffer);
					return collection;
				}
			}
			
			return null;
		}
	}
	
	public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
		synchronized(objectMutex) {
			if (valid(key)) {
				if (map.containsKey(key)) {
					List<byte[]> buffer = new ArrayList<byte[]>();
					
					for (V value : values) {
						if (valid(value)) {
							if (!map.get(key).contains(value)) {
								buffer.add(item(2, key, Baseline.toBytes(value), true, true));
							}
						} else {
							return null;
						}
					}
					
					if (buffer.size() > 0) {
						queue(buffer);
						return map.replaceValues(key, values);
					}
				}
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
			
			for (Entry<?, ?> entry : map.entries()) {
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
		if (useIndex && !(index instanceof Byte) && !(index instanceof Short)
			&& !(index instanceof Integer) && !(index instanceof Float)
			&& !(index instanceof Long) && !(index instanceof String)) {
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
		IoBuffer buffer = Delta.createBuffer((data.length + 8));
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
