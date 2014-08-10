package services.ai.states;

import main.NGECore;
import engine.resources.scene.Point3D;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import services.ai.AIActor;
import tools.DevLog;

/** 
 * @author Charon 
 */

public class RepositionState extends AIState{
	
	int prog = 10;
	
	@Override
	public byte onEnter(AIActor actor) {
		DevLog.debugoutai(actor, "Charon", "AI RepositionState", "onEnter");
		CreatureObject creature = actor.getCreature();
		Point3D creaturePosition = creature.getWorldPosition();

		TangibleObject target = actor.getLastTarget();			
//		Point3D LOSorigin = targetPosition;
//		float deltaX = LOSorigin.x-creaturePosition.x;
//		float deltaZ = LOSorigin.z-creaturePosition.z;
//		float originAngle = (float) (Math.atan2(deltaX,deltaZ));
		Point3D scanPosition = creaturePosition;
		TangibleObject tempObject = new TangibleObject(); // LOS overload for pos and creature does not work correctly
		tempObject.setPosition(scanPosition);
		boolean breakout = false;
		boolean foundPos = false;
		actor.setProgressionMarker(1);
		long repositionCalculationStart = System.currentTimeMillis(); // Limit time this is calculated to prevent deadloops
		while (!NGECore.getInstance().simulationService.checkLineOfSight(target,scanPosition) && !breakout || System.currentTimeMillis()>repositionCalculationStart+2000){
			// Quick, light-weight way to get the AI into a firing LOS position
			// ToDo: Check if the Quadnode at the LOS position contains a structure
			// Required: Add according method to Quadtree.java
			
			boolean abortCondition = false;
			for (int x=1;x<50 && !abortCondition;x=x+1){
				if (NGECore.getInstance().simulationService.checkLineOfSight(target,new Point3D(creaturePosition.x+x,creaturePosition.y,creaturePosition.z)) && !NGECore.getInstance().terrainService.isWater(creature.getPlanetId(), creaturePosition.x+x, creaturePosition.z)){
					scanPosition = new Point3D(creaturePosition.x+x,creaturePosition.y,creaturePosition.z);
					abortCondition = true;
				}
			}
			for (int x=-1;x>-50 && !abortCondition;x=x-1){
				if (NGECore.getInstance().simulationService.checkLineOfSight(target,new Point3D(creaturePosition.x+x,creaturePosition.y,creaturePosition.z)) && !NGECore.getInstance().terrainService.isWater(creature.getPlanetId(), creaturePosition.x+x, creaturePosition.z)){
					scanPosition = new Point3D(creaturePosition.x+x,creaturePosition.y,creaturePosition.z);
					abortCondition = true;
				}
			}
			for (int z=1;z<50 && !abortCondition;z=z+1){
				if (NGECore.getInstance().simulationService.checkLineOfSight(target,new Point3D(creaturePosition.x,creaturePosition.y,creaturePosition.z+z)) && !NGECore.getInstance().terrainService.isWater(creature.getPlanetId(), creaturePosition.x, creaturePosition.z+z)){
					scanPosition = new Point3D(creaturePosition.x,creaturePosition.y,creaturePosition.z+z);
					abortCondition = true;
				}
			}
			for (int z=-1;z>-50 && !abortCondition;z=z-1){
				if (NGECore.getInstance().simulationService.checkLineOfSight(target,new Point3D(creaturePosition.x,creaturePosition.y,creaturePosition.z+z)) && !NGECore.getInstance().terrainService.isWater(creature.getPlanetId(), creaturePosition.x, creaturePosition.z+z)){
					scanPosition = new Point3D(creaturePosition.x,creaturePosition.y,creaturePosition.z+z);
					abortCondition = true;
				}
			}
			actor.setProgressionMarker(2);
			
			if (!NGECore.getInstance().simulationService.checkLineOfSight(target,scanPosition)){
				foundPos=true;
			}
			
//			else
//				System.out.println("LOS found");
//			
			breakout=true;		
		}
		actor.setProgressionMarker(3);
		if (foundPos){
			actor.setRepositionLocation(scanPosition);
			actor.setRepositionStartTime(System.currentTimeMillis());
		} else {
			float positionY = NGECore.getInstance().terrainService.getHeight(target.getPlanetId(), target.getPosition().x, target.getPosition().z);
			actor.setRepositionLocation(new Point3D(target.getWorldPosition().x,positionY,target.getWorldPosition().z));
			actor.setRepositionStartTime(System.currentTimeMillis()+5000); // give it more time
		}
		
		actor.scheduleMovement();
		return StateResult.UNFINISHED;
	}

	@Override
	public byte onExit(AIActor actor) {
		// TODO Auto-generated method stub	
		actor.setProgressionMarker(9);
		TangibleObject followObject = actor.getFollowObject();
		if (followObject==null)
			return StateResult.FINISHED;
			
		if (followObject instanceof CreatureObject)
			if (((CreatureObject) followObject).getPosture()==14 || ((CreatureObject) followObject).getPosture()==13)
				actor.setFollowObject(null);
		
		if (! (followObject instanceof CreatureObject))
			if(followObject.getConditionDamage()>=followObject.getMaximumCondition())
				actor.setFollowObject(null);
		
		DevLog.debugoutai(actor, "Charon", "AI RepositionState", "exit");	
		
		return StateResult.FINISHED;
	}

	@Override
	public byte move(AIActor actor) {
		DevLog.debugoutai(actor, "Charon", "AI RepositionState", "move");	
		actor.setProgressionMarker(prog++);
		Point3D newPosition = actor.getRepositionLocation();
		TangibleObject target = actor.getLastTarget();	
		if (target==null){
			if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
				actor.setCurrentState(new RetreatState()); 	
			else {
				actor.setCurrentState(new RetreatState()); 
			}
			return StateResult.FINISHED;
		}
		
		if (target instanceof CreatureObject)
			if (((CreatureObject) target).getPosture()==14 || ((CreatureObject) target).getPosture()==13){
				if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
					actor.setCurrentState(new RetreatState()); 	
				else {
					actor.setCurrentState(new RetreatState()); 
				}
				return StateResult.FINISHED;
			}
		
		if (! (target instanceof CreatureObject))
			if(target.getConditionDamage()>=target.getMaximumCondition()){
				if (actor.getIntendedPrimaryAIState().getClass().equals(PatrolState.class))
					actor.setCurrentState(new RetreatState()); 	
				else {
					actor.setCurrentState(new RetreatState()); 
				}
				return StateResult.FINISHED;
			}
		
		if (newPosition.getDistance2D(target.getWorldPosition())<0.5 || System.currentTimeMillis()>actor.getRepositionStartTime()+5000){
			actor.addDefender(target);	
			actor.setCurrentState(new AttackState());			
			return StateResult.FINISHED;
		}
		
		doReposition(actor);
		actor.scheduleMovement();	
		return StateResult.UNFINISHED;
	}

	@Override
	public byte recover(AIActor actor) {
		// TODO Auto-generated method stub
		return 0;
	}
}
