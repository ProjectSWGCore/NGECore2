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
package resources.group;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.AString;
import engine.resources.objects.Delta;

public class GroupInviteInfo extends Delta {
	
	private static final long serialVersionUID = 1L;
	
	private long senderId;
	private AString senderName;
	private long inviteCounter;
	
	public GroupInviteInfo(long senderId, String senderName) {
		this.senderId = senderId;
		this.senderName = new AString(senderName);
		inviteCounter = 0;
	}
	
	public GroupInviteInfo() {
		
	}
	
	public long getSenderId() {
		synchronized(objectMutex) {
			return senderId;
		}
	}
	
	public String getSenderName() {
		synchronized(objectMutex) {
			return senderName.get();
		}
	}
	
	public void setSender(long senderId, String senderName) {
		synchronized(objectMutex) {
			this.senderId = senderId;
			this.senderName = new AString(senderName);
			inviteCounter++;
		}
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = createBuffer(16 + senderName.getBytes().length);
			buffer.putLong(senderId);
			buffer.put(senderName.getBytes());
			buffer.putLong(inviteCounter);
			return buffer.array();
		}
	}
	
}
