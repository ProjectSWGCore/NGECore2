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
package resources.common.collidables;

import java.util.Vector;

import main.NGECore;
import net.engio.mbassy.bus.SyncMessageBus;

import org.python.core.Py;
import org.python.core.PyObject;

import engine.resources.common.Event;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

public abstract class AbstractCollidable {
	
	private PyObject callback;
	public Vector<SWGObject> collidedObjects = new Vector<SWGObject>();
	private Planet planet;
	private SyncMessageBus<Event> eventBus = new SyncMessageBus<Event>(NGECore.getInstance().getEventBusConfig());

	public abstract boolean doesCollide(SWGObject obj);
	public abstract boolean doesCollide(Point3D position);

	public PyObject getCallback() {
		return callback;
	}

	public void setCallback(PyObject callback) {
		this.callback = callback;
	}
	
	public void addCollidedObject(SWGObject obj) {
		collidedObjects.add(obj);
		EnterEvent event = new EnterEvent();
		event.object = obj;
		eventBus.publish(event);
	}
	
	public void removeCollidedObject(SWGObject obj) {
		if(!collidedObjects.contains(obj))
			return;
		collidedObjects.remove(obj);
		ExitEvent event = new ExitEvent();
		event.object = obj;
		eventBus.publish(event);
	}

	public boolean isInCollisionList(SWGObject obj) {
		return collidedObjects.contains(obj);
	}

	public void doCollisionCheck(SWGObject obj) {
		// check if already collided
		if(isInCollisionList(obj) && doesCollide(obj)) {
			return;
		} else if(isInCollisionList(obj) && !doesCollide(obj)) {
			removeCollidedObject(obj);
		} else if(doesCollide(obj) && !isInCollisionList(obj)) {
			addCollidedObject(obj);
			if(getCallback() != null)
				getCallback().__call__(Py.java2py(NGECore.getInstance()), Py.java2py(obj), Py.java2py(this));
		}
	}

	public Planet getPlanet() {
		return planet;
	}

	public void setPlanet(Planet planet) {
		this.planet = planet;
	}
	
	public SyncMessageBus<Event> getEventBus() {
		return eventBus;
	}
	
	public class EnterEvent implements Event {
		public SWGObject object;
	}

	public class ExitEvent implements Event {
		public SWGObject object;
	}
	
	public abstract Point3D getRandomPosition(Point3D origin, float minDistance, float maxDistance);
	public abstract Point3D getRandomPosition();
	

}
