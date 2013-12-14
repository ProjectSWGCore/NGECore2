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
package resources.z.exp.objects;

import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.Persistent;

import resources.common.StringUtilities;
import resources.z.exp.objects.object.BaseObject;

@Persistent
public class ObjectMessageBuilder {
	
	protected BaseObject object;
	
	public ObjectMessageBuilder(BaseObject object) {
		setObject(object);
	}
	
	public ObjectMessageBuilder() {
		
	}
	
	public void buildBaseline1(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		/*
		baselineBuilders.put(5, new Builder {
			public byte[] build() {
				IoBuffer = Baseline.createBuffer(2);
				buffer.putShort((short) 27);
				return buffer.array();
			}
		});
		
		deltaBuilders.put(7, new Builder {
			public byte[] build() {
				IoBuffer = Baseline.createBuffer(4);
				buffer.putInt(27);
				return buffer.array();
			}
		});
		*/
	}
	
	public void buildBaseline3(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline4(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline6(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline7(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline8(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public void buildBaseline9(Map<Integer, Builder> baselineBuilders, Map<Integer, Builder> deltaBuilders) {
		
	}
	
	public IoBuffer createBuffer(int size, boolean autoSize) {
		return createBuffer(size).setAutoExpand(autoSize);
	}
	
	public IoBuffer createBuffer(int size) {
		return Baseline.createBuffer(size);
	}
	
	public BaseObject getObject() {
		return object;
	}
	
	public void setObject(BaseObject object) {
		this.object = object;
	}
	
	protected byte getBoolean(boolean variable) {
		return Baseline.getBoolean(variable);
	}
	
	protected String getAsciiString(ByteBuffer buffer) {
		return StringUtilities.getAsciiString(buffer);
	}
	
	protected String getUnicodeString(ByteBuffer buffer) {
		return StringUtilities.getUnicodeString(buffer);
	}
	
	protected byte[] getAsciiString(String string) {
		return StringUtilities.getAsciiString(string);
	}
	
	protected byte[] getUnicodeString(String string) {
		return StringUtilities.getUnicodeString(string);
	}
	
}
