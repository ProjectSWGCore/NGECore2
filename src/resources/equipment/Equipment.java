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
package resources.equipment;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.common.CRC;
import engine.resources.objects.Delta;
import engine.resources.objects.SWGObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;

public class Equipment extends Delta {
	
	private static final long serialVersionUID = 1L;
	
	private long objectId;
	
	public Equipment(SWGObject object) {
		this.objectId = ((object instanceof TangibleObject) ? object.getObjectID() : 0);
	}
	
	public Equipment() {
		
	}
	
	public SWGObject getObject() {
		synchronized(objectMutex) {
			return NGECore.getInstance().objectService.getObject(objectId);
		}
	}
	
	public long getObjectId() {
		synchronized(objectMutex) {
			return objectId;
		}
	}
	
	public byte[] getBytes() {
		SWGObject sobject = getObject();
		
		if (sobject == null) {
			System.err.println("Serious error: Equipment object is null in objectList.");
			return null;
		}
		
		TangibleObject object = (TangibleObject) sobject;
		
		synchronized(objectMutex) {
			int size = 19 + object.getCustomization	().length;
				
			if (object instanceof WeaponObject) {
				size += ((WeaponObject) object).getBaseline(3).getBaseline().array().length;
				size += ((WeaponObject) object).getBaseline(6).getBaseline().array().length;
			}
			
			IoBuffer buffer = createBuffer(size);
			buffer.putShort((short) object.getCustomization().length);
			buffer.put(object.getCustomization());
			buffer.putInt(object.getArrangementId());
			buffer.putLong(object.getObjectID());
			buffer.putInt(CRC.StringtoCRC(object.getTemplate()));
			buffer.put(getBoolean((object instanceof WeaponObject)));
			if (object instanceof WeaponObject) {
				buffer.put(((WeaponObject) object).getBaseline(3).getBaseline().array());
				buffer.put(((WeaponObject) object).getBaseline(6).getBaseline().array());
			}
			buffer.flip();
			
			return buffer.array();
		}
	}
	
}
