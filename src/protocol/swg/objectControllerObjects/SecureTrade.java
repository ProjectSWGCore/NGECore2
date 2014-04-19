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

public class SecureTrade extends ObjControllerObject{

	private long senderID;
	private long recieverID;
	private short unknown;
	
	public SecureTrade() {
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		
		setSenderID(data.getLong());
		data.getLong(); // skip through 0's
		data.getLong(); // skip through 0's
		setRecieverID(data.getLong());
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(40).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putInt(ObjControllerMessage.SPACIAL_CHAT);
		result.putInt(1);
		result.putLong(senderID);
		result.putLong(0);
		result.putLong(0);
		result.putLong(recieverID);

		return result.flip();
	}
	
	public long getSenderID() { return this.senderID; }
	public long getRecieverID() { return this.recieverID; }
	public short getUnkn() { return this.unknown; }
	
	public void setUnknown(short unknown) {
		this.unknown = unknown;
	}
	public void setSenderID(long senderID) {
		this.senderID = senderID;
	}
	
	public void setRecieverID(long recieverID) {
		this.recieverID = recieverID;
	}
	
	
}
