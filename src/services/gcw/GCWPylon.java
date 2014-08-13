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
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.NGECore;
import protocol.swg.PlayClientEffectObjectTransformMessage;
import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.datatables.FactionStatus;
import resources.datatables.Factions;
import resources.datatables.Options;
import resources.datatables.WeaponType;
import resources.objects.creature.CreatureObject;
import resources.objects.installation.InstallationObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.ai.TurretAIActor;

/** 
 * @author Charon 
 */

public class GCWPylon extends TangibleObject{
	
	private static final long serialVersionUID = 1L;
	private int pylonType = 0;
	private int pylonFaction = 0; 
	private int spawnLevel   = 0;  
	private int patrolRoute = 0;
	private int resourceFilling = 0;
	private int delay = 0;
	
	private static Map<Integer,String> typeStfMapping = new ConcurrentHashMap<Integer,String>();
	static{
		typeStfMapping.put(PylonType.SoldierPatrol, "Soldier Patrol Pylon");
		typeStfMapping.put(PylonType.SoldierDefense, "Soldier Patrol Pylon");
		typeStfMapping.put(PylonType.VehiclePatrol, "Soldier Patrol Pylon");
		typeStfMapping.put(PylonType.SiegeVehiclePatrol, "Soldier Patrol Pylon");
		typeStfMapping.put(PylonType.Barricade, "Soldier Patrol Pylon");
		typeStfMapping.put(PylonType.Turret, "Soldier Patrol Pylon");
		typeStfMapping.put(PylonType.Tower, "Soldier Patrol Pylon");
	}
	
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public GCWPylon() {
		super();
	}
	
	public GCWPylon(long objectID, Planet planet, Point3D position, Quaternion orientation, String template){
		super(objectID, planet, position, orientation, template);
		this.setAttachment("Filling",0);
		this.setResourceFilling(0);
	}
	
	public int getPylonType() {
		return pylonType;
	}

	public void setPylonType(int pylonType) {
		this.pylonType = pylonType;
		
		String pylonName = "";
		switch (pylonType){
			case PylonType.Barricade: pylonName="Barricade Construction Site";
					                  break;
			case PylonType.SoldierDefense: pylonName="Soldier Construction Site";
	        							   break;
			case PylonType.SoldierPatrol: pylonName="Patrol Construction Site";
	        							  break;
			case PylonType.VehiclePatrol: pylonName="Vehicle Construction Site";
	        							  break;
			case PylonType.SiegeVehiclePatrol: pylonName="Siege Vehicle Construction Site";
	        								   break;
			case PylonType.Tower: pylonName="Tower Construction Site";
	        					  break;
			case PylonType.Turret: pylonName="Turret Construction Site";
								   break;
		}
		
		this.setCustomName(pylonName + " 0/100");
		this.setAttachment("Name", pylonName);
		
	}
	
	public int getPylonFaction() {
		return pylonFaction;
	}

	public void setPylonFaction(int pylonFaction) {
		this.pylonFaction = pylonFaction;
	}
	
	public int getSpawnLevel() {
		return spawnLevel;
	}

	public void setSpawnLevel(int spawnLevel) {
		this.spawnLevel = spawnLevel;
	}
		
	public void setPatrolRoute(int route){
		this.patrolRoute = route;
	}
	
	public int getPatrolRoute(){
		return this.patrolRoute;
	}
	
	public void convertToPhase2Object(){
		
		//if (determinePylonLevel()==0)
			//return;
		
		resourceFilling = 100;
		spawnLevel = 3;
//		resourceFilling = 10;
//		spawnLevel = 1;
		
		switch (pylonType){
			case PylonType.Barricade: createBarricade();
					                  break;
			case PylonType.SoldierDefense: createSpawner();
	        							   break;
			case PylonType.SoldierPatrol: createSpawner();
	        							  break;
			case PylonType.VehiclePatrol: createSpawner();
	        							  break;
			case PylonType.SiegeVehiclePatrol: createSpawner();
	        								   break;
			case PylonType.Tower: createTower();
	        					  break;
			case PylonType.Turret: createTurret();
								   break;
		}			
	}
	
	public int determinePylonLevel(){
		if (resourceFilling==0) spawnLevel = 0;
		if (resourceFilling>0 && resourceFilling<50) spawnLevel = 1;
		if (resourceFilling>=50 && resourceFilling<100) spawnLevel = 2;
		if (resourceFilling==100) spawnLevel = 3;
		return spawnLevel;
	}
	

	public void createSpawner(){
		GCWSpawner spawner = (GCWSpawner) NGECore.getInstance().objectService.createObject("object/tangible/loot/creature_loot/collections/shared_dejarik_table_base.iff", 0, this.getPlanet(), this.getWorldPosition(), this.getOrientation());
		NGECore.getInstance().simulationService.add(spawner, spawner.getPosition().x, spawner.getPosition().z, true);
		spawner.setCustomName("");
		spawner.setStfFilename("battlefield");
		spawner.setStfName("battlefield_marker_name");
		spawner.setPatrolRoute(getPatrolRoute());
		spawner.setDelay(getDelay());
		spawner.setSpawnLevel(3);
		spawner.setSpawnerFaction(getPylonFaction());
		spawner.setSpawnerType(getPylonType());	
		NGECore.getInstance().invasionService.registerPhase2Objects(spawner);
	}
	
