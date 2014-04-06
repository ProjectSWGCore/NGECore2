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
		for (SWGObject object : core.objectService.getObjectList().values()) {
			if (object instanceof CreatureObject && ((CreatureObject) object).getStaticNPC()) {
				((TangibleObject) object).setRespawnTime(0);
				core.objectService.destroyObject(object);
			}
		}
		
		spawnObjects("rori");
		spawnObjects("naboo");
		spawnObjects("tatooine");
		spawnObjects("lok");
		//spawnObjects("kaas");    // Keep commented out unless you possess the latest build of Kaas!
	}
	
	public void spawnObjects(String planetName) {
		Planet planet = (Planet) core.terrainService.getPlanetByName(planetName);
		core.scriptService.callScript("scripts/static_spawns/", planet.getName(), "addPlanetSpawns", core, planet);
		System.out.println("Loaded static objects for " + planet.getName());
	}
	
	public SWGObject spawnObject(String template, String planetName, long cellId, float x, float y, float z, float qY, float qW) {
		return spawnObject(template, planetName, cellId, x, y, z, qW, 0, qY, 0);
	}
	
	public SWGObject spawnObject(String template, String planetName, long cellId, float x, float y, float z, float qW, float qX, float qY, float qZ) {
		return spawnObject(template, 0, planetName, cellId, x, y, z, qW, qX, qY, qZ);
	}
	
	public SWGObject spawnObject(String template, long objectId, String planetName, long cellId, float x, float y, float z, float qW, float qX, float qY, float qZ) {
		Planet planet = core.terrainService.getPlanetByName(planetName);
		
		if (planet == null) {
			System.out.println("StaticService: Can't spawn static object because planet is null.");
			return null;
		}
		
		SWGObject object = core.objectService.createObject(template, objectId, planet, new Point3D(x, y, z), new Quaternion(qW, qX, qY, qZ));
		
		if (object == null) {
			System.out.println("Static object is null with id " + objectId + " and template " + template + ".");
			return null;
		}
		
		if (objectId != 0 && object.getObjectID() != objectId) {
			System.out.println("StaticService: ObjectId " + objectId + " was taken for object with template " + object.getTemplate() + ".  Replacement: " + object.getObjectID());
		}
		
		if (object instanceof CreatureObject) {
			((CreatureObject) object).setStaticNPC(true);
		}
		
		if (cellId == 0) {
			boolean add = core.simulationService.add(object, (float) x, (float) z, true);
			
			if (!add) {
				System.out.println("StaticService: Quadtree insert failed for: " + template);
			}
		} else {
			SWGObject parent = core.objectService.getObject(cellId);
			
			if (parent == null) {
				System.out.println("StaticService: Cell not found");
				return object;
			}
			
			parent.add(object);
		}
		
		return object;
	}
	
	public List<SWGObject> getCloningFacilitiesByPlanet(Planet planet) {
		List<SWGObject> objects = core.simulationService.get(planet, 0, 0, 8300);
		List<SWGObject> cloners = new ArrayList<SWGObject>();
		
		for (SWGObject obj : objects) {
			if(obj instanceof BuildingObject && (obj.getTemplate().contains("cloning_facility") || obj.getTemplate().contains("cloning_tatooine") || obj.getTemplate().contains("cloning_naboo") || obj.getTemplate().contains("cloning_corellia"))) {
				if(!obj.getTemplate().equals("object/building/general/shared_cloning_facility_general.iff"))
					cloners.add(obj);
			}
		}
		return cloners;
		
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}
	
	@Override
	public void shutdown() {
		
	}
	
}
