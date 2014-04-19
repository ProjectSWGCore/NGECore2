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
package resources.objects.harvester;

import java.nio.ByteOrder;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;

import resources.objects.ObjectMessageBuilder;
import resources.objects.creature.CreatureObject;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceContainerObject;

/** 
 * @author Charon 
 */
public class HarvesterMessageBuilder extends ObjectMessageBuilder{
	
	public HarvesterMessageBuilder(HarvesterObject harvesterObject) {
		setObject(harvesterObject);
	}
	
	public IoBuffer buildBaseline3() {

		HarvesterObject building = (HarvesterObject) object;
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		buffer.putShort((short) 0x0D);
		buffer.putFloat(building.getComplexity());
		buffer.put(getAsciiString(building.getStfFilename()));
		buffer.putInt(0);
		buffer.put(getAsciiString(building.getStfName()));
		buffer.putInt(0);
		buffer.putInt(0xFF);
		//buffer.putInt(0x64);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putShort((short) 0);
		
		buffer.putInt(0);
		buffer.putInt(0);
	//	buffer.putInt(16777216);
		buffer.putInt(0x100);
		buffer.putInt(0);
		buffer.putInt(64);
		buffer.putInt(0x201C);
		buffer.put((byte) 1);
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("BUIO", (byte) 3, buffer, size);

		return buffer;
		
	}
	
	public IoBuffer buildHINO3Delta(HarvesterObject harvester,byte state) {
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)3);		
		buffer.putInt(0xB);
		buffer.putShort((short)2);
		buffer.putShort((short)8);
		buffer.put((byte)1);
		buffer.putShort((short)1);
		buffer.put((byte)0);
		buffer.putShort((short)0xD);
		buffer.put(state);
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
	}
//	0000:   05 00 53 21 86 12 33 01 0E BE 41 00 00 00 4F 4E    ..S!..3...A...ON
//	0010:   49 48 03 0B 00 00 00 02 00 08 00 01 01 00 00 0D    IH..............
//	0020:   00 01
	
	
	public IoBuffer buildHINO3Delta2(HarvesterObject harvester,byte state) {
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)3);		
		buffer.putInt(0xB);
		buffer.putShort((short)2);
		buffer.putShort((short)8);
		buffer.put((byte)0);
		buffer.putShort((short)1);
		buffer.put((byte)0);
		buffer.putShort((short)0xD);
		buffer.put(state);
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
	}
	
	
	public IoBuffer buildBaseline6() {

		HarvesterObject building = (HarvesterObject) object;
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		buffer.putShort((short) 8);
		buffer.putInt(0x43);
		
		buffer.put(getAsciiString(building.getDetailFilename()));
		buffer.putInt(0);
		buffer.put(getAsciiString(building.getDetailName()));
		buffer.putInt(0);
		buffer.put((byte) 0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("BUIO", (byte) 6, buffer, size);

		return buffer;

	}
	
	
	public IoBuffer buildHINO7Delta(HarvesterObject harvester,byte state) {
		
		Vector<ResourceContainerObject> outputHopperContent = harvester.getOutputHopperContent();
		int hopperContentSize = outputHopperContent.size();
		int iHopperList = 1;
		if (hopperContentSize==0) {
			iHopperList = 0;
		}
		//int sizeP =  30 + 15*hopperContentSize; 
		int sizeP =  30-2-4-4;
		//System.out.println("sizeP " + sizeP);
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);		
		buffer.putInt(sizeP);
		// dOut.writeInt(0x2D);
		buffer.putShort((short)5);
		buffer.putShort((short)6);
		buffer.put(state);
		buffer.putShort((short)9);
		buffer.putFloat(harvester.getActualExtractionRate());		
		buffer.putShort((short)0x0C);
		buffer.put(harvester.getUpdateCount());
//		buffer.putShort((short)0x0D);
//		buffer.putInt(iHopperList);
//		buffer.putInt(harvester.getResourceUpdateCount());
		int sumOfHopper = 0;		
		int i = 0;
        sumOfHopper = 0; 
        Vector<ResourceContainerObject> outputHopper = harvester.getOutputHopperContent();
        for (ResourceContainerObject cont : outputHopper){
        	sumOfHopper += cont.getStackCount();
            i++;
        }
						
		buffer.putShort((short)0x0A);
		buffer.putInt(sumOfHopper);
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
	}
		
	public IoBuffer buildHINO7Delta2(HarvesterObject harvester,byte state) {
		
		Vector<ResourceContainerObject> outputHopperContent = harvester.getOutputHopperContent();
		int hopperContentSize = outputHopperContent.size();
		int iHopperList = 1;
		if (hopperContentSize==0) {
			iHopperList = 0;
		}
		int sizeP =  30 + 15*hopperContentSize; 
		//System.out.println("sizeP " + sizeP);
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);		
		buffer.putInt(sizeP);
		// dOut.writeInt(0x2D);
		buffer.putShort((short)5);
		buffer.putShort((short)6);
		buffer.put(state);
		buffer.putShort((short)9);
		buffer.putFloat(harvester.getActualExtractionRate());		
		buffer.putShort((short)0x0C);
		buffer.put(harvester.getUpdateCount());
		buffer.putShort((short)0x0D);
		buffer.putInt(iHopperList);
		buffer.putInt(harvester.getResourceUpdateCount());
		int sumOfHopper = 0;

		byte i = 0;
		for (ResourceContainerObject container : outputHopperContent){
			buffer.putShort((short)harvester.getResourceUpdateCount());
			buffer.put(i);
			buffer.putLong(container.getReferenceID());
			buffer.putInt(container.getStackCount());
			sumOfHopper += container.getStackCount();
			i++;
		}

		buffer.putShort((short)0x0A);
		buffer.putInt(sumOfHopper);
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
	}

	// 2 + 2 + 1 + 2 + 4 + 2 + 1 + 2 + 4 + 4 + (hoppersize* 2+1+8+4=15) + 2 + 4
	
