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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.IDelta;
import resources.z.exp.objects.object.BaseObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

/* A SWGSet element should extend Delta or implement IDelta */

@Persistent
public class SWGSet<E> implements Set<E> {
	
	private TreeSet<E> set = new TreeSet<E>();
	@NotPersistent
	private int updateCounter = 0;
	private BaseObject object;
	private byte viewType;
	private short updateType;
	private boolean addByte;
	@NotPersistent
	protected final Object objectMutex = new Object();
	
	public SWGSet() { }
	
	public SWGSet(BaseObject object, int viewType, int updateType, boolean addByte) {
		this.object = object;
		this.viewType = (byte) viewType;
		this.updateType = (short) updateType;
		this.addByte = addByte;
	}
	
	public SWGSet(Set<E> s) {
		if (s instanceof SWGSet) {
			this.object = ((SWGSet<E>) s).object;
			this.viewType = ((SWGSet<E>) s).viewType;
			this.updateType = ((SWGSet<E>) s).updateType;
			set.addAll(s);
		}
	}
	
	public boolean add(E e) {
		synchronized(objectMutex) {
			if (valid(e)) {
				if (set.add(e)) {
					queue(item(1, e, null, true, false));
					return true;
				}
			}
			
			return false;
		}
	}
	
	public boolean addAll(Collection<? extends E> c) {
		synchronized(objectMutex) {
			List<byte[]> buffer = new ArrayList<byte[]>();
			
			for (E e : c) {
				if (valid(e) && set.add(e)) {
					buffer.add(item(1, e, null, true, false));
				}
			}
			
			if (buffer.size() > 0) {
				queue(buffer);
				return true;
			} else {
				return false;
			}
		}
	}
	
	public void clear() {
		synchronized(objectMutex) {
			queue(item(2, null, null, false, false));
		}
	}
	
	public boolean contains(Object o) {
		synchronized(objectMutex) {
			return set.contains(o);
		}
	}
	
	public boolean containsAll(Collection<?> c) {
		synchronized(objectMutex) {
			return set.containsAll(c);
		}
	}
	
	public boolean isEmpty() {
		synchronized(objectMutex) {
			return set.isEmpty();
		}
	}
	
	public Iterator<E> iterator() {
		synchronized(objectMutex) {
			return set.iterator();
		}
	}
	
	public boolean remove(Object e) {
		synchronized(objectMutex) {
			if (valid(e) && set.remove(e)) {
				queue(item(0, e, null, true, false));
				return true;
			}
			
			return false;
		}
	}
	
	public boolean removeAll(Collection<?> c) {
		synchronized(objectMutex) {
			List<byte[]> buffer = new ArrayList<byte[]>();
			
			for (Object o : c) {
				if (valid(o) && set.remove(o)) {
					buffer.add(item(0, o, null, true, false));
				}
			}
			
			if (buffer.size() > 0) {
				queue(buffer);
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean retainAll(Collection<?> c) {
		synchronized(objectMutex) {
			return set.retainAll(c);
		}
	}
	
	public int size() {
		synchronized(objectMutex) {
			return set.size();
		}
	}
	
	public Object[] toArray() {
		synchronized(objectMutex) {
			return set.toArray();
		}
	}
	
	public <T> T[] toArray(T[] a) {
		synchronized(objectMutex) {
			return set.toArray(a);
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
			
			for (Object o : set) {
				byte[] object = Baseline.toBytes(o);
				size += object.length;
				
				IoBuffer buffer = Baseline.createBuffer(size);
				buffer.put(objects);
				if (addByte) buffer.put((byte) 0);
				buffer.put(object);
				buffer.flip();
				
				objects = buffer.array();
			}
			
			IoBuffer buffer = Baseline.createBuffer(8 + size);
			buffer.putInt(set.size());
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
		IoBuffer buffer = IoBuffer.allocate((data.length + 8), false).order(ByteOrder.LITTLE_ENDIAN);
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
