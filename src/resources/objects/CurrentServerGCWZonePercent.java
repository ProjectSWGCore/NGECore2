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

public class CurrentServerGCWZonePercent {
	
	private byte unknown = 0;
	private String zone = "";
	private int percent = 50;
	
	public CurrentServerGCWZonePercent(byte unknown, String zone, int percent) {
		this.unknown = unknown;
		this.zone = zone;
		this.percent = percent;
	}
	
	public CurrentServerGCWZonePercent(String zone) {
		this.zone = zone;
	}
	
	public byte getUnknown() {
		return unknown;
	}
	
	public void setUnknown(byte unknown) {
		this.unknown = unknown;
	}
	
	public String getZone() {
		return zone;
	}
	
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public int getPercent() {
		return percent;
	}
	
	public void setPercent(int percent) {
		this.percent = percent;
	}
	
}
