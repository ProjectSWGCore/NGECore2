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
package resources.objects.factorycrate;

import java.nio.ByteOrder;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.Builder;
import resources.objects.tangible.TangibleMessageBuilder;

public class FactoryCrateMessageBuilder extends TangibleMessageBuilder{
	
	public FactoryCrateMessageBuilder(FactoryCrateObject object) {
		super(object);
	}
	
	public FactoryCrateMessageBuilder() {
		super();
	}
	
	public IoBuffer buildBaseline3() {
		FactoryCrateObject factoryCrateObject = (FactoryCrateObject) object;
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);		

		buffer.putShort((short)0x0D);
		buffer.putFloat(1.0f);	//op0
		buffer.put(getAsciiString("factory_n")); 
		buffer.putInt(0);		
		buffer.put(getAsciiString(factoryCrateObject.getFactoryCrateType()));
		
		if (factoryCrateObject.getCustomName().length()>0)
			buffer.put(getUnicodeString(factoryCrateObject.getCustomName()));
		else
			buffer.putInt(0);
		
		buffer.putInt(1); 
		buffer.putShort((short)0); 
		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0x2100); // optionsbitmask
		buffer.putInt((int)factoryCrateObject.getQuantity());   // Quantity // op9
		buffer.putInt(0);
		buffer.putInt(0x64);  // condition maybe

		buffer.put((byte)1); 
	
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("TYCF", (byte) 3, buffer, size);
		
		return buffer;	
		
//		01 00 00 00 
//		00 00 
//		00 00 00 00 
//		00 00 00 00 
//		00 00 00 00 
//		00 00 00 00 
//		00 21 00 00 
//		2A 00 00 00 
//		00 00 00 00 
//		64 00 00 00 
//		01     
		
	}
	
	public IoBuffer buildBaseline6() {
		@SuppressWarnings("unused") FactoryCrateObject factoryCrateObject = (FactoryCrateObject) object;
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
				
		buffer.putShort((short)6);
		buffer.putInt(3);
				
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);

		buffer.put((byte)0);

		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("TYCF", (byte) 6, buffer, size);
		
		return buffer;		
	}
	

	public IoBuffer buildBaseline8() {
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);		
		buffer.putShort((short)0);		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("RCNO", (byte) 8, buffer, size);	
		return buffer;
	}
		
	public IoBuffer buildBaseline9() {
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);		
		buffer.putShort((short)0);
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("TYCF", (byte) 9, buffer, size);	
		return buffer;	
	}
	
	public IoBuffer buildDelta3() {
		FactoryCrateObject factoryCrateObject = (FactoryCrateObject) object;
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);		
		
		buffer.putInt(factoryCrateObject.getQuantity());
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("TYCF", (byte) 3, (short) 1, (short) 9, buffer, size + 4); 
		
		return buffer;	
	}
	
	@Override
	public void buildBaseline3(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline3(deltaBuilders, deltaBuilders);
	}
	
	@Override
	public void buildBaseline6(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline6(deltaBuilders, deltaBuilders);
	}
	
	@Override
	public void buildBaseline8(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline8(deltaBuilders, deltaBuilders);
	}
	
	@Override
	public void buildBaseline9(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline9(deltaBuilders, deltaBuilders);
	}
	
}
