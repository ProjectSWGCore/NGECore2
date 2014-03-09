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

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.player.PlayerObject;
import engine.resources.common.CRC;

public class CharacterSheetResponseMessage extends SWGMessage {

	private PlayerObject player;
	public CharacterSheetResponseMessage(PlayerObject player) {
		this.player = player;
	}
	
	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 12);
		buffer.putInt(CRC.StringtoCRC("CharacterSheetResponseMessage"));
		buffer.putShort((short) 0); //unk 
		buffer.putInt(1); // unk
		
		
		buffer.putFloat(0); // bind x
		buffer.putFloat(0); // bind z
		buffer.putFloat(0); // bind y
		buffer.putInt(0); // planet
		
		buffer.putFloat(0); // bank x
		buffer.putFloat(0); // bank z
		buffer.putFloat(0); // bank y
		buffer.put(getAsciiString("tatooine")); // planet
		
		buffer.putFloat(0); // home x
		buffer.putFloat(0); // home z
		buffer.putFloat(0); // home y
		buffer.putInt(0); // home planet
		
		if (player.getSpouseName() == null)
			buffer.putInt(0);
		else
			buffer.put(getUnicodeString(player.getSpouseName())); // spouse name <<<<< CORRECT
		
		buffer.putInt(10); // lots remaining <<<<<<< CORRECT
		return buffer.flip();
	}

}
