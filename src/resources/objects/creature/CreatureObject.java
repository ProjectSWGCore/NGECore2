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
package resources.objects.creature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.LongAdder;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.chat.ChatSystemMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.PlayMusicMessage;
import protocol.swg.UpdatePVPStatusMessage;
import protocol.swg.UpdatePostureMessage;
import protocol.swg.objectControllerObjects.Animation;
import protocol.swg.objectControllerObjects.Posture;
import protocol.swg.objectControllerObjects.StartTask;
import engine.clients.Client;
import engine.resources.objects.Baseline;
import resources.objects.SWGList;
import resources.objects.SWGMap;
import resources.objects.SWGSet;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.skills.SkillMod;
import resources.buffs.Buff;
import resources.buffs.DamageOverTime;
import resources.common.OutOfBand;
import resources.datatables.Difficulty;
import resources.equipment.Equipment;
import resources.group.GroupInviteInfo;
import services.command.BaseSWGCommand;
import engine.resources.common.CRC;
import engine.resources.objects.IPersistent;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class CreatureObject extends TangibleObject implements IPersistent {
	
	private static final long serialVersionUID = 1L;
	
	private transient CreatureMessageBuilder messageBuilder;
	
	private transient List<CreatureObject> duelList = Collections.synchronizedList(new ArrayList<CreatureObject>());
	private transient ScheduledFuture<?> incapTask;
	private transient ScheduledFuture<?> entertainerExperience;
	private transient ScheduledFuture<?> inspirationTick;
	private transient CreatureObject performanceWatchee;
	private transient CreatureObject performanceListenee;
	private transient List<CreatureObject> performanceAudience = new ArrayList<CreatureObject>();
	private transient ScheduledFuture<?> spectatorTask;
	private transient int flourishCount = 0;
	private transient boolean performingEffect;
	private transient boolean performingFlourish;
	private transient TangibleObject conversingNpc;
	private transient ConcurrentHashMap<String, Long> cooldowns = new ConcurrentHashMap<String, Long>();
	private transient long tefTime = 0;
	private transient SWGObject useTarget;
	private transient boolean isConstructing = false;
	
	public CreatureObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
		setMaximumCondition(1000);
		setStaticObject(true);
		getAttribs().add(1000);
		getAttribs().add(0);
		getAttribs().add(300);
		getAttribs().add(0);
		getAttribs().add(300);
		getAttribs().add(0);
		getMaxAttribs().add(1000);
		getMaxAttribs().add(0);
		getMaxAttribs().add(300);
		getMaxAttribs().add(0);
		getMaxAttribs().add(300);
		getMaxAttribs().add(0);
	}
	
	public CreatureObject() {
		super();
	}
	
	public void initAfterDBLoad() {
		super.initAfterDBLoad();
		System.out.println("Name: " + getCustomName());
		System.out.println("  Cash Credits: " + getCashCredits());
		System.out.println("  Bank Credits: " + getBankCredits());
	}
	
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		baseline.put("locomotion", (byte) 0);
		baseline.put("performanceType", "");
		baseline.put("coverCharge", 0);
		baseline.put("dotList", new ArrayList<DamageOverTime>());
		baseline.put("fatigue", 0);
		return baseline;
	}
	
	public Baseline getBaseline1() {
		Baseline baseline = super.getBaseline1();
		baseline.put("bankCredits", 0);
		baseline.put("cashCredits", 0);
		baseline.put("baseAttributes", new SWGList<Integer>(this, 1, 2, false));
		baseline.put("skills", new SWGSet<String>(this, 1, 3, false));
		return baseline;
	}
	
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("posture", (byte) 0);
		baseline.put("factionRank", (byte) 0);
		baseline.put("ownerId", (long) 0);
		baseline.put("height", (float) 1);
		baseline.put("battleFatigue", 0);
		baseline.put("stateBitmask", (long) 0);
		return baseline;
	}
	
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
		baseline.put("walkSpeed", (float) 1.549);
		baseline.put("waterModPercent", (float) 0.75);
		baseline.put("missionCriticalObjects", new SWGMap<Long, Long>(this, 4, 13, false));
		baseline.put("abilities", new SWGMap<String, Integer>(this, 4, 14, true));
		baseline.put("displayXp", 0);
		return baseline;
	}
	
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("level", (short) -1);
		baseline.put("grantedHealth", 0);
		baseline.put("currentAnimation", "");
		baseline.put("moodAnimation", "neutral");
		baseline.put("weaponId", (long) 0);
		baseline.put("groupId", (long) 0);
		baseline.put("groupInviteInfo", new GroupInviteInfo((long) 0, ""));
		baseline.put("guildId", 0);
		baseline.put("lookAtTarget", (long) 0);
		baseline.put("intendedTarget", (long) 0);
		baseline.put("moodId", (byte) 0);
		baseline.put("performanceCounter", 0);
		baseline.put("performanceId", 0);
		baseline.put("attribs", new SWGList<Integer>(this, 6, 21, false));
		baseline.put("maxAttribs", new SWGList<Integer>(this, 6, 22, false));
		baseline.put("equipmentList", new SWGList<Equipment>(this, 6, 23, false));
		baseline.put("appearance", "");
		baseline.put("visible", true);
		baseline.put("buffList", new SWGMap<Integer, Buff>(this, 6, 26, true));
		baseline.put("performing", false);
		baseline.put("difficulty", Difficulty.NORMAL);
		baseline.put("hologramColor", -1);
		baseline.put("visibleOnRadar", true);
		baseline.put("isPet", false);
		baseline.put("32", (byte) 0);
		baseline.put("appearanceEquipmentList", new SWGList<Equipment>(this, 6, 31, false));
		baseline.put("34", (long) 0);
		return baseline;
	}
	
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	public boolean isPlayer() {
		synchronized(objectMutex) {
			return (getSlottedObject("ghost") != null);
		}
	}
	
	public PlayerObject getPlayerObject() {
		return (PlayerObject) getSlottedObject("ghost");
	}
	
	public int getBankCredits() {
		return (int) getBaseline(1).get("bankCredits");
	}
	
	public void setBankCredits(int bankCredits) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(1).set("bankCredits", bankCredits));
		}
	}
	
	public void addBankCredits(int bankCredits) {
		synchronized(objectMutex) {
			setBankCredits(getBankCredits() + bankCredits);
		}
	}
	
	public void deductBankCredits(int bankCredits) {
		synchronized(objectMutex) {
			setBankCredits(getBankCredits() - bankCredits);
		}
	}
	
	public int getCashCredits() {
		return (int) getBaseline(1).get("cashCredits");
	}
	
	public void setCashCredits(int cashCredits) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(1).set("cashCredits", cashCredits));
		}
	}
	
	public void addCashCredits(int cashCredits) {
		synchronized(objectMutex) {
			setCashCredits(getCashCredits() + cashCredits);
		}
	}
	
	public void deductCashCredits(int cashCredits) {
		synchronized(objectMutex) {
			setCashCredits(getCashCredits() - cashCredits);
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getBaseAttributes() {
		return (SWGList<Integer>) getBaseline(1).get("baseAttributes");
	}
	
	@SuppressWarnings("unchecked")
	public SWGSet<String> getSkills() {
		return (SWGSet<String>) getBaseline(1).get("skills");
	}
	
	public boolean hasSkill(String name) {
		return getSkills().contains(name);
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
	
	public int getIncapacityTimer() {
		return (int) getBaseline(3).get("uses");
	}
	
	public void setIncapacityTimer(int incapacityTimer) {
		notifyClients(getBaseline(3).set("uses", incapacityTimer), true);
	}
	
	public byte getPosture() {
		return (byte) getBaseline(3).get("posture");
	}
	
	public void setPosture(byte posture) {
		synchronized(objectMutex) {
			switch (posture) {
				case resources.datatables.Posture.Invalid:
					setLocomotion(resources.datatables.Locomotion.Invalid);
					break;
				case resources.datatables.Posture.Upright:
					setLocomotion(resources.datatables.Locomotion.Standing);
					break;
				case resources.datatables.Posture.Crouched:
					setLocomotion(resources.datatables.Locomotion.Kneeling);
					break;
				case resources.datatables.Posture.Prone:
					setLocomotion(resources.datatables.Locomotion.Prone);
					break;
				case resources.datatables.Posture.Sneaking:
					setLocomotion(resources.datatables.Locomotion.Sneaking);
					break;
				case resources.datatables.Posture.Blocking:
					setLocomotion(resources.datatables.Locomotion.Blocking);
					break;
				case resources.datatables.Posture.Climbing:
					setLocomotion(resources.datatables.Locomotion.ClimbingStationary);
					break;
				case resources.datatables.Posture.Flying:
					setLocomotion(resources.datatables.Locomotion.Flying);
					break;
				case resources.datatables.Posture.LyingDown:
					setLocomotion(resources.datatables.Locomotion.LyingDown);
					break;
				case resources.datatables.Posture.Sitting:
					setLocomotion(resources.datatables.Locomotion.Sitting);
					break;
				case resources.datatables.Posture.SkillAnimating:
					setLocomotion(resources.datatables.Locomotion.SkillAnimating);
					break;
				case resources.datatables.Posture.DrivingVehicle:
					setLocomotion(resources.datatables.Locomotion.DrivingVehicle);
					break;
				case resources.datatables.Posture.RidingCreature:
					setLocomotion(resources.datatables.Locomotion.RidingCreature);
					break;
				case resources.datatables.Posture.KnockedDown:
					setLocomotion(resources.datatables.Locomotion.KnockedDown);
					break;
				case resources.datatables.Posture.Incapacitated:
					setLocomotion(resources.datatables.Locomotion.Incapacitated);
					break;
				case resources.datatables.Posture.Dead:
					setLocomotion(resources.datatables.Locomotion.Dead);
					break;
			}
		}
		
		if (getPosture() == resources.datatables.Posture.SkillAnimating) {
			stopPerformance();
		}
		
		if (getPosture() == posture) {
				return;
		}
		
		notifyObservers(getBaseline(3).set("posture", posture), true);
		notifyObservers(new ObjControllerMessage(0x1B, new Posture(getObjectID(), posture)), true);
	}
	
	public byte getLocomotion() {
		return (byte) otherVariables.get("locomotion");
	}
	
	public void setLocomotion(byte locomotion) {
		otherVariables.set("locomotion", locomotion);
	}
	
	public byte getFactionRank() {
		return (byte) getBaseline(3).get("factionRank");
	}
	
	public void setFactionRank(byte factionRank) {
		notifyObservers(getBaseline(3).set("factionRank", factionRank), true);
	}
	
	public long getOwnerId() {
		return (long) getBaseline(3).get("ownerId");
	}
	
	public void setOwnerId(long ownerId) {
		setStringAttribute("owner", NGECore.getInstance().objectService.getObject(ownerId).getCustomName());
		notifyObservers(getBaseline(3).set("ownerId", ownerId), true);
	}
	
	public float getHeight() {
		return (float) getBaseline(3).get("height");
	}
	
	public void setHeight(float height) {
		height = (((height < 0.7) || (height > 1.5)) ? 1 : height);
		notifyObservers(getBaseline(3).set("height", height), true);
	}
	
	public int getBattleFatigue() {
		return (int) getBaseline(3).get("battleFatigue");
	}
	
	public void setBattleFatigue(int battleFatigue) {
		notifyObservers(getBaseline(3).set("battleFatigue", battleFatigue), true);
	}
	
	public long getStateBitmask() {
		return (long) getBaseline(3).get("stateBitmask");
	}
	
	public void setStateBitmask(long stateBitmask) {
		notifyObservers(getBaseline(3).set("stateBitmask", stateBitmask), true);
	}
	
	public void setState(long state, boolean add) {
		if (state != 0) {
			if (add) {
				state = (getStateBitmask() | state);
			} else {
				state = (getStateBitmask() & ~state);
			}
		}
		
		setStateBitmask(state);
	}
	
	public boolean getState(long state) {
		return ((getStateBitmask() & state) == state);
	}
	
	public float getAccelerationMultiplierBase() {
		return (float) getBaseline(4).get("accelerationMultiplierBase");
	}
	
	public void setAccelerationMultiplierBase(float accelerationMultiplierBase) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("accelerationMultiplierBase", accelerationMultiplierBase));
		}
	}
	
	public float getAccelerationMultiplierMod() {
		return (float) getBaseline(4).get("accelerationMultiplierMod");
	}
	
	public void setAccelerationMultiplierMod(float accelerationMultiplierMod) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("accelerationMultiplierMod", accelerationMultiplierMod));
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getHamEncumberanceList() {
		return (SWGList<Integer>) getBaseline(4).get("hamEncumberanceList");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, SkillMod> getSkillMods() {
		return (SWGMap<String, SkillMod>) getBaseline(4).get("skillMods");
	}
	
	public SkillMod getSkillMod(String name) {
		synchronized(objectMutex) {
			if (getSkillMods().containsKey(name) && getSkillMods().get(name) != null) {
				return getSkillMods().get(name);
			}
			
			return null;
		}
	}
	
	public int getSkillModBase(String name) {
		SkillMod skillMod = getSkillMod(name);
		return ((skillMod == null) ? 0 : skillMod.getBase());
	}
	
	public int getSkillModModifier(String name) {
		SkillMod skillMod = getSkillMod(name);
		return ((skillMod == null) ? 0 : skillMod.getModifier());
	}
	
	public float getSkillModValue(String name, int divisor, boolean percent) {
		SkillMod skillMod = getSkillMod(name);
		return ((skillMod == null) ? 0.0f : skillMod.getValue(divisor, percent));
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
	
	public int getLuck() {
		return (getSkillModBase("luck") + getSkillModBase("luck_modified"));
	}
	
	public int getPrecision() {
		return (getSkillModBase("precision") + getSkillModBase("precision_modified"));
	}
	
	public int getStrength() {
		return (getSkillModBase("strength") + getSkillModBase("strength_modified"));
	}
	
	public int getConstitution() {
		return (getSkillModBase("constitution") + getSkillModBase("constitution_modified"));
	}
	
	public int getStamina() {
		return (getSkillModBase("stamina") + getSkillModBase("stamina_modified"));
	}
	
	public int getAgility() {
		return (getSkillModBase("agility") + getSkillModBase("agility_modified"));
	}
	
	public float getSpeedMultiplierBase() {
		return (float) getBaseline(4).get("speedMultiplierBase");
	}
	
	public void setSpeedMultiplierBase(float speedMultiplierBase) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("speedMultiplierBase", speedMultiplierBase));
		}
	}
	
	public float getSpeedMultiplierMod() {
		return (float) getBaseline(4).get("speedMultiplierMod");
	}
	
	public void setSpeedMultiplierMod(float speedMultiplierMod) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("speedMultiplierMod", speedMultiplierMod));
		}
	}
	
	public long getListenToId() {
		return (long) getBaseline(4).get("listenToId");
	}
	
	public void setListenToId(long listenToId) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("listenToId", listenToId));
		}
	}
	
	public float getRunSpeed() {
		return (float) getBaseline(4).get("runSpeed");
	}
	
	public void setRunSpeed(float runSpeed) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("runSpeed", runSpeed));
		}
	}
	
	public float getSlopeModAngle() {
		return (float) getBaseline(4).get("slopeModAngle");
	}
	
	public void setSlopeModAngle(float slopeModAngle) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("slopeModAngle", slopeModAngle));
		}
	}
	
	public float getSlopeModPercent() {
		return (float) getBaseline(4).get("slopeModPercent");
	}
	
	public void setSlopeModPercent(float slopeModPercent) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("slopeModPercent", slopeModPercent));
		}
	}
	
	public float getTurnRadius() {
		return (float) getBaseline(4).get("turnRadius");
	}
	
	public void setTurnRadius(float turnRadius) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("turnRadius", turnRadius));
		}
	}
	
	public float getWalkSpeed() {
		return (float) getBaseline(4).get("walkSpeed");
	}
	
	public void setWalkSpeed(float walkSpeed) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("walkSpeed", walkSpeed));
		}
	}
	
	public float getWaterModPercent() {
		return (float) getBaseline(4).get("waterModPercent");
	}
	
	public void setWaterModPercent(float waterModPercent) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("waterModPercent", waterModPercent));
		}
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<Long, Long> getMissionCriticalObjects() {
		return (SWGMap<Long, Long>) getBaseline(4).get("missionCriticalObjects");
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<String, Integer> getAbilities() {
		return (SWGMap<String, Integer>) getBaseline(4).get("abilities");
	}
	
	public boolean hasAbility(String name) {
		for (String ability : getAbilities().keySet()) {
			if (ability.equals(name)) {
				return true;
			}
		}
		
		return false;
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
	
	public int getDisplayXp() {
		return (int) getBaseline(4).get("displayXp");
	}
	
	public void setDisplayXp(int displayXp) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(4).set("displayXp", displayXp));
		}
	}
	
	public short getLevel() {
		return (short) getBaseline(6).get("level");
	}
	
	public void setLevel(short level) {
		notifyObservers(getBaseline(6).set("level", level), true);
	}
	
	public int getGrantedHealth() {
		return (int) getBaseline(6).get("grantedHealth");
	}
	
	public void setGrantedHealth(int grantedHealth) {
		notifyObservers(getBaseline(6).set("grantedHealth", grantedHealth), true);
	}
	
	public String getCurrentAnimation() {
		return (String) getBaseline(6).get("currentAnimation");
	}
	
	public void setCurrentAnimation(String currentAnimation) {
		notifyObservers(new ObjControllerMessage(0x1B, new Animation(getObjectId(), currentAnimation)), true);
		notifyObservers(getBaseline(6).set("currentAnimation", currentAnimation), true);
	}
	
	public void doSkillAnimation(String skillAnimation) {
		notifyObservers(new ObjControllerMessage(0x1B, new Animation(getObjectId(), skillAnimation)), true);
	}
	
	public String getMoodAnimation() {
		return (String) getBaseline(6).get("moodAnimation");
	}
	
	public void setMoodAnimation(String moodAnimation) {
		notifyObservers(getBaseline(6).set("moodAnimation", moodAnimation), true);
	}
	
	public long getWeaponId() {
		return (long) getBaseline(6).get("weaponId");
	}
	
	public void setWeaponId(long weaponId) {
		notifyObservers(getBaseline(6).set("weaponId", weaponId), true);
	}
	
	public long getGroupId() {
		return (long) getBaseline(6).get("groupId");
	}
	
	public void setGroupId(long groupId) {
		notifyObservers(getBaseline(6).set("groupId", groupId), true);
	}
	
	public GroupInviteInfo getGroupInviteInfo() {
		return (GroupInviteInfo) getBaseline(6).get("groupInviteInfo");
	}
	
	public long getInviteSenderId() {
		return getGroupInviteInfo().getSenderId();
	}
	
	public void setInviteSenderId(long inviteSenderId) {
		getGroupInviteInfo().setSender(inviteSenderId, getInviteSenderName());
	}
	
	public String getInviteSenderName() {
		return getGroupInviteInfo().getSenderName();
	}
	
	public void setInviteSenderName(String inviteSenderName) {
		getGroupInviteInfo().setSender(getInviteSenderId(), inviteSenderName);
	}
	
	public long getInviteCounter() {
		return 0;
	}
	
	public void setInviteCounter(long inviteCounter) {
		
	}
	
	public void updateGroupInviteInfo() {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(getBaseline(6).set("groupInviteInfo", getGroupInviteInfo()));
		}
	}
	
	public int getGuildId() {
		return (int) getBaseline(6).get("guildId");
	}
	
	public void setGuildId(int guildId) {
		notifyObservers(getBaseline(6).set("guildId", guildId), true);
	}
	
	public long getLookAtTarget() {
		return (long) getBaseline(6).get("lookAtTarget");
	}
	
	public void setLookAtTarget(long lookAtTarget) {
		notifyObservers(getBaseline(6).set("lookAtTarget", lookAtTarget), true);
	}
	
	public long getIntendedTarget() {
		return (long) getBaseline(6).get("intendedTarget");
	}
	
	public void setIntendedTarget(long intendedTarget) {
		notifyObservers(getBaseline(6).set("intendedTarget", intendedTarget), true);
	}
	
	public SWGObject getUseTarget() {
		synchronized(objectMutex) {
			return useTarget;
		}
	}
	
	public void setUseTarget(SWGObject useTarget) {
		synchronized(objectMutex) {
			this.useTarget = useTarget;
		}
	}
	
	public byte getMoodId() {
		return (byte) getBaseline(6).get("moodId");
	}
	
	public void setMoodId(byte moodId) {
		notifyObservers(getBaseline(6).set("moodId", moodId), true);
	}
	
	public int getPerformanceCounter() {
		return (int) getBaseline(6).get("performanceCounter");
	}
	
	public void setPerformanceCounter(int performanceCounter) {
		notifyObservers(getBaseline(6).set("performanceCounter", performanceCounter), true);
	}
	
	public int getPerformanceId() {
		return (int) getBaseline(6).get("performanceId");
	}
	
	public void setPerformanceId(int performanceId) {
		notifyObservers(getBaseline(6).set("performanceId", performanceId), true);
	}
	
	public boolean getGroupDance() {
		return ((getGroupId() == 0) ? false : true);
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getAttribs() {
		return (SWGList<Integer>) getBaseline(6).get("attribs");
	}
	
	public void resetAttribs() {
		// TODO: Fix this, if it's really needed
	}
	
	public int getHealth() {
		return getAttribs().get(0);
	}
	
	public void setHealth(int health) {
		synchronized(objectMutex) {
			if (health > getMaxHealth()) {
				health = getMaxHealth();
			}
			
			if (health == getHealth()) {
				return;
			}
			
			if (getPosture() == 13) {
				stopIncapTask();
				setIncapTask(null);
			}
		}
		
		getAttribs().set(0, health);
		
		synchronized(objectMutex) {
			if (getPosture() == 13) {
				setPosture((byte) 0);
				setTurnRadius(1);
				setSpeedMultiplierBase(1);
			}
		}
	}
	
	public int getAction() {
		return getAttribs().get(2);
	}
	
	public void setAction(int action) {
		synchronized(objectMutex) {
			if (action > getMaxAction()) {
				action = getMaxAction();
			}
			
			if (action == getAction()) {
				return;
			}
		}
		
		getAttribs().set(2, action);
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getMaxAttribs() {
		return (SWGList<Integer>) getBaseline(6).get("maxAttribs");
	}
	
	public int getMaxHealth() {
		return getMaxAttribs().get(0);
	}
	
	public void setMaxHealth(int maxHealth) {
		synchronized(objectMutex) {
			if (maxHealth == getMaxHealth()) {
				return;
			}
		}
		
		if (maxHealth < getHealth()) {
			setHealth(maxHealth);
		}
		
		getMaxAttribs().set(0, maxHealth);
	}
	
	public int getMaxAction() {
		return getMaxAttribs().get(2);
	}
	
	public void setMaxAction(int maxAction) {
		synchronized(objectMutex) {
			if (maxAction == getMaxAction()) {
				return;
			}
		}
		
		if (maxAction < getAction()) {
			setAction(maxAction);
		}
		
		getMaxAttribs().set(2, maxAction);
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Equipment> getEquipmentList() {
		return (SWGList<Equipment>) getBaseline(6).get("equipmentList");
	}
	
	public void addObjectToEquipList(SWGObject object) {
		if (object instanceof TangibleObject) {
			getEquipmentList().add(new Equipment(object));
		}
	}
	
	public void removeObjectFromEquipList(SWGObject object) {
		if (object instanceof TangibleObject) {
			for (Equipment equipment : getEquipmentList()) {
				if (equipment.getObjectId() == object.getObjectId()) {
					getEquipmentList().remove(equipment);
				}
			}
		}
	}
	
	public String getCostume() {
		return (String) getBaseline(6).get("costume");
	}
	
	public void setCostume(String costume) {
		notifyObservers(getBaseline(6).set("costume", costume), true);
	}
	
	public boolean isVisible() {
		return (boolean) getBaseline(6).get("visible");
	}
	
	public void setVisible(boolean visible) {
		notifyObservers(getBaseline(6).set("visible", visible), true);
	}
	
	public boolean isCloaked() {
		return !isVisible();
	}
	
	public void setCloaked(boolean cloaked) {
		setVisible(!cloaked);
	}
	
	@SuppressWarnings("unchecked")
	public SWGMap<Integer, Buff> getBuffList() {
		return (SWGMap<Integer, Buff>) getBaseline(6).get("buffList");
	}
	
	public Buff getBuffByName(String buffName) {
		for (Entry<Integer, Buff> entry : getBuffList().entrySet()) {
			if (entry.getKey().equals(CRC.StringtoCRC(buffName.toLowerCase()))) {
				return entry.getValue();
			}
		}
		
		return null;
	}
	
	public void addBuff(Buff buff) {
		PlayerObject player = (PlayerObject) getSlottedObject("ghost");
		buff.setTotalPlayTime((player == null) ? 0 : (int) (player.getTotalPlayTime() + (System.currentTimeMillis() - player.getLastPlayTimeUpdate()) / 1000));
		buff.setStartTime();
		getBuffList().put(CRC.StringtoCRC(buff.getBuffName().toLowerCase()), buff);
	}
	
	public void removeBuff(Buff buff) {
		getBuffList().remove(CRC.StringtoCRC(buff.getBuffName().toLowerCase()));
	}
	
	public void updateBuff(Buff buff) {
		buff.updateRemovalTask();
		getBuffList().put(CRC.StringtoCRC(buff.getBuffName().toLowerCase()), buff);
	}	
	
	public void updateAllBuffs() {
		synchronized(objectMutex) {
			for (Buff buff : getBuffList().values()) {
				updateBuff(buff);
			}
		}
	}
	
	public boolean hasBuff(String buffName) {
		return getBuffByName(buffName) != null;
	}
	
	public void setPerforming(boolean performing) {
		notifyObservers(getBaseline(6).set("performing", performing), true);
	}
	
	public boolean isPerforming() {
		return (boolean) getBaseline(6).get("performing");
	}
	
	public String getPerformanceType() {
		return (((boolean) otherVariables.get("performanceType")) ? "dance" : "music");
	}
	
	public void setPerformanceType(String performanceType) {
		otherVariables.set("performanceType", ((performanceType.equals("dance")) ? true : false));
	}
	
	public void setPerformanceType(boolean performanceType) {
		otherVariables.set("performanceType", performanceType);
	}
	
	public ScheduledFuture<?> getEntertainerExperience() {
		return entertainerExperience;
	}
	
	public void setEntertainerExperience(ScheduledFuture<?> entertainerExperience) {
		this.entertainerExperience = entertainerExperience;
	}
	
	public ScheduledFuture<?> getInspirationTick() {
		return inspirationTick;
	}
	
	public void setInspirationTick(ScheduledFuture<?> inspirationTick) {
		this.inspirationTick = inspirationTick;
	}
	
	public void addAudience(CreatureObject audienceMember) {
		synchronized(objectMutex) {
			if (performanceAudience == null) {
				performanceAudience = new ArrayList<CreatureObject>();
			}
			
			performanceAudience.add(audienceMember);
		}
	}
	
	public void removeAudience(CreatureObject audienceMember) {
		synchronized(objectMutex) {
			if (performanceAudience == null) {
				return;
			}
			
			performanceAudience.remove(audienceMember);
		}
	}
	
	public CreatureObject getPerformanceWatchee() {
		synchronized(objectMutex) {
			return performanceWatchee;
		}
	}
	
	public void setPerformanceWatchee(CreatureObject performanceWatchee) {
		synchronized(objectMutex) {
			this.performanceWatchee = performanceWatchee;
		}
		
		setListenToId((performanceWatchee == null) ? 0L : performanceWatchee.getObjectID());
	}
	
	public CreatureObject getPerformanceListenee() {
		synchronized(objectMutex) {
			return performanceListenee;
		}
	}
	
	public void setPerformanceListenee(CreatureObject performanceListenee) {
		synchronized(objectMutex) {
			this.performanceListenee = performanceListenee;
		}
		
		setListenToId((performanceListenee == null) ? 0L : performanceListenee.getObjectID());
	}
	
	public int getFlourishCount() {
		synchronized(objectMutex) {
			return this.flourishCount;
		}
	}
	
	public void setFlourishCount(int flourishCount) {
		synchronized(objectMutex) {
			this.flourishCount = flourishCount;
		}
	}
	
	public void incFlourishCount() {
		synchronized(objectMutex) {
			this.flourishCount++;
		}
	}
	
	public byte getDifficulty() {
		return (byte) getBaseline(6).get("difficulty");
	}
	
	public void setDifficulty(byte difficulty) {
		notifyObservers(getBaseline(6).set("difficulty", difficulty), true);
	}
	
	public int getHologramColor() {
		return (int) getBaseline(6).get("hologramColor");
	}
	
	public void setHologramColor(int hologramColor) {
		getBaseline(6).set("hologramColor", hologramColor);
	}
	
	public boolean isHologram() {
		return !(getHologramColor() == -1);
	}
	
	public void setHologram(boolean hologram) {
		setHologramColor((hologram) ? 0 : -1);
	}
	
	public boolean visibleOnRadar() {
		return (boolean) getBaseline(6).get("visibleOnRadar");
	}
	
	public void setVisibleOnRadar(boolean visibleOnRadar) {
		notifyObservers(getBaseline(6).set("visibleOnRadar", visibleOnRadar), true);
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Equipment> getAppearanceEquipmentList() {
		return (SWGList<Equipment>) getBaseline(6).get("appearanceEquipmentList");
	}
	
	public void addObjectToAppearanceEquipList(SWGObject object) {
		if (object instanceof TangibleObject) {
			getAppearanceEquipmentList().add(new Equipment(object));
		}
	}
	
	public void removeObjectFromAppearanceEquipList(SWGObject object) {
		if (object instanceof TangibleObject) {
			for (Equipment equipment : getAppearanceEquipmentList()) {
				if (equipment.getObjectId() == object.getObjectId()) {
					getAppearanceEquipmentList().remove(equipment);
				}
			}
		}
	}
	
	public List<CreatureObject> getDuelList() {
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
	
	@SuppressWarnings("unchecked")
	public List<DamageOverTime> getDotList() {
		return ((List<DamageOverTime>) otherVariables.get("dotList"));
	}
	
	public DamageOverTime getDotByBuff(Buff buff) {
		List<DamageOverTime> dots = new ArrayList<DamageOverTime>(getDotList());
		
		for (DamageOverTime dot : dots) {
			if (dot.getBuff() == buff) {
				return dot;
			}
		}
		
		return null;
	}
	
	public DamageOverTime getDotByName(String dotName) {
		List<DamageOverTime> dots = new ArrayList<DamageOverTime>(getDotList());
		
		for (DamageOverTime dot : dots) {
			if (dot.getCommandName().equalsIgnoreCase(dotName)) {
				return dot;
			}
		}
		
		return null;
	}
	
	public void setDotList(List<DamageOverTime> dotList) {
		otherVariables.set("dotList", dotList);
	}
	
	public void addDot(DamageOverTime dot) {
		getDotList().add(dot);
	}
	
	public void removeDot(DamageOverTime dot) {
		getDotList().remove(dot);
	}
	
	public ScheduledFuture<?> getIncapTask() {
		return incapTask;
	}
	
	public void setIncapTask(ScheduledFuture<?> incapTask) {
		this.incapTask = incapTask;
	}
	
	public void stopIncapTask() {
		if (incapTask != null) {
			incapTask.cancel(true);
		}
	}
	
	public void addSpectator(CreatureObject audienceMember) {
		synchronized(objectMutex) {
			performanceAudience.add(audienceMember);
		}
	}
	
	public void removeSpectator(CreatureObject audienceMember) {
		synchronized(objectMutex) {
			if (performanceAudience == null) {
				return;
			}
			
			if (audienceMember.getInspirationTick() != null) {
				audienceMember.getInspirationTick().cancel(true);
			}
			
			if (performanceAudience.contains(audienceMember)) {
				performanceAudience.remove(audienceMember);
			}
		}
	}
	
	public ScheduledFuture<?> getSpectatorTask() {
		synchronized(objectMutex) {
			return spectatorTask;
		}
	}
	
	public void setSpectatorTask(ScheduledFuture<?> spectatorTask) {
		synchronized(objectMutex) {
			this.spectatorTask = spectatorTask;
		}
	}
	
	public boolean isPerformingEffect() {
		synchronized(objectMutex) {
			return performingEffect;
		}
	}
	
	public void setPerformingEffect(boolean hasEffect) {
		synchronized(objectMutex) {
			this.performingEffect = hasEffect;
		}
	}
	
	public int getCoverCharge() {
		return (int) otherVariables.get("coverCharge");
	}
	
	public void setCoverCharge(int coverCharge) {
		otherVariables.set("coverCharge", coverCharge);
	}
	
	public TangibleObject getConversingNpc() {
		synchronized(objectMutex) {
			return conversingNpc;
		}
	}

	public void setConversingNpc(TangibleObject conversingNpc) {
		synchronized(objectMutex) {
			this.conversingNpc = conversingNpc;
		}
	}
	
	public long getTefTime() {
		synchronized(objectMutex) {
			return (((tefTime - System.currentTimeMillis()) > 0) ? (tefTime - System.currentTimeMillis()) : 0);
		}
	}
	
	public void setTefTime(long tefTime) {
		synchronized(objectMutex) {
			this.tefTime = tefTime + System.currentTimeMillis();
		}
	}
	
	public void addCooldown(String cooldownGroup, float cooldownTime) {
		if (cooldowns.containsKey(cooldownGroup)) {
			cooldowns.remove(cooldownGroup);
		}
		
		long duration = System.currentTimeMillis() + ((long) (cooldownTime * 1000F)); 
		
		cooldowns.put(cooldownGroup, duration);
	}
	
	public boolean hasCooldown(String cooldownGroup) {
		if (cooldowns.containsKey(cooldownGroup)) {
			if (System.currentTimeMillis() < cooldowns.get(cooldownGroup)) {
				return true;
			} else {
				cooldowns.remove(cooldownGroup);
			}
		}
		
		return false;
	}
	
	public boolean removeCooldown(int actionCounter, BaseSWGCommand command) {
		if (cooldowns.containsKey(command.getCooldownGroup())) {
			cooldowns.remove(command.getCooldownGroup());
			getClient().getSession().write(new ObjControllerMessage(0x0B, new StartTask(actionCounter, getObjectID(), command.getCommandCRC(), CRC.StringtoCRC(command.getCooldownGroup().toLowerCase()), -1)).serialize());
			return true;
		}
		
		return false;
	}
	
	public long getRemainingCooldown(String cooldownGroup) {
		if (cooldowns.containsKey(cooldownGroup)) {
			if (System.currentTimeMillis() < cooldowns.get(cooldownGroup)) {
				return (long) (cooldowns.get(cooldownGroup) - System.currentTimeMillis());
			} else {
				cooldowns.remove(cooldownGroup);
			}
		}
		
		return 0L;
	}
	
	public int getGCWFatigue() {
		return (int) otherVariables.get("fatigue");
	}
	
	public void setGCWFatigue(int fatigue) {
		otherVariables.set("fatigue", fatigue);
	}
	
	public boolean isTrader(){
		return ((!isPlayer()) ? false : getPlayerObject().getProfession().startsWith("trader"));
	}
	
	public boolean isConstructing() {
		return isConstructing;
	}
	
	public void setConstructing(boolean isConstructing) {
		this.isConstructing = isConstructing;
	}
	
	public int getInventoryItemCount() {
		if (getSlottedObject("inventory") == null) {
			return 0;
		}
		
		LongAdder adder = new LongAdder();
		getSlottedObject("inventory").viewChildren(this, true, true, (obj) -> adder.increment());
		return adder.intValue();
	}
	
	public CreatureObject getCalledPet() {
		if (getPlayerObject() == null) {
			return null;
		}
		
		return (CreatureObject) NGECore.getInstance().objectService.getObject(getPlayerObject().getPet());
	}
	
	public void setCalledPet(CreatureObject calledPet) {
		if (getPlayerObject() != null) {
			getPlayerObject().setPet((calledPet == null) ? 0L : calledPet.getObjectID());
		}
	}
	
	public void sendSystemMessage(String message, byte displayType) {
		sendSystemMessage(message, new OutOfBand(), displayType);
	}
	
	public void sendSystemMessage(OutOfBand outOfBand, byte displayType) {
		sendSystemMessage("", outOfBand, displayType);
	}
	
	public void sendSystemMessage(String message, OutOfBand outOfBand, byte displayType) {
		if (getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write((new ChatSystemMessage(message, outOfBand, displayType)).serialize());
		}
	}
	
	public void playMusic(String sndFile) {
		playMusic(sndFile, 0, 1, false);
	}
	
	public void playMusic(String sndFile, long targetId) {
		playMusic(sndFile, targetId, 1, false);
	}
	
	public void playMusic(String sndFile, int repetitions) {
		playMusic(sndFile, 0, repetitions, false);
	}
	
	public void playMusic(String sndFile, long targetId, int repetitions) {
		playMusic(sndFile, targetId, repetitions, false);
	}
	
	public void playMusic(String sndFile, long targetId, int repetitions, boolean flag) {
		getClient().getSession().write(new PlayMusicMessage(sndFile, targetId, 1, false));
	}
	
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, notifySelf);
	}
	
	public CreatureMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new CreatureMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	public void sendBaselines(Client destination) {	
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(getBaseline(3).getBaseline());
			destination.getSession().write(getBaseline(6).getBaseline());
			if (destination == getClient()) {
				destination.getSession().write(getBaseline(1).getBaseline());
				destination.getSession().write(getBaseline(4).getBaseline());
				destination.getSession().write(getBaseline(8).getBaseline());
				destination.getSession().write(getBaseline(9).getBaseline());
			}
			
			if (destination.getParent() != this) {
				UpdatePVPStatusMessage upvpm = new UpdatePVPStatusMessage(getObjectID());
				upvpm.setFaction(CRC.StringtoCRC(getFaction()));
				upvpm.setStatus(NGECore.getInstance().factionService.calculatePvpStatus((CreatureObject) destination.getParent(), this));
				destination.getSession().write(upvpm.serialize());
				UpdatePostureMessage upm = new UpdatePostureMessage(getObjectID(), (byte) 0);
				destination.getSession().write(upm.serialize());
			}
		}
	}
	
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		switch (viewType) {
			case 1:
			case 4:
			case 7:
			case 8:
			case 9:
			{
				buffer = getBaseline(viewType).createDelta(updateType, buffer.array());
				
				if (getClient() != null && getClient().getSession() != null) {
					getClient().getSession().write(buffer);
				}
				
				break;
			}
			case 3:
			case 6:
			{
				buffer = getBaseline(viewType).createDelta(updateType, buffer.array());
				notifyClients(buffer, true);
				break;
			}
		}
	}
	
	@Deprecated public void playMusicSelf(String sndFile, long targetId, int repetitions, boolean flag) { playMusic(sndFile, targetId, repetitions, flag); }
	
	@Deprecated public void setXpBarValue(int xpBarValue) { setDisplayXp(xpBarValue); }
	
	@Deprecated public int getXpBarValue() { return getDisplayXp(); }
	
	@Deprecated public boolean isInStealth() { return isCloaked(); }
	
	@Deprecated public void setInStealth(boolean inStealth) { setCloaked(inStealth); }
	
	@Deprecated public boolean isStationary() { return isPerforming(); }
	
	@Deprecated public void setStationary(boolean stationary) { setPerforming(stationary); }
	
	@Deprecated public boolean isRadarVisible() { return visibleOnRadar(); }
	
	@Deprecated public void setRadarVisible(boolean radarVisible) { setVisibleOnRadar(radarVisible); }
	
	@Deprecated public void resetHAMList() { resetAttribs(); }
	
	@Deprecated public void updateHAMList() { updateAttribs(); }
	
	@Deprecated public long getTargetId() { return getIntendedTarget(); }
	
	@Deprecated public void setTargetId(long targetId) { setIntendedTarget(targetId); }
	
	@Deprecated public void setPerformanceId(int performanceId, boolean isDance) { setPerformanceId(isDance ? 0 : performanceId); }
	
	@Deprecated public boolean getAcceptBandflourishes() { return ((getGroupId() == 0) ? false : true); }
	
	@Deprecated public void setAcceptBandflourishes(boolean acceptBandflourishes) { }
	
	@Deprecated public void setGroupDance(boolean groupDance) { }
	
	@Deprecated public boolean toggleGroupDance() { return ((getGroupId() == 0) ? false : true); }
	
	@Deprecated public void updateAttribs() { }
	
	@Deprecated
	public void startPerformance() {
		if (!isPerforming()) {
			setPerforming(true);
		}
	}
	
	@Deprecated // FIXME Shouldn't be needed
	public boolean isPerformingFlourish() {
		synchronized(objectMutex){
			return performingFlourish;
		}
	}
	
	@Deprecated // FIXME Shouldn't be needed
	public void setPerformingFlourish(boolean performingFlourish) {
		synchronized(objectMutex) {
			this.performingFlourish = performingFlourish;
		}
	}
	
	@Deprecated // FIXME Objects aren't meant for functionality
	public void stopPerformance() {
		setPerformanceId(0);
		setPerformanceCounter(0);
		setCurrentAnimation("");
		
		synchronized(objectMutex) {
			if (entertainerExperience != null) {
				entertainerExperience.cancel(true);
				entertainerExperience = null;
			}
		}
		
	    sendSystemMessage("@performance:" + getPerformanceType()  + "_stop_self", (byte)0);
	    stopAudience();

		if (isPerforming()) {
			setPerforming(false);
		}
	}
	
	@Deprecated // FIXME Objects aren't meant for functionality
	public void stopAudience() {
		synchronized(objectMutex) {
			if (performanceAudience == null) {
				return;
			}
			
			String performanceType = getPerformanceType();
			Iterator<CreatureObject> it = performanceAudience.iterator();
			
			while (it.hasNext()) {
				CreatureObject next = it.next();
				
				if (((performanceType.equals("dance")) && (next.getPerformanceWatchee() != this))
				|| ((performanceType.equals("music")) && (next.getPerformanceListenee() != this))) {
					continue;
				}
				
				if (performanceType.equals("dance")) {
					next.setPerformanceWatchee(null);
				} else if (performanceType.equals("musci")) {
					next.setPerformanceListenee(null);
				}
				
				//this may be a bit dodgy.
				boolean isEntertained = next.getPerformanceListenee() != null && next.getPerformanceWatchee() != null;
				
				if (!isEntertained) {
					next.setMoodAnimation("");
				}
				
				if (next == this) {
					continue;
				}
				
				next.sendSystemMessage("@performance:" + performanceType  + "_stop_other", (byte)0);
			}
			
			performanceAudience = new ArrayList<CreatureObject>();
		}
	}
	
}
