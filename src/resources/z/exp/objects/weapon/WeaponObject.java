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
package resources.z.exp.objects.weapon;

import org.apache.mina.core.buffer.IoBuffer;

import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.tangible.TangibleObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class WeaponObject extends TangibleObject {
	
	@NotPersistent
	private WeaponMessageBuilder messageBuilder;
	
	public WeaponObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 1, 0), template);
	}
	
	public WeaponObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String template) {
		super(objectID, planet, position, orientation, template);
	}
	
	public WeaponObject() {
		super();
	}
	
	@Override
	public void initializeBaselines() {
		super.initializeBaselines();
		initializeBaseline(8);
		initializeBaseline(9);
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		baseline.put("maxDamage", 0);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("attackSpeed", (float) 1);
		baseline.put("14", 0);
		baseline.put("15", 0);
		baseline.put("maxRange", calculateRange());
		baseline.put("17", 0); // Could be lightsaber color?  Seen as 2 on a saber
		baseline.put("18", 0); // something to do with particle color
		baseline.put("19", 0); // something to do with particle color
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("weaponType", 0);
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
		synchronized(objectMutex) {
			return (float) baseline3.get("attackSpeed");
		}
	}
	
	public void setAttackSpeed(float attackSpeed) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("attackSpeed", attackSpeed);
		}
		
		notifyClients(buffer, false);
	}
	
	private float calculateRange() {
		int weaponType = getWeaponType();
		
		switch (weaponType) {
			case 4:
			case 5:
			case 6:
			case 7:
			case 9:
			case 10:
			case 11:
				return 5;
			case 2:
				return 35;
			case 1:
				return 50;
			case 0:
			case 3:
			case 8:
				return 64;
			default:
				return 0;
		}
	}
	
	public float getMaxRange() {
		synchronized(objectMutex) {
			return (float) baseline3.get("maxRange");
		}
	}
	
	public void setMaxRange(float maxRange) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("maxRange", maxRange);
		}
		
		notifyClients(buffer, false);
	}
	
	public int getWeaponType() {
		String template = getTemplate();
		int weaponType = -1;
		
		synchronized(objectMutex) {
			if (template == null) {
				return weaponType;
			}
			
			if (template.contains("rifle")) weaponType = 0;
			if (template.contains("carbine")) weaponType = 1;
			if (template.contains("pistol")) weaponType = 2;
			if (template.contains("heavy")) weaponType = 3;
			if (template.contains("sword") || template.contains("baton")) weaponType = 4;
			if (template.contains("2h_sword") || template.contains("axe")) weaponType = 5;
			if (template.contains("unarmed")) weaponType = 6;
			if (template.contains("polearm") || template.contains("lance")) weaponType = 7;
			if (template.contains("thrown")) weaponType = 8;
			if (template.contains("lightsaber_one_handed")) weaponType = 9;
			if (template.contains("lightsaber_two_handed")) weaponType = 10;
			if (template.contains("lightsaber_polearm")) weaponType = 11;
			
			if (weaponType == -1) {
				weaponType = 6;
			}
			
			return weaponType;
		}
	}
	
	public int getMaxDamage() {
		synchronized(objectMutex) {
			return (int) otherVariables.get("maxDamage");
		}
	}

	public void setMaxDamage(int maxDamage) {
		synchronized(objectMutex) {
			otherVariables.set("maxDamage", maxDamage);
		}
	}
	
	public boolean isMelee() {
		int weaponType = getWeaponType();
		
		if ((weaponType > 4 && weaponType < 8) || (weaponType > 8 && weaponType < 12)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isRanged() {
		int weaponType = getWeaponType();
		
		if (weaponType == 0 || weaponType == 1 || weaponType == 2 || weaponType == 3) {
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
			//destination.getSession().write(baseline3.getBaseline());
			//destination.getSession().write(baseline6.getBaseline());
			//destination.getSession().write(baseline8.getBaseline());
			//destination.getSession().write(baseline9.getBaseline());
		}
	}
	
}
