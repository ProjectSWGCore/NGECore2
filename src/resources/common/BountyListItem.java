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
package resources.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity(version=1)
public class BountyListItem implements Serializable {

	private static final long serialVersionUID = 1L;
	@PrimaryKey
	private long objectID;
	private int creditReward;
	private String profession;
	private String faction;
	private String name;
	private List<Long> assignedHunters;
	private List<Long> bountyPlacers;
	private int failedAttempts;

	public BountyListItem() { }
	
	public BountyListItem(long objectID, int creditReward, String profession, String faction, String name) {
		this.objectID = objectID;
		this.creditReward = creditReward;
		this.profession = profession;
		this.faction = faction;
		this.setName(name);
		this.setAssignedHunters(new ArrayList<Long>(3));
		this.setBountyPlacers(new ArrayList<Long>());
	}
	
	public long getObjectID() {
		return objectID;
	}
	public void setObjectID(long objectID) {
		this.objectID = objectID;
	}
	public int getCreditReward() {
		return creditReward;
	}
	public void setCreditReward(int creditReward) {
		this.creditReward = creditReward;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addBounty(int amountToAdd) {
		this.creditReward += amountToAdd;
	}
	
	public void deductBounty(int amountToDeduct) {
		this.creditReward = this.creditReward - amountToDeduct;
	}

	public List<Long> getAssignedHunters() {
		return assignedHunters;
	}

	public void setAssignedHunters(List<Long> assignedHunters) {
		this.assignedHunters = assignedHunters;
	}
	
	public void addBountyHunter(long objectId) {
		assignedHunters.add(objectId);
	}
	
	public void removeBountyHunter(long objectId) {
		assignedHunters.remove(objectId);
	}

	public List<Long> getBountyPlacers() {
		return bountyPlacers;
	}

	public void setBountyPlacers(List<Long> bountyPlacers) {
		this.bountyPlacers = bountyPlacers;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}
	
	public void incrementFailedAttempts() {
		this.failedAttempts += 1;
	}
}