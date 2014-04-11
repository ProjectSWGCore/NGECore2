package protocol.swg.auctionManagerClientListener;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class GetAuctionDetails extends SWGMessage {

	private long objectId;

	@Override
	public void deserialize(IoBuffer data) {
		data.skip(6);
		setObjectId(data.getLong());
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

}
