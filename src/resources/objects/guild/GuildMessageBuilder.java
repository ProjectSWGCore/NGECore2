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
package resources.objects.guild;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.CurrentServerGCWZoneHistory;
import resources.objects.CurrentServerGCWZonePercent;
import resources.objects.Guild;
import resources.objects.ObjectMessageBuilder;
import resources.objects.OtherServerGCWZonePercent;

public class GuildMessageBuilder extends ObjectMessageBuilder {
	
	private List<IoBuffer> zoneUpdates = new ArrayList<IoBuffer>();
	
	public GuildMessageBuilder(GuildObject guildObject) {
		setObject(guildObject);
	}
	
	public IoBuffer buildBaseline3() {
		GuildObject guilds = (GuildObject) object;
		
		IoBuffer buffer = bufferPool.allocate(((guilds.getGuildList().size() * 10) + 49), false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 5);	// Object Count
		buffer.putFloat(guilds.getComplexity());
		buffer.put(getAsciiString(guilds.getSTFFile()));
		buffer.putInt(guilds.getSTFSpacer());
		buffer.put(getAsciiString(guilds.getSTFName()));
		buffer.put(getUnicodeString(guilds.getCustomName()));
		buffer.putInt(guilds.getVolume());
		buffer.putInt(guilds.getGuildList().size());
		buffer.putInt(guilds.getGuildList().getUpdateCounter());
		for (Guild object : guilds.getGuildList().get()) {
			buffer.put(getAsciiString(object.getString()));
		}
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("GILD", (byte) 3, buffer, size);
		
		return buffer;
	}
	
