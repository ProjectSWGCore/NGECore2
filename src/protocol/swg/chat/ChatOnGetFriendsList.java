package protocol.swg.chat;

import java.nio.ByteOrder;
import java.util.List;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;
import resources.objects.player.PlayerObject;

// This is possibly unused
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

	}

	@Override
	public IoBuffer serialize() {
		
		IoBuffer buffer = IoBuffer.allocate(200).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short) 3);
		buffer.putInt(0xE97AB594);
		buffer.putLong(characterId);
		buffer.putInt(listSize); // friends list size
		
		
		for (String friend : player.getFriendList()) {
			
			buffer.put(getAsciiString("SWG"));
			buffer.put(getAsciiString(NGECore.getInstance().getConfig().getString("GALAXY_NAME")));
			
			int friendIndex = friendList.indexOf(friend);
			
			buffer.put(getAsciiString(friendList.get(friendIndex)));
		}
		int size = buffer.position();
		buffer = IoBuffer.allocate(size).put(buffer.array(), 0, size);
		buffer.flip();
		return buffer;
	}

}
