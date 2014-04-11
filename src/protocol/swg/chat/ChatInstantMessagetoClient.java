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


public class ChatInstantMessagetoClient extends SWGMessage{
	
	private String galaxy;
	private String message;
	private String name;

	public ChatInstantMessagetoClient(String galaxy, String message, String name) {
		
		this.galaxy = galaxy;
		this.message = message;
		this.name = name;
		
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(30 + galaxy.length() + message.length() * 2 + name.length()).order(ByteOrder.LITTLE_ENDIAN);

		result.putShort((short) 4);
		result.putInt(0x3C565CED);
		result.put(getAsciiString("SWG"));    							
		result.put(getAsciiString(galaxy));  
		result.put(getAsciiString(name));  
		result.put(getUnicodeString(message));    							
		result.putInt(0);

		return result.flip();
		
	}
}
