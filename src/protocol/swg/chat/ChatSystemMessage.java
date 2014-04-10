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
package protocol.swg.chat;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;
import resources.common.Opcodes;

public class ChatSystemMessage extends SWGMessage{
	
	private String stfFilename;
	private String stfName;
	private int stat;
	private String message;
	private byte displayType;
	
	public ChatSystemMessage(String message, byte displayType) {
		this.message = message;
		this.displayType = displayType;
	}
	
	public ChatSystemMessage(String stfFilename, String stfName, int stat, byte displayType) {
		this.stfFilename = stfFilename;
		this.stfName = stfName;
		this.stat = stat;
		this.displayType = displayType;
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		if (message != null) {
			IoBuffer result = IoBuffer.allocate(15 + message.length() * 2).order(ByteOrder.LITTLE_ENDIAN);
			result.putShort((short) 2);
			result.putInt(Opcodes.ChatSystemMessage);
			result.put(displayType); // 0x00 = Chat and Screen // 0x02 = Chat only
			result.put(getUnicodeString(message));
			result.putInt(0);
			return result.flip();
		} else {
			IoBuffer result = IoBuffer.allocate(99 + stfFilename.length() + stfName.length()).order(ByteOrder.LITTLE_ENDIAN);
			result.putShort((short) 4);
			result.putInt(Opcodes.ChatSystemMessage);
			result.put((byte) 0); //result.put((byte) displayType);  // 0x00 = Chat and Screen // 0x02 = Chat only
			result.putInt(0);
			result.putInt(54);
			result.putShort((short) 0);
			result.put((byte) 1);
			result.putInt(-1);
			result.put(getAsciiString(stfFilename));
			result.putInt(0);
			result.put(getAsciiString(stfName));
			result.putInt(0);
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.putInt(stat);
			result.putInt(0);
			result.put((byte) 0);
			return result.flip();
		}
	}
	
}
