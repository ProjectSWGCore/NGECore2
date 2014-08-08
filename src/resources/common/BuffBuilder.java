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

public class BuffBuilder {
	private int affectAmount;
	private String category;
	private int cost;
	private int maxTimesApplied;
	private String requiredExpertise;
	private String statAffects;
	private String statName;

	
	public enum BuffCategory {ATTRIBUTES,RESISTANCES,TRADE,COMBAT,MISC};
	private BuffCategory catId;
	
	
	public BuffBuilder() {

	}

	public int getAffectAmount() {
		return affectAmount;
	}

	public String getCategory() {
		return category;
	}

	public BuffCategory getCategoryId() {
		return catId;
	}
	
	public int getCost() {
		return this.cost;
	}

	public int getMaxTimesApplied() {
		return maxTimesApplied;
	}

	public String getRequiredExpertise() {
		return requiredExpertise;
	}

	public String getStatAffects() {
		return statAffects;
	}

	public String getStatName() {
		return statName;
	}

	public void setAffectAmount(int affectAmount) {
		this.affectAmount = affectAmount;
	}

	public void setCategory(String category)
	{
		this.catId=BuffCategory.MISC;
		if(category.equals("attributes"))
		{
			this.catId=BuffCategory.ATTRIBUTES;
		}
		else if(category.equals("trade"))
		{
			this.catId=BuffCategory.TRADE;
		}
		else if(category.equals("resistances"))
		{
			this.catId=BuffCategory.RESISTANCES;
		}
		else if(category.equals("combat"))
		{
			this.catId=BuffCategory.COMBAT;
		}

		this.category = category;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setMaxTimesApplied(int maxTimesApplied) {
		this.maxTimesApplied = maxTimesApplied;
	}

	public void setRequiredExpertise(String requiredExpertise) {
		this.requiredExpertise = requiredExpertise;
	}

	public void setStatAffects(String statAffects) {
		this.statAffects = statAffects;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}
}
