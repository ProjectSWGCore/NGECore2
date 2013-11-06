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

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.Opcodes;

public class PlayMusicMessage extends SWGMessage {
	
	private long targetId;
	private String sndFile;
	private int repetitions;
	private boolean flag;
	
	public PlayMusicMessage(String sndFile, long targetId, int repetitions, boolean flag) {
		this.targetId = targetId;
		this.sndFile = sndFile;
		this.repetitions = repetitions;
		this.flag = flag;
	}
	
	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(21 + sndFile.length()).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 5);
		result.putInt(Opcodes.PlayMusicMessage);
		result.put(getAsciiString(sndFile));
		result.putLong(targetId);
		result.putInt(repetitions);
		result.put((byte) ((flag) ? 1 : 0));
		return result.flip();
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}
	
}
