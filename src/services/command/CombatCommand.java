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
package services.command;

import java.util.Random;

import resources.objects.weapon.WeaponObject;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;

public class CombatCommand extends BaseSWGCommand {
	
	private String[] defaultAnimations = new String[]{};
	private String[] oneHandedAnimations = new String[]{};
	private String[] twoHandedAnimations = new String[]{};
	private String[] polearmAnimations = new String[]{};
	private String[] unarmedAnimations = new String[]{};
	private String[] pistolAnimations = new String[]{};
	private String[] carbineAnimations = new String[]{};
	private String[] lightRifleAnimations = new String[]{};
	private String[] rifleAnimations = new String[]{};
	private String[] heavyWpnAnimations = new String[]{};
	private String[] oneHandedLSAnimations = new String[]{};
	private String[] twoHandedLSAnimations = new String[]{};
	private String[] polearmLSAnimations = new String[]{};
	private String[] thrownAnimations = new String[]{};
	private byte validTargetType;
	private byte hitType;
	private byte healType;
	private byte attackType;
	private float coneLength;
	private float coneWidth;
	private float minRange;
	private float maxRange;
	private int addedDamage, flatActionDamage;
	private float percentFromWeapon;
	private float bypassArmor;
	private float healthCost;
	private float actionCost;
	private String dotType;
	private int dotDuration, dotIntensity;
	private String buffNameTarget, buffNameSelf;
	private float buffStrengthTarget, buffStrengthSelf, buffDurationTarget, buffDurationSelf;
	private boolean canBePunishing;
	private int minDamage, maxDamage;
	private byte weaponType, weaponCategory;
	private float maxRangeOverload;
	private int damageType, elementalType, elementalValue;
	private String performanceSpam;
	private byte hitSpam;
	//private float cooldown;
	private String delayAttackEggTemplate;
	private String delayAttackParticle;
	private float initialAttackDelay;
	private float delayAttackInterval;
	private int delayAttackLoops;
	private int delayAttackEggPosition;
	//private String cooldownGroup;
	//private float executeTime;
	//private float warmupTime;
	private float vigorCost; // for commando kill meter and bm specials
	private float criticalChance;
	private int attack_rolls;
	