//	0000:   05 00 53 21 86 12 33 01 0E BE 41 00 00 00 4F 4E    ..S!..3...A...ON
//	0010:   49 48 07 2D 00 00 00 05 00 06 00 01 09 00 3F AE    IH.-..........?.
//	0020:   2D 42 0C 00 02 0D 00 01 00 00 00 01 00 00 00 02    -B..............
//	0030:   00 00 5E C8 62 AB 41 00 00 00 CC F1 0A 40 0A 00    ..^.b.A......@..
//	0040:   CC F1 0A 40                                        ...@
		
	public IoBuffer buildHINO7EmptyHopperDelta(HarvesterObject harvester) {
	
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);		
		buffer.putInt(21);
		buffer.putShort((short)3);
		buffer.putShort((short)0x0C);
		buffer.put((byte)harvester.getResourceUpdateCount());
		buffer.putShort((short)0x0D);
		buffer.putInt(0);
		buffer.putInt(harvester.getResourceUpdateCount());
		buffer.putShort((short)0x0A);
		buffer.putFloat(harvester.getOutputHopperContent().size());
		
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
	}
	
	
	public IoBuffer buildHINO7ExperimentalDelta(HarvesterObject harvester) {
		
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);		
		buffer.putInt(0x18);
		buffer.putShort((short)3);
		buffer.putShort((short)0x0C);
		buffer.put((byte)harvester.getResourceUpdateCount());
		buffer.putShort((short)0x0D);
		buffer.putInt(1);
		buffer.putInt(0x1E);
		buffer.put((byte)0);
		buffer.putShort((short)0);
		buffer.putInt(harvester.getResourceUpdateCount());
		buffer.putShort((short)0x0A);
		buffer.putFloat(0); // since it's 0 outputhoppercontent
		
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
	}
	
