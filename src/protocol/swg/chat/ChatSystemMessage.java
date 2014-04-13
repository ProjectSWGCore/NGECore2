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
import resources.common.OutOfBand;

public class ChatSystemMessage extends SWGMessage {
	
	private String message;
	private byte displayType;
	private OutOfBand outOfBand;
	
	public ChatSystemMessage(String message, OutOfBand outOfBand, byte displayType) {
		this.message = message;
		this.displayType = displayType;
		this.outOfBand = outOfBand;
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		IoBuffer outOfBandBuffer = outOfBand.serialize();
		IoBuffer result = IoBuffer.allocate(7 + getUnicodeString(message).length + outOfBandBuffer.array().length).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 2);
		result.putInt(Opcodes.ChatSystemMessage);
		result.put(displayType);
		result.put(getUnicodeString(message));
		result.put(outOfBandBuffer.array());
		return result.flip();
	}
	
}
