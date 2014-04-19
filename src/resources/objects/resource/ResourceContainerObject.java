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
package resources.objects.resource;


import main.NGECore;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;

/** 
 * @author Charon 
 */

@Persistent(version=0)
public class ResourceContainerObject extends TangibleObject {
	
	// Unique ID
	private long containerID;
	
	private CreatureObject proprietor;
	private String resourceType;
	private String resourceClass="";
	private String resourceName;
	private byte generalType;
	private long referenceID;
	private String iffFileName;
	private String resourceFileName;
	private int stackCount=0;
	private byte containerType;
	
	// Since this can represent a resource that already despawned
	// it needs to hold all resource stats:
	private short coldResistance;
	private short conductivity;
	private short decayResistance;
	private short heatResistance;
	private short malleability;
	private short shockResistance;
	private short unitToughness;
	private short entangleResistance;
	private short potentialEnergy;
	private short overallQuality;
	private short flavor;
	
	/*
	private static byte CONTAINER_TYPE_INORGANIC_MINERALS          = 0;
	private static byte CONTAINER_TYPE_INORGANIC_CHEMICALS         = 1;
	private static byte CONTAINER_TYPE_INORGANIC_GAS               = 2;
	private static byte CONTAINER_TYPE_INORGANIC_PETROCHEM_LIQUID  = 3;
	private static byte CONTAINER_TYPE_INORGANIC_WATER             = 4;
	private static byte CONTAINER_TYPE_INORGANIC_BOTTLE_WATER      = 5;
	private static byte CONTAINER_TYPE_ORGANIC_BEAN                = 6;
	private static byte CONTAINER_TYPE_ORGANIC_BOTTLE_MILK         = 7;
	private static byte CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE       = 8;
	private static byte CONTAINER_TYPE_ORGANIC_CORN                = 9;
	private static byte CONTAINER_TYPE_ORGANIC_FOOD                = 10;
	private static byte CONTAINER_TYPE_ORGANIC_FRUIT               = 11;
	private static byte CONTAINER_TYPE_ORGANIC_FUNGI               = 12;
	private static byte CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES    = 13;
	private static byte CONTAINER_TYPE_ORGANIC_HIDE                = 14;
	private static byte CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE       = 15;
	private static byte CONTAINER_TYPE_ORGANIC_OATS                = 16;
	private static byte CONTAINER_TYPE_ORGANIC_RICE                = 17;
	private static byte CONTAINER_TYPE_ORGANIC_SCALEY_HIDE         = 18;
	private static byte CONTAINER_TYPE_ORGANIC_STRUCTURE           = 19;
	private static byte CONTAINER_TYPE_ORGANIC_TUBER               = 20;
	private static byte CONTAINER_TYPE_ORGANIC_WHEAT               = 21;
	private static byte CONTAINER_TYPE_ORGANIC_WOOLY_HIDE          = 22;
	private static byte CONTAINER_TYPE_ENERGY_GAS                  = 23;
	private static byte CONTAINER_TYPE_ENERGY_LIQUID               = 24;
	private static byte CONTAINER_TYPE_ENERGY_RADIOACTIVE          = 25;
	private static byte CONTAINER_TYPE_ENERGY_SOLID                = 26;
	
	private static String[] CONTAINER_TYPE_IFF_SIGNIFIER           = new String[]{
		"object/resource_container/shared_resource_container_inorganic_minerals.iff",
		"object/resource_container/shared_resource_container_inorganic_chemicals.iff",
		"object/resource_container/shared_resource_container_inorganic_gas.iff",
		"object/resource_container/shared_resource_container_inorganic_petrochem_liquid.iff",
		"object/resource_container/shared_resource_container_inorganic_water.iff",
		"object/resource_container/shared_resource_container_inorganic_bottle_water.iff",
		"object/resource_container/shared_resource_container_organic_bean.iff",
		"object/resource_container/shared_resource_container_organic_bottle_milk.iff",
		"object/resource_container/shared_resource_container_organic_bristley_hide.iff",
		"object/resource_container/shared_resource_container_organic_corn.iff",
		"object/resource_container/shared_resource_container_organic_food.iff",
		"object/resource_container/shared_resource_container_organic_fruit.iff",
		"object/resource_container/shared_resource_container_organic_fungi.iff",
		"object/resource_container/shared_resource_container_organic_green_vegetables.iff",
		"object/resource_container/shared_resource_container_organic_hide.iff",
		"object/resource_container/shared_resource_container_organic_leathery_hide.iff",
		"object/resource_container/shared_resource_container_organic_oats.iff",
		"object/resource_container/shared_resource_container_organic_rice.iff",
		"object/resource_container/shared_resource_container_organic_scaley_hide.iff",
		"object/resource_container/shared_resource_container_organic_structure.iff",
		"object/resource_container/shared_resource_container_organic_tuber.iff",
		"object/resource_container/shared_resource_container_organic_wheat.iff",
		"object/resource_container/shared_resource_container_organic_wooly_hide.iff",
		"object/resource_container/shared_resource_container_energy_gas.iff",
		"object/resource_container/shared_resource_container_energy_liquid.iff",
		"object/resource_container/shared_resource_container_energy_radioactive.iff",
		"object/resource_container/shared_resource_container_energy_solid.iff"		
	};
	*/
	
