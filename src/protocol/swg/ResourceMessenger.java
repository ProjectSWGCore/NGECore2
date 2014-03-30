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
import engine.resources.objects.SWGObject;
import protocol.swg.SWGMessage;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceContainerObject;

/** 
 * @author Charon 
 */
public class ResourceMessenger extends SWGMessage {
	
	private IoBuffer buffer;
	
	public ResourceMessenger(IoBuffer buffer) {
		this.buffer = buffer;
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		return IoBuffer.allocate(1); //satisfy Interface
	}
	
	public IoBuffer serialize_buildUpdateContainmentMessage(ResourceContainerObject contentObject, SWGObject containerObject) {
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		result.putShort((short)4);
		result.putInt(0x56CBDE9E);
		result.putLong(contentObject.getObjectID());
		if (containerObject != null) {
			result.putLong(containerObject.getObjectID());
		} else {
			System.out.println("buildUpdateContainmentMessage -> containerObject is Null");
			result.putLong(0);
		}
		result.putInt(-1); // unequipped
		int size = result.position();
		result.flip();
		services.resources.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();		
	}
	
	
	public IoBuffer serialize_buildRCNO3Baseline(ResourceContainerObject resourceContainer) {
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);		
		int sizeP = 80;	
		sizeP += "resource/resource_names".length(); // "@resource/resource_names:" + R.getResourceType()));
		sizeP += 1*(resourceContainer.getResourceFileName().length());	
		result.putShort((short)5);
		result.putInt(0x68A75F0C);
		result.putLong(resourceContainer.getObjectID());
		result.putInt(0x52434E4F);
		result.put((byte)3);
		result.putInt(sizeP);
		result.putShort((short)0x0F);
		result.putFloat(1.0f);	
		result.put(getAsciiString("resource/resource_names"));
		result.putInt(0);		
		result.put(getAsciiString(resourceContainer.getResourceFileName()));
		result.putInt(0); 
		result.putInt(1); 
		result.putInt(0); 
		result.putInt(0); 
		result.putInt(0); 
		result.putInt(0); 
		result.putInt(0); 
		result.putShort((short)0);
		result.putInt(0);
		result.putInt(0);
		result.putInt(100);  //MaxCondition
		result.put((byte)1);
		result.putInt(resourceContainer.getStackCount());
		//result.putInt(30000);
		result.putLong(resourceContainer.getReferenceID());
		int size = result.position();
		result.flip();
		services.resources.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();		
	}

// Pre-CU Packet	
//	0000:   05 00 53 21 86 12 3C 65 41 16 00 00 01 00 4F 4E    ..S!..<eA.....ON
//	0010:   43 52 03 08 00 00 00 01 00 0B 00 06 00 00 00       CR.............
	
