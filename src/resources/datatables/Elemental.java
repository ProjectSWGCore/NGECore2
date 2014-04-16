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
package resources.datatables;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

public final class Elemental {
	
	
	static BidiMap map = new DualHashBidiMap();
	
	static {
		map.put("kinetic", 1);
		map.put("energy", 2);
		map.put("blast", 4);
		map.put("stun", 8);
		map.put("heat", 32);
		map.put("cold", 64);
		map.put("acid", 128);
		map.put("electricity", 256);
	}
	

	public static int getElementalNum(String elementalName) {
		return (int) map.get(elementalName);
	}
	
	public static String getElementalName(int elementalNum) {
		return (String) map.getKey(elementalNum);
	}	
	
}
