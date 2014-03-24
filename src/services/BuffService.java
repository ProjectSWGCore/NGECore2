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
	
	public void addBuffToCreature(CreatureObject creature, String buffName) {
		try {
			throw new Exception();
		} catch (Exception e) {
			// New system becoming necessary.  Target can be the buffer for abilities that can't buff others.
			System.out.println("WARNING: New AddBuff Format: target, buffName, buffer");
			e.printStackTrace();
		}
		
		addBuffToCreature(creature, buffName, creature);
	}
	
	public boolean addBuffToCreature(CreatureObject target, String buffName, CreatureObject buffer) {		

		/*if(!FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py")) {
			//System.out.println("Buff script doesnt exist for: " + buffName);
			return;
		}*/

		final Buff buff = new Buff(buffName, buffer.getObjectID());
		if(buff.isGroupBuff()) {
			addGroupBuff(buffer, buffName, buffer);
			return true;
		} else {
			doAddBuff(target, buffName, buffer);
			return true;
		}
	}
		
	public Buff doAddBuff(final CreatureObject target, String buffName, CreatureObject buffer) {
		
		if (target.getPosition().getDistance(buffer.getPosition()) > 20) {
			return null;
		}
		
		if (!core.simulationService.checkLineOfSight(buffer, target)) {
			return null;
		}
		
		final Buff buff = new Buff(buffName, target.getObjectID());
		buff.setTotalPlayTime(((PlayerObject) target.getSlottedObject("ghost")).getTotalPlayTime());
		
        if(FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py"))
        	core.scriptService.callScript("scripts/buffs/", buffName, "setup", core, buffer, buff);
	
            for (final Buff otherBuff : target.getBuffList()) {
                if (buff.getGroup1().equals(otherBuff.getGroup1()))  
                	if (buff.getPriority() >= otherBuff.getPriority()) {
                        if (buff.getBuffName().equals(otherBuff.getBuffName())) {
                        	
                        		if(otherBuff.getStacks() < otherBuff.getMaxStacks()) {
                        			
                        			buff.setStacks(otherBuff.getStacks() + 1);
                        			if(target.getDotByBuff(otherBuff) != null)	// reset duration when theres a dot stack
                        				target.getDotByBuff(otherBuff).setStartTime(buff.getStartTime());
                        			
                        		} else {
                        			buff.setStacks(buff.getMaxStacks());
                        		}
                        		if (buff.getDuration() != -1.0)
                        			if (otherBuff.getRemainingDuration() > buff.getDuration() && otherBuff.getStacks() >= otherBuff.getMaxStacks())
                        				return null;
                        }
                       
                        removeBuffFromCreature(target, otherBuff);
                        break;
                } else {
                	System.out.println("buff not added:" + buffName);
                	return null;
                }
        }	
			
        if(FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py"))
        	core.scriptService.callScript("scripts/buffs/", buffName, "add", core, target, buff);
		
		target.addBuff(buff);
		
		if(buff.getDuration() > 0) {
			
			ScheduledFuture<?> task = scheduler.schedule(new Runnable() {
	
				@Override
				public void run() {
					
					removeBuffFromCreature(target, buff);
					
				
				}
				
			}, (long) buff.getDuration(), TimeUnit.SECONDS);
			
			buff.setRemovalTask(task);
			
		}
		
		for (String effect : buff.getParticleEffect().split(",")) {
			target.playEffectObject(effect, buff.getBuffName());
		}
		
		if (!buff.getCallback().equals("")) {
			if (FileUtilities.doesFileExist("scripts/buffs/" + buff.getBuffName() +  ".py")) {
				scheduler.schedule(new Runnable() {
					
					@Override
					public void run() {
						PyObject method = core.scriptService.getMethod("scripts/buffs/", buff.getBuffName(), buff.getCallback());
						
						if (method != null && method.isCallable()) {
							method.__call__(Py.java2py(core), Py.java2py(target), Py.java2py(buff));
						}
					}
						
				}, 0, TimeUnit.SECONDS);
			}
		}
		
		if (buffer != null && target.getGroupId() != 0 && target.getGroupId() == buffer.getGroupId()) {
			buff.setGroupBufferId(buffer.getObjectID());
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
        	 core.scriptService.callScript("scripts/buffs/", buff.getBuffName(), "remove", core, creature, buff);
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
	
	public void addGroupBuff(CreatureObject buffer, String buffName) {
		try {
			throw new Exception();
		} catch (Exception e) {
			// New system becoming necessary.  Target can be the buffer for abilities that can't buff others.
			System.out.println("WARNING: New GroupAddBuff Format: target, buffName, buffer");
			e.printStackTrace();
		}
		
		addGroupBuff(buffer, buffName, buffer);
	}
	
	public void addGroupBuff(CreatureObject target, String buffName, CreatureObject buffer) {
		
		if(buffer.getGroupId() == 0) {
			doAddBuff(target, buffName, buffer);
			return;
		}
			
		GroupObject group = (GroupObject) core.objectService.getObject(buffer.getGroupId());
		
		if(group == null) {
			doAddBuff(target, buffName, buffer);
			return;
		}
		
		for(SWGObject member : group.getMemberList()) {
			doAddBuff((CreatureObject) member, buffName, buffer);
		}

	}
}
