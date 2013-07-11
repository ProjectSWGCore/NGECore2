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
package protocol.swg;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

public class LoginClientToken extends SWGMessage {
	
	private byte[] sessionKey;
	private int stationID;
	private String accountName;

	public LoginClientToken(byte[] sessionKey, int stationID, String accountName) {
		this.sessionKey = sessionKey;
		this.stationID = stationID;
		this.accountName = accountName;

	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(16 + sessionKey.length + accountName.length()).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short)4);
		result.putInt(0xAAB296C6);
		result.putInt(sessionKey.length);
		result.put(sessionKey);
		result.putInt(stationID);
		result.put(getAsciiString(accountName));
		result.flip();
		return result;

	}
}
