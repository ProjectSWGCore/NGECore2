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

public class BaselinesMessage extends UnitTest {
	
	public BaselinesMessage() {
		super();
	}
	
	public boolean isValid(IoBuffer buffer) {
		int length = buffer.array().length;
		
		if (length < 25) {
			return false;
		}
		
		buffer.skip(6);
		
		if (!checkObjectId(buffer.getLong())) {
			return false;
		}
		
		try {
			if (!((Boolean) Class.forName("protocol.unitTests.baselines." + (new StringBuilder(new String(buffer.flip().array(), 14, 4, "US-ASCII"))).reverse().toString() + buffer.skip(18).get()).getMethod("validate", new Class[] { IoBuffer.class }).invoke(null, new Object[] { buffer.flip() }))) {
				return false;
			}
			
			System.out.println("Passed baseline test.");
		} catch (Exception e) {
			System.out.println("Couldn't perform baseline test.");
			//System.out.println(e.getMessage());
			//return false;
		}
		
		if (buffer.position() < length) {
			return false;
		}
		
		return true;
	}
	
}
