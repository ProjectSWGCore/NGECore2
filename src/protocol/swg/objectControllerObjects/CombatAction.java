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
package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;

public class CombatAction extends ObjControllerObject{

	private int actionCRC;
	private long attackerId;
	private long weaponId;
	private long defenderId;
	
	public CombatAction(int actionCRC, long attackerId, long weaponId, long defenderId) {
		this.actionCRC = actionCRC;
		this.attackerId = attackerId;
		this.weaponId = weaponId;
		this.defenderId = defenderId;
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putInt(ObjControllerMessage.COMBAT_ACTION);
		
		result.putLong(attackerId);
		result.putInt(0);
		result.putInt(actionCRC);
		result.putLong(attackerId);
		result.putLong(weaponId);
		result.put((byte) 0);
		result.put((byte) 1);
		result.put((byte) 0);
		byte[] unkdata = new byte[] {
				0x2B, (byte) 0x87, 0x64, (byte) 0xA1, 0x01, 0x15, 0x02, (byte) 0x97, (byte) 0xC5, 0x00, 0x00, (byte) 0xD0, 0x40, 0x6F, 0x16, (byte) 0x80, 0x45, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
		};
		result.put(unkdata);
		
		return result;
	}
	
	public CombatAction clone() {
		return new CombatAction(actionCRC, attackerId, weaponId, defenderId);
	}
}
