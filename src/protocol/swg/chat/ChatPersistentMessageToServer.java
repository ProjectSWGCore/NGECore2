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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
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
		
		try {
			size = buffer.getInt();
			setMessage(new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE"));
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
						waypoint.name = new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE");
						buffer.position(buffer.position() + size * 2);
						waypoint.cellID = buffer.getLong();
						waypoint.color = buffer.get();
						byte active = buffer.get();
						if(active == 1)
							waypoint.active = true;
						else
							waypoint.active = false;

						waypointAttachments.add(waypoint);
						System.out.println(waypoint.name);
						if(appendByte > 0)
							buffer.get();
						
					}
				}
				
			}

			setCounter(buffer.getInt());
			
			size = buffer.getInt();
			setSubject(new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE"));
			buffer.position(buffer.position() + size * 2);
			
			buffer.getInt(); // spacer

			size = buffer.getShort();
			setRecipient(new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII"));
			buffer.position(buffer.position() + size);

			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public IoBuffer serialize() {
		return null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public List<WaypointAttachment> getWaypointAttachments() {
		return waypointAttachments;
	}

	public void setWaypointAttachments(List<WaypointAttachment> waypointAttachments) {
		this.waypointAttachments = waypointAttachments;
	}



}
