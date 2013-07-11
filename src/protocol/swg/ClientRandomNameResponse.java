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

public class ClientRandomNameResponse extends SWGMessage {
	
	private String raceTemplate;
	private String name;

	public ClientRandomNameResponse(String raceTemplate, String name) {
		
		this.raceTemplate = raceTemplate;
		this.name = name;

	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(35 + raceTemplate.length() + name.length() * 2).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short)4);
		result.putInt(0xE85FB868);
		result.put(getAsciiString(raceTemplate));	// Race File
		result.put(getUnicodeString(name));		// Random Name 
		result.put(getAsciiString("ui"));				// STF File
		result.putInt(0);    							// Spacer/unk
		result.put(getAsciiString("name_approved"));	// Approves Name, for Random Name Generation this always needs to be "name_approved" 
		
		result.flip();
		return result;
	}
	
}
