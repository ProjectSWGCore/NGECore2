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
package resources.objects.intangible;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import resources.objects.ObjectMessageBuilder;
import engine.clients.Client;
import engine.resources.objects.Baseline;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class IntangibleObject extends SWGObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private transient IntangibleMessageBuilder messageBuilder;
	
	public IntangibleObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
	}
	
	public IntangibleObject() { 
		super();
	}
	
	public void initAfterDBLoad() {
		super.init();
	}
	
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		return baseline;
	}
	
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("genericInt", 0);
		return baseline;
	}
	
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		return baseline;
	}
	
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	public int getGenericInt() {
		return (int) getBaseline(3).get("genericInt");
	}
	
	public void setGenericInt(int genericInt) {
		notifyClients(getBaseline(3).set("genericInt", genericInt), true);
	}
	
	public void incrementGenericInt(int increase) {
		setGenericInt((getGenericInt() + increase));
	}
	
	public void decrementGenericInt(int decrease) {
		setGenericInt((((getGenericInt() - decrease) < 1) ? 0 : (getGenericInt() - decrease)));
	}
	
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, notifySelf);
	}
	
	public ObjectMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new IntangibleMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(getBaseline(3).getBaseline());
			destination.getSession().write(getBaseline(6).getBaseline());
			
			// FIXME Check if destination has view permissions to this
			if (this.getContainer() == destination.getParent() || this.getGrandparent() == destination.getParent()) {
				destination.getSession().write(getBaseline(8).getBaseline());
				destination.getSession().write(getBaseline(9).getBaseline());
			}
		}
	}
	
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		
	}
	
}
