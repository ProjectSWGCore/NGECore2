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

import resources.common.Console;
import resources.common.OutOfBand;
import resources.common.StringUtilities;

public class CommPlayerMessage extends SWGMessage {

	private long objectId;
	private OutOfBand outOfBand;
	
	public CommPlayerMessage(long objectId, OutOfBand outOfBand) {
		this.objectId = objectId;
		this.outOfBand = outOfBand;
	}
	
	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(200).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 2);
		buffer.putInt(0x594AD258);
		buffer.put((byte) 0); // aurebesh borders on comm, space version? Can cause crashes
		buffer.putLong(objectId);
		/* Seen numbers:
		 * 52 (Starting Station Comms)
		 * 54 (Tansarii Comms)
		 * 57 (Supply Drops)
		 * 58 (Faction Covert->Overt)
		 * 68 (Imperial stop)
		 */
		buffer.put(outOfBand.serialize().array());
		// Officer Supply Drop: 0x3E894347
		// Rebel Faction Dude: 0x528CB3D7
		buffer.putInt(0x528CB3D7); // model crc, can be anything w/o crashing
		buffer.putInt(0); // sound
		buffer.putShort((short) 0); // unk
		buffer.putShort((short) 16576); // comm display time, unsure on how it's calculated (probably milliseconds)
		buffer.flip();
		Console.println("CPM: " + StringUtilities.bytesToHex(buffer.array()));
		return buffer;
	}

}
