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

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.CRC;
import engine.resources.objects.Delta;

public class Quest extends Delta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long ownerId;
	private short activeStep = 1;
	private short completedStep;
	private boolean isCompleted = false;
	private String name;
	private String filePath;
	
	public Quest() {}
	
	public Quest(String name, long ownerId) {
		this.ownerId = ownerId;

		if (name.endsWith(".qst")) {
			this.filePath = "quest/"+name;
			this.name = name.split(".qst")[0];
		} else {
			this.filePath = "quest/"+name+".qst";
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

	public short getActiveStep() {
		return activeStep;
	}

	public void setActiveStep(short activeStep) {
		this.activeStep = activeStep;
	}

	public short getCompletedStep() {
		return completedStep;
	}

	public void setCompletedStep(short completedStep) {
		this.completedStep = completedStep;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getName() {
		return name;
	}

	public String getFilePath() {
		return filePath;
	}

	public int getCrc() {
		return CRC.StringtoCRC(getCrcName());
	}
	
	public String getCrcName() {
		return "quest/" + name;
	}

	public byte[] getBytes() {
		IoBuffer buffer = createBuffer(23);

		buffer.put((byte) 0);
		
		buffer.putInt(getCrc());

		buffer.putLong(ownerId);
		
		buffer.putShort(activeStep);
		buffer.putShort(completedStep);
		
		buffer.put((byte) ((isCompleted) ? 1 : 0)); // isCompleted
		buffer.putInt(0); // questCounter
		buffer.put((byte) 0); // ?
		
		buffer.flip();
		
		return buffer.array();
	}
	
}
