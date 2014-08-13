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

import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.lang.Math;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import protocol.swg.ObjControllerMessage;

public class DataTransform extends ObjControllerObject {
	
	private long objectId;
	private int movementIndex;
	private float yOrientation, wOrientation, xOrientation, zOrientation;
	private float xPosition, yPosition, zPosition, speed;
	private int movementStamp;
	private boolean combatFlag;
	
	public DataTransform() {
		
	}
	
	public DataTransform(Point3D position, Quaternion orientation, int movementCounter, long objectId) {
		
		xPosition = position.x;
		yPosition = position.y;
		zPosition = position.z;
		yOrientation = orientation.y;
		wOrientation = orientation.w;
		movementIndex = movementCounter;
		this.objectId = objectId;
		
	}

	
	public long    getObjectId()        { return objectId; }
	public float   getSpeed()           { return speed; }
	public float   getXOrientation()    { return xOrientation; }
	public float   getYOrientation()    { return yOrientation; }
	public float   getZOrientation()    { return zOrientation; }
	public float   getWOrientation()    { return wOrientation; }
	public float   getXPosition()       { return xPosition; }
	public float   getYPosition()       { return yPosition; }
	public float   getZPosition()       { return zPosition; }
	public short   getTransformedX()    { return (short) (xPosition * 4 + 0.5); }
	public short   getTransformedY()    { return (short) (yPosition * 4 + 0.5); }		// need to transform coordinates as they are send as a short in UTM
	public short   getTransformedZ()    { return (short) (zPosition * 4 + 0.5); }
	public int     getMovementCounter() { return movementIndex; }
	public int     getMovementStamp()   { return movementStamp; }
	public boolean getCombatFlag()      { return combatFlag; }
	
	
	public float getMovementAngle() {
		byte movementAngle = (byte) 0.0f;
		float wOrient = getWOrientation();
		float yOrient = getYOrientation();
		float sq = (float) Math.sqrt(1- (getWOrientation() * getWOrientation()));
		
		if (sq != 0) {
			if (getWOrientation() > 0 && getYOrientation() < 0) {
				wOrient *= -1;
				yOrient *= -1;
			}
			movementAngle = (byte) ((yOrient / sq) * (2 * Math.acos(wOrient) / 0.06283f));
		}
		
		return movementAngle;
	}
	
	public void deserialize(IoBuffer buffer) {
		try{
			objectId = buffer.getLong();
			buffer.getInt();
			
			movementStamp = buffer.getInt();
			movementIndex = buffer.getInt();
			
			xOrientation = buffer.getFloat();
			yOrientation = buffer.getFloat();
			zOrientation = buffer.getFloat();
			wOrientation = buffer.getFloat();
			
			xPosition = buffer.getFloat();
			yPosition = buffer.getFloat();
			zPosition = buffer.getFloat();
			speed = buffer.getFloat();
		} catch (BufferUnderflowException ex){System.err.println("BufferUnderflowException during Datatransform deserialization");}
	}
	
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(61).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putInt(ObjControllerMessage.DATA_TRANSFORM);
		result.putLong(objectId);
		result.putInt(0);
		
		result.putInt(0);
		result.putInt(movementIndex);
		
		result.putFloat(0);
		result.putFloat(yOrientation); 	//xRot
		result.putFloat(0); 			//yRot
		result.putFloat(wOrientation); 	//zRot
		
		result.putFloat(xPosition); 	//xPos
		result.putFloat(yPosition); 	//yPos
		result.putFloat(zPosition); 	//zPos
		
		result.putFloat(speed); 			//unk
		result.putFloat(0); 			//unk
		result.put((byte)0x00);	
		
		return result.flip();
	}
}
