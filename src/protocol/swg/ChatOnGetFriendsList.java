package protocol.swg;

import java.nio.ByteOrder;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.config.Config;
import engine.resources.config.DefaultConfig;
import resources.objects.player.PlayerObject;

public class ChatOnGetFriendsList extends SWGMessage {

	private List<String> friendList;
	private PlayerObject player;
	private long characterId;
	private int listSize;
	
	public ChatOnGetFriendsList(PlayerObject player) {
		this.characterId = player.getObjectID();
		this.friendList = player.getFriendList();
		this.listSize = friendList.size();
		this.player = player;
	}
	@Override
	public void deserialize(IoBuffer data) {
		// XXX Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		Config config = new Config();
		config.setFilePath("nge.cfg");
		if (!(config.loadConfigFile())) {
			config = DefaultConfig.getConfig();
		}
		
		IoBuffer buffer = IoBuffer.allocate(200).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short) 3);
		buffer.putInt(0xE97AB594);
		buffer.putLong(characterId);
		buffer.putInt(listSize); // friends list size
		
		
		for (String friend : player.getFriendList()) {
			
			buffer.put(getAsciiString("SWG"));
			buffer.put(getAsciiString(config.getString("GALAXY_NAME")));
			
			int friendIndex = friendList.indexOf(friend);
			
			buffer.put(getAsciiString(friendList.get(friendIndex)));
		}
		int size = buffer.position();
		buffer = IoBuffer.allocate(size).put(buffer.array(), 0, size);
		buffer.flip();
		//System.out.println("ChatOnGetFriendsList: " + buffer.getHexDump());
		return buffer;
	}

}
