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
import engine.resources.common.CRC;
import resources.common.Opcodes;

public class ChatOnEnteredRoom extends SWGMessage {

	private String characterName;
	private int success;
	private int roomId;
	private boolean join;

	public ChatOnEnteredRoom(String characterName, int success, int roomId, boolean join) {
		this.characterName = characterName;
		this.success = success;
		this.roomId = roomId;
	}

	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		String galaxy = NGECore.getInstance().getGalaxyName();
		IoBuffer buffer = IoBuffer.allocate(27 + galaxy.length() + characterName.length()).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 5);
		if (join)
			buffer.putInt(Opcodes.ChatOnEnteredRoom);
		else
			buffer.putInt(CRC.StringtoCRC("ChatOnLeaveRoom"));
		buffer.put(getAsciiString("SWG"));
		buffer.put(getAsciiString(galaxy));
		buffer.put(getAsciiString(characterName));
		buffer.putInt(success);
		buffer.putInt(roomId);
		buffer.putInt(0);
		return buffer.flip();
	}

	public static final int JOIN_SUCCESS = 0;
	public static final int JOIN_FAIL_NO_INVITE = 0x10;
}
