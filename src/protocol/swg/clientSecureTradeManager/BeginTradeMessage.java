package protocol.swg.clientSecureTradeManager;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class BeginTradeMessage extends SWGMessage {

	private long objectId;
	
	public BeginTradeMessage(long objectId) {
		this.objectId = objectId;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 2);
		result.putInt(0x325932D8);
		result.putLong(objectId);
		return result.flip();
	}

}
