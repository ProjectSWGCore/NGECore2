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
package services;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import resources.common.FileUtilities;
import resources.objects.Buff;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;

import main.NGECore;

import engine.resources.common.CRC;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class BuffService implements INetworkDispatch {
	
	private NGECore core;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public BuffService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		
	}
	
	public void addBuffToCreature(final CreatureObject creature, String buffName) {		

		if(!FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py")) {
			System.out.println("Buff script doesnt exist for: " + buffName);
			return;
		}
		
		final Buff buff = new Buff(buffName, creature.getObjectID());
		buff.setTotalPlayTime(((PlayerObject) creature.getSlottedObject("ghost")).getTotalPlayTime());
		
	
            for (Buff otherBuff : creature.getBuffList()) {
                if (buff.getGroup1().equals(otherBuff.getGroup1()))  
                	if (buff.getPriority() >= otherBuff.getPriority()) {
                        if (buff.getBuffName().equals(otherBuff.getBuffName())) {
                                if (otherBuff.getRemainingDuration() > buff.getDuration()) {
                                        return;
                                }
                        }
                       
                        removeBuffFromCreature(creature, otherBuff); System.out.println("buff removed " + buffName);
                        break;
                }else{
                	System.out.println("buff not added:" + buffName);
                	return;
                }
        }	
			
		
		core.scriptService.callScript("scripts/buffs", "setup", buffName, core, creature, buff);
		
		creature.addBuff(buff);
		
		if(buff.getDuration() > 0) {
			scheduler.schedule(new Runnable() {
	
				@Override
				public void run() {
					
					removeBuffFromCreature(creature, buff);
					
				
				}
				
			}, (long) buff.getDuration(), TimeUnit.SECONDS);
		}
		
	} 
	
	public void removeBuffFromCreature(CreatureObject creature, Buff buff) {
		
		 if(!creature.getBuffList().contains(buff))
             return;
            
         core.scriptService.callScript("scripts/buffs", "removeBuff", buff.getBuffName(), core, creature, buff);
         creature.removeBuff(buff);
		
	}
	
	public void clearBuffs(final CreatureObject creature) {
		
		// copy to array for thread safety
					
		for(final Buff buff : creature.getBuffList().get().toArray(new Buff[] { })) {
				
			if(buff.getRemainingDuration() > 0) {
				scheduler.schedule(new Runnable() {

					@Override
					public void run() {
						
						removeBuffFromCreature(creature, buff);
						
					}
					
				}, (long) buff.getRemainingDuration(), TimeUnit.SECONDS);
				continue;
			} else {
				removeBuffFromCreature(creature, buff);
			}
				
		}
					
	}
	
	

}
