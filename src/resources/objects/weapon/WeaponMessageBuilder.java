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
package resources.objects.weapon;

import java.nio.ByteOrder;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.Builder;
import resources.objects.tangible.TangibleMessageBuilder;
import resources.datatables.Elemental;

public class WeaponMessageBuilder extends TangibleMessageBuilder {
	
	public WeaponMessageBuilder(WeaponObject object) {
		super(object);
	}
	
	public WeaponMessageBuilder() {
		super();
	}
	
	public IoBuffer buildBaseline3() {
		WeaponObject weapon = (WeaponObject) object;
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short) 0x14);
		buffer.putFloat(0);
		buffer.put(getAsciiString(weapon.getStfFilename()));
		buffer.putInt(0);
		buffer.put(getAsciiString(weapon.getStfName()));
		if(weapon.getCustomName() == null || weapon.getCustomName().length() < 1)
			buffer.putInt(0);
		else
			buffer.put(getUnicodeString(weapon.getCustomName()));
		buffer.putInt(object.getVolume());
		buffer.putInt(0);
		buffer.putInt(0); // unknowns
		if(weapon.getCustomization() == null || weapon.getCustomization().length < 1)
			buffer.putShort((short) 0);
		else {
			buffer.putShort((short) weapon.getCustomization().length);
			buffer.put(weapon.getCustomization());
		}
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(weapon.getOptionsBitmask());
		buffer.putInt(0);
		buffer.putInt(weapon.getConditionDamage());
		buffer.putInt(0x64);
		buffer.put((byte) (weapon.isStaticObject() ? 1 : 0));
		buffer.putFloat(weapon.getAttackSpeed());
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putFloat(weapon.getMaxRange());
		buffer.putInt(0);
		if( weapon.getElementalType() != null) // Weapon particle effect
			buffer.putInt(Elemental.getElementalNum(weapon.getElementalType()));
		else
			buffer.putInt(Elemental.getElementalNum(weapon.getDamageType()));
		buffer.putInt(0);

		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("WEAO", (byte) 3, buffer, size);
		
		return buffer;
	}

	public IoBuffer buildBaseline6() {
		WeaponObject weapon = (WeaponObject) object;
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short) 9);
		buffer.putInt(0x43);
		buffer.put(getAsciiString(weapon.getDetailFilename()));
		buffer.putInt(0);
		buffer.put(getAsciiString(weapon.getDetailName()));
		buffer.put((byte) 0);
		buffer.putLong(0);
		buffer.putInt(0);
		buffer.putLong(0);
		buffer.putLong(0); // unks
		buffer.putLong(0);
		buffer.putInt(weapon.getWeaponType());

		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("WEAO", (byte) 6, buffer, size);
		
		return buffer;
	}

	public IoBuffer buildBaseline8() {
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		int size = buffer.position();
		buffer = IoBuffer.allocate(size).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("WEAO", (byte) 8, buffer, size);
		
		return buffer;
	}

	public IoBuffer buildBaseline9() {
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		int size = buffer.position();
		buffer = IoBuffer.allocate(size).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("WEAO", (byte) 9, buffer, size);
		
		return buffer;
	}
	
	@Override
	public void buildBaseline3(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline3(baselineBuilders, deltaBuilders);
	}
	
	@Override
	public void buildBaseline6(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline6(baselineBuilders, deltaBuilders);
		/*
		baselineBuilders.put(13, new Builder() {
			
			public byte[] build() {
				return Baseline.createBuffer(4).putInt(((WeaponObject) object).getWeaponType()).array();
			}
			
		});
		
		deltaBuilders.put(13, new Builder() {
			
			public byte[] build() {
				return Baseline.createBuffer(4).putInt(((WeaponObject) object).getWeaponType()).array();
			}
			
		});
		*/
	}
	
	@Override
	public void buildBaseline8(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline8(baselineBuilders, deltaBuilders);
	}
	
	@Override
	public void buildBaseline9(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline9(baselineBuilders, deltaBuilders);
	}
	
}
