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

public class RetrieveAuctionItemResponseMessage extends SWGMessage {

	private long objectId;
	private int status;
	
	public static final int SUCCESS = 0;
	public static final int NOTALLOWED = 1;
	public static final int FULLINVENTORY = 12;
	public static final int TOOFAR = 0x100;
	public static final int DONTRETRIEVE = 0x200;

	public RetrieveAuctionItemResponseMessage(long objectId, int status) {
		this.objectId = objectId;
		this.status = status;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(18).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 3);
		result.putInt(0x9499EF8C);
		result.putLong(objectId);
		result.putInt(status);
		return result.flip();
	}
	
	public void setStatus(int status) {
		this.status = status;
	}


}
