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

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.common.CRC;

import java.util.ArrayList;
import java.util.List;

public class BaseSWGCommand implements Cloneable {
	
	private String commandName;
	private String clientEffectSelf;
	private String clientEffectTarget;
	private int commandCRC;
	private boolean isGmCommand = false;
	private String characterAbility;
	private int target;
	private int targetType;
	private boolean callOnTarget = false;
	private int commandGroup;
	private boolean disabled = true;
	private int maxRangeToTarget;
	private int godLevel;
	private int displayGroup;
	private boolean combatCommand = false;
	private int validWeapon;
	private int invalidWeapon;
	private String cooldownGroup;
	private float cooldown;
	private float executeTime;
	private float warmupTime;
	//private long[] invalidLocomotions; // Not tracked anywhere
	private byte[] invalidPostures;
	private long[] invalidStates;
	
	public BaseSWGCommand(String commandName) {
		setCommandName(commandName);
		setCommandCRC(CRC.StringtoCRC(commandName));
		
		try {
			List<Long> invalidStates = new ArrayList<Long>();
			List<Byte> invalidPostures = new ArrayList<Byte>();
			
			DatatableVisitor visitor2 = ClientFileManager.loadFile("datatables/command/command_table.iff", DatatableVisitor.class);
			
			for (int i = 0; i < visitor2.getRowCount(); i++) {
				if (visitor2.getObject(i, 0) != null) {
					if (((String) visitor2.getObject(i, 0)).equalsIgnoreCase(commandName)) {
						characterAbility = (String) visitor2.getObject(i, 7);
						target = (Integer) visitor2.getObject(i, 72);
						targetType = (Integer) visitor2.getObject(i, 73);
						callOnTarget = (Boolean) visitor2.getObject(i, 76);
						commandGroup = (Integer) visitor2.getObject(i, 77);
						disabled = (Boolean) visitor2.getObject(i, 78);
						maxRangeToTarget = (Integer) visitor2.getObject(i, 79);
						godLevel = (Integer) visitor2.getObject(i, 80);
						displayGroup = (Integer) visitor2.getObject(i, 81);
						combatCommand = (Boolean) visitor2.getObject(i, 82);
						validWeapon = (Integer) visitor2.getObject(i, 83);
						invalidWeapon = (Integer) visitor2.getObject(i, 84);
						cooldownGroup = (String) visitor2.getObject(i, 85);
						warmupTime = (Float) visitor2.getObject(i, 86);
						executeTime = (Float) visitor2.getObject(i, 87);
						cooldown = (Float) visitor2.getObject(i, 88);
						
						if (!((Boolean) visitor2.getObject(8))) invalidPostures.add((byte) 0);
						if (!((Boolean) visitor2.getObject(9))) invalidPostures.add((byte) 3);
						if (!((Boolean) visitor2.getObject(16))) invalidPostures.add((byte) 2);
						if (!((Boolean) visitor2.getObject(19))) invalidPostures.add((byte) 5);
						if (!((Boolean) visitor2.getObject(21))) invalidPostures.add((byte) 6);
						if (!((Boolean) visitor2.getObject(22))) invalidPostures.add((byte) 7);
						if (!((Boolean) visitor2.getObject(23))) invalidPostures.add((byte) 8);
						if (!((Boolean) visitor2.getObject(24))) invalidPostures.add((byte) 9);
						if (!((Boolean) visitor2.getObject(25))) invalidPostures.add((byte) 10);
						if (!((Boolean) visitor2.getObject(26))) invalidPostures.add((byte) 11);
						if (!((Boolean) visitor2.getObject(27))) invalidPostures.add((byte) 12);
						if (!((Boolean) visitor2.getObject(28))) invalidPostures.add((byte) 13);
						if (!((Boolean) visitor2.getObject(29))) invalidPostures.add((byte) 14);
						if (!((Boolean) visitor2.getObject(30))) invalidPostures.add((byte) 4);
						
						if (!((Boolean) visitor2.getObject(32))) invalidStates.add(1L);
						if (!((Boolean) visitor2.getObject(33))) invalidStates.add(2L);
						if (!((Boolean) visitor2.getObject(34))) invalidStates.add(4L);
						if (!((Boolean) visitor2.getObject(35))) invalidStates.add(8L);
						if (!((Boolean) visitor2.getObject(36))) invalidStates.add(16L);
						if (!((Boolean) visitor2.getObject(37))) invalidStates.add(32L);
						if (!((Boolean) visitor2.getObject(38))) invalidStates.add(64L);
						if (!((Boolean) visitor2.getObject(39))) invalidStates.add(128L);
						if (!((Boolean) visitor2.getObject(40))) invalidStates.add(256L);
						if (!((Boolean) visitor2.getObject(41))) invalidStates.add(512L);
						if (!((Boolean) visitor2.getObject(42))) invalidStates.add(1024L);
						if (!((Boolean) visitor2.getObject(43))) invalidStates.add(2048L);
						if (!((Boolean) visitor2.getObject(44))) invalidStates.add(4096L);
						if (!((Boolean) visitor2.getObject(45))) invalidStates.add(8192L);
						if (!((Boolean) visitor2.getObject(46))) invalidStates.add(16384L);
						if (!((Boolean) visitor2.getObject(47))) invalidStates.add(32768L);
						if (!((Boolean) visitor2.getObject(48))) invalidStates.add(65536L);
						if (!((Boolean) visitor2.getObject(49))) invalidStates.add(131072L);
						if (!((Boolean) visitor2.getObject(50))) invalidStates.add(262144L);
						if (!((Boolean) visitor2.getObject(51))) invalidStates.add(524288L);
						if (!((Boolean) visitor2.getObject(52))) invalidStates.add(1048576L);
						if (!((Boolean) visitor2.getObject(53))) invalidStates.add(2097152L);
						if (!((Boolean) visitor2.getObject(54))) invalidStates.add(4194304L);
						if (!((Boolean) visitor2.getObject(55))) invalidStates.add(8388608L);
						if (!((Boolean) visitor2.getObject(56))) invalidStates.add(16777216L);
						if (!((Boolean) visitor2.getObject(57))) invalidStates.add(33554432L);
						if (!((Boolean) visitor2.getObject(58))) invalidStates.add(67108864L);
						if (!((Boolean) visitor2.getObject(59))) invalidStates.add(134217728L);
						if (!((Boolean) visitor2.getObject(60))) invalidStates.add(268435456L);
						if (!((Boolean) visitor2.getObject(61))) invalidStates.add(536870912L);
						if (!((Boolean) visitor2.getObject(62))) invalidStates.add(1073741824L);
						if (!((Boolean) visitor2.getObject(63))) invalidStates.add(2147483648L);
						if (!((Boolean) visitor2.getObject(64))) invalidStates.add(4294967296L);
						if (!((Boolean) visitor2.getObject(65))) invalidStates.add(8589934592L);
						if (!((Boolean) visitor2.getObject(66))) invalidStates.add(17179869184L);
						if (!((Boolean) visitor2.getObject(67))) invalidStates.add(34359738368L);
						if (!((Boolean) visitor2.getObject(68))) invalidStates.add(68719476736L);
						if (!((Boolean) visitor2.getObject(69))) invalidStates.add(137438953472L);
						if (!((Boolean) visitor2.getObject(70))) invalidStates.add(274877906944L);
						
						this.invalidPostures = (byte[]) invalidPostures.toArray();
						this.invalidStates = (long[]) invalidStates.toArray();
						
						break;
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public String getCommandName() {
		return commandName;
	}
	
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	
	public String getClientEffectSelf() {
		return clientEffectSelf;
	}
	
	public void setClientEffectSelf(String clientEffectSelf) {
		this.clientEffectSelf = clientEffectSelf;
	}
	
	public String getClientEffectTarget() {
		return clientEffectTarget;
	}
	
	public void setClientEffectTarget(String clientEffectTarget) {
		this.clientEffectTarget = clientEffectTarget;
	}
	
	public int getCommandCRC() {
		return commandCRC;
	}
	
	public void setCommandCRC(int commandCRC) {
		this.commandCRC = commandCRC;
	}
	
    	public Object clone() throws CloneNotSupportedException {
        	return super.clone();
    	}
    	
	public boolean isGmCommand() {
		return (godLevel > 0);
	}
	
	public void setGmCommand(boolean isGmCommand) {
		this.isGmCommand = isGmCommand;
	}
	
	public String getCharacterAbility() {
		return characterAbility;
	}
	
	public void setcharacterAbility(String characterAbility) {
		this.characterAbility = characterAbility;
	}
	
	public byte[] getInvalidPostures() {
		return invalidPostures;
	}
	
	public long[] getInvalidStates() {
		return invalidStates;
	}
	
	public int getTarget() {
		return target;
	}
	
	public void setTarget(int target) {
		this.target = target;
	}
	
	public void getTargetType() {
		return targetType;
	}
	
	public void setTargetType() {
		return targetType;
	}
	
	public boolean shouldCallOnTarget() {
		return callOnTarget;
	}
	
	public void setCallOnTarget(boolean callOnTarget) {
		this.callOnTarget = callOnTarget;
	}
	
	public int getCommandGroup() {
		return commandGroup;
	}
	
	public void setCommandGroup(int commandGroup) {
		this.commandGroup = commandGroup;
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public int getMaxRangeToTarget() {
		return maxRangeToTarget;
	}
	
	public void setMaxRangeToTarget(int maxRangeToTarget) {
		this.maxRangeToTarget = maxRangeToTarget;
	}
	
	public int getGodLevel() {
		return godLevel;
	}
	
	public void setGodLevel(int godLevel) {
		this.godLevel = godLevel;
	}
	
	public int getDisplayGroup() {
		return displayGroup;
	}
	
	public void setDisplayGroup(int displayGroup) {
		this.displayGroup = displayGroup;
	}
	
	public boolean isCombatCommand() {
		return combatCommand;
	}
	
	public void setCombatCommand(boolean combatCommand) {
		this.combatCommand = combatCommand;
	}
	
	public int getValidWeapon() {
		return validWeapon;
	}
	
	public void setValidWeapon(int validWeapon) {
		this.validWeapon = validWeapon;
	}
	
	public int getInvalidWeapon() {
		return invalidWeapon;
	}
	
	public void setInvalidWeapon(int invalidWeapon) {
		this.invalidWeapon = invalidWeapon;
	}
	
	public String getCooldownGroup() {
		return cooldownGroup;
	}
	
	public void setCooldownGroup(String cooldownGroup) {
		this.cooldownGroup = cooldownGroup;
	}
	
	public float getCooldown() {
		return cooldown;
	}
	
	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}
	
	public float getExecuteTime() {
		return executeTime;
	}
	
	public void setExecuteTime(float executeTime) {
		this.executeTime = executeTime;
	}
	
	public float getWarmupTime() {
		return warmupTime;
	}
	
	public void setWarmupTime(float warmupTime) {
		this.warmupTime = warmupTime;
	}
	
}
