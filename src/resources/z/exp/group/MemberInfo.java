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

import resources.objects.Delta;

@Persistent
public class MemberInfo extends Delta {
	
	private long info;
	private int memberId;
	
	public MemberInfo(long info, int memberId) {
		this.info = info;
		this.memberId = memberId;
	}
	
	public MemberInfo() {
		
	}
	
	public long getInfo() {
		synchronized(objectMutex) {
			return info;
		}
	}
	
	public MemberInfo setInfo(long info) {
		synchronized(objectMutex) {
			this.info = info;
			return this;
		}
	}
	
	public int getMemberId() {
		synchronized(objectMutex) {
			return memberId;
		}
	}
	
	public MemberInfo setMemberId(int memberId) {
		synchronized(objectMutex) {
			this.memberId = memberId;
			return this;
		}
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = bufferPool.allocate(12, false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.putLong(info);
			buffer.putInt(memberId);
			return buffer.array();
		}
	}
	
}
