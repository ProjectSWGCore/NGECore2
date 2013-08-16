package protocol.swg;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

public class ChatOnAddFriend extends SWGMessage {

	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN);
	    
		result.putShort((short) 0x03);
		result.putInt(0x2B2A0D94);
		result.putLong(0);
		result.flip();
		return result;
	}

}
