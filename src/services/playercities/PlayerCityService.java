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

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.datatables.Citizenship;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import services.chat.Mail;
import services.sui.SUIWindow;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import main.NGECore;

/** 
 * @author Charon 
 */

public class PlayerCityService implements INetworkDispatch {
	
	private NGECore core;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Random random = new Random();
    private Vector<PlayerCity> playerCities = new Vector<PlayerCity>();
    private int cityID = 0; // This must be persisted or handled via objectservice somehow
    private Map<String, int[]> cityRankCaps = new ConcurrentHashMap<String, int[]>();
    // static helper variable to be deleted later
    public static boolean sandboxCityBuilt = false;
    
	public PlayerCityService(NGECore core) {
		this.core = core;
		core.scriptService.callScript("scripts/", "cityRankCaps", "addCityRankCaps", core);
		scheduler.scheduleAtFixedRate(() -> {
			administratePlayerCities();
		}, 0, 1, TimeUnit.MINUTES);
	}
	
	public void addCityRankCap(String planetName, int[] caps) {
		cityRankCaps.put(planetName, caps);
	}

	public void administratePlayerCities() {
		synchronized(playerCities){
			Vector<PlayerCity> unfoundedCities = new Vector<PlayerCity>();
			for (PlayerCity city : playerCities){
				
				if (city.getNextCityUpdate() <= System.currentTimeMillis())
					city.processCityUpdate();
				
				if (city.getNextElectionDate() <= System.currentTimeMillis())
					city.processElection();
				
				if (! city.isFounded() && city.getFoundationTime()<=System.currentTimeMillis()-(60*60*24*1000)){
					if (! city.checkFoundationSuccess())
						unfoundedCities.add(city);
				}
			}
			
			if (unfoundedCities.size()>0){
				unfoundedCities.forEach(this::destroyCity);
			}			
		}
	}
	
	public boolean isRankCapped(Planet planet, int rank) {
		if(cityRankCaps.get(planet.getName()) == null)
			return true;
		int cap = cityRankCaps.get(planet.getName())[rank - 1];
		return playerCities.stream().filter(c -> c.getPlanetId() == planet.getID()).count() >= cap;
	}
	
	private long generateCityId() {
		boolean found = false;
		long id = 0;
		while(!found) {
			id = random.nextLong();
			final long cityId = id;
			if(!playerCities.stream().filter(c -> c.getCityID() == cityId).findFirst().isPresent())
				found = true;
		}
		return id;
	}
	
	private boolean doesCityNameExist(String name) {
		return playerCities.stream().map(PlayerCity::getCityName).filter(n -> n.equalsIgnoreCase(name)).findFirst().isPresent() || 
			   core.terrainService.getClientRegions().stream().map(ClientRegion::getName).filter(n -> n.equalsIgnoreCase(name)).findFirst().isPresent();
	}
	
