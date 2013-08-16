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

import java.util.Map;

import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;

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
		member.setInviteSenderId(member.getObjectId());
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

	
	

}
