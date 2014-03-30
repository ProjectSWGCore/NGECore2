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

import java.util.Random;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleMessageBuilder;
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
	private String resourceClass;
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
	
	@NotPersistent
	private ResourceContainerMessageBuilder messageBuilder;
	
	public ResourceContainerObject(){
		
	}
	
	// Overload to initialize with stack count
//	public ResourceContainerObject(int stackCount){
//		this.stackCount = stackCount;
//		long range = 1234567L;
//		Random r = new Random();
//		this.containerID = (long)(r.nextDouble()*range);
//		messageBuilder = new ResourceContainerMessageBuilder(this);
//	}
	
	// Overload for objectservice call
	public ResourceContainerObject(long objectID, Planet planet, String template, Point3D position, Quaternion orientation){
		super(objectID, planet, template, position, orientation);
		messageBuilder = new ResourceContainerMessageBuilder(this);
		}
		
	// Overload to initialize with stack count
	// and resource reference
//	public ResourceContainerObject(int stackCount, GalacticResource resource){
//		this.stackCount = stackCount;
//		long range = 1234567L;
//		Random r = new Random();
//		this.containerID = (long)(r.nextDouble()*range);
//		this.setResourceName(resource.getName());
//		this.setResourceClass(resource.getResourceRoot().getResourceClass());
//		this.setColdResistance(resource.getResourceStats()[0]);
//		this.setConductivity(resource.getResourceStats()[1]);
//		this.setDecayResistance(resource.getResourceStats()[2]);
//		this.setHeatResistance(resource.getResourceStats()[3]);
//		this.setMalleability(resource.getResourceStats()[4]);
//		this.setShockResistance(resource.getResourceStats()[5]);
//		this.setUnitToughness(resource.getResourceStats()[6]);
//		this.setEntangleResistance(resource.getResourceStats()[7]);
//		this.setPotentialEnergy(resource.getResourceStats()[8]);
//		this.setOverallQuality(resource.getResourceStats()[9]);
//		this.setFlavor(resource.getResourceStats()[10]);
//		this.setResourceType(resource.getResourceRoot().getResourceType());
//		this.setIffFileName(resource.getResourceRoot().getResourceFileName());
//		messageBuilder = new ResourceContainerMessageBuilder(this);
//	}
//	
//	public ResourceContainerObject(int stackCount, GalacticResource resource, String template,long objectID, Planet planet){
//		super(objectID, planet, template);
//		this.stackCount = stackCount;
//		long range = 1234567L;
//		Random r = new Random();
//		this.containerID = (long)(r.nextDouble()*range);
//		this.setResourceName(resource.getName());
//		this.setResourceClass(resource.getResourceRoot().getResourceClass());
//		this.setColdResistance(resource.getResourceStats()[0]);
//		this.setConductivity(resource.getResourceStats()[1]);
//		this.setDecayResistance(resource.getResourceStats()[2]);
//		this.setHeatResistance(resource.getResourceStats()[3]);
//		this.setMalleability(resource.getResourceStats()[4]);
//		this.setShockResistance(resource.getResourceStats()[5]);
//		this.setUnitToughness(resource.getResourceStats()[6]);
//		this.setEntangleResistance(resource.getResourceStats()[7]);
//		this.setPotentialEnergy(resource.getResourceStats()[8]);
//		this.setOverallQuality(resource.getResourceStats()[9]);
//		this.setFlavor(resource.getResourceStats()[10]);
//		this.setResourceType(resource.getResourceRoot().getResourceType());
//		this.setIffFileName(resource.getResourceRoot().getResourceFileName());
//		messageBuilder = new ResourceContainerMessageBuilder(this);
//		}
	
	public void initializeStats(GalacticResource resource){
		this.setResourceName(resource.getName());
		this.setResourceClass(resource.getResourceClass());
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
	}
	
//	public void initializeStats(GalacticResource resource){
//		this.setResourceName(resource.getName());
//		this.setResourceClass(resource.getResourceRoot().getResourceClass());
//		this.setColdResistance(resource.getResourceStats()[0]);
//		this.setConductivity(resource.getResourceStats()[1]);
//		this.setDecayResistance(resource.getResourceStats()[2]);
//		this.setHeatResistance(resource.getResourceStats()[3]);
//		this.setMalleability(resource.getResourceStats()[4]);
//		this.setShockResistance(resource.getResourceStats()[5]);
//		this.setUnitToughness(resource.getResourceStats()[6]);
//		this.setEntangleResistance(resource.getResourceStats()[7]);
//		this.setPotentialEnergy(resource.getResourceStats()[8]);
//		this.setOverallQuality(resource.getResourceStats()[9]);
//		this.setFlavor(resource.getResourceStats()[10]);
//		this.setResourceType(resource.getResourceRoot().getResourceType());
//		this.setGeneralType(resource.getResourceRoot().getGeneralType());
//		this.setReferenceID(resource.getId());
//		this.setResourceFileName(resource.getResourceRoot().getResourceFileName());
//	}
	

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
