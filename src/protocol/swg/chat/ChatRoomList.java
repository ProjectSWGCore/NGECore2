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
import java.util.concurrent.ConcurrentHashMap;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;
import services.chat.ChatRoom;

public class ChatRoomList extends SWGMessage {

	private ConcurrentHashMap<Integer, ChatRoom> chatRooms;
	private ChatRoom room;

	public ChatRoomList(ConcurrentHashMap<Integer, ChatRoom> chatRooms) {
		this.chatRooms = chatRooms;
	}
	
	public ChatRoomList(ChatRoom room) {
		this.room = room;
	}
	
	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		String server = NGECore.getInstance().getGalaxyName();
		IoBuffer buffer = IoBuffer.allocate(53).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		buffer.putShort((short) 2);
		buffer.putInt(0x70DEB197);
		
		if (room != null) {
			buffer.putInt(1);
			
			buffer.putInt(room.getRoomId());
			buffer.putInt((int) ((room.isPrivateRoom() ? 1 : 0)));
			buffer.put((byte) ((room.isModeratorsOnly() ? 1 : 0)));
			buffer.put(getAsciiString(room.getRoomAddress()));
			buffer.put(getAsciiString("SWG"));
			buffer.put(getAsciiString(server));
			buffer.put(getAsciiString(room.getOwner()));
			buffer.put(getAsciiString("SWG"));
			buffer.put(getAsciiString(server));
			buffer.put(getAsciiString(room.getCreator()));
			buffer.put(getUnicodeString(room.getDescription()));
			buffer.putInt(0); // moderator list (not used by client)
			buffer.putInt(0); // user list (not used by client)
		} else {
			buffer.putInt(chatRooms.size());
			chatRooms.forEach((key, value) -> {
				if (value.isVisible()) {
					buffer.putInt(value.getRoomId());
					buffer.putInt((int) ((value.isPrivateRoom() ? 1 : 0)));
					buffer.put((byte) ((value.isModeratorsOnly() ? 1 : 0)));
					buffer.put(getAsciiString(value.getRoomAddress()));
					buffer.put(getAsciiString("SWG"));
					buffer.put(getAsciiString(server));
					buffer.put(getAsciiString(value.getOwner()));
					buffer.put(getAsciiString("SWG"));
					buffer.put(getAsciiString(server));
					buffer.put(getAsciiString(value.getCreator()));
					buffer.put(getUnicodeString(value.getDescription()));
					buffer.putInt(0); // moderator list (not used by client)
					buffer.putInt(0); // user list (not used by client)
				}
			});
		}
		buffer.flip();
		//StringUtilities.printBytes(buffer.array());
		return buffer;
	}

}
