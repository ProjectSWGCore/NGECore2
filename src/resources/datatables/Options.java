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

public class Options {
	
	// Any uncommented ones are confirmed correct for NGE with detailed analysis
	
	public static int NONE = 0; // HAM is visible, but health is grey without the Attackable flag
	public static int SPACE_ATTACKABLE = 1; // Ships that are attackable in space.  Includes everything except stations and asteroids so far
	public static int USABLE = 2; // Vendors, collections (hand icon)
	public static int INSURED = 4; // Unused in NGE
	public static int CONVERSABLE = 8; // Conversable on ground / commable in space 
	//public static int FOLLOWINGWAYPOINT = 16; // Uncertain, could be "frozen" flag or "alert" flag or other.  Seen on rare occasions on NPCs
	public static int SOCKETED = 32; // Object has sockets
	public static int AGGRESSIVE = 64; // Always aggressive no matter what regardless of faction standing.
	public static int ATTACKABLE = 128; // Attackable
	public static int INVULNERABLE = 256; // No HAM
	public static int DISABLED = 512; // Disabled Vehicle
	//public static int CRAFTED = 1024; // Wearable TANO objects.  Isn't used for WEAOs (weapons), it's just for identifying if a TANO is a wearable probably as that's where this is seen.  Also seen on naboo small house deed however...  Could also be "crafted items"
	public static int QUEST = 2048; // Quest icon
	public static int MOUNT = 4096; // Vehicle and creature mounts
	public static int SERIAL = 8192; // Has serial number
	//public static int UNKNOWN = 16384; // Seen being requested by the client in objcontroller 0x02AB but probably not relevant
	public static int PILOT = 32768; // Pilot trainers, chassis dealers
	//public static int UNKNOWN = 65536;
	//public static int UNKNOWN = 131072;
	//public static int UNKNOWN = 262144;
	//public static int UNKNOWN = 524288;
	//public static int UNKNOWN = 1048576;
	//public static int UNKNOWN = 2097152;
	//public static int UNKNOWN = 4194304;
	public static int STARTER_SHIP = 8388508; // Seen on player scyk fighters, but no others.  Could also mean SPACE_OVERT
	
}
