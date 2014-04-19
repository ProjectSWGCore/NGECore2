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
package resources.objects.resource;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.ObjectMessageBuilder;

public class ResourceContainerMessageBuilder extends ObjectMessageBuilder {

	public ResourceContainerMessageBuilder(ResourceContainerObject resourceContainerObject) {
		setObject(resourceContainerObject);
	}
	
	
	public IoBuffer buildBaseline3() {
		ResourceContainerObject resourceContainer = (ResourceContainerObject) object;
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);		

		buffer.putShort((short)0x0F);
		buffer.putFloat(1.0f);	
		buffer.put(getAsciiString("resource/resource_names"));
		buffer.putInt(0);		
		buffer.put(getAsciiString(resourceContainer.getResourceFileName()));
		buffer.putInt(0); 
		buffer.putInt(1); 
		buffer.putInt(0); 
		buffer.putInt(0); 
		buffer.putInt(0); 
		buffer.putInt(0); 
		buffer.putInt(0); 
		buffer.putShort((short)0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(100);  //MaxCondition
		buffer.put((byte)1);
		buffer.putInt(resourceContainer.getStackCount());
		//result.putInt(30000);
		buffer.putLong(resourceContainer.getReferenceID());
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("RCNO", (byte) 3, buffer, size);
		
		return buffer;	
	}
	
	
	public IoBuffer buildBaseline6() {
		ResourceContainerObject resourceContainer = (ResourceContainerObject) object;
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		String iffFileName = resourceContainer.getResourceFileName(); 
		String contName    = resourceContainer.getResourceName();
		
		buffer.putShort((short)12);  //0C
		buffer.putInt(0x8A);
		buffer.put(getAsciiString("resource_container_d"));
		buffer.putInt(0);		
		// buffer.put(getAsciiString("inorganic_minerals_small"));	
		buffer.put(getAsciiString(GalacticResource.CONTAINER_TYPES[resourceContainer.getGeneralType()]));
		
		//37 x 00
		buffer.putLong(0);
		buffer.putLong(0);
		buffer.putLong(0);
		buffer.putLong(0);		
		buffer.putInt(0);
		buffer.put((byte)0);

		buffer.putInt(ResourceContainerObject.maximalStackCapacity);
		buffer.put(getAsciiString(iffFileName)); 
		buffer.put(getUnicodeString(contName)); 
		buffer.putLong(0);	
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("RCNO", (byte) 6, buffer, size);
		
		return buffer;		
	}
	
	
	public IoBuffer buildBaseline8() {
		ResourceContainerObject resourceContainer = (ResourceContainerObject) object;
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);		
	
