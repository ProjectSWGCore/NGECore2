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

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class WeaponObject extends SWGObject {
	
	// TODO: Thread safety
	
	private int incapTimer = 10;
	private int conditionDamage = 0;

	private byte[] customization;
	private List<Integer> componentCustomizations = new ArrayList<Integer>();
	private int optionsBitmask = 0;
	private int maxDamage = 0;
	private boolean staticObject = true;
	@NotPersistent
	private WeaponMessageBuilder messageBuilder;
	
	private float attackSpeed = 1;
	private float maxRange;
	
	public WeaponObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 1, 0), template);
		messageBuilder = new WeaponMessageBuilder(this);
		calculateRange();
	}
	

	public WeaponObject(long objectID, Planet planet, String template, Point3D position, Quaternion orientation) {
		super(objectID, planet, position, orientation, template);
		messageBuilder = new WeaponMessageBuilder(this);
		calculateRange();
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
			case 3: maxRange = 64; break;
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

	public int getConditionDamage() {
		return conditionDamage;
	}

	public void setConditionDamage(int conditionDamage) {
		this.conditionDamage = conditionDamage;
	}

	public byte[] getCustomization() {
		return customization;
	}

	public void setCustomization(byte[] customization) {
		this.customization = customization;
	}

	public List<Integer> getComponentCustomizations() {
		return componentCustomizations;
	}

	public void setComponentCustomizations(List<Integer> componentCustomizations) {
		this.componentCustomizations = componentCustomizations;
	}

	public int getOptionsBitmask() {
		return optionsBitmask;
	}

	public void setOptionsBitmask(int optionsBitmask) {
		this.optionsBitmask = optionsBitmask;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public boolean isStaticObject() {
		return staticObject;
	}

	public void setStaticObject(boolean staticObject) {
		this.staticObject = staticObject;
	}

	public int getWeaponType() {
		
		int weaponType = -1;
		
		String template = getTemplate();
		
		if(template == null)
			return weaponType;
		
		if(template.contains("rifle")) weaponType = 0;
		if(template.contains("carbine")) weaponType = 1;
		if(template.contains("pistol")) weaponType = 2;
		if(template.contains("heavy")) weaponType = 3;
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

}
