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

import java.util.NoSuchElementException;
import java.util.Vector;
import main.NGECore;
import engine.resources.scene.Point3D;
import resources.objects.cell.CellObject;
import resources.objects.tangible.TangibleObject;
import services.ai.TurretAIActor;

public abstract class TurretAIState {

	public abstract byte onEnter(TurretAIActor actor) throws Exception;
	public abstract byte onExit(TurretAIActor actor) throws Exception;
	public abstract byte move(TurretAIActor actor) throws Exception;
	public abstract byte recover(TurretAIActor actor) throws Exception;
	
	public enum StateResult {;
	
		// default error state result
		public static final byte NONE = 0;
		// finished with state
		public static final byte FINISHED = 1;
		// unfinished with state, call recover()
		public static final byte UNFINISHED = 2;
		public static final byte DEAD = 3;
		// for deaggro or idling
		public static final byte IDLE = 4;
		public static final byte PATROL = 5;
		public static final byte LOITER = 6;
		public static final byte FOLLOW = 7;
	
	}
	
	public boolean findNewPosition(TurretAIActor actor, float speed, float stopDistance, Point3D newPosition) {
		
		speed *= 0.5; // 2 updates per second
		TangibleObject creature = actor.getCreature();
		NGECore core = NGECore.getInstance();
		Point3D currentPosition = creature.getPosition();
		Point3D targetPosition = null;
		float maxDistance = stopDistance;
		boolean finished = false;
		float dx, dz, newX = 0, newY = 0, newZ = 0;
		Vector<Point3D> movementPoints = actor.getMovementPoints();
		//Vector<Point3D> patrolPoints = actor.getPatrolPoints();
		CellObject cell = null;
		//while(!finished && movementPoints.size() != 0) {
		
		
			
		if (movementPoints.size() != 0){
			try{
				targetPosition = movementPoints.firstElement();
			} catch (NoSuchElementException e) {
				targetPosition = currentPosition;
			}
		}
		
		
		Vector<Point3D> path = core.aiService.findPath(creature.getPlanetId(), currentPosition, targetPosition);
		
		float distanceToTarget = targetPosition.getWorldPosition().getDistance(creature.getWorldPosition());
		
		if(distanceToTarget > stopDistance) {
			maxDistance = Math.min(speed, distanceToTarget - stopDistance);
		} else {
			return false;
		}
		
		Point3D oldPosition = null;
		float pathDistance = 0;
		
		
		
		for(int i = 1; i < path.size() && !finished; i++) {
			
			Point3D currentPathPosition = path.get(i);
			cell = currentPathPosition.getCell();
			
			if(oldPosition == null)
				oldPosition = path.get(0);
			Point3D oldWorldPos = oldPosition.getWorldPosition();
			
			pathDistance += oldWorldPos.getDistance(currentPathPosition.getWorldPosition());
			if(pathDistance >= maxDistance || i == path.size() - 1 || currentPathPosition.getCell() != creature.getContainer()) {
				
				finished = true;
				
				if(movementPoints.size() != 0 && currentPosition.getWorldPosition().getDistance(currentPathPosition.getWorldPosition()) <= stopDistance && cell == creature.getContainer()) {
					if(i == path.size() - 1)
						movementPoints.remove(0);
					finished = false;
				} else {
				
					if(cell == null)
						oldPosition = oldPosition.getWorldPosition();
					else {
						if(oldPosition.getCell() == null)
							oldPosition = core.simulationService.convertPointToModelSpace(oldPosition, cell.getContainer());
					}
					
					if(pathDistance > maxDistance) {
						
						float distance = oldWorldPos.getDistance(currentPathPosition.getWorldPosition());
						float travelDistance = distance - (pathDistance - maxDistance);
						// temp fix for melee npcs
						travelDistance *= 1.3;
						if(travelDistance <= 0) {
							newX = currentPathPosition.x;
							newZ = currentPathPosition.z;
						} else {
							
							if(distance > 0) {
								dx = currentPathPosition.x - oldPosition.x;
								dz = currentPathPosition.z - oldPosition.z;
								float deltaDist = (float) Math.sqrt((dx * dx) + (dz * dz));
								newX = (float) (oldPosition.x + (speed * (dx / deltaDist)));
								newZ = (float) (oldPosition.z + (speed * (dz / deltaDist)));
								
							} else {
								newX = currentPathPosition.x;
								newZ = currentPathPosition.z;
							}
							
						}
						
						if(cell == null) {
							float height = core.terrainService.getHeight(creature.getPlanetId(), newX, newZ);
							newY = height;
						} else {
							newY = currentPathPosition.y;
						}
					}
				}
				
			} else {
				newX = currentPathPosition.x;
				newZ = currentPathPosition.z;
				newY = core.terrainService.getHeight(creature.getPlanetId(), newX, newZ);			
			}
			oldPosition = currentPathPosition;
		}
		//}
		newPosition.x = newX;
		newPosition.y = newY;
		newPosition.z = newZ;
		newPosition.setCell(cell);

		return true;
	}
	
	
	public boolean findNewLoiterPosition(TurretAIActor actor, float speed, float stopDistance, Point3D newPosition) {
		
		speed *= 0.5; // 2 updates per second
		TangibleObject creature = actor.getCreature();
		NGECore core = NGECore.getInstance();
		Point3D currentPosition = creature.getPosition();
		Point3D targetPosition = null;
		float maxDistance = stopDistance;
		boolean finished = false;
		float dx, dz, newX = 0, newY = 0, newZ = 0;
		//Vector<Point3D> movementPoints = actor.getMovementPoints();
		CellObject cell = null;
		
			targetPosition = actor.getLoiterDestination(); 

			Vector<Point3D> path = core.aiService.findPath(creature.getPlanetId(), currentPosition, targetPosition);
			
			float distanceToTarget = targetPosition.getWorldPosition().getDistance(creature.getWorldPosition());
			
			if(distanceToTarget > stopDistance) {
				maxDistance = Math.min(speed, distanceToTarget - stopDistance);
			} else {
				return false;
			}
			
			Point3D oldPosition = null;
			float pathDistance = 0;
			
			for(int i = 1; i < path.size() && !finished; i++) {
				
				Point3D currentPathPosition = path.get(i);
				cell = currentPathPosition.getCell();
				
				if(oldPosition == null)
					oldPosition = path.get(0);
				Point3D oldWorldPos = oldPosition.getWorldPosition();
				
				pathDistance += oldWorldPos.getDistance(currentPathPosition.getWorldPosition());
				if(pathDistance >= maxDistance || i == path.size() - 1 || currentPathPosition.getCell() != creature.getContainer()) {
					
					finished = true;
					
					if(currentPosition.getWorldPosition().getDistance(currentPathPosition.getWorldPosition()) <= stopDistance && cell == creature.getContainer()) {
//						if(i == path.size() - 1)
//							movementPoints.remove(0);
						finished = false;
					} else {
					
						if(cell == null)
							oldPosition = oldPosition.getWorldPosition();
						else {
							if(oldPosition.getCell() == null)
								oldPosition = core.simulationService.convertPointToModelSpace(oldPosition, cell.getContainer());
						}
						
						if(pathDistance > maxDistance) {
							
							float distance = oldWorldPos.getDistance(currentPathPosition.getWorldPosition());
							float travelDistance = distance - (pathDistance - maxDistance);
							// temp fix for melee npcs
							travelDistance *= 1.3;
							if(travelDistance <= 0) {
								newX = currentPathPosition.x;
								newZ = currentPathPosition.z;
							} else {
								
								if(distance > 0) {
									dx = currentPathPosition.x - oldPosition.x;
									dz = currentPathPosition.z - oldPosition.z;
									float deltaDist = (float) Math.sqrt((dx * dx) + (dz * dz));
									newX = (float) (oldPosition.x + (speed * (dx / deltaDist)));
									newZ = (float) (oldPosition.z + (speed * (dz / deltaDist)));
									
								} else {
									newX = currentPathPosition.x;
									newZ = currentPathPosition.z;
								}
								
							}
							
							if(cell == null) {
								float height = core.terrainService.getHeight(creature.getPlanetId(), newX, newZ);
								newY = height;
							} else {
								newY = currentPathPosition.y;
							}
						}
					}
					
				} else {
					newX = currentPathPosition.x;
					newZ = currentPathPosition.z;
					newY = core.terrainService.getHeight(creature.getPlanetId(), newX, newZ);			
				}
				oldPosition = currentPathPosition;
			}

		newPosition.x = newX;
		newPosition.y = newY;
		newPosition.z = newZ;
		newPosition.setCell(cell);

		return true;
	}
	
