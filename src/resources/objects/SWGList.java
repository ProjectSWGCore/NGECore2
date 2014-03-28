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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.apache.mina.core.buffer.IoBuffer;
import org.python.google.common.collect.Lists;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

/* A SWGList element MUST implement IDelta, or it will refuse to work with it */

@Persistent
public class SWGList<E> implements List<E> {
	
	private List<E> list = new ArrayList<E>();
	@NotPersistent
	private int updateCounter = 1;
	private ObjectMessageBuilder messageBuilder;
	private byte viewType;
	private short updateType;
	@NotPersistent
	protected final Object objectMutex = new Object();
	
	public SWGList() { }
	
	public SWGList(ObjectMessageBuilder messageBuilder, int viewType, int updateType) {
		this.messageBuilder = messageBuilder;
		this.viewType = (byte) viewType;
		this.updateType = (short) updateType;
	}
	
	@Override
	public boolean add(E e) {
		synchronized(objectMutex) {				
			if (list.add(e) && e instanceof IDelta) {
				queue(item(1, list.lastIndexOf(e), ((IDelta) e).getBytes(), true, true));
				return true;
			}			
			return false;
		}
	}
	
	@Override
	public void add(int index, E element) {
		synchronized(objectMutex) {
			if (element instanceof IDelta) {
				list.add(index, element);
				queue(item(1, index, ((IDelta) element).getBytes(), true, true));
			}
		}
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		synchronized(objectMutex) {
			if (!c.isEmpty()) {
				List<byte[]> buffer = new ArrayList<byte[]>();
				boolean success = false;
				
				for (E element : c) {
					if (element instanceof IDelta) {
						if (list.add(element)) {
							buffer.add(item(1, list.lastIndexOf(element), ((IDelta) element).getBytes(), true, true));
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
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		synchronized(objectMutex) {
			if (!c.isEmpty()) {
				List<byte[]> buffer = new ArrayList<byte[]>();
				
				for (E element : c) {
					if (element instanceof IDelta) {
						list.add(index, element);
						buffer.add(item(1, index, ((IDelta) element).getBytes(), true, true));
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
	
	@Override
	public void clear() {
		synchronized(objectMutex) {
			list.clear();
			queue(item(4, 0, null, false, false));
		}
	}
	
	@Override
	public boolean contains(Object o) {
		synchronized(objectMutex) {
			return list.contains(o);
		}
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized(objectMutex) {
			return list.containsAll(c);
		}
	}
	
	@Override
	public E get(int index) {
		synchronized(objectMutex) {
			return list.get(index);
		}
	}
	
	public List<E> get() {
		return list;
	}
	
	@Override
	public int indexOf(Object o) {
		synchronized(objectMutex) {
			return list.indexOf(o);
		}
	}
	
	@Override
	public boolean isEmpty() {
		synchronized(objectMutex) {
			return list.isEmpty();
		}
	}
	
	@Override
	public Iterator<E> iterator() {
		synchronized(objectMutex) {
			return list.iterator();
		}
	}
	
	@Override
	public int lastIndexOf(Object o) {
		synchronized(objectMutex) {
			return list.lastIndexOf(o);
		}
	}
	
	@Override
	public ListIterator<E> listIterator() {
		synchronized(objectMutex) {
			return list.listIterator();
		}
	}
	
	@Override
	public ListIterator<E> listIterator(int index) {
		synchronized(objectMutex) {
			return listIterator(index);
		}
	}
	
	@Override
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
	
	@Override
	public E remove(int index) {
		synchronized(objectMutex) {
			E element = list.remove(index);
			
			queue(item(1, index, null, true, false));
			
			return (E) element;
		}
	}
	
	@Override
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
	
	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized(objectMutex) {
			return list.retainAll(c);
		}
	}
	
	@Override
	public E set(int index, E element) {
		synchronized(objectMutex) {
			if (element instanceof IDelta) {
				E previousElement = list.set(index, element);
				
				queue(item(2, index, ((IDelta) element).getBytes(), true, true));
				
				return previousElement;
			}
			
			return null;
		}
	}
	
	public boolean set(List<E> list) {
		synchronized(objectMutex) {
			byte[] newListData = { 0x03 };
			
			if (!list.isEmpty()) {
				for (E element : list) {
					if (element instanceof IDelta) {
						IoBuffer buffer = messageBuilder.bufferPool.allocate((newListData.length + ((IDelta) element).getBytes().length), false).order(ByteOrder.LITTLE_ENDIAN);
						buffer.put(newListData);
						buffer.put(((IDelta) element).getBytes());
						newListData = buffer.array();
					} else {
						return false;
					}
				}
				
				this.list = list;
				
				updateCounter++;
				queue(newListData);
					
				return true;
			}
			
			return false;
		}
	}
	
	@Override
	public int size() {
		synchronized(objectMutex) {
			return list.size();
		}
	}
	
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		synchronized(objectMutex) {
			return list.subList(fromIndex, toIndex);
		}
	}
	
	@Override
	public Object[] toArray() {
		synchronized(objectMutex) {
			return list.toArray();
		}
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		synchronized(objectMutex) {
			return list.toArray(a);
		}
	}
	
	public int getUpdateCounter() {
		return updateCounter;
	}
	
	public Object getMutex() {
		return objectMutex;
	}
	
	private byte[] item(int type, int index, byte[] data, boolean useIndex, boolean useData) {
		int size = 1 + ((useIndex) ? 2 : 0) + ((useData) ? data.length : 0);
		
		if (messageBuilder == null) {
			updateCounter++;
			return new byte[] { };
		}
		
		IoBuffer buffer = messageBuilder.bufferPool.allocate((size), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) type);
		if (useIndex) buffer.putShort((short) index);
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

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return false;
	}
	
	public List<E> reverseGet()
	{     
	    return Lists.reverse(list);
	}

	@Override
	public Stream<E> stream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<E> parallelStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forEach(Consumer<? super E> action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sort(Comparator<? super E> c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spliterator<E> spliterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
