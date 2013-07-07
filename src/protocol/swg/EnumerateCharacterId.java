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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;


public class EnumerateCharacterId extends SWGMessage {
	
	private byte[] characters;
	private int characterCount = 0;
	
	public EnumerateCharacterId() {
		ByteBuffer result = ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short)2);
		result.putInt(0x65EA4574);
		
		data = result.array();
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		if (characters == null) {
			IoBuffer result = IoBuffer.allocate(9 + data.length).order(ByteOrder.LITTLE_ENDIAN);
			result.put(data);
			
			result.putInt(0);
			result.put((byte) 0);
			result.flip();

			return result;
		} else {
			IoBuffer result = IoBuffer.allocate(4 + data.length + characters.length).order(ByteOrder.LITTLE_ENDIAN);
			result.put(data);
			result.putInt(characterCount);
			result.put(characters);
			result.flip();
			return result;
		}
		
	}
	
	public int getSize() {
		return (data == null) ? 0 : data.length + characters.length + 4;
	}
	
	public void addCharacter(String character, int speciesCRC, long characterID, int galaxyID, int status) {
		IoBuffer result = IoBuffer.allocate(24 + character.length() * 2).order(ByteOrder.LITTLE_ENDIAN);
		
		result.put(getUnicodeString(character));
		result.putInt(speciesCRC);
		result.putLong(characterID);
		result.putInt(galaxyID);
		result.putInt(status);
		result.flip();
		
		if (characters == null)
			characters = result.array();
		else
			characters = IoBuffer.allocate(characters.length + result.capacity())
			.put(characters)
			.put(result.array())
			.flip()
			.array();
		characterCount++;
	}
}
