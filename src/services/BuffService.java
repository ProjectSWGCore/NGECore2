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
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.python.core.Py;
import org.python.core.PyObject;

import resources.buffs.Buff;
import resources.buffs.DamageOverTime;
import resources.common.FileUtilities;
import resources.datatables.Posture;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.player.PlayerObject;
import main.NGECore;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class BuffService implements INetworkDispatch {
	
	private NGECore core;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private ConcurrentHashMap<String, Buff> buffMap = new ConcurrentHashMap<String, Buff>();
	
	private static Map<String,String> bannerBuffMapping = new ConcurrentHashMap<String,String>();
	static{
		bannerBuffMapping.put("trader_0a", "banner_buff_trader");
		bannerBuffMapping.put("trader_0b", "banner_buff_trader");
		bannerBuffMapping.put("trader_0c", "banner_buff_trader");
		bannerBuffMapping.put("trader_0d", "banner_buff_trader");
		bannerBuffMapping.put("entertainer_1a", "banner_buff_entertainer");
		bannerBuffMapping.put("medic_1a", "banner_buff_medic");
		bannerBuffMapping.put("officer_1a", "banner_buff_officer");
		bannerBuffMapping.put("bounty_hunter_1a", "banner_buff_bounty_hunter");
		bannerBuffMapping.put("smuggler_1a", "banner_buff_smuggler");
		bannerBuffMapping.put("commando_1a", "banner_buff_commando");
		bannerBuffMapping.put("spy_1a", "banner_buff_spy");
		bannerBuffMapping.put("force_sensitive_1a", "banner_buff_force_sensitive");
	}
	
	public BuffService(NGECore core) {
		this.core = core;
		core.commandService.registerCommand("removeBuff");
		
		loadBuffs();
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

		Buff buff = buffMap.get(buffName);
		
		if(buff == null)
		{
			return false;
		}
		
		// Here the necessary checks must be placed to prevent buffs from the same buff group (e.g. Buff D) being stacked!
		// ^ These checks are already performed in doAddBuff, unless they were removed.
		
		if(buff.isGroupBuff()) {
			addGroupBuff(buffer, buffName, buffer);
			return true;
		} else {
			if (! hasBuff(target,buffName)) // QA, prevent infinite "refreshing" of buff by repeated use
				doAddBuff(target, buffName, buffer);
			else {
				if (buff.getMaxStacks()>1)
					doAddBuff(target, buffName, buffer);
			}
			return true;
		}
	}
		
	public Buff doAddBuff(final CreatureObject target, String buffName, final CreatureObject buffer) {
		
		//BUG HERE !! watch it
		//TODO fix this  -- !! this is wrong - I can buff from 5,5 in cantina someone sitting at 20,20 in the universe/planet !!! - accross the galaxy/planet
		//cause get position is relative to creature system of coordinates - when one's outside and other inside
		//if you must use getPosition() check for isInCell first for both or something like that
		// ^The above bug should be fixed in command service.  The checks below shouldn't really even be in this service.
		
		if (target.getPosition().getDistance(buffer.getPosition()) > 20) {
			return null;
		}
		
		if (target != buffer && !core.simulationService.checkLineOfSight(buffer, target)) {
			return null;
		}
		
		final Buff buff = new Buff(buffMap.get(buffName), buffer.getObjectID(), target.getObjectID());
		if(target.getSlottedObject("ghost") != null)
			buff.setTotalPlayTime(((PlayerObject) target.getSlottedObject("ghost")).getTotalPlayTime());
		else
			buff.setTotalPlayTime(0);
			
     
		if(FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py"))
        {
	        if(!buffName.equals("buildabuff_inspiration"))
	        {
	        	core.scriptService.callScript("scripts/buffs/", buffName, "setup", core, buffer, buff);
	        }
	        else
	        {
	        	//!!! for special case inspiration , workshop is attached to target
	        	core.scriptService.callScript("scripts/buffs/", buffName, "add", core, target, buff);
	        }
        }


	
            for (final Buff otherBuff : target.getBuffList().values()) {
            	 if (buff.getGroup1().equals(otherBuff.getGroup1())) 
                	if (buff.getPriority() >= otherBuff.getPriority()) {
                        if (buff.getBuffName().equals(otherBuff.getBuffName()))
                        {
                        	
                        		if(otherBuff.getStacks() < otherBuff.getMaxStacks()) 
                        		{
                        			
                        			buff.setStacks(otherBuff.getStacks() + 1);
                        			if(target.getDotByBuff(otherBuff) != null)	// reset duration when theres a dot stack
                        				target.getDotByBuff(otherBuff).setStartTime(buff.getStartTime());
                        			
                        		} 
                        		else 
                        		{
                        			buff.setStacks(buff.getMaxStacks());
                        		}
                        		if (buff.getDuration() != -1.0)
                        			if (otherBuff.getRemainingDuration() > buff.getDuration() && otherBuff.getStacks() >= otherBuff.getMaxStacks())
                        				return null;
                        }
                        removeBuffFromCreature(target, otherBuff);
                        break;
                } else 
                {

                	return null;
                }
        }	
			
/*        if(FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py")) core.scriptService.callScript("scripts/buffs/", buffName, "add", core, target, buff);
        else
        { */
        	if(buff.getEffect1Name().length() > 0) core.skillModService.addSkillMod(target, buff.getEffect1Name(), (int) buff.getEffect1Value());
        	if(buff.getEffect2Name().length() > 0) core.skillModService.addSkillMod(target, buff.getEffect2Name(), (int) buff.getEffect2Value());
        	if(buff.getEffect3Name().length() > 0) core.skillModService.addSkillMod(target, buff.getEffect3Name(), (int) buff.getEffect3Value());
        	if(buff.getEffect4Name().length() > 0) core.skillModService.addSkillMod(target, buff.getEffect4Name(), (int) buff.getEffect4Value());
        	if(buff.getEffect5Name().length() > 0) core.skillModService.addSkillMod(target, buff.getEffect5Name(), (int) buff.getEffect5Value());
//		}
        	
    	if (buffName.equals("gcw_fatigue")){
    		if (FileUtilities.doesFileExist("scripts/buffs/" + buffName + ".py")) {
    			core.scriptService.callScript("scripts/buffs/", buffName, "add", core, buffer, buff);
    		}
    	}
		
		target.addBuff(buff);
		
		if(buff.getDuration() > 0) {
			
			ScheduledFuture<?> task = scheduler.schedule(new Runnable() {
	
				@Override
				public void run() {
					
					try {
						if (target!=null && buff != null)
							removeBuffFromCreature(target, buff);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}, (long) buff.getDuration(), TimeUnit.SECONDS);
			
			buff.setRemovalTask(task);
			
		} else if (buff.getGroup2().contains("of_aura") && buffer != null && buffer.getObjectId() != target.getObjectId()) {

			// I'm not sure if all aura effects follow the same rules, so this is simply restricted to officer aura's atm
			ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try {
						if (buffer == null  || buffer.getClient() == null)
							if (target!=null && buff != null)
								removeBuffFromCreature(target, buff);
	
						if (target.getWorldPosition().getDistance2D(buffer.getWorldPosition()) > 80) {
							if (target!=null && buff != null)
								removeBuffFromCreature(target, buff);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, 5, 5, TimeUnit.SECONDS);
			
			buff.setRemovalTask(task);
		}
		
		for (String effect : buff.getParticleEffect().split(",")) {
			target.playEffectObject(effect, buff.getBuffName());
		}
		
		if (buffer != null && target.getGroupId() != 0 && target.getGroupId() == buffer.getGroupId()) {
			buff.setGroupBufferId(buffer.getObjectID());
		}
		
		return buff;
		
	} 
	
	@SuppressWarnings("unused")
	public void removeBuffFromCreature(CreatureObject creature, Buff buff)
	{
		 if(!creature.getBuffList().containsValue(buff))
		 {
			 return;
		 }
		 DamageOverTime dot = creature.getDotByBuff(buff);
         if(dot != null) {
        	 dot.getTask().cancel(true);
        	 creature.removeDot(dot);
         }
         
/*         if(FileUtilities.doesFileExist("scripts/buffs/" + buff.getBuffName() + ".py")) core.scriptService.callScript("scripts/buffs/", buff.getBuffName(), "remove", core, creature, buff);
         else
         {*/
         	
         	if(buff.getEffect1Name().length() > 0)
         	{
         		
         			core.skillModService.deductSkillMod(creature, buff.getEffect1Name(), (int) buff.getEffect1Value());

         	}

         	if(buff.getEffect2Name().length() > 0)
         	{
         			core.skillModService.deductSkillMod(creature, buff.getEffect2Name(), (int) buff.getEffect2Value());
         	}
  
         	if(buff.getEffect3Name().length() > 0)
         	{
     			core.skillModService.deductSkillMod(creature, buff.getEffect3Name(), (int) buff.getEffect3Value());

         	}
        	
         	if(buff.getEffect4Name().length() > 0)
         	{
         		core.skillModService.deductSkillMod(creature, buff.getEffect4Name(), (int) buff.getEffect4Value());

         	}	

         	if(buff.getEffect5Name().length() > 0) 
         	{
         		core.skillModService.deductSkillMod(creature, buff.getEffect5Name(), (int) buff.getEffect5Value());
        	}
//         }
       
         // ??? callback for what ? toggle buff ?	
         if (!buff.getCallback().equals("none") && !buff.getCallback().equals(""))
         {
        	 if (FileUtilities.doesFileExist("scripts/buffs/" + buff.getBuffName() +  ".py")) {
        		 PyObject method = core.scriptService.getMethod("scripts/buffs/", buff.getBuffName(), buff.getCallback());
			
        		 if (method != null && method.isCallable()) {
        			 method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(buff));
        		 }
        	 }
         }
         //remove method is more like it
         else
         {
        	 if (FileUtilities.doesFileExist("scripts/buffs/" + buff.getBuffName() +  ".py"))
        	 {
        		 PyObject method = core.scriptService.getMethod("scripts/buffs/", buff.getBuffName(), "remove");
			
        		 if (method != null && method.isCallable())
        		 {
        			 method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(buff));
        		 }
        	 }
         }
         creature.removeBuff(buff);
         
        for (String effect : buff.getParticleEffect().split(",")) {
        	creature.stopEffectObject(buff.getBuffName());
		}
        
        if (buff.getRemovalTask() != null) {
       	 buff.getRemovalTask().cancel(true);
       	 buff.setRemovalTask(null);
        }
	}
	
	public void removeBuffFromCreatureByName(CreatureObject creature, String buffName)
	{
		removeBuffFromCreature(creature, creature.getBuffByName(buffName));
	}
	/*
	public void clearBuffs(final CreatureObject creature) 
	{		
		// This method sucks ass, someone please fix me. indeed, it doesnt even work at all!
		for(Buff buff : creature.getBuffList().get())
		{
			if(buff.getRemainingDuration() > 0 && buff.getDuration() > 0) buff.setRemovalTask(null);
			removeBuffFromCreature(creature, buff);			
		}				
		
	}
	*/
	
	public void clearBuffs(final CreatureObject creature) {

		// copy to array for thread safety

		for(final Buff buff : creature.getBuffList().values().toArray(new Buff[] { })) {
			
			if (buff.getRemovalTask() != null) { continue; }

			if(buff.isGroupBuff() && buff.getGroupBufferId() != creature.getObjectID()) {
				removeBuffFromCreature(creature, buff);
				continue;
			}

			if (buff.getDuration() == (float) -1)
				continue;
			
			if(buff.getRemainingDuration() > 0 && buff.getDuration() > 0) {
				ScheduledFuture<?> task = scheduler.schedule(() -> {
					try {
						if (creature!=null && buff != null)
							removeBuffFromCreature(creature, buff);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}, (long) buff.getRemainingDuration(), TimeUnit.SECONDS);
				buff.setRemovalTask(task);
				continue;
			} else {
				removeBuffFromCreature(creature, buff);
			}

		}

	}
	
	public boolean hasBuff(final CreatureObject creature, String buffName) {

		for(final Buff buff : creature.getBuffList().values().toArray(new Buff[] { })) {
			if (buff.getBuffName().contains(buffName)) { return true; }
		}
		return false;
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
		
		if(buffer.getGroupId() == 0) 
		{
			doAddBuff(target, buffName, buffer);
			return;
		}
			
		GroupObject group = (GroupObject) core.objectService.getObject(buffer.getGroupId());
		
		if(group == null)
		{
			doAddBuff(target, buffName, buffer);
			return;
		}
		
		for(SWGObject member : group.getMemberList()) {
			doAddBuff((CreatureObject) member, buffName, buffer);
		}

	}
	
	public void applyGCWBuff(String buffName, String factionName, SWGObject center) {
		Vector<CreatureObject> inRangeObjects = core.simulationService.getAllNearSameFactionCreatures(10, center.getWorldPosition(), center.getPlanet(), factionName);		
		for(SWGObject obj : inRangeObjects) {		
			if(!(obj instanceof CreatureObject))
				continue;			
			if(obj instanceof CreatureObject && (((CreatureObject) obj).getPosture() == Posture.Dead))
				continue;			
			CreatureObject buffed = (CreatureObject) obj;
			if (! buffed.hasBuff(buffName))
				addBuffToCreature(buffed, buffName, buffed);			
		}
	}
	
	public void initiateBannerBuffProcess(CreatureObject buffer, SWGObject banner){
		
		String factionName = buffer.getFaction();
		String professionName = buffer.getPlayerObject().getProfession();
		String buffName = bannerBuffMapping.get(professionName); 		
		applyGCWBuff(buffName, factionName, banner);
		
		final Future<?>[] fut = {null};
		fut[0] = scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { 
				try {
					if (NGECore.getInstance().objectService.getObject(banner.getObjectID())==null){
		                Thread.yield();
		                fut[0].cancel(false);
					}
								
					applyGCWBuff(buffName, factionName, banner);
					
					if (! buffer.isInQuadtree()){
						NGECore.getInstance().objectService.destroyObject(banner.getObjectID());
						Thread.yield();
		                fut[0].cancel(false);
					}
					
					if (buffer.getWorldPosition().getDistance2D(banner.getWorldPosition())>1000 || buffer.getPlanetId()!=banner.getPlanetId()){
						NGECore.getInstance().objectService.destroyObject(banner.getObjectID());
						Thread.yield();
		                fut[0].cancel(false);
					}
										
				} catch (Exception e) {
					System.err.println("Exception in GCW Banner thread " + e.getMessage());
			}
			}
		}, 0, 2, TimeUnit.SECONDS);
	}
	
	private void loadBuffs() {
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/buff/buff.iff", DatatableVisitor.class);
			for(int i = 0; i < visitor.getRowCount(); i++) {
				Buff buff = new Buff();
				
				String name = (String) visitor.getObject(i, 0);
				
				buff.setBuffName(name);
				buff.setGroup1((String) visitor.getObject(i, 1));
				buff.setGroup2((String) visitor.getObject(i, 2));
				buff.setPriority((int) visitor.getObject(i, 4));
				buff.setDuration((Float) visitor.getObject(i, 6));
				buff.setEffect1Name((String) visitor.getObject(i, 7));
				buff.setEffect1Value((Float) visitor.getObject(i, 8));
				buff.setEffect2Name((String) visitor.getObject(i, 9));
				buff.setEffect2Value((Float) visitor.getObject(i, 10));
				buff.setEffect3Name((String) visitor.getObject(i, 11));
				buff.setEffect3Value((Float) visitor.getObject(i, 12));
				buff.setEffect4Name((String) visitor.getObject(i, 13));
				buff.setEffect4Value((Float) visitor.getObject(i, 14));
				buff.setEffect5Name((String) visitor.getObject(i, 15));
				buff.setEffect5Value((Float) visitor.getObject(i, 16));
				buff.setCallback((String) visitor.getObject(i, 18));
				buff.setParticleEffect((String) visitor.getObject(i, 19));
				buff.setDebuff((Boolean) visitor.getObject(i, 22));
				buff.setRemoveOnDeath((Integer) visitor.getObject(i, 25) != 0);
				buff.setRemovableByPlayer((Integer) visitor.getObject(i, 26) != 0);
				buff.setMaxStacks((Integer) visitor.getObject(i, 28));
				buff.setPersistent((Integer) visitor.getObject(i, 29) != 0);
				buff.setRemoveOnRespec((Integer) visitor.getObject(i, 31) != 0);
				buff.setAiRemoveOnEndCombat((Integer) visitor.getObject(i, 32) != 0);
				buff.setDecayOnPvPDeath((Integer) visitor.getObject(i, 33) != 0);
				
				buffMap.put(name, buff);
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
