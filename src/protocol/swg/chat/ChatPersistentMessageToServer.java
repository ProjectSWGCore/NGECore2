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

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;
import services.chat.WaypointAttachment;


public class ChatPersistentMessageToServer extends SWGMessage {

	private String message;
	private int counter;
	private String subject;
	private String recipient;
	private List<WaypointAttachment> waypointAttachments = new ArrayList<WaypointAttachment>();

	@Override
	public void deserialize(IoBuffer buffer) {
		buffer.getShort();
		buffer.getInt();
		
		int size;
		
		size = buffer.getInt();
		this.message = new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), StandardCharsets.UTF_16LE);
		buffer.position(buffer.position() + size * 2);
			
		int attachmentsSize = buffer.getInt() * 2; 	
			
		/*while(attachmentsSize > 0) {
			buffer.get();
			--attachmentsSize;
		}*/
			
		if(attachmentsSize > 0) {
				
			int position = buffer.position();
			
			while(buffer.position() < position + attachmentsSize) {
					
				short appendByte = buffer.getShort();
				buffer.get();
				int type = buffer.getInt();
					
				if(type == 0xFFFFFFFD) {
						
					WaypointAttachment waypoint = new WaypointAttachment();
						
					buffer.getInt(); // unk 0
					waypoint.positionX = buffer.getFloat();
					waypoint.positionY = buffer.getFloat();
					waypoint.positionZ = buffer.getFloat();
					buffer.getLong(); // unk
					waypoint.planetCRC = buffer.getInt();
					size = buffer.getInt();
					waypoint.name = new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), StandardCharsets.UTF_16LE);
					buffer.position(buffer.position() + size * 2);
					waypoint.cellID = buffer.getLong();
					waypoint.color = buffer.get();
					waypoint.active = buffer.get() == 0 ? false : true;
					waypointAttachments.add(waypoint);
					
					if(appendByte > 0)
						buffer.get();
						
				}
			}
				
		}
		
		this.counter = buffer.getInt();

		size = buffer.getInt();
		this.subject = new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), StandardCharsets.UTF_16LE);
		buffer.position(buffer.position() + size * 2);

		buffer.getInt(); // spacer

		size = buffer.getShort();
		this.recipient = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), StandardCharsets.US_ASCII);
		buffer.position(buffer.position() + size);

	}

	@Override
	public IoBuffer serialize() {
		return null;
	}

	public String getMessage() {
		return message;
	}

	public String getSubject() {
		return subject;
	}

	public String getRecipient() {
		return recipient;
	}

	public int getCounter() {
		return counter;
	}

	public List<WaypointAttachment> getWaypointAttachments() {
		return waypointAttachments;
	}

}
