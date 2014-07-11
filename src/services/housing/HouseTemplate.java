package services.housing;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import engine.resources.scene.Point3D;

public class HouseTemplate {
	
	private String deedTemplate;
	private String buildingTemplate;
	private int lotCost;
	private int defaultItemLimit;
	private int baseMaintenanceRate;
	private Vector<String> placeablePlanets;
	private Map<String, Point3D> buildingSigns;
	private boolean isCivicStructure;
	private int civicStructureType;
	private int destructionFee = 0;
	
	public HouseTemplate(String deedTemplate, String buildingTemplate, int lotCost) {
		this.deedTemplate = deedTemplate;
		this.buildingTemplate = buildingTemplate;
		this.lotCost = lotCost;
		this.defaultItemLimit = 50;
		this.baseMaintenanceRate = 8;
		this.placeablePlanets = new Vector<String>();
		this.buildingSigns = new HashMap<String, Point3D>();
	}
	
	public void addBuildingSign(String signTemplate, Point3D signPosition) {
		buildingSigns.put(signTemplate, signPosition);
	}
	
	public void addPlaceablePlanet(String planetName) {
		this.placeablePlanets.add(planetName);
	}
	
	public void setDefaultItemLimit(int itemLimit) {
		this.defaultItemLimit = itemLimit;
	}
	
	public String getDeedTemplate() {
		return deedTemplate;
	}
	
	public String getBuildingTemplate() {
		return buildingTemplate;
	}
	
	public Map<String, Point3D> getBuildingSigns() {
		return buildingSigns;
	}
	
	public Vector<String> getPlaceablePlanets() {
		return placeablePlanets;
	}
	
	public boolean canBePlacedOn(String planetName) {
		if (placeablePlanets.contains(planetName)) {
			return true;
		}
		
		return false;
	}
	
	public int getLotCost() {
		return lotCost;
	}
	
	public int getDefaultItemLimit() {
		return defaultItemLimit;
	}
	
	public int getBaseMaintenanceRate() {
		return baseMaintenanceRate;
	}
	
	public void setBaseMaintenanceRate(int baseMaintenanceRate) {
		this.baseMaintenanceRate = baseMaintenanceRate;
	}

	public boolean isCivicStructure() {
		return isCivicStructure;
	}

	public void setCivicStructure(boolean isCivicStructure) {
		this.isCivicStructure = isCivicStructure;
	}

	public int getCivicStructureType() {
		return civicStructureType;
	}

	public void setCivicStructureType(int civicStructureType) {
		this.civicStructureType = civicStructureType;
	}

	public int getDestructionFee() {
		return destructionFee;
	}

	public void setDestructionFee(int destructionFee) {
		this.destructionFee = destructionFee;
	}
	
}
