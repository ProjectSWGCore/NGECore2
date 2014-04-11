package protocol.swg.auctionManagerClientListener;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class BidAuctionMessage extends SWGMessage {

	private long auctionId;
	private int myPrice;
	private int proxyPrice;

	@Override
	public void deserialize(IoBuffer data) {
		data.skip(6);
		setAuctionId(data.getLong());
		setMyPrice(data.getInt());
		setProxyPrice(data.getInt());
	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(long auctionId) {
		this.auctionId = auctionId;
	}

	public int getMyPrice() {
		return myPrice;
	}

	public void setMyPrice(int myPrice) {
		this.myPrice = myPrice;
	}

	public int getProxyPrice() {
		return proxyPrice;
	}

	public void setProxyPrice(int proxyPrice) {
		this.proxyPrice = proxyPrice;
	}

}
