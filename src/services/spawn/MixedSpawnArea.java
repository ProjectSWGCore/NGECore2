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

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import resources.datatables.Options;
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
import engine.resources.config.Config;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

public class MixedSpawnArea extends SpawnArea {

	private SpawnGroup spawnGroup;
	private Vector<SpawnGroup> spawnGroups;
	private Vector<CreatureObject> mobiles = new Vector<CreatureObject>();
	private Vector<LairActor> lairs = new Vector<LairActor>();
	private long lastSpawnTime = 0;
	
	public MixedSpawnArea(Planet planet, AbstractCollidable area, SpawnGroup spawnGroup) {
		super(planet, area);
		this.spawnGroup = spawnGroup;
	}
	
	public MixedSpawnArea(Planet planet, AbstractCollidable area, Vector<SpawnGroup> spawnGroups) {
		super(planet, area);
		this.spawnGroups = spawnGroups;
	}

	@Override
	@Handler
	public void onEnter(EnterEvent event) {
		
		Config options = NGECore.getInstance().getOptions();

		if (options != null && options.getInt("DO.ISOLATION.TESTS") > 0){
			return;
		}
		
		
		SWGObject object = event.object;
		// ToDo: Mounted players must be handled too
		//System.out.println("Entering object " + object.getClass().getName());
		if(object == null || !(object instanceof CreatureObject))
			return;
		
		
		CreatureObject creature = (CreatureObject) object;
		
		if(creature.getSlottedObject("ghost") == null && !creature.getOption(Options.MOUNT))
			return;
		
		creature.getEventBus().subscribe(this);
		// spawn some creatures
		if (spawnGroups==null){
			if (spawnGroup.getGroupMembersNumber()==-1){
				for(int i = 0; i < 5; i++)
					spawnCreature(creature);
			} else {
				Vector<CreatureObject> groupMembers = new Vector<CreatureObject>();
				Point3D randomGroupPosition = getRandomPosition(creature.getWorldPosition(), 32, 100);
				for(int i = 0; i < spawnGroup.getGroupMembersNumber(); i++) {// A group with a specified number of members
					CreatureObject spawnedCreature = spawnCreatureMember(creature, groupMembers, randomGroupPosition, i);
					if (spawnedCreature!=null)
						groupMembers.add(spawnedCreature);
				}
			}
		} else {
			// select randomly one of the spawngroups -> minimizes number of collidables for areas with multiple different NPC groups
			// One spawnarea -> many NPC groups

			
			int index = new Random().nextInt(spawnGroups.size());
			this.spawnGroup = spawnGroups.get(index);
			if (this.spawnGroup instanceof DynamicSpawnGroup){
				Vector<CreatureObject> groupMembers = new Vector<CreatureObject>();
				Point3D randomGroupPosition = getRandomPosition(creature.getWorldPosition(), 32, 100);
				int spawnCount = spawnGroup.getGroupMembersNumber();
				if (spawnCount<0){
					int spawnCount2 = 1;
					if (Math.abs(spawnCount)<spawnGroup.getMobiles().size())
						spawnCount2 = Math.abs(spawnCount) + new Random().nextInt(spawnGroup.getMobiles().size()-Math.abs(spawnCount));
					else
						spawnCount2 = Math.abs(spawnCount);
					spawnCount = Math.max(Math.abs(spawnCount), spawnCount2);
				}
				for(int i = 0; i < spawnCount; i++) {// A group with a specified or random number of members
					CreatureObject spawnedCreature = spawnCreatureMember(creature, groupMembers, randomGroupPosition, i);
					if (spawnedCreature!=null)
						groupMembers.add(spawnedCreature);
				}
			}
			if (this.spawnGroup instanceof LairGroupTemplate){
				object = event.object;
				
				if(object == null || !(object instanceof CreatureObject))
					return;
				
				creature = (CreatureObject) object;
				
				if(creature.getSlottedObject("ghost") == null && !creature.getOption(Options.MOUNT))
					return;
				
				spawnLair(creature);
				creature.getEventBus().subscribe(this);
			}
		}
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
		// Mounts should not be excluded to trigger spawns as it was on live also
		if(object == null || !(object instanceof CreatureObject))
			return;
		
		CreatureObject creature = (CreatureObject) object;
		//System.out.println("MOUNT " + creature.getOption(Options.MOUNT) + " name " + creature.getTemplate());
		if(creature.getSlottedObject("ghost") == null && !creature.getOption(Options.MOUNT))
			return;

		if (spawnGroups==null){
			if (spawnGroup.getGroupMembersNumber()==-1){
				if(new Random().nextFloat() <= 0.25)
					spawnCreature(creature);
			} else
			{
				Vector<CreatureObject> groupMembers = new Vector<CreatureObject>();
				Point3D randomGroupPosition = getRandomPosition(creature.getWorldPosition(), 32, 100);
				for(int i = 0; i < spawnGroup.getGroupMembersNumber(); i++) {// A group with a specified number of members
					CreatureObject spawnedCreature = spawnCreatureMember(creature, groupMembers, randomGroupPosition, i);
					if (spawnedCreature!=null)
						groupMembers.add(spawnedCreature);
				}
			}
		} else {
			// select randomly one of the spawngroups -> minimizes number of collidables for areas with multiple different NPC groups
			// One spawnarea -> many NPC groups
			int index = new Random().nextInt(spawnGroups.size());
			this.spawnGroup = spawnGroups.get(index);
			if (this.spawnGroup instanceof DynamicSpawnGroup){
				Vector<CreatureObject> groupMembers = new Vector<CreatureObject>();
				//Point3D randomGroupPosition = getRandomPosition(creature.getWorldPosition(), 32, 100);
				Point3D randomGroupPosition = getRandomPosition(creature.getWorldPosition(), 32, spawnGroup.getMinSpawnDistance()+10);
				int spawnCount = spawnGroup.getGroupMembersNumber();
				if (spawnCount<0){
					int spawnCount2 = 1;
					if (Math.abs(spawnCount)<spawnGroup.getMobiles().size())
						spawnCount2 = Math.abs(spawnCount) + new Random().nextInt(spawnGroup.getMobiles().size()-Math.abs(spawnCount));
					else
						spawnCount2 = Math.abs(spawnCount);
					spawnCount = Math.max(Math.abs(spawnCount), spawnCount2);
				}
				
				for(int i = 0; i < spawnCount; i++) {// A group with a specified or random number of members
					CreatureObject spawnedCreature = spawnCreatureMember(creature, groupMembers, randomGroupPosition, i);
					if (spawnedCreature!=null)
						groupMembers.add(spawnedCreature);				
				}
			}
			if (this.spawnGroup instanceof LairGroupTemplate){
				object = event.object;
				
				//if(object == null || !(object instanceof CreatureObject) || object.getContainer() != null)
				//	return;
				
				if(object == null || !(object instanceof CreatureObject))
					return;
				
				creature = (CreatureObject) object;
				
				if(creature.getSlottedObject("ghost") == null && !creature.getOption(Options.MOUNT))
					return;
				
				if(new Random().nextFloat() <= 0.05)
					spawnLair(creature);
			}
		}		
	}


