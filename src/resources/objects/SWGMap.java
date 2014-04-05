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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.StringUtilities;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

@SuppressWarnings("unused")

@Persistent
public class SWGMap<K, V> implements Map<K, V> {
	
	private Map<K, V> map = new TreeMap<K, V>();
	@NotPersistent
	private int updateCounter = 0;
	private ObjectMessageBuilder messageBuilder;
	private byte viewType;
	private short updateType;
	@NotPersistent
	protected final Object objectMutex = new Object();
	
	public SWGMap() { }
	
	public SWGMap(ObjectMessageBuilder messageBuilder, int viewType, int updateType) {
		this.messageBuilder = messageBuilder;
		this.viewType = (byte) viewType;
		this.updateType = (short) updateType;
	}
	
	public SWGMap(Map<K, V> m) {
		if (m instanceof SWGMap) {
			this.messageBuilder = ((SWGMap<K, V>) m).messageBuilder;
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
			if ((key instanceof String || key instanceof Byte || key instanceof Short ||
				key instanceof Integer || key instanceof Float ||key instanceof Long) &&
				value instanceof IDelta) {
				if (map.containsKey(key)) {
					V oldValue = map.put(key, value);
					
					if (oldValue != null) {
						queue(item(2, (String) key, ((IDelta) value).getBytes(), true, true));
					}
					
					return oldValue;
				} else {
					V oldValue = map.put(key, value);
					
					if (oldValue != null) {
						queue(item(0, (String) key, ((IDelta) value).getBytes(), true, true));
					}
					
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
				
				if (key instanceof String || key instanceof Byte || key instanceof Short ||
					key instanceof Integer || key instanceof Float || key instanceof Long ||
					value instanceof IDelta) {
					if (map.containsKey(key)) {
						if (map.put(key, value) != null) {
							buffer.add(item(2, (String) key, ((IDelta) value).getBytes(), true, true));
						}
					} else {
						if (map.put(key, value) != null) {
							buffer.add(item(0, (String) key, ((IDelta) value).getBytes(), true, true));
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
			if (key instanceof String || key instanceof Byte || key instanceof Short ||
				key instanceof Integer || key instanceof Float || key instanceof Long) {
				V value = map.remove(key);
				
				if (value != null) {
					queue(item(1, key, ((IDelta) map.get(key)).getBytes(), true, true));
				}
				
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
			buffer.flip();
				
			updateCounter++;
			
			return buffer.array();
	}

	private void queue(byte[] data) {
		IoBuffer buffer = messageBuilder.bufferPool.allocate((data.length + 8), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(updateCounter);
		buffer.put(data);
		buffer.flip();
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
		buffer.flip();
		
		messageBuilder.sendListDelta(viewType, updateType, buffer);
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceAll(
			BiFunction<? super K, ? super V, ? extends V> function) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V putIfAbsent(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object key, Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V replace(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V computeIfAbsent(K key,
			Function<? super K, ? extends V> mappingFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V computeIfPresent(K key,
			BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V compute(K key,
			BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V merge(K key, V value,
			BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
