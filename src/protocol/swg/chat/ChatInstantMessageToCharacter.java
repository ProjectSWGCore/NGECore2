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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

@SuppressWarnings("unused")

public class ChatInstantMessageToCharacter extends SWGMessage {

	private static final int maxdistance = 1000;
	private String game;
	private String galaxy;
	private String recipient;
	private String message;
	private int sequence;
	
	public ChatInstantMessageToCharacter() {
		
	}
	
	public void deserialize(IoBuffer buffer) {
		buffer.getShort();
		buffer.getInt();
		
		int size;
				
		try {
			size = buffer.getShort();
			game = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "UTF8");
			buffer.position(buffer.position() + size);
			
			size = buffer.getShort();
			galaxy = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII");
			buffer.position(buffer.position() + size);
			
			size = buffer.getShort();
			recipient = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII");
			buffer.position(buffer.position() + size);
			
			size = buffer.getInt();
			message = new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE");
			buffer.position(buffer.position() + size * 2);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		

		
		//game = getNextAsciiString(buffer);
		//galaxy = getNextAsciiString(buffer);
		//recipient = getNextAsciiString(buffer);
		//message = getNextUnicodeString(buffer);
		buffer.getInt();
		sequence = buffer.getInt();
	}
	
	public IoBuffer serialize() {
		return IoBuffer.allocate(0);
	}
	
	public String getGalaxy()    { return galaxy; }
	public String getGame()      { return game; }
	public String getMessage()   { return message; }
	public String getRecipient() { return recipient; }
	public int    getSequence()  { return sequence; }
	
}
