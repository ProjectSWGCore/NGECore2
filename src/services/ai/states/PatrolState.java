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
package services.ai.states;

import resources.objects.creature.CreatureObject;
import services.ai.AIActor;
import services.ai.states.AIState.StateResult;
import tools.DevLog;

public class PatrolState extends AIState {
	
	private boolean locked = false;
	
	public PatrolState(){
		stateID = autoStateID++;
	}
	
	@Override
	public byte onEnter(AIActor actor) {
		
		DevLog.debugoutai(actor, "Charon", "AI PatrolState", "PATROL ENTERED!");		
		CreatureObject creature = actor.getCreature();
		if(creature.getPosture() == 14)
			return StateResult.DEAD;
		actor.scheduleMovement();
		actor.scheduleRecovery();
		return StateResult.UNFINISHED;
	}

	@Override
	public byte onExit(AIActor actor) {
		DevLog.debugoutai(actor, "Charon", "AI PatrolState", "EXITING PATROL STATE!");	
		CreatureObject creature = actor.getCreature();
		if(creature.getPosture() == 14)
			actor.setLastPositionBeforeStateChange(actor.getCreature().getWorldPosition());
		locked = true;
		return StateResult.FINISHED;
	}

	@Override
	public byte move(AIActor actor) {
		DevLog.debugoutai(actor, "Charon", "AI PatrolState", "PATROL MOVE!");
		CreatureObject creature = actor.getCreature();
		if(creature.getPosture() == 14)
			return StateResult.DEAD;
		
		if (locked){
			System.out.println("PatrolState locked!");
			return StateResult.FINISHED;
		}

		if(creature.isInCombat()){
			//System.out.println("PatrolState locking!");
			locked = true;
			return StateResult.FINISHED;
		}
		doPatrol(actor);
		actor.scheduleMovement();
		return StateResult.UNFINISHED;
	}

	@Override
	public byte recover(AIActor actor) {
		if (actor.getFollowObject()!=null)
			System.out.println("recover follow object is not null");
		if (actor.getFollowObject()!=null)
			System.out.println("recover follow object is not null");
		return StateResult.UNFINISHED;
	}

}
