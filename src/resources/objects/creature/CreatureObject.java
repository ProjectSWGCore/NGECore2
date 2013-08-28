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
import java.util.List;

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
import resources.objects.Buff;
import resources.objects.SWGList;
import engine.resources.objects.IPersistent;
import engine.resources.objects.MissionCriticalObject;
import engine.resources.objects.SWGObject;
import engine.resources.objects.SkillMod;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;

@Entity
public class CreatureObject extends TangibleObject implements IPersistent {
	
	@NotPersistent
	private Transaction txn;
	
	// CREO 1
	private int bankCredits = 0;
	private int cashCredits = 0;
	private SWGList<String> skills;
	@NotPersistent
	private int skillsUpdateCounter = 0;

	// CREO 3
	private byte posture = 0;
	private int factionStatus = 0;
	private float height;
	private int battleFatigue = 0;
	private long stateBitmask = 0;
	private long ownerId = 0;
	
	// CREO 4
	private float accelerationMultiplierBase = 1;
	private float accelerationMultiplierMod = 1;
	private SWGList<SkillMod> skillMods;
	@NotPersistent
	private int skillModsUpdateCounter = 0;
	private float speedMultiplierBase = 1;
	private float speedMultiplierMod = 1;
	private long listenToId = 0;
	private float runSpeed = (float) 7.3;
	private float slopeModAngle = 1;
	private float slopeModPercent = 1;
	private float turnRadius = 1;
	private float walkSpeed = (float) 2.75;
	private float waterModPercent = 1;
	private SWGList<String> abilities;
	private int abilitiesUpdateCounter = 0;

	private SWGList<MissionCriticalObject> missionCriticalObjects;
	@NotPersistent
	private int missionCriticalObjectsUpdateCounter = 0;


	// CREO6
	private byte combatFlag = 0;
	private short level = 0;
	private String currentAnimation;
	private String moodAnimation;
	private long weaponId = 0;
	private long groupId = 0;
	private long inviteSenderId = 0;
	private String inviteSenderName;
	private long inviteCounter = 0;
	private int guildId = 0;
	private long targetId = 0;
	private byte moodId = 0;
	private int performanceCounter = 0;
	private int performanceId = 0;
	private int health = 20000;
	private int action = 12500;
	@NotPersistent
	private int HAMListCounter = 0;
	private int maxHealth = 20000;
	private int maxAction = 12500;
	@NotPersistent
	private int maxHAMListCounter = 0;

	private SWGList<SWGObject> equipmentList;
	@NotPersistent
	private int equipmentListUpdateCounter = 0;
	private SWGList<Buff> buffList  = new SWGList<Buff>();
	@NotPersistent
	private int buffListUpdateCounter = 0;
	private SWGList<SWGObject> appearanceEquipmentList;
	@NotPersistent
	private int appearanceEquipmentListUpdateCounter = 0;

