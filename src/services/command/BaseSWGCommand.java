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

import engine.resources.common.CRC;

public class BaseSWGCommand implements Cloneable {
	
	private String commandName;
	private String clientEffectSelf;
	private String clientEffectTarget;
	private int maxRangeToTarget;
	private int commandCRC;
	
	public BaseSWGCommand(String commandName) {
		setCommandName(commandName);
		setCommandCRC(CRC.StringtoCRC(commandName));
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
	
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