//			0000:   05 00 53 21 86 12 53 D0 AC 41 3F 00 00 00 4F 4E    ..S!..S..A?...ON
//			0010:   49 48 07 18 00 00 00 03 00 0C 00 25 0D 00 01 00    IH.........%....
//			0020:   00 00 1E 00 00 00 00 00 00 0A 00 00 00 00 00       ...............
	
		
	public IoBuffer buildHINO7ExperimentalDelta2(HarvesterObject harvester) {

        int iHopperList = 0;
        if (harvester.getOutputHopperContent().size() >= 1) {
            iHopperList = 1;
        }
        
        IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);	

		buffer.putInt(36);
		buffer.putShort((short)3);
		buffer.putShort((short)0x0C);
		buffer.put(harvester.getUpdateCount());
		buffer.putShort((short)0x0D);
		buffer.putInt(iHopperList);
		buffer.putInt(harvester.getResourceUpdateCount());     
        int i = 0;
        int totalStackCount = 0; 
        Vector<ResourceContainerObject> outputHopper = harvester.getOutputHopperContent();
        for (ResourceContainerObject cont : outputHopper){
        	totalStackCount += cont.getStackCount();
        	if (cont.getReferenceID() == harvester.getSelectedHarvestResource().getId()) {
        		buffer.put((byte)2);
        		buffer.putShort((short) i);
        		buffer.putLong(cont.getReferenceID());                                                      
        		buffer.putFloat((float)cont.getStackCount());
        		//System.out.println("TOTAL STACKCOUNT " + (float)cont.getStackCount());
            }
            i++;
        }
        
        buffer.putShort((short)0x0A);
        buffer.putFloat((float)totalStackCount);
        int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
    }
	
	public IoBuffer buildHINO7ActivateDelta(HarvesterObject harvester) {

        int iHopperList = 0;
        if (harvester.getOutputHopperContent().size() >= 1) {
            iHopperList = 1;
        }
        int factor = 0;
        Vector<ResourceContainerObject> outputHopper = harvester.getOutputHopperContent();
        for (ResourceContainerObject cont : outputHopper){
        	if (cont.getReferenceID() == harvester.getSelectedHarvestResource().getId()) {
        		factor = 1;
            }
        }	
        
        byte iHopperUpdateCounter = harvester.getResourceUpdateCount();
       
        IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);	
		buffer.putInt(5+19+(15*factor));
		buffer.putShort((short)5);
		buffer.putShort((short)6);							
		buffer.put((byte)1);	
		// 2 + 4 + 4 + 2 + 4 + (15*h) = 16 + 2 + 1 = 19
		buffer.putShort((short)0x0C);
		buffer.put(harvester.getUpdateCount());
		buffer.putShort((short)0x0D);
		buffer.putInt(iHopperList);
		buffer.putInt(iHopperUpdateCounter);
		int totalStackCount = 0; 
		
		int i = 0;
        
        for (ResourceContainerObject cont : outputHopper){
        	totalStackCount += cont.getStackCount();
        	if (cont.getReferenceID() == harvester.getSelectedHarvestResource().getId()) {
        		buffer.put((byte)2);
        		buffer.putShort((short) i);
        		buffer.putLong(cont.getReferenceID());
        		buffer.putFloat((float)cont.getStackCount());
            }
            i++;
        }
       
        buffer.putShort((short)0x0A);                              
        buffer.putFloat((float)totalStackCount);

        int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
    }
	
	
	public IoBuffer buildHINO7ActivateDelta2(HarvesterObject harvester) {

        int iHopperList = 0;
        if (harvester.getOutputHopperContent().size() >= 1) {
            iHopperList = 1;
        }
        
        IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);	

		buffer.putInt(11);
		buffer.putShort((short)2);
		buffer.putShort((short)6);
		buffer.put((byte)1);
		buffer.putShort((short)9);
		buffer.putFloat(harvester.getActualExtractionRate());

        int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
    }
//	
//	02 00 06 00 01 09 00 43 94    IH............C.
//	0020:   59 42                                              YB

	public IoBuffer buildHINO7ActivateDelta2old(HarvesterObject harvester) {

        int iHopperList = 0;
        if (harvester.getOutputHopperContent().size() >= 1) {
            iHopperList = 1;
        }
        
        IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);	

		buffer.putInt(5);
		buffer.putShort((short)5);
		buffer.putShort((short)6);
		buffer.put((byte)1);

        int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
    }
	
	public IoBuffer buildHINO7DeactivateDelta(HarvesterObject harvester) {
       
        IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);	

		buffer.putInt(14); 
		buffer.putShort((short)5);
		buffer.putShort((short)6);
		buffer.put((byte)0);
		buffer.putShort((short)9);
		buffer.putFloat(0);
		buffer.putShort((short)0xC);
		buffer.put((byte)5);
		
		// new
