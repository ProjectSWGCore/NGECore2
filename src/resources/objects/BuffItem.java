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

import com.sleepycat.persist.model.Persistent;

@Persistent
public class BuffItem {
	private int affectAmount;
	private int entertainerBonus;
	private int invested;
	private String skillName;

	public BuffItem() {

	}

	public BuffItem(String skillName, int invested, int entBonus) {
		this.skillName = skillName;
		this.invested = invested;
		this.entertainerBonus = entBonus;
	}

	public int getAffectAmount() {
		return affectAmount;
	}

	public int getEntertainerBonus() {
		return entertainerBonus;
	}

	public int getInvested() {
		return invested;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setAffectAmount(int amount) {
		this.affectAmount = amount;
	}

	public void setEntertainerBonus(int entertainerBonus) {
		this.entertainerBonus = entertainerBonus;
	}

	public void setInvested(int invested) {
		this.invested = invested;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
}
