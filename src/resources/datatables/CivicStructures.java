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

public class CivicStructures {
	
	public static final int BANK              = 1; 
	public static final int CLONING_FACILITY  = 2; 
	public static final int MEDICAL_CENTER    = 3; 
	public static final int GARAGE            = 4; 
	public static final int CANTINA           = 5; 
	public static final int THEATER      	  = 6;
	public static final int SHUTTLEPORT       = 7;
	public static final int SMALL_GARDEN      = 8; 
	public static final int MEDIUM_GARDEN     = 9; 
	public static final int LARGE_GARDEN      = 10;
	
	// [CiviCode - 1] for rank
	public static final int[] rankRequired = new int[] { 2, 3, 3, 2, 2, 4, 4, 1, 2, 3};

}
