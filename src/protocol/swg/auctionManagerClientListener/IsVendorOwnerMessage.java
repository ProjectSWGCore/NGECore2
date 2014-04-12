package protocol.swg.auctionManagerClientListener;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class IsVendorOwnerMessage extends SWGMessage {

	private long terminalId;

	@Override
	public void deserialize(IoBuffer data) {
		data.skip(6);
		setTerminalId(data.getLong());
	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(long terminalId) {
		this.terminalId = terminalId;
	}

}
