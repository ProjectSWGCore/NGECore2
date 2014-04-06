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
import resources.common.ObjControllerOpcodes;
import resources.common.OutOfBand;

public class NpcConversationMessage extends ObjControllerObject {
	
	private long objectId;
	private OutOfBand outOfBand;

	public NpcConversationMessage(long objectId, OutOfBand outOfBand) {
		this.objectId = objectId;
		this.outOfBand = outOfBand;
	}

	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer outOfBandBuffer = outOfBand.serialize();
		IoBuffer buffer = IoBuffer.allocate(16 + outOfBandBuffer.array().length).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(ObjControllerMessage.CONVERSATION_MESSAGE);
		buffer.putLong(objectId);
		buffer.putInt(0);
		buffer.put(outOfBandBuffer.array());
		System.out.println(buffer.getInt(16));
		return buffer.flip();
	}

}
