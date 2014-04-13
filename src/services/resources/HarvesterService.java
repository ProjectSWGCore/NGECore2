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
package services.resources;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.EnterStructurePlacementModeMessage;
import protocol.swg.SceneCreateObjectByCrc;
import protocol.swg.SceneDestroyObject;
import protocol.swg.SceneEndBaselines;
import main.NGECore;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.common.ObjControllerOpcodes;
import resources.objects.creature.CreatureObject;
import resources.objects.deed.Harvester_Deed;
import resources.objects.harvester.HarvesterMessageBuilder;
import resources.objects.harvester.HarvesterObject;
import resources.objects.installation.InstallationObject;
import resources.objects.player.PlayerObject;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.resource.ResourceRoot;
import resources.objects.tangible.TangibleObject;
import resources.objects.waypoint.WaypointObject;
import services.chat.Mail;
import services.chat.WaypointAttachment;
import services.sui.SUIWindow;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;

/** 
 * @author Charon 
 */

public class HarvesterService implements INetworkDispatch {
	
	private NGECore core;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private Vector<HarvesterObject> allHarvesters = new Vector<HarvesterObject>();
	private Vector<InstallationObject> allConstructors = new Vector<InstallationObject>();
	
	public HarvesterService(NGECore core) {
		this.core = core;	
		//core.commandService.registerCommand("permissionlistmodify");
		//core.commandService.registerCommand("harvesterselectresource");
		//core.commandService.registerCommand("harvesteractivate");
		//core.commandService.registerCommand("harvesterdeactivate");
		//core.commandService.registerCommand("harvesterdiscardhopper");
		//core.commandService.registerCommand("harvestergetresourcedata");
		//core.commandService.registerCommand("resourceemptyhopper");
		//core.commandService.registerCommand("placestructure");
		//generateNoBuildData();
		scheduleHarvesterService();
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		objControllerOpcodes.put(ObjControllerOpcodes.RESOURCE_EMPTY_HOPPER, new INetworkRemoteEvent() {
			
			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);
				Client client = core.getClient(session);
				
//				StringBuilder sb = new StringBuilder();
//			    for (byte b : data.array()) {
//			        sb.append(String.format("%02X ", b));
//			    }
//			    System.out.println(sb.toString());
			    
			    /*
			    05 00 46 5E CE 80 83 00   00 00 ED 00 00 00 3E 45 
			    04 00 00 00 00 00 00 00   00 00 3E 45 04 00 00 00 
			    00 00 90 52 05 00 00 00   00 00 D7 35 05 00 00 00 
			    00 00 01 00 00 00 00 07			    
			    */
			    
			    long playerId = data.getLong(); // 3E 45 04 00 00 00 00 00
			    data.getInt();   // 00 00 00 00
			    data.getLong(); // 3E 45 04 00 00 00 00 00
			    long harvesterId = data.getLong(); // 1E 55 05 00 00 00 00 00
			    //long containerId = data.getLong(); // 1E 55 05 00 00 00 00 00  	Resources ID 
			    long resourceId = data.getLong(); // 1E 55 05 00 00 00 00 00  	Resources ID
			    int stackCount = data.getInt();   // Stack count
			    byte actionMode = data.get();     // 0 for retrieving, 1 for discarding 
			    byte updateCount = data.get();     // updateCount
			    
				CreatureObject actor = (CreatureObject) client.getParent();
				SWGObject target = core.objectService.getObject(harvesterId);
				
				handleEmptyHopper(actor, target, harvesterId, resourceId, stackCount, actionMode, updateCount);
			}
		});
		
	}

	
	public void scheduleHarvesterService(){
		
		final ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				ServiceProcessing();	
			}
			
		}, 10, 5000, TimeUnit.MILLISECONDS);
		
		ExecutionSurveillanceThread executionSurveillance = new ExecutionSurveillanceThread(task);
		executionSurveillance.start();
	}
	
	class ExecutionSurveillanceThread extends Thread {
		ScheduledFuture<?> task;
	  
	    public ExecutionSurveillanceThread(ScheduledFuture<?> task) {
	      this.task = task;
	    }
	  
	    public void run() {
	    	try {
	    		try {
					task.get();
				} catch (InterruptedException e1) {
					System.err.println("InterruptedException IN HARVESTERSERVICE !!! " + e1.getMessage());
				}
	    		} catch (ExecutionException e2) {
	    			System.err.println("EXCEPTION IN HARVESTERSERVICE !!! " + e2.getMessage());
	    			System.err.println("EXCEPTION CAUSE : " + e2.getCause());
	    		}
	    }
	  }
	
	public void ServiceProcessing(){
		synchronized(allHarvesters){
			for (HarvesterObject harvester : allHarvesters){
				if (harvester!=null)
					updateHarvester(harvester);
			}	
			allHarvesters.removeAll(Collections.singleton(null));
		}
		synchronized(allConstructors){
			Vector<InstallationObject> removeConstructors = new Vector<InstallationObject>();
			for (InstallationObject constructor : allConstructors){
				boolean readyToBuild = checkBuildTime(constructor);
				if (readyToBuild)
					removeConstructors.add(constructor);
			}	
			allConstructors.removeAll(removeConstructors);
			allConstructors.removeAll(Collections.singleton(null));
		}
	}
	
	
	public void addHarvester(SWGObject harvester){
		synchronized(allHarvesters){
			if (harvester!=null)
				allHarvesters.add((HarvesterObject)harvester);
		}
	}
	
	public void removeHarvester(SWGObject harvester){
		synchronized(allHarvesters){
			if (harvester!=null)
				allHarvesters.remove((HarvesterObject)harvester);
		}
	}
	
	public boolean checkBuildTime(InstallationObject installation){
		boolean value = false;
		long startTime = (long) installation.getAttachment("ConstructionStart");
		if (startTime+5000<System.currentTimeMillis()){			
			placeHarvester(installation);
			value = true;
		}
		return value;
	}
	
	
	public void createStatusSUIPage(SWGObject owner, TangibleObject target) {
		HarvesterObject harvester = (HarvesterObject)target;
		String displayname = "@installation_n:"+harvester.getStfName();
		if (harvester.getCustomName()!=null)
			displayname = harvester.getCustomName();
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:structure_status_t");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:structure_name_prompt" + " " + displayname);
		String ownerName = owner.getCustomName();
		if (ownerName.length()>0){
			String[] helper = ownerName.split(" ");
			ownerName = helper[0];
		}

		String maintenancePool_string = ""+(int)harvester.getMaintenanceAmount();
		int hourlyMaintenance = harvester.getMaintenanceCost();
		float totalNumberOfHours = (float)harvester.getMaintenanceAmount()/hourlyMaintenance;
		float minuteFraction = ((totalNumberOfHours * 100) % 100) / 100;
		int nDays = (int)totalNumberOfHours / 24;
		float difference = totalNumberOfHours % 24; 
		int nHours = (int)difference;
		int nMinutes = (int)(minuteFraction *60);		
		maintenancePool_string += " (" + nDays + " days, " + nHours + " hours, " + nMinutes + " minutes)";
		// Power reserves calculation
		String powerReserves_string = ""+(int)harvester.getPowerLevel();
		int hourlyPower = harvester.getPowerCost();
		totalNumberOfHours = (float)harvester.getPowerLevel()/hourlyPower;
		minuteFraction = ((totalNumberOfHours * 100) % 100) / 100;
		nDays = (int)totalNumberOfHours / 24;
		difference = totalNumberOfHours % 24; 
		nHours = (int)difference;
		nMinutes = (int)(minuteFraction *60);		
		powerReserves_string += " (" + nDays + " days, " + nHours + " hours, " + nMinutes + " minutes)";
		
		window.addListBoxMenuItem("@player_structure:owner_prompt" + " " + ownerName, 0);
		window.addListBoxMenuItem("@player_structure:structure_public", 1);
		window.addListBoxMenuItem("@player_structure:condition_prompt" + " " + target.getConditionDamage(), 2);	
		window.addListBoxMenuItem("@player_structure:maintenance_pool_prompt " + maintenancePool_string, 3);	
		window.addListBoxMenuItem("@player_structure:maintenance_rate_prompt " + harvester.getMaintenanceCost() + " cr/h", 4); // @player_structure:credits_per_hour	
		window.addListBoxMenuItem("@player_structure:maintenance_mods_prompt", 5);
		window.addListBoxMenuItem("@player_structure:power_reserve_prompt " + powerReserves_string, 6);
		window.addListBoxMenuItem("@player_structure:power_consumption_prompt " + harvester.getPowerCost() + " @player_structure:units_per_hour", 7);
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
	
	public void createPayMaintenanceSUIPage(SWGObject owner, TangibleObject target) {
		CreatureObject creature = (CreatureObject) owner;
		final HarvesterObject harvester = (HarvesterObject)target;
		final SUIWindow window = core.suiService.createSUIWindow("Script.transfer", owner, target, 10);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:select_amount");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:select_maint_amount" +
													"\n \n @player_structure:current_maint_pool : " + (int)harvester.getMaintenanceAmount());	
				
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
				harvester.setMaintenanceAmount(harvester.getMaintenanceAmount()+Float.parseFloat(returnList.get(1)));
				//String displayname = "@installation_n:"+harvester.getStfName();
				String displayname = "the structure";
				if (harvester.getCustomName()!=null)
					displayname = harvester.getCustomName();
				crafter.sendSystemMessage("You successfully make a payment of " + Integer.parseInt(returnList.get(1)) + " credits to " + displayname + ".", (byte) 0);
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				CreatureObject crafter = (CreatureObject)owner;
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	public void createRenameSUIPage(SWGObject owner, TangibleObject target) {
		//updateHarvester(owner, target);
		final SUIWindow window = core.suiService.createInputBox(2,"@player_structure:structure_status","@player_structure:structure_name_prompt", owner, target, 0);		
		Vector<String> returnList = new Vector<String>();
		returnList.add("txtInput:LocalText");	
		final TangibleObject outerSurveyTool = target;
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				core.harvesterService.handleSetName((CreatureObject)owner, (TangibleObject)outerSurveyTool,returnList.get(0));
				core.suiService.closeSUIWindow(owner, 0);
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	
	public void createDestroySUIPage(final SWGObject owner, final TangibleObject target) {
		
	//  "\\#32CD32 @player_structure:confirm_destruction_d3b \#" +
		final HarvesterObject harvester = (HarvesterObject)target;
		// harvester.getStfFilename(); installation_n .getTemplate();
		String displayname = "@installation_n:"+harvester.getStfName();
		if (harvester.getCustomName()!=null)
			displayname = harvester.getCustomName();
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", displayname);
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:confirm_destruction_d1 " +
												    "@player_structure:confirm_destruction_d2 "  +
												    "\n \n @player_structure:confirm_destruction_d3a " +
												    "\\#32CD32 @player_structure:confirm_destruction_d3b \\#FFFFFF " +
												    "@player_structure:confirm_destruction_d4 ");
		if (harvester.getConditionDamage()<20 && harvester.getMaintenanceAmount()<3000){
			window.addListBoxMenuItem("@player_structure:redeed_confirmation \\#BB0000 @player_structure:can_redeed_no_suffix \\#FFFFFF ",1 );
		} else {
			window.addListBoxMenuItem("@player_structure:redeed_confirmation \\#32CD32 @player_structure:can_redeed_yes_suffix \\#FFFFFF ",1 );
		}
		if (harvester.getConditionDamage()<20){
			window.addListBoxMenuItem("@player_structure:redeed_condition \\#BB0000 " + harvester.getConditionDamage() + " \\#FFFFFF ",1 );
		} else {
			window.addListBoxMenuItem("@player_structure:redeed_condition \\#32CD32 " + harvester.getConditionDamage() + " \\#FFFFFF ",1 );
		}
		if (harvester.getMaintenanceAmount()<0){
			window.addListBoxMenuItem("@player_structure:redeed_maintenance \\#BB0000 " + (int)harvester.getMaintenanceAmount() + " \\#FFFFFF ",2 );
		} else {
			window.addListBoxMenuItem("@player_structure:redeed_maintenance \\#32CD32  " + (int)harvester.getMaintenanceAmount() + " \\#FFFFFF ",2 );
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
		
		//  "\\#32CD32 @player_structure:confirm_destruction_d3b \#" +
			final HarvesterObject harvester = (HarvesterObject)target;
			// harvester.getStfFilename(); installation_n .getTemplate();
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
						String deedTemplate = harvester.getDeedTemplate(); 
						Harvester_Deed deed = (Harvester_Deed)core.objectService.createObject(deedTemplate, owner.getPlanet());
						if(player.getLotsRemaining()+deed.getLotRequirement()>10){
							// Something went wrong or hacking attempt
							crafter.sendSystemMessage("Structure can't be redeeded. Maximum lot count exceeded.",(byte)1);
							return;
						}
								
						deed.setStructureTemplate(harvester.getTemplate());
						deed.setOutputHopperCapacity(harvester.getOutputHopperCapacity());
						deed.setBER(harvester.getBER());
						deed.setSurplusMaintenance((int)harvester.getMaintenanceAmount());
						deed.setSurplusPower((int)harvester.getPowerLevel());
						deed.setAttributes();
						
						removeHarvester(harvester);
						core.objectService.destroyObject(harvester.getObjectID());
	 
						SWGObject crafterInventory = owner.getSlottedObject("inventory");
						crafterInventory.add(deed);
						
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
	
	public void handleOperateMachinery(CreatureObject owner, TangibleObject target) {
			
		HarvesterObject harvester = (HarvesterObject) target;		
		harvester.operateMachinery(owner);
	}
	
	public void handleDestroy(SWGObject owner, TangibleObject target) {
		
		HarvesterObject harvester = (HarvesterObject) target;		
		// Assemble resources at that spot	
		// For later, it might be needed to also pass the template to differentiate liquid resources
		Vector<GalacticResource> planetVector = core.resourceService.getSpawnedResourcesByPlanetAndHarvesterType(target.getPlanetId(),harvester.getHarvester_type());
		HarvesterMessageBuilder messenger = new HarvesterMessageBuilder((HarvesterObject)target);
		owner.getClient().getSession().write(messenger.buildHINOBaseline7((HarvesterObject)target, planetVector));      				
	}
	
	public void handleSetName(CreatureObject owner, TangibleObject target,String name) {
			
		((HarvesterObject) target).setHarvesterName(name,owner);		
	}
	
	public void handleDepositPower(SWGObject owner, TangibleObject target) {
		
		CreatureObject creature = (CreatureObject) owner;
		final HarvesterObject harvester = (HarvesterObject) target;
		int playerEnergyLevel = 0;
		playerEnergyLevel = calculateTotalPlayerEnergy(creature);
	
		final SUIWindow window = core.suiService.createSUIWindow("Script.transfer", owner, target, 10);
		window.setProperty("bg.caption.lblTitle:Text", "@player_structure:add_power");
		window.setProperty("Prompt.lblPrompt:Text", "@player_structure:select_power_amount"+
													"\n \n @player_structure:current_power_value " + (int)harvester.getPowerLevel());		
		window.setProperty("transaction.txtInputFrom:Text", "" + playerEnergyLevel);
		window.setProperty("transaction.txtInputTo:Text", "" + "0");
		window.setProperty("transaction.lblFrom:Text", "@player_structure:total_energy");
		window.setProperty("transaction.lblTo:Text", "@player_structure:to_deposit");	
		window.setProperty("transaction:InputFrom", "5555");
		window.setProperty("transaction:InputTo", "6666");
		window.setProperty("transaction.lblStartingFrom:Text", ""+playerEnergyLevel);
		window.setProperty("transaction.lblStartingTo:Text", "0");
		window.setProperty("transaction.ConversionRatioFrom", "0");
		window.setProperty("transaction.ConversionRatioTo", "1");
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
				harvester.setPowerLevel(harvester.getPowerLevel()+Integer.parseInt(returnList.get(1)));
				updateEnergyInventoryContainers(crafter,Integer.parseInt(returnList.get(1)));
				crafter.sendSystemMessage("You successfully deposit " + Integer.parseInt(returnList.get(1)) + " units of energy.", (byte) 0);
				crafter.sendSystemMessage("Energy reserves now at " + (int)harvester.getPowerLevel() + " units.", (byte) 0);
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
	
	public int calculateTotalPlayerEnergy(CreatureObject owner){
		// final int energy = 0; Doesn't work with generic types
		int energy = 0;
		final Vector<Integer> energyVector = new Vector<Integer>();
		TangibleObject playerInventory = (TangibleObject) owner.getSlottedObject("inventory");
		playerInventory.viewChildren(owner, false, false, new Traverser() {
			@Override
			public void process(SWGObject obj) {
				
				if (obj instanceof ResourceContainerObject){
					ResourceContainerObject container = (ResourceContainerObject) obj;
					if (container.getResourceClass()==null)System.out.println("container.getResourceClass()==null " + container.getTemplate());
					if (container.getResourceClass().equals("Radioactive") ||
						container.getResourceClass().equals("Wind Renewable Energy") ||
						container.getResourceClass().equals("Solar Renewable Energy") ||
						container.getResourceClass().equals("Geothermal Renewable Energy") ||
						container.getResourceClass().equals("Tidal Renewable Energy") ||
						container.getResourceClass().equals("Hydron-3 Renewable Energy")) {
						energyVector.add(container.getStackCount());
					}	
				}
			}
			/*
			public void process(SWGObject a1) {
		      if(!(a1 instanceof PlayerObject) && a1 != null) {
		         if(a1.getClient() != null && a1 != a.this$0) {
		            a1.makeAware(a.this$0);
		         }		
		         a.this$0.makeAware(a1);
		      }
		   }
      		}
			*/
		});
		
		for (Integer value : energyVector){
			energy += value;
		}
		return energy;
	}
	
	/*
	 *  This is deducting the deposited power from the inventory
	 */	
	public void updateEnergyInventoryContainers(SWGObject owner, int deductedEnergy){

		final Vector<ResourceContainerObject> containerVector = new Vector<ResourceContainerObject>();
		TangibleObject playerInventory = (TangibleObject) owner.getSlottedObject("inventory");
		playerInventory.viewChildren(owner, false, false, new Traverser() {
			@Override
			public void process(SWGObject obj) {
				// is energy resource?
				if (obj instanceof ResourceContainerObject){
					ResourceContainerObject container = (ResourceContainerObject) obj;					
					if (container.getResourceClass().equals("Radioactive") ||
						container.getResourceClass().equals("Wind Renewable Energy") ||
						container.getResourceClass().equals("Solar Renewable Energy") ||
						container.getResourceClass().equals("Geothermal Renewable Energy") ||
						container.getResourceClass().equals("Tidal Renewable Energy") ||
						container.getResourceClass().equals("Hydron-3 Renewable Energy")) {
						containerVector.add(container);
					}	
				}
			}
		});

		Comparator<ResourceContainerObject> comp = new ResourceContainerObjectComparator();	    
		Collections.sort(containerVector, comp);

		for (int i=containerVector.size()-1;i >= 0; i--){
			int containerEnergy = containerVector.get(i).getStackCount();
			if (containerEnergy <= deductedEnergy){
				deductedEnergy -= containerEnergy;
				playerInventory.remove(containerVector.get(i));
			} else {
				containerEnergy -= deductedEnergy;
				containerVector.get(i).setStackCount(containerEnergy,(CreatureObject)owner);
				playerInventory._remove(containerVector.get(i));
				playerInventory._add(containerVector.get(i));	
				
//				ResourceMessenger messenger = new ResourceMessenger(IoBuffer.allocate(1));
//				owner.getClient().getSession().write(messenger.serialize_buildRCNO3Delta(containerVector.get(i).getObjectID(),containerEnergy));
			}
		}
	}
	
	public class ResourceContainerObjectComparator implements Comparator<ResourceContainerObject> {

		  @Override
		  public int compare(ResourceContainerObject b1, ResourceContainerObject b2) {
		    if (b1.getStackCount() == b2.getStackCount()) {
		      return 0;
		    }
		    if (b1.getStackCount() > b2.getStackCount()) {
		      return 1;
		    }
		    if (b1.getStackCount() < b2.getStackCount()) {
		      return -1;
		    }
		    return 0;
		  }
		}
	
	public void handlePermissionAdmin(CreatureObject owner, TangibleObject target) {
		
		String listName = "ADMIN";
		((HarvesterObject) target).setPermissionAdmin(listName,owner);
	}
	
	public void handlePermissionHopper(CreatureObject owner, TangibleObject target) {
		
		String listName = "HOPPER";
		((HarvesterObject) target).setPermissionHopper(listName,owner);
	}
	
	public void handlePermissionListModify(CreatureObject crafter, SWGObject target, String commandArgs){
		
		String[] commandSplit = commandArgs.split(" ");
		if (commandSplit.length==3){		
			if (core.characterService.playerExists(commandSplit[2])){
				if (commandSplit[2].equals("ADMIN")){
					Vector<String> adminList = ((HarvesterObject)target).getAdminList();
					if (commandSplit[0].equals("add") && (!adminList.contains(commandSplit[2]))){					
						crafter.sendSystemMessage(commandSplit[2] + " added as administrator", (byte) 0);
						adminList.add(commandSplit[2]);
						((HarvesterObject)target).setAdminList(adminList);
					}
					if (commandSplit[0].equals("remove") && (adminList.contains(commandSplit[2]))){					
						crafter.sendSystemMessage(commandSplit[2] + " removed as administrator", (byte) 0);
						adminList.remove(commandSplit[2]);
						((HarvesterObject)target).setAdminList(adminList);
					}
				}
				if (commandSplit[2].equals("HOPPER")){
					Vector<String> hopperList = ((HarvesterObject)target).getHopperList();
					if (commandSplit[0].equals("add") && (!hopperList.contains(commandSplit[2]))){					
						crafter.sendSystemMessage(commandSplit[2] + " added as administrator", (byte) 0);
						hopperList.add(commandSplit[2]);
						((HarvesterObject)target).setHopperList(hopperList);
					}
					if (commandSplit[0].equals("remove") && (hopperList.contains(commandSplit[2]))){					
						crafter.sendSystemMessage(commandSplit[2] + " removed as administrator", (byte) 0);
						hopperList.remove(commandSplit[2]);
						((HarvesterObject)target).setHopperList(hopperList);
					}
				}
			} else {
				crafter.sendSystemMessage(commandSplit[2]+ " is an invalid player name", (byte) 0);
			}	
		}
		if (commandSplit.length==2){
			crafter.sendSystemMessage("No name was entered", (byte) 0);
		}
		
	}
	
	public void handleHarvesterSelectResourceCommand(CreatureObject owner, SWGObject target, String commandArgs){
		
		long selectedResourceId = Long.parseLong(commandArgs);
		GalacticResource resource = core.resourceService.findResourceById(selectedResourceId);
		((HarvesterObject) target).setSelectedHarvestResource(resource,owner);
	}

	public void handleHarvesterActivateCommand(CreatureObject owner, SWGObject target, String commandArgs){
		
	
		if (((HarvesterObject)target).getPowerLevel()<=0 && !((HarvesterObject)target).isGenerator()){
				((CreatureObject)owner).sendSystemMessage("The installation is lacking power.", (byte) 0);
				return;
		}
		if (((HarvesterObject)target).getMaintenanceAmount()<=0){
				((CreatureObject)owner).sendSystemMessage("The installation is lacking maintenance.", (byte) 0);
				return;
		}
	
			
		((HarvesterObject)target).activateHarvester(owner);
	}
	
	public void handleHarvesterDeactivateCommand(CreatureObject owner, SWGObject target, String commandArgs){
		
		((HarvesterObject)target).deactivateHarvester(owner);
	}
	
	public void handleEmptyHopper(CreatureObject owner,SWGObject target,long harvesterId, long resourceId, int stackcount, byte actionMode, byte updateCount){
		
		HarvesterMessageBuilder messenger = new HarvesterMessageBuilder((HarvesterObject)target);
		//DiscardResourceResponse
		owner.getClient().getSession().write(messenger.buildDiscardResourceResponse((HarvesterObject)target, actionMode, updateCount,owner));
						
		
		ResourceContainerObject foundContainer = null;
		Vector<ResourceContainerObject> hopperContent = ((HarvesterObject) target).getOutputHopperContent();
		for (ResourceContainerObject cont : hopperContent){
			if (cont.getReferenceID()==resourceId){
				foundContainer = cont;
			}
		}
		if (foundContainer==null)
			return; // something went wrong
		
		if (foundContainer.getStackCount()==stackcount){
			((HarvesterObject) target).setCurrentHarvestedCountFloat(1);
			foundContainer.setStackCount(0, owner);
		} else {			
			((HarvesterObject) target).setCurrentHarvestedCountFloat(foundContainer.getStackCount()-stackcount);
			foundContainer.setStackCount(foundContainer.getStackCount()-stackcount,owner);
		}
		((HarvesterObject) target).setOutputHopperContent(hopperContent);
				
		// <<<
		Vector<GalacticResource> planetResourcesVector = core.resourceService.getSpawnedResourcesByPlanetAndHarvesterType(target.getPlanetId(),((HarvesterObject)target).getHarvester_type());		
		owner.getClient().getSession().write(messenger.buildHINOBaseline7((HarvesterObject)target, planetResourcesVector));      							
		owner.getClient().getSession().write(messenger.buildHINO7ExperimentalDelta2((HarvesterObject)target));
		// >>
		
		hopperContent = ((HarvesterObject) target).getOutputHopperContent();
		for (ResourceContainerObject cont : hopperContent){
			if (cont.getReferenceID()==resourceId){
				foundContainer = cont;
			}
		}
				
		// create retrieved resource container
		GalacticResource sampleResource = core.resourceService.findResourceById(resourceId);
		String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];           		  				
		ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, owner.getPlanet());
		containerObject.initializeStats(sampleResource);
		containerObject.setProprietor(owner);
		containerObject.setStackCount(stackcount,owner);
		containerObject.setIffFileName(resourceContainerIFF);									
		SWGObject crafterInventory = owner.getSlottedObject("inventory");
//		containerObject.sendBaselines(owner.getClient());
//		SceneEndBaselines sceneEndBaselinesMsg = new SceneEndBaselines(containerObject.getObjectID());
//		owner.getClient().getSession().write(sceneEndBaselinesMsg.serialize());
//		tools.CharonPacketUtils.printAnalysis(sceneEndBaselinesMsg.serialize());		
		crafterInventory.add(containerObject);
	}
	
	
	public void handleEmptyHarvester(CreatureObject owner, SWGObject target, String commandArgs){
		
		HarvesterMessageBuilder messenger = new HarvesterMessageBuilder((HarvesterObject)target);
		Vector<ResourceContainerObject> hopperContent = ((HarvesterObject) target).getOutputHopperContent();
		Vector<ResourceContainerObject> deleteContent = new Vector<ResourceContainerObject>();
		
		for (ResourceContainerObject cont : hopperContent){
			ResourceContainerObject foundContainer = cont;
			
			if (foundContainer==null)
				return; // something went wrong
						
			long resourceId = foundContainer.getReferenceID();
			int stackcount = foundContainer.getStackCount();

			SceneDestroyObject destroyObjectMsg = new SceneDestroyObject(resourceId);
			owner.getClient().getSession().write(destroyObjectMsg.serialize());  
									
			// create retrieved resource container
			GalacticResource sampleResource = core.resourceService.findResourceById(resourceId);
			String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];           		  				
			ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, owner.getPlanet());
			containerObject.initializeStats(sampleResource);
			containerObject.setProprietor(owner);
			containerObject.setStackCount(stackcount,owner);			
			int resCRC = CRC.StringtoCRC(resourceContainerIFF);
			containerObject.setIffFileName(resourceContainerIFF);
			
			long objectId = containerObject.getObjectID();										
			SWGObject crafterInventory = owner.getSlottedObject("inventory");
			
//			SceneCreateObjectByCrc createObjectMsg = new SceneCreateObjectByCrc(objectId, owner.getOrientation().x, owner.getOrientation().y, owner.getOrientation().z, owner.getOrientation().w, owner.getPosition().x, owner.getPosition().y, owner.getPosition().z, resCRC, (byte) 0);
//			owner.getClient().getSession().write(createObjectMsg.serialize());      				
//			tools.CharonPacketUtils.printAnalysis(createObjectMsg.serialize());
//			
//			containerObject.sendBaselines(owner.getClient());
//
//			SceneEndBaselines sceneEndBaselinesMsg = new SceneEndBaselines(containerObject.getObjectID());
//			owner.getClient().getSession().write(sceneEndBaselinesMsg.serialize());
//			tools.CharonPacketUtils.printAnalysis(sceneEndBaselinesMsg.serialize());
			
			crafterInventory.add(containerObject);
			
			if (cont.getReferenceID()!=((HarvesterObject) target).getSelectedHarvestResource().getId()){
				deleteContent.add(cont);				
			} else {
				((HarvesterObject) target).setCurrentHarvestedCountFloat(0);
				foundContainer.setStackCount(0,owner);
			}
		}
		hopperContent.removeAll(deleteContent);
		
		((HarvesterObject) target).setOutputHopperContent(hopperContent);

		Vector<GalacticResource> planetResourcesVector = core.resourceService.getSpawnedResourcesByPlanetAndHarvesterType(target.getPlanetId(),((HarvesterObject)target).getHarvester_type());		
		owner.getClient().getSession().write(messenger.buildHINOBaseline7((HarvesterObject)target, planetResourcesVector));      							
		owner.getClient().getSession().write(messenger.buildHINO7ExperimentalDelta2((HarvesterObject)target));
	}
		
	public void updateHarvester(HarvesterObject harvester){
		
		CreatureObject owner = (CreatureObject)core.objectService.getObject(harvester.getOwner());

		if(harvester.isActivated())
        {    
			// Check maintenance and power
			if (harvester.getMaintenanceAmount()<=0){
				owner.sendSystemMessage("The installation is lacking maintenance.", (byte) 0);
				// ToDo: Send mail
				harvester.deactivateHarvester(owner);
				return;
			}
			
			if (harvester.getPowerLevel()<=0 && !harvester.isGenerator()){
				owner.sendSystemMessage("The installation is lacking power.", (byte) 0);
				// ToDo: Send mail
				harvester.deactivateHarvester(owner);
				return;
			}
			
			harvester.setMaintenanceAmount(harvester.getMaintenanceAmount()-(harvester.getMaintenanceCost()/3600.0F));
			harvester.setPowerLevel(harvester.getPowerLevel()-(harvester.getPowerCost()/3600.0F));
			
			Vector<ResourceContainerObject> outputHopperContent = harvester.getOutputHopperContent();
			GalacticResource currentResource = harvester.getSelectedHarvestResource();
			ResourceContainerObject hopperContainer = hopperHasContainer(currentResource, outputHopperContent);
           // check if output hopper contains already a container with the selected harvest resource
            if(hopperContainer==null)
            {
            	
            	//ResourceContainerObject container = new ResourceContainerObject();
            	String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[currentResource.getResourceRoot().getContainerType()]; 
            	ResourceContainerObject container = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, harvester.getPlanet());
    			container.initializeStats(harvester.getSelectedHarvestResource());
    			container.setStackCount(0,false);
            	harvester.setCurrentHarvestedCountFloat(0.0F);
            	outputHopperContent.add(container);
            	harvester.setOutputHopperContent(outputHopperContent); 
            	harvester.createNewHopperContainer();

            } else {
            	
            	harvester.continueHopperContainer();
       	
            	// add harvested amount
            	int BER = harvester.getBER();     
            	//BER = 15;
            	float minuteRate = harvester.getActualExtractionRate();	
            	float updateRate = minuteRate / 60;
            	harvester.setCurrentHarvestedCountFloat(harvester.getCurrentHarvestedCountFloat()+updateRate);
         	          	
            	int oldStackCount = hopperContainer.getStackCount();
            	int newStackCount = (int) (harvester.getCurrentHarvestedCountFloat());
            	if (newStackCount>oldStackCount){          		
            		for (ResourceContainerObject iterContainer : outputHopperContent){
                		if (iterContainer.getReferenceID()==hopperContainer.getReferenceID()){
                			iterContainer.setStackCount(newStackCount,owner); // updates collection
                		}
                	}
            		harvester.setOutputHopperContent(outputHopperContent);		
            		//((CreatureObject)harvester.getOwner()).getClient().getSession().write(messenger.buildHINO7ExperimentalDelta2(harvester));
            	}
            	harvester.setCurrentHarvestedCountFloat(harvester.getCurrentHarvestedCountFloat()+updateRate);
            }
        }
	}
	
	private ResourceContainerObject hopperHasContainer(GalacticResource resource, Vector<ResourceContainerObject> outputHopperContent){
		ResourceContainerObject container = null;
		for (ResourceContainerObject iterContainer : outputHopperContent){
    		if (iterContainer.getReferenceID()==resource.getId()){
    			return iterContainer;
    		}
    	}
		return container;
	}
	
	
	public void constructionSite(CreatureObject actor,SWGObject target, long objectId, float posX, float posZ, float dir){
		
		SWGObject usedDeed = (SWGObject)actor.getAttachment("LastUsedDeed");
		String constructorTemplate = ((Harvester_Deed)usedDeed).getConstructorTemplate();
		//String structureTemplate = "object/installation/mining_ore/construction/shared_construction_mining_ore_harvester_style_1.iff";

		float positionY = core.terrainService.getHeight(actor.getPlanetId(), posX, posZ) + 2f;
		Quaternion quaternion = new Quaternion(1, 0, 0, 0);
		quaternion = resources.common.MathUtilities.rotateQuaternion(quaternion, (float)((Math.PI/2) * dir), new Point3D(0, 1, 0));
		InstallationObject installation = (InstallationObject) core.objectService.createObject(constructorTemplate, 0, actor.getPlanet(), new Point3D(posX, positionY, posZ), quaternion);		
		
		
		installation.setAttachment("ConstructionStart", System.currentTimeMillis());
		installation.setAttachment("Owner", actor);
		installation.setAttachment("Deed_StructureTemplate", ((Harvester_Deed)usedDeed).getStructureTemplate());
		installation.setAttachment("Deed_DeedTemplate", ((Harvester_Deed)usedDeed).getTemplate());
		installation.setAttachment("Deed_BER", ((Harvester_Deed)usedDeed).getBER());		
		installation.setAttachment("Deed_Capacity", ((Harvester_Deed)usedDeed).getOutputHopperCapacity());
		installation.setAttachment("Deed_DeedLots", ((Harvester_Deed)usedDeed).getLotRequirement());
		installation.setAttachment("Deed_SurplusMaintenance", ((Harvester_Deed)usedDeed).getSurplusMaintenance());
		installation.setAttachment("Deed_SurplusPower", ((Harvester_Deed)usedDeed).getSurplusPower());
		
		// destroy deed
		TangibleObject playerInventory = (TangibleObject) actor.getSlottedObject("inventory");
		playerInventory.remove(usedDeed);
	
		core.simulationService.add(installation, posX, posZ, true);
		
		PlayerObject player = (PlayerObject) actor.getSlottedObject("ghost");
		player.setLotsRemaining(player.getLotsRemaining()-(int)((Harvester_Deed)usedDeed).getLotRequirement());
		synchronized(allConstructors){
			allConstructors.add(installation);
		}
	}
	
	public void placeHarvester(SWGObject object) {	
		CreatureObject actor = (CreatureObject) object.getAttachment("Owner");
		String structureTemplate = (String)object.getAttachment("Deed_StructureTemplate");
		HarvesterObject harvester = (HarvesterObject) NGECore.getInstance().objectService.createObject(structureTemplate, actor.getPlanet());
		long objectId = harvester.getObjectID();
		Vector<String> adminList = harvester.getAdminList();
		//String[] fullName = ((CreatureObject)actor).getCustomName().split(" ");
		adminList.add(actor.getCustomName());
		// Set BER and outputhopper capacity here, take it from deed
		harvester.setBER((int)object.getAttachment("Deed_BER"));
		harvester.setSpecRate((int)(1.5F*(int)object.getAttachment("Deed_BER")));
		harvester.setOutputHopperCapacity((int)object.getAttachment("Deed_Capacity"));
		harvester.setDeedTemplate((String)object.getAttachment("Deed_DeedTemplate"));	
		if ((int)object.getAttachment("Deed_SurplusMaintenance")>0){
			harvester.setMaintenanceAmount((int)object.getAttachment("Deed_SurplusMaintenance"));
		}		
		if ((int)object.getAttachment("Deed_SurplusPower")>0){
			harvester.setPowerLevel((int)object.getAttachment("Deed_SurplusPower"));
		}
		
		
		// build harvester
		float positionY = NGECore.getInstance().terrainService.getHeight(actor.getPlanetId(), object.getPosition().x, object.getPosition().z);
		harvester.setPosition(new Point3D(object.getPosition().x,positionY, object.getPosition().z));
		core.simulationService.add(harvester, harvester.getPosition().x, harvester.getPosition().z, true);
		addHarvester(harvester);	
		
		PlayerObject player = (PlayerObject) actor.getSlottedObject("ghost");
		WaypointObject constructionWaypoint = (WaypointObject)NGECore.getInstance().objectService.createObject("object/waypoint/shared_world_waypoint_blue.iff", actor.getPlanet(), harvester.getPosition().x, 0 ,harvester.getPosition().z);
		String displayname = "@installation_n:"+harvester.getStfName();
		constructionWaypoint.setName(displayname);
		constructionWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(actor.getPlanet().getName()));
		constructionWaypoint.setPosition(new Point3D(object.getPosition().x,0, object.getPosition().z));	
		player.waypointAdd(constructionWaypoint);
		constructionWaypoint.setPosition(new Point3D(object.getPosition().x,0, object.getPosition().z));
		constructionWaypoint.setActive(true);
		constructionWaypoint.setColor((byte)1);
		constructionWaypoint.setStringAttribute("", "");
		player.waypointAdd(constructionWaypoint);
		constructionWaypoint.setName(displayname);
		constructionWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(actor.getPlanet().getName()));							
		player.setLastSurveyWaypoint(constructionWaypoint);
		
		List<WaypointAttachment> attachments = new ArrayList<WaypointAttachment>(); 
		WaypointAttachment attachment = new WaypointAttachment();
		attachment.active = true;
		attachment.cellID = constructionWaypoint.getCellId();
		attachment.color = (byte)1;
		attachment.name = displayname;
		attachment.planetCRC = engine.resources.common.CRC.StringtoCRC(actor.getPlanet().getName());
		attachment.positionX = object.getPosition().x;
		attachment.positionY = 0;
		attachment.positionZ = object.getPosition().z;
		attachments.add(attachment);
		
		// remove constructor
		NGECore.getInstance().objectService.destroyObject(object);
		
		// ToDo: waypoint attachment fix
		Date date = new Date();
		Mail constructionNotificationMail = new Mail();
		constructionNotificationMail.setSenderName("Structure Service");
		constructionNotificationMail.setSubject("Your structure");
		constructionNotificationMail.setRecieverId(actor.getObjectID());
		constructionNotificationMail.setTimeStamp((int) (date.getTime() / 1000));
		constructionNotificationMail.setMailId(NGECore.getInstance().chatService.generateMailId());
		String message = "Your construction is ready";
		constructionNotificationMail.setMessage(message);
		constructionNotificationMail.setStatus(Mail.NEW);
		constructionNotificationMail.setAttachments(attachments);
		//NGECore.getInstance().chatService.sendPersistentMessage(actor.getClient(), constructionNotificationMail);
		NGECore.getInstance().chatService.storePersistentMessage(constructionNotificationMail);
		NGECore.getInstance().chatService.sendPersistentMessageHeader(actor.getClient(), constructionNotificationMail);
	}
	
	
	public void enterStructurePlacementMode(SWGObject object, CreatureObject actor){

		if (!actor.getClient().isGM() && !core.terrainService.canBuildAtPosition(actor,actor.getPosition().x,actor.getPosition().z)){
			actor.sendSystemMessage("@player_structure:not_permitted", (byte) 0);
			return;
		}
		
		PlayerObject player = (PlayerObject) actor.getSlottedObject("ghost");
		if (((Harvester_Deed)object).getLotRequirement()>player.getLotsRemaining()){
			actor.sendSystemMessage("@player_structure:not_enough_lots", (byte) 0);
			return;
		}
		
		actor.setAttachment("LastUsedDeed", object);
		String structureTemplate = ((Harvester_Deed)object).getStructureTemplate();
		//String temp = "object/installation/mining_ore/shared_mining_ore_harvester_style_1.iff";
		EnterStructurePlacementModeMessage placementMsg = new EnterStructurePlacementModeMessage(actor,structureTemplate);		
		actor.getClient().getSession().write(placementMsg.serialize()); 
	}
	
	
	public void handlePlaceStructureCommand(CreatureObject crafter, SWGObject target, String commandArgs){

		// 511379 3516.08 -4804.08 0
		// Split Args by Delimiter
		long objectId = crafter.getObjectID();
		float posX = crafter.getPosition().x;
		float posZ = crafter.getPosition().z;
		float dir  = 0;
		String[] splitter = commandArgs.split(" ");
		if (splitter.length==4){ //QA
			objectId = Long.parseLong(splitter[0]);
			posX = Float.parseFloat(splitter[1]);
			posZ = Float.parseFloat(splitter[2]);
			dir  = Float.parseFloat(splitter[3]);			
		}

		constructionSite(crafter, target, objectId, posX, posZ, dir);
	}
	
	
	// helper method to simulate a harvester deed in the player inventory
	// that contains BER,cap,template info about the harvester
	public void createExampleDeed(CreatureObject actor){
		
		String templateString="object/tangible/deed/harvester_deed/shared_harvester_ore_s1_deed.iff";
		Harvester_Deed deed = (Harvester_Deed)core.objectService.createObject(templateString, actor.getPlanet());
		deed.setName("Example Harvester Deed");
		deed.setStructureTemplate("object/installation/mining_ore/shared_mining_ore_harvester_style_1.iff");
		deed.setOutputHopperCapacity(27344);
		deed.setBER(15);
		
		TangibleObject playerInventory = (TangibleObject) actor.getSlottedObject("inventory");
		playerInventory.add(deed);
		
		deed.setParent(playerInventory);	
		
		core.resourceService.spawnSpecificResourceContainer("Radioactive", actor, 50);
		core.resourceService.spawnSpecificResourceContainer("Radioactive", actor, 501);
		core.resourceService.spawnSpecificResourceContainer("Radioactive", actor, 3);
		core.resourceService.spawnSpecificResourceContainer("Radioactive", actor, 66);
//		core.resourceService.spawnSpecificResourceContainer("Steel", actor, 50);
//		core.resourceService.spawnSpecificResourceContainer("Fiberplast", actor, 50);
		
	}
	
	
	// Stress Test (Server) for many harvesters running in the service
	// 200 are running fine, 400 also
	public void moduleTestManyHarvesters(CreatureObject actor){
		// assuming an average number of 200 harvesters per planet
		for (int i=0;i<400;i++){
			createSingleTestHarvester(actor,i);
		}		
	}
	
	public void createSingleTestHarvester(CreatureObject actor,int counter){
		// determine random position
		String structureTemplate = "object/installation/mining_ore/shared_mining_ore_harvester_style_1.iff";
		HarvesterObject harvester = (HarvesterObject) core.objectService.createObject(structureTemplate, actor.getPlanet());
		long objectId = harvester.getObjectID();
		Vector<String> adminList = harvester.getAdminList();
		String[] fullName = ((CreatureObject)actor).getCustomName().split(" ");
		adminList.add(fullName[0]);
		harvester.setAdminList(adminList);
		// Set BER and outputhopper capacity here, take it from deed
		harvester.setBER(8);
		harvester.setOutputHopperCapacity(27344);
		harvester.setMaintenanceAmount(2993);
		harvester.setMaintenanceCost(30);
		harvester.setPowerLevel(14714);
		harvester.setPowerCost(25);
		
		Random generator = new Random();
		generator = new Random(); // Exclude mountains at the edge of the maps
		int spawnCoordsX = generator.nextInt(15000) - 7500;
		int spawnCoordsZ = generator.nextInt(15000) - 7500;
		
		// build harvester
		int resCRC = CRC.StringtoCRC(structureTemplate);
		Quaternion quaternion = new Quaternion(1, 0, 0, 0);
		quaternion = resources.common.MathUtilities.rotateQuaternion(quaternion, (float)((Math.PI/2) * 1), new Point3D(0, 1, 0));
		float positionY = core.terrainService.getHeight(actor.getPlanetId(), spawnCoordsX, spawnCoordsZ)+ 2f;
		SceneCreateObjectByCrc createObjectMsg = new SceneCreateObjectByCrc(objectId,quaternion.x,quaternion.y, quaternion.z, quaternion.w, spawnCoordsX, positionY, spawnCoordsZ, resCRC, (byte) 0);
		actor.getClient().getSession().write(createObjectMsg.serialize());      				
		tools.CharonPacketUtils.printAnalysis(createObjectMsg.serialize());
		
		resources.objects.installation.InstallationMessageBuilder messenger = new resources.objects.installation.InstallationMessageBuilder((InstallationObject)harvester);
		actor.getClient().getSession().write(messenger.buildBaseline3((InstallationObject)harvester));   
	 	SceneEndBaselines sceneEndBaselinesMsg = new SceneEndBaselines(harvester.getObjectID());
	 	actor.getClient().getSession().write(sceneEndBaselinesMsg.serialize());
		tools.CharonPacketUtils.printAnalysis(sceneEndBaselinesMsg.serialize());
		
		// select resource
		long selectedResourceId = core.resourceService.getAllSpawnedResources().get(51).getId();
		GalacticResource resource = core.resourceService.findResourceById(selectedResourceId);
		harvester.setSelectedHarvestResource(resource,actor);
		
		
		// activate harvester
		harvester.activateHarvester(actor);
		HarvesterMessageBuilder messenger2 = new HarvesterMessageBuilder(harvester);
		actor.getClient().getSession().write(messenger2.buildHINO3Delta(harvester,(byte)1));  
		actor.getClient().getSession().write(messenger2.buildHINO7ActivateDelta2(harvester));
		
		
		//create waypoint
		if (counter<200){
			PlayerObject player = (PlayerObject) actor.getSlottedObject("ghost");
			WaypointObject constructionWaypoint = (WaypointObject)core.objectService.createObject("object/waypoint/shared_world_waypoint_blue.iff", actor.getPlanet(), harvester.getPosition().x, 0 ,harvester.getPosition().z);
			String displayname = "@installation_n:"+harvester.getStfName() + " " + counter;
			constructionWaypoint.setName(displayname);
			constructionWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(actor.getPlanet().getName()));
			constructionWaypoint.setPosition(new Point3D(spawnCoordsX,0, spawnCoordsZ));	
			player.waypointAdd(constructionWaypoint);
			constructionWaypoint.setPosition(new Point3D(spawnCoordsX,0, spawnCoordsZ));
			constructionWaypoint.setActive(true);
			constructionWaypoint.setColor((byte)1);
			constructionWaypoint.setStringAttribute("", "");
			player.waypointAdd(constructionWaypoint);
			constructionWaypoint.setName(displayname);
			constructionWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(actor.getPlanet().getName()));							
			player.setLastSurveyWaypoint(constructionWaypoint);
		}
		
		allHarvesters.add(harvester);
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}