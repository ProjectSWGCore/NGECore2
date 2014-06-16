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
package protocol;

import java.util.Map;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.CrcStringTableVisitor;
import engine.protocol.unitTests.AbstractUnitTest;
import engine.resources.common.Stf;
import engine.resources.objects.SWGObject;

public abstract class UnitTest extends AbstractUnitTest {
	
	protected NGECore core;
	
	protected IoBuffer buffer = null;
	
	private static CrcStringTableVisitor crcTable = null;
	
	public UnitTest() {
		super();
		core = NGECore.getInstance();
		
		if (crcTable == null) {
			try {
				crcTable = ClientFileManager.loadFile("misc/object_template_crc_string_table.iff", CrcStringTableVisitor.class);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean checkObjectId(long objectId) {
		Map<Long, String> map = UnitTests.getObjectIdMap();
		
		if (objectId == 0) {
			return false;
		}
		
		SWGObject object = core.objectService.getObject(objectId);
		
		if (object == null) {
			return false;
		}
		
		if (map.containsKey(objectId)) {
			if (map.get(objectId) == null) {
				return false;
			}
			
			if (!object.getClass().getSimpleName().equals(map.get(objectId))) {
				return false;
			}
		} else {
			map.put(object.getObjectID(), object.getClass().getSimpleName());
		}
		
		return true;
	}
	
	public boolean checkString(String string) {
		if (string == null) {
			return false;
		}
		
		return true;
	}
	
	public boolean checkCrc(int crc) {
		if (crcTable != null) {
			String template = crcTable.getTemplateString(buffer.getInt());
			
			if (template == null || template.length() == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean checkStf(IoBuffer buffer, boolean failEmpty) {
		Stf stf = new Stf();
		String string;
		
		string = getAsciiString(buffer);
		
		if (!checkString(string)) {
			return false;
		}
		
		stf.setStfFilename(string);
		
		if (buffer.getInt() > 0) {
			return false;
		}
		
		string = getAsciiString(buffer);
		
		if (!checkString(string)) {
			return false;
		}
		
		stf.setStfName(string);
		
		if (stf.getStfFilename().length() == 0 || stf.getStfName().length() == 0) {
			return !failEmpty;
		}
		
		if (stf.getStfValue() == null) {
			return false;
		}
		
		return true;
	}
	
}
