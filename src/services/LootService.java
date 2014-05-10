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

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;

import protocol.swg.PlayClientEffectObjectTransformMessage;
import resources.loot.LootGroup;
import resources.loot.LootRollSession;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import main.NGECore;
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

public class LootService implements INetworkDispatch {
	
	private NGECore core;
	private static int prepInvCnt = 0;
	
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
		System.out.println("handleLootRequest ");
		// security check
		if (hasAccess(requester,lootedObject) && ! lootedObject.isLooted()){
			LootRollSession lootRollSession = (LootRollSession )lootedObject.getAttachment("LootSession");
			if (lootRollSession.getDroppedItems().size()==0){
				System.err.println("lootRollSession.getDroppedItems().size()==0");
				return;			
			}
			SWGObject lootedObjectInventory = lootedObject.getSlottedObject("inventory");
			core.simulationService.openContainer(requester, lootedObjectInventory);	
			setLooted(requester,lootedObject);
		}
	}

	private boolean hasAccess(CreatureObject requester, TangibleObject lootedObject){
		LootRollSession lootRollSession = (LootRollSession )lootedObject.getAttachment("LootSession");
		if (lootRollSession==null)
			System.err.println("LootSession null: " + lootRollSession);
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
	    		System.out.println("this lootGroup will drop something");
	    		handleLootGroup(lootGroup,lootRollSession); //this lootGroup will drop something e.g. {kraytpearl_range,krayt_tissue_rare}	    		
	    	}		
	    	System.out.println("While Loop Stuck check");
	    }
	    System.out.println("Past while ");
	    
	    // Rare Loot System Stage (Is in place for all looted creatures)
//if (lootRollSession.isAllowRareLoot()){
//	int randomRareLoot = new Random().nextInt(100);
//	int chanceRequirement = 1; 
//	if (lootRollSession.isIncreasedRLSChance())
//		chanceRequirement+=3; // RLS chance is at 4% for groupsize >= 4
//	if (randomRareLoot <= chanceRequirement){ 
//		handleRareLootChest(lootRollSession);
//	}
//}
		
	    // set info above corpse
	    System.out.println("lootedObject instanceof CreatureObject " + (lootedObject instanceof CreatureObject));
	    if (lootedObject instanceof CreatureObject){
		    float y = 0.5F; // 1.3356977F
		    float qz= 1.06535322E9F;
		    Point3D effectorPosition = new Point3D(0,y,0);
			Quaternion effectorOrientation = new Quaternion(0,0,0,qz);
		    PlayClientEffectObjectTransformMessage lmsg = new PlayClientEffectObjectTransformMessage("appearance/pt_loot_disc.prt",lootedObject.getObjectID(),"lootMe",effectorPosition,effectorOrientation);
		    requester.getClient().getSession().write(lmsg.serialize());
		    tools.CharonPacketUtils.printAnalysis(lmsg.serialize());  
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
	    System.out.println("lootRollSession.getDroppedItems() " + (lootRollSession.getDroppedItems()));
    	for (TangibleObject droppedItem : lootRollSession.getDroppedItems()){		    
    		
    		//droppedItem.setAttachment("radial_filename", "lootitem");
    		lootedObjectInventory.add(droppedItem);
    		  		
    		// RLS chest effect
	    	if (droppedItem.getAttachment("LootItemName").toString().contains("Loot Chest")){
	    		requester.playEffectObject("clienteffect/level_granted.cef", "");
	    	}
    	}
    	
    	// register session in service
    	lootedObject.setAttachment("LootSession", lootRollSession);
    	    	
    	//lootedObject.setLooted(true); 
	    // ToDo: Group loot settings etc.  actual loot chance was lootgroupchance*lootchance    
	}
	
	
	private void handleLootGroup(LootGroup lootGroup,LootRollSession lootRollSession){
		
		double[] lootPoolChances = lootGroup.getLootPoolChances();
		String[] lootPoolNames = lootGroup.getLootPoolNames();
		if (lootPoolChances==null || lootPoolNames==null){
			System.err.println("Lootpools are null!");
			return;
		}
		if (lootPoolChances.length==0 || lootPoolNames.length==0){
			System.err.println("No Lootpools in Lootgroup!");
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
	    		System.out.println("this loot pool will drop something"); // e.g. kraytpearl_range
	    		handleLootPool(lootPoolNames[i],lootRollSession); // This loot pool will drop something	
	    		test = true;
	    		break;
	    	}			 
		}
		if (!test)
			System.err.println("SOMETHING WENT WRONG!");
	}
		
	private void handleLootPool(String poolName,LootRollSession lootRollSession){
		System.err.println("poolName.toLowerCase() " + poolName.toLowerCase());
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
	
	@SuppressWarnings("unused")
	private void handleLootPoolItems(String itemName,LootRollSession lootRollSession){
		
		final Vector<String> foundPath = new Vector<String>(); 
		Path p = Paths.get("scripts/loot/lootItems/");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	String actualFileName = file.getFileName().toString();
	        	actualFileName = actualFileName.substring(0, actualFileName.length()-3);
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
		Vector<String> itemStats = null;
		Vector<String> itemSkillMods = null;
				
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
		
			
		System.out.println("itemTemplate " + itemTemplate);
		
		TangibleObject droppedItem = createDroppedItem(itemTemplate,lootRollSession.getSessionPlanet());
		
		droppedItem.setLootItem(true);
		droppedItem.setAttachment("LootItemName", itemName);
    	
		if(core.scriptService.getMethod(itemPath,"","randomStatJewelry")!=null){
			customName = setRandomStatsJewelry(droppedItem, lootRollSession);
		}
		
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
    			lootRollSession.addErrorMessage(errorMessage);
    			return;
    		}
    		handleStats(droppedItem, itemStats);
    	}
    	
    	if (itemSkillMods!=null){
    		handleSkillMods(droppedItem, itemSkillMods);
    	}
    	
    	
    	
