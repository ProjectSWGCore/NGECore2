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

import services.travel.TravelPoint;
import engine.resources.objects.SWGObject;

@SuppressWarnings("unused")
public class EnterStructurePlacementModeMessage extends SWGMessage {

	private SWGObject deed;
	private String structureTemplate;
	
	public EnterStructurePlacementModeMessage(SWGObject deed, String structureTemplate) {
		this.deed = deed;
		this.structureTemplate = structureTemplate;
	}
	
	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(16 + structureTemplate.length()).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short) 3);
		result.putInt(0xE8A54DC1);
		
		result.putLong(deed.getObjectID());
		
		deed.setAttachment("structureTemplate", structureTemplate);
		result.put(getAsciiString(structureTemplate));
		
		return result.flip();
	}

}