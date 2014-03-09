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
package resources.objects.weapon;

import resources.objects.tangible.TangibleObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent(version=0)
public class WeaponObject extends TangibleObject {
	
	// TODO: Thread safety
	
	@NotPersistent
	private WeaponMessageBuilder messageBuilder;
	
	private float attackSpeed = 1;
	private float maxRange;
	
	public WeaponObject(long objectID, Planet planet, String template) {
		super(objectID, planet, template, new Point3D(0, 0, 0), new Quaternion(1, 0, 1, 0));
		messageBuilder = new WeaponMessageBuilder(this);
		if (this.getClass().getSimpleName().equals("WeaponObject")) setIntAttribute("volume", 1);
		calculateRange();
		calculateAttackSpeed();
	}

	public WeaponObject(long objectID, Planet planet, String template, Point3D position, Quaternion orientation) {
		super(objectID, planet, template, position, orientation);
		messageBuilder = new WeaponMessageBuilder(this);
		if (this.getClass().getSimpleName().equals("WeaponObject")) setIntAttribute("volume", 1);
		calculateRange();
		calculateAttackSpeed();
	}

	
	public WeaponObject() {
		super();
		messageBuilder = new WeaponMessageBuilder(this);
		//calculateRange();
	}
	
	private void calculateRange() {

		int weaponType = getWeaponType();
		
		switch(weaponType) {
			
			case 0: maxRange = 64; break;
			case 1: maxRange = 50; break;
			case 2: maxRange = 35; break;
			case 12: maxRange = 64; break;
			case 4: maxRange = 5; break;
			case 5: maxRange = 5; break;
			case 6: maxRange = 5; break;
			case 7: maxRange = 5; break;
			case 8: maxRange = 64; break;
			case 9: maxRange = 5; break;
			case 10: maxRange = 5; break;
			case 11: maxRange = 5; break;
		
		}
		
	}

	public int getIncapTimer() {
		return incapTimer;
	}

	public void setIncapTimer(int incapTimer) {
		this.incapTimer = incapTimer;
	}


	public byte[] getCustomization() {
		return customization;
	}

	public void setCustomization(byte[] customization) {
		this.customization = customization;
	}

	public int getOptionsBitmask() {
		return optionsBitmask;
	}

	public void setOptionsBitmask(int optionsBitmask) {
		this.optionsBitmask = optionsBitmask;
	}

	public int getMaxDamage() {
		return Integer.parseInt(getStringAttribute("cat_wpn_damage.damage").split("-")[1]);
	}

	public void setMaxDamage(int maxDamage) {
		setStringAttribute("cat_wpn_damage.damage", String.valueOf(getMinDamage()) + "-" + String.valueOf(maxDamage));
	}
	
	public int getMinDamage() {
		return Integer.parseInt(getStringAttribute("cat_wpn_damage.damage").split("-")[0]);
	}

	public void setMinDamage(int minDamage) {
		setStringAttribute("cat_wpn_damage.damage", String.valueOf(minDamage) + "-" + String.valueOf(getMaxDamage()));
	}
	
	public int getElementalDamage() {
		return getIntAttribute("cat_wpn_damage.wpn_elemental_value");
	}

	public void setElementalDamage(int elementalDamage) {
		setIntAttribute("cat_wpn_damage.wpn_elemental_value", elementalDamage);
	}

	public String getElementalType() {
		return getStringAttribute("cat_wpn_damage.wpn_elemental_type");
	}
	
	public void setElementalType(String elementalType) {
		setStringAttribute("cat_wpn_damage.wpn_elemental_type", elementalType);
	}
	
	public String getDamageType() {
		return getStringAttribute("cat_wpn_damage.wpn_damage_type");
	}
	
	public void setDamageType(String damageType) {
		setStringAttribute("cat_wpn_damage.wpn_damage_type", damageType);
	}
	
	public int getDamagePerSecond() {
		if (getElementalType() != null)
				return (int) (((getMaxDamage() + getElementalDamage()  + getMinDamage() + getElementalDamage()) / 2 + getElementalDamage()) * (1 / getAttackSpeed()));
		else
			return (int) (((getMaxDamage() + getMinDamage()) / 2 ) * (1 / getAttackSpeed()));
	}

	public int getWeaponType() {
		
		int weaponType = -1;
		
		String template = getTemplate();
		
		if(template == null)
			return weaponType;
		
		if(template.contains("rifle")) weaponType = 0;
		if(template.contains("carbine")) weaponType = 1;
		if(template.contains("pistol")) weaponType = 2;
		if(template.contains("heavy")) weaponType = 12;
		if(template.contains("sword") || template.contains("baton")) weaponType = 4;
		if(template.contains("2h_sword") || template.contains("axe")) weaponType = 5;
		if(template.contains("unarmed")) weaponType = 6;
		if(template.contains("polearm") || template.contains("lance")) weaponType = 7;
		if(template.contains("thrown")) weaponType = 8;
		if(template.contains("lightsaber_one_handed")) weaponType = 9;
		if(template.contains("lightsaber_two_handed")) weaponType = 10;
		if(template.contains("lightsaber_polearm")) weaponType = 11;

		if(weaponType == -1)
			weaponType = 6;
		
		return weaponType;

	}

	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null)
			return;
		
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
		destination.getSession().write(messageBuilder.buildBaseline8());
		destination.getSession().write(messageBuilder.buildBaseline9());

		
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
	public WeaponMessageBuilder getMessageBuilder() {
		return messageBuilder;
	}

	public float getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(float maxRange) {
		this.maxRange = maxRange;
	}

	public boolean isMelee() {
		
		int weaponType = getWeaponType();
		
		if(weaponType == 4 || weaponType == 5 || weaponType == 6 || weaponType == 7 || weaponType == 9 || weaponType == 10 || weaponType == 11)
			return true;
		
		return false;
		
	}
	
	public boolean isRanged() {
		
		int weaponType = getWeaponType();
		
		if(weaponType == 0 || weaponType == 1 || weaponType == 2 || weaponType == 3)
			return true;
		
		return false;
		
	}
	
	private void calculateAttackSpeed() {
		
		int weaponType = getWeaponType();
		
		switch(weaponType) {
			
			case 0: attackSpeed = 0.8f; break;
			case 1: attackSpeed = 0.6f; break;
			case 2: attackSpeed = 0.4f; break;
			case 12: attackSpeed = 1; break;
			case 4: attackSpeed = 1; break;
			case 5: attackSpeed = 1; break;
			case 6: attackSpeed = 1; break;
			case 7: attackSpeed = 1; break;
			case 8: attackSpeed = 1; break;
			case 9: attackSpeed = 1; break;
			case 10: attackSpeed = 1; break;
			case 11: attackSpeed = 1; break;
		
		}
		
	}


}
