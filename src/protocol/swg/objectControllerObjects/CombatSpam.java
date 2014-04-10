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
import services.combat.CombatService.HitType;

public class CombatSpam extends ObjControllerObject{

	private long attackerId;
	private long defenderId;
	private int damage;
	//private String file;
	//private String text;
	//private byte colorFlag;
	private boolean hit = true;
	private boolean critical = false;
	private boolean dodge = false;
	private boolean parry = false;
	private boolean glance = false;
	private long weaponId;
	private int armorAbsorbed;


	public CombatSpam(long attackerId, long defenderId, long weaponId, int damage, int armorAbsorbed, int hitType) {
		this.attackerId = attackerId;
		this.defenderId = defenderId;
		this.weaponId = weaponId;
		this.damage = damage;
		this.armorAbsorbed = armorAbsorbed;
		
		switch(hitType) {
		
			case HitType.CRITICAL: critical = true; break;
			case HitType.DODGE: dodge = true; hit = false; break;
			case HitType.GLANCE: glance = true; break;
			case HitType.PARRY: parry = true; hit = false; break;
			case HitType.MISS: hit = false; break;			
		
		}
		
	}
	
	public void deserialize(IoBuffer data) {
	
	}
	
	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putInt(ObjControllerMessage.COMBAT_SPAM);
		result.putLong(attackerId);
		result.putInt(0);
		result.put((byte) 0); //unk
		result.putLong(attackerId);
		result.putInt(0);
		result.putInt(0); 
		result.putInt(0);
		result.putLong(defenderId);
		result.putInt(0);
		result.putInt(0); 
		result.putInt(0);
		result.putLong(weaponId);
		result.putShort((short) 0); // unk
		result.putInt(0xFFFFFFFF); // color?
		result.putShort((short) 0); // unk
		result.put((byte) ((boolean) hit ? 1 : 0)); // 0 = miss
		result.put((byte) ((boolean) dodge ? 1 : 0)); // 1 = dodge
		result.put((byte) ((boolean) parry ? 1 : 0)); // 1 = parry
		result.put((byte) 0); // unk
		result.putInt(0); // unk
		result.put((byte) 0); //unk
		result.putInt(2000); // unk
		result.putInt(0); // type of elemental 1 = heat 2 = cold
		result.putInt(0); // elemental damage
		result.putInt(0x20); // unk
		result.putInt(0); // unk
		result.putInt(0); // unk
		//result.putInt(armorAbsorbed); // damage absorbed by armor
		result.putInt(damage);
		result.putInt(damage); // total damage done after armor without elemental
		result.putInt(0); // punishing blow for all values
		result.putInt(0); // unk
		result.putInt(0); // unk
		result.putInt(0); // unk
		result.putInt(0); // unk
		result.put((byte) ((boolean) critical ? 1 : 0)); //unk
		result.put((byte) ((boolean) glance ? 1 : 0)); //unk
		result.put((byte) 0); //unk
		result.putInt(1); // unk

		int packetSize = result.position();
		result = IoBuffer.allocate(packetSize).put(result.array(), 0, packetSize);
				
		return result.flip();
		
	}
	
}