//		buffer.putShort((short)6);
//		buffer.put((byte)0);
//		buffer.putShort((short)9);
//		buffer.put((byte)0);
//		buffer.putShort((short)0xC);
//		buffer.put((byte)5);
		
		//

        int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
    }
	
	
	public IoBuffer buildHINO7DeactivateDeltaold(HarvesterObject harvester) {
	       
        IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);	

		buffer.putInt(5); 
		buffer.putShort((short)5);
		buffer.putShort((short)6);
		buffer.put((byte)0);
		
		// new
		buffer.putShort((short)6);
		buffer.put((byte)0);
		buffer.putShort((short)9);
		buffer.put((byte)0);
		buffer.putShort((short)0xC);
		buffer.put((byte)5);
		
		//

        int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
    }
	
	public IoBuffer buildHINO7ClearDelta(HarvesterObject harvester) {

        int iHopperList = 0;
        if (harvester.getOutputHopperContent().size() >= 1) {
            iHopperList = 1;
        }
        
        IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x48494E4F);
		buffer.put((byte)7);	

		buffer.putInt(36);
		buffer.putShort((short)3);
		buffer.putShort((short)0x0C);
		buffer.put(harvester.getUpdateCount());
		buffer.putShort((short)0x0D);
		buffer.putInt(iHopperList);
		buffer.putInt(harvester.getResourceUpdateCount());  
        buffer.put((byte)4);    
        buffer.putShort((short)0x0A);                               
        buffer.putFloat((float)0.0F);
        int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
    }
	
	
	public IoBuffer buildDiscardResourceResponse(HarvesterObject harvester, byte actionMode, byte actionCounter,CreatureObject owner) {
		
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x80CE5E46);
		buffer.putInt(0x0B);
		buffer.putInt(0xEE);
		buffer.putLong(owner.getObjectID());	
		buffer.putInt(0);
		buffer.putInt(0xED);
		buffer.put((byte)1);
		buffer.put(actionCounter);		
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
	}
	
