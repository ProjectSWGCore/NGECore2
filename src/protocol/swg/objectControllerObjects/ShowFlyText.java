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
import resources.common.OutOfBand;
import resources.common.RGB;
import resources.common.Stf;

public class ShowFlyText extends ObjControllerObject {
	
	private long receiverId;
	private long objectId;
	private Stf stf;
	private OutOfBand outOfBand;
	private float scale;
	private RGB color;
	private int displayType;
	
	public ShowFlyText(long receiverId, long objectId, String stf, OutOfBand outOfBand, float scale, RGB color, int displayType) {
		this.receiverId = receiverId;
		this.objectId = objectId;
		this.stf = new Stf(stf);
		this.outOfBand = outOfBand;
		this.scale = scale;
		this.color = color;
		this.displayType = displayType;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IoBuffer serialize() { 
		IoBuffer outOfBandBuffer = outOfBand.serialize();
		IoBuffer result = IoBuffer.allocate(35 + outOfBandBuffer.array().length).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		result.putInt(ObjControllerMessage.SHOW_FLY_TEXT);
		result.putLong(receiverId);
		result.putInt(0);
		result.putLong(objectId);
		result.put(stf.getBytes());
		result.put(outOfBandBuffer.array());
		result.putFloat(scale);
		result.put(color.getBytes());
		result.putInt(displayType);
		result.flip();
		return result;
	}

}
