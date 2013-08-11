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

import protocol.swg.SWGMessage;

public class UpdateTransformWithParentMessage extends SWGMessage {
	
	private long objectId;
	private long cellId;
	private short x;
	private short y;
	private short z;
	private int movementCounter;
	private byte direction;
	private float speed;
	
	public UpdateTransformWithParentMessage(long objectId, long cellId, short x, short y, short z, int movementCounter, byte direction, float speed) {
		this.objectId = objectId;
		this.cellId = cellId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.movementCounter = movementCounter;
		this.direction = direction;
		this.speed = speed;
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(42).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short) 0x0A);
		result.putInt(0xC867AB5A);
		result.putLong(cellId);
		result.putLong(objectId);
		result.putShort(x);
		result.putShort(y);
		result.putShort(z);
		result.putInt(movementCounter+1);
		result.put((byte) speed);
		result.put(direction);
		result.put((byte) 1);
		result.put((byte) 0);
		result.flip();
		return result;
	}
}
