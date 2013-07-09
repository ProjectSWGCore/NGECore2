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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.resources.scene.Planet;


import main.NGECore;

public class TerrainService {
	
	private NGECore core;
	private List<Planet> planets = Collections.synchronizedList(new ArrayList<Planet>());

	public TerrainService(NGECore core) {

		this.core = core;
	
	}
	
	public boolean isWater(int planetId, float x, float z) {
		
		if(getPlanetByID(planetId) == null)
			return false;
		
		Planet planet = getPlanetByID(planetId);

		return planet.getTerrainVisitor().isWater(x, z);
		
	}
	
	public boolean isWater(Planet planet, float x, float z) {
		
		if(!planets.contains(planet))
			return false;
		
		return planet.getTerrainVisitor().isWater(x, z);
		
	}
	
	
	public float getHeight(int planetId, float x, float z) {
		
		if(getPlanetByID(planetId) == null)
			return Float.NaN;
		Planet planet = getPlanetByID(planetId);
		float height = planet.getTerrainVisitor().getHeight(x, z);
		System.out.println("Height: " + height);
		return height; 
	}
	
	
	public List<Planet> getPlanetList() {
		return planets;
	}
	
	public Planet getPlanetByID(int ID) {
		for (int i = 0; i < planets.size(); i++) {
			if (planets.get(i).getID() == ID) {
				return planets.get(i);
			}
		}
		
		return null;
	}
	
	public Planet getPlanetByName(String name) {
		for (int i = 0; i < planets.size(); i++) {
			if (planets.get(i).getName().equals(name)) {
				return planets.get(i);
			}
		}
		
		return null;
	}
	
	public Planet getPlanetByPath(String path) {
		for (int i = 0; i < planets.size(); i++) {
			if (planets.get(i).getPath().equals(path)) {
				return planets.get(i);
			}
		}
		
		return null;
	}
		
	public void addPlanet(int ID, String name, String path, boolean loadSnapshot) {
		Planet planet = new Planet(ID, name, path, loadSnapshot);
		planets.add(planet);
		core.mapService.addPlanet(planet);
	}

	
	public void loadSnapShotObjects() {
		
		for(Planet planet : planets) {
			
			if(planet.getSnapshotVisitor() != null) {
				
				core.objectService.loadSnapshotObjects(planet);
				
			}
			
		}
		
	}
	
	
	

}
