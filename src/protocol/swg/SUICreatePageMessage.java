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
package protocol.swg;

import java.nio.ByteOrder;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import services.sui.SUIWindowComponent;

public class SUICreatePageMessage extends SWGMessage{
	
	private String script;
	private int windowId;
	private Vector<SUIWindowComponent> components;
	private long rangeObjectId;
	private float range;
	
	
	public SUICreatePageMessage(String script, int windowId, Vector<SUIWindowComponent> components, long rangeObjectId, float range) {
		
		this.script = script;
		this.windowId = windowId;
		this.components = components;
		this.rangeObjectId = rangeObjectId;
		this.range = range;

	}
		
	

	
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		
		IoBuffer result = IoBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 2);
		result.putInt(0xD44B7259);
		
		result.putInt(windowId);
		result.put(getAsciiString(script));
		result.putInt(components.size());
		
		for(SUIWindowComponent component : components) {
			
			result.put(component.getType());
			result.putInt(component.getWideParams().size());

			for(String param : component.getWideParams()) {
				result.put(getUnicodeString(param));
			}
			
			result.putInt(component.getNarrowParams().size());

			for(String param : component.getNarrowParams()) {
				result.put(getAsciiString(param));
			}

		}
		
		result.putLong(rangeObjectId);
		result.putFloat(range);
		result.putLong(0);
		result.putInt(0);

		int size = result.position();
		result = IoBuffer.allocate(size).put(result.array(), 0, size);

		return result.flip();	
	}
}
