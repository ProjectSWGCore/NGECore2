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

public class CollidableBox extends AbstractCollidable {
	
	private Point3D center;
	private float width;
	private float height;
	private float widthHalf;
	private float heightHalf;
	
	public CollidableBox(Point3D center, float width, float height, Planet planet) {
		this.setCenter(center);
		this.setWidth(width);
		this.setHeight(height);
		this.setPlanet(planet);		
	}
	
	public Point3D getCenter() {
		return center;
	}

	public void setCenter(Point3D center) {
		this.center = center;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		this.widthHalf=.5F*width;
	}	
	
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		this.heightHalf=.5F*height;
	}
	
	@Override
	public boolean doesCollide(SWGObject obj) {
		Point3D objectPos = obj.getWorldPosition();		
		return (objectPos.x>center.x-widthHalf) &&
		(objectPos.x<center.x+widthHalf) &&
		(objectPos.z>center.z-heightHalf) &&
		(objectPos.z<center.z+heightHalf);
	}

	@Override
	public boolean doesCollide(Point3D position) {		
		return (position.x>center.x-widthHalf) &&
		(position.x<center.x+widthHalf) &&
		(position.z>center.z-heightHalf) &&
		(position.z<center.z+heightHalf);
	}

	@Override
	public Point3D getRandomPosition(Point3D origin, float minDistance,
			float maxDistance) {
		Random random = new Random();
		float coordX;
		float coordZ;
		float randomX = random.nextInt((int) (maxDistance - minDistance) + 1) + minDistance;
		float randomZ = random.nextInt((int) (maxDistance - minDistance) + 1) + minDistance;
		if (randomX>widthHalf)
			randomX = widthHalf - 5;
		if (randomZ>heightHalf)
			randomZ = heightHalf - 5;
		if (new Random().nextFloat()<.5)
			coordX = center.x + randomX;
		else
			coordX = center.x - randomX;
		if (new Random().nextFloat()<.5)
			coordZ = center.z + randomZ;
		else
			coordZ = center.z - randomZ;
		
		return new Point3D((float) coordX, 0, (float) coordZ);
	}

	@Override
	public Point3D getRandomPosition() {
		Random random = new Random();
		float coordX;
		float coordZ;
		float randomX = random.nextInt((int) widthHalf);
		float randomZ = random.nextInt((int) heightHalf);
		if (new Random().nextFloat()<.5)
			coordX = center.x + randomX;
		else
			coordX = center.x - randomX;
		if (new Random().nextFloat()<.5)
			coordZ = center.z + randomZ;
		else
			coordZ = center.z - randomZ;
		
		return new Point3D((float) coordX, 0, (float) coordZ);
	}
	
	

}
