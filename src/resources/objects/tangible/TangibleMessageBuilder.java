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
package resources.objects.tangible;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.ObjectMessageBuilder;


public class TangibleMessageBuilder extends ObjectMessageBuilder {
	
	public TangibleMessageBuilder(TangibleObject tangible) {
		setObject(tangible);
	}
	
	public IoBuffer buildBaseline3() {
		TangibleObject tangible = (TangibleObject) object;
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		buffer.putShort((short) 0x0D);
		buffer.putFloat(object.getComplexity());
		buffer.put(getAsciiString(object.getStfFilename()));
		buffer.putInt(0);
		buffer.put(getAsciiString(object.getStfName()));
		
		if(object.getCustomName() == null || object.getCustomName().length() < 1)
			buffer.putInt(0);
		else
			buffer.put(getUnicodeString(object.getCustomName()));
		buffer.putInt(object.getVolume());
		buffer.putInt(0);
		buffer.putInt(0); // faction vars
		
		if(tangible.getCustomization() == null || tangible.getCustomization().length <= 0)
			buffer.putShort((short) 0);
		else {
			buffer.putShort((short) tangible.getCustomization().length);
			buffer.put(tangible.getCustomization());
		}
		
		buffer.putInt(0);
		buffer.putInt(0); 
		
		//buffer.putInt(0); 
		if(tangible.getOptionsBitmask() == 0)
			tangible.setOptionsBitmask(0x100);
		buffer.putInt(tangible.getOptionsBitmask());
		buffer.putInt(0); // number of item uses
		buffer.putInt(tangible.getConditionDamage());
		buffer.putInt(tangible.getMaxDamage());
		buffer.put((byte) (tangible.isStaticObject() ? 1 : 0));
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("TANO", (byte) 3, buffer, size);
		
		return buffer;
	}
	
	public IoBuffer buildBaseline6() {
		TangibleObject tangible = (TangibleObject) object;
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short) 8);
		buffer.putInt(0x43);
		buffer.put(getAsciiString(tangible.getDetailFilename()));
		buffer.putInt(0);
		buffer.put(getAsciiString(tangible.getDetailName()));
		buffer.putLong(0);
		buffer.putLong(0);
		buffer.putLong(0); // unks
		buffer.putLong(0);
		buffer.putInt(0);
		buffer.put((byte) 0);
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("TANO", (byte) 6, buffer, size);
		
		return buffer;
	}

	public IoBuffer buildBaseline8() {
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		int size = buffer.position();
		buffer = IoBuffer.allocate(size).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("TANO", (byte) 8, buffer, size);
		
		return buffer;
	}
	
	public IoBuffer buildBaseline9() {
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		int size = buffer.position();
		buffer = IoBuffer.allocate(size).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("TANO", (byte) 9, buffer, size);
		
		return buffer;
	}
	
	public IoBuffer buildConditionDamageDelta(int conditionDamage) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(conditionDamage);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("TANO", (byte) 3, (short) 1, (short) 0x0A, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildCustomNameDelta(String customName) {
		IoBuffer buffer = bufferPool.allocate(getUnicodeString(customName).length, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(getUnicodeString(customName));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("TANO", (byte) 3, (short) 1, (short) 2, buffer, size + 4);
		return buffer;
		
	}
	
	public IoBuffer buildCustomizationDelta(byte[] customization) {
		IoBuffer buffer = bufferPool.allocate(customization.length, false).order(ByteOrder.LITTLE_ENDIAN);

		buffer.put(customization);

		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("TANO", (byte) 3, (short) 1, (short) 6, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildDelta3() {
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(getObject().getObjectID());
		buffer.putInt(0x54414E4F);
		buffer.put((byte)3);
		buffer.putInt(8);//buffer.putInt(8+6);
		
		buffer.putShort((short) 2);
		buffer.putShort((short)8);
		buffer.put((byte)0);
		buffer.putShort((short) 0x21);
		buffer.put((byte)0);
		
//		buffer.putShort((short) 0);
//		buffer.putFloat(4.0F); 
		
		int size = buffer.position();
		buffer.flip();
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();		
	}

	public IoBuffer buildAssemblyDelta3() {
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(getObject().getObjectID());
		buffer.putInt(0x54414E4F);
		buffer.put((byte)3);
		buffer.putInt(8);//buffer.putInt(8+6);
		
		buffer.putShort((short) 1);
		buffer.putShort((short)0x0B);
		buffer.putInt(0x000003E8); // ?
		
		int size = buffer.position();
		buffer.flip();
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();		
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void sendBaselines() {
		// TODO Auto-generated method stub
		
	}
	
}
