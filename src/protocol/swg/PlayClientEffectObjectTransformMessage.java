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

import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class PlayClientEffectObjectTransformMessage extends SWGMessage {

	private long objectId;
	private String effectFile;
	private String commandString;
	private Point3D effectorPosition; 
	private Quaternion effectorOrientation;
	public SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();

	public PlayClientEffectObjectTransformMessage(String effectFile, long objectId, String commandString, Point3D effectorPosition, Quaternion effectorOrientation) {
		
		this.effectFile = effectFile;
		this.objectId = objectId;
		this.commandString = commandString;
		this.effectorPosition = effectorPosition;
		this.effectorOrientation = effectorOrientation;
	}
	
	@Override
	public IoBuffer serialize() {
		
		IoBuffer result = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);	
		result.putShort((short) 5);
		result.putInt(0x4F5E09B6);
		result.put(getAsciiString(effectFile));		
		result.putFloat(effectorOrientation.w); // qw
		result.putFloat(effectorOrientation.x); // qx
		result.putFloat(effectorOrientation.y); // qy
		result.putFloat(effectorOrientation.z); // qz		
		result.putFloat(effectorPosition.x);    // pos.x
		result.putFloat(effectorPosition.y);    // pos.y
		result.putFloat(effectorPosition.z);    // pos.z
		result.putLong(objectId);
		result.put(getAsciiString(commandString));
				
		int size = result.position();
		result.flip();
		result = bufferPool.allocate(size, false).put(result.array(), 0, size);

		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();
	}
		
	/*
	00 09 54 6C 05 00 B6 09-5E 4F 1B 00 61 70 70 65 
	61 72 61 6E 63 65 2F 70-74 5F 6C 6F 6F 74 5F 64 
	69 73 63 2E 70 72 74 00-00 00 00 00 00 00 00 00 
	00 00 00 00 00 80 3F 00-00 00 00 24 F8 AA 3F 00 
	00 00 00 3B 54 E1 3E 33-00 00 00 06 00 6C 6F 6F 
	74 4D 65 01 EB 3C
	 */
		
	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

}
