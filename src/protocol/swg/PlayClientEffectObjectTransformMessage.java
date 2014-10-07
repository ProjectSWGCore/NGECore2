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

import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class PlayClientEffectObjectTransformMessage extends SWGMessage {
	
	private long objectId;
	private String effectFile;
	private String commandString;
	private Point3D effectorPosition; 
	private Quaternion effectorOrientation;
	
	public PlayClientEffectObjectTransformMessage(String effectFile, long objectId, String commandString, Point3D effectorPosition, Quaternion effectorOrientation) {
		this.effectFile = effectFile;
		this.objectId = objectId;
		this.commandString = commandString;
		this.effectorPosition = effectorPosition;
		this.effectorOrientation = effectorOrientation;
	}
	
	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(46 + effectFile.length() + commandString.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 5);
		result.putInt(0x4F5E09B6);
		result.put(getAsciiString(effectFile));		
		result.putFloat(effectorOrientation.w);
		result.putFloat(effectorOrientation.x);
		result.putFloat(effectorOrientation.y);
		result.putFloat(effectorOrientation.z);		
		result.putFloat(effectorPosition.x);
		result.putFloat(effectorPosition.y);
		result.putFloat(effectorPosition.z);
		result.putLong(objectId);
		result.put(getAsciiString(commandString));
		return result.flip();
	}
		
	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}
	
}