//    	if (customizationValues!=null)
//    		setCustomization(droppedItem, itemName);
    	
    	setCustomization(droppedItem, itemName); // for now
    	
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
		System.out.println("END REACHED");
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
    	System.out.println("droppedItem " + droppedItem);
    	return droppedItem;
	}
	
	private void handleRareLootChest(LootRollSession lootRollSession){
		
		TangibleObject droppedItem = null;
		
		int legendaryRoll = new Random().nextInt(100);
		int exceptionalRoll = new Random().nextInt(100);
		int chancemodifier = 0;
		if (lootRollSession.isIncreasedRLSChance())
			chancemodifier += 15;
		
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
			fillLegendaryChest(droppedItem);
			
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
			fillExceptionalChest(droppedItem);
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
			fillRareChest(droppedItem);
		}

		lootRollSession.addDroppedItem(droppedItem);
	}
	
	private void fillLegendaryChest(TangibleObject droppedItem){
		
	}
	
	private void fillExceptionalChest(TangibleObject droppedItem){
		
	}

	private void fillRareChest(TangibleObject droppedItem){
		
	}
		
	public void setCustomization(TangibleObject droppedItem,String itemName) {
		
		// Example color crystal
		if (itemName.contains("colorcrystal")) {
			System.out.println("colorcrystal");
			
			int crystalColor = new Random().nextInt(11);
			
			droppedItem.setCustomizationVariable("/private/index_color_1", (byte) crystalColor);
			droppedItem.getAttributes().put("@obj_attr_n:condition", "100/100");
			droppedItem.getAttributes().put("@obj_attr_n:crystal_owner", "\\#D1F56F UNTUNED \\#FFFFFF ");		
			droppedItem.getAttributes().put("@obj_attr_n:color", resources.datatables.LightsaberColors.get(crystalColor));
			droppedItem.setAttachment("radial_filename", "item/tunable");
			//droppedItem.getAttributes().put("@obj_attr_n:color", "@jedi_spam:saber_color_" + crystalColor); // Commented out for now
			
		}
		
		// Example power crystal
		if (itemName.contains("powercrystal")) {
			System.out.println("powercrystal");
			droppedItem.setCustomizationVariable("/private/index_color_1", (byte) 0x21);  //  0x1F
		}
		
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
	
	public void handleSpecialItems(TangibleObject droppedItem,String itemName) {
		if (itemName.contains("kraytpearl")){
			handleKraytPearl(droppedItem);
		}
		if (itemName.contains("powercrystal")){
			handlePowerCrystal(droppedItem);
		}	
	}
	
	private void handleStats(TangibleObject droppedItem, Vector<String> itemStats) {
		
		if (droppedItem.getTemplate().contains("/weapon")){
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
		if (lootedObject.isCreditRelieved())
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
									          finalMaxDmg = finalMinDmg+1;
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
							                  finalMaxDmg = finalMinDmg+1;
										      break;
				case "kraytpearl_flawless":   // Flawless - 19/20 damage   
								              finalMinDmg = 19;
								              finalMaxDmg = 20;
										      break;
				case "kraytpearl_ancient":    // Ancient - 20/22 damage 
											  finalMinDmg = 20;
		                                      finalMaxDmg = 22;
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
			int randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
			weapon.setMinDamage(randomValue);
		}
		
		if (statName.equals("maxdamage")){
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
			weapon.setMaxDamage(randomValue);
		}
	
		if (statName.equals("maxrange")){
			float value = (float) Float.parseFloat(maxValue);
			weapon.setMaxRange(value);
		}
		
		if (statName.equals("elemdamage")){
			int minimalValue = (int) Integer.parseInt(minValue);
			int maximalValue = (int) Integer.parseInt(maxValue);
			int randomValue  = minimalValue + new Random().nextInt(maximalValue-minimalValue);
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
	
	public void setLooted(TangibleObject lootedObject,TangibleObject requester){
		lootedObject.setLooted(true);
		float y = -5.0F; 
	    float qz= 1.06535322E9F;
	    Point3D effectorPosition = new Point3D(0,y,0);
		Quaternion effectorOrientation = new Quaternion(0,0,0,qz);
	    PlayClientEffectObjectTransformMessage lmsg = new PlayClientEffectObjectTransformMessage("",lootedObject.getObjectID(),"",effectorPosition,effectorOrientation);
	    //((CreatureObject) requester).getClient().getSession().write(lmsg.serialize());
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
	
	
	// ********* Junk-dealer related *********
	
	public Vector<SWGObject> getSellableInventoryItems(CreatureObject actor){
		TangibleObject playerInventory = (TangibleObject) actor.getSlottedObject("inventory");
		final Vector<SWGObject> sellableItems = new Vector<SWGObject>();
		playerInventory.viewChildren(actor, false, false, new Traverser() {
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
	
	@SuppressWarnings("unchecked")
	public Vector<TangibleObject> getBuyHistory(CreatureObject actor, CreatureObject dealer){			
		LinkedHashMap<Long,TangibleObject[]> businessHistory = (LinkedHashMap<Long,TangibleObject[]> )dealer.getAttachment("BusinessHistory");		
		if (businessHistory==null){
			businessHistory = new LinkedHashMap<Long,TangibleObject[]>();	
			dealer.setAttachment("BusinessHistory",businessHistory);		
		}
		
		TangibleObject[] actorsBuyHistory = businessHistory.get(actor.getObjectID());
		if (actorsBuyHistory==null)
			return new Vector<TangibleObject>();
		
		Vector<TangibleObject> actorHistory = new Vector<TangibleObject>();
		for (TangibleObject item : actorsBuyHistory){
			if (item!=null)
				actorHistory.add(item);
		}
		return actorHistory;
	}
	
	@SuppressWarnings("unchecked")
	public boolean addToBuyHistory(CreatureObject actor, CreatureObject dealer, TangibleObject item){			
		LinkedHashMap<Long,TangibleObject[]> businessHistory = (LinkedHashMap<Long,TangibleObject[]> )dealer.getAttachment("BusinessHistory");		
		if (businessHistory==null){
			businessHistory = new LinkedHashMap<Long,TangibleObject[]>();		
			dealer.setAttachment("BusinessHistory",businessHistory);		
		}

		TangibleObject[] actorsBuyHistory = businessHistory.get(actor.getObjectID());
		if (actorsBuyHistory==null){
			actorsBuyHistory = new TangibleObject[10];
			actorsBuyHistory[0] = item;
		} else {
			// Check Array for zeros
			int lastObject=0;
			for (int i=0;i<actorsBuyHistory.length;i++){
				if (actorsBuyHistory[i]!=null)
					lastObject=i;
			}
			if (lastObject==actorsBuyHistory.length-1){
				//>>shift right and add
				for (int i = 8; i >= 0; i--) {                
					actorsBuyHistory[i+1] = actorsBuyHistory[i];
				}
				actorsBuyHistory[0] = item;
			} else {
				actorsBuyHistory[lastObject+1] = item;
			}	
		}		
		businessHistory.put(actor.getObjectID(),actorsBuyHistory);
		dealer.setAttachment("BusinessHistory",businessHistory);			
		//printArrayElements(actorsBuyHistory);
		return true;
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
	@SuppressWarnings("unused")
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
