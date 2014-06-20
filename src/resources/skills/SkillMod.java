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
package resources.skills;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.Delta;

public class SkillMod extends Delta implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int base;
	private int modifier;
	
	public SkillMod(int base, int modifier) {
		this.base = base;
		this.modifier = modifier;
	}
	
	public SkillMod() {
		
	}
	
	public int getBase() {
		synchronized(objectMutex) {
			return base;
		}
	}
	
	public void setBase(int base) {
		synchronized(objectMutex) {
			this.base = base;
		}
	}
	
	public void addBase(int base) {
		synchronized(objectMutex) {
			this.base += base;
		}
	}
	
	public void deductBase(int base) {
		synchronized(objectMutex) {
			this.base -= base;
		}
	}
	
	public int getModifier() {
		synchronized(objectMutex) {
			return modifier;
		}
	}
	
	public void setModifier(int modifier) {
		synchronized(objectMutex) {
			this.modifier = modifier;
		}
	}
	
	public void addModifier(int modifier) {
		synchronized(objectMutex) {
			this.modifier += modifier;
		}
	}
	
	public void deductModifier(int modifier) {
		synchronized(objectMutex) {
			this.modifier -= modifier;
		}
	}
	
	public float getValue(int divisor, boolean percent) {
		synchronized(objectMutex) {
			return (((divisor < 1) ? ((float) (base + modifier)) : ((float) (base + modifier) / (float) divisor)) * ((percent) ? 100f : 1f));
		}
	}
	
	@Override
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = createBuffer(8);
			buffer.putInt(base);
			buffer.putInt(modifier);
			return buffer.array();
		}
	}
	
}
