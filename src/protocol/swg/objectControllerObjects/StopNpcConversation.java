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

public class StopNpcConversation extends ObjControllerObject {
	
	private long npcId;
	private long objectId;
	private String stfFile;
	private String stfLabel;

	public StopNpcConversation(long objectId, long npcId, String stfFile, String stfLabel) {
		this.objectId = objectId;
		this.npcId = npcId;
		this.stfFile = stfFile;
		this.stfLabel = stfLabel;
	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(40 + stfFile.length() + stfLabel.length()).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(ObjControllerMessage.STOP_CONVERSATION);
		buffer.putLong(objectId);
		
		buffer.putInt(0);
		buffer.putLong(npcId);
		buffer.put(getAsciiString(stfFile));
		buffer.putInt(0);
		buffer.put(getAsciiString(stfLabel));	
		buffer.putLong(0);
		
		return buffer.flip();
	}
}
