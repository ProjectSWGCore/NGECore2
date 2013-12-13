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
package resources.z.exp.objects.group;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.NotPersistent;

import resources.z.exp.group.Member;
import resources.z.exp.group.MemberInfo;
import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.SWGList;
import resources.z.exp.objects.creature.CreatureObject;
import resources.z.exp.objects.universe.UniverseObject;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class GroupObject extends UniverseObject {
	
	@NotPersistent
	private GroupMessageBuilder messageBuilder;
	
	public GroupObject(long objectId) {
		super(objectId, null, new Point3D(0, 0, 0), new Quaternion(0, 0, 0, 1), "object/group/shared_group_object.iff");
	}
	
	public GroupObject() {
		super();
	}
	
	@Override
	public void initializeBaselines() {
		super.initializeBaselines();
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("memberList", new SWGList<Member>(this, 6, 2, false));
		baseline.put("memberInfoList", new SWGList<MemberInfo>(this, 6, 3, false));
		baseline.put("4", "");
		baseline.put("groupLevel", (short) 0);
		baseline.put("6", 0);
		baseline.put("groupLeader", null);
		baseline.put("lootMaster", null);
		baseline.put("lootMode", 0);
		return baseline;
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Member> getMemberList() {
		return (SWGList<Member>) baseline6.get("memberList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<MemberInfo> getMemberInfoList() {
		return (SWGList<MemberInfo>) baseline6.get("memberInfoList");
	}
	
	public void addMember(SWGObject member) {
		if (member instanceof CreatureObject && member.getClient() != null) {
			SWGList<Member> memberList = getMemberList();
			SWGList<MemberInfo> memberInfoList = getMemberInfoList();
			
			if (memberList.size() >= 8) { 
				((CreatureObject) member).sendSystemMessage("@group:join_full", (byte) 0);
			} else {
				Member newMember = new Member(member);
				
				if (memberList.add(newMember)) {
					memberInfoList.add(new MemberInfo(memberList.indexOf(newMember), 0));
					
					if (((CreatureObject) member).getLevel() > getGroupLevel()) {
						setGroupLevel(((CreatureObject) member).getLevel());
					}
				}
			}
		}
	}
	
	public void removeMember(SWGObject member) {
		if (member instanceof CreatureObject && member.getClient() != null) {
			SWGList<Member> memberList = getMemberList();
			SWGList<MemberInfo> memberInfoList = getMemberInfoList();
			
			if (!(memberList.size() <= 0)) {
				int index = memberList.indexOf(member);
				
				if (memberList.remove(member)) {
					memberInfoList.remove(index);
					
					synchronized(objectMutex) {
						for (int i = 0; i < memberInfoList.size(); i++) {
							if (memberInfoList.get(i).getMemberId() != i) {
								memberInfoList.set(i, memberInfoList.get(i).setMemberId(i));
							}
						}
					}
				}
				
				short maxLevel = 0;
				
				for (Member memberObject : getMemberList()) {
					if (memberObject.getMember() instanceof CreatureObject) {
						CreatureObject creature = (CreatureObject) memberObject.getMember();
						
						if (creature.getLevel() > maxLevel) {
							maxLevel = creature.getLevel();
						}
					}
				}
				
				if (maxLevel != getGroupLevel()) {
					setGroupLevel(maxLevel);
				}
			}
		}
	}
	
	public boolean setMemberInfo(SWGObject object, long info) {
		if (object instanceof CreatureObject && object.getClient() != null) {
			for (Member member : getMemberList()) {
				if (member.getMember().equals(member)) {
					int id = getMemberList().indexOf(member);
					getMemberInfoList().set(id, new MemberInfo(info, id));
					return true;
				}
			}
		}
		
		return false;
	}
	
	public short getGroupLevel() {
		synchronized(objectMutex) {
			return (short) baseline6.get("groupLevel");
		}
	}
	
	public void setGroupLevel(short groupLevel) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("groupLevel", groupLevel);
		}
		
		notifyObservers(buffer, false);
	}
	
	public SWGObject getGroupLeader() {
		synchronized(objectMutex) {
			return (SWGObject) baseline6.get("groupLeader");
		}
	}
	
	public void setGroupLeader(SWGObject groupLeader) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("groupLeader", groupLeader);
		}
		
		notifyObservers(buffer, false);
	}
	
	public SWGObject getLootMaster() {
		synchronized(objectMutex) {
			return (SWGObject) baseline6.get("lootMaster");
		}
	}
	
	public void setLootMaster(SWGObject lootMaster) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("lootMaster", lootMaster);
		}
		
		notifyObservers(buffer, false);
	}
	
	public int getLootMode() {
		synchronized(objectMutex) {
			return (int) baseline6.get("lootMode");
		}
	}
	
	public void setLootMode(int lootMode) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("lootMode", lootMode);
		}
		
		notifyObservers(buffer, false);
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, false);
	}
	
	@Override
	public GroupMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new GroupMessageBuilder();
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(baseline3.getBaseline());
			destination.getSession().write(baseline6.getBaseline());
		}
	}
	
}
