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
package resources.z.exp.objects.waypoint;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.StringUtilities;
import resources.objects.IDelta;
import resources.z.exp.objects.intangible.IntangibleObject;
import resources.z.exp.objects.Baseline;

import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent(version=0)
public class WaypointObject extends IntangibleObject implements IDelta {
	
	public static final byte BLUE, GREEN, ORANGE, YELLOW, PURPLE, WHITE, MULTICOLOR;
	
	static {
		BLUE = 1;
		GREEN = 2;
		ORANGE = 3;
		YELLOW = 4;
		PURPLE = 5;
		WHITE = 6;
		MULTICOLOR = 7;
	};
	
	// WAYP 3
	private int cellId;
	//private Point3D position = new Point3D(0, 0, 0);
	private long targetId = 0;
	private int planetCrc = 0;
	private String name = "Waypoint";
	//private long waypointId = 0;
	private byte color = 1;
	private boolean isActive = false;
	
	public WaypointObject(long objectID, Planet planet, Point3D position) { 
		super(objectID, planet, position, new Quaternion(0, 0, 0, 1), "object/waypoint/shared_waypoint.iff");
	}
	
	public WaypointObject() {
		super();
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
	
	public long getTargetId() {
		synchronized(objectMutex) {
			return targetId;
		}
	}
	
	public void setTargetId(long targetId) {
		synchronized(objectMutex) {
			this.targetId = targetId;
		}
	}
	
	public int getPlanetCrc() {
		synchronized(objectMutex) {
			return planetCrc;
		}
	}
	
	public void setPlanetCrc(int planetCrc) {
		synchronized(objectMutex) {
			this.planetCrc = planetCrc;
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
	
	public void toggleActive() {
		setActive(!isActive());
	}
	
	@Override
	public void sendBaselines(Client destination) {
		
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer =  IoBuffer.allocate(42, false).order(ByteOrder.LITTLE_ENDIAN).setAutoExpand(true);
			buffer.putInt(cellId);
			buffer.putFloat(getPosition().x);
			buffer.putFloat(getPosition().y);
			buffer.putFloat(getPosition().z);
			buffer.putLong(targetId);
			buffer.putInt(planetCrc);
			buffer.put(StringUtilities.getUnicodeString(name));
			buffer.putLong(getObjectID());
			buffer.put(color);
			buffer.put(Baseline.getBoolean(isActive));
			buffer.flip();
			return buffer.array();
		}
	}
	
}
