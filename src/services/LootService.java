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

import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.loot.LootDrop;
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
			
		String lootedObjectType = "Tangible";
		if (lootedObject instanceof CreatureObject)
			lootedObjectType = "Creature";

		// Credit drop is depending on the CL of the looted CreatureObject
		// or if explicitely assigned in the .py script
		int lootedCredits = 0;
		if (lootedObjectType.equals("Creature")){
			CreatureObject lootedCreature = (CreatureObject) lootedObject;
			int creatureCL = lootedCreature.getLevel();
			int minimalCredits = 40 * creatureCL; // Predetermined factor of 40 per CL
			int spanOfCredits  = 15 * creatureCL; // Predetermined factor of 15 per CL
			lootedCredits = minimalCredits + new Random().nextInt(spanOfCredits);
		}
		
		if (lootedObjectType.equals("Tangible")){
			// This is for chests etc.
			// Check the py script
		}
		
		
		List<LootDrop> lootDrops = new ArrayList<LootDrop>();
		List<String> lootElements = new ArrayList<String>();
		
		TreeSet<TreeMap<String,Integer>> lootSpec = lootedObject.getLootSpecification();
		Iterator<TreeMap<String,Integer>> iterator = lootSpec.iterator();
	     	    
	    while (iterator.hasNext()){
	    	TreeMap<String,Integer> lootPool = iterator.next();
	    	int poolProbability = lootPool.lastEntry().getValue();
	    	int lootPoolRoll = new Random().nextInt(100);
	    	if (lootPoolRoll <= poolProbability){    		
	    		lootElements = handleLootPool(lootPool); //this lootPool will drop
	    	}		
	    }
	    
	    // Now handle the chosen pools and determine the according items  

	    for (String element : lootElements){
	    	lootDrops = handleLootPoolElement(element);
	    }
	    
	    // ********** Phase 1 complete, loot items determined **********
	    
	    // Distribute the loot drops according to group loot rules
	    
	    
	    // For now just spawn items into requester's inventory
	    for (LootDrop dropElem : lootDrops){
	    	PlayerObject requesterObj = (PlayerObject) requester.getSlottedObject("ghost");	
	    	SWGObject requesterInventory = requesterObj.getSlottedObject("inventory");
	    	List<String> elementList = dropElem.getElements();
	    	for (String template : elementList){		    
		    	TangibleObject droppedItem = (TangibleObject) core.objectService.createObject(template, requester.getPlanet());				
		    	requesterInventory.add(droppedItem);
	    	}
	    }
	    
    
	    // ToDo: Group loot settings etc.
	    
		// [20:35] <@_Light> your actual loot chance was lootgroupchance*lootchance
		long leaderGroupId = requester.getGroupId();
		GroupObject group = (GroupObject) core.objectService.getObject(leaderGroupId);
		if(group.getMemberList().size() == 1) {
			// looter is alone
		}	
	}
	
	
	private List<String> handleLootPool(TreeMap<String,Integer> lootPool){
		List<String> lootElements = new ArrayList<String>();
		for(Map.Entry<String,Integer> entry : lootPool.entrySet()) {
			  String poolElementName = entry.getKey();
			  int poolElementProbability = entry.getValue();
		    	int lootPoolElementRoll = new Random().nextInt(100);
		    	if (lootPoolElementRoll <= poolElementProbability){
		    		lootElements.add(poolElementName);		    		
		    	}			 
			}

		return lootElements;
	}
		
	private List<LootDrop> handleLootPoolElement(String element){
		List<LootDrop> lootDropList = new ArrayList<LootDrop>();
		// This is just a random element from the python
		
		return lootDropList;
	}	
}
