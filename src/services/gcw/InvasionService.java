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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import protocol.swg.ObjControllerMessage;
import protocol.swg.PlayClientEffectObjectTransformMessage;
import protocol.swg.objectControllerObjects.NpcConversationMessage;
import protocol.swg.objectControllerObjects.QuestTaskTimerMessage;
import resources.common.OutOfBand;
import resources.common.SpawnPoint;
import resources.datatables.FactionStatus;
import resources.datatables.Factions;
import resources.datatables.GalaxyStatus;
import resources.datatables.GcwType;
import resources.datatables.Options;
import resources.datatables.Posture;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import services.ai.AIActor;
import services.ai.states.AIState;
import services.ai.states.DeathState;
import services.ai.states.WithdrawalState;
import services.gcw.GCWSpawner.PatrolRoute;
import services.gcw.GCWPylon.PylonType;
import services.sui.SUIWindow;
import services.sui.SUIService.ListBoxType;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import main.NGECore;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.config.Config;
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

@SuppressWarnings("unused")
public class InvasionService implements INetworkDispatch {
	
	private NGECore core;	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);	
	protected final Object objectMutex = new Object();
	
	private boolean invasionScheduled = false;
	
	private CreatureObject invasionGeneral = null;
	private CreatureObject defendingGeneral = null;
	private CreatureObject defendingQuestOfficer = null;
	private Vector<CreatureObject> offensiveQuestOfficers = new Vector<CreatureObject>();
	private boolean defendingGeneralAlive = true;
	
	private int invasionLocation=-1;
	private String invasionPlanet="tatooine";
		
	private Point3D invasionLocationCenter;
	private int invadingFaction=Factions.Imperial;
	private int defendingFaction=Factions.Rebel;

	private int invasionPhase=1; // Test phase 1
	
	private Vector<GCWPylon> pylonList = new Vector<GCWPylon>();
	private Vector<TangibleObject> offensiveSupplyTerminals = new Vector<TangibleObject>();
	
	private long temptimestartreference = 0L;
	private boolean ph1_message1sent = false;
	private boolean ph1_message2sent = false;
	private boolean ph2_message1sent = false;
	private boolean ph2_message2sent = false;
	private boolean ph2_message3sent = false;
	private boolean ph2_message4sent = false;
	private boolean ph2_message5sent = false;
	private boolean ph2_message6sent = false;
	private boolean ph2_message7sent = false;
	
	private ScheduledFuture<?> defendingGeneralTask = null;
	private Map<CreatureObject, Integer> generalWarningLevelMap = new ConcurrentHashMap<CreatureObject, Integer>();
	
	private Map<Integer,CreatureObject> invadingCreatures = new ConcurrentHashMap<Integer,CreatureObject>();
	private int invaderRegistryCounter = 0;
	private Map<Integer,CreatureObject> defendingCreatures = new ConcurrentHashMap<Integer,CreatureObject>();
	private int defenderRegistryCounter = 0;
	private Map<Integer,CreatureObject> demoralizedSoldiers = new ConcurrentHashMap<Integer,CreatureObject>();
	private int demoralizedSoldierRegistryCounter = 0;
	private Map<Integer,CreatureObject> disabledVehicles = new ConcurrentHashMap<Integer,CreatureObject>();
	private int disabledVehicleRegistryCounter = 0;
	private Map<Integer,TangibleObject> phase2Objects = new ConcurrentHashMap<Integer,TangibleObject>(); 
	private int phase2ObjectRegistryCounter = 0;
	private Vector<TangibleObject> defensiveBanners = new Vector<TangibleObject>();
	private Vector<TangibleObject> defensiveSupplyTerminals = new Vector<TangibleObject>();
	private TangibleObject defensiveTable;
	private Vector<BuildingObject> offensiveCamps = new Vector<BuildingObject>();
	
	private List<SWGObject> offensiveClonerList = new Vector<SWGObject>();
	
	public InvasionService(final NGECore core) {
		this.core = core;	
		
		Config options = core.getOptions();
		if (options != null && options.getInt("ENABLE.INVASIONS") == 0){
			return;
		}
		
		
		// Utility code
//		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
//			arrangePylons(new Point3D(-1238.13F, 56, -3812.84F),100);
//		}, 20, TimeUnit.SECONDS);	
		
		
		
		// Local code to schedule a 20-30 minutes long phase2 invasion
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			
			//testPylons();
			// temp time reference, will be server time later
			while (NGECore.getInstance().getGalaxyStatus()!=GalaxyStatus.Online){
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			temptimestartreference = System.currentTimeMillis();
			scheduleInvasionManager();
			invasionScheduled=true;
			scheduleInvasionEventManager();
		}, 60, TimeUnit.SECONDS);	
		// Local code to schedule a 20 minutes long phase2 invasion
		
		
		// Server code to schedule invasions hourly
		/*
		for (;;){					
			while (!invasionScheduled) {
				Calendar now = Calendar.getInstance();
				// wait for the next full hour
				if (now.get(Calendar.MINUTE)==0 && ! invasionScheduled){	
					temptimestartreference = System.currentTimeMillis();
					scheduleInvasionManager();
					scheduleInvasionEventManager();
					invasionScheduled=true;
					try {
					if (!invasionScheduled)
						Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}								
			}
			try {
					if (invasionScheduled)
						Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		*/		
	}
		
	public void scheduleInvasionManager(){

		invasionLocation++; // Change Location of invasion
		if (invasionLocation>2)
			invasionLocation=0; // Rotate
		
		// Random for now
		invasionLocation = new Random().nextInt(2);
		
		invasionPlanet = getInvasionPlanet(invasionLocation).getName();
		
//		invasionLocation=InvasionLocation.Dearic;
//		invasionPlanet="talus";
		
//		invasionLocation=InvasionLocation.Keren;
//		invasionPlanet="naboo";
		
//		invasionLocation=InvasionLocation.Bestine;
//		invasionPlanet="tatooine";
		
		// Make sides random for now.
		// Later GCWService will determine the sides		
		if (new Random().nextFloat()<0.5){
			invadingFaction=Factions.Rebel;
			defendingFaction=Factions.Imperial;
		}
						
		if (invasionLocation==InvasionLocation.Keren){ 
			spawnPylonsKeren();
		}
		if (invasionLocation==InvasionLocation.Bestine){ 
			spawnPylonsBestine();
		}
		if (invasionLocation==InvasionLocation.Dearic){ 
			spawnPylonsDearic();			
		}
						
		spawnDefensiveAssets();
		spawnOffensiveAssets();
		
		setInvasionLocationCenter();		
	}
	
	public void scheduleInvasionEventManager(){

		final Future<?>[] evm = {null};
		evm[0] = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					if (!invasionScheduled){
						Thread.yield();
		                evm[0].cancel(false);
					}
					invasionEventManager();	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, 10, 2000, TimeUnit.MILLISECONDS);
	}
	
	public void invasionEventManager(){
		
		// First message at 0:00
		if (invasionPhase==1 && System.currentTimeMillis()>temptimestartreference + 10 && ! ph1_message1sent){
			send_Phase1_InvaderMessage1();
			send_Phase1_DefenderMessage1();
			ph1_message1sent = true;
			//System.out.println("Phase 1 msg1 sent ");
		}
		
		// Message at 15:00 -> 900000 seconds
		if (invasionPhase==1 && System.currentTimeMillis()>temptimestartreference + 50000 && ! ph1_message2sent){
			send_Phase1_InvaderMessage2();
			send_Phase1_DefenderMessage2();
			ph1_message2sent = true;
			//System.out.println("Phase 1 msg2 sent ");
		}
		
		// Message at 30:00 -> 1800000 seconds
		if (invasionPhase==1 && System.currentTimeMillis()>temptimestartreference + 60000 && ! ph2_message1sent){
			invasionPhase=2;
			preparePhase2();	
			//System.out.println("PHASE 2");
			send_Phase2_InvaderMessage1();
			send_Phase2_DefenderMessage1();
			ph2_message1sent = true;
			//System.out.println("Defender msg1 sent ");
		}
		
		// Message at 45:00 -> 2700000 seconds
		if (invasionPhase==2 && System.currentTimeMillis()>temptimestartreference + 900000 && ! ph2_message2sent){ // 1200000
			send_Phase2_InvaderMessage2();
			send_Phase2_DefenderMessage2();
			ph2_message2sent = true;
			invasionPhase=3;
			AIActor actor = (AIActor)defendingGeneral.getAttachment("AI");
			actor.setAIactive(true);
			defendingGeneral.setOptionsBitmask(Options.ATTACKABLE);
			
			defendingGeneral.updatePvpStatus();
			
			makeDefensiveGeneralDefenderForNPCs();
			//System.out.println("Defender msg2 sent "); // Local server gives only 30 mins time, so its ~15 mins for this phase
		}
			
		if (defendingGeneral!=null){
			
			if (invasionPhase==3 && defendingGeneral.getHealth()<defendingGeneral.getMaxHealth() &&(defendingGeneral.getPosture()!=13 || defendingGeneral.getPosture()!=14) && ! ph2_message6sent){
				sendInvaderGeneralAttackMessage();
				sendDefenderGeneralAttackMessage();
				ph2_message6sent = true;
			}
			

			//defenders won 59:00 mins 3600000-60000 = 3540000
			if (invasionPhase==2 && System.currentTimeMillis()>temptimestartreference + 1500000 && ! ph2_message3sent){ 
				sendDefenderWonMessage();
				sendInvaderLostMessage();				
				ph2_message3sent = true;
				invasionPhase=4;
				
				// Take care of GCW points
				grantGCWPoints(invadingFaction,100);
				grantGCWPoints(defendingFaction,1000);

				grantGCWTokens(invadingFaction,10);
				grantGCWTokens(defendingFaction,130);
				
				playVictoryMusic(defendingFaction);
				
				// set up delayed tasks
				//Clone Maps
				Map<Integer,CreatureObject> invadingCreaturesClone = new ConcurrentHashMap<Integer,CreatureObject>();
				Map<Integer,CreatureObject> defendingCreaturesClone = new ConcurrentHashMap<Integer,CreatureObject>();
				for (Map.Entry<Integer, CreatureObject> entry : getInvadingCreaturesEntrySet()) {
					invadingCreaturesClone.put(entry.getKey(), entry.getValue());
				}
				for (Map.Entry<Integer, CreatureObject> entry : getDefendingCreaturesEntrySet()) {
					defendingCreaturesClone.put(entry.getKey(), entry.getValue());
				}
				
				withdrawInvaders();
				
				Executors.newSingleThreadScheduledExecutor().schedule(() -> {	
					wipeInvaders(invadingCreaturesClone);
					wipeDefenders(defendingCreaturesClone); // Make sure all are deleted
				}, 360, TimeUnit.SECONDS);
				
				
				
				for (BuildingObject camp : offensiveCamps){
					core.simulationService.remove(camp, camp.getWorldPosition().x, camp.getWorldPosition().z, true);
					core.objectService.destroyObject(camp.getObjectID());
				}
				
				for (CreatureObject officer : offensiveQuestOfficers){
					core.simulationService.remove(officer, officer.getWorldPosition().x, officer.getWorldPosition().z, true);
					core.objectService.destroyObject(officer.getObjectID());
				}
				for (TangibleObject banner : defensiveBanners){
					core.objectService.destroyObject(banner.getObjectID());
				}
				
				if (defensiveTable!=null)
					core.objectService.destroyObject(defensiveTable.getObjectID());
				if (defendingGeneral!=null)
					core.objectService.destroyObject(defendingGeneral.getObjectID());
				if (defendingQuestOfficer!=null)
					core.objectService.destroyObject(defendingQuestOfficer.getObjectID());
				if (invasionGeneral!=null)
					core.objectService.destroyObject(invasionGeneral.getObjectID());
							
				deletePhase2Objects();
				generalWarningLevelMap.clear();
				offensiveSupplyTerminals = new Vector<TangibleObject>();
				invaderRegistryCounter = 0;
				defenderRegistryCounter = 0;
				defensiveBanners.clear();
				defensiveSupplyTerminals.clear();
				defensiveTable = null;
				defendingGeneralTask.cancel(true);
				defendingGeneralTask = null;
							
				invasionScheduled = false; // free Service
			}

			// General defeated
			if (invasionPhase==3 && (defendingGeneral.getPosture()==13 || defendingGeneral.getPosture()==14) && ! ph2_message4sent){
				sendInvaderWonMessage();
				sendDefenderLostMessage();
				ph2_message4sent = true;
				invasionPhase=4; // Post-Invasion phase
				//System.out.println("Defender msg4 sent ");
				
				// Take care of GCW points
				grantGCWPoints(invadingFaction,1000);
				grantGCWPoints(defendingFaction,100);

				grantGCWTokens(invadingFaction,130);
				grantGCWTokens(defendingFaction,10);
				
				playVictoryMusic(invadingFaction);
							
				// Clean Up
//				wipeInvaders();
//				wipeDefenders();
				
				// set up delayed tasks
				//Clone Maps
				Map<Integer,CreatureObject> invadingCreaturesClone = new ConcurrentHashMap<Integer,CreatureObject>();
				Map<Integer,CreatureObject> defendingCreaturesClone = new ConcurrentHashMap<Integer,CreatureObject>();
				for (Map.Entry<Integer, CreatureObject> entry : getInvadingCreaturesEntrySet()) {
					invadingCreaturesClone.put(entry.getKey(), entry.getValue());
				}
				for (Map.Entry<Integer, CreatureObject> entry : getDefendingCreaturesEntrySet()) {
					defendingCreaturesClone.put(entry.getKey(), entry.getValue());
				}
				
				withdrawInvaders();
				
				Executors.newSingleThreadScheduledExecutor().schedule(() -> {	
					wipeInvaders(invadingCreaturesClone);
					wipeDefenders(defendingCreaturesClone); // Make sure all are deleted
				}, 360, TimeUnit.SECONDS);
				
				
				
				for (BuildingObject camp : offensiveCamps){
					core.simulationService.remove(camp, camp.getWorldPosition().x, camp.getWorldPosition().z, true);
					core.objectService.destroyObject(camp.getObjectID());
				}
				
				for (CreatureObject officer : offensiveQuestOfficers){
					core.simulationService.remove(officer, officer.getWorldPosition().x, officer.getWorldPosition().z, true);
					core.objectService.destroyObject(officer.getObjectID());
				}
				for (TangibleObject banner : defensiveBanners){
					core.objectService.destroyObject(banner.getObjectID());
				}
				
				if (defensiveTable!=null)
					core.objectService.destroyObject(defensiveTable.getObjectID());
				if (defendingGeneral!=null)
					core.objectService.destroyObject(defendingGeneral.getObjectID());
				if (defendingQuestOfficer!=null)
					core.objectService.destroyObject(defendingQuestOfficer.getObjectID());
				if (invasionGeneral!=null)
					core.objectService.destroyObject(invasionGeneral.getObjectID());
							
				deletePhase2Objects();
				generalWarningLevelMap.clear();
				offensiveSupplyTerminals = new Vector<TangibleObject>();
				invaderRegistryCounter = 0;
				defenderRegistryCounter = 0;
				defensiveBanners.clear();
				defensiveSupplyTerminals.clear();
				defensiveTable = null;
				defendingGeneralTask.cancel(true);
				defendingGeneralTask = null;
				
				invasionScheduled = false; // free Service
			}
			
			// General at half health
			if (invasionPhase==3 && defendingGeneral.getHealth()<0.5*defendingGeneral.getMaxHealth() &&(defendingGeneral.getPosture()!=13 || defendingGeneral.getPosture()!=14) && ! ph2_message5sent){
				//sendInvaderGeneralHalfMessage();
				sendDefenderGeneralHalfMessage();
				ph2_message5sent = true;
			}
		} 	
	}
	
	public void withdrawInvaders(){
		for (Map.Entry<Integer, CreatureObject> entry : getInvadingCreaturesEntrySet()) {
			CreatureObject creature = entry.getValue();
			if (creature!=null){
				if (creature.getAttachment("AI")!=null){
					AIActor actor = (AIActor)creature.getAttachment("AI");
					if (actor!=null){			
						actor.setActorAlive(true);
						actor.setAIactive(true);
						actor.setIntendedPrimaryAIState(new WithdrawalState());
						actor.setCurrentState(new WithdrawalState());
						creature.setAttachment("isWithdrawn",1);
					}					
				}
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
	
	public void grantGCWTokens(int faction, int tokenamount){
		String factionName = getFactionName(faction);
		Vector<CreatureObject> allPlayers = core.simulationService.getAllNearSameFactionPlayers(500,invasionLocationCenter, getInvasionPlanet(invasionLocation), factionName);
		for (CreatureObject player : allPlayers) {			
			if (player.getFaction().length() > 0 && player.getFactionStatus() > FactionStatus.OnLeave) {

				String tokenTemplate = "object/tangible/gcw/shared_gcw_rebel_token.iff";
				String name = "item_gcw_rebel_token";
				String description = "item_gcw_rebel_token";
				if (player.getFaction().equals(Factions.Imperial)){
					tokenTemplate = "object/tangible/gcw/shared_gcw_imperial_token.iff";
					name = "item_gcw_imperial_token";
					description = "item_gcw_imperial_token";
				}
				TangibleObject token = (TangibleObject) core.objectService.createObject(tokenTemplate, player.getPlanet());
						
				token.setStfFilename("static_item_n");
				token.setStfName(name);
				token.setDetailFilename("static_item_d");
				token.setDetailName(description);

				token.setUses(tokenamount);
				
				player.sendSystemMessage("@static_item_n:"+name+" \\#32CD32 x" + tokenamount +" have been placed in your inventory \\#FFFFFF", (byte)0);
				core.playerService.giveItem(player, token);
			}			
		}
		
	}
	
	public void registerInvader(CreatureObject registrant){
		synchronized(invadingCreatures) {
	//		System.out.println("Registered! " + invaderRegistryCounter);
			invadingCreatures.put(invaderRegistryCounter++, registrant);
		}
	}
	
	public void registerDefender(CreatureObject registrant){
		synchronized(defendingCreatures) {
			defendingCreatures.put(defenderRegistryCounter++, registrant);
		}
	}
	
	public void registerDemoralizedSoldier(CreatureObject registrant){
		synchronized(demoralizedSoldiers) {
	//		System.out.println("Registered! " + invaderRegistryCounter);
			demoralizedSoldiers.put(demoralizedSoldierRegistryCounter++, registrant);
		}
	}
	
	public void registerDisabledVehicle(CreatureObject registrant){
		synchronized(disabledVehicles) {
	//		System.out.println("Registered! " + invaderRegistryCounter);
			disabledVehicles.put(disabledVehicleRegistryCounter++, registrant);
		}
	}
		
	public void wipeInvaders(Map<Integer,CreatureObject> invaderMap){
				
		for (BuildingObject camp : offensiveCamps){
			core.simulationService.remove(camp, camp.getWorldPosition().x, camp.getWorldPosition().z, true);
			core.objectService.destroyObject(camp.getObjectID());
		}
		
		for (CreatureObject officer : offensiveQuestOfficers){
			core.simulationService.remove(officer, officer.getWorldPosition().x, officer.getWorldPosition().z, true);
			core.objectService.destroyObject(officer.getObjectID());
		}
		
		for (SWGObject cloner : offensiveClonerList){
			core.simulationService.remove(cloner, cloner.getWorldPosition().x, cloner.getWorldPosition().z, true);
			core.objectService.destroyObject(cloner.getObjectID());
		}
		
				
		for (Map.Entry<Integer, CreatureObject> entry : invaderMap.entrySet()) {
			CreatureObject creature = entry.getValue();
			if (creature!=null){
				if (creature.getAttachment("AI")!=null){
					AIActor actor = (AIActor)creature.getAttachment("AI");
					if (actor!=null){
						//System.out.println("Preparing deletion " + creature.getObjectID());
						actor.prepareDeletion();
					}					
				}
			}
		}	
		
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			//System.out.println("Started schedule to destroy " + invaderMap.entrySet().size());
			for (Map.Entry<Integer, CreatureObject> entry : invaderMap.entrySet()) {
				CreatureObject creature = entry.getValue();
				if (creature!=null){
					if (creature.getAttachment("AI")!=null){
						AIActor actor = (AIActor)creature.getAttachment("AI");
						core.simulationService.remove(creature, creature.getWorldPosition().x, creature.getWorldPosition().z, true); // Make sure
						//System.out.println("destroying Actor " + creature.getObjectID());
						actor.destroyActor();
					}
				}
			}
		}, 5, TimeUnit.SECONDS);
		
				
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			for (Map.Entry<Integer, CreatureObject> entry : invaderMap.entrySet()) {
				CreatureObject creature = entry.getValue();
				if (creature!=null){
					core.simulationService.remove(creature, creature.getWorldPosition().x, creature.getWorldPosition().z, true); // Make sure
					core.objectService.destroyObject(creature.getObjectID());	
				}
			}
			invaderMap.clear();
			//System.out.println("Destroyed all invaders.");
		}, 15, TimeUnit.SECONDS);	// Make sure remaining AI is finished with moves that might make them re-appear
	}
	
	public void wipeDefenders(Map<Integer,CreatureObject> defenderMap){
		
		for (TangibleObject banner : defensiveBanners){
			core.objectService.destroyObject(banner.getObjectID());
		}
		
		if (defensiveTable!=null)
			core.objectService.destroyObject(defensiveTable.getObjectID());
		if (defendingGeneral!=null)
			core.objectService.destroyObject(defendingGeneral.getObjectID());
		if (defendingQuestOfficer!=null)
			core.objectService.destroyObject(defendingQuestOfficer.getObjectID());
				
		for (Map.Entry<Integer, CreatureObject> entry : defenderMap.entrySet()) {
			CreatureObject creature = entry.getValue();
			if (creature!=null){
				if (creature.getAttachment("AI")!=null){
					AIActor actor = (AIActor)creature.getAttachment("AI");
					actor.prepareDeletion();
				}
			}
		}	
		
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			for (Map.Entry<Integer, CreatureObject> entry : defenderMap.entrySet()) {
				CreatureObject creature = entry.getValue();
				if (creature!=null){
					if (creature.getAttachment("AI")!=null){
						AIActor actor = (AIActor)creature.getAttachment("AI");
						core.simulationService.remove(creature, creature.getWorldPosition().x, creature.getWorldPosition().z, true); // Make sure
						actor.destroyActor();
					}
				}
			}
		}, 5, TimeUnit.SECONDS);
				
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			for (Map.Entry<Integer, CreatureObject> entry : defenderMap.entrySet()) {
				CreatureObject creature = entry.getValue();
				if (creature!=null){
						core.simulationService.remove(creature, creature.getWorldPosition().x, creature.getWorldPosition().z, true); // Make sure
						core.objectService.destroyObject(creature.getObjectID());	
				}
			}
			defenderMap.clear();
			//System.out.println("Destroyed all defenders.");
		}, 15, TimeUnit.SECONDS);	// Make sure remaining AI is finished with moves that might make them re-appear
				
	}
	
	
	public void registerPhase2Objects(TangibleObject registrant){
		synchronized(phase2Objects){
			phase2Objects.put(phase2ObjectRegistryCounter++, registrant);	
		}
	}
	
	public void deletePhase2Objects(){
		for (Map.Entry<Integer, TangibleObject> entry : phase2Objects.entrySet()) {
			TangibleObject tano = entry.getValue();
			if (tano!=null){
					core.simulationService.remove(tano, tano.getWorldPosition().x, tano.getWorldPosition().z, true); // Make sure
					core.objectService.destroyObject(tano.getObjectID());	
			}
		}
		defendingCreatures.clear();
	}
	
		
	public void spawnOffensiveAssets(){
		spawnOffensiveCamps();	
		spawnOffensiveCloners();
	}
	
	public void spawnDefensiveAssets(){
		spawnDefendingGeneral();
		spawnDefensiveQuestOfficer();
		spawnBanners();
		spawnSupplyTerminals();
		spawnDefensivePlanningTable();
	}
	
	public void spawnDefensivePlanningTable(){
		if (invasionLocation==InvasionLocation.Keren){ 
			String template = "object/tangible/terminal/shared_planning_table_keren_reb.iff";
			if (defendingFaction==Factions.Imperial)
				template = "object/tangible/terminal/shared_planning_table_keren_imp.iff";
			spawnDefensiveTable(new Point3D(1789.07F, 12, 2530.95F),new Quaternion(1,0F,0,0F),template);
		}
		if (invasionLocation==InvasionLocation.Bestine){ 
			String template = "object/tangible/terminal/shared_planning_table_bestine_reb.iff";
			if (defendingFaction==Factions.Imperial)
				template = "object/tangible/terminal/shared_planning_table_bestine_imp.iff";
			spawnDefensiveTable(new Point3D(-1196.37F, 12, -3615.20F),new Quaternion(1,0F,0,0F),template);
		}
		if (invasionLocation==InvasionLocation.Dearic){ 
			String template = "object/tangible/terminal/shared_planning_table_dearic_reb.iff";
			if (defendingFaction==Factions.Imperial)
				template = "object/tangible/terminal/shared_planning_table_dearic_imp.iff";
			spawnDefensiveTable(new Point3D(476.19F, 6, -3026.57F),new Quaternion(-0.37F,0F,0.92F,0F),template);			
		}	
	}
		
	public void spawnDefensiveTable(Point3D position, Quaternion quaternion, String template){
		
		defensiveTable = (TangibleObject) core.objectService.createObject(template, 0, getInvasionPlanet(invasionLocation),  position, quaternion);
		core.simulationService.add(defensiveTable, defensiveTable.getPosition().x, defensiveTable.getPosition().z, true);
		
		String name = "gcw_reb_defense_table_n";
		String description = "gcw_reb_defense_table_d";
		if (defendingFaction==Factions.Imperial){
			name = "gcw_imp_defense_table_n";
			description = "gcw_imp_defense_table_d";
		}
			
		defensiveTable.setStfFilename("gcw");
		defensiveTable.setStfName(name);
		defensiveTable.setDetailFilename("gcw");
		defensiveTable.setDetailName(description);
	}
	
	public void spawnSupplyTerminals(){
		
		if (invasionLocation==InvasionLocation.Keren){ 
			spawnDefensiveSupplyTerminal(new Point3D(1787.41F, 12, 2521.47F),new Quaternion(0,0F,1,0F));
		}
		if (invasionLocation==InvasionLocation.Bestine){ 
			spawnDefensiveSupplyTerminal(new Point3D(-1212.58F, 12, -3614.57F),new Quaternion(0,0F,1,0F));
		}
		if (invasionLocation==InvasionLocation.Dearic){ 
			spawnDefensiveSupplyTerminal(new Point3D(465.68F, 6, -3006.34F),new Quaternion(0,0F,1,0F));			
		}			
	}
	
	public void spawnDefensiveSupplyTerminal(Point3D position, Quaternion quaternion){
			
		// "object/tangible/terminal/shared_terminal_gcw_build_resource.iff"
		String supplyTerminalTemplate = "object/tangible/terminal/shared_terminal_gcw_reb_def.iff"; // shared_terminal_gcw_reb_def.iff
		if (defendingFaction==Factions.Imperial)
			supplyTerminalTemplate = "object/tangible/terminal/shared_terminal_gcw_imp_def.iff"; 
		
		TangibleObject supplyTerminal1 = (TangibleObject) core.objectService.createObject(supplyTerminalTemplate, 0, getInvasionPlanet(invasionLocation),  position, quaternion);
		core.simulationService.add(supplyTerminal1, supplyTerminal1.getPosition().x, supplyTerminal1.getPosition().z, true);
		supplyTerminal1.setStfFilename("gcw");
		supplyTerminal1.setStfName("terminal_gcw_reb_def_n");
		supplyTerminal1.setDetailFilename("gcw");
		supplyTerminal1.setDetailName("terminal_gcw_reb_def_d");
		if (defendingFaction==Factions.Imperial){
			supplyTerminalTemplate = "object/tangible/terminal/shared_terminal_gcw_imp_def.iff"; 
			supplyTerminal1.setStfFilename("gcw");
			supplyTerminal1.setStfName("terminal_gcw_reb_def_n");
			supplyTerminal1.setDetailFilename("gcw");
			supplyTerminal1.setDetailName("terminal_gcw_reb_def_d");
		}
		defensiveSupplyTerminals.add(supplyTerminal1);
	}
	
	public void supplyTerminalSUI(CreatureObject user, int childMenu){
		if (invasionPhase!=1)
			return; // prevent exploit
		String terminalName = "@gcw:terminal_gcw_reb_def_n";
		if (defendingFaction==Factions.Imperial)
			terminalName = "@gcw:terminal_gcw_imp_def_n";			
		
		Map<Long, String> suiOptions = new HashMap<Long, String>();
		suiOptions.put((long) 1, "@gcw:gcw_barricade_tool_n");
		suiOptions.put((long) 2, "@gcw:gcw_spawner_tool_n");
		suiOptions.put((long) 3, "@gcw:gcw_turret_tool_n");	
		suiOptions.put((long) 4, "@gcw:gcw_tower_tool_n");	
		final SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL,  terminalName, "For now the tools are directly available.", suiOptions, user, null, 10);
		Vector<String> returnList = new Vector<String>();
		
		returnList.add("List.lstList:SelectedRow");		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() 
		{
			public void process(SWGObject owner, int eventType, Vector<String> returnList) 
			{
				if (invasionPhase!=1)
					return; // prevent exploit
				int index = Integer.parseInt(returnList.get(0));
				int childIndex = (int) window.getObjectIdByIndex(index);
				
				CreatureObject player = (CreatureObject) owner;		
				SWGObject inventory = player.getSlottedObject("inventory");
				Planet planet = player.getPlanet();
				
				switch(childIndex)
				{
					case 1:
						TangibleObject barricadeTool = (TangibleObject) core.objectService.createObject("object/tangible/gcw/crafting_quest/shared_gcw_barricade_tool.iff", planet);						
						barricadeTool.setStfFilename("gcw");
						barricadeTool.setStfName("gcw_barricade_tool_n");
						barricadeTool.setDetailFilename("gcw");
						barricadeTool.setDetailName("gcw_barricade_tool_d");	
						barricadeTool.setUses(10);
						inventory.add(barricadeTool);	
						break;
					
					case 2:
						TangibleObject spawnerTool = (TangibleObject) core.objectService.createObject("object/tangible/gcw/crafting_quest/shared_gcw_spawner_tool.iff", planet);						
						spawnerTool.setStfFilename("gcw");
						spawnerTool.setStfName("gcw_spawner_tool_n");
						spawnerTool.setDetailFilename("gcw");
						spawnerTool.setDetailName("gcw_spawner_tool_d");
						spawnerTool.setUses(10);
						inventory.add(spawnerTool);	
						break;
						
					case 3:
						TangibleObject turretTool = (TangibleObject) core.objectService.createObject("object/tangible/gcw/crafting_quest/shared_gcw_turret_tool.iff", planet);						
						turretTool.setStfFilename("gcw");
						turretTool.setStfName("gcw_turret_tool_n");
						turretTool.setDetailFilename("gcw");
						turretTool.setDetailName("gcw_turret_tool_d");	
						turretTool.setUses(10);
						inventory.add(turretTool);	
						break;
						
					case 4:
						TangibleObject towerTool = (TangibleObject) core.objectService.createObject("object/tangible/gcw/crafting_quest/shared_gcw_tower_tool.iff", planet);						
						towerTool.setStfFilename("gcw");
						towerTool.setStfName("gcw_tower_tool_n");
						towerTool.setDetailFilename("gcw");
						towerTool.setDetailName("gcw_tower_tool_d");	
						towerTool.setUses(10);
						inventory.add(towerTool);	
						break;
				}
			}
		});
		
		core.suiService.openSUIWindow(window);	
	
	}
	
	public void supplyOffensiveTerminalSUI(CreatureObject user, int childMenu){
		if (invasionPhase!=1)
			return; // prevent exploit
		String terminalName = "@gcw:terminal_gcw_reb_offensiv_n";
		if (defendingFaction==Factions.Imperial)
			terminalName = "@gcw:terminal_gcw_imp_offensiv_n";			
		Map<Long, String> suiOptions = new HashMap<Long, String>();
		suiOptions.put((long) 1, "@gcw:gcw_patrol_tool_n");
		suiOptions.put((long) 2, "@gcw:gcw_vehicle_tool_n");
		final SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL,  terminalName, "For now the tools are directly available", suiOptions, user, null, 10);
		Vector<String> returnList = new Vector<String>();
		
		returnList.add("List.lstList:SelectedRow");		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() 
		{
			public void process(SWGObject owner, int eventType, Vector<String> returnList) 
			{
				if (invasionPhase!=1)
					return; // prevent exploit
				int index = Integer.parseInt(returnList.get(0));
				int childIndex = (int) window.getObjectIdByIndex(index);
				
				CreatureObject player = (CreatureObject) owner;		
				SWGObject inventory = player.getSlottedObject("inventory");
				Planet planet = player.getPlanet();
				
				switch(childIndex)
				{
					case 1:
						TangibleObject barricadeTool = (TangibleObject) core.objectService.createObject("object/tangible/gcw/crafting_quest/shared_gcw_patrol_tool.iff", planet);						
						barricadeTool.setStfFilename("gcw");
						barricadeTool.setStfName("gcw_patrol_tool_n");
						barricadeTool.setDetailFilename("gcw");
						barricadeTool.setDetailName("gcw_patrol_tool_d");	
						barricadeTool.setUses(10);
						inventory.add(barricadeTool);	
						break;
					
					case 2:
						TangibleObject spawnerTool = (TangibleObject) core.objectService.createObject("object/tangible/gcw/crafting_quest/shared_gcw_vehicle_tool.iff", planet);						
						spawnerTool.setStfFilename("gcw");
						spawnerTool.setStfName("gcw_vehicle_tool_n");
						spawnerTool.setDetailFilename("gcw");
						spawnerTool.setDetailName("gcw_vehicle_tool_d");
						spawnerTool.setUses(10);
						inventory.add(spawnerTool);	
						break;
				}
			}
		});	
		
		core.suiService.openSUIWindow(window);	
	}
	
	public void usePylon(CreatureObject user, GCWPylon pylon){
		
		// check pylon filling status
		if (pylon.getAttachment("Filling")==null)
			return;
		
		if (user.getWorldPosition().getDistance2D(pylon.getWorldPosition())>7){
			user.sendSystemMessage("@gcw:pylon_too_far",(byte) 0);
			return;
		}
		
		int pylonFilling = (int) pylon.getAttachment("Filling");
		if (pylonFilling>=100){
			user.sendSystemMessage("@gcw:pylon_construction_complete",(byte) 0);			
			return;
		}
		
		if (!(CRC.StringtoCRC(user.getFaction())==pylon.getPylonFaction())){
			user.sendSystemMessage("@gcw:pylon_construction_cannot_use",(byte) 0);			
			return;
		}
		
		if (!user.getFaction().equals(getFactionName(pylon.getPylonFaction()))){
			return;
		}
		// ToDo: Consider Factional Helpers
		if (user.getFaction().equals(getFactionName(pylon.getPylonFaction())) && user.getFactionStatus()<FactionStatus.Combatant){
			return;
		}
		
		if (user.isConstructing())
			return;
			
		// determine pylon type
		int pylonType = pylon.getPylonType();
		String toolTemplate = ""; 
		switch (pylonType){
			case PylonType.Barricade: toolTemplate="object/tangible/gcw/crafting_quest/shared_gcw_barricade_tool.iff";
					                  break;
			case PylonType.SoldierDefense: toolTemplate="object/tangible/gcw/crafting_quest/shared_gcw_spawner_tool.iff";
            							   break;
			case PylonType.SoldierPatrol: toolTemplate="object/tangible/gcw/crafting_quest/shared_gcw_patrol_tool.iff";
            							  break;
			case PylonType.VehiclePatrol: toolTemplate="object/tangible/gcw/crafting_quest/shared_gcw_vehicle_tool.iff";
            							  break;
			case PylonType.SiegeVehiclePatrol: toolTemplate="object/tangible/gcw/crafting_quest/shared_gcw_vehicle_tool.iff";
            								   break;
			case PylonType.Tower: toolTemplate="object/tangible/gcw/crafting_quest/shared_gcw_tower_tool.iff";
            					  break;
			case PylonType.Turret: toolTemplate="object/tangible/gcw/crafting_quest/shared_gcw_turret_tool.iff";
								   break;
		}
		
		if (toolTemplate.length()==0)
			return; // shouldn't happen
		
		// Find the according tool with the lowest uses in user's inventory
		final String foundToolTemplate = toolTemplate;
		Vector<TangibleObject> tools = new Vector<TangibleObject>();
		user.getSlottedObject("inventory").viewChildren(user, true, false, new Traverser() {
			@Override
			public void process(SWGObject item) {
				if (item.getTemplate().equals(foundToolTemplate)) {
					if (tools.size()==0)
						tools.add((TangibleObject)item);
					else {
						TangibleObject itemTANO = (TangibleObject)item;
						if (itemTANO.getUses()<tools.get(0).getUses()){
							tools.clear();
							tools.add(itemTANO);
						}
					}
						
				}
			}
		});
		
		if (tools.size()==0){
			// User has no appropriate tool in the inventory
			user.sendSystemMessage("@gcw:pylon_resources_needed",(byte) 0);			
			return;
		}
		
		// start construction
		float y = -3.1F;
	    float qz= 1.06535322E9F;
	    String animName = "pt_gcw_pylon_construction.prt";
		Point3D effectorPosition = new Point3D(0,y,0);
		Quaternion effectorOrientation = new Quaternion(0,0,0,qz);
		PlayClientEffectObjectTransformMessage lmsg = new PlayClientEffectObjectTransformMessage("appearance/" + animName,pylon.getObjectID(),"lootMe",effectorPosition,effectorOrientation);
		//PlayClientEffectObjectMessage lmsg = new PlayClientEffectObjectMessage("appearance/" + animName, pylon.getObjectID(), "");
		pylon.notifyObserversInRange(lmsg, false, 100);	
					
		int fatigue = user.getGCWFatigue();
		int constructiontimeagain = 10 + fatigue/10;
		
		SUIWindow window = core.suiService.createSUIWindow("Script.countdownTimerBar", user, null, 5);
		core.suiService.openTimerSUIWindow(window, "@gcw:pylon_construction_prompt", constructiontimeagain);
		
		int[] countdown = {constructiontimeagain};
		final Point3D userpos = user.getWorldPosition();
		user.setConstructing(true);
		boolean[] cancelled = {false};
		final Future<?>[] fut = {null};
		fut[0] = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					//user.sendSystemMessage(""+countdown[0], (byte)0);
					countdown[0]--;
					// check if user has moved
					if (userpos.getDistance2D(user.getWorldPosition())>0.2){
						// cancel count-down
						fut[0].cancel(true);
						cancelled[0] = true;
						user.setConstructing(false);
						core.suiService.closeSUIWindow(window.getOwner(),window.getWindowId());
						user.sendSystemMessage("@gcw:pylon_construction_moved",(byte) 0);
					}	
					if (user.isInCombat()){
						fut[0].cancel(true);
						cancelled[0] = true;
						user.setConstructing(false);
						core.suiService.closeSUIWindow(window.getOwner(),window.getWindowId());
						user.sendSystemMessage("@gcw:pylon_construction_entered_combat",(byte) 0);						
					}
					if (user.getPosture()==13 & user.getPosture()==14){
						fut[0].cancel(true);
						cancelled[0] = true;
						user.setConstructing(false);
						core.suiService.closeSUIWindow(window.getOwner(),window.getWindowId());
						user.sendSystemMessage("@gcw:pylon_construction_incapacitated",(byte) 0);						
					}
						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, 0, 1, TimeUnit.SECONDS);
		
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					fut[0].cancel(true);					
					if (! cancelled[0]){
						TangibleObject tool = tools.get(0);
						updatePylonFilling(pylon, tool);
						user.setConstructing(false);
						core.suiService.closeSUIWindow(window.getOwner(),window.getWindowId());						
						core.buffService.addBuffToCreature(user, "gcw_fatigue", user);
						// increase fatigue
						user.setGCWFatigue(user.getGCWFatigue()+1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, constructiontimeagain, TimeUnit.SECONDS);
		
	}
	
	public void updatePylonFilling(GCWPylon pylon, TangibleObject tool){
		
		int pylonFilling = (int)pylon.getAttachment("Filling");
		String pylonName = (String)pylon.getAttachment("Name");
		// determine power of tool
		int power = 15; // assume 15 for now
		
		pylonFilling += power;
		if (pylonFilling>100)
			pylonFilling=100;
		
		String newPylonName = pylonName + " " + pylonFilling +"/100";
		pylon.setCustomName(newPylonName);
		pylon.setAttachment("Filling",pylonFilling);
		pylon.setResourceFilling(pylonFilling);
		
		
		tool.setUses(tool.getUses()-1);
		if (tool.getUses()==0){
			core.objectService.destroyObject(tool.getObjectID());
		}
	}
	
	public void useBarricade(CreatureObject user, TangibleObject structure){
		
		if (!user.getFaction().equals(getFactionName(defendingFaction))){
			return;
		}
		// ToDo: Consider Factional Helpers
		if (user.getFaction().equals(getFactionName(defendingFaction)) && user.getFactionStatus()<FactionStatus.Combatant){
			return;
		}
		
		if (user.isTrader()){
			TangibleObject tool = retrieveToolFromInventory(user, "object/tangible/gcw/crafting_quest/shared_gcw_barricade_tool.iff");
			if (tool==null)
				return;
			repairStructure(user, tool, structure);
		} else {
			// ToDo: IMPLEMENT BARRICADE QUEST CALL HERE
			user.sendSystemMessage("Barricade Quest not yet implemented!", (byte)0);
			
//			QuestTaskTimerMessage convoMessage = new QuestTaskTimerMessage(user.getObjectID(), "this");
//			ObjControllerMessage objController = new ObjControllerMessage(0x0B, convoMessage);
//			user.getClient().getSession().write(objController.serialize());
		}			
	}
	
	public void useTurret(CreatureObject user, TangibleObject structure){
		
		if (!user.getFaction().equals(getFactionName(defendingFaction))){
			return;
		}
		// ToDo: Consider Factional Helpers
		if (user.getFaction().equals(getFactionName(defendingFaction)) && user.getFactionStatus()<FactionStatus.Combatant){
			return;
		}
		
		if (user.isTrader()){
			TangibleObject tool = retrieveToolFromInventory(user, "object/tangible/gcw/crafting_quest/shared_gcw_turret_tool.iff");
			if (tool==null)
				return;
			repairStructure(user, tool, structure);
		} else {
			// ToDo: IMPLEMENT TURRET QUEST CALL HERE
			user.sendSystemMessage("Turret Quest not yet implemented!", (byte)0);
		}			
	}
	
	public void useTower(CreatureObject user, TangibleObject structure){
		
		if (!user.getFaction().equals(getFactionName(defendingFaction))){
			return;
		}
		// ToDo: Consider Factional Helpers
		if (user.getFaction().equals(getFactionName(defendingFaction)) && user.getFactionStatus()<FactionStatus.Combatant){
			return;
		}
		
		if (user.isTrader()){
			TangibleObject tool = retrieveToolFromInventory(user, "object/tangible/gcw/crafting_quest/shared_gcw_tower_tool.iff");
			if (tool==null)
				return;
			repairStructure(user, tool, structure);
		} else {
			// ToDo: IMPLEMENT TOWER QUEST CALL HERE
			user.sendSystemMessage("Tower Quest not yet implemented!", (byte)0);
		}			
	}
	
	public void repairStructure(CreatureObject user, TangibleObject tool, TangibleObject structure){
		
		int structureCondition = structure.getConditionDamage();
		if (structureCondition>=structure.getMaximumCondition()){
			return;
		}
		if (user.isConstructing())
			return;
		if (structureCondition<10000){
			user.sendSystemMessage("@gcw:gcw_object_doesnt_need_repair",(byte) 0);
			return;
		}
			
				
		SUIWindow window = core.suiService.createSUIWindow("Script.countdownTimerBar", user, null, 5);
		core.suiService.openTimerSUIWindow(window, "@gcw:reparing_gcw_object", 10); // also influenced by fatigue?
		
		user.setConstructing(true);
		int repairtimeagain = 10;
		int[] countdown = {repairtimeagain};
		final Point3D userpos = user.getWorldPosition();
		boolean[] cancelled = {false};
		final Future<?>[] fut = {null};
		fut[0] = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					countdown[0]--;
					// check if user has moved
					if (userpos.getDistance2D(user.getWorldPosition())>0.2){
						// cancel count-down
						fut[0].cancel(true);
						cancelled[0] = true;
						core.suiService.closeSUIWindow(window.getOwner(),window.getWindowId());
						user.setConstructing(false);
						user.sendSystemMessage("@gcw:pylon_construction_moved",(byte) 0);
					}	
					if (user.isInCombat()){
						fut[0].cancel(true);
						cancelled[0] = true;
						core.suiService.closeSUIWindow(window.getOwner(),window.getWindowId());
						user.setConstructing(false);
						user.sendSystemMessage("@gcw:pylon_construction_entered_combat",(byte) 0);						
					}
					if (user.getPosture()==13 & user.getPosture()==14){
						fut[0].cancel(true);
						cancelled[0] = true;
						core.suiService.closeSUIWindow(window.getOwner(),window.getWindowId());
						user.setConstructing(false);
						user.sendSystemMessage("@gcw:pylon_construction_incapacitated",(byte) 0);						
					}
						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, 0, 1, TimeUnit.SECONDS);
		
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					fut[0].cancel(true);					
					if (! cancelled[0]){
						int repairAmount = 5000;
						int newCondition = structureCondition-repairAmount;
						if (newCondition<0)
							newCondition=0;
						
						structure.setConditionDamage(newCondition);
						user.setConstructing(false);
						tool.setUses(tool.getUses()-1);
						if (tool.getUses()==0){
							core.objectService.destroyObject(tool.getObjectID());
						}
						core.suiService.closeSUIWindow(window.getOwner(),window.getWindowId());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, repairtimeagain, TimeUnit.SECONDS);

	}
	
	public TangibleObject retrieveToolFromInventory(CreatureObject user, String toolTemplate){
		
		if (toolTemplate.length()==0)
			return null; 
		
		// Find the according tool with the lowest uses in user's inventory
		final String foundToolTemplate = toolTemplate;
		Vector<TangibleObject> tools = new Vector<TangibleObject>();
		user.getSlottedObject("inventory").viewChildren(user, true, false, new Traverser() {
			@Override
			public void process(SWGObject item) {
				if (item.getTemplate().equals(foundToolTemplate)) {
					if (tools.size()==0)
						tools.add((TangibleObject)item);
					else {
						TangibleObject itemTANO = (TangibleObject)item;
						if (itemTANO.getUses()<tools.get(0).getUses()){
							tools.clear();
							tools.add(itemTANO);
						}
					}
						
				}
			}
		});
		
		if (tools.size()==0){
			// User has no appropriate tool in the inventory
			user.sendSystemMessage("@gcw:pylon_resources_needed",(byte) 0);			
			return null;
		}
		return tools.get(0);
	}
	
	
	public void spawnPylon(int pylonType, int pylonFaction, String planetName, Point3D pylonPos, float qw, float qy){
		GCWPylon pylon = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName(planetName), pylonPos, new Quaternion(qw,0F,qy,0F));
		pylon.setPylonType(pylonType);
		pylon.setPylonFaction(pylonFaction);
		core.simulationService.add(pylon, pylon.getPosition().x, pylon.getPosition().z, true);
		pylonList.add(pylon);
	}
	
	public void spawnPylon(int pylonType, int patrolRoute, int pylonFaction, String planetName, Point3D pylonPos, float qw, float qy){
		GCWPylon pylon = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName(planetName), pylonPos, new Quaternion(qw,0F,qy,0F));
		pylon.setPylonType(pylonType);
		pylon.setPylonFaction(pylonFaction);
		pylon.setPatrolRoute(patrolRoute);
		core.simulationService.add(pylon, pylon.getPosition().x, pylon.getPosition().z, true);
		pylonList.add(pylon);
	}
	
	public void spawnPylon(int pylonType, int patrolRoute, int pylonFaction, String planetName, int delay, Point3D pylonPos, float qw, float qy){
		GCWPylon pylon = (GCWPylon) core.objectService.createObject("object/tangible/destructible/shared_gcw_city_construction_beacon.iff", 0, core.terrainService.getPlanetByName(planetName), pylonPos, new Quaternion(qw,0F,qy,0F));
		pylon.setPylonType(pylonType);
		pylon.setPylonFaction(pylonFaction);
		pylon.setPatrolRoute(patrolRoute);
		pylon.setDelay(delay);
		core.simulationService.add(pylon, pylon.getPosition().x, pylon.getPosition().z, true);
		pylonList.add(pylon);
	}
	
