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
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoSession;

import protocol.swg.CommPlayerMessage;
import protocol.swg.UpdateContainmentMessage;
import resources.common.OutOfBand;
import resources.datatables.FactionStatus;
import resources.datatables.Factions;
import resources.datatables.GcwType;
import resources.datatables.Options;
import resources.datatables.WeaponType;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.installation.InstallationMessageBuilder;
import resources.objects.installation.InstallationObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.ai.AIActor;
import services.ai.TurretAIActor;
import services.gcw.GCWSpawner.PatrolRoute;
import services.gcw.GCWPylon.PylonType;
import services.gcw.GCWSpawner.SpawnerType;
import main.NGECore;
import engine.clients.Client;
import engine.resources.scene.Planet;
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
	private CreatureObject defendingGeneral = null;
	private CreatureObject defendingQuestOfficer = null;
	
	private int invasionLocation=InvasionLocation.Dearic;
	private Point3D invasionLocationCenter;
	private int invadingFaction=Factions.Imperial;
	private int defendingFaction=Factions.Rebel;
	private int invasionPhase=2; // Test phase 2 
	
	private long temptimestartreference = 0L;
	private boolean message1sent = false;
	private boolean message2sent = false;
	private boolean message3sent = false;
	private boolean message4sent = false;
	
	private ScheduledFuture<?> eventManagerTask = null; 
	
	private Map<Integer,CreatureObject> invadingCreatures = new ConcurrentHashMap<Integer,CreatureObject>();
	private int invaderRegistryCounter = 0;
	private Map<Integer,CreatureObject> defendingCreatures = new ConcurrentHashMap<Integer,CreatureObject>();
	private int defenderRegistryCounter = 0;
	private Vector<TangibleObject> defensiveBanners = new Vector<TangibleObject>();
	
	public InvasionService(final NGECore core) {
		this.core = core;	
				
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			testPylons();
			// temp time reference, will be server time later
			temptimestartreference = System.currentTimeMillis();
			scheduleInvasionEventManager();
		}, 30, TimeUnit.SECONDS);	// Start invasion phase 2 after 30 seconds from server start
		
		
	}
	
	public void scheduleInvasionEventManager(){
		
		eventManagerTask = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					invasionEventManager();	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, 10, 2000, TimeUnit.MILLISECONDS);
	}
	
	public void invasionEventManager(){
		
		// Determine planet here
		
		setInvasionLocationCenter();
		
		if (invasionPhase==2 && System.currentTimeMillis()>temptimestartreference + 90000 && ! message1sent){
			sendDefenderMessage1();
			message1sent = true;
			System.out.println("Defender msg1 sent ");
		}
		if (invasionPhase==2 && System.currentTimeMillis()>temptimestartreference + 600000 && ! message2sent){
			sendDefenderMessage2();
			message2sent = true;
			invasionPhase=3;
			defendingGeneral.setOptionsBitmask(Options.ATTACKABLE);
			makeDefensiveGeneralDefenderForNPCs();
			System.out.println("Defender msg2 sent "); // Local server gives only 30 mins time, so its 20 mins for this phase
		}
		if (invasionPhase==3 && System.currentTimeMillis()>temptimestartreference + 1000000 && ! message3sent){
			sendDefenderMessage3();
			message3sent = true;
			sendInvaderLostMessage();
			sendDefenderWonMessage();
			System.out.println("Defender msg3 sent ");
		}
		
		if (defendingGeneral!=null){
			if (invasionPhase==3 && (defendingGeneral.getPosture()==13 || defendingGeneral.getPosture()==14) && ! message4sent){
				sendInvaderWonMessage();
				sendDefenderLostMessage();
				message4sent = true;
				invasionPhase=4; // Post-Invasion phase
				System.out.println("Defender msg4 sent ");
				
				// Take care of GCW points
				grantGCWPoints(invadingFaction,1000);
				grantGCWPoints(defendingFaction,100);
				// ToDo: tokens
							
				// Clean Up
				wipeInvaders();
				wipeDefenders();
			}
		}		
	}
	
	public void grantGCWPoints(int faction, int gcwPoints){
		String factionName = getFactionName(faction);
		Vector<CreatureObject> allPlayers = core.simulationService.getAllNearSameFactionPlayers(500,invasionLocationCenter, getInvasionPlanet(invasionLocation), factionName);
		for (CreatureObject player : allPlayers) {			
			if (player.getFaction().length() > 0 && player.getFactionStatus() > FactionStatus.OnLeave) {
				core.gcwService.addGcwPoints(player, gcwPoints, GcwType.Enemy);
			}			
		}		
	}
	
	public void registerInvader(CreatureObject registrant){
		invadingCreatures.put(invaderRegistryCounter++, registrant);
	}
	
	public void registerDefender(CreatureObject registrant){
		defendingCreatures.put(invaderRegistryCounter++, registrant);
	}
	
	public void wipeInvaders(){
		
		for (Map.Entry<Integer, CreatureObject> entry : invadingCreatures.entrySet()) {
			CreatureObject creature = entry.getValue();
			AIActor actor = (AIActor)creature.getAttachment("AI");
			core.simulationService.remove(creature, creature.getWorldPosition().x, creature.getWorldPosition().z, true); // Make sure
			actor.destroyActor();
		}		
				
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			for (Map.Entry<Integer, CreatureObject> entry : invadingCreatures.entrySet()) {
				CreatureObject creature = entry.getValue();
				core.simulationService.remove(creature, creature.getWorldPosition().x, creature.getWorldPosition().z, true); // Make sure
				core.objectService.destroyObject(creature.getObjectID());	
			}
			System.out.println("Destroyed all invaders.");
		}, 15, TimeUnit.SECONDS);	// Make sure remaining AI is finished with moves that might make them re-appear
	}
	
	public void wipeDefenders(){
		
		for (TangibleObject banner : defensiveBanners){
			core.objectService.destroyObject(banner.getObjectID());
		}
		
		core.objectService.destroyObject(defendingGeneral.getObjectID());
		core.objectService.destroyObject(defendingQuestOfficer.getObjectID());
		
//		for (Map.Entry<Integer, CreatureObject> entry : defendingCreatures.entrySet()) {
//			CreatureObject creature = entry.getValue();
//			AIActor actor = (AIActor)creature.getAttachment("AI");
//			core.simulationService.remove(creature, creature.getWorldPosition().x, creature.getWorldPosition().z, true); // Make sure
//			actor.destroyActor();
//		}		
				
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			for (Map.Entry<Integer, CreatureObject> entry : defendingCreatures.entrySet()) {
				CreatureObject creature = entry.getValue();
				core.simulationService.remove(creature, creature.getWorldPosition().x, creature.getWorldPosition().z, true); // Make sure
				core.objectService.destroyObject(creature.getObjectID());	
			}
			System.out.println("Destroyed all defenders.");
		}, 15, TimeUnit.SECONDS);	// Make sure remaining AI is finished with moves that might make them re-appear
				
	}
	
	
	public void spawnOffensiveAssets(){
		spawnOffensiveCamps();
	}
	
	public void spawnDefensiveAssets(){
		spawnDefendingGeneral();
		spawnDefensiveQuestOfficer();
		spawnBarricades();
		spawnTurrets();
		spawnTower();
		spawnBanners();
		spawnSupplyTerminals();
	}
	
	public void spawnSupplyTerminals(){
		
	}
	
	public void testPylons(){
		
		spawnDefensiveAssets();
		spawnOffensiveAssets();
		
		Quaternion quaternion = new Quaternion(0.28F,0F,0.95F,0F);

		
		GCWSpawner pylon1 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(-48, 6, -2798), quaternion);
		core.simulationService.add(pylon1, pylon1.getPosition().x, pylon1.getPosition().z, true);
		pylon1.setPatrolRoute(PatrolRoute.Dearic1);
		pylon1.setSpawnerType(PylonType.SoldierPatrol);
		pylon1.setSpawnerFaction(invadingFaction);
		pylon1.setSpawnLevel(3);
		
		GCWSpawner pylon2 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(-51, 6, -2798), quaternion);
		core.simulationService.add(pylon2, pylon2.getPosition().x, pylon2.getPosition().z, true);
		pylon2.setPatrolRoute(PatrolRoute.Dearic1);
		pylon2.setSpawnerType(PylonType.VehiclePatrol);
		pylon2.setSpawnerFaction(invadingFaction);
		pylon2.setSpawnLevel(3);
		
