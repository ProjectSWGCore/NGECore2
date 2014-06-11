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

import java.util.Random;

import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

public class CollidableCircle extends AbstractCollidable {
	
	private Point3D center;
	private float radius;
	private float radiusSquared;
	// for bh traps
	private boolean useYAxis = false;
	
	public CollidableCircle(Point3D center, float radius, Planet planet) {
		this.setCenter(center);
		this.setRadius(radius);
		this.setPlanet(planet);
	}

	public Point3D getCenter() {
		return center;
	}

	public void setCenter(Point3D center) {
		this.center = center;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
		this.radiusSquared = radius * radius;
	}	

	public boolean isUseYAxis() {
		return useYAxis;
	}

	public void setUseYAxis(boolean useYAxis) {
		this.useYAxis = useYAxis;
	}

	@Override
	public boolean doesCollide(SWGObject obj) {
		Point3D objectPos = obj.getWorldPosition();
		if(useYAxis) {
			return (objectPos.x - center.x) * (objectPos.x - center.x) + (objectPos.z - center.z) * (objectPos.z - center.z) <= radiusSquared && objectPos.y <= center.y + 1 && objectPos.y >= center.y - 1;
		} else {
			return (objectPos.x - center.x) * (objectPos.x - center.x) + (objectPos.z - center.z) * (objectPos.z - center.z) <= radiusSquared;
		}
	}

	@Override
	public boolean doesCollide(Point3D position) {
		if(useYAxis) {
			return (position.x - center.x) * (position.x - center.x) + (position.z - center.z) * (position.z - center.z) <= radiusSquared && position.y <= center.y + 1 && position.y >= center.y - 1;
		} else {
			return (position.x - center.x) * (position.x - center.x) + (position.z - center.z) * (position.z - center.z) <= radiusSquared;
		}
	}

	@Override
	public Point3D getRandomPosition(Point3D origin, float minDistance, float maxDistance) {
		
		Random random = new Random();
		float distance = random.nextInt((int) (maxDistance - minDistance) + 1) + minDistance;
		float angle = (float) (random.nextInt(360) * (Math.PI / 180));
		
		return new Point3D((float) (origin.x + distance * Math.cos(angle)), 0, (float) (origin.z + distance * Math.sin(angle)));
		
	}

	@Override
	public Point3D getRandomPosition() {
		
		Random random = new Random();
		float distance = random.nextInt((int) radius);
		float angle = (float) (random.nextInt(360) * (Math.PI / 180));
		
		return new Point3D((float) (center.x + distance * Math.cos(angle)), 0, (float) (center.z + distance * Math.sin(angle)));
		
	}
}
