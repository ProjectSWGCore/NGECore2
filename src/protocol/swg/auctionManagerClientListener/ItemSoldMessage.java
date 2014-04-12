package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class ItemSoldMessage extends SWGMessage {

	public static final int SUCCESS = 0;
	public static final int INVALIDAUCTIONER = 1;
	public static final int INVALIDITEM = 2;
	public static final int BADVENDOR = 3;
	public static final int INVALIDPRICE = 4;
	public static final int INVALIDDURATION = 5;
	public static final int ALREADYFORSALE = 6;
	public static final int UNKERROR = 6;
	public static final int DONTOWNITEM = 8;
	public static final int NOTENOUGHCREDITS = 9;
	public static final int TOOMANYITEMS = 13;
	public static final int PRICETOOHIGH = 14;
	public static final int CANTSELLTRADINGITEM = 19;
	public static final int VENDORFULL = 25;

	private long objectId;
	private int status;
	
	public ItemSoldMessage(long objectId, int status) {
		this.objectId = objectId;
		this.status = status;
	}


	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(18).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 3);
		result.putInt(0x0E61CC92);
		result.putLong(objectId);
		result.putInt(status);
		return result.flip();
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

}
