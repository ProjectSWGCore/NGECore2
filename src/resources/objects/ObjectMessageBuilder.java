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
package resources.objects;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

import resources.common.Opcodes;

import engine.resources.objects.SWGObject;

public abstract class ObjectMessageBuilder {
	
	public SWGObject object;
	public SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();
	
	public IoBuffer createBaseline(String objectType, byte viewType, IoBuffer data, int size) {
		IoBuffer buffer = bufferPool.allocate(23 + size, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short) 5);
		buffer.putInt(0x68A75F0C);
		buffer.putLong(object.getObjectID());
		try {
			buffer.put(reverse(objectType).getBytes("US-ASCII"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		buffer.put(viewType);
		buffer.putInt(size);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public IoBuffer createDelta(String objectType, byte viewType, short updateCount, short updateType, IoBuffer data, int size) {
		IoBuffer buffer = bufferPool.allocate(27 + size, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short) 5);
		buffer.putInt(Opcodes.DeltasMessage);
		buffer.putLong(object.getObjectID());
		try {
			buffer.put(reverse(objectType).getBytes("US-ASCII"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		buffer.put(viewType);
		buffer.putInt(size);
		buffer.putShort(updateCount);
		buffer.putShort(updateType);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public IoBuffer createDelta(String objectType, byte viewType, short updateCount, IoBuffer data, int size) {
		IoBuffer buffer = bufferPool.allocate(25 + size, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short) 5);
		buffer.putInt(Opcodes.DeltasMessage);
		buffer.putLong(object.getObjectID());
		try {
			buffer.put(reverse(objectType).getBytes("US-ASCII"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		buffer.put(viewType);
		buffer.putInt(size);
		buffer.putShort(updateCount);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public IoBuffer createDeltaObject(short updateType, IoBuffer data, int size) {
		IoBuffer buffer = bufferPool.allocate(2 + size, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort(updateType);
		buffer.put(data.array());
		
		return buffer;
	}
	
	public SWGObject getObject() { return object; }
	public void setObject(SWGObject object) { this.object = object; }
	
	public abstract void sendListDelta(byte viewType, short updateType, IoBuffer buffer);
	
	public abstract void sendBaselines();
	
	private String reverse(String reverseString) {
		
	    if (reverseString.length() <= 1) return reverseString;
	    return reverse(reverseString.substring(1, reverseString.length())) + reverseString.charAt(0);
	    
	}

	protected String getAsciiString(ByteBuffer buffer) { return getString(buffer, "US-ASCII"); }
	protected String getUnicodeString(ByteBuffer buffer) { return getString(buffer, "UTF-16LE"); }
	protected byte[] getAsciiString(String string) { return getString(string, "US-ASCII"); }
	protected byte[] getUnicodeString(String string) { return getString(string, "UTF-16LE"); }
	
	private String getString(ByteBuffer buffer, String charFormat) {
		String result;
		int length;
		if (charFormat == "UTF-16LE")
			length = buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
		else
			length = buffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
		
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
	
	private byte[] getString(String string, String charFormat) {
		ByteBuffer result;
		int length = 2 + string.length();
		if (charFormat == "UTF-16LE") {
			result = ByteBuffer.allocate(length * 2).order(ByteOrder.LITTLE_ENDIAN);
			result.putInt(string.length());
		}
		else {
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
