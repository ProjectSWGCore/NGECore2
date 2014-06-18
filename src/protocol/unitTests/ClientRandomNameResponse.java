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
package protocol.unitTests;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.UnitTest;

public class ClientRandomNameResponse extends UnitTest {
	
	public ClientRandomNameResponse() {
		super();
	}
	
	public boolean isValid(IoBuffer buffer) {
		int length = buffer.array().length;
		
		if (length < 20) {
			return false;
		}
		
		buffer.skip(6);
		
		String string;
		
		string = getAsciiString(buffer);
		
		if (!checkString(string) || !string.startsWith("object/creature/player/")) {
			return false;
		}
		
		string = getUnicodeString(buffer);
		
		if (!checkString(string) || string.length() == 0) {
			return false;
		}
		
		string = getAsciiString(buffer);
		
		if (!checkString(string) || !string.equals("ui")) {
			return false;
		}
		
		if (buffer.getInt() != 0) {
			return false;
		}
		
		string = getAsciiString(buffer);
		
		if (!checkString(string) || !string.equals("name_approved")) {
			return false;
		}
		
		if (buffer.position() < length) {
			return false;
		}
		
		return true;
	}
	
}
