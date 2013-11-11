package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;

public class BuffBuilderStartMessage extends ObjControllerObject {

	private long objectId;
	private long recieverId;
	
	public BuffBuilderStartMessage(long objectId, long recieverId) {
		this.objectId = objectId;
		this.recieverId = recieverId;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(37).order(ByteOrder.LITTLE_ENDIAN);
		
		//0B 00 00-00 # flag (11)
		result.putInt(ObjControllerMessage.BUFF_BUILDER_START);

		result.putLong(objectId);
		result.putInt(0); // tick count
		result.putLong(objectId);
		result.putLong(recieverId);
		
		
		//System.out.println("BBMSG: " + result.flip().getHexDump());
		return result.flip();
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public long getRecieverId() {
		return recieverId;
	}

	public void setRecieverId(long recieverId) {
		this.recieverId = recieverId;
	}

}
