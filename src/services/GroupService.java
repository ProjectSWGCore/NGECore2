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
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import resources.objects.Buff;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import services.chat.ChatRoom;
import main.NGECore;
import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class GroupService implements INetworkDispatch {
	
	private NGECore core;

	public GroupService(NGECore core) {
		this.core = core;
		core.commandService.registerCommand("invite");
		core.commandService.registerCommand("join");
		core.commandService.registerCommand("disband");
		core.commandService.registerCommand("decline");
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	}

	@Override
	public void shutdown() {
		
	}
	
	public void handleGroupInvite(CreatureObject leader, CreatureObject member) {
		
		Client leaderClient = leader.getClient();
		Client memberClient = member.getClient();
		
		if(leaderClient == null || memberClient == null)
			return;
		
		long leaderGroupId = leader.getGroupId();
		long memberGroupId = member.getGroupId();
		
		if(leader == member) {
			leader.sendSystemMessage("@group:invite_no_target_self", (byte) 0);
			return;
		}
		
		if(memberGroupId != 0) {
			leader.sendSystemMessage(member.getCustomName() + " is already in a group.", (byte) 0);
			return;
		}
		
		
		if(leaderGroupId != 0) {
			
			GroupObject group = (GroupObject) core.objectService.getObject(leaderGroupId);
			
			if(group.getGroupLeader() != leader) {
				leader.sendSystemMessage("@group:must_be_leader", (byte) 0);
				return;
			}
			
			if(group.getMemberList().size() >= 8) {
				leader.sendSystemMessage("@group:full", (byte) 0);
				return;
			}
			
		}
		
		if(member.getInviteSenderId() != 0 && member.getInviteSenderId() != leader.getObjectID()) {
			leader.sendSystemMessage(member.getCustomName() + " is considering joining another group.", (byte) 0);
			return;
		}
		
		if(member.getInviteSenderId() != 0 && member.getInviteSenderId() == leader.getObjectID()) {
			leader.sendSystemMessage(member.getCustomName() + " has already been invited to join your group.", (byte) 0);
			return;
		}

		leader.sendSystemMessage("You invite " + member.getCustomName() + " to join the group.", (byte) 0);
		member.setInviteCounter(member.getInviteCounter() + 1);
		member.setInviteSenderId(leader.getObjectId());
		member.setInviteSenderName(leader.getCustomName());
		
		member.updateGroupInviteInfo();
		
	}
	
	public void handleGroupDecline(CreatureObject invited) {
		
		CreatureObject leader = (CreatureObject) core.objectService.getObject(invited.getInviteSenderId());
		
		invited.setInviteCounter(invited.getInviteCounter() + 1);
		invited.setInviteSenderId(0);
		invited.setInviteSenderName("");
		
		invited.sendSystemMessage("You decline to join " + leader.getCustomName() + "'s group.", (byte) 0);
		invited.updateGroupInviteInfo();
		
		leader.sendSystemMessage(invited.getCustomName() + " declines to join your group.", (byte) 0);
		
	}

	public void handleGroupJoin(CreatureObject invited) {
		
		
		if(invited.getInviteSenderId() == 0 || invited.getGroupId() != 0)
			return;
		
		CreatureObject leader = (CreatureObject) core.objectService.getObject(invited.getInviteSenderId());
		
		if(leader == null)
			return;

		if(leader.getGroupId() == 0) {
			
			GroupObject group = (GroupObject) core.objectService.createObject("object/group/shared_group_object.iff", leader.getPlanet());
			group.setGroupLeader(leader);
			group.getMemberList().add(leader);
			group.getMemberList().add(invited);
			leader.makeAware(group);
			leader.setGroupId(group.getObjectID());
			invited.makeAware(group);
			invited.setGroupId(group.getObjectID());
			addGroupBuffsToMember(group, leader);
			addGroupBuffsToMember(group, invited);
			
			//ChatRoom groupChat = core.chatService.createChatRoom("GroupChat", "group." + group.getObjectID(), leader.getCustomName(), true);
			//group.setChatRoomId(groupChat.getRoomId());

			//core.chatService.joinChatRoom(leader, groupChat.getRoomId());
			//core.chatService.joinChatRoom(invited, groupChat.getRoomId());
			
			return;
			
		}
		
		GroupObject group = (GroupObject) core.objectService.getObject(leader.getGroupId());
		
		if(group != null && group.getMemberList().size() < 8) {
			
			group.addMember(invited);
			invited.makeAware(group);
			invited.setGroupId(group.getObjectID());	
			invited.sendSystemMessage("@group:joined_self", (byte) 0);
			addGroupBuffsToMember(group, invited);
			
		} else if(group.getMemberList().size() >= 8) {
			
			invited.sendSystemMessage("@group:full", (byte) 0);
			invited.setInviteCounter(invited.getInviteCounter() + 1);
			invited.setInviteSenderId(0);
			invited.setInviteSenderName("");
			invited.updateGroupInviteInfo();
			
			return;
			
		}
		
	}
	
	public void addGroupBuffsToMember(GroupObject group, CreatureObject member) {
		
		// loop until we find a member other than ourself
		for(SWGObject otherMember : group.getMemberList()) {
			if(otherMember != member) {
				for(Buff buff : ((CreatureObject) otherMember).getBuffList().get()) {
					if(buff.isGroupBuff() && otherMember.getPlanet() == member.getPlanet() && otherMember.getPosition().getDistance2D(member.getWorldPosition()) <= 80) {
						core.buffService.addBuffToCreature((CreatureObject) otherMember, buff.getBuffName(), member);
					}
				}
				return;
			}
		}
		
	}
	
	public void removeGroupBuffs(CreatureObject member) {
		for(Buff buff : new ArrayList<Buff>(member.getBuffList().get())) {
			if(buff.isGroupBuff() && buff.getGroupBufferId() != member.getObjectID()) {
				core.buffService.removeBuffFromCreature(member, buff);
			}
		}
	}
	
	public void handleGroupDisband(CreatureObject creature, boolean destroy) {
		
		if(creature.getGroupId() == 0)
			return;
		
		SWGObject object = core.objectService.getObject(creature.getGroupId());

		if(object == null || !(object instanceof GroupObject))
			return;
		
		GroupObject group = (GroupObject) object;
		
		List<SWGObject> memberList = new ArrayList<SWGObject>(group.getMemberList());
		
		if(group.getGroupLeader() != creature || !destroy || memberList.size() > 2) {
			
			group.removeMember(creature);
			creature.setInviteCounter(creature.getInviteCounter() + 1);
			creature.setInviteSenderId(0);
			creature.setInviteSenderName("");
			creature.updateGroupInviteInfo();
			creature.setGroupId(0);
			creature.makeUnaware(group);
			creature.sendSystemMessage("You have left the group.", (byte) 0);

			for(SWGObject member : memberList) {
				
				CreatureObject creature2 = (CreatureObject) member;
				creature2.sendSystemMessage(creature.getCustomName() + " has left the group.", (byte) 0);
				
			}
			
			removeGroupBuffs(creature);
			
			if (group.getMemberList().size() == 0) // ensure that there are no empty groups just incase..
				core.objectService.destroyObject(group.getObjectID());

		} else {
			
			for(SWGObject member : memberList) {
				
				CreatureObject creature2 = (CreatureObject) member;
				
				creature2.setInviteCounter(creature2.getInviteCounter() + 1);
				creature2.setInviteSenderId(0);
				creature2.setInviteSenderName("");
				creature2.updateGroupInviteInfo();
				creature2.setGroupId(0);
				
				creature2.makeUnaware(group);
				
				creature2.sendSystemMessage("The group has been disbanded.", (byte) 0);
				
				removeGroupBuffs((CreatureObject) member);
				
			}
			core.objectService.destroyObject(group.getObjectID());
		}
	}
	
	public void handleGroupDisband(CreatureObject creature) {
		handleGroupDisband(creature, true);
	}
}
