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
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import resources.common.OutOfBand;
import resources.common.RGB;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import services.sui.SUIWindow;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import main.NGECore;

public class PlayerCityService implements INetworkDispatch {
	
	private NGECore core;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    private Vector<PlayerCity> playerCities = new Vector<PlayerCity>();
    private int cityID = 0; // This must be persisted or handled via objectservice somehow

	public PlayerCityService(NGECore core) {
		this.core = core;
		
		scheduler.scheduleAtFixedRate(() -> {
			administratePlayerCities();
		}, 0, 1, TimeUnit.MINUTES);
	}

	public void administratePlayerCities() {
		synchronized(playerCities){
			Vector<PlayerCity> unfoundedCities = new Vector<PlayerCity>();
			for (PlayerCity city : playerCities){
				
				if (city.getNextCityUpdate()<=System.currentTimeMillis())
					city.processCityUpdate();
				
				if (city.getNextElectionDate()<=System.currentTimeMillis())
					city.processElection();
				
				if (! city.isFounded() && city.getFoundationTime()<=System.currentTimeMillis()-(60*60*24*1000)){
					if (! city.checkFoundationSuccess())
						unfoundedCities.add(city);
				}
			}
			
			if (unfoundedCities.size()>0){
				playerCities.removeAll(unfoundedCities);
			}			
			playerCities.removeAll(Collections.singleton(null));
		}
	}
	
	public PlayerCity addNewPlayerCity(CreatureObject founder) {
		PlayerCity newCity = null;
		synchronized(playerCities){
			newCity = new PlayerCity(founder,cityID++);
			playerCities.add(newCity);
		}
		newCitySUI1(founder,newCity);
		return newCity;
	}
	
	public PlayerCity getCityObjectIsIn(SWGObject object) {
		PlayerCity foundCity = null;
		synchronized(playerCities){
			for (PlayerCity city : playerCities){
				int radius = city.getCityRadius();
				if (object.getPosition().getDistance2D(city.getCityCenterPosition())<radius){
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
	
	
	
	public void newCitySUI1(CreatureObject citizen, final PlayerCity newCity) {		
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

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0,
			Map<Integer, INetworkRemoteEvent> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}	
}
