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

import java.util.List;
import java.util.Vector;

import resources.objects.creature.CreatureObject;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

@Entity
public class ChatRoom {
	private String creator; // creator of the room (first name only, lowercase)
	private String description; // title
	@PrimaryKey
	private int roomId;
	private String roomAddress; // name

	private Vector<CreatureObject> moderatorList = new Vector<CreatureObject>();
	private Vector<CreatureObject> banList = new Vector<CreatureObject>();
	@NotPersistent
	private Vector<CreatureObject> userList = new Vector<CreatureObject>(); // current users

	@NotPersistent
	private int requestId; // not-persistent
	private boolean moderatorsOnly;
	private boolean privateRoom;
	private boolean visible;
	private String owner; // current owner of the room (first name only lowercase)

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public Vector<CreatureObject> getModeratorList() {
		return moderatorList;
	}

	public void setModeratorList(Vector<CreatureObject> moderatorList) {
		this.moderatorList = moderatorList;
	}

	public Vector<CreatureObject> getUserList() {
		return userList;
	}

	public void setUserList(Vector<CreatureObject> userList) {
		this.userList = userList;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
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
		this.owner = owner;
	}

	public Vector<CreatureObject> getBanList() {
		return banList;
	}

	public void setBanList(Vector<CreatureObject> banList) {
		this.banList = banList;
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
}
