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

import engine.resources.common.CRC;

import protocol.swg.SWGMessage;

public class UpdatePVPStatusMessage extends SWGMessage {
	
	private long objectId;
	private int pvpStatus;
	private int faction;
	
	public UpdatePVPStatusMessage(long objectId) {
		this.objectId = objectId;
	}
	
	public UpdatePVPStatusMessage(long objectId, int pvpStatus, String faction) {
		this.objectId = objectId;
		this.pvpStatus = pvpStatus;
		this.faction = CRC.StringtoCRC(faction);
	}

	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(22).order(ByteOrder.LITTLE_ENDIAN);;
		
		result.putShort((short)4);
		result.putInt(0x08A1C126);
		result.putInt(pvpStatus);
		result.putInt(faction);
		result.putLong(objectId);
		result.flip();
		return result;
	}
	public enum factionCRC {;
		public static final int Neutral = 0;
		public static final int Imperial = 0xDB4ACC54;
		public static final int Rebel = 0x16148850;
	}
	public void setFaction(int factionCRC) {
		this.faction = factionCRC;
	}
	
	public void setStatus(int status) {
		this.pvpStatus = status;
	}
}
