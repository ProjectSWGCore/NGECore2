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
package protocol.swg.chat;

import java.nio.ByteOrder;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;
import services.chat.WaypointAttachment;


public class ChatPersistentMessageToClient extends SWGMessage {
	
	private String sender;
	private String galaxyName;
	private int mailId;
	private byte requestTypeFlag;
	private String message;
	private String subject;
	private byte status;
	private int timestamp;
	private List<WaypointAttachment> attachments;

	public ChatPersistentMessageToClient(String sender, String galaxyName, int mailId, byte requestTypeFlag, String message, String subject, byte status, int timestamp, List<WaypointAttachment> attachments) {
		
		this.sender = sender;
		this.galaxyName = galaxyName;
		this.mailId = mailId;
		this.requestTypeFlag = requestTypeFlag;
		this.message = message;
		this.subject = subject;
		this.status = status;
		this.timestamp = timestamp;
		this.attachments = attachments;

	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
				
		IoBuffer result = IoBuffer.allocate(41 + sender.length() + galaxyName.length() + message.length() * 2 + subject.length() * 2).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		result.putShort((short) 2);
		result.putInt(0x08485E17);
		result.put(getAsciiString(sender));
		result.put(getAsciiString("SWG"));
		result.put(getAsciiString(galaxyName));
		result.putInt(mailId);
		result.put(requestTypeFlag);

		if(requestTypeFlag == 1)
			result.putInt(0);
		else
			result.put(getUnicodeString(message));
		
		result.put(getUnicodeString(subject));
		
		if(requestTypeFlag == 1 || attachments.size() == 0)
			result.putInt(0);	
		else if(requestTypeFlag == 0 && attachments.size() > 0){
			
			int position = result.position();
			result.putInt(0);
			
			for(WaypointAttachment attachment : attachments) {
				
				result.putInt(0);
				result.putFloat(attachment.positionX);
				result.putFloat(attachment.positionY);
				result.putFloat(attachment.positionZ);
				result.putLong(0);
				result.putInt(attachment.planetCRC);
				result.put(getUnicodeString(attachment.name));
				result.putLong(attachment.cellID);
				result.put(attachment.color);
				result.put((byte) (attachment.active ? 1 : 0));
				
			}
			
			result.putInt(position, (result.position() - position - 4) / 2);
			
		}
		
		result.put(status);
		result.putInt(timestamp);
		result.putInt(0);
		
		int size = result.position();
		result = IoBuffer.allocate(size).put(result.array(), 0, size);

		return result.flip();
	}

}
