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

import org.apache.mina.core.buffer.IoBuffer;


public class ClientVerifyAndLockNameRequest extends SWGMessage {
	
	private String name;
	private String raceTemplate;
	private String firstName;
	private String lastName;
	
	public ClientVerifyAndLockNameRequest() {
		
	}
	
	public void deserialize(IoBuffer buffer) {
		buffer.position(6); // Skips SOE and SWG opcodes
		raceTemplate = getNextAsciiString(buffer);
		name = getNextUnicodeString(buffer);
		System.out.println("msg: " + name);	
		// Gets the first and last names
		String [] splitName = name.split(" ");
		firstName = splitName[0];
		lastName  = (splitName.length == 1) ? "" : splitName[1];
	}
	
	public IoBuffer serialize() {
		return IoBuffer.allocate(0);
	}

	public String getFirstName()    { return firstName; }
	public String getLastName()     { return lastName; }
	public String getName()         { return name; }
	public String getRaceTemplate() { return raceTemplate; }
}
