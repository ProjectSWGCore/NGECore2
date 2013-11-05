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
package resources.common;

import engine.resources.common.CRC;

public class Opcodes {
	
	public static int ChatDeletePersistentMessage = 0x8F251641;
	public static int ChatInstantMessageToCharacter = 0x84BB21F7;
	public static int ChatPersistentMessageToServer = 0x25A29FA6;
	public static int ChatRequestPersistentMessage = 0x07E3559F;
	public static int ChatSystemMessage = CRC.StringtoCRC("ChatSystemMessage");
	public static int ClientOpenContainerMessage = 0x2D2D6EE1;
	public static int CmdSceneReady = 0x43FD1C22;
	public static int ConnectPlayerMessage = 0x2E365218;
	public static int ClientCreateCharacter = 0xB97F3074;
	public static int ClientIdMsg = 0xD5899226;
	public static int ClientRandomNameRequest = 0xD6D1B6D1;
	public static int ClientVerifyAndLockNameRequest = 0x9eb04b9f;
	public static int DeleteCharacterMessage = 0xE87AD031;
	public static int GetMapLocationsMessage = 0x1A7AB839;
	public static int LagRequest = 0x31805EE0;
	public static int LoginClientId = 0x41131F96;
	public static int ObjControllerMessage = 0x80CE5E46;
	public static int ObjectSelectMenuMessage = 0x7CA18726;
	public static int RequestGalaxyLoopTimes = 0x7D842D68;
	public static int SelectCharacter = 0xB5098D76;
	public static int SuiEventNotification = 0x092D3564;
	public static int DeltasMessage = 0x12862153;
	public static int ChatAddFriend = 0x6FE7BD90;
	public static int GcwRegionsReq = CRC.StringtoCRC("GcwRegionsReq");
	public static int GcwRegionsRsp = CRC.StringtoCRC("GcwRegionsRsp");
	public static int GcwGroupsRsp = CRC.StringtoCRC("GcwGroupsRsp");
	public static int ExpertiseRequestMessage = 0xC19085D5;
	public static int ClientMfdStatusUpdateMessage = CRC.StringtoCRC("ClientMdfStatusUpdateMessage");
	public static int PlayMusicMessage = CRC.StringtoCRC("PlayMusicMessage");
	public static int PlanetTravelPointListRequest = 0x96405D4D;
	
}
