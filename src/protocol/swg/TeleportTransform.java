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

public class TeleportTransform extends SWGMessage {
	
	private long charId;
	private float x;
	private float y;
	private float z;
	private float yOrient;
	private float wOrient;
	private int movementIndex;
	
	public TeleportTransform(long charId, float x, float y, float z, float yOrient, float wOrient, int movementIndex) {
		this.charId = charId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yOrient = yOrient;
		this.wOrient = wOrient;
		this.movementIndex = movementIndex;
	}
	
	public void deserialize(IoBuffer data) {
		
	}

	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short)5);
		result.putInt(0x80CE5E46);
		result.putInt(0x1B);	// this is a special flag for DataTransform, other flags don't teleport the player
		result.putInt(0x71);
		result.putLong(charId);
		result.putInt(0);
		result.putInt(0);
		result.putInt(movementIndex+1);
		result.putFloat(0);
		result.putFloat(yOrient);
		result.putFloat(0);
		result.putFloat(wOrient);
		
		result.putFloat(x);
		result.putFloat(y);
		result.putFloat(z);
		
		result.putFloat(0);
		result.putFloat(0);
		result.put((byte)0x01);
		
		return result;
	}
}
