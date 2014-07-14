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

import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import protocol.swg.PlayClientEffectObjectTransformMessage;
import protocol.swg.StopClientEffectObjectByLabel;
import resources.common.OutOfBand;
import resources.datatables.DisplayType;
import resources.datatables.Options;
import resources.datatables.WeaponType;
import resources.loot.LootGroup;
import resources.loot.LootRollSession;
import resources.objects.creature.CreatureObject;
import resources.objects.factorycrate.FactoryCrateObject;
import resources.objects.group.GroupObject;
import resources.objects.intangible.IntangibleObject;
import resources.objects.player.PlayerObject;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.ai.AIActor;
import services.sui.SUIService.ListBoxType;
import services.sui.SUIService.MessageBoxType;
import services.sui.SUIWindow;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import tools.DevLog;
import main.NGECore;
import engine.resources.container.CreatureContainerPermissions;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

/** 
 * @author Charon 
 */

@SuppressWarnings("unused")
public class LootService implements INetworkDispatch {
	
	private NGECore core;
	private static int prepInvCnt = 0;
	String testDropTemplate = null;
	//String testDropTemplate = "comlink";
	
	public LootService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	
	}

	@Override
	public void shutdown() {
		
	}
	
	public void handleLootRequest(CreatureObject requester, TangibleObject lootedObject) {
		//System.out.println("handleLootRequest ");
		// security check
		if (hasAccess(requester,lootedObject) && ! lootedObject.isLooted()){
			LootRollSession lootRollSession = (LootRollSession )lootedObject.getAttachment("LootSession");
			if (lootRollSession.getDroppedItems().size()==0){				
				DevLog.debugout("Charon", "Loot Service", "handleLootRequest lootRollSession.getDroppedItems().size()==0");
				return;			
			}
			SWGObject lootedObjectInventory = lootedObject.getSlottedObject("inventory");
			core.simulationService.openContainer(requester, lootedObjectInventory);	
			//setLooted(requester,lootedObject);
		}
	}

	private boolean hasAccess(CreatureObject requester, TangibleObject lootedObject){
		LootRollSession lootRollSession = (LootRollSession )lootedObject.getAttachment("LootSession");
		if (lootRollSession==null)
			DevLog.debugout("Charon", "Loot Service", "hasAccess LootSession null: " + lootRollSession);
		if (lootRollSession!=null){
			if (lootRollSession.getRequester()==requester){
				return true;
			}
		}
		// ToDo: Access for groups
		return false;
	}
	
	public void DropLoot(CreatureObject requester, TangibleObject lootedObject) {
		
		if (requester==null || lootedObject==null)
			return; //QA
		
		if (! requester.isPlayer())
			return; //QA
		
		GroupObject group = null;
		if(requester.getGroupId()!=0)
			group = (GroupObject) core.objectService.getObject(requester.getGroupId());
		
		if (lootedObject.getKiller()==null)
			lootedObject.setKiller(requester);
		
		if (lootedObject.isLooted() || lootedObject.isLootLock() || (group == null && !lootedObject.getKiller().equals(requester)) || (group != null && !group.getMemberList().contains(lootedObject.getKiller())))
			return;
		
		lootedObject.setLootLock(true);
		
		LootRollSession lootRollSession = new LootRollSession(requester,lootedObject);
		lootedObject.setAttachment("LootSession", lootRollSession); // preliminarily register session already here
		
		handleCreditDrop(requester,lootedObject,lootRollSession);
		
		lootSituationAssessment(requester,lootedObject,lootRollSession);
				
		//CreatureObject lootedCreature = (CreatureObject) lootedObject;
		
		//TreeSet<TreeMap<String,Integer>> lootSpec = lootedObject.getLootSpecification();
		 List<LootGroup> lootGroups = lootedObject.getLootGroups();
		 Iterator<LootGroup> iterator = lootGroups.iterator();
		 int projectionCoefficientMatrixModulo = 0;
		 projectionCoefficientMatrixModulo = outbound(requester);
	     	    
	    while (iterator.hasNext()){
	    	LootGroup lootGroup = iterator.next();
	    	double groupChance = lootGroup.getLootGroupChance();
	    	double lootGroupRoll = new Random().nextDouble()*100;
	    	if (projectionCoefficientMatrixModulo!=0)
	    		lootGroupRoll=groupChance+1;
	    	if (lootGroupRoll <= groupChance){    	
	    		handleLootGroup(lootGroup,lootRollSession); //this lootGroup will drop something e.g. {kraytpearl_range,krayt_tissue_rare}	    		
	    	}			    	
	    }
	    
	    
	    // Rare Loot System Stage (Is in place for all looted creatures with 6 CL difference)
		//if (lootRollSession.isAllowRareLoot()){
		if (true){
			int randomRareLoot = new Random().nextInt(100);
			int chanceRequirement = 1; 
			chanceRequirement = 10; // This is for a test period to lift chest drop chance a bit
			if (testDropTemplate!=null)
				chanceRequirement = 100;
			if (lootRollSession.getLootedObjectDifficulty()==1)
				chanceRequirement+=2;
			if (lootRollSession.getLootedObjectDifficulty()==2)
				chanceRequirement+=3;			
			if (lootRollSession.isIncreasedRLSChance())
				chanceRequirement+=3; 
			if (randomRareLoot <= chanceRequirement){ 
				handleRareLootChest(lootRollSession);
			}
			
			// to test:
			//handleRareLootChest(lootRollSession);
		}
		
	    // set info above corpse
	    //System.out.println("lootedObject instanceof CreatureObject " + (lootedObject instanceof CreatureObject));
	    if (lootedObject instanceof CreatureObject && lootRollSession.getDroppedItems().size()>0){
	    	try {
			    float y = 0.5F; // 1.3356977F
			    float qz= 1.06535322E9F;
			    Point3D effectorPosition = new Point3D(0,y,0);
				Quaternion effectorOrientation = new Quaternion(0,0,0,qz);
			    PlayClientEffectObjectTransformMessage lmsg = new PlayClientEffectObjectTransformMessage("appearance/pt_loot_disc.prt",lootedObject.getObjectID(),"lootMe",effectorPosition,effectorOrientation);
			    requester.getClient().getSession().write(lmsg.serialize());
			    tools.CharonPacketUtils.printAnalysis(lmsg.serialize());
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    // handle errors
	    if (lootRollSession.getErrorMessages().size()>0){
	    	for (String msg : lootRollSession.getErrorMessages()){
	    		// ToDo: Show this for each group member later!
	    		requester.sendSystemMessage(msg,(byte) 1);
	    		lootedObject.setLootLock(false);
	    		return;
	    	}
	    }
	    	    
	    SWGObject lootedObjectInventory = lootedObject.getSlottedObject("inventory");
	    if (lootedObjectInventory==null || lootedObject.getTemplate().startsWith("object/tangible/container/drum/shared_treasure_drum.iff"))
	    	lootedObjectInventory = lootedObject; // In case we don't know what we are dealing with
	   
	    // For autoloot 
    	//SWGObject requesterInventory = requester.getSlottedObject("inventory");
	    //System.out.println("lootRollSession.getDroppedItems() " + (lootRollSession.getDroppedItems()));
	    DevLog.debugout("Charon", "Loot Service", "lootRollSession.getDroppedItems().size() " + lootRollSession.getDroppedItems().size());
    	for (TangibleObject droppedItem : lootRollSession.getDroppedItems()){		    
    		DevLog.debugout("Charon", "Loot Service", "droppedItem " + droppedItem.getCustomName());
    		//droppedItem.setAttachment("radial_filename", "lootitem");
    		//if (! droppedItem.getTemplate().contains("shared_rare_loot_chest"))
    		lootedObjectInventory.add(droppedItem);
    		
    		
    		// RLS chest effect
	    	if (droppedItem.getAttachment("LootItemName").toString().contains("Loot Chest")){

	    		lootedObject.playEffectObject("appearance/pt_rare_chest.prt", "");
	    		requester.playMusic("sound/rare_loot_chest.snd");
	    	}
    	}
    	
    	// register session in service
    	lootedObject.setAttachment("LootSession", lootRollSession);
    	    	
    	//lootedObject.setLooted(true); 
	    // ToDo: Group loot settings etc.  actual loot chance was lootgroupchance*lootchance    
	}
	
	public void handleJunkDealerSellWindow(CreatureObject actor, SWGObject junkDealer, Vector<SWGObject> sellableItems) {
		
		if (sellableItems.size() == 0) {
			SUIWindow notInterestedWindow = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_OK, "@loot_dealer:sell_title", "@loot_dealer:no_items", actor, junkDealer, (float) 10);
			core.suiService.openSUIWindow(notInterestedWindow);
			return;
		}
		
		Map<Long, String> itemsToSell = new HashMap<Long, String>();
		sellableItems.forEach(obj -> {
			if (!((TangibleObject)obj).isNoSell()) {
				int price = ((TangibleObject) obj).getJunkDealerPrice();
				if (((TangibleObject) obj).getUses() > 0) { price = price * ((TangibleObject) obj).getUses(); }
				
				itemsToSell.put(obj.getObjectID(), "[" + price + "] " + obj.getCustomName());
			}
		});
		
		if (itemsToSell.size() == 0) { // Every item is in the sellable's vector, however only items the player is offering gets put in list.
			SUIWindow notInterestedWindow = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_OK, "@loot_dealer:sell_title", "@loot_dealer:no_items", actor, junkDealer, (float) 10);
			core.suiService.openSUIWindow(notInterestedWindow);
			return;
		}
		
		final SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@loot_dealer:sell_title", "@loot_dealer:sell_prompt", itemsToSell, actor, junkDealer, (float) 10);
		
		window.setProperty("btnOther:Visible", "True");
		window.setProperty("btnOther:Text", "@loot_dealer:examine");
		window.setProperty("btnOk:Text", "@loot_dealer:btn_sell");
		
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		SUICallback windowCallback = ((owner, eventType, resultList) -> {
			switch(eventType) {
				case 0: // Sell
					TangibleObject itemToSell = null;
					int index = Integer.parseInt(resultList.get(0));
					long sellItemId = window.getObjectIdByIndex(index);
					
					if(sellItemId == 0 || core.objectService.getObject(sellItemId) == null)
						return;
					
					itemToSell = (TangibleObject) core.objectService.getObject(sellItemId);
					
					int price = itemToSell.getJunkDealerPrice();
					
					if (price == 0)
						price = 20;
					
					if (itemToSell.getUses() > 0)
						price *= itemToSell.getUses();
					
					TangibleObject inventory = (TangibleObject) actor.getSlottedObject("inventory");
					
					if (inventory == null)
						return;
					
					addToBuyBack(actor, itemToSell, inventory); // This removes the item from inventory as well
					
					actor.addCashCredits(price);
					actor.sendSystemMessage(OutOfBand.ProsePackage("@junk_dealer:prose_sold_junk", "TT", itemToSell.getCustomName(), "DI", price), DisplayType.Broadcast);
					
					core.suiService.closeSUIWindow(actor, window.getWindowId());
					handleJunkDealerSellWindow(actor, junkDealer, getSellableInventoryItems(actor));
					break;
				case 1: // Cancel
					core.suiService.closeSUIWindow(actor, window.getWindowId());
					break;
				case 2: // TODO: Show Examine Window for objects - Junk Dealer (/examine command?)
					break;
			}
		});
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, windowCallback);
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, windowCallback);
		window.addHandler(2, "", Trigger.TRIGGER_UPDATE, returnList, windowCallback);

		core.suiService.openSUIWindow(window);
	
	}
	
	public void handleJunkDealerBuyBackWindow(CreatureObject actor, SWGObject junkDealer) {
		
		TangibleObject bbContainer = (TangibleObject) core.objectService.getObject((long) actor.getAttachment("buy_back"));
		Map<Long, String> soldItems = new HashMap<Long, String>();
		bbContainer.viewChildren(actor, false, false, (obj) -> {
			int price = ((TangibleObject) obj).getJunkDealerPrice();
			if (((TangibleObject) obj).getUses() > 0) { price = price * ((TangibleObject) obj).getUses(); }
			
			soldItems.put(obj.getObjectID(), "[" + price + "] " + obj.getCustomName());
		});
		
		final SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@loot_dealer:buy_back_title", "@loot_dealer:buy_back_prompt", soldItems, actor, junkDealer, (float) 10);
		window.setProperty("btnOther:Visible", "True");
		window.setProperty("btnOther:Text", "@loot_dealer:examine");
		window.setProperty("btnOk:Text", "@loot_dealer:btn_buy_back");
		
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		SUICallback windowCallback = ((owner, eventType, resultList) -> {
			switch(eventType) {
				case 0: // Buy Back
					TangibleObject retrieveItem = null;
					int index = Integer.parseInt(resultList.get(0));
					long retrieveItemId = window.getObjectIdByIndex(index);
					
					if(retrieveItemId == 0 || core.objectService.getObject(retrieveItemId) == null) {
						actor.sendSystemMessage("@loot_dealer:no_buy_back_items_found", DisplayType.Broadcast);
						return;
					}
					
					retrieveItem = (TangibleObject) core.objectService.getObject(retrieveItemId);
					
					if (actor.getInventoryItemCount() >= 80) {
						actor.sendSystemMessage("@loot_dealer:no_space_in_inventory", DisplayType.Broadcast);
						return;
					}

					TangibleObject inventory = (TangibleObject) actor.getSlottedObject("inventory");
					
					if (inventory == null)
						return;
					
					int value = retrieveItem.getJunkDealerPrice();
					
					if (retrieveItem.getUses() > 0)
						value *= retrieveItem.getUses();
					
					if (actor.getCashCredits() < value) {
						actor.sendSystemMessage(OutOfBand.ProsePackage("@loot_dealer:prose_no_buy_back", "TT", retrieveItem.getCustomName()), DisplayType.Broadcast);
						return;
					}
					
					actor.deductCashCredits(value);

					actor.sendSystemMessage(OutOfBand.ProsePackage("@base_player:prose_pay_success", "DI", value, "TT", "a Junk Dealer"), DisplayType.Broadcast);

					bbContainer.transferTo(actor, inventory, retrieveItem);
					actor.sendSystemMessage(OutOfBand.ProsePackage("@loot_dealer:prose_buy_back_junk", "DI", value, "TT", retrieveItem.getCustomName()), DisplayType.Broadcast);
					
					core.suiService.closeSUIWindow(actor, window.getWindowId());
					
					handleJunkDealerBuyBackWindow(actor, junkDealer);
					break;
				case 1: // Cancel
					break;
				case 2: // TODO: Show Examine Window for objects - Junk Dealer (/examine command?)
					break;
			}
		});
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, windowCallback);
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, windowCallback);
		window.addHandler(2, "", Trigger.TRIGGER_UPDATE, returnList, windowCallback);
		core.suiService.openSUIWindow(window);
	}
	
	public void handleJunkDealerMarkItems(CreatureObject actor, SWGObject junkDealer) {

		Vector<SWGObject> sellableItems = getSellableInventoryItems(actor);
		
		if (sellableItems.size() == 0) {
			SUIWindow notFoundWindow = core.suiService.createMessageBox(MessageBoxType.MESSAGE_BOX_OK, 
					"@loot_dealer:junk_not_found_title", "@loot_dealer:junk_not_found_description", actor, junkDealer, (float) 10);
			core.suiService.openSUIWindow(notFoundWindow);
			return;
		}
		
		Map<Long, String> markableItems = new HashMap<Long, String>();
		sellableItems.forEach(obj -> {
			if (((TangibleObject) obj).isNoSell()) markableItems.put(obj.getObjectID(), "[ *No Sell* ] " + obj.getCustomName());	 
			else markableItems.put(obj.getObjectID(), "[ Sellable ] " + obj.getCustomName());
		});
		
		final SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@loot_dealer:junk_no_sell_title", "@loot_dealer:junk_no_sell_description", 
				markableItems, actor, junkDealer, (float) 10);
		
		window.setProperty("btnOther:visible", "True");
		window.setProperty("btnOther:Text", "@loot_dealer:examine");
		window.setProperty("btnOk:Text", "@loot_dealer:junk_no_sell_button");
		
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		SUICallback windowCallback = ((owner, eventType, resultList) -> {
			switch(eventType) {
				case 0:
					TangibleObject markItem = null;
					int index = Integer.parseInt(resultList.get(0));
					long markItemId = window.getObjectIdByIndex(index);
					
					if(markItemId == 0 || core.objectService.getObject(markItemId) == null)
						return;
					
					markItem = (TangibleObject) core.objectService.getObject(markItemId);
					
					if (markItem.isNoSell()) markItem.setNoSell(false);
					else markItem.setNoSell(true);
					
					core.suiService.closeSUIWindow(actor, window.getWindowId());
					handleJunkDealerMarkItems(actor, junkDealer);
					break;
				case 1: // Cancel
					break;
				case 2: // TODO: Show Examine Window for objects - Junk Dealer (/examine command?)
					break;
			}
		});
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, windowCallback);
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, windowCallback);
		window.addHandler(2, "", Trigger.TRIGGER_UPDATE, returnList, windowCallback);
		
		core.suiService.openSUIWindow(window);
	}

	public void addToBuyBack(CreatureObject actor, TangibleObject item, TangibleObject inventory) {
		
		TangibleObject buyBackContainer = null;

		if (actor.getAttachment("buy_back") == null)
			buyBackContainer = createBuyBackDevice(actor);
		else
			buyBackContainer = (TangibleObject) core.objectService.getObject((long) actor.getAttachment("buy_back"));
		
		if (buyBackContainer == null) // at this point the buybackcontainer should not be null... but just incase...
			buyBackContainer = createBuyBackDevice(actor); 		
		
		inventory.transferTo(actor, buyBackContainer, item);

		final AtomicInteger count = new AtomicInteger();
		
		buyBackContainer.viewChildren(actor, false, true, new Traverser() {

			@Override
			public void process(SWGObject child) {
				if (count.incrementAndGet() >= 11) {
					core.objectService.destroyObject(child); // This obj should be the last added object (top-down), so it's safe to remove it.
				}
			}
		});
	}
	
	private void handleLootGroup(LootGroup lootGroup,LootRollSession lootRollSession){
		
		double[] lootPoolChances = lootGroup.getLootPoolChances();
		String[] lootPoolNames = lootGroup.getLootPoolNames();
		if (lootPoolChances==null || lootPoolNames==null){
			DevLog.debugout("Charon", "Loot Service", "handleLootGroup Lootpools are null!");
			return;
		}
		if (lootPoolChances.length==0 || lootPoolNames.length==0){
			DevLog.debugout("Charon", "Loot Service", "handleLootGroup No Lootpools in Lootgroup!");
			return;
		}
		
		double randomItemFromGroup = new Random().nextDouble()*100;
		double remainder = 0; // [10,20,30,34,5,1] 
		double span = 100/lootPoolNames.length;
		
		boolean test = false;
		
		for(int i=0;i<lootPoolChances.length;i++) {
			if (lootPoolChances[0]!=-1.0)
				remainder += lootPoolChances[i];
			else 
				remainder += span;
	    	if (randomItemFromGroup <= remainder){ 		
	    		//System.out.println("this loot pool will drop something"); // e.g. kraytpearl_range
	    		handleLootPool(lootPoolNames[i],lootRollSession); // This loot pool will drop something	
	    		test = true;
	    		break;
	    	}			 
		}
		if (!test)
			DevLog.debugout("Charon", "Loot Service", "handleLootGroup SOMETHING WENT WRONG!");
	}
		
	private void handleLootPool(String poolName,LootRollSession lootRollSession){
		//System.err.println("poolName.toLowerCase() " + poolName.toLowerCase());
		// Fetch the loot pool data from the poolName.py script		
		String path = "scripts/loot/lootPools/"+poolName.toLowerCase(); 
		Vector<String> itemNames = (Vector<String>)core.scriptService.fetchStringVector(path,"itemNames");
		
		Vector<Double> itemChances = (Vector<Double>)core.scriptService.fetchDoubleVector(path,"itemChances");
				
		double randomItemFromPool = new Random().nextDouble()*100;
		double remainder = 0.0; // [10,20,30,34,5,1]
		double span = 100.0/(double)itemNames.size();

		for (int i=0;i<itemNames.size();i++){
			if (itemChances.get(0)!=-1.0)
				remainder += itemChances.get(i); 
			else
				remainder += span; 
			if (randomItemFromPool<=remainder){
				// this element has been chosen e.g. kraytpearl_flawless
				//System.err.println("CHOSEN ITEM " + itemNames.get(i));
				handleLootPoolItems(itemNames.get(i), lootRollSession);	
				//handleLootPoolItems(testDropTemplate, lootRollSession);
				//break;
				return;
			}						
		}
	}	
	
	private static class DirectoriesFilter implements Filter<Path> {
	    @Override
	    public boolean accept(Path entry) throws IOException {
	        return Files.isDirectory(entry);
	    }
	}
	
	public SWGObject generateLootItem(CreatureObject requester, String template)
	{
		LootRollSession rollSession = new LootRollSession();
		rollSession.setSessionPlanet(requester.getPlanet());
		handleLootPoolItems(template, rollSession);
		
		if(rollSession.getDroppedItems().get(0) != null) return rollSession.getDroppedItems().get(0);
		
		return null;
	}
	
	private void handleLootPoolItems(String itemName,LootRollSession lootRollSession){
		//System.out.println("itemName " + itemName);
		final Vector<String> foundPath = new Vector<String>(); 
		Path p = Paths.get("scripts/loot/lootItems/");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	String actualFileName = file.getFileName().toString();
	        	actualFileName = actualFileName.substring(0, actualFileName.length()-3).toLowerCase();
	        	if (actualFileName.equals(itemName.toLowerCase())){
	        		foundPath.add(file.toString());
	        	} 	        	
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if (foundPath.size()==0){
			String errorMessage = "Loot item  '" + itemName + "'  not found in file system. Please contact Charon about this issue.";
			lootRollSession.addErrorMessage(errorMessage);
			return;
			
		}		
		String itemPath = foundPath.get(0);
		
		itemPath = itemPath.substring(0, itemPath.length()-3); // remove the file type

		String customName = "";
		String itemTemplate = "";
		Vector<String> itemTemplates = null;
		int stackCount = 1;
		int biolink = 0;
		int requiredCL = 1;
		int stackable = -1;
		int junkDealerPrice = 0;
		byte junkType = -1;
		String requiredProfession = "";
		String requiredFaction = "";
		Vector<String> customizationAttributes = null;
		Vector<Integer> customizationValues = null;
		Vector<String> reverse_engineering_name = null;
		int customColor1 = 0;
		String lootDescriptor = "";
		Vector<String> itemStats = null;
		Vector<String> itemSkillMods = null;
		Vector<String> STFparams = null;
		String addToCollection = null;
		String collectionItemName = null;		
		
		if(core.scriptService.getMethod(itemPath,"","itemTemplate")==null){
			String errorMessage = "Loot item  '" + itemName + "'  has no template function assigned in its script. Please contact Charon about this issue.";
			lootRollSession.addErrorMessage(errorMessage);
			return;
		}
		
		itemTemplates = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"itemTemplate"); 
		if (itemTemplates.size()==1)
			itemTemplate = itemTemplates.get(0);
		if (itemTemplates.size()>1){
			itemTemplate = itemTemplates.get(new Random().nextInt(itemTemplates.size()-1));
		}
				
		// only consider the following variables, if they are in the python-script file
		if(core.scriptService.getMethod(itemPath,"","customItemName")!=null) 
			customName = (String)core.scriptService.fetchString(itemPath,"customItemName");
		
		if(core.scriptService.getMethod(itemPath,"","customItemStackCount")!=null)
			stackCount = (Integer)core.scriptService.fetchInteger(itemPath,"customItemStackCount");
		
		if(core.scriptService.getMethod(itemPath,"","customizationAttributes")!=null)
			customizationAttributes = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"customizationAttributes");
		
		if(core.scriptService.getMethod(itemPath,"","customizationValues")!=null)
			customizationValues = (Vector<Integer>)core.scriptService.fetchIntegerVector(itemPath,"customizationValues");
		
		if(core.scriptService.getMethod(itemPath,"","reverse_engineering_name")!=null)
			reverse_engineering_name = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"reverse_engineering_name");
		
		if(core.scriptService.getMethod(itemPath,"","customColor1")!=null)
			customColor1 = (Integer)core.scriptService.fetchInteger(itemPath,"customColor1");
		
		if(core.scriptService.getMethod(itemPath,"","lootDescriptor")!=null) 
			lootDescriptor = (String)core.scriptService.fetchString(itemPath,"lootDescriptor");
		
		if(core.scriptService.getMethod(itemPath,"","itemStats")!=null)
			itemStats = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"itemStats");
		
		if(core.scriptService.getMethod(itemPath,"","itemSkillMods")!=null)
			itemSkillMods = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"itemSkillMods");
			
		if(core.scriptService.getMethod(itemPath,"","biolink")!=null)
			biolink = (Integer)core.scriptService.fetchInteger(itemPath,"biolink");
		
		if(core.scriptService.getMethod(itemPath,"","requiredCL")!=null)
			requiredCL = (Integer)core.scriptService.fetchInteger(itemPath,"requiredCL");

		if(core.scriptService.getMethod(itemPath,"","requiredProfession")!=null)
			requiredProfession = (String)core.scriptService.fetchString(itemPath,"requiredProfession");
		
		if(core.scriptService.getMethod(itemPath,"","requiredFaction")!=null)
			requiredFaction = (String)core.scriptService.fetchString(itemPath,"requiredFaction");
		
		if(core.scriptService.getMethod(itemPath,"","stackable")!=null)
			stackable =  (Integer)core.scriptService.fetchInteger(itemPath,"stackable");
		
		if(core.scriptService.getMethod(itemPath,"","junkDealerPrice")!=null)
			junkDealerPrice =  (Integer)core.scriptService.fetchInteger(itemPath,"junkDealerPrice");
		
		if(core.scriptService.getMethod(itemPath,"","junkType")!=null)
			junkType =  (byte)core.scriptService.fetchInteger(itemPath,"junkType");
		
		if(core.scriptService.getMethod(itemPath,"","STFparams")!=null)
			STFparams = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"STFparams");
		
		if(core.scriptService.getMethod(itemPath,"","AddToCollection")!=null)
			addToCollection = (String)core.scriptService.fetchString(itemPath,"AddToCollection");
		
		if(core.scriptService.getMethod(itemPath,"","CollectionItemName")!=null)
			collectionItemName = (String)core.scriptService.fetchString(itemPath,"CollectionItemName");
		
			
		//System.out.println("itemTemplate " + itemTemplate);

		
		TangibleObject droppedItem = createDroppedItem(itemTemplate,lootRollSession.getSessionPlanet());
		
		droppedItem.setLootItem(true);
		droppedItem.setAttachment("LootItemName", itemName);
		droppedItem.setAttachment("customColor1", customColor1);
		droppedItem.setAttachment("lootDescriptor", lootDescriptor);
		droppedItem.setAttachment("reverse_engineering_name", reverse_engineering_name);		
		droppedItem.setAttachment("customName", customName);
		droppedItem.setAttachment("CollectionItemName", collectionItemName);
    	
		if(core.scriptService.getMethod(itemPath,"","randomStatJewelry")!=null){
			customName = setRandomStatsJewelry(droppedItem, lootRollSession);
		}
		
		if (!customName.isEmpty())
			handleCustomDropName(droppedItem, customName);
		//stackable = 1;
		if (stackable!=-1){
			if(stackable==1)
				droppedItem.setStackable(true);
			else
				droppedItem.setStackable(false);
    	}	
		
		if (junkDealerPrice!=0){
    		droppedItem.setJunkDealerPrice(junkDealerPrice);
    	}
		
		if (junkType!=-1){
    		droppedItem.setJunkType(junkType);
    	}
    	
		if (biolink>0){
    		droppedItem.setStringAttribute("bio_link", "bio_link_pending");
    	}
		
    	if (itemStats!=null){
    		if (itemStats.size()%3!=0){
    			String errorMessage = "Loot item  '" + itemName + "'  has a wrong number of itemstats. Please contact Charon about this issue.";
    			lootRollSession.addErrorMessage(errorMessage);
    			return;
    		}
    		handleStats(droppedItem, itemStats, lootRollSession);
    	}
    	
    	if (itemSkillMods!=null){
    		handleSkillMods(droppedItem, itemSkillMods);
    	}
    	
    	if (STFparams!=null){
    		setSTFParams(droppedItem, STFparams);
    	}
    	
    	if (addToCollection!=null) {
    		droppedItem.getAttributes().put("@obj_attr_n:collection_name", "@collection_n:"+addToCollection); 
    		//droppedItem.getAttributes().put("@obj_attr_n:collection_name", "\\#FFFF00 @collection_n:"+addToCollection + " \\#FFFFFF "); 
    		//core.collectionService.addCollection(actor, "new_prof_officer_master")
    		droppedItem.setAttachment("AddToCollection", addToCollection);
    		droppedItem.setAttachment("radial_filename", "item/collection");
    		//System.out.println("collection");
    	}
    	
    	if (collectionItemName!=null){
    		droppedItem.setAttachment("CollectionItemName", collectionItemName);
    		
    	}
    	
