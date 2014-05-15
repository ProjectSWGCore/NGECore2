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

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class ChatOnLeaveRoom extends SWGMessage {

	private String server;
	private String character;
	private String channelAddress;
	private int errorId;
	private int channelId;
	private int requestId;

	public ChatOnLeaveRoom() { }
	
	public ChatOnLeaveRoom(String server, String character) {
		this.server = server;
		this.character = character;
		this.errorId = 0;
		this.requestId = 0;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		data.getShort();
		data.getInt();
		getAsciiString(data);
		getAsciiString(data);
		character = getAsciiString(data);
		channelAddress = getAsciiString(data);
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(28 + server.length() + character.length()).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short) 5);
		buffer.putInt(0x60B5098B);
		
		buffer.put(getAsciiString("SWG"));
		buffer.put(getAsciiString(server));
		buffer.put(getAsciiString(character));
		
		buffer.putInt(errorId);
		buffer.putInt(channelId);
		buffer.putInt(requestId);
		
		return buffer.flip();
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getChannelAddress() {
		return channelAddress;
	}

	public void setChannelAddress(String channelAddress) {
		this.channelAddress = channelAddress;
	}

	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

}
