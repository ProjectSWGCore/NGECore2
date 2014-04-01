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
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;

import resources.common.RadialOptions;

public class ObjectMenuResponse extends ObjControllerObject {
	
	private long ownerId;
	private long targetId;
	private Vector<RadialOptions> radialOptions;
	private byte radialCount;

	public ObjectMenuResponse() {
		
	}
	
	public ObjectMenuResponse(long ownerId, long targetId, Vector<RadialOptions> radialOptions, byte radialCount) {
		
		this.ownerId = ownerId;
		this.targetId = targetId;
		this.radialOptions = radialOptions;
		this.radialCount = radialCount;

	}


	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		int size = 37;
		byte counter = 0;
		
		for (RadialOptions radialOption : radialOptions) {
			size += 5 + getUnicodeString(radialOption.getDescription()).length;
		}
		
		IoBuffer result = IoBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN);
		result.putInt(ObjControllerMessage.OBJECT_MENU_RESPONSE);
		result.putLong(ownerId);
		result.putInt(0);
		result.putLong(targetId);
		result.putLong(ownerId);
		result.putInt(radialOptions.size());
		for (RadialOptions radialOption : radialOptions) {
			result.put(++counter);
			result.put(radialOption.getParentId());
			result.putShort(radialOption.getOptionId());
			//result.put(radialOption.getOptionType()); 
			result.put((byte) 3);
			result.put(getUnicodeString(radialOption.getDescription()));
		}
		result.put(radialCount);
		
		return result.flip();
	}

}
