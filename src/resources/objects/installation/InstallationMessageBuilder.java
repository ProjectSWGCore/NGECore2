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
package resources.objects.installation;

import java.nio.ByteOrder;
import org.apache.mina.core.buffer.IoBuffer;
import resources.objects.ObjectMessageBuilder;
import resources.objects.harvester.HarvesterObject;

/** 
 * @author Charon 
 */

public class InstallationMessageBuilder extends ObjectMessageBuilder{
	
	public InstallationMessageBuilder(InstallationObject installationObject) {
		setObject(installationObject);
	}
	
	public IoBuffer buildBaseline3(InstallationObject installationObject) {
		IoBuffer buffer = bufferPool.allocate(10, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		int packSize = 66; 
		packSize += installationObject.getStfName().length();
		packSize += installationObject.getStfFilename().length();
		
		buffer.putShort((short)5);
		buffer.putInt(0x68A75F0C);
		buffer.putLong(installationObject.getObjectID());
		buffer.putInt(0x494E534F);
		buffer.put((byte)3);
		buffer.putInt(packSize);
		buffer.putShort((short) 0x05);
		buffer.putFloat(installationObject.getComplexity()); 
		buffer.put(getAsciiString(installationObject.getStfFilename())); // installation_n
		buffer.putInt(0);
		buffer.put(getAsciiString(installationObject.getStfName()));
		buffer.put(getUnicodeString(""));//buffer.put(getUnicodeString(installationObject.getCustomName()));
		buffer.putInt(1);// int 1 // oper 3
		buffer.putShort((short)0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putLong(0); 

		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.put((byte)1);
		buffer.put((byte)0);
		buffer.putFloat(0.0F); 
		buffer.putFloat(0.0F);
		
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();		
	}

//	0000:   05 00 0C 5F A7 68 82 EC 7D 16 00 00 02 00 4F 53    ..._.h..}.....OS
//	0010:   4E 49 06 0E 00 00 00 05 00 76 00 00 00 00 00 00    NI.......v......
//	0020:   00 01 00 00 00                                     .....
	
	public IoBuffer buildBaseline6(InstallationObject installationObject) {
		IoBuffer buffer = bufferPool.allocate(10, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		int packSize = 14; 
		buffer.putShort((short)5);
		buffer.putInt(0x68A75F0C);
		buffer.putLong(installationObject.getObjectID());
		buffer.putInt(0x494E534F);
		buffer.put((byte)6);
		buffer.putInt(packSize);									
		buffer.putShort((short)5);
		buffer.putInt(0x76);
		buffer.putInt(0);
		buffer.putInt(1);
		
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();		
	}
	

	public IoBuffer buildBaseline7() {
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		int size = buffer.position();
		buffer = IoBuffer.allocate(size).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("INSO", (byte) 7, buffer, size);
		
		return buffer;
	}
	
	public IoBuffer buildBaseline8() {
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		int size = buffer.position();
		buffer = IoBuffer.allocate(size).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("INSO", (byte) 8, buffer, size);
		
		return buffer;
	}
	
	public IoBuffer buildBaseline9() {
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		int size = buffer.position();
		buffer = IoBuffer.allocate(size).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("INSO", (byte) 9, buffer, size);
		
		return buffer;
	}
	
	public IoBuffer buildDelta3(InstallationObject installationObject,IoBuffer packet,int packSize) {
		IoBuffer buffer = bufferPool.allocate(10, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(installationObject.getObjectID());
		buffer.putInt(0x494E534F);
		buffer.put((byte)3);
		buffer.putInt(packSize);
		buffer.put(packet);						
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();		
	}
	
	public IoBuffer buildDelta7(InstallationObject installationObject,IoBuffer packet,int packSize) {
		IoBuffer buffer = bufferPool.allocate(10, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(installationObject.getObjectID());
		buffer.putInt(0x494E534F);
		buffer.put((byte)7);
		buffer.putInt(packSize);
		buffer.put(packet);						
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();		
	}
	
	public IoBuffer constructINSO7Var1(InstallationObject installationObject,long selectedResource) {
		IoBuffer buffer = bufferPool.allocate(10, false).order(ByteOrder.LITTLE_ENDIAN);
		int hopperContentsSize = ((HarvesterObject)installationObject).getOutputHopperContent().size()+1;
		byte updateCounter = ((HarvesterObject)installationObject).getUpdateCount();
		
		buffer.setAutoExpand(true);
		buffer.putShort((short)2); // buffer.putShort((short)updateCounter);
		buffer.putShort((short)0xC);
		buffer.put((byte)1);
		buffer.putShort((short)0xD);
		
		buffer.putInt(1);//buffer.putInt(hopperContentsSize); // works once with 1! HopperContentsSize->Number of resources in hopper
		buffer.putInt(2); // UpdateCounter works once with 2!
		buffer.put((byte)1); // subtype : ADD
		buffer.putShort((short)0); // index was 0 buffer.putShort((short)(hopperContentsSize-1));
		buffer.putLong(selectedResource);
		buffer.putFloat(0);		
		int size = buffer.position();
		buffer.flip();
		return buildDelta7(installationObject,IoBuffer.allocate(size).put(buffer.array(), 0, size).flip(),size);		
	}
	
//	0000:   05 00 53 21 86 12 82 EC 7D 16 00 00 02 00 4F 53    ..S!....}.....OS
//	0010:   4E 49 07 1E 00 00 00 02 00 0C 00 01 0D 00 01 00    NI..............
//	0020:   00 00 02 00 00 00 01 00 00 8A D3 79 16 00 00 0F    ...........y....
//	0030:   00 00 00 00 00                                     .....
	
	public IoBuffer constructINSO7Var2(InstallationObject installationObject,long selectedResource) {
		IoBuffer buffer = bufferPool.allocate(10, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)2);
		buffer.putShort((short)0x9);
		buffer.putFloat(0);
		buffer.putShort((short)0x5);
		buffer.putLong(selectedResource);	
		int size = buffer.position();
		buffer.flip();
		return buildDelta7(installationObject,IoBuffer.allocate(size).put(buffer.array(), 0, size).flip(),size);		
	}
	
//	0000:   05 00 53 21 86 12 82 EC 7D 16 00 00 02 00 4F 53    ..S!....}.....OS
//	0010:   4E 49 07 12 00 00 00 02 00 09 00 00 00 00 00 05    NI..............
//	0020:   00 8A D3 79 16 00 00 0F 00                         ...y.....

	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendBaselines() {
		
	}
}
