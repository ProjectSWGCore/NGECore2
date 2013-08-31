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

import main.NGECore;

import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class StaticService implements INetworkDispatch {
	
	private NGECore core;

	public StaticService(NGECore core) {
		this.core = core;
	}

	public void spawnStatics() {
		spawnPlanetStaticObjs("rori");
		spawnPlanetStaticObjs("naboo");
		spawnPlanetStaticObjs("tatooine");
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		
	}
	
	public void spawnPlanetStaticObjs(String planet) {
		Planet planetObj = (Planet) core.terrainService.getPlanetByName(planet);
		core.scriptService.callScript("scripts/static_spawns", "addPlanetSpawns", planetObj.getName(), core, planetObj);
		System.out.println("Loaded static objs for " + planetObj.getName());
	}
	
	public void spawnObject(String template, String planetName, long cellId, float x, float y, float z, float qY, float qW) {
		
		Planet planet = core.terrainService.getPlanetByName(planetName);
		
		//System.out.println("template: " + template + " x: " + x + " y: " + y + " z: " + z);
		
		if(planet == null) {
			System.out.println("Cant spawn static object because planet is null");
			return;
		}
		
		SWGObject object = core.objectService.createObject(template, 0, planet, new Point3D(x, y, z), new Quaternion(qW, 0, qY, 0));
		
		if(object == null) {
			System.out.println("Static object is null");
			return;
		}
		
		if(cellId == 0) {
			boolean add = core.simulationService.add(object, (float) x, (float) z);
			if(!add)
				System.out.println("Quadtree insert failed for: " + template);
		}
		else {
			SWGObject parent = core.objectService.getObject(cellId);
			if(parent == null) {
				System.out.println("Cell not found");
				return;
			}
			parent.add(object);
		}
		
	}

}
