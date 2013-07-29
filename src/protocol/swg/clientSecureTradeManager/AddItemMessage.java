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
package protocol.swg.clientSecureTradeManager;

import java.nio.ByteOrder;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;
import protocol.swg.SWGMessage;

@SuppressWarnings("unused")

public class AddItemMessage extends SWGMessage {

	private NGECore core;
	private SWGObject tradeObject;
	private long tradeObjectID;
	
	@Override
	public void deserialize(IoBuffer data) {
		data.getShort();
		data.getInt();
		setTradeObjectID(data.getLong());
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(200).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 2);
		result.putInt(0x1E8D1356);
		result.putLong(tradeObjectID);
		return result.flip();
	}
	
	public long getTradeObjectID() {
		return this.tradeObjectID;
		
	}
	
	public SWGObject getTradeObject() {
		return this.tradeObject;
	}
	
	public void setTradeObject(SWGObject object) {
		this.tradeObject = object;
		this.tradeObjectID = object.getObjectID();
	}
	
	public void setTradeObjectID(long objectID) {
		this.tradeObjectID = objectID;
	}

}
