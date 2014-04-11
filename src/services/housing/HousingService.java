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
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import main.NGECore;
import protocol.swg.EnterStructurePlacementModeMessage;
import protocol.swg.SceneDestroyObject;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.deed.Harvester_Deed;
import resources.objects.deed.Player_House_Deed;
import resources.objects.harvester.HarvesterObject;
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
//		if(!actor.getClient().isGM() && !core.terrainService.canBuildAtPosition(actor, actor.getWorldPosition().x, actor.getWorldPosition().z))
//		{
//			actor.sendSystemMessage("You may not place a structure here.", (byte) 0); // should probably load this from an stf
//			return;
//		}
		
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
		
		int structureLotCost = houseTemplate.getLotCost();
		String structureTemplate = houseTemplate.getBuildingTemplate();
		
//		if(!houseTemplate.canBePlacedOn(actor.getPlanet().getName()))
//		{
//			actor.sendSystemMessage("You may not place this structure on this planet.", (byte) 0); // should probably load this from an stf
//			return;
//		}
//		
//		if(!actor.getClient().isGM() && !core.terrainService.canBuildAtPosition(actor, positionX, positionZ))
//		{
//			actor.sendSystemMessage("You may not place a structure here.", (byte) 0); // should probably load this from an stf
//			return;
//		}
//		
//		// Lot stuff
//		if(!actor.getPlayerObject().deductLots(structureLotCost))
//		{
//			actor.sendSystemMessage("You do not have enough available lots to place this structure.", (byte) 0); // should probably load this from an stf
//			return;
//		}
		
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
				@SuppressWarnings("unchecked")
				@Override
				public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
					core.suiService.closeSUIWindow(owner, 0);
					createCodeWindow(owner, target);
				}					
			});		
			window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
				@SuppressWarnings("unchecked")
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
				@SuppressWarnings("unchecked")
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
						
						SceneDestroyObject destroyObjectMsg = new SceneDestroyObject(building.getObjectID());
						owner.getClient().getSession().write(destroyObjectMsg.serialize());
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
				@SuppressWarnings("unchecked")
				@Override
				public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
					core.suiService.closeSUIWindow(owner, 0);
				}					
			});		
			core.suiService.openSUIWindow(window);
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