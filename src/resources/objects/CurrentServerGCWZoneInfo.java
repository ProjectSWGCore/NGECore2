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
package resources.objects;

public class CurrentServerGCWZoneInfo {
	
	private byte unknown1 = 0;
	private String zone = "";
	private int unknown2 = 0x0D05EA4E;
	private int percent = 50;
	
	public CurrentServerGCWZoneInfo(byte unknown1, String zone, int unknown2, int percent) {
		this.unknown1 = unknown1;
		this.zone = zone;
		this.unknown2 = unknown2;
		this.percent = percent;
	}
	
	public CurrentServerGCWZoneInfo(String zone) {
		this.zone = zone;
	}
	
	public byte getUnknown1() {
		return unknown1;
	}
	
	public void setUnknown1(byte unknown1) {
		this.unknown1 = unknown1;
	}
	
	public String getZone() {
		return zone;
	}
	
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public int getUnknown2() {
		return unknown2;
	}
	
	public void setUnknown2(int unknown2) {
		this.unknown2 = unknown2;
	}
	
	public int getPercent() {
		return percent;
	}
	
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
}
