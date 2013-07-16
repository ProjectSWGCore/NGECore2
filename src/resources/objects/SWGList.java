package resources.objects;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.NotPersistent;

public class SWGList {
	
	private List<ListObject> list = new ArrayList<ListObject>();
	@NotPersistent
	private int updateCounter;
	
	private ObjectMessageBuilder messageBuilder;
	private short updateType;
	
	protected final Object objectMutex = new Object();
	
	public SWGList(ObjectMessageBuilder messageBuilder, short updateType) {
		this.messageBuilder = messageBuilder;
		this.updateType = updateType;
	}
	
	public void add(ListObject e) {
		synchronized(objectMutex) {
			byte[] bytes = e.getBytes();
			list.add(e);
			updateCounter++;

			IoBuffer buffer = messageBuilder.bufferPool.allocate((bytes.length + 11), false).order(ByteOrder.LITTLE_ENDIAN);;
			buffer.putInt(1);
			buffer.putInt(updateCounter);
			buffer.put((byte) 1);
			buffer.putShort((short) list.lastIndexOf(e));
			buffer.put(bytes);
			messageBuilder.sendListDelta(updateType, buffer);
		}
	}
	
	public ListObject get(int index) {
		synchronized(objectMutex) {
			return list.get(index);
		}
	}
	
	public void set(int index, ListObject element) {
		synchronized(objectMutex) {
			byte[] bytes = element.getBytes();
			list.set(index, element);
			updateCounter++;
			
			IoBuffer buffer = messageBuilder.bufferPool.allocate((bytes.length + 11), false).order(ByteOrder.LITTLE_ENDIAN);;
			buffer.putInt(1);
			buffer.putInt(updateCounter);
			buffer.put((byte) 2);
			buffer.putShort((short) index);
			buffer.put(bytes);
			messageBuilder.sendListDelta(updateType, buffer);
		}
	}
	
	public List<ListObject> get() {
		synchronized(objectMutex) {
			return list;
		}
	}
	
	public void set(List<ListObject> list) {
		synchronized(objectMutex) {
			this.list = list;
			updateCounter++;
			
			IoBuffer buffer = messageBuilder.bufferPool.allocate(11, false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.putInt(list.size());
			buffer.putInt(updateCounter);
			buffer.put((byte) 3);
			buffer.putShort((short) list.size());
			
			for (ListObject object : list) {
				buffer = messageBuilder.bufferPool.allocate(buffer.position(), false).put(buffer.array(), 0, buffer.position());
				buffer.put(object.getBytes());
			}
			
			messageBuilder.sendListDelta(updateType, buffer);
		}
	}
	
	public boolean contains(ListObject o) {
		synchronized(objectMutex) {
			return list.contains(o);
		}
	}
	
	public void remove(int index) {
		synchronized(objectMutex) {
			list.remove(index);
			updateCounter++;
			
			IoBuffer buffer = messageBuilder.bufferPool.allocate(11, false).order(ByteOrder.LITTLE_ENDIAN);;
			buffer.putInt(1);
			buffer.putInt(updateCounter);
			buffer.put((byte) 0);
			buffer.putShort((short) index);
			messageBuilder.sendListDelta(updateType, buffer);
		}
	}
	
	public void remove(ListObject o) {
		synchronized(objectMutex) {
			int index = list.indexOf(o);
			
			list.remove(index);
			updateCounter++;
			
			IoBuffer buffer = messageBuilder.bufferPool.allocate(11, false).order(ByteOrder.LITTLE_ENDIAN);;
			buffer.putInt(1);
			buffer.putInt(updateCounter);
			buffer.put((byte) 0);
			buffer.putShort((short) index);
			messageBuilder.sendListDelta(updateType, buffer);
		}
		
	}
	
	public void clear() {
		synchronized(objectMutex) {
			list.clear();
			updateCounter++;
			
			IoBuffer buffer = messageBuilder.bufferPool.allocate(9, false).order(ByteOrder.LITTLE_ENDIAN);;
			buffer.putInt(0);
			buffer.putInt(updateCounter);
			buffer.put((byte) 0);
			messageBuilder.sendListDelta(updateType, buffer);
		}
	}
	
	public int getUpdateCounter() {
		synchronized(objectMutex) {
			return updateCounter;
		}
	}
	
	public int size() {
		synchronized(objectMutex) {
			return list.size();
		}
	}

}
