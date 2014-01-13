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

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

import resources.common.AString;
import resources.common.Opcodes;
import resources.common.UString;
import resources.objects.IDelta;
import resources.z.exp.objects.object.BaseObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.resources.objects.SWGObject;

@Persistent
public class Baseline implements List<Object> {
	
	private List<Object> list = new CopyOnWriteArrayList<Object>();
	private Map<String, Integer> definition = new TreeMap<String, Integer>();
	private BaseObject object;
	private byte viewType;
	@NotPersistent
	protected final Object objectMutex = new Object();
	@NotPersistent
	private static SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();
	@NotPersistent
	private Map<Integer, Builder> baselineBuilders;
	@NotPersistent
	private Map<Integer, Builder> deltaBuilders;
	
	public Baseline() { }
	
	public Baseline(BaseObject object, int viewType) {
		this.viewType = (byte) viewType;
		this.object = object;
	}
	
	public boolean add(Object e) {
		synchronized(objectMutex) {
			if (list.add(e)) {
				definition.put(Integer.toString(list.size() - 1), list.size() - 1);
				return true;
			} else {
				return false;
			}
		}
	}
	
	public void add(int index, Object element) {
		synchronized(objectMutex) {
			return;
		}
	}
	
	public boolean addAll(Collection<? extends Object> c) {
		synchronized(objectMutex) {
			return false;
		}
	}
	
	public boolean addAll(int index, Collection<? extends Object> c) {
		synchronized(objectMutex) {
			return false;
		}
	}
	
