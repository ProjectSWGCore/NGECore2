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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.waypoint.WaypointObject;
import services.chat.Mail;
import services.chat.WaypointAttachment;
import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;

/** 
 * @author Charon 
 */

public class PlayerCity {
	
	public static final int DESERTED   = 0;
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
	public static final int INDUSTRIAL_SOCIETY     = 10; 
	public static final int SCIENTIFIC_SOCIETY     = 11;
	public static final int STRONGHOLD             = 12;
	
	public static final String[] specialisationSTFNames = new String[]{"city_spec_cloning",
																       "city_spec_bm_incubator",
																       "city_spec_storyteller",
																       "city_spec_entertainer",
																       "city_spec_missions",
																       "city_spec_industry",
																       "city_spec_doctor",
																       "city_spec_research",
																       "city_spec_sample_rich",
																       "city_spec_master_manufacturing",
																       "city_spec_master_healing",
																 	   "city_spec_stronghold"};
	
	public static final int BANK              = 1; 
	public static final int CLONING_FACILITY  = 2; 
	public static final int GARAGE            = 3; 
	public static final int MEDIUM_GARDEN     = 4; 
	public static final int LARGE_GARDEN      = 5;
	public static final int SHUTTLEPORT       = 6;
	
	
	private String cityName = "";
	private int cityID = -1;
	private Point3D cityCenterPosition = new Point3D(0,0,0);
	private int cityRadius = 0;
	private int cityRank = 0;
	private int specialization = -1;
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
		// ToDo: handle everything		
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
		// ToDo: Consider 1 rank per update changes
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
			cityTreasury += cumulatedPropertyTax;			
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
			CreatureObject citizenObject = (CreatureObject) NGECore.getInstance().objectService.getObject(founderId);
			if (citizenList.contains(founderId) && (long)citizenObject.getAttachment("residentCity")==this.getCityID())
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

	public int getRank() {
		return cityRank;
	}

