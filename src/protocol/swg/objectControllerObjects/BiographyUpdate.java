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
package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

public class BiographyUpdate extends ObjControllerObject {
	
	private long objectId;
	private long targetObjectId;
	
	private String biography;

	public BiographyUpdate(long objectId, long targetObjectId, String biography) {
		this.objectId = objectId;
		this.biography = biography;
		this.targetObjectId = targetObjectId;
	}

	@Override
	public void deserialize(IoBuffer data) {
		
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(28 + (biography.length() * 2)).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(0x000001DB);
		buffer.putLong(objectId); // requester
		buffer.putInt(0);
		buffer.putLong(targetObjectId);
		buffer.put(getUnicodeString(biography));
		
		return buffer.flip();
	}
	
	
}