//0000:   05 00 46 5E CE 80 0B 00 00 00 EE 00 00 00 62 5F    ..F^..........b_
//0010:   02 15 00 00 01 00 00 00 00 00 ED 00 00 00 01 01    ................
//                                                  ^^ weird but works, should be zero allowed too

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

	
	public IoBuffer buildHINOBaseline7(HarvesterObject harvester,Vector<GalacticResource> vSRD) {
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		int sizeP = 74; 

		buffer.putShort((short)5);
		buffer.putInt(0x68A75F0C);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x4F4E4948);
		buffer.put((byte)7);
		
		int iResourceCount = vSRD.size();
		Vector<ResourceContainerObject> outputHopperContent = harvester.getOutputHopperContent();
		int hopperContentSize = outputHopperContent.size();
		float outputHopperCount = 0;
	
		//y = a + 4x + 16x + 12z = a + 20x + 12z		
		sizeP += (iResourceCount * 4);
		sizeP += (iResourceCount * 16);

		sizeP += (hopperContentSize * 12);
		
		for (int i = 0; i < iResourceCount; i++) {
			sizeP += vSRD.get(i).getName().length(); 
			sizeP += vSRD.get(i).getFileName().length();										
		}
		
		buffer.putInt(sizeP);
		
		for (ResourceContainerObject container : outputHopperContent){
			outputHopperCount += container.getStackCount();
		}

		buffer.putShort((short)0x0F);									
		buffer.put((byte)1);
		buffer.putInt(iResourceCount);
		buffer.putInt(iResourceCount);

		for (int i = 0; i < iResourceCount; i++) {
			buffer.putLong(vSRD.get(i).getId());
		}
		buffer.putInt(iResourceCount);

		buffer.putInt(iResourceCount);
										
		for (int i = 0; i < iResourceCount; i++) {
			buffer.putLong(vSRD.get(i).getId());
		}

		buffer.putInt(iResourceCount);

		buffer.putInt(iResourceCount);
		
		for (int i = 0; i < iResourceCount; i++) {
			buffer.put(getAsciiString(vSRD.get(i).getName()));
		}
		
		buffer.putInt(iResourceCount);
		buffer.putInt(iResourceCount);
		for (int i = 0; i < iResourceCount; i++) {
			buffer.put(getAsciiString(vSRD.get(i).getFileName()));
														
		}
		
		if (harvester.getSelectedHarvestResource() != null) {
			buffer.putLong(harvester.getSelectedHarvestResource().getId());
		} else {
			buffer.putLong(0);
		}

		buffer.put(harvester.isActivated() ? (byte)1 : (byte)0);
													
		buffer.putInt(harvester.getSpecRate()); // SPEC RATE buffer.putInt(harvester.getBER());
													
		buffer.putFloat(harvester.getBER());
														
		buffer.putFloat(harvester.getActualExtractionRate());
		buffer.putFloat(outputHopperCount);		
		buffer.putInt(harvester.getOutputHopperCapacity());
		buffer.put(harvester.getUpdateCount());
		buffer.putInt(hopperContentSize);
		buffer.putInt(0);
		for (ResourceContainerObject container : outputHopperContent){
			buffer.putLong(container.getReferenceID()); 												
			buffer.putFloat(container.getStackCount()); 
		}
		buffer.put(harvester.getStructuralCondition());
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();		
	}
	
	public IoBuffer buildCustomNameDelta(HarvesterObject harvester, String customName) {
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		int sizeP = 8;
		sizeP += 2*customName.length();
		buffer.putShort((short)5);
		buffer.putInt(0x12862153);
		buffer.putLong(harvester.getObjectID());
		buffer.putInt(0x54414E4F);
		buffer.put((byte)3);		
		buffer.putInt(sizeP);
		buffer.putShort((short)1);
		buffer.putShort((short)2);
		buffer.put(getUnicodeString(customName));
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();		
	}
	
	public IoBuffer buildPermissionListCreate(HarvesterObject harvester, Vector<String> permissionList, String listName) {
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		int listSize = permissionList.size();
		buffer.putShort((short)4);
		buffer.putInt(0x52F364B8);
		buffer.putInt(listSize);
		for (String name : permissionList){
			buffer.put(getUnicodeString(name));
		}
		//buffer.putInt(0x61);
		buffer.putInt(0);
		//buffer.putShort((short)0);
		buffer.put(getUnicodeString(listName));
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();		
	}
	/*
	    0000:   04 00 B8 64 F3 52 01 00 00 00 06 00 00 00 63 00    ...d.R........c.
		0010:   68 00 61 00 72 00 6F 00 6E 00 00 00 00 00 05 00    h.a.r.o.n.......
		0020:   00 00 41 00 44 00 4D 00 49 00 4E 00                ..A.D.M.I.N.
	*/
		
	// Send this in response to the Radial Menu to manage harvesters, 
	// it is necessary to send this *before* the 07 Baseline! 
	//ResourceHarvesterActivatePageMessage (BD18C679)
	public IoBuffer buildResourceHarvesterActivatePageMessage(HarvesterObject harvester) {
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)2);
		buffer.putInt(0xBD18C679);
		buffer.putLong(harvester.getObjectID());
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
	}

	public IoBuffer buildHarvesterGetResourceData(HarvesterObject harvester,SWGObject owner,Vector<GalacticResource> planetResources) {
		IoBuffer buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short)5);
		buffer.putInt(0x80CE5E46);
		buffer.putInt(0x0B);
		buffer.putInt(0x000000EA);
		buffer.putLong(owner.getObjectID());
		buffer.putInt(0);
		buffer.putLong(harvester.getObjectID());
		if (planetResources == null || planetResources.isEmpty()) {
			buffer.putInt(0);
		} else {
			buffer.putInt(planetResources.size());
			for (int i = 0; i < planetResources.size(); i++) {
				float localConcentration = planetResources.get(i).deliverConcentrationForSurvey(owner.getPlanetId(), owner.getPosition().x, owner.getPosition().z);
				buffer.putLong(planetResources.get(i).getId());
				buffer.put(getAsciiString(planetResources.get(i).getName()));
				buffer.put(getAsciiString(planetResources.get(i).getFileName()));
				buffer.put((byte)localConcentration);
			}
		}
		
		int size = buffer.position();
		buffer.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(buffer.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();	
	}
	
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendBaselines() {
		
	}
}
