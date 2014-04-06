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

public class BaseSWGCommand implements Cloneable {
	
	private String commandName;
	private String clientEffectSelf;
	private String clientEffectTarget;
	private int maxRangeToTarget;
	private int commandCRC;
	private boolean isGmCommand = false;
	private String requiredAbility;
	private String cooldownGroup;
	private float cooldown;
	private float executeTime;
	private float warmupTime;
	
	public BaseSWGCommand(String commandName) {
		setCommandName(commandName);
		setCommandCRC(CRC.StringtoCRC(commandName));
		
		try {
			DatatableVisitor visitor2 = ClientFileManager.loadFile("datatables/command/command_table.iff", DatatableVisitor.class);
			for (int i = 0; i < visitor2.getRowCount(); i++) {
				if (visitor2.getObject(i, 0) != null) {
					if (((String) visitor2.getObject(i, 0)).equalsIgnoreCase(commandName)) {
						requiredAbility = (String) visitor2.getObject(i, 7);
						cooldownGroup = (String) visitor2.getObject(i, 85);
						warmupTime = (Float) visitor2.getObject(i, 86);
						executeTime = (Float) visitor2.getObject(i, 87);
						cooldown = (Float) visitor2.getObject(i, 88);
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

	public int getMaxRangeToTarget() {
		return maxRangeToTarget;
	}

	public void setMaxRangeToTarget(int maxRangeToTarget) {
		this.maxRangeToTarget = maxRangeToTarget;
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
		return isGmCommand;
	}

	public void setGmCommand(boolean isGmCommand) {
		this.isGmCommand = isGmCommand;
	}

	public String getRequiredAbility() {
		return requiredAbility;
	}

	public void setRequiredAbility(String requiredAbility) {
		this.requiredAbility = requiredAbility;
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
