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

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GuildMember {
	private long objectId;
	private long joinTime;
	private String profession = "";
	private short level;
	private String rank = "";
	private String name = "";
	private String title = "";
	private int votes = 0;
	private long votedId;
	private Vector<String> permissions = new Vector<String>();
	private boolean mailPermission = false;
	private boolean sponsorPermission = false;
	private boolean titlePermission = false;
	private boolean kickPermission = false;
	private boolean acceptPermission = false;
	private boolean warPermission = false;
	private boolean changeNamePermission = false;
	private boolean disbandPermission = false;
	private boolean rankPermission = false;
	
	private boolean warExcluded = false;
	private boolean warExclusive = false;
	
	public GuildMember() { }
	
	public GuildMember(long objectId) {
		this.objectId = objectId;
		this.joinTime = System.currentTimeMillis();
	}
	
	public void removeAllPermissions() {
		setMailPermission(false);
		setSponsorPermission(false);
		setTitlePermission(false);
		setKickPermission(false);
		setAcceptPermission(false);
		setWarPermission(false);
		setChangeNamePermission(false);
		setDisbandPermission(false);
		setRankPermission(false);
	}
	
	public void giveAllPermissions() {
		setMailPermission(true);
		setSponsorPermission(true);
		setTitlePermission(true);
		setKickPermission(true);
		setAcceptPermission(true);
		setWarPermission(true);
		setChangeNamePermission(true);
		setDisbandPermission(true);
		setRankPermission(true);
	}
	
	public long getObjectId() {
		return objectId;
	}
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	public void incrementVotes() {
		this.votes++;
	}
	
	public void decrementVotes() {
		this.votes--;
	}
	
	public long getVotedId() {
		return votedId;
	}

	public void setVotedId(long votedId) {
		this.votedId = votedId;
	}

	public boolean hasMailPermission() {
		return mailPermission;
	}
	public void setMailPermission(boolean mailPermission) {
		if (mailPermission)
			permissions.add("Mail");
		else
			permissions.remove("Mail");
		
		this.mailPermission = mailPermission;
	}
	
	public boolean hasSponsorPermission() {
		return sponsorPermission;
	}
	public void setSponsorPermission(boolean sponsorPermission) {
		if (sponsorPermission)
			permissions.add("Sponsor");
		else
			permissions.remove("Sponsor");
		
		this.sponsorPermission = sponsorPermission;
	}
	public boolean hasTitlePermission() {
		return titlePermission;
	}
	public void setTitlePermission(boolean titlePermission) {
		if (titlePermission)
			permissions.add("Title");
		else
			permissions.remove("Title");
		
		this.titlePermission = titlePermission;
	}
	public boolean hasKickPermission() {
		return kickPermission;
	}
	public void setKickPermission(boolean kickPermission) {
		if (kickPermission)
			permissions.add("Kick");
		else
			permissions.remove("Kick");
		
		this.kickPermission = kickPermission;
	}
	public boolean hasAcceptPermission() {
		return acceptPermission;
	}
	public void setAcceptPermission(boolean acceptPermission) {
		if (acceptPermission)
			permissions.add("Accept");
		else
			permissions.remove("Accept");
		
		this.acceptPermission = acceptPermission;
	}
	public boolean hasWarPermission() {
		return warPermission;
	}
	public void setWarPermission(boolean warPermission) {
		if (warPermission)
			permissions.add("War");
		else
			permissions.remove("War");
		
		this.warPermission = warPermission;
	}
	public boolean hasChangeNamePermission() {
		return changeNamePermission;
	}
	public void setChangeNamePermission(boolean changeNamePermission) {
		if (changeNamePermission)
			permissions.add("Change Guild Name");
		else
			permissions.remove("Change Guild Name");
		
		this.changeNamePermission = changeNamePermission;
	}
	public boolean hasDisbandPermission() {
		return disbandPermission;
	}
	public void setDisbandPermission(boolean disbandPermission) {
		if (disbandPermission)
			permissions.add("Disband");
		else
			permissions.remove("Disband");
		
		this.disbandPermission = disbandPermission;
	}
	
	public boolean hasRankPermission() {
		return rankPermission;
	}

	public void setRankPermission(boolean rankPermission) {
		if (rankPermission)
			permissions.add("Rank");
		else
			permissions.remove("Rank");
		this.rankPermission = rankPermission;
	}

	public boolean isWarExcluded() {
		return warExcluded;
	}

	public void setWarExcluded(boolean warExcluded) {
		if (warExcluded)
			permissions.add("War Excluded");
		else
			permissions.remove("War Excluded");
		this.warExcluded = warExcluded;
	}

	public boolean isWarExclusive() {
		return warExclusive;
	}

	public void setWarExclusive(boolean warExclusive) {
		if (warExclusive)
			permissions.add("War Exclusive");
		else
			permissions.remove("War Exclusive");
		this.warExclusive = warExclusive;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Long, String> getAllPermissions(GuildMember requester) {
		Map<Long, String> permissions = new HashMap<Long, String>();
		
		// Only allows requester to change permissions that they have (Ex. Can't change sponsor permission on a member if they don't have that permission)
		// War Excluded and War Exclusive did not follow that rule
		if (requester.getObjectId() != getObjectId()) {
			if (requester.hasMailPermission()) {
				if (hasMailPermission()) permissions.put((long) 1, "@guild:permission_mail_yes");
				else permissions.put((long) 1, "@guild:permission_mail_no");
			}

			if (requester.hasSponsorPermission()) {
				if (hasSponsorPermission()) permissions.put((long) 2, "@guild:permission_sponsor_yes");
				else permissions.put((long) 2, "@guild:permission_sponsor_no");
			}

			if (requester.hasTitlePermission()) {
				if (hasTitlePermission()) permissions.put((long) 3, "@guild:permission_title_yes");
				else permissions.put((long) 3, "@guild:permission_title_no");
			}

			if (requester.hasAcceptPermission()) {
				if (hasAcceptPermission()) permissions.put((long) 4, "@guild:permission_accept_yes");
				else permissions.put((long) 4, "@guild:permission_accept_no");
			}

			if (requester.hasKickPermission()) {
				if (hasKickPermission()) permissions.put((long) 5, "@guild:permission_kick_yes");
				else permissions.put((long) 5, "@guild:permission_kick_no");
			}

			if (requester.hasWarPermission()) {
				if (hasWarPermission()) permissions.put((long) 6, "@guild:permission_war_yes");
				else permissions.put((long) 6, "@guild:permission_war_no");
			}

			if (requester.hasChangeNamePermission()) {
				if (hasChangeNamePermission()) permissions.put((long) 7, "@guild:permission_namechange_yes");
				else permissions.put((long) 7, "@guild:permission_namechange_no");
			}

			if (requester.hasDisbandPermission()) {
				if (hasDisbandPermission()) permissions.put((long) 8, "@guild:permission_disband_yes");
				else permissions.put((long) 8, "@guild:permission_disband_no");
			}

			if (requester.hasRankPermission()) {
				if (hasRankPermission()) permissions.put((long) 9, "@guild:permission_rank_yes");
				else permissions.put((long) 9, "@guild:permission_rank_no");
			}
		}
		if (requester.hasWarPermission() || (requester.getObjectId() == getObjectId())) {
			if (isWarExcluded()) permissions.put((long) 10, "+ War Excluded");
			else permissions.put((long) 10, "- War Excluded");

			if (isWarExclusive()) permissions.put((long) 11, "+ War Exclusive");
			else permissions.put((long) 11, "- War Exclusive");
		}
		return permissions;
	}

	public Vector<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Vector<String> permissions) {
		this.permissions = permissions;
	}
}
