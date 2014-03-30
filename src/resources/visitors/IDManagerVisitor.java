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
package resources.visitors;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;

import engine.clientdata.VisitorInterface;

public class IDManagerVisitor implements VisitorInterface {

	private ConcurrentHashMap<String, Byte> customizationMap;
	private CharsetDecoder charsetDecoder;
	
	public IDManagerVisitor() {
		customizationMap = new ConcurrentHashMap<String, Byte>();
		charsetDecoder = Charset.forName("US-ASCII").newDecoder();
	}
	
	@Override
	public void parseData(String node, IoBuffer data, int depth, int size) throws Exception {

		if (!node.equals("0001DATA") || depth != 2)
			return;
		
		while(data.hasRemaining()) {
			
			byte designNumber = data.get();
			data.get();
			String customizationType = data.getString(charsetDecoder);
			charsetDecoder.reset();
				
			customizationMap.put(customizationType, designNumber);
			
			//System.out.println(designNumber + ": " + customizationType);
		}
	}
	
	public byte getAttributeIndex(String type)
	{
		return customizationMap.get(type);
	}

	@Override
	public void notifyFolder(String node, int depth) throws Exception {
	}
	
	public ConcurrentHashMap<String, Byte> getCustomizationMap() {
		return this.customizationMap;
	}
}
