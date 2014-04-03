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
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ChatSystemMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.PlayClientEffectObjectMessage;
import protocol.swg.PlayMusicMessage;
import protocol.swg.UpdatePostureMessage;
import protocol.swg.UpdatePVPStatusMessage;
import protocol.swg.objectControllerObjects.Animation;
import protocol.swg.objectControllerObjects.Posture;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;

import main.NGECore;
import engine.clients.Client;
import resources.common.Cooldown;
import resources.objects.Buff;
import resources.objects.DamageOverTime;
import resources.objects.SWGList;
import resources.objects.SWGMap;
import resources.objects.SkillMod;
import engine.resources.objects.IPersistent;
import engine.resources.objects.MissionCriticalObject;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;

@Entity(version=5)
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
	private SWGMap<String, SkillMod> skillMods;
	private float speedMultiplierBase = 1;
	private float speedMultiplierMod = 1;
	private float runSpeed = (float) 7.3;
	private float slopeModAngle = 1;
	private float slopeModPercent = 1;
	private float turnRadius = 1;
	private float walkSpeed = (float) 1.549;
	private float waterModPercent = (float) 0.75;
	private SWGList<String> abilities;
	private int abilitiesUpdateCounter = 0;
	private int xpBarValue = 0;

	private SWGList<MissionCriticalObject> missionCriticalObjects;
	@NotPersistent
	private int missionCriticalObjectsUpdateCounter = 0;


	// CREO6
	private byte combatFlag = 0;
	private short level = -1;
	private int grantedHealth = 0;
	private String currentAnimation;
	private String moodAnimation;
	private long weaponId = 0;
	private long groupId = 0;
	private long inviteSenderId = 0;
	private String inviteSenderName;
	private long inviteCounter = 0;
	private int guildId = 0;
	private long lookAtTarget = 0;
	private long targetId = 0;
	private byte moodId = 0;
	private int performanceCounter = 0;
	private int performanceId = 0;
	private boolean hologram = false;
	private boolean performanceType = false;
	private boolean acceptBandflourishes = true;
	private boolean groupDance = true;
	private CreatureObject performanceWatchee;
	private CreatureObject performanceListenee;
	@NotPersistent
	private Vector<CreatureObject> performanceAudience = new Vector<CreatureObject>();
	private int health = 1000;
	private int action = 300;
	@NotPersistent
	private int HAMListCounter = 0;
	private int maxHealth = 1000;
	private int maxAction = 300;
	@NotPersistent
	private int maxHAMListCounter = 0;

	private SWGList<SWGObject> equipmentList;
	@NotPersistent
	private int equipmentListUpdateCounter = 0;
	private SWGList<Buff> buffList  = new SWGList<Buff>();
	@NotPersistent
	private int buffListUpdateCounter = 0;
	private byte difficulty = 0;
	private SWGList<SWGObject> appearanceEquipmentList;
	@NotPersistent
	private int appearanceEquipmentListUpdateCounter = 0;
	
	private boolean inStealth = false;
	private boolean radarVisible = true; // radar
	private boolean stationary = false;
	
	// non-baseline vars
	@NotPersistent
	private List<CreatureObject> duelList = Collections.synchronizedList(new ArrayList<CreatureObject>());
	@NotPersistent
	private CreatureMessageBuilder messageBuilder;
	private SWGList<DamageOverTime> dotList = new SWGList<DamageOverTime>();
	@NotPersistent
	private ScheduledFuture<?> incapTask;
	@NotPersistent
	private ScheduledFuture<?> entertainerExperience;
	@NotPersistent
	private ScheduledFuture<?> inspirationTick;
	
	@NotPersistent
	private ScheduledFuture<?> spectatorTask;
	
	private boolean staticNPC = false; // temp
	@NotPersistent
	private int flourishCount = 0;
	@NotPersistent
	private boolean performingEffect;
	@NotPersistent
	private boolean performingFlourish;
	private int coverCharge;
	@NotPersistent
	private TangibleObject conversingNpc;
	@NotPersistent
	private ConcurrentHashMap<String, Cooldown> cooldowns = new ConcurrentHashMap<String, Cooldown>();
	
	public CreatureObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, Template, position, orientation);
		messageBuilder = new CreatureMessageBuilder(this);
		loadTemplateData();
		skills = new SWGList<String>(messageBuilder, 1, 3);
		skillMods = new SWGMap<String, SkillMod>(messageBuilder, 4, 3);
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
	
	private void loadTemplateData() {
		
		/*if(getTemplateData().getAttribute("scale") != null)
			setHeight((float) getTemplateData().getAttribute("scale"));
		if(getTemplateData().getAttribute("speed") != null) {
			//System.out.println(getTemplateData().getAttribute("speed"));
			setRunSpeed((float) getTemplateData().getAttribute("speed"));
		}
		if(getTemplateData().getAttribute("turnRate") != null)
			setTurnRadius((float) getTemplateData().getAttribute("turnRate"));*/

	}
	
	public void setCustomName2(String customName) {
		setCustomName(customName);
		
		notifyObservers(messageBuilder.buildCustomNameDelta(customName), true);
	}
	
	public Transaction getTransaction() { return txn; }
	
	public void createTransaction(Environment env) { txn = env.beginTransaction(null, null); }

	public int getBankCredits() {
		synchronized(objectMutex) {
			return bankCredits;
		}
	}
	
	public boolean isPlayer() {
		synchronized(objectMutex) {
			return ( getSlottedObject("ghost") != null );
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
	
	public boolean hasSkill(String name) {
		/*synchronized(objectMutex) {
			for (String skill : skills) {
				if (skill.equals(name)) {
					return true;
				}
			}
			
			return false;
		}*/
		return skills.contains(name);
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
	public int getOptionsBitmask() {
		synchronized(objectMutex) {
			return optionsBitmask;
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
	
	public void setOptions(int options, boolean add) {
		synchronized(objectMutex) {
			if (options != 0) {
				if (add) {
					addOption(options);
				} else {
					removeOption(options);
				}
			}
		}
	}
	
	public void addOption(int option) {
		setOptionsBitmask(getOptionsBitmask() | option);
	}
	
	public void removeOption(int option) {
		setOptionsBitmask(getOptionsBitmask() & ~option);
	}

	public byte getPosture() {
		synchronized(objectMutex) {
			return posture;
		}
	}

	public void setPosture(byte posture) {

		synchronized(objectMutex) {
			if (this.posture == 0x09) {
				stopPerformance();
			}
			if(this.posture == posture)
				return;
			this.posture = posture;
		}

		Posture postureUpdate = new Posture(getObjectID(), posture);
		ObjControllerMessage objController = new ObjControllerMessage(0x1B, postureUpdate);
		
		notifyObservers(messageBuilder.buildPostureDelta(posture), true);
		notifyObservers(objController, true);
		
	}
	
	public void startPerformance() {
		
		getClient().getSession().write(messageBuilder.buildStartPerformance(true));
		
	}
	
	public void stopPerformance() {

		String type = "";
		synchronized(objectMutex) {
			
			// Some reason this prevents the animation for playing an instrument when stopping (unless that's what "" does)
			setCurrentAnimation(getCurrentAnimation());
			
			setPerformanceCounter(0);
			setPerformanceId(0,true);
			
			type = (performanceType) ? "dance" : "music";
			if (entertainerExperience != null) {
				entertainerExperience.cancel(true);
				entertainerExperience = null;
			}
		}
		
	    sendSystemMessage("@performance:" + type  + "_stop_self",(byte)0);
	    stopAudience();

		getClient().getSession().write(messageBuilder.buildStartPerformance(false));

	}
	
	public void stopAudience() {
		synchronized(objectMutex) {
			//String type = (performanceType) ? "dance" : "music";
			if (performanceAudience == null) {
				return;
			}
			Iterator<CreatureObject> it = performanceAudience.iterator();
			while (it.hasNext()) {
				CreatureObject next = it.next();
				if ((performanceType) && (next.getPerformanceWatchee() != this)) { continue; }
				if ((!performanceType) && (next.getPerformanceListenee() != this)) { continue; }
				if (performanceType) { next.setPerformanceWatchee(null); }
				if (!performanceType) { next.setPerformanceListenee(null); }
				//this may be a bit dodgy.
				boolean isEntertained = next.getPerformanceListenee() != null && next.getPerformanceWatchee() != null;
				if (!isEntertained) {
					next.setMoodAnimation("");
				}
				if (next == this) { continue; }
				if(performanceType) { next.sendSystemMessage("You stop watching " + getCustomName() + ".",(byte)0); }
				else { next.sendSystemMessage("You stop listening to " + getCustomName() + ".",(byte)0); }
				next.getSpectatorTask().cancel(true);
			}
		}
	}
	public ScheduledFuture<?> getEntertainerExperience() {
		return entertainerExperience;
	}

	public void setEntertainerExperience(ScheduledFuture<?> entertainerExperience) {
		this.entertainerExperience = entertainerExperience;
	}

	public ScheduledFuture<?> getInspirationTick() {
		synchronized(objectMutex) {
			return inspirationTick;
		}
	}

	public void setInspirationTick(ScheduledFuture<?> inspirationTick) {
		synchronized(objectMutex) {
			this.inspirationTick = inspirationTick;
		}
	}

	@Override
	public void setFaction(String faction) {
		synchronized(objectMutex) {
			this.faction = faction;
		}
		
		notifyObservers(messageBuilder.buildFactionDelta(faction), true);
		//updatePvpStatus();
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
		
		notifyObservers(messageBuilder.buildFactionStatusDelta(factionStatus), true);
		//updatePvpStatus();
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
	
	public SWGMap<String, SkillMod> getSkillMods() {
		return skillMods;
	}
	
	public SkillMod getSkillMod(String name) {
		return skillMods.get(name);
	}
	
	public int getSkillModBase(String name) {
		SkillMod skillMod = getSkillMod(name);
		return ((skillMod == null) ? 0 : skillMod.getBase());
	}
	
	public void addSkillMod(String name, int base) {
		SkillMod mod;
		
		if (getSkillMod(name) == null) {
			mod = new SkillMod();
			mod.setBase(base);
			mod.setName(name);
			mod.setModifier(0);
		} else {
			mod = getSkillMod(name);
			mod.setBase(mod.getBase() + base);
		}
		
		skillMods.put(name, mod);
	}
	
	public void deductSkillMod(String name, int base) {
		if (getSkillMod(name) == null) {
			return;
		}
		
		SkillMod mod = getSkillMod(name);
		mod.setBase(mod.getBase() - base);
		
		if (mod.getBase() <= 0) {
			removeSkillMod(mod);
		} else {
			skillMods.put(name, mod);
		}
	}
	
	public void removeSkillMod(SkillMod mod) {
		if (mod != null) {
			skillMods.remove(mod);
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
		IoBuffer speedDelta = messageBuilder.buildSpeedModBaseDelta(speedMultiplierBase);
		
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
		IoBuffer speedDelta = messageBuilder.buildSpeedModDelta(speedMultiplierMod);
		
		notifyObservers(speedDelta, true);
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
	
	public boolean hasAbility(String name) {
		for (String ability : abilities) {
			if (ability.equals(name)) {
				return true;
			}
		}
		
		return false;
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
	
	public int getGrantedHealth() {
		synchronized(objectMutex) {
			return grantedHealth;
		}
	}
	
	public void setGrantedHealth(int grantedHealth) {
		synchronized(objectMutex) {
			this.grantedHealth = grantedHealth;
		}
		
		notifyObservers(messageBuilder.buildGrantedHealthDelta(grantedHealth), true);
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
		Animation animation = new Animation(getObjectId(), currentAnimation);
		ObjControllerMessage objController = new ObjControllerMessage(0x1B, animation);
		
		notifyObservers(objController, true);
		
		notifyObservers(messageBuilder.buildCurrentAnimationDelta(currentAnimation), true);

	}
	
	public void doSkillAnimation(String skillAnimation) {
		Animation animation = new Animation(getObjectId(), skillAnimation);
		ObjControllerMessage objController = new ObjControllerMessage(0x1B, animation);
		
		notifyObservers(objController, true);
		
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
		IoBuffer moodAnimationDelta = messageBuilder.buildMoodAnimationDelta(moodAnimation);
		notifyObservers(moodAnimationDelta, true);
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
	
	public long getLookAtTarget() {
		synchronized(objectMutex) {
			return lookAtTarget;
		}
	}
	
	public void setLookAtTarget(long lookAtTarget) {
		synchronized(objectMutex) {
			this.lookAtTarget = lookAtTarget;
		}
		
		notifyObservers(messageBuilder.buildLookAtTargetDelta(lookAtTarget), true);
	}
	
	public long getIntendedTarget() {
		synchronized(objectMutex) {
			return targetId;
		}
	}

	public void setIntendedTarget(long intendedTarget) {
		synchronized(objectMutex) {
			this.targetId = intendedTarget;
		}
		
		notifyObservers(messageBuilder.buildIntendedTargetDelta(intendedTarget), true);
	}
	
	public long getTargetId() {
		return getIntendedTarget();
	}
	
	public void setTargetId(long targetId) {
		setIntendedTarget(targetId);
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
		getClient().getSession().write(messageBuilder.buildPerformanceCounter(performanceCounter));
	}

	public int getPerformanceId() {
		synchronized(objectMutex) {
			return performanceId;
		}
	}

	public void setPerformanceId(int performanceId, boolean isDance) {
		synchronized(objectMutex) {
			this.performanceId = performanceId;
		}
		getClient().getSession().write(messageBuilder.buildPerformanceId((isDance) ? 0 : performanceId));
	}

	public boolean getAcceptBandflourishes() {
		return acceptBandflourishes;
	}

	public void setAcceptBandflourishes(boolean acceptBandflourishes) {
		this.acceptBandflourishes = acceptBandflourishes;
	}

	public SWGList<SWGObject> getEquipmentList() {
	    synchronized(objectMutex) {
		return equipmentList;
	    }
	}

	public SWGList<Buff> getBuffList() {
		return buffList;
	}

	public SWGList<SWGObject> getAppearanceEquipmentList() {
		return appearanceEquipmentList;
	}

	public List<CreatureObject> getDuelList() {
		return duelList;
	}

	public boolean isInDuelList(long objectId) {
		if(duelList.contains(objectId))
			return true;
		return false;
	}
	
	public void addObjectToEquipList(SWGObject object) {
		if(object instanceof TangibleObject) {
			equipmentList.get().add(object);
			setEquipmentListUpdateCounter(getEquipmentListUpdateCounter() + 1);
			notifyObservers(messageBuilder.buildAddEquipmentDelta((TangibleObject) object), true);
		}
	}
	
	public void removeObjectFromEquipList(SWGObject object) {
		if(object instanceof TangibleObject && equipmentList.contains(object)) {
			setEquipmentListUpdateCounter(getEquipmentListUpdateCounter() + 1);
			notifyObservers(messageBuilder.buildRemoveEquipmentDelta((TangibleObject) object), true);
			equipmentList.get().remove(object);
		}
	}
	
	public void addObjectToAppearanceEquipList(SWGObject object) {
		if(object instanceof TangibleObject) {
			appearanceEquipmentList.get().add(object);
			setAppearanceEquipmentListUpdateCounter(getAppearanceEquipmentListUpdateCounter() + 1);
			notifyObservers(messageBuilder.buildAddAppearanceEquipmentDelta((TangibleObject) object), true);
		}
	}
	
	public void removeObjectFromAppearanceEquipList(SWGObject object) {
		if(object instanceof TangibleObject && appearanceEquipmentList.contains(object)) {
			setAppearanceEquipmentListUpdateCounter(getAppearanceEquipmentListUpdateCounter() + 1);
			notifyObservers(messageBuilder.buildRemoveAppearanceEquipmentDelta((TangibleObject) object), true);
			appearanceEquipmentList.get().remove(object);
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
		 		
		if(destination != getClient()) {
			UpdatePVPStatusMessage upvpm = new UpdatePVPStatusMessage(getObjectID(), NGECore.getInstance().factionService.calculatePvpStatus((CreatureObject) destination.getParent(), this), getFaction());
			destination.getSession().write(upvpm.serialize());
			UpdatePostureMessage upm = new UpdatePostureMessage(getObjectID(), getPosture());
			destination.getSession().write(upm.serialize());
		}
	}

	public void sendSystemMessage(String message, byte displayType) {
		
		if(getClient() != null && getClient().getSession() != null) {
			ChatSystemMessage systemMsg = new ChatSystemMessage(message, displayType);
			getClient().getSession().write(systemMsg.serialize());
		}
		
	}
	
	public void sendSystemMessage(String stfFilename, String stfName, int stat, int displayType) {
		
		if(getClient() != null && getClient().getSession() != null) {
			ChatSystemMessage systemMsg = new ChatSystemMessage(stfFilename, stfName, stat, (byte) displayType);
			getClient().getSession().write(systemMsg.serialize());
		}
		
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
	
	public int getHealth() {
		synchronized(objectMutex) {
			return health;
		}
	}

	public void setHealth(int health) {
		
		synchronized(objectMutex) {
			if(getPosture() == 13) {
					if(health > maxHealth)
						health = maxHealth;
					stopIncapTask();
					setIncapTask(null);
					this.health = health;
					notifyObservers(messageBuilder.buildUpdateHAMListDelta(), true);
					setPosture((byte) 0);
					setTurnRadius(1);
					setSpeedMultiplierBase(1);
					return;
			}
		}

		IoBuffer delta;
		synchronized(objectMutex) {
			if (this.health == health) return;
			if(health > maxHealth)
			{
				setHealth(maxHealth);
				return;	
			}
			setHamListCounter(getHamListCounter() + 1);
			delta = messageBuilder.buildHealthDelta(health);
			
			this.health = health;
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
			if (this.action == action) return;
			if(action > maxAction)
			{
				setAction(maxAction);
				return;	
			}
			setHamListCounter(getHamListCounter() + 1);
			delta = messageBuilder.buildActionDelta(action);
			this.action = action;
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
		    	if(this.maxHealth == maxHealth) return;
		    
			this.maxHealth = maxHealth;
			setMaxHAMListCounter(getMaxHAMListCounter() + 1);
			if(maxHealth < getHealth())
				setHealth(maxHealth);
			notifyObservers(messageBuilder.buildMaxHealthDelta(maxHealth), true);
		}
	}

	public int getMaxAction() {
		synchronized(objectMutex) {
			return maxAction;
		}
	}

	public void setMaxAction(int maxAction) {
		synchronized(objectMutex) {
		    	if(this.maxAction == maxAction) return;
		    
			this.maxAction = maxAction;
			setMaxHAMListCounter(getMaxHAMListCounter() + 1);
			if(maxAction < getAction())
				setAction(maxAction);
			notifyObservers(messageBuilder.buildMaxActionDelta(maxAction), true);
		}
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
	
	public void updateBuff(Buff buff) {
		buff.updateRemovalTask();
		synchronized(objectMutex) {
			setBuffListCounter(getBuffListCounter() + 1);
			notifyObservers(messageBuilder.buildUpdateBuffDelta(buff), true);
		}
	}	

	public void updateAllBuffs() {
		synchronized(objectMutex) {
			for(Buff buff : buffList.get()) {
				updateBuff(buff);
			}
		}
	}

	public boolean hasBuff(String buffName) {
		return getBuffByName(buffName) != null;
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
	
	public Buff getBuffByCRC(int crc) {
		synchronized(objectMutex) {
			for (Buff buff : buffList.get()) {
				if (buff.getBuffCRC() == crc) {
					return buff;
				}
			}
		}
		return null;
	}
	
	public void updateGroupInviteInfo() {
		
		if(getClient() == null || getClient().getSession() == null)
			return;
		
		getClient().getSession().write(messageBuilder.buildGroupInviteDelta(getInviteSenderId(), getInviteCounter(), getInviteSenderName()));
		
	}
	
	public void resetHAMList() {
		synchronized(objectMutex) {
			setHamListCounter(getHamListCounter() + 1);
			notifyObservers(messageBuilder.buildResetHAMListDelta(), true);
		}
	}
	
	public void updateHAMList() {
		synchronized(objectMutex) {
			setHamListCounter(getHamListCounter() + 1);
			notifyObservers(messageBuilder.buildUpdateHAMListDelta(), true);
		}
	}
	
	public SWGList<DamageOverTime> getDotList() {
		return dotList;
	}

	public void setDotList(SWGList<DamageOverTime> dotList) {
		this.dotList = dotList;
	}

	public void addDot(DamageOverTime dot) {
		dotList.get().add(dot);
	}
	
	public void removeDot(DamageOverTime dot) {
		dotList.get().remove(dot);
	}
	
	public DamageOverTime getDotByBuff(Buff buff) {
		
		List<DamageOverTime> dots = new ArrayList<DamageOverTime>(dotList.get());
		
		for(DamageOverTime dot : dots) {
			if(dot.getBuff() == buff)
				return dot;
		}
		
		return null;
		
	}
	
	public DamageOverTime getDotByName(String dotName) {
		
		List<DamageOverTime> dots = new ArrayList<DamageOverTime>(dotList.get());
		
		for(DamageOverTime dot : dots) {
			if(dot.getCommandName().equalsIgnoreCase(dotName))
				return dot;
		}
		
		return null;
		
	}


	public ScheduledFuture<?> getIncapTask() {
		return incapTask;
	}

	public void setIncapTask(ScheduledFuture<?> incapTask) {
		this.incapTask = incapTask;
	}

	public void stopIncapTask() {
		if(incapTask != null)
			incapTask.cancel(true);
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
	
	public boolean getStaticNPC() {
		return staticNPC;
	}
	
	public boolean setStaticNPC(boolean staticNPC) {
		return this.staticNPC = staticNPC;
	}
	
	public boolean getGroupDance() {
		synchronized(objectMutex) {
			return groupDance;
		}
	}

	public void setGroupDance(boolean groupDance) {
		synchronized(objectMutex) {
			this.groupDance = groupDance;
		}
	}

	public boolean toggleGroupDance() {
		synchronized(objectMutex) {
			groupDance = !groupDance;
			return groupDance;
		}
	}
	
	public void addSpectator(CreatureObject audienceMember) {
		synchronized(objectMutex) {
			performanceAudience.add(audienceMember);
		}
	}
	
	public void removeSpectator(CreatureObject audienceMember) {
		synchronized(objectMutex) {
			if (performanceAudience == null) { return; }
			if (audienceMember.getInspirationTick() != null)
				audienceMember.getInspirationTick().cancel(true);
			
			if(performanceAudience.contains(audienceMember))
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

		//if(this.performanceListenee == null)
			//getClient().getSession().write(messageBuilder.buildListenToId(0));
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
		if(getClient() != null)
			getClient().getSession().write(messageBuilder.buildListenToId(performanceListenee.getObjectId()));
	}


	
	@Override
	public void setPvPBitmask(int pvpBitmask) {
		super.setPvPBitmask(pvpBitmask);
		notifyObservers(new UpdatePVPStatusMessage(getObjectID(), getPvPBitmask(), getFaction()), false);
	}
	
	@Override
	public void setPvpStatus(int pvpBitmask, boolean add) {
		super.setPvpStatus(pvpBitmask, add);
		notifyObservers(new UpdatePVPStatusMessage(getObjectID(), getPvPBitmask(), getFaction()), false);
	}
	
	public byte getDifficulty() {
		synchronized(objectMutex) {
			return difficulty;
		}
	}
	
	public void setDifficulty(byte difficulty) {
		synchronized(objectMutex) {
			this.difficulty = difficulty;
		}
		
		notifyObservers(messageBuilder.buildDifficultyDelta(difficulty), true);
	}

	public void setPerformanceType(boolean isDance) {
		synchronized(objectMutex) {
			this.performanceType = isDance;
		}
	}
	
	public boolean getPerformanceType() {
		synchronized(objectMutex) {
			return this.performanceType;
		}
	}

	public void setFlourishCount(int flourishCount) {
		synchronized(objectMutex) {
			this.flourishCount = flourishCount;
		}
	}

	public int getFlourishCount() {
		synchronized(objectMutex) {
			return this.flourishCount;
		}
	}
	
	public void incFlourishCount() {
		synchronized(objectMutex) {
			this.flourishCount++;
		}
	}

	public int getEquipmentListUpdateCounter() {
		synchronized(objectMutex) {
			return equipmentListUpdateCounter;
		}
	}

	public void setEquipmentListUpdateCounter(int equipmentListUpdateCounter) {
		synchronized(objectMutex) {
			this.equipmentListUpdateCounter = equipmentListUpdateCounter;
		}
	}

	public int getAppearanceEquipmentListUpdateCounter() {
		synchronized(objectMutex) {
			return appearanceEquipmentListUpdateCounter;
		}
	}

	public void setAppearanceEquipmentListUpdateCounter(int appearanceEquipmentListUpdateCounter) {
		synchronized(objectMutex) {
			this.appearanceEquipmentListUpdateCounter = appearanceEquipmentListUpdateCounter;
		}
	}
	public int getXpBarValue() {
		synchronized(objectMutex) {
			return xpBarValue;
		}
	}

	public void setXpBarValue(int xpBarValue) {
		synchronized(objectMutex) {
			this.xpBarValue = xpBarValue;
		}
		getClient().getSession().write(messageBuilder.buildXPBarDelta(xpBarValue));
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
	
	public void setHologram(boolean isHologram) {
		synchronized(objectMutex) {
			this.hologram = isHologram;
		}
	}
	
	public boolean isHologram() {
		synchronized(objectMutex) {
			return hologram;
		}
	}

	public int getCoverCharge() {
		synchronized(objectMutex) {
			return coverCharge;
		}
	}

	public void setCoverCharge(int coverCharge) {
		synchronized (objectMutex) {
			this.coverCharge = coverCharge;
		}
	}

	public boolean isPerformingFlourish() {
		synchronized(objectMutex){
			return performingFlourish;
		}
	}

	public void setPerformingFlourish(boolean performingFlourish) {
		synchronized(objectMutex) {
			this.performingFlourish = performingFlourish;
		}
	}

	public boolean isInStealth() {
		synchronized(objectMutex) {
			return inStealth;
		}
	}

	public void setInStealth(boolean inStealth) {
		synchronized(objectMutex) {
			this.inStealth = inStealth;
		}
		notifyObservers(messageBuilder.buildStealthFlagDelta(inStealth), false);
	}

	public boolean isRadarVisible() {
		synchronized(objectMutex) {
			return radarVisible;
		}
	}

	public void setRadarVisible(boolean radarVisible) {
		synchronized(objectMutex) {
			this.radarVisible = radarVisible;
		}
		notifyObservers(messageBuilder.buildRadarVisibleFlagDelta(radarVisible), false);
	}

	public boolean isStationary() {
		synchronized(objectMutex) {
			return stationary;
		}
	}

	public void setStationary(boolean stationary) {
		synchronized(objectMutex) {
			this.stationary = stationary;
		}
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
	
	public void addCooldown(String ability, long duration) {
		if (cooldowns.containsKey(ability))
			cooldowns.remove(ability);
		
		Cooldown cd = new Cooldown(duration);
		cd.setRemovalTask(Executors.newScheduledThreadPool(1).schedule(new Runnable() {

			@Override
			public void run() {
				removeCooldown(ability);
			}
			
		}, duration, TimeUnit.MILLISECONDS));
		cooldowns.put(ability, cd);
	}
	
	public boolean hasCooldown(String ability) {
		if (cooldowns.containsKey(ability))
			return true;
		else
			return false;
	}
	
	public boolean removeCooldown(String ability) {
		if (cooldowns.containsKey(ability)) {
			cooldowns.remove(ability);
			return true;
		} else {
			return false;
		}
	}
	
	public Cooldown getCooldown(String ability) {
		if (cooldowns.containsKey(ability)) {
			return cooldowns.get(ability);
		} else {
			return null;
		}
	}
}
