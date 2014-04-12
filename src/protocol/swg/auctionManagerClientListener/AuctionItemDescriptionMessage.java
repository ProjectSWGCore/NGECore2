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

public class AuctionItemDescriptionMessage extends SWGMessage {
	
	private long itemId;
	private String description;

	public AuctionItemDescriptionMessage(long itemId, String description) {
		this.itemId = itemId;
		this.description = description;
	}
	

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(26 + (description.length() * 2)).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 2);
		result.putInt(0xFE0E644B);
		result.putLong(itemId);
		result.put(getUnicodeString(description));
		result.putInt(0);
		result.putInt(0);
		return result.flip();
	}

}
