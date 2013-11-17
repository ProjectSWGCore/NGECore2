package protocol.swg.objectControllerObjects;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;
import resources.common.Console;
import resources.objects.BuffItem;

public class BuffBuilderChange extends ObjControllerObject {

	private int buffCost;
	private int accepted;
	private int startTime;
	private int tickCount;

	private long objectId;
	private long bufferId;
	private long recipientId;
	
	private byte unkByte;
	
	private Vector<BuffItem> buffs = new Vector<BuffItem>();
	
	public BuffBuilderChange() {
		
	}
	
	public BuffBuilderChange(long objectId, long bufferId, long recipientId, int accepted, int cost, byte unkByte) {
		this.objectId = objectId;
		this.bufferId = bufferId;
		this.recipientId = recipientId;
		this.accepted = accepted;
		this.buffCost = cost;
		this.unkByte = unkByte;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		objectId = data.getLong(); // acting players id
		tickCount = data.getInt(); // tick count
		bufferId = data.getLong(); // objId
		recipientId = data.getLong();
		startTime = data.getInt();
		buffCost = data.getInt();
		accepted = data.getInt();
		unkByte = data.get();
		
		int size = data.getInt(); // list size
		for(int i = 0; i < size; i++) {
			BuffItem buff = new BuffItem();
			int stringSize;
			try {
				stringSize = data.getShort();
				String buffName = new String(ByteBuffer.allocate(stringSize).put(data.array(), data.position(), size).array(), "US-ASCII");
				buff.setSkillName(buffName);
				Console.println("Buff Name: " + buffName);
				data.position(data.position() + size);
				buff.setUnknown(data.getInt());
				Console.println("Buff Unknown: " + buff.getUnknown());
				buff.setAmount(data.getInt());
				Console.println("Buff Amount: " + buff.getAmount());
				buffs.add(buff);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);

		result.putInt(ObjControllerMessage.BUFF_BUILDER_CHANGE);
		result.putLong(objectId);
		
		result.putInt(tickCount); // tickCount
		result.putLong(bufferId);
		result.putLong(recipientId);
		result.putInt(startTime); // starting time
		result.putInt(buffCost);
		result.putInt(accepted);
		result.put(unkByte); // default 0
		
		if (buffs == null || buffs.isEmpty()){
			result.putInt(0);
		} else {
			result.putInt(buffs.size());
			for (BuffItem buff : buffs) {
				result.put(getAsciiString(buff.getSkillName()));
				result.putInt(buff.getUnknown());
				result.putInt(buff.getAmount());
			}
		}

		return result.flip();
	}

	public long getBufferId() {
		return bufferId;
	}

	public void setBufferId(long bufferId) {
		this.bufferId = bufferId;
	}

	public int getBuffCost() {
		return buffCost;
	}

	public void setBuffCost(int buffCost) {
		this.buffCost = buffCost;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objId) {
		this.objectId = objId;
	}

	public byte getUnkByte() {
		return this.unkByte;
	}
	
	public void setUnkByte(byte unkByte) {
		this.unkByte = unkByte;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(long recipientId) {
		this.recipientId = recipientId;
	}
	
	public void setBuffs(Vector<BuffItem> buffVector) {
		this.buffs = buffVector;
	}
	
	public Vector<BuffItem> getBuffVector() {
		return this.buffs;
	}

	public int getTickCount() {
		return tickCount;
	}

	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}
}
