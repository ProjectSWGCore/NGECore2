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

import main.NGECore;
import engine.resources.scene.Point3D;
import resources.objects.creature.CreatureObject;
import services.ai.AIActor;

public class RetreatState extends AIState {

	@Override
	public byte onEnter(AIActor actor) {
		/*synchronized(actor.getDamageMap()) {
			for(CreatureObject defender : actor.getDamageMap().keySet()) {
				actor.removeDefender(defender);
			}
		}*/
		NGECore.getInstance().aiService.logAI("RetreatState ENTER ");
		
		if (actor.getCreature().getOwnerId()>0){
			CreatureObject owner = (CreatureObject) NGECore.getInstance().objectService.getObject(actor.getCreature().getOwnerId());
			actor.setFollowObject(owner);
			actor.setCurrentState(new FollowState());
			return StateResult.FINISHED;
		} else
			actor.setFollowObject(null);
		actor.scheduleMovement();
		return StateResult.UNFINISHED;
	}

	@Override
	public byte onExit(AIActor actor) {
		// TODO Auto-generated method stub
		//actor.setCurrentState(actor.getIntendedPrimaryAIState());
		return StateResult.FINISHED;
	}

	@Override
	public byte move(AIActor actor) {
		
		Point3D retreatPosition = actor.getSpawnPosition();
		if (actor.getIntendedPrimaryAIState() instanceof PatrolState)
			retreatPosition = actor.getLastPositionBeforeStateChange();
		
		actor.setNextPosition(retreatPosition);
		actor.getMovementPoints().clear();
		actor.getMovementPoints().add(retreatPosition);
		doMove(actor);
		
		if (actor.getIntendedPrimaryAIState().getClass().equals(FollowState.class))
			return StateResult.FOLLOW;
		
		if(actor.getCreature().getWorldPosition().getDistance(retreatPosition) > 5) {
			//NGECore.getInstance().aiService.logAI("RetreatState ENTER move if case dist " + actor.getCreature().getWorldPosition().getDistance(retreatPosition));
			actor.scheduleMovement();
		} else {
			//NGECore.getInstance().aiService.logAI("RetreatState ENTER move else case");
			if (actor.getIntendedPrimaryAIState().getClass().equals(IdleState.class))
				return StateResult.IDLE;
			if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
				return StateResult.PATROL;
			if (actor.getIntendedPrimaryAIState().getClass().equals(LoiterState.class))
				return StateResult.LOITER;
			if (actor.getIntendedPrimaryAIState().getClass().equals(FollowState.class))
				return StateResult.FOLLOW;
		}
		return StateResult.UNFINISHED;
	}

	@Override
	public byte recover(AIActor actor) {
		// TODO Auto-generated method stub
		return 0;
	}

}
