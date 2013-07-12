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

import resources.objects.CurrentServerGCWZoneInfo;
import resources.objects.CurrentServerGCWZonePercent;
import resources.objects.Guild;
import resources.objects.ObjectMessageBuilder;
import resources.objects.OtherServerGCWZonePercent;

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
		buffer.putInt(guilds.getGuildListUpdateCounter());
		for (Guild object : guilds.getGuildList()) {
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
		//int capacity = 77;
		capacity += (guilds.getCurrentServerGCWZonePercentList().size() * 30);
		capacity += (guilds.getCurrentServerGCWTotalPercentList().size() * 14);
		capacity += (guilds.getCurrentServerGCWZoneInfoList().size() * 34);
		capacity += (guilds.getCurrentServerGCWTotalInfoList().size() * 18);
		capacity += (guilds.getOtherServerGCWZonePercentList().size() * 45);
		capacity += (guilds.getOtherServerGCWTotalPercentList().size() * 29);
		
		IoBuffer buffer = bufferPool.allocate(capacity, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort((short) 9);	// Object Count
		buffer.putInt(guilds.getServerId());
		buffer.put(getAsciiString(guilds.getSTFFile()));
		buffer.putInt(guilds.getUnknown1());
		buffer.putShort(guilds.getUnknown2());
		buffer.putInt(guilds.getCurrentServerGCWZonePercentList().size());
		buffer.putInt(guilds.getCurrentServerGCWZonePercentListUpdateCounter());
		for (CurrentServerGCWZonePercent object : guilds.getCurrentServerGCWZonePercentList()) {
			buffer.put(object.getUnknown());
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getCurrentServerGCWTotalPercentList().size());
		buffer.putInt(guilds.getCurrentServerGCWTotalPercentListUpdateCounter());
		for (CurrentServerGCWZonePercent object : guilds.getCurrentServerGCWTotalPercentList()) {
			buffer.put(object.getUnknown());
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getCurrentServerGCWZoneInfoList().size());
		buffer.putInt(guilds.getCurrentServerGCWZoneInfoListUpdateCounter());
		for (CurrentServerGCWZoneInfo object : guilds.getCurrentServerGCWZoneInfoList()) {
			buffer.put(object.getUnknown1());
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getUnknown2());
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getCurrentServerGCWTotalInfoList().size());
		buffer.putInt(guilds.getCurrentServerGCWTotalInfoListUpdateCounter());
		for (CurrentServerGCWZoneInfo object : guilds.getCurrentServerGCWTotalInfoList()) {
			buffer.put(object.getUnknown1());
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getUnknown2());
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getOtherServerGCWZonePercentList().size());
		buffer.putInt(guilds.getOtherServerGCWZonePercentListUpdateCounter());
		for (OtherServerGCWZonePercent object : guilds.getOtherServerGCWZonePercentList()) {
			buffer.put(object.getUnknown());
			buffer.put(getAsciiString(object.getServer()));
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(guilds.getOtherServerGCWTotalPercentList().size());
		buffer.putInt(guilds.getOtherServerGCWTotalPercentListUpdateCounter());
		for (OtherServerGCWZonePercent object : guilds.getOtherServerGCWTotalPercentList()) {
			buffer.put(object.getUnknown());
			buffer.put(getAsciiString(object.getServer()));
			buffer.put(getAsciiString(object.getZone()));
			buffer.putInt(object.getPercent());
		}
		buffer.putInt(5); // Unknown

		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("GILD", (byte) 6, buffer, size);
		
		return buffer;
	}
	
	@Override
	public void sendBaselines() {
		// TODO Auto-generated method stub
		
	}

}
