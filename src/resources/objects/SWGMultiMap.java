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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.python.google.common.collect.ArrayListMultimap;
import org.python.google.common.collect.Multimap;
import org.python.google.common.collect.Multiset;
import org.python.google.common.collect.Ordering;
import org.python.google.common.collect.TreeMultimap;

import resources.common.StringUtilities;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

@SuppressWarnings("unused")

@Persistent
public class SWGMultiMap<K, V> implements Multimap<K, V> {
	
	private Multimap<K, V> map = ArrayListMultimap.create();
	@NotPersistent
	private int updateCounter = 0;
	private ObjectMessageBuilder messageBuilder;
	private byte viewType;
	private short updateType;
	@NotPersistent
	protected final Object objectMutex = new Object();
	
	public SWGMultiMap() { }
	
	public SWGMultiMap(ObjectMessageBuilder messageBuilder, int viewType, int updateType) {
		this.messageBuilder = messageBuilder;
		this.viewType = (byte) viewType;
		this.updateType = (short) updateType;
	}
	
	public SWGMultiMap(Multimap<K, V> m) {
		if (m instanceof SWGMultiMap) {
			this.messageBuilder = ((SWGMultiMap<K, V>) m).messageBuilder;
			this.viewType = ((SWGMultiMap<K, V>) m).viewType;
			this.updateType = ((SWGMultiMap<K, V>) m).updateType;
			map.putAll(m);
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
			if (key instanceof String || key instanceof Byte || key instanceof Short ||
				key instanceof Integer || key instanceof Float || key instanceof Long ||
				value instanceof IListObject) {
				if (map.put(key, value)) {
					queue(item(0, (String) key, ((IListObject) value).getBytes(), true, true));
					return true;
				}
			}
			
			return false;
		}
	}
	
	public boolean putAll(K key, Iterable<? extends V> values) {
		synchronized(objectMutex) {
			if (key instanceof String || key instanceof Byte || key instanceof Short ||
				key instanceof Integer || key instanceof Float || key instanceof Long) {
				List<byte[]> buffer = new ArrayList<byte[]>();
				
				for (V value : values) {
					if (value instanceof IListObject) {
						if (map.put(key, value)) {
							buffer.add(item(0, (String) key, ((IListObject) value).getBytes(), true, true));
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
				
				if (key instanceof String || key instanceof Byte || key instanceof Short ||
					key instanceof Integer || key instanceof Float || key instanceof Long ||
					value instanceof IListObject) {
					if (this.map.put(key, value)) {
						buffer.add(item(0, (String) key, ((IListObject) value).getBytes(), true, true));
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
	
	@SuppressWarnings("unchecked")
	public boolean remove(Object key, Object value) {
		synchronized(objectMutex) {
			if (key instanceof String || key instanceof Byte || key instanceof Short ||
				key instanceof Integer || key instanceof Float || key instanceof Long) {
				if (map.remove(key, value)) {
					queue(item(1, (String) key, ((IListObject) map.get((K) key)).getBytes(), true, true));
					
					return true;
				}
			}
			
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<V> removeAll(Object key) {
		synchronized(objectMutex) {
			if (key instanceof String || key instanceof Byte || key instanceof Short ||
				key instanceof Integer || key instanceof Float || key instanceof Long) {
				Collection<V> collection = map.get((K) key);
				List<byte[]> buffer = new ArrayList<byte[]>();
				
				for (V value : map.get((K) key)) {
					if (map.remove(key, value)) {
						buffer.add(item(1, (String) key, ((IListObject) map.get((K) key)).getBytes(), true, true));
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
			if (key instanceof String || key instanceof Byte || key instanceof Short ||
				key instanceof Integer || key instanceof Float || key instanceof Long) {
				if (map.containsKey(key)) {
					List<byte[]> buffer = new ArrayList<byte[]>();
					
					for (V value : values) {
						if (value instanceof IListObject) {
							if (!map.get(key).contains(value)) {
								buffer.add(item(2, (String) key, ((IListObject) value).getBytes(), true, true));
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
	
	private byte[] item(int type, Object index, byte[] data, boolean useIndex, boolean useData) {
		if (useIndex && !(index instanceof Byte) && !(index instanceof Short)
			&& !(index instanceof Integer) && !(index instanceof Float)
			&& !(index instanceof Long) && !(index instanceof String)) {
			throw new IllegalArgumentException();
		}
		
		int size = 1 + ((useIndex) ? (2 + index.toString().getBytes().length) : 0) + ((useData) ? data.length : 0);
		
		IoBuffer buffer = messageBuilder.bufferPool.allocate((size), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) type);
		if (useIndex) {
			if (index instanceof String) {
				buffer.put(StringUtilities.getAsciiString((String) index));
			} else if (index instanceof Byte) {
				buffer.put(((Byte) index).byteValue());
			} else if (index instanceof Short) {
				buffer.putShort(((Short) index).shortValue());
			} else if (index instanceof Integer) {
				buffer.putInt(((Integer) index).intValue());
			} else if (index instanceof Float) {
				buffer.putFloat(((Float) index).floatValue());
			} else if (index instanceof Long) {
				buffer.putLong(((Long) index).longValue());
			} else {
				throw new IllegalArgumentException();
			}
		}
		if (useData) buffer.put(data);
		
		updateCounter++;
		
		return buffer.array();
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
