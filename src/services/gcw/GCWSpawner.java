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

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import main.NGECore;
import resources.datatables.Factions;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;

/** 
 * @author Charon 
 */

@SuppressWarnings("unused")
public class GCWSpawner extends TangibleObject{
	
	private static final long serialVersionUID = 1L;
	private int spawnerType = 0;
	private int spawnerFaction = 0; 
	private int spawnLevel   = 0;  
	private int totalSpawnedUnits = 0;
	private String[] soldierTemplates   = new String[]{""};
	private String[] rebelSoldierTemplates1    = new String[]{""};
	private String[] rebelSoldierTemplates2    = new String[]{""};
	private String[] rebelSoldierTemplates3    = new String[]{"gcw_city_rebel_soldier"};
	private String[] imperialSoldierTemplates1 = new String[]{""};
	private String[] imperialSoldierTemplates2 = new String[]{""};
	private String[] imperialSoldierTemplates3 = new String[]{"elite_imperial_stormtrooper_90"};
	
	private String[] vehicleTemplates   = new String[]{""};
	private String[] rebelVehicleTemplates1    = new String[]{"rebel_invasion_at_xt"};
	private String[] rebelVehicleTemplates2    = new String[]{"rebel_invasion_at_xt"};
	private String[] rebelVehicleTemplates3    = new String[]{"rebel_invasion_at_xt"};
	private String[] imperialVehicleTemplates1 = new String[]{"imp_invasion_at_st"};
	private String[] imperialVehicleTemplates2 = new String[]{"imp_invasion_at_st"};
	private String[] imperialVehicleTemplates3 = new String[]{"imp_invasion_at_st"};
	
	
	private String[] siegeVehicleTemplates   = new String[]{""};
	private String[] rebelSiegeVehicleTemplates1    = new String[]{"rebel_invasion_hailfire_droid"};
	private String[] rebelSiegeVehicleTemplates2    = new String[]{"rebel_invasion_hailfire_droid"};
	private String[] rebelSiegeVehicleTemplates3    = new String[]{"rebel_invasion_hailfire_droid"};
	private String[] imperialSiegeVehicleTemplates1 = new String[]{"imp_invasion_at_at"};
	private String[] imperialSiegeVehicleTemplates2 = new String[]{"imp_invasion_at_at"};
	private String[] imperialSiegeVehicleTemplates3 = new String[]{"imp_invasion_at_at"};
	
	private String barricadeTemplate = "";
	private String rebelBarricadeTemplate = "object/tangible/destructible/shared_gcw_rebel_barricade.iff";
	private String imperialBarricadeTemplate = "object/tangible/destructible/shared_gcw_imperial_barricade.iff";
	
	
	private String turretTemplates = "";
	private String[] rebelTurretTemplates1    = new String[]{""};
	private String[] rebelTurretTemplates2    = new String[]{""};
	private String[] rebelTurretTemplates3    = new String[]{"object/tangible/destructible/shared_gcw_rebel_turret.iff"};
	private String[] imperialTurretTemplates1 = new String[]{""};
	private String[] imperialTurretTemplates2 = new String[]{""};
	private String[] imperialTurretTemplates3 = new String[]{"object/tangible/destructible/shared_gcw_imperial_turret.iff"};
	
	private String towerTemplate = "";
	private String rebelTowerTemplate = "object/tangible/destructible/shared_gcw_rebel_tower.iff";
	private String imperialTowerTemplate = "object/tangible/destructible/shared_gcw_imperial_tower.iff";
	
	private Vector<Point3D> patrolRoute = new Vector<Point3D>();
	private CreatureObject lastDefender = null;
	private int delay = 0;
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public GCWSpawner() {
		super();
	}
	
	public GCWSpawner(long objectID, Planet planet, Point3D position, Quaternion orientation, String template){
		super(objectID, planet, position, orientation, template);	
	}
	
	public int getSpawnerType() {
		return spawnerType;
	}