	public CombatCommand(String commandName) {
		super(commandName);
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/combat/combat_data.iff", DatatableVisitor.class);
			
			for(int i = 0; i < visitor.getRowCount(); i++) {
				if(visitor.getObject(i, 0) != null)
					if(((String) visitor.getObject(i, 0)).equalsIgnoreCase(commandName)) {
						
						validTargetType = ((Integer) visitor.getObject(i, 5)).byteValue();
						hitType = ((Integer) visitor.getObject(i, 6)).byteValue();
						healType = ((Integer) visitor.getObject(i, 7)).byteValue();
						delayAttackEggTemplate = (String) visitor.getObject(i, 12);
						delayAttackParticle = (String) visitor.getObject(i, 13);
						initialAttackDelay = (Float) visitor.getObject(i, 14);
						delayAttackInterval = (Float) visitor.getObject(i, 15);
						delayAttackLoops = (Integer) visitor.getObject(i, 16);
						delayAttackEggPosition = (Integer) visitor.getObject(i, 17);
						String defaultAnims = (String) visitor.getObject(i, 21);
						setDefaultAnimations(defaultAnims.split(","));
						String unarmedAnims = (String) visitor.getObject(i, 22);
						setUnarmedAnimations(unarmedAnims.split(","));
						String oneHAnims = (String) visitor.getObject(i, 23);
						setOneHandedAnimations(oneHAnims.split(","));
						String twoHAnims = (String) visitor.getObject(i, 24);
						setTwoHandedAnimations(twoHAnims.split(","));
						String polearmAnims = (String) visitor.getObject(i, 25);
						setPolearmAnimations(polearmAnims.split(","));
						String pistolAnims = (String) visitor.getObject(i, 26);
						setPistolAnimations(pistolAnims.split(","));
						String lightRifleAnims = (String) visitor.getObject(i, 27);
						setLightRifleAnimations(lightRifleAnims.split(","));
						String carbineAnims = (String) visitor.getObject(i, 28);
						setCarbineAnimations(carbineAnims.split(","));
						String rifleAnims = (String) visitor.getObject(i, 29);
						setRifleAnimations(rifleAnims.split(","));
						String heavyAnims = (String) visitor.getObject(i, 30);
						setHeavyWpnAnimations(heavyAnims.split(","));
						String thrownAnims = (String) visitor.getObject(i, 31);
						setThrownAnimations(thrownAnims.split(","));
						String oneHandLSAnims = (String) visitor.getObject(i, 32);
						setOneHandedLSAnimations(oneHandLSAnims.split(","));
						String twoHandLSAnims = (String) visitor.getObject(i, 33);
						setTwoHandedLSAnimations(twoHandLSAnims.split(","));
						String polearmLSAnims = (String) visitor.getObject(i, 34);
						setPolearmLSAnimations(polearmLSAnims.split(","));
						attackType = ((Integer) visitor.getObject(i, 40)).byteValue();
						coneLength = (Float) visitor.getObject(i, 41);
						coneWidth = (Float) visitor.getObject(i, 42);
						minRange = (Float) visitor.getObject(i, 43);
						maxRange = (Float) visitor.getObject(i, 44);
						addedDamage = (Integer) visitor.getObject(i, 45);		
						flatActionDamage = (Integer) visitor.getObject(i, 46);
						percentFromWeapon = (Float) visitor.getObject(i, 47);
						bypassArmor = (Float) visitor.getObject(i, 48);
						healthCost = (Float) visitor.getObject(i, 54);
						actionCost = (Float) visitor.getObject(i, 55);
						setVigorCost((Float) visitor.getObject(i, 56));
						dotType = (String) visitor.getObject(i, 60);
						dotIntensity = (Integer) visitor.getObject(i, 61);
						dotDuration = (Integer) visitor.getObject(i, 62);
						buffNameTarget = (String) visitor.getObject(i, 63);
						buffStrengthTarget = (Float) visitor.getObject(i, 64);
						buffDurationTarget = (Float) visitor.getObject(i, 65);
						buffNameSelf = (String) visitor.getObject(i, 66);
						buffStrengthSelf = (Float) visitor.getObject(i, 67);
						buffDurationSelf = (Float) visitor.getObject(i, 68);
						canBePunishing = (Integer) visitor.getObject(i, 69) != null;
						minDamage = (Integer) visitor.getObject(i, 77);
						maxDamage = (Integer) visitor.getObject(i, 78);
						maxRangeOverload = (Float) visitor.getObject(i, 79);
						weaponCategory = ((Integer) visitor.getObject(i, 81)).byteValue();
						damageType = ((Integer) visitor.getObject(i, 82)).byteValue();
						elementalType = ((Integer) visitor.getObject(i, 83));
						elementalValue = (Integer) visitor.getObject(i, 84);
						performanceSpam = (String) visitor.getObject(i, 89);
						hitSpam = ((Integer) visitor.getObject(i, 90)).byteValue();
						attack_rolls = (Integer) visitor.getObject(i, 93);
						
					}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public String[] getDefaultAnimations() {
		return defaultAnimations;
	}

	public void setDefaultAnimations(String[] defaultAnimations) {
		this.defaultAnimations = defaultAnimations;
	}

	public String[] getOneHandedAnimations() {
		return oneHandedAnimations;
	}

	public void setOneHandedAnimations(String[] oneHandedAnimations) {
		this.oneHandedAnimations = oneHandedAnimations;
	}

	public String[] getTwoHandedAnimations() {
		return twoHandedAnimations;
	}

	public void setTwoHandedAnimations(String[] twoHandedAnimations) {
		this.twoHandedAnimations = twoHandedAnimations;
	}

	public String[] getPolearmAnimations() {
		return polearmAnimations;
	}

	public void setPolearmAnimations(String[] polearmAnimations) {
		this.polearmAnimations = polearmAnimations;
	}

	public String[] getUnarmedAnimations() {
		return unarmedAnimations;
	}

	public void setUnarmedAnimations(String[] unarmedAnimations) {
		this.unarmedAnimations = unarmedAnimations;
	}

	public String[] getPistolAnimations() {
		return pistolAnimations;
	}

	public void setPistolAnimations(String[] pistolAnimations) {
		this.pistolAnimations = pistolAnimations;
	}

	public String[] getCarbineAnimations() {
		return carbineAnimations;
	}

	public void setCarbineAnimations(String[] carbineAnimations) {
		this.carbineAnimations = carbineAnimations;
	}

	public String[] getLightRifleAnimations() {
		return lightRifleAnimations;
	}

	public void setLightRifleAnimations(String[] lightRifleAnimations) {
		this.lightRifleAnimations = lightRifleAnimations;
	}

	public String[] getRifleAnimations() {
		return rifleAnimations;
	}

	public void setRifleAnimations(String[] rifleAnimations) {
		this.rifleAnimations = rifleAnimations;
	}

	public String[] getHeavyWpnAnimations() {
		return heavyWpnAnimations;
	}

	public void setHeavyWpnAnimations(String[] heavyWpnAnimations) {
		this.heavyWpnAnimations = heavyWpnAnimations;
	}

	public String[] getOneHandedLSAnimations() {
		return oneHandedLSAnimations;
	}

	public void setOneHandedLSAnimations(String[] oneHandedLSAnimations) {
		this.oneHandedLSAnimations = oneHandedLSAnimations;
	}

	public String[] getTwoHandedLSAnimations() {
		return twoHandedLSAnimations;
	}

	public void setTwoHandedLSAnimations(String[] twoHandedLSAnimations) {
		this.twoHandedLSAnimations = twoHandedLSAnimations;
	}

	public String[] getPolearmLSAnimations() {
		return polearmLSAnimations;
	}

	public void setPolearmLSAnimations(String[] polearmLSAnimations) {
		this.polearmLSAnimations = polearmLSAnimations;
	}

	public String[] getThrownAnimations() {
		return thrownAnimations;
	}

	public void setThrownAnimations(String[] thrownAnimations) {
		this.thrownAnimations = thrownAnimations;
	}

	public byte getValidTargetType() {
		return validTargetType;
	}

	public void setValidTargetType(byte validTargetType) {
		this.validTargetType = validTargetType;
	}

	public byte getHitType() {
		return hitType;
	}

	public void setHitType(byte hitType) {
		this.hitType = hitType;
	}

	public byte getHealType() {
		return healType;
	}

	public void setHealType(byte healType) {
		this.healType = healType;
	}

	public byte getAttackType() {
		return attackType;
	}

	public void setAttackType(byte attackType) {
		this.attackType = attackType;
	}

	public float getConeLength() {
		return coneLength;
	}

	public void setConeLength(float coneLength) {
		this.coneLength = coneLength;
	}

	public float getConeWidth() {
		return coneWidth;
	}

	public void setConeWidth(float coneWidth) {
		this.coneWidth = coneWidth;
	}

	public float getMinRange() {
		return minRange;
	}

	public void setMinRange(float minRange) {
		this.minRange = minRange;
	}

	public float getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(float maxRange) {
		this.maxRange = maxRange;
	}

	public int getAddedDamage() {
		return addedDamage;
	}

	public void setAddedDamage(int addedDamage) {
		this.addedDamage = addedDamage;
	}

	public int getFlatActionDamage() {
		return flatActionDamage;
	}

	public void setFlatActionDamage(int flatActionDamage) {
		this.flatActionDamage = flatActionDamage;
	}

	public float getPercentFromWeapon() {
		return percentFromWeapon;
	}

	public void setPercentFromWeapon(float percentFromWeapon) {
		this.percentFromWeapon = percentFromWeapon;
	}

	public float getBypassArmor() {
		return bypassArmor;
	}

	public void setBypassArmor(float bypassArmor) {
		this.bypassArmor = bypassArmor;
	}

	public float getHealthCost() {
		return healthCost;
	}

	public void setHealthCost(float healthCost) {
		this.healthCost = healthCost;
	}

	public float getActionCost() {
		return actionCost;
	}

	public void setActionCost(float actionCost) {
		this.actionCost = actionCost;
	}

	public String getDotType() {
		return dotType;
	}

	public void setDotType(String dotType) {
		this.dotType = dotType;
	}

	public int getDotDuration() {
		return dotDuration;
	}

	public void setDotDuration(int dotDuration) {
		this.dotDuration = dotDuration;
	}

	public int getDotIntensity() {
		return dotIntensity;
	}

	public void setDotIntensity(int dotIntensity) {
		this.dotIntensity = dotIntensity;
	}

	public String getBuffNameTarget() {
		return buffNameTarget;
	}

	public void setBuffNameTarget(String buffNameTarget) {
		this.buffNameTarget = buffNameTarget;
	}

	public String getBuffNameSelf() {
		return buffNameSelf;
	}

	public void setBuffNameSelf(String buffNameSelf) {
		this.buffNameSelf = buffNameSelf;
	}

	public float getBuffStrengthTarget() {
		return buffStrengthTarget;
	}

	public void setBuffStrengthTarget(float buffStrengthTarget) {
		this.buffStrengthTarget = buffStrengthTarget;
	}

	public float getBuffStrengthSelf() {
		return buffStrengthSelf;
	}

	public void setBuffStrengthSelf(float buffStrengthSelf) {
		this.buffStrengthSelf = buffStrengthSelf;
	}

	public float getBuffDurationTarget() {
		return buffDurationTarget;
	}

	public void setBuffDurationTarget(float buffDurationTarget) {
		this.buffDurationTarget = buffDurationTarget;
	}

	public float getBuffDurationSelf() {
		return buffDurationSelf;
	}

	public void setBuffDurationSelf(float buffDurationSelf) {
		this.buffDurationSelf = buffDurationSelf;
	}

	public boolean isCanBePunishing() {
		return canBePunishing;
	}

	public void setCanBePunishing(boolean canBePunishing) {
		this.canBePunishing = canBePunishing;
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

	public byte getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(byte weaponType) {
		this.weaponType = weaponType;
	}

	public byte getWeaponCategory() {
		return weaponCategory;
	}

	public void setWeaponCategory(byte weaponCategory) {
		this.weaponCategory = weaponCategory;
	}

	public float getMaxRangeOverload() {
		return maxRangeOverload;
	}

	public void setMaxRangeOverload(float maxRangeOverload) {
		this.maxRangeOverload = maxRangeOverload;
	}

	public int getDamageType() {
		return damageType;
	}

	public void setDamageType(int damageType) {
		this.damageType = damageType;
	}

	public int getElementalType() {
		return elementalType;
	}

	public void setElementalType(int elementalType) {
		this.elementalType = elementalType;
	}

	public int getElementalValue() {
		return elementalValue;
	}

	public void setElementalValue(int elementalValue) {
		this.elementalValue = elementalValue;
	}

	public String getPerformanceSpam() {
		return performanceSpam;
	}

	public void setPerformanceSpam(String performanceSpam) {
		this.performanceSpam = performanceSpam;
	}

	public byte getHitSpam() {
		return hitSpam;
	}

	public void setHitSpam(byte hitSpam) {
		this.hitSpam = hitSpam;
	}

	public String getRandomAnimation(WeaponObject weapon) {
		
		int weaponType = weapon.getWeaponType();
		String[] animations;
		
		switch(weaponType) {
			
			case 0:
				animations = rifleAnimations;
				break;
			case 1:
				animations = carbineAnimations;
				break;
			case 2:
				animations = pistolAnimations;
				break;
			case 3:
				animations = heavyWpnAnimations;
				break;
			case 4:
				animations = oneHandedAnimations;
				break;
			case 5:
				animations = twoHandedAnimations;
				break;
			case 6:
				animations = defaultAnimations;
				break;
			case 7:
				animations = polearmAnimations;
				break;
			case 8:
				animations = thrownAnimations;
				break;
			case 9:
				animations = oneHandedLSAnimations;				
				break;
			case 10:
				animations = twoHandedLSAnimations;
				break;
			case 11:
				animations = polearmLSAnimations;
				break;

			default:
				animations = defaultAnimations;
				break;

		}
		
		if(animations.length == 0)
			animations = defaultAnimations;
		
		return animations[new Random().nextInt(animations.length)];
		
	}

	public String getDelayAttackEggTemplate() {
		return delayAttackEggTemplate;
	}

	public void setDelayAttackEggTemplate(String delayAttackEggTemplate) {
		this.delayAttackEggTemplate = delayAttackEggTemplate;
	}

	public String getDelayAttackParticle() {
		return delayAttackParticle;
	}

	public void setDelayAttackParticle(String delayAttackParticle) {
		this.delayAttackParticle = delayAttackParticle;
	}

	public float getInitialAttackDelay() {
		return initialAttackDelay;
	}

	public void setInitialAttackDelay(float initialAttackDelay) {
		this.initialAttackDelay = initialAttackDelay;
	}

	public float getDelayAttackInterval() {
		return delayAttackInterval;
	}

	public void setDelayAttackInterval(float delayAttackInterval) {
		this.delayAttackInterval = delayAttackInterval;
	}

	public int getDelayAttackEggPosition() {
		return delayAttackEggPosition;
	}

	public void setDelayAttackEggPosition(int delayAttackEggPosition) {
		this.delayAttackEggPosition = delayAttackEggPosition;
	}

	public int getDelayAttackLoops() {
		return delayAttackLoops;
	}

	public void setDelayAttackLoops(int delayAttackLoops) {
		this.delayAttackLoops = delayAttackLoops;
	}

	public float getVigorCost() {
		return vigorCost;
	}

	public void setVigorCost(float vigorCost) {
		this.vigorCost = vigorCost;
	}

	public float getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(float criticalChance) {
		this.criticalChance = criticalChance;
	}

	public int getAttack_rolls() {
		return attack_rolls;
	}

	public void setAttack_rolls(int attack_rolls) {
		this.attack_rolls = attack_rolls;
	}
	
	

}
