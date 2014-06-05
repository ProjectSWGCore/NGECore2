package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;
import resources.buffs.BuffItem;

public class BuffBuilderChangeMessage extends ObjControllerObject {

	private boolean accepted;
	private int buffCost;

	private long bufferId;
	private int buffRecipientAccepted;
	private long buffRecipientId;

	private long objectId;
	private Vector<BuffItem> statBuffs = new Vector<BuffItem>();

	private int time;

	public BuffBuilderChangeMessage() {

	}

	public BuffBuilderChangeMessage(long objectId, long bufferId, long buffRecipientId, Vector<BuffItem> statBuffs) {
		this.objectId = objectId;
		this.bufferId = bufferId;
		this.buffRecipientId = buffRecipientId;
		this.statBuffs = statBuffs;
	}


	@Override
	public void deserialize(IoBuffer data) {
		setObjectId(data.getLong());
		data.getInt();
		setBufferId(data.getLong());
		setBuffRecipientId(data.getLong());
		setTime(data.getInt());
		setBuffCost(data.getInt());

		byte value = data.get();
		if (value == (byte) 0)
			setAccepted(false);
		else if (value == (byte) 1)
			setAccepted(true);

		setBuffRecipientAccepted(data.getInt());

		int statSize = data.getInt();
		for (int i = 0; i < statSize; i++) {
			BuffItem item = new BuffItem();

			item.setSkillName(getAsciiString(data));
			item.setInvested(data.getInt());
			item.setEntertainerBonus(data.getInt());

			statBuffs.add(item);
		}
	}

	public boolean getAccepted() {
		return accepted;
	}

	public int getBuffCost() {
		return buffCost;
	}

	public long getBufferId() {
		return bufferId;
	}

	public int getBuffRecipientAccepted() {
		return buffRecipientAccepted;
	}

	public long getBuffRecipientId() {
		return buffRecipientId;
	}

	public long getObjectId() {
		return objectId;
	}

	public Vector<BuffItem> getStatBuffs() {
		return statBuffs;
	}

	public int getTime() {
		return time;
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
				result.putInt(item.getEntertainerBonus());
			}
		}

		return result.flip();
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public void setBuffCost(int buffCost) {
		this.buffCost = buffCost;
	}

	public void setBufferId(long bufferId) {
		this.bufferId = bufferId;
	}

	public void setBuffRecipientAccepted(int buffRecipientAccepted) {
		this.buffRecipientAccepted = buffRecipientAccepted;
	}

	public void setBuffRecipientId(long buffRecipientId) {
		this.buffRecipientId = buffRecipientId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public void setStatBuffs(Vector<BuffItem> statBuffs) {
		this.statBuffs = statBuffs;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
