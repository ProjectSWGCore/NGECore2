package services.playercities;

import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;

public class ClientRegion {
	
	private String name;
	private Point3D center;
	private float radius;
	private Planet planet;
	
	public ClientRegion(String name, Point3D center, float radius, Planet planet) {
		this.name = name;
		this.center = center;
		this.radius = radius;
		this.planet = planet;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Point3D getCenter() {
		return center;
	}

	public void setCenter(Point3D center) {
		this.center = center;
	}

	public Planet getPlanet() {
		return planet;
	}

	public void setPlanet(Planet planet) {
		this.planet = planet;
	}


}
