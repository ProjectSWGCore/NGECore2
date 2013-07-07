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

import java.nio.ByteOrder;

import java.lang.Math;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;



import protocol.swg.ObjControllerMessage;

public class DataTransformWithParent extends ObjControllerObject {
	
	private long objectId;
	private int movementIndex;
	private float xOrientation, yOrientation, zOrientation, wOrientation;
	private float xPosition, yPosition, zPosition, speed;
	private long cellId;
	private int movementStamp;
	private boolean combatFlag;
	
	public DataTransformWithParent() {
		
	}
	
	public DataTransformWithParent(Point3D position, Quaternion orientation, int movementCounter, long objectId, long cellId) {
		
		xPosition = position.x;
		yPosition = position.y;
		zPosition = position.z;
		yOrientation = orientation.y;
		wOrientation = orientation.w;
		movementIndex = movementCounter;
		this.objectId = objectId;
		this.cellId = cellId;

	}
	
	public void deserialize(IoBuffer buffer) {
		objectId = buffer.getLong();
		buffer.getInt();
		
		movementStamp = buffer.getInt();
		movementIndex = buffer.getInt();
		cellId = buffer.getLong();
		
		xOrientation = buffer.getFloat();
		yOrientation = buffer.getFloat();
		zOrientation = buffer.getFloat();
		wOrientation = buffer.getFloat();
		
		xPosition = buffer.getFloat();
		yPosition = buffer.getFloat();
		zPosition = buffer.getFloat();
		speed = buffer.getFloat();
	}
	
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(69).order(ByteOrder.LITTLE_ENDIAN);
		
		result.putInt(ObjControllerMessage.DATA_TRANSFORM_WITH_PARENT);
		result.putLong(objectId);
		result.putInt(0);
		
		result.putInt(0);
		result.putInt(movementIndex+1);
		result.putLong(cellId);
		
		result.putFloat(0);
		result.putFloat(yOrientation); 	//xRot
		result.putFloat(0); 			//yRot
		result.putFloat(wOrientation); 	//zRot
		
		result.putFloat(xPosition); 	//xPos
		result.putFloat(yPosition); 	//yPos
		result.putFloat(zPosition); 	//zPos
		
		result.putFloat(speed); 			//unk
		result.putFloat(0); 			//unk
		result.put((byte)0x01);	
		return result.flip();
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
	public short   getTransformedX()    { return (short) (xPosition * 8 + 0.5); }
	public short   getTransformedY()    { return (short) (yPosition * 8 + 0.5); }		// need to transform coordinates as they are send as a short in UTM
	public short   getTransformedZ()    { return (short) (zPosition * 8 + 0.5); }
	public int     getMovementCounter() { return movementIndex; }
	public int     getMovementStamp()   { return movementStamp; }
	public boolean getCombatFlag()      { return combatFlag; }
	public long    getCellId()          { return cellId; }
	
	public float getMovementAngle() { 
		byte MovementAngle = (byte) 0.0f;
		float wOrient = getWOrientation();
		float yOrient = getYOrientation();
		float sq = (float) Math.sqrt(1- (getWOrientation() * getWOrientation()));
		
		if (sq != 0) {
			if (getWOrientation() > 0 && getYOrientation() < 0) {
				wOrient *= -1;
				yOrient *= -1;
			}
			MovementAngle = (byte) ((yOrient / sq) * (2 * Math.acos(wOrient) / 0.06283f));
		}
		return MovementAngle;
	}
}
