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
package resources.objects.staticobject;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

import engine.clients.Client;
import engine.resources.objects.Baseline;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

public class StaticObject extends SWGObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private transient StaticMessageBuilder messageBuilder;
	
	public StaticObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
	}
	
	public StaticObject() {
		super();
	}
	
	@Override
	public void initAfterDBLoad() {
		super.init();
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		return baseline;
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, false);
	}
	
	@Override
	public StaticMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new StaticMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(getBaseline(3).getBaseline());
			destination.getSession().write(getBaseline(6).getBaseline());
			
			Client parent = ((getGrandparent() == null) ? null : getGrandparent().getClient());
			
			if (parent != null && destination == parent) {
				destination.getSession().write(getBaseline(8).getBaseline());
				destination.getSession().write(getBaseline(9).getBaseline());
			}
		}
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		// TODO Auto-generated method stub
		
	}
	
}
