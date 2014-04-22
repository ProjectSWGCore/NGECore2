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

import resources.objects.creature.CreatureObject;
import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;

/** 
 * @author Charon 
 */

public class PlayerCity {
	
	public static int DESERTED   = 0;
	public static int OUTPOST    = 1; 
	public static int VILLAGE    = 2; 
	public static int TOWNSHIP   = 3; 
	public static int CITY       = 4; 
	public static int METROPOLIS = 5; 
	
	public static int CLONING_LAB            = 1; 
	public static int DNA_LABORATORY         = 2; 
	public static int ENCORE_PERFORMANCE     = 3; 
	public static int ENTERTAINMENT_DISTRICT = 4; 
	public static int IMPROVED_JOB_MARKET    = 5; 
	public static int MANUFACTURING_CENTER   = 6; 
	public static int MEDICAL_CENTER         = 7; 
	public static int RESEARCH_CENTER        = 8; 
	public static int SAMPLE_RICH            = 9; 
	
	public static int BANK              = 1; 
	public static int CLONING_FACILITY  = 2; 
	public static int GARAGE            = 3; 
	public static int MEDIUM_GARDEN     = 4; 
	public static int LARGE_GARDEN      = 5;
	public static int SHUTTLEPORT       = 6;
	
	
	private String cityName = "";
	private int cityID = -1;
	private Point3D cityCenterPosition = new Point3D(0,0,0);
	private int cityRadius = 0;
	private int cityRank = 0;
	private int specialisation = -1;
	private long mayorID = 0L;
	private int cityTreasury = 0;
	private int maintenanceFee = 0;
	private int GCW_Alignment = 0;
	private long nextElectionDate = 0L; 
	private long nextCityUpdate = 0L; 
	private long foundationTime = 0L;
	private int tax = 0;
	private boolean founded = false;
	
	private final long cityUpdateSpan = 7*86400*1000;
	private final long legislationPeriod = 21*86400*1000;
	
	private Vector<Long> placedStructures = new Vector<Long>();
	private Vector<Long> citizens = new Vector<Long>();
	private Vector<Long> electionCandidates = new Vector<Long>();
	private Vector<Byte> electionVotes = new Vector<Byte>();
	private Vector<Long> cityBanList = new Vector<Long>();
	private Vector<Long> militiaList = new Vector<Long>();
	private Vector<Long> foundersList = new Vector<Long>();
	
	public PlayerCity(){}
	
	public PlayerCity(CreatureObject founder, int cityID){
		
		setCityCenterPosition(founder.getPosition());
		setCityRadius(150);
		setFoundationTime(System.currentTimeMillis());		
		setMaintenanceFee(1);
		setNextCityUpdate(foundationTime+cityUpdateSpan);
		setNextElectionDate(foundationTime+legislationPeriod);
		citizens.add(founder.getObjectID());
		this.cityID = cityID;
	}
	
	public void handleGrantZoning() {
		
	}
	
	public void handleBuildRequest() {
		
	}
	
	public void checkPlacementPermission() {
		
	}
	
	public void processElection() {
				
		long winnerID = mayorID;
		mayorID = winnerID;
		electionVotes.clear();
		electionCandidates.clear();
		electionCandidates.add(mayorID);
		setNextElectionDate(System.currentTimeMillis()+legislationPeriod);
	}
	
