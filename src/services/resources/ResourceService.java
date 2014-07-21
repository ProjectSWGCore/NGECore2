/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on
 * NGEngine. Therefore all terms and conditions of the GNU Lesser General Public
 * License cover the combination.
 ******************************************************************************/
package services.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.NGECore;
import engine.resources.database.ODBCursor;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.datatables.MilkState;
import resources.objects.creature.CreatureObject;
import resources.objects.harvester.HarvesterObject;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.resource.ResourceRoot;
import services.ai.AIActor;

public class ResourceService implements INetworkDispatch {
	
	private NGECore core;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private boolean enableResourceHistory = true; // Set this to false, to
	// prevent persistence of resources into history Db
	
	private Hashtable<Integer, ResourceRoot> resourceRootTable = new Hashtable<Integer, ResourceRoot>(); // synchronized
	private Map<ResourceClass, List<ResourceRoot>> resourceTypeTable = new ConcurrentHashMap<ResourceClass, List<ResourceRoot>>();
	private Map<Integer, List<GalacticResource>> poolResources = new ConcurrentHashMap<Integer, List<GalacticResource>>();
	private Vector<ResourceRoot> planetaryPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> corelliaPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> dantooinePool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> dathomirPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> endorPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> kashyyykPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> lokPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> mustafarPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> nabooPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> roriPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> talusPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> tattoinePool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> yavinPool4Roots = new Vector<ResourceRoot>();
	// For new future planet's resources add:
	// private Vector<ResourceRoot> NEWPLANETPool4Roots = new
	// Vector<ResourceRoot>();
	private Vector<ResourceRoot> JTLRoots = new Vector<ResourceRoot>();
	
	private Vector<GalacticResource> allSpawnedResources = new Vector<GalacticResource>(); // needs
	// persistance
	private Vector<String> completeResourceNameHistory = new Vector<String>(); // needs
	// persistance
	
	int totalSpawnedResourcesNumber = 0;
	boolean bigBangOccured = false;
	
