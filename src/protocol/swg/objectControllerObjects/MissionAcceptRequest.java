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

public class MissionAcceptRequest extends ObjControllerObject {

	private long missionObjId;
	private long terminalObjId;
	private byte terminalType;

	public MissionAcceptRequest() { }
	
	@Override
	public void deserialize(IoBuffer data) {
		data.getLong();
		data.getInt();
		setMissionObjId(data.getLong());
		setTerminalObjId(data.getLong());
		setTerminalType(data.get());
	}

	@Override
	public IoBuffer serialize() {
		return null;
	}

	public long getMissionObjId() {
		return missionObjId;
	}

	public void setMissionObjId(long misssionObjId) {
		this.missionObjId = misssionObjId;
	}

	public long getTerminalObjId() {
		return terminalObjId;
	}

	public void setTerminalObjId(long terminalObjId) {
		this.terminalObjId = terminalObjId;
	}
	
	public void setTerminalType(byte terminalType) {
		this.terminalType = terminalType;
	}
	
	public byte getTerminalType() {
		return terminalType;
	}
}
