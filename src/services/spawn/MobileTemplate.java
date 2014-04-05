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

import java.util.Vector;

import main.NGECore;
import resources.datatables.Options;
import resources.datatables.PvpStatus;
import resources.objects.weapon.WeaponObject;

public class MobileTemplate {
	
	private Vector<String> templates;
	private int optionBitmask = Options.ATTACKABLE;
	private int pvpBitmask = PvpStatus.Attackable;
	private short level;
	private Vector<String> attacks;
	private String defaultAttack;
	private int minDamage = 0;
	private int maxDamage = 0;
	private int difficulty = 0;
	private int health, action;
	private String creatureName;
	private float scale = 1;
	// this is a custom attack Range setting to use for large mobs like krayts
	private int attackRange;
	private Vector<String> weaponTemplates = new Vector<String>();

	public Vector<String> getTemplates() {
		return templates;
	}

	public void setTemplates(Vector<String> templates) {
		this.templates = templates;
	}

	public int getOptionBitmask() {
		return optionBitmask;
	}

	public void setOptionBitmask(int optionBitmask) {
		this.optionBitmask = optionBitmask;
	}

	public int getPvpBitmask() {
		return pvpBitmask;
	}

	public void setPvpBitmask(int pvpBitmask) {
		this.pvpBitmask = pvpBitmask;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public Vector<String> getAttacks() {
		return attacks;
	}

	public void setAttacks(Vector<String> attacks) {
		this.attacks = attacks;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getDefaultAttack() {
		return defaultAttack;
	}

	public void setDefaultAttack(String defaultAttack) {
		this.defaultAttack = defaultAttack;
	}

	public String getCreatureName() {
		return creatureName;
	}

	public void setCreatureName(String creatureName) {
		this.creatureName = creatureName;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}
	
	public Vector<String> getWeaponTemplates() {
		return weaponTemplates;
	}

	public void setWeaponTemplates(Vector<String> weaponTemplates) {
		this.weaponTemplates = weaponTemplates;
	}
}
