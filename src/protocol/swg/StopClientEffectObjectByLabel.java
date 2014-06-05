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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

@SuppressWarnings("unused")

public class StopClientEffectObjectByLabel extends SWGMessage {

	private long objectId;
	private String effectFile;
	private String commandString;
	private byte unknownFlag;

	public StopClientEffectObjectByLabel(long objectId, String commandString) {
		this.objectId = objectId;
		this.commandString = commandString;
		this.unknownFlag = 1;
	}
	
	public StopClientEffectObjectByLabel(long objectId, String commandString, byte unknownFlag) {
		this.objectId = objectId;
		this.commandString = commandString;
		this.unknownFlag = unknownFlag;
	}
	
	@Override
	public IoBuffer serialize() {

		IoBuffer result = IoBuffer.allocate(17 + commandString.length()).order(ByteOrder.LITTLE_ENDIAN);

		result.putShort((short) 4);
		result.putInt(0xAD6F6B26);
		result.putLong(objectId);
		result.put(getAsciiString(commandString));
		result.put(unknownFlag);
		
		return result.flip();

	}

	@Override
	public void deserialize(IoBuffer data) {
		
	}
	
}