//		GCWPylon pylon4 = (GCWPylon) core.objectService.createObject("object/tangible/gcw/shared_gcw_city_spawner.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(506, 6, -3025), quaternion);
//		core.simulationService.add(pylon4, pylon4.getPosition().x, pylon4.getPosition().z, true);
//		pylon4.setPylonType(PylonType.SoldierPatrol);
//		pylon4.setPylonFaction(invadingFaction);
//		pylon4.setSpawnLevel(3);
		
		GCWSpawner pylon3 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(5, 6, -2683), quaternion);
		core.simulationService.add(pylon3, pylon3.getPosition().x, pylon3.getPosition().z, true);
		pylon3.setPatrolRoute(PatrolRoute.Dearic2);
		pylon3.setSpawnerType(SpawnerType.SoldierPatrol);
		pylon3.setSpawnerFaction(invadingFaction);
		pylon3.setSpawnLevel(3);
				
		GCWSpawner pylon4 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(141, 6, -2517), quaternion);
		core.simulationService.add(pylon4, pylon4.getPosition().x, pylon4.getPosition().z, true);
		pylon4.setPatrolRoute(PatrolRoute.Dearic3);
		pylon4.setSpawnerType(SpawnerType.SoldierPatrol);
		pylon4.setSpawnerFaction(invadingFaction);
		pylon4.setSpawnLevel(3);
		
		GCWSpawner pylon5 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(275, 6, -2481), quaternion);
		core.simulationService.add(pylon5, pylon5.getPosition().x, pylon5.getPosition().z, true);
		pylon5.setPatrolRoute(PatrolRoute.Dearic4);
		pylon5.setSpawnerType(SpawnerType.SoldierPatrol);
		pylon5.setSpawnerFaction(invadingFaction);
		pylon5.setSpawnLevel(3);
		
		GCWSpawner pylon5a = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(271, 6, -2481), quaternion);
		core.simulationService.add(pylon5a, pylon5a.getPosition().x, pylon5a.getPosition().z, true);
		pylon5a.setPatrolRoute(PatrolRoute.Dearic11);
		pylon5a.setSpawnerType(SpawnerType.SiegeVehiclePatrol);
		pylon5a.setSpawnerFaction(invadingFaction);
		pylon5a.setSpawnLevel(3);
		
		GCWSpawner pylon6 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(519, 6, -2452), quaternion);
		core.simulationService.add(pylon6, pylon6.getPosition().x, pylon6.getPosition().z, true);
		pylon6.setPatrolRoute(PatrolRoute.Dearic5);
		pylon6.setSpawnerType(SpawnerType.SoldierPatrol);
		pylon6.setSpawnerFaction(invadingFaction);
		pylon6.setSpawnLevel(3);
		
		GCWSpawner pylon7 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(845, 6, -2550), quaternion);
		core.simulationService.add(pylon7, pylon7.getPosition().x, pylon7.getPosition().z, true);
		pylon7.setPatrolRoute(PatrolRoute.Dearic6);
		pylon7.setSpawnerType(SpawnerType.SoldierPatrol);
		pylon7.setSpawnerFaction(invadingFaction);
		pylon7.setSpawnLevel(3);
		
		GCWSpawner pylon8 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(1082, 6, -2855), quaternion);
		core.simulationService.add(pylon8, pylon8.getPosition().x, pylon8.getPosition().z, true);
		pylon8.setPatrolRoute(PatrolRoute.Dearic7);
		pylon8.setSpawnerType(SpawnerType.SoldierPatrol);
		pylon8.setSpawnerFaction(invadingFaction);
		pylon8.setSpawnLevel(3);
		
		GCWSpawner pylon9 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(946, 6, -3075), quaternion);
		core.simulationService.add(pylon9, pylon9.getPosition().x, pylon9.getPosition().z, true);
		pylon9.setPatrolRoute(PatrolRoute.Dearic8);
		pylon9.setSpawnerType(SpawnerType.SoldierPatrol);
		pylon9.setSpawnerFaction(invadingFaction);
		pylon9.setSpawnLevel(3);
		
		GCWSpawner pylon10 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(314, 43, -3546), quaternion);
		core.simulationService.add(pylon10, pylon10.getPosition().x, pylon10.getPosition().z, true);
		pylon10.setPatrolRoute(PatrolRoute.Dearic9);
		pylon10.setSpawnerType(SpawnerType.SoldierPatrol);
		pylon10.setSpawnerFaction(invadingFaction);
		pylon10.setSpawnLevel(3);
				
		// to test turret // object/tangible/destructible/shared_gcw_city_construction_beacon.iff
