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
package protocol.swg.objectControllerObjects;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;
import engine.resources.common.CRC;
import engine.resources.objects.Baseline;

public class ShowQuestAcceptWindow extends ObjControllerObject {

	private long objectId;
	private String questName;
	
	public ShowQuestAcceptWindow(long objectId, String questName) {
		this.objectId = objectId;
		this.questName = questName;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer buffer = Baseline.createBuffer(20);
		
		buffer.putInt(ObjControllerMessage.SHOW_QUEST_ACCEPT_WINDOW);
		buffer.putLong(objectId);
		buffer.putInt(0);
		
		buffer.putInt(CRC.StringtoCRC("quest/" + questName));
		
		return buffer.flip();
	}

}
