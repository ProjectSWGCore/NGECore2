package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class CancelLiveAuctionResponseMessage extends SWGMessage {
	
	public static final int SUCCESS = 0;
	public static final int NOTALLOWED = 1;
	public static final int INVALIDITEM = 2;
	public static final int NOTOWNER = 8;
	public static final int ALREADYCOMPLETED = 15;
	private long itemId;
	private int status;
	
	public CancelLiveAuctionResponseMessage(long itemId, int status) {
		this.itemId = itemId;
		this.status = status;
	}


	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(19).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 4);
		result.putInt(0x7DA2246C);
		result.putLong(itemId);
		result.putInt(status);
		result.put((byte) 0); // unk
		return result.flip();
	}

}
