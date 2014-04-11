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

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import protocol.swg.SWGMessage;
import resources.objects.creature.CreatureObject;
import resources.objects.resource.GalacticResource;
import resources.objects.tangible.TangibleObject;

public class ResourceListForSurveyMessage extends SWGMessage {
	
	private TangibleObject tool;
	private List<GalacticResource> resourceList;
	
	public ResourceListForSurveyMessage(TangibleObject tool, List<GalacticResource> resourceList) {
		this.tool = tool;
		this.resourceList = resourceList;
	}
	
	public ResourceListForSurveyMessage(NGECore core, TangibleObject tool, CreatureObject surveyor) {
		this.tool = tool;
		this.resourceList = new ArrayList<GalacticResource>();
		byte generalType = 0;
		String toolName = tool.getDetailName();
		if (toolName.equals("survey_tool_mineral"))
			generalType = (byte) 0;
		if (toolName.equals("survey_tool_inorganic"))
			generalType = (byte) 1;
		if (toolName.equals("survey_tool_organic") || toolName.equals("survey_tool_lumber"))
			generalType = (byte) 2;
		if (toolName.equals("survey_tool_gas"))
			generalType = (byte) 3;
		if (toolName.equals("survey_tool_moisture"))
			generalType = (byte) 4;
		if (toolName.equals("survey_tool_solar"))
			generalType = (byte) 5;
		if (toolName.equals("survey_tool_wind"))
			generalType = (byte) 6;
		if (toolName.equals("survey_tool_liquid"))
			generalType = (byte) 7;
		
		Vector<GalacticResource> planetVector = core.resourceService.getSpawnedResourcesByPlanetAndType(surveyor.getPlanetId(),generalType);
		
		resourceList = new ArrayList<GalacticResource>(planetVector);
		
	}

	public void deserialize(IoBuffer data) {
		
	}
	
	public IoBuffer serialize() {		
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);	
		result.setAutoExpand(true);
		result.putShort((short)4);
		result.putInt(0x8A64B1D5);
		result.putInt(resourceList.size());	
		GalacticResource resourceElement = null;
		for (int i = 0; i < resourceList.size(); i++) {
			resourceElement = resourceList.get(i);
			try {
				result.putShort((short)resourceElement.getName().length()); // CStringesque length ahead
				result.put(resourceElement.getName().getBytes("US-ASCII")); 
				result.putLong(resourceElement.getId());
				result.putShort((short)resourceElement.getFileName().length());
				result.put(resourceElement.getFileName().getBytes("US-ASCII")); 
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				e.getMessage();
				e.getCause();
			} 		
		}
		if (resourceElement != null) {
			try {
				result.putShort((short)resourceElement.getCategory().length());
				result.put(resourceElement.getCategory().getBytes("US-ASCII")); 
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		} else {
			result.putShort((short)0);
		}

		result.putLong(tool.getObjectID());
		
		int size = result.position();
		result.flip();
		tools.CharonPacketUtils.printAnalysis(IoBuffer.allocate(size).put(result.array(), 0, size).flip());
		return IoBuffer.allocate(size).put(result.array(), 0, size).flip();
	}
}