	public void createTurret(){	
		String turretTemplate="object/installation/turret/gcw/shared_adv_turret_dish_sm_heat.iff";
		//String turretTemplate="object/installation/turret/gcw/shared_adv_turret_dish_large_heat.iff";
		if (spawnLevel==2) turretTemplate="object/installation/turret/gcw/shared_adv_turret_dish_large_heat.iff";
		if (spawnLevel==3) turretTemplate="object/installation/turret/gcw/shared_adv_turret_block_med_heat.iff";
		Planet spawnPlanet = this.getPlanet();
		Point3D spawnPos = this.getWorldPosition();
		Quaternion quat = this.getOrientation();
		NGECore.getInstance().objectService.destroyObject(this.getObjectID());
		InstallationObject turret = (InstallationObject) NGECore.getInstance().objectService.createObject(turretTemplate, 0, spawnPlanet, spawnPos, quat);
		NGECore.getInstance().simulationService.add(turret, turret.getPosition().x, turret.getPosition().z, true);
		float positionY = NGECore.getInstance().terrainService.getHeight(turret.getPlanetId(), turret.getPosition().x, turret.getPosition().z);
		if (turret.getWorldPosition().getDistance2D(new Point3D(1779.27F, 12, 2517.37F))<5 || turret.getWorldPosition().getDistance2D(new Point3D(1797.00F, 12, 2518.32F))<5){
			positionY +=4; // The terrainService delivers incorrect heights on that bridge near the general in Keren
		}				
		turret.setPosition(new Point3D(turret.getPosition().x,positionY, turret.getPosition().z));
		String customName = "Rebel Turret";
		String factionString = "rebel";
		if (getPylonFaction()==Factions.Imperial){
			customName = "Imperial Turret";
			factionString = "imperial";
		}
		turret.setCustomName(customName);

		turret.setOptionsBitmask(Options.ATTACKABLE | Options.QUEST);
		//turret.setOptionsBitmask(Options.QUEST);
		turret.setFaction(factionString);
		turret.setFactionStatus(FactionStatus.Combatant);
		turret.setMaximumCondition(50000);
		TurretAIActor actor = new TurretAIActor(turret, turret.getPosition(), scheduler);
		turret.setAttachment("AI", actor);
		NGECore.getInstance().invasionService.registerPhase2Objects(turret);
				
		WeaponObject turretWeapon = (WeaponObject) NGECore.getInstance().objectService.createObject("object/weapon/ranged/turret/shared_turret_heat.iff", turret.getPlanet());
		turretWeapon.setAttackSpeed(5.0F);
		//turretWeapon.setAttackSpeed(0.50F);
		turretWeapon.setWeaponType(WeaponType.RIFLE);
		turretWeapon.setMaxRange(60.0F);
		turretWeapon.setDamageType("energy");
		
		int minDamage = 1500; int maxDamage = 2000;		
		if (spawnLevel==2) {minDamage=2500; maxDamage=3500;}
		if (spawnLevel==3) {minDamage=4000; maxDamage=5000;}
		
		turretWeapon.setMinDamage(minDamage);
		turretWeapon.setMaxDamage(maxDamage);
		//turret.add(turretWeapon); // Does not have to be added explicitly, would cause engine slot arrangement error message
		turret.setAttachment("TurretWeapon",turretWeapon.getObjectID());
	}
	
