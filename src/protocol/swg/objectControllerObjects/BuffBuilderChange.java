package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;

public class BuffBuilderChange extends ObjControllerObject {

	private long bufferId;
	private long recipientId;
	private int buffCost;
	private int accepted;

	private long unk1;
	private long unk2;
	private long unk3;
	
	private int unkI1;
	private int unkI2;
	
	private byte unkByte;
	
	public BuffBuilderChange() {
		
	}
	
	public BuffBuilderChange(long bufferId, long recipientId, int accepted, int cost) {
		this.bufferId = bufferId;
		this.recipientId = recipientId;
		this.accepted = accepted;
		this.buffCost = cost;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		unk1 = data.getLong();
		unkI1 = data.getInt();
		unk2 = data.getLong();
		unk3 = data.getLong();
		unkI2 = data.getInt();
		buffCost = data.getInt();
		accepted = data.getInt();
		unkByte = data.get();
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putInt(ObjControllerMessage.BUFF_BUILDER_CHANGE);
		result.putLong(unk1);
		
		result.putInt(unkI1); // tickCount
		result.putLong(unk2);
		result.putLong(unk3);
		result.putInt(unkI2); // starting time
		result.putInt(buffCost);
		result.putInt(accepted);
		result.put(unkByte); // unk
		
		//return result.flip();
		return result.flip();
	}

	public long getBufferId() {
		return bufferId;
	}

	public void setBufferId(long bufferId) {
		this.bufferId = bufferId;
	}

	public long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(long recipientId) {
		this.recipientId = recipientId;
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

	public long getUnk1() {
		return unk1;
	}

	public void setUnk1(long unk1) {
		this.unk1 = unk1;
	}

	public long getUnk2() {
		return unk2;
	}

	public void setUnk2(long unk2) {
		this.unk2 = unk2;
	}

	public long getUnk3() {
		return unk3;
	}

	public void setUnk3(long unk3) {
		this.unk3 = unk3;
	}

	public int getUnkI1() {
		return unkI1;
	}

	public void setUnkI1(int unkI1) {
		this.unkI1 = unkI1;
	}

	public int getUnkI2() {
		return unkI2;
	}

	public void setUnkI2(int unkI2) {
		this.unkI2 = unkI2;
	}
	
	public byte getUnkByte() {
		return this.unkByte;
	}
	
	public void setUnkByte(byte unkByte) {
		this.unkByte = unkByte;
	}

}
