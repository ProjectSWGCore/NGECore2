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
package services.mission;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.CRC;
import engine.resources.objects.Delta;
import engine.resources.scene.Point3D;

public class MissionLocation extends Delta {

	private static final long serialVersionUID = 1L;

	private Point3D location;
	private long objId;
	private String planet;
	
	public MissionLocation() { }
	
	public MissionLocation(Point3D location, long objId, String planet) {
		this.location = location;
		this.objId = objId;
		this.planet = planet;
	}
	
	public Point3D getLocation() {
		return location;
	}

	public void setLocation(Point3D location) {
		this.location = location;
	}

	public long getObjectId() {
		return objId;
	}

	public void setObjectId(long objId) {
		this.objId = objId;
	}

	public String getPlanet() {
		return planet;
	}

	public void setPlanet(String planet) {
		this.planet = planet;
	}

	public int getPlanetCRC() {
		if (planet != null)
			return CRC.StringtoCRC(planet);
		else
			return 0;
	}
	
	@Override
	public byte[] getBytes() {
		IoBuffer buffer = createBuffer(24);
		
		buffer.putFloat(location.x);
		buffer.putFloat(location.y);
		buffer.putFloat(location.z);
		buffer.putLong(objId);
		buffer.putInt(getPlanetCRC());
		return buffer.flip().array();
	}
}