		buffer.putInt(2);
		buffer.putShort((short)0);
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("RCNO", (byte) 8, buffer, size);	
		return buffer;
	}
	
	
	public IoBuffer buildBaseline9() {
		ResourceContainerObject resourceContainer = (ResourceContainerObject) object;
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);		
		buffer.putShort((short)5);
		buffer.putInt(0x68A75F0C);
		buffer.putLong(resourceContainer.getObjectID());
		buffer.putInt(0x52434E4F);
		buffer.put((byte)9);	
		buffer.putInt(2);
		buffer.putShort((short)0);
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("RCNO", (byte) 9, buffer, size);	
		return buffer;	
	}
	
	public IoBuffer buildDelta3() {
		ResourceContainerObject resourceContainer = (ResourceContainerObject) object;
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);		
		
		buffer.putInt(resourceContainer.getStackCount());
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("RCNO", (byte) 3, (short) 1, (short) 0x0D, buffer, size + 4); // 0x0D differs from pre-NGE specification !
		
		return buffer;	
	}
	
	// This is a specific AttributeListMessage for the resource container
	// and represents no baseline or delta.
	public IoBuffer serialized_buildAttributeListMessage() {
		ResourceContainerObject resourceContainer = (ResourceContainerObject) object;
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		result.putShort((short)5);
		result.putInt(0xF3F12F2A);  // !
		result.putLong(resourceContainer.getObjectID());
		result.putShort((short)0);
		
		int countedSize = 0;
		if (resourceContainer.getColdResistance()>0)
			countedSize++;	
		if (resourceContainer.getConductivity()>0)
			countedSize++;		
		if (resourceContainer.getDecayResistance()>0)
			countedSize++;		
		if (resourceContainer.getHeatResistance()>0)
			countedSize++;		
		if (resourceContainer.getMalleability()>0)
			countedSize++;		
		if (resourceContainer.getOverallQuality()>0)
			countedSize++;	
		if (resourceContainer.getShockResistance()>0)
			countedSize++;	
		if (resourceContainer.getUnitToughness()>0)
			countedSize++;	
		if (resourceContainer.getFlavor()>0)
			countedSize++;	
		if (resourceContainer.getEntangleResistance()>0)
			countedSize++;	
		if (resourceContainer.getPotentialEnergy()>0)
			countedSize++;	
		
		int attributeSize = 5 + countedSize;
		
		result.putInt(attributeSize);//result.putInt(16);
		
		result.put(getAsciiString("condition"));
		result.put(getUnicodeString("100/100"));	
		
		result.put(getAsciiString("volume"));
		result.put(getUnicodeString("1"));
		
		result.put(getAsciiString("resource_contents"));
		result.put(getUnicodeString(resourceContainer.getStackCount()+"/"+ResourceContainerObject.maximalStackCapacity));

		result.put(getAsciiString("resource_name"));
		result.put(getUnicodeString(resourceContainer.getResourceName()));
		
		result.put(getAsciiString("resource_class"));
		result.put(getUnicodeString("@resource/resource_names:" + resourceContainer.getResourceFileName()));
		//result.put(getUnicodeString(resourceContainer.getResourceClass()));
		
		if (resourceContainer.getColdResistance()>0){
			result.put(getAsciiString("res_cold_resist"));
			result.put(getUnicodeString(""+resourceContainer.getColdResistance()));
		}
		
		if (resourceContainer.getConductivity()>0){
			result.put(getAsciiString("res_conductivity"));
			result.put(getUnicodeString(""+resourceContainer.getConductivity()));
		}
		
		if (resourceContainer.getDecayResistance()>0){
			result.put(getAsciiString("res_decay_resist"));
			result.put(getUnicodeString(""+resourceContainer.getDecayResistance()));
		}
		
		if (resourceContainer.getHeatResistance()>0){
			result.put(getAsciiString("res_heat_resist"));
			result.put(getUnicodeString(""+resourceContainer.getHeatResistance()));
		}		
		
		if (resourceContainer.getMalleability()>0){
			result.put(getAsciiString("res_malleability"));
			result.put(getUnicodeString(""+resourceContainer.getMalleability()));
		}
		
		if (resourceContainer.getOverallQuality()>0){
			result.put(getAsciiString("res_quality"));
			result.put(getUnicodeString(""+resourceContainer.getOverallQuality()));		
		}
		
		if (resourceContainer.getShockResistance()>0){
			result.put(getAsciiString("res_shock_resistance"));
			result.put(getUnicodeString(""+resourceContainer.getShockResistance()));		
		}
		
		if (resourceContainer.getUnitToughness()>0){
			result.put(getAsciiString("res_toughness"));
			result.put(getUnicodeString(""+resourceContainer.getUnitToughness()));	
		}
		
		if (resourceContainer.getFlavor()>0){
			result.put(getAsciiString("res_flavor"));
			result.put(getUnicodeString(""+resourceContainer.getFlavor()));		
		}
		
		if (resourceContainer.getEntangleResistance()>0){
			result.put(getAsciiString("entangle_resistance"));
			result.put(getUnicodeString(""+resourceContainer.getEntangleResistance()));		
		}
		
		if (resourceContainer.getPotentialEnergy()>0){
			result.put(getAsciiString("res_potential_energy"));
			result.put(getUnicodeString(""+resourceContainer.getPotentialEnergy()));		
		}
		
		result.putInt(10);  
		int size = result.position();
		result.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();		
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
