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

public class ShowFlyText extends ObjControllerObject {
	
	private long recieverId;
	private long objectId;
	private String stfFile;
	private String stfString;
	private float scale;
	private float color;

	public ShowFlyText(long recieverId, long objectId, String stfFile, String stfString, float scale, float color) {
		this.recieverId = recieverId;
		this.objectId = objectId;
		this.stfFile = stfFile;
		this.stfString = stfString;
		this.scale = scale;
		this.color = color;
	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(47 + stfFile.length() + stfString.length()).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putInt(ObjControllerMessage.SHOW_FLY_TEXT);
		result.putLong(recieverId);
		result.putInt(0);
		result.putLong(objectId);
		
		result.put(getAsciiString(stfFile));
		result.putInt(0);
		result.put(getAsciiString(stfString));
		result.putInt(0);
		result.putFloat(scale);
		result.putFloat(color); // color
		result.putShort((short) 0);
		result.put((byte) 0);
		
		return result.flip();
		
	}

}
