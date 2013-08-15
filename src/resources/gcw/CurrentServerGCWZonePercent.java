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
package resources.gcw;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.scene.Point2D;

import resources.objects.ListObject;

public class CurrentServerGCWZonePercent extends ListObject {
	
	private Point2D position;
	private float radius;
	private int group;
	private float weight;
	private int percent = 50;
	private int gcwPoints = 0;
	private long lastUpdateTime = System.currentTimeMillis();
	
	public CurrentServerGCWZonePercent(Point2D position, float radius, int group, float weight) {
		this.position = position;
		this.radius = radius;
		this.group = group;
		this.weight = weight;
	}
	
	public Point2D getPosition() {
		synchronized(objectMutex) {
			return position;
		}
	}
	
	public float getRadius() {
		synchronized(objectMutex) {
			return radius;
		}
	}
	
	public int getGroup() {
		synchronized(objectMutex) {
			return group;
		}
	}
	
	public float getWeight() {
		synchronized(objectMutex) {
			return weight;
		}
	}
	
	public int getPercent() {
		synchronized(objectMutex) {
			return percent;
		}
	}
	
	public CurrentServerGCWZonePercent setPercent(int percent) {
		synchronized(objectMutex) {
			this.percent = percent;
			return this;
		}
	}
	
	public int getGCWPoints() {
		synchronized(objectMutex) {
			return gcwPoints;
		}
	}
	
	public void addGCWPoints(int gcwPoints) {
		synchronized(objectMutex) {
			this.gcwPoints += gcwPoints;
		}
	}
	
	public void removeGCWPoints(int gcwPoints) {
		synchronized(objectMutex) {
			this.gcwPoints = (((this.gcwPoints - gcwPoints) < 0) ? 0 : this.gcwPoints - gcwPoints);
		}
	}
	
	public int getLastUpdateTime() {
		synchronized(objectMutex) {
			return (int) lastUpdateTime;
		}
	}

	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.putInt(percent);
			return buffer.array();
		}
	}
	
}
