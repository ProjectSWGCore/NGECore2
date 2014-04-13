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
package resources.objects.player;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Map.Entry;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.ObjectMessageBuilder;
import resources.objects.waypoint.WaypointObject;

public class PlayerMessageBuilder extends ObjectMessageBuilder {
	
	public PlayerMessageBuilder(PlayerObject playerObject) {
		setObject(playerObject);
	}

	public IoBuffer buildBaseline3() {
		
		PlayerObject player = (PlayerObject) object;
		
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short) 0x14);
		buffer.putFloat(1);
		buffer.put(getAsciiString("string_id_table"));
		buffer.putInt(0);	// spacer
		buffer.putShort((short) 0);	
		buffer.putInt(0);	// custom name
		buffer.putInt(0);	// volume
		buffer.putInt(0);	// generic int
		
		buffer.putInt(4);	// flag bitmask list size
		buffer.putInt(player.getFlagBitmask());	
		buffer.putInt(0);	
		buffer.putInt(0);	
		buffer.putInt(0);	

		buffer.putInt(4);	// profile bitmask list size
		buffer.putInt(0);	
		buffer.putInt(0);	
		buffer.putInt(0);	
		buffer.putInt(0);	

		if(player.getTitle() == null || player.getTitle().length() < 1)
			buffer.putShort((short) 0);
		else
			buffer.put(getAsciiString(player.getTitle()));	
		
		buffer.putInt(0); // born date - Needs to be in yyymmdd format - Stored as epoch time of character create
		
		buffer.putInt(player.getTotalPlayTime()); // total play time?
		
		buffer.putInt(player.getProfessionIcon());
		
		buffer.put(getAsciiString(player.getProfession()));	
		buffer.putInt(0); // GCW
		buffer.putInt(0); // PvP Kills
		buffer.putLong(0); // Lifetime GCW
		buffer.putInt(0); // Lifetime PvP Kills
		buffer.putInt(player.getCollections().length);
		buffer.putInt(player.getHighestSetBit());
		buffer.put(player.getCollections());
		buffer.putInt(0); // Unknown, part of one delta, maybe a list
		buffer.putInt(0); // Unknown, part of one delta, maybe a list
		buffer.put((byte) ((player.isShowBackpack()) ? 1 : 0));
		buffer.put((byte) ((player.isShowHelmet()) ? 1 : 0));
		
		int size = buffer.position();

		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		
		buffer.flip();
		buffer = createBaseline("PLAY", (byte) 3, buffer, size);

		return buffer;
		
	}
	
	public IoBuffer buildBaseline6() {
		
		PlayerObject player = (PlayerObject) object;
		
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short) 0x11);
		buffer.putInt((short) 0x43);
		buffer.put(getAsciiString("string_id_table"));
		buffer.putInt(0); // probably stringId
		buffer.putShort((short) 0);	// detailedDescription
		buffer.put(player.getGodLevel());
		buffer.putInt(0); // unk
		buffer.putInt(0); // unk
		buffer.putInt(0); // unk
		buffer.putInt(0); // unk
		buffer.putInt(0); // unk
		if(player.getHome() == null || player.getHome().length() < 1) {
			buffer.putShort((short) 0);
		} else {
			buffer.put(getAsciiString(player.getHome()));
		}
		buffer.putInt(0); // unk
		buffer.put((byte) 0);	// unk
		buffer.putInt(0); // unk
		buffer.putInt(0); // unk
		buffer.putInt(0); // unk
		buffer.putInt(0); // unk
		buffer.putInt(0); // unk
		buffer.putInt(0); // unk

		int size = buffer.position();

		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		
		buffer.flip();
		buffer = createBaseline("PLAY", (byte) 6, buffer, size);

		return buffer;
		
	}

	public IoBuffer buildBaseline8() {
	
		PlayerObject player = (PlayerObject) object;
			
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		buffer.putShort((short) 0x09);
		
		if(player.getXpList().isEmpty()) {
			buffer.putInt(0); 
			buffer.putInt(player.getXpListUpdateCounter()); 
		} else {
			buffer.putInt(player.getXpList().size()); 
			buffer.putInt(player.getXpListUpdateCounter()); 
			
			// no need for locking here, concurrent hash map iterator wont throw concurrency exceptions and multiple thread access at zone in is unlikely to occur for the PlayerObject
			for(Entry<String, Integer> entry : player.getXpList().entrySet()) {
					
				buffer.put((byte) 0);	
				buffer.put(getAsciiString(entry.getKey()));
				buffer.putInt(entry.getValue()); 

			}

		}
		
		if(player.getWaypoints().isEmpty()) {
			buffer.putInt(0); 
			buffer.putInt(0); 
		} else {
			buffer.putInt(player.getWaypoints().size()); 
			buffer.putInt(0); 

			synchronized(player.getWaypoints()) {
				
				for(WaypointObject waypoint : player.getWaypoints()) {
					
					buffer.put((byte) 0);	
					buffer.putLong(waypoint.getObjectID());
					buffer.putInt(waypoint.getCellId());
					buffer.putFloat(waypoint.getPosition().x);
					buffer.putFloat(waypoint.getPosition().y);
					buffer.putFloat(waypoint.getPosition().z);
					buffer.putLong(waypoint.getLocationNetworkId());
					buffer.putInt(waypoint.getPlanetCRC());
					buffer.put(getUnicodeString(waypoint.getName()));
					buffer.putLong(waypoint.getObjectID());
					buffer.put(waypoint.getColor());	
					buffer.put((byte) (waypoint.isActive() ? 1 : 0));	

				}
				
			}
		}
		
		buffer.putInt(0); // Current force power ?
		buffer.putInt(0); // Max force power ?
		buffer.putInt(0); // Current FS Quest List size ?
		buffer.putInt(0); // Current FS Quest List update counter ?
		
		buffer.putInt(0); // Completed FS Quest List size ?
		buffer.putInt(0); // Completed FS Quest List update counter ?
		
		buffer.putInt(0); // Quest Journal List size ?
		buffer.putInt(0); // Quest Journal List update counter ?

		buffer.putInt(0); // unk

		buffer.put(getAsciiString(player.getProfessionWheelPosition()));
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		buffer.flip();
		buffer = createBaseline("PLAY", (byte) 8, buffer, size);
	
		return buffer;
	
}

	public IoBuffer buildBaseline9() {
	
		PlayerObject player = (PlayerObject) object;
		
		IoBuffer buffer = bufferPool.allocate(100, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		buffer.putShort((short) 0x1F);
		
		buffer.putInt(0); // ExperimentationFlag?
		buffer.putInt(0); // CraftingStage?

		buffer.putLong(0); // Nearest Crafting Station id ?

		
		buffer.putInt(0); // Draft Schematic List Size
		buffer.putInt(0); // Draft Schematic List update counter
		
		buffer.putInt(0); // ExperimentationPoints?
		buffer.putInt(0); // CraftingStage?

		buffer.putInt(8); // unk
		
		buffer.putInt(0); // unk

		if(player.getFriendList().isEmpty()) {
			buffer.putInt(0); 
			buffer.putInt(0);
		} else {
			buffer.putInt(player.getFriendList().size()); 
			buffer.putInt(player.getFriendListUpdateCounter()); 

			synchronized(player.getFriendList()) {
				
				for(String friend : player.getFriendList()) {
					buffer.put(getAsciiString(friend));
				}
			}
		}
		
		if(player.getIgnoreList().isEmpty()) {
			buffer.putInt(0); 
			buffer.putInt(0); 
		} else {
			buffer.putInt(player.getIgnoreList().size()); 
			buffer.putInt(player.getIgnoreListUpdateCounter()); 

			synchronized(player.getIgnoreList()) {
				
				for(String ignored : player.getIgnoreList()) {
					buffer.put(getAsciiString(ignored));
				}
				
			}
		}
		
		buffer.putInt(1);
		buffer.putInt(0);	// Current stomach unused in NGE
		buffer.putInt(0x64);	// Max stomach unused in NGE
		buffer.putInt(0);	// Current drink unused in NGE
		buffer.putInt(0x64);	// Max drink unused in NGE
		buffer.putInt(0);	// Current consumable unused in NGE
		buffer.putInt(0x64);	// Max consumable unused in NGE

		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		
		buffer.putInt(0); // jedi state???
		buffer.putShort((short) 0);
		int size = buffer.position();

		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);

		
		buffer.flip();
		buffer = createBaseline("PLAY", (byte) 9, buffer, size);
	
		return buffer;
	
	}
	
	public IoBuffer buildTitleDelta(String title) {
		IoBuffer buffer = bufferPool.allocate(4 + getAsciiString(title).length, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(getAsciiString(title));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("PLAY", (byte) 3, (short) 1, (short) 0x07, buffer, size + 4);
		return buffer;
		
	}
	
	public IoBuffer buildProfessionIconDelta(int professionIcon) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(professionIcon);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("PLAY", (byte) 3, (short) 1, (short) 0x0A, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildCollectionsDelta(byte[] collections, int highestSetBit) {
		IoBuffer buffer = bufferPool.allocate(8 + collections.length, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(collections.length);
		buffer.putInt(highestSetBit);
		for (byte collection : collections) buffer.put(collection);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("PLAY", (byte) 3, (short) 1, (short) 16, buffer, size + 4);
		return buffer;		
	}
	
	public IoBuffer buildXPListDelta(String type, int amount, boolean alreadyExists) {
		PlayerObject player = (PlayerObject) object;
		player.setXpListUpdateCounter(player.getXpListUpdateCounter() + 1);

		IoBuffer result = bufferPool.allocate(15 + type.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putInt(1);
		result.putInt(player.getXpListUpdateCounter());
		result.put((byte) ((alreadyExists) ? 2 : 0));
		result.put(getAsciiString(type));
		result.putInt(amount);
		
		int size = result.position();
		result.flip();
		result = createDelta("PLAY", (byte) 8, (short) 1, (short) 0, result, size + 4);
		return result;
	}
	
	public IoBuffer buildProfessionWheelPositionDelta(String professionWheelPosition) {
		IoBuffer buffer = bufferPool.allocate(2 + professionWheelPosition.length(), false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.put(getAsciiString(professionWheelPosition));
		
		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 8, (short) 1, (short) 8, buffer, size + 4);
		return buffer;

	}
	
	public IoBuffer buildWaypointAddDelta(WaypointObject waypoint) {
		
		PlayerObject player = (PlayerObject) object;
		IoBuffer buffer = bufferPool.allocate(59 + waypoint.getName().length() * 2, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		int nextCounter = player.getWaypointListUpdateCounter() + 1;
		player.setWaypointListUpdateCounter(nextCounter);
		buffer.putInt(1);
		buffer.putInt(player.getWaypointListUpdateCounter());
		
		buffer.put((byte) 0); // updateType (SubType)
		
		buffer.putLong(waypoint.getObjectID());
		buffer.putInt(waypoint.getCellId());
		
		buffer.putFloat(waypoint.getPosition().x);
		buffer.putFloat(waypoint.getPosition().y);
		buffer.putFloat(waypoint.getPosition().z);
		
		buffer.putLong(0); // networklocationId
		buffer.putInt(waypoint.getPlanetCRC());
		
		buffer.put(getUnicodeString(waypoint.getName()));
		buffer.putLong(waypoint.getObjectID());
		
		buffer.put((byte) waypoint.getColor());
		
		if (waypoint.isActive()) { buffer.put((byte) 1); } 
		else { buffer.put((byte) 0); }
		
		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 8, (short) 1, (short) 1, buffer, size + 4);
		//System.out.println("WaypointAdd: " + buffer.getHexDump());
		return buffer;
		
	}
	
	public IoBuffer buildWaypointRemoveDelta(WaypointObject waypoint) {
		
		IoBuffer buffer = bufferPool.allocate(59 + waypoint.getName().length() * 2, false).order(ByteOrder.LITTLE_ENDIAN);
		PlayerObject player = (PlayerObject) object;
		
		int nextCounter = player.getWaypointListUpdateCounter() + 1;
		player.setWaypointListUpdateCounter(nextCounter);

		buffer.putInt(1);
		buffer.putInt(player.getWaypointListUpdateCounter());
		
		buffer.put((byte) 1); // updateType (SubType)
		
		buffer.putLong(waypoint.getObjectID());
		buffer.putInt(waypoint.getCellId());
		
		buffer.putFloat(waypoint.getPosition().x);
		buffer.putFloat(waypoint.getPosition().y);
		buffer.putFloat(waypoint.getPosition().z);
		
		buffer.putLong(0); // networklocationId
		buffer.putInt(waypoint.getPlanetCRC());
		
		buffer.put(getUnicodeString(waypoint.getName()));
		buffer.putLong(waypoint.getObjectID());
		
		buffer.put((byte) waypoint.getColor());
		
		if (waypoint.isActive()) { buffer.put((byte) 1); }
		else { buffer.put((byte) 0); }
		
		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 8, (short) 1, (short) 1, buffer, size + 4);
		
		return buffer;
		
	}
	
	public IoBuffer buildWaypointUpdateDelta(WaypointObject waypoint) {
		
		IoBuffer buffer = bufferPool.allocate(59 + waypoint.getName().length() * 2, false).order(ByteOrder.LITTLE_ENDIAN);
		PlayerObject player = (PlayerObject) object;
		
		int nextCounter = player.getWaypointListUpdateCounter() + 1;
		player.setWaypointListUpdateCounter(nextCounter);

		buffer.putInt(1);
		buffer.putInt(player.getWaypointListUpdateCounter());
		
		buffer.put((byte) 2); // updateType (SubType)
		
		buffer.putLong(waypoint.getObjectID());
		buffer.putInt(waypoint.getCellId());
		
		buffer.putFloat(waypoint.getPosition().x);
		buffer.putFloat(waypoint.getPosition().y);
		buffer.putFloat(waypoint.getPosition().z);
		
		buffer.putLong(0); // networklocationId << cluster system I guess?
		buffer.putInt(waypoint.getPlanetCRC());
		
		buffer.put(getUnicodeString(waypoint.getName()));
		buffer.putLong(waypoint.getObjectID());
		
		buffer.put((byte) waypoint.getColor());
		
		if (waypoint.isActive()) { buffer.put((byte) 1); }
		else { buffer.put((byte) 0); } // isActive. Activates automatically when created.
		
		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 8, (short) 1, (short) 1, buffer, size + 4);
		
		return buffer;
		
	}
	
	public IoBuffer buildTotalPlayTimeDelta(int totalPlayTime) {
		
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(totalPlayTime);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("PLAY", (byte) 3, (short) 1, (short) 0x09, buffer, size + 4);
		
		return buffer;
		
	}
	
	public IoBuffer buildFriendAddDelta(List<String> friends) {
		
		IoBuffer buffer = bufferPool.allocate(13 + (friends.size() * 2), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		
		PlayerObject player = (PlayerObject) object;
		
		buffer.putInt(1);
		buffer.putInt(player.getFriendListUpdateCounter());
		
		buffer.put((byte) 3); // updateType (SubType)
		
		buffer.putShort((short) (player.getFriendList().size()));
		for (String f : friends) {
			buffer.put(getAsciiString(f));
		}
		
		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 9, (short) 1, (short) 7, buffer, size + 4);

		return buffer;
		
	}
	
	public IoBuffer buildFriendRemoveDelta(List<String> friends) {
		
		IoBuffer buffer = bufferPool.allocate(13 + (friends.size() * 2), false).order(ByteOrder.LITTLE_ENDIAN);
		PlayerObject player = (PlayerObject) object;
		
		buffer.putInt(1);
		buffer.putInt(player.getFriendListUpdateCounter());
		
		buffer.put((byte) 3); // updateType (SubType)
		
		buffer.putShort((short) friends.size());
		for (String f : friends) {
			buffer.put(getAsciiString(f));
		}
		
		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 9, (short) 1, (short) 7, buffer, size + 4);

		return buffer;
	}
		
	
	public IoBuffer buildIgnoreAddDelta(List<String> ignoreList) {
		IoBuffer buffer = bufferPool.allocate(13 + (ignoreList.size() * 2) , false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		PlayerObject player = (PlayerObject) object;
		
		buffer.putInt(1);
		buffer.putInt(player.getIgnoreListUpdateCounter());
		
		buffer.put((byte) 3); // updateType (SubType)
		
		buffer.putShort((short) ignoreList.size());
		for (String ignore : ignoreList) {
			buffer.put(getAsciiString(ignore));
		}
		
		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 9, (short) 1, (short) 8, buffer, size + 4);
		
		return buffer;
	}
	
	public IoBuffer buildIgnoreRemoveDelta(List<String> ignoreList) {
		IoBuffer buffer = bufferPool.allocate(13 + (ignoreList.size() * 2) , false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.setAutoExpand(true);
		PlayerObject player = (PlayerObject) object;
		
		buffer.putInt(1);
		buffer.putInt(player.getIgnoreListUpdateCounter());
		
		buffer.put((byte) 3); // updateType (SubType)
		
		buffer.putShort((short) ignoreList.size());
		for (String ignore : ignoreList) {
			buffer.put(getAsciiString(ignore));
		}
		
		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 9, (short) 1, (short) 8, buffer, size + 4);
		
		return buffer;
	}
	
	public IoBuffer buildFlagBitmask(int bitmask) {
		IoBuffer buffer = bufferPool.allocate(20, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(4);
		buffer.putInt(bitmask);
		buffer.putInt(0);
		buffer.putInt(0);
		buffer.putInt(0);
		
		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 3, (short) 1, (short) 5, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildShowBackpackDelta(boolean showBackpack) {
		IoBuffer buffer = bufferPool.allocate(1, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.put((byte) (showBackpack ? 1 : 0));

		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 3, (short) 1, (short) 18, buffer, size + 4);
		return buffer;
	}

	public IoBuffer buildShowHelmetDelta(boolean showHelmet) {
		IoBuffer buffer = bufferPool.allocate(1, false).order(ByteOrder.LITTLE_ENDIAN);

		buffer.put((byte) (showHelmet ? 1 : 0));

		int size = buffer.position();
		buffer.flip();
		
		buffer = createDelta("PLAY", (byte) 3, (short) 1, (short) 19, buffer, size + 4);
		return buffer;
	}
	
	public IoBuffer buildGodLevelDelta(byte godLevel) {
		IoBuffer buffer = bufferPool.allocate(1, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(godLevel);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("PLAY", (byte) 6, (short) 1, (short) 0x02, buffer, size + 4);
		return buffer;
		
	}

	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendBaselines() {
		// TODO Auto-generated method stub
		
	}

}
