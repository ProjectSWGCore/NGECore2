package protocol.swg;

import java.nio.ByteOrder;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.config.Config;
import engine.resources.config.DefaultConfig;

public class ChatOnChangeFriendStatus extends SWGMessage {

	private String name;
	private int type;
	private long playerID;

	// Adds or Removes a friend, not to be confused with online/offline status
	public ChatOnChangeFriendStatus(long playerID, String name, int type) {
		this.name = name;
		this.type = type;
		this.playerID = playerID;
	}
	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		Config config = new Config();
		config.setFilePath("nge.cfg");
		if (!(config.loadConfigFile())) {
			config = DefaultConfig.getConfig();
		}
		String server = config.getString("GALAXY_NAME");
		IoBuffer result = IoBuffer.allocate(100 + this.name.length() + server.length()).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short)0x06);
		result.putInt(0x54336726);
		result.putLong(playerID);
		
		result.put(getAsciiString("SWG"));
		result.put(getAsciiString(server));
		result.put(getAsciiString(name));
		result.putInt(0);
		result.putInt(type);
		result.put((byte) 0);
		result.flip();
		//System.out.println("ChatOnChangeFriendStatus: " + result.getHexDump());
		return result;
	}

}
