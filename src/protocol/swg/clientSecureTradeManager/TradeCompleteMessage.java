package protocol.swg.clientSecureTradeManager;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class TradeCompleteMessage extends SWGMessage {

	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 1);
		result.putInt(0xC542038B);
		return result.flip();
	}

}
