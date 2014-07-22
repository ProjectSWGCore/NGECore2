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
		while (!NGECore.getInstance().simulationService.checkLineOfSight2(target,scanPosition) && !breakout){
			// Quick, light-weight way to get the AI into a firing LOS position
			// ToDo: Check if the Quadnode at the LOS position contains a structure
			// Required: Add according method to Quadtree.java
			for (int x=2;x<50 && (!NGECore.getInstance().simulationService.checkLineOfSight2(target,scanPosition));x=x+2){
				scanPosition = new Point3D(creaturePosition.x+x,creaturePosition.y,creaturePosition.z);
			}
			for (int x=2;x>-50 && (!NGECore.getInstance().simulationService.checkLineOfSight2(target,scanPosition));x=x-2){
				scanPosition = new Point3D(creaturePosition.x+x,creaturePosition.y,creaturePosition.z);
			}
			for (int z=2;z<50 && (!NGECore.getInstance().simulationService.checkLineOfSight2(target,scanPosition));z=z+2){
				scanPosition = new Point3D(creaturePosition.x,creaturePosition.y,creaturePosition.z+z);
			}
			for (int z=2;z>-50 && (!NGECore.getInstance().simulationService.checkLineOfSight2(target,scanPosition));z=z-2){
				scanPosition = new Point3D(creaturePosition.x,creaturePosition.y,creaturePosition.z+z);
			}
//			if (!NGECore.getInstance().simulationService.checkLineOfSight2(target,scanPosition))
//				breakout=true;
//			else
//				System.out.println("LOS found");
//			
			breakout=true;		
		}
		actor.setRepositionLocation(scanPosition);
		actor.setRepositionStartTime(System.currentTimeMillis());
		
		return 0;
	}

	@Override
	public byte onExit(AIActor actor) {
		// TODO Auto-generated method stub
		DevLog.debugoutai(actor, "Charon", "AI RepositionState", "exit");	
		return 0;
	}

	@Override
	public byte move(AIActor actor) {
		DevLog.debugoutai(actor, "Charon", "AI RepositionState", "move");	
		
		Point3D newPosition = actor.getRepositionLocation();
		TangibleObject target = actor.getLastTarget();	
		if (target==null)
			return StateResult.FINISHED;
		
		if (newPosition.getDistance2D(target.getWorldPosition())<0.5 || System.currentTimeMillis()>actor.getRepositionStartTime()+5000){
			actor.addDefender(target);	
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
