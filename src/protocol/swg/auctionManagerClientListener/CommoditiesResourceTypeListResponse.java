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
package protocol.swg.auctionManagerClientListener;

import java.nio.ByteOrder;
import java.util.Vector;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class CommoditiesResourceTypeListResponse extends SWGMessage {

	private Vector<String> resNames;

	public CommoditiesResourceTypeListResponse(Vector<String> resNames) {
		this.resNames = resNames;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		String galaxyName = NGECore.getInstance().getGalaxyName();
		final IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 2);
		result.putInt(0x5EDD19CB);
		
		result.put(getAsciiString(galaxyName + "." + String.valueOf(resNames.size())));
		result.putInt(479);
		result.putLong(0); 
		
		// wrong struct, TODO: research
		resNames.forEach(s -> result.put(getAsciiString(s)));
		
		int size = result.position();
		IoBuffer result2 = IoBuffer.allocate(size).put(result.array(), 0, size);

		return result2.flip();
	}

}
