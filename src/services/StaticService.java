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
import java.util.List;
import java.util.Map;

import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;

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
		for (SWGObject object : core.objectService.getObjectList().values())
			if (object instanceof CreatureObject && ((CreatureObject) object).getStaticNPC()) {
				((TangibleObject) object).setRespawnTime(0);
				core.objectService.destroyObject(object);
			}
		spawnPlanetStaticObjs("rori");
		spawnPlanetStaticObjs("naboo");
		spawnPlanetStaticObjs("tatooine");
		spawnPlanetStaticObjs("lok");
		//
		//spawnPlanetStaticObjs("kaas");    // Keep commented out unless you possess the latest build of Kaas!
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		
	}
	
	public void spawnPlanetStaticObjs(String planet) {
		Planet planetObj = (Planet) core.terrainService.getPlanetByName(planet);
		core.scriptService.callScript("scripts/static_spawns/", planetObj.getName(), "addPlanetSpawns", core, planetObj);
		System.out.println("Loaded static objs for " + planetObj.getName());
	}
	
	public SWGObject spawnObject(String template, String planetName, long cellId, float x, float y, float z, float qY, float qW) {
		return spawnObject(template, planetName, cellId, x, y, z, qW, 0, qY, 0);
	}
	
	// TODO make sure static objects get unloaded
	public SWGObject spawnObject(String template, String planetName, long cellId, float x, float y, float z, float qW, float qX, float qY, float qZ) {
		
		Planet planet = core.terrainService.getPlanetByName(planetName);
		
		//System.out.println("template: " + template + " x: " + x + " y: " + y + " z: " + z);
		
		if(planet == null) {
			System.out.println("Cant spawn static object because planet is null");
			return null;
		}
		
		SWGObject object = core.objectService.createObject(template, 0, planet, new Point3D(x, y, z), new Quaternion(qW, qX, qY, qZ));
		
		if(object == null) {
			System.out.println("Static object is null");
			return null;
		}
		
		if (object instanceof CreatureObject) ((CreatureObject) object).setStaticNPC(true);
		
		if(cellId == 0) {
			boolean add = core.simulationService.add(object, (float) x, (float) z, true);
			if(!add)
				System.out.println("Quadtree insert failed for: " + template);
		}
		else {
			SWGObject parent = core.objectService.getObject(cellId);
			if(parent == null) {
				System.out.println("Cell not found");
				return object;
			}
			parent.add(object);
		}
		
		return object;
	}
	
	public List<SWGObject> getCloningFacilitiesByPlanet(Planet planet) {
		
		List<SWGObject> objects = core.simulationService.get(planet, 0, 0, 8300);
		List<SWGObject> cloners = new ArrayList<SWGObject>();
		
		for(SWGObject obj : objects) {
			if(obj instanceof BuildingObject && (obj.getTemplate().contains("cloning_facility") || obj.getTemplate().contains("cloning_tatooine") || obj.getTemplate().contains("cloning_naboo") || obj.getTemplate().contains("cloning_corellia"))) {
				if(!obj.getTemplate().equals("object/building/general/shared_cloning_facility_general.iff"))
					cloners.add(obj);
			}
		}
		return cloners;
		
	}

}
