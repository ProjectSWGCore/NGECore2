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
package services;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import protocol.swg.ServerWeatherMessage;
import resources.objects.creature.CreatureObject;
import engine.resources.scene.Planet;
import main.NGECore;

public class WeatherService {
	
	private NGECore core;
	private Map<Planet, Integer> weatherStability = new ConcurrentHashMap<Planet, Integer>();
	private Map<Planet, Byte> currentWeatherMap = new ConcurrentHashMap<Planet, Byte>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // TODO: randomise cloud vectors, proper algorithm for weather type rolls
    
	public WeatherService(NGECore core) {
		this.core = core;
	}
	
	public void loadPlanetSettings(){
		core.scriptService.callScript("scripts/", "weather", "init", core);		
	}
	
	public void addPlanetSettings(String planetName, int stability, byte defaultWeather) {
		
		final Planet planet = core.terrainService.getPlanetByName(planetName);
		
		if(planet == null)
			return;
		
		if(stability > 100)
			stability = 100;
		if(defaultWeather > 4 || defaultWeather < 0)
			defaultWeather = 0;
		
		weatherStability.put(planet, stability);
		currentWeatherMap.put(planet, defaultWeather);
		
		scheduler.scheduleAtFixedRate(() -> {
			runWeatherCycle(planet);
		}, 30, 30, TimeUnit.MINUTES);
		
	}

	public void runWeatherCycle(Planet planet) {
		
		int stability = weatherStability.get(planet);
		
		Random rand = new Random();
		int weatherRoll = rand.nextInt(100);
		byte weatherType;
		
		if(weatherRoll < stability)
			weatherType = 0;
		else {
			weatherType = (byte) (rand.nextInt(4) + 1);
		}
		
		currentWeatherMap.put(planet, weatherType);
		core.simulationService.notifyPlanet(planet, new ServerWeatherMessage(weatherType, 1, 0, 0).serialize());
		//System.out.println("Weather type changed to: " + weatherType + " on: " + planet.getName());
		
	}
	
	public void sendWeather(CreatureObject player) {
		
		if(player.getClient() == null || player.getPlanet() == null)
			return;
		
		byte weatherType;
		
		if(!currentWeatherMap.containsKey(player.getPlanet())) {
			weatherType = 0;
		} else {
			weatherType = currentWeatherMap.get(player.getPlanet());
		}
		
		ServerWeatherMessage weatherMsg = new ServerWeatherMessage(weatherType, 1, 0, 0);
		player.getClient().getSession().write(weatherMsg.serialize());
		
	}

}
