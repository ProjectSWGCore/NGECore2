package protocol.swg.clientSecureTradeManager;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class GiveMoneyMessage extends SWGMessage {

	private int credits;

	@Override
	public void deserialize(IoBuffer data) {

		data.getShort();
		data.getInt();
		
		setTradingCredits(data.getInt());
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(40).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 2);
		result.putInt(0xD1527EE8);
		result.putInt(credits);
		return result.flip();
	}
	
	public int getTradingCredits() {
		return this.credits;
	}
	
	public void setTradingCredits(int credits) {
		this.credits = credits;
	}

}
