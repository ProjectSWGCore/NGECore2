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

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.Baseline;
import resources.datatables.Elemental;
import resources.datatables.WeaponType;
import resources.objects.tangible.TangibleObject;
import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class WeaponObject extends TangibleObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private transient WeaponMessageBuilder messageBuilder;
	
	public WeaponObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 1, 0), template);
		if (this.getClass().getSimpleName().equals("WeaponObject")) setIntAttribute("volume", 1);
		setStringAttribute("cat_wpn_damage.damage", "0-0");
	}
	
	public WeaponObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String template) {
		super(objectID, planet, position, orientation, template);
		if (this.getClass().getSimpleName().equals("WeaponObject")) setIntAttribute("volume", 1);
		setStringAttribute("cat_wpn_damage.damage", "0-0");
	}
	
	public WeaponObject() {
		super();
	}
	
	public void initAfterDBLoad() {
		super.initAfterDBLoad();
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("attackSpeed", (float) 0);
		baseline.put("14", 0);
		baseline.put("15", 0);
		baseline.put("maxRange", (float) 0);
		baseline.put("17", 0); // Could be lightsaber color?  Seen as 2 on a saber
		baseline.put("weaponParticleEffect", 0);
		baseline.put("19", 0); // something to do with particle color
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("weaponType", WeaponType.UNARMED);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	public float getAttackSpeed() {
		return (float) getBaseline(3).get("attackSpeed");
	}
	
	public void setAttackSpeed(float attackSpeed) {
		if ((int) attackSpeed != attackSpeed) {
			setFloatAttribute("cat_wpn_damage.wpn_attack_speed", attackSpeed);
		} else {
			setIntAttribute("cat_wpn_damage.wpn_attack_speed", (int) attackSpeed);
		}
		
		setIntAttribute("cat_wpn_damage.dps", getDamagePerSecond());
		notifyClients(getBaseline(3).set("attackSpeed", attackSpeed), false);
	}
	
	public float getMaxRange() {
		return (float) getBaseline(3).get("maxRange");
	}
	
	public void setMaxRange(float maxRange) {
		if ((int) maxRange != maxRange) {
			setStringAttribute("cat_wpn_damage.wpn_range", "0-" + String.valueOf(maxRange) + "m");
		} else {
			setStringAttribute("cat_wpn_damage.wpn_range", "0-" + String.valueOf((int) maxRange) + "m");
		}
		
		notifyClients(getBaseline(3).set("maxRange", maxRange), false);
	}
	
	public int getWeaponParticleEffect() {
		return (int) getBaseline(3).get("weaponParticleEffect");
	}
	
	public void setWeaponParticleEffect(int weaponParticleEffect) {
		notifyClients(getBaseline(3).set("weaponParticleEffect", weaponParticleEffect), false);
	}
	
	public int getWeaponType() {
		if (getStringAttribute("cat_wpn_damage.wpn_category") == null) {
			System.err.println("Error: Weapon Type not defined for " + getTemplate());
		}
		
		return (int) getBaseline(6).get("weaponType");
	}
	
	public void setWeaponType(int weaponType) {
		setStringAttribute("cat_wpn_damage.wpn_category", "@obj_attr_n:wpn_category_" + weaponType);
		notifyClients(getBaseline(6).set("weaponType", weaponType), false);
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
		if (getStringAttribute("cat_wpn_damage.wpn_elemental_type") != null) {
			return getStringAttribute("cat_wpn_damage.wpn_elemental_type").replace("@obj_attr_n:elemental_", "");
		}
		
		return null;
	}
	
	public void setElementalType(String elementalType) {
		setStringAttribute("cat_wpn_damage.wpn_elemental_type", "@obj_attr_n:elemental_" + elementalType);
		setWeaponParticleEffect(Elemental.getElementalNum(getElementalType()));
	}
	
	public String getDamageType() {
		return getStringAttribute("cat_wpn_damage.wpn_damage_type").replace("@obj_attr_n:armor_eff_", "");
	}
	
	public void setDamageType(String damageType) {
		setStringAttribute("cat_wpn_damage.wpn_damage_type", "@obj_attr_n:armor_eff_" + damageType);
		setWeaponParticleEffect(Elemental.getElementalNum(getDamageType()));
	}
	
	public int getDamagePerSecond() {
		if (getAttributes().containsKey("cat_wpn_damage.damage") && getAttributes().containsKey("cat_wpn_damage.wpn_attack_speed")) {
			if (getElementalType() != null) {
				return (int) (((getMaxDamage() + getElementalDamage()  + getMinDamage() + getElementalDamage()) / 2 + getElementalDamage()) * (1 / getAttackSpeed()));
			} else {
				return (int) (((getMaxDamage() + getMinDamage()) / 2 ) * (1 / getAttackSpeed()));
			}
		} else {
			return 0;
		}
	}
	
	public boolean isMelee() {
		int weaponType = getWeaponType();
		
		if (weaponType == WeaponType.ONEHANDEDMELEE || weaponType == WeaponType.TWOHANDEDMELEE || weaponType == WeaponType.UNARMED || weaponType == WeaponType.POLEARMMELEE || weaponType == WeaponType.ONEHANDEDSABER || weaponType == WeaponType.TWOHANDEDSABER || weaponType == WeaponType.POLEARMSABER) {
			return true;
		}
		
		return false;
	}
	
	public boolean isRanged() {
		int weaponType = getWeaponType();
		
		if (weaponType == WeaponType.RIFLE || weaponType == WeaponType.CARBINE || weaponType == WeaponType.PISTOL || weaponType == WeaponType.FLAMETHROWER || weaponType == WeaponType.HEAVYWEAPON) {
			return true;
		}
			
		return false;
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, notifySelf);
	}
	
	@Override
	public WeaponMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new WeaponMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(getBaseline(3).getBaseline());
			destination.getSession().write(getBaseline(6).getBaseline());
			
			Client parent = ((getGrandparent() == null) ? null : getGrandparent().getClient());
			
			if (parent != null && destination == parent) {
				destination.getSession().write(getBaseline(8).getBaseline());
				destination.getSession().write(getBaseline(9).getBaseline());
			}
		}
	}
	
}
