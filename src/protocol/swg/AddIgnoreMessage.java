/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package protocol.swg;

import java.nio.ByteOrder;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.config.Config;
import engine.resources.objects.SWGObject;

public class AddIgnoreMessage extends SWGMessage {

	private SWGObject player;
	private boolean type;
	private String ignoreName;
	
	public AddIgnoreMessage(SWGObject obj, String name, boolean type) {
		this.player = obj;
		this.ignoreName = name;
		this.type = type;
	}
	
	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		Config config = NGECore.getInstance().getConfig();
		String server = config.getProperty("GALAXY_NAME");
		
		IoBuffer result = IoBuffer.allocate(34 + server.length() + ignoreName.length()).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short) 6);
		result.putInt(0x70E9DA0F);
		result.putLong(player.getObjectId());
		
		result.put(getAsciiString("SWG"));
		result.put(getAsciiString(server));
		result.put(getAsciiString(ignoreName));
		
		result.putInt(0);
		result.put((byte) ((type) ? 1 : 0));  // add = T, remove = F
		result.putInt(0);
		
		return result.flip();
	}

}
