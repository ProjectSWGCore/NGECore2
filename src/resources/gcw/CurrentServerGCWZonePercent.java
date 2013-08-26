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

import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import com.sleepycat.persist.model.Persistent;

import engine.resources.scene.Point2D;

import resources.objects.ListObject;

@Persistent
public class CurrentServerGCWZonePercent extends ListObject implements Cloneable {
	
	private Point2D position;
	private float radius = 0;
	private BigDecimal weight;
	private int type = 0;
	private BigDecimal percent;
	private int gcwPoints = 1;
	private long lastUpdateTime = (System.currentTimeMillis() / ((long) 1000));
	
	public CurrentServerGCWZonePercent(Point2D position, float radius, int weight, int type) {
		this.position = position;
		this.radius = radius;
		this.weight = new BigDecimal(weight, MathContext.DECIMAL128);
		this.weight = this.weight.divide(new BigDecimal("10000000.0", MathContext.DECIMAL128), MathContext.DECIMAL128);
		this.type = type;
		this.percent = new BigDecimal("50.0", MathContext.DECIMAL128);
	}
	
	public Point2D getPosition() {
		synchronized(objectMutex) {
			return position.clone();
		}
	}
	
	public void setPosition(Point2D position) {
		synchronized(objectMutex) {
			this.position = position;
		}
	}
	
	public float getRadius() {
		synchronized(objectMutex) {
			return radius;
		}
	}
	
	public void setRadius(float radius) {
		synchronized(objectMutex) {
			this.radius = radius;
		}
	}
	
	public int getGroup() {
		synchronized(objectMutex) {
			return weight.unscaledValue().intValue();
		}
	}
	
	public BigDecimal getWeight() {
		synchronized(objectMutex) {
			return weight;
		}
	}
	
	public void setWeight(int weight) {
		synchronized(objectMutex) {
			this.weight = new BigDecimal(weight, MathContext.DECIMAL128);
			this.weight = this.weight.divide(new BigDecimal("10000000.0", MathContext.DECIMAL128), MathContext.DECIMAL128);
		}
	}
	
	public int getType() {
		synchronized(objectMutex) {
			return type;
		}
	}
	
	public void setType(int type) {
		synchronized(objectMutex) {
			this.type = type;
		}
	}
	
	public BigDecimal getPercent() {
		synchronized(objectMutex) {
			return percent;
		}
	}
	
	public CurrentServerGCWZonePercent setPercent(BigDecimal percent) {
		synchronized(objectMutex) {
			this.percent = percent;
			this.lastUpdateTime = (System.currentTimeMillis() / ((long) 1000));
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
			this.gcwPoints = (((this.gcwPoints - gcwPoints) < 1) ? 1 : this.gcwPoints - gcwPoints);
		}
	}
	
	public int getLastUpdateTime() {
		synchronized(objectMutex) {
			return ((int) lastUpdateTime);
		}
	}

	public byte[] getBytes() {
		synchronized(objectMutex) {
			IoBuffer buffer = bufferPool.allocate(4, false).order(ByteOrder.LITTLE_ENDIAN);
			buffer.putInt(percent.intValue());
			return buffer.array();
		}
	}
	
	@Override
	public CurrentServerGCWZonePercent clone() {
		try {
			CurrentServerGCWZonePercent object = (CurrentServerGCWZonePercent) super.clone();
			object.setPosition(getPosition());
			return object;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
