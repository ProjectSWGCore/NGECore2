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
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

import resources.common.Opcodes;
import engine.resources.common.StringUtilities;
import engine.resources.objects.Baseline;
import engine.resources.objects.Builder;
import engine.resources.objects.SWGObject;

public class ObjectMessageBuilder {
	
	protected SWGObject object;
	
	public ObjectMessageBuilder(SWGObject object) {
		this.object = object;
	}
	
	public ObjectMessageBuilder() {
		
	}
	
	public void buildBaseline1(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		/*
		baselineBuilders.put(5, new Builder {
			public byte[] build() {
				IoBuffer = Baseline.createBuffer(2);
				buffer.putShort((short) 27);
				return buffer.array();
			}
		});
		
		deltaBuilders.put(7, new Builder {
			public byte[] build() {
				IoBuffer = Baseline.createBuffer(4);
				buffer.putInt(27);
				return buffer.array();
			}
		});
		*/
	}
	
	public void buildBaseline3(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline4(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline6(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline7(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline8(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline9(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public IoBuffer createBuffer(int size, boolean autoSize) {
		return createBuffer(size).setAutoExpand(autoSize);
	}
	
	public IoBuffer createBuffer(int size) {
		return Baseline.createBuffer(size);
	}
	
	protected byte getBoolean(boolean variable) {
		return Baseline.getBoolean(variable);
	}
	
	protected String getAsciiString(ByteBuffer buffer) {
		return StringUtilities.getAsciiString(buffer);
	}
	
	protected String getUnicodeString(ByteBuffer buffer) {
		return StringUtilities.getUnicodeString(buffer);
	}
	
	protected byte[] getAsciiString(String string) {
		return StringUtilities.getAsciiString(string);
	}
	
	protected byte[] getUnicodeString(String string) {
		return StringUtilities.getUnicodeString(string);
	}
	
	// Temporary for compatibility
	public SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();
	
	public SWGObject getObject() {
		return object;
	}
	
	public void setObject(SWGObject object) {
		this.object = object;
	}
	
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
		IoBuffer buffer = bufferPool.allocate(23 + size, false).order(ByteOrder.LITTLE_ENDIAN);
		
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
	
	private String reverse(String reverseString) {
		
	    if (reverseString.length() <= 1) return reverseString;
	    return reverse(reverseString.substring(1, reverseString.length())) + reverseString.charAt(0);
	    
	}
	
}
