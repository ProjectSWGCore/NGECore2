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

public class CreateAuctionResponseMessage extends SWGMessage {

	public static final int SUCCESS = 0;
	public static final int INVALIDAUCTIONER = 1;
	public static final int INVALIDITEM = 2;
	public static final int BADVENDOR = 3;
	public static final int INVALIDPRICE = 4;
	public static final int INVALIDDURATION = 5;
	public static final int ALREADYFORSALE = 6;
	public static final int UNKERROR = 6;
	public static final int DONTOWNITEM = 8;
	public static final int NOTENOUGHCREDITS = 9;
	public static final int TOOMANYITEMS = 13;
	public static final int PRICETOOHIGH = 14;
	public static final int CANTSELLTRADINGITEM = 19;
	public static final int VENDORFULL = 25;

	private long objectId;
	private int status;
	
	public CreateAuctionResponseMessage(long objectId, int status) {
		this.objectId = objectId;
		this.status = status;
	}


	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 4);
		result.putInt(0x0E61CC92);
		result.putLong(objectId);
		result.putInt(status);
		result.putShort((short) 0);
		return result.flip();
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

}
