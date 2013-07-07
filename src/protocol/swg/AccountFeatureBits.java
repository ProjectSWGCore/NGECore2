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


public class AccountFeatureBits extends SWGMessage {
	
	public AccountFeatureBits() { 
		
		operandCount	= 2;
		opcode			= 0x979F0279;
		
		data = new byte[] { 
				(byte)0x31, (byte)0x82, (byte)0x5C, (byte)0x02, 
				(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x06, (byte)0x00, (byte)0x00, (byte)0x00, 
				(byte)0x8A, (byte)0xC0, (byte)0xEA, (byte)0x4E };
	
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() { 
		IoBuffer result = IoBuffer.allocate(22).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort(operandCount);
		result.putInt(opcode);
		result.put(data);
		result.flip();
		
		return result;
	}
}
