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
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.python.core.Py;
import org.python.core.PyObject;

import resources.common.FileUtilities;
import resources.objects.Buff;
import resources.objects.DamageOverTime;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.player.PlayerObject;
import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class BuffService implements INetworkDispatch {
	
	private NGECore core;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public BuffService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	}

	@Override
	public void shutdown() {
		
	}
	
	public void addBuffToCreature(final CreatureObject creature, String buffName) {		

		/*if(!FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py")) {
			//System.out.println("Buff script doesnt exist for: " + buffName);
			return;
		}*/

		final Buff buff = new Buff(buffName, creature.getObjectID());
		if(buff.isGroupBuff()) {
			addGroupBuff(creature, buffName);
		} else {
			doAddBuff(creature, buffName);
		}
	}
		
	public Buff doAddBuff(final CreatureObject creature, String buffName) {
		
		final Buff buff = new Buff(buffName, creature.getObjectID());
		buff.setTotalPlayTime(((PlayerObject) creature.getSlottedObject("ghost")).getTotalPlayTime());
		
	
            for (final Buff otherBuff : creature.getBuffList()) {
                if (buff.getGroup1().equals(otherBuff.getGroup1()))  
                	if (buff.getPriority() >= otherBuff.getPriority()) {
                        if (buff.getBuffName().equals(otherBuff.getBuffName())) {
                        	
                        		if(otherBuff.getStacks() < otherBuff.getMaxStacks()) {
                        			
                        			buff.setStacks(otherBuff.getStacks() + 1);
                        			if(creature.getDotByBuff(otherBuff) != null)	// reset duration when theres a dot stack
                        				creature.getDotByBuff(otherBuff).setStartTime(buff.getStartTime());
                        			
                        		} else {
                        			buff.setStacks(buff.getMaxStacks());
                        		}
                        	
                                if (otherBuff.getRemainingDuration() > buff.getDuration() && otherBuff.getStacks() >= otherBuff.getMaxStacks()) {
                                        return null;
                                }
                        }
                       
                        removeBuffFromCreature(creature, otherBuff);
                        break;
                } else {
                	System.out.println("buff not added:" + buffName);
                	return null;
                }
        }	
			
        if(FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py"))
        	core.scriptService.callScript("scripts/buffs/", buffName, "setup", core, creature, buff);
		
		creature.addBuff(buff);
		
		if(buff.getDuration() > 0) {
			
			ScheduledFuture<?> task = scheduler.schedule(new Runnable() {
	
				@Override
				public void run() {
					
					removeBuffFromCreature(creature, buff);
					
				
				}
				
			}, (long) buff.getDuration(), TimeUnit.SECONDS);
			
			buff.setRemovalTask(task);
			
		}
		
		for (String effect : buff.getParticleEffect().split(",")) {
			creature.playEffectObject(effect, buff.getBuffName());
		}
		
		if (!buff.getCallback().equals("")) {
			if (FileUtilities.doesFileExist("scripts/buffs/" + buff.getBuffName() +  ".py")) {
				scheduler.schedule(new Runnable() {
					
					@Override
					public void run() {
						PyObject method = core.scriptService.getMethod("scripts/buffs/", buff.getBuffName(), buff.getCallback());
						
						if (method != null && method.isCallable()) {
							method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(buff));
						}
					}
						
				}, 0, TimeUnit.SECONDS);
			}
		}
		
		return buff;
		
	} 
	
	@SuppressWarnings("unused")
	public void removeBuffFromCreature(CreatureObject creature, Buff buff) {
		
		 if(!creature.getBuffList().contains(buff))
             return;
		 DamageOverTime dot = creature.getDotByBuff(buff);
         if(dot != null) {
        	 dot.getTask().cancel(true);
        	 creature.removeDot(dot);
         }
         if(FileUtilities.doesFileExist("scripts/buffs/" + buff.getBuffName() + ".py"))
        	 core.scriptService.callScript("scripts/buffs/", buff.getBuffName(), "removeBuff", core, creature, buff);
         creature.removeBuff(buff);
         
        for (String effect : buff.getParticleEffect().split(",")) {
        	creature.stopEffectObject(buff.getBuffName());
		}
         
		
	}
	
	public void clearBuffs(final CreatureObject creature) {
		
		// copy to array for thread safety
					
		for(final Buff buff : creature.getBuffList().get().toArray(new Buff[] { })) {
			
			if (buff.getGroup1().startsWith("setBonus")) { continue; }
			
			if(buff.isGroupBuff() && buff.getGroupBufferId() != creature.getObjectID()) {
				removeBuffFromCreature(creature, buff);
				continue;
			}

			if(buff.getRemainingDuration() > 0 && buff.getDuration() > 0) {
				ScheduledFuture<?> task = scheduler.schedule(new Runnable() {

					@Override
					public void run() {
						
						removeBuffFromCreature(creature, buff);
						
					}
					
				}, (long) buff.getRemainingDuration(), TimeUnit.SECONDS);
				buff.setRemovalTask(task);
				continue;
			} else {
				removeBuffFromCreature(creature, buff);
			}
				
		}
					
	}
	
	public void addGroupBuff(final CreatureObject buffer, String buffName) {
		
		if(buffer.getGroupId() == 0) {
			doAddBuff(buffer, buffName);
			return;
		}
			
		GroupObject group = (GroupObject) core.objectService.getObject(buffer.getGroupId());
		
		if(group == null) {
			doAddBuff(buffer, buffName);
			return;
		}
		
		for(SWGObject member : group.getMemberList()) {
			Buff buff = doAddBuff((CreatureObject) member, buffName);
			buff.setGroupBufferId(buffer.getObjectID());
		}

	}
}