	// non-baseline vars
	@NotPersistent
	private List<Long> duelList = Collections.synchronizedList(new ArrayList<Long>());
	@NotPersistent
	private CreatureMessageBuilder messageBuilder;
	
	
	public CreatureObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, Template, position, orientation);
		messageBuilder = new CreatureMessageBuilder(this);
		skills = new SWGList<String>(messageBuilder, 1, 3);
		skillMods = new SWGList<SkillMod>(messageBuilder, 4, 3);
		abilities = new SWGList<String>(messageBuilder, 4, 14);
		missionCriticalObjects = new SWGList<MissionCriticalObject>(messageBuilder, 4, 13);
		equipmentList = new SWGList<SWGObject>(messageBuilder, 6, 0x17);
		buffList = new SWGList<Buff>(messageBuilder, 6, 0x1A);
		appearanceEquipmentList = new SWGList<SWGObject>(messageBuilder, 6, 0x1F);
	}
	
	public CreatureObject() {
		super();
		messageBuilder = new CreatureMessageBuilder(this);
	}
	
	public Transaction getTransaction() { return txn; }
	
	public void createTransaction(Environment env) { txn = env.beginTransaction(null, null); }

	public int getBankCredits() {
		synchronized(objectMutex) {
			return bankCredits;
		}
	}

	public void setBankCredits(int bankCredits) {
		synchronized(objectMutex) {
			this.bankCredits = bankCredits;
		}
		if(getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(messageBuilder.buildBankCreditsDelta(bankCredits));
		}
	}

	public int getCashCredits() {
		synchronized(objectMutex) {
			return cashCredits;
		}
	}

	public void setCashCredits(int cashCredits) {
		synchronized(objectMutex) {
			this.cashCredits = cashCredits;
		}
		if(getClient() != null && getClient().getSession() != null) {
			getClient().getSession().write(messageBuilder.buildCashCreditsDelta(cashCredits));
		}
	}

	public SWGList<String> getSkills() {
		return skills;
	}
	
	public short getSkillsUpdateCounter() {
		synchronized(objectMutex) {
			return (short) skillsUpdateCounter;
		}
	}
	
	public void setSkillsUpdateCounter(short skillsUpdateCounter) {
		synchronized(objectMutex) {
			this.skillsUpdateCounter = skillsUpdateCounter;
		}
	}
	
	public void addSkill(String skill) {
		
		if(skills.contains(skill))
			return;
		
		skills.add(skill);
		
		if(getClient() != null) {
			setSkillsUpdateCounter((short) (getSkillsUpdateCounter() + 1));
			getClient().getSession().write(messageBuilder.buildAddSkillDelta(skill));
		}

	}
	
	public void removeSkill(String skill) {
		
		if(!skills.contains(skill))
			return;
		
		skills.remove(skill);
		
		if(getClient() != null) {
			setSkillsUpdateCounter((short) (getSkillsUpdateCounter() + 1));
			getClient().getSession().write(messageBuilder.buildRemoveSkillDelta(skill));
		}
		
	}

	@Override
	public void setOptionsBitmask(int optionBitmask) {
		synchronized(objectMutex) {
			this.optionsBitmask = optionBitmask;
		}
		
		IoBuffer optionDelta = messageBuilder.buildOptionMaskDelta(optionBitmask);
		
		notifyObservers(optionDelta, true);

	}

	public byte getPosture() {
		synchronized(objectMutex) {
			return posture;
		}
	}

	public void setPosture(byte posture) {
		synchronized(objectMutex) {
			this.posture = posture;
		}
		IoBuffer postureDelta = messageBuilder.buildPostureDelta(posture);
		Posture postureUpdate = new Posture(getObjectID(), posture);
		ObjControllerMessage objController = new ObjControllerMessage(0x1B, postureUpdate);
		
		notifyObservers(postureDelta, true);
		notifyObservers(objController, true);
	}

	@Override
	public void setFaction(String faction) {
		synchronized(objectMutex) {
			this.faction = faction;
		}
		
		IoBuffer factionDelta = messageBuilder.buildFactionDelta(faction);
		
		notifyObservers(factionDelta, true);

	}

	public int getFactionStatus() {
		synchronized(objectMutex) {
			return factionStatus;
		}
	}

	public void setFactionStatus(int factionStatus) {
		synchronized(objectMutex) {
			this.factionStatus = factionStatus;
		}
		
		IoBuffer factionStatusDelta = messageBuilder.buildFactionStatusDelta(factionStatus);
		
		notifyObservers(factionStatusDelta, true);

	}

	public float getHeight() {
		synchronized(objectMutex) {
			return height;
		}
	}

	public void setHeight(float height) {
		synchronized(objectMutex) {
			this.height = height;
		}
		
		IoBuffer heightDelta = messageBuilder.buildHeightDelta(height);
		
		notifyObservers(heightDelta, true);

	}

	public int getBattleFatigue() {
		synchronized(objectMutex) {
			return battleFatigue;
		}
	}

	public void setBattleFatigue(int battleFatigue) {
		synchronized(objectMutex) {
			this.battleFatigue = battleFatigue;
		}
	}

	public long getStateBitmask() {
		synchronized(objectMutex) {
			return stateBitmask;
		}
	}

	public void setStateBitmask(long stateBitmask) {
		synchronized(objectMutex) {
			this.stateBitmask = stateBitmask;
		}
		
		IoBuffer stateDelta = messageBuilder.buildStateDelta(stateBitmask);
		
		notifyObservers(stateDelta, true);

	}

	public long getOwnerId() {
		synchronized(objectMutex) {
			return ownerId;
		}
	}

	public void setOwnerId(long ownerId) {
		synchronized(objectMutex) {
			this.ownerId = ownerId;
		}
	}

	public float getAccelerationMultiplierBase() {
		synchronized(objectMutex) {
			return accelerationMultiplierBase;
		}
	}

	public void setAccelerationMultiplierBase(float accelerationMultiplierBase) {
		synchronized(objectMutex) {
			this.accelerationMultiplierBase = accelerationMultiplierBase;
		}
	}

	public float getAccelerationMultiplierMod() {
		synchronized(objectMutex) {
			return accelerationMultiplierMod;
		}
	}

	public void setAccelerationMultiplierMod(float accelerationMultiplierMod) {
		synchronized(objectMutex) {
			this.accelerationMultiplierMod = accelerationMultiplierMod;
		}
	}

	public SWGList<SkillMod> getSkillMods() {
		return skillMods;
	}
	
	public SkillMod getSkillMod(String name) {
		synchronized(skillMods.getMutex()) {
			for(SkillMod skillMod : skillMods.get()) {
				if(skillMod.getSkillModString().equals(name))
					return skillMod;
			}
		}
		return null;
	}
	
	public void addSkillMod(String name, int base) {
		
		if(getSkillMod(name) == null) {
			SkillMod skillMod = new SkillMod();
			skillMod.setBase(base);
			skillMod.setSkillModString(name);
			skillMod.setModifier(0);
			skillMods.add(skillMod);
		} else {
			SkillMod mod = getSkillMod(name);
			mod.setBase(mod.getBase() + base);
			if(getClient() != null) {
				setSkillModsUpdateCounter((short) (getSkillModsUpdateCounter() + 1));
				getClient().getSession().write(messageBuilder.buildAddSkillModDelta(name, mod.getBase()));
			}
		}
		
	}
	
	public void deductSkillMod(String name, int base) {
		
		if(getSkillMod(name) == null)
			return;
		
		SkillMod mod = getSkillMod(name);
		mod.setBase(mod.getBase() - base);
		
		if(mod.getBase() <= 0) {
			removeSkillMod(mod);
		} else {
			if(getClient() != null) {
				setSkillModsUpdateCounter((short) (getSkillModsUpdateCounter() + 1));
				getClient().getSession().write(messageBuilder.buildAddSkillModDelta(name, mod.getBase()));
			}
		}
		
	}

	public void removeSkillMod(SkillMod mod) {
		
		skillMods.remove(mod);
		
		if(getClient() != null) {
			setSkillModsUpdateCounter((short) (getSkillModsUpdateCounter() + 1));
			getClient().getSession().write(messageBuilder.buildRemoveSkillModDelta(mod.getSkillModString(), mod.getBase()));
		}

		
	}

	public short getSkillModsUpdateCounter() {
		synchronized(objectMutex) {
			return (short) skillModsUpdateCounter;
		}
	}
	
	public void setSkillModsUpdateCounter(short skillModsUpdateCounter) {
		synchronized(objectMutex) {
			this.skillModsUpdateCounter = skillModsUpdateCounter;
		}
	}

	
	public float getSpeedMultiplierBase() {
		synchronized(objectMutex) {
			return speedMultiplierBase;
		}
	}

	public void setSpeedMultiplierBase(float speedMultiplierBase) {
		synchronized(objectMutex) {
			this.speedMultiplierBase = speedMultiplierBase;
		}
		IoBuffer speedDelta = messageBuilder.buildSpeedModDelta(speedMultiplierBase);
		
		notifyObservers(speedDelta, true);

	}

	public float getSpeedMultiplierMod() {
		synchronized(objectMutex) {
			return speedMultiplierMod;
		}
	}

	public void setSpeedMultiplierMod(float speedMultiplierMod) {
		synchronized(objectMutex) {
			this.speedMultiplierMod = speedMultiplierMod;
		}
	}

	public long getListenToId() {
		synchronized(objectMutex) {
			return listenToId;
		}
	}

	public void setListenToId(long listenToId) {
		synchronized(objectMutex) {
			this.listenToId = listenToId;
		}
	}

	public float getRunSpeed() {
		synchronized(objectMutex) {
			return runSpeed;
		}
	}

	public void setRunSpeed(float runSpeed) {
		synchronized(objectMutex) {
			this.runSpeed = runSpeed;
		}
	}

	public float getSlopeModAngle() {
		synchronized(objectMutex) {
			return slopeModAngle;
		}
	}

	public void setSlopeModAngle(float slopeModAngle) {
		synchronized(objectMutex) {
			this.slopeModAngle = slopeModAngle;
		}
	}

	public float getSlopeModPercent() {
		synchronized(objectMutex) {
			return slopeModPercent;
		}
	}

	public void setSlopeModPercent(float slopeModPercent) {
		synchronized(objectMutex) {
			this.slopeModPercent = slopeModPercent;
		}
	}

	public float getTurnRadius() {
		synchronized(objectMutex) {
			return turnRadius;
		}
	}

	public void setTurnRadius(float turnRadius) {
		synchronized(objectMutex) {
			this.turnRadius = turnRadius;
		}
		IoBuffer turnDelta = messageBuilder.buildTurnRadiusDelta(turnRadius);
		
		notifyObservers(turnDelta, true);

	}

	public float getWalkSpeed() {
		synchronized(objectMutex) {
			return walkSpeed;
		}
	}

	public void setWalkSpeed(float walkSpeed) {
		synchronized(objectMutex) {
			this.walkSpeed = walkSpeed;
		}
	}

	public float getWaterModPercent() {
		synchronized(objectMutex) {
			return waterModPercent;
		}
	}

	public void setWaterModPercent(float waterModPercent) {
		synchronized(objectMutex) {
			this.waterModPercent = waterModPercent;
		}
	}

	public SWGList<String> getAbilities() {
		return abilities;
	}

	public int getAbilitiesUpdateCounter() {
		synchronized(objectMutex) {
			return abilitiesUpdateCounter;
		}
	}

	public void setAbilitiesUpdateCounter(int abilitiesUpdateCounter) {
		synchronized(objectMutex) {
			this.abilitiesUpdateCounter = abilitiesUpdateCounter;
		}
	}
	
	
	public void addAbility(String abilityName) {
		
		if(abilities.contains(abilityName))
			return;
		
		abilities.add(abilityName);
		
		if(getClient() != null) {
			setAbilitiesUpdateCounter((short) (getAbilitiesUpdateCounter() + 1));
			getClient().getSession().write(messageBuilder.buildAddAbilityDelta(abilityName));
		}

	}
	
	public void removeAbility(String abilityName) {
		
		if(!abilities.contains(abilityName))
			return;
		
		abilities.remove(abilityName);
		
		if(getClient() != null) {
			setAbilitiesUpdateCounter((short) (getAbilitiesUpdateCounter() + 1));
			getClient().getSession().write(messageBuilder.buildRemoveAbilityDelta(abilityName));
		}
		
	}


	public SWGList<MissionCriticalObject> getMissionCriticalObjects() {
		return missionCriticalObjects;
	}

	public byte getCombatFlag() {
		synchronized(objectMutex) {
			return combatFlag;
		}
	}

	public void setCombatFlag(byte combatFlag) {
		synchronized(objectMutex) {
			this.combatFlag = combatFlag;
		}
		IoBuffer combatDelta = messageBuilder.buildCombatFlagDelta(combatFlag);
		
		notifyObservers(combatDelta, true);
	}

	public short getLevel() {
		synchronized(objectMutex) {
			return level;
		}
	}

	public void setLevel(short level) {
		synchronized(objectMutex) {
			this.level = level;
		}
		
		IoBuffer levelDelta = messageBuilder.buildLevelDelta(level);
		
		notifyObservers(levelDelta, true);

	}

	public String getCurrentAnimation() {
		synchronized(objectMutex) {
			return currentAnimation;
		}
	}

	public void setCurrentAnimation(String currentAnimation) {
		synchronized(objectMutex) {
			this.currentAnimation = currentAnimation;
		}
	}

	public String getMoodAnimation() {
		synchronized(objectMutex) {
			return moodAnimation;
		}
	}

	public void setMoodAnimation(String moodAnimation) {
		synchronized(objectMutex) {
			this.moodAnimation = moodAnimation;
		}
	}

	public long getWeaponId() {
		synchronized(objectMutex) {
			return weaponId;
		}
	}

	public void setWeaponId(long weaponId) {
		synchronized(objectMutex) {
			this.weaponId = weaponId;
		}
		
		IoBuffer weaponIdDelta = messageBuilder.buildWeaponIdDelta(weaponId);
		
		notifyObservers(weaponIdDelta, true);

	}

	public long getGroupId() {
		synchronized(objectMutex) {
			return groupId;
		}
	}

	public void setGroupId(long groupId) {
		synchronized(objectMutex) {
			this.groupId = groupId;
		}
		
		IoBuffer groupIdDelta = messageBuilder.buildGroupIdDelta(groupId);
		
		notifyObservers(groupIdDelta, true);

	}

	public long getInviteSenderId() {
		synchronized(objectMutex) {
			return inviteSenderId;
		}
	}

	public void setInviteSenderId(long inviteSenderId) {
		synchronized(objectMutex) {
			this.inviteSenderId = inviteSenderId;
		}
	}

	public String getInviteSenderName() {
		synchronized(objectMutex) {
			return inviteSenderName;
		}
	}

	public void setInviteSenderName(String inviteSenderName) {
		synchronized(objectMutex) {
			this.inviteSenderName = inviteSenderName;
		}
	}

	public int getGuildId() {
		synchronized(objectMutex) {
			return guildId;
		}
	}

	public void setGuildId(int guildId) {
		synchronized(objectMutex) {
			this.guildId = guildId;
		}
	}

	public long getTargetId() {
		synchronized(objectMutex) {
			return targetId;
		}
	}

	public void setTargetId(long targetId) {
		synchronized(objectMutex) {
			this.targetId = targetId;
		}
		IoBuffer targetDelta = messageBuilder.buildTargetDelta(targetId);
		
		notifyObservers(targetDelta, false);

	}

	public long getInviteCounter() {
		synchronized(objectMutex) {
			return inviteCounter;
		}
	}

	public void setInviteCounter(long inviteCounter) {
		synchronized(objectMutex) {
			this.inviteCounter = inviteCounter;
		}
	}

	public byte getMoodId() {
		synchronized(objectMutex) {
			return moodId;
		}
	}

	public void setMoodId(byte moodId) {
		synchronized(objectMutex) {
			this.moodId = moodId;
		}
	}

	public int getPerformanceCounter() {
		synchronized(objectMutex) {
			return performanceCounter;
		}
	}

	public void setPerformanceCounter(int performanceCounter) {
		synchronized(objectMutex) {
			this.performanceCounter = performanceCounter;
		}
	}

	public int getPerformanceId() {
		synchronized(objectMutex) {
			return performanceId;
		}
	}

	public void setPerformanceId(int performanceId) {
		synchronized(objectMutex) {
			this.performanceId = performanceId;
		}
	}

	public SWGList<SWGObject> getEquipmentList() {
		return equipmentList;
	}

	public SWGList<Buff> getBuffList() {
		return buffList;
	}

	public SWGList<SWGObject> getAppearanceEquipmentList() {
		return appearanceEquipmentList;
	}

	public List<Long> getDuelList() {
		return duelList;
	}

	public boolean isInDuelList(long objectId) {
		if(duelList.contains(objectId))
			return true;
		return false;
	}
	
	public void addObjectToEquipList(SWGObject object) {
		if(object instanceof TangibleObject || object instanceof WeaponObject) {
			equipmentList.add(object);
		}
	}
	
	public void removeObjectFromEquipList(SWGObject object) {
		if(object instanceof TangibleObject || object instanceof WeaponObject) {
			equipmentList.remove(object);
		}
	}


	@SuppressWarnings("unused")
	@Override
	public void sendBaselines(Client destination) {
				
		if(destination == null || destination.getSession() == null) {
			System.out.println("NULL session");
			return;
		}
		
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
		if(destination == getClient()) {
			destination.getSession().write(messageBuilder.buildBaseline1());
			destination.getSession().write(messageBuilder.buildBaseline4());
		}
		//destination.getSession().write(messageBuilder.buildBaseline8());
		//destination.getSession().write(messageBuilder.buildBaseline9());
		 
		UpdatePostureMessage upm = new UpdatePostureMessage(getObjectID(), (byte) 0);
		//destination.getSession().write(upm.serialize());
		
		if(destination != getClient()) {
			UpdatePVPStatusMessage upvpm = new UpdatePVPStatusMessage(getObjectID());
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
			if(factionStatus == 0 && faction == "neutral") {
				upvpm.setFaction(UpdatePVPStatusMessage.factionCRC.Neutral);
				upvpm.setStatus(16);
			}
			else {
				upvpm.setFaction(UpdatePVPStatusMessage.factionCRC.Neutral);
				upvpm.setStatus(16);
			}
			destination.getSession().write(upvpm.serialize());
		}
	}

	public void sendSystemMessage(String message, byte displayType) {
		
		if(getClient() != null && getClient().getSession() != null) {
			ChatSystemMessage systemMsg = new ChatSystemMessage(message, displayType);
			getClient().getSession().write(systemMsg.serialize());
		}
		
	}

	public int getHealth() {
		synchronized(objectMutex) {
			return health;
		}
	}

	public void setHealth(int health) {
		IoBuffer delta;
		synchronized(objectMutex) {
			if(health > maxHealth)
				health = maxHealth;
			this.health = health;
			setHamListCounter(getHamListCounter() + 1);
			delta = messageBuilder.buildHealthDelta(health);
		}
		notifyObservers(delta, true);
	}

	public int getAction() {
		synchronized(objectMutex) {
			return action;
		}
	}

	public void setAction(int action) {
		IoBuffer delta;
		synchronized(objectMutex) {
			if(action > maxAction)
				action = maxAction;
			this.action = action;
			setHamListCounter(getHamListCounter() + 1);
			delta = messageBuilder.buildActionDelta(action);
		}
		notifyObservers(delta, true);
	}

	public int getHamListCounter() {
		synchronized(objectMutex) {
			return HAMListCounter;
		}
	}

	public void setHamListCounter(int hamListCounter) {
		synchronized(objectMutex) {
			this.HAMListCounter = hamListCounter;
		}
	}

	public int getMaxHealth() {
		synchronized(objectMutex) {
			return maxHealth;
		}
	}

	public void setMaxHealth(int maxHealth) {
		synchronized(objectMutex) {
			this.maxHealth = maxHealth;
			setMaxHAMListCounter(getMaxHAMListCounter() + 1);
		}
		notifyObservers(messageBuilder.buildMaxHealthDelta(maxHealth), true);
	}

	public int getMaxAction() {
		synchronized(objectMutex) {
			return maxAction;
		}
	}

	public void setMaxAction(int maxAction) {
		synchronized(objectMutex) {
			this.maxAction = maxAction;
			setMaxHAMListCounter(getMaxHAMListCounter() + 1);
		}
		notifyObservers(messageBuilder.buildMaxActionDelta(maxAction), true);
	}

	public int getMaxHAMListCounter() {
		synchronized(objectMutex) {
			return maxHAMListCounter;
		}
	}

	public void setMaxHAMListCounter(int maxHAMListCounter) {
		synchronized(objectMutex) {
			this.maxHAMListCounter = maxHAMListCounter;
		}
	}
	
	public void addBuff(Buff buff) {
		synchronized(objectMutex) {
			buffList.get().add(buff);
			setBuffListCounter(getBuffListCounter() + 1);
			
		}
		buff.setStartTime();
		notifyObservers(messageBuilder.buildAddBuffDelta(buff), true);
	}
	
	public void removeBuff(Buff buff) {
		synchronized(objectMutex) {
			buffList.get().remove(buff);
			setBuffListCounter(getBuffListCounter() + 1);
		}
		notifyObservers(messageBuilder.buildRemoveBuffDelta(buff), true);
	}

	public int getBuffListCounter() {
		synchronized(objectMutex) {
			return buffListUpdateCounter;
		}
	}
	
	public void setBuffListCounter(int buffListCounter) {
		synchronized(objectMutex) {
			this.buffListUpdateCounter = buffListCounter;
		}
	}
	
	public Buff getBuffByName(String buffName) {
		synchronized(objectMutex) {
			for(Buff buff : buffList.get()) {
				if(buff.getBuffName().equals(buffName))
					return buff;
			}
		}
		return null;
	}
	
	public void updateGroupInviteInfo() {
		
		if(getClient() == null || getClient().getSession() == null)
			return;
		
		getClient().getSession().write(messageBuilder.buildGroupInviteDelta(getInviteSenderId(), getInviteCounter(), getInviteSenderName()));
		
	}


}
