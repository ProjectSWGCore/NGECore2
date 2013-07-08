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
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

public class AttributeListMessage extends SWGMessage {
	
	private Map<String, String> attributes;
	private long objectId;
	private SimpleBufferAllocator bufferPool;

	public AttributeListMessage(Map<String, String> attributes, long objectId, SimpleBufferAllocator bufferPool) {
		this.attributes = attributes;
		this.objectId = objectId;
		this.bufferPool = bufferPool;
	}

	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 5);
		result.putInt(0xF3F12F2A);
		
		result.putLong(objectId);
		result.putShort((short) 0);

		result.putInt(attributes.size());

		for(Entry<String, String> e : attributes.entrySet()) {
			result.put(getAsciiString(e.getKey()));
			result.put(getUnicodeString(e.getValue()));
		}
		result.putInt(0);
		int size = result.position();
		result = bufferPool.allocate(size, false).put(result.array(), 0, size);

		return result.flip();
		
	}

}
