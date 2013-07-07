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
package resources.objects.waypoint;



import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class WaypointObject extends SWGObject {
	
	private int cellId; // ???
	private long locationNetworkId;
	private int planetCRC;
	private String name;
	private byte color;
	private boolean isActive;
	
	public static final byte BLUE = 1;
	public static final byte GREEN = 2;
	public static final byte ORANGE = 3;
	public static final byte YELLOW = 4;
	public static final byte PURPLE = 5;
	public static final byte WHITE = 6;
	public static final byte MULTICOLOR = 7;	// JTL waypoint

	public WaypointObject() { }

	public WaypointObject(long objectID, Planet planet, Point3D position) { 
		super(objectID, planet, position, new Quaternion(0, 0, 0, 1), "object/waypoint/shared_waypoint.iff");
	}
	
	public int getCellId() {
		synchronized(objectMutex) {
			return cellId;
		}
	}


	public void setCellId(int cellId) {
		synchronized(objectMutex) {
			this.cellId = cellId;
		}
	}


	public long getLocationNetworkId() {
		synchronized(objectMutex) {
			return locationNetworkId;
		}
	}


	public void setLocationNetworkId(long locationNetworkId) {
		synchronized(objectMutex) {
			this.locationNetworkId = locationNetworkId;
		}
	}


	public int getPlanetCRC() {
		synchronized(objectMutex) {
			return planetCRC;
		}
	}


	public void setPlanetCRC(int planetCRC) {
		synchronized(objectMutex) {
			this.planetCRC = planetCRC;
		}
	}


	public String getName() {
		synchronized(objectMutex) {
			return name;
		}
	}


	public void setName(String name) {
		synchronized(objectMutex) {
			this.name = name;
		}
	}


	public byte getColor() {
		synchronized(objectMutex) {
			return color;
		}
	}


	public void setColor(byte color) {
		synchronized(objectMutex) {
			this.color = color;
		}
	}


	public boolean isActive() {
		synchronized(objectMutex) {
			return isActive;
		}
	}


	public void setActive(boolean isActive) {
		synchronized(objectMutex) {
			this.isActive = isActive;
		}
	}

	@Override
	public void sendBaselines(Client client) {
		// TODO Auto-generated method stub
		
	}
	
}
