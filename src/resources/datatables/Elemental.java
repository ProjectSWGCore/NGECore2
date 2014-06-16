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

public enum Elemental {

	KINETIC(1), ENERGY(2), BLAST(4), STUN(8), HEAT(32), COLD(64), ACID(128), ELECTRICITY(256);

	private int element;
	
	private Elemental(int element) {
		this.element = element;
	}
	
	@Override
	public String toString() {
		switch(this) {
			case KINETIC:
				return "kinetic";
			case ENERGY:
				return "energy";
			case BLAST:
				return "blast";
			case STUN:
				return "stun";
			case HEAT:
				return "heat";
			case COLD:
				return "cold";
			case ACID:
				return "acid";
			case ELECTRICITY:
				return "electricity";
		}
		return null;
	}
	
	public int getValue() {
		return element;
	}
	
	public static int getElementalNum(String element) {
		switch(element) {
			case "kinetic":
				return 1;
			case "energy":
				return 2;
			case "blast":
				return 4;
			case "stun":
				return 8;
			case "heat":
				return 32;
			case "cold":
				return 64;
			case "acid":
				return 128;
			case "electricity":
				return 256;
		}
		return 0;
	}

	public static String getElementalName(int elementalType) {
		for (Elemental element : Elemental.values()) {
			if (element.getValue() == elementalType) {
				return element.toString();
			}
		}
		
		return null;
	}
}