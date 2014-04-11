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

import java.nio.ByteOrder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mina.core.buffer.IoBuffer;

import services.collections.ServerFirst;
import engine.resources.common.CRC;

public class CollectionServerFirstListResponse extends SWGMessage {
	
	private Map<String, ServerFirst> sfList;
	private String server;
	
	public CollectionServerFirstListResponse(String server, Map<String, ServerFirst> serverFirstList){
		this.sfList = serverFirstList;
		this.server = server;
	}
	
	@Override
	public void deserialize(IoBuffer data) {

	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer;
		
		int size = 0;
		
		synchronized (sfList) {
			for (Entry<String, ServerFirst> entry : sfList.entrySet()) {
				size += entry.getValue().getBytes().length;
			}
			
			buffer = IoBuffer.allocate(12 + server.length() + size).order(ByteOrder.LITTLE_ENDIAN);
			buffer.putShort((short) 2);
			buffer.putInt(CRC.StringtoCRC("CollectionServerFirstListResponse"));
			
			buffer.put(getAsciiString(server));
			buffer.putInt(sfList.size());
			
			for (Entry<String, ServerFirst> entry : sfList.entrySet()) {
				buffer.put(entry.getValue().getBytes());

			}
			return buffer.flip();
		}
	}
}
