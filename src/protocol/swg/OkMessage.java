package protocol.swg;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.StringUtilities;

public class OkMessage extends SWGMessage {

	public OkMessage() {
		
	}
	
	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 1);
		buffer.putInt(0xA16CF9AF);
		System.out.println(StringUtilities.bytesToHex(buffer.array()));
		return buffer.flip();
	}

}
