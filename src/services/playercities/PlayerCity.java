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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import net.engio.mbassy.listener.Handler;
import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.common.collidables.CollidableCircle;
import resources.common.collidables.AbstractCollidable.EnterEvent;
import resources.common.collidables.AbstractCollidable.ExitEvent;
import resources.datatables.CivicStructures;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import services.chat.Mail;
import main.NGECore;
import engine.clientdata.StfTable;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;

/** 
 * @author Charon 
 */

public class PlayerCity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int OUTPOST    = 1; 
	public static final int VILLAGE    = 2; 
	public static final int TOWNSHIP   = 3; 
	public static final int CITY       = 4; 
	public static final int METROPOLIS = 5; 
	
	public static final int CLONE_LAB              = 1; 
	public static final int DNA_LABORATORY         = 2; 
	public static final int ENCORE_PERFORMANCE     = 3; 
	public static final int ENTERTAINMENT_DISTRICT = 4; 
	public static final int IMPROVED_JOB_MARKET    = 5; 
	public static final int MANUFACTURING_CENTER   = 6; 
	public static final int MEDICAL_CENTER         = 7; 
	public static final int RESEARCH_CENTER        = 8; 
	public static final int SAMPLE_RICH            = 9;
	
	public static final String[] specialisationSTFNames = new String[]{"city_spec_cloning",
																       "city_spec_bm_incubator",
																       "city_spec_storyteller",
																       "city_spec_entertainer",
																       "city_spec_missions",
																       "city_spec_industry",
																       "city_spec_doctor",
																       "city_spec_research",
																       "city_spec_sample_rich"};
	
		
	public static final int[] citizensPerRank = new int[] { 5, 10, 15, 30, 40 };
	
	private String cityName = "";
	private int planetId;
	private long cityHallId = 0;
	private long cityID = -1;
	private Point3D cityCenterPosition = new Point3D(0,0,0);
	private int cityRadius = 150;
	private int cityRank = 1;
	private int specialization = 0;
	private long mayorID = 0L;
	private int cityTreasury = 0;
	private int maintenanceFee = 0;
	private int GCW_Alignment = 0;
	private long nextElectionDate = 0L; 
	private long nextCityUpdate = 0L; 
	private long foundationTime = 0L;
	private int incomeTax = 0;
	private int propertyTax  = 0;
	private int salesTax = 0;
	private int travelTax = 0;
	private int garageTax = 0;
	private boolean founded = false;
	private boolean shuttlePort = false;
	private boolean registered = false;
	private boolean zoningEnabled = false;
	private boolean electionLocked = false; // election is locked in third and final week
	private transient CollidableCircle area;
	private long cityNameChangeCooldown;
	private long cityTreasuryWithdrawalCooldown;

	//public static final long cityUpdateSpan = 7*86400*1000;
	public static final long cityUpdateSpan = 100*1000;
	//public static final long cityUpdateSpan = 86400*1000;
	public static final long newCityGraceSpan = 100*1000;

	//public static final long legislationPeriod = 21*86400*1000;
	public static final long legislationPeriod = 100*1000;
	
	private Vector<Long> placedStructures = new Vector<Long>();
	private Vector<Long> citizens = new Vector<Long>();
	private Map<Long, Integer> electionList = new ConcurrentHashMap<Long, Integer>();
	private Map<Long, Long> mayoralVotes = new ConcurrentHashMap<Long, Long>(); // Key = voter id Value = candidate id
	private Vector<Long> cityBanList = new Vector<Long>();
	private Vector<Long> militiaList = new Vector<Long>();
	private Vector<Long> foundersList = new Vector<Long>();
	
	public PlayerCity() {}
	
	public PlayerCity(CreatureObject founder, long cityId, BuildingObject cityHall) {
		setCityCenterPosition(cityHall.getPosition());
		setCityHallId(cityHall.getObjectID());
		setFoundationTime(System.currentTimeMillis());		
		setMaintenanceFee(1);
		setNextCityUpdate(foundationTime+cityUpdateSpan);
		setNextElectionDate(foundationTime+legislationPeriod);
		this.cityID = cityId;
		setMayorID(founder.getObjectID());
		setPlanetId(cityHall.getPlanetId());
		init();
	}
	
	public void init() {
		area = new CollidableCircle(getCityCenterPosition(), cityRadius, NGECore.getInstance().terrainService.getPlanetByID(planetId));
		area.getEventBus().subscribe(this);
		NGECore.getInstance().simulationService.addCollidable(area, area.getCenter().x, area.getCenter().z);
		if(isRegistered())
			NGECore.getInstance().mapService.addLocation(NGECore.getInstance().terrainService.getPlanetByID(planetId), getCityName(), area.getCenter().x, area.getCenter().z, (byte) 17, (byte) 0, (byte) 0);
	}
		
	public void handleGrantZoning() {
		
	}
	
	public void handleBuildRequest() {
		
	}
	
	public void checkPlacementPermission() {
		
	}
	
	public int getCivicStructuresCount() {
		return (int) placedStructures.stream().
		map(NGECore.getInstance().objectService::getObject).
		filter(o -> o.getAttachment("civicStructureType") != null && (int) o.getAttachment("civicStructureType") > 0).
		count();
	}
	
	public void processElection() {
		// ToDo: handle everything		
		long winnerID = mayorID;
		mayorID = winnerID;
		setNextElectionDate(System.currentTimeMillis()+legislationPeriod);
	}
	
	public void processCityUpdate() {
		// has something changed?
		System.out.println("processCityUpdate for " + cityName);

		fixupCitizens();
		int censusResult = citizens.size();		
		int currentRank = getRank();
		
		int minCitizen = citizensPerRank[currentRank - 1];

		if(censusResult < minCitizen) {
			contractCity();
		} else {
			if(getRank() != METROPOLIS && citizensPerRank[currentRank] <= censusResult)
				expandCity();
		}
		
		NGECore core = NGECore.getInstance();
		// collect taxes
		if(core.objectService.getObject(cityHallId) == null)
			return;
		for (long citizen : citizens) {
			CreatureObject citizenObject = core.objectService.getObject(citizen) == null ? core.objectService.getCreatureFromDB(citizen) : (CreatureObject) core.objectService.getObject(citizen);
			if(citizenObject == null)
				continue;
			// ToDo: Handle tax evaders etc.
			int incomeTax = getIncomeTax();
			citizenObject.setBankCredits(citizenObject.getBankCredits()-incomeTax);
			cityTreasury += incomeTax;
			
			int propertyTaxRate = getPropertyTax()/100;
			Vector<BuildingObject> allStructuresOfCitizen = new Vector<BuildingObject>(); // Collect the structures 
			int cumulatedPropertyTax = 0;
			for (BuildingObject structure : allStructuresOfCitizen){
				cumulatedPropertyTax += propertyTaxRate*structure.getBMR();
			}
			
			citizenObject.setBankCredits(citizenObject.getBankCredits()-cumulatedPropertyTax);
			if(core.objectService.getObject(citizen) == null) 
				core.objectService.persistObject(citizen, citizenObject, core.getSWGObjectODB());
			cityTreasury += cumulatedPropertyTax;			
		}
		demolishHighRankStructures();
		calculateAndPayMaintenance();
		core.playerCityService.schedulePlayerCityUpdate(this, cityUpdateSpan);
		setNextCityUpdate(System.currentTimeMillis()+cityUpdateSpan);
	}
	
	public void contractCity() {
		
		NGECore core = NGECore.getInstance();
		int newRank = getRank() - 1;
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;

		if(newRank < 1) {
			core.housingService.destroyStructure((BuildingObject) core.objectService.getObject(cityHallId));
			return;
		}
		if(newRank < 3 && isRegistered()) {
			setRegistered(false);
			core.mapService.removeLocation(core.terrainService.getPlanetByID(planetId), getCityCenterPosition().x, getCityCenterPosition().z, (byte) 17);
		}
        setRank(newRank);
        demolishCivicStructuresOutsideRadius();
        demolishHighRankStructures();
        sendCityContractMail();
		fixupCitizens();
	}
	
	private void demolishCivicStructuresOutsideRadius() {
		NGECore core = NGECore.getInstance();
		List<SWGObject> remove = new ArrayList<SWGObject>();
		placedStructures.stream().map(core.objectService::getObject)
		.filter(o -> o.getAttachment("isCivicStructure") != null && (boolean) o.getAttachment("isCivicStructure"))
		.filter(o -> !area.doesCollide(o)).forEach(remove::add);
		remove.forEach(b -> core.housingService.destroyStructure((BuildingObject) b));
	}

	public void expandCity() {
		
		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;

		if(getRank() == METROPOLIS)
			return;
		
		int newRank = getRank() + 1;
		boolean rankCapped = core.playerCityService.isRankCapped(core.terrainService.getPlanetByID(planetId), newRank);
		sendCityExpandMail(rankCapped);

		if(!rankCapped) {
			setRank(newRank);
			addNewStructures();
			fixupCitizens();
		}
		
	}
	
	private void addNewStructures() {
		NGECore core = NGECore.getInstance();
		List<SWGObject> objects = core.simulationService.get(core.terrainService.getPlanetByID(planetId), area.getCenter().x, area.getCenter().z, 500);
		objects.stream().filter(o -> o instanceof BuildingObject && o.getAttachment("structureOwner") != null && area.doesCollide(o)).map(SWGObject::getObjectID).forEach(this::addNewStructure);
	}

	// cleanup in case something went wrong
	public void fixupCitizens() {
		List<Long> residents = new ArrayList<Long>();
		List<CreatureObject> added = new ArrayList<CreatureObject>();
		List<CreatureObject> removed = new ArrayList<CreatureObject>();
		// TODO: add extra mail for deleted characters
		
		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;

		for (long structureID : new Vector<Long>(placedStructures)) {
			BuildingObject structure = (BuildingObject) NGECore.getInstance().objectService.getObject(structureID);
			if(structure == null)
				return;
			if(structure.getResidency())
				residents.add((Long) structure.getAttachment("structureOwner"));
		}
		for(long citizenId : new Vector<Long>(citizens)) {
			if(!residents.contains(citizenId)) {
				removeCitizen(citizenId, false);
				CreatureObject citizenObject = core.objectService.getObject(citizenId) == null ? core.objectService.getCreatureFromDB(citizenId) : (CreatureObject) core.objectService.getObject(citizenId);
				if(citizenObject != null)
					removed.add(citizenObject);
			}
		}
		for(long residentId : residents) {
			if(!citizens.contains(residentId)) {
				addCitizen(residentId, false);
				CreatureObject citizenObject = core.objectService.getObject(residentId) == null ? core.objectService.getCreatureFromDB(residentId) : (CreatureObject) core.objectService.getObject(residentId);
				if(citizenObject != null)
					added.add(citizenObject);
			}
		}
		
		String addTT = "";
		for(CreatureObject addedCitizen : added) {
			if(added.indexOf(addedCitizen) == added.size() - 1)
				addTT += addedCitizen.getCustomName();
			else
				addTT += (addedCitizen.getCustomName() + ", ");
		}
		
		String removedTT = "";
		for(CreatureObject removedCitizen : removed) {
			if(removed.indexOf(removedCitizen) == removed.size() - 1)
				removedTT += removedCitizen.getCustomName();
			else
				removedTT += (removedCitizen.getCustomName() + ", ");
		}
		
		if(added.size() > 0) {
			Mail mail = new Mail();
			mail.setMailId(core.chatService.generateMailId());
			mail.setRecieverId(getMayorID());
			mail.setStatus(Mail.NEW);
	        mail.setTimeStamp((int) (new Date().getTime() / 1000));
	        mail.setSubject("@city/city:city_fixup_add_citizens_subject");
	        mail.setSenderName("@city/city:new_city_from");
	        mail.addProseAttachment(new ProsePackage("@city/city:city_fixup_add_citizens_body", "TT", addTT));
	        
	        core.chatService.storePersistentMessage(mail);
			if(mayor.getClient() != null)
				core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);
		}
		
		if(removed.size() > 0) {
			Mail mail = new Mail();
			mail.setMailId(core.chatService.generateMailId());
			mail.setRecieverId(getMayorID());
			mail.setStatus(Mail.NEW);
	        mail.setTimeStamp((int) (new Date().getTime() / 1000));
	        mail.setSubject("@city/city:city_fixup_remove_citizens_subject");
	        mail.setSenderName("@city/city:new_city_from");
	        mail.addProseAttachment(new ProsePackage("@city/city:city_fixup_remove_citizens_body", "TT", removedTT));
	        
	        core.chatService.storePersistentMessage(mail);
			if(mayor.getClient() != null)
				core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);
		}


	}
	
	public void demolishHighRankStructures() {
		// Check if there are any structures in the city
		// that require a higher rank than the current one
		synchronized(placedStructures){
			Vector<SWGObject> wreckingBall = new Vector<SWGObject>();
			for (long structureID : placedStructures) {
				SWGObject structure = NGECore.getInstance().objectService.getObject(structureID);
				if(structure.getAttachment("civicStructureType") == null || (int) structure.getAttachment("civicStructureType") == 0)
					return;
				int structureCode = (int) structure.getAttachment("civicStructureType");
				if(getRank() < CivicStructures.rankRequired[structureCode - 1])
					wreckingBall.add(structure);
			}
			
			for (SWGObject wreck : wreckingBall){
				placedStructures.remove(wreck.getObjectID());
				NGECore.getInstance().housingService.destroyStructure((BuildingObject) wreck);
			}			
		}		
	}
	
	public boolean checkFoundationSuccess() {
		
		Vector<Long> founders = getFoundersList();
		Vector<Long> citizenList = getCitizens();
		int founderCitizenCount = 0;
		NGECore core = NGECore.getInstance();
		for (long founderId : founders){
			CreatureObject citizenObject = core.objectService.getObject(founderId) == null ? core.objectService.getCreatureFromDB(founderId) : (CreatureObject) core.objectService.getObject(founderId);
			if(citizenObject == null)
				continue;
			if (citizenList.contains(founderId) && (long)citizenObject.getAttachment("residentCity")==this.getCityID())
				founderCitizenCount++;
		}
		if (founderCitizenCount==founders.size()){
			return true; // All founders are citizens now
		}
		
		return false;
	}
	
	public void calculateAndPayMaintenance() {
		NGECore core = NGECore.getInstance();
		// 1. Pay City Hall Maintenance
		int maintAmount = 0;
		BuildingObject cityHall = (BuildingObject) core.objectService.getObject(cityHallId);
		int cityHallMaintenance = 1000 + getRank() * 2500;
		if(registered)
			cityHallMaintenance += 5000;
		cityHallMaintenance += getSpecializationMaintenance();
		maintAmount += deductCivicStructureMaintenance(cityHall, cityHallMaintenance);
		
		for(long placedStructures : new Vector<Long>(placedStructures)) {
			BuildingObject building = (BuildingObject) core.objectService.getObject(placedStructures);
			if(building != null)
				maintAmount += deductCivicStructureMaintenance(building, building.getBMR());
		}

		sendMaintenanceMail(maintAmount);
	}
	
	private int getSpecializationMaintenance() {
		
		switch(specialization) {
		
			case CLONE_LAB:
				return 80000;
			case DNA_LABORATORY:
				return 200000;
			case ENCORE_PERFORMANCE:
				return 200000;
			case ENTERTAINMENT_DISTRICT:
				return 80000;
			case IMPROVED_JOB_MARKET:
				return 80000;
			case MANUFACTURING_CENTER:
				return 50000;
			case MEDICAL_CENTER:
				return 80000;
			case RESEARCH_CENTER:
				return 125000;
			case SAMPLE_RICH:
				return 70000;
			default:
				return 0;
		}
		
	}
	
	private int deductCivicStructureMaintenance(BuildingObject civicStructure, int amount) {
		int maintOwed = civicStructure.getOutstandingMaintenance();
		int amountPaid = 0;
		if(amount > cityTreasury) {
			int decay = civicStructure.getConditionDamage();
			int amountToPay = amount - cityTreasury;
			int nextDecay = (int) (amountToPay / amount * civicStructure.getMaximumCondition() * 0.25f); 
			civicStructure.setOutstandingMaintenance(maintOwed - amountToPay);
			civicStructure.setConditionDamage(decay + nextDecay);
			amountPaid = cityTreasury;
			cityTreasury = 0;
			sendStructureDecayMail(civicStructure, amount);
		} else {
			cityTreasury -= amount;
			amountPaid = amount;
			if(maintOwed > 0) {
				if(maintOwed > cityTreasury) {
					amountPaid = cityTreasury;
					int decay = civicStructure.getConditionDamage();
					if(decay > 0) {
						civicStructure.setConditionDamage(cityTreasury / (maintOwed / decay));
						civicStructure.setOutstandingMaintenance(maintOwed - cityTreasury);
						cityTreasury = 0;
					}
				} else {
					amountPaid += maintOwed;
					civicStructure.setOutstandingMaintenance(0);
					cityTreasury -= maintOwed;
					civicStructure.setConditionDamage(0);
				}
				sendStructureDecayRepairMail(civicStructure);
			}
		}
		if(civicStructure.getConditionDamage() >= civicStructure.getMaximumCondition()) {
			placedStructures.remove(civicStructure);
			NGECore.getInstance().housingService.destroyStructure(civicStructure);
			sendStructureDecayDestroyMail(civicStructure);
		}
		return amountPaid;
	}
	
	public void handleBanRequest() {
		
	}
	
	public void handlePardonRequest() {
		
	}

	public int getRank() {
		return cityRank;
	}

	public void setRank(int cityRank) {
		this.cityRank = cityRank;
		switch(cityRank) {
		
			case OUTPOST:
				setCityRadius(150);				
			case VILLAGE:
				setCityRadius(200);				
			case TOWNSHIP:
				setCityRadius(300);				
			case CITY:
				setCityRadius(400);				
			case METROPOLIS:
				setCityRadius(450);				
			default:
				setCityRadius(150);
		}
	}

	public int getSpecialization() {
		return specialization;
	}
	
	public void setSpecialization(int specialization) {
		this.specialization = specialization;
		sendSpecUpdateMail(specialization);
	}

	public long getMayorID() {
		return mayorID;
	}

	public void setMayorID(long mayorID) {
		this.mayorID = mayorID;
	}

	public synchronized int getCityTreasury() {
		return cityTreasury;
	}

	public synchronized void setCityTreasury(int cityTreasury) {
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
	
	public void addNewStructure(long newStructure) {
		synchronized(placedStructures){
			this.placedStructures.add(newStructure);
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
		addCitizen(citizen, true);
	}
	
	public void removeCitizen(long citizen) {
		removeCitizen(citizen, true);
	}
	
	public void addCitizen(long citizen, boolean sendMail) {
		citizens.add(citizen);
		NGECore core = NGECore.getInstance();
		CreatureObject citizenObject = core.objectService.getObject(citizen) == null ? core.objectService.getCreatureFromDB(citizen) : (CreatureObject) core.objectService.getObject(citizen);
		if(citizenObject == null)
			return;
		if(sendMail) {
			sendNewCitizenMailAll(citizenObject);
			sendNewCitizenMail(citizenObject);
		}
	}
	
	public void removeCitizen(long citizen, boolean sendMail) {
		citizens.remove(citizen);
		removeMilitia(citizen);
		NGECore core = NGECore.getInstance();
		CreatureObject citizenObject = core.objectService.getObject(citizen) == null ? core.objectService.getCreatureFromDB(citizen) : (CreatureObject) core.objectService.getObject(citizen);
		if(citizenObject == null)
			return;
		if(sendMail) {
			sendCitizenLeftMailAll(citizenObject);
		}
	}
	
	public void addMilitia(long militiaId) {
		militiaList.add(militiaId);
	}

	public void removeMilitia(long militiaId) {
		militiaList.remove(militiaId);
	}

	public long getNextElectionDate() {
		return nextElectionDate;
	}

	public void setNextElectionDate(long nextElectionDate) {
		this.nextElectionDate = nextElectionDate;
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

	public synchronized void addToTreasury(int amountToAdd) {
		cityTreasury += amountToAdd;
	}
	
	public synchronized void removeFromTreasury(int amountToDeduct) {
		cityTreasury -= amountToDeduct;
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
		area.setRadius(cityRadius);
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
		sendNameChangeMail(cityName);
		return;
	}

	public long getCityID() {
		return cityID;
	}

	public void setCityID(long cityID) {
		this.cityID = cityID;
	}
	
	public int getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(int incomeTax) {
		this.incomeTax = incomeTax;
	}

	public int getPropertyTax() {
		return propertyTax;
	}

	public void setPropertyTax(int propertyTax) {
		this.propertyTax = propertyTax;
	}

	public int getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(int salesTax) {
		this.salesTax = salesTax;
	}

	public int getTravelTax() {
		return travelTax;
	}

	public void setTravelTax(int travelTax) {
		this.travelTax = travelTax;
	}

	public int getGarageTax() {
		return garageTax;
	}

	public void setGarageTax(int garageTax) {
		this.garageTax = garageTax;
	}

	public boolean hasShuttlePort() {
		return shuttlePort;
	}

	public void setShuttlePort(boolean shuttlePort) {
		this.shuttlePort = shuttlePort;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public boolean isZoningEnabled() {
		return zoningEnabled;
	}

	public void setZoningEnabled(boolean zoningEnabled) {
		this.zoningEnabled = zoningEnabled;
	}
	
	public boolean isMilitiaMember(long actor){
		if (getMilitiaList().contains(actor))
			return true;
		
		return false;
	}
	
	public static String[] getSpecialisationSTFNames() {
		return specialisationSTFNames;
	}
	
	public List<String> getSpecializationSTFNamesAsList() {
		return Arrays.asList(specialisationSTFNames);
	}
	
	public void sendSpecUpdateMail(int cityID) {
		// city_version_update_subject_4
		// city_version_update_body_4
		Vector<Long> citizenList = getCitizens();
		NGECore core = NGECore.getInstance();
		for (long citizen : citizenList){
			CreatureObject citizenObject = core.objectService.getObject(citizen) == null ? core.objectService.getCreatureFromDB(citizen) : (CreatureObject) core.objectService.getObject(citizen);
			if(citizenObject == null)
				continue;
			Mail actorMail = new Mail();
	        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
	        actorMail.setRecieverId(citizen);
	        actorMail.setStatus(Mail.NEW);
	        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
	        actorMail.setMessage("@city/city:city_version_update_body_4");
	        actorMail.setSubject("@city/city:city_version_update_subject_4");
	        actorMail.setSenderName("City: " + this.cityName);
	        /*
	        List<WaypointAttachment> attachments = new ArrayList<WaypointAttachment>(); 
	        WaypointAttachment attachment = new WaypointAttachment();
			attachment.active = false;
			attachments.add(attachment);
			attachment.cellID = 0;
			attachment.color = (byte)1;
			attachment.name = "City";
			attachment.planetCRC = engine.resources.common.CRC.StringtoCRC(citizenObject.getPlanet().getName());
			attachment.positionX = object.getPosition().x;
			attachment.positionY = 0;
			attachment.positionZ = object.getPosition().z;
			actorMail.setWaypointAttachments(attachments);*/
	        
	        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
	        if (citizenObject.getClient()!=null)
	        	NGECore.getInstance().chatService.sendPersistentMessageHeader(citizenObject.getClient(), actorMail);

		}
	}
	
	public void sendNameChangeMail(String name) {		
		
	}
	
	public void sendNewCitizenMailAll(CreatureObject newCitizen) {		

		Vector<Long> citizenList = getCitizens();
		NGECore core = NGECore.getInstance();
		for (long citizen : citizenList){
			CreatureObject citizenObject = core.objectService.getObject(citizen) == null ? core.objectService.getCreatureFromDB(citizen) : (CreatureObject) core.objectService.getObject(citizen);
			if(citizenObject == null)
				continue;
			Mail actorMail = new Mail();
	        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
	        actorMail.setRecieverId(citizen);
	        actorMail.setStatus(Mail.NEW);
	        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
	        actorMail.setSubject("@city/city:new_city_citizen_subject");
	        actorMail.setSenderName("City " + this.cityName);
	        actorMail.addProseAttachment(new ProsePackage("@city/city:new_city_citizen_body", "TO", newCitizen.getCustomName()));
	        
	        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
	        if (newCitizen.getClient()!=null)
	        	NGECore.getInstance().chatService.sendPersistentMessageHeader(newCitizen.getClient(), actorMail);

		}
	}
	
	public void sendNewCitizenMail(CreatureObject citizen) {	
		
		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;

		Mail actorMail = new Mail();
        actorMail.setMailId(core.chatService.generateMailId());
        actorMail.setRecieverId(citizen.getObjectID());
        actorMail.setStatus(Mail.NEW);
        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
        actorMail.setSubject("@city/city:new_city_citizen_other_subject");
        actorMail.setSenderName("City " + this.cityName);
        actorMail.addProseAttachment(new ProsePackage("@city/city:new_city_citizen_other_body", "TT", mayor.getCustomName(), "TU", cityName));
      		
        core.chatService.storePersistentMessage(actorMail);
        if (citizen.getClient()!=null)
        	core.chatService.sendPersistentMessageHeader(citizen.getClient(), actorMail);
	}
	
	public void sendCitizenLeftMailAll(CreatureObject newCitizen) {		

		Vector<Long> citizenList = getCitizens();
		NGECore core = NGECore.getInstance();
		for (long citizen : citizenList){
			CreatureObject citizenObject = core.objectService.getObject(citizen) == null ? core.objectService.getCreatureFromDB(citizen) : (CreatureObject) core.objectService.getObject(citizen);
			if(citizenObject == null)
				continue;
			Mail actorMail = new Mail();
	        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
	        actorMail.setRecieverId(citizen);
	        actorMail.setStatus(Mail.NEW);
	        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
	        actorMail.setSubject("@city/city:lost_citizen_subject");
	        actorMail.setSenderName("City " + this.cityName);
	        actorMail.addProseAttachment(new ProsePackage("@city/city:lost_citizen_body", "TO", newCitizen.getCustomName()));
	        	        
	        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
	        if (citizenObject.getClient()!=null)
	        	NGECore.getInstance().chatService.sendPersistentMessageHeader(citizenObject.getClient(), actorMail);

		}
	}
	
	public void sendCityExpandMail(boolean rankCapped) {		

		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;
		Mail mail = new Mail();
		mail.setMailId(NGECore.getInstance().chatService.generateMailId());
		mail.setRecieverId(getMayorID());
		mail.setStatus(Mail.NEW);
        mail.setTimeStamp((int) (new Date().getTime() / 1000));
        mail.setSenderName("@city/city:new_city_from");
        if(rankCapped) {
	        mail.setSubject("@city/city:city_expand_cap_subject");
	        mail.addProseAttachment(new ProsePackage("@city/city:city_expand_cap_body", "TO", cityName, cityRank + 1));        	
        } else {
	        mail.setSubject("@city/city:city_expand_subject");
	        mail.addProseAttachment(new ProsePackage("@city/city:city_expand_body", "TO", cityName, cityRank));
        }
        core.chatService.storePersistentMessage(mail);
		if(mayor.getClient() != null)
			core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);
	}
	
	public void sendCityContractMail() {	
		
		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;
		Mail mail = new Mail();
		mail.setMailId(NGECore.getInstance().chatService.generateMailId());
		mail.setRecieverId(getMayorID());
		mail.setStatus(Mail.NEW);
        mail.setTimeStamp((int) (new Date().getTime() / 1000));
        mail.setSubject("@city/city:city_contract_subject");
        mail.setSenderName("@city/city:new_city_from");
        mail.addProseAttachment(new ProsePackage("@city/city:city_contract_body", "TO", cityName, cityRank));
        
        core.chatService.storePersistentMessage(mail);
		if(mayor.getClient() != null)
			core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);

	}
	
	
	
	
	// Test method to be deleted later
	public void Add10MoreCitizens() {	
		synchronized(citizens){
			for (int i=0;i<10;i++){			
				this.citizens.add(this.getMayorID());
			}
			
		}
	}
	
	
	// Test method to be deleted later
	public void Deduct10Citizens() {	
		int citizenCount = this.getCitizens().size();
		synchronized(citizens){
			this.citizens.clear();
			
			for (int i=0;i<citizenCount-10;i++){			
				this.citizens.add(this.getMayorID());
			}
		}		
	}

	public void removeStructure(long objectID) {
		placedStructures.remove(objectID);
	}

	public CollidableCircle getArea() {
		return area;
	}

	public void setArea(CollidableCircle area) {
		this.area = area;
	}

	public long getCityHallId() {
		return cityHallId;
	}

	public void setCityHallId(long cityHallId) {
		this.cityHallId = cityHallId;
	}
	
	public String getSpecialisationStfValue() {
		if(specialization == 0)
			return "";
		try {
			StfTable stf = new StfTable("clientdata/string/en/city/city.stf");
			
			for (int s = 1; s < stf.getRowCount(); s++) {
				String stfKey = stf.getStringById(s).getKey();
				
				if (stfKey != null && stfKey != "" && stfKey.equals(specialisationSTFNames[specialization - 1])) {
					return stf.getStringById(s).getValue();
				}
			}
			
        } catch (Exception e) {
                e.printStackTrace();
        }
		return "";
	}
	
	public String getCityRankStfValue() {
		try {
			StfTable stf = new StfTable("clientdata/string/en/city/city.stf");
			
			for (int s = 1; s < stf.getRowCount(); s++) {
				String stfKey = stf.getStringById(s).getKey();
				
				if (stfKey != null && stfKey != "" && stfKey.equals("rank" + String.valueOf(cityRank))) {
					return stf.getStringById(s).getValue();
				}
			}
			
        } catch (Exception e) {
                e.printStackTrace();
        }
		return "";
	}

	
	@Handler
	public void onEnter(EnterEvent event) {
		SWGObject object = event.object;
		
		if(object == null || !(object instanceof CreatureObject))
			return;
		
		String rank = getCityRankStfValue();
		String specialisation = getSpecialisationStfValue();
		if(specialisation.length() == 0)
			((CreatureObject) object).sendSystemMessage(OutOfBand.ProsePackage("@city/city:city_enter_city", "TT", getCityName(), "TO", rank), (byte) 0);
		else
			((CreatureObject) object).sendSystemMessage(OutOfBand.ProsePackage("@city/city:city_enter_city", "TT", getCityName(), "TO", rank + ", " + specialisation), (byte) 0);

		// TODO: apply specialisation skill mods
	}
	
	@Handler
	public void onExit(ExitEvent event) {
		SWGObject object = event.object;
		
		if(object == null || !(object instanceof CreatureObject))
			return;

		((CreatureObject) object).sendSystemMessage(OutOfBand.ProsePackage("@city/city:city_leave_city", "TO", getCityName()), (byte) 0);

		// TODO: remove specialisation skill mods

	}	
	private void sendMaintenanceMail(int amount) {
		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;
		Mail mail = new Mail();
		mail.setMailId(NGECore.getInstance().chatService.generateMailId());
		mail.setRecieverId(getMayorID());
		mail.setStatus(Mail.NEW);
        mail.setTimeStamp((int) (new Date().getTime() / 1000));
        mail.setSubject("@city/city:city_maint_subject");
        mail.setSenderName("@city/city:treasury_withdraw_from");
        mail.addProseAttachment(new ProsePackage("@city/city:city_maint_body", "TO", mayor.getObjectID(), amount));
        
        core.chatService.storePersistentMessage(mail);
		if(mayor.getClient() != null)
			core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);

	}

	
	private void sendStructureDecayRepairMail(BuildingObject building) {
		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;
		Mail mail = new Mail();
		mail.setMailId(NGECore.getInstance().chatService.generateMailId());
		mail.setRecieverId(getMayorID());
		mail.setStatus(Mail.NEW);
        mail.setTimeStamp((int) (new Date().getTime() / 1000));
        mail.setSubject("@city/city:structure_repaired_subject");
        mail.setSenderName("@city/city:treasury_withdraw_from");
        mail.addProseAttachment(new ProsePackage("@city/city:structure_repaired_body", "TO", mayor.getObjectID(), "TT", building.getLookAtText()));
        
        core.chatService.storePersistentMessage(mail);
		if(mayor.getClient() != null)
			core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);

	}

	private void sendStructureDecayMail(BuildingObject building, int amount) {
		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;
		Mail mail = new Mail();
		mail.setMailId(NGECore.getInstance().chatService.generateMailId());
		mail.setRecieverId(getMayorID());
		mail.setStatus(Mail.NEW);
        mail.setTimeStamp((int) (new Date().getTime() / 1000));
        mail.setSubject("@city/city:structure_damaged_subject");
        mail.setSenderName("@city/city:treasury_withdraw_from");
        mail.addProseAttachment(new ProsePackage("@city/city:structure_damaged_body", "TO", mayor.getObjectID(), "TT", building.getObjectName().getString(), amount));
        
        core.chatService.storePersistentMessage(mail);
		if(mayor.getClient() != null)
			core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);

	}

	
	private void sendStructureDecayDestroyMail(BuildingObject building) {
		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;
		Mail mail = new Mail();
		mail.setMailId(NGECore.getInstance().chatService.generateMailId());
		mail.setRecieverId(getMayorID());
		mail.setStatus(Mail.NEW);
        mail.setTimeStamp((int) (new Date().getTime() / 1000));
        mail.setSubject("@city/city:structure_destroyed_maint_subject");
        mail.setSenderName("@city/city:treasury_withdraw_from");
        mail.addProseAttachment(new ProsePackage("@city/city:structure_destroyed_maint_body", "TO", mayor.getObjectID(), "TT", building.getObjectName().getString()));
        
        core.chatService.storePersistentMessage(mail);
		if(mayor.getClient() != null)
			core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);

	}

	public int getPlanetId() {
		return planetId;
	}

	public void setPlanetId(int planetId) {
		this.planetId = planetId;
	}

	public Map<Long, Integer> getElectionList() {
		return electionList;
	}

	public void setElectionList(Map<Long, Integer> electionList) {
		this.electionList = electionList;
	}
	
	public int getReqCitizenCountForRank(int rank) {
		return citizensPerRank[rank - 1];
	}

	public long getCityNameChangeCooldown() {
		return cityNameChangeCooldown;
	}

	public void setCityNameChangeCooldown(long cityNameChangeCooldown) {
		this.cityNameChangeCooldown = cityNameChangeCooldown;
	}

	public long getCityTreasuryWithdrawalCooldown() {
		return cityTreasuryWithdrawalCooldown;
	}

	public void setCityTreasuryWithdrawalCooldown(long cityTreasuryWithdrawalCooldown) {
		this.cityTreasuryWithdrawalCooldown = cityTreasuryWithdrawalCooldown;
	}

	public void sendTreasuryWithdrawalMail(CreatureObject mayor, int amount, String reason) {
		Vector<Long> citizenList = getCitizens();
		NGECore core = NGECore.getInstance();
		for (long citizen : citizenList){
			CreatureObject citizenObject = core.objectService.getObject(citizen) == null ? core.objectService.getCreatureFromDB(citizen) : (CreatureObject) core.objectService.getObject(citizen);
			if(citizenObject == null)
				continue;
			Mail actorMail = new Mail();
	        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
	        actorMail.setRecieverId(citizen);
	        actorMail.setStatus(Mail.NEW);
	        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
	        actorMail.setSubject("@city/city:treasury_withdraw_subject");
	        actorMail.setSenderName("@city/city:treasury_withdraw_from");
	        actorMail.addProseAttachment(new ProsePackage("@city/city:treasury_withdraw_body", "TO", mayor.getCustomName(), "TT", reason, amount));
	        
	        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
	        if (citizenObject.getClient() != null)
	        	NGECore.getInstance().chatService.sendPersistentMessageHeader(citizenObject.getClient(), actorMail);

		}

	}
	
	public void sendTreasuryDepositMail(CreatureObject actor, int amount) {
		NGECore core = NGECore.getInstance();
		CreatureObject mayor = core.objectService.getObject(getMayorID()) == null ? core.objectService.getCreatureFromDB(getMayorID()) : (CreatureObject) core.objectService.getObject(getMayorID());
		if(mayor == null)
			return;
		Mail mail = new Mail();
		mail.setMailId(NGECore.getInstance().chatService.generateMailId());
		mail.setRecieverId(getMayorID());
		mail.setStatus(Mail.NEW);
        mail.setTimeStamp((int) (new Date().getTime() / 1000));
        mail.setSubject("@city/city:treasury_deposit_subject");
        mail.setSenderName("@city/city:treasury_deposit_from");
        mail.addProseAttachment(new ProsePackage("@city/city:treasury_deposit_body", "TO", actor.getCustomName(), amount));
        
        core.chatService.storePersistentMessage(mail);
		if(mayor.getClient() != null)
			core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);		
	}
	
	public boolean isCitizen(long citizenId) {
		return getCitizens().contains(citizenId);
	}
	
	public boolean isCandidate(long candidateId) {
		return electionList.containsKey(candidateId);
	}

	public void castVote(CreatureObject actor, CreatureObject candidate) {
		if(mayoralVotes.get(actor.getObjectID()) == candidate.getObjectID())
			return;
		if(mayoralVotes.containsKey(actor.getObjectID()) && electionList.containsKey(mayoralVotes.get(actor.getObjectID())))
			electionList.put(mayoralVotes.get(actor.getObjectID()), electionList.get(mayoralVotes.get(actor.getObjectID())) - 1);
		mayoralVotes.put(actor.getObjectID(), candidate.getObjectID());
		electionList.put(candidate.getObjectID(), electionList.get(candidate.getObjectID()) + 1);
	}

	public boolean isElectionLocked() {
		return electionLocked;
	}

	public void setElectionLocked(boolean electionLocked) {
		this.electionLocked = electionLocked;
	}
	
	public void sendCandidateRegisteredMail(CreatureObject candidate) {
		Vector<Long> citizenList = getCitizens();
		NGECore core = NGECore.getInstance();
		for (long citizen : citizenList) {
			if(citizen == candidate.getObjectID())
				continue;
			CreatureObject citizenObject = core.objectService.getObject(citizen) == null ? core.objectService.getCreatureFromDB(citizen) : (CreatureObject) core.objectService.getObject(citizen);
			if(citizenObject == null)
				continue;
			Mail actorMail = new Mail();
	        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
	        actorMail.setRecieverId(citizen);
	        actorMail.setStatus(Mail.NEW);
	        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
	        actorMail.setSubject("@city/city:registered_citizen_email_subject");
	        actorMail.setSenderName("@city/city:new_city_from");
	        actorMail.addProseAttachment(new ProsePackage("@city/city:rceb", "TO", candidate.getCustomName()));
	        
	        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
	        if (citizenObject.getClient() != null)
	        	NGECore.getInstance().chatService.sendPersistentMessageHeader(citizenObject.getClient(), actorMail);

		}

	}

	public void sendCandidateUnregisteredMail(CreatureObject candidate) {
		Vector<Long> citizenList = getCitizens();
		NGECore core = NGECore.getInstance();
		for (long citizen : citizenList) {
			if(citizen == candidate.getObjectID())
				continue;
			CreatureObject citizenObject = core.objectService.getObject(citizen) == null ? core.objectService.getCreatureFromDB(citizen) : (CreatureObject) core.objectService.getObject(citizen);
			if(citizenObject == null)
				continue;
			Mail actorMail = new Mail();
	        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
	        actorMail.setRecieverId(citizen);
	        actorMail.setStatus(Mail.NEW);
	        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
	        actorMail.setSubject("@city/city:unregistered_citizen_email_subject");
	        actorMail.setSenderName("@city/city:new_city_from");
	        actorMail.addProseAttachment(new ProsePackage("@city/city:unregistered_citizen_email_body", "TO", candidate.getCustomName()));
	        
	        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
	        if (citizenObject.getClient() != null)
	        	NGECore.getInstance().chatService.sendPersistentMessageHeader(citizenObject.getClient(), actorMail);

		}

	}

	
}
