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

import protocol.swg.ObjControllerMessage;
import resources.datatables.HitType;

@SuppressWarnings("unused")
public class ObjController_02AC extends ObjControllerObject{

	private long creatureId;

	public ObjController_02AC(long creatureId) {
		this.creatureId = creatureId;		
	}
	
	public void deserialize(IoBuffer data) {
	
	}
	
	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putInt(0x02AB);
		result.putLong(creatureId);
		result.putInt(0);        
		result.putInt(0x2F2B6FAA);
		
		int packetSize = result.position();
		result = IoBuffer.allocate(packetSize).put(result.array(), 0, packetSize);
				
		return result.flip();
		
	}
	
}