	public void setRank(int cityRank) {
		this.cityRank = cityRank;
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
		synchronized(citizens){
			this.citizens.add(citizen);
		}
		CreatureObject citizenObject = (CreatureObject) NGECore.getInstance().objectService.getObject(citizen);
		sendNewCitizenMailAll(citizenObject);
		sendNewCitizenMail(citizenObject);		
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
		sendNameChangeMail(cityName);
	}

	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
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
		for (long citizen : citizenList){
			CreatureObject citizenObject = (CreatureObject) NGECore.getInstance().objectService.getObject(citizen);
			Mail actorMail = new Mail();
	        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
	        actorMail.setRecieverId(citizen);
	        actorMail.setStatus(Mail.NEW);
	        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
	        actorMail.setMessage("@city/city:city_version_update_body_4");
	        actorMail.setSubject("@city/city:city_version_update_subject_4");
	        actorMail.setSenderName("City " + this.cityName);
	        
//	        List<WaypointAttachment> attachments = new ArrayList<WaypointAttachment>(); 
//	        WaypointAttachment attachment = new WaypointAttachment();
//			attachment.active = false;
//			attachments.add(attachment);
//			attachment.cellID = constructionWaypoint.getCellId();
//			attachment.color = (byte)1;
//			attachment.name = "City";
//			attachment.planetCRC = engine.resources.common.CRC.StringtoCRC(citizenObject.getPlanet().getName());
//			attachment.positionX = object.getPosition().x;
//			attachment.positionY = 0;
//			attachment.positionZ = object.getPosition().z;
//			actorMail.setAttachments(attachments);
	        
	        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
//	        if (newCitizen.getClient()!=null)
//	        	NGECore.getInstance().chatService.sendPersistentMessageHeader(newCitizen.getClient(), actorMail);

		}
	}
	
	public void sendNameChangeMail(String name) {		
		
	}
	
	public void sendNewCitizenMailAll(CreatureObject newCitizen) {		

		Vector<Long> citizenList = getCitizens();
		for (long citizen : citizenList){
			CreatureObject citizenObject = (CreatureObject) NGECore.getInstance().objectService.getObject(citizen);
			Mail actorMail = new Mail();
	        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
	        actorMail.setRecieverId(citizen);
	        actorMail.setStatus(Mail.NEW);
	        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
	        actorMail.setMessage("@city/city:new_city_citizen_body");
	        actorMail.setSubject("@city/city:new_city_citizen_subject");
	        actorMail.setSenderName("City " + this.cityName);
	        
	        List<WaypointAttachment> attachments = new ArrayList<WaypointAttachment>(); 
	        WaypointObject constructionWaypoint = (WaypointObject)NGECore.getInstance().objectService.createObject("object/waypoint/shared_world_waypoint_blue.iff", citizenObject.getPlanet(), citizenObject.getPosition().x, 0 ,citizenObject.getPosition().z);
	        WaypointAttachment attachment = new WaypointAttachment();
			attachment.active = false;		
			attachment.cellID = constructionWaypoint.getCellId();
			attachment.color = (byte)1;
			attachment.name = "City";
			attachment.planetCRC = engine.resources.common.CRC.StringtoCRC(citizenObject.getPlanet().getName());
			attachment.positionX = citizenObject.getPosition().x;
			attachment.positionY = 0;
			attachment.positionZ = citizenObject.getPosition().z;
			attachments.add(attachment);
			actorMail.setAttachments(attachments);
	        
	        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
	        if (newCitizen.getClient()!=null)
	        	NGECore.getInstance().chatService.sendPersistentMessageHeader(newCitizen.getClient(), actorMail);

		}
	}
	
	public void sendNewCitizenMail(CreatureObject citizen) {		

		Mail actorMail = new Mail();
        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
        actorMail.setRecieverId(citizen.getObjectID());
        actorMail.setStatus(Mail.NEW);
        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
        actorMail.setMessage("@city/city:new_city_citizen_other_body");
        actorMail.setSubject("@city/city:new_city_citizen_other_subject");
        actorMail.setSenderName("City " + this.cityName);
        
        List<WaypointAttachment> attachments = new ArrayList<WaypointAttachment>(); 
        WaypointObject constructionWaypoint = (WaypointObject)NGECore.getInstance().objectService.createObject("object/waypoint/shared_world_waypoint_blue.iff", citizen.getPlanet(), citizen.getPosition().x, 0 ,citizen.getPosition().z);
        WaypointAttachment attachment = new WaypointAttachment();
		attachment.active = false;		
		attachment.cellID = constructionWaypoint.getCellId();
		attachment.color = (byte)1;
		attachment.name = "City";
		attachment.planetCRC = engine.resources.common.CRC.StringtoCRC(citizen.getPlanet().getName());
		attachment.positionX = citizen.getPosition().x;
		attachment.positionY = 0;
		attachment.positionZ = citizen.getPosition().z;
		attachments.add(attachment);
		actorMail.setAttachments(attachments);

		
        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
        NGECore.getInstance().chatService.sendPersistentMessageHeader(citizen.getClient(), actorMail);
	}
	
	public void sendCitizenLeftMailAll(CreatureObject newCitizen) {		

		Vector<Long> citizenList = getCitizens();
		for (long citizen : citizenList){
			CreatureObject citizenObject = (CreatureObject) NGECore.getInstance().objectService.getObject(citizen);
			Mail actorMail = new Mail();
	        actorMail.setMailId(NGECore.getInstance().chatService.generateMailId());
	        actorMail.setRecieverId(citizen);
	        actorMail.setStatus(Mail.NEW);
	        actorMail.setTimeStamp((int) (new Date().getTime() / 1000));
	        actorMail.setMessage("@city/city:lost_citizen_body");
	        actorMail.setSubject("@city/city:lost_citizen_subject");
	        actorMail.setSenderName("City " + this.cityName);
	        
	        List<WaypointAttachment> attachments = new ArrayList<WaypointAttachment>(); 
	        WaypointObject constructionWaypoint = (WaypointObject)NGECore.getInstance().objectService.createObject("object/waypoint/shared_world_waypoint_blue.iff", citizenObject.getPlanet(), citizenObject.getPosition().x, 0 ,citizenObject.getPosition().z);
	        WaypointAttachment attachment = new WaypointAttachment();
			attachment.active = false;		
			attachment.cellID = constructionWaypoint.getCellId();
			attachment.color = (byte)1;
			attachment.name = "City";
			attachment.planetCRC = engine.resources.common.CRC.StringtoCRC(citizenObject.getPlanet().getName());
			attachment.positionX = citizenObject.getPosition().x;
			attachment.positionY = 0;
			attachment.positionZ = citizenObject.getPosition().z;
			attachments.add(attachment);
			actorMail.setAttachments(attachments);
	        
	        NGECore.getInstance().chatService.storePersistentMessage(actorMail);
	        if (newCitizen.getClient()!=null)
	        	NGECore.getInstance().chatService.sendPersistentMessageHeader(newCitizen.getClient(), actorMail);

		}
	}

}
