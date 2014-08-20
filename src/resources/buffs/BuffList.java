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

import java.util.Map.Entry;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.Delta;
import resources.objects.SWGMap;

public class BuffList extends Delta {
	
	private static final long serialVersionUID = 1L;
	
	private SWGMap<Integer, Buff> list;
	
	public BuffList(SWGMap<Integer, Buff> list) {
		this.list = list;
	}
	
	public BuffList() {
		
	}
	
	public SWGMap<Integer, Buff> getList() {
		synchronized(objectMutex) {
			return list;
		}
	}
	
	public byte[] getBytes() {
		int size = 8 + ((list.size() > 0) ? 4 : 0);
		
		for (Buff buff : list.values().toArray(new Buff[] { })) {
			size += (25 + ((buff.getBuffName().equals("")) ? 0 : 4));
		}
		
		IoBuffer buffer = createBuffer(size);
		buffer.putInt(list.size());
		buffer.putInt(list.getUpdateCounter());
		for (Entry<Integer, Buff> entry : list.entrySet()) {
			int crc = entry.getKey();
			Buff buff = entry.getValue();
			
			buffer.put(getBoolean(!buff.getBuffName().equals("")));
			if (!buff.getBuffName().equals("")) {
				buffer.putInt(0);
			}
			buffer.putInt(crc);
			if (buff.getDuration() > 0) {
				buffer.putInt((int) (buff.getTotalPlayTime() + buff.getRemainingDuration()));		
				buffer.putInt(0);
				buffer.putInt((int) buff.getDuration());
			} else {
				buffer.putInt(-1);
				buffer.putInt(0);
				if (buff.getBuffName().equals("")) {
					buffer.putInt(0);
				} else {
					buffer.putInt(-1);
				}
			}
			buffer.putLong(buff.getOwnerId());
		}
		if (list.size() > 0) {
			buffer.putInt(1);
		}
		return buffer.array();
	}
	
}
