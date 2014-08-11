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

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.StringUtilities;
import engine.resources.common.UString;
import engine.resources.objects.Baseline;
import engine.resources.objects.IDelta;
import engine.resources.objects.SWGObject;
import resources.objects.intangible.IntangibleObject;
import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class WaypointObject extends IntangibleObject implements IDelta, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final byte BLUE = 1, GREEN = 2, ORANGE = 3, YELLOW = 4, PURPLE = 5, WHITE = 6, MULTICOLOR = 7;
	
	public WaypointObject(long objectID, Planet planet, Point3D position) { 
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(0, 0, 0, 1), "object/waypoint/shared_waypoint.iff");
	}
	
	public WaypointObject() {
		super();
	}
	
	public void initAfterDBLoad() {
		super.init();
	}
	
	public void init(SWGObject object) {
		
	}
	
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		return baseline;
	}
	
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("cellNumber", 0);
		baseline.put("position", new Point3D(0, 0, 0));
		baseline.put("targetId", (long) 0);
		baseline.put("planetCrc", 0);
		baseline.put("name", new UString("Waypoint"));
		baseline.put("color", (byte) 1);
		baseline.put("active", false);
		return baseline;
	}
	
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	public int getCellNumber() {
		return (int) getBaseline(3).get("cellNumber");
	}
	
	public void setCellNumber(int cellNumber) {
		getBaseline(3).set("cellNumber", cellNumber);
	}
	
	public void setPosition(Point3D position) {
		getBaseline(3).set("position", position);
	}
	
	public long getTargetId() {
		return (long) getBaseline(3).get("targetId");
	}
	
	public void setTargetId(long targetId) {
		getBaseline(3).set("targetId", targetId);
	}
	
	public int getPlanetCrc() {
		return (int) getBaseline(3).get("planetCrc");
	}
	
	public void setPlanetCrc(int planetCrc) {
		getBaseline(3).set("planetCrc", planetCrc);
	}
	
	public String getName() {
		return ((UString) getBaseline(3).get("name")).get();
	}
	
	public void setName(String name) {
		getBaseline(3).set("name", new UString(name));
	}
	
	public byte getColor() {
		return (byte) getBaseline(3).get("color");
	}
	
	public void setColor(byte color) {
		getBaseline(3).set("color", color);
	}
	
	public boolean isActive() {
		return (boolean) getBaseline(3).get("active");
	}
	
	public void setActive(boolean active) {
		getBaseline(3).set("active", active);
	}
	
	public void toggleActive() {
		setActive(!isActive());
	}
	
	@Override
	public void sendBaselines(Client destination) {
		
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = Baseline.createBuffer(38 + StringUtilities.getUnicodeString(getName()).length);
			buffer.putInt(getCellNumber());
			buffer.put(Baseline.toBytes(getBaseline(3).get("position")));
			buffer.putLong(getTargetId());
			buffer.putInt(getPlanetCrc());
			buffer.put(StringUtilities.getUnicodeString(getName()));
			buffer.putLong(getObjectID());
			buffer.put(getColor());
			buffer.put(Baseline.getBoolean(isActive()));
			return buffer.flip().array();
		}
	}
	
	@Deprecated public int getCellId() { return getCellNumber(); }
	
	@Deprecated public void setCellId(int cellId) { setCellNumber(cellId); }
	
	@Deprecated public long getLocationNetworkId() { return getTargetId(); }
	
	@Deprecated public void setLocationNetworkId(long locationNetworkId) { setTargetId(locationNetworkId); }
	
	@Deprecated public int getPlanetCRC() { return getPlanetCrc(); }
	
	@Deprecated public void setPlanetCRC(int planetCRC) { setPlanetCrc(planetCRC); }
	
}
