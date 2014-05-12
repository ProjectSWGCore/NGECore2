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
package resources.buffs;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;

import com.sleepycat.persist.model.NotPersistent;

public class DamageOverTime implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Buff buff;
	private String type;
	private int duration;
	private int intensity;
	@NotPersistent
	private transient ScheduledFuture<?> task;
	private long startTime;
	private String commandName;

	public DamageOverTime() { }
	
	public DamageOverTime(String commandName, Buff buff, String type, int duration, int intensity) { 
		this.buff = buff;
		this.type = type;
		this.duration = duration;
		this.intensity = intensity;
		this.commandName = commandName;
	}
	
	public Buff getBuff() {
		return buff;
	}
	
	public void setBuff(Buff buff) {
		this.buff = buff;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getIntensity() {
		return intensity;
	}
	
	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	public ScheduledFuture<?> getTask() {
		return task;
	}

	public void setTask(ScheduledFuture<?> task) {
		this.task = task;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public int getRemainingDuration() {
		
		long currentTime = System.currentTimeMillis();
		long timeDiff = (currentTime - startTime) / 1000;
		return (int) (duration - timeDiff);
		
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}


}
