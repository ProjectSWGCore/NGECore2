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

import java.io.Serializable;
import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

import resources.objects.intangible.IntangibleObject;
import engine.clients.Client;
import engine.resources.common.StringUtilities;
import engine.resources.objects.IDelta;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class WaypointObject extends IntangibleObject implements Serializable, IDelta {
	
	private static final long serialVersionUID = 1L;
	
	private int cellId; // ???
	private long locationNetworkId;
	private int planetCRC;
	private String name = "";
	private byte color;
	private boolean isActive;
	private transient static SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();
	
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
	
	@Override
	public void initAfterDBLoad() {
		super.init();
	}
	
	public synchronized int getCellId() {
		return cellId;
	}


	public synchronized void setCellId(int cellId) {
		this.cellId = cellId;
	}


	public synchronized long getLocationNetworkId() {
		return locationNetworkId;
	}


	public synchronized void setLocationNetworkId(long locationNetworkId) {
		this.locationNetworkId = locationNetworkId;
	}


	public synchronized int getPlanetCRC() {
		return planetCRC;
	}


	public synchronized void setPlanetCRC(int planetCRC) {
		this.planetCRC = planetCRC;
	}


	public synchronized String getName() {
		return name;
	}


	public synchronized void setName(String name) {
		this.name = name;
	}


	public synchronized byte getColor() {
		return color;
	}


	public synchronized void setColor(byte color) {
		this.color = color;
	}


	public synchronized boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public void sendBaselines(Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getBytes() {
		IoBuffer buffer = bufferPool.allocate(42 + name.length() * 2, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(getCellId());

		buffer.putFloat(getPosition().x);
		buffer.putFloat(getPosition().y);
		buffer.putFloat(getPosition().z);

		buffer.putLong(0); // networklocationId
		buffer.putInt(getPlanetCRC());

		buffer.put(StringUtilities.getUnicodeString(getName()));
		buffer.putLong(getObjectID());

		buffer.put((byte) getColor());

		if (isActive()) 
			buffer.put((byte) 1);
		else 
			buffer.put((byte) 0);
		
		return buffer.flip().array();

	}

	@Override
	public void init(SWGObject arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
