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

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import resources.datatables.Posture;
import main.NGECore;
import net.engio.mbassy.listener.Handler;
import resources.common.collidables.AbstractCollidable;
import resources.common.collidables.AbstractCollidable.EnterEvent;
import resources.common.collidables.AbstractCollidable.ExitEvent;
import resources.objects.creature.CreatureObject;
import services.TerrainService;
import services.SimulationService.MoveEvent;
import services.ai.LairActor;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

public class DynamicSpawnArea extends SpawnArea {

	private DynamicSpawnGroup spawnGroup;
	private Vector<CreatureObject> mobiles = new Vector<CreatureObject>();
	
	public DynamicSpawnArea(Planet planet, AbstractCollidable area, DynamicSpawnGroup spawnGroup) {
		super(planet, area);
		this.spawnGroup = spawnGroup;
	}

	@Override
	@Handler
	public void onEnter(EnterEvent event) {
		System.err.println("We got a spawn!!!!!!!!!!!!!");
		SWGObject object = event.object;
		
		if(object == null || !(object instanceof CreatureObject))
			return;
		
		CreatureObject creature = (CreatureObject) object;
		
		if(creature.getSlottedObject("ghost") == null)
			return;
		
		creature.getEventBus().subscribe(this);
		// spawn some creatures
		for(int i = 0; i < 5; i++)
			spawnCreature(creature);

	}

	@Override
	@Handler
	public void onExit(ExitEvent event) {
		
		SWGObject object = event.object;
		
		if(object == null || !(object instanceof CreatureObject))
			return;
		
		CreatureObject creature = (CreatureObject) object;
		
		if(creature.getSlottedObject("ghost") == null)
			return;

		creature.getEventBus().unsubscribe(this);

	}
	
	@Handler
	public void onMove(MoveEvent event) {
		
		SWGObject object = event.object;
		
		if(object == null || !(object instanceof CreatureObject) || object.getContainer() != null)
			return;
		
		CreatureObject creature = (CreatureObject) object;
		
		if(creature.getSlottedObject("ghost") == null)
			return;
		
		if(new Random().nextFloat() <= 0.25)
			spawnCreature(creature);
		
	}


	private void spawnCreature(CreatureObject creature) {
		
		NGECore core = NGECore.getInstance();
		
		Iterator<CreatureObject> it = mobiles.iterator();
		it.forEachRemaining(mobile -> {
			if(mobile.getPosture() == Posture.Dead)
				it.remove();
		});
		
		if(mobiles.size() >= spawnGroup.getMaxSpawns())
			return;
		
		boolean foundPos = false;
		int tries = 0;
		Point3D randomPosition = null;
		
		while(!foundPos && ++tries < 10) {
		
			randomPosition = getRandomPosition(creature.getWorldPosition(), 32.f, 200.f);
			
			if(randomPosition == null)
				return;
			
			TerrainService terrainSvc = core.terrainService;
			
			float height = terrainSvc.getHeight(getPlanet().getID(), randomPosition.x, randomPosition.z);
			randomPosition.y = height;
			
			for(CreatureObject mobile : mobiles) {
				if(mobile.getWorldPosition().getDistance(randomPosition) > spawnGroup.getMinSpawnDistance())
					foundPos = true;
			}
			
			if(!terrainSvc.canBuildAtPosition(creature, randomPosition.x, randomPosition.z))
				foundPos = false;

		}
		
		if(!foundPos)
			return;
		
		Random random = new Random();
		
		String mobileTemplate = spawnGroup.getMobiles().get(random.nextInt(spawnGroup.getMobiles().size()));
		CreatureObject spawnedCreature = core.spawnService.spawnCreature(mobileTemplate, getPlanet().getName(), 0, randomPosition.x, randomPosition.y, randomPosition.z);
		if(spawnedCreature != null)
			mobiles.add(spawnedCreature);
		
	}

}
