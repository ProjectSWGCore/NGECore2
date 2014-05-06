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
		// I put in the names of the really generic ones... I should be right up until Dark Purple... I'm unsure after Dark Purple.
		map.put("Red", 0);
		map.put("Dark Red", 1);
		map.put("Light Green", 2);
		map.put("Dark Green", 3);
		map.put("Blue", 4);
		map.put("Dark Blue", 5);
		map.put("Yellow", 6);
		map.put("Dark Yellow", 7);
		map.put("Light Purple", 8);
		map.put("Purple", 9);
		map.put("Orange", 0x0A);
		map.put("Browns", 0x0B);
		map.put("Maul's Vengeance", 0x0C);
		map.put("Kun's Blood", 0x0D);
		map.put("Bondara's Folly", 0x0E);
		map.put("Ulic's Redemption", 0x0F);
		map.put("Bane's Heart", 10);
		map.put("Gallia's Intuition", 11);		
		map.put("Prowess of Plo Koon", 12);
		map.put("B'Nar's Sacrifice", 13);
		map.put("Windu's Guile", 14);
		map.put("Mundi's Response", 15);
		map.put("Strength of Luminaria", 16);		
		map.put("Baaa's Wisdom", 17);
		map.put("Quintessence of Force", 18);
		map.put("Dawn of Dagobah", 19);
		map.put("Horn's Future", 0x1A);
		map.put("Qui-Gon's Devotion", 0x1B);
		map.put("Kenobi's Legacy", 0x1C);
		map.put("Sunrider's Destiny", 0x1D);
		map.put("Kit's Ferocity", 0x1E);
		
		// Special crystals that have seperate shaders - the 256 is for a hacky way to set blade type and color without more code
		map.put("Lava", 256 + 1);
		map.put("Permafrost", 256 + 2);
		map.put("Blackwing", 256 + 3);
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