	public void setSpawnerType(int spawnerType) {
		this.spawnerType = spawnerType;
		this.setCustomName("");
		if (spawnerType==SpawnerType.SoldierPatrol){
			final Future<?>[] fut1 = {null};
			fut1[0] = scheduler.scheduleAtFixedRate(new Runnable() {
				@Override public void run() { 
					try {
						if (NGECore.getInstance().invasionService.getInvasionPhase()>2){
			                Thread.yield();
			                fut1[0].cancel(false);
						}
						spawnSoldier();	
					} catch (Exception e) {
						System.err.println("Exception in SoldierPatrolPylon->scheduleAtFixedRate->spawnSoldier() " + e.getMessage());
					}
				}
			}, getDelay(), 240, TimeUnit.SECONDS); //120
		}
		
		if (spawnerType==SpawnerType.SoldierDefense){
			final Future<?>[] fut2 = {null};
			fut2[0] = scheduler.scheduleAtFixedRate(new Runnable() {
				@Override public void run() { 
					try {
						if (NGECore.getInstance().invasionService.getInvasionPhase()>2){
			                Thread.yield();
			                fut2[0].cancel(false);
						}
						spawnSoldierDefender();	
					} catch (Exception e) {
						System.err.println("Exception in SoldierDefensePylon->scheduleAtFixedRate->spawnSoldier() " + e.getMessage());
					}
				}
			}, 5, 90, TimeUnit.SECONDS);
		}
		
		if (spawnerType==SpawnerType.VehiclePatrol){
			final Future<?>[] fut3 = {null};
			fut3[0] = scheduler.scheduleAtFixedRate(new Runnable() {
				@Override public void run() { 
					try {
						if (NGECore.getInstance().invasionService.getInvasionPhase()>2){
			                Thread.yield();
			                fut3[0].cancel(false);
						}
						spawnVehicle();	
					} catch (Exception e) {
						System.err.println("Exception in SoldierPatrolPylon->scheduleAtFixedRate->spawnVehicle() " + e.getMessage());
					}
				}
			}, getDelay(), 240, TimeUnit.SECONDS); //240
		}
		
		if (spawnerType==SpawnerType.SiegeVehiclePatrol){
			final Future<?>[] fut4 = {null};
			fut4[0] = scheduler.scheduleAtFixedRate(new Runnable() {
				@Override public void run() { 
					try {
						if (NGECore.getInstance().invasionService.getInvasionPhase()>2){
			                Thread.yield();
			                fut4[0].cancel(false);
						}
						spawnSiegeVehicle();	
					} catch (Exception e) {
						System.err.println("Exception in SoldierPatrolPylon->scheduleAtFixedRate->spawnSoldier() " + e.getMessage());
					}
				}
			}, getDelay(), 600, TimeUnit.SECONDS);
		}
		
	}
	
	public int getSpawnerFaction() {
		return spawnerFaction;
	}

	public void setSpawnerFaction(int spawnerFaction) {
		this.spawnerFaction = spawnerFaction;
	}
	
	public int getSpawnLevel() {
		return spawnLevel;
	}

	public void setSpawnLevel(int spawnLevel) {
		this.spawnLevel = spawnLevel;
	}
	
	public void spawnSoldier(){
		if (NGECore.getInstance().invasionService.getInvasionPhase()==2){
			String stfName = "gcw_city_rebel_specforce_defense";
			if (spawnerFaction==Factions.Rebel){
				switch (spawnLevel) {
					case 1: soldierTemplates = rebelSoldierTemplates1;
							stfName = "gcw_city_rebel_grunt_assault";
							break;
					case 2: soldierTemplates = rebelSoldierTemplates2;
							stfName = "gcw_city_rebel_grunt_assault";
							break;
					case 3: soldierTemplates = rebelSoldierTemplates3;
							stfName = "gcw_city_rebel_specforce_assault";
							break;
				}
			}
			if (spawnerFaction==Factions.Imperial){
				switch (spawnLevel) {
					case 1: soldierTemplates = imperialSoldierTemplates1;
							break;
					case 2: soldierTemplates = imperialSoldierTemplates2;
							break;
					case 3: soldierTemplates = imperialSoldierTemplates3;
							break;
				}
			}	
			String soldierTemplate = soldierTemplates[new Random().nextInt(soldierTemplates.length)];	
			//if (totalSpawnedUnits==0){
			CreatureObject soldier = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(soldierTemplate, this.getPlanet().getName(), 0L, this.getPosition().x, this.getPosition().y, this.getPosition().z, this.getOrientation().w, this.getOrientation().x, this.getOrientation().y,this.getOrientation().z,-1);
			if (spawnerFaction==Factions.Rebel)
				soldier.setStfName(stfName);
			if (spawnerFaction==Factions.Imperial){				
				String[] Prefices = new String[]{"GK-","TK-"};
				int suffix = 300 + new Random().nextInt(700);
				String stormtrooperID = Prefices[new Random().nextInt(Prefices.length)]+suffix;
				soldier.setCustomName(stormtrooperID);
			}
			NGECore.getInstance().invasionService.registerInvader(soldier);
			totalSpawnedUnits++;			
			soldier.setAttachment("IsInvader","yes");
			NGECore.getInstance().aiService.setPatrol(soldier, patrolRoute);	
			NGECore.getInstance().aiService.setPatrolLoop(soldier,false);
			
			//}
		}
	}
	
