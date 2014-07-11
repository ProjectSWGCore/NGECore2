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
	
	public static final int AuctionQueryHeadersMessage = CRC.StringtoCRC("AuctionQueryHeadersMessage");
	public static final int CreateImmediateAuctionMessage = CRC.StringtoCRC("CreateImmediateAuctionMessage");
	public static final int ChatLeaveRoom = 0x493E3FFA;
	public static final int ChatEnterRoomById = CRC.StringtoCRC("ChatEnterRoomById");
	public static final int ChatSendToRoom = CRC.StringtoCRC("ChatSendToRoom");
	public static final int ChatDeletePersistentMessage = 0x8F251641;
	public static final int ChatInstantMessageToCharacter = 0x84BB21F7;
	public static final int ChatPersistentMessageToServer = 0x25A29FA6;
	public static final int ChatRequestPersistentMessage = 0x07E3559F;
	public static final int ChatRequestRoomList = 0x4C3D2CFA;
	public static final int ChatSystemMessage = CRC.StringtoCRC("ChatSystemMessage");
	public static final int ClientOpenContainerMessage = 0x2D2D6EE1;
	public static final int CommoditiesItemTypeListRequest = 0x48F493C5;
	public static final int CommoditiesResourceTypeListRequest = 0xCB1AE82D;
	public static final int NewbieTutorialResponse = 0xCA88FBAD;
	public static final int CmdSceneReady = 0x43FD1C22;
	public static final int CommPlayerMessage = 0x594AD258;
	public static final int ConnectPlayerMessage = 0x2E365218;
	public static final int ClientCreateCharacter = 0xB97F3074;
	public static final int ClientIdMsg = 0xD5899226;
	public static final int ClientRandomNameRequest = 0xD6D1B6D1;
	public static final int ClientVerifyAndLockNameRequest = 0x9eb04b9f;
	public static final int DeleteCharacterMessage = 0xE87AD031;
	public static final int ErrorMessage = 0xB5ABF91A;
	public static final int GetMapLocationsMessage = 0x1A7AB839;
	public static final int IsVendorOwnerMessage = CRC.StringtoCRC("IsVendorOwnerMessage");
	public static final int LagRequest = 0x31805EE0;
	public static final int LoginClientId = 0x41131F96;
	public static final int ObjControllerMessage = 0x80CE5E46;
	public static final int ObjectMenuSelectMessage = 0x7CA18726;
	public static final int RequestGalaxyLoopTimes = 0x7D842D68;
	public static final int SelectCharacter = 0xB5098D76;
	public static final int SuiEventNotification = 0x092D3564;
	public static final int BaselinesMessage = 0x68A75F0C;
	public static final int DeltasMessage = 0x12862153;
	public static final int ChatAddFriend = 0x6FE7BD90;
	public static final int GcwRegionsReq = CRC.StringtoCRC("GcwRegionsReq");
	public static final int GcwRegionsRsp = CRC.StringtoCRC("GcwRegionsRsp");
	public static final int GcwGroupsRsp = CRC.StringtoCRC("GcwGroupsRsp");
	public static final int ExpertiseRequestMessage = 0xC19085D5;
	public static final int ClientMfdStatusUpdateMessage = CRC.StringtoCRC("ClientMdfStatusUpdateMessage");
	public static final int PlayMusicMessage = CRC.StringtoCRC("PlayMusicMessage");
	public static final int PlanetTravelPointListRequest = 0x96405D4D;
	public static final int FactionRequestMessage = CRC.StringtoCRC("FactionRequestMessage");
	public static final int FactionResponseMessage = CRC.StringtoCRC("FactionResponseMessage");
	public static final int SetWaypointColor = CRC.StringtoCRC("SetWaypointColor");
	public static final int GuildRequestMessage = CRC.StringtoCRC("GuildRequestMessage");
	public static final int PlayerMoneyRequest = CRC.StringtoCRC("PlayerMoneyRequest");
	public static final int LagReport = CRC.StringtoCRC("LagReport");
	public static final int GetSpecificMapLocationsMessage = CRC.StringtoCRC("GetSpecificMapLocationsMessage");
	public static final int SetCombatSpamFilter = CRC.StringtoCRC("SetCombatSpamFilter");
	public static final int SetCombatSpamRangeFilter = CRC.StringtoCRC("SetCombatSpamRangeFilter");
	public static final int SetLfgInterests = CRC.StringtoCRC("SetLfgInterests");
	public static final int SetFurnitureRoationDegree = CRC.StringtoCRC("SetFurnitureRotationDegree");
	public static final int SetJediSlotInfo = CRC.StringtoCRC("SetJediSlotInfo");
	public static final int CollectionServerFirstListRequest = CRC.StringtoCRC("CollectionServerFirstListRequest");
	public static final int ShowHelmet = CRC.StringtoCRC("ShowHelmet");
	public static final int ShowBackpack = CRC.StringtoCRC("ShowBackpack");
	public static final int CreateAuctionMessage = CRC.StringtoCRC("CreateAuctionMessage");
	public static final int ChatOnEnteredRoom = CRC.StringtoCRC("ChatOnEnteredRoom");
	public static final int ChatCreateRoom = CRC.StringtoCRC("ChatCreateRoom");
	public static final int ChatQueryRoom = 0x9CF2B192;
	public static final int Unknown = 0x173B91C2; // packet sent to server on every character load-in
	public static final int GetAuctionDetails = CRC.StringtoCRC("GetAuctionDetails");
	public static final int CancelLiveAuctionMessage = CRC.StringtoCRC("CancelLiveAuctionMessage");
	public static final int BidAuctionMessage = CRC.StringtoCRC("BidAuctionMessage");
	public static final int RetrieveAuctionItemMessage = CRC.StringtoCRC("RetrieveAuctionItemMessage");
	public static final int LaunchBrowserMessage = CRC.StringtoCRC("LaunchBrowserMessage");
	public static final int LoginServerId = 0x58C07F21;
	public static final int LogoutMessage = 0x42FD19DD;
	public static final int AttributeListMessage = 0xF3F12F2A;
	public static final int GuildResponseMessage = 0x32263F20;
	public static final int CreateCharacterSuccess = 0x1DB575CC;
	public static final int PlayerMoneyResponse = 0x367E737E;
	public static final int CmdStartScene = 0x3AE6DFAE;
	
}
