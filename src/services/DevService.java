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

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import resources.common.Console;
import resources.common.FileUtilities;
import resources.common.Forager;
import resources.common.Opcodes;
import resources.common.SpawnPoint;
import resources.datatables.Posture;
import resources.datatables.WeaponType;
import resources.harvest.SurveyTool;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.sui.SUIWindow;
import services.sui.SUIService.ListBoxType;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import main.NGECore;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.container.CreatureContainerPermissions;
import engine.resources.container.CreaturePermissions;
import engine.resources.container.NullPermissions;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

@SuppressWarnings("unused")
public class DevService implements INetworkDispatch {
	
	private NGECore core;
	private long frogBuildingId = 0;
	
	public DevService(NGECore core) {
		this.core = core;
		loadFrogBuilding();
	}
	
	private void loadFrogBuilding() {
		BuildingObject building = (BuildingObject) core.objectService.createObject("object/building/tatooine/shared_association_hall_civilian_tatooine_02.iff", 0, core.terrainService.getPlanetByName("tatooine"), 
				new Point3D(-3308, 5, 2174), new Quaternion((float) 0.7323, 0, (float) 0.680961, 0));
		
		core.simulationService.add(building, building.getPosition().x, building.getPosition().z);
		
		this.frogBuildingId = building.getObjectID();
		
		building.setAttachment("structureOwner", 0);
		
		TangibleObject frog = (TangibleObject) core.objectService.createObject("object/tangible/terminal/shared_terminal_character_builder.iff", core.terrainService.getPlanetByID(building.getPlanetId()));
		frog.setPosition(new Point3D((float)-1.10475, (float)0.51, (float)-4.3665));
		frog.setOrientation(new Quaternion((float) 0.9965, 0, (float)-0.09, 0)); 
		building.getCellByCellNumber(2).add(frog);
	}
	
	public long getFrogBuildingId() {
		return this.frogBuildingId;
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
	}

	
	@Override
	public void shutdown() {
		
	}
	
}

