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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import resources.datatables.Options;
import resources.datatables.PvpStatus;
import resources.datatables.FactionStatus;
import resources.loot.LootGroup;

public class MobileTemplate implements Cloneable {
	
	private Vector<String> templates;
	private int optionsBitmask = Options.ATTACKABLE;
	private int pvpBitmask = PvpStatus.Attackable;
	private int factionstatus = FactionStatus.OnLeave;
	private String faction =  "";
	private short level;
	private short minLevel;
	private short maxLevel;
	private Vector<String> attacks;
	private String defaultAttack;
	private int difficulty = 0;
	private int health, action;
	private String creatureName;
	private String StfFilename;
	private String customName;
	private float scale = 1;
	private Vector<String> weaponTemplates = new Vector<String>();
	private Vector<WeaponTemplate> weaponTemplateVector = new Vector<WeaponTemplate>();
	private int minSpawnDistance = 0;
	private int maxSpawnDistance = 0;
	private boolean deathblow = false;
	private String socialGroup = ""; // see prima guide 
	private int assistRange; // use prima guide for ranges
	private boolean isStalker = false;
	private String meatType, milkType, boneType, hideType;
	private int meatAmount, milkAmount, boneAmount, hideAmount;
	private int respawnTime = 0;
	private List<LootGroup> lootGroups = new ArrayList<LootGroup>();
	private String PCDTemplate="";
	private String conversationFileName="";

	
	public Vector<String> getTemplates() {
		return templates;
	}

	public void setTemplates(Vector<String> templates) {
		this.templates = templates;
	}

	public int getOptionsBitmask() {
		return optionsBitmask;
	}

	public void setOptionsBitmask(int optionsBitmask) {
		this.optionsBitmask = optionsBitmask;
	}

	public int getPvpBitmask() {
		return pvpBitmask;
	}

	public String getFaction(){
		return faction;
	}
	
	public void setFaction(String faction){
		this.faction = faction;
	}
	
	public int getFactionStatus(){
		return factionstatus;
	}
	
	public void setFactionStatus(int factionstatus){
		this.factionstatus = factionstatus;
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
	
	public Vector<String> getWeaponTemplates() {
		return weaponTemplates;
	}

	public void setWeaponTemplates(Vector<String> weaponTemplates) {
		this.weaponTemplates = weaponTemplates;
	}

	public Vector<WeaponTemplate> getWeaponTemplateVector() {
		return weaponTemplateVector;
	}

	public void setWeaponTemplateVector(Vector<WeaponTemplate> weaponTemplateVector) {
		this.weaponTemplateVector = weaponTemplateVector;
	}

	public int getMinSpawnDistance() {
		return minSpawnDistance;
	}

	public void setMinSpawnDistance(int minSpawnDistance) {
		this.minSpawnDistance = minSpawnDistance;
	}

	public int getMaxSpawnDistance() {
		return maxSpawnDistance;
	}

	public void setMaxSpawnDistance(int maxSpawnDistance) {
		this.maxSpawnDistance = maxSpawnDistance;
	}

	public short getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(short minLevel) {
		this.minLevel = minLevel;
	}

	public short getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(short maxLevel) {
		this.maxLevel = maxLevel;
	}

	public boolean isDeathblow() {
		return deathblow;
	}

	public void setDeathblow(boolean deathblow) {
		this.deathblow = deathblow;
	}

	public String getSocialGroup() {
		return socialGroup;
	}

	public void setSocialGroup(String socialGroup) {
		this.socialGroup = socialGroup;
	}

	public int getAssistRange() {
		return assistRange;
	}

	public void setAssistRange(int assistRange) {
		this.assistRange = assistRange;
	}

	public boolean isStalker() {
		return isStalker;
	}

	public void setStalker(boolean isStalker) {
		this.isStalker = isStalker;
	}

	public String getMeatType() {
		return meatType;
	}

	public void setMeatType(String meatType) {
		this.meatType = meatType;
	}

	public String getMilkType() {
		return milkType;
	}

	public void setMilkType(String milkType) {
		this.milkType = milkType;
	}

	public String getBoneType() {
		return boneType;
	}

	public void setBoneType(String boneType) {
		this.boneType = boneType;
	}

	public String getHideType() {
		return hideType;
	}

	public void setHideType(String hideType) {
		this.hideType = hideType;
	}

	public int getMeatAmount() {
		return meatAmount;
	}

	public void setMeatAmount(int meatAmount) {
		this.meatAmount = meatAmount;
	}

	public int getMilkAmount() {
		return milkAmount;
	}

	public void setMilkAmount(int milkAmount) {
		this.milkAmount = milkAmount;
	}

	public int getBoneAmount() {
		return boneAmount;
	}

	public void setBoneAmount(int boneAmount) {
		this.boneAmount = boneAmount;
	}

	public int getHideAmount() {
		return hideAmount;
	}

	public void setHideAmount(int hideAmount) {
		this.hideAmount = hideAmount;
	}
	
	public int getRespawnTime() {
		return respawnTime;
	}
	
	public void setRespawnTime(int respawnTime) {
		this.respawnTime = respawnTime;
	}

	public List<LootGroup> getLootGroups() {
		return lootGroups;
	}

	public void setLootGroups(List<LootGroup> lootGroups) {
		this.lootGroups = lootGroups;
	}
	
	public void addToLootGroups(String[] lootPoolNames, double[] lootPoolChances, double lootGroupChance) {
		//System.out.println("lootPoolNames[0] " + lootPoolNames[0]);
		LootGroup lootGroup = new LootGroup(lootPoolNames, lootPoolChances, lootGroupChance);
		this.lootGroups.add(lootGroup);
	}
	
	public Object clone() throws CloneNotSupportedException {
    	return super.clone();
	}

	public String getStfFilename() {
		return StfFilename;
	}

	public void setStfFilename(String stfFilename) {
		StfFilename = stfFilename;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getPCDTemplate() {
		return PCDTemplate;
	}

	public void setPCDTemplate(String pCDTemplate) {
		PCDTemplate = pCDTemplate;
	}

	public String getConversationFileName() {
		return conversationFileName;
	}

	public void setConversationFileName(String conversationFileName) {
		this.conversationFileName = conversationFileName;
	}
	
}
