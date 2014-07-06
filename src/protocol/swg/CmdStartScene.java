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

public class CmdStartScene extends SWGMessage {

	private byte ignoreLayoutFiles;
	private long characterId;
	private String terrainFile;
	private String sharedRaceTemplate;
	private float x;
	private float y;
	private float z;
	private long time ;
	private float radians;

	public CmdStartScene(byte ignoreLayoutFiles, long characterId, String terrainFile, String sharedRaceTemplate, float x, float y, float z, long time, float radians) {
		
		this.ignoreLayoutFiles = ignoreLayoutFiles;
		this.characterId= characterId;
		this.terrainFile = terrainFile;
		this.sharedRaceTemplate = sharedRaceTemplate;
		this.x = x;
		this.y = y;
		this.z = z;
		this.time = time;
		this.radians = radians;

	}
	
	public void deserialize(IoBuffer data) {
		
	}

	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(47 + terrainFile.length() + sharedRaceTemplate.length()).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putShort((short)9);
		result.putInt(Opcodes.CmdStartScene);
		result.put((byte)ignoreLayoutFiles);
		result.putLong(characterId);
		result.put(getAsciiString(terrainFile));
		result.putFloat(x);
		result.putFloat(y);
		result.putFloat(z);
		result.putFloat(radians);
		result.put(getAsciiString(sharedRaceTemplate));
		
		result.putLong(time);
		result.put(new byte[] { (byte)0x8E, (byte)0xB5, (byte)0xEA, (byte)0x4E });
		return result.flip();
		
	}
}
