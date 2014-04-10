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
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import main.NGECore;
import protocol.swg.EnterStructurePlacementModeMessage;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
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
	
	
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0,
			Map<Integer, INetworkRemoteEvent> arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void shutdown() {
		
	}	
}