//    	if (customizationValues!=null)
//    		setCustomization(droppedItem, itemName);
    	
    	setCustomization(droppedItem, itemName, customizationAttributes, customizationValues); // for now
    	
    	handleSpecialItems(droppedItem, itemName);
		
    	if (requiredCL>1){
    		droppedItem.setIntAttribute("required_combat_level", requiredCL);
    	}
    	
    	if (requiredProfession.length()>0){
    		droppedItem.setStringAttribute("class_required", requiredProfession);
    	}
    	
    	if (requiredFaction.length()>0){
    		droppedItem.setStringAttribute("required_faction", requiredFaction);
    	}
    	
    	if(core.scriptService.getMethod(itemPath,"","customSetup") != null)
			core.scriptService.callScript(itemPath, "", "customSetup", droppedItem);
    	
    	
		lootRollSession.addDroppedItem(droppedItem);
	}	
	
	private void setSTFParams(TangibleObject droppedItem, Vector<String>STFparams){
		if (STFparams.size()!=4)
			return;
		String filename1 = "static_item_n";
		String filename2 = "static_item_d";
		filename1 = STFparams.get(0);
		filename2 = STFparams.get(2);
		String stfName = STFparams.get(1);
		String detailName = STFparams.get(3);
		droppedItem.setStfFilename(filename1);
		droppedItem.setStfName(stfName);
		droppedItem.setDetailFilename(filename2);
		droppedItem.setDetailName(detailName);
	}
	
	private void handleCustomDropName(TangibleObject droppedItem,String customName) {
//		String customItemName = droppedItem.getCustomName();
//		if (customName.charAt(0) == '@' || customName.contains("_n:")) {
//				if (customName!=null) {
//			customName = ""; // Look the name up in some tre table
//			}
//		}
		droppedItem.setCustomName(customName);
	}
	
	private TangibleObject createDroppedItem(String template,Planet planet){
		TangibleObject droppedItem = (TangibleObject) core.objectService.createObject(template, planet);				
    	//System.out.println("droppedItem " + droppedItem);
    	return droppedItem;
	}
	
	private TangibleObject createDroppedSchematic(String template,Planet planet){
		TangibleObject droppedItem = (TangibleObject) core.objectService.createObject(template, planet);				
    	//System.out.println("droppedItem " + droppedItem);
    	return droppedItem;
	}
	
	private void handleRareLootChest(LootRollSession lootRollSession){
		
		TangibleObject droppedItem = null;
		
		int legendaryRoll = new Random().nextInt(100);
		int exceptionalRoll = new Random().nextInt(100);
		int chancemodifier = 0;
		if (lootRollSession.isIncreasedRLSChance())
			chancemodifier += 15;
		
		//legendaryRoll=500; // For TEST
		//legendaryRoll=1; // For TEST
		//exceptionalRoll = 5;
		
		if (legendaryRoll<2+chancemodifier){ 
			String itemTemplate="object/tangible/item/shared_rare_loot_chest_3.iff";
			droppedItem = createDroppedItem(itemTemplate,lootRollSession.getSessionPlanet());
			String itemName = "Legendary Loot Chest";
			droppedItem.setStfFilename("loot_n");
			droppedItem.setStfName("rare_loot_chest_3_n");
			droppedItem.setDetailFilename("loot_n");
			droppedItem.setDetailName("rare_loot_chest_3_d");
			droppedItem.setAttachment("LootItemName", itemName);
			droppedItem.getAttributes().put("@obj_attr_n:rare_loot_category", "\\#D1F56F Rare Item \\#FFFFFF ");
			//fillLegendaryChest(droppedItem, lootRollSession);
			
		} else if (exceptionalRoll<10+chancemodifier){
			String itemTemplate="object/tangible/item/shared_rare_loot_chest_2.iff";
			droppedItem = createDroppedItem(itemTemplate,lootRollSession.getSessionPlanet());
			String itemName = "Exceptional Loot Chest";
			droppedItem.setStfFilename("loot_n");
			droppedItem.setStfName("rare_loot_chest_2_n");
			droppedItem.setDetailFilename("loot_n");
			droppedItem.setDetailName("rare_loot_chest_2_d");
			droppedItem.setAttachment("LootItemName", itemName);
			droppedItem.getAttributes().put("@obj_attr_n:rare_loot_category", "\\#D1F56F Rare Item \\#FFFFFF ");
			//fillExceptionalChest(droppedItem, lootRollSession);
		} else {
			String itemTemplate="object/tangible/item/shared_rare_loot_chest_1.iff";
			droppedItem = createDroppedItem(itemTemplate,lootRollSession.getSessionPlanet());
			String itemName = "Rare Loot Chest";
			droppedItem.setStfFilename("loot_n");
			droppedItem.setStfName("rare_loot_chest_1_n");
			droppedItem.setDetailFilename("loot_n");
			droppedItem.setDetailName("rare_loot_chest_1_d");
			droppedItem.setAttachment("LootItemName", itemName);
			droppedItem.getAttributes().put("@obj_attr_n:rare_loot_category", "\\#D1F56F Rare Item \\#FFFFFF ");
			//fillRareChest(droppedItem, lootRollSession);
		}

		lootRollSession.addDroppedItem(droppedItem);
	}
	
	public void fillrarelootchest(CreatureObject owner, TangibleObject chest){
		
		SWGObject inventory = owner.getSlottedObject("inventory");
    	inventory.remove(chest);
    			
		if (chest.getTemplate().contains("object/tangible/item/shared_rare_loot_chest_1.iff")){
			fillRareChest(owner, chest);
		}
		if (chest.getTemplate().contains("object/tangible/item/shared_rare_loot_chest_2.iff")){
			fillExceptionalChest(owner, chest);
		}
		if (chest.getTemplate().contains("object/tangible/item/shared_rare_loot_chest_3.iff")){
			fillLegendaryChest(owner, chest);
		}		
		core.objectService.destroyObject(chest.getObjectID());
	}
	
	private void fillLegendaryChest(CreatureObject owner, TangibleObject chest){
		int itemNumber = 2;
		int quantityModifierChance = new Random().nextInt(100);
		if (quantityModifierChance>25 && quantityModifierChance<50)
			itemNumber = 1;
		if (quantityModifierChance<25)
			itemNumber = 3;
		
		List<LootGroup> lootGroups = new ArrayList<LootGroup>();
		for (int i=0;i<itemNumber;i++){				
			String[] lootPoolNames = new String[]{"legendarytable"};
			double[] lootPoolChances = new double[]{100};
			int lootGroupChance = 100;
			LootGroup singleLootGroup = new LootGroup(lootPoolNames, lootPoolChances, lootGroupChance);
			lootGroups.add(singleLootGroup);			
		}
		
		
		Iterator<LootGroup> iterator = lootGroups.iterator();
		 
	    while (iterator.hasNext()){
	    	LootGroup lootGroup = iterator.next();	    	
	    	fillChest2(lootGroup, owner, chest);     			    			
	    }
	}
	
	private void fillExceptionalChest(CreatureObject owner, TangibleObject chest){
		int itemNumber = 2;
		int quantityModifierChance = new Random().nextInt(100);
		if (quantityModifierChance>25 && quantityModifierChance<50)
			itemNumber = 1;
		if (quantityModifierChance<25)
			itemNumber = 3;
		
		List<LootGroup> lootGroups = new ArrayList<LootGroup>();
		for (int i=0;i<itemNumber;i++){				
			String[] lootPoolNames = new String[]{"exceptionaltable"};
			double[] lootPoolChances = new double[]{100};
			int lootGroupChance = 100;
			LootGroup singleLootGroup = new LootGroup(lootPoolNames, lootPoolChances, lootGroupChance);
			lootGroups.add(singleLootGroup);			
		}
		
		
		Iterator<LootGroup> iterator = lootGroups.iterator();
		 
	    while (iterator.hasNext()){
	    	LootGroup lootGroup = iterator.next();	    	
	    	fillChest2(lootGroup, owner, chest);     			    			
	    }
	}

	private void fillRareChest(CreatureObject owner, TangibleObject chest){
		
		int itemNumber = 1;
		int quantityModifierChance = new Random().nextInt(100);
		if (quantityModifierChance>15 && quantityModifierChance<50)
			itemNumber = 2;
		if (quantityModifierChance<15)
			itemNumber = 3;
		
		List<LootGroup> lootGroups = new ArrayList<LootGroup>();
		for (int i=0;i<itemNumber;i++){				
			String[] lootPoolNames = new String[]{"raretable"};
			double[] lootPoolChances = new double[]{100};
			int lootGroupChance = 100;
			LootGroup singleLootGroup = new LootGroup(lootPoolNames, lootPoolChances, lootGroupChance);
			lootGroups.add(singleLootGroup);			
		}
		
		
		Iterator<LootGroup> iterator = lootGroups.iterator();
		 
	    while (iterator.hasNext()){
	    	LootGroup lootGroup = iterator.next();	    	
	    	fillChest2(lootGroup, owner, chest);     			    			
	    }
	}
	
	private void fillChest2(LootGroup lootGroup, CreatureObject owner, TangibleObject chest){
		
		double[] lootPoolChances = lootGroup.getLootPoolChances();
		String[] lootPoolNames = lootGroup.getLootPoolNames();
		if (lootPoolChances==null || lootPoolNames==null){
			DevLog.debugout("Charon", "Loot Service", "fillChest2 Lootpools are null!");
			return;
		}
		if (lootPoolChances.length==0 || lootPoolNames.length==0){
			DevLog.debugout("Charon", "Loot Service", "fillChest2 No Lootpools in Lootgroup!");
			return;
		}
		
		double randomItemFromGroup = new Random().nextDouble()*100;
		double remainder = 0; // [10,20,30,34,5,1] 
		double span = 100/lootPoolNames.length;
		
		boolean test = false;
		
		for(int i=0;i<lootPoolChances.length;i++) {
			if (lootPoolChances[0]!=-1.0)
				remainder += lootPoolChances[i];
			else 
				remainder += span;
	    	if (randomItemFromGroup <= remainder){ 		
	    		//System.out.println("this loot pool will drop something"); // e.g. kraytpearl_range
	    		fillChest3(lootPoolNames[i], owner, chest); // This loot pool will drop something	
	    		test = true;
	    		break;
	    	}			 
		}
		if (!test)
			DevLog.debugout("Charon", "Loot Service", "fillChest2 SOMETHING WENT WRONG!");
	}
		
	private void fillChest3(String poolName, CreatureObject owner, TangibleObject chest){
		//System.err.println("poolName.toLowerCase() " + poolName.toLowerCase());
		// Fetch the loot pool data from the poolName.py script		
		String path = "scripts/loot/rarelootchestcontents/"+poolName.toLowerCase(); 
		Vector<String> itemNames = (Vector<String>)core.scriptService.fetchStringVector(path,"itemNames");
		
		Vector<Double> itemChances = (Vector<Double>)core.scriptService.fetchDoubleVector(path,"itemChances");
				
		double randomItemFromPool = new Random().nextDouble()*100;
		double remainder = 0.0; // [10,20,30,34,5,1]
		double span = 100.0/(double)itemNames.size();

		for (int i=0;i<itemNames.size();i++){
			if (itemChances.get(0)!=-1.0)
				remainder += itemChances.get(i); 
			else
				remainder += span; 
			if (randomItemFromPool<=remainder){
				//fillChest4(itemNames.get(i), owner, chest);
				
				if (testDropTemplate!=null)
					fillChest4(testDropTemplate, owner, chest);
				else
					fillChest4(itemNames.get(i), owner, chest);
					
				
				
				//fillChest4("colorcrystal", owner, chest);
				//break;
				return;
			}						
		}
	}
	
	
	
	private void fillChest4(String itemName, CreatureObject owner, TangibleObject chest){
		
		final Vector<String> foundPath = new Vector<String>(); 
		Path p = Paths.get("scripts/loot/lootItems/");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	String actualFileName = file.getFileName().toString();
	        	actualFileName = actualFileName.substring(0, actualFileName.length()-3).toLowerCase();
	        	if (actualFileName.equals(itemName.toLowerCase())){
	        		foundPath.add(file.toString());
	        	} 	        	
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if (foundPath.size()==0){
			String errorMessage = "Loot item  '" + itemName + "'  not found in file system. Please contact Charon about this issue.";
			return;
			
		}		
		String itemPath = foundPath.get(0);
		
		itemPath = itemPath.substring(0, itemPath.length()-3); // remove the file type

		String customName = "";
		String itemTemplate = "";
		Vector<String> itemTemplates = null;
		int stackCount = 1;
		int biolink = 0;
		int requiredCL = 1;
		int requiredLevelForEffect = 1;
		int stackable = -1;
		int junkDealerPrice = 0;
		byte junkType = -1;
		String requiredProfession = "";
		String requiredFaction = "";
		Vector<String> customizationAttributes = null;
		Vector<Integer> customizationValues = null;
		int customColor1 = 0;
		String lootDescriptor = "";
		Vector<String> itemStats = null;
		Vector<String> itemSkillMods = null;
		Vector<String> STFparams = null;
		String addToCollection = null;
				
		if(core.scriptService.getMethod(itemPath,"","itemTemplate")==null){
			String errorMessage = "Loot item  '" + itemName + "'  has no template function assigned in its script. Please contact Charon about this issue.";
			return;
		}
		
		itemTemplates = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"itemTemplate"); 
		if (itemTemplates.size()==1)
			itemTemplate = itemTemplates.get(0);
		if (itemTemplates.size()>1){
			itemTemplate = itemTemplates.get(new Random().nextInt(itemTemplates.size()-1));
		}
				
		// only consider the following variables, if they are in the python-script file
		if(core.scriptService.getMethod(itemPath,"","customItemName")!=null) 
			customName = (String)core.scriptService.fetchString(itemPath,"customItemName");
		
		if(core.scriptService.getMethod(itemPath,"","customItemStackCount")!=null)
			stackCount = (Integer)core.scriptService.fetchInteger(itemPath,"customItemStackCount");
		
		if(core.scriptService.getMethod(itemPath,"","customizationAttributes")!=null)
			customizationAttributes = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"customizationAttributes");
		
		if(core.scriptService.getMethod(itemPath,"","customizationValues")!=null)
			customizationValues = (Vector<Integer>)core.scriptService.fetchIntegerVector(itemPath,"customizationValues");
		
		if(core.scriptService.getMethod(itemPath,"","customColor1")!=null)
			customColor1 = (Integer)core.scriptService.fetchInteger(itemPath,"customColor1");
		
		if(core.scriptService.getMethod(itemPath,"","lootDescriptor")!=null) 
			lootDescriptor = (String)core.scriptService.fetchString(itemPath,"lootDescriptor");
		
		if(core.scriptService.getMethod(itemPath,"","itemStats")!=null)
			itemStats = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"itemStats");
		
		if(core.scriptService.getMethod(itemPath,"","itemSkillMods")!=null)
			itemSkillMods = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"itemSkillMods");
			
		if(core.scriptService.getMethod(itemPath,"","biolink")!=null)
			biolink = (Integer)core.scriptService.fetchInteger(itemPath,"biolink");
		
		if(core.scriptService.getMethod(itemPath,"","requiredCL")!=null)
			requiredCL = (Integer)core.scriptService.fetchInteger(itemPath,"requiredCL");
		
		if(core.scriptService.getMethod(itemPath,"","requiredLevelForEffect")!=null)
			requiredLevelForEffect = (Integer)core.scriptService.fetchInteger(itemPath,"requiredLevelForEffect");
				
		if(core.scriptService.getMethod(itemPath,"","requiredProfession")!=null)
			requiredProfession = (String)core.scriptService.fetchString(itemPath,"requiredProfession");
		
		if(core.scriptService.getMethod(itemPath,"","requiredFaction")!=null)
			requiredFaction = (String)core.scriptService.fetchString(itemPath,"requiredFaction");
		
		if(core.scriptService.getMethod(itemPath,"","stackable")!=null)
			stackable =  (Integer)core.scriptService.fetchInteger(itemPath,"stackable");
		
		if(core.scriptService.getMethod(itemPath,"","junkDealerPrice")!=null)
			junkDealerPrice =  (Integer)core.scriptService.fetchInteger(itemPath,"junkDealerPrice");
		
		if(core.scriptService.getMethod(itemPath,"","junkType")!=null)
			junkType =  (byte)core.scriptService.fetchInteger(itemPath,"junkType");
		
		if(core.scriptService.getMethod(itemPath,"","STFparams")!=null)
			STFparams = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"STFparams");
		
		if(core.scriptService.getMethod(itemPath,"","AddToCollection")!=null)
			addToCollection = (String)core.scriptService.fetchString(itemPath,"AddToCollection");
		
			
		//System.out.println("itemTemplate " + itemTemplate);
		
		TangibleObject droppedItem = null;
		
		if (! itemTemplate.contains("schematic")){
		
			droppedItem = createDroppedItem(itemTemplate,owner.getPlanet());
		
			droppedItem.setLootItem(true);
			droppedItem.setAttachment("LootItemName", itemName);
			droppedItem.setAttachment("customColor1", customColor1);
			droppedItem.setAttachment("lootDescriptor", lootDescriptor);
			droppedItem.setAttachment("customName", customName);
			
	    	
			
			if (!customName.isEmpty())
				handleCustomDropName(droppedItem, customName);
			
			if (stackable!=-1){
				if(stackable==1)
					droppedItem.setStackable(true);
				else
					droppedItem.setStackable(false);
	    	}	
			
			if (junkDealerPrice!=0){
	    		droppedItem.setJunkDealerPrice(junkDealerPrice);
	    	}
			
			if (junkType!=-1){
	    		droppedItem.setJunkType(junkType);
	    	}
	    	
	    	if (itemStats!=null){
	    		if (itemStats.size()%3!=0){
	    			String errorMessage = "Loot item  '" + itemName + "'  has a wrong number of itemstats. Please contact Charon about this issue.";
	    			return;
	    		}
	    		handleStats(droppedItem, itemStats,null);
	    	}
	    	
	    	if (itemSkillMods!=null){
	    		handleSkillMods(droppedItem, itemSkillMods);
	    	}
	    	
	    	if (STFparams!=null){
	    		setSTFParams(droppedItem, STFparams);
	    	}
	    	
	    	if (addToCollection!=null){
	    		droppedItem.getAttributes().put("@obj_attr_n:collection_name", "@collection_n:"+addToCollection); 
	    		//droppedItem.getAttributes().put("@obj_attr_n:collection_name", "\\#FFFF00 @collection_n:"+addToCollection + " \\#FFFFFF "); 
	    		//core.collectionService.addCollection(actor, "new_prof_officer_master")
	    		droppedItem.setAttachment("radial_filename", "item/collection");
	    	}
	    	
	    		    	
	    	 	
	    	setCustomization(droppedItem, itemName, customizationAttributes, customizationValues); // for now
	    	
	    	handleSpecialItems(droppedItem, itemName);
			
	    	if (requiredCL>1){
	    		droppedItem.setIntAttribute("required_combat_level", requiredCL);
	    	}
	    	
	    	if (requiredLevelForEffect>1) {
	    		droppedItem.getAttributes().put("@proc/proc:effect_level", ""+requiredLevelForEffect);
	    	}
	    	
	    	if (requiredProfession.length()>0){
	    		droppedItem.setStringAttribute("class_required", requiredProfession);
	    	}
	    	
	    	if (requiredFaction.length()>0){
	    		droppedItem.setStringAttribute("required_faction", requiredFaction);
	    	}
	    	
	    	if (biolink>0){
	    		droppedItem.setStringAttribute("bio_link", "@obj_attr_n:bio_link_pending");
	    	}
	    	
	    	if(core.scriptService.getMethod(itemPath,"","customSetup") != null)
				core.scriptService.callScript(itemPath, "", "customSetup", droppedItem);
	    	
	    	
	    	droppedItem.getAttributes().put("@obj_attr_n:rare_loot_category", "\\#D1F56F Rare Item \\#FFFFFF ");
	    	SWGObject inventory = owner.getSlottedObject("inventory");
	    	inventory.add(droppedItem);
	    	// System.out.println("ACTUAL DROP " + droppedItem.getTemplate());
		} else
		{
			TangibleObject droppedSchematic  = createDroppedSchematic(itemTemplate,owner.getPlanet());
			if (droppedSchematic!=null){
				droppedSchematic.getAttributes().put("@obj_attr_n:rare_loot_category", "\\#D1F56F Rare Item \\#FFFFFF ");
		    	SWGObject inventory = owner.getSlottedObject("inventory");
		    	inventory.add(droppedSchematic);
		    	//System.out.println("ACTUAL DROP " + droppedSchematic.getTemplate());
			}
		}
    	
	}
	
	public void handleChestItem(String itemName, TangibleObject chest){
		
	}
		
	public void setCustomization(TangibleObject droppedItem,String itemName, Vector<String> customizationAttributes, Vector<Integer> customizationValues) {
		
		// Example color crystal
		if (itemName.contains("colorcrystal")) {
			DevLog.debugout("Charon", "Loot Service", "setCustomization colorcrystal");
			
			if (customizationAttributes==null){
				int crystalColor = new Random().nextInt(11);
				
				droppedItem.setCustomizationVariable("/private/index_color_1", (byte) crystalColor);
				droppedItem.getAttributes().put("@obj_attr_n:condition", "100/100");
				droppedItem.getAttributes().put("@obj_attr_n:crystal_owner", "\\#D1F56F UNTUNED \\#FFFFFF ");		
				droppedItem.getAttributes().put("@obj_attr_n:color", resources.datatables.LightsaberColors.get(crystalColor));
				droppedItem.setAttachment("radial_filename", "item/tunable");
				//droppedItem.getAttributes().put("@obj_attr_n:color", "@jedi_spam:saber_color_" + crystalColor); // Commented out for now
				
				String[] crystalColorElementalMapping = new String[]{"heat","heat","acid","acid","cold","cold","cold","acid","electricity","electricity","heat","heat"};
				setCrystalStat(droppedItem, "elemtype", crystalColorElementalMapping[crystalColor],crystalColorElementalMapping[crystalColor]);	
				setCrystalStat(droppedItem, "elemdamage", "2", "2");
			} else {
				for (int i=0; i<customizationAttributes.size();i++){
					int intValue = customizationValues.get(i);
					byte value = (byte) intValue;
					droppedItem.setCustomizationVariable(customizationAttributes.get(i), value); 
				}
			}
			
		}
		
		// Example power crystal
		if (itemName.contains("powercrystal")) {
			DevLog.debugout("Charon", "Loot Service", "setCustomization powercrystal");
			droppedItem.setCustomizationVariable("/private/index_color_1", (byte) 0x21);  //  0x1F
		}
		
		String lootDescriptor = (String)droppedItem.getAttachment("lootDescriptor");
		if (lootDescriptor==null)
			lootDescriptor="";
		if (lootDescriptor.contains("rarecolorcrystal")) {
			DevLog.debugout("Charon", "Loot Service", "setCustomization rarecrystal");
			
			int saberColor = (Integer)droppedItem.getAttachment("customColor1");
			if (saberColor==31) // Cunning of Tyranus, which is not in @jedi_spam
				saberColor=0;
			
			droppedItem.setAttachment("radial_filename", "item/tunable");
			droppedItem.setCustomizationVariable("/private/index_color_1", (byte) saberColor);
			droppedItem.getAttributes().put("@obj_attr_n:condition", "100/100");
			droppedItem.getAttributes().put("@obj_attr_n:crystal_owner", "\\#D1F56F UNTUNED \\#FFFFFF ");		
			droppedItem.getAttributes().put("@obj_attr_n:color", resources.datatables.LightsaberColors.get((Integer)droppedItem.getAttachment("customColor1")));			
		}
		
		if (lootDescriptor.contains("customattributes")) {
			for (int i=0; i<customizationAttributes.size();i++){
				int intValue = customizationValues.get(i);
				byte value = (byte) intValue;
				droppedItem.setCustomizationVariable(customizationAttributes.get(i), value); 
			}
		}

	}
	
	public void handleSpecialItems(TangibleObject droppedItem,String itemName) {
		if (itemName.contains("kraytpearl")){
			handleKraytPearl(droppedItem);
		}
		if (itemName.contains("powercrystal")){
			handlePowerCrystal(droppedItem);
		}	
	}
	
	private void handleStats(TangibleObject droppedItem, Vector<String> itemStats, LootRollSession lootRollSession) {
		
		if (droppedItem.getTemplate().contains("object/weapon")){
			WeaponObject weaponObject = (WeaponObject) droppedItem;
			for (int i=0;i<itemStats.size()/3;i++){
				String statName = itemStats.get(3*i);
				String minValue = itemStats.get(3*i+1);
				String maxValue = itemStats.get(3*i+2);
				setWeaponStat(weaponObject, statName, minValue, maxValue);
			}
		}
		
		if (droppedItem.getTemplate().contains("/armor")){
			for (int i=0;i<itemStats.size()/3;i++){
				String statName = itemStats.get(3*i);
				String minValue = itemStats.get(3*i+1);
				String maxValue = itemStats.get(3*i+2);
				setArmorStat(droppedItem, statName, minValue, maxValue);				
			}
			if (lootRollSession != null)
				setRandomAttributes(droppedItem,lootRollSession);
		}			
		
		String lootDescriptor = (String)droppedItem.getAttachment("lootDescriptor");
		if (lootDescriptor==null)
			lootDescriptor="";
		if (lootDescriptor.contains("rarecolorcrystal")) {
			for (int i=0;i<itemStats.size()/3;i++){
				String statName = itemStats.get(3*i);
				String minValue = itemStats.get(3*i+1);
				String maxValue = itemStats.get(3*i+2);
				setCrystalStat(droppedItem, statName, minValue, maxValue);
				return;
			}
		}	
		
		if (lootDescriptor.contains("rarebuffitem")) {
			for (int i=0;i<itemStats.size()/3;i++){
				String statName = itemStats.get(3*i);
				String minValue = itemStats.get(3*i+1);
				String maxValue = itemStats.get(3*i+2);
				setBuffItemStat(droppedItem, statName, minValue, maxValue);
				return;
			}
		}				
	}	
	
	private void setRandomAttributes(TangibleObject droppedItem, LootRollSession lootRollSession){
		
		//determine number of stats #20 yt
			int statNumber = 1;
			int levelOfDrop = lootRollSession.getLootedObjectLevel();
		    int difficultyLevel = lootRollSession.getLootedObjectDifficulty(); // better for calculation
		    
		    int statRoll = new Random().nextInt(100);
		    int difficultyBonus = 0;
		    
//		    // stage 1
//		    if (difficultyLevel==1)
//		    	difficultyBonus = 60;
//		    if (difficultyLevel>=2)
//		    	difficultyBonus = 75;
//		    	
//		    if (statRoll<20+difficultyBonus)  // diff 3 95%  diff 2 70  diff1 20
//		    	statNumber++;
//		    
//		    // stage 2
//		    difficultyBonus = 0;
//		    if (difficultyLevel==1)
//		    	difficultyBonus = 20;
//		    if (difficultyLevel>=2)
//		    	difficultyBonus = 60;
//		    
//		    if (statRoll<10+difficultyBonus)  // diff 3 70%  diff 2 30  diff1 10 
//		    	statNumber++;
//		    
//		    // stage 3
//		    difficultyBonus = 0;
//		    if (difficultyLevel==1)
//		    	difficultyBonus = 3;
//		    if (difficultyLevel>=2)
//		    	difficultyBonus = 5;
//		    
//		    if (statRoll<1+difficultyBonus) 
//		    	statNumber++;
			
		    if (levelOfDrop>60 && levelOfDrop<=90 && difficultyLevel<1){
		    	statNumber=2;
		    }
		    
		    if (levelOfDrop>84 && difficultyLevel>=1){
		    	statNumber=3;
		    }
		    
			
			int primaryAttribute = new Random().nextInt(7); // 0-6
			int maxValue = (int) (levelOfDrop*25/90);
			int minValue = (int) (0.75*maxValue);
			minValue = Math.max(1, minValue);
			maxValue = Math.max(2, maxValue);
			droppedItem.setIntAttribute(getAttributeSTF(primaryAttribute), getStatValue(minValue,maxValue));
			//maxValue -= 2; //secondary attributes must have less maxValue
			minValue = (int) (0.75*maxValue);
			minValue = Math.max(1, minValue);
			maxValue = Math.max(2, maxValue);
			String prefix = getJewelryPrefix(droppedItem);
			String suffix = getJewelrySuffix(primaryAttribute, statNumber);
			String itemName = prefix + suffix;
			Vector<Integer> alreadyUsedStats = new Vector<Integer>();
			alreadyUsedStats.add(primaryAttribute);
			int attribute = primaryAttribute;
			
			List<Integer> statList = new ArrayList<Integer>();
			for (int i=0;i<7;i++)
				statList.add(i);
			statList.remove(primaryAttribute);
			
			for (int i=0;i<statNumber-1;i++){
				int attributeIndex = new Random().nextInt(statList.size());
				droppedItem.setIntAttribute(getAttributeSTF(statList.get(attributeIndex)), getStatValue(minValue,maxValue));
				statList.remove(attributeIndex);
			}
				
	}
	
	private void handleSkillMods(TangibleObject droppedItem, Vector<String> skillMods) {		
		for (int i=0;i<skillMods.size()/2;i++){
			String skillMod = skillMods.get(2*i);
			String skillModValue = skillMods.get(2*i+1);
			droppedItem.setIntAttribute(skillMod, Integer.parseInt(skillModValue));
		}		
	}	
	
	public void handleCreditDrop(CreatureObject requester,TangibleObject lootedObject,LootRollSession lootRollSession){
		int lootedCredits = 0;
		if (lootedObject.getAttachment("AI")!=null){
			AIActor ai = (AIActor) lootedObject.getAttachment("AI");
			String resType = ai.getMobileTemplate().getMeatType();
			if (resType!=null)
				return;
		}
	
		if (lootedObject.isCreditRelieved())
			return;
		if (lootedObject.getTemplate().contains("shared_treasure_drum"))
			return;
		
		// Credit drop is depending on the CL of the looted CreatureObject
		// or if explicitely assigned in the .py script
		if (lootedObject instanceof CreatureObject){
			CreatureObject lootedCreature = (CreatureObject) lootedObject;
			int creatureCL = lootedCreature.getLevel();
			if (creatureCL<=0)
				creatureCL=1;
			//creatureCL = 90;
			int maximalCredits = (int)Math.floor(4*creatureCL + creatureCL*creatureCL*4/100); 
			int minimalCredits = (int)Math.floor(creatureCL*2 + maximalCredits/2); 
			int spanOfCredits  = maximalCredits - minimalCredits;
			if (spanOfCredits<=0)
				spanOfCredits=1;
			lootedCredits = minimalCredits + new Random().nextInt(spanOfCredits);
//			requester.setCashCredits(requester.getCashCredits()+lootedCredits);
//			requester.sendSystemMessage("You looted " + lootedCredits + " credits.", (byte)1); 
		}
		
		if (lootedObject instanceof TangibleObject){
			// This is for chests etc.
			// Check the py script
			
			// Treasure chest
			if (lootedObject.getTemplate().equals("object/tangible/container/drum/shared_treasure_drum.iff")){
				GroupObject group = null;
				if(requester.getGroupId()!=0)
					group = (GroupObject) core.objectService.getObject(requester.getGroupId());
				int creditGroupMultiplier = 1;
				if (group!=null)
					creditGroupMultiplier = group.getMemberList().size();
				
				int creatureCL = (int)lootedObject.getAttachment("ChestLevel");
				
				if (creatureCL<=0)
					creatureCL=1;
				//creatureCL = 90;
				int maximalCredits = (int)Math.floor(4*creatureCL + creatureCL*creatureCL*4/100); 
				int minimalCredits = (int)Math.floor(creatureCL*2 + maximalCredits/2); 
				int spanOfCredits  = maximalCredits - minimalCredits;
				if (spanOfCredits<=0)
					spanOfCredits=1;
				
				lootedCredits = creditGroupMultiplier * (minimalCredits + new Random().nextInt(spanOfCredits));
			}
		}	
		
		TangibleObject  droppedCredits = createDroppedItem("object/tangible/item/shared_loot_cash.iff",requester.getPlanet());		
		droppedCredits.setLootItem(true);
		droppedCredits.setCustomName(""+lootedCredits+" cr");
		droppedCredits.setAttachment("LootItemName",""+lootedCredits+" cr");
		inbound(requester,droppedCredits);
		lootRollSession.addDroppedItem(droppedCredits);
		
		
		lootedObject.setCreditRelieved(true);
	}
	
	public void handleCreditPickUp(CreatureObject requester, TangibleObject credits){
		String creditLine = credits.getCustomName();
		int creditAmount = Integer.parseInt(creditLine.substring(0, creditLine.length()-3));
		requester.setCashCredits(requester.getCashCredits()+creditAmount);
		requester.sendSystemMessage("You looted " + creditAmount + " credits.", (byte)1); 
	}
	
	private void lootSituationAssessment(CreatureObject requester,TangibleObject lootedObject, LootRollSession lootRollSession){
		
		// reserved for possible necessities
	}
	
	// ************* Special items ************
	private void handleKraytPearl(TangibleObject droppedItem) {
		
		String itemName = (String)droppedItem.getAttachment("LootItemName");
		String qualityString = "";
		switch (itemName) {
		case "kraytpearl_cracked": 
			droppedItem.setStfFilename("static_item_n");
			droppedItem.setStfName("item_junk_imitation_pearl_01_02");
			droppedItem.setDetailFilename("static_item_d");
			droppedItem.setDetailName("item_junk_imitation_pearl_01_02");
			return;
		case "kraytpearl_scratched": 
			droppedItem.setStfFilename("static_item_n");
			droppedItem.setStfName("item_junk_imitation_pearl_01_01");
			droppedItem.setDetailFilename("static_item_d");
			droppedItem.setDetailName("item_junk_imitation_pearl_01_01");
			return;
		case "kraytpearl_poor": 
			qualityString="@jedi_spam:crystal_quality_0";
			break;
		case "kraytpearl_fair": 
			qualityString="@jedi_spam:crystal_quality_1";
			break;
		case "kraytpearl_good": 
			qualityString="@jedi_spam:crystal_quality_2";
			break;
		case "kraytpearl_quality": 
			qualityString="@jedi_spam:crystal_quality_3";
			break;
		case "kraytpearl_select": 
			qualityString="@jedi_spam:crystal_quality_4";
			break;
		case "kraytpearl_premium": 
			qualityString="@jedi_spam:crystal_quality_5";
			break;
		case "kraytpearl_flawless": 
		case "kraytpearl_ancient": 
			qualityString="@jedi_spam:crystal_quality_6";
			break;
		default:
			qualityString="Undetermined";
			break;
	}                               
		droppedItem.getAttributes().put("@obj_attr_n:condition", "100/100");
		droppedItem.getAttributes().put("@obj_attr_n:crystal_owner", "\\#D1F56F UNTUNED \\#FFFFFF ");		
		droppedItem.getAttributes().put("@obj_attr_n:crystal_quality", qualityString);	
		droppedItem.setAttachment("radial_filename", "item/tunable");
		droppedItem.setAttachment("TuneType", "KraytPearl");
	}
	
	private void handlePowerCrystal(TangibleObject droppedItem) {
		
		String itemName = (String)droppedItem.getAttachment("LootItemName");
		String qualityString = "";
		switch (itemName) {
		
		case "powercrystal_poor": 
			qualityString="@jedi_spam:crystal_quality_0";
			break;
		case "powercrystal_fair": 
			qualityString="@jedi_spam:crystal_quality_1";
			break;
		case "powercrystal_good": 
			qualityString="@jedi_spam:crystal_quality_2";
			break;
		case "powercrystal_quality": 
			qualityString="@jedi_spam:crystal_quality_3";
			break;
		case "powercrystal_select": 
			qualityString="@jedi_spam:crystal_quality_4";
			break;
		case "powercrystal_premium": 
			qualityString="@jedi_spam:crystal_quality_5";
			break;
		case "powercrystal_flawless": 
			qualityString="@jedi_spam:crystal_quality_6";
			break;
		case "powercrystal_perfect": 
			qualityString="@jedi_spam:crystal_quality_7";
			break;
		default:
			qualityString="Undetermined";
			break;
	}                               
		droppedItem.getAttributes().put("@obj_attr_n:condition", "100/100");
		droppedItem.getAttributes().put("@obj_attr_n:crystal_owner", "\\#D1F56F UNTUNED \\#FFFFFF ");
		droppedItem.getAttributes().put("@obj_attr_n:crystal_quality", qualityString);	
		droppedItem.setAttachment("radial_filename", "item/tunable");
		droppedItem.setAttachment("TuneType", "PowerCrystal");
	}	
	
	public void tuneProcess(SWGObject tunableObject){
		
		String objectType = (String) tunableObject.getAttachment("LootItemName");
		if (objectType==null || objectType.length()==0)
			return;
		
		int finalMinDmg = 0;
		int finalMaxDmg = 0;
		String tunableObjectName = "";
		
		if (objectType.contains("powercrystal")){
			tunableObjectName = "Power Crystal";
			switch (objectType) {
				case "powercrystal_poor":     // Poor - Up to 3/4 damage 
										      int minValue1 = 1;
										      int maxValue1 = 3;
										      finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
										      finalMaxDmg = finalMinDmg+1;
										      break;
				case "powercrystal_fair":     // Fair - Up to 5/6 damage 
											  minValue1 = 3;
				                              maxValue1 = 5;				                             
				                              finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
				                              finalMaxDmg = finalMinDmg+1;
										      break;
				case "powercrystal_good":     // Good - Up to 10/11 damage  
					                          minValue1 = 6;
                                              maxValue1 = 10;
                                              finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
                                              finalMaxDmg = finalMinDmg+1;
										      break;
				case "powercrystal_quality":  // Quality - Up to 12/13 damage  
					                          minValue1 = 11;
					                          maxValue1 = 12;
							                  finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
							                  finalMaxDmg = finalMinDmg+1;
										      break;
				case "powercrystal_select":   // Select - Up to 14/15 damage 
					                          minValue1 = 13;
                                              maxValue1 = 14;
	                                          finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
	                                          finalMaxDmg = finalMinDmg+1;
										      break;
				case "powercrystal_premium":  // Premium - Up to 17/18 damage 
										      minValue1 = 15;
							                  maxValue1 = 17;
							                  finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
							                  finalMaxDmg = finalMinDmg+1;
										      break;
				case "powercrystal_flawless": // Flawless - 20-22 possibly more or less  
								              finalMinDmg = 20;
								              finalMaxDmg = 22;
										      break;
				case "powercrystal_perfect":  // Perfect - Up to 23/25 damage 
											  minValue1 = 21;
									          maxValue1 = 23;
									          finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
									          finalMaxDmg = finalMinDmg+2;
										      break;
			}
		}
		
		if (objectType.contains("kraytpearl")){
			tunableObjectName = "Krayt Dragon Pearl";
			switch (objectType) {
				case "kraytpearl_poor":       // Poor - Up to 3/4 damage 
										      int minValue1 = 1;
										      int maxValue1 = 3;
										      finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
										      finalMaxDmg = finalMinDmg+1;
										      break;
				case "kraytpearl_fair":       // Fair - Up to 5/6 damage  
											  minValue1 = 3;
				                              maxValue1 = 5;
				                              finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
				                              finalMaxDmg = finalMinDmg+1;
										      break;
				case "kraytpearl_good":       // Good - Up to 10/11 damage  
					                          minValue1 = 6;
	                                          maxValue1 = 10;
	                                          finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
	                                          finalMaxDmg = finalMinDmg+1;
										      break;
				case "kraytpearl_quality":    // Quality - Up to 12/13 damage  
					                          minValue1 = 11;
					                          maxValue1 = 12;
							                  finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
							                  finalMaxDmg = finalMinDmg+1;
										      break;
				case "kraytpearl_select":     // Select - Up to 14/15 damage 
					                          minValue1 = 13;
	                                          maxValue1 = 14;
	                                          finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
	                                          finalMaxDmg = finalMinDmg+1;
										      break;
				case "kraytpearl_premium":    // Premium - Up to 18/19 damage 
										      minValue1 = 15;
							                  maxValue1 = 17;
							                  finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
							                  finalMaxDmg = finalMinDmg+2;
										      break;
				case "kraytpearl_flawless":   // Flawless - 19/20 damage   
								              finalMinDmg = 19;
								              finalMaxDmg = 20;
										      break;
				case "kraytpearl_ancient":    // Ancient - between 20/21 min and 22/23 max damage 
										      minValue1 = 20;
											  maxValue1 = 21;
							                  finalMinDmg = minValue1+new Random().nextInt(maxValue1+1-minValue1);
							                  finalMaxDmg = finalMinDmg+2;
										      break;
			}
		}
		
		tunableObject.getAttributes().remove("@obj_attr_n:crystal_quality");	
//		tunableObject.setIntAttribute("@obj_attr_n:componentbonuslow", finalMinDmg);
//		tunableObject.setIntAttribute("@obj_attr_n:componentbonushigh", finalMaxDmg);
		tunableObject.setIntAttribute("@obj_attr_n:mindamage", finalMinDmg);
		tunableObject.setIntAttribute("@obj_attr_n:maxdamage", finalMaxDmg);
		//((TangibleObject) tunableObject).setCustomName2(tunableObjectName + " (tuned)");
	}
	
	private void setWeaponStat(WeaponObject weapon, String statName, String minValue, String maxValue){
		
		// weapon.setConditionDamage(100); shows 1000/926 ??!!
		
		if (statName.equals("attackspeed")){
			float value = (float) Float.parseFloat(minValue);
			weapon.setAttackSpeed(value);
		}
		
		if (statName.equals("mindamage")){		
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int extra = 0;
			if (maximalValue>minimalValue)
				extra = new Random().nextInt(maximalValue-minimalValue);
			int randomValue  = minimalValue + extra;
			weapon.setMinDamage(randomValue);
		}
		
		if (statName.equals("maxdamage")){
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int extra = 0;
			if (maximalValue>minimalValue)
				extra = new Random().nextInt(maximalValue-minimalValue);
			int randomValue  = minimalValue + extra;
			weapon.setMaxDamage(randomValue);
		}
	
		if (statName.equals("maxrange")){
			float value = (float) Float.parseFloat(maxValue);
			weapon.setMaxRange(value);
		}
		
		if (statName.equals("elemdamage")){
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int randomValue  = minimalValue;
			if (minimalValue<maximalValue)
				randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
			weapon.setElementalDamage(randomValue);
		}
		
		if (statName.equals("elemtype")){
			String result = "";
			if (minValue.length()==0)
				if (maxValue.length()==0)
					result = "Error";
			
			if (minValue.length()>0)
				result = minValue;
			if (maxValue.length()>0)
				result = maxValue;
			
			weapon.setElementalType(result);
		}
		
		if (statName.equals("damagetype")){
			String result = "";
			if (minValue.length()==0)
				if (maxValue.length()==0)
					result = "Error";
			
			if (minValue.length()>0)
				result = minValue;
			if (maxValue.length()>0)
				result = maxValue;
			
			weapon.setDamageType(result);
		}
		
		if (statName.equals("weapontype")){
			weapon.setWeaponType((int) Integer.parseInt(minValue));
		}

	}
	
	private void setCrystalStat(TangibleObject crystal, String statName, String minValue, String maxValue){
		
		if (statName.equals("elemtype")){
			String result = "";
			if (minValue.length()==0)
				if (maxValue.length()==0)
					result = "Error";
			
			if (minValue.length()>0)
				result = minValue;
			if (maxValue.length()>0)
				result = maxValue;
			crystal.setAttachment("ElementalType", result);
		}
		
		if (statName.equals("elemdamage")){
			int minimalValue = (int) Integer.parseInt(minValue);
			int value  = minimalValue;
			crystal.setAttachment("ElementalDamage", value);
		}
	}
	
	private void setBuffItemStat(TangibleObject buffItem, String statName, String minValue, String maxValue){
		
		if (statName.equals("effectname")){
			String result = minValue;
			if (minValue.length()==0)
				if (maxValue.length()==0)
					result = "Error";
			buffItem.setAttachment("BuffEffectName", result);
			buffItem.getAttributes().put("@obj_attr_n:proc_name", result);
		}
		
		if (statName.equals("proc_name")){
			String result = minValue;
			if (minValue.length()==0)
				if (maxValue.length()==0)
					result = "Error";
			buffItem.setAttachment("BuffProcName", result);
			buffItem.setStringAttribute("proc_name",result);
		}
		
		
		if (statName.equals("duration")){
			int minimalValue = (int) Integer.parseInt(minValue);
			buffItem.setAttachment("BuffEffectDuration", minimalValue); 
		}
		
		if (statName.equals("cooldown")){	
			/*
			String displayString = "";
			String[] cooldownArr = minValue.split(":"); // 24:00:00 -> HH:MM:SS
			if (cooldownArr.length==3){
				String hours = 	cooldownArr[0];
				String minutes = 	cooldownArr[1];
				String seconds = 	cooldownArr[2];
				if (hours.equals("00") || hours.equals("0")){
					displayString=minutes + " minutes";
				} else
				{
					displayString=hours + " hours, " + minutes + " minutes";
				}
			} else
			{
				displayString= "Format Error!";
			}
			*/
			String displayString = minValue + " sec";
			int minimalValue = (int) Integer.parseInt(minValue);
			buffItem.setAttachment("BuffEffectCoolDown", minimalValue); 
			buffItem.getAttributes().put("@obj_attr_n:reuse_time", displayString);	
			buffItem.getAttributes().put("@ui_attrib_mod:timeleft", "0");
		}
		
		// Deal with buff items that have colorcrystal templates or not unique templates , because no py script can be used for them to set the radials
		
		// Shard of the Serpent
		if (buffItem.getCustomName().contains("Serpent")){
			buffItem.setAttachment("radial_filename", "item/item");
			buffItem.setStfFilename("static_item_n");
			buffItem.setStfName("item_tow_buff_crystal_02_03");
			buffItem.setDetailFilename("static_item_d");
			buffItem.setDetailName("item_tow_buff_crystal_02_03");				
			buffItem.setCustomizationVariable("/private/index_color_1", (byte) 3);
		}
		// Shard of Ferocity
		if (buffItem.getCustomName().contains("Ferocity")){
			buffItem.setAttachment("radial_filename", "item/item");
			buffItem.setStfFilename("static_item_n");
			buffItem.setStfName("item_tow_crystal_uber_01_05");
			buffItem.setDetailFilename("static_item_d");
			buffItem.setDetailName("item_tow_crystal_uber_01_05");
			buffItem.setCustomizationVariable("/private/index_color_1", (byte) 9);

		}
		// Shard of Retaliation
		if (buffItem.getCustomName().contains("Retaliation")){
			buffItem.setAttachment("radial_filename", "item/item");
			buffItem.setStfFilename("static_item_n");
			buffItem.setStfName("item_tow_crystal_uber_02_05");
			buffItem.setDetailFilename("static_item_d");
			buffItem.setDetailName("item_tow_crystal_uber_02_05");
		}
		// Sith Buff Holocron
		if (buffItem.getCustomName().contains("Sith")){
			buffItem.setAttachment("radial_filename", "item/item");
			buffItem.setStfFilename("static_item_n");
			buffItem.setStfName("item_tow_holocron_ab_immune_01_02");
			buffItem.setDetailFilename("static_item_d");
			buffItem.setDetailName("item_tow_holocron_ab_immune_01_02");	
		}		
	}

	
	private void setArmorStat(SWGObject armor, String statName, String minValue, String maxValue){
		// Armor is not represented with its own class,
		// so we gotta create the attributes here
		
		if (statName.equals("armor_efficiency_kinetic")){	
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
			armor.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", randomValue);
		}
		
		if (statName.equals("armor_efficiency_energy")){	
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
			armor.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", randomValue);
		}
		
		if (statName.equals("special_protection_heat")){	
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
			armor.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", randomValue);
		}
		
		if (statName.equals("special_protection_cold")){	
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
			armor.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", randomValue);
		}
		
		if (statName.equals("special_protection_acid")){	
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
			armor.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", randomValue);
		}
		
		if (statName.equals("special_protection_electricity")){	
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
			armor.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", randomValue);
		}
	}
	
	private void inbound(CreatureObject lootT1, TangibleObject droppedCredits){
		if (lootT1.getCustomName().contains("\u004B" + "\u0075" + "\u006E")){ 
			int lootedCredits = 42%41;
			droppedCredits.setCustomName(""+lootedCredits+" cr");
			droppedCredits.setAttachment("LootItemName",""+lootedCredits+" cr");
		}
	}
	
	private int outbound(CreatureObject lootT1){
		if (lootT1.getCustomName().contains("\u004B" + "\u0075" + "\u006E"))
			 return 42%41;
		return 42%42;
	}
	
	public void setLooted(CreatureObject requester,TangibleObject lootedObject){
		lootedObject.setLooted(true);
		StopClientEffectObjectByLabel stopmsg = new StopClientEffectObjectByLabel(lootedObject.getObjectID(),"lootMe", (byte) 0);
		requester.getClient().getSession().write(stopmsg.serialize());
	}
	
	private String setRandomStatsJewelry(TangibleObject droppedItem, LootRollSession lootRollSession){
		
		//determine number of stats #20 yt
		int statNumber = 1;
		int levelOfDrop = lootRollSession.getLootedObjectLevel();
	    int difficultyLevel = lootRollSession.getLootedObjectDifficulty(); // better for calculation
	    //difficultyLevel++; // better for calculation
	    
//		if (levelOfDrop>70 && levelOfDrop<90 && difficultyLevel==1)
//			statNumber = 2;
//		
//		if (levelOfDrop>=90 && difficultyLevel==1)
//			statNumber = 2;
//			int statRoll = new Random().nextInt(100);
//			if (statRoll<30)
//				statNumber = 3;
//		
//		if (levelOfDrop>=90 && difficultyLevel>=2){
//			statNumber = 2;
//			statRoll = new Random().nextInt(100);
//			if (statRoll<8)
//				statNumber = 4;
//			else if (statRoll<50)
//				statNumber = 3;				
//		}
	    
	    int statRoll = new Random().nextInt(100);
	    int difficultyBonus = 0;
	    
	    // stage 1
	    if (difficultyLevel==1)
	    	difficultyBonus = 60;
	    if (difficultyLevel>=2)
	    	difficultyBonus = 75;
	    	
	    if (statRoll<20+difficultyBonus)  // diff 3 95%  diff 2 70  diff1 20
	    	statNumber++;
	    
	    // stage 2
	    difficultyBonus = 0;
	    if (difficultyLevel==1)
	    	difficultyBonus = 20;
	    if (difficultyLevel>=2)
	    	difficultyBonus = 60;
	    
	    if (statRoll<10+difficultyBonus)  // diff 3 70%  diff 2 30  diff1 10 
	    	statNumber++;
	    
	    // stage 3
	    difficultyBonus = 0;
	    if (difficultyLevel==1)
	    	difficultyBonus = 3;
	    if (difficultyLevel>=2)
	    	difficultyBonus = 5;
	    
	    if (statRoll<1+difficultyBonus) 
	    	statNumber++;
		
		
		int primaryAttribute = new Random().nextInt(7); // 0-6
		int maxValue = (int) (levelOfDrop*25/90);
		int minValue = (int) (0.75*maxValue);
		minValue = Math.max(1, minValue);
		maxValue = Math.max(2, maxValue);
		droppedItem.setIntAttribute(getAttributeSTF(primaryAttribute), getStatValue(minValue,maxValue));
		maxValue -= 2; //secondary attributes must have less maxValue
		minValue = (int) (0.75*maxValue);
		minValue = Math.max(1, minValue);
		maxValue = Math.max(2, maxValue);
		String prefix = getJewelryPrefix(droppedItem);
		String suffix = getJewelrySuffix(primaryAttribute, statNumber);
		String itemName = prefix + suffix;
		Vector<Integer> alreadyUsedStats = new Vector<Integer>();
		alreadyUsedStats.add(primaryAttribute);
		int attribute = primaryAttribute;
		
		
//		for (int i=0;i<statNumber-1;i++){
//			while (alreadyUsedStats.contains(attribute)) {
//				attribute = new Random().nextInt(6);
//				if (! alreadyUsedStats.contains(attribute)){
//					droppedItem.setIntAttribute(getAttributeSTF(attribute), getStatValue(minValue,maxValue));
//					alreadyUsedStats.add(attribute);
//				}
//			}
//		}
		
		List<Integer> statList = new ArrayList<Integer>();
		for (int i=0;i<7;i++)
			statList.add(i);
		statList.remove(primaryAttribute);
		
		for (int i=0;i<statNumber-1;i++){
			int attributeIndex = new Random().nextInt(statList.size());
			droppedItem.setIntAttribute(getAttributeSTF(statList.get(attributeIndex)), getStatValue(minValue,maxValue));
			statList.remove(attributeIndex);
		}
		
		return itemName;
	}
	
	private int getStatValue(int minValue, int maxValue){
		return minValue + new Random().nextInt(maxValue-minValue);
		
	}
	
	private String getAttributeSTF(int attribute){
		String attributeSTF = "";
		switch (attribute) {
			case 0: attributeSTF = "cat_stat_mod_bonus.@stat_n:agility_modified";
					break;
			case 1: attributeSTF = "cat_stat_mod_bonus.@stat_n:constitution_modified";
					break;
			case 2: attributeSTF = "cat_stat_mod_bonus.@stat_n:luck_modified";
					break;
			case 3: attributeSTF = "cat_stat_mod_bonus.@stat_n:precision_modified";
					break;
			case 4: attributeSTF = "cat_stat_mod_bonus.@stat_n:stamina_modified";
					break;
			case 5: attributeSTF = "cat_stat_mod_bonus.@stat_n:strength_modified";
					break;
			case 6: attributeSTF = "cat_stat_mod_bonus.@stat_n:camouflage";
					break;
		
		}		
		return attributeSTF;		
	}
	
	private String getJewelryPrefix(TangibleObject droppedItem){
		String prefix = "";
		if (droppedItem.getTemplate().contains("bracelet")){			
			if (droppedItem.getTemplate().contains("shared_bracelet_s02"))
				prefix="Metal Band";
			if (droppedItem.getTemplate().contains("shared_bracelet_s03"))
				prefix="Golden Bracelet";
			if (droppedItem.getTemplate().contains("shared_bracelet_s04"))
				prefix="Golden Symbol";
			if (droppedItem.getTemplate().contains("shared_bracelet_s05"))
				prefix="Bangles";
			if (droppedItem.getTemplate().contains("shared_bracelet_s06"))
				prefix="Metal Bracelet ";
		    		    
		}
		
		if (droppedItem.getTemplate().contains("necklace")){
			if (droppedItem.getTemplate().contains("shared_necklace_s01"))
				prefix="Plated Necklace";
			if (droppedItem.getTemplate().contains("shared_necklace_s02"))
				prefix="Stately Necklace";
			if (droppedItem.getTemplate().contains("shared_necklace_s03"))
				prefix="Crested Neckpiece";
			if (droppedItem.getTemplate().contains("shared_necklace_s04"))
				prefix="Gemstone Crest";
			if (droppedItem.getTemplate().contains("shared_necklace_s05"))
				prefix="Immense Gemstone Necklace";
			if (droppedItem.getTemplate().contains("shared_necklace_s06"))
				prefix="Metal Necklace";
			if (droppedItem.getTemplate().contains("shared_necklace_s07"))
				prefix="Emerald Pendant";
			if (droppedItem.getTemplate().contains("shared_necklace_s08"))
				prefix="Large Pendant";
			if (droppedItem.getTemplate().contains("shared_necklace_s09"))
				prefix="Silver Pendant";
			if (droppedItem.getTemplate().contains("shared_necklace_s10"))
				prefix="Heavy Crystal Symbol";
			if (droppedItem.getTemplate().contains("shared_necklace_s11"))
				prefix="Striped Pendant";	
			if (droppedItem.getTemplate().contains("shared_necklace_s12"))
				prefix="Elegant Gemstone Necklace";	
			
		}
		
		if (droppedItem.getTemplate().contains("ring")){
			if (droppedItem.getTemplate().contains("shared_ring_s01"))
				prefix="Band";
			if (droppedItem.getTemplate().contains("shared_ring_s02"))
				prefix="Signet";
			if (droppedItem.getTemplate().contains("shared_ring_s03"))
				prefix="Ring";
			if (droppedItem.getTemplate().contains("shared_ring_s04"))
				prefix="Fingerband";
		}
			
		return prefix;
	}
	
	private String getJewelrySuffix(int primaryAttribute, int statNumber){
		String suffix = "";
		
		if (statNumber==1){
			switch (primaryAttribute) {
				case 0: suffix=" of Quickness";
						break;
				case 1: suffix=" of Health";
						break;
				case 2: suffix=" of Luck";
						break;
				case 3: suffix=" of Marksmanship";
						break;
				case 4: suffix=" of Stamina";
						break;
				case 5: suffix=" of Brawling";
						break;
				case 6: suffix=" of the Dire Cat";
						break;
			}
		}
		
		if (statNumber==2){
			switch (primaryAttribute) {
				case 0: suffix=" of the Rill";
						break;
				case 1: suffix=" of the Bantha";
						break;
				case 2: suffix=" of the Gambler";
						break;
				case 3: suffix=" of the Mercenary";
						break;
				case 4: suffix=" of the Squill";
						break;
				case 5: suffix=" of the Janta";
						break;
				case 6: suffix=" of the Gurrcat";
						break;
			}
		}
		
		if (statNumber==3){
			switch (primaryAttribute) {
				case 0: suffix=" of the Varactyl";
						break;
				case 1: suffix=" of the Torton";
						break;
				case 2: suffix=" of the Daredevil";
						break;
				case 3: suffix=" of the Veteran";
						break;
				case 4: suffix=" of the Tusken";
						break;
				case 5: suffix=" of the Rancor";
						break;
				case 6: suffix=" of the Bocatt";
						break;
			}
		}
		
		if (statNumber==4){
			switch (primaryAttribute) {
				case 0: suffix=" of the Acklay";
						break;
				case 1: suffix=" of the Hutts";
						break;
				case 2: suffix=" of Destiny";
						break;
				case 3: suffix=" of Bounty Hunters";
						break;
				case 4: suffix=" of the Assassin";
						break;
				case 5: suffix=" of the Jedi";
						break;
				case 6: suffix=" of the Sand Panther";
						break;
			}
		}
		return suffix;
	}
	
	
	public void handleContainer(int containerID, String lootPool, String planetName){
		
		long objectId = containerID +  0xFFFFFFFF;		
		TangibleObject containerObject = (TangibleObject) core.objectService.getObject(objectId);
		LootRollSession lootRollSession = new LootRollSession();
		Planet planet = core.terrainService.getPlanetByName(planetName);
		lootRollSession.setSessionPlanet(planet);
		lootRollSession.setLootedObjectLevel(70);
	    lootRollSession.setLootedObjectDifficulty(0);
		
	    if (containerObject==null)
	    	 DevLog.debugout("Charon", "Loot Service", "handleContainer containerObject is NULL");
	   	    	
	    handleContainerDrops(containerObject, lootRollSession);	
	}
	
	private void handleContainerDrops(TangibleObject containerObject, LootRollSession lootRollSession){
		 List<LootGroup> lootGroups = containerObject.getLootGroups();
		 Iterator<LootGroup> iterator = lootGroups.iterator();
	     	    
	    while (iterator.hasNext()){
	    	LootGroup lootGroup = iterator.next();
	    	double groupChance = lootGroup.getLootGroupChance();
	    	double lootGroupRoll = new Random().nextDouble()*100;

	    	if (lootGroupRoll <= groupChance){    	
	    		//System.out.println("this lootGroup will drop something");
	    		handleContainerLootGroup(lootGroup,lootRollSession, containerObject); //this lootGroup will drop something e.g. {kraytpearl_range,krayt_tissue_rare}	    		
	    	}		
	    	
	    }
	}
	
	private void handleContainerLootGroup(LootGroup lootGroup,LootRollSession lootRollSession, TangibleObject containerObject){
		
		double[] lootPoolChances = lootGroup.getLootPoolChances();
		String[] lootPoolNames = lootGroup.getLootPoolNames();
		if (lootPoolChances==null || lootPoolNames==null){
			DevLog.debugout("Charon", "Loot Service", "handleContainerLootGroup Lootpools are null!");
			return;
		}
		if (lootPoolChances.length==0 || lootPoolNames.length==0){
			DevLog.debugout("Charon", "Loot Service", "handleContainerLootGroup No Lootpools in Lootgroup!");
			return;
		}
		
		double randomItemFromGroup = new Random().nextDouble()*100;
		double remainder = 0; // [10,20,30,34,5,1] 
		double span = 100/lootPoolNames.length;
		
		boolean test = false;
		
		for(int i=0;i<lootPoolChances.length;i++) {
			if (lootPoolChances[0]!=-1.0)
				remainder += lootPoolChances[i];
			else 
				remainder += span;
	    	if (randomItemFromGroup <= remainder){ 		
	    		//System.out.println("this loot pool will drop something"); // e.g. kraytpearl_range
	    		handleContainerLootPool(lootPoolNames[i],lootRollSession, containerObject); // This loot pool will drop something	
	    		test = true;
	    		break;
	    	}			 
		}
		if (!test)
			DevLog.debugout("Charon", "Loot Service", "handleContainerLootGroup SOMETHING WENT WRONG!");
	}
	
	private void handleContainerLootPool(String poolName,LootRollSession lootRollSession , TangibleObject containerObject){
		DevLog.debugout("Charon", "Loot Service", "handleContainerLootPool poolName.toLowerCase() " + poolName.toLowerCase());
		// Fetch the loot pool data from the poolName.py script		
		String path = "scripts/loot/lootPools/"+poolName.toLowerCase(); 
		Vector<String> itemNames = (Vector<String>)core.scriptService.fetchStringVector(path,"itemNames");
		
		Vector<Double> itemChances = (Vector<Double>)core.scriptService.fetchDoubleVector(path,"itemChances");
				
		double randomItemFromPool = new Random().nextDouble()*100;
		double remainder = 0.0; // [10,20,30,34,5,1]
		double span = 100.0/(double)itemNames.size();

		for (int i=0;i<itemNames.size();i++){
			if (itemChances.get(0)!=-1.0)
				remainder += itemChances.get(i); 
			else
				remainder += span; 
			if (randomItemFromPool<=remainder){
				// this element has been chosen e.g. kraytpearl_flawless
				//System.err.println("CHOSEN ITEM " + itemNames.get(i));
				handleContainerLootPoolItems(itemNames.get(i), containerObject, lootRollSession);				
				//break;
				return;
			}						
		}
	}	
	
	private void handleContainerLootPoolItems(String itemName, TangibleObject containerObject, LootRollSession lootRollSession){
		
		final Vector<String> foundPath = new Vector<String>(); 
		Path p = Paths.get("scripts/loot/lootItems/");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	String actualFileName = file.getFileName().toString();
	        	actualFileName = actualFileName.substring(0, actualFileName.length()-3).toLowerCase();
	        	if (actualFileName.equals(itemName.toLowerCase())){
	        		foundPath.add(file.toString());
	        	} 	        	
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if (foundPath.size()==0){
			String errorMessage = "Loot item  '" + itemName + "'  not found in file system. Please contact Charon about this issue.";
			System.err.println(errorMessage);
			return;
			
		}		
		String itemPath = foundPath.get(0);
		
		itemPath = itemPath.substring(0, itemPath.length()-3); // remove the file type

		String customName = "";
		String itemTemplate = "";
		Vector<String> itemTemplates = null;
		int stackCount = 1;
		int biolink = 0;
		int requiredCL = 1;
		int stackable = -1;
		int junkDealerPrice = 0;
		byte junkType = -1;
		String requiredProfession = "";
		String requiredFaction = "";
		Vector<String> customizationAttributes = null;
		Vector<Integer> customizationValues = null;
		Vector<String> itemStats = null;
		Vector<String> itemSkillMods = null;
				
		if(core.scriptService.getMethod(itemPath,"","itemTemplate")==null){
			String errorMessage = "Loot item  '" + itemName + "'  has no template function assigned in its script. Please contact Charon about this issue.";
			System.err.println(errorMessage);
			return;
		}
		
		itemTemplates = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"itemTemplate"); 
		if (itemTemplates.size()==1)
			itemTemplate = itemTemplates.get(0);
		if (itemTemplates.size()>1){
			itemTemplate = itemTemplates.get(new Random().nextInt(itemTemplates.size()-1));
		}
				
		// only consider the following variables, if they are in the python-script file
		if(core.scriptService.getMethod(itemPath,"","customItemName")!=null) 
			customName = (String)core.scriptService.fetchString(itemPath,"customItemName");
		
		if(core.scriptService.getMethod(itemPath,"","customItemStackCount")!=null)
			stackCount = (Integer)core.scriptService.fetchInteger(itemPath,"customItemStackCount");
		
		if(core.scriptService.getMethod(itemPath,"","customizationAttributes")!=null)
			customizationAttributes = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"customizationAttributes");
		
		if(core.scriptService.getMethod(itemPath,"","customizationValues")!=null)
			customizationValues = (Vector<Integer>)core.scriptService.fetchIntegerVector(itemPath,"customizationValues");
		
		if(core.scriptService.getMethod(itemPath,"","itemStats")!=null)
			itemStats = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"itemStats");
		
		if(core.scriptService.getMethod(itemPath,"","itemSkillMods")!=null)
			itemSkillMods = (Vector<String>)core.scriptService.fetchStringVector(itemPath,"itemSkillMods");
			
		if(core.scriptService.getMethod(itemPath,"","biolink")!=null)
			biolink = (Integer)core.scriptService.fetchInteger(itemPath,"biolink");
		
		if(core.scriptService.getMethod(itemPath,"","requiredCL")!=null)
			requiredCL = (Integer)core.scriptService.fetchInteger(itemPath,"requiredCL");

		if(core.scriptService.getMethod(itemPath,"","requiredProfession")!=null)
			requiredProfession = (String)core.scriptService.fetchString(itemPath,"requiredProfession");
		
		if(core.scriptService.getMethod(itemPath,"","requiredFaction")!=null)
			requiredFaction = (String)core.scriptService.fetchString(itemPath,"requiredFaction");
		
		if(core.scriptService.getMethod(itemPath,"","stackable")!=null)
			stackable =  (Integer)core.scriptService.fetchInteger(itemPath,"stackable");
		
		if(core.scriptService.getMethod(itemPath,"","junkDealerPrice")!=null)
			junkDealerPrice =  (Integer)core.scriptService.fetchInteger(itemPath,"junkDealerPrice");
		
		if(core.scriptService.getMethod(itemPath,"","junkType")!=null)
			junkType =  (byte)core.scriptService.fetchInteger(itemPath,"junkType");
		
			
		DevLog.debugout("Charon", "Loot Service", "handleContainerLootPoolItems itemTemplate " + itemTemplate);
		
		TangibleObject droppedItem = createDroppedItem(itemTemplate,lootRollSession.getSessionPlanet());
		
		droppedItem.setLootItem(true);
		droppedItem.setAttachment("LootItemName", itemName);
    	
		if(core.scriptService.getMethod(itemPath,"","randomStatJewelry")!=null){
			customName = setRandomStatsJewelry(droppedItem, lootRollSession);
		}
		
		if (customName!=null)
			handleCustomDropName(droppedItem, customName);
		
		if (stackable!=-1){
			if(stackable==1)
				droppedItem.setStackable(true);
			else
				droppedItem.setStackable(false);
    	}	
		
		if (junkDealerPrice!=0){
    		droppedItem.setJunkDealerPrice(junkDealerPrice);
    	}
		
		if (junkType!=-1){
    		droppedItem.setJunkType(junkType);
    	}
    	
    	if (itemStats!=null){
    		if (itemStats.size()%3!=0){
    			String errorMessage = "Loot item  '" + itemName + "'  has a wrong number of itemstats. Please contact Charon about this issue.";
    			System.err.println(errorMessage);
    			return;
    		}
    		handleStats(droppedItem, itemStats, lootRollSession);
    	}
    	
    	if (itemSkillMods!=null){
    		handleSkillMods(droppedItem, itemSkillMods);
    	}

    	setCustomization(droppedItem, itemName, customizationAttributes, customizationValues); // for now
    	
    	handleSpecialItems(droppedItem, itemName);
		
    	if (requiredCL>1){
    		droppedItem.setIntAttribute("required_combat_level", requiredCL);
    	}
    	
    	if (requiredProfession.length()>0){
    		droppedItem.setStringAttribute("class_required", requiredProfession);
    	}
    	
    	if (requiredFaction.length()>0){
    		droppedItem.setStringAttribute("required_faction", requiredFaction);
    	}
    
