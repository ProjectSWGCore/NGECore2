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
package resources.guild;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import main.NGECore;
import resources.common.ProsePackage;
import services.chat.Mail;
import engine.resources.objects.SWGObject;

public class Guild {
	
	private int id;
	private int chatRoomId;
	private String abbreviation;
	private String name;
	private long leader;
	private String leaderName;
	private Map<Long, GuildMember> members = new HashMap<Long, GuildMember>();
	private Map<Long, String> sponsoredPlayers = new HashMap<Long, String>();
	private Map<Long, Integer> leaderCandidates = new HashMap<Long, Integer>();
	private String motd = "";
	private boolean electionsEnabled = false;
	private long electionResultsDate;
	
	public Guild(int id, String abbreviation, String name, SWGObject leader) {
		this.id = id;
		this.abbreviation = abbreviation;
		this.name = name;
		this.leader = leader.getObjectID();
		this.leaderName = leader.getCustomName();
	}
	
	public Guild() { }
	
	public GuildMember addMember(long objID) {
		GuildMember member = new GuildMember(objID);
		members.put(objID, member);
		return member;
	}
	
	public void sendGuildMail(String sender, String subject, String message, ProsePackage prose) {
		NGECore core = NGECore.getInstance();
		Date date = new Date();
		members.forEach((id, member) -> {
			Mail guildMail = new Mail();
            guildMail.setMailId(core.chatService.generateMailId());
            guildMail.setTimeStamp((int) date.getTime());
            guildMail.setRecieverId(id);
            guildMail.setStatus(Mail.NEW);
            guildMail.setMessage(message);
            if (prose != null) guildMail.addProseAttachment(prose);
            guildMail.setSubject(subject);
            guildMail.setSenderName(sender);
            core.chatService.storePersistentMessage(guildMail);
            
            if (core.objectService.getObject(id) != null) {
            	core.chatService.sendPersistentMessageHeader(core.objectService.getObject(id).getClient(), guildMail);
            }
		});
	}
	
	public void sendGuildMail(String sender, String subject, String message) {
		sendGuildMail(sender, subject, message, null);
	}
	
	public void sendGuildMail(String sender, String subject, ProsePackage prose) {
		sendGuildMail(sender, subject, "", prose);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(int chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public String getAbbreviation() {
		return abbreviation;
	}
	
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getString() {
		return (Integer.toString(getId()) + ":" + getAbbreviation());
	}
	
	public long getLeader() {
		return leader;
	}
	
	public void setLeader(long leader) {
		this.leader = leader;
	}
	
	public String getMotd() {
		return motd;
	}

	public void setMotd(String motd) {
		this.motd = motd;
	}

	public Map<Long, GuildMember> getMembers() {
		return members;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public Map<Long, String> getSponsoredPlayers() {
		return sponsoredPlayers;
	}

	public void setSponsoredPlayers(Map<Long, String> sponsoredPlayers) {
		this.sponsoredPlayers = sponsoredPlayers;
	}

	public GuildMember getMember(long objectID) {
		return members.get(objectID);
	}

	public boolean isElectionsEnabled() {
		return electionsEnabled;
	}

	public void setElectionsEnabled(boolean electionsEnabled) {
		this.electionsEnabled = electionsEnabled;
	}

	public long getElectionResultsDate() {
		return electionResultsDate;
	}

	public void setElectionResultsDate(long electionResultsDate) {
		this.electionResultsDate = electionResultsDate;
	}

	public Map<Long, Integer> getLeaderCandidates() {
		return leaderCandidates;
	}

	public void setLeaderCandidates(Map<Long, Integer> leaderCandidates) {
		this.leaderCandidates = leaderCandidates;
	}
	
	public boolean isRunningForLeader(long objectID) {
		if (leaderCandidates.containsKey(Long.valueOf(objectID))) return true;
		else return false;
	}
}
