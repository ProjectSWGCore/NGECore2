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
package resources.quest;

import java.io.Serializable;
import java.util.BitSet;
import java.util.concurrent.ScheduledFuture;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.CRC;
import engine.resources.common.StringUtilities;
import engine.resources.objects.Delta;

public class Quest extends Delta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long ownerId;
	private int activeStep; // Quest starts at step 0

	private BitSet activeStepBitmask = new BitSet(16);
	private BitSet completedStepBitmask = new BitSet(16);
	
	private boolean isCompleted = false;
	private boolean recievedAward = false;
	private String name;

	private ScheduledFuture<?> timer;
	private long waypointId;
	
	public Quest() {}
	
	public Quest(String name, long ownerId) {
		this.ownerId = ownerId;

		if (name.endsWith(".qst")) {
			//this.filePath = "quest/"+name;
			this.name = name.split(".qst")[0];
		} else {
			//this.filePath = "quest/"+name+".qst";
			this.name = name;
		}
		
		if (name.contains("/")) {
			String[] split = name.split("/");
			this.name = split[split.length - 1];
		}
	}
	
	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public int getActiveTask() {
		return activeStep;
	}

	public void setActiveTask(int activeStep) {
		this.activeStep = activeStep;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		if (isCompleted) {
			//activeStepBitmask.set(activeStep, false);
			completedStepBitmask.set(activeStep);
		}
		this.isCompleted = isCompleted;
	}

	public boolean hasRecievedAward() {
		return recievedAward;
	}

	public void setRecievedAward(boolean recievedAward) {
		this.recievedAward = recievedAward;
	}

	public String getName() {
		return name;
	}

	public int getCrc() {
		return CRC.StringtoCRC(getCrcName());
	}
	
	public String getCrcName() {
		return "quest/" + name;
	}
	
	public long getWaypointId() {
		return waypointId;
	}

	public void setWaypointId(long waypointId) {
		this.waypointId = waypointId;
	}

	public void incrementQuestStep() {
		completedStepBitmask.set(activeStep);
		activeStepBitmask.set(activeStep, false);
		
		activeStep++;
		activeStepBitmask.set(activeStep, true);
		
		//System.out.println("Active step was "+ (activeStep - 1) + " and is now " + activeStep );
	}
	
	public ScheduledFuture<?> getTimer() {
		return timer;
	}

	public void setTimer(ScheduledFuture<?> timer) {
		this.timer = timer;
	}

	public byte[] getBytes() {
		byte[] activeStepBytes = activeStepBitmask.toByteArray();
		byte[] completedStepBytes = completedStepBitmask.toByteArray();
		
		IoBuffer buffer = createBuffer(18);

		//buffer.put((byte) 0);
		
		//buffer.putInt(getCrc());

		buffer.putLong(ownerId); // quest giver id?
		
		// Probably a MUCH better way of doing this, but... well... I can't stand bitmasks.
		switch (activeStepBytes.length) {
		case 0:
			buffer.putShort((short) 0);
			break;
			
		case 1:
			buffer.put(activeStepBytes[0]);
			buffer.put((byte) 0);
			break;
			
		case 2:
			buffer.put(activeStepBytes);
			break;
		}
		
		switch (completedStepBytes.length) {
		case 0:
			buffer.putShort((short) 0);
			break;
			
		case 1:
			buffer.put(completedStepBytes[0]);
			buffer.put((byte) 0);
			break;
			
		case 2:
			buffer.put(completedStepBytes);
			break;
		}
		
		buffer.put((byte) ((isCompleted) ? 1 : 0));
		buffer.putInt(0); // questCounter
		buffer.put((byte) ((recievedAward) ? 1 : 0));
		
		buffer.flip();

		//StringUtilities.printBytes(buffer.array());
		return buffer.array();
	}
	
}
