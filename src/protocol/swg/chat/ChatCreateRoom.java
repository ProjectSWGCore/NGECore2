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

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;


public class ChatCreateRoom extends SWGMessage {

	private String address, title;
	private boolean privacy, moderatorOnly;
	private int request;

	public ChatCreateRoom() { }
	@Override
	public void deserialize(IoBuffer data) {

		this.privacy = (boolean) ((data.get() == 1) ? false : true);
		this.moderatorOnly = (boolean) ((data.get() == 1) ? true : false);
		data.getShort(); // unk
		this.address = getAsciiString(data);
		this.title = getAsciiString(data);
		this.request = data.getInt();
	}

	@Override
	public IoBuffer serialize() {
		return null;
	}

	public String getAddress() {
		return address;
	}
	public String getTitle() {
		return title;
	}
	public boolean isPrivacy() {
		return privacy;
	}
	public boolean isModeratorOnly() {
		return moderatorOnly;
	}
	
	public int getRequest() {
		return request;
	}

}
