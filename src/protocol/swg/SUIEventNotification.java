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
package protocol.swg;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

public class SUIEventNotification extends SWGMessage {
	
	private int windowId;
	private int eventType;
	private int updateCounter;
	private Vector<String> returnList = new Vector<String>();

	@Override
	public void deserialize(IoBuffer buffer) {
		
		buffer.getShort();
		buffer.getInt();
		
		windowId = buffer.getInt();
		eventType = buffer.getInt();
		int count = buffer.getInt();
		updateCounter = buffer.getInt();
		
		for(int i = 0; i < count; ++i) {

			int size = buffer.getInt();
			if(size > 0) {

				try {
					returnList.add(new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE"));
					buffer.position(buffer.position() + size * 2);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
			}

		}
		
	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getWindowId() {
		return windowId;
	}

	public void setWindowId(int windowId) {
		this.windowId = windowId;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public int getUpdateCounter() {
		return updateCounter;
	}

	public void setUpdateCounter(int updateCounter) {
		this.updateCounter = updateCounter;
	}

	public Vector<String> getReturnList() {
		return returnList;
	}

	public void setReturnList(Vector<String> returnList) {
		this.returnList = returnList;
	}

}
