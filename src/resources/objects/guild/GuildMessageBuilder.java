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

import org.apache.mina.core.buffer.IoBuffer;

import engine.clients.Client;

import resources.gcw.CurrentServerGCWZoneHistory;
import resources.gcw.OtherServerGCWZonePercent;
import resources.guild.Guild;
import resources.objects.ObjectMessageBuilder;

public class GuildMessageBuilder extends ObjectMessageBuilder {
	
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
		capacity += (guilds.getCurrentServerGCWZonePercentMap().size() * 30);
		capacity += (guilds.getCurrentServerGCWTotalPercentMap().size() * 14);
		capacity += (guilds.getCurrentServerGCWZoneHistoryMap().size() * 34);
		capacity += (guilds.getCurrentServerGCWTotalHistoryMap().size() * 18);
		capacity += (guilds.getOtherServerGCWZonePercentMap().size() * 45);
		capacity += (guilds.getOtherServerGCWTotalPercentMap().size() * 29);
		
		IoBuffer buffer = bufferPool.allocate(capacity, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 9);	// Object Count
		buffer.putInt(guilds.getServerId());
		buffer.put(getAsciiString(guilds.getSTFFile()));
		buffer.putInt(guilds.getUnknown1());
		buffer.putShort(guilds.getUnknown2());
		buffer.putInt(guilds.getCurrentServerGCWZonePercentMap().size());
		buffer.putInt(guilds.getCurrentServerGCWZonePercentMap().getUpdateCounter());
		for (String key : guilds.getCurrentServerGCWZonePercentMap().keySet()) {
			buffer.put((byte) 0);
			buffer.put(getAsciiString(key));
			buffer.put(guilds.getCurrentServerGCWZonePercentMap().get(key).getBytes());
		}
		buffer.putInt(guilds.getCurrentServerGCWTotalPercentMap().size());
		buffer.putInt(guilds.getCurrentServerGCWTotalPercentMap().getUpdateCounter());
		for (String key : guilds.getCurrentServerGCWTotalPercentMap().keySet()) {
			buffer.put((byte) 0);
			buffer.put(getAsciiString(key));
			buffer.put(guilds.getCurrentServerGCWTotalPercentMap().get(key).getBytes());
		}
		buffer.putInt(guilds.getCurrentServerGCWZoneHistoryMap().size());
		buffer.putInt(guilds.getCurrentServerGCWZoneHistoryMap().getUpdateCounter());
		for (String key : guilds.getCurrentServerGCWZoneHistoryMap().keySet()) {
			for (CurrentServerGCWZoneHistory historicZone : guilds.getCurrentServerGCWZoneHistoryMap().get(key)) {
				buffer.put((byte) 0);
				buffer.put(getAsciiString(key));
				buffer.putInt(historicZone.getLastUpdateTime());
				buffer.putInt(historicZone.getPercent());
			}
		}
		buffer.putInt(guilds.getCurrentServerGCWTotalHistoryMap().size());
		buffer.putInt(guilds.getCurrentServerGCWTotalHistoryMap().getUpdateCounter());
		for (String key : guilds.getCurrentServerGCWTotalHistoryMap().keySet()) {
			for (CurrentServerGCWZoneHistory historicTotal : guilds.getCurrentServerGCWZoneHistoryMap().get(key)) {
				buffer.put((byte) 0);
				buffer.put(getAsciiString(key));
				buffer.putInt(historicTotal.getLastUpdateTime());
				buffer.putInt(historicTotal.getPercent());
			}
		}
		buffer.putInt(guilds.getOtherServerGCWZonePercentMap().size());
		buffer.putInt(guilds.getOtherServerGCWZonePercentMap().getUpdateCounter());
		for (String key : guilds.getOtherServerGCWZonePercentMap().keySet()) {
			for (OtherServerGCWZonePercent server : guilds.getOtherServerGCWZonePercentMap().get(key)) {
				buffer.put((byte) 0);
				buffer.put(getAsciiString(key));
				buffer.put(getAsciiString(server.getZone()));
				buffer.putInt(server.getPercent());
			}
		}
		buffer.putInt(guilds.getOtherServerGCWTotalPercentMap().size());
		buffer.putInt(guilds.getOtherServerGCWTotalPercentMap().getUpdateCounter());
		for (String key : guilds.getOtherServerGCWTotalPercentMap().keySet()) {
			for (OtherServerGCWZonePercent server : guilds.getOtherServerGCWZonePercentMap().get(key)) {
				buffer.put((byte) 0);
				buffer.put(getAsciiString(key));
				buffer.put(getAsciiString(server.getZone()));
				buffer.putInt(server.getPercent());
			}
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
	
	public IoBuffer buildUnknown3(int unknown3) {
		IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putInt(unknown3);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GILD", (byte) 6, (short) 1, (short) 8, buffer, size + 4);
		
		return buffer;
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		
		switch (viewType) {
			case 3:
			{
				switch (updateType) {
					case 4:
					{
						buffer = createDelta("GILD", (byte) 3, (short) 1, (short) 4, buffer.flip(), buffer.array().length + 4);
			
						for (Client client : ((GuildObject) object).core.getActiveConnectionsMap().values()) {
							client.getSession().write(buffer);
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
						buffer = createDelta("GILD", (byte) 6, (short) 1, (short) updateType, buffer.flip(), buffer.array().length + 4);

						for (Client client : ((GuildObject) object).core.getActiveConnectionsMap().values()) {
							client.getSession().write(buffer);
						}
						
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
		
	@Override
	public void sendBaselines() {
		// TODO Auto-generated method stub
		
	}
	
}
