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
import java.util.Vector;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;
import services.chat.ChatRoom;

public class ChatQueryRoomResults extends SWGMessage {

	private ChatRoom room;
	private int requestId;
	
	public ChatQueryRoomResults(ChatRoom room, int requestId) {
		this.room = room;
		this.requestId = requestId;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
	}

	@Override
	public IoBuffer serialize() {
		String server = NGECore.getInstance().getGalaxyName();
		IoBuffer buffer = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short) 7);
		buffer.putInt(0xC4DE864E);
		
		Vector<String> users = room.getUserList();
		
		buffer.putInt(users.size());
		if (users.size() > 0) {
			for (String str : users) {
				buffer.put(getAsciiString("SWG"));
				buffer.put(getAsciiString(server));
				buffer.put(getAsciiString(str));
			}
		}
		
		buffer.putInt(0); // TODO: Invited list for chat rooms
		
		Vector<String> moderators = room.getModeratorList();
		
		buffer.putInt(moderators.size());
		if (moderators.size() > 0) {
			for (String str : moderators) {
				buffer.put(getAsciiString("SWG"));
				buffer.put(getAsciiString(server));
				buffer.put(getAsciiString(str));
			}
		}
		
		Vector<String> banned = room.getBanList();
		buffer.putInt(banned.size());
		if (banned.size() > 0) {
			for (String str : banned) {
				buffer.put(getAsciiString("SWG"));
				buffer.put(getAsciiString(server));
				buffer.put(getAsciiString(str));
			}
		}
		
		buffer.putInt(requestId);
		buffer.putInt(room.getRoomId());
		buffer.putInt(room.isPrivateRoom() ? 0 : 1);
		buffer.put((byte) (room.isModeratorsOnly() ? 1 : 0));
		
		buffer.put(getAsciiString(room.getRoomAddress()));
		
		buffer.put(getAsciiString("SWG"));
		buffer.put(getAsciiString(server));
		buffer.put(getAsciiString(room.getOwner()));
		
		buffer.put(getAsciiString("SWG"));
		buffer.put(getAsciiString(server));
		buffer.put(getAsciiString(room.getCreator()));
		
		buffer.put(getUnicodeString(room.getDescription()));
		
		buffer.putInt(0); // moderator list
		buffer.putInt(0); // user list
		return buffer.flip();
	}

}
