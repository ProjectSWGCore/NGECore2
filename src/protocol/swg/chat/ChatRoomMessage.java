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

public class ChatRoomMessage extends SWGMessage {

	private String character;
	private String message;
	private int roomId;

	public ChatRoomMessage(int roomId, String player, String message) {
		this.roomId = roomId;
		this.character = player;
		this.message = message;
	}

	@Override
	public void deserialize(IoBuffer data) {
	}

	@Override
	public IoBuffer serialize() {
		String server = NGECore.getInstance().getGalaxyName();

		IoBuffer buffer = IoBuffer.allocate(27 + server.length() + character.length() + (message.length() * 2)).order(ByteOrder.LITTLE_ENDIAN);

		buffer.putShort((short) 5);
		buffer.putInt(0xCD4CE444);
		buffer.put(getAsciiString("SWG"));
		buffer.put(getAsciiString(server));
		buffer.put(getAsciiString(character));
		buffer.putInt(roomId);
		buffer.put(getUnicodeString(message));
		buffer.putInt(0); // out of band package ?
		return buffer.flip();
	}

}
