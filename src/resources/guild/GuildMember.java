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

public class GuildMember {
	private long objectId;
	private long joinTime;
	// TODO: These might have to be moved to a new GuildRank class depending on how permissions worked for NGE
	//this works for now... Lack of NGE Guild Rank guides makes re-creation difficult (Ranks introduced Game Update 8: http://swg.wikia.com/wiki/Game_Update_8)
	private boolean mailPermission = false;
	private boolean sponsorPermission = false;
	private boolean titlePermission = false;
	private boolean kickPermission = false;
	private boolean acceptPermission = false;
	private boolean warPermission = false;
	private boolean changeNamePermission = false;
	private boolean disbandPermission = false;
	
	public GuildMember() { }
	
	public GuildMember(long objectId) {
		this.objectId = objectId;
		this.joinTime = System.currentTimeMillis();
	}
	
	public void removeAllPermissions() {
		this.mailPermission = false;
		this.sponsorPermission = false;
		this.titlePermission = false;
		this.kickPermission = false;
		this.acceptPermission = false;
		this.warPermission = false;
		this.changeNamePermission = false;
		this.disbandPermission = false;
	}
	
	public void giveAllPermissions() {
		this.mailPermission = true;
		this.sponsorPermission = true;
		this.titlePermission = true;
		this.kickPermission = true;
		this.acceptPermission = true;
		this.warPermission = true;
		this.changeNamePermission = true;
		this.disbandPermission = true;
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

	public boolean hasMailPermission() {
		return mailPermission;
	}
	public void setMailPermission(boolean mailPermission) {
		this.mailPermission = mailPermission;
	}
	
	public boolean hasSponsorPermission() {
		return sponsorPermission;
	}
	public void setSponsorPermission(boolean sponsorPermission) {
		this.sponsorPermission = sponsorPermission;
	}
	public boolean hasTitlePermission() {
		return titlePermission;
	}
	public void setTitlePermission(boolean titlePermission) {
		this.titlePermission = titlePermission;
	}
	public boolean hasKickPermission() {
		return kickPermission;
	}
	public void setKickPermission(boolean kickPermission) {
		this.kickPermission = kickPermission;
	}
	public boolean hasAcceptPermission() {
		return acceptPermission;
	}
	public void setAcceptPermission(boolean acceptPermission) {
		this.acceptPermission = acceptPermission;
	}
	public boolean hasWarPermission() {
		return warPermission;
	}
	public void setWarPermission(boolean warPermission) {
		this.warPermission = warPermission;
	}
	public boolean hasChangeNamePermission() {
		return changeNamePermission;
	}
	public void setChangeNamePermission(boolean changeNamePermission) {
		this.changeNamePermission = changeNamePermission;
	}
	public boolean hasDisbandPermission() {
		return disbandPermission;
	}
	public void setDisbandPermission(boolean disbandPermission) {
		this.disbandPermission = disbandPermission;
	}
	
	public Map<Long, String> getAllPermissions() {
		Map<Long, String> permissions = new HashMap<Long, String>();
		permissions.put((long) 1, "@guild:permission_mail");
		permissions.put((long) 3, "@guild:permission_sponsor");
		permissions.put((long) 5, "@guild:permission_title");
		permissions.put((long) 7, "@guild:permission_accept");
		permissions.put((long) 9, "@guild:permission_kick");
		permissions.put((long) 11, "@guild:permission_war");
		permissions.put((long) 13, "@guild:permission_namechange");
		permissions.put((long) 15, "@guild:permission_disband");
		
		
		if (hasMailPermission())
			permissions.put((long) 2, "@guild:permission_mail_yes");
		else
			permissions.put((long) 2, "@guild:permission_mail_no");
		
		if (hasSponsorPermission())
			permissions.put((long) 4, "@guild:permission_sponsor_yes");
		else
			permissions.put((long) 4, "@guild:permission_sponsor_no");
		
		if (hasTitlePermission())
			permissions.put((long) 6, "@guild:permission_title_yes");
		else
			permissions.put((long) 6, "@guild:permission_title_no");
		
		if (hasAcceptPermission())
			permissions.put((long) 8, "@guild:permission_accept_yes");
		else
			permissions.put((long) 8, "@guild:permission_accept_no");
		
		if (hasKickPermission())
			permissions.put((long) 10, "@guild:permission_kick_yes");
		else
			permissions.put((long) 10, "@guild:permission_kick_no");
		
		if (hasWarPermission())
			permissions.put((long) 12, "@guild:permission_war_yes");
		else
			permissions.put((long) 12, "@guild:permission_war_no");
		
		if (hasChangeNamePermission())
			permissions.put((long) 14, "@guild:permission_namechange_yes");
		else
			permissions.put((long) 14, "@guild:permission_namechange_no");
		
		if (hasDisbandPermission())
			permissions.put((long) 16, "@guild:permission_disband_yes");
		else
			permissions.put((long) 16, "@guild:permission_disband_no");
		return permissions;
	}
}
