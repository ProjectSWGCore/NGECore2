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

import java.nio.ByteBuffer;

import protocol.Message;
import protocol.swg.chat.ChatInstantMessageToCharacter;


public class SWGMessageFactory {
	
	public Message getMessage(byte[] data) {
		
		/*if (data.length < 4)
			return new UnknownMessage(data);*/
		
		Message message;
		
		if (data[0] < 0x10 && data[1] == 0x00)
			message = getMessageType(data);
		else {
			
			message = getMessageType(data);
			
		}
		
		return message;
		
	}
	private Message getMessageType(byte[] data) {
		
		try {
			
			int opcode = ByteBuffer.wrap(data).getInt(2);
			switch (Integer.reverseBytes(opcode)) {
				
				case 0x31805EE0: return new LagRequest();
				case 0x41131F96: return new LoginClientId();
				case 0x80CE5E46: return new ObjControllerMessage();
				case 0x8E33ED05: return new RequestExtendedClusterInfo();
				case 0xB5098D76: return new SelectCharacter();
				case 0xD5899226: return new ClientIdMsg();
				case 0x84BB21F7: return new ChatInstantMessageToCharacter();
				case 0xD6D1B6D1: return new ClientRandomNameRequest();
				case 0x9EB04B9F: return new ClientVerifyAndLockNameRequest();
				case 0xB97F3074: return new ClientCreateCharacter();
				//case 0xE87AD031: return new DeleteCharacterMessage(data);
				case 0x1A7AB839: return new GetMapLocationsMessage();
				case 0x43FD1C22: return new CmdSceneReady();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		return null;
		
		//return new UnknownMessage(data);
		
	}
	
}
