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
package protocol.swg;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.CRC;
import resources.common.Opcodes;
import resources.common.OutOfBand;

public class CommPlayerMessage extends SWGMessage {

	private long objectId;
	private String model;
	private OutOfBand outOfBand;
	private int time;

	public CommPlayerMessage(long objectId, OutOfBand outOfBand) {
		this.objectId = objectId;
		this.outOfBand = outOfBand;
	}
	
	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(50).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);

		buffer.putShort((short) 2);
		buffer.putInt(Opcodes.CommPlayerMessage);
		buffer.put((byte) 0); // aurebesh borders on comm, space version? Can cause crashes
		buffer.putLong(objectId);
		buffer.put(outOfBand.serialize().array());
		// Officer Supply Drop: 0x3E894347
		// Rebel Faction Dude: 0x528CB3D7
		if (model == null)
			buffer.putInt(0x3E894347);
		else 
			buffer.putInt(CRC.StringtoCRC(model));
		buffer.putInt(0); // sound
		buffer.putShort((short) 0); // unk
		if (time == 0)
			buffer.putShort((short) 16576);
		else
			buffer.putShort((short) time);
		buffer.flip();
		return buffer;
	}
	
	public void setModel(String model) {
		this.model = model;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
