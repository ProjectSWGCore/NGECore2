package protocol.swg.clientSecureTradeManager;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;
import protocol.swg.SWGMessage;

public class RemoveItemMessage extends SWGMessage {

	private long objectID;
	
	@Override
	public void deserialize(IoBuffer data) {
		data.getShort();
		data.getInt();
		
		setObjectID(data.getLong());
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 1);
		result.putInt(0x4417AF8B);
		result.putLong(objectID);
		return result.flip();
	}
	
	public void setObjectID(long objectID) {
		this.objectID = objectID;
	}
	
	public long getObjectID() {
		return this.objectID;
	}

}
