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

import java.util.Vector;

import net.engio.mbassy.listener.Handler;

import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import services.combat.CombatEvents.DamageTaken;

public class LairActor {

	private Vector<AIActor> creatures = new Vector<AIActor>();
	private TangibleObject lairObject;
	private int maxSpawns;
	private String creatureTemplate;
	private volatile int spawnWave = 0;
	
	public LairActor(TangibleObject lairObject, String creatureTemplate) {
		this.lairObject = lairObject;
		this.creatureTemplate = creatureTemplate;
		lairObject.getEventBus().subscribe(this);
	}
	
	public LairActor(TangibleObject lairObject, String creatureTemplate, int maxSpawns) {
		this.lairObject = lairObject;
		this.creatureTemplate = creatureTemplate;
		this.maxSpawns = maxSpawns;
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
		
		for(AIActor ai : creatures) {
			ai.doAggro(event.attacker);
		}
		
		spawnNewCreatures();
		
	}

	private void spawnNewCreatures() {
		
		if(creatures.size() >= maxSpawns)
			return;
		
		int currentCondition = lairObject.getConditionDamage();
		int maxCondition = lairObject.getMaxDamage();
		
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
		
		// TODO: spawn creatures
		
		healLair();
		
	}
	
	private void healLair() {
		
		
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
}
