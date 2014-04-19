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
package resources.common;

public class PerformanceEffect {

	private int effectActionCost;
	private float effectDuration;
	private String name;
	private String performanceType;
	private boolean requiredPerforming;
	private int requiredSkillModValue;
	private int targetType;
	private boolean music;
	private boolean dance;

	public PerformanceEffect() { }
	
	public int getEffectActionCost() {
		return effectActionCost;
	}
	public float getEffectDuration() {
		return effectDuration;
	}
	public String getName() {
		return name;
	}
	public int getRequiredSkillModValue() {
		return requiredSkillModValue;
	}
	public int getTargetType() {
		return targetType;
	}
	public String getPerformanceType() {
		return performanceType;
	}
	public boolean isRequiredPerforming() {
		return requiredPerforming;
	}
	public void setEffectActionCost(int effectActionCost) {
		this.effectActionCost = effectActionCost;
	}
	public void setEffectDuration(float effectDuration) {
		this.effectDuration = effectDuration;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPerformanceType(String performanceType) {
		this.performanceType = performanceType;
		
		if (performanceType.contains(",")) {
			String[] array = performanceType.split(",");
			if(array.length < 2)
				return;
			
			this.dance = true;
			this.music = true;
		}
		else if (performanceType.equals("music")) { this.music = true; }
		else if (performanceType.equals("dance")) { this.dance = true; }
	}
	public void setRequiredPerforming(boolean requiredPerforming) {
		this.requiredPerforming = requiredPerforming;
	}
	public void setRequiredSkillModValue(int requiredSkillModValue) {
		this.requiredSkillModValue = requiredSkillModValue;
	}
	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}
	
	public boolean isMusic() {
		return music;
	}

	public void setMusic(boolean music) {
		this.music = music;
	}

	public boolean isDance() {
		return dance;
	}

	public void setDance(boolean dance) {
		this.dance = dance;
	}

}
