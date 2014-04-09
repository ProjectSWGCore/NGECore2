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
package protocol.swg.chat;

import java.nio.ByteOrder;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;
import services.chat.ChatRoom;

public class ChatOnCreateRoom extends SWGMessage {

	private ChatRoom room;
	private int error;
	private int requestId;
	
	public ChatOnCreateRoom(ChatRoom room, int error, int requestId) {
		this.room = room;
		this.error = error;
		this.requestId = requestId;
	}

	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		String server = NGECore.getInstance().getGalaxyName();
		IoBuffer data = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		data.setAutoExpand(true);

		data.putShort((short) 4);
		data.putInt(0x35D7CC9F);
		
		data.putInt(error);
		data.putInt(room.getRoomId());
		data.putInt(room.isPrivateRoom() ? 0 : 1);
		data.put((byte) (room.isModeratorsOnly() ? 1 : 0));
		data.put(getAsciiString(room.getRoomAddress()));
		data.put(getAsciiString("SWG"));
		data.put(getAsciiString(server));
		data.put(getAsciiString(room.getCreator()));
		data.put(getAsciiString("SWG"));
		data.put(getAsciiString(server));
		data.put(getAsciiString(room.getOwner()));
		data.put(getUnicodeString(room.getDescription()));
		
		data.putInt(0);
		/*if (room.getModeratorList().size() > 0) {
			for (CreatureObject creo : room.getModeratorList()) {
				data.put(getAsciiString("SWG"));
				data.put(getAsciiString(server));
				data.put(getAsciiString(creo.getCustomName()));
			}
		}*/
		
		data.putInt(0);	
		/*if (room.getUserList().size() > 0) {
			for (CreatureObject creo : room.getUserList()) {
				data.put(getAsciiString("SWG"));
				data.put(getAsciiString(server));
				data.put(getAsciiString(creo.getCustomName()));
			}
		}*/
		
		data.putInt(requestId);
		return data.flip();
	}

}
