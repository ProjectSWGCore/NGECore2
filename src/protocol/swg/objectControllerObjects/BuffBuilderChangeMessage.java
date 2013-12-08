package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;
import resources.common.ObjControllerOpcodes;
import resources.common.StringUtilities;
import resources.objects.BuffItem;

public class BuffBuilderChangeMessage extends ObjControllerObject {

	private int buffCost;
	private int time;

	private long objectId;
	private long bufferId;
	private long buffRecipientId;
	
	private boolean accepted;
	private int buffRecipientAccepted;
	
	private Vector<BuffItem> statBuffs = new Vector<BuffItem>();
	
	public BuffBuilderChangeMessage() {
		
	}
	
	public BuffBuilderChangeMessage(long objectId, long bufferId, long buffRecipientId, Vector<BuffItem> statBuffs) {
		this.objectId = objectId;
		this.bufferId = bufferId;
		this.buffRecipientId = buffRecipientId;
		this.statBuffs = statBuffs;
	}

	
	@Override
	public void deserialize(IoBuffer buffer) {
		setObjectId(buffer.getLong());
		buffer.getInt();
		setBufferId(buffer.getLong());
		setBuffRecipientId(buffer.getLong());
		setTime(buffer.getInt());
		setBuffCost(buffer.getInt());
		
		byte value = buffer.get();
		if (value == (byte) 0)
			setAccepted(false);
		else if (value == (byte) 1)
			setAccepted(true);
		
		setBuffRecipientAccepted(buffer.getInt());
		
		int statSize = buffer.getInt();
		for (int i = 0; i < statSize; i++) {
			BuffItem item = new BuffItem();
			
			String statName = getAsciiString(buffer);
			item.setSkillName(statName);
			
			int investedPoints = buffer.getInt();
			item.setInvested(investedPoints);
			
			int maxAmount = buffer.getInt();
			item.setBonusAmount(maxAmount);
			
			statBuffs.add(item);
			System.out.println("Added buff item with " + investedPoints + " invested points " + " which has a bonus amount of " + maxAmount);
		}
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(65 + statBuffs.size()).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putInt(ObjControllerMessage.BUFF_BUILDER_CHANGE);
		result.putLong(objectId);
		
		result.putInt(0);
		
		result.putLong(bufferId);
		result.putLong(buffRecipientId);
		
		result.putInt(time);
		result.putInt(buffCost);
		
		result.put((byte) ((accepted) ? 1 : 0));
		result.putInt(buffRecipientAccepted);
		
		result.putInt(statBuffs.size());
		
		if (!statBuffs.isEmpty()) {
			for (BuffItem item : statBuffs) {
				result.put(getAsciiString(item.getSkillName()));
				result.putInt(item.getInvested());
				result.putInt(item.getBonusAmount());
			}
		}
		
		return result.flip();
	}

	public int getBuffCost() {
		return buffCost;
	}

	public void setBuffCost(int buffCost) {
		this.buffCost = buffCost;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public long getBufferId() {
		return bufferId;
	}

	public void setBufferId(long bufferId) {
		this.bufferId = bufferId;
	}

	public long getBuffRecipientId() {
		return buffRecipientId;
	}

	public void setBuffRecipientId(long buffRecipientId) {
		this.buffRecipientId = buffRecipientId;
	}

	public boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public int getBuffRecipientAccepted() {
		return buffRecipientAccepted;
	}

	public void setBuffRecipientAccepted(int buffRecipientAccepted) {
		this.buffRecipientAccepted = buffRecipientAccepted;
	}

	public Vector<BuffItem> getStatBuffs() {
		return statBuffs;
	}

	public void setStatBuffs(Vector<BuffItem> statBuffs) {
		this.statBuffs = statBuffs;
	}

}
