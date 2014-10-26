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

import engine.resources.container.CreatureContainerPermissions;
import main.NGECore;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import services.ai.AIActor;

public class DeathState extends AIState {

	@Override
	public byte onEnter(AIActor actor) {
		if (!actor.isActorAlive())
			return 0;
		actor.setActorAlive(false);
		NGECore.getInstance().buffService.clearBuffs(actor.getCreature());
		NGECore.getInstance().aiService.awardExperience(actor);
		NGECore.getInstance().aiService.awardGcw(actor);
		actor.setActorAlive(false);
		actor.getCreature().setAttachment("radial_filename", "npc/corpse");
		//NGECore.getInstance().scriptService.callScript("scripts/radial/npc/corpse", "", "createRadial", NGECore.getInstance(), actor.getCreature().getKiller(), actor.getCreature(), new Vector<RadialOptions>());		
		actor.scheduleDespawn();
	
		if (actor.getCreature().getKiller() instanceof CreatureObject){
			CreatureObject killer = (CreatureObject)actor.getCreature().getKiller();
			if (killer==null)
				killer = actor.getHighestDamageDealer();
			actor.getCreature().setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
			if (killer.isPlayer()) // No point to do this for NPCs
				NGECore.getInstance().lootService.DropLoot(killer,(TangibleObject)(actor.getCreature()));
		}	
		//actor.destroyActor(); // to prevent standing up right after death
		
		return 0;
	}

	@Override
	public byte onExit(AIActor actor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte move(AIActor actor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte recover(AIActor actor) {
		// TODO Auto-generated method stub
		return 0;
	}

}
