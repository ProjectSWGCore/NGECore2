package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;
import java.util.Vector;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class CommoditiesResourceTypeListResponse extends SWGMessage {

	private Vector<String> resNames;

	public CommoditiesResourceTypeListResponse(Vector<String> resNames) {
		this.resNames = resNames;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		String galaxyName = NGECore.getInstance().getGalaxyName();
		final IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 2);
		result.putInt(0x5EDD19CB);
		
		result.put(getAsciiString(galaxyName + "." + String.valueOf(resNames.size())));
		result.putInt(479);
		result.putLong(0); 
		
		resNames.forEach(s -> result.put(getAsciiString(s)));
		
		int size = result.position();
		IoBuffer result2 = IoBuffer.allocate(size).put(result.array(), 0, size);

		return result2.flip();
	}

}
