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
package resources.objects.group;

import java.nio.ByteOrder;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.Builder;
import engine.resources.objects.SWGObject;
import resources.objects.universe.UniverseMessageBuilder;

public class GroupMessageBuilder extends UniverseMessageBuilder {
	
	public GroupMessageBuilder(GroupObject object) {
		super(object);
	}
	
	public GroupMessageBuilder() {
		super();
	}
	
	public IoBuffer buildBaseline3() {
		
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);

		buffer.putShort((short) 4);	// Obj Operand Count
		buffer.putFloat(1);			// Complexity
		
		buffer.put(getAsciiString("string_id_table"));
		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putShort((short) 0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.put((byte) 0);
		
		int size = buffer.position();
		
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("GRUP", (byte) 3, buffer, size);
		
		return buffer;

	}
	
	public IoBuffer buildBaseline6() {
		
		GroupObject group = (GroupObject) object;
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		buffer.putShort((short) 0x0B); // Obj Operand Count
		buffer.putInt(0x43);		   
		
		buffer.put(getAsciiString("string_id_table"));
		
		buffer.putInt(0);
		buffer.putShort((short) 0);

		buffer.putInt(group.getMemberList().size());
		buffer.putInt(group.getMemberListUpdateCounter());
		
		synchronized(group.getMemberList()) {
			for(SWGObject obj : group.getMemberList()) {
				buffer.putLong(obj.getObjectID());
				buffer.put(getAsciiString(obj.getCustomName()));
			}
		}
		
		buffer.putInt(0);
		buffer.putInt(0);

		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(1);
		buffer.putShort((short) 0);
		buffer.putShort((short) 0x5A);
		buffer.putInt(0);
		buffer.putLong(group.getGroupLeader().getObjectID());
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);


		int size = buffer.position();
		
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("GRUP", (byte) 6, buffer, size);
		
		return buffer;

	}
	
	public IoBuffer buildAddMemberDelta(SWGObject member) {
		
		GroupObject group = (GroupObject) object;
		
		IoBuffer buffer = bufferPool.allocate(21 + member.getCustomName().length(), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(group.getMemberListUpdateCounter());
		
		buffer.put((byte) 1);
		buffer.putShort((short) group.getMemberList().indexOf(member));
		buffer.putLong(member.getObjectID());
		buffer.put(getAsciiString(member.getCustomName()));

		int size = buffer.position();
		buffer.flip();
		return buffer = createDelta("GRUP", (byte) 6, (short) 1, (short) 2, buffer, size + 4);

	}
	
	public IoBuffer buildRemoveMemberDelta(SWGObject member) {
		
		GroupObject group = (GroupObject) object;
		
		IoBuffer buffer = bufferPool.allocate(11, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(group.getMemberListUpdateCounter());
		
		buffer.put((byte) 0);
		buffer.putShort((short) group.getMemberList().indexOf(member));

		int size = buffer.position();
		buffer.flip();
		return buffer = createDelta("GRUP", (byte) 6, (short) 1, (short) 2, buffer, size + 4);
		
	}
	
	@Override
	public void buildBaseline3(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline3(baselineBuilders, deltaBuilders);
	}
	
	@Override
	public void buildBaseline6(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		super.buildBaseline6(baselineBuilders, deltaBuilders);
	}
	
}
