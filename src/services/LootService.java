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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import resources.objects.creature.CreatureObject;
import resources.objects.loot.LootGroup;
import resources.objects.loot.LootRollSession;
import resources.objects.tangible.TangibleObject;
import main.NGECore;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
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
			int maximalCredits = (int)Math.floor(4*creatureCL + creatureCL*creatureCL*4/100); 
			int minimalCredits = (int)Math.floor(creatureCL*2 + maximalCredits/2); 
			int spanOfCredits  = maximalCredits - minimalCredits;			
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
	    		handleLootGroup(lootGroup,lootRollSession); //this lootGroup will drop something e.g. {kraytpearl_range,krayt_tissue_rare}	    		
	    	}		
	    }
	    
	    
	    // ********** Phase 1 complete, loot items determined **********
	    // stored in the lootSession
	    
	    // Distribute the loot drops according to group loot rules	    
	    // For now just spawn items into requester's inventory
    	SWGObject requesterInventory = requester.getSlottedObject("inventory");
    	
    	for (TangibleObject droppedItem : lootRollSession.getDroppedItems()){		    
    		
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
		
		int randomItemFromGroup = new Random().nextInt(100);
		int remainder = 0; // [10,20,30,34,5,1] 
		
		for(int i=0;i<lootPoolChances.length;i++) {
			remainder += lootPoolChances[i]; 
	    	if (randomItemFromGroup <= remainder){
	    		System.out.println("this loot pool will drop something"); // e.g. kraytpearl_range
	    		handleLootPool(lootPoolNames[i],lootRollSession); // This loot pool will drop something	
	    		break;
	    	}			 
		}
	}
		
	private void handleLootPool(String poolName,LootRollSession lootRollSession){

		// Fetch the loot pool data from the poolName.py script		
		String path = "scripts/loot/lootPools/"+poolName.toLowerCase(); 
		Vector<String> itemNames = (Vector<String>)core.scriptService.fetchStringVector(path,"itemNames");
		
		for (String s : itemNames){
			System.out.println("template: " + s);
		}
		
		Vector<Integer> itemChances = (Vector<Integer>)core.scriptService.fetchIntegerVector(path,"itemChances");
				
		int randomItemFromPool = new Random().nextInt(100);
		int remainder = 0; // [10,20,30,34,5,1]
		
		for (int i=0;i<itemNames.size();i++){
			remainder += itemChances.get(i); 
			if (randomItemFromPool<=remainder){
				// this element has been chosen e.g. kraytpearl_flawless
				handleLootPoolItems(itemNames.get(i), lootRollSession);
				break;
			}						
		}
	}	
	
	private void handleLootPoolItems(String itemName,LootRollSession lootRollSession){

		String path = "scripts/loot/lootItems/"+itemName.toLowerCase(); 
		System.out.println("path: " + path);
		String itemTemplate = (String)core.scriptService.fetchString(path,"itemTemplate");
		String customName = (String)core.scriptService.fetchString(path,"customItemName");
		int stackCount = (Integer)core.scriptService.fetchInteger(path,"customItemStackCount");
		Vector<String> customizationAttributes = (Vector<String>)core.scriptService.fetchStringVector(path,"customizationAttributes");
		Vector<Integer> customizationValues = (Vector<Integer>)core.scriptService.fetchIntegerVector(path,"customizationValues");
		
		System.out.println("itemTemplate " + itemTemplate);
		
		/*
		 craftingValues = {
		{"mindamage",25,35,0},
		{"maxdamage",40,50,0},
		{"attackspeed",1,-1,5},
		{"woundchance",4,8,5},
		{"hitpoints",800,1200,0},
		{"attackhealthcost",0,9,0},
		{"attackactioncost",0,9,0},
		{"attackmindcost",0,9,0},
		{"forcecost",0,9.9,0},
		{"color",31,31,0},
		{"quality",6,6,0},
	},
		 */
		
		
		
		
		
		
		
		
		TangibleObject droppedItem = createDroppedItem(itemTemplate,lootRollSession.getSessionPlanet());
    	
    	handleCustomDropName(droppedItem);
    	handleStats(droppedItem);
    	setCustomization(droppedItem);
		
		lootRollSession.addDroppedItem(droppedItem);
	}	

	
	
	private void handleCustomDropName(TangibleObject droppedItem) {
		String customName = droppedItem.getCustomName();

		if (customName!=null) {
			if (customName.charAt(0) == '@' || customName.contains("_n:")) {
				customName = ""; // Look the name up in some tre table
			}
		}
		droppedItem.setCustomName(customName);
	}
	
	private TangibleObject createDroppedItem(String template,Planet planet){
		TangibleObject droppedItem = (TangibleObject) core.objectService.createObject(template, planet);				
    	System.out.println("droppedItem " + droppedItem);
    	return droppedItem;
	}
	
	
	
	private void setCustomization(TangibleObject droppedItem) {
		
		// Example color crystal
		droppedItem.setCustomizationVariable("/private/index_color_1", (byte) new Random().nextInt(7));  // 4 blue
		
		// More general 
//		String path = "scripts/loot/lootItems/"+droppedItem.getCustomName().toLowerCase(); 
//		Vector<String> customizationPaths = (Vector<String>)core.scriptService.fetchStringVector(path,"itemCustomizationPaths");
//		Vector<Integer> customizationValues = (Vector<Integer>)core.scriptService.fetchIntegerVector(path,"itemCustomizationValues");
//		for (int i=0;i<customizationPaths.size();i++){
//			String attributePath = customizationPaths.get(i);
//			int attributeValue = customizationValues.get(i);
//			droppedItem.setCustomizationVariable(attributePath, (byte)attributeValue);
//		}
	}
	
	private void handleStats(TangibleObject droppedItem) {
		// ToDo: Min,Max for weapons , Dots on weapons etc.
		// This must be considered for the python scripts as well
		// So basically every item needs to have loot-related data in their py scripts as well
	}
	
	
}	