	private void spawnCreature(CreatureObject creature) {

		NGECore core = NGECore.getInstance();
		
//		Iterator<CreatureObject> it = mobiles.iterator(); // This stops further execution of this method for some reason
//		it.forEachRemaining(mobile -> {
//			if(mobile.getPosture() == Posture.Dead)
//				it.remove();
//		});
		
		mobiles.removeAll(Collections.singleton(null));
		Vector<CreatureObject> removers = new Vector<CreatureObject>();
		for (CreatureObject ob : mobiles){
			if (ob.getPosture() == Posture.Dead)
				removers.add(ob);
		}
		mobiles.removeAll(removers);
		
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
			
			if (mobiles.size()>0){ // Fix, mobiles must be filled first, before doing this check
				for(CreatureObject mobile : mobiles) {
					if(mobile.getWorldPosition().getDistance(randomPosition) > spawnGroup.getMinSpawnDistance())
						foundPos = true;
				}
			} else {foundPos = true;}
			
			if(!terrainSvc.canBuildAtPosition(creature, randomPosition.x, randomPosition.z))
				foundPos = false;

		}
		
		if(!foundPos)
			return;
		
		Random random = new Random();
		
		String mobileTemplate = spawnGroup.getMobiles().get(random.nextInt(spawnGroup.getMobiles().size()));
		CreatureObject spawnedCreature = core.spawnService.spawnCreature(mobileTemplate, getPlanet().getName(), 0, randomPosition.x, randomPosition.y, randomPosition.z);
		if(spawnedCreature != null){
			mobiles.add(spawnedCreature);
		}
		
	}
	
	
	private CreatureObject spawnCreatureMember(CreatureObject creature, Vector<CreatureObject> groupMembers, Point3D randomGroupPosition, int spawnIndex) {

		NGECore core = NGECore.getInstance();
		
//		Iterator<CreatureObject> it = mobiles.iterator(); // This stops further execution of this method for some reason
//		it.forEachRemaining(mobile -> {
//			if(mobile.getPosture() == Posture.Dead)
//				it.remove();
//		});
				
		mobiles.removeAll(Collections.singleton(null));
		Vector<CreatureObject> removers = new Vector<CreatureObject>();
		for (CreatureObject ob : mobiles){
			if (ob.getPosture() == Posture.Dead)
				removers.add(ob);
		}
		mobiles.removeAll(removers);
				
//		if(mobiles.size() >= spawnGroup.getMaxSpawns())
//			return;
				
		boolean foundPos = false;
		int tries = 0;
		Point3D randomPosition = null;
		String template = spawnGroup.getMobiles().get(spawnIndex);
		MobileTemplate mobileTemplate = NGECore.getInstance().spawnService.getMobileTemplate(template);
		if (mobileTemplate==null){
			System.out.println("mobileTemplate==null for template " + template);
			return null;
		}
		
		while(!foundPos && ++tries < 30) {
		
			randomPosition = getRandomPosition(randomGroupPosition, mobileTemplate.getMinSpawnDistance(), mobileTemplate.getMaxSpawnDistance());
			
			if(randomPosition == null){
				//System.out.println("randomPosition == null");
				return null;
			}
			
			TerrainService terrainSvc = core.terrainService;
			
			float height = terrainSvc.getHeight(getPlanet().getID(), randomPosition.x, randomPosition.z);
			randomPosition.y = height;
			
			if (mobiles.size()>0){ // Fix, mobiles must be filled first, before doing this check
				boolean minDistViolated = false;
				for(CreatureObject mobile : mobiles) {
					if(mobile.getWorldPosition().getDistance(randomPosition) < spawnGroup.getMinSpawnDistance() && ! groupMembers.contains(mobile)){
						minDistViolated = true; // Distance to other nearby groups
						//System.out.println("minDistViolated " + mobile.getWorldPosition().getDistance(randomPosition) + "mobile: " + mobile.getTemplate());
					}
				}
				for (LairActor actor : lairs){
					if(actor.getLairObject().getWorldPosition().getDistance(randomPosition) < spawnGroup.getMinSpawnDistance()){
						minDistViolated = true; // Distance to other nearby lairs!
						//System.out.println("minDistViolated " + mobile.getWorldPosition().getDistance(randomPosition) + "mobile: " + mobile.getTemplate());
					}
				}
				if (minDistViolated)
					foundPos = false;
				else
					foundPos = true;
			} else {foundPos = true;}
			
			if(!terrainSvc.canBuildAtPosition(creature, randomPosition.x, randomPosition.z))
				foundPos = false;

		}
		
		if(!foundPos){
			//System.out.println("mobileTemplate pos not found " + mobileTemplate.getCreatureName());
			return null;
		}
		//System.out.println("mobileTemplate pos found " + mobileTemplate.getCreatureName());
		
		CreatureObject spawnedCreature = core.spawnService.spawnCreature(template, getPlanet().getName(), 0, randomPosition.x, randomPosition.y, randomPosition.z);
		if(spawnedCreature != null){
			mobiles.add(spawnedCreature);
			//System.err.println("Creature ADDED " + spawnedCreature.getTemplate());
		} else 
		{
			//System.out.println("Creature not spawned" + template);
		}

		return spawnedCreature;		
	}
	
	// LairSpawnArea class methods
	public void spawnLair(CreatureObject object) {
		
		NGECore core = NGECore.getInstance();
		
		LairGroupTemplate lairGroup = (LairGroupTemplate)this.spawnGroup;
		
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
	
}
