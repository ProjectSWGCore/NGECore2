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

public class CombatSpam extends ObjControllerObject{

	private long attackerId;
	private long defenderId;
	private int damage;
	private String file;
	private String text;
	private byte colorFlag;
	private long objectId;


	public CombatSpam(long attackerId, long defenderId, int damage, String file, String text, byte colorFlag, long objectId) {
		this.attackerId = attackerId;
		this.defenderId = defenderId;
		this.damage = damage;
		this.file = file;
		this.text = text;
		this.colorFlag = colorFlag;
		this.objectId = objectId;
	}
	
	public void deserialize(IoBuffer data) {
	
	}
	
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putInt(ObjControllerMessage.COMBAT_SPAM);
		
		result.putLong(objectId);
		result.putInt(0);
		//result.put((byte) 2);	//unk
		result.putLong(attackerId);
		result.putLong(defenderId);
		result.putLong(0);
		result.putInt(damage);
		result.putShort((short) file.length());
		result.put(getAsciiString(file));
		result.putInt(0);
		result.putShort((short) text.length());
		result.put(getAsciiString(text));
		result.put(colorFlag);
		
		return result;
	}
	
	public CombatSpam clone() {
		return new CombatSpam(attackerId, defenderId, damage, file, text, colorFlag, objectId);
	}
}
