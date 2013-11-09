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
package services.travel;

import resources.common.SpawnPoint;
import resources.objects.creature.CreatureObject;
import engine.resources.scene.Point3D;

public class TravelPoint {
	
	private String planetName;
	private String name;
	private Point3D location;
	private SpawnPoint spawnLocation;
	private int ticketPrice;
	private CreatureObject shuttle;
	private boolean shuttleAvailable;
	private boolean shuttleLanding;
	
	public TravelPoint() {
	}
	
	public TravelPoint(String name, float x, float y, float z, int price) {
		this.name = name;
		this.location = new Point3D(x, y, z);
		this.spawnLocation = new SpawnPoint(this.location, 0, 1);
		this.shuttleAvailable = false;
		this.shuttleLanding = false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Point3D getLocation() {
		return location;
	}
	
	public void setLocation(float x, float y, float z) {
		this.location.x = x;
		this.location.y = y;
		this.location.z = z;
	}
	
	public int getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}
	
	public SpawnPoint getSpawnLocation() {
		return this.spawnLocation;
	}
	
	public void setSpawnLocation(SpawnPoint point) {
		this.spawnLocation = point;
	}
	
	public CreatureObject getShuttle() {
		return this.shuttle;
	}
	
	public void setShuttle(CreatureObject shuttleObj) {
		this.shuttle = shuttleObj;
	}

	public boolean isShuttleAvailable() {
		return shuttleAvailable;
	}

	public void setShuttleAvailable(boolean shuttleAvailable) {
		this.shuttleAvailable = shuttleAvailable;
	}

	public boolean isShuttleLanding() {
		return shuttleLanding;
	}

	public void setShuttleLanding(boolean shuttleLanding) {
		this.shuttleLanding = shuttleLanding;
	}
}