// Post NGE
//	0000:   05 00 53 21 86 12 68 8C 7D 3C 33 00 00 00 4F 4E    ..S!..h.}<3...ON
//	0010:   43 52 03 08 00 00 00 01 00 0D 00 20 42 0F 00       CR..........B..
	
	public IoBuffer serialize_buildRCNO3Delta(long containerID, int stackCount) {
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);		
		result.putShort((short)5);
		result.putInt(0x12862153);
		result.putLong(containerID);
		result.putInt(0x52434E4F);
		result.put((byte)3);
		result.putInt(8);
		result.putShort((short)0x01);
		result.putShort((short)0x0D); // differs from pre-NGE specification !
		result.putInt(stackCount);
		int size = result.position();
		result.flip();
		services.resources.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();		
	}
	

	public IoBuffer serialize_buildRCNO6Baseline(ResourceContainerObject resourceContainer) {
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		int sizeP = 95; // 75 + 20 for "resource_container_d"
		//sizeP += "inorganic_minerals_small".length();
		sizeP += GalacticResource.CONTAINER_TYPES[resourceContainer.getGeneralType()].length();
		String contName = resourceContainer.getResourceName();
		if (contName!=null){
			sizeP += 2*(contName.length());
		}
		String iffFileName = resourceContainer.getResourceFileName(); 
		if (iffFileName!=null){
			sizeP += iffFileName.length();
		}
		result.putShort((short)5);
		result.putInt(0x68A75F0C);
		result.putLong(resourceContainer.getObjectID());
		result.putInt(0x52434E4F);
		result.put((byte)6);
		result.putInt(sizeP);
		result.putShort((short)12);  //0C
		result.putInt(0x8A);
		result.put(getAsciiString("resource_container_d"));
		result.putInt(0);		
		// result.put(getAsciiString("inorganic_minerals_small"));	
		result.put(getAsciiString(GalacticResource.CONTAINER_TYPES[resourceContainer.getGeneralType()]));
		
		//37 x 00
		result.putLong(0);
		result.putLong(0);
		result.putLong(0);
		result.putLong(0);		
		result.putInt(0);
		result.put((byte)0);

		result.putInt(1000000);
		result.put(getAsciiString(iffFileName)); 
		result.put(getUnicodeString(contName)); 
		result.putLong(0);	
		int size = result.position();
		result.flip();
		services.resources.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();		
	}
	
	
	public IoBuffer serialize_buildRCNO8Baseline(ResourceContainerObject resourceContainer) {
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);		
		result.putShort((short)5);
		result.putInt(0x68A75F0C);
		result.putLong(resourceContainer.getObjectID());
		result.putInt(0x52434E4F);
		result.put((byte)8);	
		result.putInt(2);
		result.putShort((short)0);
		int size = result.position();
		result.flip();
		services.resources.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();		
	}
	
	public IoBuffer serialize_buildRCNO9Baseline(ResourceContainerObject resourceContainer) {
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);		
		result.putShort((short)5);
		result.putInt(0x68A75F0C);
		result.putLong(resourceContainer.getObjectID());
		result.putInt(0x52434E4F);
		result.put((byte)9);	
		result.putInt(2);
		result.putShort((short)0);
		int size = result.position();
		result.flip();
		services.resources.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();		
	}
	
	public IoBuffer serialize_buildAttributeListMessage(ResourceContainerObject resourceContainer) {
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		result.putShort((short)5);
		result.putInt(0xF3F12F2A);  
		result.putLong(resourceContainer.getObjectID());
		result.putShort((short)0);
		result.putInt(16);
		
		result.put(getAsciiString("condition"));
		result.put(getUnicodeString("100/100"));	
		
		result.put(getAsciiString("volume"));
		result.put(getUnicodeString("1"));
		
		result.put(getAsciiString("resource_contents"));
		result.put(getUnicodeString(resourceContainer.getStackCount()+"/"+100000));
		System.out.println("XXXXXXXXX " + resourceContainer.getStackCount()+"/"+100000);
		result.put(getAsciiString("resource_name"));
		result.put(getUnicodeString(resourceContainer.getResourceName()));
		
		result.put(getAsciiString("resource_class"));
		result.put(getUnicodeString("@resource/resource_names:" + resourceContainer.getResourceFileName()));
		//result.put(getUnicodeString(resourceContainer.getResourceClass()));
		
		result.put(getAsciiString("res_cold_resist"));
		result.put(getUnicodeString(""+resourceContainer.getColdResistance()));
		
		result.put(getAsciiString("res_conductivity"));
		result.put(getUnicodeString(""+resourceContainer.getConductivity()));
		
		result.put(getAsciiString("res_decay_resist"));
		result.put(getUnicodeString(""+resourceContainer.getDecayResistance()));
		
		result.put(getAsciiString("res_heat_resist"));
		result.put(getUnicodeString(""+resourceContainer.getHeatResistance()));
		
		result.put(getAsciiString("res_malleability"));
		result.put(getUnicodeString(""+resourceContainer.getMalleability()));
		
		result.put(getAsciiString("res_quality"));
		result.put(getUnicodeString(""+resourceContainer.getOverallQuality()));
		
		result.put(getAsciiString("res_shock_resistance"));
		result.put(getUnicodeString(""+resourceContainer.getShockResistance()));
		
		result.put(getAsciiString("res_toughness"));
		result.put(getUnicodeString(""+resourceContainer.getUnitToughness()));
		
		result.put(getAsciiString("res_flavor"));
		result.put(getUnicodeString(""+resourceContainer.getFlavor()));
		
		result.put(getAsciiString("entangle_resistance"));
		result.put(getUnicodeString(""+resourceContainer.getEntangleResistance()));
		
		result.put(getAsciiString("res_potential_energy"));
		result.put(getUnicodeString(""+resourceContainer.getPotentialEnergy()));
			
		result.putInt(10);  
		int size = result.position();
		result.flip();
		services.resources.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();		
	}
}