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

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.Utilities;

public class SetProfessionTemplate extends ObjControllerObject {
	
	private String profession;

	@Override
	public void deserialize(IoBuffer data) {
		data.getLong();
		data.getInt();
		short size = data.getShort();
		try {
			profession = new String(ByteBuffer.allocate(size).put(data.array(), data.position(), size).array(), "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		data.position(data.position() + size);
	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

}
