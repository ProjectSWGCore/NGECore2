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

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;


public class GroupObject extends SWGObject {
	
	private Vector<SWGObject> memberList = new Vector<SWGObject>();
	private int memberListUpdateCounter;
	private SWGObject groupLeader;
	private SWGObject lootMaster;
	private short groupLevel;
	private int lootMode;
	
	public GroupObject(long objectId) {
		super(objectId, null, new Point3D(0, 0, 0), new Quaternion(0, 0, 0, 1), "object/group/shared_group_object.iff");
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

	@Override
	public void sendBaselines(Client client) {
		// TODO Auto-generated method stub
		
	}

}
