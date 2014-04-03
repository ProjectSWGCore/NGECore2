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

import java.nio.ByteOrder;


import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.Persistent;

import engine.resources.common.CRC;
import resources.objects.Buff;
import resources.objects.ObjectMessageBuilder;
import resources.objects.SkillMod;
import engine.resources.objects.SWGObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;

@Persistent
public class CreatureMessageBuilder extends ObjectMessageBuilder {

	public CreatureMessageBuilder() { }
	
	public CreatureMessageBuilder(CreatureObject creatureObject) {

		setObject(creatureObject);

	}

	public IoBuffer buildBaseline1() {
		CreatureObject creature = (CreatureObject) object;
		IoBuffer buffer = bufferPool.allocate(26, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short) 4);
		buffer.putInt(creature.getBankCredits());
		buffer.putInt(creature.getCashCredits());

		buffer.putInt(6);	// Base HAM List - so leveling knows the base HAM, before equipment etc
		buffer.putInt(0);
		buffer.putInt(creature.getHealth());
		buffer.putInt(0);
		buffer.putInt(creature.getAction());
		buffer.putInt(0);
		buffer.putInt(0x2C01);
		buffer.putInt(0);
		
		if(creature.getSkills().isEmpty()) {
			buffer.putInt(0);	
			buffer.putInt(creature.getSkillsUpdateCounter());
		} else {
			buffer.putInt(creature.getSkills().size());	
			buffer.putInt(creature.getSkillsUpdateCounter());
			for(String skill : creature.getSkills().get())
				buffer.put(getAsciiString(skill));
		}
		int size = buffer.position();

		buffer.flip();
		buffer = createBaseline("CREO", (byte) 1, buffer, size);

