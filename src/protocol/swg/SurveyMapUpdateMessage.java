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
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;
import resources.objects.resource.ResourceConcentration;

public class SurveyMapUpdateMessage extends SWGMessage {
	
	private IoBuffer buffer;
	private float highestConcentration;
	float highestX;
	float highestZ;
	
	public SurveyMapUpdateMessage(Vector<ResourceConcentration> concentrationMap, int pointsAmount) {
		//this.buffer = buffer;
		
		this.buffer = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putInt(pointsAmount);		
		highestX = 0;
		highestZ = 0;
		highestConcentration = 0;	
		for (int i = 0; i < pointsAmount; i++) {
			ResourceConcentration cursorConcentration = concentrationMap.get(i);
			float concentration = 0.01f*cursorConcentration.getConcentration(); // percentage
			float markerX = cursorConcentration.getCoordsX();
			float markerZ = cursorConcentration.getCoordsZ();	
//			System.out.println("markerX " + markerX);
//			System.out.println("markerZ " + markerZ);
//			System.out.println("concentration " + concentration);
			buffer.putFloat(markerX);
			buffer.putFloat(0);
			buffer.putFloat(markerZ);
			buffer.putFloat(concentration);
			if (highestConcentration<concentration) {
				highestX = markerX;
				highestZ = markerZ;
				highestConcentration = concentration;
			}
		}	
		//int size = buffer.position();
		buffer.flip();		
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		result.putShort((short)2);
		result.putInt(0x877F79AC);
		result.put(buffer.array());
		int size = result.position();
		result.flip();
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();		
	}

	public float getHighestConcentration() {
		return highestConcentration;
	}

	public float getHighestX() {
		return highestX;
	}

	public float getHighestZ() {
		return highestZ;
	}
}
