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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class ResourceService implements INetworkDispatch {
	
	private final NGECore core;
	
	private final List<ResourceRoot> jtlRoots;
	private final List<String> resourceNameHistory;
	private final Map<Integer, ResourceRoot> resourceRootTable;
	private final Map<ResourceClass, List<ResourceRoot>> resourceTypeTable;
	private final List<GalacticResource> spawnedResources;
	
	public ResourceService(NGECore core) {
		this.core = core;
		this.spawnedResources = new Vector<GalacticResource>();
		this.resourceNameHistory = new Vector<String>();
		this.jtlRoots = new Vector<ResourceRoot>();
		this.resourceRootTable = new ConcurrentHashMap<Integer, ResourceRoot>();
		this.resourceTypeTable = new ConcurrentHashMap<ResourceClass, List<ResourceRoot>>();
		
		initialize();
	}
	
	public void addSpawnedResource(GalacticResource loadedResource) {
		spawnedResources.add(loadedResource);
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
	
	public SWGObject createResource() {
		Planet planet = core.terrainService.getPlanetByID(1);
		Point3D position = new Point3D(0, 0, 0);
		Quaternion orientation = new Quaternion(1, 1, 1, 1);
		String template = "object/resource_container/base/shared_base_resource_container.iff";
		boolean isSnapshot = false;
		long objectID = core.objectService.generateObjectID();
		
		SWGObject object = new GalacticResource(objectID, planet, position, orientation, template);
		object.setPlanetId(planet.getID());
		object.setAttachment("customServerTemplate", template);
		object.setisInSnapshot(isSnapshot);
		core.objectService.getObjectList().put(objectID, object);
		
		return object;
	}
	
	public void createResource(String fileName, String rClass, String rType, short [] minCapArray, short [] maxCapArray) {
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
	
	// ToDo: Improve
	public GalacticResource grabMeatForCreature(CreatureObject corpse) {
		GalacticResource resource = null;
		int planetId = corpse.getPlanetId();
		String meatName = corpse.getStfName(); // placeholder
		List<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources) {
			Vector<Integer> spawnedPlanets = res.getAllSpawnedPlanetIds();
			if (spawnedPlanets.contains(planetId) && res.getResourceRoot().getResourceClass().equals(meatName))
				resource = res;
		}
		return resource;
	}
	
	public GalacticResource grabResourceByClass(String searchName, int planetId) {
		GalacticResource resource = null;
		List<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources) {
			List<Integer> spawnedPlanets = res.getAllSpawnedPlanetIds();
			if (spawnedPlanets.contains(planetId) && res.getResourceRoot().getResourceClass().equals(searchName))
				resource = res; // resourceClass= "Insect Meat" i.e.
		}
		return resource;
	}
	
	public GalacticResource grabResourceByFileName(String searchName) {
		GalacticResource resource = null;
		List<GalacticResource> allResources = getAllSpawnedResources();
		for (GalacticResource res : allResources)
			if (res.getResourceRoot().getResourceFileName().equals(searchName))
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
	
	public void loadResources() {
		System.out.println("Loading resources...");
		long start = System.nanoTime();
		try {
			final int DESC_INDEX = 16;
			final int MIN_MAX_INDEX = 27;
			List <String> acceptablePrefixes = Arrays.asList(new String [] {
				"Corellian",
				"Dantooine",
				"Dathomirian",
				"Endorian",
				"Lokian",
				"Nabooian",
				"Rori",
				"Talusian",
				"Tatooinian",
				"Yavinian",
				"Kashyyykian",
				"Mustafarian",
				"Yavin IV"
			});
			String [] classes = new String[8];
			Map <String, Integer> minMap = new HashMap<String, Integer>();
			Map <String, Integer> maxMap = new HashMap<String, Integer>();
			
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/resource/resource_tree.iff", DatatableVisitor.class);
			for (int i = 0; i < visitor.getRowCount(); i++) {
				if (visitor.getObject(i, 0) != null) {
					int end = 2;
					for (int j = 2; j < 10; j++) {
						if (!((String) visitor.getObject(i, j)).isEmpty()) {
							classes[j-2] = (String) visitor.getObject(i, j);
							end = j;
						}
					}
					for (int j = end+1; j < 10; j++)
						classes[j-2] = "";
					for (int j = DESC_INDEX; j < DESC_INDEX+11 && j < visitor.getColumnCount(); j++) {
						if (visitor.getObject(i, j) instanceof String) {
							String str = (String) visitor.getObject(i, j);
							if (!str.isEmpty()) {
								minMap.put(str, (Integer) visitor.getObject(i, (j-DESC_INDEX)*2+MIN_MAX_INDEX));
								maxMap.put(str, (Integer) visitor.getObject(i, (j-DESC_INDEX)*2+MIN_MAX_INDEX+1));
							}
						}
					}
					String combined = "";
					for (int j = classes.length-1; j >= 0; j--) {
						if (classes[j] != null && !classes[j].isEmpty()) {
							combined = classes[j];
							break;
						}
					}
					if (!visitor.getObject(i, 12).equals(0)) {
						String [] split = combined.split(" ", 2);
						String rType = split.length >= 1 ? split[0] : "";
						String rClass = split.length >= 2 ? split[1] : "";
						if (!acceptablePrefixes.contains(rType)) {
							rType = "";
							rClass = combined;
						}
						rType = rType.trim();
						rClass = rClass.trim();
						if (!rClass.isEmpty()) {
							short [] minimums = new short[11];
							short [] maximums = new short[11];
							minimums[0]  = minMap.get("res_cold_resist").shortValue();
							maximums[0]  = maxMap.get("res_cold_resist").shortValue();
							minimums[1]  = minMap.get("res_conductivity").shortValue();
							maximums[1]  = maxMap.get("res_conductivity").shortValue();
							minimums[2]  = minMap.get("res_decay_resist").shortValue();
							maximums[2]  = maxMap.get("res_decay_resist").shortValue();
							minimums[3]  = minMap.get("res_heat_resist").shortValue();
							maximums[3]  = maxMap.get("res_heat_resist").shortValue();
							minimums[4]  = minMap.get("res_malleability").shortValue();
							maximums[4]  = maxMap.get("res_malleability").shortValue();
							minimums[5]  = minMap.get("res_shock_resistance").shortValue();
							maximums[5]  = maxMap.get("res_shock_resistance").shortValue();
							minimums[6]  = minMap.get("res_toughness").shortValue();
							maximums[6]  = maxMap.get("res_toughness").shortValue();
							minimums[7]  = minMap.get("entangle_resistance").shortValue();
							maximums[7]  = maxMap.get("entangle_resistance").shortValue();
							minimums[8]  = minMap.get("res_potential_energy").shortValue();
							maximums[8]  = maxMap.get("res_potential_energy").shortValue();
							minimums[9]  = minMap.get("res_flavor").shortValue();
							maximums[9]  = maxMap.get("res_flavor").shortValue();
							minimums[10] = minMap.get("res_quality").shortValue();
							maximums[10] = maxMap.get("res_quality").shortValue();
							createResource((String)visitor.getObject(i, 1), rClass, rType, minimums, maximums);
						}
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		double timeMs = (System.nanoTime() - start) / 1E6;
		System.out.println(String.format("Finished loading resources. Took %.3fms. Loaded %d resources.", timeMs, resourceRootTable.size()));
		System.out.println("Spawning resources...");
		start = System.nanoTime();
		spawnResources();
		timeMs = (System.nanoTime() - start) / 1E6;
		System.out.println(String.format("Finished spawning resources. Took %.3fms.", timeMs));
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
	
	@Override
	public void shutdown() {
		
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
			
			core.getResourcesODB().put(resource.getObjectID(), resource);
			core.getResourceHistoryODB().put(resource.getObjectID(), resource);
			resourceNameHistory.add(resource.getName());
			addSpawnedResource(resource);
		} catch (Exception e) {
			System.err.println("Error in spawning resource: " + root.getResourceFileName());
			if (planetId != -1)
				System.err.println("  On Planet: " + planetId);
			e.printStackTrace();
		}
		return;
	}
	
	private void spawnResources() {
		System.out.println("Persisting resources...");
		
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
		for (int i = 0; i < 8; i++)
			spawnResource(1, jtlRoots.get(new Random().nextInt(jtlRoots.size() - 1)));
		
		// 16 out of 24
		for (int i = 0; i < 16; i++)
			spawnResource(1, pickRandomResource(ResourceClass.IRON));
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
				// int stackCount = getResourceSampleQuantity(crafter,
				// sampleResource);
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
}