//	public void testPylons(){
//		spawnPylonsKeren();
//		spawnDefensiveAssets();
//		spawnOffensiveAssets();
//	}
	
	public void spawnPylonsKeren(){	
		
		spawnPylon(PylonType.Tower, defendingFaction, "naboo", new Point3D(1769.25F, 12, 2505.71F), 0F, 1.00F);
		spawnPylon(PylonType.Turret, defendingFaction, "naboo", new Point3D(1779.27F, 12, 2517.37F), 0.70F, -0.70F); // Near General
		spawnPylon(PylonType.Turret, defendingFaction, "naboo", new Point3D(1797.00F, 12, 2518.32F), 0.68F, 0.73F);	 // Near General	
		spawnPylon(PylonType.Turret, defendingFaction, "naboo", new Point3D(1988.83F, 30, 2709.65F), 0.97F, 0.23F);
		spawnPylon(PylonType.Turret, defendingFaction, "naboo", new Point3D(1729.45F, 12, 2510.74F), 1.00F, 0.00F);
		spawnPylon(PylonType.Turret, defendingFaction, "naboo", new Point3D(1534.97F, 25, 2753.62F), -0.67F, 0.74F);
		
		
		spawnPylon(PylonType.Barricade, defendingFaction, "naboo", new Point3D(1799.69F, 12, 2518.91F), 0.85F, 0.52F);
		spawnPylon(PylonType.Barricade, defendingFaction, "naboo", new Point3D(1792.67F, 12, 2516.24F), 0.71F, 0.70F);
		spawnPylon(PylonType.Barricade, defendingFaction, "naboo", new Point3D(1782.81F, 12, 2516.37F), 0.71F, 0.70F);
		spawnPylon(PylonType.Barricade, defendingFaction, "naboo", new Point3D(1991.90F, 30, 2713.51F), 0.25F, 0.96F);
		//spawnPylon(PylonType.Barricade, defendingFaction, "naboo", new Point3D(1990.17F, 30.29F, 2685.63F), 0.99F, 0.07F); // Makes patrols fall through the bridge
		spawnPylon(PylonType.Barricade, defendingFaction, "naboo", new Point3D(1998.98F, 30.0F, 2670.41F), 0.94F, -0.32F); // works better. Do not change, it prevents client clipping problems!
		spawnPylon(PylonType.Barricade, defendingFaction, "naboo", new Point3D(1713.97F, 12, 2655.29F), 1.00F, 0.00F);
		spawnPylon(PylonType.Barricade, defendingFaction, "naboo", new Point3D(1693.40F, 12, 2636.64F), 0.72F, -0.69F);
		spawnPylon(PylonType.Barricade, defendingFaction, "naboo", new Point3D(1532.30F, 25, 2757.37F), -0.69F, 0.71F);
		
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1776.75F, 12, 2519.21F), 0.90F, -0.42F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1803.14F, 12, 2523.18F), 0.66F, 0.75F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1785.39F, 12, 2518.77F), 0.71F, -0.69F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1782.33F, 12, 2529.21F), 0.70F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1791.95F, 12, 2529.19F), 0.70F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1806.29F, 12, 2526.72F), 0.99F, 0.03F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1806.78F, 12, 2521.70F), 0.01F, 0.99F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1774.09F, 12, 2521.07F), 0.92F, -0.38F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1764.65F, 12, 2528.48F),  0.99F, -0.03F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1803.98F, 12, 2492.16F), 0.67F, 0.73F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1809.18F, 12, 2492.16F), 0.67F, 0.73F);	
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1820.98F, 12, 2518.77F), 0.79F, 0.61F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1854.98F, 12, 2541.42F), 0.36F, 0.93F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1857.63F, 12, 2545.66F), 0.36F, 0.93F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1864.14F, 12, 2551.11F), 0.94F, 0.32F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1875.09F, 12, 2556.38F), 0.95F, 0.29F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1875.03F, 12, 2522.12F), 0.93F, 0.36F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1878.19F, 12, 2524.03F), 0.94F, 0.33F);	
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(2000.56F, 29.88F, 2674.05F), 0.01F, 0.99F); // Do not change, it prevents clipping problems!
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1731.00F, 12, 2582.05F), 0.90F, -0.42F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1737.00F, 12, 2582.05F), 0.90F, -0.42F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1714.75F, 12, 2649.42F), 1.00F, 0.00F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1697.67F, 12, 2636.46F), 0.75F, -0.65F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1697.67F, 12, 2639.46F), 0.75F, -0.65F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1598.12F, 25, 2670.29F), 1F, 0F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1538.10F, 25, 2756.20F), -0.69F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "naboo", new Point3D(1537.60F, 25, 2751.62F), -0.69F, 0.71F);
		
		
		// Offense
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren4 ,invadingFaction, "naboo", 0, new Point3D(2024.92F, 29.93F, 2762.69F), -0.26F, 0.96F);	
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren4 ,invadingFaction, "naboo", 30, new Point3D(2028.54F, 29.99F, 2770.40F), -0.26F, 0.96F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren4 ,invadingFaction, "naboo", 60, new Point3D(2018.48F, 30.0F, 2769.42F), -0.66F, 0.74F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren4 ,invadingFaction, "naboo", 90, new Point3D(2021.15F, 30.0F, 2778.45F), -0.21F, 0.97F);
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren3 ,invadingFaction, "naboo", 0, new Point3D(2070.01F, 40F, 2806.16F), -0.66F, 0.74F);	
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Keren3 ,invadingFaction, "naboo", 24, new Point3D(2061.67F, 40F, 2805.12F), -0.67F, 0.73F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren3 ,invadingFaction, "naboo", 48, new Point3D(2059.73F, 40F, 2812.73F), 0.71F, -0.70F);	
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Keren3 ,invadingFaction, "naboo", 72, new Point3D(2069.81F, 40F, 2817.16F), -0.67F, 0.73F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren3 ,invadingFaction, "naboo", 96, new Point3D(2065.03F, 40F, 2820.56F), -0.66F, 0.74F);	
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren1 ,invadingFaction, "naboo", 0, new Point3D(1174.23F, 6.94F, 2934.68F), 0.61F, 0.79F);	
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren1 ,invadingFaction, "naboo", 24, new Point3D(1170.23F, 6.68F, 2923.63F), 0.61F, 0.79F);
		spawnPylon(PylonType.SiegeVehiclePatrol, PatrolRoute.Keren7 ,invadingFaction, "naboo", 48, new Point3D(1164.86F, 7.55F, 2934.75F), 0.67F, 0.73F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren1 ,invadingFaction, "naboo", 72, new Point3D(1164.43F, 7.12F, 2922.25F), 0.61F, 0.79F);	
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Keren1 ,invadingFaction, "naboo", 96, new Point3D(1161.15F, 7.76F, 2932.34F), 0.61F, 0.79F);
	
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren1 ,invadingFaction, "naboo", 0, new Point3D(1103.40F, 14.5F, 2830.32F), 0.88F, 0.45F);	
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Keren1 ,invadingFaction, "naboo", 24, new Point3D(1106.28F, 14.59F, 2817.39F), 0.90F, 0.42F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren1 ,invadingFaction, "naboo", 48, new Point3D(1090.35F, 14.83F, 2825.11F), 0.89F, 0.45F);	
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Keren1 ,invadingFaction, "naboo", 72, new Point3D(1095.26F, 14.88F, 2809.69F), 0.17F, 0.98F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren1 ,invadingFaction, "naboo", 96, new Point3D(1087.09F, 14.86F, 2824.62F), 0.83F, 0.54F);
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren2 ,invadingFaction, "naboo", 0, new Point3D(2020.78F, 40F, 2807.94F), 1F, 0F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren2 ,invadingFaction, "naboo", 24, new Point3D(2013.14F, 40F, 2806.57F), 1F, 0F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren2 ,invadingFaction, "naboo", 48, new Point3D(2018.44F, 40F, 2802.86F), 1F, 0F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren2 ,invadingFaction, "naboo", 72, new Point3D(2011.99F, 40F, 2802.82F), 1F, 0F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren2 ,invadingFaction, "naboo", 96, new Point3D(2006.30F, 40F, 2805.18F), 1F, 0F);
		
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren6 ,invadingFaction, "naboo", 0, new Point3D(2033.00F, 9.34F, 2028.02F), 0.95F, -0.30F);	
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Keren6 ,invadingFaction, "naboo", 24, new Point3D(2019.84F, 10.25F, 2016.39F), 0.71F, 0.69F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren6 ,invadingFaction, "naboo", 48, new Point3D(2042.47F, 8.15F, 2016.58F), 0.97F, -0.21F);	
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Keren6 ,invadingFaction, "naboo", 72, new Point3D(2024.09F, 9.78F, 2012.29F), 0.99F, -0.04F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Keren6 ,invadingFaction, "naboo", 96, new Point3D(2042.07F, 8.16F, 2015.80F), 0.83F, 0.54F);
	
	}
	
	public void spawnPylonsDearic(){
		
//		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic1 ,invadingFaction, "talus", 0, new Point3D(-15.30F, 6, -2726.66F), 0.95F, -0.30F);
//		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Dearic1 ,invadingFaction, "talus", 30, new Point3D(-24.99F, 5.48F, -2720.02F), 0.95F, -0.30F);
//		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic1 ,invadingFaction, "talus", 60, new Point3D(-12.12F, 6, -2728.09F), 0.95F, -0.30F);
//		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic1 ,invadingFaction, "talus", 90, new Point3D(-17.06F, 5.88F, -2710.48F), 0.95F, -0.30F);
		//spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic1 ,invadingFaction, "talus", 0, new Point3D(-3.73F, 6F, 2717.41F), 0.95F, -0.30F);
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic1 ,invadingFaction, "talus", 0, new Point3D(34.755714F, 6.0F, -2562.549F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic1 ,invadingFaction, "talus", 30, new Point3D(40.315704F, 6.0F, -2569.5552F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic1 ,invadingFaction, "talus", 60, new Point3D(31.37477F, 6.0F, -2569.7996F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Dearic1 ,invadingFaction, "talus", 90, new Point3D(36.934757F, 6.0F, -2576.8057F), 0.95F, -0.30F);
		
				
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic2 ,invadingFaction, "talus", 0, new Point3D(72.755714F, 6.0F, -2508.549F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic2 ,invadingFaction, "talus", 30, new Point3D(78.315704F, 6.0F, -2515.5552F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic2 ,invadingFaction, "talus", 60, new Point3D(69.37477F, 6.0F, -2515.7996F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Dearic2 ,invadingFaction, "talus", 90, new Point3D(74.93476F, 6.0F, -2522.8057F), 0.95F, -0.30F);
		
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic3 ,invadingFaction, "talus", 0, new Point3D(150.67159F, 6.0F, -2417.8162F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic3 ,invadingFaction, "talus", 30, new Point3D(159.24464F, 6.0F, -2420.3662F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic3 ,invadingFaction, "talus", 60, new Point3D(152.06078F, 6.0F, -2425.6946F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Dearic3 ,invadingFaction, "talus", 90, new Point3D(160.63382F, 6.0F, -2428.2446F), 0.95F, -0.30F);
		//spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic3 ,invadingFaction, "talus", 0, new Point3D(278.0263F, 38.64219F, -2412.854F), 0.95F, -0.30F);
		
		
		
		//spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic4 ,invadingFaction, "talus", 0, new Point3D(275, 6, -2481), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic4 ,invadingFaction, "talus", 0, new Point3D(231.20735F, 6.0F, -2463.64F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic4 ,invadingFaction, "talus", 30, new Point3D(237.2575F, 6.0F, -2457.0525F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic4 ,invadingFaction, "talus", 60, new Point3D(238.89745F, 6.0F, -2465.845F), 0.95F, -0.30F);
		spawnPylon(PylonType.SiegeVehiclePatrol, PatrolRoute.Dearic11 ,invadingFaction, "talus", 90, new Point3D(244.9476F, 6.0F, -2459.2576F), 0.95F, -0.30F);
		
		
		
		//spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic5 ,invadingFaction, "talus", 0, new Point3D(519, 6, -2452), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic5 ,invadingFaction, "talus", 0, new Point3D(513.6716F, 6.0F, -2444.8162F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic5 ,invadingFaction, "talus", 30, new Point3D(522.2446F, 6.0F, -2447.3662F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic5 ,invadingFaction, "talus", 60, new Point3D(515.0608F, 6.0F, -2452.6946F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Dearic5 ,invadingFaction, "talus", 90, new Point3D(523.63385F, 6.0F, -2455.2446F), 0.95F, -0.30F);
		
		
		//spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic6 ,invadingFaction, "talus", 0, new Point3D(845, 6, -2550), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic6 ,invadingFaction, "talus", 0, new Point3D(839.6716F, 6.0F, -2542.8162F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic6 ,invadingFaction, "talus", 30, new Point3D(848.2446F, 6.0F, -2545.3662F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic6 ,invadingFaction, "talus", 60, new Point3D(841.0608F, 6.0F, -2550.6946F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Dearic6 ,invadingFaction, "talus", 90, new Point3D(849.63385F, 6.0F, -2553.2446F), 0.95F, -0.30F);
		
		
		//spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic7 ,invadingFaction, "talus", 0, new Point3D(1082, 6, -2855), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic7 ,invadingFaction, "talus", 0, new Point3D(1083.1494F, 6.0F, -2844.5051F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic7 ,invadingFaction, "talus", 30, new Point3D(1082.1268F, 6.0F, -2853.3906F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic7 ,invadingFaction, "talus", 60, new Point3D(1075.632F, 6.0F, -2847.2412F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Dearic7 ,invadingFaction, "talus", 90, new Point3D(1074.6093F, 6.0F, -2856.127F), 0.95F, -0.30F);
		
		
		
		//spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic8 ,invadingFaction, "talus", 0, new Point3D(946, 6, -3075), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic8 ,invadingFaction, "talus", 0, new Point3D(939.50507F, 6.0F, -3068.8506F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic8 ,invadingFaction, "talus", 30, new Point3D(948.3907F, 6.0F, -3069.873F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic8 ,invadingFaction, "talus", 60, new Point3D(942.2412F, 6.0F, -3076.3682F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Dearic8 ,invadingFaction, "talus", 90, new Point3D(951.12683F, 6.0F, -3077.3906F), 0.95F, -0.30F);
		
		
		//spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic9 ,invadingFaction, "talus", 0, new Point3D(314, 43, -3546), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic9 ,invadingFaction, "talus", 0, new Point3D(313.7557F, 41.023533F, -3537.059F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic9 ,invadingFaction, "talus", 30, new Point3D(319.3157F, 41.421783F, -3544.0652F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic9 ,invadingFaction, "talus", 60, new Point3D(310.37476F, 41.709377F, -3544.3096F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Dearic9 ,invadingFaction, "talus", 90, new Point3D(315.93475F, 42.105118F, -3551.3157F), 0.95F, -0.30F);
		
		
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "talus", new Point3D(410.98F, 6, -2988.05F), -0.69F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "talus", new Point3D(500, 6, -2954), -0.69F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "talus", new Point3D(548, 6, -2941), -0.69F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "talus", new Point3D(449F, 6, -3077), -0.69F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "talus", new Point3D(356.42F, 6, -2746.99F), -0.69F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "talus", new Point3D(369.54F, 6, -2747.12F), -0.69F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "talus", new Point3D(361.69F, 6, -2737.65F), -0.69F, 0.71F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "talus", new Point3D(344.56F, 6, -2738.03F), -0.69F, 0.71F);
		
					
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(480.05F, 6, -3009.93F), 1.00F, 0F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(483.42F, 6, -3007.00F), -0.7F, 0.7F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(476.95F, 6, -3006.85F), -0.69F, 0.716F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(410.82F, 6, -3012.75F), 1.00F, 0.00F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(431.76F, 6, -3072.4F), 0.41F, 0.91F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(444.5F, 6, -3074.5F), 0.41F, 0.91F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(509.8F, 6, -2955.06F),  0.72F, 0.69F); // Bank
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(552.81F, 6, -2938.06F),  0.72F, 0.69F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(339.51F, 6, -2742.10F),  0F, 1F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(362.60F, 6, -2741.95F),  0F, 1F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(386.05F, 6, -2741.98F),  0F, 1F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(357.56F, 6, -2749.91F),  0F, 1F);
		spawnPylon(PylonType.Barricade, defendingFaction, "talus", new Point3D(369.93F, 6, -2749.91F),  0F, 1F);
		
		
		//spawnPylon(PylonType.Tower, defendingFaction, "talus", new Point3D(548.8F, 6, -2872.06F), 0.72F, 0.69F);
		spawnPylon(PylonType.Tower, defendingFaction, "talus", new Point3D(331.96F, 6, -2742.12F), 0F, 1F);
		spawnPylon(PylonType.Turret, defendingFaction, "talus", new Point3D(557, 6, -3028), 0.62F, 0.78F);
		spawnPylon(PylonType.Turret, defendingFaction, "talus", new Point3D(405.09F, 6, -3020.4F), 1.00F, 0.00F);
		spawnPylon(PylonType.Turret, defendingFaction, "talus", new Point3D(423.5F, 6, -3059.5F), 0.97F, -0.23F);
		spawnPylon(PylonType.Turret, defendingFaction, "talus", new Point3D(498.87F, 6, -2950.12F), 0.72F, 0.69F);
		spawnPylon(PylonType.Turret, defendingFaction, "talus", new Point3D(344.91F, 6, -2741.06F), 1F, 0F);
		spawnPylon(PylonType.Turret, defendingFaction, "talus", new Point3D(379.53F, 6, -2741.90F), 1F, 0F);
		spawnPylon(PylonType.Turret, defendingFaction, "talus", new Point3D(362.86F, 6, -2754.18F), 1F, 0F);
		// object/tangible/destructible/shared_gcw_rebel_turret.iff crashes the client, not fixable	
		
	}
	
	public void spawnPylonsBestine(){
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1216.00F,12, -3617.07F), 0.72F, -0.69F);
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1164.60F,12, -3643.42F), 0.42F, 0.9F);
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1278.45F,12, -3615.71F), 0.41F, 0.9F);
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1237.01F,12, -3640.84F), 0.89F, -0.44F);
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1231.72F,12, -3646.87F), 0.89F, -0.44F);
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1330.74F,12, -3678.19F), 0F, 1F);
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1274.77F,12, -3572.47F), 0.28F, 0.95F);
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1124.69F,8.89F, -3729.97F), -0.17F, 0.98F);
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1156.16F,12, -3704.48F), 0.02F, 0.99F);
		spawnPylon(PylonType.Turret, defendingFaction, "tatooine", new Point3D(-1108.44F,12, -3632.28F), 0.90F, 0.422F);
		
		
		spawnPylon(PylonType.Tower, defendingFaction, "tatooine", new Point3D(-1309.32F, 12, -3685.33F), -0.16F, 0.98F);
		spawnPylon(PylonType.Tower, defendingFaction, "tatooine", new Point3D(-1342.37F, 12, -3675.02F), -0.13F, 0.99F);
		spawnPylon(PylonType.Tower, defendingFaction, "tatooine", new Point3D(-1143.69F, 12, -3704.37F), 0.95F, 0.28F);
		spawnPylon(PylonType.Tower, defendingFaction, "tatooine", new Point3D(-1125.20F, 12, -3718.84F), 0.94F, 0.32F);
		
		
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1181.09F, 12, -3632.82F),  0.45F, 0.89F);
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1211.10F, 12, -3626.07F),  0.72F, 0.68F);
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1216.85F, 12, -3626.06F),  0.71F, 0.69F);
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1213.83F, 12, -3629.28F),  1F, 0.00F);
		
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1244.37F, 12, -3622.32F),  -0.28F, 0.95F);
		
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1335.82F, 12, -3676.72F),  0F, 1F);
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1325.74F, 12, -3677.50F),  0F, 1F);
		
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1242.81F, 12, -3694.70F),  0F, 1F);
		
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1166.36F, 12, -3648.66F),  0.88F, -0.47F);
		spawnPylon(PylonType.Barricade, defendingFaction, "tatooine", new Point3D(-1160.55F, 12, -3641.41F),  0.88F, -0.47F);
		
		
		
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1218.57F, 12, -3621.80F), 0.71F, -0.69F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1213.07F, 12, -3623.91F), 0.66F, 0.74F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1214.84F, 12, -3628.14F), 0.00F, 1.0F);
		
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1161.58F, 12, -3639.93F), 0.46F, 0.88F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1167.72F, 12, -3647.47F), 0.46F, 0.88F);
		
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1242.97F, 12, -3620.19F), -0.28F, 0.95F);
		
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1273.95F, 12, -3610.01F), 0.51F, 0.85F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1278.66F, 12, -3622.50F), 0.76F, -0.64F);
		
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1327.47F, 12, -3674.75F), 0F, 1F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1336.92F, 12, -3673.78F), 0F, 1F);
		
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1136.46F, 12, -3707.30F), -0.24F, 0.97F);
		spawnPylon(PylonType.SoldierDefense, defendingFaction, "tatooine", new Point3D(-1131.13F, 12, -3712.09F), -0.24F, 0.97F);
	
	
	
	
		//Offense
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine1 ,invadingFaction, "tatooine", 0, new Point3D(-913.44995F, 27.967157F, -3776.683F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine1 ,invadingFaction, "tatooine", 48, new Point3D(-920.63385F, 25.46609F, -3771.3547F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine1 ,invadingFaction, "tatooine", 96, new Point3D(-912.0608F, 26.6927F, -3768.8047F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Bestine1 ,invadingFaction, "tatooine", 144, new Point3D(-919.2446F, 24.43212F, -3763.4763F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine1 ,invadingFaction, "tatooine", 192, new Point3D(-910.6716F, 25.557018F, -3760.9263F), 0.95F, -0.30F);
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine2 ,invadingFaction, "tatooine", 0, new Point3D(-885.44995F, 29.524225F, -3643.683F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine2 ,invadingFaction, "tatooine", 48, new Point3D(-892.63385F, 30.933945F, -3638.3547F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine2 ,invadingFaction, "tatooine", 96, new Point3D(-884.0608F, 30.324928F, -3635.8047F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Bestine2 ,invadingFaction, "tatooine", 144, new Point3D(-891.2446F, 31.003443F, -3630.4763F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine2 ,invadingFaction, "tatooine", 192, new Point3D(-882.6716F, 30.163105F, -3627.9263F), 0.95F, -0.30F);
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine3 ,invadingFaction, "tatooine", 0, new Point3D(-1114.45F, 68.20212F, -3326.573F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine3 ,invadingFaction, "tatooine", 48, new Point3D(-1121.6338F, 68.55075F, -3321.2446F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine3 ,invadingFaction, "tatooine", 96, new Point3D(-1113.0608F, 69.59729F, -3318.6946F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Bestine3 ,invadingFaction, "tatooine", 144, new Point3D(-1120.2446F, 70.137566F, -3313.3662F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine3 ,invadingFaction, "tatooine", 192, new Point3D(-1111.6716F, 71.81949F, -3310.8162F), 0.95F, -0.30F);
	
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine4 ,invadingFaction, "tatooine", 0, new Point3D(-1204.427F, 62.936188F, -3311.45F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine4 ,invadingFaction, "tatooine", 48, new Point3D(-1209.7554F, 61.935455F, -3318.6338F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine4 ,invadingFaction, "tatooine", 96, new Point3D(-1209.7554F, 61.935455F, -3318.6338F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Bestine4 ,invadingFaction, "tatooine", 144, new Point3D(-1217.6338F, 62.91336F, -3317.2446F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine4 ,invadingFaction, "tatooine", 192, new Point3D(-1220.1838F, 59.116177F, -3308.6716F), 0.95F, -0.30F);
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine5 ,invadingFaction, "tatooine", 0, new Point3D(-1291.427F, 68.381676F, -3304.45F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine5 ,invadingFaction, "tatooine", 48, new Point3D(-1296.7554F, 68.844666F, -3311.6338F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine5 ,invadingFaction, "tatooine", 96, new Point3D(-1299.3054F, 69.36506F, -3303.0608F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Bestine5 ,invadingFaction, "tatooine", 144, new Point3D(-1304.6338F, 69.58588F, -3310.2446F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine5 ,invadingFaction, "tatooine", 192, new Point3D(-1307.1838F, 70.38781F, -3301.6716F), 0.95F, -0.30F);
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine6 ,invadingFaction, "tatooine", 0, new Point3D(-1516.55F, 60.74011F, -3888.427F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine6 ,invadingFaction, "tatooine", 48, new Point3D(-1509.3662F, 60.093807F, -3893.7554F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine6 ,invadingFaction, "tatooine", 96, new Point3D(-1517.9392F, 60.705017F, -3896.3054F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Bestine6 ,invadingFaction, "tatooine", 144, new Point3D(-1510.7554F, 61.663685F, -3901.6338F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine6 ,invadingFaction, "tatooine", 192, new Point3D(-1519.3284F, 60.720818F, -3904.1838F), 0.95F, -0.30F);
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine7 ,invadingFaction, "tatooine", 0, new Point3D(-1251.55F, 58.936737F, -3999.427F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine7 ,invadingFaction, "tatooine", 48, new Point3D(-1244.3662F, 58.147915F, -4004.7554F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine7 ,invadingFaction, "tatooine", 96, new Point3D(-1252.9392F, 57.208424F, -4007.3054F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Bestine7 ,invadingFaction, "tatooine", 144, new Point3D(-1245.7554F, 57.537865F, -4012.6338F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine7 ,invadingFaction, "tatooine", 192, new Point3D(-1254.3284F, 58.225056F, -4015.1838F), 0.95F, -0.30F);
		
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine8 ,invadingFaction, "tatooine", 0, new Point3D(-1240.68F, 57.57871F, -3804.267F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine8 ,invadingFaction, "tatooine", 48, new Point3D(-1240.68F, 57.57871F, -3804.267F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine8 ,invadingFaction, "tatooine", 96, new Point3D(-1242.0692F, 56.288704F, -3812.1455F), 0.95F, -0.30F);
		spawnPylon(PylonType.VehiclePatrol, PatrolRoute.Bestine8 ,invadingFaction, "tatooine", 144, new Point3D(-1234.8854F, 56.11045F, -3817.4739F), 0.95F, -0.30F);
		spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine8 ,invadingFaction, "tatooine", 192, new Point3D(-1243.4584F, 56.694393F, -3820.024F), 0.95F, -0.30F);
		
		
//		for (int i=0;i<40;i++){
//			float fx = -8000 + 16000*new Random().nextFloat();
//			float fz = -8000 + 16000*new Random().nextFloat();
//			float positionY1 = core.terrainService.getHeight(getInvasionPlanet(invasionLocation).getID(), fx, fz);
//			spawnPylon(PylonType.SoldierPatrol, PatrolRoute.Bestine8 ,invadingFaction, "tatooine", 0, new Point3D(fx, positionY1, fz), 0.95F, -0.30F);
//			System.out.println("fx " + fx + " fz" + fz);
//		}
	
	}
	
	
	public void spawnDefendingGeneral(){
		
		String generalTemplate = "";
		if (invadingFaction==Factions.Imperial){
			generalTemplate = "rebel_invasion_general";
		}
		if (invadingFaction==Factions.Rebel){
			generalTemplate = "imp_invasion_general";
		}
		
		if (invasionLocation==InvasionLocation.Keren){
			defendingGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "naboo", 0L, 1788.75F, 12.086F, 2511.84F, 0, 1);
		}
		if (invasionLocation==InvasionLocation.Bestine){
			defendingGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "tatooine", 0L, -1214, 12, -3626, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Dearic){
			defendingGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "talus", 0L, 480.071F, 6, -3006.851F, 1, 0);			
		}
		
		if (defendingGeneral==null || defendingGeneral.getTemplate()==null){
			try {
				throw new Exception();
			} catch (Exception e) {
				System.err.println("spawnDefendingGeneral()->General is null");
				e.printStackTrace();
			}				
		}
		defendingGeneralAlive = true;
		
		final Future<?>[] dgt = {null};
		dgt[0] = defendingGeneralTask = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					AIActor actor = (AIActor)defendingGeneral.getAttachment("AI");
					if (actor.getCurrentState().getClass().equals(DeathState.class)){
						defendingGeneralAlive = false;
						Thread.yield();
						dgt[0].cancel(false);
					}
					for (CreatureObject damager : actor.getDamageMap().keySet()){
						if (damager.getOwnerId()>0){ // For pets doing damage
							damager = (CreatureObject)core.objectService.getObject(damager.getOwnerId());
						}
						if (!damager.isPlayer())
							continue;
						int warningLevel = 0;
						
						if (generalWarningLevelMap.get(damager)!=null)
							warningLevel = generalWarningLevelMap.get(damager);
						
						if (actor.getDamageMap().get(damager)>3000 && warningLevel==0){
							// Warning 1
							generalWarningLevelMap.put(damager,1);
							damager.sendSystemMessage("Attacking the general puts you at risk to become attackable by other players!", (byte) 0);
						}
						
						if (actor.getDamageMap().get(damager)>10000 && warningLevel==1){
							// Warning 2
							generalWarningLevelMap.put(damager,2);
							damager.sendSystemMessage("Continuing your attacks on the general will turn you special forces!", (byte) 0);
						}
						
						if (actor.getDamageMap().get(damager)>15000 && warningLevel==2){
							// Warning 3 -> turn overt
							generalWarningLevelMap.put(damager,3);
							damager.sendSystemMessage("Your faction status is now special forces!", (byte) 0);
							damager.setFactionStatus(FactionStatus.SpecialForces); 
							damager.updatePvpStatus();
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, 10, 5000, TimeUnit.MILLISECONDS);
			
	}
	
	public void spawnDefensiveQuestOfficer(){

		String questOfficerTemplate = "";
		if (invadingFaction==Factions.Imperial){
			questOfficerTemplate = "rebel_defensive_quest_officer";
		}
		if (invadingFaction==Factions.Rebel){
			questOfficerTemplate = "imp_defensive_quest_officer";
		}
		
		if (invasionLocation==InvasionLocation.Keren){ 
			defendingQuestOfficer = (CreatureObject) NGECore.getInstance().staticService.spawnObject(questOfficerTemplate, "naboo", 0L, 1787, 12, 2531, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Bestine){ 
			defendingQuestOfficer = (CreatureObject) NGECore.getInstance().staticService.spawnObject(questOfficerTemplate, "tatooine", 0L, -1195, 12, -3620, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Dearic){ 
			defendingQuestOfficer = (CreatureObject) NGECore.getInstance().staticService.spawnObject(questOfficerTemplate, "talus", 0L, 478, 6, -3023, 0, 1);			
		}		
	}
	
	public void spawnOffensiveGeneral(){

		String generalTemplate = "rebel_offensive_invasion_general"; 
		if (invadingFaction==Factions.Imperial){
			generalTemplate = "imp_offensive_invasion_general";
		}
			
		if (invasionLocation==InvasionLocation.Keren){
			invasionGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "naboo", 0L, 1206.99F, 7.33F, 3057.06F, 0.85F, 0.51F); 
		}
		if (invasionLocation==InvasionLocation.Bestine){
			invasionGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "tatooine", 0L, -900, 12, -3782, 1, 0);
		}
		if (invasionLocation==InvasionLocation.Dearic){
			invasionGeneral = (CreatureObject) NGECore.getInstance().staticService.spawnObject(generalTemplate, "talus", 0L, -894.90F, 9.93F, -2993.29F, 0.96F, 0.26F);			
		}
			
	}
	
	public void spawnOffensiveCamps(){
		String campTemplate = "object/building/poi/shared_scout_camp_s5.iff";
		if (invadingFaction==Factions.Imperial){
			campTemplate = "object/building/poi/shared_scout_camp_s5.iff";
		}
		if (invasionLocation==InvasionLocation.Keren){ 
			spawnOffensiveCamp(new Point3D(1820, 17, 2192),new Quaternion(0.00F,0F,1.00F,0F),campTemplate);
			spawnOffensiveCamp(new Point3D(2131, 60, 2766),new Quaternion(0.00F,0F,1.00F,0F),campTemplate);
			spawnOffensiveCamp(new Point3D(1212, 7, 3054),new Quaternion(1.00F,0F,0.00F,0F),campTemplate);
			
			spawnOffensiveQuestOfficer(new Point3D(1820, 17, 2192), new Quaternion(0.81F,0F,-0.58F,0F));
			spawnOffensiveQuestOfficer(new Point3D(2135.60F,60.0F,2769.78F), new Quaternion(-0.58F,0F,0.81F,0F));
			spawnOffensiveQuestOfficer(new Point3D(1212, 7, 3054), new Quaternion(0.51F,0F,0.85F,0F));
			
			spawnOffensiveGeneral();
			
			spawnOffensiveSupplyTerminal(new Point3D(1204.51F,7.33F,3054.27F), new Quaternion(0.28F,0F,0.95F,0F));
			spawnOffensiveSupplyTerminal(new Point3D(2139.16F,60.0F,2765.92F), new Quaternion(0.98F,0F,-0.14F,0F));
			spawnOffensiveSupplyTerminal(new Point3D(1828.02F,17.34F,2192.75F), new Quaternion(0.96F,0F,-0.26F,0F));
		}
		if (invasionLocation==InvasionLocation.Bestine){ 
			spawnOffensiveCamp(new Point3D(-900,24, -3782),new Quaternion(0.00F,0F,1.00F,0F),campTemplate);
			spawnOffensiveCamp(new Point3D(-1253,44.77F, -3348),new Quaternion(0.00F,0F,1.00F,0F),campTemplate);
			spawnOffensiveCamp(new Point3D(-1184,62.95F, -3821),new Quaternion(0.00F,0F,1.00F,0F),campTemplate);
		
			spawnOffensiveQuestOfficer(new Point3D(-900,24, -3782), new Quaternion(0.81F,0F,-0.58F,0F));
			spawnOffensiveQuestOfficer(new Point3D(-1253,44, -3348), new Quaternion(-0.58F,0F,0.81F,0F));
			spawnOffensiveQuestOfficer(new Point3D(-1184,62, -3821), new Quaternion(0.51F,0F,0.85F,0F));
			
			spawnOffensiveGeneral();
			
			spawnOffensiveSupplyTerminal(new Point3D(-903,24, -3782), new Quaternion(0.28F,0F,0.95F,0F));
			spawnOffensiveSupplyTerminal(new Point3D(-1256,44, -3348), new Quaternion(0.98F,0F,-0.14F,0F));
			spawnOffensiveSupplyTerminal(new Point3D(-1188,62, -3821), new Quaternion(0.96F,0F,-0.26F,0F));
		}
		if (invasionLocation==InvasionLocation.Dearic){ 

			spawnOffensiveCamp(new Point3D(-890,9.93F, -2994),new Quaternion(0.00F,0F,1.00F,0F),campTemplate);
			spawnOffensiveCamp(new Point3D(-2,6, -2805),new Quaternion(0.00F,0F,1.00F,0F),campTemplate);
			spawnOffensiveCamp(new Point3D(352.85F,6F, -2606.14F),new Quaternion(0.00F,0F,1.00F,0F),campTemplate);
			
			spawnOffensiveGeneral();
			
			spawnOffensiveQuestOfficer(new Point3D(-890,9, -2994), new Quaternion(0.81F,0F,-0.58F,0F));
			spawnOffensiveQuestOfficer(new Point3D(-1.70F,6, -2802.84F), new Quaternion(0.52F,0F,0.85F,0F));
			spawnOffensiveQuestOfficer(new Point3D(355,6, -2605), new Quaternion(0.00F,0F,1.00F,0F));

			spawnOffensiveSupplyTerminal(new Point3D(-882.80F,9.93F, -2994.76F), new Quaternion(1.00F,0F,0.00F,0F));
			spawnOffensiveSupplyTerminal(new Point3D(-1.33F,6, -2807.17F), new Quaternion(0.87F,0F,0.48F,0F));
			spawnOffensiveSupplyTerminal(new Point3D(347.56F,6, -2604.86F), new Quaternion(0.24F,0F,0.97F,0F));
			
		}		
	}
	
	public void spawnOffensiveCamp(Point3D position, Quaternion quaternion, String campTemplate){
		// object/building/poi/shared_gcw_camp_rebel_recruiter.iff This IS the right template, but the client doesn't spawn it!
		// This works at least object/building/poi/shared_scout_camp_s5.iff
		BuildingObject camp = (BuildingObject) core.objectService.createObject(campTemplate, 0, getInvasionPlanet(invasionLocation), position, quaternion);
		//TangibleObject camp = (TangibleObject)  core.objectService.createObject(campTemplate, 0, core.terrainService.getPlanetByName("talus"), new Point3D(-890,9, -2994), quaternion);
		core.simulationService.add(camp, camp.getPosition().x, camp.getPosition().z, true);
		offensiveCamps.add(camp);
	}
	
	
	public void spawnOffensiveQuestOfficer(Point3D position, Quaternion quaternion){

		String questOfficerTemplate = "rebel_invasion_captain";
		if (invadingFaction==Factions.Imperial)
			questOfficerTemplate = "imp_invasion_captain";
		CreatureObject offensiveQuestOfficer = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(questOfficerTemplate, getInvasionPlanet(invasionLocation).getName(), 0L, position.x, position.y, position.z, quaternion.w, quaternion.x, quaternion.y,quaternion.z,-1);
		offensiveQuestOfficers.add(offensiveQuestOfficer);
	}
	
	public void spawnOffensiveSupplyTerminal(Point3D position, Quaternion quaternion){

		// "object/tangible/terminal/shared_terminal_gcw_build_resource.iff"
		String supplyTerminalTemplate = "object/tangible/terminal/shared_terminal_gcw_reb_offensiv.iff"; 
		if (invadingFaction==Factions.Imperial)
			supplyTerminalTemplate = "object/tangible/terminal/shared_terminal_gcw_imp_offensiv.iff"; 
		
		TangibleObject supplyTerminal1 = (TangibleObject) core.objectService.createObject(supplyTerminalTemplate, 0, getInvasionPlanet(invasionLocation),  position, quaternion);
		core.simulationService.add(supplyTerminal1, supplyTerminal1.getPosition().x, supplyTerminal1.getPosition().z, true);
		supplyTerminal1.setStfFilename("gcw");
		supplyTerminal1.setStfName("terminal_gcw_reb_offensiv_n");
		supplyTerminal1.setDetailFilename("gcw");
		supplyTerminal1.setDetailName("terminal_gcw_reb_offensiv_d");
		if (invadingFaction==Factions.Imperial){
			supplyTerminalTemplate = "object/tangible/terminal/shared_terminal_gcw_imp_offensiv.iff"; 
			supplyTerminal1.setStfFilename("gcw");
			supplyTerminal1.setStfName("terminal_gcw_imp_offensiv_n");
			supplyTerminal1.setDetailFilename("gcw");
			supplyTerminal1.setDetailName("terminal_gcw_imp_offensiv_d");
		}
		offensiveSupplyTerminals.add(supplyTerminal1);
	}
		
	public void preparePhase2(){
		for (GCWPylon pylon : pylonList){
			pylon.convertToPhase2Object();
			core.objectService.destroyObject(pylon.getObjectID());
		}
		pylonList.clear();	
		pylonList = null;	
		for (TangibleObject defensiveSupplyTerminal : defensiveSupplyTerminals){
			core.objectService.destroyObject(defensiveSupplyTerminal.getObjectID());
		}
		for (TangibleObject offensiveSupplyTerminal : offensiveSupplyTerminals){
			core.objectService.destroyObject(offensiveSupplyTerminal.getObjectID());
		}
		
		
	}
	
	public void spawnBanners(){
		if (invasionLocation==InvasionLocation.Keren){ 
			spawnKerenBanners();	
		}
		if (invasionLocation==InvasionLocation.Bestine){ 
			spawnBestineBanners();	
		}
		if (invasionLocation==InvasionLocation.Dearic){ 
			spawnDearicBanners();			
		}			
	}
	
	public void spawnDearicBanners(){
		
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
	
	public void spawnKerenBanners(){
		
		// Starport
		createBanner(1436.87F, 13, 2764.41F, 0.71F, 0.7F);
		createBanner(1436.46F, 13, 2777.70F, 0.71F, 0.7F);
		
	}
	
	public void spawnBestineBanners(){
		
		// Starport	
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
	
	// Phase 1 messages
	
	public void send_Phase1_InvaderMessage1(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_call_to_arms_imperial_" + getInvasionLocationName();	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_call_to_arms_rebel_" + getInvasionLocationName();
		sendMessageToClients(invadingFaction, message);
	}
	
	public void send_Phase1_DefenderMessage1(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_construct_defenses_imperial_" + getInvasionLocationName();
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_construct_defenses_rebel_" + getInvasionLocationName();
		sendMessageToClients(defendingFaction, message);
	}
	
	public void send_Phase1_InvaderMessage2(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_rally_for_invasion_imperial_" + getInvasionLocationName();	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_rally_for_invasion_rebel_" + getInvasionLocationName();
		sendMessageToClients(invadingFaction, message);
	}
	
	public void send_Phase1_DefenderMessage2(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_building_almost_complete_imperial_" + getInvasionLocationName();
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_building_almost_complete_rebel_" + getInvasionLocationName();
		sendMessageToClients(defendingFaction, message);
	}
	
	
	
	// Phase 2 messages
		
	public void send_Phase2_InvaderMessage1(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_attack_city_imperial_" + getInvasionLocationName();	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_attack_city_rebel_" + getInvasionLocationName();
		sendMessageToClients(invadingFaction, message);
	}
	
	public void send_Phase2_DefenderMessage1(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_man_defenses_imperial_" + getInvasionLocationName();
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_man_defenses_rebel_" + getInvasionLocationName();
		sendMessageToClients(defendingFaction, message);
	}
		
	public void send_Phase2_InvaderMessage2(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_attack_half_done_imperial_" + getInvasionLocationName();	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_attack_city_rebel_" + getInvasionLocationName();		
		sendMessageToClients(invadingFaction, message);
	}
	
	public void send_Phase2_DefenderMessage2(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_defense_half_done_imperial_" + getInvasionLocationName();	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_defense_half_done_rebel_" + getInvasionLocationName();
		sendMessageToClients(defendingFaction, message);
	}
			
	public void sendInvaderWonMessage(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_city_won_imperial_" + getInvasionLocationName();	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_city_won_rebel_" + getInvasionLocationName();		
		sendMessageToClients(invadingFaction, message);
	}
	
	public void sendDefenderWonMessage(){		
		String message = "";
		if (getInvasionLocationName().equals("talus_dearic")){  
			if (defendingFaction==Factions.Imperial)
				message = "@gcw:gcw_announcement_won_attack_imeprial_" + getInvasionLocationName();
			if (defendingFaction==Factions.Rebel)
				message = "@gcw:gcw_announcement_won_attack_rebel_" + getInvasionLocationName();
		} else {
			if (defendingFaction==Factions.Imperial)
				message = "@gcw:gcw_announcement_city_safe_imperial_" + getInvasionLocationName();	
			if (defendingFaction==Factions.Rebel)
				message = "@gcw:gcw_announcement_city_safe_rebel_" + getInvasionLocationName();
		}
		sendMessageToClients(defendingFaction, message);
	}
	
	public void sendInvaderLostMessage(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_city_lost_imperial_" + getInvasionLocationName();
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_city_lost_rebel_" + getInvasionLocationName();		
		sendMessageToClients(invadingFaction, message);
	}
	
	public void sendCitySafeMessage(){		
		String message = "";
		if (invadingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_city_end_safe_imperial_" + getInvasionLocationName();	
		if (invadingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_city_end_safe_rebel_" + getInvasionLocationName();		
		sendMessageToClients(invadingFaction, message);
	}
		
	public void sendDefenderLostMessage(){		
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_city_lost_imperial_" + getInvasionLocationName();	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_city_lost_rebel_" + getInvasionLocationName();
		sendMessageToClients(defendingFaction, message);
	}
	
	public void sendInvaderGeneralAttackMessage(){
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_attack_half_done_imperial_" + getInvasionLocationName();
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_attack_half_done_rebel_" + getInvasionLocationName();		
		sendMessageToClients(invadingFaction, message);
	}
	
	public void sendDefenderGeneralAttackMessage(){
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_general_under_attack_imperial_" + getInvasionLocationName();	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_general_under_attack_rebel_" + getInvasionLocationName();
		sendMessageToClients(defendingFaction, message);
	}
	
	public void sendInvaderGeneralHalfMessage(){ // Is there any?
//		String message = "";
//		if (defendingFaction==Factions.Imperial)
//			message = "@gcw:gcw_announcement_city_lost_imperial_" + getInvasionLocationName();
//		if (defendingFaction==Factions.Rebel)
//			message = "@gcw:gcw_announcement_city_lost_rebel_" + getInvasionLocationName();		
//		sendMessageToClients(invadingFaction, message);
	}
	
	public void sendDefenderGeneralHalfMessage(){
		String message = "";
		if (defendingFaction==Factions.Imperial)
			message = "@gcw:gcw_announcement_invasion_almost_defeated_imperial_" + getInvasionLocationName();	
		if (defendingFaction==Factions.Rebel)
			message = "@gcw:gcw_announcement_invasion_almost_defeated_rebel_" + getInvasionLocationName();
		sendMessageToClients(defendingFaction, message);
	}
	
	public void sendMessageToClients(int tofaction, String message){
		String modelTemplate = defendingGeneral.getTemplate();
		if (invadingFaction==tofaction)
			modelTemplate = invasionGeneral.getTemplate();

		ConcurrentHashMap<IoSession, Client> clients = NGECore.getInstance().getActiveConnectionsMap();			
		for (Map.Entry<IoSession, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			if (client.getParent()!=null){
				String faction = ((CreatureObject)client.getParent()).getFaction();				
				if (client.getParent().getPlanet().equals(getInvasionPlanet(invasionLocation)) && faction.equals(getFactionName(tofaction))){
					CommPlayerMessage comm = new CommPlayerMessage(client.getParent().getObjectId(), OutOfBand.ProsePackage(message));
					comm.setTime(2000);
					comm.setModel(modelTemplate);
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
		if (defendingGeneral==null)
			return;
		for (CreatureObject enemy : enemies) {
			AIActor actor = (AIActor)enemy.getAttachment("AI");
			if (actor==null)
				return;
			actor.setLastPositionBeforeStateChange(enemy.getWorldPosition());
			actor.addDefender(defendingGeneral);
		}
	}
	
	public float getDistanceToDefensiveGeneral(CreatureObject checker){
		//float dist = checker.getWorldPosition().getDistance2D(defendingGeneral.getWorldPosition());
		//return dist;
		return NGECore.getInstance().aiService.distanceSquared2D(defendingGeneral.getWorldPosition(), checker.getWorldPosition());
	}
	
	public CreatureObject getDefensiveGeneral(){
		return defendingGeneral;
	}
	
	public boolean checkInvasionDeath(CreatureObject dead){
		boolean value = false;
		if (invasionScheduled){
			String funeralSite = dead.getPlanet().getName();
			if (funeralSite.equals(invasionPlanet)){
				if (invasionLocationCenter.getDistance2D(dead.getWorldPosition())<500){
					value = true;
				}
			}
		}
		return value;
	}
	
	public List<SWGObject> getAccordingCloners(CreatureObject dead){
		List<SWGObject> clonerList = new Vector<SWGObject>();
		if (invasionScheduled){
			String funeralSite = dead.getPlanet().getName();
			if (funeralSite.equals(invasionPlanet)){
				if (invasionLocationCenter.getDistance2D(dead.getWorldPosition())<500){
					if (isInvadingFaction(dead)){
						clonerList = getInvaderClonersForPlanet();
					} 
				}
			}
		}
		return clonerList;
	}
	
	public List<SWGObject> getInvaderClonersForPlanet(){
		return offensiveClonerList;
	}
	
	public boolean isInvadingFaction(CreatureObject checker){
		if (checker.getFaction().equals(getFactionName(invadingFaction)))
				return true;
		return false;
	}
	
	public boolean isdefendingFaction(CreatureObject checker){
		if (checker.getFaction().equals(getFactionName(defendingFaction)))
				return true;
		return false;
	}
	
	private void spawnOffensiveCloners(){
		String clonerTemplate = "object/tangible/gcw/static_base/shared_invisible_cloner_rebel.iff";
		if (invadingFaction==Factions.Imperial){
			clonerTemplate = "object/tangible/gcw/static_base/shared_invisible_cloner_imperial.iff";
		}
		
		Point3D cloner1Pos = null;
		Point3D cloner2Pos = null;
		Point3D cloner3Pos = null;
		switch (invasionPlanet) {
			case "naboo": cloner1Pos = new Point3D(1814.81F, 17.34F, 2183.17F);
						  cloner2Pos = new Point3D(2125.71F, 60, 2756.94F);
						  cloner3Pos = new Point3D(1217.18F, 7.33F, 3062.95F);
						  break;
			case "tatooine": cloner1Pos = new Point3D(-905.35F,24, -3791.04F);
						     cloner2Pos = new Point3D(-1258.13F,44.77F, -3357.66F);
						     cloner3Pos = new Point3D(-1189.65F,62.95F, -3830.22F);
						     break;
			case "talus": cloner1Pos = new Point3D(-895.30F,9.93F, -3003.25F);
						   cloner2Pos = new Point3D(-7.27F,6, -2813.55F);
						   cloner3Pos = new Point3D(347.51F,6F, -2614.82F);
						   break;		
		}
	
		BuildingObject cloner1 = (BuildingObject) NGECore.getInstance().objectService.createObject(clonerTemplate, 0, core.terrainService.getPlanetByName(invasionPlanet),  cloner1Pos, new Quaternion(0.00F,0F,1.00F,0F));
		NGECore.getInstance().simulationService.add(cloner1, cloner1.getPosition().x, cloner1.getPosition().z, true);
		cloner1.setCustomName("Attack camp #1");
		Vector<SpawnPoint> spawnPoints = new Vector<SpawnPoint>();
		spawnPoints.add(new SpawnPoint(cloner1Pos,0F,1F));					
		cloner1.setAttachment("spawnPoints", spawnPoints);
		core.mapService.addLocation(cloner1.getPlanet(), "Attack camp #1", cloner1.getPosition().x, cloner1.getPosition().z, (byte)5, (byte)0, (byte)0);
		
		BuildingObject cloner2 = (BuildingObject) NGECore.getInstance().objectService.createObject(clonerTemplate, 0, core.terrainService.getPlanetByName(invasionPlanet),  cloner2Pos, new Quaternion(0.00F,0F,1.00F,0F));
		NGECore.getInstance().simulationService.add(cloner2, cloner2.getPosition().x, cloner2.getPosition().z, true);
		cloner2.setCustomName("Attack camp #2");
		spawnPoints = new Vector<SpawnPoint>();
		spawnPoints.add(new SpawnPoint(cloner2Pos,0F,1F));					
		cloner2.setAttachment("spawnPoints", spawnPoints);
		core.mapService.addLocation(cloner2.getPlanet(), "Attack camp #2", cloner2.getPosition().x, cloner2.getPosition().z, (byte)5, (byte)0, (byte)0);
		
		BuildingObject cloner3 = (BuildingObject) NGECore.getInstance().objectService.createObject(clonerTemplate, 0, core.terrainService.getPlanetByName(invasionPlanet),  cloner3Pos, new Quaternion(0.00F,0F,1.00F,0F));
		NGECore.getInstance().simulationService.add(cloner3, cloner3.getPosition().x, cloner3.getPosition().z, true);
		cloner3.setCustomName("Attack camp #3");
		spawnPoints = new Vector<SpawnPoint>();
		spawnPoints.add(new SpawnPoint(cloner3Pos,0F,1F));					
		cloner3.setAttachment("spawnPoints", spawnPoints);
		core.mapService.addLocation(cloner3.getPlanet(), "Attack camp #3", cloner3.getPosition().x, cloner3.getPosition().z, (byte)5, (byte)0, (byte)0);
		
		offensiveClonerList.add(cloner1);
		offensiveClonerList.add(cloner2);
		offensiveClonerList.add(cloner3);
	}
	
	private void spawnDemoralizedSoldier(int faction, Point3D pos, float qw, float qy){		
		String soldierTemplate = "rebel_invasion_demoralized_soldier";
		if (faction==Factions.Imperial){
			soldierTemplate = "imp_invasion_demoralized_soldier";
		}	

		CreatureObject soldier = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(soldierTemplate, getInvasionPlanet(invasionLocation).getName(), 0L, pos.x, pos.y, pos.z, qw,0F,qy,0F,-1);
		NGECore.getInstance().invasionService.registerDemoralizedSoldier(soldier);		
	}
	
	private void spawnDisabledVehicle(int faction, Point3D pos, float qw, float qy){
		String vehicleTemplate = "rebel_invasion_disabled_atxt";
		if (faction==Factions.Imperial){
			vehicleTemplate = "imp_invasion_disabled_atst";
		}	

		CreatureObject vehicle = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(vehicleTemplate, getInvasionPlanet(invasionLocation).getName(), 0L, pos.x, pos.y, pos.z, qw,0F,qy,0F,-1);
		vehicle.setPosture((byte) Posture.Incapacitated); 
		NGECore.getInstance().invasionService.registerDisabledVehicle(vehicle);	
	}
	
	private void playVictoryMusic(int winnerFaction){
		String audioTemplate = "sound/music_themequest_victory_rebel.snd";
		if (winnerFaction==Factions.Imperial)
			audioTemplate = "sound/music_themequest_victory_imperial.snd";
		
//		Vector<CreatureObject> allPlayers = core.simulationService.getAllNearSameFactionPlayers(500,invasionLocationCenter, getInvasionPlanet(invasionLocation), getFactionName(winnerFaction));
//		for (CreatureObject player : allPlayers) {			
//			if (player.getFaction().length() > 0 && player.getFactionStatus() > FactionStatus.OnLeave) {
//				player.playMusic(audioTemplate);
//			}			
//		}
		Vector<CreatureObject> allPlayers = core.simulationService.getAllNearPlayers(500, getInvasionPlanet(invasionLocation), invasionLocationCenter);
		for (CreatureObject player : allPlayers) {	
			player.playMusic(audioTemplate);			
		}
	}
	
	// Utility method
	public void arrangePylons(Point3D center, int angle){
		
		int pylondistance= 4;
		
		Point3D pylonRefPos1 = new Point3D(-2*pylondistance,0,-pylondistance);
		Point3D pylonRefPos2 = new Point3D(-pylondistance,0,pylondistance);
		Point3D pylonRefPos3 = new Point3D(0,0,-pylondistance);
		Point3D pylonRefPos4 = new Point3D(pylondistance,0,pylondistance);
		Point3D pylonRefPos5 = new Point3D(2*pylondistance,0,-pylondistance);
		
		Point3D pylonTurnedPos1 = turnPoint(pylonRefPos1, angle);
		Point3D pylonTurnedPos2 = turnPoint(pylonRefPos2, angle);
		Point3D pylonTurnedPos3 = turnPoint(pylonRefPos3, angle);
		Point3D pylonTurnedPos4 = turnPoint(pylonRefPos4, angle);
		Point3D pylonTurnedPos5 = turnPoint(pylonRefPos5, angle);
				
		
		float positionY1 = core.terrainService.getHeight(getInvasionPlanet(invasionLocation).getID(), center.x + pylonTurnedPos1.x, center.z + pylonTurnedPos1.z);
		float positionY2 = core.terrainService.getHeight(getInvasionPlanet(invasionLocation).getID(), center.x + pylonTurnedPos2.x, center.z + pylonTurnedPos2.z);
		float positionY3 = core.terrainService.getHeight(getInvasionPlanet(invasionLocation).getID(), center.x + pylonTurnedPos3.x, center.z + pylonTurnedPos3.z);
		float positionY4 = core.terrainService.getHeight(getInvasionPlanet(invasionLocation).getID(), center.x + pylonTurnedPos4.x, center.z + pylonTurnedPos4.z);
		float positionY5 = core.terrainService.getHeight(getInvasionPlanet(invasionLocation).getID(), center.x + pylonTurnedPos5.x, center.z + pylonTurnedPos5.z);
		
		Point3D pylonWorldPos1 = new Point3D(center.x + pylonTurnedPos1.x,positionY1,center.z + pylonTurnedPos1.z);
		Point3D pylonWorldPos2 = new Point3D(center.x + pylonTurnedPos2.x,positionY2,center.z + pylonTurnedPos2.z);
		Point3D pylonWorldPos3 = new Point3D(center.x + pylonTurnedPos3.x,positionY3,center.z + pylonTurnedPos3.z);
		Point3D pylonWorldPos4 = new Point3D(center.x + pylonTurnedPos4.x,positionY4,center.z + pylonTurnedPos4.z);
		Point3D pylonWorldPos5 = new Point3D(center.x + pylonTurnedPos5.x,positionY5,center.z + pylonTurnedPos5.z);
				
		System.out.println("new Point3D(" + pylonWorldPos1.x + "F, " + pylonWorldPos1.y + "F, " + pylonWorldPos1.z +  "F)");
		System.out.println("new Point3D(" + pylonWorldPos2.x + "F, " + pylonWorldPos2.y + "F, " + pylonWorldPos2.z +  "F)");
		System.out.println("new Point3D(" + pylonWorldPos3.x + "F, " + pylonWorldPos3.y + "F, " + pylonWorldPos3.z +  "F)");
		System.out.println("new Point3D(" + pylonWorldPos4.x + "F, " + pylonWorldPos4.y + "F, " + pylonWorldPos4.z +  "F)");
		System.out.println("new Point3D(" + pylonWorldPos5.x + "F, " + pylonWorldPos5.y + "F, " + pylonWorldPos5.z +  "F)");
	}
	
	private Point3D turnPoint(Point3D orig, int angle){
		double origangle = Math.toDegrees(Math.atan2(orig.x, orig.z));	
		double hypo = Math.sqrt(orig.x*orig.x+orig.z*orig.z);
		double newAngle = origangle + angle;		
		float newX = (float)(hypo * Math.sin(Math.toRadians(newAngle)));
		float newZ = (float)(hypo * Math.cos(Math.toRadians(newAngle)));
		Point3D result = new Point3D(newX,0,newZ);
		return result;
	}
	
	private Set<Entry<Integer,CreatureObject>> getInvadingCreaturesEntrySet(){
		synchronized(invadingCreatures){
			return invadingCreatures.entrySet();
		}
	}
	
	private Set<Entry<Integer,CreatureObject>> getDefendingCreaturesEntrySet(){
		synchronized(defendingCreatures){
			return defendingCreatures.entrySet();
		}
	}
	

	public void setInvasionLocationCenter(){
		switch (invasionLocation){
			case InvasionLocation.Dearic : invasionLocationCenter = new Point3D(452,6,-3002);
										   break;
			case InvasionLocation.Keren :  invasionLocationCenter = new Point3D(1840,12,2635);
			  							   break;
			case InvasionLocation.Bestine : invasionLocationCenter = new Point3D(-1192,12,-3606);
			  							    break;
		}
	}
	
	public String getInvasionLocationName(){
		switch (invasionLocation){
			case InvasionLocation.Dearic  : return "talus_dearic";										   
			case InvasionLocation.Keren   : return "naboo_keren";			  						
			case InvasionLocation.Bestine : return "tatooine_bestine";			  						
			}
		return "";
	}
	
	public String getFactionName(int faction){
		if (faction==Factions.Rebel)
			return "rebel";
		if (faction==Factions.Imperial)
			return "imperial";
		return "neutral";
	}
	

	public boolean isDefendingGeneralAlive() {
		return defendingGeneralAlive;
	}
	
	@Override
	public void shutdown() {
		
	}
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0,
			Map<Integer, INetworkRemoteEvent> arg1) {		
	}
	
	public class InvasionLocation {
		
		public static final int Keren = 0;
		public static final int Bestine = 1;
		public static final int Dearic = 2;
		
	}

	public int getInvasionLocation() {
		return invasionLocation;
	}
	
	public String getInvasionPlanetName() {
		return getInvasionPlanet(invasionLocation).getName();
	}

	public void setInvasionLocation(int invasionLocation) {
		this.invasionLocation = invasionLocation;
	}	
}
