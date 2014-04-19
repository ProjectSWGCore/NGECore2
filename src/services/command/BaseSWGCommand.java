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
	private String characterAbility;
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
		commandName.toLowerCase();
		setCommandName(commandName);
		setCommandCRC(CRC.StringtoCRC(commandName));
		
		try {
			boolean foundCommand = false;
			
			String[] tableArray = new String[] {
			"client_command_table", "client_command_table_ground", "client_command_table_space",
			"command_table", "command_table_ground", "command_table_space" };
			
			for (int n = 0; n < tableArray.length; n++) {
				List<Long> invalidStates = new ArrayList<Long>();
				List<Byte> invalidPostures = new ArrayList<Byte>();
				
				DatatableVisitor visitor2 = ClientFileManager.loadFile("datatables/command/" + tableArray[n] + ".iff", DatatableVisitor.class);
				
				for (int i = 0; i < visitor2.getRowCount(); i++) {
					if (visitor2.getObject(i, 0) != null) {
						if (((String) visitor2.getObject(i, 0)).equalsIgnoreCase(commandName)) {
							int sub = 0; // Subtract due to command table structures being different
							
							characterAbility = (String) visitor2.getObject(i, 7);
							
							if (!((Boolean) visitor2.getObject(i, 8-sub))) invalidPostures.add((byte) 0);
							if (!((Boolean) visitor2.getObject(i, 9-sub))) invalidPostures.add((byte) 3);
							if (!((Boolean) visitor2.getObject(i, 15-sub))) invalidPostures.add((byte) 2);
							if (!((Boolean) visitor2.getObject(i, 18-sub))) invalidPostures.add((byte) 5);
							if (!((Boolean) visitor2.getObject(i, 20-sub))) invalidPostures.add((byte) 6);
							if (!((Boolean) visitor2.getObject(i, 21-sub))) invalidPostures.add((byte) 7);
							if (!((Boolean) visitor2.getObject(i, 22-sub))) invalidPostures.add((byte) 8);
							if (!((Boolean) visitor2.getObject(i, 23-sub))) invalidPostures.add((byte) 9);
							if (!((Boolean) visitor2.getObject(i, 24-sub))) invalidPostures.add((byte) 10);
							if (!((Boolean) visitor2.getObject(i, 25-sub))) invalidPostures.add((byte) 11);
							if (!((Boolean) visitor2.getObject(i, 26-sub))) invalidPostures.add((byte) 12);
							if (!((Boolean) visitor2.getObject(i, 27-sub))) invalidPostures.add((byte) 13);
							if (!((Boolean) visitor2.getObject(i, 28-sub))) invalidPostures.add((byte) 14);
							if (!((Boolean) visitor2.getObject(i, 29-sub))) invalidPostures.add((byte) 4);
							
							if (tableArray[n].startsWith("client_") || tableArray[n].startsWith("command_table_")) {
								sub += 1;
							}
							
							if (!((Boolean) visitor2.getObject(i, 32-sub))) invalidStates.add(1L);
							if (!((Boolean) visitor2.getObject(i, 33-sub))) invalidStates.add(2L);
							if (!((Boolean) visitor2.getObject(i, 34-sub))) invalidStates.add(4L);
							if (!((Boolean) visitor2.getObject(i, 35-sub))) invalidStates.add(8L);
							if (!((Boolean) visitor2.getObject(i, 36-sub))) invalidStates.add(16L);
							if (!((Boolean) visitor2.getObject(i, 37-sub))) invalidStates.add(32L);
							if (!((Boolean) visitor2.getObject(i, 38-sub))) invalidStates.add(64L);
							if (!((Boolean) visitor2.getObject(i, 39-sub))) invalidStates.add(128L);
							if (!((Boolean) visitor2.getObject(i, 40-sub))) invalidStates.add(256L);
							if (!((Boolean) visitor2.getObject(i, 41-sub))) invalidStates.add(512L);
							if (!((Boolean) visitor2.getObject(i, 42-sub))) invalidStates.add(1024L);
							if (!((Boolean) visitor2.getObject(i, 43-sub))) invalidStates.add(2048L);
							if (!((Boolean) visitor2.getObject(i, 44-sub))) invalidStates.add(4096L);
							if (!((Boolean) visitor2.getObject(i, 45-sub))) invalidStates.add(8192L);
							if (!((Boolean) visitor2.getObject(i, 46-sub))) invalidStates.add(16384L);
							if (!((Boolean) visitor2.getObject(i, 47-sub))) invalidStates.add(32768L);
							if (!((Boolean) visitor2.getObject(i, 48-sub))) invalidStates.add(65536L);
							if (!((Boolean) visitor2.getObject(i, 49-sub))) invalidStates.add(131072L);
							if (!((Boolean) visitor2.getObject(i, 50-sub))) invalidStates.add(262144L);
							if (!((Boolean) visitor2.getObject(i, 51-sub))) invalidStates.add(524288L);
							if (!((Boolean) visitor2.getObject(i, 52-sub))) invalidStates.add(1048576L);
							if (!((Boolean) visitor2.getObject(i, 53-sub))) invalidStates.add(2097152L);
							if (!((Boolean) visitor2.getObject(i, 54-sub))) invalidStates.add(4194304L);
							if (!((Boolean) visitor2.getObject(i, 55-sub))) invalidStates.add(8388608L);
							if (!((Boolean) visitor2.getObject(i, 56-sub))) invalidStates.add(16777216L);
							if (!((Boolean) visitor2.getObject(i, 57-sub))) invalidStates.add(33554432L);
							if (!((Boolean) visitor2.getObject(i, 58-sub))) invalidStates.add(67108864L);
							if (!((Boolean) visitor2.getObject(i, 59-sub))) invalidStates.add(134217728L);
							
							if (!tableArray[n].equals("client_command_table_space")) {
								if (!((Boolean) visitor2.getObject(i, 60-sub))) invalidStates.add(268435456L);
								if (!((Boolean) visitor2.getObject(i, 61-sub))) invalidStates.add(536870912L);
								if (!((Boolean) visitor2.getObject(i, 62-sub))) invalidStates.add(1073741824L);
								if (!((Boolean) visitor2.getObject(i, 63-sub))) invalidStates.add(2147483648L);
								if (!((Boolean) visitor2.getObject(i, 64-sub))) invalidStates.add(4294967296L);
							} else {
								sub += 5;
							}
							
							if (!tableArray[n].startsWith("client_") && !tableArray[n].startsWith("command_table_")) {
								if (!((Boolean) visitor2.getObject(i, 65-sub))) invalidStates.add(8589934592L);
								if (!((Boolean) visitor2.getObject(i, 66-sub))) invalidStates.add(17179869184L);
								if (!((Boolean) visitor2.getObject(i, 67-sub))) invalidStates.add(34359738368L);
								if (!((Boolean) visitor2.getObject(i, 68-sub))) invalidStates.add(68719476736L);
								if (!((Boolean) visitor2.getObject(i, 69-sub))) invalidStates.add(137438953472L);
								if (!((Boolean) visitor2.getObject(i, 70-sub))) invalidStates.add(274877906944L);
							} else {
								sub += 6;
							}
							
							target = (Integer) visitor2.getObject(i, 72-sub);
							targetType = (Integer) visitor2.getObject(i, 73-sub);
							callOnTarget = (Boolean) visitor2.getObject(i, 76-sub);
							commandGroup = (Integer) visitor2.getObject(i, 77-sub);
							disabled = (Boolean) visitor2.getObject(i, 78-sub);
							maxRangeToTarget = (Float) visitor2.getObject(i, 79-sub);
							godLevel = (Integer) visitor2.getObject(i, 80-sub);
							displayGroup = (Integer) visitor2.getObject(i, 81-sub);
							combatCommand = (Boolean) visitor2.getObject(i, 82-sub);
							validWeapon = (Integer) visitor2.getObject(i, 83-sub);
							invalidWeapon = (Integer) visitor2.getObject(i, 84-sub);
							cooldownGroup = (String) visitor2.getObject(i, 85-sub);
							warmupTime = (Float) visitor2.getObject(i, 86-sub);
							executeTime = (Float) visitor2.getObject(i, 87-sub);
							cooldown = (Float) visitor2.getObject(i, 88-sub);
							
							this.invalidPostures = invalidPostures.toArray(new Byte[] { });
							this.invalidStates = invalidStates.toArray(new Long[] { });
							
							foundCommand = true;
							break;
						}
					}
				}
				
				if (foundCommand) {
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
