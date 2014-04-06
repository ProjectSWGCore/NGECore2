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
import resources.common.ConversationOption;
import resources.common.ObjControllerOpcodes;

public class NpcConversationOptions extends ObjControllerObject {
	
	private long npcId;
	private long objectId;
	private Vector<ConversationOption> conversationOptions = new Vector<ConversationOption>();

	public NpcConversationOptions(long objectId, long npcId) {
		this.objectId = objectId;
		this.npcId = npcId;
	}

	@Override
	public void deserialize(IoBuffer data) {		
	}
	
	public void addOption(ConversationOption option) {
		conversationOptions.add(option);
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(17).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putInt(ObjControllerMessage.CONVERSATION_OPTIONS);
		buffer.putLong(objectId);
		
		buffer.putInt(0);
		buffer.put((byte) conversationOptions.size());

		for(ConversationOption option : conversationOptions) {
			buffer.put(option.getOutOfBand().serialize());
		}
		int size = buffer.position();
		buffer.flip();
		return IoBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN).put(buffer.array(), 0, size).flip();
	}

}
