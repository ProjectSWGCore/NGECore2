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
package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;
import resources.buffs.BuffItem;

public class BuffBuilderEndMessage extends ObjControllerObject {

	private boolean accepted;
	private int buffCost;

	private long bufferId;
	private int buffRecipientAccepted;
	private long buffRecipientId;

	private long objectId;
	private Vector<BuffItem> statBuffs = new Vector<BuffItem>();

	private int time;

	public BuffBuilderEndMessage() {

	}

	public BuffBuilderEndMessage(BuffBuilderEndMessage endMessage) {
		this.buffCost = endMessage.getBuffCost();
		this.time = endMessage.getTime();
		this.accepted = endMessage.getAccepted();
		this.buffRecipientAccepted = endMessage.getBuffRecipientAccepted();
		this.statBuffs = endMessage.getStatBuffs();
	}
	
	public BuffBuilderEndMessage(BuffBuilderChangeMessage changeMessage) {
		this.buffRecipientId = changeMessage.getBuffRecipientId();
		this.bufferId = changeMessage.getBufferId();
		this.buffCost = changeMessage.getBuffCost();
		this.time = changeMessage.getTime();
		this.accepted = changeMessage.getAccepted();
		this.buffRecipientAccepted = changeMessage.getBuffRecipientAccepted();
		this.statBuffs = changeMessage.getStatBuffs();
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

			String statName = getAsciiString(data);
			item.setSkillName(statName);

			int investedPoints = data.getInt();
			item.setInvested(investedPoints);

			int entBonus = data.getInt();
			item.setEntertainerBonus(entBonus);

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

		result.putInt(ObjControllerMessage.BUFF_BUILDER_END);
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
