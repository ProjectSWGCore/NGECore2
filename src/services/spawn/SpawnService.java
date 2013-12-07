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
package services.spawn;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import main.NGECore;

public class SpawnService {

	private NGECore core;
	private Map<Planet, List<SpawnArea>> spawnAreas = new ConcurrentHashMap<Planet, List<SpawnArea>>();
	
	public SpawnService(NGECore core) {
		
	}	
	
	public void spawnCreature(String template, float x, float y, float z) {
		spawnCreature(template, new Point3D(x, y, z));
	}
	
	public void spawnCreature(String template, Point3D position) {
		
	}
	
	public void spawnLair(LairTemplate lairTemplate, Planet planet, Point3D position, int level) {
		
	}

	
}
