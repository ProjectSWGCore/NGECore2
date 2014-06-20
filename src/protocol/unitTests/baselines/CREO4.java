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
package protocol.unitTests.baselines;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.UnitTest;

public class CREO4 extends UnitTest {
	
	public CREO4() {
		super();
	}
	
	public boolean isValid(IoBuffer buffer) {
		int length = buffer.array().length;
		
		buffer.skip(19);
		
		if (buffer.getInt() != (length - 23)) {
			return false;
		}
		
		if (buffer.getShort() != 16) {
			return false;
		}
		
		buffer.skip(8);
		
		if (buffer.getInt() != 0) {
			return false;
		}
		
		if (buffer.getInt() != 0) {
			return false;
		}
		
		int skillModListSize = buffer.getInt();
		
		buffer.skip(4);
		
		for (int i = 0; i < skillModListSize; i++) {
			if (buffer.get() != 0) {
				return false;
			}
			
			if (!checkString(getAsciiString(buffer))) {
				return false;
			}
			
			buffer.skip(8);
		}
		
		buffer.skip(0);
		
		if (!checkObjectId(buffer.getLong())) {
			return false;
		}
		
		buffer.skip(24);
		
		if (buffer.getInt() > 0) {
			return false;
		}
		
		if (buffer.getInt() > 0) {
			return false;
		}
		
		buffer.skip(4);
		
		if (buffer.position() < length) {
			return false;
		}
		
		return true;
	}
	
}