	public void handlePlaceCity(final CreatureObject actor, TangibleObject deed, float positionX, float positionZ, float rotation) {
		
		if(!canPlacePlayerCityAtPosition(actor, actor.getPlanet(), new Point3D(positionX, 0, positionZ)))
			return;
		
		if(isRankCapped(actor.getPlanet(), PlayerCity.OUTPOST)) {
			actor.sendSystemMessage("This Planet can not support any more cities", (byte) 0);
			return;
		}
		
		if(actor.getPlayerObject().getCitizenship() == Citizenship.Mayor) {
			actor.sendSystemMessage("@city/city:already_mayor", (byte) 0);
			return;
		}
		
		final SUIWindow window = core.suiService.createInputBox(2,"@city/city:city_name_t","@city/city:city_name_d", actor, null, 0);		
		core.suiService.openSUIWindow(window);				
		Vector<String> returnList = new Vector<String>();		
		returnList.add("txtInput:LocalText");	
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				if (returnList.size() == 0 || returnList.get(0).length() == 0)
					return;
				String name = returnList.get(0);
				if(!core.characterService.checkName(name, owner.getClient())) {
					actor.sendSystemMessage("@player_structure:not_valid_name", (byte) 0);
					return;
				}
				if(doesCityNameExist(name)) {
					actor.sendSystemMessage("@player_structure:cityname_not_unique", (byte) 0);
					return;
				}
				
				BuildingObject cityHall = core.housingService.placeStructure(actor, deed, positionX, positionZ, rotation);
				
				if(cityHall == null) {
					actor.sendSystemMessage("Error: NULL city hall", (byte) 0);
					return;
				}
				
				PlayerCity playerCity = addNewPlayerCity(actor, cityHall);
				cityHall.setAttachment("structureCity", playerCity.getCityID());
				actor.setAttachment("residentCity", playerCity.getCityID());
				core.housingService.declareResidency(actor, cityHall);
				newCitySUI2((CreatureObject) owner, playerCity);

			}					
		});		
		core.suiService.openSUIWindow(window);

	}
	
	public PlayerCity addNewPlayerCity(CreatureObject founder, BuildingObject cityHall) {
		PlayerCity newCity = null;
		synchronized(playerCities){
			newCity = new PlayerCity(founder, generateCityId(), cityHall);
			playerCities.add(newCity);
		}
		return newCity;
	}
	
	public PlayerCity getCityObjectIsIn(SWGObject object) {
		PlayerCity foundCity = null;
		synchronized(playerCities){
			for (PlayerCity city : playerCities){
				int radius = city.getCityRadius();
				float distance = object.getWorldPosition().getDistance2D(city.getCityCenterPosition());
				if (distance<radius){
					foundCity = city;
				}				
			}
		}
		return foundCity;
	}
	
	public PlayerCity getPlayerCity(CreatureObject citizen) {
		PlayerCity foundCity = null;
		synchronized(playerCities){
			for (PlayerCity city : playerCities){
				int id = (int)citizen.getAttachment("residentCity");
				if (city.getCityID()==id){
					foundCity = city;
				}				
			}
		}
		return foundCity;
	}
	
	public boolean canPlacePlayerCityAtPosition(CreatureObject actor, Planet planet, Point3D position) {
		
		PlayerCity closest = playerCities.stream().min((c1, c2) -> (int) (c1.getCityCenterPosition().getDistance2D(position) - c2.getCityCenterPosition().getDistance2D(position))).orElse(null);
		if(closest != null && closest.getCityCenterPosition().getDistance2D(position) < 1024) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@player_structure:city_too_close", "TO", closest.getCityName()), (byte) 0);
			return false;
		}
		ClientRegion closest2 = core.terrainService.getClientRegionsForPlanet(planet).stream().min((c1, c2) -> (int) (c1.getCenter().getDistance2D(position) - c2.getCenter().getDistance2D(position))).orElse(null);
		if(closest2 != null && closest2.getCenter().getDistance2D(position) < 1024) {
			actor.sendSystemMessage(OutOfBand.ProsePackage("@player_structure:city_too_close", "TO", closest2.getName()), (byte) 0);
			return false;
		}
		return true;
	}
	
	public void newCitySUI1(CreatureObject citizen, final PlayerCity newCity) {		
		final SUIWindow window = core.suiService.createInputBox(2,"@city/city:city_name_t","@city/city:city_name_d", citizen, citizen, 0);		
		core.suiService.openSUIWindow(window);				
		Vector<String> returnList = new Vector<String>();		
		returnList.add("txtInput:LocalText");	
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				if (returnList.size()==0)
					return;
				PlayerCity playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity((CreatureObject) owner);	
				playerCity.setCityName(returnList.get(0));
				//owner.sendSystemMessage("@city/city:name_changed", 0);
				core.suiService.closeSUIWindow(owner, 0);
				newCitySUI2((CreatureObject) owner, playerCity);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	public void newCitySUI2(CreatureObject citizen, final PlayerCity newCity) {		
		final SUIWindow window = core.suiService.createSUIWindow("Script.messageBox", citizen, citizen, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@city/city:rank0");
		window.setProperty("Prompt.lblPrompt:Text", "@city/city:new_city_body");		
		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@ok");
		window.setProperty("btnCancel:Text", "@cancel");				
		Vector<String> returnList = new Vector<String>();		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	
	// Test method to be deleted later
	public void buildSandboxTestCity(CreatureObject founder) {		
		
		if (sandboxCityBuilt)
			return;
		
		float positionX = 2170.0F;
		float positionY = 1.0F;
		float positionZ = -4659.0F;
		
		String structureTemplate = "object/building/player/city/shared_cityhall_tatooine.iff";
		
		BuildingObject cityHall = (BuildingObject) core.objectService.createObject(structureTemplate, 0, founder.getPlanet(), new Point3D(positionX, positionY, positionZ), founder.getOrientation());
		core.simulationService.add(cityHall, cityHall.getPosition().x, cityHall.getPosition().z, true);
		
		SWGObject sign = core.objectService.createChildObject((SWGObject)cityHall, "object/tangible/sign/player/shared_house_address.iff", -7.39F, 2.36F, 2, -1, 0, -1);
		cityHall.setAttachment("structureSign", sign);
		
		PlayerCity sandboxCity = null;
		synchronized(playerCities){
			sandboxCity = new PlayerCity(founder,cityID++, cityHall);
			sandboxCity.setCityName("Sandbox City");
			playerCities.add(sandboxCity);
		}
	
		cityHall.setAttachment("structureCity", sandboxCity.getCityID());
		founder.setAttachment("residentCity", sandboxCity.getCityID());
		// Name the sign
//		TangibleObject sign = (TangibleObject) cityHall.getAttachment("structureSign");	
//		String playerFirstName = founder.getCustomName().split(" ")[0];
//		
//		if (sign !=null)
//			sign.setCustomName2(playerFirstName + "'s House");
		

				
		// Structure management
		Vector<Long> admins = new Vector<>();
		admins.add(founder.getObjectID());
		
		cityHall.setAttachment("structureOwner", founder.getObjectID());
		cityHall.setAttachment("structureAdmins", admins);
		cityHall.setDeedTemplate("object/tangible/deed/city_deed/shared_cityhall_tatooine_deed.iff");
		cityHall.setBMR(325);
		cityHall.setConditionDamage(100);
		
		
		positionY = core.terrainService.getHeight(founder.getPlanetId(), positionX, positionZ);
		founder.setPosition(new Point3D(positionX,positionY,positionZ+50));
		core.simulationService.teleport(founder, new Point3D(positionX,positionY,positionZ+50), founder.getOrientation(), 0);
		
		TangibleObject swoopDeed = (TangibleObject) core.objectService.createObject("object/tangible/deed/vehicle_deed/shared_speederbike_swoop_deed.iff", founder.getPlanet());
		SWGObject inventory = founder.getSlottedObject("inventory");
		inventory.add(swoopDeed);
		
		TangibleObject shuttleportDeed = (TangibleObject) core.objectService.createObject("object/tangible/deed/city_deed/shared_shuttleport_tatooine_deed.iff", founder.getPlanet());
		inventory.add(shuttleportDeed);
		
		TangibleObject garden1 = (TangibleObject) core.objectService.createObject("object/tangible/deed/city_deed/shared_garden_tatooine_sml_01_deed.iff", founder.getPlanet());
		inventory.add(garden1);
	
		
		//structureTemplate = "object/building/player/city/shared_shuttleport_tatooine.iff";
		structureTemplate = "object/building/tatooine/shared_shuttleport_tatooine.iff";
		positionX = 2170.0F;
		positionY = 1.0F;
		positionZ = -4559.0F;
		positionY = core.terrainService.getHeight(founder.getPlanetId(), positionX, positionZ);
		
		//StructureObject shuttlePort = (StructureObject) core.objectService.createObject(structureTemplate, 0, founder.getPlanet(), new Point3D(positionX, positionY, positionZ), founder.getOrientation());
		//BuildingObject shuttlePort = (BuildingObject) core.objectService.createObject(structureTemplate, 0, founder.getPlanet(), new Point3D(positionX, positionY, positionZ), founder.getOrientation());
		//shuttlePort.setAttachment("cellsSorted", new Boolean(true));
		//"bigSpawnRange"
		
		//core.simulationService.add(shuttlePort, shuttlePort.getPosition().x, shuttlePort.getPosition().z, true);
//		sign = core.objectService.createChildObject((SWGObject)shuttlePort, "object/tangible/sign/player/shared_house_address.iff", -7.39F, 2.36F, 2, -1, 0, -1);
//		shuttlePort.setAttachment("structureSign", sign);
		
		// Structure management
		admins = new Vector<>();
		admins.add(founder.getObjectID());
		
//		shuttlePort.setAttachment("structureOwner", founder.getObjectID());
//		shuttlePort.setAttachment("structureAdmins", admins);
//		shuttlePort.setDeedTemplate("object/tangible/deed/city_deed/shared_cityhall_tatooine_deed.iff");
//		shuttlePort.setBMR(12);
//		shuttlePort.setConditionDamage(100);
		
		sandboxCityBuilt = true;
	}
	

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0,
			Map<Integer, INetworkRemoteEvent> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	public void destroyCity(PlayerCity city) {
		
		CreatureObject mayor = core.objectService.getObject(city.getMayorID()) == null ? core.objectService.getCreatureFromDB(city.getMayorID()) : (CreatureObject) core.objectService.getObject(city.getMayorID());
		if(mayor == null)
			return;

		Mail mail = new Mail();
		mail.setMailId(NGECore.getInstance().chatService.generateMailId());
		mail.setRecieverId(city.getMayorID());
		mail.setStatus(Mail.NEW);
		mail.setTimeStamp((int) (new Date().getTime() / 1000));
		mail.setSubject("@city/city:new_city_fail_subject");
		mail.setSenderName("@city/city:new_city_from");
		mail.setMessage("@city/city:new_city_fail_body");
		
		if(mayor.getClient() != null)
			core.chatService.sendPersistentMessageHeader(mayor.getClient(), mail);
		core.chatService.storePersistentMessage(mail);
		for(long civicStructureId : new Vector<Long>(city.getPlacedStructures())) {
			BuildingObject civicStructure = (BuildingObject) core.objectService.getObject(civicStructureId);
			if(civicStructure == null)
				continue;
			core.housingService.destroyStructure(civicStructure);
		}

		playerCities.remove(city);
		
	}	
}