	@NotPersistent
	public static int maximalStackCapacity = 100000;
	
	@NotPersistent
	private ResourceContainerMessageBuilder messageBuilder;
	
	public ResourceContainerObject(){
		
	}
	
	public ResourceContainerObject(long objectID, Planet planet, String template, Point3D position, Quaternion orientation){
		super(objectID, planet, template, position, orientation);
		messageBuilder = new ResourceContainerMessageBuilder(this);
		this.setAttachment("radial_filename", "resourceContainer");
		}
			
	public void initializeStats(GalacticResource resource){
		this.setResourceName(resource.getName());
		this.setResourceClass(resource.getCategory());
		this.setColdResistance(resource.getResourceStats()[0]);
		this.setConductivity(resource.getResourceStats()[1]);
		this.setDecayResistance(resource.getResourceStats()[2]);
		this.setHeatResistance(resource.getResourceStats()[3]);
		this.setMalleability(resource.getResourceStats()[4]);
		this.setShockResistance(resource.getResourceStats()[5]);
		this.setUnitToughness(resource.getResourceStats()[6]);
		this.setEntangleResistance(resource.getResourceStats()[7]);
		this.setPotentialEnergy(resource.getResourceStats()[8]);
		this.setOverallQuality(resource.getResourceStats()[9]);
		this.setFlavor(resource.getResourceStats()[10]);
		this.setResourceType(resource.getResourceType());
		this.setGeneralType(resource.getGeneralType());
		this.setReferenceID(resource.getId());
		this.setResourceFileName(resource.getIffFileName());
		//this.setIffFileName(resource.getIffFileName());
		
		// set attributes
		this.getAttributes().put("@obj_attr_n:condition", "100/100");
		this.getAttributes().put("@obj_attr_n:volume", "1");
		this.getAttributes().put("@obj_attr_n:resource_contents", this.getStackCount()+"/"+maximalStackCapacity);
		this.getAttributes().put("@obj_attr_n:resource_name", this.getResourceName());
		this.getAttributes().put("@obj_attr_n:resource_class", "@resource/resource_names:" + this.getResourceFileName());
					
		if (this.getColdResistance()>0){
			this.getAttributes().put("res_cold_resist", ""+this.getColdResistance());
			resource.getAttributes().put("res_cold_resist", ""+this.getColdResistance());
		}
		
		if (this.getConductivity()>0){
			this.getAttributes().put("res_conductivity", ""+this.getConductivity());
			resource.getAttributes().put("res_conductivity", ""+this.getConductivity());
		}
		
		if (this.getDecayResistance()>0){
			this.getAttributes().put("res_decay_resist", ""+this.getDecayResistance());
			resource.getAttributes().put("res_decay_resist", ""+this.getDecayResistance());
		}
		
		if (this.getHeatResistance()>0){
			this.getAttributes().put("res_heat_resist", ""+this.getHeatResistance());
			resource.getAttributes().put("res_heat_resist", ""+this.getHeatResistance());
		}		
		
		if (this.getMalleability()>0){
			this.getAttributes().put("res_malleability", ""+this.getMalleability());
			resource.getAttributes().put("res_malleability", ""+this.getMalleability());
		}
		
		if (this.getOverallQuality()>0){
			this.getAttributes().put("res_quality", ""+this.getOverallQuality());
			resource.getAttributes().put("res_quality", ""+this.getOverallQuality());

		}
		
		if (this.getShockResistance()>0){
			this.getAttributes().put("res_shock_resistance", ""+this.getShockResistance());
			resource.getAttributes().put("res_shock_resistance", ""+this.getShockResistance());
		}
		
		if (this.getUnitToughness()>0){	
			this.getAttributes().put("res_toughness", ""+this.getUnitToughness());
			resource.getAttributes().put("res_toughness", ""+this.getUnitToughness());
		}
		
		if (this.getFlavor()>0){
			this.getAttributes().put("res_flavor", ""+this.getFlavor());
			resource.getAttributes().put("res_flavor", ""+this.getFlavor());
		}
		
		if (this.getEntangleResistance()>0){
			this.getAttributes().put("entangle_resistance", ""+this.getEntangleResistance());
			resource.getAttributes().put("entangle_resistance", ""+this.getEntangleResistance());
		}
		
		if (this.getPotentialEnergy()>0){
			this.getAttributes().put("res_potential_energy", ""+this.getPotentialEnergy());
			resource.getAttributes().put("res_potential_energy", ""+this.getPotentialEnergy());
		}	
	}
	
