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
import org.apache.mina.core.buffer.SimpleBufferAllocator;

import engine.resources.objects.SWGObject;

public class AttributeListMessage extends SWGMessage {
	
	private SimpleBufferAllocator bufferPool;
	private SWGObject target;

	public AttributeListMessage(SWGObject target, SimpleBufferAllocator bufferPool) {
		this.target = target;
		this.bufferPool = bufferPool;
	}


	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		final IoBuffer result = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 5);
		result.putInt(0xF3F12F2A);
		
		result.putLong(target.getObjectID());
		result.putShort((short) 0);

		synchronized(target.getMutex()) {
			result.putInt(target.getAttributes().size());
			target.getAttributes().forEach((key, value) -> {
				result.put(getAsciiString(key));
				result.put(getUnicodeString(value));
			});
		}
		result.putInt(0);
		int size = result.position();
		IoBuffer result2 = bufferPool.allocate(size, false).put(result.array(), 0, size);

		return result2.flip();
		
	}

}
