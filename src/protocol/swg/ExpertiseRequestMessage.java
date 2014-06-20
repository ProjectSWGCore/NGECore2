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
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

public class ExpertiseRequestMessage extends SWGMessage {
	
	private List<String> expertiseSkills = new ArrayList<String>();

	@Override
	public void deserialize(IoBuffer buffer) {
		
		buffer.getShort();
		buffer.getInt();
		
		int size = buffer.getInt();
		
		for(int i = 0; i < size; i++) {
			
			short length = buffer.getShort();
			try {
				String skill = new String(ByteBuffer.allocate(length).put(buffer.array(), buffer.position(), length).array(), "US-ASCII");
				System.out.println(skill);
				expertiseSkills.add(skill);
			} catch (Exception e) {
				e.printStackTrace();
			}
			buffer.position(buffer.position() + length);

			
		}

		
	}

	@Override
	public IoBuffer serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getExpertiseSkills() {
		return expertiseSkills;
	}

}
