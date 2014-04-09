package services.housing;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import engine.resources.scene.Point3D;

public class HouseTemplate
{
	private String deedTemplate;
	private String buildingTemplate;
	private int lotCost;
	private Vector<String> placeablePlanets;
	private Map<String, Point3D> buildingSigns;
	
	public HouseTemplate(String deedTemplate, String buildingTemplate, int lotCost)
	{
		this.deedTemplate = deedTemplate;
		this.buildingTemplate = buildingTemplate;
		this.lotCost = lotCost;
		this.placeablePlanets = new Vector<String>();
		this.buildingSigns = new HashMap<String, Point3D>();
	}
	
	public void addBuildingSign(String signTemplate, Point3D signPosition)
	{
		buildingSigns.put(signTemplate, signPosition);
	}
	public void addPlaceablePlanet(String planetName)
	{
		this.placeablePlanets.add(planetName);
	}
	
	public String getDeedTemplate()
	{
		return this.deedTemplate;
	}
	public String getBuildingTemplate()
	{
		return this.buildingTemplate;
	}
	public Map<String, Point3D> getBuildingSigns()
	{
		return this.buildingSigns;
	}
	public Vector<String> getPlaceablePlanets()
	{
		return this.placeablePlanets;
	}
	public int getLotCost()
	{
		return this.lotCost;
	}
}
