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
package resources.z.exp.objects.creature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ChatSystemMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.UpdatePVPStatusMessage;
import protocol.swg.UpdatePostureMessage;
import protocol.swg.objectControllerObjects.Posture;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;

import engine.clients.Client;
import resources.z.exp.buffs.Buff;
import resources.common.StringUtilities;
import resources.z.exp.equipment.Equipment;
import resources.z.exp.group.GroupInviteInfo;
import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.SWGList;
import resources.z.exp.objects.SWGMap;
import resources.z.exp.objects.SWGSet;
import engine.resources.common.CRC;
import engine.resources.objects.IPersistent;
import engine.resources.objects.SWGObject;
import resources.z.exp.skills.SkillMod;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

import resources.z.exp.objects.player.PlayerObject;
import resources.z.exp.objects.tangible.TangibleObject;

@Entity
public class CreatureObject extends TangibleObject implements IPersistent {
	
	@NotPersistent
	private Transaction txn;
	
	@NotPersistent
	private CreatureMessageBuilder messageBuilder;
	
	// non-baseline vars
	@NotPersistent
	private List<Long> duelList = Collections.synchronizedList(new ArrayList<Long>());
	
	public CreatureObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
		getBaseHAMList().add(0x00002FF8);
		getBaseHAMList().add(0x000003E8);
		getBaseHAMList().add(0x0000245F);
		getBaseHAMList().add(0x000001F4);
		getBaseHAMList().add(0x0000012C);
		getBaseHAMList().add(0x000003E8);
		setVolume(0x000F4240);
		getComponentCustomizationList().add(0);
		setOptionsBitmask(0x80);
		setMaximumCondition(0x3A98);
		getHamList().add(20000);
		getHamList().add(0);
		getHamList().add(12500);
		getHamList().add(0);
		getHamList().add(0x2C01);
		getHamList().add(0);
		getMaxHamList().add(20000);
		getMaxHamList().add(0);
		getMaxHamList().add(12500);
		getMaxHamList().add(0);
		getMaxHamList().add(0x2C01);
		getMaxHamList().add(0);
		getBuffList().put(0, new Buff("", 0)); // Initial Default Buff
	}
	
	public CreatureObject() {
		super();
	}
	
	@Override
	public void initializeBaselines() {
		super.initializeBaselines();
		initializeBaseline(1);
		initializeBaseline(4);
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline1() {
		Baseline baseline = super.getBaseline1();
		baseline.put("bankCredits", 0);
		baseline.put("cashCredits", 0);
		baseline.put("baseHamList", new SWGList<Integer>(this, 1, 2, false));
		baseline.put("skills", new SWGSet<String>(this, 1, 3, false));
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("posture", (byte) 1);
		baseline.put("factionRank", (byte) 0); // For NPCs probably
		baseline.put("ownerId", (long) 0);
		baseline.put("height", (float) 1);
		baseline.put("battleFatigue", 0);
		baseline.put("stateBitmask", (long) 0);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline4() {
		Baseline baseline = super.getBaseline4();
		baseline.put("accelerationMultiplierBase", (float) 1);
		baseline.put("accelerationMultiplierMod", (float) 1);
		baseline.put("hamEncumberanceList", new SWGList<Integer>(this, 4, 2, false));
		baseline.put("skillMods", new SWGMap<String, SkillMod>(this, 4, 3, true));
		baseline.put("speedMultiplierBase", (float) 1);
		baseline.put("speedMultiplierMod", (float) 1);
		baseline.put("listenToId", (long) 0);
		baseline.put("runSpeed", (float) 7.3);
		baseline.put("slopeModAngle", (float) 1);
		baseline.put("slopeModPercent", (float) 1);
		baseline.put("turnRadius", (float) 1);
		baseline.put("walkSpeed", (float) 2.75);
		baseline.put("waterModPercent", (float) 1);
		baseline.put("missionCriticalObjects", new SWGMap<Long, Long>(this, 4, 13, false));
		baseline.put("abilities", new SWGMap<String, Integer>(this, 4, 14, true));
		baseline.put("15", (byte) 0); // Unknown (been seen as 0xD4)
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("level", (short) 90);
		baseline.put("9", (int) 0xD007);
		baseline.put("currentAnimation", "");
		baseline.put("moodAnimation", "neutral");
		baseline.put("weaponId", (long) 0);
		baseline.put("groupId", (long) 0);
		baseline.put("groupInviteInfo", new GroupInviteInfo((long) 0, ""));
		baseline.put("guildId", 0);
		baseline.put("targetId", (long) 0);
		baseline.put("moodId", (byte) 0);
		baseline.put("performanceCounter", 0);
		baseline.put("performanceId", 0);
		baseline.put("20", new SWGList<Byte>(this, 6, 20, false)); // Unknown List
		baseline.put("hamList", new SWGList<Integer>(this, 6, 21, false));
		baseline.put("maxHamList", new SWGList<Integer>(this, 6, 22, false));
		baseline.put("equipmentList", new SWGList<Equipment>(this, 6, 23, false));
		baseline.put("costume", "");
		baseline.put("lockMovement", false);
		baseline.put("buffList", new SWGMap<Integer, Buff>(this, 6, 26, false));
		// need to build an 0x01 int after the bufflist
		baseline.put("27", (short) 0);
		baseline.put("hologramColor", -1);
		baseline.put("29", (byte) 1);
		baseline.put("30", (short) 0);
		baseline.put("appearanceEquipmentList", new SWGList<Equipment>(this, 6, 31, false));
		baseline.put("32", 0);
		baseline.put("33", (short) 0);
		baseline.put("34", (short) 0); // there's 35 elements, so 2 of these = shorts
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
	
	public int getBankCredits() {
		synchronized(objectMutex) {
			return (int) baseline1.get("bankCredits");
		}
	}
	
	public void setBankCredits(int bankCredits) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline1.set("bankCredits", bankCredits);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public int getCashCredits() {
		synchronized(objectMutex) {
			return (int) baseline1.get("cashCredits");
		}
	}
	
	public void setCashCredits(int cashCredits) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline1.set("cashCredits", cashCredits);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getBaseHAMList() {
		return (SWGList<Integer>) baseline1.get("baseHamList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGSet<String> getSkills() {
		return (SWGSet<String>) baseline3.get("skills");
	}
	
	public void addSkill(String skill) {
		if (!getSkills().contains(skill)) {
			getSkills().add(skill);
		}
	}
	
	public void removeSkill(String skill) {
		if (getSkills().contains(skill)) {
			getSkills().remove(skill);
		}
	}
	
	public byte getPosture() {
		synchronized(objectMutex) {
			return (byte) baseline3.get("posture");
		}
	}
	
	public void setPosture(byte posture) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("posture", posture);
		}
		
		notifyObservers(buffer, true);
		notifyObservers(new ObjControllerMessage(0x1B, new Posture(getObjectID(), posture)), true);
	}
	
	public byte getFactionRank() {
		synchronized(objectMutex) {
			return (byte) baseline3.get("factionRank");
		}
	}
	
	public void setFactionRank(byte factionRank) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("factionRank", factionRank);
		}
		
		notifyObservers(buffer, true);
	}
	
	public long getOwnerId() {
		synchronized(objectMutex) {
			return (long) baseline3.get("ownerId");
		}
	}
	
	public void setOwnerId(long ownerId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("ownerId", ownerId);
		}
		
		notifyObservers(buffer, true);
	}
	
	public float getHeight() {
		synchronized(objectMutex) {
			return (float) baseline3.get("height");
		}
	}
	
	public void setHeight(float height) {
		height = (((height < 0.7) || (height > 1.5)) ? 1 : height);
		
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("height", height);
		}
		
		notifyObservers(buffer, true);
	}
	
	public int getBattleFatigue() {
		synchronized(objectMutex) {
			return (int) baseline3.get("battleFatigue");
		}
	}
	
	public void setBattleFatigue(int battleFatigue) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("battleFatigue", battleFatigue);
		}
		
		notifyObservers(buffer, true);
	}
	
	public long getStateBitmask() {
		synchronized(objectMutex) {
			return (long) baseline3.get("stateBitmask");
		}
	}
	
	public void setStateBitmask(long stateBitmask) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("stateBitmask", stateBitmask);
		}
		
		notifyObservers(buffer, true);
	}
	
	public float getAccelerationMultiplierBase() {
		synchronized(objectMutex) {
			return (float) baseline4.get("accelerationMultiplierBase");
		}
	}
	
	public void setAccelerationMultiplierBase(float accelerationMultiplierBase) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("accelerationMultiplierBase", accelerationMultiplierBase);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public float getAccelerationMultiplierMod() {
		synchronized(objectMutex) {
			return (float) baseline4.get("accelerationMultiplierMod");
		}
	}
	
	public void setAccelerationMultiplierMod(float accelerationMultiplierMod) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("accelerationMultiplierMod", accelerationMultiplierMod);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getHamEncumberanceList() {
		return (SWGList<Integer>) baseline4.get("hamEncumberanceList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, SkillMod> getSkillMods() {
		return (SWGMap<String, SkillMod>) baseline4.get("skillMods");
	}
	
	public SkillMod getSkillMod(String name) {
		synchronized(objectMutex) {
			if (getSkillMods().containsKey(name) && getSkillMods().get(name) != null) {
				return getSkillMods().get(name);
			}
			
			return null;
		}
	}
	
	public void addSkillMod(String name, int base) {
		if (!getSkillMods().containsKey(name)) {
			getSkillMods().put(name, new SkillMod(base, 0));
		} else {
			SkillMod mod = getSkillMods().get(name);
			getSkillMods().put(name, new SkillMod(mod.getBase() + base, mod.getModifier()));
		}
	}
	
	public void deductSkillMod(String name, int base) {
		if (getSkillMods().containsKey(name)) {
			SkillMod mod = getSkillMods().get(name);
			mod = new SkillMod(mod.getBase() - base, mod.getModifier());
			
			if (mod.getBase() - base <= 0) {
				removeSkillMod(name);
			} else {
				getSkillMods().put(name, mod);
			}
		}
	}
	
	public void removeSkillMod(String name) {
		getSkillMods().remove(name);
	}
	
	public float getSpeedMultiplierBase() {
		synchronized(objectMutex) {
			return (float) baseline4.get("speedMultiplierBase");
		}
	}
	
	public void setSpeedMultiplierBase(float speedMultiplierBase) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("speedMultiplierBase", speedMultiplierBase);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public float getSpeedMultiplierMod() {
		synchronized(objectMutex) {
			return (float) baseline4.get("speedMultiplierMod");
		}
	}
	
	public void setSpeedMultiplierMod(float speedMultiplierMod) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("speedMultiplierMod", speedMultiplierMod);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public long getListenToId() {
		synchronized(objectMutex) {
			return (long) baseline4.get("listenToId");
		}
	}
	
	public void setListenToId(long listenToId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("listenToId", listenToId);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public float getRunSpeed() {
		synchronized(objectMutex) {
			return (float) baseline4.get("runSpeed");
		}
	}
	
	public void setRunSpeed(float runSpeed) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("runSpeed", runSpeed);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public float getSlopeModAngle() {
		synchronized(objectMutex) {
			return (float) baseline4.get("slopeModAngle");
		}
	}
	
	public void setSlopeModAngle(float slopeModAngle) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("slopeModAngle", slopeModAngle);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public float getSlopeModPercent() {
		synchronized(objectMutex) {
			return (float) baseline4.get("slopeModPercent");
		}
	}
	
	public void setSlopeModPercent(float slopeModPercent) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("slopeModPercent", slopeModPercent);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public float getTurnRadius() {
		synchronized(objectMutex) {
			return (float) baseline4.get("turnRadius");
		}
	}
	
	public void setTurnRadius(float turnRadius) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("turnRadius", turnRadius);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public float getWalkSpeed() {
		synchronized(objectMutex) {
			return (float) baseline4.get("walkSpeed");
		}
	}
	
	public void setWalkSpeed(float walkSpeed) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("walkSpeed", walkSpeed);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public float getWaterModPercent() {
		synchronized(objectMutex) {
			return (float) baseline4.get("waterModPercent");
		}
	}
	
	public void setWaterModPercent(float waterModPercent) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline4.set("waterModPercent", waterModPercent);
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<Long, Long> getMissionCriticalObjects() {
		return (SWGMap<Long, Long>) baseline4.get("missionCriticalObjects");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, Integer> getAbilities() {
		return (SWGMap<String, Integer>) baseline4.get("abilities");
	}
	
	public void addAbility(String abilityName) {
		if (!getAbilities().containsKey(abilityName)) {
			getAbilities().put(abilityName, 1);
		}
	}
	
	public void removeAbility(String abilityName) {
		if (getAbilities().containsKey(abilityName)) {
			getAbilities().remove(abilityName);
		}
	}
	
	public short getLevel() {
		synchronized(objectMutex) {
			return (short) baseline6.get("level");
		}
	}
	
	public void setLevel(short level) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("level", level);
		}
		
		notifyObservers(buffer, true);
	}
	
	public String getCurrentAnimation() {
		synchronized(objectMutex) {
			return (String) baseline6.get("currentAnimation");
		}
	}
	
	public void setCurrentAnimation(String currentAnimation) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("currentAnimation", currentAnimation);
		}
		
		notifyObservers(buffer, true);
	}
	
	public String getMoodAnimation() {
		synchronized(objectMutex) {
			return (String) baseline6.get("moodAnimation");
		}
	}
	
	public void setMoodAnimation(String moodAnimation) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("moodAnimation", moodAnimation);
		}
		
		notifyObservers(buffer, true);
	}
	
	public long getWeaponId() {
		synchronized(objectMutex) {
			return (long) baseline6.get("weaponId");
		}
	}
	
	public void setWeaponId(long weaponId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("weaponId", weaponId);
		}
		
		notifyObservers(buffer, true);
	}
	
	public long getGroupId() {
		synchronized(objectMutex) {
			return (long) baseline6.get("groupId");
		}
	}
	
	public void setGroupId(long groupId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("groupId", groupId);
		}
		
		notifyObservers(buffer, true);
	}
	
	public GroupInviteInfo getGroupInviteInfo() {
		return (GroupInviteInfo) baseline6.get("groupInviteInfo");
	}
	
	public long getInviteSenderId() {
		synchronized(objectMutex) {
			return getGroupInviteInfo().getSenderId();
		}
	}
	
	public void setInviteSenderId(long inviteSenderId) {
		synchronized(objectMutex) {
			getGroupInviteInfo().setSender(inviteSenderId, getInviteSenderName());
		}
	}
	
	public String getInviteSenderName() {
		synchronized(objectMutex) {
			return getGroupInviteInfo().getSenderName();
		}
	}
	
	public void setInviteSenderName(String inviteSenderName) {
		synchronized(objectMutex) {
			getGroupInviteInfo().setSender(getInviteSenderId(), inviteSenderName);
		}
	}
	
	public long getInviteCounter() {
		return 0;
	}
	
	public void setInviteCounter(long inviteCounter) {

	}
	
	public void updateGroupInviteInfo() {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("groupInviteInfo", getGroupInviteInfo());
		}
		
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(buffer);
		}
	}
	
	public int getGuildId() {
		synchronized(objectMutex) {
			return (int) baseline6.get("guildId");
		}
	}
	
	public void setGuildId(int guildId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("guildId", guildId);
		}
		
		notifyObservers(buffer, true);
	}
	
	public long getTargetId() {
		synchronized(objectMutex) {
			return (long) baseline6.get("targetId");
		}
	}
	
	public void setTargetId(long targetId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("targetId", targetId);
		}
		
		notifyObservers(buffer, true);
	}
	
	public byte getMoodId() {
		synchronized(objectMutex) {
			return (byte) baseline6.get("moodId");
		}
	}
	
	public void setMoodId(byte moodId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("moodId", moodId);
		}
		
		notifyObservers(buffer, true);
	}
	
	public int getPerformanceCounter() {
		synchronized(objectMutex) {
			return (int) baseline6.get("performanceCounter");
		}
	}
	
	public void setPerformanceCounter(int performanceCounter) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("performanceCounter", performanceCounter);
		}
		
		notifyObservers(buffer, true);
	}
	
	public int getPerformanceId() {
		synchronized(objectMutex) {
			return (int) baseline6.get("performanceId");
		}
	}
	
	public void setPerformanceId(int performanceId) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("performanceId", performanceId);
		}
		
		notifyObservers(buffer, true);
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getHamList() {
		return (SWGList<Integer>) baseline6.get("hamList");
	}
	
	public int getHealth() {
		synchronized(objectMutex) {
			return getHamList().get(0);
		}
	}
	
	public void setHealth(int health) {
		if (health > getMaxHealth()) {
			health = getMaxHealth();
		}
		
		synchronized(objectMutex) {
			getHamList().set(0, health);
		}
	}
	
	public int getAction() {
		synchronized(objectMutex) {
			return getHamList().get(2);
		}
	}
	
	public void setAction(int action) {
		if (action > getMaxAction()) {
			action = getMaxAction();
		}
		
		synchronized(objectMutex) {
			getHamList().set(2, action);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getMaxHamList() {
		return (SWGList<Integer>) baseline6.get("maxHamList");
	}
	
	public int getMaxHealth() {
		synchronized(objectMutex) {
			return getMaxHamList().get(0);
		}
	}
	
	public void setMaxHealth(int maxHealth) {
		synchronized(objectMutex) {
			getMaxHamList().set(0, maxHealth);
		}
	}
	
	public int getMaxAction() {
		synchronized(objectMutex) {
			return getMaxHamList().get(2);
		}
	}
	
	public void setMaxAction(int maxAction) {
		synchronized(objectMutex) {
			getMaxHamList().set(2, maxAction);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Equipment> getEquipmentList() {
		return (SWGList<Equipment>) baseline6.get("equipmentList");
	}
	
	public void equipObject(SWGObject object) {
		if (object instanceof TangibleObject) {
			getEquipmentList().add(new Equipment(object));
		}
	}
	
	public void unequipObject(SWGObject object) {
		if (object instanceof TangibleObject) {
			for (Equipment equipment : getEquipmentList()) {
				if (equipment.getObjectId() == object.getObjectId()) {
					getEquipmentList().remove(equipment);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<Integer, Buff> getBuffList() {
		return (SWGMap<Integer, Buff>) baseline6.get("buffList");
	}
	
	public void addBuff(Buff buff) {
		synchronized(objectMutex) {
			PlayerObject player = (PlayerObject) this.getSlottedObject("ghost");
			buff.setTotalPlayTime((int) (player.getTotalPlayTime() + (System.currentTimeMillis() - player.getLastPlayTimeUpdate()) / 1000));
		}
		
		getBuffList().put(CRC.StringtoCRC(buff.getBuffName()), buff);
		
		synchronized(objectMutex) {
			buff.setStartTime();
		}
	}
	
	public void removeBuff(Buff buff) {
		getBuffList().remove(CRC.StringtoCRC(buff.getBuffName()));
	}
	
	public Buff getBuffByName(String buffName) {
		for (Entry<Integer, Buff> entry : getBuffList().entrySet()) {
			if (entry.getKey().equals(CRC.StringtoCRC(buffName))) {
				return entry.getValue();
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Equipment> getAppearanceEquipmentList() {
		return (SWGList<Equipment>) baseline6.get("appearanceEquipmentList");
	}
	
	public void equipAppearance(SWGObject object) {
		if (object instanceof TangibleObject) {
			getAppearanceEquipmentList().add(new Equipment(object));
		}
	}
	
	public void unequipAppearance(SWGObject object) {
		if (object instanceof TangibleObject) {
			for (Equipment equipment : getAppearanceEquipmentList()) {
				if (equipment.getObjectId() == object.getObjectId()) {
					getAppearanceEquipmentList().remove(equipment);
				}
			}
		}
	}
	
	public List<Long> getDuelList() {
		return duelList;
	}
	
	public boolean isInDuelList(long objectId) {
		if (duelList.contains(objectId)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isInDuelList(SWGObject object) {
		return isInDuelList(object.getObjectId());
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, notifySelf);
	}
	
	@Override
	public CreatureMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new CreatureMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public void sendBaselines(Client destination) {	
		if (destination == null || destination.getSession() == null) {
			System.out.println("NULL session");
			return;
		}
		
		System.out.println(StringUtilities.bytesToHex(baseline3.getBaseline().array()));
		destination.getSession().write(baseline3.getBaseline());
		//destination.getSession().write(baseline6.getBaseline());
		if (destination == getClient()) {
			//destination.getSession().write(baseline1.getBaseline());
			//destination.getSession().write(baseline4.getBaseline());
		}
		//destination.getSession().write(baseline8().getBaseline());
		//destination.getSession().write(baseline9().getBaseline());
		 
		UpdatePostureMessage upm = new UpdatePostureMessage(getObjectID(), (byte) 0);
		//destination.getSession().write(upm.serialize());
		
		if (destination != getClient()) {
			UpdatePVPStatusMessage upvpm = new UpdatePVPStatusMessage(getObjectID());
			
			int factionStatus = getFactionStatus();
			String faction = getFaction();
			
			if (factionStatus == 1 && faction == "imperial") {
				upvpm.setFaction(UpdatePVPStatusMessage.factionCRC.Imperial);
				upvpm.setStatus(16);
			}
			
			if (factionStatus == 1 && faction == "rebel") {
				upvpm.setFaction(UpdatePVPStatusMessage.factionCRC.Rebel);
				upvpm.setStatus(16);
			}
			
			if (factionStatus == 2 && faction == "imperial") {
				upvpm.setFaction(UpdatePVPStatusMessage.factionCRC.Imperial);
				upvpm.setStatus(55);
			}
			
			if (factionStatus == 2 && faction == "rebel") {
				upvpm.setFaction(UpdatePVPStatusMessage.factionCRC.Rebel);
				upvpm.setStatus(55);
			} 
			
			if (factionStatus == 0 && faction == "neutral") {
				upvpm.setFaction(UpdatePVPStatusMessage.factionCRC.Neutral);
				upvpm.setStatus(16);
			} else {
				upvpm.setFaction(UpdatePVPStatusMessage.factionCRC.Neutral);
				upvpm.setStatus(16);
			}
			
			destination.getSession().write(upvpm.serialize());
		}
	}
	
	public void sendSystemMessage(String message, byte displayType) {
		if (getClient() != null && getClient().getSession() != null) {
			ChatSystemMessage systemMsg = new ChatSystemMessage(message, displayType);
			getClient().getSession().write(systemMsg.serialize());
		}
	}
	
	public Transaction getTransaction() {
		return txn;
	}
	
	public void createTransaction(Environment env) {
		txn = env.beginTransaction(null, null);
	}
	
}
