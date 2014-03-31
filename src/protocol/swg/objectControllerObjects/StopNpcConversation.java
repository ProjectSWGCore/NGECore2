package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.ObjControllerOpcodes;

public class StopNpcConversation extends ObjControllerObject {
	
	private long npcId;
	private long objectId;
	private String stfFile;
	private String stfLabel;

	public StopNpcConversation(long objectId, long npcId, String stfFile, String stfLabel) {
		this.objectId = objectId;
		this.npcId = npcId;
		this.stfFile = stfFile;
		this.stfLabel = stfLabel;
	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(40 + stfFile.length() + stfLabel.length()).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(ObjControllerOpcodes.STOP_NPC_CONVERSATION);
		buffer.putLong(objectId);
		
		buffer.putInt(0);
		buffer.putLong(npcId);
		buffer.put(getAsciiString(stfFile));
		buffer.putInt(0);
		buffer.put(getAsciiString(stfLabel));	
		buffer.putLong(0);
		
		return buffer.flip();
	}
}
