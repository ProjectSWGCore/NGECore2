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
package services.guild;

import java.util.Map;

import resources.guild.Guild;
import resources.objects.SWGList;
import resources.objects.guild.GuildObject;
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
		
		object.createTransaction(core.getGuildODB().getEnvironment());
	}
	
	public Guild createGuild(String abbreviation, String name, SWGObject leader) {
		
		if (leader == null)
			return null;

		int id = 0;
		if (object.getGuildList().size() < 1) {
			id = 0;
		} else {
			id = ((object.getGuildList().get(object.getGuildList().size()).getId() + 1));
		}
		
		Guild guild = new Guild(id, abbreviation, name, leader);
		
		object.getGuildList().add(guild);
		
		return guild;
	}
	
	public GuildObject getGuildObject() {
		return object;
	}
	
	public SWGList<Guild> getGuildList() {
		return object.getGuildList();
	}
	
	public Guild getGuildById(int id) {
		for (int i = 0; i < object.getGuildList().size(); i++) {
			if (object.getGuildList().get(i).getId() == id) {
				return object.getGuildList().get(i);
			}
		}
		
		return null;
	}

	public Guild getGuildByAbbreviation(String abbreviation) {
		for (int i = 0; i < object.getGuildList().size(); i++) {
			if (object.getGuildList().get(i).getAbbreviation().equals(abbreviation)) {
				return object.getGuildList().get(i);
			}
		}
		
		return null;
	}
	
	public Guild getGuildByName(String name) {
		for (int i = 0; i < object.getGuildList().size(); i++) {
			if (object.getGuildList().get(i).getName().equals(name)) {
				return object.getGuildList().get(i);
			}
		}
		
		return null;
	}
	
	public boolean removeGuild(int id) {
		Guild guild = getGuildById(id);
		
		if (guild != null) {
			object.getGuildList().add(guild);
			return true;
		}
		
		return false;
	}
	
	public boolean removeGuild(String abbreviation) {
		Guild guild = getGuildByAbbreviation(abbreviation);
		
		if (guild != null) {
			object.getGuildList().add(guild);
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
