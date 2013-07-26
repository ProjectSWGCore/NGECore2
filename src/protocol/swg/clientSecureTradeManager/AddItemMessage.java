package protocol.swg.clientSecureTradeManager;

import java.nio.ByteOrder;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;
import protocol.swg.SWGMessage;

public class AddItemMessage extends SWGMessage {

	private NGECore core;
	private SWGObject tradeObject;
	private long tradeObjectID;
	
	@Override
	public void deserialize(IoBuffer data) {
		data.getShort();
		data.getInt();
		setTradeObjectID(data.getLong());
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(200).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 2);
		result.putInt(0x1E8D1356);
		result.putLong(tradeObjectID);
		return result.flip();
	}
	
	public long getTradeObjectID() {
		return this.tradeObjectID;
		
	}
	
	public SWGObject getTradeObject() {
		return this.tradeObject;
	}
	
	public void setTradeObject(SWGObject object) {
		this.tradeObject = object;
		this.tradeObjectID = object.getObjectID();
	}
	
	public void setTradeObjectID(long objectID) {
		this.tradeObjectID = objectID;
	}

}
