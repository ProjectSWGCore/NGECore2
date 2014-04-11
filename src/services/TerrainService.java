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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import resources.common.FileUtilities;
import resources.common.collidables.CollidableCircle;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.config.Config;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

import main.NGECore;

public class TerrainService {
	
	private NGECore core;
	private List<Planet> planets = Collections.synchronizedList(new ArrayList<Planet>());
	private Map<Planet, List<CollidableCircle>> noBuildAreas = new ConcurrentHashMap<Planet, List<CollidableCircle>>();

	public TerrainService(NGECore core) {
		this.core = core;	
	}
	
	public void loadClientPois()  {
		
		try {
			
			DatatableVisitor poiTable = ClientFileManager.loadFile("datatables/clientpoi/clientpoi.iff", DatatableVisitor.class);
			
			for (int i = 0; i < poiTable.getRowCount(); i++) {
				
				Planet planet = getPlanetByName((String) poiTable.getObject(i, 0));

				if(planet == null)
					continue;
				
				float x = (Float) poiTable.getObject(i, 4);
				float z = (Float) poiTable.getObject(i, 6);
				
				CollidableCircle poiArea = new CollidableCircle(new Point3D(x, 0, z), 150, planet);
				noBuildAreas.get(planet).add(poiArea);
				
			}

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}		
		
	}
	
	private void loadClientRegions(Planet planet)  {
		
		if(!FileUtilities.doesFileExist("clientdata/datatables/clientregion/" + planet.getName() + ".iff"))
			return;

		try {
			
			DatatableVisitor regionTable = ClientFileManager.loadFile("datatables/clientregion/" + planet.getName() + ".iff", DatatableVisitor.class);
			
			for (int i = 0; i < regionTable.getRowCount(); i++) {
									
				float x = (Float) regionTable.getObject(i, 1);
				float z = (Float) regionTable.getObject(i, 2);
				float radius = (Float) regionTable.getObject(i, 3);
					
				CollidableCircle region = new CollidableCircle(new Point3D(x, 0, z), radius, planet);
				noBuildAreas.get(planet).add(region);
				
			}

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}		
		
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
			if (planets.get(i).getName().equalsIgnoreCase(name)) {
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
		noBuildAreas.put(planet, new ArrayList<CollidableCircle>());
		loadClientRegions(planet);
		
		core.chatService.createChatRoom("", name, "system", true);
		core.chatService.createChatRoom("public chat for this planet, cannot create rooms here", name + ".Planet", "SYSTEM", true);
		core.chatService.createChatRoom("system messages for this planet, cannot create rooms here", name + ".system", "SYSTEM", true);
		
		System.out.println("Created chat rooms for " + name);
	}

	
	public void loadSnapShotObjects() {
		
		Map<Planet, Thread> threadMap = new HashMap<Planet, Thread>();
		
		for(final Planet planet : planets) {
			
			if(planet.getSnapshotVisitor() != null) {
				Thread thread = new Thread(() -> {

					Config config = new Config();
					config.setFilePath("options.cfg");
					boolean loaded = config.loadConfigFile();
					
					if (loaded && config.getInt("LOAD.SNAPSHOT_OBJECTS") > 0) {
						try {							
							core.objectService.loadSnapshotObjects(planet);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					if (loaded && config.getInt("LOAD.BUILDOUT_OBJECTS") > 0) {
						try {							
							core.objectService.loadBuildoutObjects(planet);
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					
				});
				thread.start();
				threadMap.put(planet, thread);
			}
			
		}
		
		// wait for threads to finish loading
		for(Planet planet : planets) {
			try {
				if(threadMap.get(planet) != null)
					threadMap.get(planet).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/*synchronized(core.objectService.getObjectList()) {
		
			for(SWGObject object : core.objectService.getObjectList().values()) {
				
				if(!(object instanceof BuildingObject) || object.isInSnapshot())
					continue;
				
				BuildingObject building = (BuildingObject) object;
				final Set<CreatureObject> creatures = new HashSet<CreatureObject>();
				building.viewChildren(building, true, true, new Traverser() {

					@Override
					public void process(SWGObject obj) {
						if(obj instanceof CreatureObject)
							creatures.add((CreatureObject) obj);
					}
					
				});
				for(CreatureObject creature : creatures) {
					long parentId = creature.getParentId();
					creature.getContainer().remove(creature);
					creature.setParentId(parentId);
				}
				if(building.getTransaction() == null)
					continue;
				building.createTransaction(core.getBuildingODB().getEnvironment());
				core.getBuildingODB().put(building, Long.class, BuildingObject.class, building.getTransaction());
				building.getTransaction().commitSync();
				
			}
			
		}*/
		
	}
	
	public boolean canBuildAtPosition(SWGObject object, float x, float z) {
		
		if(isWater(object.getPlanet(), x, z))
			return false;
		
		Point3D position = new Point3D(x, 0, z);
		
		for(CollidableCircle noBuildArea : noBuildAreas.get(object.getPlanet())) {
			if(noBuildArea.doesCollide(position)) {
				return false;
			}
		}
		
		return true;
		
	}

	public boolean isWater(int planetId, Point3D worldPosition) {
		if(getPlanetByID(planetId) == null)
			return false;
		
		Planet planet = getPlanetByID(planetId);

		return planet.getTerrainVisitor().isWater(worldPosition.x, worldPosition.z);
	}
	
	

}
