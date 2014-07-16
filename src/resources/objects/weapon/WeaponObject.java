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

import java.io.Serializable;
import java.util.Vector;

import resources.datatables.WeaponType;
import resources.objects.tangible.TangibleObject;

import com.sleepycat.persist.model.NotPersistent;

import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class WeaponObject extends TangibleObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// TODO: Thread safety
	
	@NotPersistent
	private transient WeaponMessageBuilder messageBuilder;
	
	public WeaponObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 1, 0), template);
		messageBuilder = new WeaponMessageBuilder(this);
		if (this.getClass().getSimpleName().equals("WeaponObject")) setIntAttribute("volume", 1);
		setStringAttribute("cat_wpn_damage.damage", "0-0");
	}

	public WeaponObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String template) {
		super(objectID, planet, position, orientation, template);
		messageBuilder = new WeaponMessageBuilder(this);
		if (this.getClass().getSimpleName().equals("WeaponObject")) setIntAttribute("volume", 1);
		setStringAttribute("cat_wpn_damage.damage", "0-0");
	}
	
	public WeaponObject() {
		super();
		messageBuilder = new WeaponMessageBuilder(this);
	}
	
	@Override
	public void initAfterDBLoad() {
		super.init();
		messageBuilder = new WeaponMessageBuilder(this);
		defendersList = new Vector<TangibleObject>();
	}
	
	public int getMaxDamage() {
		return Integer.parseInt(getStringAttribute("cat_wpn_damage.damage").split("-")[1]);
	}
	
	public void setMaxDamage(int maxDamage) {
		setStringAttribute("cat_wpn_damage.damage", String.valueOf(getMinDamage()) + "-" + String.valueOf(maxDamage));
		setIntAttribute("cat_wpn_damage.dps", getDamagePerSecond());
	}
	
	public int getMinDamage() {
		return Integer.parseInt(getStringAttribute("cat_wpn_damage.damage").split("-")[0]);
	}

	public void setMinDamage(int minDamage) {
			
		setStringAttribute("cat_wpn_damage.damage", String.valueOf(minDamage) + "-" + String.valueOf(getMaxDamage()));
		setIntAttribute("cat_wpn_damage.dps", getDamagePerSecond());
	}
	
	public int getElementalDamage() {
		return getIntAttribute("cat_wpn_damage.wpn_elemental_value");
	}

	public void setElementalDamage(int elementalDamage) {
		setIntAttribute("cat_wpn_damage.wpn_elemental_value", elementalDamage);
		setIntAttribute("cat_wpn_damage.dps", getDamagePerSecond());
	}

	public String getElementalType() {
		if (getStringAttribute("cat_wpn_damage.wpn_elemental_type") != null )
			return getStringAttribute("cat_wpn_damage.wpn_elemental_type").replace("@obj_attr_n:elemental_", "");
		return null;
	}
	
	public void setElementalType(String elementalType) {
		setStringAttribute("cat_wpn_damage.wpn_elemental_type", "@obj_attr_n:elemental_" + elementalType);
	}
	
	public String getDamageType() {
		return getStringAttribute("cat_wpn_damage.wpn_damage_type").replace("@obj_attr_n:armor_eff_", "");
	}
	
	public void setDamageType(String damageType) {
		setStringAttribute("cat_wpn_damage.wpn_damage_type", "@obj_attr_n:armor_eff_" + damageType);
	}
	
	public int getDamagePerSecond() {
		if(getAttributes().containsKey("cat_wpn_damage.damage") && getAttributes().containsKey("cat_wpn_damage.wpn_attack_speed")) {

			if (getElementalType() != null )
				return (int) (((getMaxDamage() + getElementalDamage()  + getMinDamage() + getElementalDamage()) / 2 + getElementalDamage()) * (1 / getAttackSpeed()));
			else
				return (int) (((getMaxDamage() + getMinDamage()) / 2 ) * (1 / getAttackSpeed()));
		} else
				return 0;
	}

	public int getWeaponType() {
		if (getStringAttribute("cat_wpn_damage.wpn_category") == null) {
			System.err.println("Error: Weapon Type not defined for " + getTemplate());
			return 6;
		}
		
		return Integer.parseInt(getStringAttribute("cat_wpn_damage.wpn_category").replace("@obj_attr_n:wpn_category_", ""));
	}
	
	public void setWeaponType(int weaponType) {
		setStringAttribute("cat_wpn_damage.wpn_category", "@obj_attr_n:wpn_category_" + weaponType);
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
		return getFloatAttribute("cat_wpn_damage.wpn_attack_speed");
	}

	public void setAttackSpeed(float attackSpeed) {
		if((int) attackSpeed != attackSpeed)
			setFloatAttribute("cat_wpn_damage.wpn_attack_speed", attackSpeed);
		else
			setIntAttribute("cat_wpn_damage.wpn_attack_speed", (int) attackSpeed);
		
		setIntAttribute("cat_wpn_damage.dps", getDamagePerSecond());
	}
	
	public WeaponMessageBuilder getMessageBuilder() {
		return messageBuilder;
	}

	public float getMaxRange() {
		return Float.parseFloat(getStringAttribute("cat_wpn_damage.wpn_range").replace("0-", "").replace("m", ""));
	}

	public void setMaxRange(float maxRange) {
		if((int) maxRange != maxRange)
			setStringAttribute("cat_wpn_damage.wpn_range", "0-" + String.valueOf(maxRange) + "m");
		else
			setStringAttribute("cat_wpn_damage.wpn_range", "0-" + String.valueOf((int) maxRange) + "m");
	}

	public boolean isMelee() {
		
		int weaponType = getWeaponType();
		
		if(weaponType == WeaponType.ONEHANDEDMELEE || weaponType == WeaponType.TWOHANDEDMELEE || weaponType == WeaponType.UNARMED || weaponType == WeaponType.POLEARMMELEE || weaponType == WeaponType.ONEHANDEDSABER || weaponType == WeaponType.TWOHANDEDSABER || weaponType == WeaponType.POLEARMSABER)
			return true;
		
		return false;
		
	}
	
	public boolean isRanged() {
		
		int weaponType = getWeaponType();
		
		if(weaponType == WeaponType.RIFLE || weaponType == WeaponType.CARBINE || weaponType == WeaponType.PISTOL || weaponType == WeaponType.FLAMETHROWER || weaponType == WeaponType.HEAVYWEAPON)
			return true;
		
		return false;
		
	}
}
