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

import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;

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
		CreatureObject creature = (CreatureObject) player.getContainer();

		if (creature != null) {

			SWGObject desCloner = null;
			if (creature.getAttachment("preDesignatedCloner") != null)
				desCloner = NGECore.getInstance().objectService.getObject((long) creature.getAttachment("preDesignatedCloner"));

			String clonerPlanet = "";
			String spouse = "";

			if (desCloner != null)
				clonerPlanet = desCloner.getPlanet().getName();

			if (player.getSpouseName() != null)
				spouse = player.getSpouseName();

			IoBuffer buffer = IoBuffer.allocate(82 + clonerPlanet.length() + (spouse.length() * 2)).order(ByteOrder.LITTLE_ENDIAN);

			buffer.putShort((short) 12);
			buffer.putInt(CRC.StringtoCRC("CharacterSheetResponseMessage"));

			buffer.putInt(0);
			buffer.putInt(0);

			if (desCloner != null) {
				Point3D loc = desCloner.getPosition();
				buffer.putFloat(loc.x); // bind x
				buffer.putFloat(loc.y); // bind z
				buffer.putFloat(loc.z); // bind y
				buffer.put(getAsciiString(clonerPlanet)); // bind planet
			} else {
				buffer.putFloat(0); // bind x
				buffer.putFloat(0); // bind z
				buffer.putFloat(0); // bind y
				buffer.put(getAsciiString("")); // bind planet
			}

			buffer.putFloat(0); // bank x
			buffer.putFloat(0); // bank z
			buffer.putFloat(0); // bank y
			buffer.put(getAsciiString("tatooine"));

			buffer.putFloat(0); // home x
			buffer.putFloat(0); // home z
			buffer.putFloat(0); // home y
			buffer.put(getAsciiString("")); // home planet

			buffer.put(getAsciiString(""));  // Name of city player resides in

			buffer.put(getUnicodeString(spouse));

			buffer.putInt(creature.getPlayerObject().getLotsRemaining()); // lots remaining

			return buffer.flip();
		}
		return null;
	}

}
