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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;

@SuppressWarnings("unused")

public class CommandEnqueue extends ObjControllerObject {
	
	public static final int SOCIAL_INTERNAL			= 0x32CF1BEE;
	public static final int SPATIAL_CHAT			= 0xEE540CF7;
	public static final int SPATIAL_CHAT_INTERNAL	= 0x7C8D63D4;
	public static final int SIT_SERVER				= 0xB719FA26;
	public static final int PRONE					= 0xBD8D02AF;
	public static final int KNEEL					= 0x01B48B26;
	public static final int STAND					= 0xA8A25C79;
	public static final int ATTACK					= 0xA8FEF90A;
	public static final int TRANSFERITEM			= 0x3CFB449D;

    //ent
	// this really needed?
    public static final int STARTDANCE 				= 0x7B1DCBE0;
    public static final int STOPDANCE 				= 0xECC171CC;
    public static final int FLOURISH 				= 0xC8998CE9;
    public static final int FLO 					= 0x3B159B76;
    public static final int BANDFLOURISH 			= 0xF4C60EC3;
    public static final int BANDFLO 				= 0xDD3FB008;
	
	private int actionCounter;
	private int commandCRC;
	private long targetId;
	private ObjControllerObject commandObject;
	private final int commandObjectIndex = 20;
	private long objectId;
	private String commandArguments;
	
	public CommandEnqueue() {
		
	}
	public CommandEnqueue(int actionCounter, ObjControllerObject commandObject) {
		this.actionCounter = actionCounter;
		this.commandObject = commandObject;
	}
	
	public void deserialize(IoBuffer buffer) {
		objectId         = buffer.getLong();
		buffer.getInt();		
		actionCounter	 = buffer.getInt();

		commandCRC       = buffer.getInt();
		targetId		 = buffer.getLong();
		int size = buffer.getInt();
		try {
			commandArguments = new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		//commandArguments = getNextUnicodeString(buffer);
		//commandObject	 = getCommandObject(commandCRC);
	}
	
	public IoBuffer serialize() {
		return IoBuffer.allocate(0);
	}
	
	
	public int getActionCounter() { return actionCounter; }
	public int getCommandCRC()    { return commandCRC; }
	public long getObjectID()     { return objectId; }
	public long getTargetID()     { return targetId; }
	
	public ObjControllerObject getCommandObject() { return commandObject; }
	public String getCommandArguments() { return commandArguments; }
	
}