	public void clear() {
		synchronized(objectMutex) {
			list.clear();
			definition.clear();
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
	
	private boolean compareTypes(Object o1, Object o2) {
		if ((o1 instanceof IDelta && o2 instanceof IDelta) ||
		(o1 instanceof AString && o2 instanceof AString) ||
		(o1 instanceof UString && o2 instanceof UString) ||
		(o1 instanceof Byte[] && o2 instanceof Byte[]) ||
		(o1 instanceof Byte && o2 instanceof Byte) ||
		(o1 instanceof Boolean && o2 instanceof Boolean) ||
		(o1 instanceof Short && o2 instanceof Short) ||
		(o1 instanceof Integer && o2 instanceof Integer) ||
		(o1 instanceof Float && o2 instanceof Float) ||
		(o1 instanceof Long && o2 instanceof Long) ||
		(o1 instanceof SWGObject && o2 instanceof SWGObject) ||
		(o1 instanceof SWGList && o2 instanceof SWGList) ||
		(o1 instanceof SWGSet && o2 instanceof SWGSet) ||
		(o1 instanceof SWGMap && o2 instanceof SWGMap) ||
		(o1 instanceof SWGMultiMap && o2 instanceof SWGMultiMap) ||
		(o1 instanceof ArrayList && o2 instanceof ArrayList)) {
			return true;
		}
		
		return false;
	}
	
	public IoBuffer createBaseline() {
		byte[] objects = { };
		int size = 0;
		
		for (Object o : list) {
			byte[] object;
			
			if (baselineBuilders != null && baselineBuilders.containsKey(o)) {
				object = baselineBuilders.get(o).build();
			} else {
				object = toBytes(o);
			}
			
			size += object.length;
			
			IoBuffer buffer = createBuffer(size);
			buffer.put(objects);
			buffer.put(object);
			buffer.flip();
			
			objects = buffer.array();
		}
		
		IoBuffer buffer = createBuffer(25 + size);
		buffer.putShort((short) 5);
		buffer.putInt(Opcodes.BaselinesMessage);
		buffer.putLong(object.getObjectID());
		try {
			buffer.put(reverse(getShortTemplate()).getBytes("US-ASCII"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer.put(viewType);
		buffer.putInt(size);
		buffer.putShort((short) list.size());
		buffer.put(objects);
		buffer.flip();
		
		return buffer;
	}
	
	public static IoBuffer createBuffer(int size) {
		return bufferPool.allocate(size, false).order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public IoBuffer createDelta() {
		List<Integer> objectQueue = new ArrayList<Integer>();
		
		synchronized(objectMutex) {
			for (int i = 0; i < list.size(); i++) {
				objectQueue.add(i);
			}
		}
		
		return createDelta(objectQueue, null);
	}
	
	public IoBuffer createDelta(int object) {
		List<Integer> objectQueue = new ArrayList<Integer>();
		objectQueue.add(object);
		return createDelta(objectQueue, null);
	}
	
	public IoBuffer createDelta(int object, byte[] data) {
		List<Integer> objectQueue = new ArrayList<Integer>();
		objectQueue.add(object);
		return createDelta(objectQueue, data);
	}
	
	public IoBuffer createDelta(List<Integer> objectQueue, byte[] data) {
		byte[] objects = { };
		int size = 0;
		
		for (Integer o : objectQueue) {
			byte[] object;
			
			if (deltaBuilders != null && deltaBuilders.containsKey(o)) {
				object = deltaBuilders.get(o).build();
			} else if (data != null) {
				object = data;
			} else {
				object = toBytes(get(o));
			}
			
			size += 2 + object.length;
			
			IoBuffer buffer = createBuffer(size);
			buffer.put(objects);
			buffer.putShort(o.shortValue());
			buffer.put(object);
			buffer.flip();
			
			objects = buffer.array();
		}
		
		IoBuffer buffer = createBuffer(27 + size);
		buffer.putShort((short) 5);
		buffer.putInt(Opcodes.DeltasMessage);
		buffer.putLong(object.getObjectID());
		try {
			buffer.put(reverse(getShortTemplate()).getBytes("US-ASCII"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
 		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer.put(viewType);
		buffer.putInt(size);
		buffer.putShort((short) objectQueue.size());
		buffer.put(objects);
		buffer.flip();
		
		return buffer;
	}
	
	public Object get(int index) {
		synchronized(objectMutex) {
			return list.get(index);
		}
	}
	
	public Object get(String name) {
		synchronized(objectMutex) {
			return list.get(definition.get(name));
		}
	}
	
	public IoBuffer getBaseline() {
		return createBaseline();
	}
	
	public Map<Integer, Builder> getBaselineBuilders() {
		synchronized(objectMutex) {
			return baselineBuilders;
		}
	}
	
	public static byte getBoolean(boolean condition) {
		return ((byte) ((condition) ? 1 : 0));
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			int size = 0;
			
			for (Object o : list) {
				size += toBytes(o).length;
			}
			
			IoBuffer buffer = createBuffer(size);
			
			for (Object o : list) {
				buffer.put(toBytes(o));
			}
			
			buffer.flip();
			
			return buffer.array();
		}
	}
	
	public byte[] getBytes(int index) {
		synchronized(objectMutex) {
			return toBytes(list.get(index));
		}
	}
	
	public byte[] getBytes(String name) {
		synchronized(objectMutex) {
			return toBytes(list.get(definition.get(name)));
		}
	}
	
	private String getDefinition(int index) {
		for (Entry<String, Integer> entry : definition.entrySet()) {
			if (entry.getValue() == index) {
				return entry.getKey();
			}
		}
			
		return null;
	}
	
	public Map<String, Integer> getDefinitions() {
		synchronized(objectMutex) {
			return definition;
		}
	}
	
	public IoBuffer getDelta(int index) {
		return createDelta(index);
	}
	
	public IoBuffer getDelta(String name) {
		return createDelta(definition.get(name));
	}
	
	public Map<Integer, Builder> getDeltaBuilders() {
		synchronized(objectMutex) {
			return deltaBuilders;
		}
	}
	
	public Object getMutex() {
		return objectMutex;
	}
	
	public String getShortTemplate() throws Exception {
		String Template = object.getTemplate();
		
		if (Template.startsWith("object/battlefield_marker")) {
			return "BMRK";
		} else if(Template.startsWith("object/building")){
			return "BUIO";
		} else if(Template.startsWith("object/cell")) {
			return "SCLT";
		} else if (Template.startsWith("object/construction_contract")) {
			throw new Exception();
		} else if (Template.startsWith("object/counting")) {
			throw new Exception();
		} else if (Template.startsWith("object/creature")) {
			return "CREO";
		} else if (Template.startsWith("object/draft_schematic")) {
			throw new Exception();
		} else if (Template.startsWith("object/factory")) {
			return "FCYT";
		} else if(Template.startsWith("object/group")) {
			return "GRUP";
		} else if(Template.startsWith("object/guild")) {
			return "GILD";
		} else if (Template.startsWith("object/installation")) {
			return "INSO";
		} else if (Template.startsWith("object/installation/battlefield")) {
			throw new Exception();
		} else if (Template.startsWith("object/installation/faction_perk/covert_detector")) {
			throw new Exception();
		} else if (Template.startsWith("object/installation/faction_perk/minefield")) {
			throw new Exception();
		} else if (Template.startsWith("object/installation/faction_perk/turret")) {
			throw new Exception();
		} else if (Template.startsWith("object/installation/generators")) {
			return "HINO";
		} else if (Template.startsWith("object/installation/manufacture")) {
			return "MINO";
		} else if (Template.startsWith("object/installation/mining_gas")) {
			throw new Exception();
		} else if (Template.startsWith("object/installation/mining_liquid")) {
			throw new Exception();
		} else if (Template.startsWith("object/installation/mining_gas")) {
			throw new Exception();
		} else if (Template.startsWith("object/installation/mining_ore")) {
			throw new Exception();
		} else if (Template.startsWith("object/installation/mining_organic")) {
			throw new Exception();
		} else if (Template.startsWith("object/installation/turret")) {
			throw new Exception();
		} else if (Template.startsWith("object/intangible")) {
			return "ITNO";
		} else if (Template.startsWith("object/jedi_manager")) {
			throw new Exception();
		} else if (Template.startsWith("object/manufacture_schematic")) {
			return "MSCO";
		} else if (Template.startsWith("object/mission")) {
			return "MISO";
		} else if (Template.startsWith("object/mobile")) {
			throw new Exception();
		} else if (Template.startsWith("object/object")) {
			throw new Exception();
		} else if (Template.startsWith("object/path_waypoint")) {
			throw new Exception();
		} else if (Template.startsWith("object/player")) {
			return "PLAY";
		} else if (Template.startsWith("object/player_quest")) {
			return "PQOS";
		} else if (Template.startsWith("object/resource_container")) {
			return "RCNO";
		} else if (Template.startsWith("object/ship")) {
			return "SHIP";
		} else if (Template.startsWith("object/soundobject")) {
			throw new Exception();
		} else if(Template.startsWith("object/static")) {
			return "STAO";
		} else if (Template.startsWith("object/tangible")) {
			return "TANO";
		} else if (Template.startsWith("object/token")) {
			throw new Exception();
		} else if (Template.startsWith("object/universe")) {
			throw new Exception();
		} else if(Template.startsWith("object/waypoint")) {
			return "WAYP";
		} else if (Template.startsWith("object/weapon")) {
			return "WEAO";
		} else {
			throw new Exception();
		}
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
	
	public Iterator<Object> iterator() {
		synchronized(objectMutex) {
			return list.iterator();
		}
	}
	
	public int lastIndexOf(Object o) {
		synchronized(objectMutex) {
			return lastIndexOf(o);
		}
	}
	
	public ListIterator<Object> listIterator() {
		synchronized(objectMutex) {
			return list.listIterator();
		}
	}
	
	public ListIterator<Object> listIterator(int index) {
		synchronized(objectMutex) {
			return listIterator(index);
		}
	}
	
	public void put(String name, Object o) {
		synchronized(objectMutex) {
			definition.put(name, list.size());
			list.add(o);
		}
	}
	
	public boolean remove(Object o) {
		return false;
	}
	
	public Object remove(int index) {
		return null;
	}
	
	public boolean removeAll(Collection<?> c) {
		return false;
	}
	
	public boolean retainAll(Collection<?> c) {
		return false;
	}
	
	private String reverse(String reverseString) {
	    if (reverseString.length() <= 1) {
	    	return reverseString;
	    }
	    
	    return reverse(reverseString.substring(1, reverseString.length())) + reverseString.charAt(0);
	}
	
	public Object set(int index, Object element) {
		return null;
	}
	
	public IoBuffer set(String name, Object o) {
		synchronized(objectMutex) {
			int index = definition.get(name);
			
			if (compareTypes(o, list.get(index)) && list.set(index, o) != null) {
				return createDelta(index);
			} else {
				return null;
			}
		}
	}
	
	public void setBaselineBuilders(Map<Integer, Builder> baselineBuilders) {
		synchronized(objectMutex) {
			this.baselineBuilders = baselineBuilders;
		}
	}
	
	public void setDeltaBuilders(Map<Integer, Builder> deltaBuilders) {
		synchronized(objectMutex) {
			this.deltaBuilders = deltaBuilders;
		}
	}
	
	public int size() {
		synchronized(objectMutex) {
			return list.size();
		}
	}
	
	public List<Object> subList(int fromIndex, int toIndex) {
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
	
	public static byte[] toBytes(Object o) {
		try {
			if (o != null) {
				if (o instanceof IDelta) {
					return ((IDelta) o).getBytes();
				} else if (o instanceof String) {
					return (new AString((String) o)).getBytes();
				} else if (o instanceof AString) {
					return ((AString) o).getBytes();
				} else if (o instanceof UString) {
					return ((UString) o).getBytes();
				} else if (o instanceof Byte[]) {
					IoBuffer buffer = createBuffer(2 + ((Byte[]) o).length);
					buffer.putShort((short) ((Byte[]) o).length);
					for (Byte b : (Byte[]) o) buffer.put(b);
					return buffer.array();
				} else if (o instanceof Byte) {
					return createBuffer(1).put((Byte) o).array();
				} else if (o instanceof Boolean) {
					return createBuffer(1).put(((((Boolean) o)) ? (byte) 1 : (byte) 0)).array();
				} else if (o instanceof Short) {
					return createBuffer(2).putShort((Short) o).array();
				} else if (o instanceof Integer) {
					return createBuffer(4).putInt((Integer) o).array();
				} else if (o instanceof Float) {
					return createBuffer(4).putFloat((Float) o).array();
				} else if (o instanceof Long) {
					return createBuffer(8).putLong((Long) o).array();
				} else if (o instanceof SWGObject) {
					long objectId = ((((SWGObject) o) == null) ? (long) 0 : ((SWGObject) o).getObjectID());
					return createBuffer(8).putLong(objectId).array();
				} else if (o instanceof SWGList) {
					return ((SWGList<?>) o).getBytes();
				} else if (o instanceof SWGSet) {
					return ((SWGSet<?>) o).getBytes();
				} else if (o instanceof SWGMap) {
					return ((SWGMap<?, ?>) o).getBytes();
				} else if (o instanceof SWGMultiMap) {
					return ((SWGMultiMap<?, ?>) o).getBytes();
				} else if (o instanceof ArrayList) {
					ArrayList<?> list = ((ArrayList<?>) o);
					int size = 0;
					byte[] objects = { };
					
					for (int i = 0; i < list.size(); i++) {
						byte[] object = toBytes(list.get(i));
						size += object.length;
						
						IoBuffer buffer = createBuffer(size);
						buffer.put(objects);
						buffer.put(object);
						buffer.flip();
						
						objects = buffer.array();
					}
					
					return createBuffer(size + 4).putInt(list.size()).array();
				} else {
					System.out.println("ERROR: Unsupported type used in Baseline: " + (o.getClass()).getSimpleName());
					throw new Exception();
					//return new byte[] { };
				}
			} else {
				if (o instanceof IDelta) {
					System.out.println("ERROR: a baseline object that implements IDelta is null.  This could cause crashes!  Make it at least a new instance with all fields set to 0.");
					return new byte[] { };
				} else if (o instanceof String) {
					return new byte[] { 0x00, 0x00 };
				} else if (o instanceof AString) {
					return new byte[] { 0x00, 0x00 };
				} else if (o instanceof UString) {
					return new byte[] { 0x00, 0x00, 0x00, 0x00 };
				} else if (o instanceof Byte[]) {
					return new byte[] { 0x00, 0x00 };
				} else if (o instanceof Byte) {
					return new byte[] { 0x00 };
				} else if (o instanceof Boolean) {
					return new byte[] { 0x00 };
				} else if (o instanceof Short) {
					return new byte[] { 0x00, 0x00 };
				} else if (o instanceof Integer) {
					return new byte[] { 0x00, 0x00, 0x00, 0x00 };
				} else if (o instanceof Float) {
					return new byte[] { 0x00, 0x00, 0x00, 0x00 };
				} else if (o instanceof Long) {
					return new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
				} else if (o instanceof SWGObject) {
					return null;
				} else if (o instanceof SWGList) {
					return new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
				} else if (o instanceof SWGSet) {
					return new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
				} else if (o instanceof SWGMap) {
					return new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
				} else if (o instanceof SWGMultiMap) {
					return new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
				} else if (o instanceof ArrayList) {
					return new byte[] { 0x00, 0x00, 0x00, 0x00 };
				} else {
					System.out.println("ERROR: Unsupported type used in Baseline.");
					System.out.println("~additionally, the type was null, which is dangerous.");
					return new byte[] { };
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[] { };
		}
	}
	
	public void transformStructure(Baseline defaults) {
		synchronized(objectMutex) {
			List<Object> oldStruct = list;
			Baseline newStruct = defaults;
			
			for (int i = 0; i < newStruct.size(); i++) {
				if (definition.containsKey(newStruct.getDefinition(i))) {
					int oldIndex = definition.get(newStruct.getDefinition(i));
					Object newObject = newStruct.get(i);
					Object oldObject = oldStruct.get(oldIndex);
					
					if (compareTypes(newObject, oldObject)) {
						newStruct.set(i, oldObject);
					} else {
						if (newObject instanceof String) {
							if (oldObject instanceof AString) {
								newStruct.set(i, ((AString) oldObject).get());
							} else if (oldObject instanceof UString) {
								newStruct.set(i, ((UString) oldObject).get());
							}
						} else if (newObject instanceof AString) {
							if (oldObject instanceof String) {
								newStruct.set(i, new AString((String) oldObject));
							} else if (oldObject instanceof UString) {
								newStruct.set(i, new AString(((UString) oldObject).get()));
							}
						} else if (newObject instanceof UString) {
							if (oldObject instanceof String) {
								newStruct.set(i, new UString((String) oldObject));
							} else if (oldObject instanceof AString) {
								newStruct.set(i, new UString(((AString) oldObject).get()));
							}
						} else if (newObject instanceof Byte) {
							if (oldObject instanceof Short && (Short) oldObject < 0xFF) {
								newStruct.set(i, ((Short) oldObject).byteValue());
							} else if (oldObject instanceof Integer && (Integer) oldObject < 0xFF) {
								newStruct.set(i, ((Integer) oldObject).byteValue());
							} else if (oldObject instanceof Long && (Long) oldObject < 0xFF) {
								newStruct.set(i, ((Long) oldObject).byteValue());
							}
						} else if (newObject instanceof Short) {
							if (oldObject instanceof Byte) {
								newStruct.set(i, ((Byte) oldObject).shortValue());
							} else if (oldObject instanceof Integer && (Integer) oldObject < 0xFFFF) {
								newStruct.set(i, ((Integer) oldObject).shortValue());
							} else if (oldObject instanceof Long && (Long) oldObject < 0xFFFF) {
								newStruct.set(i, ((Long) oldObject).shortValue());
							}
						} else if (newObject instanceof Integer) {
							if (oldObject instanceof Byte) {
								newStruct.set(i, ((Byte) oldObject).intValue());
							} else if (oldObject instanceof Short) {
								newStruct.set(i, ((Short) oldObject).intValue());
							} else if (oldObject instanceof Long && (Long) oldObject < 0xFFFFFFFF) {
								newStruct.set(i, ((Long) oldObject).intValue());
							}
						} else if (newObject instanceof Long) {
							if (oldObject instanceof Byte) {
								newStruct.set(i, ((Byte) oldObject).longValue());
							} else if (oldObject instanceof Short) {
								newStruct.set(i, ((Short) oldObject).longValue());
							} else if (oldObject instanceof Integer) {
								newStruct.set(i, ((Integer) oldObject).longValue());
							} else if (oldObject instanceof SWGObject) {
								newStruct.set(i, ((SWGObject) oldObject).getObjectID());
							}
						}
					}
				}
			}
			
			list = newStruct.list;
		}
	}
	
}
