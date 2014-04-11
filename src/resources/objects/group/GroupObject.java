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

import java.util.Vector;

import resources.objects.universe.UniverseObject;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class GroupObject extends UniverseObject {
	
	private Vector<SWGObject> memberList = new Vector<SWGObject>();
	private int memberListUpdateCounter;
	private SWGObject groupLeader;
	private SWGObject lootMaster;
	private short groupLevel;
	private int lootMode;
	private GroupMessageBuilder messageBuilder;
	private int chatRoomId;
	
	public static int FREE_FOR_ALL  = 0;
	public static int MASTER_LOOTER = 1;
	public static int LOTTERY       = 2;
	
	
	public GroupObject(long objectId) {
		super(objectId, null, new Point3D(0, 0, 0), new Quaternion(1, 0, 0, 0), "object/group/shared_group_object.iff");
		messageBuilder = new GroupMessageBuilder(this);
	}

	public Vector<SWGObject> getMemberList() {
		return memberList;
	}

	public int getMemberListUpdateCounter() {
		synchronized(objectMutex) {
			return memberListUpdateCounter;
		}
	}

	public void setMemberListUpdateCounter(int memberListUpdateCounter) {
		synchronized(objectMutex) {
			this.memberListUpdateCounter = memberListUpdateCounter;
		}
	}

	public SWGObject getGroupLeader() {
		synchronized(objectMutex) {
			return groupLeader;
		}
	}

	public void setGroupLeader(SWGObject groupLeader) {
		synchronized(objectMutex) {
			this.groupLeader = groupLeader;
		}
	}

	public SWGObject getLootMaster() {
		synchronized(objectMutex) {
			return lootMaster;
		}
	}

	public void setLootMaster(SWGObject lootMaster) {
		synchronized(objectMutex) {
			this.lootMaster = lootMaster;
		}
	}

	public short getGroupLevel() {
		synchronized(objectMutex) {
			return groupLevel;
		}
	}

	public void setGroupLevel(short groupLevel) {
		synchronized(objectMutex) {
			this.groupLevel = groupLevel;
		}
	}

	public int getLootMode() {
		synchronized(objectMutex) {
			return lootMode;
		}
	}

	public void setLootMode(int lootMode) {
		synchronized(objectMutex) {
			this.lootMode = lootMode;
		}
	}

	public int getChatRoomId() {
		synchronized(objectMutex) {
			return chatRoomId;
		}
	}

	public void setChatRoomId(int chatRoomId) {
		synchronized(objectMutex) {
			this.chatRoomId = chatRoomId;
		}
	}

	public void addMember(SWGObject member) {
		
		if(memberList.size() >= 8 || member.getClient() == null)
			return;
		
		memberList.add(member);
		
		setMemberListUpdateCounter(getMemberListUpdateCounter() + 1);
		notifyObservers(messageBuilder.buildAddMemberDelta(member), false);
		
	}
	
	public void removeMember(SWGObject member) {
		
		if(memberList.size() <= 0 || member.getClient() == null)
			return;
		
		
		setMemberListUpdateCounter(getMemberListUpdateCounter() + 1);
		notifyObservers(messageBuilder.buildRemoveMemberDelta(member), false);
		
		memberList.remove(member);
		
	}


	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null) {
			System.out.println("NULL session");
			return;
		}
		
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
		
	}
}
