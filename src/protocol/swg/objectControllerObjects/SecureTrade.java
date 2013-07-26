package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;
import protocol.swg.ObjControllerMessage;
import resources.objects.creature.CreatureObject;

public class SecureTrade extends ObjControllerObject{

	private long senderID;
	private long recieverID;
	private short unknown;
	
	public SecureTrade() {
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		
		//setUnknown(data.getShort());
		System.out.println("SecureTrade deserialize Dump: " + data.getHexDump());
		setSenderID(data.getLong()); // FINE AT SPOT GOT RIGHT SENDER ID. DUMPING DUMPS IT HERE.
		data.getLong(); // skip through 0's
		data.getLong(); // skip through 0's
		setRecieverID(data.getLong());
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putInt(ObjControllerMessage.SPACIAL_CHAT);
		result.putInt(1);
		result.putLong(senderID);
		result.putLong(0);
		result.putLong(0);
		result.putLong(recieverID);

		return result.flip();
	}
	
	public long getSenderID() { return this.senderID; }
	public long getRecieverID() { return this.recieverID; }
	public short getUnkn() { return this.unknown; }
	
	public void setUnknown(short unknown) {
		this.unknown = unknown;
	}
	public void setSenderID(long senderID) {
		this.senderID = senderID;
	}
	
	public void setRecieverID(long recieverID) {
		this.recieverID = recieverID;
	}
	
	
}
