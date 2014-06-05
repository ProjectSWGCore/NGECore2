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
package services.spawn;

public class LairSpawnTemplate {
	
	private int maxSpawnLimit;
	private String lairTemplate;
	private int minLevel;
	private int maxLevel;
	
	public LairSpawnTemplate(int maxSpawnLimit, String lairTemplate, int minLevel, int maxLevel) {
		this.maxSpawnLimit = maxSpawnLimit;
		this.lairTemplate = lairTemplate;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
	}

	public int getMaxSpawnLimit() {
		return maxSpawnLimit;
	}

	public void setMaxSpawnLimit(int maxSpawnLimit) {
		this.maxSpawnLimit = maxSpawnLimit;
	}

	public String getLairTemplate() {
		return lairTemplate;
	}

	public void setLairTemplate(String lairTemplate) {
		this.lairTemplate = lairTemplate;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	

}