	public IoBuffer buildBaseline6() {
		GuildObject guilds = (GuildObject) object;
		
		int capacity = 82;
		capacity += (guilds.getCurrentServerGCWZonePercentList().size() * 30);
		capacity += (guilds.getCurrentServerGCWTotalPercentList().size() * 14);
		capacity += (guilds.getCurrentServerGCWZoneHistoryList().size() * 34);
		capacity += (guilds.getCurrentServerGCWTotalHistoryList().size() * 18);
		capacity += (guilds.getOtherServerGCWZonePercentList().size() * 45);
		capacity += (guilds.getOtherServerGCWTotalPercentList().size() * 29);
		
		IoBuffer buffer = bufferPool.allocate(capacity, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 9);	// Object Count
		buffer.putInt(guilds.getServerId());
		buffer.put(getAsciiString(guilds.getSTFFile()));
		buffer.putInt(guilds.getUnknown1());
		buffer.putShort(guilds.getUnknown2());
		buffer.putInt(guilds.getCurrentServerGCWZonePercentList().size());
		buffer.putInt(guilds.getCurrentServerGCWZonePercentList().getUpdateCounter());
		for (CurrentServerGCWZonePercent object : guilds.getCurrentServerGCWZonePercentList().get()) {
			buffer.put(object.getUnknown1());
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getCurrentServerGCWTotalPercentList().size());
		buffer.putInt(guilds.getCurrentServerGCWTotalPercentList().getUpdateCounter());
		for (CurrentServerGCWZonePercent object : guilds.getCurrentServerGCWTotalPercentList().get()) {
			buffer.put(object.getUnknown1());
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getCurrentServerGCWZoneHistoryList().size());
		buffer.putInt(guilds.getCurrentServerGCWZoneHistoryList().getUpdateCounter());
		for (CurrentServerGCWZoneHistory object : guilds.getCurrentServerGCWZoneHistoryList().get()) {
			buffer.put(object.getUnknown1());
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getLastUpdateTime());
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getCurrentServerGCWTotalHistoryList().size());
		buffer.putInt(guilds.getCurrentServerGCWTotalHistoryList().getUpdateCounter());
		for (CurrentServerGCWZoneHistory object : guilds.getCurrentServerGCWTotalHistoryList().get()) {
			buffer.put(object.getUnknown1());
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getLastUpdateTime());
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getOtherServerGCWZonePercentList().size());
		buffer.putInt(guilds.getOtherServerGCWZonePercentList().getUpdateCounter());
		for (OtherServerGCWZonePercent object : guilds.getOtherServerGCWZonePercentList().get()) {
			buffer.put(object.getUnknown1());
			buffer.put(getAsciiString(object.getServer()));
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getOtherServerGCWTotalPercentList().size());
		buffer.putInt(guilds.getOtherServerGCWTotalPercentList().getUpdateCounter());
		for (OtherServerGCWZonePercent object : guilds.getOtherServerGCWTotalPercentList().get()) {
			buffer.put(object.getUnknown1());
			buffer.put(getAsciiString(object.getServer()));
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getUnknown3());

		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("GILD", (byte) 6, buffer, size);
		
		return buffer;
	}
	
	public IoBuffer buildComplexity(float complexity) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putFloat(complexity);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GILD", (byte) 3, (short) 1, (short) 0, buffer, size + 4);
		
		return buffer;
	}
	
	public IoBuffer buildSTF(String STFFile, int STFSpacer, String STFName) {
		IoBuffer buffer = bufferPool.allocate((STFFile.length() + STFName.length() + 8), false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.put(getAsciiString(STFFile));
		buffer.putInt(STFSpacer);
		buffer.put(getAsciiString(STFName));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GILD", (byte) 3, (short) 1, (short) 1, buffer, size + 4);
		
		return buffer;
	}
	
	public IoBuffer buildCustomName(String customName) {
		IoBuffer buffer = bufferPool.allocate((customName.length() + 4), false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.put(getUnicodeString(customName));
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GILD", (byte) 3, (short) 1, (short) 2, buffer, size + 4);
		
		return buffer;
	}
	
	public IoBuffer buildVolume(int volume) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(volume);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GILD", (byte) 3, (short) 1, (short) 3, buffer, size + 4);
		
		return buffer;
	}
	
	public IoBuffer buildServerId(int serverId) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(serverId);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GILD", (byte) 6, (short) 1, (short) 0, buffer, size + 4);
		
		return buffer;
	}
	
	public IoBuffer buildUnknowns(int unknown1, short unknown2) {
		IoBuffer buffer = bufferPool.allocate(6, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(unknown1);
		buffer.putShort(unknown2);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GILD", (byte) 6, (short) 1, (short) 1, buffer, size + 4);
		
		return buffer;
	}
	
	public IoBuffer buildGCWDelta() {
		synchronized(zoneUpdates) {
			if (zoneUpdates.size() > 0) {
				int updates = zoneUpdates.size();
				int size = 4;
				
				for (int i = 0; i < zoneUpdates.size(); i++) {
					size += zoneUpdates.get(i).position();
				}
				
				IoBuffer buffer = bufferPool.allocate(size, false).order(ByteOrder.LITTLE_ENDIAN);
				
				for (int i = 0; i < zoneUpdates.size(); i++) {
					buffer.put(zoneUpdates.get(i).array());
				}
				
				zoneUpdates.clear();
				
				buffer.flip();
				
				return createDelta("GILD", (byte) 6, (short) updates, buffer, size);
			} else {
				return null;
			}
		}
	}
	
	public IoBuffer buildUnknown3(int unknown3) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(unknown3);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GILD", (byte) 6, (short) 1, (short) 8, buffer, size + 4);
		
		return buffer;
	}
	
	final protected static char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		synchronized(zoneUpdates) {
			switch (viewType) {
				case 3:
				{
					switch (updateType) {
						case 4:
						{
							buffer.flip();
							buffer = createDelta("GILD", (byte) 3, (short) 1, (short) 4, buffer, buffer.position() + 4);
			
							for (int i = 0; i < ((GuildObject) object).core.getActiveConnectionsMap().size(); i++) {
								((GuildObject) object).core.getActiveConnectionsMap().get(i).getSession().write(buffer);
							}
							
							break;
						}
					}
				}
				case 6:
				{
					switch (updateType) {
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
						{
							buffer = createDeltaObject(updateType, buffer, buffer.position());
							//System.out.println("Packet: " + bytesToHex(buffer.array()));
							zoneUpdates.add(buffer);
							break;
						}
						default:
						{
							return;
						}
					}
				}
			}
		}
	}
		
	@Override
	public void sendBaselines() {
		// TODO Auto-generated method stub
		
	}
	
}
