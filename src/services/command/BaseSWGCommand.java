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
	private String characterAbility = null;
	private int target;
	private int targetType;
	private boolean callOnTarget = false;
	private int commandGroup;
	private boolean disabled = true;
	private float maxRangeToTarget;
	private int godLevel;
	private int displayGroup;
	private boolean combatCommand = false;
	private int validWeapon;
	private int invalidWeapon;
	private String cooldownGroup;
	private float cooldown;
	private float executeTime;
	private float warmupTime;
	//private Long[] invalidLocomotions; // Not tracked anywhere
	private Byte[] invalidPostures;
	private Long[] invalidStates;
	
	public BaseSWGCommand(String commandName) {
		setCommandName(commandName);
		setCommandCRC(CRC.StringtoCRC(commandName));
		
		try {
			String[] tableArray = new String[] {
			"client_command_table", "client_command_table_ground", "client_command_table_space",
			"command_table", "command_table_ground", "command_table_space", "command_table_atmospheric_flight" };
			
			for (int n = 0; n < tableArray.length; n++) {
				List<Long> invalidStates = new ArrayList<Long>();
				List<Byte> invalidPostures = new ArrayList<Byte>();
				
				DatatableVisitor visitor2 = ClientFileManager.loadFile("datatables/command/" + tableArray[n] + ".iff", DatatableVisitor.class);
				
				for (int i = 0; i < visitor2.getRowCount(); i++) {
					if (visitor2.getObject(i, 0) != null) {
						if (((String) visitor2.getObject(i, 0)).equalsIgnoreCase(commandName)) {
							characterAbility = (String) visitor2.getObject(i, 7);
							target = (Integer) visitor2.getObject(i, 72);
							targetType = (Integer) visitor2.getObject(i, 73);
							callOnTarget = (Boolean) visitor2.getObject(i, 76);
							commandGroup = (Integer) visitor2.getObject(i, 77);
							disabled = (Boolean) visitor2.getObject(i, 78);
							maxRangeToTarget = (Float) visitor2.getObject(i, 79);
							godLevel = (Integer) visitor2.getObject(i, 80);
							displayGroup = (Integer) visitor2.getObject(i, 81);
							combatCommand = (Boolean) visitor2.getObject(i, 82);
							validWeapon = (Integer) visitor2.getObject(i, 83);
							invalidWeapon = (Integer) visitor2.getObject(i, 84);
							cooldownGroup = (String) visitor2.getObject(i, 85);
							warmupTime = (Float) visitor2.getObject(i, 86);
							executeTime = (Float) visitor2.getObject(i, 87);
							cooldown = (Float) visitor2.getObject(i, 88);
							
							if (!((Boolean) visitor2.getObject(i, 8))) invalidPostures.add((byte) 0);
							if (!((Boolean) visitor2.getObject(i, 9))) invalidPostures.add((byte) 3);
							if (!((Boolean) visitor2.getObject(i, 16))) invalidPostures.add((byte) 2);
							if (!((Boolean) visitor2.getObject(i, 19))) invalidPostures.add((byte) 5);
							if (!((Boolean) visitor2.getObject(i, 21))) invalidPostures.add((byte) 6);
							if (!((Boolean) visitor2.getObject(i, 22))) invalidPostures.add((byte) 7);
							if (!((Boolean) visitor2.getObject(i, 23))) invalidPostures.add((byte) 8);
							if (!((Boolean) visitor2.getObject(i, 24))) invalidPostures.add((byte) 9);
							if (!((Boolean) visitor2.getObject(i, 25))) invalidPostures.add((byte) 10);
							if (!((Boolean) visitor2.getObject(i, 26))) invalidPostures.add((byte) 11);
							if (!((Boolean) visitor2.getObject(i, 27))) invalidPostures.add((byte) 12);
							if (!((Boolean) visitor2.getObject(i, 28))) invalidPostures.add((byte) 13);
							if (!((Boolean) visitor2.getObject(i, 29))) invalidPostures.add((byte) 14);
							if (!((Boolean) visitor2.getObject(i, 30))) invalidPostures.add((byte) 4);
							
							if (!((Boolean) visitor2.getObject(i, 32))) invalidStates.add(1L);
							if (!((Boolean) visitor2.getObject(i, 33))) invalidStates.add(2L);
							if (!((Boolean) visitor2.getObject(i, 34))) invalidStates.add(4L);
							if (!((Boolean) visitor2.getObject(i, 35))) invalidStates.add(8L);
							if (!((Boolean) visitor2.getObject(i, 36))) invalidStates.add(16L);
							if (!((Boolean) visitor2.getObject(i, 37))) invalidStates.add(32L);
							if (!((Boolean) visitor2.getObject(i, 38))) invalidStates.add(64L);
							if (!((Boolean) visitor2.getObject(i, 39))) invalidStates.add(128L);
							if (!((Boolean) visitor2.getObject(i, 40))) invalidStates.add(256L);
							if (!((Boolean) visitor2.getObject(i, 41))) invalidStates.add(512L);
							if (!((Boolean) visitor2.getObject(i, 42))) invalidStates.add(1024L);
							if (!((Boolean) visitor2.getObject(i, 43))) invalidStates.add(2048L);
							if (!((Boolean) visitor2.getObject(i, 44))) invalidStates.add(4096L);
							if (!((Boolean) visitor2.getObject(i, 45))) invalidStates.add(8192L);
							if (!((Boolean) visitor2.getObject(i, 46))) invalidStates.add(16384L);
							if (!((Boolean) visitor2.getObject(i, 47))) invalidStates.add(32768L);
							if (!((Boolean) visitor2.getObject(i, 48))) invalidStates.add(65536L);
							if (!((Boolean) visitor2.getObject(i, 49))) invalidStates.add(131072L);
							if (!((Boolean) visitor2.getObject(i, 50))) invalidStates.add(262144L);
							if (!((Boolean) visitor2.getObject(i, 51))) invalidStates.add(524288L);
							if (!((Boolean) visitor2.getObject(i, 52))) invalidStates.add(1048576L);
							if (!((Boolean) visitor2.getObject(i, 53))) invalidStates.add(2097152L);
							if (!((Boolean) visitor2.getObject(i, 54))) invalidStates.add(4194304L);
							if (!((Boolean) visitor2.getObject(i, 55))) invalidStates.add(8388608L);
							if (!((Boolean) visitor2.getObject(i, 56))) invalidStates.add(16777216L);
							if (!((Boolean) visitor2.getObject(i, 57))) invalidStates.add(33554432L);
							if (!((Boolean) visitor2.getObject(i, 58))) invalidStates.add(67108864L);
							if (!((Boolean) visitor2.getObject(i, 59))) invalidStates.add(134217728L);
							if (!((Boolean) visitor2.getObject(i, 60))) invalidStates.add(268435456L);
							if (!((Boolean) visitor2.getObject(i, 61))) invalidStates.add(536870912L);
							if (!((Boolean) visitor2.getObject(i, 62))) invalidStates.add(1073741824L);
							if (!((Boolean) visitor2.getObject(i, 63))) invalidStates.add(2147483648L);
							if (!((Boolean) visitor2.getObject(i, 64))) invalidStates.add(4294967296L);
							if (!((Boolean) visitor2.getObject(i, 65))) invalidStates.add(8589934592L);
							if (!((Boolean) visitor2.getObject(i, 66))) invalidStates.add(17179869184L);
							if (!((Boolean) visitor2.getObject(i, 67))) invalidStates.add(34359738368L);
							if (!((Boolean) visitor2.getObject(i, 68))) invalidStates.add(68719476736L);
							if (!((Boolean) visitor2.getObject(i, 69))) invalidStates.add(137438953472L);
							if (!((Boolean) visitor2.getObject(i, 70))) invalidStates.add(274877906944L);
							
							this.invalidPostures = invalidPostures.toArray(new Byte[] { });
							this.invalidStates = invalidStates.toArray(new Long[] { });
							
							break;
						}
					}
				}
				
				if (characterAbility != null) {
					break;
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
	
	public Byte[] getInvalidPostures() {
		return invalidPostures;
	}
	
	public Long[] getInvalidStates() {
		return invalidStates;
	}
	
	public int getTarget() {
		return target;
	}
	
	public void setTarget(int target) {
		this.target = target;
	}
	
	public int getTargetType() {
		return targetType;
	}
	
	public void setTargetType(int targetType) {
		this.targetType = targetType;
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
	
	public float getMaxRangeToTarget() {
		return maxRangeToTarget;
	}
	
	public void setMaxRangeToTarget(float maxRangeToTarget) {
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
