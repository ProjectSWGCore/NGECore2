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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;

import resources.datatables.Difficulty;
import resources.datatables.FactionStatus;
import resources.datatables.GcwType;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.player.PlayerObject;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import main.NGECore;

public class AIService {
	
	private Vector<AIActor> aiActors = new Vector<AIActor>();
	private NGECore core;
	
	public AIService(NGECore core) {
		this.core = core;
	}
	
	public Vector<Point3D> findPath(int planetId, Point3D pointA, Point3D pointB) {
		
		// TODO: implement cell pathfinding, returning straight line for now
		Vector<Point3D> path = new Vector<Point3D>();
		path.add(pointA);
		float x = pointB.x - 1 + new Random().nextFloat();
		float z = pointB.z - 1 + new Random().nextFloat();
		Point3D endPoint = new Point3D(x, core.terrainService.getHeight(planetId, x, z), z);
		endPoint.setCell(pointB.getCell());
		path.add(endPoint);
		return path;
	}
	
	public void awardExperience(AIActor actor) {
		
		Map<CreatureObject, Integer> damageMap = actor.getDamageMap();
		CreatureObject creature = actor.getCreature();
		int baseXP = getBaseXP(creature);
		for(Entry<CreatureObject, Integer> e : damageMap.entrySet()) {
			
			CreatureObject player = e.getKey();
			PlayerObject ghost = (PlayerObject) player.getSlottedObject("ghost");
			if(ghost == null)
				continue;
			int damage = e.getValue();
			
			short level = (player.getGroupId() == 0) ? player.getLevel() : ((GroupObject) core.objectService.getObject(player.getGroupId())).getGroupLevel();
			int levelDifference = ((creature.getLevel() >= level) ? 0 : (level - creature.getLevel()));
			float damagePercent = damage / creature.getMaxHealth();
			int finalXP = (int) (damagePercent * baseXP);
			finalXP -= ((levelDifference > 20) ? (finalXP - 1) : (((levelDifference * 5) / 100) * finalXP));	
			core.playerService.giveExperience(player, finalXP);
		}
		
	}
	
	public int getBaseXP(CreatureObject creature) {
		
		int difficulty = creature.getDifficulty();
		int baseXP = 60;
		for (int i = 2; i <= creature.getLevel(); i++) {
			
			if(i < 25)
				baseXP += 3;
			else if(i < 50)
				baseXP += 4;
			else if(i < 75)
				baseXP += 5;
			else if(i < 100)
				baseXP += 6;
			else
				baseXP += 7;

		}
		
		
		//TODO: this is slightly inaccurate if the xp table in the prima guide is correct
		if(difficulty == 1) {
			baseXP += (6 + ((creature.getLevel() - 1) / 10) * 3);
		} else if(difficulty == 2) {
			baseXP += (20 + (creature.getLevel() - 1));
		}
		
		return baseXP;
		
	}
	
	public void awardGcw(AIActor actor) {
		CreatureObject npc = actor.getCreature();
		
		if (core.factionService.isPvpFaction(npc.getFaction())) {
			int gcwPoints = 5;
			
			if (npc.getDifficulty() == Difficulty.ELITE) {
				gcwPoints *= 2;
			}
			
			if (npc.getDifficulty() == Difficulty.BOSS) {
				gcwPoints *= 5;
			}
			
			//gcwPoints = actor.getMobileTemplate().getGcwPoints(); // We might want to make this get set in mobile templates if it was different for different npcs ie. assault squads.
			
			for (CreatureObject player : actor.getDamageMap().keySet()) {
				if (player.getGroupId() == 0) {
					if (player.getFaction().length() > 0 && player.getFactionStatus() > FactionStatus.OnLeave) {
						if ((npc.getLevel() / player.getLevel() * 100) < 86) {
							continue;
						}
						
						core.gcwService.addGcwPoints(player, gcwPoints, GcwType.Enemy);
					}
				} else {
					for (SWGObject object : ((GroupObject) core.objectService.getObject(player.getGroupId())).getMemberList()) {
						CreatureObject member = (CreatureObject) object;
						
						if (member == null) {
							continue;
						}
						
						if (npc.getPlanet().getName().equals(member.getPlanet().getName()) && npc.getPosition().getDistance(member.getPosition()) > 300) {
							continue;
						}
						
						if ((npc.getLevel() / member.getLevel() * 100) < 86) {
							continue;
						}
						
						if (member.getFaction().length() > 0 && member.getFactionStatus() > FactionStatus.OnLeave) {
							core.gcwService.addGcwPoints(member, gcwPoints, GcwType.Enemy);
						}
					}
				}
			}
		}
	}
	
}
