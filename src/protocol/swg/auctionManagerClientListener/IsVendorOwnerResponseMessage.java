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
package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class IsVendorOwnerResponseMessage extends SWGMessage {
	
	private int permission;
	private int errorCode;
	private long terminalId;
	private String terminalString;

	public IsVendorOwnerResponseMessage(int permission, int errorCode, long terminalId, String terminalString) {
		this.permission = permission;
		this.errorCode = errorCode;
		this.terminalId = terminalId;
		this.terminalString = terminalString;
	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(26 + terminalString.length()).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 6);
		result.putInt(0xCE04173E);
		result.putInt(permission);
		result.putInt(errorCode);
		result.putLong(terminalId);
		result.put(getAsciiString(terminalString));
		result.putShort((short) 0x64); // unk
		return result.flip();
	}

}
