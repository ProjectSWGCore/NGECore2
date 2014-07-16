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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import main.NGECore;
import protocol.swg.EnterStructurePlacementModeMessage;
import resources.common.OutOfBand;
import resources.datatables.Citizenship;
import resources.datatables.DisplayType;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.harvester.HarvesterObject;
import resources.objects.installation.InstallationObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import services.playercities.PlayerCity;
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
	private Map<String, String> houseToDeed = new ConcurrentHashMap<String, String>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public HousingService(NGECore core) {
		this.core = core;
		
	    Path p = Paths.get("scripts/houses/");
	    
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	core.scriptService.callScript("scripts/houses/", file.getFileName().toString().replace(".py", ""), "setup", housingTemplates);
	        	return FileVisitResult.CONTINUE;
	        }
	    };
	    
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        housingTemplates.values().forEach((houseTemplate) -> houseToDeed.put(houseTemplate.getBuildingTemplate(), houseTemplate.getDeedTemplate()));
	}
	
	public void enterStructureMode(CreatureObject actor, TangibleObject deed) {
		PlayerCity city = core.playerCityService.getCityObjectIsIn(actor);
		if (!actor.getClient().isGM() && !core.terrainService.canBuildAtPosition(actor, actor.getWorldPosition().x, actor.getWorldPosition().z)) {
			actor.sendSystemMessage("You may not place a structure here.", (byte) 0); // should probably load this from an stf
			return;
		}
		
		if(city != null && !city.hasZoningRights(actor)) {
			actor.sendSystemMessage("@player_structure:no_rights", (byte) 0); 
			return;
		}
		
		if (housingTemplates.containsKey(deed.getTemplate())) {
			HouseTemplate houseTemplate = housingTemplates.get(deed.getTemplate());
			EnterStructurePlacementModeMessage packet = new EnterStructurePlacementModeMessage(deed, houseTemplate.getBuildingTemplate());	
			actor.getClient().getSession().write(packet.serialize());
		}
	}
	
	public BuildingObject placeStructure(final CreatureObject actor, TangibleObject deed, float positionX, float positionZ, float rotation) {
		HouseTemplate houseTemplate = housingTemplates.get(deed.getTemplate());
		int structureLotCost = houseTemplate.getLotCost();
		String structureTemplate = houseTemplate.getBuildingTemplate();
		//PlayerCity city = core.playerCityService.getCityPositionIsIn(new Point3D(positionX, 0, positionZ));
		// This function is not implemented, so it had to be commented out, because it resulted in an error
		// Whoever wrote this, should still add the method to playerCityService, then it can be uncommented here.
		@SuppressWarnings("unused") PlayerCity city = null;
		if (!houseTemplate.canBePlacedOn(actor.getPlanet().getName())) {
			actor.sendSystemMessage("You may not place this structure on this planet.", (byte) 0); // should probably load this from an stf
			return null;
		}
		
		if(!actor.getClient().isGM() && !core.terrainService.canBuildAtPosition(actor, positionX, positionZ)) {
			actor.sendSystemMessage("You may not place a structure here.", (byte) 0); // should probably load this from an stf
			return null;
		}
		
		/* This will always be null, so has no use...
		if(city != null && !city.hasZoningRights(actor)) {
			actor.sendSystemMessage("@player_structure:no_rights", (byte) 0); 
			return null;
		}
		*/
		
		// Lot stuff
		if (!actor.getPlayerObject().deductLots(structureLotCost)) {
			actor.sendSystemMessage("You do not have enough available lots to place this structure.", (byte) 0); // should probably load this from an stf
			return null;
		}
		
		// Calculate our orientation and height
		Quaternion quaternion = new Quaternion(1, 0, 0, 0);
		quaternion = resources.common.MathUtilities.rotateQuaternion(quaternion, (float)((Math.PI/2) * rotation), new Point3D(0, 1, 0));
		
		float positionY = core.terrainService.getHeight(actor.getPlanetId(), positionX, positionZ) + 2f;
		
		String constructorTemplate = mapConstructor(structureTemplate);
		InstallationObject constructors = (InstallationObject) core.objectService.createObject(constructorTemplate, 0, actor.getPlanet(), new Point3D(positionX, positionY, positionZ), quaternion);				
		core.simulationService.add(constructors, positionX, positionZ, true);
		core.scriptService.callScript("scripts/", "constructor_build_phase", "buildConstructor", core);
		core.objectService.destroyObject(constructors);
		
		// Create the building
		BuildingObject building = (BuildingObject) core.objectService.createObject(structureTemplate, 0, actor.getPlanet(), new Point3D(positionX, positionY, positionZ), quaternion);

		core.simulationService.add(building, building.getPosition().x, building.getPosition().z, true);
		
		// Name the sign
		TangibleObject sign = (TangibleObject) building.getAttachment("structureSign");	
		String playerFirstName = actor.getCustomName().split(" ")[0];
		
		if (sign != null) {
			sign.setCustomName(playerFirstName + "'s House");
		}
		
		//building.add(sign);
		
		core.objectService.destroyObject(deed);
		
		building.setAttachment("sign", sign); // meh workaround
		building.setAttachment("nextMaintenance", System.currentTimeMillis() + 3600000);
		building.setAttachment("structureOwner", actor.getObjectID());
		building.setAttachment("isCondemned", false);
		building.setAttachment("isCivicStructure", houseTemplate.isCivicStructure());
		building.setAttachment("civicStructureType", houseTemplate.getCivicStructureType());
		building.setAttachment("outstandingMaint", 0);
		building.addPlayerToAdminList(null, actor.getObjectID(), playerFirstName);
		building.setDeedTemplate(deed.getTemplate());
		building.setMaintenanceAmount(houseTemplate.getBaseMaintenanceRate());
		building.setDestructionFee(houseTemplate.getDestructionFee());
		
		/* This will always be null, so has no use...
		if(city != null) {
			city.addNewStructure(building.getObjectID());
		}
		*/
		
		/*
		// Check for city founders joining a new city
		PlayerCity cityActorIsIn = core.playerCityService.getCityObjectIsIn(actor);
		
		if (cityActorIsIn != null) {
			actor.setAttachment("Has24HZoningFor",cityActorIsIn.getCityID()); // for testing
			
			int cityActorHasZoning = (int)actor.getAttachment("Has24HZoningFor");
			if (cityActorIsIn!=null && cityActorHasZoning==cityActorIsIn.getCityID()){
				if (! cityActorIsIn.getCitizens().contains(actor.getObjectID())){
					building.setAttachment("structureCity", cityActorIsIn.getCityID());
					// actor.setAttachment("residentCity", cityActorIsIn.getCityID()); He must do it manually
					cityActorIsIn.addCitizen(actor.getObjectID());
					cityActorIsIn.addNewStructure(building.getObjectID());
				}
			}
		}*/
		
		core.objectService.persistObject(building.getObjectID(), building, core.getSWGObjectODB());
		return building;	
	}
	
	public String mapConstructor(String structureTemplate) {
		String mappedConstructorTemplate = "object/building/player/construction/shared_construction_player_house_generic_small_style_01.iff";
		if (structureTemplate.contains("small")){
			mappedConstructorTemplate = "object/building/player/construction/shared_construction_player_house_generic_small_style_01.iff";
		} else if (structureTemplate.contains("medium")){
			mappedConstructorTemplate = "object/building/player/construction/shared_construction_player_house_generic_medium_style_01.iff";
		} else if (structureTemplate.contains("large")){
			mappedConstructorTemplate = "object/building/player/construction/shared_construction_player_house_generic_large_style_01.iff";
		}
		
		if (structureTemplate.contains("guildhall") || structureTemplate.contains("cityhall")){
			mappedConstructorTemplate = "object/building/player/construction/shared_construction_player_guildhall_corellia_style_01.iff";
		} else if (structureTemplate.contains("hangar")){
			mappedConstructorTemplate = "object/building/player/construction/shared_construction_player_house_hangar.iff";
		} else if (structureTemplate.contains("meditation")){
			mappedConstructorTemplate = "object/building/player/construction/shared_construction_player_jedi_meditation_room.iff";
		}
		// Probably more could be added		
		return mappedConstructorTemplate;
	}
	
	public void startMaintenanceTask(BuildingObject building) {
		
		if(building.getAttachment("isCivicStructure") != null && (boolean) building.getAttachment("isCivicStructure"))
			return;
		
		AtomicReference<ScheduledFuture<?>> ref = new AtomicReference<ScheduledFuture<?>>();
		ref.set(scheduler.scheduleAtFixedRate(() -> {
			try {
				if(core.objectService.getObject(building.getObjectID()) == null)
					ref.get().cancel(true);
					
				int amount = building.getBMR();
				boolean needSave = false;
				CreatureObject owner = (CreatureObject) core.objectService.getObject((long) building.getAttachment("structureOwner"));
				if(owner == null) {
					needSave = true;
					owner = core.objectService.getCreatureFromDB((long) building.getAttachment("structureOwner"));
				}
				if(building.getMaintenanceAmount() >= amount) {
					building.setMaintenanceAmount(building.getMaintenanceAmount() - amount);
				} else {
					if(owner == null)
						ref.get().cancel(true);
					if(owner.getBankCredits() >= amount) {
						owner.setBankCredits(owner.getBankCredits() - amount);
					} else if(owner.getCashCredits() >= amount) {
						owner.setCashCredits(owner.getCashCredits() - amount);					
					} else {
						if(building.getAttachment("isCondemned") != null && !((boolean) building.getAttachment("isCondemned"))) {
							building.setAttachment("isCondemned", true);
							building.setBuildingName(building.getBuildingName() + " \\#FF0000(CONDEMNED)\\#FFFFFF");
						}
						building.setOutstandingMaintenance(building.getOutstandingMaintenance() + amount);
						// TODO: lock down building, add option to pay outstanding maintenance and uncondemn building
					}
				}
				if(needSave && owner != null)
					core.objectService.persistObject(owner.getObjectID(), owner, core.getSWGObjectODB());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, (long) building.getAttachment("nextMaintenance") - System.currentTimeMillis(), 3600000, TimeUnit.MILLISECONDS));
		
	}
	
	@SuppressWarnings("unchecked")
	public void saveBuildings() {
		core.objectService.getObjectList()
		.values().stream()
		.filter(obj -> obj instanceof BuildingObject && obj.getAttachment("structureOwner") != null)
		.forEach(building -> { 
			if(building.getAttachment("childObjects") != null)
				((Vector<SWGObject>) building.getAttachment("childObjects")).clear();
			core.objectService.persistObject(building.getObjectID(), building, core.getSWGObjectODB());
		});
	}
	
	public boolean getPermissions(SWGObject player, SWGObject container) {
		// Temp fix for null error on return line.
		if (player == null || container == null || container.getContainer() == null) {
			return false;
		}
		
		SWGObject structure = container.getContainer();
		
		// This could easily be a ShipObject in the future.
		if (structure instanceof BuildingObject) {
			return ((BuildingObject) structure).isOnAdminList((CreatureObject) player);
		} else {
			return false;
		}
	}
	
	public void createDestroySUIPage(final SWGObject owner, final TangibleObject target) {
		
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		//final BuildingObject building = (BuildingObject) core.objectService.getObject(target.getParentId());
		// harvester.getStfFilename(); installation_n .getTemplate();
		
		if (building.getItemsList().size()>0){
			((CreatureObject) owner).sendSystemMessage("@player_structure:clear_building_for_delete", (byte)1);
		}
		
		String displayname = "@installation_n:"+building.getStfName();
		final Vector<Integer> noRedeed = new Vector<Integer>();
		if (building.getCustomName()!=null)
			displayname = building.getCustomName();
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", displayname);
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:confirm_destruction_d1 " +
												    "@player_structure:confirm_destruction_d2 "  +
												    "\n \n @player_structure:confirm_destruction_d3a " +
												    "\\#32CD32 @player_structure:confirm_destruction_d3b \\#FFFFFF " +
												    "@player_structure:confirm_destruction_d4 ");
		if (building.getConditionDamage()<20 && building.getMaintenanceAmount()<building.getDestructionFee()){
			window.addListBoxMenuItem("@player_structure:redeed_confirmation \\#BB0000 @player_structure:can_redeed_no_suffix \\#FFFFFF ",1 );
			noRedeed.add(-1);
		} else {
			window.addListBoxMenuItem("@player_structure:redeed_confirmation \\#32CD32 @player_structure:can_redeed_yes_suffix \\#FFFFFF ",1 );
		}
		if (building.getConditionDamage()>20){
			window.addListBoxMenuItem("@player_structure:redeed_condition \\#BB0000 " + building.getConditionDamage() + " \\#FFFFFF ",1 );
			noRedeed.add(-1);
		} else {
			window.addListBoxMenuItem("@player_structure:redeed_condition \\#32CD32 " + building.getConditionDamage() + " \\#FFFFFF ",1 );
		}
		if (building.getMaintenanceAmount()<building.getDestructionFee()){
			window.addListBoxMenuItem("@player_structure:redeed_maintenance \\#BB0000 " + (int)building.getMaintenanceAmount() + " \\#FFFFFF ",2 );
			noRedeed.add(-1);
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
				if (noRedeed.size()==0){
					building.setAttachment("Can_Redeed", 1);					
				} else {
					building.setAttachment("Can_Redeed", 0);	
				}
				
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
		
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		int canRedeed = (int) building.getAttachment("Can_Redeed");
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		//final BuildingObject building = (BuildingObject)target;
		Random rnd = new Random();
		final int confirmCode = 100000 + rnd.nextInt(900000);
		final SUIWindow window = core.suiService.createInputBox(2,"@player_structure:structure_status","@player_structure:structure_name_prompt", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:confirm_destruction_t");
		
		
		
		if (canRedeed==1){
			window.setProperty("Prompt.lblPrompt:Text", "@player_structure:your_structure_prefix " +
												"\\#32CD32 @player_structure:will_redeed_confirm \\#FFFFFF "+
											    "@player_structure:will_redeed_suffix "  +
											    "\n \n Code: " + confirmCode);
		} else {
			window.setProperty("Prompt.lblPrompt:Text", "@player_structure:your_structure_prefix " +
					"\\#BB0000 @player_structure:will_not_redeed_confirm \\#FFFFFF "+
				    "@player_structure:will_redeed_suffix "  +
				    "\n \n Code: " + confirmCode);
		}

		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@yes");
		window.setProperty("btnCancel:Text", "@no");				
		Vector<String> returnList = new Vector<String>();
		returnList.add("txtInput:LocalText");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				CreatureObject houseOwner = (CreatureObject)owner;
				core.suiService.closeSUIWindow(owner, 0);
				if (returnList.get(0).equals(""+confirmCode)) {
					// handle creation of correct deed in player inventory
					PlayerObject player = (PlayerObject) houseOwner.getSlottedObject("ghost");	
					HouseTemplate houseTemplate = housingTemplates.get(houseToDeed.get(building.getTemplate()));
					
					
					
					if (canRedeed==1){					
						TangibleObject deed = (TangibleObject) core.objectService.createObject(houseTemplate.getDeedTemplate(), owner.getPlanet());
						
						if (player.getLotsRemaining() + houseTemplate.getLotCost() > 10){
							// Something went wrong or hacking attempt
							houseOwner.sendSystemMessage("Structure can't be redeeded. Maximum lot count exceeded.",(byte)1);
							return;
						}
						
						deed.setIntAttribute("@obj_attr_n:examine_maintenance_rate", houseTemplate.getBaseMaintenanceRate());
						deed.setIntAttribute("@obj_attr_n:examine_maintenance", (int) building.getMaintenanceAmount());
						
						owner.getContainer().remove(owner);
						
						int costs = 800;
						houseOwner.setBankCredits(houseOwner.getBankCredits()-costs);
						
						destroyStructure(building);
											
	 
						SWGObject ownerInventory = owner.getSlottedObject("inventory");
						ownerInventory.add(deed);
						
						if (player.getLotsRemaining() + houseTemplate.getLotCost() <= 10) {
							player.setLotsRemaining(player.getLotsRemaining() + houseTemplate.getLotCost());
						}
											
						houseOwner.sendSystemMessage("@player_structure:processing_destruction",(byte)1);
						houseOwner.sendSystemMessage("@player_structure:deed_reclaimed",(byte)1);
						
					} else {
						
						if (player.getLotsRemaining() + houseTemplate.getLotCost() > 10){
							// Something went wrong or hacking attempt
							houseOwner.sendSystemMessage("Structure can't be destroyed. Maximum lot count exceeded.",(byte)1);
							return;
						}						
						owner.getContainer().remove(owner);					
						destroyStructure(building); 						
						if (player.getLotsRemaining() + houseTemplate.getLotCost() <= 10) {
							player.setLotsRemaining(player.getLotsRemaining() + houseTemplate.getLotCost());
						}
											
						houseOwner.sendSystemMessage("@player_structure:processing_destruction",(byte)1);
					}
					
				} else {
					houseOwner.sendSystemMessage("@player_structure:incorrect_destroy_code",(byte)1);
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
	
	public void destroyStructure(BuildingObject building) {
		
		PlayerCity city = core.playerCityService.getCityObjectIsIn(building);
		CreatureObject owner = null;
		if(building.getAttachment("structureOwner") != null)
			owner = (CreatureObject) core.objectService.getObject((long) building.getAttachment("structureOwner"));
		
		if(building.getAttachment("sign") != null)
			core.objectService.destroyObject((SWGObject) building.getAttachment("sign"));
		
		core.objectService.destroyObject(building.getObjectID());
		core.objectService.deletePersistentObject(building.getObjectID(), core.getSWGObjectODB());
		
		if(building.getResidency())
			owner.setAttachment("residentBuilding", null);
		
		
		if(city != null) {
			city.removeStructure(building.getObjectID());
			if(building.getResidency()) {
				city.removeCitizen(owner.getObjectID());
			}
			if(building.getTemplate().contains("cityhall")) {
				// destroy city
				core.playerCityService.destroyCity(city);
			}
		}

	}
	
	public void createPayMaintenanceSUIPage(SWGObject owner, TangibleObject target) {
		CreatureObject creature = (CreatureObject) owner;
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
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
				crafter.sendSystemMessage("You successfully make a payment of " + Integer.parseInt(returnList.get(1)) + " credits to the maintenance pool.", (byte) 0);
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
		((BuildingObject) target).setBuildingName(name);	
		owner.sendSystemMessage("Structure renamed.", DisplayType.Broadcast);
	}
	
	public void createStatusSUIPage(CreatureObject owner, TangibleObject target) 
	{
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		
		String displayname = "@installation_n:"+building.getStfName();
		if (building.getCustomName()!=null) displayname = building.getCustomName();
		
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:structure_status_t");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:structure_name_prompt" + " " + displayname);
		
		String ownerName =  core.objectService.getObject((long) building.getAttachment("structureOwner")).getFirstName();
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
		
		if (building.getPrivacy()==BuildingObject.PRIVATE) window.addListBoxMenuItem("@player_structure:structure_private", 1);
		else window.addListBoxMenuItem("@player_structure:structure_public", 1);
		
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
	
	public void declareResidency(SWGObject owner, BuildingObject building) {
		PlayerCity cityActorIsIn = core.playerCityService.getCityObjectIsIn(building);
		if(owner.getObjectID() != (long) building.getAttachment("structureOwner")) {
			((CreatureObject) owner).sendSystemMessage("@player_structure:declare_must_be_owner", (byte) 0);
			return;
		} else if(building.getResidency()) {
			((CreatureObject) owner).sendSystemMessage("@player_structure:already_residence", (byte) 0);
			return;
		}
		if(((CreatureObject) owner).getPlayerObject().getCitizenship() == Citizenship.Mayor && !building.getTemplate().contains("cityhall")) { // mayors always have the city hall as their residence and cant change it
			if(cityActorIsIn == null || cityActorIsIn.getMayorID() != owner.getObjectID()) {
				((CreatureObject) owner).sendSystemMessage("@city/city:mayor_residence_change", (byte) 0);			
				return;
			}
		}
		if((owner.getAttachment("residencyCooldown") == null || System.currentTimeMillis() < (long) owner.getAttachment("residencyCooldown")) && cityActorIsIn.getMayorID() != owner.getObjectID()) {
			((CreatureObject) owner).sendSystemMessage(OutOfBand.ProsePackage("@player_structure:change_residence_time", "DI", (int) ((long) owner.getAttachment("residencyCooldown") - System.currentTimeMillis()) / 3600000), (byte) 0);
			return;
		}
		BuildingObject oldResidency = null;
		if(owner.getAttachment("residentBuilding") != null && core.objectService.getObject((long) owner.getAttachment("residentBuilding")) != null) {
			oldResidency = (BuildingObject) core.objectService.getObject((long) owner.getAttachment("residentBuilding"));
			oldResidency.setResidency(false);
			((CreatureObject) owner).sendSystemMessage("@player_structure:change_residence", (byte) 0);
			PlayerCity oldCity = core.playerCityService.getCityObjectIsIn(oldResidency);
			if(oldCity != null && oldCity != cityActorIsIn) {
				oldCity.removeCitizen(owner.getObjectID());
			}
		} else {
			((CreatureObject) owner).sendSystemMessage("@player_structure:declared_residency", (byte) 0);		
		}
		building.setResidency(true);
		owner.setAttachment("residencyCooldown", System.currentTimeMillis() + 86400000); // 24 hours
		owner.setAttachment("residentBuilding", building.getObjectID());
		if(cityActorIsIn != null) {
			cityActorIsIn.addCitizen(owner.getObjectID());
		}
	}
	
	@SuppressWarnings("unused")
	public void handleListAllItems(SWGObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		String displayname = "@installation_n:"+building.getStfName();
		if (building.getCustomName()!=null)
			displayname = building.getCustomName();
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:find_items_find_all_house_items");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:find_items_prompt");
		
		Vector<TangibleObject> itemList = building.getItemsList();

		for (int i=0;i<itemList.size();i++){
			String itemName = itemList.get(i).getObjectName().getStfValue();
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
	
	public void handleDeleteAllItems(SWGObject owner, TangibleObject target) 
	{
		confirmDeleteAllItems(owner, target);
	}
	
	public void confirmDeleteAllItems(SWGObject owner, TangibleObject target) {

		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		
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
	
	public void confirmDeleteAllItems2ndStage(final CreatureObject ownerC, BuildingObject building) 
	{
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
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
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
		
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
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
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:find_items_search_keyword_title");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:find_items_search_list_prompt");
		
		Vector<TangibleObject> itemList = building.getItemsList();
			
		final Map<Integer,Long> itemIDMapping = new HashMap<Integer,Long>();
		
		for (int i=0;i<foundItems.size();i++){
			window.addListBoxMenuItem("#"+(i+1)+": " + foundItems.get(i).getTemplate(), i);
			itemIDMapping.put(i, itemList.get(i).getObjectID());
			if (i==0){
				// possibly sending the same delta multiple times
				if (((CreatureObject) owner).getLookAtTarget() != itemList.get(i).getObjectID());
					((CreatureObject)owner).setLookAtTarget(itemList.get(i).getObjectID());
				if (((CreatureObject) owner).getTargetId() != itemList.get(i).getObjectID());
					((CreatureObject)owner).setTargetId(itemList.get(i).getObjectID());
				if (((CreatureObject) owner).getIntendedTarget() != itemList.get(i).getObjectID());
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
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		String listName = "entry";
		building.setPermissionEntry(listName,owner);
	}
	
	public void handlePermissionBan(CreatureObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		String listName = "ban";
		building.setPermissionBan(listName,owner);
	}
	
	public void handlePermissionAdmin(CreatureObject owner, TangibleObject target) {
		final BuildingObject building = (BuildingObject) target.getGrandparent();
		//final BuildingObject building = (BuildingObject) target.getAttachment("housing_parentstruct");
		String listName = "admin";
		building.setPermissionAdmin(listName,owner);
	}

	
	public SWGObject getClosestStructureWithAdminRights(CreatureObject actor) {
		return core.simulationService.get(actor.getPlanet(), actor.getWorldPosition().x, actor.getWorldPosition().z, 20)
			   .stream().filter(o -> o instanceof BuildingObject || o instanceof InstallationObject)
			   .filter(o -> {
				   if(o instanceof BuildingObject)
					   return ((BuildingObject) o).isOnAdminList(actor);
				   else if(o instanceof HarvesterObject) {
					   System.out.println("test");
					   return ((HarvesterObject) o).isOnAdminList(actor);
				   }
				   return false;
			   }).min((o1, o2) -> (int) (o1.getWorldPosition().getDistance(actor.getWorldPosition()) - o2.getWorldPosition().getDistance(actor.getWorldPosition()))).orElse(null);
	}
	
	public void handlePermissionListModify(CreatureObject owner, SWGObject target, String commandArgs){
		
		String[] commandSplit = commandArgs.split(" ");
		if (commandSplit.length >= 3) {	
			if (core.characterService.playerExists(commandSplit[2]) && core.characterService.getPlayerOID(commandSplit[2]) > 0) {
				long playerOID = core.characterService.getPlayerOID(commandSplit[2]);
				if (commandSplit[1].equals("entry")) {
					
					if (commandSplit[0].equals("add")){
						((BuildingObject)target).addPlayerToEntryList(owner, playerOID, commandSplit[2]);
					}
					if (commandSplit[0].equals("remove")){
						((BuildingObject)target).removePlayerFromEntryList(owner, playerOID, commandSplit[2]);
					}
				}
				if (commandSplit[1].equals("ban")) {
					
					if (commandSplit[0].equals("add")) {			
						if(((BuildingObject)target).isOnAdminList((CreatureObject) core.objectService.getObject(playerOID))) {
							owner.sendSystemMessage("@player_structure:cannot_ban_admin", (byte) 0);
							return;
						}
						((BuildingObject)target).addPlayerToBanList(owner, playerOID, commandSplit[2]);
						((BuildingObject)target).removePlayerFromEntryList(owner, playerOID, commandSplit[2]);
					}
					if (commandSplit[0].equals("remove")) {					
						((BuildingObject)target).removePlayerFromBanList(owner, playerOID, commandSplit[2]);
					} 
				}
				if (commandSplit[1].equals("admin")) {
					
					if (commandSplit[0].equals("add")){					
						((BuildingObject)target).addPlayerToAdminList(owner, playerOID, commandSplit[2]);
					}
					if (commandSplit[0].equals("remove")) {					
						if(playerOID == (long) target.getAttachment("structureOwner")) {
							owner.sendSystemMessage("@player_structure:cannot_remove_owner", (byte) 0);
							return;
						}
						else if(playerOID == owner.getObjectID()) {
							owner.sendSystemMessage("@player_structure:cannot_remove_self", (byte) 0);
							return;
						}
						((BuildingObject)target).removePlayerFromAdminList(owner, playerOID, commandSplit[2]);
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
		//final BuildingObject building = (BuildingObject) object.getAttachment("housing_parentstruct");
		//return building.getPrivacyString();
		return ((BuildingObject) object.getGrandparent()).getPrivacyString();
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