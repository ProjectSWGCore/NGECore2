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

import resources.guild.Guild;
import resources.objects.SWGSet;
import resources.objects.creature.CreatureObject;
import resources.objects.guild.GuildObject;
import resources.objects.player.PlayerObject;
import services.chat.ChatRoom;
import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class GuildService implements INetworkDispatch {
	
	private NGECore core;
	private GuildObject object;
	
	public GuildService(NGECore core) {
		this.core = core;
		
		for (SWGObject swgObject : core.objectService.getObjectList().values()) {
			if (swgObject.getTemplate().equals("object/guild/shared_guild_object.iff") && swgObject instanceof GuildObject) {
				object = (GuildObject) swgObject;
			}
		}
		
		if (object == null) {
			object = (GuildObject) this.core.objectService.createObject("object/guild/shared_guild_object.iff", core.terrainService.getPlanetList().get(0));
		}
		
	}
	
	public Guild createGuild(String abbreviation, String name, SWGObject leader) {
		
		if (leader == null)
			return null;
		
		int listSize = object.getGuildList().size();
		Guild guild = new Guild((listSize == 0 ? 1 : listSize + 1), abbreviation, name, leader);
		
		core.chatService.createChatRoom("", "guild." + guild.getId(), leader.getCustomName(), false, true, false);
		ChatRoom guildChat = core.chatService.createChatRoom("", "guild." + guild.getId() + ".GuildChat", leader.getCustomName(), false, true, false);
		guild.setChatRoomId(guildChat.getRoomId());
		
		object.getGuildList().add(guild);
		return guild;
	}
	
	public void joinGuild(Guild guild, CreatureObject joinee) {
		PlayerObject ghost = joinee.getPlayerObject();
		if (ghost == null)
			return;
		
		guild.getMembers().add(joinee.getObjectID());
		joinee.setGuildId(guild.getId());
		
		core.chatService.joinChatRoom(joinee.getCustomName(), guild.getChatRoomId());
		// TODO: Send new member guild mail
	}
	
	public GuildObject getGuildObject() {
		return object;
	}
	
	public SWGSet<Guild> getGuildList() {
		return object.getGuildList();
	}
	
	public Guild getGuildById(int id) {
		return object.getGuildList().stream().filter(g -> g.getId() == id).findFirst().get();
	}

	public Guild getGuildByAbbreviation(String abbreviation) {
		return object.getGuildList().stream().filter(g -> g.getAbbreviation().equals(abbreviation)).findFirst().get();
	}
	
	public Guild getGuildByName(String name) {
		return object.getGuildList().stream().filter(g -> g.getName().equals(name)).findFirst().get();
	}
	
	public boolean removeGuild(int id) {
		Guild guild = getGuildById(id);
		
		if (guild != null) {
			object.getGuildList().remove(guild);
			return true;
		}
		
		return false;
	}
	
	public boolean removeGuild(String abbreviation) {
		Guild guild = getGuildByAbbreviation(abbreviation);
		
		if (guild != null) {
			object.getGuildList().remove(guild);
			return true;
		}
		
		return false;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	}

	@Override
	public void shutdown() {
		
	}

}
