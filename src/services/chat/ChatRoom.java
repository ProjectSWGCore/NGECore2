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
package services.chat;

import java.util.Vector;


import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class ChatRoom {
	private String creator; // creator of the room (first name only, lowercase)
	private String description; // title
	@PrimaryKey
	private int roomId;
	private String roomAddress; // name

	private Vector<String> moderatorList = new Vector<String>();
	private Vector<String> banList = new Vector<String>();
	@NotPersistent
	private Vector<String> userList = new Vector<String>(); // current users

	private boolean moderatorsOnly;
	private boolean privateRoom;
	private boolean visible;
	private String owner; // current owner of the room (first name only lowercase)

	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator.toLowerCase();
	}

	public String getRoomAddress() {
		return roomAddress;
	}

	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Vector<String> getModeratorList() {
		return moderatorList;
	}

	public Vector<String> getUserList() {
		return userList;
	}

	public boolean isModeratorsOnly() {
		return moderatorsOnly;
	}

	public void setModeratorsOnly(boolean moderatorsOnly) {
		this.moderatorsOnly = moderatorsOnly;
	}

	public boolean isPrivateRoom() {
		return privateRoom;
	}

	public void setPrivateRoom(boolean privateRoom) {
		this.privateRoom = privateRoom;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner.toLowerCase();
	}

	public Vector<String> getBanList() {
		return banList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void addUser(String user) {
		userList.add(user.toLowerCase());
	}
	
	public void addModerator(String moderator) {
		moderatorList.add(moderator.toLowerCase());
	}
	
	public void banUser(String user) {
		banList.add(user.toLowerCase());
	}
	
	public boolean hasUser(String user) {
		return userList.contains(user.toLowerCase());
	}
	
	public boolean hasModerator(String moderator) {
		return moderatorList.contains(moderator.toLowerCase());
	}
	
	public boolean hasBan(String user) {
		return banList.contains(user.toLowerCase());
	}
}
