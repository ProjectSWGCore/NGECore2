package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class AcceptAuctionResponseMessage extends SWGMessage {
	
	private long itemId;
	private int error;

	public AcceptAuctionResponseMessage(long itemId, int error) {
		this.itemId = itemId;
		this.error = error;
	}

	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(18).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 3);
		result.putInt(0xC58A446E);
		result.putLong(itemId);
		result.putInt(error);
		return result.flip();
	}

}
