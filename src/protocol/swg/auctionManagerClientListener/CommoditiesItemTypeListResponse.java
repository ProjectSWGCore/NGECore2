package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class CommoditiesItemTypeListResponse extends SWGMessage {

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		String galaxy = NGECore.getInstance().getGalaxyName();
		IoBuffer result = IoBuffer.allocate(14 + galaxy.length()).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 2);
		result.putInt(0xD4E937FC);
		result.put(getAsciiString(galaxy + ".0"));
		result.putInt(0);
		return result.flip();
	}

}
