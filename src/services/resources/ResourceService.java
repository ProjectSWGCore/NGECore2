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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import main.NGECore;
import resources.datatables.MilkState;
import resources.objects.creature.CreatureObject;
import resources.objects.harvester.HarvesterObject;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceClass;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.resource.ResourceRoot;
import services.ai.AIActor;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.database.ODBCursor;
import engine.resources.database.ObjectDatabase;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class ResourceService implements INetworkDispatch {
	
	private static final List<String> ACCEPTABLE_RESOURCE_PREFIXES = Arrays.asList(new String[] {
			"Corellian",	"Dantooine",	"Dathomirian",
			"Endorian",		"Lokian",		"Nabooian",
			"Rori",			"Talusian",		"Tatooinian",
			"Yavinian",		"Kashyyykian",	"Mustafarian", "Yavin IV" });
	
	private final NGECore core;
	private final List<ResourceRoot> jtlRoots;
	private final List<GalacticResource> spawnedResources;
	private final Map<Long, GalacticResource> resourceHistory;
	private final Map<Integer, ResourceRoot> resourceRootTable;
	private final Map<ResourceClass, List<ResourceRoot>> resourceTypeTable;
	
	public ResourceService(NGECore core) {
		this.core              = core;
		this.spawnedResources  = new Vector<GalacticResource>();
		this.jtlRoots          = new Vector<ResourceRoot>();
		this.resourceHistory   = new ConcurrentHashMap<Long, GalacticResource>();
		this.resourceRootTable = new ConcurrentHashMap<Integer, ResourceRoot>();
		this.resourceTypeTable = new ConcurrentHashMap<ResourceClass, List<ResourceRoot>>();
		initialize();
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
	
	public boolean canMilk(CreatureObject actor, CreatureObject target) {
		if (target.getPosture() == 14 || !actor.inRange(target.getWorldPosition(), 5) || actor.isInCombat() || target.isInCombat())
			return false;
		
		AIActor ai = (AIActor) target.getAttachment("AI");
		
		if (ai.getMobileTemplate().getMilkAmount() <= 0 || ai.getMobileTemplate().getMilkType() == null || ai.getMilkState() == MilkState.MILKED || ai.getMilkState() == MilkState.MILKINGINPROGRESS)
			return false;
		
		return true;
	}
	
	private GalacticResource createResource() {
		Planet planet = core.terrainService.getPlanetByID(1);
		Point3D position = new Point3D(0, 0, 0);
		Quaternion orientation = new Quaternion(1, 1, 1, 1);
		String template = "object/resource_container/base/shared_base_resource_container.iff";
		boolean isSnapshot = false;
		long objectID = core.objectService.generateObjectID();
		
		GalacticResource object = new GalacticResource(objectID, planet, position, orientation, template);
		object.setPlanetId(planet.getID());
		object.setAttachment("customServerTemplate", template);
		object.setisInSnapshot(isSnapshot);
		core.objectService.getObjectList().put(objectID, object);
		
		return object;
	}
	
	private void createResource(String fileName, String rClass, String rType, short [] minCapArray, short [] maxCapArray) {
		ResourceClass resourceClass = ResourceClass.forEndOfString(rClass);
		ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName(fileName);
		resourceRoot.setResourceClass(rClass);
		resourceRoot.setResourceType(rType);
		resourceRoot.setGeneralType((byte) resourceClass.getType());
		resourceRoot.setContainerType((byte) resourceClass.getType());
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);
		finalizeResource(resourceClass, resourceRoot);
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
		
		if (density >= 0.8f)
			resAmount *= 1.25f;
		else if (density >= 0.6f)
			resAmount *= 1.f;
		else if (density >= 0.4f)
			resAmount *= 0.75f;
		else
			resAmount *= 0.5f;
		
		giveResource(actor, res, resAmount);
		actor.sendSystemMessage("@skl_use:corpse_harvest_success", (byte) 0);
	}
	
	public void doMilk(CreatureObject actor, CreatureObject target) {
		AIActor ai = (AIActor) target.getAttachment("AI");
		ai.setMilkState(MilkState.MILKINGINPROGRESS);
		actor.sendSystemMessage("@skl_use:milk_begin", (byte) 0);
		
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
				jtlRoots.add(resourceRoot);
				break;
			default:
				break;
		}
	}
	
	public GalacticResource findResourceById(long id) {
		GalacticResource resource = new GalacticResource();
		for (GalacticResource sampleResource : spawnedResources)
			if (sampleResource.getId() == id)
				return sampleResource;
		return resource;
	}
	
	public List<GalacticResource> getAllSpawnedResources() {
		return spawnedResources;
	}
	
	public int getResourceSampleQuantity(CreatureObject crafter, GalacticResource sampleResource) {
		if (crafter.getContainer() != null) { // No indoor sampling
			System.out.println("Indoor sampling attempt");
			return 0;
		}
		core.skillModService.addSkillMod(crafter, "surveying", 100);
		int skillMod = (Math.round(crafter.getSkillMod("surveying").getBase()));
		int skillModAdapted = skillMod;
		if (skillModAdapted <= 35)
			skillModAdapted = (3 * skillMod) / 2;
		else if (skillModAdapted <= 20)
			skillModAdapted = 2 * skillMod;
		
		float concentration = sampleResource.deliverConcentrationForSurvey(crafter.getPlanetId(), crafter.getPosition().x, crafter.getPosition().z);
		float skillFactor = skillMod / 80.0F;
		float quantity = skillFactor * (concentration / 100) * new Random().nextInt(25);
		if (new Random().nextInt(100) <= (2 * skillModAdapted))
			quantity = 0.5F * quantity;
		else
			quantity = 0;
		if ((quantity > 0) && (quantity < 1.0f)) {
			quantity = 1.0f + quantity;
			quantity = Math.min(quantity, 20);
		}
		return (int) quantity;
	}
	
	private String [] getResourceTypeAndClass(String name) {
		String [] split = name.split(" ", 2);
		String rType = split.length >= 1 ? split[0].trim() : "";
		String rClass = split.length >= 2 ? split[1].trim() : "";
		if (!ACCEPTABLE_RESOURCE_PREFIXES.contains(rType)) {
			rType = "";
			rClass = name;
		}
		return new String[] { rType, rClass };
	}
	
	public List<GalacticResource> getSpawnedResourcesByPlanet(int planetId) {
		Vector<GalacticResource> planetResourceList = new Vector<GalacticResource>();
		for (GalacticResource gal : spawnedResources)
			if (gal.isSpawnedOn(planetId))
				planetResourceList.add(gal);
		return planetResourceList;
	}
	
	public Vector<GalacticResource> getSpawnedResourcesByPlanetAndHarvesterType(int planetId, byte harvesterType) {
		Vector<GalacticResource> planetResourceList = new Vector<GalacticResource>();
		byte searchtype = harvesterType;
		if (harvesterType == 7)
			searchtype = (byte) 0;
		for (GalacticResource gal : spawnedResources)
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
		return planetResourceList;
	}
	
	public List<GalacticResource> getSpawnedResourcesByPlanetAndType(int planetId, byte generalType) {
		Vector<GalacticResource> planetResourceList = new Vector<GalacticResource>();
		for (GalacticResource gal : spawnedResources)
			if (gal.isSpawnedOn(planetId) && gal.getGeneralType() == generalType)
				planetResourceList.add(gal);
		return planetResourceList;
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
		
		if (density >= 0.8f)
			milkAmount *= 1.25f;
		else if (density >= 0.6f)
			milkAmount *= 1.f;
		else if (density >= 0.4f)
			milkAmount *= 0.75f;
		else
			milkAmount *= 0.5f;
		
		giveResource(actor, res, milkAmount);
		ai.setMilkState(MilkState.MILKED);
		actor.sendSystemMessage("@skl_use:milk_success", (byte) 0);
	}
	
	public void giveResource(CreatureObject actor, GalacticResource res, int amount) {
		ResourceContainerObject foundContainer = core.surveyService.findResourceContainerInInventory(actor, res);
		
		if (foundContainer != null && amount < ResourceContainerObject.maximalStackCapacity - foundContainer.getStackCount())
			foundContainer.setStackCount(foundContainer.getStackCount() + amount, actor);
		else {
			SWGObject inventory = actor.getSlottedObject("inventory");
			String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[res.getResourceRoot().getContainerType()];
			ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, actor.getPlanet());
			containerObject.initializeStats(res);
			containerObject.setProprietor(actor);
			containerObject.setStackCount(amount, actor);
			inventory.add(containerObject);
		}
	}
	
	public GalacticResource grabMeatForCreature(CreatureObject corpse) {
		GalacticResource resource = null;
		int planetId = corpse.getPlanetId();
		String meatName = corpse.getStfName(); // placeholder
		List<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources) {
			Vector<Integer> spawnedPlanets = res.getAllSpawnedPlanetIds();
			if (spawnedPlanets.contains(planetId) && res.getResourceRoot().getResourceClass().equalsIgnoreCase(meatName))
				resource = res;
		}
		return resource;
	}
	
	public GalacticResource grabResourceByClass(String searchName, int planetId) {
		GalacticResource resource = null;
		List<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources) {
			List<Integer> spawnedPlanets = res.getAllSpawnedPlanetIds();
			if (spawnedPlanets.contains(planetId) && res.getResourceRoot().getResourceClass().equalsIgnoreCase(searchName))
				resource = res; // resourceClass= "Insect Meat" i.e.
		}
		return resource;
	}
	
	public GalacticResource grabResourceByFileName(String searchName) {
		GalacticResource resource = null;
		List<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources)
			if (res.getResourceRoot().getResourceFileName().equalsIgnoreCase(searchName))
				resource = res; // resourceFileName= "meat_insect_mustafar" i.e.
		return resource;
	}
	
	public GalacticResource grabResourceByName(String searchName) {
		GalacticResource resource = null;
		List<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources)
			if (res.getName().equals(searchName))
				resource = res;
		return resource;
	}
	
	private void initialize() {
		for (ResourceClass c : ResourceClass.values())
			resourceTypeTable.put(c, new Vector<ResourceRoot>());
		
		core.commandService.registerCommand("harvestcorpse");
		core.commandService.registerCommand("milkcreature");
		core.commandService.registerCommand("resourcecontainersplit");
		core.commandService.registerCommand("resourcecontainertransfer");
		core.commandService.registerCommand("factorycratesplit");
		start();
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	}
	
	private void loadNewResources() {
		System.out.println("Loading new resources...");
		int resourceSize = resourceRootTable.size();
		long start = System.nanoTime();
		String [] classes = new String[8];
		Map<String, Integer> minMap = new HashMap<String, Integer>();
		Map<String, Integer> maxMap = new HashMap<String, Integer>();
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/resource/resource_tree.iff", DatatableVisitor.class);
			for (int i = 0; i < visitor.getRowCount(); i++)
				processDatatableRow(visitor, classes, minMap, maxMap, i);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		double timeMs = (System.nanoTime() - start) / 1E6;
		resourceSize = resourceRootTable.size() - resourceSize;
		System.out.println(String.format("Loaded %d new resources in %.3fms.", resourceSize, timeMs));
	}
	
	private void loadOldResources() {
		System.out.println("Loading old resources...");
		long start = System.nanoTime();
		ObjectDatabase resourceHistoryODB = core.getResourceHistoryODB();
		ODBCursor cursor = resourceHistoryODB.getCursor();
		while (cursor.hasNext()) {
			GalacticResource resource = (GalacticResource) cursor.next();
			resourceHistory.put(resource.getObjectID(), resource);
		}
		double timeMs = (System.nanoTime() - start) / 1E6;
		int resourceCount = resourceHistory.size();
		System.out.println(String.format("Loaded %d old resources in %.3fms", resourceCount, timeMs));
	}
	
	public void loadResources() {
		loadOldResources();
		loadNewResources();
		spawnResources();
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
	
	private void processDatatableRow(DatatableVisitor visitor, String [] classes, Map<String, Integer> minMap, Map<String, Integer> maxMap, int i) {
		ResourceDatatableRow row = new ResourceDatatableRow(visitor, classes, minMap, maxMap, i);
		if (row.getMinPools() > 0) {
			String [] rTypeAndClass = getResourceTypeAndClass(row.getResourceName());
			if (!rTypeAndClass[1].isEmpty())
				createResource(row.getFilename(), rTypeAndClass[1], rTypeAndClass[0], row.getMinimums(), row.getMaximums());
		}
	}
	
	@Override
	public void shutdown() {
		
	}
	
	private void spawnResource(int pool, ResourceRoot root) {
		spawnResource(pool, root, -1);
	}
	
	private void spawnResource(int pool, ResourceRoot root, int planetId) {
		GalacticResource resource = createResource();
		if (root == null)
			return;
		try {
			resource.setResourceRoot(root);
			resource.setPoolNumber((byte) pool);
			if (planetId != -1)
				resource.setPlanetID(planetId);
			resource.initializeNewGalaxyResource();
			
			resourceHistory.put(resource.getObjectID(), resource);
			core.getResourceHistoryODB().put(resource.getObjectID(), resource);
			spawnedResources.add(resource);
		} catch (Exception e) {
			System.err.println("Error in spawning resource: " + root.getResourceFileName());
			if (planetId != -1)
				System.err.println("  On Planet: " + planetId);
			e.printStackTrace();
		}
		return;
	}
	
	private void spawnResources() {
		long start = System.nanoTime();
		System.out.println("Spawning resources...");
		
		// Always spawn
		spawnResource(1, pickRandomResource(ResourceClass.STEEL));
		spawnResource(1, pickRandomResource(ResourceClass.ALUMINUM));
		spawnResource(1, pickRandomResource(ResourceClass.COPPER));
		spawnResource(1, pickRandomResource(ResourceClass.IRON));
		spawnResource(1, pickRandomResource(ResourceClass.AMORPHOUS_GEMSTONE));
		spawnResource(1, pickRandomResource(ResourceClass.CRYSTALLINE_GEMSTONE));
		spawnResource(1, pickRandomResource(ResourceClass.ORE_INTRUSIVE));
		spawnResource(1, pickRandomResource(ResourceClass.ORE_CARBONATE));
		spawnResource(1, pickRandomResource(ResourceClass.ORE_EXTRUSIVE));
		spawnResource(1, pickRandomResource(ResourceClass.RADIOACTIVE));
		spawnResource(1, pickRandomResource(ResourceClass.PETROCHEMICAL_LIQUID));
		spawnResource(1, pickRandomResource(ResourceClass.PETROCHEMICAL_SOLID));
		spawnResource(1, pickRandomResource(ResourceClass.POLYMER));
		spawnResource(1, pickRandomResource(ResourceClass.POLYMER));
		spawnResource(1, pickRandomResource(ResourceClass.LUBRICATING_OIL));
		spawnResource(1, pickRandomResource(ResourceClass.LUBRICATING_OIL));
		
		// Picks 24 random resources to spawn
		for (int i = 0; i < 24; i++)
			spawnResource(1, pickRandomResource());
		
		// 8 out of 24 fixed resources are JTL resources
		if (jtlRoots.size() == 0)
			System.err.println("WARNING: JTL Resource size is 0!");
		else
			for (int i = 0; i < 8; i++)
				spawnResource(1, jtlRoots.get(new Random().nextInt(jtlRoots.size() - 1)));
		
		// 16 out of 24
		for (int i = 0; i < 16; i++)
			spawnResource(1, pickRandomResource(ResourceClass.IRON));
		double timeMs = (System.nanoTime() - start) / 1E6;
		System.out.println(String.format("Finished spawning resources. Took %.3fms.", timeMs));
	}
	
	// Utility method to quickly spawn resource containers into the inventory
	public ResourceContainerObject spawnSpecificResourceContainer(String spawnType, CreatureObject crafter, int stackCount) {
		ResourceContainerObject containerObject = null;
		for (GalacticResource sampleResource : spawnedResources)
			if (sampleResource.getResourceRoot().getResourceClass().contains(spawnType)) {
				String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];
				containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, crafter.getPlanet());
				containerObject.setProprietor(crafter);
				containerObject.setStackCount(stackCount, false);
//				int stackCount = getResourceSampleQuantity(crafter, sampleResource);
				containerObject.initializeStats(sampleResource);
				containerObject.setIffFileName(resourceContainerIFF);
				SWGObject crafterInventory = crafter.getSlottedObject("inventory");
				crafterInventory.add(containerObject);
				return containerObject;
			}
		return containerObject;
	}
	
	public void start() {
		
	}
	
	@SuppressWarnings("unused")
	private static class ResourceDatatableRow {
		private static final String [] attributeOrder = new String[] {
			"res_cold_resist",	"res_conductivity",		"res_decay_resist",
			"res_heat_resist",	"res_malleability",		"res_shock_resistance",
			"res_toughness",	"entangle_resistance",	"res_potential_energy", 
			"res_flavor",		"res_quality" };
		private String [] classes;
		private String containerType;
		private String filename;
		private int index;
		private short [] maximums;
		private int maxPools;
		private int maxTypes;
		private short [] minimums;
		private int minPools;
		private int minTypes;
		private String nameClass;
		private boolean permanent;
		private boolean recycled;
		
		public ResourceDatatableRow(DatatableVisitor visitor, String [] classes, Map<String, Integer> minMap, Map<String, Integer> maxMap, int index) {
			if (visitor.getObject(index, 0) == null)
				setAllToEmpty();
			else {
				this.index = (Integer) visitor.getObject(index, 0);
				this.filename = (String) visitor.getObject(index, 1);
				initializeClasses(visitor, classes, index);
				this.minTypes = (Integer) visitor.getObject(index, 11);
				this.maxTypes = (Integer) visitor.getObject(index, 10);
				this.minPools = (Integer) visitor.getObject(index, 12);
				this.maxPools = (Integer) visitor.getObject(index, 13);
				this.recycled = visitor.getObject(index, 14).equals("true");
				this.permanent = visitor.getObject(index, 15).equals("true");
				initializeAttributes(visitor, minMap, maxMap, index);
			}
		}
		
		private short [] generateMaximums(Map<String, Integer> maxMap) {
			short [] maximums = new short[11];
			for (int i = 0; i < attributeOrder.length; i++)
				maximums[i] = maxMap.containsKey(attributeOrder[i]) ? maxMap.get(attributeOrder[i]).shortValue() : 0;
				return maximums;
		}
		
		private short [] generateMinimums(Map<String, Integer> minMap) {
			short [] minimums = new short[11];
			for (int i = 0; i < attributeOrder.length; i++)
				minimums[i] = minMap.containsKey(attributeOrder[i]) ? minMap.get(attributeOrder[i]).shortValue() : 0;
				return minimums;
		}
		
		public String [] getClasses() {
			return classes;
		}
		
		public String getContainerType() {
			return containerType;
		}
		
		public String getFilename() {
			return filename;
		}
		
		public int getIndex() {
			return index;
		}
		
		public short [] getMaximums() {
			return maximums;
		}
		
		public int getMaxPools() {
			return maxPools;
		}
		
		public int getMaxTypes() {
			return maxTypes;
		}
		
		public short [] getMinimums() {
			return minimums;
		}
		
		public int getMinPools() {
			return minPools;
		}
		
		public int getMinTypes() {
			return minTypes;
		}
		
		public String getNameClass() {
			return nameClass;
		}
		
		public String getResourceName() {
			for (int i = classes.length - 1; i >= 0; i--)
				if (!classes[i].isEmpty())
					return classes[i];
			return "";
		}
		
		private void initializeAttributes(DatatableVisitor visitor, Map<String, Integer> minMap, Map<String, Integer> maxMap, int index) {
			final int DESC_INDEX = 16;
			final int MIN_MAX_INDEX = 27;
			for (int j = DESC_INDEX; j < DESC_INDEX + 11 && j < visitor.getColumnCount(); j++)
				if (visitor.getObject(index, j) instanceof String) {
					String str = (String) visitor.getObject(index, j);
					if (!str.isEmpty()) {
						minMap.put(str, (Integer) visitor.getObject(index, (j - DESC_INDEX) * 2 + MIN_MAX_INDEX));
						maxMap.put(str, (Integer) visitor.getObject(index, (j - DESC_INDEX) * 2 + MIN_MAX_INDEX + 1));
					}
				}
			minimums = generateMinimums(minMap);
			maximums = generateMaximums(maxMap);
		}
		
		private void initializeClasses(DatatableVisitor visitor, String [] classes, int index) {
			int end = 2;
			for (int j = 2; j < 10; j++)
				if (!((String) visitor.getObject(index, j)).isEmpty()) {
					classes[j - 2] = (String) visitor.getObject(index, j);
					end = j;
				}
			for (int j = 0; j <= end && j < 8; j++)
				if (classes[j] == null)
					classes[j] = "";
			for (int j = end - 1; j < 8; j++)
				classes[j] = "";
			this.classes = new String[classes.length];
			System.arraycopy(classes, 0, this.classes, 0, classes.length);
		}
		
		public boolean isPermanent() {
			return permanent;
		}
		
		public boolean isRecycled() {
			return recycled;
		}
		
		private void setAllToEmpty() {
			index = 0;
			filename = "";
			classes = new String[8];
			for (int i = 0; i < classes.length; i++)
				classes[i] = "";
			minTypes = 0;
			maxTypes = 0;
			minPools = 0;
			maxPools = 0;
			recycled = false;
			permanent = false;
			minimums = new short[11];
			maximums = new short[11];
			containerType = "";
			nameClass = "";
		}
		
	}
}
