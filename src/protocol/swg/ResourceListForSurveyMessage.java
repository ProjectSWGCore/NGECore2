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
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;
import resources.common.Opcodes;
import resources.objects.resource.GalacticResource;

public class ResourceListForSurveyMessage extends SWGMessage {
	
	private List<GalacticResource> resourceList;
	private long characterId;
	
	public ResourceListForSurveyMessage(long characterId, List<GalacticResource> resourceList) {
		this.characterId = characterId;
		this.resourceList = resourceList;
	}

	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {		
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		GalacticResource resourceElement = null;
		
		result.setAutoExpand(true);
		result.putShort((short)4);
		result.putInt(Opcodes.ResourceListForSurveyMessage);
		result.putInt(resourceList.size());	
		
		for (int i = 0; i < resourceList.size(); i++) {
			resourceElement = resourceList.get(i);
			result.putShort((short)resourceElement.getName().length()); // CStringesque length ahead
			result.put(resourceElement.getName().getBytes(StandardCharsets.US_ASCII)); 
			result.putLong(resourceElement.getId());
			result.putShort((short)resourceElement.getFileName().length());
			result.put(resourceElement.getFileName().getBytes(StandardCharsets.US_ASCII)); 
		}
		
		if (resourceElement != null) {
			result.putShort((short)resourceElement.getCategory().length());
			result.put(resourceElement.getCategory().getBytes(StandardCharsets.US_ASCII)); 
		} else
			result.putShort((short) 0);

		result.putLong(characterId);
		
		// Debugging
		int size = result.position();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		// Debugging end
		
		return result.flip();
	}
}
