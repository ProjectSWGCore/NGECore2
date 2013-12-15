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
package resources.z.exp.skills;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.Persistent;

import resources.objects.Delta;

// Proxy for engine.resources.objects.SkillMod so it uses Delta

@Persistent
public class SkillMod extends Delta  {
	
	private int base = 0;
	private int modifier = 0;
	
	public SkillMod(int base, int modifier) {
		engine.resources.objects.SkillMod skillMod = new engine.resources.objects.SkillMod();
		skillMod.setBase(base);
		skillMod.setModifier(modifier);
		this.base = skillMod.getBase();
		this.modifier = skillMod.getModifier();
	}
	
	public SkillMod() {
		 
	}
	
	public int getBase() {
		synchronized(objectMutex) {
			engine.resources.objects.SkillMod skillMod = new engine.resources.objects.SkillMod();
			skillMod.setBase(base);
			skillMod.setModifier(modifier);
			return skillMod.getBase();
		}
	}
	
	public void setBase(int base) {
		synchronized(objectMutex) {
			engine.resources.objects.SkillMod skillMod = new engine.resources.objects.SkillMod();
			skillMod.setBase(this.base);
			skillMod.setModifier(modifier);
			skillMod.setBase(base);
			this.base = skillMod.getBase();
		}
	}
	
	public int getModifier() {
		synchronized(objectMutex) {
			engine.resources.objects.SkillMod skillMod = new engine.resources.objects.SkillMod();
			skillMod.setBase(base);
			skillMod.setModifier(modifier);
			return skillMod.getModifier();
		}
	}
	
	public void setModifier(int modifier) {
		synchronized(objectMutex) {
			engine.resources.objects.SkillMod skillMod = new engine.resources.objects.SkillMod();
			skillMod.setBase(base);
			skillMod.setModifier(this.modifier);
			skillMod.setModifier(modifier);
			this.modifier = skillMod.getModifier();
		}
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
			engine.resources.objects.SkillMod skillMod = new engine.resources.objects.SkillMod();
			skillMod.setBase(base);
			skillMod.setModifier(modifier);
			buffer.putInt(skillMod.getBase());
			buffer.putInt(skillMod.getModifier());
			return buffer.array();
		}
	}
	
}
