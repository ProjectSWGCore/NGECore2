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
import resources.common.StringUtilities;
import engine.resources.common.CRC;

public class CommPlayerMessage extends SWGMessage {

	public CommPlayerMessage() {
		
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
		
		//buffer.putInt(0x3E46EED5);
		buffer.putInt(0); // some random number, can be 0, doesn't seem to affect anything
		
		buffer.putInt(51); // unknown, seems to always be 51
		buffer.putInt(54); // unknown, changes for each comm message (counter?)
		
		buffer.putShort((short) 0); // unknonw flag, seems to do nothing
		buffer.put((byte) 1); // unknown, always 0
		
		buffer.putInt(0xFFFFFFFF);
		
		buffer.put(getAsciiString("npe_hangar_1")); // StfFile
		buffer.putInt(0); // stf spacer
		buffer.put(getAsciiString("droid_xp_bar")); // StfName
		
		buffer.putInt(0); // stf spacer
		buffer.putLong(0);
		buffer.putLong(0);
		buffer.putLong(0);
		buffer.putLong(0);  // 70 bytes
		buffer.putLong(0);
		buffer.putLong(0);
		buffer.putLong(0);
		buffer.putLong(0);
		buffer.put((byte) 0);
		
		// Officer Supply Drop: 0x3E894347
		buffer.putInt(0xF19429F0); // model crc
		
		buffer.put(getAsciiString("sound/vo_c3po_9a.snd")); // does not cause crash when changing to anything
		
		buffer.putShort((short) 0); // unk
		buffer.putShort((short) 16544); // comm display time, unsure on how it's calculated
		buffer.flip();
		Console.println("CPM: " + StringUtilities.bytesToHex(buffer.array()));
		return buffer;
	}

}
