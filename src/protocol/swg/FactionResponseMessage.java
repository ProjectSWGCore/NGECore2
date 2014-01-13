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
package protocol.swg;

import java.nio.ByteOrder;
import java.util.Map;
import java.util.TreeMap;

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.Opcodes;

public class FactionResponseMessage extends SWGMessage {
	
	private Map<String, Integer> factionStandingMap;
	private int factionRank = 0;
	private int rebelPoints = 0;
	private int imperialPoints = 0;
	
	public FactionResponseMessage(Map<String, Integer> factionStandingMap, int factionRank) {
		this.factionStandingMap = new TreeMap<String, Integer>();
		this.factionStandingMap.putAll(factionStandingMap);
		this.factionRank = factionRank;
		
		if (this.factionStandingMap.containsKey("rebel")) {
			this.rebelPoints = this.factionStandingMap.get("rebel");
		}
		
		if (this.factionStandingMap.containsKey("imperial")) {
			this.imperialPoints = this.factionStandingMap.get("imperial");
		}
	}
	
	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {
		int size = 26 + (factionStandingMap.size() * 4);
		
		for (String faction : factionStandingMap.keySet()) {
			size += (2 + faction.length());
		}
		
		IoBuffer result = IoBuffer.allocate(size, false).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		result.putShort((short) 2);
		result.putInt(Opcodes.FactionResponseMessage);
		result.putInt(factionRank);
		result.putInt(rebelPoints);
		result.putInt(imperialPoints);
		result.putInt(factionStandingMap.size());
		for (String faction : factionStandingMap.keySet()) {
			result.put(getAsciiString(faction));
		}
		result.putInt(factionStandingMap.size());
		for (Integer points : factionStandingMap.values()) {
			result.putFloat((float) points);
		}
		result.flip();
		return result;
	}
	
}
