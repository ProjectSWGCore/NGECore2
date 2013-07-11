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
		
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putInt(ObjControllerMessage.OBJECT_MENU_RESPONSE);
		result.putLong(ownerId);
		result.putInt(0);
		result.putLong(targetId);
		result.putLong(ownerId);
		
		int size = radialOptions.size();
		result.putInt(size);
		
		if(size > 0) {
			byte counter = 1;
			for(RadialOptions radialOption : radialOptions) {
				result.put(counter++);
				result.put(radialOption.getParentId());
				result.put(radialOption.getOptionId());
				result.put((byte) 0);
				result.put(radialOption.getOptionType());

				if(radialOption.getDescription().length() > 0)
					result.put(getUnicodeString(radialOption.getDescription()));
				else
					result.putInt(0);

			}
		}
		
		result.put(radialCount);
		
		int packetSize = result.position();
		result = IoBuffer.allocate(packetSize).put(result.array(), 0, packetSize);
		return result.flip();

	}

}
