package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class BidAuctionResponseMessage extends SWGMessage {

	private long objectId;
	private int status;
	
	public static final int SUCCESS = 0;
	public static final int INVALIDAUCTIONER = 1;
	public static final int INVALIDITEM = 2;
	public static final int INVALIDPRICE = 4;
	public static final int NOTENOUGHCREDITS = 9;
	public static final int PURCHASEFAILED = 10;
	public static final int PURCHASEREJECTED = 11;
	public static final int PRICETOOHIGH = 13;
	public static final int PRICEOVERFLOW = 14;

	public BidAuctionResponseMessage(long objectId, int status) {
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
		result.putInt(0x8FCBEF4A);
		result.putLong(objectId);
		result.putInt(status);
		return result.flip();
	}

}
