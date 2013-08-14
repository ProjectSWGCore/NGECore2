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


public class SceneCreateObjectByCrc extends SWGMessage {
	
	private long objectId;
	private float oX;
	private float oY;
	private float oZ;
	private float oW;
	private float pX;
	private float pY;
	private float pZ;
	private int crc;
	private byte flags;
	
	public SceneCreateObjectByCrc(long objectId, float orientationX, float orientationY, float orientationZ, float orientationW, float positionX, float positionY, float positionZ, int objectCRC, byte flags) {
		this.objectId = objectId;
		
		this.oX       = orientationX;
		this.oY       = orientationY;
		this.oZ       = orientationZ;
		this.oW       = orientationW;
		
		this.pX       = positionX;
		this.pY       = positionY;
		this.pZ       = positionZ;
		
		this.crc      = objectCRC;
		this.flags    = flags;
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
 	public IoBuffer serialize() {
 		IoBuffer result = IoBuffer.allocate(47).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short)5);
		result.putInt(0xFE89DDEA);
		result.putLong(objectId);
		result.putFloat(oX);
		result.putFloat(oY);
		result.putFloat(oZ);
		result.putFloat(oW);
		result.putFloat(pX);
		result.putFloat(pY);
		result.putFloat(pZ);
		result.putInt(crc);
		result.put(flags);
		return result.flip();
	}
}
