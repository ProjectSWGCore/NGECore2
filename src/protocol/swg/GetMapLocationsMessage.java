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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;


public class GetMapLocationsMessage extends SWGMessage{

	private String planet;
	private float x, y;
	private byte category;
	private byte subCategory;
	private byte icon;
	
	public GetMapLocationsMessage() {
		
	}
	
	public void deserialize(IoBuffer buffer) {
		buffer.getShort();
		buffer.getInt();
		short size = buffer.getShort();
		try {
			planet = new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII");
			buffer.position(buffer.position() + size);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		x = buffer.getFloat();
		y = buffer.getFloat();
		category = buffer.get();
		subCategory = buffer.get();
		icon = buffer.get();
	}
	
	public IoBuffer serialize() {
		return null;
	}

	public byte   getCategory()    { return category; }
	public byte   getIcon()        { return icon; }
	public String getPlanet()      { return planet; }
	public byte   getSubCategory() { return subCategory; }
	public float  getX()           { return x; }
	public float  getY()           { return y; }
}
