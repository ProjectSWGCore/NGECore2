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

import java.util.Random;
import java.util.Vector;

import main.NGECore;
import net.engio.mbassy.listener.Handler;
import resources.common.collidables.AbstractCollidable;
import resources.common.collidables.AbstractCollidable.EnterEvent;
import resources.common.collidables.AbstractCollidable.ExitEvent;
import resources.objects.creature.CreatureObject;
import services.SimulationService.MoveEvent;
import services.TerrainService;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

public class LairSpawnArea extends SpawnArea {
	
	private LairGroupTemplate lairGroup;
	
	public LairSpawnArea(Planet planet, AbstractCollidable area) {
		super(planet, area);
	}

	public LairGroupTemplate getLairGroup() {
		return lairGroup;
	}

	public void setLairGroup(LairGroupTemplate lairGroup) {
		this.lairGroup = lairGroup;
	}

	public void spawnLair(CreatureObject object) {
		
		Vector<LairSpawnTemplate> lairTemplates = lairGroup.getLairSpawnTemplates();
		
		if(lairGroup == null || lairTemplates.isEmpty())
			return;
	
		Point3D randomPosition = getRandomPosition(object.getWorldPosition(), 32.f, 256.f);
		
		if(randomPosition == null)
			return;
		
		TerrainService terrainSvc = NGECore.getInstance().terrainService;
		
		float height = terrainSvc.getHeight(getPlanet().getID(), randomPosition.x, randomPosition.z);
		randomPosition.y = height;
		
		if(!terrainSvc.canBuildAtPosition(object, randomPosition.x, randomPosition.z))
			return;
		
		Random random = new Random();
				
		LairSpawnTemplate lairSpawn = lairTemplates.get(random.nextInt(lairTemplates.size()));
		
		int level = random.nextInt((int) (lairSpawn.getMaxLevel() - lairSpawn.getMinLevel()) + 1) + lairSpawn.getMinLevel();
		
		NGECore.getInstance().spawnService.spawnLair(lairSpawn.getLairTemplate(), getPlanet(), randomPosition, level);
		
	}

	@Override
	@Handler
	public void onEnter(EnterEvent event) {
		
		SWGObject object = event.object;
		
		if(object == null || !(object instanceof CreatureObject))
			return;
		
		CreatureObject creature = (CreatureObject) object;
		
		if(creature.getSlottedObject("ghost") == null)
			return;
		
		spawnLair(creature);
		
	}

	@Override
	@Handler
	public void onExit(ExitEvent event) {
		
	}	
	
	@Handler
	public void onMove(MoveEvent event) {
		
		SWGObject object = event.object;
		
		if(object == null || !(object instanceof CreatureObject) || object.getContainer() != null)
			return;
		
		CreatureObject creature = (CreatureObject) object;
		
		if(creature.getSlottedObject("ghost") == null)
			return;
		
		if(new Random().nextFloat() <= 0.10)
			spawnLair(creature);
		
	}
	
	
}