	public void spawnSoldierDefender(){
		
		if (NGECore.getInstance().invasionService.getInvasionPhase()==2){
			String stfName = "gcw_city_rebel_specforce_defense";
			if (spawnerFaction==Factions.Rebel){
				switch (spawnLevel) {
					case 1: soldierTemplates = rebelSoldierTemplates1;
							stfName = "gcw_city_rebel_grunt_defense";
							break;
					case 2: soldierTemplates = rebelSoldierTemplates2;
							stfName = "gcw_city_rebel_grunt_defense";
						    break;
					case 3: soldierTemplates = rebelSoldierTemplates3;
							stfName = "gcw_city_rebel_specforce_defense";
						    break;
				}
			}
			if (spawnerFaction==Factions.Imperial){
				switch (spawnLevel) {
					case 1: soldierTemplates = imperialSoldierTemplates1;
					        break;
					case 2: soldierTemplates = imperialSoldierTemplates2;
						    break;
					case 3: soldierTemplates = imperialSoldierTemplates3;
					        break;
				}
			}	
			if (lastDefender!=null)
				if (lastDefender.getPosture()!=13 && lastDefender.getPosture()!=13)
					return;
			String soldierTemplate = soldierTemplates[new Random().nextInt(soldierTemplates.length)];	
			lastDefender = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(soldierTemplate, this.getPlanet().getName(), 0L, this.getPosition().x, this.getPosition().y, this.getPosition().z, this.getOrientation().w, this.getOrientation().x, this.getOrientation().y,this.getOrientation().z,-1);
			if (spawnerFaction==Factions.Rebel)
				lastDefender.setStfName(stfName);
			if (spawnerFaction==Factions.Imperial){				
				String[] Prefices = new String[]{"GK-","TK-"};
				int suffix = 300 + new Random().nextInt(700);
				String stormtrooperID = Prefices[new Random().nextInt(Prefices.length)]+suffix;
				lastDefender.setCustomName(stormtrooperID);
			}
			NGECore.getInstance().invasionService.registerDefender(lastDefender);
			totalSpawnedUnits++;
		}
	}
	
	public void spawnVehicle(){
		
		if (NGECore.getInstance().invasionService.getInvasionPhase()==2){
			if (spawnerFaction==Factions.Rebel){
				switch (spawnLevel) {
					case 1: vehicleTemplates = rebelVehicleTemplates1;
					case 2: vehicleTemplates = rebelVehicleTemplates2;
					case 3: vehicleTemplates = rebelVehicleTemplates3;
				}
			}
			if (spawnerFaction==Factions.Imperial){
				switch (spawnLevel) {
					case 1: vehicleTemplates = imperialVehicleTemplates1;
					case 2: vehicleTemplates = imperialVehicleTemplates2;
					case 3: vehicleTemplates = imperialVehicleTemplates3;
				}
			}	
			String vehicleTemplate = vehicleTemplates[new Random().nextInt(vehicleTemplates.length)];	
			CreatureObject vehicle = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(vehicleTemplate, this.getPlanet().getName(), 0L, this.getPosition().x, this.getPosition().y, this.getPosition().z, this.getOrientation().w, this.getOrientation().x, this.getOrientation().y,this.getOrientation().z,-1);
			NGECore.getInstance().invasionService.registerInvader(vehicle);
			totalSpawnedUnits++;
			vehicle.setAttachment("IsInvader","yes");
			NGECore.getInstance().aiService.setPatrol(vehicle, patrolRoute);	
			NGECore.getInstance().aiService.setPatrolLoop(vehicle,false);			
		}
	}
	
	public void spawnSiegeVehicle(){
		
		if (NGECore.getInstance().invasionService.getInvasionPhase()==2){
			if (spawnerFaction==Factions.Rebel){
				switch (spawnLevel) {
					case 1: vehicleTemplates = rebelSiegeVehicleTemplates1;
					case 2: vehicleTemplates = rebelSiegeVehicleTemplates2;
					case 3: vehicleTemplates = rebelSiegeVehicleTemplates3;
				}
			}
			if (spawnerFaction==Factions.Imperial){
				switch (spawnLevel) {
					case 1: vehicleTemplates = imperialSiegeVehicleTemplates1;
					case 2: vehicleTemplates = imperialSiegeVehicleTemplates2;
					case 3: vehicleTemplates = imperialSiegeVehicleTemplates3;
				}
			}	
			String vehicleTemplate = vehicleTemplates[new Random().nextInt(vehicleTemplates.length)];	
			CreatureObject vehicle = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(vehicleTemplate, this.getPlanet().getName(), 0L, this.getPosition().x, this.getPosition().y, this.getPosition().z, this.getOrientation().w, this.getOrientation().x, this.getOrientation().y,this.getOrientation().z,-1);
			vehicle.setAttachment("IsSlowVehicle",true);
			NGECore.getInstance().invasionService.registerInvader(vehicle);
			totalSpawnedUnits++;
			vehicle.setAttachment("IsInvader","yes");
			
			NGECore.getInstance().aiService.setPatrol(vehicle, patrolRoute);	
			NGECore.getInstance().aiService.setPatrolLoop(vehicle,false);			
		}
	}
	
