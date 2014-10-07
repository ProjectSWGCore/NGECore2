package services.ai.states;

import resources.datatables.Options;
import resources.objects.creature.CreatureObject;
import services.ai.AIActor;
import tools.DevLog;

public class WithdrawalState extends AIState {
private boolean locked = false;
	
	
	@Override
	public byte onEnter(AIActor actor) {
		
		DevLog.debugoutai(actor, "Charon", "AI WithdrawalState", "WITHDRAWAL ENTERED!");		
		CreatureObject creature = actor.getCreature();
		if (creature==null)
			return 0;
		creature.setOptions(Options.INVULNERABLE, true);	
		if(creature.getPosture() == 14)
			return StateResult.DEAD;
		actor.setPatrolPointIndex(actor.getPatrolPointIndex()-1);
		actor.scheduleMovement();
		actor.scheduleRecovery();
		return StateResult.UNFINISHED;
	}

	@Override
	public byte onExit(AIActor actor) {
		DevLog.debugoutai(actor, "Charon", "AI WithdrawalState", "EXITING WITHDRAWAL STATE!");		
		
		return StateResult.FINISHED;
	}

	@Override
	public byte move(AIActor actor) {
		DevLog.debugoutai(actor, "Charon", "AI WithdrawalState", "WITHDRAWAL MOVE!");
		if (locked){
			System.out.println("WITHDRAWAL locked!");
			return StateResult.FINISHED;
		}
		if (actor==null)
			return StateResult.FINISHED;
		CreatureObject creature = actor.getCreature();
		if (creature==null)
			return StateResult.FINISHED;
		
//		if(creature.isInCombat()){
//			//System.out.println("PatrolState locking!");
//			locked = true;
//			return StateResult.FINISHED;
//		}
		doWithdrawal(actor);
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