	public boolean findNewFollowPosition(TurretAIActor actor, float speed, float stopDistance, Point3D newPosition) {
		
		speed *= 0.5; // 2 updates per second
		TangibleObject creature = actor.getCreature();
		NGECore core = NGECore.getInstance();
		Point3D currentPosition = creature.getPosition();
		Point3D targetPosition = null;
		float maxDistance = stopDistance;
		boolean finished = false;
		float dx, dz, newX = 0, newY = 0, newZ = 0;
		Vector<Point3D> movementPoints = actor.getMovementPoints();
		Vector<Point3D> patrolPoints = actor.getPatrolPoints();
		CellObject cell = null;
		//while(!finished && movementPoints.size() != 0) {
		
		
		if (movementPoints.size() != 0)
				targetPosition = movementPoints.firstElement();
		
		if (actor.getCurrentState().getClass().equals(PatrolState.class)){
			targetPosition = patrolPoints.get(actor.getPatrolPointIndex());
		}
		
		if (actor.getCurrentState().getClass().equals(FollowState.class)){
			targetPosition = actor.getFollowObject().getWorldPosition();
		}
		
		Vector<Point3D> path = core.aiService.findPath(creature.getPlanetId(), currentPosition, targetPosition);
		
		float distanceToTarget = targetPosition.getWorldPosition().getDistance(creature.getWorldPosition());
		
		if(distanceToTarget > stopDistance) {
			maxDistance = Math.min(speed, distanceToTarget - stopDistance);
		} else {
			return false;
		}
		
		Point3D oldPosition = null;
		float pathDistance = 0;
		
		
		
		for(int i = 1; i < path.size() && !finished; i++) {
			
			Point3D currentPathPosition = path.get(i);
			cell = currentPathPosition.getCell();
			
			if(oldPosition == null)
				oldPosition = path.get(0);
			Point3D oldWorldPos = oldPosition.getWorldPosition();
			
			pathDistance += oldWorldPos.getDistance(currentPathPosition.getWorldPosition());
			if(pathDistance >= maxDistance || i == path.size() - 1 || currentPathPosition.getCell() != creature.getContainer()) {
				
				finished = true;
				
				if(movementPoints.size() != 0 && currentPosition.getWorldPosition().getDistance(currentPathPosition.getWorldPosition()) <= stopDistance && cell == creature.getContainer()) {
					if(i == path.size() - 1)
						movementPoints.remove(0);
					finished = false;
				} else {
				
					if(cell == null)
						oldPosition = oldPosition.getWorldPosition();
					else {
						if(oldPosition.getCell() == null)
							oldPosition = core.simulationService.convertPointToModelSpace(oldPosition, cell.getContainer());
					}
					
					if(pathDistance > maxDistance) {
						
						float distance = oldWorldPos.getDistance(currentPathPosition.getWorldPosition());
						float travelDistance = distance - (pathDistance - maxDistance);
						// temp fix for melee npcs
						travelDistance *= 1.3;
						if(travelDistance <= 0) {
							newX = currentPathPosition.x;
							newZ = currentPathPosition.z;
						} else {
							
							if(distance > 0) {
								dx = currentPathPosition.x - oldPosition.x;
								dz = currentPathPosition.z - oldPosition.z;
								float deltaDist = (float) Math.sqrt((dx * dx) + (dz * dz));
								newX = (float) (oldPosition.x + (speed * (dx / deltaDist)));
								newZ = (float) (oldPosition.z + (speed * (dz / deltaDist)));
								
							} else {
								newX = currentPathPosition.x;
								newZ = currentPathPosition.z;
							}
							
						}
						
						if(cell == null) {
							float height = core.terrainService.getHeight(creature.getPlanetId(), newX, newZ);
							newY = height;
						} else {
							newY = currentPathPosition.y;
						}
					}
				}
				
			} else {
				newX = currentPathPosition.x;
				newZ = currentPathPosition.z;
				newY = core.terrainService.getHeight(creature.getPlanetId(), newX, newZ);			
			}
			oldPosition = currentPathPosition;
		}
		//}
		newPosition.x = newX;
		newPosition.y = newY;
		newPosition.z = newZ;
		newPosition.setCell(cell);

		return true;
	}
	
	public void doMove(TurretAIActor actor) {
		
	}
	
	public void doPatrol(TurretAIActor actor) {
		
	}
	
	public void doLoiter(TurretAIActor actor) {
		
	}
	
	public void doFollow(TurretAIActor actor) {
		
	}

}
