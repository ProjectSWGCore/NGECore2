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
package services.ai;

import java.util.Random;
import java.util.Vector;

import engine.resources.scene.Point3D;
import main.NGECore;
import net.engio.mbassy.listener.Handler;
import resources.common.SpawnPoint;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import services.combat.CombatEvents.DamageTaken;
import tools.DevLog;

public class LairActor {

	private Vector<AIActor> creatures = new Vector<AIActor>();
	private TangibleObject lairObject;
	private int maxSpawns;
	private String creatureTemplate;
	private Vector<String> creatureTemplates;
	private volatile int spawnWave = 0;
	private short level;
	
	public LairActor(TangibleObject lairObject, String creatureTemplate) {
		this.lairObject = lairObject;
		this.creatureTemplate = creatureTemplate;
		lairObject.getEventBus().subscribe(this);
	}
	
	public LairActor(TangibleObject lairObject, String creatureTemplate, int maxSpawns, short level) {
		this.lairObject = lairObject;
		this.creatureTemplate = creatureTemplate;
		this.maxSpawns = maxSpawns;
		this.level = level;
		lairObject.getEventBus().subscribe(this);
	}
	
	public LairActor(TangibleObject lairObject, Vector<String> creatureTemplates, int maxSpawns, short level) {
		this.lairObject = lairObject;
		this.creatureTemplates = creatureTemplates;
		this.maxSpawns = maxSpawns;
		this.level = level;
		lairObject.getEventBus().subscribe(this);
	}

	
	public Vector<AIActor> getCreatures() {
		return creatures;
	}

	public TangibleObject getLairObject() {
		return lairObject;
	}

	public void setLairObject(TangibleObject lairObject) {
		this.lairObject = lairObject;
	}
	
	@Handler
	public void handleLairDamageEvent(DamageTaken event) {
		
		spawnNewCreatures();
		
		for(AIActor ai : creatures) {
			ai.addDefender(event.attacker);
			event.attacker.addDefender(ai.getCreature());
		}
		
		
	}

	public void spawnNewCreatures() {
		
		if(creatures.size() >= maxSpawns)
			return;
		
		int currentCondition = lairObject.getConditionDamage();
		int maxCondition = lairObject.getMaximumCondition();
		
		switch(spawnWave) {
			// TODO: play damage effect
			case 0: 
				spawnWave++;
				break;
			case 1:
				if((currentCondition / maxCondition) < 0.7) {
					spawnWave++;
				} else {
					return;
				}
			case 2:
				if((currentCondition / maxCondition) < 0.3) {
					spawnWave++;
				} else {
					return;
				}
				break;
			case 3:
				return;
				
			default:
				return;
				
		}
						
		int creatureAmount = 0;
		int tries = 0;
		do {
			creatureAmount = new Random().nextInt(4) + (maxSpawns / 5);
			tries++;
		}
		while(creatureAmount > maxSpawns && tries < 10);
				
		for(int i = 0; i < creatureAmount; i++) {
			Point3D position = SpawnPoint.getRandomPosition(lairObject.getPosition(), 5, 30, lairObject.getPlanetId());
			if (creatureTemplates!=null)
				creatureTemplate = creatureTemplates.get(new Random().nextInt(creatureTemplates.size()));
			float babyChance = new Random().nextFloat();
			CreatureObject creature = null;
			if (babyChance>0.9){
				creature = NGECore.getInstance().spawnService.spawnCreature(creatureTemplate, lairObject.getPlanet().getName(), 0, position.x, position.y, position.z, level);
			} else {
				creature = NGECore.getInstance().spawnService.spawnCreatureBaby(creatureTemplate, lairObject.getPlanet().getName(), 0, position.x, position.y, position.z, level);
			}
			
			if(creature == null || !creature.isInQuadtree()) {
				DevLog.debugout("Charon", "Lair AI", "LairActor spawnNewCreatures() NULL creature!");
				continue;
			}
			creatures.add((AIActor) creature.getAttachment("AI"));
		}
		
		healLair();
		
		
	}
	
	@SuppressWarnings("unused")
	private void healLair() {
		
		if(lairObject.getConditionDamage() == 0 || creatures.isEmpty())
			return;
		
		int healAmount = 0;
		
		for(AIActor ai : creatures) {
			healAmount += lairObject.getMaximumCondition() / 100;
		}
		
		lairObject.setConditionDamage(lairObject.getConditionDamage() - healAmount);
		lairObject.playEffectObject("clienteffect/healing_healdamage.cef", "root");
		
	}

	public int getMaxSpawns() {
		return maxSpawns;
	}

	public void setMaxSpawns(int maxSpawns) {
		this.maxSpawns = maxSpawns;
	}

	public String getCreatureTemplate() {
		return creatureTemplate;
	}

	public void setCreatureTemplate(String creatureTemplate) {
		this.creatureTemplate = creatureTemplate;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}
}
