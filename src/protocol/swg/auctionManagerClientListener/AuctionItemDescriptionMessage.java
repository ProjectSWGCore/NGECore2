package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class AuctionItemDescriptionMessage extends SWGMessage {
	
	private long itemId;
	private String description;

	public AuctionItemDescriptionMessage(long itemId, String description) {
		this.itemId = itemId;
		this.description = description;
	}
	

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(26 + (description.length() * 2)).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 2);
		result.putInt(0xFE0E644B);
		result.putLong(itemId);
		result.put(getUnicodeString(description));
		result.putInt(0);
		result.putInt(0);
		return result.flip();
	}

}
