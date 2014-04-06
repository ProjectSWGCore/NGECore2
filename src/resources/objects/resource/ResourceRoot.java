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

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.PrimaryKey;

/** 
 * @author Charon 
 */

@Entity(version=0)
public class ResourceRoot {
	
//	Publish 15:

//    - Total: 61, Minimum pool: 15, Random pool: 24 (3 was removed), Fixed pool: 22.
//    - Possible spawns for rare non-Iron resources: 39.
//    - Indoor concentrations were fixed for creature harvesting.
	
	
//	1) Minimum Pool (15 resources)
//
//    1 Steel not including Hardened and Crystallized from JTL
//    1 Copper not including Conductive from JTL
//    0 Iron
//    1 Aluminium not including Perovskitic from JTL
//    1 Extrusive Ore
//    1 Intrusive Ore
//    0 Siliclastic Ore not including Fermionic from JTL
//    1 Carbonate Ore
//    1 Crystalline Gemstone
//    1 Armophous Gemstone
//    1 Known Radioactive not including High Grade from JTL
//    1 Known Solid Petrochemicals
//    1 Known Liquid Petrochemicals
//    2 Polymer
//    2 Lubricating Oil
//    0 Known Inert Gas
//    0 Known Reactive Gas not including Unstable from JTL Gas
//
//
//2) Random Pool (24 resources)
//
//    Any Mineral except Iron and JTL resources.
//    Any Gas except JTL Gas.
//    Any Chemical except Fiberplast
//    Extra Water (Bonus Water in addition to the one in the Native Pool)
//
//
//3) Fixed Pool (22 resources)
//
//    8 JTL Resources (Added just before Publish 11)
//    14 Iron
//
//
//4) Native Pool (420 resources)
//
//    One Fiberplast for each planet
//    One Water for each planet
//    One Wind Energy and One Solar Energy for each planet
//    All 38 Organic types for each planet
	
	@NotPersistent
	private Transaction txn;
	
	private static int highestID = 0;
	
	@PrimaryKey
	private int resourceRootID;
	
	private String resourceClass;
	private String resourceType;
	private byte generalType;
	private byte containerType;
	private String resourceFileName;
	private short[] resourceMaxCaps;
	private short[] resourceMinCaps;
	private Long minimalLifeTime;
	private Long maximalLifeTime;	
//	Non-JTL Inorganics: 6 to 11 days.
//	Organics: 6 to 22 days.
//	JTL resources: 13 to 22 days.
	
	public static byte CONTAINER_TYPE_INORGANIC_MINERALS          = 0;
	public static byte CONTAINER_TYPE_INORGANIC_CHEMICALS         = 1;
	public static byte CONTAINER_TYPE_INORGANIC_GAS               = 2;
	public static byte CONTAINER_TYPE_INORGANIC_PETROCHEM_LIQUID  = 3;
	public static byte CONTAINER_TYPE_INORGANIC_WATER             = 4;
	public static byte CONTAINER_TYPE_INORGANIC_BOTTLE_WATER      = 5;
	public static byte CONTAINER_TYPE_ORGANIC_BEAN                = 6;
	public static byte CONTAINER_TYPE_ORGANIC_BOTTLE_MILK         = 7;
	public static byte CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE       = 8;
	public static byte CONTAINER_TYPE_ORGANIC_CORN                = 9;
	public static byte CONTAINER_TYPE_ORGANIC_FOOD                = 10;
	public static byte CONTAINER_TYPE_ORGANIC_FRUIT               = 11;
	public static byte CONTAINER_TYPE_ORGANIC_FUNGI               = 12;
	public static byte CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES    = 13;
	public static byte CONTAINER_TYPE_ORGANIC_HIDE                = 14;
	public static byte CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE       = 15;
	public static byte CONTAINER_TYPE_ORGANIC_OATS                = 16;
	public static byte CONTAINER_TYPE_ORGANIC_RICE                = 17;
	public static byte CONTAINER_TYPE_ORGANIC_SCALEY_HIDE         = 18;
	public static byte CONTAINER_TYPE_ORGANIC_STRUCTURE           = 19;
	public static byte CONTAINER_TYPE_ORGANIC_TUBER               = 20;
	public static byte CONTAINER_TYPE_ORGANIC_WHEAT               = 21;
	public static byte CONTAINER_TYPE_ORGANIC_WOOLY_HIDE          = 22;
	public static byte CONTAINER_TYPE_ENERGY_GAS                  = 23;
	public static byte CONTAINER_TYPE_ENERGY_LIQUID               = 24;
	public static byte CONTAINER_TYPE_ENERGY_RADIOACTIVE          = 25;
	public static byte CONTAINER_TYPE_ENERGY_SOLID                = 26;
	
	public static String[] CONTAINER_TYPE_IFF_SIGNIFIER           = new String[]{
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
	
	
	public ResourceRoot(){
		this.resourceRootID = highestID++;
		resourceMinCaps = new short[11];
		resourceMaxCaps = new short[11];		
	}
	
	public String getResourceClass() {
		return resourceClass;
	}
	public void setResourceClass(String resourceClass) {
		this.resourceClass = resourceClass;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceFileName() {
		return resourceFileName;
	}
	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}
	public short[] getResourceMaxCaps() {
		return resourceMaxCaps;
	}
	public void setResourceMaxCaps(short[] resourceMaxCaps) {
		this.resourceMaxCaps = resourceMaxCaps;
	}
	public short[] getResourceMinCaps() {
		return resourceMinCaps;
	}
	public void setResourceMinCaps(short[] resourceMinCaps) {
		this.resourceMinCaps = resourceMinCaps;
	}
	public Long getMinimalLifeTime() {
		return minimalLifeTime;
	}
	public void setMinimalLifeTime(Long minimalLifeTime) {
		this.minimalLifeTime = minimalLifeTime;
	}
	public Long getMaximalLifeTime() {
		return maximalLifeTime;
	}
	public void setMaximalLifeTime(Long maximalLifeTime) {
		this.maximalLifeTime = maximalLifeTime;
	}
	
	public byte getGeneralType(){
		return generalType;
	}	
	
	public void setgeneralType(byte generalType){
		this.generalType = generalType;
	}

	public byte getContainerType() {
		return containerType;
	}

	public void setContainerType(byte containerType) {
		this.containerType = containerType;
	}

	public void setGeneralType(byte generalType) {
		this.generalType = generalType;
	}

	public int getResourceRootID() {
		return resourceRootID;
	}

	public void setResourceRootID(int resourceRootID) {
		this.resourceRootID = resourceRootID;
	}	
	
	public void createTransaction(Environment env) { txn = env.beginTransaction(null, null);}

	public Transaction getTransaction() { return txn; }

}
