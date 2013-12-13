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
package resources.z.exp.objects.intangible;

import org.apache.mina.core.buffer.IoBuffer;

import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.object.BaseObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class IntangibleObject extends BaseObject {
	
	@NotPersistent
	private IntangibleMessageBuilder messageBuilder;
	
	public IntangibleObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
	}
	
	public IntangibleObject() { 
		super();
	}
	
	@Override
	public void initializeBaselines() {
		super.initializeBaselines();
		initializeBaseline(8);
		initializeBaseline(9);
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("genericInt", 0);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	public int getGenericInt() {
		synchronized(objectMutex) {
			return (int) baseline3.get("genericInt");
		}
	}
	
	public void setGenericInt(int genericInt) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("genericInt", genericInt);
		}
		
		notifyClients(buffer, true);
	}
	
	public void incrementGenericInt(int increase) {
		setGenericInt((getGenericInt() + increase));
	}
	
	public void decrementGenericInt(int decrease) {
		setGenericInt((((getGenericInt() - decrease) < 1) ? 0 : (getGenericInt() - decrease)));
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, notifySelf);
	}
	
	@Override
	public IntangibleMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new IntangibleMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			//destination.getSession().write(baseline3.getBaseline());
			//destination.getSession().write(baseline6.getBaseline());
			//destination.getSession().write(baseline8.getBaseline());
			//destination.getSession().write(baseline9.getBaseline());
		}
	}
	
}
