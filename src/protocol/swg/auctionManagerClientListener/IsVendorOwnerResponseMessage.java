package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class IsVendorOwnerResponseMessage extends SWGMessage {
	
	private int permission;
	private int errorCode;
	private long terminalId;
	private String terminalString;

	public IsVendorOwnerResponseMessage(int permission, int errorCode, long terminalId, String terminalString) {
		this.permission = permission;
		this.errorCode = errorCode;
		this.terminalId = terminalId;
		this.terminalString = terminalString;
	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(26 + terminalString.length()).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 6);
		result.putInt(0xCE04173E);
		result.putInt(permission);
		result.putInt(errorCode);
		result.putLong(terminalId);
		result.put(getAsciiString(terminalString));
		result.putShort((short) 0x64); // unk
		return result.flip();
	}

}