	public void cloneStats(ResourceContainerObject original){
		this.setResourceName(original.getResourceName());
		this.setResourceClass(original.getResourceClass());
		this.setColdResistance(original.getColdResistance());
		this.setConductivity(original.getConductivity());
		this.setDecayResistance(original.getDecayResistance());
		this.setHeatResistance(original.getHeatResistance());
		this.setMalleability(original.getMalleability());
		this.setShockResistance(original.getShockResistance());
		this.setUnitToughness(original.getUnitToughness());
		this.setEntangleResistance(original.getEntangleResistance());
		this.setPotentialEnergy(original.getPotentialEnergy());
		this.setOverallQuality(original.getOverallQuality());
		this.setFlavor(original.getFlavor());
		this.setResourceType(original.getResourceType());
		this.setGeneralType(original.getGeneralType());
		this.setReferenceID(original.getReferenceID());
		this.setResourceFileName(original.getResourceFileName());
	
		// set attributes
		this.getAttributes().put("@obj_attr_n:condition", "100/100");
		this.getAttributes().put("@obj_attr_n:volume", "1");
		this.getAttributes().put("@obj_attr_n:resource_contents", this.getStackCount()+"/"+maximalStackCapacity);
		this.getAttributes().put("@obj_attr_n:resource_name", this.getResourceName());
		this.getAttributes().put("@obj_attr_n:resource_class", "@resource/resource_names:" + this.getResourceFileName());
					
		if (this.getColdResistance()>0){
			this.getAttributes().put("res_cold_resist", ""+this.getColdResistance());
		}
		
		if (this.getConductivity()>0){
			this.getAttributes().put("res_conductivity", ""+this.getConductivity());
		}
		
		if (this.getDecayResistance()>0){
			this.getAttributes().put("res_decay_resist", ""+this.getDecayResistance());
		}
		
		if (this.getHeatResistance()>0){
			this.getAttributes().put("res_heat_resist", ""+this.getHeatResistance());
		}		
		
		if (this.getMalleability()>0){
			this.getAttributes().put("res_malleability", ""+this.getMalleability());
		}
		
		if (this.getOverallQuality()>0){
			this.getAttributes().put("res_quality", ""+this.getOverallQuality());
		}
		
		if (this.getShockResistance()>0){
			this.getAttributes().put("res_shock_resistance", ""+this.getShockResistance());
		}
		
		if (this.getUnitToughness()>0){	
			this.getAttributes().put("res_toughness", ""+this.getUnitToughness());
		}
		
		if (this.getFlavor()>0){
			this.getAttributes().put("res_flavor", ""+this.getFlavor());
		}
		
		if (this.getEntangleResistance()>0){
			this.getAttributes().put("entangle_resistance", ""+this.getEntangleResistance());
		}
		
		if (this.getPotentialEnergy()>0){
			this.getAttributes().put("res_potential_energy", ""+this.getPotentialEnergy());
		}	
		
	}
	
	public long getContainerID() {
		return containerID;
	}

	public void setContainerID(long containerID) {
		this.containerID = containerID;
	}

	public CreatureObject getProprietor() {
		return proprietor;
	}

