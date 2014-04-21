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

import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import main.NGECore;

public class PlayerCityService implements INetworkDispatch {
	
	private NGECore core;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    private Vector<PlayerCity> playerCities = new Vector<PlayerCity>();

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
