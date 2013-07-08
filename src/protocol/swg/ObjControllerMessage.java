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

import org.apache.mina.core.buffer.IoBuffer;


import protocol.swg.objectControllerObjects.CommandEnqueue;
import protocol.swg.objectControllerObjects.DataTransform;
import protocol.swg.objectControllerObjects.DataTransformWithParent;
import protocol.swg.objectControllerObjects.ObjControllerObject;
import protocol.swg.objectControllerObjects.UnknownObjController;

public class ObjControllerMessage extends SWGMessage {
	
	private ObjControllerObject objControllerObject;
	private final int controllerObjectIndex	= 10;
	private int update;
	
	public static final int DATA_TRANSFORM 	= 0x0071;
	public static final int SPACIAL_CHAT	= 0x00F4;
	public static final int COMMAND_ENQUEUE	= 0x0116;
	public static final int USE_OBJECT 		= 0x0126;
	public static final int PLAYER_EMOTE	= 0x012E;
	public static final int DATA_TRANSFORM_WITH_PARENT = 0x00F1;
	public static final int COMBAT_ACTION = 0x00CC;
	public static final int COMBAT_SPAM = 0x0134;
	public static final int POSTURE = 0x0131;
	public static final int SIT_ON_OBJECT = 0x013B;
	public static final int OBJECT_MENU_RESPONSE = 0x0147;

	public ObjControllerMessage() { 
		
	}
	
	public ObjControllerMessage(int update, ObjControllerObject objControllerObject) {
		this.update = update;
		this.objControllerObject = objControllerObject;
	}
	
	public void deserialize(IoBuffer buffer) {
		update = buffer.getInt(6);
		objControllerObject = getControllerObject(buffer.getInt(controllerObjectIndex));
		objControllerObject.deserialize(buffer);
	}
	
	public IoBuffer serialize() {
		IoBuffer buffer = IoBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short)5);
		buffer.putInt(0x80CE5E46);
		buffer.putInt(update);
		buffer.put(objControllerObject.serialize());
		
		int size = buffer.position();
		return IoBuffer.allocate(size).put(buffer.array(), 0, size).flip();
	}
	
	private ObjControllerObject getControllerObject(int objType) {
		switch (objType) {
			case DATA_TRANSFORM: return new DataTransform();
			case DATA_TRANSFORM_WITH_PARENT: return new DataTransformWithParent();
			case COMMAND_ENQUEUE: return new CommandEnqueue();
			default: return new UnknownObjController();
		}
	}
}
