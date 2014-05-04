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
import engine.resources.config.Config;

public class ChatOnChangeFriendStatus extends SWGMessage {

	private String name;
	private int type;
	private long playerID;

	// Adds or Removes a friend, not to be confused with online/offline status
	public ChatOnChangeFriendStatus(long playerID, String name, int type) {
		this.name = name;
		this.type = type;
		this.playerID = playerID;
	}
	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		Config config = NGECore.getInstance().getConfig();
		String server = config.getString("GALAXY_NAME");
		
		IoBuffer result = IoBuffer.allocate(100 + this.name.length() + server.length()).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short)0x06);
		result.putInt(0x54336726);
		result.putLong(playerID);
		
		result.put(getAsciiString("SWG"));
		result.put(getAsciiString(server));
		result.put(getAsciiString(name));
		result.putInt(0);
		result.putInt(type);
		result.put((byte) 0);
		result.flip();
		//System.out.println("ChatOnChangeFriendStatus: " + result.getHexDump());
		return result;
	}

}
