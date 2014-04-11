package protocol.swg;

import org.apache.mina.core.buffer.IoBuffer;

public class GuildRequestMessage extends SWGMessage {

	private long characterId;
	
	public GuildRequestMessage() {
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		//data.getShort();
		setCharacterId(data.getLong());
	}

	@Override
	public IoBuffer serialize() {
		return null;
	}

	public long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(long characterId) {
		this.characterId = characterId;
	}

}