	public void processCityUpdate() {
		// has something changed?
		System.out.println("processCityUpdate");
		int censusResult = citizens.size();
		if (censusResult<5){			
			setRank(DESERTED); // City is technically not a city anymore
			setCityRadius(0);
		}
		
		if (censusResult>=5 && censusResult<10){
			setRank(OUTPOST);	
			setCityRadius(150);
		}
		
		if (censusResult>=10 && censusResult<15){
			setRank(VILLAGE);		
			setCityRadius(200);
		}
		
		if (censusResult>=15 && censusResult<30){
			setRank(TOWNSHIP);
			setCityRadius(300);
		}
		
		if (censusResult>=30 && censusResult<40){
			setRank(CITY);		
			setCityRadius(400);
		}
		
		if (censusResult>=40){
			setRank(METROPOLIS);	
			setCityRadius(450);
		}
		
		// collect taxes
		for (long citizen : citizens){
			CreatureObject citizenObject = (CreatureObject) NGECore.getInstance().objectService.getObject(citizen);
			citizenObject.setBankCredits(citizenObject.getBankCredits()-tax);
			cityTreasury += tax;
			// ToDo: Handle tax evaders etc.
		}
		
		demolishHighRankStructures();
		setNextCityUpdate(System.currentTimeMillis()+cityUpdateSpan);
	}
	
	public void demolishHighRankStructures() {
		// Check if there are any structures in the city
		// that require a higher rank than the current one
		synchronized(placedStructures){
			Vector<SWGObject> wreckingBall = new Vector<SWGObject>();
			for (long structureID : placedStructures){
				SWGObject structure = NGECore.getInstance().objectService.getObject(structureID);
				int structureCode = (int) structure.getAttachment("CityStructureCode");
				
				if (structureCode==BANK && cityRank<2){				
					wreckingBall.add(structure);
				}
				
				if (structureCode==CLONING_FACILITY && cityRank<3){				
					wreckingBall.add(structure);
				}
				
				if (structureCode==GARAGE && cityRank<2){				
					wreckingBall.add(structure);
				}
				
				if (structureCode==MEDIUM_GARDEN && cityRank<2){				
					wreckingBall.add(structure);
				}
				
				if (structureCode==LARGE_GARDEN && cityRank<3){				
					wreckingBall.add(structure);
				}
				
				if (structureCode==SHUTTLEPORT && cityRank<4){				
					wreckingBall.add(structure);
				}						
			}
			
			for (SWGObject wreck : wreckingBall){
				placedStructures.remove(wreck.getObjectID());
				NGECore.getInstance().objectService.destroyObject(wreck.getObjectID());
			}			
		}		
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
				return true; // All founders are citizens now
		
		return false;
	}
	
	public void calculateMaintenance() {
		
	}
	
	public void handleBanRequest() {
		
	}
	
	public void handlePardonRequest() {
		
	}

	public int getCityRank() {
		return cityRank;
	}

	public void setRank(int cityRank) {
		this.cityRank = cityRank;
	}

	public int getSpecialisation() {
		return specialisation;
	}

	public void setSpecialisation(int specialisation) {
		this.specialisation = specialisation;
		sendSpecUpdateMail(specialisation);
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
		synchronized(placedStructures){
			return placedStructures;
		}
	}

	public void setPlacedStructures(Vector<Long> placedStructures) {
		synchronized(placedStructures){
			this.placedStructures = placedStructures;
		}
	}

	public Vector<Long> getCitizens() {
		synchronized(citizens){
			return citizens;
		}
	}

	public void setCitizens(Vector<Long> citizens) {
		synchronized(citizens){
			this.citizens = citizens;
		}
	}
	
	public void addCitizen(long citizen) {
		synchronized(citizens){
			this.citizens.add(citizen);
		}
	}
	
	public void removeCitizen(long citizen) {
		synchronized(citizens){
			this.citizens.remove(citizen);
		}
	}

	public long getNextElectionDate() {
		return nextElectionDate;
	}

	public void setNextElectionDate(long nextElectionDate) {
		this.nextElectionDate = nextElectionDate;
	}

	public Vector<Long> getElectionCandidates() {
		return electionCandidates;
	}

	public void setElectionCandidates(Vector<Long> electionCandidates) {
		this.electionCandidates = electionCandidates;
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

	public boolean isFounded() {
		return founded;
	}

	public void setFounded(boolean founded) {
		this.founded = founded;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	
	public void sendSpecUpdateMail(int cityID) {
		this.cityID = cityID;
		// city_version_update_subject_4
		// city_version_update_body_4
	}
	

}