	public void setPatrolRoute(int route){
		switch(route){
			case PatrolRoute.Dearic1: patrolRoute = get_Dearic_Route_1();
									  break;
			case PatrolRoute.Dearic2: patrolRoute = get_Dearic_Route_2();
			  						  break;
			case PatrolRoute.Dearic3: patrolRoute = get_Dearic_Route_3();
			  						  break;
			case PatrolRoute.Dearic4: patrolRoute = get_Dearic_Route_4();
			                          break;
			case PatrolRoute.Dearic5: patrolRoute = get_Dearic_Route_5();
			  						  break;
			case PatrolRoute.Dearic6: patrolRoute = get_Dearic_Route_6();
			  						  break;
			case PatrolRoute.Dearic7: patrolRoute = get_Dearic_Route_7();
									  break;
			case PatrolRoute.Dearic8: patrolRoute = get_Dearic_Route_8();
            						  break;
			case PatrolRoute.Dearic9: patrolRoute = get_Dearic_Route_9();
									  break;
			case PatrolRoute.Dearic10: patrolRoute = get_Dearic_Route_10();
			  						   break;
			case PatrolRoute.Dearic11: patrolRoute = get_Dearic_Route_11();
									   break;
									   
			case PatrolRoute.Keren1: patrolRoute = get_Keren_Route_1();
			  						 break;
			case PatrolRoute.Keren2: patrolRoute = get_Keren_Route_2();
									 break;
			case PatrolRoute.Keren3: patrolRoute = get_Keren_Route_3();
				 					 break;
			case PatrolRoute.Keren4: patrolRoute = get_Keren_Route_4();
				 					 break;
			case PatrolRoute.Keren5: patrolRoute = get_Keren_Route_5();
				 					 break;
			case PatrolRoute.Keren6: patrolRoute = get_Keren_Route_6();
				 					 break;
			case PatrolRoute.Keren7: patrolRoute = get_Keren_Route_7();
			 						 break;
			 						 
			case PatrolRoute.Bestine1: patrolRoute = get_Bestine_Route_1();
				 					   break;
			case PatrolRoute.Bestine2: patrolRoute = get_Bestine_Route_2();
			   						   break;
			case PatrolRoute.Bestine3: patrolRoute = get_Bestine_Route_3();
			                           break;
			case PatrolRoute.Bestine4: patrolRoute = get_Bestine_Route_4();
			   						   break;
			case PatrolRoute.Bestine5: patrolRoute = get_Bestine_Route_5();
			   						   break;
			case PatrolRoute.Bestine6: patrolRoute = get_Bestine_Route_6();
            						   break;
			case PatrolRoute.Bestine7: patrolRoute = get_Bestine_Route_7();
			   						   break;
			case PatrolRoute.Bestine8: patrolRoute = get_Bestine_Route_8();
						               break;
		}
	}
	
