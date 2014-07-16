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
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import protocol.swg.chat.ChatRoomMessage;
import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.datatables.DisplayType;
import resources.guild.Guild;
import resources.guild.GuildMember;
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
import services.sui.SUITableItem;
import services.sui.SUIWindow;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import main.NGECore;
import engine.clientdata.StfTable;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import engine.resources.common.Stf;

public class GuildService implements INetworkDispatch {
	
	private NGECore core;
	private GuildObject object;
	private Map<Long, String> guildRanks = new ConcurrentHashMap<Long, String>();
	
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
		
		try {
			StfTable stf = new StfTable("clientdata/string/en/guild_rank_title.stf");
			
			for (int s = 1; s < stf.getRowCount(); s++) {
				String rank = stf.getStringById(s).getKey();
				
				if (rank != null && rank != "") {
					guildRanks.put((long) s, "@guild_rank_title:" + rank);
				}
			}
			
        } catch (Exception e) {
                e.printStackTrace();
        }
		
	}
	
	public Guild createGuild(String abbreviation, String name, CreatureObject leader) {
		
		if (leader == null)
			return null;
		
		int listSize = object.getGuildList().size();
		Guild guild = new Guild((listSize == 0 ? 1 : listSize + 1), abbreviation, name, leader);
		
		core.chatService.createChatRoom("", "guild." + guild.getId(), leader.getCustomName(), false, true, false);
		ChatRoom guildChat = core.chatService.createChatRoom("", "guild." + guild.getId() + ".GuildChat", leader.getCustomName(), false, true, false);
		guild.setChatRoomId(guildChat.getRoomId());
		
		object.getGuilds().put(guild.getId(), guild);
		object.getGuildList().add(guild.getString());
		
		GuildMember member = joinGuild(guild, leader, null);
		member.giveAllPermissions();
		return guild;
	}
	
	public GuildMember joinGuild(Guild guild, CreatureObject joinee, CreatureObject acceptor) {
		GuildMember member = null;
		PlayerObject ghost = joinee.getPlayerObject();
		if (ghost == null)
			return member;
		
		member = guild.addMember(joinee.getObjectID());
		
		joinee.setGuildId(guild.getId());
		
		guild.getSponsoredPlayers().remove(joinee.getObjectID());
		
		if (joinee.isInQuadtree())
			core.chatService.joinChatRoom(joinee.getCustomName(), guild.getChatRoomId());
		else
			ghost.addChannel(guild.getChatRoomId());
		
		// Notify guild & player
		if (acceptor != null) {
			guild.sendGuildMail(guild.getName(), "@guildmail:accept_subject", new ProsePackage("@guildmail:accept_text", "TU", acceptor.getCustomName(), "TT", joinee.getCustomName()));
			
			Mail acceptedMail = new Mail();
			acceptedMail.setMailId(core.chatService.generateMailId());
			acceptedMail.setTimeStamp((int) new Date().getTime());
			acceptedMail.setRecieverId(joinee.getObjectID());
			acceptedMail.setStatus(Mail.NEW);
			acceptedMail.addProseAttachment(new ProsePackage("@guildmail:accept_target_text", "TU", acceptor.getCustomName(), "TT", joinee.getCustomName()));
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
		member.setName(joinee.getCustomName());
		member.setLevel(joinee.getLevel());
		if (joinee.getPlayerObject() != null) {
			member.setProfession(core.playerService.getFormalProfessionName(joinee.getPlayerObject().getProfession()));
		}
		
		return member;
	}
	
	public void changeGuildLeader(Guild guild, CreatureObject formerLeaderCreo, CreatureObject newLeaderCreo, boolean elected) {
		GuildMember formerLeader = guild.getMember(formerLeaderCreo.getObjectID());
		
		if (formerLeader == null)
			return;
		
		GuildMember newLeader = guild.getMember(newLeaderCreo.getObjectID());
		
		if (newLeader == null)
			return;
		
		formerLeader.removeAllPermissions();
		newLeader.giveAllPermissions();
		
		// TODO: Handling of guild PA Halls
		
		guild.setLeader(newLeaderCreo.getObjectID());
		guild.setLeaderName(newLeaderCreo.getCustomName());
		
		if (elected) {
			// TODO: Guild leader elections
		} else {
			formerLeaderCreo.sendSystemMessage("@guild:ml_success", DisplayType.Broadcast);
			newLeaderCreo.sendSystemMessage("@guild:ml_you_are_leader", DisplayType.Broadcast);
			guild.sendGuildMail(guild.getName(), "@guildmail:leaderchange_subject", new ProsePackage("@guildmail:leaderchange_text", "TU", newLeaderCreo.getCustomName()));
		}
		
	}
	
	public void handleTransferLeadership(CreatureObject actor, Guild guild) {

		SUIWindow transferWindow = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, "@guild:make_leader_t", "@guild:make_leader_d", actor, null, 0, 
				(owner, eventType, returnList) -> {
					String name = returnList.get(0);

					if (name == "" || name == " ")
						return;
					
					SWGObject transferTarget = core.objectService.getObjectByFirstName(name); //chatService.getObjectByFirstName(name);
					
					if (transferTarget == null) {
						actor.sendSystemMessage("@guild:ml_not_loaded", DisplayType.Broadcast);
						return;
					}
					
					if (!guild.getMembers().containsKey(transferTarget.getObjectID())) {
						actor.sendSystemMessage("@guild:ml_fail", DisplayType.Broadcast);
						return;
					}
					
					if (actor.getPlanetId() != transferTarget.getPlanetId() || actor.getWorldPosition().getDistance2D(transferTarget.getWorldPosition()) > (float) 10) {
						actor.sendSystemMessage("@guild:ml_not_loaded", DisplayType.Broadcast);
						return;
					}
					
					SUIWindow confirmTransferWindow = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_YES_NO, "@guild:make_leader_t", "@guild:make_leader_p", transferTarget, null, 0, (owner2, eventType2, returnList2) -> {
						changeGuildLeader(guild, actor, (CreatureObject) transferTarget, false);
					});
					core.suiService.openSUIWindow(confirmTransferWindow);
					
				});
		
		core.suiService.openSUIWindow(transferWindow);
	}
	
	public void handleCreateGuildName(CreatureObject actor, SWGObject creationSource) {
		if (actor.getGuildId() != 0) { actor.sendSystemMessage("@guild:create_fail_in_guild", DisplayType.Broadcast); return; }
		
		SUIWindow window = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, "@guild:create_name_title", "@guild:create_name_prompt", actor, null, 0, 
				(owner, eventType, returnList) -> { 
					String guildName = returnList.get(0); // TODO: Character checks
					
					if (guildName.length() > 24) { actor.sendSystemMessage("@guild:create_fail_name_bad_length", DisplayType.Broadcast); handleCreateGuildName(actor, creationSource); return;}
					if (getGuildByName(guildName) != null) { actor.sendSystemMessage("@guild:create_fail_name_in_use", DisplayType.Broadcast); handleCreateGuildName(actor, creationSource); return;}
					
					handleCreateGuildAbbrev(actor, guildName, creationSource);
				});
        window.setProperty("txtInput:MaxLength", "24");
        window.setProperty("txtInput:NumericInteger", "False");
        
        core.suiService.openSUIWindow(window);
	}
	
	public void handleEnableGuildElections(Guild guild) {
		guild.setElectionsEnabled(true);
		guild.setElectionResultsDate(System.currentTimeMillis() + (12095 * 100000)); // 2 weeks
		
		Map<Long, Integer> candidates = new HashMap<Long, Integer>();
		candidates.put(Long.valueOf(guild.getLeader()), 1);
		guild.setLeaderCandidates(candidates);
		
		GuildMember leader = guild.getMember(guild.getLeader());
		
		leader.setVotedId(guild.getLeader());

		guild.sendGuildMail(guild.getName(), "@guild:open_elections_email_subject", "@guild:open_elections_email_body");
	}
	
	public void handleViewElectionStandings(CreatureObject actor, Guild guild) {
		
		Map<Long, Integer> candidates = guild.getLeaderCandidates();
		
		if (candidates == null || candidates.size() <= 1) {
			actor.sendSystemMessage("@guild:vote_no_candidates", DisplayType.Broadcast);
			return;
		}
		
		SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@guild:leader_standings_t", "@guild:leader_standings_d", new TreeMap<Long, String>(), actor, null, 0);
		
		candidates.keySet().stream().forEach(candidateId -> {
			if (candidateId == guild.getLeader()) {
				window.addListBoxMenuItem("Incumbent: " + guild.getLeaderName() + " -- Votes: " + String.valueOf(candidates.get(candidateId)), candidateId);
			} else {
				GuildMember candidate = guild.getMember(candidateId);
				
				window.addListBoxMenuItem("Challenger: " + candidate.getName() + " -- Votes: " + String.valueOf(candidates.get(candidateId)), candidateId);
			}

		});
		core.suiService.openSUIWindow(window);
	}
	
	public void handleRunForLeader(CreatureObject actor, Guild guild) {
		if (guild.isRunningForLeader(actor.getObjectID())) {
			actor.sendSystemMessage("@guild:vote_register_dupe", DisplayType.Broadcast);
			return;
		} else guild.getLeaderCandidates().put(actor.getObjectID(), 1);

		actor.sendSystemMessage("@guild:vote_register_congrats", DisplayType.Broadcast);
	}
	
	public void handleUnregisterForLeader(CreatureObject actor, Guild guild) {
		if (!guild.isRunningForLeader(actor.getObjectID())) {
			return;
		} else guild.getLeaderCandidates().remove(actor.getObjectID());

		actor.sendSystemMessage("@guild:vote_unregistered", DisplayType.Broadcast);
	}
	
	public void handleVoteForLeader(CreatureObject actor, Guild guild) {
		
		SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@guild:leader_vote_t", "@guild:leader_vote_d", new TreeMap<Long, String>(), actor, null, 0);

		Map<Long, Integer> candidates = guild.getLeaderCandidates();
		for (Long candidateId : candidates.keySet()) {
			if (candidateId == guild.getLeader()) {
				window.addListBoxMenuItem("Incumbent: " + guild.getLeaderName() + " -- Votes: " + String.valueOf(candidates.get(candidateId)), candidateId);
			} else {
				GuildMember candidate = guild.getMember(candidateId);
				
				window.addListBoxMenuItem("Challenger: " + candidate.getName() + " -- Votes: " + String.valueOf(candidates.get(candidateId)), candidateId);
			}
		}

		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, (owner, eventType, resultList) -> {

			int index = Integer.parseInt(resultList.get(0));
			
			long voteId = window.getObjectIdByIndex(index);
			
			if(voteId == 0)
				return;
			
			GuildMember voter = guild.getMember(actor.getObjectID());
			if (voter == null) 
				return;
			
			if (voter.getVotedId() == voteId) {
				if (guild.getLeaderCandidates().containsKey(voteId))
					guild.getLeaderCandidates().put(voteId, guild.getLeaderCandidates().get(voteId) - 1);
				
				actor.sendSystemMessage("@guild:vote_abstain", DisplayType.Broadcast);
				return;
			}
			
			// remove prior votes
			if (voter.getVotedId() != 0 && guild.getLeaderCandidates().containsKey(voter.getVotedId()))
				guild.getLeaderCandidates().put(voter.getVotedId(), guild.getLeaderCandidates().get(voter.getVotedId()) - 1);
			
			voter.setVotedId(voteId);
			guild.getLeaderCandidates().put(voteId, guild.getLeaderCandidates().get(voteId) + 1);
			actor.sendSystemMessage(OutOfBand.ProsePackage("@guild:vote_placed", "TO", guild.getMember(voteId).getName()), DisplayType.Broadcast);
			
		});
		
		core.suiService.openSUIWindow(window);
	}
	
	public void handleCreateGuildAbbrev(CreatureObject actor, String guildName, SWGObject creationSource) {
		SUIWindow window = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, "@guild:create_abbrev_title", "@guild:create_abbrev_prompt", actor, null, 0,
				(owner, eventType, returnList) -> {
					String abbrev = returnList.get(0); // TODO: Character checks

					if (abbrev.length() > 5) { actor.sendSystemMessage("@guild:create_fail_abbrev_bad_length", DisplayType.Broadcast); handleCreateGuildAbbrev(actor, guildName, creationSource); return;}
					if (getGuildByAbbreviation(abbrev) != null) { actor.sendSystemMessage("@guild:create_fail_abbrev_in_use", DisplayType.Broadcast); handleCreateGuildAbbrev(actor, guildName, creationSource); return;}
					
					Guild guild = createGuild(abbrev, guildName, actor);
					if (guild != null && creationSource != null && creationSource.getTemplate().equals("object/tangible/furniture/technical/shared_guild_registry_initial.iff")) { 
						core.objectService.destroyObject(creationSource);
					}
				});
		
    	window.setProperty("txtInput:MaxLength", "4");
    	window.setProperty("txtInput:NumericInteger", "False");
    	
    	core.suiService.openSUIWindow(window);
	}
	
	public void handleViewPermissionsList(CreatureObject actor, Guild guild) {
        final SUIWindow window = core.suiService.createSUIWindow("Script.tablePage", actor, null, (float) 0);
        window.setProperty("bg.caption.lblTitle:Text", "Permissions List");
        window.setProperty("comp.Prompt:Visible", "False");
        window.setProperty("btnExport:Visible", "False");
        window.setProperty("tablePage:Size", "785,434");
        window.setProperty("comp.TablePage.header:ScrollExtent", "444,30");
        window.addTableColumn("Name", "text");
        window.addTableColumn("Mail", "text");
        window.addTableColumn("Sponsor", "text");
        window.addTableColumn("Title", "text");
        window.addTableColumn("Accept", "text");
        window.addTableColumn("Kick", "text");
        window.addTableColumn("War", "text");
        window.addTableColumn("Change Guild Name", "text");
        window.addTableColumn("Disband", "text");
        window.addTableColumn("Rank", "text");
        window.addTableColumn("War Excluded", "text");
        window.addTableColumn("War Exclusive", "text");
        
        Map<Long, GuildMember> members = guild.getMembers();
        
        members.entrySet().forEach(e -> {
        	GuildMember member = e.getValue();
        	for (SUITableItem column : window.getTableItems()) {
        		if (column.getItemName().equals("Name")) {
        			window.addTableCell(member.getName(), e.getKey(), column.getIndex());
        			continue;
        		}
        		else if (member.getPermissions().contains(column.getItemName())) {
        			window.addTableCell("X", e.getKey(), column.getIndex());
        			continue;
        		} else
        			window.addTableCell("", e.getKey(), column.getIndex());
        	}
        });
        
		Vector<String> returnList = new Vector<String>();
		returnList.add("comp.TablePage.table:SelectedRow");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, (owner, eventType, resultList) -> {

			int selectedRow = Integer.parseInt(resultList.get(0));
			GuildMember selectedMember = guild.getMember(window.getTableObjIdByRow(selectedRow));
			
			if (selectedMember == null)
				return;
			
			handleChangeMemberPermissions((CreatureObject) owner, guild, selectedMember, "PermissionsList");
		});
        core.suiService.openSUIWindow(window);
	}
	
	public void handleChangeMemberPermissions(CreatureObject actor, Guild guild, GuildMember target) { handleChangeMemberPermissions(actor, guild, target, ""); }
	
	public void handleChangeMemberPermissions(CreatureObject actor, Guild guild, GuildMember target, String source) {
		GuildMember requester = guild.getMember(actor.getObjectID());
		
		if (requester == null)
			return;
		
		final SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "\\#00FF00\\" + target.getName() + "\\#FFFFFF Guild Member Permissions", 
				new Stf("@guild:permissions_prompt").getStfValue().replace("%TU", "\\#00FF00\\" + target.getName() + "\\#.\\"), target.getAllPermissions(requester), actor, null, 0);
		
		window.setProperty("listBox:Size", "487,316");
		
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		SUICallback windowCallback = (owner, eventType, resultList) -> {
			switch(eventType) {
				case 0:
					if (resultList.size() == 0)
						return;
					
					int selectedPermission = (int) (window.getObjectIdByIndex(Integer.parseInt(resultList.get(0))));
					
					switch (selectedPermission) {
						case 1: // Mail
							if (target.hasMailPermission()) target.setMailPermission(false);
							else target.setMailPermission(true);
							break;
						case 2: // Sponsor
							if (target.hasSponsorPermission()) target.setSponsorPermission(false);
							else target.setSponsorPermission(true);
							break;
						case 3: // Title
							if (target.hasTitlePermission()) target.setTitlePermission(false);
							else target.setTitlePermission(true);
							break;
						case 4: // Accept
							if (target.hasAcceptPermission()) target.setAcceptPermission(false);
							else target.setAcceptPermission(true);
							break;
						case 5: // Kick
							if (target.hasKickPermission()) target.setKickPermission(false);
							else target.setKickPermission(true);
							break;
						case 6: // War
							if (target.hasWarPermission()) target.setWarPermission(false);
							else target.setWarPermission(true);
							break;
						case 7: // Change Guild Name
							if (target.hasChangeNamePermission()) target.setChangeNamePermission(false);
							else target.setChangeNamePermission(true);
							break;
						case 8: // Disband
							if (target.hasDisbandPermission()) target.setDisbandPermission(false);
							else target.setDisbandPermission(true);
							break;
						case 9: // Rank
							if (target.hasRankPermission()) target.setRankPermission(false);
							else target.setRankPermission(true);
							break;
						case 10: // War Excluded
							if (target.isWarExcluded()) target.setWarExcluded(false);
							else target.setWarExcluded(true);
							break;
						case 11: // War Exclusive
							if (target.isWarExclusive()) target.setWarExclusive(false);
							else target.setWarExclusive(true);
							break;

						default: break;
					}
					handleChangeMemberPermissions(actor, guild, target, source);
					break;
				
				case 1: // Cancel
					break;
				
				case 2: // Back
					switch(source) {
						case "PermissionsList":
							handleViewPermissionsList(actor, guild);
							break;
						case "MemberOptions":
							handleMemberOptions(actor, guild, target);
							break;
						default: return;
					}
					break;
			}
		};
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, windowCallback);
		
		if (!source.isEmpty() || !source.equals("")) {
			window.setProperty("btnOther:Visible", "True");
			window.setProperty("btnOther:Text", "@guild:back_button");
			window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, windowCallback);
			window.addHandler(2, "", Trigger.TRIGGER_UPDATE, returnList, windowCallback);
		}
		core.suiService.openSUIWindow(window);
	}
	
	public void handleChangeGuildName(CreatureObject actor, Guild guild) {
		SUIWindow window = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, "@guild:namechange_name_title", "@guild:namechange_name_prompt", actor, null, 0,
				(owner, eventType, returnList) -> {
					String guildName = returnList.get(0); // TODO: Character checks
					
					if (guildName.length() > 24) { actor.sendSystemMessage("@guild:namechange_fail_name_bad_length", DisplayType.Broadcast); handleChangeGuildName(actor, guild); return;}
					// Allows the guild to keep their old name if they want to
					if (!guildName.equals(guild.getName()) && getGuildByName(guildName) != null) { actor.sendSystemMessage("@guild:create_fail_name_in_use", DisplayType.Broadcast); handleChangeGuildName(actor, guild); return;}
					
					handleChangeGuildAbbrev(actor, guild, guildName);
				});
        window.setProperty("txtInput:MaxLength", "24");
        window.setProperty("txtInput:NumericInteger", "False");
		core.suiService.openSUIWindow(window);
	}
	
	public void handleChangeGuildAbbrev(CreatureObject actor, Guild guild, String newName) {
		SUIWindow window = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, "@guild:namechange_abbrev_title", "@guild:namechange_abbrev_prompt", actor, null, 0,
				(owner, eventType, returnList) -> {
					String newAbbrev = returnList.get(0); // TODO: Character checks
					
					if (newAbbrev.length() > 5) { actor.sendSystemMessage("@guild:namechange_fail_abbrev_bad_length", DisplayType.Broadcast); handleChangeGuildAbbrev(actor, guild, newName); return;}
					// Allows the guild to keep their old abbreviation if they want to
					if (!guild.getAbbreviation().equals(newAbbrev) && getGuildByAbbreviation(newName) != null) { actor.sendSystemMessage("@guild:create_fail_name_in_use", DisplayType.Broadcast); handleChangeGuildAbbrev(actor, guild, newName); return;}
					
					changeGuildNameAbbrev(actor, guild, newName, newAbbrev);
				});
        window.setProperty("txtInput:MaxLength", "4");
        window.setProperty("txtInput:NumericInteger", "False");
		core.suiService.openSUIWindow(window);
	}
	
	public void changeGuildNameAbbrev(CreatureObject actor, Guild guild, String newName, String newAbbreviation) {
		if (newName.equals(guild.getName()) && newAbbreviation.equals(guild.getAbbreviation()))
			return;
		
		object.getGuildList().remove(guild.getString());
		
		guild.setName(newName);
		guild.setAbbreviation(newAbbreviation);
		
		object.getGuildList().add(guild.getString());
		
		guild.sendGuildMail(newName, "@guildmail:namechange_subject", new ProsePackage("@guildmail:namechange_text", "TO", actor.getCustomName(), "TU", newName, "TT", newAbbreviation));
	}
	
	public void handleChangeGuildMotd(CreatureObject actor, Guild guild) {
		SUIWindow window = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, "@guild:menu_member_motd", "@guild:prompt_member_motd_message", actor, null, 0, 
				(owner, eventType, resultList) -> {
					String newMotd = resultList.get(0);

					guild.setMotd(newMotd);
					
					actor.sendSystemMessage("Guild Message of the Day set to: " + newMotd, DisplayType.Broadcast);
				});
		
		window.setProperty("inputBox:Size", "506,430");
		core.suiService.openSUIWindow(window);
	}
	
	public void handleViewGuildMembers(CreatureObject actor, Guild guild) {
        final SUIWindow window = core.suiService.createSUIWindow("Script.tablePage", actor, null, (float) 0);
        window.setProperty("bg.caption.lblTitle:Text", "@guild:members_title");
        window.setProperty("comp.Prompt.lblPrompt:Text", "@guild:members_prompt");
        //if (actingMember.getAllPermissions(actingMember).size() > 2) window.setProperty("comp.Prompt.lblPrompt:Text", "@guild:members_show_prompt");
        window.setProperty("tablePage:Size", "785,634");
        // window.setProperty("comp.TablePage.table:columsizedatasource", "dsColSizes");
        window.setProperty("comp.TablePage.header:ScrollExtent", "444,30");
        window.addTableColumn("@guild:table_title_level", "text");
        window.addTableColumn("@guild:table_title_name", "text");
        window.addTableColumn("@guild:table_title_profession", "text");
        window.addTableColumn("@guild:table_title_rank", "text");
        window.addTableColumn("@guild:table_title_status", "text");
        window.addTableColumn("@guild:table_title_title", "text");
        window.addTableColumn("@guild:table_title_war_excluded", "text");
        window.addTableColumn("@guild:table_title_war_included", "text");
        
        Map<Long, GuildMember> members = guild.getMembers();
        members.entrySet().forEach(e -> {
        	GuildMember member = e.getValue();
        	for (SUITableItem column : window.getTableItems()) {
        		switch(column.getItemName()) {
        			case "@guild:table_title_level":
            			window.addTableCell(String.valueOf(member.getLevel()), e.getKey(), column.getIndex());
            			continue;
        			case "@guild:table_title_name":
            			window.addTableCell(member.getName(), e.getKey(), column.getIndex());
        				continue;
        			case "@guild:table_title_profession":
            			window.addTableCell(member.getProfession(), e.getKey(), column.getIndex());
        				continue;
        			case "@guild:table_title_rank":
            			window.addTableCell(member.getRank(), e.getKey(), column.getIndex());
        				continue;
        			case "@guild:table_title_status":
            			//window.addTableCell(member.getStatus(), e.getKey(), column.getIndex());
        				continue;
        			case "@guild:table_title_title":
        				window.addTableCell(member.getTitle(), e.getKey(), column.getIndex());
        				continue;
        			case "@guild:table_title_war_excluded":
        				if (member.isWarExcluded()) window.addTableCell("X", e.getKey(), column.getIndex());
        				else window.addTableCell("", e.getKey(), column.getIndex());
        				continue;
        			case "@guild:table_title_war_included":
        				if (member.isWarExclusive()) window.addTableCell("X", e.getKey(), column.getIndex());
        				else window.addTableCell("", e.getKey(), column.getIndex());
        				continue;
        		}
        	}
        });
        
		Vector<String> returnList = new Vector<String>();
		returnList.add("comp.TablePage.table:SelectedRow");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, (owner, eventType, resultList) -> {

			long objectId = window.getTableObjIdByRow(Integer.parseInt(resultList.get(0)));
			
			if (objectId == 0)
				return;
			
			GuildMember selectedMember = guild.getMember(objectId);
			
			if (selectedMember == null)
				return;
			
			handleMemberOptions((CreatureObject) owner, guild, selectedMember);
		});
		
		core.suiService.openSUIWindow(window);
	}
	
	public void handleMemberOptions(CreatureObject actor, Guild guild, GuildMember member) {
		GuildMember actingMember = guild.getMember(actor.getObjectID());
		
		Map<Long, String> options = new HashMap<Long, String>();
		if (actingMember.getPermissions().size() > 2)
			options.put((long) 0, "@guild:permissions");
		if (actingMember.hasTitlePermission())
			options.put((long) 1, "@guild:title");
		//if (actingMember.hasRankPermission())
			//options.put((long) 2, "@guild:rank");
		if (actingMember.hasKickPermission())
			options.put((long) 3, "@guild:kick");
		
		if (options.size() == 0)
			return;
		
		SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@guild:member_options_title", 
				new Stf("@guild:member_options_prompt").getStfValue().replace("%TU", "\\#00FF00\\" + member.getName() + "\\#.\\"), options, actor, null, 0);

		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		SUICallback callBack = (owner, eventType, resultList) -> {
			
			if (eventType == 1)
				return;
			
			if (resultList.size() == 0)
				return;

			int selectedOption = (int) (window.getObjectIdByIndex(Integer.parseInt(resultList.get(0))));
			
			switch(selectedOption) {
				case 0:
					core.suiService.closeSUIWindow(actor, window.getWindowId());
					handleChangeMemberPermissions(actor, guild, member, "MemberOptions");
					break;
				case 1:
					if (!actingMember.hasTitlePermission()) {
						actor.sendSystemMessage("@guild:guild_no_permission_operation", DisplayType.Broadcast);
						return;
					}
					handleMemberSetTitle(actor, guild, member);
					break;
				case 2:
					break;
				case 3:
					SWGObject target = core.objectService.getObject(member.getObjectId());
					
					if (target == null)
						target = core.objectService.getCreatureFromDB(member.getObjectId());
					
					if (target == null) {
						actor.sendSystemMessage("@guild:could_not_find_member", DisplayType.Broadcast);
						return;
					}
					
					leaveGuild(actor, (CreatureObject) target, guild);
					core.suiService.closeSUIWindow(actor, window.getWindowId());
					break;
				default: break;
			}
		};
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, callBack);
		core.suiService.openSUIWindow(window);
	}
	
	public void handleMemberSetTitle(CreatureObject actor, Guild guild, GuildMember target) {
		GuildMember actingMember = guild.getMember(actor.getObjectID());
		
		if (!actingMember.hasTitlePermission()) {
			actor.sendSystemMessage("@guild:guild_no_permission_operation", DisplayType.Broadcast);
			return;
		}
		
		SUIWindow window = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, "@guild:title_title", 
				new Stf("@guild:title_prompt").getStfValue().replace("%TU", target.getName()), actor, null, 0);
		
		Vector<String> returnList = new Vector<String>();
		returnList.add("txtInput:LocalText");
		
        window.setProperty("txtInput:MaxLength", "24");
        window.setProperty("txtInput:NumericInteger", "False");
        
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, (owner, eventType, resultList) -> {
			String title = resultList.get(0);
			
			if (title.length() > 25) {
				actor.sendSystemMessage("@guild:title_fail_bad_length", DisplayType.Broadcast);
				core.suiService.closeSUIWindow(actor, window.getWindowId());
				handleMemberSetTitle(actor, guild, target);
				return;
			}
			
			target.setTitle(title);
			
			actor.sendSystemMessage(OutOfBand.ProsePackage("@guild:title_self", "TU", target.getName(), "TT", title), DisplayType.Broadcast);
			
			core.suiService.closeSUIWindow(actor, window.getWindowId());
			
			if (core.objectService.getObject(target.getObjectId()) != null) {
				CreatureObject creoTarget = (CreatureObject)  core.objectService.getObject(target.getObjectId());
				if (creoTarget.getClient() == null || creoTarget.getClient().getSession() == null)
					return;
				
				creoTarget.sendSystemMessage(OutOfBand.ProsePackage("@guild:title_target", "TU", actor.getCustomName(), "TT", title), DisplayType.Broadcast);
			}
		});
		core.suiService.openSUIWindow(window);
	}
	
	public void handleGuildSponsorWindow(CreatureObject actor) {
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
		    	
		    	((CreatureObject)cOwner).sendSystemMessage(OutOfBand.ProsePackage("@guild:sponsor_target", "TT", guild.getName(), "TU", actor.getCustomName()), DisplayType.Broadcast);
		    	
				guild.sendGuildMail(guild.getName(), "@guildmail:sponsor_subject", new ProsePackage("@guildmail:sponsor_text", "TU", actor.getCustomName(), "TT", cOwner.getCustomName()));
	    	});
	    	core.suiService.openSUIWindow(wndSponsoredConfirm);
	    	
	    	actor.sendSystemMessage(OutOfBand.ProsePackage("@guild:sponsor_self", "TU", pSponsored.getCustomName(), "TT", invitingGuild.getName()), DisplayType.Broadcast);

	    });
	    core.suiService.openSUIWindow(wndSponsorPlayer);
	}
	
	public void handleGuildDisband(CreatureObject actor, Guild guild) {
		
		Map<Long, GuildMember> members = guild.getMembers();
		
		members.entrySet().forEach(e -> {
			CreatureObject target = (CreatureObject) core.objectService.getObject(e.getKey());
			
			if (target == null)
				target = (CreatureObject) core.objectService.getCreatureFromDB(e.getKey());

			if (target != null) {

				target.setGuildId(0);

				// Update association device
				TangibleObject datapad = (TangibleObject) target.getSlottedObject("datapad");
				
				if (datapad != null) {
					datapad.viewChildren(target, true, false, (obj) -> {
						if (obj instanceof IntangibleObject && obj.getTemplate().equals("object/intangible/data_item/shared_guild_stone.iff")) {
							obj.removeAttribute("guild_name");
							obj.removeAttribute("guild_abbrev");
							obj.removeAttribute("guild_leader");
						}
					});
				}
				
				if (target.getClient() != null && target.getClient().getSession() != null) 
					core.chatService.leaveChatRoom(target, guild.getChatRoomId(), true);
				else
					target.getPlayerObject().removeChannel(guild.getChatRoomId());
			}
		});

		guild.sendGuildMail(guild.getName(), "@guildmail:disband_subject", new ProsePackage("@guildmail:disband_text", "TU", actor.getCustomName()));

		removeGuild(guild.getId());
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
		
		guild.sendGuildMail(guild.getName(), "@guildmail:decline_subject", new ProsePackage("@guildmail:decline_text", "TU", actor.getCustomName(), "TT", sponsoree.getCustomName()));
		
		Mail declinedMail = new Mail();
        declinedMail.setMailId(core.chatService.generateMailId());
        declinedMail.setTimeStamp((int) new Date().getTime());
        declinedMail.setRecieverId(sponsoree.getObjectID());
        declinedMail.setStatus(Mail.NEW);
        declinedMail.addProseAttachment(new ProsePackage("@guildmail:decline_target_text", "TU", actor.getCustomName(), "TT", guild.getName()));
        declinedMail.setSubject("@guildmail:decline_subject");
        declinedMail.setSenderName(guild.getName());
        core.chatService.storePersistentMessage(declinedMail);
        
        if (sponsoree.isInQuadtree())
        	core.chatService.sendPersistentMessageHeader(sponsoree.getClient(), declinedMail);
	}
	
	public void showKickConfirmWindow(CreatureObject actor, CreatureObject target, Guild guild) {
		
		if (target.getObjectID() == guild.getLeader() && target.getObjectID() == actor.getObjectID()) {
			actor.sendSystemMessage("@guild:leave_fail_leader_tried_to_leave", DisplayType.Broadcast);
			return;
		} else if (target.getObjectID() == guild.getLeader()) {
			actor.sendSystemMessage("@guild:guild_no_permission_operation", DisplayType.Broadcast);
			return;
		}
		
		SUIWindow kickPrompt = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_YES_NO, "@guild:kick_title", 
				new Stf("@guild:kick_prompt").getStfValue().replace("%TU", target.getCustomName()), actor, null, 0);
		
		kickPrompt.addHandler(0, "", Trigger.TRIGGER_OK, new Vector<String>(), (owner, eventType, resultList) -> {
			leaveGuild(actor, target, guild);
		});
		
		core.suiService.openSUIWindow(kickPrompt);
	}

	public void showDisbandConfirmWindow(CreatureObject actor, Guild guild) {
		
		if (!guild.getMember(actor.getObjectID()).hasDisbandPermission() && actor.getObjectID() != guild.getLeader()) {
			actor.sendSystemMessage("@guild:guild_no_permission_operation", DisplayType.Broadcast);
			return;
		}
		
		SUIWindow disbandConfirm = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_YES_NO, "@guild:disband_title", "@guild:disband_prompt", actor, null, 0);
		disbandConfirm.addHandler(0, "", Trigger.TRIGGER_OK, new Vector<String>(), (owner, eventType, resultList) -> {
			handleGuildDisband(actor, guild);
		});
		core.suiService.openSUIWindow(disbandConfirm);
	}
	
	public void showGuildInfoWindow(CreatureObject actor, Guild guild) {
		String message = "Guild Name: " + guild.getName() + "\t (neutral)\n"
				+ "Guild Abbreviation: " + guild.getAbbreviation() + "\n"
				+ "Guild Leader: " + guild.getLeaderName() + "\n"
				+ "GCW Region Defender: None\n"
				+ "GCW Region Defender Bonus: 0%\n"
				+ "Guild Members: " + String.valueOf(guild.getMembers().size());

		SUIWindow window = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_OK, "@guild:info_title", message, actor, null, 0);
		window.setProperty("messageBox:Size", "290,170");
		core.suiService.openSUIWindow(window);
	}
	
	public void leaveGuild(CreatureObject actor, CreatureObject target, Guild guild) {
		GuildMember actingMember = guild.getMember(actor.getObjectID());
		
		if (actingMember == null)
			return;
		
		if ((!actingMember.hasKickPermission() && actor != target) || (target.getObjectID() == guild.getLeader() && target.getObjectID() != actor.getObjectID())) {
			actor.sendSystemMessage("@guild:guild_no_permission_operation", DisplayType.Broadcast);
			return;
		}
		
		if (actor.getObjectID() != target.getObjectID()) {

			guild.sendGuildMail(guild.getName(), "@guildmail:kick_subject", new ProsePackage("@guildmail:kick_text", "TU", actor.getCustomName(), "TT", target.getCustomName()));
			
			actor.sendSystemMessage(OutOfBand.ProsePackage("@guild:kick_self", "TU", target.getCustomName(), "TT", guild.getName()), DisplayType.Broadcast);
			
			if (target.isInQuadtree())
				target.sendSystemMessage(OutOfBand.ProsePackage("@guild:kick_target", "TU", actor.getCustomName(), "TT", guild.getName()), DisplayType.Broadcast);

		} else {
			if (target.getObjectID() == guild.getLeader()) {
				actor.sendSystemMessage("@guild:leave_fail_leader_tried_to_leave", DisplayType.Broadcast);
				return;
			} else {
				guild.sendGuildMail(guild.getName(), "@guildmail:leave_subject", new ProsePackage("@guildmail:leave_text", "TU", actor.getCustomName()));
				actor.sendSystemMessage(OutOfBand.ProsePackage("@guild:leave_self", "TU", guild.getName()), DisplayType.Broadcast);
			}
		}
		guild.getMembers().remove(target.getObjectID());
		target.setGuildId(0);
		core.chatService.leaveChatRoom(target, guild.getChatRoomId(), true);
		
		// Update association device
		TangibleObject datapad = (TangibleObject) target.getSlottedObject("datapad");
		
		if (datapad != null) {
			datapad.viewChildren(target, true, false, (obj) -> {
				if (obj instanceof IntangibleObject && obj.getTemplate().equals("object/intangible/data_item/shared_guild_stone.iff")) {
					obj.removeAttribute("guild_name");
					obj.removeAttribute("guild_abbrev");
					obj.removeAttribute("guild_leader");
				}
			});
		}
	}
	
	public void sendMailToGuild(String sender, String subject, String message, int guildId) {
		Guild guild = getGuildById(guildId);
		
		if (guild == null)
			return;
		
		guild.sendGuildMail(sender, subject, message);
	}
	
	public void sendGuildMotd(CreatureObject target, Guild guild) {
		if (guild == null || guild.getMotd().isEmpty())
			return;
		
		ChatRoom room = core.chatService.getChatRoom(guild.getChatRoomId());
		
		if (room == null)
			return;
		
		String message = guild.getMotd();
		
		if (message.startsWith("\\#"))
			message = " " + message;
		
		ChatRoomMessage roomMessage = new ChatRoomMessage(guild.getChatRoomId(), "Message of the Day", message);
		target.getClient().getSession().write(roomMessage.serialize());
	}
	
	public GuildObject getGuildObject() {
		return object;
	}
	
	public SWGSet<String> getGuildList() {
		return object.getGuildList();
	}
	
	public Guild getGuildByAbbreviation(String abbreviation) {
		TreeMap<Integer, Guild> guildMap = object.getGuilds();
		
		for (Entry<Integer, Guild> e : guildMap.entrySet()) {
			if (e.getValue().getAbbreviation().equals(abbreviation)) {
				return e.getValue();
			}
		}
		return null;
	}
	
	public Guild getGuildByName(String name) {
		TreeMap<Integer, Guild> guildMap = object.getGuilds();
		
		for (Entry<Integer, Guild> e : guildMap.entrySet()) {
			if (e.getValue().getName().equals(name)) {
				return e.getValue();
			}
		}
		
		return null;
	}
	
	public Guild getGuildById(int id) {
		TreeMap<Integer, Guild> guildMap = object.getGuilds();

		return guildMap.get(id);
	}
	
	public boolean removeGuild(int id) {
		Guild guild = getGuildById(id);
		
		if (guild != null) 
			return true;
		
		return false;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	}

	@Override
	public void shutdown() {
		
	}

}
