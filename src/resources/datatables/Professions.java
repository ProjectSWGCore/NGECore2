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

import java.util.HashMap;
import java.util.Map;

public final class Professions {
	
	private static Map<String, Integer> map = new HashMap<String, Integer>();

	static {
		map.put("trader_0a", 0);
		map.put("trader_0b", 0);
		map.put("trader_0c", 0);
		map.put("trader_0d", 0);
		map.put("entertainer_1a", 5);
		map.put("medic_1a", 10);
		map.put("officer_1a", 15);
		map.put("bounty_hunter_1a", 20);
		map.put("smuggler_1a", 25);
		map.put("commando_1a", 30);
		map.put("spy_1a", 35);
		map.put("force_sensitive_1a", 40);
	}
	
	public static int get(String profession) {
		return map.get(profession);
	}
	
	public static boolean isProfession(String profession) {
		return map.containsKey(profession);
	}
	
}
