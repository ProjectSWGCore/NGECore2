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
import resources.common.RGB;

public class ShowFlyText extends ObjControllerObject {
	
	private long recieverId;
	private long objectId;
	private int unknownInt1;
	private short unknownShort1;
	private byte unknownByte1;
	private int unknownInt2;
	private String stfFile;
	private String stfString;
	private short xp;
	private float scale;
	private RGB color;
	private int displayType;
	private boolean alternativeStructure;
	private String customText;
	
	public ShowFlyText(long recieverId, long objectId, String stfFile, String stfString, float scale, RGB color, int displayType) {
		this.recieverId = recieverId;
		this.objectId = objectId;
		this.stfFile = stfFile;
		this.stfString = stfString;
		this.scale = scale;
		this.color = color;
		this.displayType = displayType;
		this.alternativeStructure = false;
	}
	
	public ShowFlyText(long recieverId, long objectId, int unknownInt1, int unknownShort1, int unknownByte1, int unknownInt2, String stfFile, String stfString, String customText, int xp, float scale, RGB color, int displayType) {
		this.recieverId = recieverId;
		this.objectId = objectId;
		this.unknownInt1 = unknownInt1;
		this.unknownShort1 = (short) unknownShort1;
		this.unknownByte1 = (byte) unknownByte1;
		this.unknownInt2 = unknownInt2;
		this.stfFile = stfFile;
		this.stfString = stfString;
		this.customText = customText;
		this.xp = (short) xp;
		this.scale = scale;
		this.color = color;
		this.alternativeStructure = true;
		this.displayType = displayType;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IoBuffer serialize() { 
		if (!alternativeStructure) {
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
			result.put(color.getBytes());
			result.putInt(displayType);
			return result.flip();
		} else {
			IoBuffer result = IoBuffer.allocate(124 + stfFile.length() + stfString.length()).order(ByteOrder.LITTLE_ENDIAN);
			result.setAutoExpand(true);
			result.putInt(ObjControllerMessage.SHOW_FLY_TEXT);
			result.putLong(recieverId);
			result.putInt(0);
			result.putLong(objectId);
			result.putLong(0);
			result.putInt(unknownInt1);
			result.putShort(unknownShort1);
			result.put(unknownByte1);
			result.putInt(unknownInt2);
			result.put(getAsciiString(stfFile));
			result.putInt(0);
			result.put(getAsciiString(stfString));
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.putLong(0);
			result.put(getUnicodeString(customText)); // %TO?
			result.putShort(xp); // %DI ?
			result.putLong(0);
			result.putFloat(scale);
			result.put(color.getBytes());
			result.putInt(displayType);
			
			result.flip();
			//StringUtilities.printBytes(result.array());
			return result;
		}
	}

}
