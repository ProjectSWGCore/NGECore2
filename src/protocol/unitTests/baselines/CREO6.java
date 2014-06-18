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

public class CREO6 extends UnitTest {
	
	public CREO6() {
		super();
	}
	
	public boolean isValid(IoBuffer buffer) {
		int length = buffer.array().length;
		
		buffer.skip(19);
		
		if (buffer.getInt() != (length - 23)) {
			return false;
		}
		
		if (buffer.getShort() != 35) {
			return false;
		}
		
		buffer.skip(4);
		
		if (!checkStf(buffer, false)) {
			return false;
		}
		
		if (buffer.get() > 1) {
			return false;
		}
		
		int unknownSetSize = buffer.getInt();
		
		buffer.skip(4);
		
		for (int i = 0; i < unknownSetSize; i++) {
			if (!checkObjectId(buffer.getLong())) {
				return false;
			}
		}
		
		buffer.skip(28);
		
		if (buffer.getShort() > 500) {
			return false;
		}
		
		if (buffer.getInt() > 2000) {
			return false;
		}
		
		String string;
		
		string = getAsciiString(buffer);
		
		if (!checkString(string)) {
			return false;
		}
		
		string = getAsciiString(buffer);
		
		if (!checkString(string)) {
			return false;
		}
		
		if (!checkObjectId(buffer.getLong())) {
			return false;
		}
		
		if (!checkObjectId(buffer.getLong())) {
			return false;
		}
		
		if (!checkObjectId(buffer.getLong())) {
			return false;
		}
		
		string = getAsciiString(buffer);
		
		if (!checkString(string)) {
			return false;
		}
		
		buffer.skip(8);
		
		if (core.guildService.getGuildById(buffer.getInt()) == null) {
			return false;
		}
		
		if (!checkObjectId(buffer.getLong())) {
			return false;
		}
		
		if (!checkObjectId(buffer.getLong())) {
			return false;
		}
		
		buffer.skip(9);
		
		if (buffer.getInt() != 6) {
			return false;
		}
		
		buffer.skip(8);
		
		if (buffer.getInt() != 0) {
			return false;
		}
		
		buffer.skip(4);
		
		if (buffer.getInt() != 0) {
			return false;
		}
		
		if (buffer.getInt() != 300) {
			return false;
		}
		
		if (buffer.getInt() != 0) {
			return false;
		}
		
		if (buffer.getInt() != 6) {
			return false;
		}
		
		buffer.skip(8);
		
		if (buffer.getInt() != 0) {
			return false;
		}
		
		buffer.skip(4);
		
		if (buffer.getInt() != 0) {
			return false;
		}
		
		if (buffer.getInt() != 300) {
			return false;
		}
		
		if (buffer.getInt() != 0) {
			return false;
		}
		
		int equipmentListSize = buffer.getInt();
		
		buffer.skip(4);
		
		for (int i = 0; i < equipmentListSize; i++) {
			buffer.skip(buffer.getShort());
			buffer.skip(4);
			
			if (!checkObjectId(buffer.getLong())) {
				return false;
			}
			
			if (!checkCrc(buffer.getInt())) {
				return false;
			}
			
			if (buffer.get() == 1) {
				if (buffer.getShort() != 20) {
					return false;
				}
				
				buffer.skip(4);
				
				if (!checkStf(buffer, true)) {
					return false;
				}
				
				string = getUnicodeString(buffer);
				
				if (!checkString(string)) {
					return false;
				}
				
				if (buffer.getInt() > 1) {
					return false;
				}
				
				if (buffer.getInt() > 0) {
					return false;
				}
				
				if (buffer.getInt() > 0) {
					return false;
				}
				
				buffer.skip(buffer.getShort());
				
				int componentCustomizationSize = buffer.getInt();
				
				buffer.skip(4);
				
				for (int n = 0; n < componentCustomizationSize; n++) {
					checkCrc(buffer.getInt());
				}
				
				buffer.skip(12);
				
				if (buffer.get() > 1) {
					return false;
				}
				
				buffer.skip(28);
				
				if (buffer.getShort() != 9) {
					return false;
				}
				
				buffer.skip(4);
				
				if (!checkStf(buffer, true)) {
					return false;
				}
				
				buffer.skip(41);
			}
		}
		
		string = getAsciiString(buffer);
		
		if (!checkString(string)) {
			return false;
		}
		
		if (buffer.get() > 1) {
			return false;
		}
		
		int buffListSize = buffer.getInt();
		
		buffer.skip(4);
		
		for (int i = 0; i < buffListSize; i++) {
			int hasCrc = buffer.get();
			
			if (hasCrc > 1) {
				return false;
			}
			
			buffer.skip(4);
			
			if (hasCrc == 1 && !checkCrc(buffer.getInt())) {
				return false;
			}
			
			buffer.skip(12);
		}
		
		if (buffListSize > 1) {
			buffer.skip(4);
		}
		
		if (buffer.get() > 1) {
			return false;
		}
		
		if (buffer.get() > 2) {
			return false;
		}
		
		buffer.skip(4);
		
		if (buffer.get() > 1) {
			return false;
		}
		
		if (buffer.get() > 1) {
			return false;
		}
		
		if (buffer.get() > 1) {
			return false;
		}
		
		int appearanceEquipmentListSize = buffer.getInt();
		
		buffer.skip(4);
		
		for (int i = 0; i < appearanceEquipmentListSize; i++) {
			buffer.skip(buffer.getShort());
			buffer.skip(4);
			
			if (!checkObjectId(buffer.getLong())) {
				return false;
			}
			
			if (!checkCrc(buffer.getInt())) {
				return false;
			}
			
			if (buffer.get() == 1) {
				if (buffer.getShort() != 20) {
					return false;
				}
				
				buffer.skip(4);
				
				if (!checkStf(buffer, true)) {
					return false;
				}
				
				string = getUnicodeString(buffer);
				
				if (!checkString(string)) {
					return false;
				}
				
				if (buffer.getInt() > 1) {
					return false;
				}
				
				if (buffer.getInt() > 0) {
					return false;
				}
				
				if (buffer.getInt() > 0) {
					return false;
				}
				
				buffer.skip(buffer.getShort());
				
				int componentCustomizationSize = buffer.getInt();
				
				buffer.skip(4);
				
				for (int n = 0; n < componentCustomizationSize; n++) {
					checkCrc(buffer.getInt());
				}
				
				buffer.skip(12);
				
				if (buffer.get() > 1) {
					return false;
				}
				
				buffer.skip(28);
				
				if (buffer.getShort() != 9) {
					return false;
				}
				
				buffer.skip(4);
				
				if (!checkStf(buffer, true)) {
					return false;
				}
				
				buffer.skip(41);
			}
		}
		
		buffer.skip(8);
		
		if (buffer.position() < length) {
			return false;
		}
		
		return true;
	}
	
}
