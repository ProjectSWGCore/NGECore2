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
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import resources.common.SpawnPoint;
import resources.objects.cell.CellObject;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.ai.AIActor;
import tools.DevLog;

@SuppressWarnings("unused")
public abstract class AIState {

	public abstract byte onEnter(AIActor actor) throws Exception;
	public abstract byte onExit(AIActor actor) throws Exception;
	public abstract byte move(AIActor actor) throws Exception;
	public abstract byte recover(AIActor actor) throws Exception;
	
	public static long lastExecutionTime = 0L;
	public static long stateID = 0L;
	public static long autoStateID = 0L;
	
	
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
		public static final byte ATTACK = 8;
	
	}
	
	public boolean findNewPosition(AIActor actor, float speed, float stopDistance, Point3D newPosition) {
		
		speed *= 0.5; // 2 updates per second
		CreatureObject creature = actor.getCreature();
		if (!actor.isActorAlive())
			return false; // Suppress any further movement when actor is destroyed to prevent reappearing
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
		
		if ((!(actor.getCurrentState() instanceof FollowState)) && movementPoints.size() == 0 && patrolPoints.size() == 0) {
			DevLog.debugoutai(actor, "Charon", "AI State findnewpos", "Breakout1");
			return false;
		}
			
		if (movementPoints.size() != 0){
			try{
				targetPosition = movementPoints.firstElement();
			} catch (NoSuchElementException e) {
				targetPosition = currentPosition;
			}
		}
		
		if (actor.getCurrentState().getClass().equals(PatrolState.class)){
			targetPosition = patrolPoints.get(actor.getPatrolPointIndex());
		}
		
		if (actor.getCurrentState().getClass().equals(WithdrawalState.class)){
			targetPosition = patrolPoints.get(actor.getPatrolPointIndex());
		}
		
		if (actor.getCurrentState().getClass().equals(FollowState.class)){
			targetPosition = actor.getFollowObject().getWorldPosition();
		}
		
		Vector<Point3D> path = core.aiService.findPath(creature.getPlanetId(), currentPosition, targetPosition);
		
		if (targetPosition==null){
			DevLog.debugoutai(actor, "Charon", "AI State findnewpos", "Breakout2");
			return false;
		}
		float distanceToTarget = targetPosition.getWorldPosition().getDistance(creature.getWorldPosition());
		
		if(distanceToTarget > stopDistance) {
			maxDistance = Math.min(speed, distanceToTarget - stopDistance);
		} else {
			/*
			DevLog.debugoutai(actor, "Charon", "AI State findnewpos", "Breakout3");
			if (actor.getCurrentState().getClass().equals(RetreatState.class)){
				if(actor.getIntendedPrimaryAIState().equals(PatrolState.class)){
					actor.setCurrentState(new PatrolState());
				}	
				if(actor.getIntendedPrimaryAIState().equals(FollowState.class)){
					actor.setCurrentState(new FollowState());
				}
				if(actor.getIntendedPrimaryAIState().equals(LoiterState.class)){
					actor.setCurrentState(new LoiterState());
				}
			}
			*/		
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
					if(i == path.size() - 1 && movementPoints.size()>0){
						try {
							movementPoints.remove(0);
						} catch (ArrayIndexOutOfBoundsException ex){} // No idea why a vector with size>0 throws this sometimes
					}
					finished = false;
				} else {
				
					if(cell == null)
						oldPosition = oldPosition.getWorldPosition();
					else {
						if(oldPosition.getCell() == null)
							oldPosition = core.simulationService.convertPointToModelSpace(oldPosition, cell.getContainer());
					}
					
					if(pathDistance > maxDistance) {
						
						//float distance = oldWorldPos.getDistance(currentPathPosition.getWorldPosition());
						float distance = NGECore.getInstance().aiService.distanceSquared(oldWorldPos, currentPathPosition.getWorldPosition()); // Ok to use, as distance is not directly used later
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
								//float deltaDist = (float) Math.sqrt((dx * dx) + (dz * dz)); 
								int fullong=0; // Approximation->faster
								int halfshort=0;
								float tempdx = dx;
								float tempdz = dz;
								if (tempdx<0)
									tempdx*=-1;
								if (tempdz<0)
									tempdz*=-1;
								if(tempdx > tempdz) {
									fullong = (int)(tempdx*100);
									halfshort = ((int)(tempdz*100)) >> 1;
								} else {
									fullong = (int)(tempdz*100);
									halfshort = ((int)(tempdx*100)) >> 1;
								}
								float deltaDist = (fullong + halfshort)/100;

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
	
	
	public boolean findNewLOSPosition(AIActor actor, float speed, float stopDistance, Point3D newPosition) {
		
		speed *= 0.5; // 2 updates per second
		CreatureObject creature = actor.getCreature();
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
		
		if ((!(actor.getCurrentState() instanceof FollowState)) && movementPoints.size() == 0 && patrolPoints.size() == 0) {
			return false;
			}
			
			if (movementPoints.size() != 0){
				try{
					targetPosition = movementPoints.firstElement();
				} catch (NoSuchElementException e) {
					targetPosition = currentPosition;
				}
			}
			
			if (actor.getCurrentState().getClass().equals(PatrolState.class)){
				targetPosition = patrolPoints.get(actor.getPatrolPointIndex());
			}
			
			if (actor.getCurrentState().getClass().equals(FollowState.class)){
				targetPosition = actor.getFollowObject().getWorldPosition();
			}
			
			Vector<Point3D> path = core.aiService.findPath(creature.getPlanetId(), currentPosition, targetPosition);
			
			if (targetPosition==null)
				return false;
			if (actor.getFollowObject()==null)
				return false;
			
			int attempts = 0;
			float LOSdistance = targetPosition.getWorldPosition().getDistance(actor.getFollowObject().getWorldPosition());
			Point3D LOSorigin = actor.getFollowObject().getWorldPosition();
			float deltaX = actor.getFollowObject().getWorldPosition().x-targetPosition.getWorldPosition().x;
			float deltaZ = actor.getFollowObject().getWorldPosition().z-targetPosition.getWorldPosition().z;
			float originAngle = (float) (Math.atan2(deltaX,deltaZ));
			int sign = 0;
			while (!core.simulationService.checkLineOfSight(actor.getFollowObject(),targetPosition) && attempts<160){
				
				float angle = 0;
				if (sign==0){
					angle = originAngle + attempts;
					sign = 1;
				} else {
					angle = originAngle - attempts;
					sign = 0;
				}
				if (actor.getFollowObject() instanceof CreatureObject){
					if (((CreatureObject)actor.getFollowObject()).isPlayer()){
						System.out.println("CORRECTING LOS");
					}
				}
						 
				
				targetPosition = new Point3D((float) (LOSorigin.x + LOSdistance * Math.cos(angle)), 0, (float) (LOSorigin.z + LOSdistance * Math.sin(angle)));
				attempts++;
			}
			
				
			
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
	
	
	public boolean findNewLoiterPosition(AIActor actor, float speed, float stopDistance, Point3D newPosition) {
		
		speed *= 0.5; // 2 updates per second
		CreatureObject creature = actor.getCreature();
		NGECore core = NGECore.getInstance();
		Point3D currentPosition = creature.getPosition();
		Point3D targetPosition = null;
		float maxDistance = stopDistance;
		boolean finished = false;
		float dx, dz, newX = 0, newY = 0, newZ = 0;
		Vector<Point3D> movementPoints = actor.getMovementPoints();
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
	
	public boolean findNewFollowPosition(AIActor actor, float speed, float stopDistance, Point3D newPosition) {
		
		speed *= 0.5; // 2 updates per second
		CreatureObject creature = actor.getCreature();
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
		
		if ((!(actor.getCurrentState() instanceof FollowState)) && movementPoints.size() == 0 && patrolPoints.size() == 0) {
			return false;
			}
			
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
	
	public void doMove(AIActor actor) {
		
		NGECore core = NGECore.getInstance();

		CreatureObject creature = actor.getCreature();
		if(creature.getPosture() == 14 || creature.getPosture() == 13) {
			actor.setFollowObject(null);
			return;
		}
		TangibleObject target = actor.getFollowObject();
		float speed = (float) creature.getRunSpeed();
		if (creature.getAttachment("IsSlowVehicle")!=null){
			speed = (float) creature.getWalkSpeed();
		}
		float maxDistance = 6;
		if(creature.getWeaponId() != 0) {
			WeaponObject weapon = (WeaponObject) core.objectService.getObject(creature.getWeaponId());
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		} else if(creature.getSlottedObject("default_weapon") != null) {
			WeaponObject weapon = (WeaponObject) creature.getSlottedObject("default_weapon");
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		}
		Point3D currentPosition = creature.getWorldPosition();
		
		if(target != null && !core.simulationService.checkLineOfSight(creature, target))
			maxDistance = 1;
		
		Point3D newPosition = new Point3D();
		
		boolean foundNewPos = false;
		foundNewPos = findNewPosition(actor, speed, maxDistance, newPosition);

		if(!foundNewPos || (newPosition.x == 0 && newPosition.z == 0))
			return;
		
		Point3D newWorldPos = newPosition.getWorldPosition();
		float direction = (float) Math.atan2(newWorldPos.x - currentPosition.x, newWorldPos.z - currentPosition.z);
		if(direction < 0)
			direction = (float) (2 * Math.PI + direction);
		Quaternion quaternion = new Quaternion((float) Math.cos(direction / 2), 0, (float) Math.sin(direction / 2), 0);
        if (quaternion.y < 0.0f && quaternion.w > 0.0f) {
        	quaternion.y *= -1;
        	quaternion.w *= -1;
        }
        
		core.simulationService.moveObject(creature, newPosition, quaternion, creature.getMovementCounter(), speed, newPosition.getCell());	
	}
	
	public void doReposition(AIActor actor){
		
		NGECore core = NGECore.getInstance();

		CreatureObject creature = actor.getCreature();
		if(creature.getPosture() == 14 || creature.getPosture() == 13) {
			actor.setFollowObject(null);
			return;
		}
		TangibleObject target = actor.getLastTarget();
		float speed = (float) creature.getRunSpeed();
		float maxDistance = 6;
		if(creature.getWeaponId() != 0) {
			WeaponObject weapon = (WeaponObject) core.objectService.getObject(creature.getWeaponId());
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		} else if(creature.getSlottedObject("default_weapon") != null) {
			WeaponObject weapon = (WeaponObject) creature.getSlottedObject("default_weapon");
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		}
		Point3D currentPosition = creature.getWorldPosition();
		
		if(target != null && !core.simulationService.checkLineOfSight(creature, target))
			maxDistance = 1;
		
		Point3D newPosition = actor.getRepositionLocation();

		if(newPosition.x == 0 && newPosition.z == 0)
			return;
		
		Point3D newWorldPos = newPosition.getWorldPosition();
		float direction = (float) Math.atan2(newWorldPos.x - currentPosition.x, newWorldPos.z - currentPosition.z);
		if(direction < 0)
			direction = (float) (2 * Math.PI + direction);
		Quaternion quaternion = new Quaternion((float) Math.cos(direction / 2), 0, (float) Math.sin(direction / 2), 0);
        if (quaternion.y < 0.0f && quaternion.w > 0.0f) {
        	quaternion.y *= -1;
        	quaternion.w *= -1;
        }
        
		core.simulationService.moveObject(creature, newPosition, quaternion, creature.getMovementCounter(), speed, newPosition.getCell());
		
	}
	
	public void doPatrol(AIActor actor) {
		//NGECore.getInstance().aiService.logAI("AI STATE doPatrol");
		
		NGECore core = NGECore.getInstance();

		CreatureObject creature = actor.getCreature();

		if(creature.getPosture() == 14 || creature.getPosture() == 13) {
			actor.setFollowObject(null);
			return;
		}
		
		if (creature.isInCombat()){
			return;
		}
		
		
		TangibleObject target = actor.getFollowObject();
		float speed = (float) creature.getWalkSpeed();
		float maxDistance = 6;
		if(creature.getWeaponId() != 0) {
			WeaponObject weapon = (WeaponObject) core.objectService.getObject(creature.getWeaponId());
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		} else if(creature.getSlottedObject("default_weapon") != null) {
			WeaponObject weapon = (WeaponObject) creature.getSlottedObject("default_weapon");
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		}
		Point3D currentPosition = creature.getWorldPosition();
		
//		if(target != null && !core.simulationService.checkLineOfSight(creature, target))
//			maxDistance = 1;
		
		// Manage Patrol points
		maxDistance = 1;
		if (actor.getPatrolPoints().size()==0)
			return;
		Point3D currentDestination = actor.getPatrolPoints().get(actor.getPatrolPointIndex());
		//System.out.println("currentPosition.getDistance2D(currentDestination) " + currentPosition.getDistance2D(currentDestination));
		if (NGECore.getInstance().aiService.distanceSquared2D(currentPosition, currentDestination)<4){
		//if (currentPosition.getDistance2D(currentDestination)<4){
			if (actor.getPatrolPointIndex()<actor.getPatrolPoints().size()-1){
				actor.setPatrolPointIndex(actor.getPatrolPointIndex()+1);
			} else {
				if (actor.isPatrolLoop())
					actor.setPatrolPointIndex(0);
				else {
					String isInvader = (String) creature.getAttachment("IsInvader");
					if (isInvader==null)
						return;
					// If invader check for in weapon range general
					if (core.invasionService.getDefensiveGeneral()!=null){
						if (core.invasionService.getInvasionPhase()==3 && core.invasionService.getDefensiveGeneral().getPosture()!=13 && core.invasionService.getDefensiveGeneral().getPosture()!=14 && core.invasionService.getDistanceToDefensiveGeneral(creature)<2500){
							actor.addDefender(core.invasionService.getDefensiveGeneral());
							return;
						}
					}
					
					// Check if there are more than 5 invaders at same spot
					if (NGECore.getInstance().simulationService.getAllNearSameFactionNPCs(7, creature).size()>=4 && NGECore.getInstance().invasionService.getInvasionPhase()!=3){
						actor.setAIactive(false); // switch off auto-target-recognition to counter lag
						actor.setCurrentState(new IdleState());
						//System.out.println("AI switched off!");
						return;						
					}
					
					//actor.setCurrentState(new IdleState());
					// Wait state() Since this state does not require move,recover and all that its simple task

					//NGECore.getInstance().aiService.waitForEvent(actor, NGECore.getInstance().invasionService, "isDefendingGeneralAlive", false, WithdrawalState.class);
					
					return; // Last Patrol point reached and no loop
				}
			}
		}
		
		Point3D newPosition = new Point3D();
		boolean foundNewPos = findNewPosition(actor, speed, maxDistance, newPosition);
		
		
		
		
		if(!foundNewPos || (newPosition.x == 0 && newPosition.z == 0))
			return;
		
		Point3D newWorldPos = newPosition.getWorldPosition();
		float direction = (float) Math.atan2(newWorldPos.x - currentPosition.x, newWorldPos.z - currentPosition.z);
		if(direction < 0)
			direction = (float) (2 * Math.PI + direction);
		Quaternion quaternion = new Quaternion((float) Math.cos(direction / 2), 0, (float) Math.sin(direction / 2), 0);
        if (quaternion.y < 0.0f && quaternion.w > 0.0f) {
        	quaternion.y *= -1;
        	quaternion.w *= -1;
        }
        
//        if (newPosition.getCell()==null)
//        	System.out.println("newPosition.getCell() is NULL");
        try{
        	core.simulationService.moveObject(creature, newPosition, quaternion, creature.getMovementCounter(), speed, newPosition.getCell());
        } catch (NullPointerException e) {
        	// Just to identify what exactly is null here
//        	if (creature==null)
//        		System.out.print("creature==null" );
//        	if (newPosition==null)
//        		System.out.print("newPosition==null" );
//        	if (quaternion==null)
//        		System.out.print("quaternion==null" );
//        	if (newPosition.getCell()==null)
//        		System.out.print("newPosition.getCell()==null" );
//
//        	System.out.print("creature.getMovementCounter() " + creature.getMovementCounter());
        }
		
	}
	
	public void doWithdrawal(AIActor actor) {
		//NGECore.getInstance().aiService.logAI("AI STATE doPatrol");
		
		NGECore core = NGECore.getInstance();
		
		if(actor==null)
			return;		
		CreatureObject creature = actor.getCreature();
		if(creature==null)
			return;
		
//		if (creature.getPlanet().getName().equals("talus") && creature.getTemplate().contains("stormtrooper")){
//			System.out.println("actor " + actor.getActorID() + " stateID " + stateID + " creatureID"+ creature.getObjectID()+ " TIMEDIFF: " + (System.currentTimeMillis()-lastExecutionTime));
//			lastExecutionTime = System.currentTimeMillis();
//		}
		if(creature.getPosture() == 14 || creature.getPosture() == 13) {
			actor.setFollowObject(null);
			return;
		}
		
//		if (creature.isInCombat()){
//			return;
//		}
		
		
		TangibleObject target = actor.getFollowObject();
		float speed = (float) creature.getWalkSpeed();
		float maxDistance = 6;
		if(creature.getWeaponId() != 0) {
			WeaponObject weapon = (WeaponObject) core.objectService.getObject(creature.getWeaponId());
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		} else if(creature.getSlottedObject("default_weapon") != null) {
			WeaponObject weapon = (WeaponObject) creature.getSlottedObject("default_weapon");
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		}
		Point3D currentPosition = creature.getWorldPosition();
		
//		if(target != null && !core.simulationService.checkLineOfSight(creature, target))
//			maxDistance = 1;
		
		// Manage Patrol points
		maxDistance = 1;
		if (actor.getPatrolPoints().size()==0)
			return;
		if (actor.getPatrolPointIndex()<=0)
			actor.setPatrolPointIndex(0);
		Point3D currentDestination = actor.getPatrolPoints().get(actor.getPatrolPointIndex());
		//System.out.println("currentPosition.getDistance2D(currentDestination) " + currentPosition.getDistance2D(currentDestination));
		if (NGECore.getInstance().aiService.distanceSquared2D(currentPosition, currentDestination)<4){
		//if (currentPosition.getDistance2D(currentDestination)<4){
			if (actor.getPatrolPointIndex()>0){
				actor.setPatrolPointIndex(actor.getPatrolPointIndex()-1);
			} else {
				if (actor!=null)
					actor.destroyActor();
				if (creature!=null){
					core.simulationService.remove(creature, creature.getWorldPosition().x, creature.getWorldPosition().z, true); // Make sure
					core.objectService.destroyObject(creature.getObjectID());	
				}
				return; // First Patrol point reached
			}
		}
		
		Point3D newPosition = new Point3D();
		boolean foundNewPos = findNewPosition(actor, speed, maxDistance, newPosition);
		
		
		
		
		if(!foundNewPos || (newPosition.x == 0 && newPosition.z == 0))
			return;
		
		Point3D newWorldPos = newPosition.getWorldPosition();
		float direction = (float) Math.atan2(newWorldPos.x - currentPosition.x, newWorldPos.z - currentPosition.z);
		if(direction < 0)
			direction = (float) (2 * Math.PI + direction);
		Quaternion quaternion = new Quaternion((float) Math.cos(direction / 2), 0, (float) Math.sin(direction / 2), 0);
        if (quaternion.y < 0.0f && quaternion.w > 0.0f) {
        	quaternion.y *= -1;
        	quaternion.w *= -1;
        }
        
//        if (newPosition.getCell()==null)
//        	System.out.println("newPosition.getCell() is NULL");
        try{
        	core.simulationService.moveObject(creature, newPosition, quaternion, creature.getMovementCounter(), speed, newPosition.getCell());
        } catch (NullPointerException e) {
        	// Just to identify what exactly is null here
//        	if (creature==null)
//        		System.out.print("creature==null" );
//        	if (newPosition==null)
//        		System.out.print("newPosition==null" );
//        	if (quaternion==null)
//        		System.out.print("quaternion==null" );
//        	if (newPosition.getCell()==null)
//        		System.out.print("newPosition.getCell()==null" );
//
//        	System.out.print("creature.getMovementCounter() " + creature.getMovementCounter());
        }
		
	}
	
	public void doLoiter(AIActor actor) {
		
		NGECore core = NGECore.getInstance();

		CreatureObject creature = actor.getCreature();
		if(creature.getPosture() == 14 || creature.getPosture() == 13) {
			actor.setFollowObject(null);
			return;
		}
		TangibleObject target = actor.getFollowObject();
		float speed = (float) creature.getWalkSpeed();
		float maxDistance = 6;
		if(creature.getWeaponId() != 0) {
			WeaponObject weapon = (WeaponObject) core.objectService.getObject(creature.getWeaponId());
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		} else if(creature.getSlottedObject("default_weapon") != null) {
			WeaponObject weapon = (WeaponObject) creature.getSlottedObject("default_weapon");
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		}
		Point3D currentPosition = creature.getWorldPosition();
		
		if(target != null && !core.simulationService.checkLineOfSight(creature, target))
			maxDistance = 1;
		
		// Manage Loiter points
		maxDistance = 1;
		long waitTime = 10000;
			
		Point3D currentDestination = actor.getLoiterDestination();
		if (actor.getLoiterDestType().equals("LOITER")){
			
			if (actor.getWaitState().equals("NO")){
				if (currentPosition.getDistance2D(currentDestination)<2){	
					actor.setWaitState("WAIT");
					actor.setWaitStartTime(System.currentTimeMillis());
				}
			}
			
			if (actor.getWaitState().equals("WAIT")){
				if (System.currentTimeMillis()-actor.getWaitStartTime()>waitTime){	
					actor.setWaitState("NO");
					currentDestination = actor.getOriginPosition();
					actor.setLoiterDestType("ORIGIN");
					actor.setLoiterDestination(actor.getOriginPosition());
				}
			}
			
//			if (currentPosition.getDistance2D(currentDestination)<2){				
//				// wait
//				core.scriptService.callScript("scripts/", "constructor_build_phase", "buildConstructor", core);
//				currentDestination = actor.getOriginPosition();
//				actor.setLoiterDestType("ORIGIN");
//				actor.setLoiterDestination(actor.getOriginPosition());
//			}
		} 
		if (actor.getLoiterDestType().equals("ORIGIN")){
			currentDestination = actor.getOriginPosition();
			
			if (actor.getWaitState().equals("NO")){
				if (currentPosition.getDistance2D(currentDestination)<2){	
					actor.setWaitState("WAIT");
					actor.setWaitStartTime(System.currentTimeMillis());
				}
			}
			
			if (actor.getWaitState().equals("WAIT")){
				if (System.currentTimeMillis()-actor.getWaitStartTime()>waitTime){	
					actor.setWaitState("NO");
					actor.setLoiterDestType("LOITER");	
					currentDestination = SpawnPoint.getRandomPosition(currentPosition, actor.getMinLoiterDist(), actor.getMaxLoiterDist(), creature.getPlanetId()); 
					actor.setLoiterDestination(currentDestination);
				}
			}
			
//			if (currentPosition.getDistance2D(currentDestination)<2){				
//				// wait
//				core.scriptService.callScript("scripts/", "constructor_build_phase", "buildConstructor", core);
//				actor.setLoiterDestType("LOITER");	
//				currentDestination = SpawnPoint.getRandomPosition(currentPosition, actor.getMinLoiterDist(), actor.getMaxLoiterDist(), creature.getPlanetId()); 
//				actor.setLoiterDestination(currentDestination);
//			}
		}
		
		Point3D newPosition = new Point3D();
		boolean foundNewPos = findNewLoiterPosition(actor, speed, maxDistance, newPosition);

		if(!foundNewPos || (newPosition.x == 0 && newPosition.z == 0))
			return;
		
		Point3D newWorldPos = newPosition.getWorldPosition();
		float direction = (float) Math.atan2(newWorldPos.x - currentPosition.x, newWorldPos.z - currentPosition.z);
		if(direction < 0)
			direction = (float) (2 * Math.PI + direction);
		Quaternion quaternion = new Quaternion((float) Math.cos(direction / 2), 0, (float) Math.sin(direction / 2), 0);
        if (quaternion.y < 0.0f && quaternion.w > 0.0f) {
        	quaternion.y *= -1;
        	quaternion.w *= -1;
        }
        
//        if (newPosition.getCell()==null)
//        	System.out.println("newPosition.getCell() is NULL");
        
        try{
        	core.simulationService.moveObject(creature, newPosition, quaternion, creature.getMovementCounter(), speed, newPosition.getCell());
        } catch (NullPointerException e) {
//        	// Just to identify what exactly is null here
//        	if (creature==null)
//        		System.out.print("creature==null" );
//        	if (newPosition==null)
//        		System.out.print("newPosition==null" );
//        	if (quaternion==null)
//        		System.out.print("quaternion==null" );
//        	if (newPosition.getCell()==null)
//        		System.out.print("newPosition.getCell()==null" );
//
//        	System.out.print("creature.getMovementCounter() " + creature.getMovementCounter());
        }
		
	}
	
	public void doFollow(AIActor actor) {
		//NGECore.getInstance().aiService.logAI("AI STATE doPatrol");
		NGECore core = NGECore.getInstance();

		CreatureObject creature = actor.getCreature();
		if(creature.getPosture() == 14 || creature.getPosture() == 13) {
			actor.setFollowObject(null);
			return;
		}
		TangibleObject target = actor.getFollowObject();
		float speed = (float) creature.getRunSpeed();
		float maxDistance = 6;
		if(creature.getWeaponId() != 0) {
			WeaponObject weapon = (WeaponObject) core.objectService.getObject(creature.getWeaponId());
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		} else if(creature.getSlottedObject("default_weapon") != null) {
			WeaponObject weapon = (WeaponObject) creature.getSlottedObject("default_weapon");
			if(weapon != null)
				maxDistance = weapon.getMaxRange() - 1;
		}
		Point3D currentPosition = creature.getWorldPosition();
		
		if(target != null && !core.simulationService.checkLineOfSight(creature, target))
			maxDistance = 1;
		
		// Manage Follow points
		maxDistance = 3;
		Point3D currentDestination = target.getWorldPosition();
		//System.out.println("currentPosition.getDistance2D(currentDestination)<1) " + currentPosition.getDistance2D(currentDestination));
		
		Point3D newPosition = new Point3D();
		boolean foundNewPos = findNewFollowPosition(actor, speed, maxDistance, newPosition);
		
		if(!foundNewPos || (newPosition.x == 0 && newPosition.z == 0))
			return;
		
		Point3D newWorldPos = newPosition.getWorldPosition();
		float direction = (float) Math.atan2(newWorldPos.x - currentPosition.x, newWorldPos.z - currentPosition.z);
		if(direction < 0)
			direction = (float) (2 * Math.PI + direction);
		Quaternion quaternion = new Quaternion((float) Math.cos(direction / 2), 0, (float) Math.sin(direction / 2), 0);
        if (quaternion.y < 0.0f && quaternion.w > 0.0f) {
        	quaternion.y *= -1;
        	quaternion.w *= -1;
        }
        
//        if (newPosition.getCell()==null)
//        	System.out.println("newPosition.getCell() is NULL");
        try{
        	core.simulationService.moveObject(creature, newPosition, quaternion, creature.getMovementCounter(), speed, newPosition.getCell());
        } catch (NullPointerException e) {
        	// Just to identify what exactly is null here
//        	if (creature==null)
//        		System.out.print("creature==null" );
//        	if (newPosition==null)
//        		System.out.print("newPosition==null" );
//        	if (quaternion==null)
//        		System.out.print("quaternion==null" );
//        	if (newPosition.getCell()==null)
//        		System.out.print("newPosition.getCell()==null" );
//
//        	System.out.print("creature.getMovementCounter() " + creature.getMovementCounter());
        }
		
	}

}
