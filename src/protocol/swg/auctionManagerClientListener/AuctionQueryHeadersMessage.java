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

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class AuctionQueryHeadersMessage extends SWGMessage {

	private int range;
	private int counter;
	private int screen;
	private int category;
	private int itemTypeCRC;
	private byte unk;
	private String searchString;
	private int unkInt;
	private int minPrice;
	private int maxPrice;
	private byte includeEntranceFee;
	private long vendorId;
	private byte vendorFlag;
	private short offset;

	@Override
	public void deserialize(IoBuffer data) {
		data.skip(6);
		this.range = data.getInt();
		this.counter = data.getInt();
		this.screen = data.getInt();
		this.category = data.getInt();
		this.itemTypeCRC = data.getInt();
		this.unk = data.get();
		int size = data.getInt();
		
		this.searchString = new String(ByteBuffer.allocate(size * 2).put(data.array(), data.position(), size * 2).array(), StandardCharsets.UTF_16LE);

		data.position(data.position() + size * 2);
		
		this.unkInt = data.getInt();
		this.minPrice = data.getInt();
		this.maxPrice = data.getInt();
		this.includeEntranceFee = data.get();	// Ziggy - boolean?
		data.skip(5); // unk
		this.vendorId = data.getLong();
		this.vendorFlag = data.get();
		this.offset = data.getShort();

	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRange() {
		return range;
	}

	public int getCounter() {
		return counter;
	}
	
	public int getScreen() {
		return screen;
	}

	public int getCategory() {
		return category;
	}
	
	public int getItemTypeCRC() {
		return itemTypeCRC;
	}

	public String getSearchString() {
		return searchString;
	}

	public int getUnkInt() {
		return unkInt;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public byte getIncludeEntranceFee() {
		return includeEntranceFee;
	}

	public long getVendorId() {
		return vendorId;
	}

	public byte getVendorFlag() {
		return vendorFlag;
	}

	public short getOffset() {
		return offset;
	}

	public byte getUnk() {
		return unk;
	}

}
