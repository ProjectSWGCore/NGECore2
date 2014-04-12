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

}