//		GCWSpawner pylon10a = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(618, 4, -2963), quaternion);
//		core.simulationService.add(pylon10a, pylon10a.getPosition().x, pylon10a.getPosition().z, true);
//		pylon10a.setPatrolRoute(PatrolRoute.Dearic10);
//		pylon10a.setSpawnerType(PylonType.SoldierPatrol);
//		pylon10a.setSpawnerFaction(invadingFaction);
//		pylon10a.setSpawnLevel(3);
		
				
		// Defenders  object/tangible/ground_spawning/shared_patrol_spawner.iff
		GCWSpawner pylonD1 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(410.98F, 6, -2988.05F), quaternion);
		core.simulationService.add(pylonD1, pylonD1.getPosition().x, pylonD1.getPosition().z, true);
		pylonD1.setSpawnerType(PylonType.SoldierDefense);
		pylonD1.setSpawnerFaction(defendingFaction);
		pylonD1.setSpawnLevel(3);
		
		GCWSpawner pylonD2 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(500, 6, -2954), quaternion);
		core.simulationService.add(pylonD2, pylonD2.getPosition().x, pylonD2.getPosition().z, true);
		pylonD2.setSpawnerType(SpawnerType.SoldierDefense);
		pylonD2.setSpawnerFaction(defendingFaction);
		pylonD2.setSpawnLevel(3);
		
		GCWSpawner pylonD3 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(548, 6, -2941), quaternion);
		core.simulationService.add(pylonD3, pylonD3.getPosition().x, pylonD3.getPosition().z, true);
		pylonD3.setSpawnerType(SpawnerType.SoldierDefense);
		pylonD3.setSpawnerFaction(defendingFaction);
		pylonD3.setSpawnLevel(3);
		
		GCWSpawner pylonD4 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(449F, 6, -3077), quaternion);
		core.simulationService.add(pylonD4, pylonD4.getPosition().x, pylonD4.getPosition().z, true);
		pylonD4.setSpawnerType(SpawnerType.SoldierDefense);
		pylonD4.setSpawnerFaction(defendingFaction);
		pylonD4.setSpawnLevel(3);
		
		
		GCWSpawner pylonD5 = (GCWSpawner) core.objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, core.terrainService.getPlanetByName("talus"), new Point3D(339F, 6, -2742), quaternion);
		core.simulationService.add(pylonD5, pylonD5.getPosition().x, pylonD5.getPosition().z, true);
		pylonD5.setSpawnerType(SpawnerType.SoldierDefense);
		pylonD5.setSpawnerFaction(defendingFaction);
		pylonD5.setSpawnLevel(3);
		
		
		
	}
	
	public void spawnDefendingGeneral(){
		System.out.println("Spawn general");
		String generalTemplate = "";
		if (invadingFaction==Factions.Imperial){
			generalTemplate = "rebel_invasion_general";
		}
		if (invadingFaction==Factions.Rebel){
			generalTemplate = "imp_invasion_general";
		}
		
		if (invasionLocation==InvasionLocation.Keren){
			defendingGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "naboo", 0L, 1786, 6, 2511, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Bestine){
			defendingGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "tatooine", 0L, -1214, 6, -3626, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Dearic){
			defendingGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "talus", 0L, 480.071F, 6, -3006.851F, 1, 0);			
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
			defendingQuestOfficer = (CreatureObject) NGECore.getInstance().staticService.spawnObject(questOfficerTemplate, "naboo", 0L, 1787, 6, 2512, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Bestine){ 
			defendingQuestOfficer = (CreatureObject) NGECore.getInstance().staticService.spawnObject(questOfficerTemplate, "tatooine", 0L, -1195, 6, -3620, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Dearic){ 
			defendingQuestOfficer = (CreatureObject) NGECore.getInstance().staticService.spawnObject(questOfficerTemplate, "talus", 0L, 478, 6, -3023, 0, 1);			
		}		
	}
	
	public void spawnOffensiveCamps(){
		Quaternion quaternion = new Quaternion(0.00F,0F,1.00F,0F);
		String campTemplate = "object/building/military/shared_gcw_battlefield_base.iff";	
		//String campTemplate = "object/building/poi/shared_gcw_rebel_clone_tent_small.iff"; // LOL why doesn't this spawn?!?!?!
		BuildingObject camp = (BuildingObject) core.objectService.createObject(campTemplate, 0, core.terrainService.getPlanetByName("talus"), new Point3D(-890,9, -2994), quaternion);
		//TangibleObject camp = (TangibleObject)  core.objectService.createObject(campTemplate, 0, core.terrainService.getPlanetByName("talus"), new Point3D(-890,9, -2994), quaternion);
		core.simulationService.add(camp, camp.getPosition().x, camp.getPosition().z, true);
		System.out.println("checkForObject: " + core.simulationService.checkForObject(30, camp));
		System.out.println("camp: " + camp.getTemplate());
	}
	
	public void spawnBarricades(){
		
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 480.05F, 6, -3009.93F, 1.0F, 0F, 50000);
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 483.42F, 6, -3007.00F, -0.7F, 0.7F, 50000);
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 476.95F, 6, -3006.85F, -0.69F, 0.716F, 50000);
		
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 410.82F, 6, -3012.75F, 1.00F, 0.00F, 50000);
		
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 431.76F, 6, -3072.4F, 0.41F, 0.91F, 50000);
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 444.5F, 6, -3074.5F, 0.41F, 0.91F, 50000);
		
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 509.8F, 6, -2955.06F, 0.72F, 0.69F, 50000); // Bank
		
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 552.81F, 6, -2938.06F, 0.72F, 0.69F, 50000);
		
		
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 387.98F, 6, -2742.24F, 0.032F, 0.999F, 50000);
		createBarricade("object/tangible/destructible/shared_gcw_rebel_barricade.iff", 332.25F, 6, -2741.97F, 0.032F, 0.999F, 50000);
	}
	
	public void spawnTurrets(){
	
		createTurret("object/installation/turret/gcw/shared_adv_turret_dish_large_heat.iff", 557, 6, -3028, 0.62F, 0.78F, 2000, 2400);
		createTurret("object/installation/turret/gcw/shared_adv_turret_dish_large_heat.iff", 405.09F, 6, -3020.4F, 1.00F, 0.00F, 2000, 2400);
		createTurret("object/installation/turret/gcw/shared_adv_turret_dish_large_heat.iff", 423.5F, 6, -3059.5F, 0.97F, -0.23F, 2000, 2400);
		createTurret("object/installation/turret/gcw/shared_adv_turret_dish_large_heat.iff", 498.87F, 6, -2950.12F, 0.72F, 0.69F, 2000, 2400);
		// object/tangible/destructible/shared_gcw_rebel_turret.iff crashes the client, not fixable	
		
		createTurret("object/installation/turret/gcw/shared_adv_turret_dish_large_heat.iff", 378.07F, 6, -2742.26F, -0.023F, 0.999F, 2000, 2400);
	} 
	
	public void createTurret(String turretTemplate, float x, float y, float z, float qy, float qw, int minDamage, int maxDamage){
		Quaternion quaternion = new Quaternion(qy,0F,qw,0F);	
		InstallationObject turret = (InstallationObject) core.objectService.createObject(turretTemplate, 0, getInvasionPlanet(invasionLocation), new Point3D(x, y, z), quaternion);
		//TangibleObject turret = (TangibleObject) core.objectService.createObject(turretTemplate, 0, getInvasionPlanet(invasionLocation), new Point3D(x, y, z), quaternion);
		core.simulationService.add(turret, turret.getPosition().x, turret.getPosition().z, true);
		String customName = "Rebel Turret";
		String factionString = "rebel";
		if (defendingFaction==Factions.Imperial){
			customName = "Imperial Turret";
			factionString = "imperial";
		}
		turret.setCustomName(customName);
//		turret.setCustomName2(customName);
		
//		turret.setStfFilename(stfFilename);
//		turret.setStfName(stfName);
		
//		InstallationMessageBuilder messageBuilder = new InstallationMessageBuilder();
//		// steal a session for lack of a better method
//		Client stolenClient = null;
//		synchronized(core.getActiveConnectionsMap()) {
//			for (Client client : core.getActiveConnectionsMap().values()) {
//				if (client.getParent() != null && client.getParent() instanceof CreatureObject) {					
//					if (((CreatureObject)client.getParent()).isPlayer())
//						stolenClient = client;
//				}
//			}
//		}
//		if (stolenClient!=null)
//			stolenClient.getSession().write(messageBuilder.buildCustomNameDelta(turret,customName)); 
		
		turret.setOptionsBitmask(Options.ATTACKABLE | Options.QUEST);
		turret.setFaction(factionString);
		turret.setFactionStatus(FactionStatus.Combatant);
		turret.setMaximumCondition(50000);
		TurretAIActor actor = new TurretAIActor(turret, turret.getPosition(), scheduler);
		turret.setAttachment("AI", actor);
				
		WeaponObject turretWeapon = (WeaponObject) core.objectService.createObject("object/weapon/ranged/turret/shared_turret_heat.iff", turret.getPlanet());
		turretWeapon.setAttackSpeed(5.0F);
		//turretWeapon.setAttackSpeed(0.50F);
		turretWeapon.setWeaponType(WeaponType.RIFLE);
		turretWeapon.setMaxRange(60.0F); // setMaxRange(60.0F); 14.0F
		turretWeapon.setDamageType("energy");
		turretWeapon.setMinDamage(minDamage);
		turretWeapon.setMaxDamage(maxDamage);
		turret.add(turretWeapon);
		turret.setAttachment("TurretWeapon",turretWeapon.getObjectID());
		Set<Client> newObservers = new HashSet<Client>(turret.getObservers());
		for(Client c : newObservers) {
			UpdateContainmentMessage ucm = new UpdateContainmentMessage(turretWeapon.getObjectID(), turret.getObjectID(), 4);
			c.getSession().write(ucm.serialize());
		}
	}
	
	public void createBarricade(String barricadeTemplate, float x, float y, float z, float qy, float qw, int maxCondition){
		Quaternion quaternion = new Quaternion(qy,0F,qw,0F);		
		TangibleObject barricade = (TangibleObject) core.objectService.createObject(barricadeTemplate, 0, getInvasionPlanet(invasionLocation),  new Point3D(x, y, z), quaternion);
		core.simulationService.add(barricade, barricade.getPosition().x, barricade.getPosition().z, true);
		String customName = "Rebel Barricade";
		String factionString = "rebel";
		if (defendingFaction==Factions.Imperial){
			customName = "Imperial Barricade";
			factionString = "imperial";
		}
		barricade.setCustomName(customName);
		barricade.setOptionsBitmask(Options.ATTACKABLE | Options.QUEST);
		barricade.setFaction(factionString);
		barricade.setFactionStatus(FactionStatus.Combatant);
		barricade.setMaximumCondition(maxCondition);
		
		final Future<?>[] fut = {null};
		fut[0] = scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { 
				try {
					if (NGECore.getInstance().objectService.getObject(barricade.getObjectID())==null){
		                Thread.yield();
		                fut[0].cancel(false);
					}
								
					Vector<CreatureObject> nearAllies = NGECore.getInstance().simulationService.getAllNearSameFactionNPCs(5, barricade);					
					for (CreatureObject ally : nearAllies){
						if (! ally.hasBuff("barricade_defender")){
							core.buffService.addBuffToCreature(ally, "barricade_defender", ally);
						} else {
							/*System.out.println("HASBUFF " + nearAllies.size());*/
//							if (ally.getClient()!=null)
//								System.out.println("ALLY: " + ally.getCustomName());
						}
					} // towFacGuardDefenseItem
					Thread.currentThread().interrupt();
				} catch (Exception e) {
					System.err.println("Exception in Barricade thread " + e.getMessage());
			}
			}
		}, 5, 2, TimeUnit.SECONDS);
	}
	// gcw_general_resistance_stack_rebel
	// for Towers: tower_defender
	
	public void spawnTower(){
		createTower(548.8F, 6, -2872.06F, 0.72F, 0.69F, 100000);
	}
	
	public void createTower(float x, float y, float z, float qy, float qw, int maxCondition){
		Quaternion quaternion = new Quaternion(qy,0F,qw,0F);		
		String towerTemplate = "object/tangible/destructible/shared_gcw_rebel_tower.iff";
		String factionString = "rebel";
		String towerString = "Rebel Tower";
		if (defendingFaction==Factions.Imperial){
			towerTemplate = "object/tangible/destructible/shared_gcw_imperial_tower.iff";
			factionString = "imperial";
			towerString = "Imperial Tower";
		}		
		TangibleObject tower = (TangibleObject) core.objectService.createObject(towerTemplate, 0, getInvasionPlanet(invasionLocation),  new Point3D(x, y, z), quaternion);
		core.simulationService.add(tower, tower.getPosition().x, tower.getPosition().z, true);
		tower.setCustomName(towerString);
		tower.setOptionsBitmask(Options.ATTACKABLE | Options.QUEST);
		tower.setFaction(factionString);
		tower.setFactionStatus(FactionStatus.Combatant);
		tower.setMaximumCondition(maxCondition);
		
		final Future<?>[] fut = {null};
		fut[0] = scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { 
				try {
					if (NGECore.getInstance().objectService.getObject(tower.getObjectID())==null){
		                Thread.yield();
		                fut[0].cancel(false);
					}
								
					Vector<CreatureObject> nearAllies = NGECore.getInstance().simulationService.getAllNearSameFactionNPCs(500, tower);					
					for (CreatureObject ally : nearAllies){
						if (!ally.isPlayer()){
							if (! ally.hasBuff("tower_defender")){
								core.buffService.addBuffToCreature(ally, "tower_defender", ally);
							} 
						}
					} 
					Thread.currentThread().interrupt();
				} catch (Exception e) {
					System.err.println("Exception in Tower thread " + e.getMessage());
			}
			}
		}, 5, 2, TimeUnit.SECONDS);
	}
	
	
	public void spawnBanners(){
		
		// Starport
		createBanner(329.34F, 6, -2936.67F, 0.71F, 0.7F);
		createBanner(329.34F, 6, -2925.15F, 0.71F, 0.7F);
		
		createBanner(414.98F, 6, -3027.86F, 0.71F, -0.7F);
		createBanner(414.98F, 6, -2988.05F, 0.71F, -0.7F);
		createBanner(448.98F, 6, -3028.05F, 0.68F, -0.73F);
		createBanner(448.98F, 6, -2987.95F, 0.70F, 0.71F);
		
		createBanner(369.96F, 6, -2944.02F, 1.00F, 0.0F);
		
		createBanner(537.96F, 6, -3038.97F, 0.00F, 1.0F);
		
		createBanner(569.96F, 6, -2940.37F, 0.71F, -0.69F);
		
		createBanner(476.09F, 6, -2955.1F, 0.71F, 0.69F);
		
		createBanner(471.56F, 6, -3004.66F, 1.0F, 0.00F);
		createBanner(488.40F, 6, -3004.66F, 1.0F, 0.00F);
		
		createBanner(524.88F, 6.51F, -3004.96F, 0.927F, 0.374F);
		createBanner(525.03F, 6.51F, -2974.90F, 0.91F, -0.41F);
		createBanner(555.13F, 6.51F, -2975.05F, 0.93F, 0.37F);
		createBanner(555.15F, 6.51F, -3004.96F, 0.39F, 0.922F);
		
	}
	
	public void createBanner(float x, float y, float z, float qy, float qw){
		Quaternion quaternion = new Quaternion(qy,0F,qw,0F);	 //object/installation/battlefield/destructible  shared_banner_rebel_style_01.iff	
		String bannerTemplate = "object/tangible/gcw/shared_flip_banner_onpole_rebel.iff";  //object/tangible/event_perk/shared_banner_rebel_style_01.iff
		String factionString = "rebel_banner";
		if (defendingFaction==Factions.Imperial){
			bannerTemplate = "object/tangible/gcw/shared_flip_banner_onpole_imperial.iff";
			factionString = "imperial_banner";
		}		
		TangibleObject banner = (TangibleObject) core.objectService.createObject(bannerTemplate, 0, getInvasionPlanet(invasionLocation),  new Point3D(x, y, z), quaternion);
		core.simulationService.add(banner, banner.getPosition().x, banner.getPosition().z, true);
		banner.setStfFilename("obj_n");
		banner.setStfName(factionString);
		banner.setDetailFilename("obj_d");
		banner.setDetailName(factionString);
		defensiveBanners.add(banner);
	}
	
	public int getInvasionPhase() {
		return invasionPhase;
	}

	public void setInvasionPhase(int invasionPhase) {
		this.invasionPhase = invasionPhase;
	}
		
	public void sendInvaderMessage1(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_attack_city_imperial_talus_dearic";	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_attack_city_rebel_talus_dearic";
		
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
				CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
				comm.setTime(2000);
				comm.setModel("object/mobile/shared_dressed_assassin_mission_giver_0" + Integer.toString(new Random().nextInt(2) + 1) + ".iff");
				client.getSession().write(comm.serialize());
			}			
		}
	}
	
	public void sendDefenderMessage1(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_rally_for_invasion_imperial_talus_dearic";	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_rally_for_invasion_rebel_talus_dearic";
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel(defendingGeneral.getTemplate());
					client.getSession().write(comm.serialize());
				}	
			}
		}
	}
	
	public void sendInvaderMessage2(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_attack_half_done_imperial_talus_dearic";	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_attack_city_rebel_talus_dearic";
		
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel("object/mobile/shared_dressed_assassin_mission_giver_0" + Integer.toString(new Random().nextInt(2) + 1) + ".iff");
					client.getSession().write(comm.serialize());
				}	
			}
		}
	}
	
	public void sendDefenderMessage2(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_defense_half_done_imperial_talus_dearic";	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_defense_half_done_rebel_talus_dearic";
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel(defendingGeneral.getTemplate());
					client.getSession().write(comm.serialize());
				}	
			}
		}
	}
	
	public void sendInvaderMessage3(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_attack_half_done_imperial_talus_dearic";	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_attack_city_rebel_talus_dearic";
		
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel("object/mobile/shared_dressed_assassin_mission_giver_0" + Integer.toString(new Random().nextInt(2) + 1) + ".iff");
					client.getSession().write(comm.serialize());
				}	
			}
		}
	}
	
	public void sendDefenderMessage3(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_defense_half_done_imperial_talus_dearic";	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_defense_half_done_rebel_talus_dearic";
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel(defendingGeneral.getTemplate());
					client.getSession().write(comm.serialize());
				}		
			}
		}
	}
	
	
	public void sendInvaderWonMessage(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_city_won_imperial_talus_dearic";	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_city_won_rebel_talus_dearic";
		
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel("object/mobile/shared_dressed_assassin_mission_giver_0" + Integer.toString(new Random().nextInt(2) + 1) + ".iff");
					client.getSession().write(comm.serialize());
				}	
			}
		}
	}
	
	public void sendDefenderWonMessage(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_won_attack_imeprial_talus_dearic";	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_won_attack_rebel_talus_dearic";
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel(defendingGeneral.getTemplate());
					client.getSession().write(comm.serialize());
				}	
			}
		}
	}
	
	public void sendInvaderLostMessage(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_city_lost_imperial_tatooine_bestine";	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_city_lost_rebel_talus_dearic";
		
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel("object/mobile/shared_dressed_assassin_mission_giver_0" + Integer.toString(new Random().nextInt(2) + 1) + ".iff");
					client.getSession().write(comm.serialize());
				}	
			}
		}
	}
	
	public void sendDefenderLostMessage(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_city_lost_imperial_talus_dearic";	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_city_lost_rebel_talus_dearic";
		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel(defendingGeneral.getTemplate());
					client.getSession().write(comm.serialize());
				}		
			}
		}
	}
	
	
	public Planet getInvasionPlanet(int location){
		Planet planet = null;
		switch (location){
			case InvasionLocation.Dearic : planet = core.terrainService.getPlanetByName("talus");
										   break;
			case InvasionLocation.Keren : planet = core.terrainService.getPlanetByName("naboo");
			  							   break;
			case InvasionLocation.Bestine : planet = core.terrainService.getPlanetByName("tatooine");
			  							   break;
		}
		return planet;
	}
	
	public void makeDefensiveGeneralDefenderForNPCs(){
		Vector<CreatureObject> enemies = core.simulationService.getAllNearNonSameFactionNPCs(50, defendingGeneral);
		for (CreatureObject enemy : enemies) {
			AIActor actor = (AIActor)enemy.getAttachment("AI");
			actor.addDefender(defendingGeneral);
		}
	}
	
	public float getDistanceToDefensiveGeneral(CreatureObject checker){
		float dist = checker.getWorldPosition().getDistance2D(defendingGeneral.getWorldPosition());
		return dist;
	}
	
	public CreatureObject getDefensiveGeneral(){
		return defendingGeneral;
	}

	public void setInvasionLocationCenter(){
		switch (invasionLocation){
			case InvasionLocation.Dearic : invasionLocationCenter = new Point3D(452,6,-3002);
										   break;
			case InvasionLocation.Keren :  invasionLocationCenter = new Point3D();
			  							   break;
			case InvasionLocation.Bestine : invasionLocationCenter = new Point3D();
			  							    break;
		}
	}
	
	public String getFactionName(int faction){
		if (faction==Factions.Rebel)
			return "rebel";
		if (faction==Factions.Imperial)
			return "imperial";
		return "neutral";
	}
	
	@Override
	public void shutdown() {
		
	}
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0,
			Map<Integer, INetworkRemoteEvent> arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public class InvasionLocation {
		
		public static final int Keren = 0;
		public static final int Bestine = 1;
		public static final int Dearic = 2;
		
	}
	
}
