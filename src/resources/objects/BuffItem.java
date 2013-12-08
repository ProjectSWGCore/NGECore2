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

public class BuffItem {
	private String skillName;
	private int invested;
	private int bonusAmount; // expertise bonus (50%*4 = 200) which would then result in bonusAmount + (maxTimesApplied * affectAmount) = result
	
	public BuffItem() {
		
	}
	
	public BuffItem(String skillName, int invested, int amount) {
		this.skillName = skillName;
		this.invested = invested;
		this.bonusAmount = amount;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public int getInvested() {
		return invested;
	}

	public void setInvested(int invested) {
		this.invested = invested;
	}

	public int getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(int amount) {
		this.bonusAmount = amount;
	}
}