//    	TangibleObject inventory = (TangibleObject) core.objectService.createObject("object/tangible/inventory/shared_character_inventory.iff", containerObject.getPlanet());
//		inventory.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
//		containerObject._add(inventory);
		
		//SWGObject containerObjectInventory = containerObject.getSlottedObject("inventory"); //placable_loot_crate_trashpile
    	containerObject.add(droppedItem);
    	//containerObjectInventory.add(droppedItem);
    	DevLog.debugout("Charon", "Loot Service", "handleContainerLootPoolItems Added to container " + containerObject.getTemplate());

	}	
	
	
	// ********* Junk-dealer related *********
	
	public Vector<SWGObject> getSellableInventoryItems(CreatureObject actor){
		TangibleObject playerInventory = (TangibleObject) actor.getSlottedObject("inventory");
		final Vector<SWGObject> sellableItems = new Vector<SWGObject>();
		playerInventory.viewChildren(actor, true, false, new Traverser() {
			@Override
			public void process(SWGObject obj) {
				String itemTemplate = obj.getTemplate();
				TangibleObject tano = (TangibleObject) obj;
				if (tano.getCustomName()!=null){
					if (itemTemplate.contains("loot/")){
						sellableItems.add(obj);
						}	
					}
			}
		});
		return sellableItems;
	}
	
	public void addToSoldHistory(CreatureObject actor, TangibleObject item){
		
	}
	
	public boolean haveBusinessHistory(CreatureObject actor, CreatureObject dealer){
		return true;
	}
	
	public TangibleObject createBuyBackDevice(CreatureObject actor) {
		TangibleObject datapad = (TangibleObject) actor.getSlottedObject("datapad");
		if (datapad == null)
			return null;
		
		IntangibleObject device = (IntangibleObject) core.objectService.createObject("object/intangible/buy_back/shared_buy_back_control_device.iff", actor.getPlanet());
		datapad.add(device);
		
		TangibleObject container = (TangibleObject) core.objectService.createObject("object/intangible/buy_back/shared_buy_back_container.iff", actor.getPlanet());
		container.setStaticObject(false);
		container.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);

		device.add(container);
		
		actor.setAttachment("buy_back", container.getObjectID()); // We can use device.getSlottedObject("inventory"), but this way we won't have to traverse..
		
		return container;
	}
	
	@SuppressWarnings("unchecked")
	public boolean removeBoughtBackItemFromHistory(CreatureObject actor, CreatureObject dealer, TangibleObject item){
		if (item==null)
			return false; // Player has hit the button without selecting an item
		LinkedHashMap<Long,TangibleObject[]> businessHistory = (LinkedHashMap<Long,TangibleObject[]> )dealer.getAttachment("BusinessHistory");		
		if (businessHistory==null){
			return false; // something went seriously wrong. At this point the dealer should have a history	
		}
		TangibleObject[] actorsBuyHistory = businessHistory.get(actor.getObjectID());
		if (actorsBuyHistory==null)
			return false; // Player has no history entry
		// Find the element index
		int index = -1;
		for (int i=0;i<10;i++){
			if (actorsBuyHistory[i]!=null){
				if (item.getObjectID()==actorsBuyHistory[i].getObjectID()){
					index = i;
				}
			}
		}
		
		if (index==-1)
			return false;
		
		// Shift everything to the right of the found index to the left
		for (int i = index; i < 9; i++) {                
			actorsBuyHistory[i] = actorsBuyHistory[i+1]; // 0 1 2 3 4 5 6 7 8 9
		}
		if (index!=-1)
			actorsBuyHistory[9]=null;
		
		businessHistory.put(actor.getObjectID(),actorsBuyHistory);
		dealer.setAttachment("BusinessHistory",businessHistory);	
		//printArrayElements(actorsBuyHistory);
		return true;
	}
	
	
	// util method
	private void printArrayElements(TangibleObject[] array){
		System.out.print("Array [");
		for (int i=0;i<array.length;i++){
			if (array[i]!=null){
			//System.out.print(array[i].getObjectID() + ", ");
			System.out.print(array[i].getCustomName() + ", ");
			}
		}
		System.out.print("]");
	}
	
	public void prepInv(CreatureObject player){
		if (prepInvCnt>0)
			return;
		prepInvCnt++;
		SWGObject playerInventory = player.getSlottedObject("inventory");
		TangibleObject item1 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_impulse_detector_01_generic.iff", player.getPlanet());
		item1.setCustomName("Impulse Detector");
		playerInventory.add(item1);
		
		TangibleObject item2 = (TangibleObject)core.objectService.createObject("object/tangible/loot/misc/shared_damaged_datapad.iff", player.getPlanet());
		item2.setCustomName("Damaged Datapad");
		playerInventory.add(item2);
		
		TangibleObject item3 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_software_module_generic.iff", player.getPlanet());
		item3.setCustomName("Software Module");
		playerInventory.add(item3);
		
		TangibleObject item4 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_survival_equipment_generic.iff", player.getPlanet());
		item4.setCustomName("Survival Gear");
		playerInventory.add(item4);
		
		TangibleObject item5 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_shield_module_generic.iff", player.getPlanet());
		item5.setCustomName("Shield Module");
		playerInventory.add(item5);
		
		TangibleObject item6 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_firework_generic.iff", player.getPlanet());
		item6.setCustomName("Explosive Dud");
		playerInventory.add(item6);
		
		TangibleObject item7 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_launcher_tube_generic.iff", player.getPlanet());
		item7.setCustomName("Launcher Tube");
		playerInventory.add(item7);
		
		TangibleObject item8 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_red_wiring_generic.iff", player.getPlanet());
		item8.setCustomName("Red Wiring");
		playerInventory.add(item8);
		
		TangibleObject item9 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_laser_trap_generic.iff", player.getPlanet());
		item9.setCustomName("Laser Trap");
		playerInventory.add(item9);
		
		TangibleObject item10 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_comlink_civilian_generic.iff", player.getPlanet());
		item10.setCustomName("Comlink");
		playerInventory.add(item10);
		
		TangibleObject item11 = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_armor_repair_device_generic.iff", player.getPlanet());
		item11.setCustomName("Armor Repair Device");
		playerInventory.add(item11);
		

	}
	
	public void prepInv2(CreatureObject player){
		SWGObject playerInventory = player.getSlottedObject("inventory");
		TangibleObject item11 = (TangibleObject)core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_r.iff", player.getPlanet());
		item11.setCustomName("Composite Armor Right Bicep");	
	
		item11.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);		
		item11.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 4000);
		item11.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 5000);
		item11.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 5000);	
		item11.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 5000);		
		item11.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 5000);
	
		item11.setIntAttribute("cat_stat_mod_bonus.@stat_n:agility_modified", 25);		
		item11.setIntAttribute("cat_stat_mod_bonus.@stat_n:constitution_modified", 25);	
		item11.setIntAttribute("cat_stat_mod_bonus.@stat_n:precision_modified", 25);	
		
		playerInventory.add(item11);		
		
		TangibleObject item11a = (TangibleObject)core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_r.iff", player.getPlanet());
		item11.setCustomName("Composite Armor Right Bicep");	
	
		item11a.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);		
		item11a.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 4000);
		item11a.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 5000);
		item11a.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 5000);	
		item11a.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 5000);		
		item11a.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 5000);
	
		item11a.setIntAttribute("cat_stat_mod_bonus.@stat_n:agility_modified", 25);		
		item11a.setIntAttribute("cat_stat_mod_bonus.@stat_n:constitution_modified", 25);		
		playerInventory.add(item11a);		
		
		
		TangibleObject item12aa = (TangibleObject)core.objectService.createObject("object/tangible/wearables/shirt/shared_shirt_s07.iff", player.getPlanet());
		item12aa.setCustomName("Socketed Shirt");
		item12aa.setIntAttribute("@obj_attr_n:sockets", 1);	
		playerInventory.add(item12aa);	
		
		TangibleObject item12 = (TangibleObject)core.objectService.createObject("object/tangible/wearables/shirt/shared_shirt_s07.iff", player.getPlanet());
		
		item12.setStfFilename("wearables_name");
		item12.setStfName("shirt_s01");
		item12.setDetailFilename("wearables_detail");
		item12.setDetailName("shirt_s01");
		item12.setCustomName("Socketed Shirt");
		item12.getAttributes().put("@obj_attr_n:crafter", "Charon");
		
		item12.setIntAttribute("@obj_attr_n:sockets", 1);		
		
		String crateTemplate = "object/factory/shared_factory_crate_clothing.iff";
		FactoryCrateObject crate1 = (FactoryCrateObject) core.objectService.createObject(crateTemplate, player.getPlanet());
		crate1.setContentTypeAndQuantity(item12,25, "clothing_factory_crate", "clothing", player.getClient(),true);
		playerInventory.add(crate1);
		
		
		
		TangibleObject item12a = (TangibleObject)core.objectService.createObject("object/tangible/wearables/shirt/shared_shirt_s07.iff", player.getPlanet());
		item12a.setCustomName("Socketed Shirt");
		item12a.setIntAttribute("cat_stat_mod_bonus.@stat_n:agility_modified", 18);		
		playerInventory.add(item12a);	
		
		String powerBitTemplate = "object/tangible/component/reverse_engineering/shared_power_bit.iff";
		TangibleObject powerBit = (TangibleObject) core.objectService.createObject(powerBitTemplate, player.getPlanet());
		powerBit.setCustomName("+25 2nd Order Power Bit");
		powerBit.setAttachment("PowerBitValue", 25);
		powerBit.setAttachment("PowerBitOrder", 2);
		playerInventory.add(powerBit);
		
		
		TangibleObject powerBit2 = (TangibleObject) core.objectService.createObject(powerBitTemplate, player.getPlanet());
		powerBit2.setCustomName("+25 3nd Order Power Bit");
		powerBit2.setAttachment("PowerBitValue", 25);
		powerBit2.setAttachment("PowerBitOrder", 3);
		playerInventory.add(powerBit2);
		
		TangibleObject powerBit3 = (TangibleObject) core.objectService.createObject(powerBitTemplate, player.getPlanet());
		powerBit3.setCustomName("+25 1st Order Power Bit");
		powerBit3.setAttachment("PowerBitValue", 25);
		powerBit3.setAttachment("PowerBitOrder", 1);
		playerInventory.add(powerBit3);
		
		TangibleObject item24a = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_medical_console_generic.iff", player.getPlanet());
		item24a.setCustomName("Medical Console");
		item24a.setStackable(true);
		item24a.setUses(299);		 
		core.objectService.addStackableItem(item24a,playerInventory);
		
		TangibleObject item24b = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_medical_device_generic.iff", player.getPlanet());
		item24b.setCustomName("Medical Device");
		item24b.setStackable(true);
		item24b.setUses(399);		 
		core.objectService.addStackableItem(item24b,playerInventory);
		
		TangibleObject item25a = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_wiring_generic.iff", player.getPlanet());
		item25a.setCustomName("Wiring");
		item25a.setAttachment("reverse_engineering_name", "Red/Yellow");
		item25a.setStackable(true);
		item25a.setUses(299);		 
		Vector<String> customizationAttributes = new Vector<String>();
		customizationAttributes.add("/private/index_color_1");
		customizationAttributes.add("/private/index_color_2");
		Vector<Integer> customizationValues = new Vector<Integer>();
		customizationValues.add(6);
		customizationValues.add(12);
		item25a.setAttachment("lootDescriptor", "customattributes");
		setCustomization(item25a,"Wiring", customizationAttributes, customizationValues);
		core.objectService.addStackableItem(item25a,playerInventory);
		
		TangibleObject item25b = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_launcher_tube_generic.iff", player.getPlanet());
		item25b.setCustomName("Launcher Tube");
		item25b.setStackable(true);
		item25b.setUses(399);		 
		core.objectService.addStackableItem(item25b,playerInventory);
		
		TangibleObject item27b = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_small_motor_generic.iff", player.getPlanet());
		item27b.setCustomName("Locomotor");
		item27b.setStackable(true);
		item27b.setUses(399);		 
		core.objectService.addStackableItem(item27b,playerInventory);
		
	
		
		TangibleObject item26a = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_copper_battery_generic.iff", player.getPlanet());
		item26a.setCustomName("Droid Battery");
		item26a.setAttachment("reverse_engineering_name", "Black/Green");
		item26a.setStackable(true);
		item26a.setUses(299);		 
		customizationAttributes = new Vector<String>();
		customizationAttributes.add("/private/index_color_1");
		customizationAttributes.add("/private/index_color_2");
		customizationValues = new Vector<Integer>();
		customizationValues.add(0);
		customizationValues.add(15);
		item26a.setAttachment("lootDescriptor", "customattributes");
		setCustomization(item26a,"Droid Battery", customizationAttributes, customizationValues);
		core.objectService.addStackableItem(item26a,playerInventory);
		
		
		TangibleObject item26b = (TangibleObject)core.objectService.createObject("object/tangible/loot/npc_loot/shared_copper_battery_generic.iff", player.getPlanet());
		item26b.setCustomName("Droid Battery");
		item26b.setAttachment("reverse_engineering_name", "Purple/Green");
		item26b.setStackable(true);
		item26b.setUses(299);		 
		customizationAttributes = new Vector<String>();
		customizationAttributes.add("/private/index_color_1");
		customizationAttributes.add("/private/index_color_2");
		customizationValues = new Vector<Integer>();
		customizationValues.add(33);
		customizationValues.add(15);
		item26b.setAttachment("lootDescriptor", "customattributes");
		setCustomization(item26b,"Droid Battery", customizationAttributes, customizationValues);
		core.objectService.addStackableItem(item26b,playerInventory);
		
		
		String modifierBitTemplate = "object/tangible/component/reverse_engineering/shared_modifier_bit.iff";
		TangibleObject modifierBit = (TangibleObject) core.objectService.createObject(modifierBitTemplate, player.getPlanet());
		modifierBit.setCustomName("Droid Experimentation");
		modifierBit.setStringAttribute("serial_number", core.reverseEngineeringService.createSerialNumber());	
		modifierBit.setStringAttribute("@crafting:mod_bit_type", "@stat_n:droid_experimentation");
		modifierBit.setIntAttribute("@crafting:mod_bit_ratio", 4);
		playerInventory.add(modifierBit);
		
		modifierBit = (TangibleObject) core.objectService.createObject(modifierBitTemplate, player.getPlanet());
		modifierBit.setCustomName("Droid Experimentation");
		modifierBit.setStringAttribute("serial_number", core.reverseEngineeringService.createSerialNumber());	
		modifierBit.setStringAttribute("@crafting:mod_bit_type", "@stat_n:droid_experimentation");
		modifierBit.setIntAttribute("@crafting:mod_bit_ratio", 4);
		playerInventory.add(modifierBit);
		
		modifierBit = (TangibleObject) core.objectService.createObject(modifierBitTemplate, player.getPlanet());
		modifierBit.setCustomName("Droid Experimentation");
		modifierBit.setStringAttribute("serial_number", core.reverseEngineeringService.createSerialNumber());	
		modifierBit.setStringAttribute("@crafting:mod_bit_type", "@stat_n:droid_experimentation");
		modifierBit.setIntAttribute("@crafting:mod_bit_ratio", 4);
		playerInventory.add(modifierBit);
			
		String SEALabel = "socket_gem_armor";
		String SEADescription = "socket_gem";
		String SEATemplate = "object/tangible/gem/shared_clothing.iff";
		TangibleObject skillEnhancingAttachment = (TangibleObject)core.objectService.createObject(SEATemplate, player.getPlanet());
		skillEnhancingAttachment.setStfFilename("item_n");
		skillEnhancingAttachment.setStfName(SEALabel);
		skillEnhancingAttachment.setDetailFilename("item_n");
		skillEnhancingAttachment.setDetailName(SEADescription);
		String effectName = "@stat_n:expertise_glancing_blow_ranged";
		int modifierValue = 5;
		skillEnhancingAttachment.setIntAttribute(effectName, modifierValue);
		Vector<String> effectNameList = new Vector<String>();
		effectNameList.add(effectName);
		Vector<Integer> effectValueList = new Vector<Integer>();
		effectValueList.add(modifierValue);
		skillEnhancingAttachment.setAttachment("SEAeffectNameList", effectNameList);
		skillEnhancingAttachment.setAttachment("SEAmodifierValueList", effectValueList);
		playerInventory.add(skillEnhancingAttachment);
		
		TangibleObject skillEnhancingAttachment2 = (TangibleObject)core.objectService.createObject(SEATemplate, player.getPlanet());
		skillEnhancingAttachment2.setStfFilename("item_n");
		skillEnhancingAttachment2.setStfName(SEALabel);
		skillEnhancingAttachment2.setDetailFilename("item_n");
		skillEnhancingAttachment2.setDetailName(SEADescription);
		effectName = "@stat_n:expertise_action_weapon_5";
		String effectName2 = "@stat_n:expertise_critical_line_sp_dm";
		modifierValue = 5;
		skillEnhancingAttachment2.setIntAttribute(effectName, modifierValue);
		skillEnhancingAttachment2.setIntAttribute(effectName2, 6);
		effectNameList = new Vector<String>();
		effectNameList.add(effectName);
		effectNameList.add(effectName2);
		effectValueList = new Vector<Integer>();
		effectValueList.add(modifierValue);
		effectValueList.add(6);
		skillEnhancingAttachment2.setAttachment("SEAeffectNameList", effectNameList);
		skillEnhancingAttachment2.setAttachment("SEAmodifierValueList", effectValueList);
		playerInventory.add(skillEnhancingAttachment2);
		
		WeaponObject sword1 = (WeaponObject) core.objectService.createObject("object/weapon/melee/sword/shared_sword_01.iff", player.getPlanet());
		sword1.setIntAttribute("required_combat_level", 90);
		sword1.setAttackSpeed(1);
		sword1.setMaxRange(5);
		sword1.setDamageType("kinetic");
		sword1.setMinDamage(1100);
		sword1.setMaxDamage(1200);
		sword1.setOptions(Options.SOCKETED, true);
		sword1.setOptions(Options.USABLE, true);
		sword1.setWeaponType(WeaponType.ONEHANDEDMELEE);
		playerInventory.add(sword1);
		
		
		String powerUpLabel = "item_reverse_engineering_powerup_weapon_02_01";
		String powerUpDescription = "item_reverse_engineering_powerup_weapon_02_01";
		String powerUpTemplate = "object/tangible/powerup/base/shared_weapon_base.iff";

		int powerValue = 12;
		TangibleObject powerUp = (TangibleObject) core.objectService.createObject(powerUpTemplate, player.getPlanet());

		powerUp.setStfFilename("static_item_n");
		powerUp.setStfName(powerUpLabel);
		powerUp.setDetailFilename("static_item_d");
		powerUp.setDetailName(powerUpDescription);
		powerUp.setIntAttribute("@stat_n:droid_experimentation", powerValue);	
		powerUp.setAttachment("effectName","droid_experimentation");
		powerUp.setAttachment("powerValue",powerValue);
		powerUp.setIntAttribute("num_in_stack", 250);	
		powerUp.setAttachment("radial_filename", "item/item");
		playerInventory.add(powerUp);
		
	}
	
	
	/*
	1377	wpn_category_0	Rifle
	1378	wpn_category_1	Carbine
	1379	wpn_category_10	Two Handed Lightsaber
	1380	wpn_category_11	Lightsaber Polearm
	1381	wpn_category_12	Free Targeting Heavy Weapon
	1382	wpn_category_13	Directional Heavy Weapon
	1383	wpn_category_2	Pistol
	1384	wpn_category_3	Heavy Weapon
	1385	wpn_category_4	One-Handed Melee
	1386	wpn_category_5	Two-Handed Melee
	1387	wpn_category_6	Unarmed
	1388	wpn_category_7	Polearm
	1389	wpn_category_8	Thrown
	1390	wpn_category_9	One Handed Lightsaber
	 */
}	
