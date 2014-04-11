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

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.Console;
import resources.common.StringUtilities;

public class MissionListRequest extends ObjControllerObject {

	private long objectId;
	private long terminalId;
	private int tickCount;
	
	@Override
	public void deserialize(IoBuffer data) {
		Console.println("MissionListRequest: " + StringUtilities.bytesToHex(data.array()));
		setObjectId(data.getLong());
		data.getInt(); // unk
		data.get(); // unk byte
		setTickCount(data.get());
		setTerminalId(data.getLong());
	}

	@Override
	public IoBuffer serialize() {
		return null;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(long terminalId) {
		this.terminalId = terminalId;
	}

	public int getTickCount() {
		return tickCount;
	}

	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}

}
