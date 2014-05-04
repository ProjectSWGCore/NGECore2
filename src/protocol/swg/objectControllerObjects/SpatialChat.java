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

@SuppressWarnings("unused")
public class SpatialChat extends ObjControllerObject {
	
	private long destinationId;
	private long sourceId;
	private long targetId;
	private String chatMessage;
	private String chatMessageParsed;
	private short balloonSize;
	private short balloonType = 1;
	private short chatType;
	private short moodId;
	private byte languageId = 1;
	private OutOfBand outOfBand;
	
	public SpatialChat(long destinationId, long sourceId, long targetId, String chatMessage, short chatType, short moodId, int languageId, OutOfBand outOfBand) {
		try {
			this.destinationId = destinationId;
			this.sourceId = sourceId;
			this.targetId = targetId;
			this.chatMessage = chatMessage;
			this.chatType = chatType;
			this.moodId = moodId;
			this.languageId = (byte) languageId;
			this.outOfBand = ((outOfBand == null) ? new OutOfBand() : outOfBand);

			/*String[] chatMessageParse = chatMessage.split(" ", 6);
			
			this.chatMessageParsed = chatMessageParse[5];
			
			balloonSize = (short)(Short.parseShort(chatMessageParse[4], 10) + 4);
			//balloonType = Short.parseShort(chatMessageParse[1], 10);*/
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public void setDestinationId(long destinationId) {
		this.destinationId = destinationId;
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		outOfBand.setHeaderBytes(2);
		IoBuffer outOfBandBuffer = outOfBand.serialize();
		IoBuffer result = IoBuffer.allocate(51 + (chatMessage.length() * 2) + outOfBandBuffer.array().length).order(ByteOrder.LITTLE_ENDIAN);
		result.putInt(ObjControllerMessage.SPACIAL_CHAT);
		result.putLong(destinationId);
		result.putInt(0);
		result.putLong(sourceId);
		result.putLong(targetId);
		result.put(getUnicodeString(chatMessage));
		result.putInt(0x00000000);						// Changes type of message?
		result.putShort((short) 0x32);
		result.putShort(chatType);
		result.putShort(moodId);
		result.put(languageId);
		result.putInt(0);
		result.put(outOfBandBuffer.array());
		return result.flip();
	}
	
}
