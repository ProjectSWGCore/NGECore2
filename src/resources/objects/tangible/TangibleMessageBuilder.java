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
		buffer.putInt(0); // unknowns
		
		if(tangible.getCustomization() == null || tangible.getCustomization().length < 1)
			buffer.putShort((short) 0);
		else {
			buffer.putShort((short) tangible.getCustomization().length);
			buffer.put(tangible.getCustomization());
		}
		buffer.putInt(tangible.getOptionsBitmask());
		buffer.putInt(tangible.getIncapTimer());
		buffer.putInt(tangible.getConditionDamage());
		buffer.putInt(tangible.getMaxDamage());
		buffer.putInt(0);
		buffer.putInt(0x64);
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
		buffer.putInt(0x64);
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

	@Override
	public void sendBaselines() {
		// TODO Auto-generated method stub
		
	}


}
