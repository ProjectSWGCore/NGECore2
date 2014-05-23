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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import resources.common.OutOfBand;
import resources.datatables.DisplayType;
import resources.guild.Guild;
import resources.objects.SWGSet;
import resources.objects.creature.CreatureObject;
import resources.objects.guild.GuildObject;
import resources.objects.intangible.IntangibleObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import services.chat.ChatRoom;
import services.chat.Mail;
import services.sui.SUIService.InputBoxType;
import services.sui.SUIService.ListBoxType;
import services.sui.SUIService.MessageBoxType;
import services.sui.SUIWindow;
import services.sui.SUIWindow.Trigger;
import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import engine.resources.common.Stf;

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
	
	public void joinGuild(Guild guild, CreatureObject joinee, CreatureObject acceptor) {
		PlayerObject ghost = joinee.getPlayerObject();
		if (ghost == null)
			return;
		
		guild.getMembers().add(joinee.getObjectID());
		joinee.setGuildId(guild.getId());
		
		guild.getSponsoredPlayers().remove(joinee.getObjectID());
		
		if (joinee.isInQuadtree())
			core.chatService.joinChatRoom(joinee.getCustomName(), guild.getChatRoomId());
		else
			ghost.addChannel(guild.getChatRoomId());
		
		// Notify guild & player
		if (acceptor != null) {
			guild.sendGuildMail(guild.getName(), "@guildmail:accept_subject", new Stf("@guildmail:accept_text").getStfValue().replace("%TU", acceptor.getCustomName()).replace("%TT", joinee.getCustomName()));
			
			Mail acceptedMail = new Mail();
			acceptedMail.setMailId(core.chatService.generateMailId());
			acceptedMail.setTimeStamp((int) new Date().getTime());
			acceptedMail.setRecieverId(joinee.getObjectID());
			acceptedMail.setStatus(Mail.NEW);
			acceptedMail.setMessage(new Stf("@guildmail:accept_target_text").getStfValue().replace("%TU", acceptor.getCustomName()).replace("%TT", joinee.getCustomName()));
			acceptedMail.setSubject("@guildmail:accept_subject");
			acceptedMail.setSenderName(guild.getName());
	        core.chatService.storePersistentMessage(acceptedMail);
	        
	        if (joinee.isInQuadtree())
	        	core.chatService.sendPersistentMessageHeader(joinee.getClient(), acceptedMail);
		}
		
		// Update association device to guild stats
		TangibleObject datapad = (TangibleObject) joinee.getSlottedObject("datapad");
		
		if (datapad != null) {
			datapad.viewChildren(joinee, true, false, (obj) -> {
				if (obj instanceof IntangibleObject && obj.getTemplate().equals("object/intangible/data_item/shared_guild_stone.iff")) { // shared_guild_stone
					obj.setStringAttribute("guild_name", guild.getName());
					obj.setStringAttribute("guild_abbrev", guild.getAbbreviation());
					obj.setStringAttribute("guild_leader", guild.getLeaderName());
				}
			});
		}
	}
	
	public void handleGuildSponsor(CreatureObject actor) {
	    SUIWindow wndSponsorPlayer = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, "@guild:sponsor_title", "@guild:sponsor_prompt", actor, null, (float) 10, (sponsor, eventType, returnList) -> {
	        
	    	if (eventType != 0)
	    		return;
	    	
	    	String input = returnList.get(0);
	    	
	    	if (input.isEmpty())
	    		return;
	    	
	    	SWGObject pSponsored = core.objectService.getObjectByFirstName(input);
	    	
	    	if (pSponsored == null || !pSponsored.isInQuadtree()) {
	    		actor.sendSystemMessage("@guild:sponsor_not_found", DisplayType.Broadcast);
	    		return;
	    	}
	    	
	    	if (((CreatureObject)pSponsored).getGuildId() != 0) {
	    		actor.sendSystemMessage(OutOfBand.ProsePackage("@guild:sponsor_already_in_guild", "TU", pSponsored.getCustomName()), DisplayType.Broadcast);
	    		return;
	    	}
	    	
	    	Guild invitingGuild = core.guildService.getGuildById(actor.getGuildId());

	    	SUIWindow wndSponsoredConfirm = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_YES_NO, "@guild:sponsor_verify_title", 
	    			new Stf("@guild:sponsor_verify_prompt").getStfValue().replace("%TU", actor.getCustomName()).replace("%TT", invitingGuild.getName()), pSponsored, null, (float) 10);
	    	
	    	wndSponsoredConfirm.addHandler(0, "", Trigger.TRIGGER_OK, new Vector<String>(), (cOwner, cEventType, cReturnList) -> {
	    		
	    		if (cEventType != 0)
	    			return;

	    		Guild guild = core.guildService.getGuildById(actor.getGuildId());
		    	
		    	if (guild == null)
		    		return;
		    	
		    	guild.getSponsoredPlayers().put(cOwner.getObjectID(), cOwner.getCustomName());
		    	
		    	actor.sendSystemMessage(OutOfBand.ProsePackage("@guild:sponsor_self", "TU", cOwner.getCustomName(), "TT", guild.getName()), DisplayType.Broadcast);
		    	((CreatureObject)cOwner).sendSystemMessage(OutOfBand.ProsePackage("@guild:sponsor_target", "TT", guild.getName(), "TU", actor.getCustomName()), DisplayType.Broadcast);
		    	
		    	guild.sendGuildMail(guild.getName(), "@guildmail:sponsor_subject", new Stf("@guild:sponsor_text").getStfValue().replace("%TU", actor.getCustomName()).replace("%TT", cOwner.getCustomName()));
	    	});
	    	core.suiService.openSUIWindow(wndSponsoredConfirm);

	    });
	    core.suiService.openSUIWindow(wndSponsorPlayer);
	}
	
	public void handleManageSponsoredPlayers(CreatureObject actor) {
		Guild guild = getGuildById(actor.getGuildId());
		
		
		if (guild == null)
			return;
		
		Map<Long, String> sponsorships = guild.getSponsoredPlayers();
		
		SUIWindow wndSponsoredPlayers = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@guild:sponsored_title", "@guild:sponsored_prompt", sponsorships, actor, null, 0);
		
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		wndSponsoredPlayers.addHandler(0, "", Trigger.TRIGGER_OK, returnList, (owner, eventType, resultList) -> {
			int index = Integer.parseInt(resultList.get(0));
			long objectId = wndSponsoredPlayers.getObjectIdByIndex(index);
			
			if(objectId == 0)
				return;
			
			SWGObject sponsoredPlayer = core.objectService.getObject(objectId);
			
			if (sponsoredPlayer == null) {
				sponsoredPlayer = core.objectService.getCreatureFromDB(objectId);
				
				if (sponsoredPlayer == null) {
					guild.getSponsoredPlayers().remove(objectId);
					return;
				}
			}
			final SWGObject finalSponseredPlayer = sponsoredPlayer;

			Map<Long, String> options = new HashMap<Long, String>();
			options.put((long) 0, "Accept");
			options.put((long) 1, "Decline");
			options.put((long) 2, "Cancel");
			SUIWindow sponsorOptions = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@guild:sponsored_options_title", 
					new Stf("@guild:sponsored_options_prompt").getStfValue().replace("%TU", sponsoredPlayer.getCustomName()), options, owner, null, 0);
			
			Vector<String> returnList2 = new Vector<String>();
			returnList2.add("List.lstList:SelectedRow");
			
			sponsorOptions.addHandler(0, "", Trigger.TRIGGER_OK, returnList2, (owner2, eventType2, resultList2) -> {
				int selectedOption = Integer.parseInt(resultList2.get(0));
				
				Guild gOwner = getGuildById(((CreatureObject)owner2).getGuildId());
				CreatureObject sponsoredCreo = (CreatureObject) finalSponseredPlayer;
				
				switch(selectedOption) {
					case 0: // Accept

						if (sponsoredCreo.isInQuadtree())
							sponsoredCreo.sendSystemMessage(OutOfBand.ProsePackage("@guild:sponsor_accept", "TU", owner2.getCustomName()), DisplayType.Broadcast);
						
						joinGuild(gOwner, sponsoredCreo, (CreatureObject) owner2);
						break;
					case 1: // Decline
						sponsoredCreo.sendSystemMessage(OutOfBand.ProsePackage("@guild:sponsor_decline", "TU", owner2.getCustomName()), DisplayType.Broadcast);
						
						handleGuildDeclineSponsorship(gOwner, (CreatureObject) owner2, sponsoredCreo);
						break;
					case 2: // Cancel
						break;
					default:
						break;
				}
			});
			
			core.suiService.openSUIWindow(sponsorOptions);
		});
		core.suiService.openSUIWindow(wndSponsoredPlayers);
	}
	
	public void handleGuildDeclineSponsorship(Guild guild, CreatureObject actor, CreatureObject sponsoree) {
		guild.getSponsoredPlayers().remove(sponsoree.getObjectID());
		
		guild.sendGuildMail(guild.getName(), "@guildmail:decline_subject", new Stf("@guildmail:decline_text").getStfValue().replace("%TU", actor.getCustomName()).replace("%TO", sponsoree.getCustomName()));
		
		Mail declinedMail = new Mail();
        declinedMail.setMailId(core.chatService.generateMailId());
        declinedMail.setTimeStamp((int) new Date().getTime());
        declinedMail.setRecieverId(sponsoree.getObjectID());
        declinedMail.setStatus(Mail.NEW);
        declinedMail.setMessage(new Stf("@guildmail:decline_target_text").getStfValue().replace("%TU", actor.getCustomName()).replace("%TT", guild.getName()));
        declinedMail.setSubject("@guildmail:decline_subject");
        declinedMail.setSenderName(guild.getName());
        core.chatService.storePersistentMessage(declinedMail);
        
        if (sponsoree.isInQuadtree())
        	core.chatService.sendPersistentMessageHeader(sponsoree.getClient(), declinedMail);
	}
	
	public void sendMailToGuild(String sender, String subject, String message, int guildId) {
		Guild guild = getGuildById(guildId);
		
		if (guild == null)
			return;
		
		guild.sendGuildMail(sender, subject, message);
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
