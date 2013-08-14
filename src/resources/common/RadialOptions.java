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

public class RadialOptions {
	
    public static int unknown	= 0;
    public static int combatTarget	= 1;
    public static int combatUntarget	= 2;
    public static int combatAttack	= 3;
    public static int combatPeace	= 4;
    public static int combatDuel	= 5;
    public static int combatDeathBlow	= 6;
    public static int examine	= 7;     
    public static int characterSheet	= 8;
    public static int tradeStart	= 9;
    public static int tradeAccept	= 10;
    public static int itemPickup	= 11;
    public static int itemEquip	= 12;
    public static int itemUnequip	= 13;
    public static int itemDrop	= 14;
    public static int itemDestroy = 15;
    public static int itemToken	= 16;
    public static int itemOpen	= 17;
    public static int itemOpenNewWindow	= 18;
    public static int itemActivate	= 19;
    public static int itemDeactivate = 20;
    public static int itemUseSelf	= 21;
    public static int itemUseOther	= 22;
    public static int itemUse	= 23;
    public static int itemSit	= 24;
    public static int itemMail = 25;
    public static int converseStart	= 26;
    public static int converseRespond	= 27;
    public static int converseResponse	= 28; // err
    public static int converseStop	= 29;
    public static int craftOptions	= 30;
    public static int craftStart	= 31;
    public static int craftHopperInput	= 32;
    public static int craftGetFinishedPrototype = 33;
    public static int missionTerminalList	= 34;
    public static int missionDetails	= 35; 
    public static int loot	= 36;
    public static int lootAll	= 37;
    public static int groupInvite	= 38;
    public static int groupJoin	= 39;
    public static int groupLeave	= 40;
    public static int groupKick	= 41;
    public static int groupDisband	= 42;
    public static int groupDecline	= 43;
    public static int itemRemoveFromCrate = 44;
    public static int petCall = 45;
    public static int unknown2 = 46; // err
    public static int creatureFollow	= 47;
    public static int creatureStopFollow	= 48;
    public static int split	= 49; // err
    public static int imageDesign	= 50;
    public static int setName	= 51;
    public static int itemRotate	= 52;
    public static int itemRotateRight	= 53;
    public static int itemRotateLeft	= 54;
    public static int itemMove	= 55;
    public static int itemMoveForward	= 56;
    public static int itemMoveBack	= 57;
    public static int itemMoveUp	= 58;
    public static int itemMoveDown	= 59;
    public static int petStore	= 60;
    public static int vehicleCall = 62;
    public static int missionAbort = 63;
    public static int missionEndDuty	= 64;
    public static int shipManageComponents	= 65;
    public static int waypointAutopilot	= 66;
    public static int programDroid	= 67;   
    public static int creatureOfferRide	= 68; // Unused
    public static int itemUse2	= 69; // Use
    public static int creatureShowCollections	= 70;
    public static int groupMakeMasterLooter	= 71;
    public static int groupMakeLeader	= 72;
    public static int groupLootOptions	= 73;
    public static int itemPitchLeft	= 74;
    public static int itemPitchRight	= 75;
    public static int itemRollRight	= 76;
    public static int itemRollLeft	= 77;
    public static int groupLootRandom = 78;
    public static int itemYawRandom = 79;
    public static int itemPitchRandom = 80;
    public static int itemRollRandom = 81;
    public static int itemReset = 82;
    public static int itemCopyPosition = 83;
    public static int itemCopyLocation = 84;
    public static int itemCopyHeight = 85;
    public static int creatureSendTell = 86;
    public static int itemSetColor = 87;
    public static int colorBlue = 88;
    public static int colorGreen = 89;
    public static int colorOrange = 90;
    public static int colorYellow = 91;
    public static int colorPurple = 92;
    public static int colorWhite = 93;
    public static int itemMoveLeft = 94;
    public static int itemMoveRight = 95;
    public static int itemRotationApply = 96;
    public static int itemRotationReset = 97;
    public static int windowLock = 98;
    public static int windowUnlock	= 99;
    public static int groupPickupPoint	= 100;
    public static int travelStarportShuttleportCamp	= 101; // Used for GPP
    public static int travelStarportShuttleport	= 102; // Used for GPP
    public static int groupRemove = 103;
    public static int voiceInvite	= 104;
    public static int voiceKickFromChannel = 105;
    public static int itemEquipAppearance	= 106;
    public static int itemUnequipAppearance	= 107;
    public static int draftSchematicEdit	= 108;
    public static int waypointCity	= 109;
    public static int waypointGuild	= 110;
    public static int shipLaunch	= 111;
    public static int shipLand	= 112;
    public static int serverPerformanceListen	= 113;
    public static int serverPerformanceWatch	= 114;
    public static int serverPerformanceListenStop	= 115;
    public static int serverPerformanceWatchStop	= 116;
    public static int serverTerminalPermissions	= 117;
    public static int serverTerminalManagement	= 118;
    public static int serverTerminalPermissionsEnter	= 119;
    public static int serverTerminalPermissionsBanned	= 120;
    public static int serverTerminalPermissionsAdmin	= 121;
    public static int serverTerminalPermissionVendor	= 122;
    public static int serverTerminalPermissionsHopper	= 123;
    public static int serverTerminalManagementStatus	= 124;
    public static int serverTerminalManagementPrivacy	= 125;
    public static int serverTerminalManagementTransfer	= 126;
    public static int serverTerminalManagementResidence	= 127;
    public static int serverTerminalManagementDestroy	= 128;
    public static int serverTerminalManagementPay	= 129;
    public static int serverTerminalCreateVendor	= 130;
    public static int serverGiveVendorMaintenance	= 131;
    public static int serveritemOptions	= 132;
    public static int serverSurveyToolRange	= 133;
    public static int serverSurveyToolResolution	= 134;
    public static int serverSurveyToolClass	= 135;
    public static int serverProbeDroidTrackTarget	= 136;
    public static int serverProbeDroidFindTarget	= 137;
    public static int serverProbeDroidActivate	= 138;
    public static int serverProbeDroidBuy	= 139;
    public static int serverTeach	= 140;
    public static int petCommand	= 141;
    public static int petFollow	= 142;
    public static int petStay	= 143;
    public static int petGuard	= 144;
    public static int petFriend	= 145;
    public static int petAttack	= 146;
    public static int petPatrol	= 147;
    public static int petGetPatrolPoint	= 148;
    public static int petClearPatrolPoint	= 149;
    public static int petAssumeFormation1	= 150;
    public static int petAssumeFormation2	= 151;
    public static int petTransfer	= 152;
    public static int petRelease	= 153;
    public static int petTrick1	= 154;
    public static int petTrick2	= 155;
    public static int petTrick3	= 156;
    public static int petTrick4	= 157;
    public static int petGroup	= 158;
    public static int petTame	= 159;
    public static int petFeed	= 160;
    public static int petSpecialAttackOne	= 161;
    public static int petSpecialAttackTwo	= 162;
    public static int petRangedAttack	= 163;
    public static int diceRoll	= 164; //Reused for HarvestMeat
    public static int diceTwoFace	= 165; //Reused for HarvestHide
    public static int diceThreeFace	= 166; //Reused for HarvestBone
    public static int diceFourFace	= 167; //Reused for Milk Me
    public static int diceFiveFace	= 168;
    public static int diceSixFace	= 169;
    public static int diceSevenFace	= 170;
    public static int diceEightFace	= 171;
    public static int diceCountOne	= 172;
    public static int diceCountTwo	= 173;
    public static int diceCountThree	= 174;
    public static int diceCountFour	= 175;
    public static int createBallot	= 176;
    public static int vote	= 177;
    public static int bombingRun	= 178;
    public static int selfDestruct	= 179;
    public static int thirtySec	= 180;
    public static int fifteenSec	= 181;
    public static int serverCampDisband	= 182;
    public static int serverCampAssumeOwnership	= 183;
    public static int serverProbeDroidProram	= 184;
    public static int serverGuildCreate	= 185;
    public static int serverGuildInfo	= 186;
    public static int serverGuildMembers	= 187;
    public static int serverGuildSponsored	= 188;
    public static int serverGuildEnemies	= 189;
    public static int serverGuildSponsor	= 190;
    public static int serverGuildDisband	= 191;
    public static int serverGuildNameChange	= 192;
    public static int serverGuildGuildManagement	= 193;
    public static int serverGuildMemberManagement	= 194;
    public static int serverManfHopperInput	= 195;
    public static int serverManfHopperOutput	= 196;
    public static int serverManfStationSchematic	= 197;
    public static int elevatorUp	= 198;
    public static int elevatorDown	= 199;
    public static int serverPetOpen	= 200;
    public static int serverPetDpad	= 201;
    public static int serverMedToolDiagnose	= 202;
    public static int serverMedToolTendWound	= 203;
    public static int serverMedToolTendDamage	= 204;
    public static int serverPetMount	= 205;
    public static int serverPetDismount	= 206;
    public static int serverPetTrainMount	= 207;
    public static int serverVehicleEnter	= 208;
    public static int serverVehicleExit	= 209;
    public static int openNaviCompDpad	= 210;
    public static int initNavicompDpad	= 211;
    public static int cityStatus	= 212;
    public static int cityCitizens	= 213;
    public static int cityStructures	= 214;
    public static int cityTreasury	= 215;
    public static int cityManagement	= 216;
    public static int cityName	= 217;
    public static int cityMilitia	= 218;
    public static int cityTaxes	= 219;
    public static int cityTreasuryDeposit	= 220;
    public static int cityTreasuryWithdraw	= 221;
    public static int cityRegister	= 222;
    public static int cityRank	= 223;
    public static int cityAdmin1	= 224;
    public static int cityAdmin2	= 225;
    public static int cityAdmin3	= 226;
    public static int cityAdmin4	= 227;
    public static int cityAdmin5	= 228;
    public static int cityAdmin6	= 229;
    public static int memoryChipProgram	= 230;
    public static int memoryChipTransfer	= 231;
    public static int memoryChipAnalyze	= 232;
    public static int equipDroidOnChip	= 233;
    public static int bankJoin	= 234;
    public static int bankQuit	= 235;
    public static int bankDepositAll	= 114;
    public static int bankWithdrawAll	= 115;
    public static int bankTransfer	= 112;
    public static int bankitems	= 113;
    public static int fireworkshowAdd	= 240;
    public static int fireworkshowRemove	= 241;
    public static int fireworkshowModify	= 242;
    public static int fireworkshowReorder	= 243;
    public static int fireworkshowData	= 244;
    public static int operateHarvester	= 245;
    public static int payMaintenance	= 246;
    public static int depositPower	= 247;
    public static int StructureStatus	= 248;
    public static int StructureOptions	= 249;
    public static int StartManufacture	= 250;
    public static int ListIngredients	= 251;
    public static int StopManufacture	= 252;
    
	private byte parentId;
	private short optionId;
	private byte optionType;
	private String description;
    
    public RadialOptions(byte parentId, short optionId, byte optionType, String description) {
    	this.setParentId(parentId);
    	this.setOptionId(optionId);
    	this.setOptionType(optionType);
    	this.setDescription(description);
    }

	public RadialOptions() {
		// TODO Auto-generated constructor stub
	}

	public byte getParentId() {
		return parentId;
	}

	public void setParentId(byte parentId) {
		this.parentId = parentId;
	}

	public short getOptionId() {
		return optionId;
	}

	public void setOptionId(short optionId) {
		this.optionId = optionId;
	}

	public byte getOptionType() {
		return optionType;
	}

	public void setOptionType(byte optionType) {
		this.optionType = optionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
