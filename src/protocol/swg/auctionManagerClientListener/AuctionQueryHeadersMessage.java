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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class AuctionQueryHeadersMessage extends SWGMessage {

	private int range;
	private int counter;
	private int screen;
	private int category;
	private int itemTypeCRC;
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
		setRange(data.getInt());
		setCounter(data.getInt());
		setScreen(data.getInt());
		setCategory(data.getInt());
		setItemTypeCRC(data.getInt());
		int size = data.getInt();
		try {
			setSearchString(new String(ByteBuffer.allocate(size * 2).put(data.array(), data.position(), size * 2).array(), "UTF-16LE"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		data.position(data.position() + size * 2);
		setUnkInt(data.getInt());
		setMinPrice(data.getInt());
		setMaxPrice(data.getInt());
		setIncludeEntranceFee(data.get());
		data.skip(6); // unk
		setVendorId(data.getLong());
		setVendorFlag(data.get());
		setOffset(data.getShort());

	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getScreen() {
		return screen;
	}

	public void setScreen(int screen) {
		this.screen = screen;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getItemTypeCRC() {
		return itemTypeCRC;
	}

	public void setItemTypeCRC(int itemTypeCRC) {
		this.itemTypeCRC = itemTypeCRC;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public int getUnkInt() {
		return unkInt;
	}

	public void setUnkInt(int unkInt) {
		this.unkInt = unkInt;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public byte getIncludeEntranceFee() {
		return includeEntranceFee;
	}

	public void setIncludeEntranceFee(byte includeEntranceFee) {
		this.includeEntranceFee = includeEntranceFee;
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public byte getVendorFlag() {
		return vendorFlag;
	}

	public void setVendorFlag(byte vendorFlag) {
		this.vendorFlag = vendorFlag;
	}

	public short getOffset() {
		return offset;
	}

	public void setOffset(short offset) {
		this.offset = offset;
	}

}
