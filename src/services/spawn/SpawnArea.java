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
package services.spawn;

import java.util.Vector;

import net.engio.mbassy.listener.Handler;

import resources.common.collidables.AbstractCollidable;
import resources.common.collidables.AbstractCollidable.EnterEvent;
import resources.common.collidables.AbstractCollidable.ExitEvent;

import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

public abstract class SpawnArea {
	
	private Planet planet;
	private Vector<String> templates;
	private AbstractCollidable area;
	private Vector<SWGObject> observers = new Vector<SWGObject>();
	
	public SpawnArea(Planet planet, AbstractCollidable area) {
		this.planet = planet;
		this.area = area;
		area.getEventBus().subscribe(this);
	}

	public Planet getPlanet() {
		return planet;
	}

	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

	public Vector<String> getTemplates() {
		return templates;
	}

	public void setTemplates(Vector<String> templates) {
		this.templates = templates;
	}

	public AbstractCollidable getArea() {
		return area;
	}

	public void setArea(AbstractCollidable area) {
		this.area = area;
	}
	
	@Handler
	public abstract void onEnter(EnterEvent event);
	@Handler
	public abstract void onExit(ExitEvent event);

	public Vector<SWGObject> getObservers() {
		return observers;
	}
	
	public Point3D getRandomPosition() {
		
		int tries = 0;
		
		while(tries++ < 10) {
			Point3D randomPos = area.getRandomPosition();
			if(area.doesCollide(randomPos))
				return randomPos;
		}
		
		return null;
		
	}
	
	public Point3D getRandomPosition(Point3D origin, float minDistance, float maxDistance) {
		
		int tries = 0;
		
		while(tries++ < 10) {
			Point3D randomPos = area.getRandomPosition(origin, minDistance, maxDistance);
			if(area.doesCollide(randomPos))
				return randomPos;
		}
		
		return null;
		
	}

	
}
