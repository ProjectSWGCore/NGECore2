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

import org.python.google.common.collect.BiMap;
import org.python.google.common.collect.HashBiMap;

public final class LightsaberColors {
	
	public static BiMap<String, Integer> map = HashBiMap.create();

	static
	{
		// Basic color crystals with the basic lightsaber shader
		map.put("Red", 0);
		map.put("Dark Red", 1);
		map.put("Light Green", 2);
		map.put("Dark Green", 3);
		map.put("Blue", 4);
		map.put("Dark Blue", 5);
		map.put("Yellow", 6);
		map.put("Dark Yellow", 7);
		map.put("Light Purple", 8);
		map.put("Dark Purple", 9);
		map.put("Orange", 10);
		map.put("Brown", 11);
		map.put("Allya's Exile", 12);
		map.put("Kun's Blood", 13);
		map.put("Bondara's Folly", 14);
		map.put("Ulic's Redemption", 15);
		map.put("Bane's Heart", 16);
		map.put("Gallia's Intuition", 17);		
		map.put("Prowess of Plo Koon", 18);
		map.put("B'Nar's Sacrifice", 19);
		map.put("Windu's Guile", 20);
		map.put("Mundi's Response", 21);
		map.put("Strength of Luminaria", 22);		
		map.put("Baaa's Wisdom", 23);
		map.put("Quintessence of the Force", 24);
		map.put("Dawn of Dagobah", 25);
		map.put("Horn's Future", 26);
		map.put("Allya's Redemption", 27);
		map.put("Kenobi's Legacy", 28);
		map.put("Sunrider's Destiny", 29);
		map.put("Kit's Ferocity", 30);
		map.put("Cunning of Tyranus", 31);
		
		// Special crystals that have seperate shaders - the 256 is for a hacky way to set blade type and color without more code		
		map.put("Lava", 256 + 1);
		map.put("Permafrost", 256 + 2);
		map.put("Blackwing Bezoar", 256 + 3);
	}
	
	public static int getByName(String colorName)
	{
		return map.get(colorName);
	}
	
	public static String get(int index)
	{
		return map.inverse().get(index);
	}
	
	
}
