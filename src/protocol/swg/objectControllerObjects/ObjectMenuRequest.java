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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.RadialOptions;

public class ObjectMenuRequest extends ObjControllerObject {
	
	private long characterId;
	private long targetId;
	private Vector<RadialOptions> radialOptions = new Vector<RadialOptions>();
	private byte radialCount;

	@Override
	public void deserialize(IoBuffer buffer) {

		setCharacterId(buffer.getLong());
		buffer.getInt();
		setTargetId(buffer.getLong());
		setCharacterId(buffer.getLong());

		int listSize = buffer.getInt();
		for(int i = 0; i < listSize; i++) {
			RadialOptions radial = new RadialOptions();
		    buffer.get();
			radial.setParentId(buffer.get());
			radial.setOptionId(buffer.getShort());
			radial.setOptionType(buffer.get());
			int size = buffer.getInt();
			if(size > 0) {
				try {
					radial.setDescription(new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE"));
					buffer.position(buffer.position() + size * 2);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				radial.setDescription("");
			}
			radialOptions.add(radial);
		}
		setRadialCount(buffer.get());
	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector<RadialOptions> getRadialOptions() {
		return radialOptions;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(long characterId) {
		this.characterId = characterId;
	}

	public byte getRadialCount() {
		return radialCount;
	}

	public void setRadialCount(byte radialCount) {
		this.radialCount = radialCount;
	}

}
