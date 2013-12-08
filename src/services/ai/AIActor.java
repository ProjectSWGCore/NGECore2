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

import engine.resources.scene.Point3D;
import resources.objects.creature.CreatureObject;
import services.ai.states.AIState;

public class AIActor {
	
	private CreatureObject creature;
	private Point3D spawnPosition;
	private volatile AIState currentState;
	private CreatureObject followObject;
	
	public AIActor(CreatureObject creature, Point3D spawnPosition) {
		this.creature = creature;
		this.spawnPosition = spawnPosition;
	}

	public CreatureObject getCreature() {
		return creature;
	}

	public void setCreature(CreatureObject creature) {
		this.creature = creature;
	}

	public Point3D getSpawnPosition() {
		return spawnPosition;
	}

	public void setSpawnPosition(Point3D spawnPosition) {
		this.spawnPosition = spawnPosition;
	}
	
	public void doAggro(CreatureObject defender) {
		creature.addDefender(defender);
	}

	public AIState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(AIState currentState) {
		if(currentState == this.currentState)
			return;
		this.currentState.onExit();
		this.currentState = currentState;
		currentState.onEnter();
	}

	public CreatureObject getFollowObject() {
		return followObject;
	}

	public void setFollowObject(CreatureObject followObject) {
		this.followObject = followObject;
	}

}
