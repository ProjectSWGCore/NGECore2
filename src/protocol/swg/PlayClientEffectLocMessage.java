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

public class PlayClientEffectLocMessage extends SWGMessage {

	private String effectFile;
	private String planet;
	private Point3D position;

	public PlayClientEffectLocMessage(String effectFile, String planet, Point3D position) {
		
		this.effectFile = effectFile;
		this.planet = planet;
		this.position = position;

	}
	
	@Override
	public IoBuffer serialize() {

		IoBuffer result = IoBuffer.allocate(36 + effectFile.length() + planet.length()).order(ByteOrder.LITTLE_ENDIAN);

		result.putShort((short) 9);
		result.putInt(0x02949E74);
		result.put(getAsciiString(effectFile));
		result.put(getAsciiString(planet));
		result.putFloat(position.x);
		result.putFloat(position.y);
		result.putFloat(position.z);
		result.putLong(0);
		result.putInt(0);		// unks seem to be always 0
		result.putShort((short) 0);
		
		return result.flip();

	}

	@Override
	public void deserialize(IoBuffer data) {
		// TODO Auto-generated method stub
		
	}

}
