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

import engine.resources.common.CRC;
import resources.objects.ObjectMessageBuilder;
import engine.resources.objects.SWGObject;
import engine.resources.objects.SkillMod;

import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;

public class CreatureMessageBuilder extends ObjectMessageBuilder {

	
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

		buffer.putInt(0);	// Base HAM List most likely unused in NGE
		buffer.putInt(0);
		
		if(creature.getSkills().isEmpty()) {
			buffer.putInt(0);	
			buffer.putInt(creature.getSkillsUpdateCounter());
		} else {
			buffer.putInt(creature.getSkills().size());	
			buffer.putInt(creature.getSkillsUpdateCounter());
			for(String skill : creature.getSkills())
				buffer.put(getAsciiString(skill));
		}
		int size = buffer.position();

		buffer.flip();
		buffer = createBaseline("CREO", (byte) 1, buffer, size);

		return buffer;

	}
	
	public IoBuffer buildBaseline3() {
		
		CreatureObject creature = (CreatureObject) object;
		IoBuffer buffer = bufferPool.allocate(300, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);

		buffer.putShort((short) 19);	// Object Count
		buffer.putFloat(1);
		buffer.put(getAsciiString(creature.getStfFilename()));
		buffer.putInt(0);	
		buffer.put(getAsciiString(creature.getStfName()));
		buffer.put(getUnicodeString(creature.getCustomName()));
		buffer.putInt(0x000F4240); // unk
		String factionCRC = creature.getFaction();
		if(factionCRC == null)
			buffer.putInt(0);
		else if(factionCRC.equals("neutral"))
			buffer.putInt(0);
		else
			buffer.putInt(CRC.StringtoCRC(factionCRC));
		
		buffer.putInt(creature.getFactionStatus());
		
		byte[] customizationData = creature.getCustomizationData();
		
		if(customizationData.length <= 0)
			buffer.putShort((short) 0);
		else {
			buffer.putShort((short) customizationData.length);
			buffer.put(customizationData);
		}
		buffer.putInt(1);	
		buffer.putInt(0);	// TANO Data
		buffer.putInt(0);	
		buffer.putInt(0x80);
		buffer.putInt(creature.getIncapTimer());
		buffer.putInt(0);
		buffer.putInt(0x3A98);
		
		buffer.put((byte) 1);
		buffer.put((byte) 0);
		buffer.put((byte) 1);

		buffer.putLong(creature.getOwnerId());

		buffer.putFloat(creature.getHeight());
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
		
		if(creature.getSkillMods().isEmpty()) {
			buffer.putInt(0);
			buffer.putInt(0);
		} else {
			buffer.putInt(creature.getSkillMods().size());
			buffer.putInt(creature.getSkillModsUpdateCounter());
			
			for(SkillMod skillMod : creature.getSkillMods()) {
				buffer.put((byte) 0);
				buffer.put(getAsciiString(skillMod.getSkillModString()));
				buffer.putInt(skillMod.getBase());
				buffer.putInt(skillMod.getModifier());
			}
		}
		buffer.putFloat(creature.getSpeedMultiplierBase());
		buffer.putFloat(creature.getSpeedMultiplierMod());
		buffer.putLong(0);		// unk
		
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

			for(String ability : creature.getAbilities()) {
				buffer.put((byte) 0);
				buffer.put(getAsciiString(ability));
				buffer.putInt(1);
			}
		}
		buffer.putInt(0);	// unk
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
		buffer.putInt(0x4E);	
		
		buffer.putInt(0);	// defenders list unused in NGE
		buffer.putInt(0);
		
		buffer.put(creature.getCombatFlag());

		buffer.putLong(0);
		buffer.putLong(0);
		buffer.putLong(0);	// Vehicle vars or TANO 6 vars TODO: research
		buffer.putLong(0);
		buffer.putInt(0);
		
		//buffer.putShort(creature.getLevel());
		buffer.putShort((short) 90);
		buffer.putInt(0xD007); // unk
		
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
		buffer.putLong(creature.getTargetId());
		buffer.put(creature.getMoodId());
		buffer.putInt(creature.getPerformanceCounter());
		buffer.putInt(creature.getPerformanceId());

		buffer.putInt(0);	// unks
		buffer.putInt(0);
		
		buffer.putInt(6);	// Current HAM
		buffer.putInt(0);

		buffer.putInt(20000);
		buffer.putInt(0);
		buffer.putInt(12500);
		buffer.putInt(0);
		buffer.putInt(0x2C01);
		buffer.putInt(0);
		
		buffer.putInt(6);	// Max HAM
		buffer.putInt(0);

		buffer.putInt(20000);
		buffer.putInt(0);
		buffer.putInt(12500);
		buffer.putInt(0);
		buffer.putInt(0x2C01);
		buffer.putInt(0);
		
		if(creature.getEquipmentList().isEmpty()) {
			buffer.putInt(0);
			buffer.putInt(0);
		} else {
			buffer.putInt(creature.getEquipmentList().size());
			buffer.putInt(0);
			
			for(SWGObject obj : creature.getEquipmentList()) {
				
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
		buffer.putShort((short) 0);
		buffer.put((byte) 1);

		buffer.putInt(0);	// Buff List todo later
		buffer.putInt(0);
		
		buffer.putShort((short) 0);
		buffer.putInt(0xFFFFFFFF);
		buffer.put((byte) 1);
		buffer.putShort((short) 0);

		if(creature.getAppearanceEquipmentList().isEmpty()) {
			buffer.putInt(0);
			buffer.putInt(0);
		} else {
			buffer.putInt(creature.getAppearanceEquipmentList().size());
			buffer.putInt(0);
			
			for(SWGObject obj : creature.getAppearanceEquipmentList()) {
				
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

		buffer.putInt(0);
		buffer.putInt(0);
		//buffer.put((byte) 0);
		
		
		
		/*buffer.putShort((short) 1); 
		buffer.putInt(0);
		buffer.putInt(0xFFFFFFFF); 
		buffer.putInt(1); 
		buffer.putInt(0);
		buffer.putInt(0); 
		buffer.putInt(0); 
		buffer.putShort((short) 0);
		buffer.put((byte)0);*/

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

	public IoBuffer buildSpeedModDelta(float speed) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putFloat(speed);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 4, (short) 1, (short) 4, buffer, size + 4);
		
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
	
	public IoBuffer buildTargetDelta(long targetId) {
		
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(targetId);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("CREO", (byte) 6, (short) 1, (short) 0x10, buffer, size + 4);
		
		return buffer;

	}

	
	@Override
	public void sendBaselines() {


		
	}

}