	short [] minCapArray = new short[] { 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300 };
	short [] maxCapArray = new short[] { 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800 };
	short [] minUnCappedArray = new short[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	short [] maxUnCappedArray = new short[] { 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000 };
	
	public ResourceService(NGECore core) {
		this.core = core;
		for (ResourceClass c : ResourceClass.values()) {
			resourceTypeTable.put(c, new Vector<ResourceRoot>());
		}
		for (int i = 1; i <= 4; i++)
			poolResources.put(i, new Vector<GalacticResource>());
		
		core.commandService.registerCommand("harvestcorpse");
		core.commandService.registerCommand("milkcreature");
		core.commandService.registerCommand("resourcecontainersplit");
		core.commandService.registerCommand("resourcecontainertransfer");
		core.commandService.registerCommand("factorycratesplit");
		start();
	}
	
	// "Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
	
	// loads the resource roots at server start
	public void loadResourceRoots() {
		
	}
	
	// loads the currently spawned resources at server start
	public void loadResources() {
		loadFromTextFile();
	}
	
	public SWGObject createResource() {
		SWGObject object = null;
		Planet planet = core.terrainService.getPlanetByID(1);
		Point3D position = new Point3D(0, 0, 0);
		Quaternion orientation = new Quaternion(1, 1, 1, 1);
		String Template = "object/resource_container/base/shared_base_resource_container.iff";
		boolean isSnapshot = false;
		
		long objectID = core.objectService.generateObjectID();
		
		object = new GalacticResource(objectID, planet, position, orientation, Template);
		
		object.setPlanetId(planet.getID());
		
		object.setAttachment("customServerTemplate", Template);
		
		object.setisInSnapshot(isSnapshot);
		
		core.objectService.getObjectList().put(objectID, object);
		
		return object;
	}
	
	private static enum ResourceClass {
		IRON("Iron"),
		ALUMINUM("Aluminum"),
		STEEL("Steel"),
		COPPER("Copper"),
		ORE_EXTRUSIVE("Extrusive Ore"),
		ORE_INTRUSIVE("Intrusive Ore"),
		ORE_CARBONITE("Carbonite Ore"),
		ORE_CARBONATE("Carbonate Ore"),
		ORE_SILICLASTIC("Siliclastic Ore"),
		CRYSTALLINE_GEMSTONE("Crystalline Gemstone"),
		AMORPHOUS_GEMSTONE("Amorphous Gemstone"),
		RADIOACTIVE("Radioactive"),
		PETROCHEMICAL_SOLID("Petrochemical Solid"),
		PETROCHEMICAL_LIQUID("Petrochemical Liquid"),
		POLYMER("Polymer"),
		LUBRICATING_OIL("Lubricating Oil"),
		REACTIVE_GAS("Reactive Gas"),
		FERROUS_METAL("Ferrous Metal"),
		NON_FERROUS("Non-Ferrous"),
		SEDIMENTARY_ORE("Sedimentary Ore"),
		IGNEOUS_ORE("Igneous Ore"),
		GEMSTONE("Gemstone"),
		PETRO_FUEL("Petro Fuel"),
		OIL("Oil"),
		INERT_GAS("Inert Gas"),
		WATER_VAPOR("Water Vapor"),
		WIND_RENEWABLE_ENERGY("Wind Renewable Energy"),
		SOLAR_RENEWABLE_ENERGY("Solar Renewable Energy"),
		TIDAL_RENEWABLE_ENERGY("Tidal Renewable Energy"),
		HYDRON3_RENEWABLE_ENERGY("Hydron-3 Renewable Energy"),
		GEOTHERMAL_RENEWABLE_ENERGY("Geothermal Renewable Energy"),
		CONIFER_WOOD("Conifer Wood"),
		EVERGREEN_WOOD("Evergreen Wood"),
		DECIDUOUS_WOOD("Deciduous Wood"),
		FRUIT("Fruit"),
		FLOWER_FRUIT("Flower Fruit"),
		DOMESTICATED_CORN("Domesticated Corn"),
		DOMESTICATED_OATS("Domesticated Oats"),
		DOMESTICATED_RICE("Domesticated Rice"),
		DOMESTICATED_WHEAT("Domesticated Wheat"),
		WILD_CORN("Wild Corn"),
		WILD_OATS("Wild Oats"),
		WILD_RICE("Wild Rice"),
		WILD_WHEAT("Wild Wheat"),
		VEGETABLE_BEANS("Vegetable Beans"),
		BERRY_FRUIT("Berry Fruit"),
		VEGETABLE_FUNGUS("Vegetable Fungus"),
		VEGETABLE_GREENS("Vegetable Greens"),
		VEGETABLE_TUBERS("Vegetable Tubers"),
		ANIMAL_BONES("Animal Bones"),
		AVIAN_BONES("Avian Bones"),
		HORN("Horn"),
		BRISTLEY_HIDE("Bristley Hide"),
		LEATHERY_HIDE("Leathery Hide"),
		SCALEY_HIDE("Scaley Hide"),
		WOOLY_HIDE("Wooly Hide"),
		AVIAN_MEAT("Avian Meat"),
		CARNIVORE_MEAT("Carnivore Meat"),
		DOMESTICATED_MEAT("Domesticated Meat"),
		HERBIVORE_MEAT("Herbivore Meat"),
		INSECT_MEAT("Insect Meat"),
		WILD_MEAT("Wild Meat"),
		DOMESTICATED_MILK("Domesticated Milk"),
		WILD_MILK("Wild Milk"),
		CRUSTACEAN_MEAT("Crustacean Meat"),
		FISH_MEAT("Fish Meat"),
		MOLLUSK_MEAT("Mollusk Meat"),
		REPTILIAN_MEAT("Reptilian Meat"),
		EGG("Egg"),
		FIBERPLAST("Fiberplast"),
		EGG_MEAT("Egg Meat"),
		VEGETABLE_FUNGI("Vegetable Fungi"),
		VEGETABLE_FRUITS("Vegetable Fruits"),
		VEGETABLE_BERRIES("Vegetable Berries"),
		LIQUID_PETRO_FUEL("Liquid Petro Fuel"),
		SOLID_PETRO_FUEL("Solid Petro Fuel"),
		TYPE_1_CRYSTAL_AMORPHOUS_GEM("Type 1 Crystal Amorphous Gem"),
		TYPE_2_CRYSTAL_AMORPHOUS_GEM("Type 2 Crystal Amorphous Gem"),
		TYPE_1_CRYSTALLINE_GEM("Type 1 Crystalline Gem"),
		TYPE_2_CRYSTALLINE_GEM("Type 2 Crystalline Gem");
		
		private static final Map<String, ResourceClass> CLASS_MAP = new ConcurrentHashMap<String, ResourceClass>();
		private String name;
		
		static {
			for (ResourceClass c : values())
				CLASS_MAP.put(c.getName(), c);
		}
		
		ResourceClass(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public static final ResourceClass forString(String str) {
			return CLASS_MAP.get(str);
		}
	}
	
	public void addResource(ResourceRoot resource) {
		System.out.print("Created Resource: " + resource.getResourceFileName());
		System.out.print("  Class: " + resource.getResourceClass());
		System.out.println("  Type: " + resource.getResourceType());
		finalizeResource(ResourceClass.forString(resource.getResourceClass()), resource);
		spawnResource(1, resource);
	}
	
	public void createResource(String fileName, String rClass, String type, byte generalType, byte containerType, short [] minCapArray, short [] maxCapArray, long minLife, long maxLife) {
		ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName(fileName);
		resourceRoot.setResourceClass(rClass);
		resourceRoot.setResourceType(type);
		resourceRoot.setGeneralType(generalType);
		resourceRoot.setContainerType(containerType);
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(minLife);
		resourceRoot.setMaximalLifeTime(maxLife);
		finalizeResource(ResourceClass.forString(rClass), resourceRoot);
		spawnResource(1, resourceRoot);
	}
	
	private void loadFromTextFile() {
		System.out.println("Loading resources...");
		final String path = "scripts/resources/";
		final String filename = "generate_resources";
		final String method = "generate";
		long start = System.nanoTime();
		core.scriptService.callScript(path, filename, method, core);
		double timeMs = (System.nanoTime()-start)/1E6;
		System.out.println(String.format("Finished loading resources. Took %.3fms. Loaded %d resources.", timeMs, resourceRootTable.size()));
	}
	
	private void finalizeResource(ResourceClass rClass, ResourceRoot resourceRoot) {
		resourceRootTable.put(resourceRootTable.size(), resourceRoot);
		resourceTypeTable.get(rClass).add(resourceRoot);
		switch (resourceRoot.getResourceFileName()) {
			case "aluminum_perovskitic":
			case "copper_borcarbantium":
			case "steel_bicorbantium":
			case "steel_arveshian":
			case "radioactive_polymetric":
			case "gas_reactive_organometallic":
			case "ore_siliclastic_fermionic":
				JTLRoots.add(resourceRoot);
				break;
			default:
				break;
		}
	}
	
	// TODO: Go through all of this again, Vegetable tubers are double 632 berry
	// 635 double 645 and 647 double 671,672
	// 683,84,85,86 all same
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	}
	
	@Override
	public void shutdown() {
		
	}
	
	public Vector<GalacticResource> getAllSpawnedResources() {
		return allSpawnedResources;
	}
	
	public void addSpawnedResource(GalacticResource loadedResource) {
		allSpawnedResources.add(loadedResource);
	}
	
	public List<GalacticResource> getSpawnedResourcesByPlanet(int planetId) {
		Vector<GalacticResource> planetResourceList = new Vector<GalacticResource>();
		for (GalacticResource gal : allSpawnedResources) {
			if (gal.isSpawnedOn(planetId))
				planetResourceList.add(gal);
		}
		return planetResourceList;
	}
	
	public List<GalacticResource> getSpawnedResourcesByPlanetAndType(int planetId, byte generalType) {
		Vector<GalacticResource> planetResourceList = new Vector<GalacticResource>();
		for (GalacticResource gal : allSpawnedResources) {
			if (gal.isSpawnedOn(planetId) && gal.getGeneralType() == generalType)
				planetResourceList.add(gal);
		}
		return planetResourceList;
	}
	
	public int getResourceSampleQuantity(CreatureObject crafter, GalacticResource sampleResource) {
		if (crafter.getContainer() != null) { // No indoor sampling
			System.out.println("Indoor sampling attempt");
			return 0;
		}
		core.skillModService.addSkillMod(crafter, "surveying", 100);
		int skillMod = (int) (Math.round(crafter.getSkillMod("surveying").getBase()));
		int skillModAdapted = skillMod;
		if (skillModAdapted <= 35)
			skillModAdapted = (3 * skillMod) / 2;
		else if (skillModAdapted <= 20)
			skillModAdapted = 2 * skillMod;
		
		float concentration = sampleResource.deliverConcentrationForSurvey(crafter.getPlanetId(), crafter.getPosition().x, crafter.getPosition().z);
		float skillFactor = skillMod / 80.0F;
		float quantity = skillFactor * (concentration / 100) * new Random().nextInt(25);
		if (new Random().nextInt(100) <= (2 * skillModAdapted)) {
			quantity = 0.5F * quantity;
		} else {
			quantity = 0;
		}
		if ((quantity > 0) && (quantity < 1.0f)) {
			quantity = 1.0f + quantity;
			quantity = Math.min(quantity, 20);
		}
		// System.out.println("(int)amount" + (int)quantity);
		return (int) quantity;
	}
	
	public void kickOffBigBang() {
		bigBangSpawn();
		String [] PlanetTempArr = new String[] { ", ", "Tattoine", "Naboo", "Corellia", "Rori", "Lok", "Dantooine", "Talus", "Yavin4", "Endor", "Dathomir", "Mustafar", "Kashyyyk" };
		for (GalacticResource res : allSpawnedResources) {
			System.out.println("res.getName() : " + res.getName());
			System.out.println("Resource Class : " + res.getResourceRoot().getResourceType() + " " + res.getResourceRoot().getResourceClass());
			Vector<Integer> pln = res.getAllSpawnedPlanetIds();
			System.out.println("Spawned on :");
			for (Integer n : pln) {
				System.out.print(PlanetTempArr[n] + ", ");
			}
			System.out.println(", ");
		}
	}
	
	public void start() {
		
	}
	
	// Never call this except once upon first server resource datatable creation
	// !!!
	private boolean bigBangSpawn() {
		
		if (bigBangOccured)
			return false;
		bigBangOccured = true;
		
		System.out.println("Persisting resources...");
		// Database table ResourceRootData
		
		// ID
		// 1 String Name
		// 2 int ID
		// 3 String Class
		// 4 String General Name
		// 5 RootID
		// 6 MaxSpawnTime
		// 7 MinSpawnTime
		// CAPS
		// 8 Short Cold_Resistance_Cap_Min
		// 9 Short Cold_Resistance_Cap_Max
		// 10 Short Conductivity_Cap_Min
		// 11 Short Conductivity_Cap_Max
		// 12 Short Decay_Res_Cap_Min
		// 13 Short Decay_Res_Cap_Max
		// 14 Short Heat_Res_Cap_Min
		// 15 Short Heat_Res_Cap_Max
		// 16 Short Malleability_Cap_Min
		// 17 Short Malleability_Cap_Max
		// 18 Short Shock_Res_Cap_Min
		// 19 Short Shock_Res_Cap_Max
		// 20 Short Unit_Toughness_Cap_Min
		// 21 Short Unit_Toughness_Cap_Max
		// 22 Short Entangle_Res_Cap_Min
		// 23 Short Entangle_Res_Cap_Max
		// 24 Short Potential_Energy_Cap_Min
		// 25 Short Potential_Energy_Cap_Max
		// 26 Short Flavor_Cap_Min
		// 27 Short Flavor_Cap_Max
		// 28 Short Overall_Quality_Cap_Min
		// 29 Short Overall_Quality_Cap_Max
		
		// Publish 15: Basis for ProjectSWG
		
		// - Total: 61, Minimum pool: 15, Random pool: 24 (3 was removed), Fixed
		// pool:
		// 22.
		// - Possible spawns for rare non-Iron resources: 39.
		// - Indoor concentrations were fixed for creature harvesting.
		
		// There are some resource classes that always spawn:
		
		// Steel
		spawnResource(1, pickRandomResource(ResourceClass.STEEL));
		// Aluminum
		spawnResource(1, pickRandomResource(ResourceClass.ALUMINUM));
		// Copper
		spawnResource(1, pickRandomResource(ResourceClass.COPPER));
		// Iron
		spawnResource(1, pickRandomResource(ResourceClass.IRON));
		// Amorphous Gemstone
		spawnResource(1, pickRandomResource(ResourceClass.AMORPHOUS_GEMSTONE));
		// Crystalline Gemstone
		spawnResource(1, pickRandomResource(ResourceClass.CRYSTALLINE_GEMSTONE));
		// Intrusive Ore
		spawnResource(1, pickRandomResource(ResourceClass.ORE_INTRUSIVE));
		// Carbonate Ore
		spawnResource(1, pickRandomResource(ResourceClass.ORE_CARBONATE));
		// Extrusive Ore
		spawnResource(1, pickRandomResource(ResourceClass.ORE_EXTRUSIVE));
		// Radioactive
		spawnResource(1, pickRandomResource(ResourceClass.RADIOACTIVE));
		// Liquid Petrochemical
		spawnResource(1, pickRandomResource(ResourceClass.PETROCHEMICAL_LIQUID));
		// Solid Petrochemical
		spawnResource(1, pickRandomResource(ResourceClass.PETROCHEMICAL_SOLID));
		// Polymer
		spawnResource(1, pickRandomResource(ResourceClass.POLYMER));
		spawnResource(1, pickRandomResource(ResourceClass.POLYMER));
		// Lubricating oil
		spawnResource(1, pickRandomResource(ResourceClass.LUBRICATING_OIL));
		spawnResource(1, pickRandomResource(ResourceClass.LUBRICATING_OIL));
		
		// Random pool: 24
		for (int i = 0; i < 24; i++) {
			spawnResource(2, pickRandomResource());
		}
		
		// Fixed pool: 22 -> 8 are JTL resources
		for (int i = 0; i < 8; i++) {
			ResourceRoot root = JTLRoots.get((int) new Random().nextInt(JTLRoots.size() - 1));
			spawnResource(3, root);
		}
		
		// and 16 are irons
		for (int i = 0; i < 16; i++) {
			spawnResource(3, pickRandomResource(ResourceClass.IRON));
		}

		for (ResourceRoot rootElement : tattoinePool4Roots) {
			spawnResource(4, rootElement, 1);
		}
		for (ResourceRoot rootElement : nabooPool4Roots) {
			spawnResource(4, rootElement, 2);
		}
		for (ResourceRoot rootElement : corelliaPool4Roots) {
			spawnResource(4, rootElement, 3);
		}
		for (ResourceRoot rootElement : roriPool4Roots) {
			spawnResource(4, rootElement, 4);
		}
		for (ResourceRoot rootElement : lokPool4Roots) {
			spawnResource(4, rootElement, 5);
		}
		for (ResourceRoot rootElement : dantooinePool4Roots) {
			spawnResource(4, rootElement, 6);
		}
		for (ResourceRoot rootElement : talusPool4Roots) {
			spawnResource(4, rootElement, 7);
		}
		for (ResourceRoot rootElement : yavinPool4Roots) {
			spawnResource(4, rootElement, 8);
		}
		for (ResourceRoot rootElement : endorPool4Roots) {
			spawnResource(4, rootElement, 9);
		}
		for (ResourceRoot rootElement : dathomirPool4Roots) {
			spawnResource(4, rootElement, 10);
		}
		for (ResourceRoot rootElement : mustafarPool4Roots) {
			spawnResource(4, rootElement, 11);
		}
		for (ResourceRoot rootElement : kashyyykPool4Roots) {
			spawnResource(4, rootElement, 12);
		}
		// for (ResourceRoot rootElement : NEWPLANETPool4Roots){
		// spawnResource(4, rootElement,NEWPLANET_ID);
		// }
		
		System.out.println(allSpawnedResources.size() + " RESOURCES SPAWNED.");
		System.out.println("Finished persisting resources.");
		return true;
	}
	
	private ResourceRoot pickRandomResource() {
		if (resourceRootTable.size() == 0)
			return null;
		return resourceRootTable.get((int) (Math.random() * resourceRootTable.size()));
	}
	
	private ResourceRoot pickRandomResource(ResourceClass c) {
		List<ResourceRoot> roots = resourceTypeTable.get(c);
		if (roots.size() == 0)
			return null;
		return roots.get((int) (Math.random() * roots.size()));
	}
	
	private void spawnResource(int pool, ResourceRoot root) {
		spawnResource(pool, root, -1);
	}
	
	private void spawnResource(int pool, ResourceRoot root, int planetId) {
		GalacticResource resource = (GalacticResource) createResource();
		if (root == null)
			return;
		try {
			resource.setResourceRoot(root);
			resource.setPoolNumber((byte) pool);
			if (planetId != -1)
				resource.setPlanetID(planetId);
			resource.initializeNewGalaxyResource();
			
//			core.getResourcesODB().put(resource.getObjectID(), resource);
//			
//			if (enableResourceHistory) {
//				GalacticResource historicResource = resource.convertToHistoricResource();
//				core.getResourceHistoryODB().put(historicResource.getObjectID(), historicResource);
//			}
			completeResourceNameHistory.add(resource.getName());
			addSpawnedResource(resource);
			totalSpawnedResourcesNumber++;
		} catch (Exception e) {
			System.err.println("Error in spawning resource: " + root.getResourceFileName());
			if (planetId != -1)
				System.err.println("  On Planet: " + planetId);
			e.printStackTrace();
		}
		return;
	}
	
	public GalacticResource grabResourceByName(String searchName) {
		GalacticResource resource = null;
		Vector<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources) {
			if (res.getName().equals(searchName)) {
				resource = res;
			}
		}
		return resource;
	}
	
