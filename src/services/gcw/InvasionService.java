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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import protocol.swg.UpdateContainmentMessage;
import resources.datatables.FactionStatus;
import resources.datatables.Factions;
import resources.datatables.Options;
import resources.datatables.WeaponType;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.installation.InstallationObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.ai.AIActor;
import services.ai.TurretAIActor;
import services.gcw.GCWPylon.PatrolRoute;
import services.gcw.GCWPylon.PylonType;
import main.NGECore;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
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
	
	private CreatureObject invasionGeneral = null;
	
	private int invasionLocation=InvasionLocation.Dearic;
	private int invadingFaction=Factions.Imperial;
	private int defendingFaction=Factions.Rebel;
	private int invasionPhase=2; // Test phase 2 
	
	public InvasionService(final NGECore core) {
		this.core = core;	
	}
	
	
	public void spawnOffensiveAssets(){
		spawnOffensiveCamps();
	}
	
	public void spawnDefensiveAssets(){
		spawnGeneral();
		spawnDefensiveQuestOfficer();
		spawnBarricades();
		spawnTurrets();
		
	}
	
	public void testPylons(){
		
		Quaternion quaternion = new Quaternion(0.28F,0F,0.95F,0F);

		
		/*
		GCWPylon pylon1 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(-48, 6, -2798), quaternion);
		core.simulationService.add(pylon1, pylon1.getPosition().x, pylon1.getPosition().z, true);
		pylon1.setPatrolRoute(PatrolRoute.Dearic1);
		pylon1.setPylonType(PylonType.SoldierPatrol);
		pylon1.setPylonFaction(invadingFaction);
		pylon1.setSpawnLevel(3);
		
		GCWPylon pylon2 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(-51, 6, -2798), quaternion);
		core.simulationService.add(pylon2, pylon2.getPosition().x, pylon2.getPosition().z, true);
		pylon2.setPatrolRoute(PatrolRoute.Dearic1);
		pylon2.setPylonType(PylonType.VehiclePatrol);
		pylon2.setPylonFaction(invadingFaction);
		pylon2.setSpawnLevel(3);
		
//		GCWPylon pylon4 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(506, 6, -3025), quaternion);
//		core.simulationService.add(pylon4, pylon4.getPosition().x, pylon4.getPosition().z, true);
//		pylon4.setPylonType(PylonType.SoldierPatrol);
//		pylon4.setPylonFaction(invadingFaction);
//		pylon4.setSpawnLevel(3);
		
		GCWPylon pylon3 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(5, 6, -2683), quaternion);
		core.simulationService.add(pylon3, pylon3.getPosition().x, pylon3.getPosition().z, true);
		pylon3.setPatrolRoute(PatrolRoute.Dearic2);
		pylon3.setPylonType(PylonType.SoldierPatrol);
		pylon3.setPylonFaction(invadingFaction);
		pylon3.setSpawnLevel(3);
		
		
		GCWPylon pylon4 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(141, 6, -2517), quaternion);
		core.simulationService.add(pylon4, pylon4.getPosition().x, pylon4.getPosition().z, true);
		pylon4.setPatrolRoute(PatrolRoute.Dearic3);
		pylon4.setPylonType(PylonType.SoldierPatrol);
		pylon4.setPylonFaction(invadingFaction);
		pylon4.setSpawnLevel(3);
		
		GCWPylon pylon5 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(275, 6, -2481), quaternion);
		core.simulationService.add(pylon5, pylon5.getPosition().x, pylon5.getPosition().z, true);
		pylon5.setPatrolRoute(PatrolRoute.Dearic4);
		pylon5.setPylonType(PylonType.SoldierPatrol);
		pylon5.setPylonFaction(invadingFaction);
		pylon5.setSpawnLevel(3);
		
		GCWPylon pylon6 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(519, 6, -2452), quaternion);
		core.simulationService.add(pylon6, pylon6.getPosition().x, pylon6.getPosition().z, true);
		pylon6.setPatrolRoute(PatrolRoute.Dearic5);
		pylon6.setPylonType(PylonType.SoldierPatrol);
		pylon6.setPylonFaction(invadingFaction);
		pylon6.setSpawnLevel(3);
		
		GCWPylon pylon7 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(845, 6, -2550), quaternion);
		core.simulationService.add(pylon7, pylon7.getPosition().x, pylon7.getPosition().z, true);
		pylon7.setPatrolRoute(PatrolRoute.Dearic6);
		pylon7.setPylonType(PylonType.SoldierPatrol);
		pylon7.setPylonFaction(invadingFaction);
		pylon7.setSpawnLevel(3);
		
		GCWPylon pylon8 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(1082, 6, -2855), quaternion);
		core.simulationService.add(pylon8, pylon8.getPosition().x, pylon8.getPosition().z, true);
		pylon8.setPatrolRoute(PatrolRoute.Dearic7);
		pylon8.setPylonType(PylonType.SoldierPatrol);
		pylon8.setPylonFaction(invadingFaction);
		pylon8.setSpawnLevel(3);
		
		GCWPylon pylon9 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(946, 6, -3075), quaternion);
		core.simulationService.add(pylon9, pylon9.getPosition().x, pylon9.getPosition().z, true);
		pylon9.setPatrolRoute(PatrolRoute.Dearic8);
		pylon9.setPylonType(PylonType.SoldierPatrol);
		pylon9.setPylonFaction(invadingFaction);
		pylon9.setSpawnLevel(3);
		
		GCWPylon pylon10 = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(314, 43, -3546), quaternion);
		core.simulationService.add(pylon10, pylon10.getPosition().x, pylon10.getPosition().z, true);
		pylon10.setPatrolRoute(PatrolRoute.Dearic9);
		pylon10.setPylonType(PylonType.SoldierPatrol);
		pylon10.setPylonFaction(invadingFaction);
		pylon10.setSpawnLevel(3);
		*/
		
		
		// to test turret
		GCWPylon pylon10a = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(621, 4, -2996), quaternion);
		core.simulationService.add(pylon10a, pylon10a.getPosition().x, pylon10a.getPosition().z, true);
		pylon10a.setPatrolRoute(PatrolRoute.Dearic10);
		pylon10a.setPylonType(PylonType.SoldierPatrol);
		pylon10a.setPylonFaction(invadingFaction);
		pylon10a.setSpawnLevel(3);
		
		
		
		spawnDefensiveAssets();
		spawnOffensiveAssets();
	}
	
	public void spawnGeneral(){
		System.out.println("Spawn general");
		String generalTemplate = "";
		if (invadingFaction==Factions.Imperial){
			generalTemplate = "rebel_invasion_general";
		}
		if (invadingFaction==Factions.Rebel){
			generalTemplate = "imp_invasion_general";
		}
		
		if (invasionLocation==InvasionLocation.Keren){
			invasionGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "naboo", 0L, 1786, 6, 2511, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Bestine){
			invasionGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "tatooine", 0L, -1214, 6, -3626, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Dearic){
			invasionGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "talus", 0L, 480.071F, 6, -3006.851F, 1, 0);			
		}
			
	}
	
	public void spawnDefensiveQuestOfficer(){
		System.out.println("spawnDefensiveQuestOfficer");
		String questOfficerTemplate = "";
		if (invadingFaction==Factions.Imperial){
			questOfficerTemplate = "rebel_defensive_quest_officer";
		}
		if (invadingFaction==Factions.Rebel){
			questOfficerTemplate = "imp_defensive_quest_officer";
		}
		
		if (invasionLocation==InvasionLocation.Keren){ 
			invasionGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(questOfficerTemplate, "naboo", 0L, 1787, 6, 2512, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Bestine){ 
			invasionGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(questOfficerTemplate, "tatooine", 0L, -1195, 6, -3620, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Dearic){ 
			invasionGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(questOfficerTemplate, "talus", 0L, 478, 6, -3023, 0, 1);			
		}		
	}
	
	public void spawnOffensiveCamps(){
//		Quaternion quaternion = new Quaternion(0.28F,0F,0.95F,0F);
//		String campTemplate = "object/building/military/shared_gcw_battlefield_base.iff";	
//		//String campTemplate = "object/building/poi/shared_gcw_rebel_clone_tent_small.iff"; // LOL why doesn't this spawn?!?!?!
//		BuildingObject camp = (BuildingObject) core.objectService.createObject(campTemplate, 0, core.terrainService.getPlanetByName("talus"), new Point3D(-890,9, -2994), quaternion);
//		core.simulationService.add(camp, camp.getPosition().x, camp.getPosition().z, true);
	}
	
	public void spawnBarricades(){
		Quaternion quaternion = new Quaternion(0.28F,0F,0.95F,0F);
		String campTemplate = "object/tangible/destructible/shared_gcw_rebel_barricade.iff";	
		//String campTemplate = "object/building/poi/shared_gcw_rebel_clone_tent_small.iff"; // LOL why doesn't this spawn?!?!?!
		TangibleObject barricade = (TangibleObject) core.objectService.createObject(campTemplate, 0, core.terrainService.getPlanetByName("talus"), new Point3D( 475, 6, -3013), quaternion);
		core.simulationService.add(barricade, barricade.getPosition().x, barricade.getPosition().z, true);
		barricade.setCustomName("Rebel Barricade");
		//barricade.setOptions(Options.ATTACKABLE, true);
		barricade.setOptionsBitmask(Options.ATTACKABLE | Options.QUEST);
		barricade.setFaction("rebel");
		barricade.setFactionStatus(FactionStatus.Combatant);
		barricade.setMaximumCondition(50000);
		
	}
	
	public void spawnTurrets(){
		Quaternion quaternion = new Quaternion(0.28F,0F,0.95F,0F);
		String turretTemplate = "object/installation/turret/gcw/shared_adv_turret_dish_sm_heat.iff";	
		//String turretTemplate = "object/tangible/destructible/shared_gcw_rebel_turret.iff";	
		InstallationObject turret = (InstallationObject) core.objectService.createObject(turretTemplate, 0, core.terrainService.getPlanetByName("talus"), new Point3D( 557, 6, -3028), quaternion);
		core.simulationService.add(turret, turret.getPosition().x, turret.getPosition().z, true);
		turret.setCustomName("Rebel Turret");
		turret.setOptionsBitmask(Options.ATTACKABLE | Options.QUEST);
		turret.setFaction("rebel");
		turret.setFactionStatus(FactionStatus.Combatant);
		turret.setMaximumCondition(50000);
		TurretAIActor actor = new TurretAIActor(turret, turret.getPosition(), scheduler);
		turret.setAttachment("AI", actor);
		turret.setFaction("rebel");
		
		// const static int TURRET = 0x1004;		
		WeaponObject defaultWeapon = (WeaponObject) core.objectService.createObject("object/weapon/ranged/turret/shared_turret_heat.iff", turret.getPlanet());
		defaultWeapon.setAttackSpeed(1.0F);
		defaultWeapon.setWeaponType(WeaponType.RIFLE);
		defaultWeapon.setMaxRange(14.0F); // defaultWeapon.setMaxRange(60.0F);
		defaultWeapon.setDamageType("energy");
		defaultWeapon.setMinDamage(2000);
		defaultWeapon.setMaxDamage(2400);
		turret.add(defaultWeapon);
		turret.setAttachment("TurretWeapon",defaultWeapon.getObjectID());
		Set<Client> newObservers = new HashSet<Client>(turret.getObservers());
		for(Client c : newObservers) {
			UpdateContainmentMessage ucm = new UpdateContainmentMessage(defaultWeapon.getObjectID(), turret.getObjectID(), 4);
			c.getSession().write(ucm.serialize());
		}
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
