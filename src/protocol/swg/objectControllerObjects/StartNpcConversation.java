package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;
import org.apache.mina.core.buffer.IoBuffer;
import resources.common.ObjControllerOpcodes;

public class StartNpcConversation extends ObjControllerObject {
	
	private long npcId;
	private long objectId;

	public StartNpcConversation(long objectId, long npcId) {
		this.objectId = objectId;
		this.npcId = npcId;
	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(31).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(ObjControllerOpcodes.START_NPC_CONVERSATION);
		buffer.putLong(objectId);
		
		buffer.putInt(0);
		buffer.putLong(npcId);
		buffer.putInt(0);
		buffer.putShort((short) 0);
		buffer.put((byte) 0);
		
		return buffer.flip();
	}

}