	public void setProprietor(CreatureObject proprietor) {
		this.proprietor = proprietor;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public int getStackCount() {
		return stackCount;
	}

	public void setStackCount(int stackCount,boolean isNewContainer) {
		this.stackCount += stackCount;
		if (isNewContainer)
			this.stackCount = stackCount;
	}
	
	public void setStackCount(int stackCount,CreatureObject owner) {
		this.stackCount = stackCount;
		this.getAttributes().put("@obj_attr_n:resource_contents", this.getStackCount()+"/"+maximalStackCapacity);
		if (stackCount>0)
			this.sendDelta3(owner.getClient());
	}

	public short getColdResistance() {
		return coldResistance;
	}

	public void setColdResistance(short coldResistance) {
		this.coldResistance = coldResistance;
	}

	public short getConductivity() {
		return conductivity;
	}

	public void setConductivity(short conductivity) {
		this.conductivity = conductivity;
	}

	public short getDecayResistance() {
		return decayResistance;
	}

	public void setDecayResistance(short decayResistance) {
		this.decayResistance = decayResistance;
	}

	public short getHeatResistance() {
		return heatResistance;
	}

	public void setHeatResistance(short heatResistance) {
		this.heatResistance = heatResistance;
	}

	public short getMalleability() {
		return malleability;
	}

	public void setMalleability(short malleability) {
		this.malleability = malleability;
	}

	public short getShockResistance() {
		return shockResistance;
	}

	public void setShockResistance(short shockResistance) {
		this.shockResistance = shockResistance;
	}

	public short getUnitToughness() {
		return unitToughness;
	}

	public void setUnitToughness(short unitToughness) {
		this.unitToughness = unitToughness;
	}

	public short getEntangleResistance() {
		return entangleResistance;
	}

	public void setEntangleResistance(short entangleResistance) {
		this.entangleResistance = entangleResistance;
	}

	public short getPotentialEnergy() {
		return potentialEnergy;
	}

	public void setPotentialEnergy(short potentialEnergy) {
		this.potentialEnergy = potentialEnergy;
	}

	public short getOverallQuality() {
		return overallQuality;
	}

	public void setOverallQuality(short overallQuality) {
		this.overallQuality = overallQuality;
	}

	public short getFlavor() {
		return flavor;
	}

	public void setFlavor(short flavor) {
		this.flavor = flavor;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getIffFileName() {
		return iffFileName;
	}

	public void setIffFileName(String iffFileName) {
		this.iffFileName = iffFileName;
	}

	public String getResourceClass() {
		return resourceClass;
	}

	public void setResourceClass(String resourceClass) {
		this.resourceClass = resourceClass;
	}

	public long getReferenceID() {
		return referenceID;
	}

	public void setReferenceID(long referenceID) {
		this.referenceID = referenceID;
	}

	public String getResourceFileName() {
		return resourceFileName;
	}

	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}

	public byte getGeneralType() {
		return generalType;
	}

	public void setGeneralType(byte generalType) {
		this.generalType = generalType;
	}

	public void setStackCount(int stackCount) {
		this.stackCount = stackCount;
		this.getAttributes().put("@obj_attr_n:resource_contents", this.getStackCount()+"/"+maximalStackCapacity);
		this.sendDelta3(this.getProprietor().getClient());
	}

	public byte getContainerType() {
		return containerType;
	}

	public void setContainerType(byte containerType) {
		this.containerType = containerType;
	}
	
	public void buildAttributeListMessage(Client destination){
		destination.getSession().write(messageBuilder.serialized_buildAttributeListMessage());
	}
	
	public void splitContainer(CreatureObject owner,ResourceContainerObject originalObject,String commandString){
		String[] splitArray = commandString.split(" ");
		if (splitArray.length<3)
			return;
		int cloneStackQuantity= Integer.parseInt(splitArray[0]);
		int parentcontainerID= Integer.parseInt(splitArray[1]);
		SWGObject objectParentContainer = (SWGObject) NGECore.getInstance().objectService.getObject(parentcontainerID);			
		if (objectParentContainer==null || originalObject==null)
			return;
		CreatureObject proprietor = originalObject.getProprietor();
		ResourceContainerObject containerObject = (ResourceContainerObject) NGECore.getInstance().objectService.createObject(originalObject.getTemplate(), proprietor.getPlanet());
		containerObject.cloneStats(originalObject);
		containerObject.setProprietor(originalObject.getProprietor());
		containerObject.setStackCount(cloneStackQuantity);
		originalObject.setStackCount(originalObject.getStackCount()-cloneStackQuantity);
		objectParentContainer.add(containerObject);
	}
	
	public void transferContainer(CreatureObject owner,ResourceContainerObject disposableObject,String commandString){
		String[] splitArray = commandString.split(" ");
		if (splitArray.length<2)
			return;
		int parentcontainerID= Integer.parseInt(splitArray[0]);
		int cloneStackQuantity= Integer.parseInt(splitArray[1]);		
		ResourceContainerObject resourceContainer = (ResourceContainerObject) NGECore.getInstance().objectService.getObject(parentcontainerID);			
		if (resourceContainer==null)
			return;
		resourceContainer.setStackCount(resourceContainer.getStackCount()+cloneStackQuantity);
		SWGObject objectParentContainer = (SWGObject) NGECore.getInstance().objectService.getObject(resourceContainer.getParentId());			
		objectParentContainer.remove(disposableObject);
	}
	
	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null)
			return;
		
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
		destination.getSession().write(messageBuilder.buildBaseline8());
		destination.getSession().write(messageBuilder.buildBaseline9());
	}
	
	public void sendDelta3(Client destination) {
		
		if(destination == null || destination.getSession() == null)
			return;
		
		destination.getSession().write(messageBuilder.buildDelta3());
	}
}
