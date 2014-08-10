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
import tools.DevLog;

public class RetreatState extends AIState {
	
	private long retreatStartTime = 0L;
	private Point3D retreatStartPos = null;
	
	@Override
	public byte onEnter(AIActor actor) {
		/*synchronized(actor.getDamageMap()) {
			for(CreatureObject defender : actor.getDamageMap().keySet()) {
				actor.removeDefender(defender);
			}
		}*/
		DevLog.debugoutai(actor, "Charon", "AI RetreatState", "RetreatState ENTER!");
		CreatureObject creature = actor.getCreature();
		if (creature.getPosture()==13 || creature.getPosture()==14)
			return StateResult.FINISHED;
		creature.setInCombat(false);
		if (actor.getCreature().getOwnerId()>0){
			CreatureObject owner = (CreatureObject) NGECore.getInstance().objectService.getObject(actor.getCreature().getOwnerId());
			actor.setFollowObject(owner);
			actor.setCurrentState(new FollowState());
			return StateResult.FINISHED;
		} else
			actor.setFollowObject(null);
		
		retreatStartTime = System.currentTimeMillis();
		retreatStartPos = creature.getWorldPosition();
		actor.scheduleMovement();
		return StateResult.UNFINISHED;
	}

	@Override
	public byte onExit(AIActor actor) {
		// TODO Auto-generated method stub
		//actor.setCurrentState(actor.getIntendedPrimaryAIState());
		DevLog.debugoutai(actor, "Charon", "AI RetreatState", "RetreatState EXIT!");
		return StateResult.FINISHED;
	}

	@Override
	public byte move(AIActor actor) {
		DevLog.debugoutai(actor, "Charon", "AI RetreatState", "RetreatState MOVE!");
		Point3D retreatPosition = actor.getSpawnPosition();
		if (actor.getIntendedPrimaryAIState() instanceof PatrolState)
			retreatPosition = actor.getLastPositionBeforeStateChange();
		
		CreatureObject creature = actor.getCreature();
		if (creature.getPosture()==13 || creature.getPosture()==14)
			return StateResult.FINISHED;
		actor.setNextPosition(retreatPosition);
		actor.getMovementPoints().clear();
		actor.getMovementPoints().add(retreatPosition);
		doMove(actor);
		
		if (actor.getIntendedPrimaryAIState().getClass().equals(FollowState.class))
			return StateResult.FOLLOW;
		
		if(actor.getCreature().getWorldPosition().getDistance(retreatPosition) > 5) {
			actor.scheduleMovement();
		} else {

			if (actor.getIntendedPrimaryAIState().getClass().equals(IdleState.class))
				actor.setCurrentState(new IdleState());
			if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
				actor.setCurrentState(new PatrolState());
			if (actor.getIntendedPrimaryAIState().getClass().equals(LoiterState.class))
				actor.setCurrentState(new LoiterState());
			if (actor.getIntendedPrimaryAIState().getClass().equals(FollowState.class))
				actor.setCurrentState(new FollowState());

			return StateResult.FINISHED;
		}
		
		if(System.currentTimeMillis()> retreatStartTime + 5000 && creature.getWorldPosition().getDistance2D(retreatStartPos)<1.5){
			// Actor is locked in retreat state, get back to intended state
			if (actor.getIntendedPrimaryAIState().getClass().equals(IdleState.class))
				actor.setCurrentState(new IdleState());
			if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
				actor.setCurrentState(new PatrolState());
			if (actor.getIntendedPrimaryAIState().getClass().equals(LoiterState.class))
				actor.setCurrentState(new LoiterState());
			if (actor.getIntendedPrimaryAIState().getClass().equals(FollowState.class))
				actor.setCurrentState(new FollowState());

			return StateResult.FINISHED;
		}
		
		if(System.currentTimeMillis()> retreatStartTime + 40000){
			// Actor is locked in retreat state, get back to intended state after 40s, no matter what
			if (actor.getIntendedPrimaryAIState().getClass().equals(IdleState.class))
				actor.setCurrentState(new IdleState());
			if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
				actor.setCurrentState(new PatrolState());
			if (actor.getIntendedPrimaryAIState().getClass().equals(LoiterState.class))
				actor.setCurrentState(new LoiterState());
			if (actor.getIntendedPrimaryAIState().getClass().equals(FollowState.class))
				actor.setCurrentState(new FollowState());

			return StateResult.FINISHED;
		}
		
		return StateResult.UNFINISHED;
	}

	@Override
	public byte recover(AIActor actor) {
		// TODO Auto-generated method stub
		return 0;
	}

}