	public Vector<Point3D> get_Dearic_Route_1(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(68.0F, 6.00F, -2959F));
		patrolpoints.add(new Point3D(151.93F,6.00F, -2961.66F));
		patrolpoints.add(new Point3D(170.18F, 6.00F, -2992.99F));
		patrolpoints.add(new Point3D(262.76F, 6.00F, -2999.76F));
		patrolpoints.add(new Point3D(275.87F, 6.00F, -3008.44F));
		patrolpoints.add(new Point3D(303.41F, 6.00F, -3005.19F));
		patrolpoints.add(new Point3D(343.31F, 6.00F, -2961.06F));
		patrolpoints.add(new Point3D(379.98F, 6.00F, -2963.31F));
		patrolpoints.add(new Point3D(404.70F, 6.00F, -2980.57F));
		patrolpoints.add(new Point3D(412.54F, 6.00F, -3039.39F));
		patrolpoints.add(new Point3D(423.06F, 6.00F, -3050.75F));
		patrolpoints.add(new Point3D(446.92F, 6.00F, -3046.32F));
		patrolpoints.add(new Point3D(470.48F, 6.00F, -3023.43F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_2(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(76.0F, 6.00F, -2893F));
		patrolpoints.add(new Point3D(153.0F, 6.00F, -2898F));
		patrolpoints.add(new Point3D(162.89F, 6.00F, -2836.9F));
		patrolpoints.add(new Point3D(291.44F, 6.00F, -2844.6F));
		patrolpoints.add(new Point3D(333.19F, 6.00F, -2878.0F));
		patrolpoints.add(new Point3D(337.00F, 6.00F, -2954.0F));
		patrolpoints.add(new Point3D(378.00F, 6.00F, -2959.0F));
		patrolpoints.add(new Point3D(407.00F, 6.00F, -2978.0F));
		patrolpoints.add(new Point3D(414.00F, 6.00F, -3041.0F));
		patrolpoints.add(new Point3D(448.00F, 6.00F, -3043.0F));
		patrolpoints.add(new Point3D(464.00F, 6.00F, -3017.0F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_3(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(199.0F, 6.00F, -2790F));
		patrolpoints.add(new Point3D(200.0F, 6.00F, -2838F));
		patrolpoints.add(new Point3D(294.0F, 6.00F, -2841F));
		patrolpoints.add(new Point3D(335.0F, 6.00F, -2882F));
		patrolpoints.add(new Point3D(341.0F, 6.00F, -2950F));
		patrolpoints.add(new Point3D(378.0F, 6.00F, -2958F));
		patrolpoints.add(new Point3D(405.0F, 6.00F, -2981F));
		patrolpoints.add(new Point3D(414.00F, 6.00F, -3041.0F));
		patrolpoints.add(new Point3D(448.00F, 6.00F, -3043.0F));
		patrolpoints.add(new Point3D(473.0F, 6.00F, -3024F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_4(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(332.0F, 6.00F, -2715F));
		patrolpoints.add(new Point3D(324.0F, 6.00F, -2817F));
		patrolpoints.add(new Point3D(302.0F, 6.00F, -2835F));
		patrolpoints.add(new Point3D(338.0F, 6.00F, -2888F));
		patrolpoints.add(new Point3D(342.0F, 6.00F, -2949F));
		patrolpoints.add(new Point3D(407.0F, 6.00F, -2972F));
		patrolpoints.add(new Point3D(415.0F, 6.00F, -3055F));
		patrolpoints.add(new Point3D(451.0F, 6.00F, -3044F));
		patrolpoints.add(new Point3D(460.0F, 6.00F, -3011F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_5(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(384.0F, 6.00F, -2720F));
		patrolpoints.add(new Point3D(404.0F, 6.00F, -2817F));
		patrolpoints.add(new Point3D(417.0F, 6.00F, -2827F));
		patrolpoints.add(new Point3D(414.0F, 6.00F, -2854F));
		patrolpoints.add(new Point3D(384.0F, 6.00F, -2888F));
		patrolpoints.add(new Point3D(384.0F, 6.00F, -2959F));
		patrolpoints.add(new Point3D(407.0F, 6.00F, -2975F));
		patrolpoints.add(new Point3D(416.0F, 6.00F, -3049F));
		patrolpoints.add(new Point3D(450.0F, 6.00F, -3043F));
		patrolpoints.add(new Point3D(474.0F, 6.00F, -3025F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_6(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(590.0F, 6.00F, -2825F));
		patrolpoints.add(new Point3D(567.0F, 6.00F, -2860F));
		patrolpoints.add(new Point3D(563.0F, 6.00F, -2943F));
		patrolpoints.add(new Point3D(563.0F, 6.00F, -2943F));
		patrolpoints.add(new Point3D(507.0F, 6.00F, -2955F));
		patrolpoints.add(new Point3D(500.0F, 6.00F, -3011F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_7(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(723.0F, 6.00F, -2995F));
		patrolpoints.add(new Point3D(720.0F, 6.00F, -2964F));
		patrolpoints.add(new Point3D(623.0F, 6.00F, -2961F));
		patrolpoints.add(new Point3D(614.0F, 6.00F, -3010F));
		patrolpoints.add(new Point3D(510.0F, 6.00F, -3032F));
		patrolpoints.add(new Point3D(484.0F, 6.00F, -3021F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_8(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(733.0F, 6.00F, -3004F));
		patrolpoints.add(new Point3D(701.0F, 6.00F, -3004F));
		patrolpoints.add(new Point3D(699.0F, 6.00F, -3039F));
		patrolpoints.add(new Point3D(627.0F, 6.00F, -3041F));
		patrolpoints.add(new Point3D(605.0F, 6.00F, -3018F));
		patrolpoints.add(new Point3D(509.0F, 6.00F, -3033F));
		patrolpoints.add(new Point3D(495.0F, 6.00F, -3017F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_9(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(330.0F, 23.00F, -3402F));
		patrolpoints.add(new Point3D(360.0F, 6.00F, -3357F));
		patrolpoints.add(new Point3D(415.0F, 6.00F, -3273F));
		patrolpoints.add(new Point3D(464.0F, 6.00F, -3256F));
		patrolpoints.add(new Point3D(467.0F, 6.00F, -3236F));
		patrolpoints.add(new Point3D(498.0F, 6.00F, -3230F));
		patrolpoints.add(new Point3D(501.0F, 6.00F, -3205F));
		patrolpoints.add(new Point3D(481.0F, 6.00F, -3174F));
		patrolpoints.add(new Point3D(482.0F, 6.00F, -3126F));
		patrolpoints.add(new Point3D(437.0F, 6.00F, -3065F));
		patrolpoints.add(new Point3D(465.0F, 6.00F, -3019F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_10(){// just for tests
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(618.0F, 6.00F, -2972F));
		patrolpoints.add(new Point3D(617.0F, 6.00F, -2980F));
		patrolpoints.add(new Point3D(616.0F, 6.00F, -2990F));
		patrolpoints.add(new Point3D(615.0F, 6.00F, -3010F));
		patrolpoints.add(new Point3D(500.0F, 6.00F, -3035F));
		
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_11(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(332.0F, 6.00F, -2715F));
		return patrolpoints;
	}
	
	
	public Vector<Point3D> get_Keren_Route_1(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(1342.0F, 13.00F, 2917F));
		patrolpoints.add(new Point3D(1447.0F, 13.00F, 2825F));
		patrolpoints.add(new Point3D(1463.0F, 14.00F, 2781F));
		patrolpoints.add(new Point3D(1530.0F, 25.00F, 2760F));
		patrolpoints.add(new Point3D(1523.0F, 25.00F, 2731F));
		patrolpoints.add(new Point3D(1555.0F, 29.00F, 2721F));
		patrolpoints.add(new Point3D(1607.0F, 25.00F, 2706F));
		patrolpoints.add(new Point3D(1607.0F, 25.00F, 2706F));
		patrolpoints.add(new Point3D(1601.0F, 25.00F, 2676F));
		patrolpoints.add(new Point3D(1652.0F, 12.00F, 2652F));
		patrolpoints.add(new Point3D(1674.0F, 12.00F, 2637F));
		patrolpoints.add(new Point3D(1674.0F, 12.00F, 2586F));
		patrolpoints.add(new Point3D(1725.0F, 12.00F, 2584F));		
		patrolpoints.add(new Point3D(1727.0F, 12.00F, 2559F));		
		patrolpoints.add(new Point3D(1758.0F, 12.00F, 2555F));
		patrolpoints.add(new Point3D(1766.0F, 12.00F, 2528F));
		patrolpoints.add(new Point3D(1780.0F, 12.00F, 2520F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Keren_Route_2(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(1994.03F, 12.00F, 2769.94F));
		patrolpoints.add(new Point3D(1981.79F, 13.89F, 2736.23F));
		patrolpoints.add(new Point3D(1911.87F, 12F, 2737.51F));
		patrolpoints.add(new Point3D(1892.03F, 12F, 2718.64F));
		patrolpoints.add(new Point3D(1883.43F, 12F, 2727.32F));
		patrolpoints.add(new Point3D(1863.00F, 12F, 2707.36F));
		patrolpoints.add(new Point3D(1883.67F, 12F, 2694.01F));
		patrolpoints.add(new Point3D(1899.48F, 12F, 2661.86F));
		patrolpoints.add(new Point3D(1901.68F, 12F, 2638.95F));
		patrolpoints.add(new Point3D(1897.24F, 12F, 2603.49F));
		patrolpoints.add(new Point3D(1879.39F, 12F, 2563.75F));
		patrolpoints.add(new Point3D(1841.89F, 12F, 2527.75F));
		patrolpoints.add(new Point3D(1824.33F, 12F, 2521.45F));
		patrolpoints.add(new Point3D(1789.56F, 12F, 2518.26F));

		return patrolpoints;
	}

	public Vector<Point3D> get_Keren_Route_3(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(2063.0F, 49.00F, 2794F));
		patrolpoints.add(new Point3D(2020.0F, 49.00F, 2796F));
		patrolpoints.add(new Point3D(1817.0F, 40.00F, 2808F));
		patrolpoints.add(new Point3D(1753.0F, 40.00F, 2808F));
		patrolpoints.add(new Point3D(1715.0F, 40.00F, 2780F));
		patrolpoints.add(new Point3D(1717.0F, 12.00F, 2741F));
		patrolpoints.add(new Point3D(1713.0F, 12.00F, 2639F));
		patrolpoints.add(new Point3D(1695.0F, 12.00F, 2635F));
		patrolpoints.add(new Point3D(1696.0F, 12.00F, 2589F));
		patrolpoints.add(new Point3D(1725.0F, 12.00F, 2585F));
		patrolpoints.add(new Point3D(1727.0F, 12.00F, 2560F));
		patrolpoints.add(new Point3D(1758.0F, 12.00F, 2555F));
		patrolpoints.add(new Point3D(1766.0F, 12.00F, 2527F));
		patrolpoints.add(new Point3D(1781.0F, 12.00F, 2516F));

		return patrolpoints;
	}
	
	public Vector<Point3D> get_Keren_Route_4(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(1990.0F, 30.00F, 2715F));
		patrolpoints.add(new Point3D(1990.0F, 30.00F, 2682F));
		patrolpoints.add(new Point3D(1988.0F, 12.00F, 2624F));
		patrolpoints.add(new Point3D(1983.0F, 12.00F, 2560F));
		patrolpoints.add(new Point3D(1931.0F, 12.00F, 2535F));
		patrolpoints.add(new Point3D(1902.0F, 12.00F, 2554F));
		patrolpoints.add(new Point3D(1879.0F, 12.00F, 2525F));
		patrolpoints.add(new Point3D(1846.0F, 12.00F, 2504F));
		patrolpoints.add(new Point3D(1806.0F, 12.00F, 2492F));
		patrolpoints.add(new Point3D(1806.0F, 12.00F, 2520F));
		patrolpoints.add(new Point3D(1794.0F, 12.00F, 2519F));

		return patrolpoints;
	}

	public Vector<Point3D> get_Keren_Route_5(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(1342.0F, 13.00F, 2917F));
		patrolpoints.add(new Point3D(1447.0F, 13.00F, 2825F));
		patrolpoints.add(new Point3D(1463.0F, 14.00F, 2781F));
		patrolpoints.add(new Point3D(1530.0F, 25.00F, 2760F));
		patrolpoints.add(new Point3D(1523.0F, 25.00F, 2731F));
		patrolpoints.add(new Point3D(1555.0F, 29.00F, 2721F));
		patrolpoints.add(new Point3D(1607.0F, 25.00F, 2706F));
		patrolpoints.add(new Point3D(1607.0F, 25.00F, 2706F));
		patrolpoints.add(new Point3D(1601.0F, 25.00F, 2676F));
		patrolpoints.add(new Point3D(1652.0F, 12.00F, 2652F));
		patrolpoints.add(new Point3D(1674.0F, 12.00F, 2637F));
		patrolpoints.add(new Point3D(1674.0F, 12.00F, 2586F));
		patrolpoints.add(new Point3D(1725.0F, 12.00F, 2584F));
		patrolpoints.add(new Point3D(1758.0F, 12.00F, 2555F));
		patrolpoints.add(new Point3D(1766.0F, 12.00F, 2528F));
		patrolpoints.add(new Point3D(1780.0F, 12.00F, 2520F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Keren_Route_6(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(1980.0F, 10.00F, 2179F));
		patrolpoints.add(new Point3D(1957.0F, 11.00F, 2259F));
		patrolpoints.add(new Point3D(1934.0F, 12.00F, 2341F));
		patrolpoints.add(new Point3D(1947.0F, 12.00F, 2387F));
		patrolpoints.add(new Point3D(1945.0F, 12.00F, 2414F));
		patrolpoints.add(new Point3D(1879.0F, 12.00F, 2419F));
		patrolpoints.add(new Point3D(1847.0F, 12.00F, 2458F));
		patrolpoints.add(new Point3D(1789.0F, 12.00F, 2459F));
		patrolpoints.add(new Point3D(1743.0F, 12.00F, 2458F));
		patrolpoints.add(new Point3D(1728.0F, 12.00F, 2468F));
		patrolpoints.add(new Point3D(1729.0F, 12.00F, 2495F));
		patrolpoints.add(new Point3D(1761.0F, 12.00F, 2495F));
		patrolpoints.add(new Point3D(1764.0F, 12.00F, 2516F));
		patrolpoints.add(new Point3D(1776.0F, 12.00F, 2517F));
		return patrolpoints;
	}

	public Vector<Point3D> get_Keren_Route_7(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(1342.0F, 13.00F, 2917F));
		patrolpoints.add(new Point3D(1487.0F, 20.00F, 2808F));
		
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Bestine_Route_1(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(-978.75F, 12.00F, -3747.46F));
		patrolpoints.add(new Point3D(-996.37F, 12.00F, -3731.11F));
		patrolpoints.add(new Point3D(-1004.13F, 12.00F, -3708.60F));
		patrolpoints.add(new Point3D(-1042.40F, 12.00F, -3672.19F));
		patrolpoints.add(new Point3D(-1067.11F, 12.00F, -3665.97F));
		patrolpoints.add(new Point3D(-1107.36F, 12.00F, -3633.51F));
		patrolpoints.add(new Point3D(-1126.32F, 12.00F, -3650.40F));
		patrolpoints.add(new Point3D(-1144.03F, 12.00F, -3652.36F));
		patrolpoints.add(new Point3D(-1175.46F, 12.00F, -3631.40F));
		patrolpoints.add(new Point3D(-1204.07F, 12.00F, -3627.40F));	
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Bestine_Route_2(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(-1002.90F, 19.15F, -3593.77F));
		patrolpoints.add(new Point3D(-1053.93F, 12F, -3576.39F));
		patrolpoints.add(new Point3D(-1064.00F, 12F, -3586.90F));
		patrolpoints.add(new Point3D(-1084.30F, 12F, -3583.85F));
		patrolpoints.add(new Point3D(-1100.69F, 12F, -3622.65F));
		patrolpoints.add(new Point3D(-1129.25F, 12F, -3653.48F));
		patrolpoints.add(new Point3D(-1158.12F, 12F, -3654.10F));
		patrolpoints.add(new Point3D(-1191.37F, 12F, -3628.60F));
		patrolpoints.add(new Point3D(-1214.79F, 12F, -3633.39F));	
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Bestine_Route_3(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(-1104F, 20.00F, -3467F));
		patrolpoints.add(new Point3D(-1084.21F, 12.00F, -3513.44F));
		patrolpoints.add(new Point3D(-1107.65F, 12.00F, -3570.01F));
		patrolpoints.add(new Point3D(-1166.30F, 12.00F, -3527.04F));
		patrolpoints.add(new Point3D(-1182.49F, 12.00F, -3543.62F));
		patrolpoints.add(new Point3D(-1195.03F, 12.00F, -3534.84F));
		patrolpoints.add(new Point3D(-1222.08F, 12.00F, -3567.95F));
		patrolpoints.add(new Point3D(-1245.74F, 12.00F, -3550.56F));
		patrolpoints.add(new Point3D(-1284.93F, 12.00F, -3596.65F));
		patrolpoints.add(new Point3D(-1255.27F, 12.00F, -3620.79F));
		patrolpoints.add(new Point3D(-1223.85F, 12.00F, -3625.50F));	
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Bestine_Route_4(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(-1224.17F, 14.92F, -3477.66F));
		patrolpoints.add(new Point3D(-1214.17F, 12F, -3520.83F));
		patrolpoints.add(new Point3D(-1239.38F, 12F, -3552.16F));
		patrolpoints.add(new Point3D(-1251.67F, 12F, -3554.41F));
		patrolpoints.add(new Point3D(-1288.84F, 12F, -3605.51F));
		patrolpoints.add(new Point3D(-1253.18F, 12F, -3620.50F));
		patrolpoints.add(new Point3D(-1224.78F, 12F, -3627.92F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Bestine_Route_5(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(-1289.09F, 10.77F, -3451.00F));
		patrolpoints.add(new Point3D(-1287.35F, 12F, -3499.91F));
		patrolpoints.add(new Point3D(-1293.84F, 12F, -3516.75F));
		patrolpoints.add(new Point3D(-1293.84F, 12F, -3516.75F));
		patrolpoints.add(new Point3D(-1282.01F, 12F, -3531.76F));
		patrolpoints.add(new Point3D(-1286.69F, 12F, -3604.70F));
		patrolpoints.add(new Point3D(-1247.24F, 12F, -3622.33F));
		patrolpoints.add(new Point3D(-1221.27F, 12F, -3616.53F));
		return patrolpoints;
	}

	public Vector<Point3D> get_Bestine_Route_6(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(-1399.33F, 18.02F, -3842.45F));
		patrolpoints.add(new Point3D(-1378.02F, 10.02F, -3803.54F));
		patrolpoints.add(new Point3D(-1355.98F, 12.25F, -3741.07F));
		patrolpoints.add(new Point3D(-1351.22F, 12.0F, -3703.81F));
		patrolpoints.add(new Point3D(-1301.95F, 12.0F, -3626.74F));
		patrolpoints.add(new Point3D(-1261.14F, 12.0F, -3624.71F));
		patrolpoints.add(new Point3D(-1224.89F, 12.0F, -3631.51F));
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Bestine_Route_7(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(-1319.14F, 21.54F, -3854.52F));
		patrolpoints.add(new Point3D(-1327.96F, 24.39F, -3791.74F));
		patrolpoints.add(new Point3D(-1325.62F, 12.0F, -3694.68F));
		patrolpoints.add(new Point3D(-1325.62F, 12.0F, -3694.68F));
		patrolpoints.add(new Point3D(-1313.86F, 12.0F, -3632.90F));
		patrolpoints.add(new Point3D(-1261.17F, 12.0F, -3623.71F));
		patrolpoints.add(new Point3D(-1225.88F, 12.0F, -3631.15F));		
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Bestine_Route_8(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();		
		patrolpoints.add(new Point3D(-1143.05F, 14.43F, -3756.47F));
		patrolpoints.add(new Point3D(-1126.50F, 12F, -3691.73F));
		patrolpoints.add(new Point3D(-1188.91F, 12F, -3627.55F));
		patrolpoints.add(new Point3D(-1203.55F, 12F, -3624.19F));		
		return patrolpoints;
	}
	
	
	public int getTotalSpawnedUnits() {
		return totalSpawnedUnits;
	}

	public void setTotalSpawnedUnits(int totalSpawnedUnits) {
		this.totalSpawnedUnits = totalSpawnedUnits;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public class SpawnerType {
		
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
	
	public class PatrolRoute {
		
		public static final int Dearic1 = 0;
		public static final int Dearic2 = 1;
		public static final int Dearic3 = 2;
		public static final int Dearic4 = 3;
		public static final int Dearic5 = 4;
		public static final int Dearic6 = 5;
		public static final int Dearic7 = 6;
		public static final int Dearic8 = 7;
		public static final int Dearic9 = 8;
		public static final int Dearic10 = 9;
		public static final int Dearic11 = 10;
		
		public static final int Keren1 = 11;
		public static final int Keren2 = 12;
		public static final int Keren3 = 13;
		public static final int Keren4 = 14;
		public static final int Keren5 = 15;
		public static final int Keren6 = 16;
		public static final int Keren7 = 17;
		
		public static final int Bestine1 = 18;
		public static final int Bestine2 = 19;
		public static final int Bestine3 = 20;
		public static final int Bestine4 = 21;
		public static final int Bestine5 = 22;
		public static final int Bestine6 = 23;
		public static final int Bestine7 = 24;
		public static final int Bestine8 = 25;
		public static final int Bestine9 = 26;		
	}
}
