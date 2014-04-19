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
package services.housing;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import main.NGECore;
import protocol.swg.EnterStructurePlacementModeMessage;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.deed.Player_House_Deed;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import services.sui.SUIWindow;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class HousingService implements INetworkDispatch {
	
	private NGECore core;
	private Map<String, HouseTemplate> housingTemplates = new ConcurrentHashMap<String, HouseTemplate>();

	public HousingService(NGECore core) {
		this.core = core;
		core.commandService.registerCommand("placestructure");
		core.commandService.registerCommand("movefurniture");
		core.commandService.registerCommand("rotatefurniture");
	}
	
	public void enterStructureMode(CreatureObject actor, TangibleObject deed)
	{	
		if(!actor.getClient().isGM() && !core.terrainService.canBuildAtPosition(actor, actor.getWorldPosition().x, actor.getWorldPosition().z))
		{
			actor.sendSystemMessage("You may not place a structure here.", (byte) 0); // should probably load this from an stf
			return;
		}
		
		if(housingTemplates.containsKey(deed.getTemplate()))
		{
			HouseTemplate houseTemplate = housingTemplates.get(deed.getTemplate());
			EnterStructurePlacementModeMessage packet = new EnterStructurePlacementModeMessage(deed, houseTemplate.getBuildingTemplate());	
			actor.getClient().getSession().write(packet.serialize());
		}
	}
	
	public void placeStructure(final CreatureObject actor, TangibleObject deed, float positionX, float positionZ, float rotation)
	{
		HouseTemplate houseTemplate = housingTemplates.get(deed.getTemplate());
		Player_House_Deed playerHourseDeed = (Player_House_Deed)core.objectService.getObject(deed.getObjectID());
		int structureLotCost = houseTemplate.getLotCost();
		String structureTemplate = houseTemplate.getBuildingTemplate();
		
		if(!houseTemplate.canBePlacedOn(actor.getPlanet().getName()))
		{
			actor.sendSystemMessage("You may not place this structure on this planet.", (byte) 0); // should probably load this from an stf
			return;
		}
		
		if(!actor.getClient().isGM() && !core.terrainService.canBuildAtPosition(actor, positionX, positionZ))
		{
			actor.sendSystemMessage("You may not place a structure here.", (byte) 0); // should probably load this from an stf
			return;
		}
		
		// Lot stuff
		if(!actor.getPlayerObject().deductLots(structureLotCost))
		{
			actor.sendSystemMessage("You do not have enough available lots to place this structure.", (byte) 0); // should probably load this from an stf
			return;
		}
		
		// Calculate our orientation and height
		Quaternion quaternion = new Quaternion(1, 0, 0, 0);
		quaternion = resources.common.MathUtilities.rotateQuaternion(quaternion, (float)((Math.PI/2) * rotation), new Point3D(0, 1, 0));
		
		float positionY = core.terrainService.getHeight(actor.getPlanetId(), positionX, positionZ) + 2f;
		
		// Create the building
		BuildingObject building = (BuildingObject) core.objectService.createObject(structureTemplate, 0, actor.getPlanet(), new Point3D(positionX, positionY, positionZ), quaternion);
		core.simulationService.add(building, building.getPosition().x, building.getPosition().z, true);
		
		// Name the sign
		TangibleObject sign = (TangibleObject) building.getAttachment("structureSign");	
		String playerFirstName = actor.getCustomName().split(" ")[0];
		sign.setCustomName2(playerFirstName + "'s House");
		//building.add(sign);

		core.objectService.destroyObject(deed);
		
		// Structure management
		Vector<Long> admins = new Vector<>();
		admins.add(actor.getObjectID());
		
		building.setAttachment("structureOwner", actor.getObjectID());
		building.setAttachment("structureAdmins", admins);
		building.setDeedTemplate(deed.getTemplate());
		building.setBMR(playerHourseDeed.getBMR());
		building.setConditionDamage(100);
		
		// Save structure to DB
		//building.createTransaction(core.getBuildingODB().getEnvironment());
		//core.getBuildingODB().put(building, Long.class, BuildingObject.class, building.getTransaction());
		//building.getTransaction().commitSync();
	}
	
	@SuppressWarnings("unchecked")
	public boolean getPermissions(SWGObject player, SWGObject container)
	{
		if(((Vector<Long>) container.getContainer().getAttachment("structureAdmins")).contains(player.getObjectID())) return true;
		return false;	
	}
		
	public void addHousingTemplate(HouseTemplate houseTemplate)
	{
		housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate);
	}
	
	public void loadHousingTemplates() {
	    Path p = Paths.get("scripts/houses/");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() 
	    {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
	        {
	        	System.out.println("Loading housing template " + file.getFileName());
	        	core.scriptService.callScript("scripts/houses/", file.getFileName().toString().replace(".py", ""), "setup", core);
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try 
        {
			Files.walkFileTree(p, fv);
		} 
        catch (IOException e) { e.printStackTrace(); }
	}
	
	public void createDestroySUIPage(final SWGObject owner, final TangibleObject target) {
		
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		//final BuildingObject building = (BuildingObject) core.objectService.getObject(target.getParentId());
		// harvester.getStfFilename(); installation_n .getTemplate();
		
		if (building.getItemsList().size()>0){
			((CreatureObject) owner).sendSystemMessage("@player_structure:clear_building_for_delete", (byte)1);
		}
		
		String displayname = "@installation_n:"+building.getStfName();
		if (building.getCustomName()!=null)
			displayname = building.getCustomName();
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", displayname);
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:confirm_destruction_d1 " +
												    "@player_structure:confirm_destruction_d2 "  +
												    "\n \n @player_structure:confirm_destruction_d3a " +
												    "\\#32CD32 @player_structure:confirm_destruction_d3b \\#FFFFFF " +
												    "@player_structure:confirm_destruction_d4 ");
		if (building.getConditionDamage()<20 && building.getMaintenanceAmount()<3000){
			window.addListBoxMenuItem("@player_structure:redeed_confirmation \\#BB0000 @player_structure:can_redeed_no_suffix \\#FFFFFF ",1 );
		} else {
			window.addListBoxMenuItem("@player_structure:redeed_confirmation \\#32CD32 @player_structure:can_redeed_yes_suffix \\#FFFFFF ",1 );
		}
		if (building.getConditionDamage()<20){
			window.addListBoxMenuItem("@player_structure:redeed_condition \\#BB0000 " + building.getConditionDamage() + " \\#FFFFFF ",1 );
		} else {
			window.addListBoxMenuItem("@player_structure:redeed_condition \\#32CD32 " + building.getConditionDamage() + " \\#FFFFFF ",1 );
		}
		if (building.getMaintenanceAmount()<0){
			window.addListBoxMenuItem("@player_structure:redeed_maintenance \\#BB0000 " + (int)building.getMaintenanceAmount() + " \\#FFFFFF ",2 );
		} else {
			window.addListBoxMenuItem("@player_structure:redeed_maintenance \\#32CD32  " + (int)building.getMaintenanceAmount() + " \\#FFFFFF ",2 );
		}
		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@yes");
		window.setProperty("btnCancel:Text", "@no");				
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");	
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
				createCodeWindow(owner, target);
			}					
		});		
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});	
		core.suiService.openSUIWindow(window);
	}
	
	public void createCodeWindow(SWGObject owner, TangibleObject target) {
		
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		//final BuildingObject building = (BuildingObject)target;
		Random rnd = new Random();
		final int confirmCode = 100000 + rnd.nextInt(900000);
		final SUIWindow window = core.suiService.createInputBox(2,"@player_structure:structure_status","@player_structure:structure_name_prompt", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:confirm_destruction_t");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:your_structure_prefix " +
													"\\#32CD32 @player_structure:will_redeed_confirm \\#FFFFFF "+
												    "@player_structure:will_redeed_suffix "  +
												    "\n \n Code: " + confirmCode);
		
		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@yes");
		window.setProperty("btnCancel:Text", "@no");				
		Vector<String> returnList = new Vector<String>();
		returnList.add("txtInput:LocalText");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				CreatureObject crafter = (CreatureObject)owner;
				core.suiService.closeSUIWindow(owner, 0);
				if (returnList.get(0).equals(""+confirmCode)){
					// handle creation of correct deed in player inventory
					PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
					String deedTemplate = building.getDeedTemplate(); 
	
					Player_House_Deed deed = (Player_House_Deed)core.objectService.createObject(deedTemplate, owner.getPlanet());
					if(player.getLotsRemaining()+deed.getLotRequirement()>10){
						// Something went wrong or hacking attempt
						crafter.sendSystemMessage("Structure can't be redeeded. Maximum lot count exceeded.",(byte)1);
						return;
					}
							
					deed.setStructureTemplate(building.getTemplate());					
					deed.setSurplusMaintenance((int)building.getMaintenanceAmount());					
					deed.setAttributes();
					
					core.objectService.destroyObject(building.getObjectID());
 
					SWGObject ownerInventory = owner.getSlottedObject("inventory");
					ownerInventory.add(deed);
					
					if(player.getLotsRemaining()+deed.getLotRequirement()<=10)
						player.setLotsRemaining(player.getLotsRemaining()+deed.getLotRequirement());
					
					crafter.sendSystemMessage("@player_structure:processing_destruction",(byte)1);
					crafter.sendSystemMessage("@player_structure:deed_reclaimed",(byte)1);
					
				} else {
					crafter.sendSystemMessage("@player_structure:incorrect_destroy_code",(byte)1);
				}
					
			}					
		});		
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	public void createPayMaintenanceSUIPage(SWGObject owner, TangibleObject target) {
		CreatureObject creature = (CreatureObject) owner;
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		final SUIWindow window = core.suiService.createSUIWindow("Script.transfer", owner, target, 10);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:select_amount");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:select_maint_amount" +
													"\n \n @player_structure:current_maint_pool : " + (int)building.getMaintenanceAmount());	
				
		window.setProperty("msgPayMaintenance", "transaction.txtInputFrom");
		
		window.setProperty("transaction.lblFrom:Text", "@player_structure:total_funds");
		window.setProperty("transaction.lblTo:Text", "@player_structure:to_pay");		
		window.setProperty("transaction.lblFrom", "@player_structure:total_funds");
		window.setProperty("transaction.lblTo", "@player_structure:to_pay");	
				
		window.setProperty("transaction.lblStartingFrom:Text", ""+creature.getCashCredits());
		window.setProperty("transaction.lblStartingTo:Text", "0");
				
		window.setProperty("transaction:InputFrom", "555555");
		window.setProperty("transaction:InputTo", "666666");
		
		window.setProperty("transaction:txtInputFrom", ""+creature.getCashCredits());
		window.setProperty("transaction:txtInputTo", "1");
		window.setProperty("transaction.txtInputFrom:Text", ""+creature.getCashCredits());
		window.setProperty("transaction.txtInputTo:Text", "" + "0");
		
		window.setProperty("transaction.ConversionRatioFrom", "1");
		window.setProperty("transaction.ConversionRatioTo", "0");
			
		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@ok");
		window.setProperty("btnCancel:Text", "@cancel");				

		Vector<String> returnList = new Vector<String>();
		returnList.add("transaction.txtInputFrom:Text");
		returnList.add("transaction.txtInputTo:Text");
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				CreatureObject crafter = (CreatureObject)owner;
				crafter.setCashCredits(crafter.getCashCredits() - Integer.parseInt(returnList.get(1)));
				building.setMaintenanceAmount(building.getMaintenanceAmount()+Float.parseFloat(returnList.get(1)));
				String displayname = "the structure";
				if (building.getCustomName()!=null)
					displayname = building.getCustomName();
				crafter.sendSystemMessage("You successfully make a payment of " + Integer.parseInt(returnList.get(1)) + " credits to " + displayname + ".", (byte) 0);
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	public void createRenameSUIPage(SWGObject owner, TangibleObject target) {
		final SUIWindow window = core.suiService.createInputBox(2,"@player_structure:structure_status","@player_structure:structure_name_prompt", owner, target, 0);		
		Vector<String> returnList = new Vector<String>();
		returnList.add("txtInput:LocalText");	
		final TangibleObject outerSurveyTool = target;
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.housingService.handleSetName((CreatureObject)owner, (TangibleObject)outerSurveyTool,returnList.get(0));
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	public void handleSetName(CreatureObject owner, TangibleObject target,String name) {
		
		((BuildingObject) target).setBuildingName(name,owner);		
	}
	
	public void createStatusSUIPage(SWGObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		String displayname = "@installation_n:"+building.getStfName();
		if (building.getCustomName()!=null)
			displayname = building.getCustomName();
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:structure_status_t");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:structure_name_prompt" + " " + displayname);
		String ownerName = owner.getCustomName();
		if (ownerName.length()>0){
			String[] helper = ownerName.split(" ");
			ownerName = helper[0];
		}

		String maintenancePool_string = ""+(int)building.getMaintenanceAmount();
		int hourlyMaintenance = building.getBMR();
		float totalNumberOfHours = (float)building.getMaintenanceAmount()/hourlyMaintenance;
		float minuteFraction = ((totalNumberOfHours * 100) % 100) / 100;
		int nDays = (int)totalNumberOfHours / 24;
		float difference = totalNumberOfHours % 24; 
		int nHours = (int)difference;
		int nMinutes = (int)(minuteFraction *60);		
		maintenancePool_string += " (" + nDays + " days, " + nHours + " hours, " + nMinutes + " minutes)";
				
		window.addListBoxMenuItem("@player_structure:owner_prompt" + " " + ownerName, 0);
		if (building.getPrivacy()==BuildingObject.PRIVATE)
			window.addListBoxMenuItem("@player_structure:structure_private", 1);
		else
			window.addListBoxMenuItem("@player_structure:structure_public", 1);
		window.addListBoxMenuItem("@player_structure:condition_prompt" + " " + target.getConditionDamage()+"%", 2);	
		window.addListBoxMenuItem("@player_structure:maintenance_pool_prompt " + maintenancePool_string, 3);	
		window.addListBoxMenuItem("@player_structure:maintenance_rate_prompt " + building.getBMR() + " cr/h", 4); // @player_structure:credits_per_hour	
		window.addListBoxMenuItem("@player_structure:maintenance_mods_prompt", 5);
		window.addListBoxMenuItem("@player_structure:items_in_building_prompt " + building.getItemsList().size(), 6);
		window.addListBoxMenuItem("@player_structure:total_house_storage " + building.getMaximumStorageCapacity(), 7);

		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@ok");
		window.setProperty("btnCancel:Text", "@cancel");				
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	public void declareResidency(SWGObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		building.setResidency((CreatureObject)owner);
		//owner.setResidence();
	}
	
	public void handleListAllItems(SWGObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		String displayname = "@installation_n:"+building.getStfName();
		if (building.getCustomName()!=null)
			displayname = building.getCustomName();
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:find_items_find_all_house_items");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:find_items_prompt");
		
		Vector<TangibleObject> itemList = building.getItemsList();

		for (int i=0;i<itemList.size();i++){
			String itemName = (itemList.get(i).getCustomName() != null) ? itemList.get(i).getCustomName() : "@" + itemList.get(i).getStfFilename() + ":" + itemList.get(i).getStfName();
			window.addListBoxMenuItem(itemName, i);
		}		

		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@ok");
		window.setProperty("btnCancel:Text", "@cancel");
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);

		
	}
	
	public void handleDeleteAllItems(SWGObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		//building.getItemsList().clear();
		// confirmation needed
		confirmDeleteAllItems(owner, target);
	}
	
	public void confirmDeleteAllItems(SWGObject owner, TangibleObject target) {
		
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		
		Random rnd = new Random();
		final int confirmCode = 100000 + rnd.nextInt(900000);
		final SUIWindow window = core.suiService.createInputBox(2,"@player_structure:structure_status","@player_structure:structure_name_prompt", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:confirm_destruction_t");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:delete_all_items_d " +
												    "@player_structure:delete_all_items_prompt "  +
												    "\n \n Code: " + confirmCode);
			
		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@yes");
		window.setProperty("btnCancel:Text", "@no");				
		Vector<String> returnList = new Vector<String>();
		returnList.add("txtInput:LocalText");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {
				CreatureObject ownerC = (CreatureObject)owner;
				core.suiService.closeSUIWindow(owner, 0);
				if (returnList.get(0).equals(""+confirmCode)){
					confirmDeleteAllItems2ndStage(ownerC,building);
				} else {
					ownerC.sendSystemMessage("@player_structure:incorrect_destroy_code",(byte)1);
				}
									
			}					
		});		
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	public void confirmDeleteAllItems2ndStage(final CreatureObject ownerC, BuildingObject building) {
		
		final SUIWindow window = core.suiService.createMessageBox(2,"@player_structure:structure_status","@player_structure:structure_name_prompt", ownerC, building, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:confirm_destruction_t");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:delete_all_items_second_d");
			
		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@yes");
		window.setProperty("btnCancel:Text", "@no");				
		Vector<String> returnList = new Vector<String>();
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {
				for (TangibleObject del : building.getItemsList()){
					core.objectService.destroyObject(del);
				}
				ownerC.sendSystemMessage("@player_structure:items_deleted",(byte)1);
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	public void handleFindLostItems(SWGObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		String displayname = "@installation_n:"+building.getStfName();
		if (building.getCustomName()!=null)
			displayname = building.getCustomName();
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:find_items_find_all_house_items");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:structure_name_prompt" + " " + displayname);
		
		Vector<TangibleObject> itemList = building.getItemsList();
		final Map<Integer,Long> itemIDMapping = new HashMap<Integer,Long>();
		
		for (int i=0;i<itemList.size();i++){
			window.addListBoxMenuItem("#"+(i+1)+": " + itemList.get(i).getCustomName(), i);
			itemIDMapping.put(i, itemList.get(i).getObjectID());
		}		

		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@ok");
		window.setProperty("btnCancel:Text", "@cancel");
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				
				int index = Integer.parseInt(returnList.get(0));
				long foundItemID = (long) itemIDMapping.get(index);	
				TangibleObject foundItem = (TangibleObject) core.objectService.getObject(foundItemID);
				//core.simulationService.transform(foundItem,owner.getPosition());
				foundItem.setPosition(owner.getPosition());
				core.suiService.closeSUIWindow(owner, 0);
			}
		});
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});	
		core.suiService.openSUIWindow(window);

		
	}
	
	public void handleSearchForItems(SWGObject owner, TangibleObject target) {
		// Spawning the structure terminal outside makes it display the correct radial
		core.staticService.spawnObject("object/tangible/terminal/shared_terminal_player_structure.iff", "tatooine", 0L, 3525.0F, 4.0F, -4800.0F, 0.70F, 0.71F);
		// I assume that childobject does not get a radial somehow
		
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		final SUIWindow window = core.suiService.createInputBox(2,"@player_structure:structure_status","@player_structure:structure_name_prompt", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:find_items_search_keyword_title");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:find_items_search_keyword_prompt");
		
		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@ok");
		window.setProperty("btnCancel:Text", "@cancel");
		Vector<String> returnList = new Vector<String>();
		returnList.add("txtInput:LocalText");
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				
				String itemSearchName = (String) returnList.get(0);
				Vector<TangibleObject> allItems = building.getItemsList();
				Vector<TangibleObject> foundItems = new Vector<TangibleObject>();
				
				for (TangibleObject obj : allItems){
					String searchAttribute=obj.getCustomName();
					if (searchAttribute==null)
						searchAttribute=obj.getTemplate();
					if (searchAttribute.toLowerCase().contains(itemSearchName.toLowerCase())){
						foundItems.add(obj);
					}
				}
				if (foundItems.size()>0){
					((CreatureObject) owner).sendSystemMessage("@player_structure:find_items_search_list_title", (byte) 1);
					displayFoundItems(owner, target, foundItems);
				} else {
					((CreatureObject) owner).sendSystemMessage("@player_structure:find_items_search_not_found", (byte) 1);
				}
				core.suiService.closeSUIWindow(owner, 0);
			}
		});
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});	
		core.suiService.openSUIWindow(window);		
	}
	
	public void displayFoundItems(SWGObject owner, TangibleObject target, Vector<TangibleObject> foundItems) {
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:find_items_search_keyword_title");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:find_items_search_list_prompt");
		
		Vector<TangibleObject> itemList = building.getItemsList();
			
		final Map<Integer,Long> itemIDMapping = new HashMap<Integer,Long>();
		
		for (int i=0;i<foundItems.size();i++){
			window.addListBoxMenuItem("#"+(i+1)+": " + foundItems.get(i).getTemplate(), i);
			itemIDMapping.put(i, itemList.get(i).getObjectID());
			if (i==0){
				((CreatureObject)owner).setLookAtTarget(itemList.get(i).getObjectID());
				((CreatureObject)owner).setTargetId(itemList.get(i).getObjectID());
				((CreatureObject)owner).setIntendedTarget(itemList.get(i).getObjectID());
			}
		}		

		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@ok");
		window.setProperty("btnCancel:Text", "@cancel");
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {						
				core.suiService.closeSUIWindow(owner, 0);
			}
		});
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});	
		core.suiService.openSUIWindow(window);	
	}
	
	public void handlePermissionEntry(CreatureObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		String listName = "ENTRY";
		building.setPermissionEntry(listName,owner);
	}
	
	public void handlePermissionBan(CreatureObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		String listName = "BAN";
		building.setPermissionBan(listName,owner);
	}
	
	public void handlePermissionListModify(CreatureObject owner, SWGObject target, String commandArgs){
		
		String[] commandSplit = commandArgs.split(" ");
		if (commandSplit.length==3){		
			if (core.characterService.playerExists(commandSplit[2]) && 
				core.characterService.getPlayerOID(commandSplit[2])>0){
				long playerOID = core.characterService.getPlayerOID(commandSplit[2]);
				if (commandSplit[2].equals("ENTRY")){
					
					if (commandSplit[0].equals("add")){
						((BuildingObject)target).addPlayerToEntryList(owner, playerOID, commandSplit[2]);
					}
					if (commandSplit[0].equals("remove")){
						((BuildingObject)target).removePlayerFromEntryList(owner, playerOID, commandSplit[2]);
					}
				}
				if (commandSplit[2].equals("BAN")){
					
					if (commandSplit[0].equals("add")){					
						((BuildingObject)target).addPlayerToBanList(owner, playerOID, commandSplit[2]);
					}
					if (commandSplit[0].equals("remove")){					
						((BuildingObject)target).removePlayerFromBanList(owner, playerOID, commandSplit[2]);
					}
				}
			} else {
				owner.sendSystemMessage(commandSplit[2]+ " is an invalid player name", (byte) 0); // modify_list_invalid_player	%NO is an invalid player name.
			}	
		}
		if (commandSplit.length==2){
			owner.sendSystemMessage("No name was entered", (byte) 0);
		}
		
	}
	
	public String fetchPrivacyString(TangibleObject object){
		final BuildingObject building = (BuildingObject) object.getAttachment("housing_parentstruct");
		return building.getPrivacyString();
	}
	
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0,
			Map<Integer, INetworkRemoteEvent> arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void shutdown() {
		
	}	
}