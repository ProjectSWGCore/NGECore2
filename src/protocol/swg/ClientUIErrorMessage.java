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


public class ClientUIErrorMessage extends SWGMessage {

	private String errorType;
	private String errorMessage;
	
	public ClientUIErrorMessage(String errorType, String errorMessage) {
		operandCount		= 3;
		opcode				= 0xB5ABF91A;
		
		this.errorType		= errorType;
		this.errorMessage	= errorMessage;
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		int	size = 11 + errorType.length() + errorMessage.length();
		IoBuffer result = IoBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort(operandCount);
		result.putInt(opcode);
		result.put(getAsciiString(errorType));
		result.put(getAsciiString(errorMessage));
		result.put((byte)0);
		result.flip();
		
		return result;
	}
}