	public GalacticResource grabResourceByFileName(String searchName) {
		GalacticResource resource = null;
		Vector<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources) {
			if (res.getResourceRoot().getResourceFileName().equals(searchName)) {
				resource = res; // resourceFileName= "meat_insect_mustafar" i.e.
			}
		}
		return resource;
	}
	
	public GalacticResource grabResourceByClass(String searchName, int planetId) {
		GalacticResource resource = null;
		Vector<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources) {
			Vector<Integer> spawnedPlanets = res.getAllSpawnedPlanetIds();
			if (spawnedPlanets.contains(planetId) && res.getResourceRoot().getResourceClass().equals(searchName)) {
				resource = res; // resourceClass= "Insect Meat" i.e.
			}
		}
		return resource;
	}
	
	public Vector<GalacticResource> getSpawnedResourcesByPlanetAndHarvesterType(int planetId, byte harvesterType) {
		Vector<GalacticResource> planetResourceList = new Vector<GalacticResource>();
		byte searchtype = harvesterType;
		if (harvesterType == 7)
			searchtype = (byte) 0;
		for (GalacticResource gal : allSpawnedResources) {
			if (gal.isSpawnedOn(planetId) && gal.getGeneralType() == searchtype)
				if (harvesterType != HarvesterObject.HARVESTER_TYPE_FUSION)
					planetResourceList.add(gal);
				else if (harvesterType == HarvesterObject.HARVESTER_TYPE_FUSION) {
					System.err.println("gal.getContainerType() " + gal.getContainerType());
					if (gal.getResourceRoot().getContainerType() == ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE)
						planetResourceList.add(gal);
				} else if (harvesterType == HarvesterObject.HARVESTER_TYPE_GEO) {
					System.err.println("gal.getContainerType() " + gal.getContainerType());
					if (gal.getGeneralType() == GalacticResource.GENERAL_GEOTHERM)
						planetResourceList.add(gal);
				}
		}
		return planetResourceList;
	}
	
	public GalacticResource findResourceById(long id) {
		GalacticResource resource = new GalacticResource();
		for (GalacticResource sampleResource : allSpawnedResources) {
			if (sampleResource.getId() == id)
				return sampleResource;
		}
		return resource;
	}
	
	// ToDo: Improve
	public GalacticResource grabMeatForCreature(CreatureObject corpse) {
		GalacticResource resource = null;
		int planetId = corpse.getPlanetId();
		String meatName = corpse.getStfName(); // placeholder
		Vector<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources) {
			Vector<Integer> spawnedPlanets = res.getAllSpawnedPlanetIds();
			if (spawnedPlanets.contains(planetId) && res.getResourceRoot().getResourceClass().equals(meatName)) {
				resource = res;
			}
		}
		return resource;
	}
	
	// Utility method to quickly spawn resource containers into the inventory
	public ResourceContainerObject spawnSpecificResourceContainer(String spawnType, CreatureObject crafter, int stackCount) {
		ResourceContainerObject containerObject = null;
		for (GalacticResource sampleResource : allSpawnedResources) {
			if (sampleResource.getResourceRoot().getResourceClass().contains(spawnType)) {
				String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];
				containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, crafter.getPlanet());
				containerObject.setProprietor(crafter);
				containerObject.setStackCount(stackCount, false);
				// int stackCount = getResourceSampleQuantity(crafter,
				// sampleResource);
				containerObject.initializeStats(sampleResource);
				containerObject.setIffFileName(resourceContainerIFF);
				SWGObject crafterInventory = crafter.getSlottedObject("inventory");
				crafterInventory.add(containerObject);
				return containerObject;
			}
		}
		return containerObject;
	}
	
	public void giveResource(CreatureObject actor, GalacticResource res, int amount) {
		
		ResourceContainerObject foundContainer = core.surveyService.findResourceContainerInInventory(actor, res);
		
		if (foundContainer != null && amount < ResourceContainerObject.maximalStackCapacity - foundContainer.getStackCount()) {
			foundContainer.setStackCount(foundContainer.getStackCount() + amount, actor);
		} else {
			SWGObject inventory = actor.getSlottedObject("inventory");
			String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[res.getResourceRoot().getContainerType()];
			ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, actor.getPlanet());
			containerObject.initializeStats(res);
			containerObject.setProprietor(actor);
			containerObject.setStackCount(amount, actor);
			inventory.add(containerObject);
		}
		
	}
	
	public boolean canMilk(CreatureObject actor, CreatureObject target) {
		
		if (target.getPosture() == 14 || !actor.inRange(target.getWorldPosition(), 5) || actor.isInCombat() || target.isInCombat())
			return false;
		
		AIActor ai = (AIActor) target.getAttachment("AI");
		
		if (ai.getMobileTemplate().getMilkAmount() <= 0 || ai.getMobileTemplate().getMilkType() == null || ai.getMilkState() == MilkState.MILKED || ai.getMilkState() == MilkState.MILKINGINPROGRESS)
			return false;
		
		return true;
		
	}
	
	public boolean canHarvest(CreatureObject actor, CreatureObject target) {
		
		if (target.getPosture() != 14)
			return false;
		
		AIActor ai = (AIActor) target.getAttachment("AI");
		
		if (ai.getMobileTemplate().getMeatAmount() <= 0 && ai.getMobileTemplate().getBoneAmount() <= 0 && ai.getMobileTemplate().getHideAmount() <= 0)
			return false;
		
		if (target.getKiller() != actor && !(actor.getGroupId() == target.getGroupId() && actor.getGroupId() != 0))
			return false;
		
		return true;
		
	}
	
	public void doHarvest(CreatureObject actor, CreatureObject target, String type) {
		
		AIActor ai = (AIActor) target.getAttachment("AI");
		
		if (!actor.inRange(target.getWorldPosition(), 7)) {
			actor.sendSystemMessage("You are too far away to harvest the creature.", (byte) 0);
			return;
		}
		
		if (ai.hasBeenHarvested()) {
			actor.sendSystemMessage("@skl_use:nothing_to_harvest", (byte) 0);
			return;
		}
		
		ai.setHasBeenHarvested(true);
		
		String resType = "";
		int resAmount = 0;
		
		switch (type) {
		
			case "hide":
				resType = ai.getMobileTemplate().getHideType();
				resAmount = ai.getMobileTemplate().getHideAmount();
				break;
			case "meat":
				resType = ai.getMobileTemplate().getMeatType();
				resAmount = ai.getMobileTemplate().getMeatAmount();
				break;
			case "bone":
				resType = ai.getMobileTemplate().getBoneType();
				resAmount = ai.getMobileTemplate().getBoneAmount();
				break;
		
		}
		
		resAmount += (resAmount * (actor.getSkillModBase("creature_harvesting") / 100));
		GalacticResource res = grabResourceByClass(resType, target.getPlanetId());
		
		if (res == null) {
			actor.sendSystemMessage("@skl_use:nothing_to_harvest", (byte) 0);
			ai.setHasBeenHarvested(false);
			return;
		}
		
		float density = res.deliverConcentrationForSurvey(target.getPlanetId(), target.getWorldPosition().x, target.getWorldPosition().z);
		
		if (density >= 0.8f) {
			resAmount *= 1.25f;
		} else if (density >= 0.6f) {
			resAmount *= 1.f;
		} else if (density >= 0.4f) {
			resAmount *= 0.75f;
		} else {
			resAmount *= 0.5f;
		}
		
		giveResource(actor, res, resAmount);
		actor.sendSystemMessage("@skl_use:corpse_harvest_success", (byte) 0);
		
	}
	
	public void doMilk(CreatureObject actor, CreatureObject target) {
		
		AIActor ai = (AIActor) target.getAttachment("AI");
		ai.setMilkState(MilkState.MILKINGINPROGRESS);
		actor.sendSystemMessage("@skl_use:milk_begin", (byte) 0);
		
		scheduler.schedule(() -> {
			try {
				if (actor.getInventoryItemCount() >= 80) {
					actor.sendSystemMessage("@skl_use:milk_inventory_full", (byte) 0);
					ai.setMilkState(MilkState.NOTYETMILKED);
					return;
				}
				
				if (target.getPosture() == 14) {
					actor.sendSystemMessage("@skl_use:milk_cant_milk_the_dead", (byte) 0);
					ai.setMilkState(MilkState.NOTYETMILKED);
					return;
				}
				
				if (!actor.inRange(target.getWorldPosition(), 5)) {
					actor.sendSystemMessage("@skl_use:milk_too_far", (byte) 0);
					ai.setMilkState(MilkState.NOTYETMILKED);
					return;
				}
				
				if (actor.isInCombat()) {
					actor.sendSystemMessage("@skl_use:milk_combat", (byte) 0);
					ai.setMilkState(MilkState.NOTYETMILKED);
					return;
				}
				
				if (new Random().nextFloat() <= 0.05f) {
					actor.sendSystemMessage("@skl_use:milk_not_hidden", (byte) 0);
					ai.setMilkState(MilkState.NOTYETMILKED);
					ai.addDefender(actor);
					return;
				}
				
				giveMilk(actor, target);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 10000, TimeUnit.MILLISECONDS);
		
	}
	
	public void giveMilk(CreatureObject actor, CreatureObject target) {
		AIActor ai = (AIActor) target.getAttachment("AI");
		String milkType = ai.getMobileTemplate().getMilkType();
		int milkAmount = ai.getMobileTemplate().getMilkAmount();
		
		GalacticResource res = grabResourceByClass(milkType, target.getPlanetId());
		
		if (res == null) {
			actor.sendSystemMessage("Can't find milk resource.", (byte) 0);
			ai.setMilkState(MilkState.NOTYETMILKED);
			return;
		}
		
		float density = res.deliverConcentrationForSurvey(target.getPlanetId(), target.getWorldPosition().x, target.getWorldPosition().z);
		
		if (density >= 0.8f) {
			milkAmount *= 1.25f;
		} else if (density >= 0.6f) {
			milkAmount *= 1.f;
		} else if (density >= 0.4f) {
			milkAmount *= 0.75f;
		} else {
			milkAmount *= 0.5f;
		}
		
		giveResource(actor, res, milkAmount);
		ai.setMilkState(MilkState.MILKED);
		actor.sendSystemMessage("@skl_use:milk_success", (byte) 0);
	}
}
