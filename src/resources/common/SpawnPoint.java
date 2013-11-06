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
package resources.common;

import com.sleepycat.persist.model.Persistent;

import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class SpawnPoint {
	
	private Point3D position;
	private Quaternion orientation;
	private int cellNumber;
	
	public SpawnPoint() { }
	
	public SpawnPoint(Point3D position, Quaternion orientation) {
		this.setPosition(position);
		this.setOrientation(orientation);		
	}
	
	public SpawnPoint(Point3D position, float oY, float oW) {
		this.setPosition(position);
		this.setOrientation(new Quaternion(oW, 0, oY, 0));
	}
	
	public SpawnPoint(float x, float y, float z, float oY, float oW) {
		this.setPosition(new Point3D(x, y, z));
		this.setOrientation(new Quaternion(oW, 0, oY, 0));		
	}
	
	public SpawnPoint(float x, float y, float z, float oY, float oW, int cellNumber) {
		this.setPosition(new Point3D(x, y, z));
		this.setOrientation(new Quaternion(oW, 0, oY, 0));	
		this.setCellNumber(cellNumber);
	}

	public Point3D getPosition() {
		return position;
	}

	public void setPosition(Point3D position) {
		this.position = position;
	}

	public Quaternion getOrientation() {
		return orientation;
	}

	public void setOrientation(Quaternion orientation) {
		this.orientation = orientation;
	}

	public int getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
	}

}