	public void createBarricade(){	
		String barricadeTemplate = "object/tangible/destructible/shared_gcw_rebel_barricade.iff";
		TangibleObject barricade = (TangibleObject) NGECore.getInstance().objectService.createObject(barricadeTemplate, 0, this.getPlanet(),  this.getWorldPosition(), this.getOrientation());
		NGECore.getInstance().simulationService.add(barricade, barricade.getPosition().x, barricade.getPosition().z, true);
		String customName = "Rebel Barricade";
		String factionString = "rebel";
		if (getPylonFaction()==Factions.Imperial){
			customName = "Imperial Barricade";
			factionString = "imperial";
		}
		barricade.setCustomName(customName);
		barricade.setOptionsBitmask(Options.ATTACKABLE | Options.QUEST);
		//barricade.setOptionsBitmask(Options.QUEST);
		barricade.setFaction(factionString);
		barricade.setFactionStatus(FactionStatus.Combatant);
		int maxCondition = 50000;
		maxCondition += 5000 * resourceFilling;
		
		barricade.setMaximumCondition(maxCondition);
		NGECore.getInstance().invasionService.registerPhase2Objects(barricade);
		
		final Future<?>[] fut = {null};
		fut[0] = scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { 
				try {
					if (NGECore.getInstance().objectService.getObject(barricade.getObjectID())==null){
		                Thread.yield();
		                fut[0].cancel(false);
					}
								
					Vector<CreatureObject> nearAllies = NGECore.getInstance().simulationService.getAllNearSameFactionCreatures(7,barricade.getWorldPosition(),barricade.getPlanet(),NGECore.getInstance().invasionService.getFactionName(getPylonFaction()));					
					for (CreatureObject ally : nearAllies){
						if (! ally.hasBuff("barricade_defender")){
							NGECore.getInstance().buffService.addBuffToCreature(ally, "barricade_defender", ally);
						} else {
							
						}
					} // towFacGuardDefenseItem
					Thread.currentThread().interrupt();
				} catch (Exception e) {
					System.err.println("Exception in Barricade thread " + e.getMessage());
			}
			}
		}, 5, 2, TimeUnit.SECONDS);
	}

	public void createTower(){		
		String towerTemplate = "object/tangible/destructible/shared_gcw_rebel_tower.iff";
		String factionString = "rebel";
		String towerString = "Rebel Tower";
		if (getPylonFaction()==Factions.Imperial){
			towerTemplate = "object/tangible/destructible/shared_gcw_imperial_tower.iff";
			factionString = "imperial";
			towerString = "Imperial Tower";
		}		
		TangibleObject tower = (TangibleObject) NGECore.getInstance().objectService.createObject(towerTemplate, 0, this.getPlanet(),  this.getWorldPosition(), this.getOrientation());
		NGECore.getInstance().simulationService.add(tower, tower.getPosition().x, tower.getPosition().z, true);
		tower.setCustomName(towerString);
		tower.setOptionsBitmask(Options.ATTACKABLE | Options.QUEST);
		//tower.setOptionsBitmask(Options.QUEST);
		tower.setFaction(factionString);
		tower.setFactionStatus(FactionStatus.Combatant);
		int maxCondition = 50000;
		maxCondition += 5000 * resourceFilling;
		
		tower.setMaximumCondition(maxCondition);
		NGECore.getInstance().invasionService.registerPhase2Objects(tower);
		
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
								NGECore.getInstance().buffService.addBuffToCreature(ally, "tower_defender", ally);
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
	
	public int getResourceFilling() {
		return resourceFilling;
	}

	public void setResourceFilling(int resourceFilling) {
		this.resourceFilling = resourceFilling;
	}
	
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public class PylonType {
		
		public static final int SoldierPatrol = 0;
		public static final int SoldierDefense = 1;
		public static final int VehiclePatrol = 2;
		public static final int SiegeVehiclePatrol = 3;
		public static final int Barricade = 4;
		public static final int Turret = 5;
		public static final int Tower = 6;
		
	}
	/*
    Soldier Patrol Pylons - Spawns a continuous stream of NPCs to aid in the battle - shown with a Helmet icon.
    Vehicle Patrol Pylons - Spawns vehicles that are considerably more lethal than the Soldiers - shown with a Terminal icon.
    Siege Vehicle Patrol Pylon - Spawns Rebel Hailfire Driods or Imperial AT-ATs (only spawn at a Level 3 Pylon) - shown with the same Terminal icon.
    Barricade Pylon - Spawns a barricade that defends units behind it by giving them a defensive buff - shown with a Barricade icon.
    Turret Pylon - Spawns an AI controlled turret that fires on enemy units - shown with a Gun Barrel or Turret Dish icon.
    Tower Pylon - Spawns a watch tower that grants intelligence to nearby NPCs and offers combat missions to players - shown with a Tower icon. 
	*/
	
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(getBaseline(3).getBaseline());
			destination.getSession().write(getBaseline(6).getBaseline());
			
			Client parent = ((getGrandparent() == null) ? null : getGrandparent().getClient());
			
			if (parent != null && destination == parent) {
				destination.getSession().write(getBaseline(8).getBaseline());
				destination.getSession().write(getBaseline(9).getBaseline());
			}

			if (destination.getParent() != this) {
				// The icon disappears after a few seconds, no idea why. Must be the commandString requirement
				float y = -0.1F;
			    //float qz= 1.06535322E9F;
			    float qz= 99999;
			    //String animName = "pt_gcw_quest_rebel_barricade.prt";
			    String animName = "pt_gcw_quest_imperial_patrol_2.prt";
			    //String animName = "pt_reb_insignia_02.prt";  //   pt_gcw_quest_imperial_patrol_2.prt
				Point3D effectorPosition = new Point3D(0,y,0);
				Quaternion effectorOrientation = new Quaternion(0,0,0,qz);
				PlayClientEffectObjectTransformMessage lmsg = new PlayClientEffectObjectTransformMessage("appearance/" + animName,this.getObjectID(),"",effectorPosition,effectorOrientation);
				//PlayClientEffectObjectMessage lmsg = new PlayClientEffectObjectMessage("appearance/" + animName, this.getObjectID(), "");
				destination.getSession().write(lmsg.serialize());
			}
		}
	}
}
