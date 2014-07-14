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
package services.gcw;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import resources.datatables.Factions;
import main.NGECore;
import engine.resources.common.CRC;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class InvasionService implements INetworkDispatch {
	
	private NGECore core;	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);	
	protected final Object objectMutex = new Object();
	
	private long kerenInvasionStartTime = 1;
	private long bestineInvasionStartTime = 2;
	private long dearicInvasionStartTime = 3;
	
	private int invasionLocation=InvasionLocation.Dearic;
	private int invasionPhase=2; // Test phase 2 
	
	public InvasionService(final NGECore core) {
		this.core = core;
		
		
		
	}
	
	public void testPylons(){
		Quaternion quaternion = new Quaternion(0.28F,0F,0.95F,0F);
		SoldierPatrolPylon pylon1 = (SoldierPatrolPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(-48, 4, -2798), quaternion);
		pylon1.setPylonFaction(Factions.Imperial);
		pylon1.setSpawnLevel(3);
	}
	
	public void spawnGeneral(){
		Quaternion quaternion = new Quaternion(0.28F,0F,0.95F,0F);
		SoldierPatrolPylon pylon1 = (SoldierPatrolPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(-48, 4, -2798), quaternion);		
	}
	
	public void spawnDefensiveQuestOfficer(){
		Quaternion quaternion = new Quaternion(0.28F,0F,0.95F,0F);
		SoldierPatrolPylon pylon1 = (SoldierPatrolPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(-48, 4, -2798), quaternion);		
	}
	
	public void spawnOffensiveCamps(){
		Quaternion quaternion = new Quaternion(0.28F,0F,0.95F,0F);
		SoldierPatrolPylon pylon1 = (SoldierPatrolPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(-48, 4, -2798), quaternion);		
	}
	
	@Override
	public void shutdown() {
		
	}
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0,
			Map<Integer, INetworkRemoteEvent> arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	// Test methods
	public void buildPylon1(){
		
	}

	public int getInvasionPhase() {
		return invasionPhase;
	}

	public void setInvasionPhase(int invasionPhase) {
		this.invasionPhase = invasionPhase;
	}
	
	public class InvasionLocation {
		
		public static final int Keren = 0;
		public static final int Bestine = 1;
		public static final int Dearic = 2;
		
	}
	
}
