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
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.apache.mina.core.buffer.IoBuffer;
import org.python.google.common.collect.Lists;

import engine.resources.objects.Baseline;
import engine.resources.objects.Delta;
import engine.resources.objects.IDelta;
import engine.resources.objects.SWGObject;

/* A SWGList element should extend Delta or implement IDelta */

public class SWGList<E> implements List<E>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<E> list = new CopyOnWriteArrayList<E>();
	private transient int updateCounter = 0;
	private byte viewType;
	private short updateType;
	private boolean addByte;
	protected transient Object objectMutex = new Object();
	private transient SWGObject object;
	
	public SWGList() { }
	
	public SWGList(SWGObject object, int viewType, int updateType, boolean addByte) {
		this.viewType = (byte) viewType;
		this.updateType = (short) updateType;
		this.addByte = addByte;
		this.object = object;
	}
	
	public void init(SWGObject object) {
		objectMutex = new Object();
		updateCounter = 0;
		this.object = object;
		
		for (Object item : list) {				
			try {
				if (item instanceof IDelta) {
					item.getClass().getMethod("init", new Class[] { SWGObject.class }).invoke(item, new Object[] { object });
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean add(E e) {
		synchronized(objectMutex) {				
			if (valid(e) && list.add(e)) {
				queue(item(1, list.lastIndexOf(e), Baseline.toBytes(e), true, true));
				return true;
			}
			
			return false;
		}
	}
	
	public void add(int index, E element) {
		synchronized(objectMutex) {
			if (valid(element)) {
				list.add(index, element);
				queue(item(1, index, Baseline.toBytes(element), true, true));
			}
		}
	}
	
	public boolean addAll(Collection<? extends E> c) {
		synchronized(objectMutex) {
			if (!c.isEmpty()) {
				List<byte[]> buffer = new ArrayList<byte[]>();
				boolean success = false;
				
				for (E element : c) {
					if (valid(element)) {
						if (list.add(element)) {
							buffer.add(item(1, list.lastIndexOf(element), Baseline.toBytes(element), true, true));
							success = true;
						}
					} else {
						return false;
					}
				}
				
				if (success == true) {
					queue(buffer);
				} else {
					return false;
				}
			}
			
			return false;
		}
	}
	
	public boolean addAll(int index, Collection<? extends E> c) {
		synchronized(objectMutex) {
			if (!c.isEmpty()) {
				List<byte[]> buffer = new ArrayList<byte[]>();
				
				for (E element : c) {
					if (valid(element)) {
						list.add(index, element);
						buffer.add(item(1, index, Baseline.toBytes(element), true, true));
						index++;
					} else {
						return false;
					}
				}
				
				queue(buffer);
				
				return true;
			}
			
			return false;
		}
	}
	
	public void clear() {
		synchronized(objectMutex) {
			list.clear();
			queue(item(4, 0, null, false, false));
		}
	}
	
	public boolean contains(Object o) {
		synchronized(objectMutex) {
			return list.contains(o);
		}
	}
	
	public boolean containsAll(Collection<?> c) {
		synchronized(objectMutex) {
			return list.containsAll(c);
		}
	}
	
	public void forEach(Consumer<? super E> action) {
		list.forEach(action);
	}
	
	public E get(int index) {
		synchronized(objectMutex) {
			return list.get(index);
		}
	}
	
	public List<E> get() {
		return list;
	}
	
	public int indexOf(Object o) {
		synchronized(objectMutex) {
			return list.indexOf(o);
		}
	}
	
	public boolean isEmpty() {
		synchronized(objectMutex) {
			return list.isEmpty();
		}
	}
	
	public Iterator<E> iterator() {
		synchronized(objectMutex) {
			return list.iterator();
		}
	}
	
	public int lastIndexOf(Object o) {
		synchronized(objectMutex) {
			return list.lastIndexOf(o);
		}
	}
	
	public ListIterator<E> listIterator() {
		synchronized(objectMutex) {
			return list.listIterator();
		}
	}
	
	public ListIterator<E> listIterator(int index) {
		synchronized(objectMutex) {
			return listIterator(index);
		}
	}
	
	public Stream<E> parallelStream() {
		synchronized(objectMutex) {
			return list.parallelStream();
		}
	}
	
	public boolean remove(Object o) {
		synchronized(objectMutex) {
			int index = list.indexOf(o);
			
			if (list.remove(o)) {
				queue(item(1, index, null, true, false));
				return true;
			} else {
				return false;
			}
		}
	}
	
	public E remove(int index) {
		synchronized(objectMutex) {
			E element = list.remove(index);
			
			queue(item(1, index, null, true, false));
			
			return (E) element;
		}
	}
	
	public boolean removeAll(Collection<?> c) {
		synchronized(objectMutex) {
			if (!c.isEmpty()) {
				List<byte[]> buffer = new ArrayList<byte[]>();
				int index;
				boolean success = false;
				
				for (Object element : c) {
					index = list.indexOf(element);
					
					if (list.remove(element)) {
						buffer.add(item(0, index, null, true, false));
						success = true;
					}
				}
				
				if (success) {
					queue(buffer);
				}
				
				return success;
			}
			
			return false;
		}
	}
	
	public void replaceAll(UnaryOperator<E> operator) {
		System.err.println("SWGList::sort: Not currently supported.");
	}
	
	public boolean retainAll(Collection<?> c) {
		synchronized(objectMutex) {
			return list.retainAll(c);
		}
	}
	
	public List<E> reverseGet() {     
	    synchronized(objectMutex) {
	    	return Lists.reverse(list);
	    }
	}
	
	public boolean removeIf(Predicate<? super E> filter) {
		synchronized(objectMutex) {
			return false;
		}
	}
	
	public E set(int index, E element) {
		synchronized(objectMutex) {
			if (valid(element)) {
				E previousElement = list.set(index, element);
				queue(item(2, index, Baseline.toBytes(element), true, true));
				
				return previousElement;
			}
			
			return null;
		}
	}
	
	public boolean set(List<E> list) {
		synchronized(objectMutex) {
			byte[] newListData = { };
			
			if (!list.isEmpty()) {
				for (E element : list) {
					if (valid(element)) {
						IoBuffer buffer = Delta.createBuffer((newListData.length + Baseline.toBytes(element).length));
						buffer.put(newListData);
						buffer.put(Baseline.toBytes(element));
						newListData = buffer.array();
					} else {
						return false;
					}
				}
				
				IoBuffer buffer = Delta.createBuffer(3 + newListData.length);
				buffer.put((byte) 3);
				buffer.putShort((short) list.size());
				buffer.put(newListData);
				newListData = buffer.array();
				
				this.list = list;
				
				updateCounter += list.size();
				//StringUtilities.printBytes(newListData);
				//tools.CharonPacketUtils.printAnalysis(buffer,"SWGList set");
				queue(newListData);
					
				return true;
			}
			
			return false;
		}
	}
	
	public int size() {
		synchronized(objectMutex) {
			return list.size();
		}
	}
	
	public void sort(Comparator<? super E> c) {
		synchronized(objectMutex) {
			System.err.println("SWGList::sort: Not supported.");
		}
	}
	
	public Spliterator<E> spliterator() {
		synchronized(objectMutex) {
			return list.spliterator();
		}
	}
	
	public Stream<E> stream() {
		synchronized(objectMutex) {
			return list.stream();
		}
	}
	
	public List<E> subList(int fromIndex, int toIndex) {
		synchronized(objectMutex) {
			return list.subList(fromIndex, toIndex);
		}
	}
	
	public Object[] toArray() {
		synchronized(objectMutex) {
			return list.toArray();
		}
	}
	
	public <T> T[] toArray(T[] a) {
		synchronized(objectMutex) {
			return list.toArray(a);
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
			
			for (Object o : list) {
				byte[] object = Baseline.toBytes(o);
				size += object.length;
				
				IoBuffer buffer = Delta.createBuffer(size);
				buffer.put(objects);
				if (addByte) buffer.put((byte) 0);
				buffer.put(object);
				buffer.flip();
				
				objects = buffer.array();
			}
			
			IoBuffer buffer = Delta.createBuffer(8 + size);
			buffer.putInt(list.size());
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
	
	private byte[] item(int type, int index, byte[] data, boolean useIndex, boolean useData) {
		int size = 1 + ((useIndex) ? 2 : 0) + ((useData) ? data.length : 0);
		
		IoBuffer buffer = Delta.createBuffer(size);
		buffer.put((byte) type);
		if (useIndex) buffer.putShort((short) index);
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
