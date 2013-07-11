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
import java.util.TimeZone;

import org.apache.mina.core.buffer.IoBuffer;

public class LoginEnumCluster extends SWGMessage {

	private byte[] servers;
	private int serverCount = 0;
	private int maxCharacters = 8;
	private int maxPopulation = 250;
	private int maxConcurrent = 12;
	private TimeZone timeZone = TimeZone.getDefault();
	
	public LoginEnumCluster(int maxCharacters) {
		this.maxCharacters = maxCharacters;
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		if (servers == null || servers.length == 0)
			return IoBuffer.allocate(0);

		IoBuffer result = IoBuffer.allocate(22 + servers.length).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short)3);
		result.putInt(0xC11C63B9);
		result.putInt(serverCount);
		result.put(servers);
		result.putInt(maxCharacters);
		result.putInt(maxPopulation);
		result.putInt(maxConcurrent);
		
		int size = result.position();
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();
		
	}
	
	public void addServer(int galaxyID, String serverName) {
		IoBuffer result = IoBuffer.allocate(10 + serverName.length()).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putInt(galaxyID);
		result.put(getAsciiString(serverName));
		result.putInt(timeZone.getRawOffset() / 3600000);
		result.flip();
		
		if (servers == null)
			servers = result.array();
		else
			servers = IoBuffer.allocate(servers.length + result.capacity())
			.put(servers)
			.put(result.array())
			.flip()
			.array();
		serverCount++;
	}
	
	public int getSize() {
		return 22 + servers.length;
	}
}
