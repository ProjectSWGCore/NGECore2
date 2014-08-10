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

@SuppressWarnings("unused")
public class VehiclePatrolPylon extends TangibleObject{
	
	private static final long serialVersionUID = 1L;
	private int pylonFaction = 0; 
	private int spawnLevel   = 0;  
	private int totalSpawnedVehicles = 0;
	private String[] vehicleTemplates   = new String[]{""};
	private String[] rebelTemplates1    = new String[]{""};
	private String[] rebelTemplates2    = new String[]{""};
	private String[] rebelTemplates3    = new String[]{""};
	private String[] imperialTemplates1 = new String[]{""};
	private String[] imperialTemplates2 = new String[]{""};
	private String[] imperialTemplates3 = new String[]{"elite_imperial_stormtrooper_90"};
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public VehiclePatrolPylon() {
		super();
	}
	
	public VehiclePatrolPylon(long objectID, Planet planet, Point3D position, Quaternion orientation, String template){
		super(objectID, planet, position, orientation, template);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override public void run() { 
				try {
					spawnVehicle();
				} catch (Exception e) {
					System.err.println("Exception in VehiclePatrolPylon->scheduleAtFixedRate->spawnVehicle() " + e.getMessage());
				}
			}
		}, 5, 20, TimeUnit.SECONDS);
		
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
	
	public void spawnVehicle(){
		
		if (NGECore.getInstance().invasionService.getInvasionPhase()==2){
			if (pylonFaction==Factions.Rebel){
				switch (spawnLevel) {
					case 1: vehicleTemplates = rebelTemplates1;
					case 2: vehicleTemplates = rebelTemplates2;
					case 3: vehicleTemplates = rebelTemplates3;
				}
			}
			if (pylonFaction==Factions.Imperial){
				switch (spawnLevel) {
					case 1: vehicleTemplates = imperialTemplates1;
					case 2: vehicleTemplates = imperialTemplates2;
					case 3: vehicleTemplates = imperialTemplates3;
				}
			}	
			String vehicleTemplate = vehicleTemplates[new Random().nextInt(vehicleTemplates.length)];	
			CreatureObject vehicle = (CreatureObject)NGECore.getInstance().spawnService.spawnCreature(vehicleTemplate, this.getPlanet().getName(), 0L, this.getPosition().x, this.getPosition().y, this.getPosition().z, this.getOrientation().w, this.getOrientation().x, this.getOrientation().y,this.getOrientation().z,-1);
				
			NGECore.getInstance().aiService.setPatrol(vehicle, get_Dearic_Route_1());	
			NGECore.getInstance().aiService.setPatrolLoop(vehicle,false);
			totalSpawnedVehicles++;
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
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_3(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_4(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_5(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_6(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_7(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_8(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();
		return patrolpoints;
	}
	
	public Vector<Point3D> get_Dearic_Route_9(){
		Vector<Point3D> patrolpoints = new Vector<Point3D>();
		return patrolpoints;
	}

	
	
}
