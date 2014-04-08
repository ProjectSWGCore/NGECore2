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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.loot.LootDrop;
import resources.objects.loot.LootGroup;
import resources.objects.loot.LootPool;
import resources.objects.loot.LootRollSession;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.tool.SurveyTool;
import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

/** 
 * @author Charon 
 */

public class LootService implements INetworkDispatch {
	
	private NGECore core;

	public LootService(NGECore core) {
		this.core = core;
		//core.commandService.registerCommand("");
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	}

	@Override
	public void shutdown() {
		
	}
	
	public void handleLootRequest(CreatureObject requester, TangibleObject lootedObject) {
			
		LootRollSession lootRollSession = new LootRollSession(requester);
		String lootedObjectType = "Tangible";
		if (lootedObject instanceof CreatureObject)
			lootedObjectType = "Creature";

		// Credit drop is depending on the CL of the looted CreatureObject
		// or if explicitely assigned in the .py script
		int lootedCredits = 0;
		if (lootedObjectType.equals("Creature")){
			CreatureObject lootedCreature = (CreatureObject) lootedObject;
			int creatureCL = lootedCreature.getLevel();
			creatureCL = 90;
			int minimalCredits = 40 * creatureCL; // Predetermined factor of 40 per CL
			int spanOfCredits  = 15 * creatureCL; // Predetermined factor of 15 per CL
			System.out.println("spanOfCredits " + spanOfCredits);
			lootedCredits = minimalCredits + new Random().nextInt(spanOfCredits);
			requester.sendSystemMessage("You looted " + lootedCredits + " credits.", (byte)1); 
		}
		
		if (lootedObjectType.equals("Tangible")){
			// This is for chests etc.
			// Check the py script
		}
		
		
		CreatureObject lootedCreature = (CreatureObject) lootedObject;
		
		//TreeSet<TreeMap<String,Integer>> lootSpec = lootedObject.getLootSpecification();
		 List<LootGroup> lootGroups = lootedCreature.getLootGroups();
		 System.out.println("lootGroups size " + lootGroups.size());
		 Iterator<LootGroup> iterator = lootGroups.iterator();
	     	    
	    while (iterator.hasNext()){
	    	LootGroup lootGroup = iterator.next();
	    	int groupChance = lootGroup.getLootGroupChance();
	    	int lootGroupRoll = new Random().nextInt(100);
	    	if (lootGroupRoll <= groupChance){    		
	    		System.out.println("this lootGroup will drop something");
	    		handleLootGroup(lootGroup,lootRollSession); //this lootGroup will drop something	    		
	    	}		
	    }
	    
	    for (String s : lootRollSession.getDroppedItemTemplates()){
			System.out.println("lootRollSession template: " + s);
		}
	    
	 
	    // ********** Phase 1 complete, loot items determined **********
	    // stored in the lootSession
	    
	    // Distribute the loot drops according to group loot rules	    
	    // For now just spawn items into requester's inventory
    	SWGObject requesterInventory = requester.getSlottedObject("inventory");
    	System.out.println("requesterInventory " + requesterInventory);
    	for (String template : lootRollSession.getDroppedItemTemplates()){		    
	    	TangibleObject droppedItem = (TangibleObject) core.objectService.createObject(template, requester.getPlanet());				
	    	System.out.println("droppedItem " + droppedItem);
	    	requesterInventory.add(droppedItem);
    	}
       
	    // ToDo: Group loot settings etc.  actual loot chance was lootgroupchance*lootchance    
	}
	
	
	private void handleLootGroup(LootGroup lootGroup,LootRollSession lootRollSession){
		
		int[] lootPoolChances = lootGroup.getLootPoolChances();
		String[] lootPoolNames = lootGroup.getLootPoolNames();
		if (lootPoolChances==null || lootPoolNames==null){
			System.err.println("Lootpools are null!");
			return;
		}
		if (lootPoolChances.length==0 || lootPoolNames.length==0){
			System.err.println("No Lootpools in Lootgroup!");
			return;
		}
		for(int i=0;i<lootPoolChances.length;i++) {
		    	int lootPoolElementRoll = new Random().nextInt(100);
		    	if (lootPoolElementRoll <= lootPoolChances[i]){
		    		System.out.println("this loot pool will drop something");
		    		handleLootPool(lootPoolNames[i],lootRollSession); // This loot pool will drop something			    		
		    	}			 
			}
	}
		
	private void handleLootPool(String poolName,LootRollSession lootRollSession){

		// Fetch the loot pool data from the poolName.py script		
		String path = "scripts/lootPools/"+poolName.toLowerCase(); 
		Vector<String> itemTemplates = (Vector<String>)core.scriptService.fetchStringVector(path,"itemTemplates");
		
		for (String s : itemTemplates){
			System.out.println("template: " + s);
		}
		
		Vector<Integer> itemChances = (Vector<Integer>)core.scriptService.fetchIntegerVector(path,"itemChances");
		
		// Now here maybe all items in pool have an equal chance to spawn
		// or they have percentages assigned to them as well
		
		int randomItemFromPool = new Random().nextInt(100);
		int itemIndex = (int)Math.floor(randomItemFromPool/100*itemTemplates.size()); // 5 Elements,rnd is 83 -> 0.83*5=
		
		lootRollSession.addDroppedItemTemplate(itemTemplates.get(itemIndex));
	}	
}	
