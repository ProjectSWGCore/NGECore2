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

public class GCWPylon extends TangibleObject{
	
	private static final long serialVersionUID = 1L;
	private int pylonType = 0;
	private int pylonFaction = 0; 
	private int spawnLevel   = 0;  
	private int totalSpawnedUnits = 0;
	private String[] soldierTemplates   = new String[]{""};
	private String[] rebelSoldierTemplates1    = new String[]{""};
	private String[] rebelSoldierTemplates2    = new String[]{""};
	private String[] rebelSoldierTemplates3    = new String[]{""};
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
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public GCWPylon() {
		super();
	}
	
	public GCWPylon(long objectID, Planet planet, Point3D position, Quaternion orientation, String template){
		super(objectID, planet, position, orientation, template);	
	}
	
	public int getPylonType() {
		return pylonType;
	}

	public void setPylonType(int pylonType) {
		this.pylonType = pylonType;
		
		if (pylonType==PylonType.SoldierPatrol){
			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override public void run() { 
					try {
						spawnSoldier();	
					} catch (Exception e) {
						System.err.println("Exception in SoldierPatrolPylon->scheduleAtFixedRate->spawnSoldier() " + e.getMessage());
					}
				}
			}, 5, 30, TimeUnit.SECONDS);
		}
		
		if (pylonType==PylonType.VehiclePatrol){
			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override public void run() { 
					try {
						spawnVehicle();	
					} catch (Exception e) {
						System.err.println("Exception in SoldierPatrolPylon->scheduleAtFixedRate->spawnVehicle() " + e.getMessage());
					}
				}
			}, 5, 80, TimeUnit.SECONDS);
		}
		
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
	
	public void spawnSoldier(){
		
		if (NGECore.getInstance().invasionService.getInvasionPhase()==2){
			if (pylonFaction==Factions.Rebel){
				switch (spawnLevel) {
					case 1: soldierTemplates = rebelSoldierTemplates1;
					case 2: soldierTemplates = rebelSoldierTemplates2;
					case 3: soldierTemplates = rebelSoldierTemplates3;
				}
			}
			if (pylonFaction==Factions.Imperial){
				switch (spawnLevel) {
					case 1: soldierTemplates = imperialSoldierTemplates1;
					case 2: soldierTemplates = imperialSoldierTemplates2;
					case 3: soldierTemplates = imperialSoldierTemplates3;
				}
			}	
			String soldierTemplate = soldierTemplates[new Random().nextInt(soldierTemplates.length)];	
			CreatureObject soldier = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(soldierTemplate, this.getPlanet().getName(), 0L, this.getPosition().x, this.getPosition().y, this.getPosition().z, this.getOrientation().w, this.getOrientation().x, this.getOrientation().y,this.getOrientation().z,-1);
				
			NGECore.getInstance().aiService.setPatrol(soldier, patrolRoute);	
			NGECore.getInstance().aiService.setPatrolLoop(soldier,false);
			totalSpawnedUnits++;
		}
	}
	
	public void spawnVehicle(){
		
		if (NGECore.getInstance().invasionService.getInvasionPhase()==2){
			if (pylonFaction==Factions.Rebel){
				switch (spawnLevel) {
					case 1: vehicleTemplates = rebelVehicleTemplates1;
					case 2: vehicleTemplates = rebelVehicleTemplates2;
					case 3: vehicleTemplates = rebelVehicleTemplates3;
				}
			}
			if (pylonFaction==Factions.Imperial){
				switch (spawnLevel) {
					case 1: vehicleTemplates = imperialVehicleTemplates1;
					case 2: vehicleTemplates = imperialVehicleTemplates2;
					case 3: vehicleTemplates = imperialVehicleTemplates3;
				}
			}	
			String vehicleTemplate = vehicleTemplates[new Random().nextInt(vehicleTemplates.length)];	
			CreatureObject vehicle = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(vehicleTemplate, this.getPlanet().getName(), 0L, this.getPosition().x, this.getPosition().y, this.getPosition().z, this.getOrientation().w, this.getOrientation().x, this.getOrientation().y,this.getOrientation().z,-1);
				
			NGECore.getInstance().aiService.setPatrol(vehicle, get_Dearic_Route_1());	
			NGECore.getInstance().aiService.setPatrolLoop(vehicle,false);
			totalSpawnedUnits++;
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
		patrolpoints.add(new Point3D(463.00F, 6.00F, -3017.0F));
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
		patrolpoints.add(new Point3D(496.0F, 6.00F, -3018F));
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
		patrolpoints.add(new Point3D(620.0F, 6.00F, -3009F));
		patrolpoints.add(new Point3D(565.0F, 6.00F, -3025F));
		patrolpoints.add(new Point3D(535.0F, 6.00F, -3023F));
		
		return patrolpoints;
	}

	public class PylonType {
		
		public static final int SoldierPatrol = 0;
		public static final int VehiclePatrol = 1;
		public static final int SiegeVehiclePatrol = 2;
		public static final int Barricade = 3;
		public static final int Turret = 4;
		public static final int Tower = 5;
		
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
		
	}

	public int getTotalSpawnedUnits() {
		return totalSpawnedUnits;
	}

	public void setTotalSpawnedUnits(int totalSpawnedUnits) {
		this.totalSpawnedUnits = totalSpawnedUnits;
	}
}
