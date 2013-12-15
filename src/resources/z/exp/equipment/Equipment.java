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
package resources.z.exp.equipment;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.Persistent;

import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import resources.objects.Delta;
import resources.z.exp.objects.tangible.TangibleObject;
import resources.z.exp.objects.weapon.WeaponObject;

@Persistent
public class Equipment extends Delta {
	
	private TangibleObject object;
	
	public Equipment(SWGObject object) {
		this.object = ((object instanceof TangibleObject) ? (TangibleObject) object : null);
	}
	
	public Equipment() {
		
	}
	
	public SWGObject getObject() {
		synchronized(objectMutex) {
			return object;
		}
	}
	
	public long getObjectId() {
		synchronized(objectMutex) {
			return object.getObjectId();
		}
	}
	
	public byte[] getBytes() {
		synchronized(objectMutex) {
			int size = 18 + object.getCustomization().length;
			
			IoBuffer buffer = bufferPool.allocate(size, false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.setAutoExpand(true);
			buffer.putShort((short) object.getCustomization().length);
			buffer.put(object.getCustomization());
			buffer.putInt(object.getArrangementId());
			buffer.putLong(object.getObjectID());
			buffer.putInt(CRC.StringtoCRC(object.getTemplate()));
			buffer.put(getBoolean((object instanceof WeaponObject)));
			if (object instanceof WeaponObject) {
				buffer.put(((WeaponObject) object).getBaseline3().getBaseline());
				buffer.put(((WeaponObject) object).getBaseline6().getBaseline());
			}
			buffer.flip();
			
			return buffer.array();
		}
	}
	
}
