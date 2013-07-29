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
package resources.common;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StringUtilities {
	
	public static String getAsciiString(ByteBuffer buffer) {
		return getString(buffer, "US-ASCII");
	}
	
	public static String getUnicodeString(ByteBuffer buffer) {
		return getString(buffer, "UTF-16LE");
	}
	
	public static byte[] getAsciiString(String string) {
		return getString(string, "US-ASCII");
	}
	
	public static byte[] getUnicodeString(String string) {
		return getString(string, "UTF-16LE");
	}
	
	private static String getString(ByteBuffer buffer, String charFormat) {
		String result;
		int length;
		
		if (charFormat == "UTF-16LE") {
			length = buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
		} else {
			length = buffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
		}
		
		int bufferPosition = buffer.position();
		
		try {
			result = new String(buffer.array(), bufferPosition, length, charFormat);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		
		buffer.position(bufferPosition + length);
		
		return result;
	}
	
	private static byte[] getString(String string, String charFormat) {
		ByteBuffer result;
		int length = 2 + string.length();
		
		if (charFormat == "UTF-16LE") {
			result = ByteBuffer.allocate(length * 2).order(ByteOrder.LITTLE_ENDIAN);
			result.putInt(string.length());
		} else {
			result = ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN);
			result.putShort((short)string.length());
		}
		
		try {
			result.put(string.getBytes(charFormat));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new byte[] { };
		}
		
		return result.array();		
	}

}
