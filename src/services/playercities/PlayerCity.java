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
package services.playercities;

import java.util.Vector;

import engine.resources.scene.Point3D;

/** 
 * @author Charon 
 */

public class PlayerCity {
	
	public static int OUTPOST    = 0; 
	public static int VILLAGE    = 1; 
	public static int TOWNSHIP   = 2; 
	public static int CITY       = 3; 
	public static int METROPOLIS = 4; 
	
	public static int CLONING_LAB            = 0; 
	public static int DNA_LABORATORY         = 1; 
	public static int ENCORE_PERFORMANCE     = 2; 
	public static int ENTERTAINMENT_DISTRICT = 3; 
	public static int IMPROVED_JOB_MARKET    = 4; 
	public static int MANUFACTURING_CENTER   = 5; 
	public static int MEDICAL_CENTER         = 6; 
	public static int RESEARCH_CENTER        = 7; 
	public static int SAMPLE_RICH            = 8; 
	
	
	private Point3D cityCenterPosition = new Point3D(0,0,0);
	private int cityRadius = 0;
	private int rank = 0;
	private int specialisation = -1;
	private long mayorID = 0L;
	private int cityTreasury = 0;
	private int maintenanceFee = 0;
	private int GCW_Alignment = 0;
	private long nextElectionDate = 0L; 
	private long nextCityUpdate = 0L; 
	private long foundationTime = 0L;
	
	private Vector<Long> placedStructures = new Vector<Long>();
	private Vector<Long> citizens = new Vector<Long>();
	private Vector<Long> electionChallengers = new Vector<Long>();
	private Vector<Byte> electionVotes = new Vector<Byte>();
	private Vector<Long> cityBanList = new Vector<Long>();
	private Vector<Long> militiaList = new Vector<Long>();
	private Vector<Long> foundersList = new Vector<Long>();
	
	public PlayerCity(){
		
		setFoundationTime(System.currentTimeMillis());
		setCityRadius(150);
		setMaintenanceFee(1);
		setNextCityUpdate(foundationTime+7*86400*1000);
		setNextElectionDate(foundationTime+21*86400*1000);
	}
	
	public void handleGrantZoning() {
		
	}
	
	public void handleBuildRequest() {
		
	}
	
	public void checkPlacementPermission() {
		
	}
	
	public void processElection() {
		
	}
	
	public void processCityUpdate() {
		
	}
	
	public boolean checkFoundationSuccess() {
		
		Vector<Long> founders = getFoundersList();
		Vector<Long> citizenList = getCitizens();
		int founderCitizenCount = 0;
		for (long founderId : founders){
			if (citizenList.contains(founderId))
				founderCitizenCount++;
		}
		if (founderCitizenCount==founders.size())
				return true;
		
		return false;
	}
	
	public void calculateMaintenance() {
		
	}
	
	public void handleBanRequest() {
		
	}
	
	public void handlePardonRequest() {
		
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getSpecialisation() {
		return specialisation;
	}

	public void setSpecialisation(int specialisation) {
		this.specialisation = specialisation;
	}

	public long getMayorID() {
		return mayorID;
	}

	public void setMayorID(long mayorID) {
		this.mayorID = mayorID;
	}

	public int getCityTreasury() {
		return cityTreasury;
	}

	public void setCityTreasury(int cityTreasury) {
		this.cityTreasury = cityTreasury;
	}

	public int getMaintenanceFee() {
		return maintenanceFee;
	}

	public void setMaintenanceFee(int maintenanceFee) {
		this.maintenanceFee = maintenanceFee;
	}

	public int getGCW_Alignment() {
		return GCW_Alignment;
	}

	public void setGCW_Alignment(int gCW_Alignment) {
		GCW_Alignment = gCW_Alignment;
	}

	public Vector<Long> getPlacedStructures() {
		return placedStructures;
	}

	public void setPlacedStructures(Vector<Long> placedStructures) {
		this.placedStructures = placedStructures;
	}

	public Vector<Long> getCitizens() {
		return citizens;
	}

	public void setCitizens(Vector<Long> citizens) {
		this.citizens = citizens;
	}

	public long getNextElectionDate() {
		return nextElectionDate;
	}

	public void setNextElectionDate(long nextElectionDate) {
		this.nextElectionDate = nextElectionDate;
	}

	public Vector<Long> getElectionChallengers() {
		return electionChallengers;
	}

	public void setElectionChallengers(Vector<Long> electionChallengers) {
		this.electionChallengers = electionChallengers;
	}

	public long getNextCityUpdate() {
		return nextCityUpdate;
	}

	public void setNextCityUpdate(long nextCityUpdate) {
		this.nextCityUpdate = nextCityUpdate;
	}

	public Vector<Long> getCityBanList() {
		return cityBanList;
	}

	public void setCityBanList(Vector<Long> cityBanList) {
		this.cityBanList = cityBanList;
	}

	public Vector<Byte> getElectionVotes() {
		return electionVotes;
	}
	
	public void addToElectionVotes(byte vote) {
		electionVotes.add(vote);
	}
	
	// Stalinesque method :D
	public void setElectionVotes(Vector<Byte> electionVotes) {
		this.electionVotes = electionVotes;
	}

	public Point3D getCityCenterPosition() {
		return cityCenterPosition;
	}

	public void setCityCenterPosition(Point3D cityCenterPosition) {
		this.cityCenterPosition = cityCenterPosition;
	}
	
	public int getCityRadius() {
		return cityRadius;
	}

	public void setCityRadius(int cityRadius) {
		this.cityRadius = cityRadius;
	}

	public Vector<Long> getMilitiaList() {
		return militiaList;
	}

	public void setMilitiaList(Vector<Long> militiaList) {
		this.militiaList = militiaList;
	}

	public Vector<Long> getFoundersList() {
		return foundersList;
	}

	public void setFoundersList(Vector<Long> foundersList) {
		this.foundersList = foundersList;
	}

	public long getFoundationTime() {
		return foundationTime;
	}

	public void setFoundationTime(long foundationTime) {
		this.foundationTime = foundationTime;
	}

}
