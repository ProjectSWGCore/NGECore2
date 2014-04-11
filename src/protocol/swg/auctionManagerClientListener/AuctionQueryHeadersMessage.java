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
		data.skip(5); // unk
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