		return buffer;

	}
	
	public IoBuffer buildBaseline3() {
		
		CreatureObject creature = (CreatureObject) object;
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		if(creature.getStfName() == null || creature.getStfFilename() == null) {
			creature.setStfName("");
			creature.setStfFilename("");
		}

		buffer.putShort((short) 19);	// Object Count
		buffer.putFloat(1);
		buffer.put(getAsciiString(creature.getStfFilename()));
		buffer.putInt(0);	
		buffer.put(getAsciiString(creature.getStfName()));
		if (creature.getCustomName() == null) { creature.setCustomName(""); }//Not all CreatureObjects have CustomName (Shuttles)
		buffer.put(getUnicodeString(creature.getCustomName()));
	//	buffer.putInt(0x000F4240); // volume
		buffer.putInt(1);
		buffer.putInt(CRC.StringtoCRC(creature.getFaction()));
		
		buffer.putInt(creature.getFactionStatus());
		
		byte[] customizationData = creature.getCustomization();
		
		if(customizationData == null || customizationData.length <= 0) // Shuttles have no customization data
			buffer.putShort((short) 0);
		else {
			buffer.putShort((short) customizationData.length);
			buffer.put(customizationData);
		}
		//buffer.putInt(1);	
		buffer.putInt(0);	// TANO Data
		buffer.putInt(0);	
		buffer.putInt(creature.getOptionsBitmask()); // 0x80 = Player, 0x08 = Quest NPC, 
		//buffer.putInt(0x80);
		buffer.putInt(creature.getIncapTimer());
		buffer.putInt(0);
		buffer.putInt(0x3A98);
		
		buffer.put((byte) 1);
		buffer.put((byte) creature.getPosture());
		buffer.put((byte) 0);

		buffer.putLong(creature.getOwnerId());
		
		float height = creature.getHeight();
		//if (height < 0.7 || height > 1.5)
		//	height = 1;
		buffer.putFloat(height);
		buffer.putInt(0); // battle fatigue
		buffer.putLong(creature.getStateBitmask());
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("CREO", (byte) 3, buffer, size);
		return buffer;

	}

	public IoBuffer buildBaseline4() {
		CreatureObject creature = (CreatureObject) object;

		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short) 0x10);
		buffer.putFloat(creature.getAccelerationMultiplierBase());
		buffer.putFloat(creature.getAccelerationMultiplierMod());
		
		buffer.putInt(0);	// HAM Encumberance List unused in NGE
		buffer.putInt(0);
		
		buffer.putInt(creature.getSkillMods().size());
		buffer.putInt(creature.getSkillMods().getUpdateCounter());
		for (SkillMod skillMod : creature.getSkillMods().values()) {
			buffer.put((byte) 0);
			buffer.put(getAsciiString(skillMod.getName()));
			buffer.put(skillMod.getBytes());
		}
		
		buffer.putFloat(creature.getSpeedMultiplierBase());
		buffer.putFloat(creature.getSpeedMultiplierMod());
		
		if(creature.getPerformanceListenee() != null)
			buffer.putLong(creature.getPerformanceListenee().getObjectId());
		else
			buffer.putLong(0);
		
		buffer.putFloat(creature.getRunSpeed());
		
		buffer.putFloat(creature.getSlopeModAngle());
		buffer.putFloat(creature.getSlopeModPercent());
		buffer.putFloat(creature.getTurnRadius());

		buffer.putFloat(creature.getWalkSpeed());
		buffer.putFloat(creature.getWaterModPercent());
		
		buffer.putInt(0);	// MissionCritical objects todo later
		buffer.putInt(0);

		if(creature.getAbilities().isEmpty()) {
			buffer.putInt(0);	
			buffer.putInt(creature.getAbilitiesUpdateCounter());
		} else {
			buffer.putInt(creature.getAbilities().size());	
			buffer.putInt(creature.getAbilitiesUpdateCounter());

			for(String ability : creature.getAbilities().get()) {
				buffer.put((byte) 0);
				buffer.put(getAsciiString(ability));
				buffer.putInt(1);
			}
		}
		buffer.putInt(creature.getXpBarValue());	// xp bar value
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("CREO", (byte) 4, buffer, size);

		return buffer;

	}

	public IoBuffer buildBaseline6() {
		CreatureObject creature = (CreatureObject) object;

		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short) 0x23);
		buffer.putInt(0x43); // serverId
		
		buffer.putShort((short) 0); // detaiLStfFilename
		buffer.putInt(0); // detailStfSpacer
		buffer.putShort((short) 0); // detailStfName
		
		// TANO 6 lists TODO: research
		
		buffer.put(creature.getCombatFlag());
		
		buffer.putLong(0); //List<Long> possibly defenders list
		buffer.putInt(0); //Int
		buffer.putLong(0); //List<Long>
		buffer.putLong(0);	//List<Int>
		buffer.putLong(0); //List<Unknown>
		
		buffer.putShort(creature.getLevel());
		buffer.putInt(creature.getGrantedHealth()); // From player_level.iff.  Ranges from 0-2000 as you level, consistent with that table.
		
		//0A
		if(creature.getCurrentAnimation() == null || creature.getCurrentAnimation().length() == 0) 
			buffer.putShort((short) 0);
		else
			buffer.put(getAsciiString(creature.getCurrentAnimation()));
		
		if(creature.getMoodAnimation() == null || creature.getMoodAnimation().length() == 0) 
			buffer.put(getAsciiString("neutral"));
		else
			buffer.put(getAsciiString(creature.getMoodAnimation()));
		
		buffer.putLong(creature.getWeaponId());
		
		buffer.putLong(creature.getGroupId());
		buffer.putLong(creature.getInviteSenderId());
		if(creature.getInviteSenderName() == null || creature.getInviteSenderName().length() == 0)
			buffer.putShort((short) 0);
		else
			buffer.put(getAsciiString(creature.getInviteSenderName()));
	
		buffer.putLong(creature.getInviteCounter());

		buffer.putInt(creature.getGuildId());
		
		buffer.putLong(creature.getLookAtTarget()); // lookAtTarget 0x10
		buffer.putLong(creature.getIntendedTarget()); // intendedTarget 0x11
		buffer.put(creature.getMoodId());
		buffer.putInt(creature.getPerformanceCounter());
		/*
		 * minor dilemma: performance ID is needed for XP calculation, but it can't be sent
		 * in the CREO, otherwise the evul note bubbles appear
		 */ 
		buffer.putInt((creature.getPerformanceType()) ? 0 : creature.getPerformanceId());
		
		buffer.putInt(6);	// Current HAM
		buffer.putInt(creature.getHamListCounter());

		buffer.putInt(creature.getHealth());
		//1A
		buffer.putInt(0);
		buffer.putInt(creature.getAction());
		buffer.putInt(0);
		buffer.putInt(0x2C01);
		buffer.putInt(0);
		
		buffer.putInt(6);	// Max HAM
		buffer.putInt(creature.getMaxHAMListCounter());

		buffer.putInt(creature.getMaxHealth());
		buffer.putInt(0);
		buffer.putInt(creature.getMaxAction());
		buffer.putInt(0);
		buffer.putInt(0x2C01);
		buffer.putInt(0);

		
		if(creature.getEquipmentList().isEmpty()) {
			buffer.putInt(0);
			buffer.putInt(creature.getEquipmentListUpdateCounter());
		} else {
			buffer.putInt(creature.getEquipmentList().size());
			buffer.putInt(creature.getEquipmentListUpdateCounter());
			
			for(SWGObject obj : creature.getEquipmentList().get()) {
				
				if(obj instanceof TangibleObject && !(obj instanceof WeaponObject)) {
					TangibleObject tangible = (TangibleObject) obj;
					if(tangible.getCustomization() == null || tangible.getCustomization().length == 0) {
						buffer.putShort((short) 0);
					} else {
						buffer.putShort((short) tangible.getCustomization().length);
						buffer.put(tangible.getCustomization());
					}
					buffer.putInt(tangible.getArrangementId());
					buffer.putLong(tangible.getObjectID());
					buffer.putInt(CRC.StringtoCRC(tangible.getTemplate()));
					buffer.put((byte) 0);
				} else if(obj instanceof WeaponObject) {
					WeaponObject weapon = (WeaponObject) obj;
					if(weapon.getCustomization() == null || weapon.getCustomization().length == 0) {
						buffer.putShort((short) 0);
					} else {
						buffer.putShort((short) weapon.getCustomization().length);
						buffer.put(weapon.getCustomization());
					}
					buffer.putInt(weapon.getArrangementId());
					buffer.putLong(weapon.getObjectID());
					buffer.putInt(CRC.StringtoCRC(weapon.getTemplate()));

					buffer.put((byte) 1);
					buffer.put(weapon.getMessageBuilder().buildBaseline3());
					buffer.put(weapon.getMessageBuilder().buildBaseline6());
				} else {
					System.out.println("Bad equipment object");
				}
				
			}

		}
		
		buffer.putShort((short) 0); // costume
		//buffer.put(getAsciiString("appearance/gungan_m.sat"));
		buffer.put((byte) (creature.isInStealth() ? 0 : 1));

		if(creature.getBuffList().isEmpty()) {
			buffer.putInt(0);	
			buffer.putInt(creature.getBuffListCounter());
		} else {
			buffer.putInt(creature.getBuffList().size() + 1);	
			buffer.putInt(creature.getBuffListCounter());
			
			buffer.put((byte) 0);
			//buffer.putInt(0x2098793D);
			buffer.putInt(0);
			buffer.putInt(-1);
			buffer.putInt(0);
			buffer.putInt(0);
			buffer.putLong(creature.getObjectID());
					
			PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
			
			for(Buff buff : creature.getBuffList().get()) {
				
				if(player != null)
					buff.setTotalPlayTime((int) (player.getTotalPlayTime() + (System.currentTimeMillis() - player.getLastPlayTimeUpdate()) / 1000));
				else 
					buff.setTotalPlayTime(0);
				buffer.put((byte) 1);
				buffer.putInt(0);
				buffer.putInt(CRC.StringtoCRC(buff.getBuffName().toLowerCase()));
				if(buff.getDuration() > 0) {
					buffer.putInt((int) (buff.getTotalPlayTime() + buff.getRemainingDuration()));		
					buffer.putInt(0);
					buffer.putInt((int) buff.getDuration());
				} else {
					buffer.putInt(-1);
					buffer.putInt(0);
					buffer.putInt(0);
				}
				
				buffer.putLong(creature.getObjectID());

			}
			
			buffer.putInt(1);
				
		}
		
		buffer.put((byte) (creature.isStationary() ? 1 : 0)); // if the server accepts transforms from the object
		buffer.put(creature.getDifficulty());
		
		if(creature.isHologram())
			buffer.putInt(0);
		else
			buffer.putInt(0xFFFFFFFF);

		buffer.put((byte) (creature.isRadarVisible() ? 1 : 0));
		buffer.put((byte) 0); // no effect for 1?
		buffer.put((byte) 0); // no effect for 1?
		

		if(creature.getAppearanceEquipmentList().isEmpty()) {
			buffer.putInt(0);
			buffer.putInt(creature.getAppearanceEquipmentListUpdateCounter());
		} else {
			buffer.putInt(creature.getAppearanceEquipmentList().size());
			buffer.putInt(creature.getAppearanceEquipmentListUpdateCounter());
			
			for(SWGObject obj : creature.getAppearanceEquipmentList().get()) {
				
				if(obj instanceof TangibleObject) {
					TangibleObject tangible = (TangibleObject) obj;
					if(tangible.getCustomization() == null || tangible.getCustomization().length == 0) {
						buffer.putShort((short) 0);
					} else {
						buffer.putShort((short) tangible.getCustomization().length);
						buffer.put(tangible.getCustomization());
					}
					buffer.putInt(tangible.getArrangementId());
					buffer.putLong(tangible.getObjectID());
					buffer.putInt(CRC.StringtoCRC(tangible.getTemplate()));
					buffer.put((byte) 0);
				} else {
					System.out.println("Bad appearance equipment object");
				}
				
			}
		}

		buffer.putLong(0); // unk long

		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		
		buffer.flip();
		buffer = createBaseline("CREO", (byte) 6, buffer, size);

		return buffer;

	}

	public IoBuffer buildBaseline8() {
		
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		int size = buffer.position();
		buffer.flip();
		buffer = createBaseline("CREO", (byte) 8, buffer, size);

		return buffer;
		
	}

	public IoBuffer buildBaseline9() {
		
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 0);
		int size = buffer.position();
		buffer.flip();
		buffer = createBaseline("CREO", (byte) 9, buffer, size);
		
		return buffer;

	}
	
	public IoBuffer buildCashCreditsDelta(int credits) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(credits);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 1, (short) 1, (short) 1, buffer, size + 4);
		
		return buffer;

	}

	public IoBuffer buildBankCreditsDelta(int credits) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(credits);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 1, (short) 1, (short) 0, buffer, size + 4);
		
		return buffer;

	}

	
	public IoBuffer buildPostureDelta(byte posture) {
		
		IoBuffer buffer = bufferPool.allocate(1, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(posture);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 3, (short) 1, (short) 0x0D, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildFactionDelta(String faction) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(CRC.StringtoCRC(faction));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 3, (short) 1, (short) 0x04, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildFactionStatusDelta(int factionStatus) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(factionStatus);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 3, (short) 1, (short) 0x05, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildOptionMaskDelta(int optionMask) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(optionMask);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 3, (short) 1, (short) 0x08, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildHeightDelta(float height) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(height);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 3, (short) 1, (short) 0x10, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildStateDelta(long state) {
		
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(state);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 3, (short) 1, (short) 0x12, buffer, size + 4);
		
		return buffer;

	}

	public IoBuffer buildSpeedModBaseDelta(float speed) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(speed);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 4, (short) 1, (short) 4, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildSpeedModDelta(float speedModifier) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(speedModifier);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 4, (short) 1, (short) 5, buffer, size + 4);
		
		return buffer;

	}


	public IoBuffer buildTurnRadiusDelta(float turnRadius) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(turnRadius);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 4, (short) 1, (short) 0x0A, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildCombatFlagDelta(byte combatFlag) {
		
		IoBuffer buffer = bufferPool.allocate(1, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(combatFlag);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x02, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildGrantedHealthDelta(int grantedHealth) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(grantedHealth);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 9, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildCurrentAnimationDelta(String currentAnimation) {
		IoBuffer buffer = bufferPool.allocate(getAsciiString(currentAnimation).length, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(getAsciiString(currentAnimation));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 10, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildLevelDelta(short level) {
		
		IoBuffer buffer = bufferPool.allocate(2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort(level);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x08, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildWeaponIdDelta(long weaponId) {
		
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(weaponId);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x0C, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildGroupIdDelta(long groupId) {
		
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(groupId);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x0D, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildGroupInviteDelta(long inviteSenderId, String inviteSenderName, long inviteCounter) {
		
		IoBuffer buffer = bufferPool.allocate(18 + inviteSenderName.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(inviteSenderId);
		buffer.put(getAsciiString(inviteSenderName)); 	
		buffer.putLong(inviteCounter);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x0E, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildLookAtTargetDelta(long targetId) {
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(targetId);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x10, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildIntendedTargetDelta(long targetId) {
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(targetId);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x11, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildHealthDelta(int health) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(15, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getHamListCounter());
		//System.out.println("HAM list counter: " + creature.getHamListCounter());
		buffer.put((byte) 2);
		buffer.putShort((short) 0);
		buffer.putInt(health);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x15, buffer, size + 4);
		
		return buffer;
		
	}
	
	public IoBuffer buildActionDelta(int action) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(15, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getHamListCounter());
		//System.out.println("HAM list counter: " + creature.getHamListCounter());
		buffer.put((byte) 2);
		buffer.putShort((short) 2);
		buffer.putInt(action);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x15, buffer, size + 4);
		
		return buffer;
		
	}	
	
	public IoBuffer buildMaxHealthDelta(int health) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(15, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getMaxHAMListCounter());
		buffer.put((byte) 2);
		buffer.putShort((short) 0);
		buffer.putInt(health);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x16, buffer, size + 4);
		
		return buffer;
		
	}
	
	public IoBuffer buildMaxActionDelta(int action) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(15, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getMaxHAMListCounter());
		buffer.put((byte) 2);
		buffer.putShort((short) 2);
		buffer.putInt(action);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x16, buffer, size + 4);
		
		return buffer;
		
	}
	
	public IoBuffer buildAddBuffDelta(Buff buff) {
		
		CreatureObject creature = (CreatureObject) object;
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		
		IoBuffer buffer = bufferPool.allocate(37, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getBuffListCounter());
		if(player != null)
			buff.setTotalPlayTime((int) (player.getTotalPlayTime() + (System.currentTimeMillis() - player.getLastPlayTimeUpdate()) / 1000));
		else
			buff.setTotalPlayTime(0);
		buffer.put((byte) 0);
		buffer.put(buff.getBytes());
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x1A, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildRemoveBuffDelta(Buff buff) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(37, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getBuffListCounter());
		buffer.put((byte) 1);
		buffer.put(buff.getBytes());
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x1A, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildUpdateBuffDelta(Buff buff) {
		
		CreatureObject creature = (CreatureObject) object;
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		
		IoBuffer buffer = bufferPool.allocate(37, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getBuffListCounter());
		if(player != null)
			buff.setTotalPlayTime((int) (player.getTotalPlayTime() + (System.currentTimeMillis() - player.getLastPlayTimeUpdate()) / 1000));
		else
			buff.setTotalPlayTime(0);
		buffer.put((byte) 2);
		buffer.put(buff.getBytes());
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x1A, buffer, size + 4);
		
		return buffer;

	}

	
	public IoBuffer buildGroupInviteDelta(long inviteSenderId, long inviteCounter, String leaderName) {
		
		IoBuffer buffer = bufferPool.allocate(18 + leaderName.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putLong(inviteSenderId);
		buffer.put(getAsciiString(leaderName));
		buffer.putLong(inviteCounter);
		
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x0E, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildAddSkillDelta(String name) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(11 + name.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getSkillsUpdateCounter());
		buffer.put((byte) 1);
		buffer.put(getAsciiString(name));
		
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 1, (short) 1, (short) 3, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildRemoveSkillDelta(String name) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(11 + name.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getSkillsUpdateCounter());
		buffer.put((byte) 0);
		buffer.put(getAsciiString(name));
		
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 1, (short) 1, (short) 3, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildAddAbilityDelta(String name) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(15 + name.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getAbilitiesUpdateCounter());
		buffer.put((byte) 0);
		buffer.put(getAsciiString(name));
		buffer.putInt(1);
		
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 4, (short) 1, (short) 0x0E, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildRemoveAbilityDelta(String name) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(15 + name.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getAbilitiesUpdateCounter());
		buffer.put((byte) 1);
		buffer.put(getAsciiString(name));
		buffer.putInt(1);
		
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 4, (short) 1, (short) 0x0E, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildResetHAMListDelta() {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(35, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(1);
		buffer.putInt(creature.getHamListCounter());
		buffer.put((byte) 3);
		buffer.putShort((short) 6);
		buffer.putInt(creature.getHealth());
		buffer.putInt(0);
		buffer.putInt(creature.getAction());
		buffer.putInt(0);
		buffer.putInt(0x2C01);
		buffer.putInt(0);
		
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x15, buffer, size + 4);

		return buffer;

	}
	
	public IoBuffer buildUpdateHAMListDelta() {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(22, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(2);
		buffer.putInt(creature.getHamListCounter());
		buffer.put((byte) 2);
		buffer.putShort((short) 0);
		buffer.putInt(creature.getHealth());
		buffer.put((byte) 2);
		buffer.putShort((short) 2);
		buffer.putInt(creature.getAction());
		
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x15, buffer, size + 4);

		return buffer;

	}
	
	public IoBuffer buildCustomNameDelta(String customName) {
		IoBuffer buffer = bufferPool.allocate(getUnicodeString(customName).length, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(getUnicodeString(customName));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 3, (short) 1, (short) 2, buffer, size + 4);
		return buffer;
		
	}
	
	public IoBuffer buildMoodAnimationDelta(String moodAnimation) {
		IoBuffer buffer = bufferPool.allocate(getAsciiString(moodAnimation).length, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(getAsciiString(moodAnimation));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x0B, buffer, size + 4);
		return buffer;
		
	}
	
	public IoBuffer buildListenToId(long creatureObjectId) {
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(creatureObjectId);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 4, (short) 1, (short) 0x09, buffer, size + 4);
		return buffer;
	}

	public IoBuffer buildPerformanceId(int performanceId) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(performanceId);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x14, buffer, size + 4);
		return buffer;	
	}

	public IoBuffer buildPerformanceCounter(int performanceCounter) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(performanceCounter);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x13, buffer, size + 4);
		return buffer;	
	}

	public IoBuffer buildStartPerformance(boolean doStart) {
		IoBuffer buffer = bufferPool.allocate(1, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) ((doStart) ? 1 : 0));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x1B, buffer, size + 4);
		return buffer;	
	}
	
	
	public IoBuffer buildDifficultyDelta(byte difficulty) {
		IoBuffer buffer = bufferPool.allocate(1, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(difficulty);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x1C, buffer, size + 4);
		return buffer;
	}

	public IoBuffer buildAddEquipmentDelta(TangibleObject item) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(496, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getEquipmentListUpdateCounter());
		buffer.put((byte) 1);
		buffer.putShort((short) creature.getEquipmentList().indexOf(item));
		if(item.getCustomization() == null || item.getCustomization().length == 0) {
			buffer.putShort((short) 0);
		} else {
			buffer.putShort((short) item.getCustomization().length);
			buffer.put(item.getCustomization());
		}
		buffer.putInt(item.getArrangementId());
		buffer.putLong(item.getObjectID());
		buffer.putInt(CRC.StringtoCRC(item.getTemplate()));
		if(item instanceof WeaponObject) {
			WeaponObject weapon = (WeaponObject) item;
			buffer.put((byte) 1);
			buffer.put(weapon.getMessageBuilder().buildBaseline3());
			buffer.put(weapon.getMessageBuilder().buildBaseline6());
		} else {
			buffer.put((byte) 0);
		}
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x17, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildRemoveEquipmentDelta(TangibleObject item) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(11, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getEquipmentListUpdateCounter());
		buffer.put((byte) 0);
		buffer.putShort((short) creature.getEquipmentList().indexOf(item));
		
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x17, buffer, size + 4);
		
		return buffer;
		
	}
	
	public IoBuffer buildAddAppearanceEquipmentDelta(TangibleObject item) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getAppearanceEquipmentListUpdateCounter());
		buffer.put((byte) 1);
		buffer.putShort((short) creature.getAppearanceEquipmentList().indexOf(item));
		if(item.getCustomization() == null || item.getCustomization().length == 0) {
			buffer.putShort((short) 0);
		} else {
			buffer.putShort((short) item.getCustomization().length);
			buffer.put(item.getCustomization());
		}
		buffer.putInt(item.getArrangementId());
		buffer.putLong(item.getObjectID());
		buffer.putInt(CRC.StringtoCRC(item.getTemplate()));
		buffer.put((byte) 0);
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x21, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildRemoveAppearanceEquipmentDelta(TangibleObject item) {
		
		CreatureObject creature = (CreatureObject) object;
		
		IoBuffer buffer = bufferPool.allocate(11, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(1);
		buffer.putInt(creature.getAppearanceEquipmentListUpdateCounter());
		buffer.put((byte) 0);
		buffer.putShort((short) creature.getAppearanceEquipmentList().indexOf(item));
		
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x21, buffer, size + 4);
		
		return buffer;
		
	}
	
	public IoBuffer buildXPBarDelta(int xpBarValue) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(xpBarValue);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 4, (short) 1, (short) 0x0F, buffer, size + 4);
		return buffer;	
	}
	
	// TODO: This seems to be somehow related to chairs as well.
	public IoBuffer buildOwnerIdDelta(long ownerId) {
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(ownerId);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 3, (short) 1, (short) 0x0D, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildStealthFlagDelta(boolean flag) {
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) (flag ? 0 : 1));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x19, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildRadarVisibleFlagDelta(boolean flag) {
		IoBuffer buffer = bufferPool.allocate(1, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put((byte) (flag ? 1 : 0));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x1E, buffer, size + 4);
		return buffer;
	}

	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		switch (viewType) {
			case 1:
			case 3:
			case 4: {
				switch(updateType) {
					case 3: {
						buffer = createDelta("CREO", (byte) 4, (short) 1, (byte) 3, buffer.flip(), buffer.array().length + 4);
						
						if (object.getClient() != null && object.getClient().getSession() != null) {
							object.getClient().getSession().write(buffer);
						}
						break;
					}
					
				}
			}
			case 6:
			case 8:
			case 9:
			default:
			{
				return;
			}
		}
	}
	
	@Override
	public void sendBaselines() {
		
	}
	
}
