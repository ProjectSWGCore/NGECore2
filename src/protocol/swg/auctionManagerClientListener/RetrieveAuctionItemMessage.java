package protocol.swg.auctionManagerClientListener;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class RetrieveAuctionItemMessage extends SWGMessage {

	private long objectId;
	private long vendorId;

	@Override
	public void deserialize(IoBuffer data) {
		data.skip(6);
		setObjectId(data.getLong());
		setVendorId(data.getLong());
	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

}
