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
package resources.z.exp.group;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.Persistent;

import engine.resources.objects.SWGObject;

import resources.objects.Delta;

@Persistent
public class Member extends Delta {
	
	private SWGObject member;
	
	public Member(SWGObject member) {
		this.member = member;
	}
	
	public Member() {
		
	}
	
	public SWGObject getMember() {
		synchronized(objectMutex) {
			return member;
		}
	}
	
	public long getObjectId() {
		return member.getObjectID();
	}
	
	public String getCustomName() {
		return member.getCustomName();
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = bufferPool.allocate(8 + getAsciiString(getCustomName()).length, false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.putLong(getObjectId());
			buffer.put(getAsciiString(getCustomName()));
			return buffer.array();
		}
	}
	
}
