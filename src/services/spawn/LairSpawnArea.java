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
import resources.datatables.Options;
import resources.objects.creature.CreatureObject;
import services.SimulationService.MoveEvent;
import services.TerrainService;
import services.ai.LairActor;
import engine.resources.config.Config;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

public class LairSpawnArea extends SpawnArea {
	
	private LairGroupTemplate lairGroup;
	private long lastSpawnTime = 0;
	private Vector<LairActor> lairs = new Vector<LairActor>();
	
	public LairSpawnArea(Planet planet, AbstractCollidable area, LairGroupTemplate lairGroup) {
		super(planet, area);
		this.lairGroup = lairGroup;
	}

	public LairGroupTemplate getLairGroup() {
		return lairGroup;
	}

	public void setLairGroup(LairGroupTemplate lairGroup) {
		this.lairGroup = lairGroup;
	}

	public void spawnLair(CreatureObject object) {
		
		NGECore core = NGECore.getInstance();
		
		Vector<LairSpawnTemplate> lairTemplates = lairGroup.getLairSpawnTemplates();
		
		if(lairGroup == null || lairTemplates.isEmpty())
			return;
		
		if((System.currentTimeMillis() - lastSpawnTime) < 10000)
			return;
			
		Point3D randomPosition = getRandomPosition(object.getWorldPosition(), 32.f, 200.f);
		
		if(randomPosition == null)
			return;
		
		TerrainService terrainSvc = core.terrainService;
		
		float height = terrainSvc.getHeight(getPlanet().getID(), randomPosition.x, randomPosition.z);
		randomPosition.y = height;
		
		for(LairActor otherLair : lairs) {
			if(otherLair.getLairObject().getWorldPosition().getDistance(randomPosition) < 30)
				return;
		}
		
		if(!terrainSvc.canBuildAtPosition(object, randomPosition.x, randomPosition.z))
			return;
		
		Random random = new Random();
				
		LairSpawnTemplate lairSpawn = lairTemplates.get(random.nextInt(lairTemplates.size()));
		
		int level = -1; // If level equals -1 then the mobile template CL will be used!
		if (lairSpawn.getMinLevel() != -1 && lairSpawn.getMaxLevel()!=-1)
			level = random.nextInt((int) (lairSpawn.getMaxLevel() - lairSpawn.getMinLevel()) + 1) + lairSpawn.getMinLevel();
		
		
		
		LairActor lairActor = core.spawnService.spawnLair(lairSpawn.getLairTemplate(), getPlanet(), randomPosition, (short) level);
		if(lairActor == null)
			return;
		lairs.add(lairActor);
		lastSpawnTime = System.currentTimeMillis();
		
	}

	@Override
	@Handler
	public void onEnter(EnterEvent event) {
		
		Config options = NGECore.getInstance().getOptions();

		if (options != null && options.getInt("DO.ISOLATION.TESTS") > 0){
			return;
		}
		
		SWGObject object = event.object;
		
		if(object == null || !(object instanceof CreatureObject))
			return;
		
		CreatureObject creature = (CreatureObject) object;
		
		if(creature.getSlottedObject("ghost") == null && !creature.getOption(Options.MOUNT))
			return;
		
		spawnLair(creature);
		creature.getEventBus().subscribe(this);
				
	}

	@Override
	@Handler
	public void onExit(ExitEvent event) {
		
		SWGObject object = event.object;
		
		if(object == null || !(object instanceof CreatureObject))
			return;
		
		CreatureObject creature = (CreatureObject) object;
		
		if(creature.getSlottedObject("ghost") == null && !creature.getOption(Options.MOUNT))
			return;

		creature.getEventBus().unsubscribe(this);
		
	}	
	
	@Handler
	public void onMove(MoveEvent event) {
		
		SWGObject object = event.object;
		
		//if(object == null || !(object instanceof CreatureObject) || object.getContainer() != null)
		//	return;
		
		if(object == null || !(object instanceof CreatureObject))
			return;
		
		CreatureObject creature = (CreatureObject) object;
		
		if(creature.getSlottedObject("ghost") == null && !creature.getOption(Options.MOUNT))
			return;
		
		if(new Random().nextFloat() <= 0.05)
			spawnLair(creature);
		
	}

	public long getLastSpawnTime() {
		return lastSpawnTime;
	}

	public void setLastSpawnTime(long lastSpawnTime) {
		this.lastSpawnTime = lastSpawnTime;
	}
	
	
}
