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
package services.spawn;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import resources.common.collidables.CollidableBox;
import resources.common.collidables.CollidableCircle;
import resources.datatables.Options;
import resources.datatables.PvpStatus;
import resources.datatables.WeaponType;
import resources.objects.cell.CellObject;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.ai.AIActor;
import services.ai.LairActor;
import engine.resources.common.Stf;
import engine.resources.container.StaticContainerPermissions;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import main.NGECore;

public class SpawnService {

	private NGECore core;
	private Map<Planet, List<SpawnArea>> spawnAreas = new ConcurrentHashMap<Planet, List<SpawnArea>>();
	private Map<String, MobileTemplate> mobileTemplates = new ConcurrentHashMap<String, MobileTemplate>();
	private Map<String, LairGroupTemplate> lairGroupTemplates = new ConcurrentHashMap<String, LairGroupTemplate>();
	private Map<String, LairTemplate> lairTemplates = new ConcurrentHashMap<String, LairTemplate>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
	private Map<String, DynamicSpawnGroup> dynamicGroupTemplates = new ConcurrentHashMap<String, DynamicSpawnGroup>();

	public SpawnService(NGECore core) {
		this.core = core;
		for(Planet planet : core.terrainService.getPlanetList()) {
			spawnAreas.put(planet, new ArrayList<SpawnArea>());
		}
	}	
	
	public CreatureObject spawnCreature(String template, long objectId, String planetName, long cellId, float x, float y, float z, float qW, float qX, float qY, float qZ, short level) {
		
		Planet planet = core.terrainService.getPlanetByName(planetName);
		MobileTemplate mobileTemplate = mobileTemplates.get(template);
		if(planet == null || mobileTemplate == null)
			return null;
		
		CellObject cell = null;
		
		if(cellId != 0) {
			cell = (CellObject) core.objectService.getObject(cellId);
			if(cell == null)
				return null;
		}
		
		CreatureObject creature = (CreatureObject) core.objectService.createObject(mobileTemplate.getTemplates().get(new Random().nextInt(mobileTemplate.getTemplates().size())), objectId, planet, new Point3D(x, y, z), new Quaternion(qW, qX, qY, qZ), null, (objectId > 0), true);
		
		if(creature == null)
			return null;
		
		TangibleObject inventory = (TangibleObject) core.objectService.createObject("object/tangible/inventory/shared_character_inventory.iff", creature.getPlanet());
		inventory.setContainerPermissions(StaticContainerPermissions.STATIC_CONTAINER_PERMISSIONS);

		
		/*if(mobileTemplate.getCustomWeapon() != null) {
			creature.addObjectToEquipList(mobileTemplate.getCustomWeapon());
			creature.add(mobileTemplate.getCustomWeapon());
			creature.setWeaponId(mobileTemplate.getCustomWeapon().getObjectID());
		}*/
		
		if(mobileTemplate.getLootGroups() != null && !mobileTemplate.getLootGroups().isEmpty())
			creature.setLootGroups(mobileTemplate.getLootGroups());
		
		creature.setOptionsBitmask(mobileTemplate.getOptionsBitmask());
				
		creature.setFaction(mobileTemplate.getFaction());
		creature.setFactionStatus(mobileTemplate.getFactionStatus());
		creature.setPvpBitmask(mobileTemplate.getPvpBitmask());
		if (mobileTemplate.getCustomName()==null){
			creature.setStfFilename("mob/creature_names"); // TODO: set other STFs for NPCs other than creatures -> DONE
			if (mobileTemplate.getStfFilename()!=null)
				creature.setStfFilename(mobileTemplate.getStfFilename());
			creature.setStfName(mobileTemplate.getCreatureName());
		} else {
			creature.setCustomName(mobileTemplate.getCustomName()); // For cases where no STF name can be found!
		}
		creature.setHeight(mobileTemplate.getScale());
		int difficulty = mobileTemplate.getDifficulty();
		creature.setDifficulty((byte) difficulty);

		if(level != -1)
			creature.setLevel(level);
		else {
			if (mobileTemplate.getLevel()!=0){
				level = mobileTemplate.getLevel();
				creature.setLevel(level);
			} else {
				level = -1;
			}
		}
		
		if (mobileTemplate.getMinLevel()!=0 && mobileTemplate.getMaxLevel()!=0 && level == -1){
			level = (short) (mobileTemplate.getMinLevel() + (new Random().nextInt(mobileTemplate.getMaxLevel()-mobileTemplate.getMinLevel()))); 
			creature.setLevel(level);
		}
		
		WeaponObject defaultWeapon = null;
		Vector<WeaponTemplate> weaponTemplates = mobileTemplate.getWeaponTemplateVector();
		int rnd = 0;

		if (weaponTemplates.size() > 0 )
			rnd = new Random().nextInt(weaponTemplates.size());
		
		if (weaponTemplates.size() == 0){
			defaultWeapon =  (WeaponObject) core.objectService.createObject("object/weapon/creature/shared_creature_default_weapon.iff", creature.getPlanet());
			defaultWeapon.setAttackSpeed(1.0F);
			defaultWeapon.setWeaponType(WeaponType.UNARMED);
			defaultWeapon.setDamageType("kinetic");

			defaultWeapon.setMaxRange(5);
		} else {
			defaultWeapon = (WeaponObject) core.objectService.createObject(weaponTemplates.get(rnd).getTemplate(), creature.getPlanet());
			defaultWeapon.setAttackSpeed(weaponTemplates.get(rnd).getAttackSpeed());
			defaultWeapon.setWeaponType(weaponTemplates.get(rnd).getWeaponType());
			defaultWeapon.setMaxRange(weaponTemplates.get(rnd).getMaxRange());
			defaultWeapon.setDamageType(weaponTemplates.get(rnd).getDamageType());
		}
		
		if (weaponTemplates.get(rnd).getMinDamage() != 0 && weaponTemplates.get(rnd).getMaxDamage() != 0) {
			defaultWeapon.setMaxDamage(weaponTemplates.get(rnd).getMinDamage());
			defaultWeapon.setMinDamage(weaponTemplates.get(rnd).getMaxDamage());
		} else {
			defaultWeapon.setMaxDamage(creature.getLevel() * 24);
			defaultWeapon.setMinDamage(creature.getLevel() * 22);
		}
		
		creature.addObjectToEquipList(defaultWeapon);
		creature.add(defaultWeapon);
		creature.setWeaponId(defaultWeapon.getObjectID());
		creature.addObjectToEquipList(inventory);
		creature.add(inventory);

		int customHealth = mobileTemplate.getHealth();
		if(difficulty > 0 && customHealth == 0) {
			if(difficulty == 1) {
				creature.setMaxHealth((int) (400 + level * 134 * 2.5));
				creature.setHealth((int) (400 + level * 134 * 2.5));
				creature.setMaxAction((int) (400 + level * 134 * 2.5));
				creature.setAction((int) (400 + level * 134 * 2.5));		
			} else if(difficulty == 2) {
				creature.setMaxHealth((int) (400 + level * 134 * 10));
				creature.setHealth((int) (400 + level * 134 * 10));
				creature.setMaxAction((int) (400 + level * 134 * 10));
				creature.setAction((int) (400 + level * 134 * 10));		
			}
		} else if(difficulty <= 0 && customHealth == 0) {
			creature.setMaxHealth((int) (400 + level * 134));
			creature.setHealth((int) (400 + level * 134));
			creature.setMaxAction((int) (400 + level * 134));
			creature.setAction((int) (400 + level * 134));			
		} else {
			creature.setMaxHealth(customHealth);
			creature.setHealth(customHealth);
			creature.setMaxAction((int) (level * 128));
			creature.setAction((int) (level * 128));			
		}
		
		if(creature.getLevel() > 16) {
			int armor = (creature.getLevel() - 16) * 87;
			if(armor > 6000)
				armor = 6000;
			core.skillModService.addSkillMod(creature, "expertise_innate_protection_all", armor);
		}

		AIActor actor = new AIActor(creature, creature.getPosition(), scheduler);
		creature.setAttachment("AI", actor);
		if (mobileTemplate.getConversationFileName().length()>0){
			creature.setAttachment("radial_filename", "object/conversation");
			creature.setAttachment("conversationFile", mobileTemplate.getConversationFileName());
			}
		else  creature.setAttachment("radial_filename", "npc/mobile");
		actor.setMobileTemplate(mobileTemplate);
		
	
		
		if(cell == null) {
			core.simulationService.add(creature, x, z, true);
		} else {
			creature.getPosition().setCell(cell);
			cell.add(creature);
		}
		return creature;
	}
	
	public CreatureObject spawnCreatureBaby(String template, long objectId, String planetName, long cellId, float x, float y, float z, float qW, float qX, float qY, float qZ, short level) {

		Planet planet = core.terrainService.getPlanetByName(planetName);
		MobileTemplate mobileTemplate = mobileTemplates.get(template);
		if(planet == null || mobileTemplate == null)
			return null;
		

		
		CellObject cell = null;
		
		if(cellId != 0) {
			cell = (CellObject) core.objectService.getObject(cellId);
			if(cell == null)
				return null;
		}

		CreatureObject creature = (CreatureObject) core.objectService.createObject(mobileTemplate.getTemplates().get(new Random().nextInt(mobileTemplate.getTemplates().size())), objectId, planet, new Point3D(x, y, z), new Quaternion(qW, qX, qY, qZ), null, (objectId > 0), true);
		
		if(creature == null)
			return null;

		TangibleObject inventory = (TangibleObject) core.objectService.createObject("object/tangible/inventory/shared_character_inventory.iff", creature.getPlanet());
		inventory.setContainerPermissions(StaticContainerPermissions.STATIC_CONTAINER_PERMISSIONS);

		
		/*if(mobileTemplate.getCustomWeapon() != null) {
			creature.addObjectToEquipList(mobileTemplate.getCustomWeapon());
			creature.add(mobileTemplate.getCustomWeapon());
			creature.setWeaponId(mobileTemplate.getCustomWeapon().getObjectID());
		}*/
		
		if(mobileTemplate.getLootGroups() != null && !mobileTemplate.getLootGroups().isEmpty())
			creature.setLootGroups(mobileTemplate.getLootGroups());
		
		creature.setOptionsBitmask(mobileTemplate.getOptionsBitmask());
		creature.setFaction(mobileTemplate.getFaction());
		creature.setFactionStatus(mobileTemplate.getFactionStatus());
		creature.setPvpBitmask(mobileTemplate.getPvpBitmask());

		if (mobileTemplate.getCustomName()==null){
//			//creature.setStfFilename("mob/creature_names"); // TODO: set other STFs for NPCs other than creatures -> DONE
			if (mobileTemplate.getStfFilename()!=null){			
//				System.out.println("S4");
//				Stf stf = new Stf(mobileTemplate.getStfFilename());
//				System.out.println("S5");
//				String stfValue = stf.getStfValue();
//				System.out.println("S6");
//				creature.setCustomName(stfValue+" (baby)");
//				
				System.out.println("STFNAMECHANGE");
			}
			if (mobileTemplate.getCreatureName()!=null){
				String creatureName = mobileTemplate.getCreatureName();
				Stf stf = new Stf("mob/creature_names", 0, creatureName); 
				String stfValue = stf.getStfValue();
				creature.setCustomName(stfValue+" (baby)");
			}
			
		} else {
			creature.setCustomName(mobileTemplate.getCustomName()+" (baby)"); // For cases where no STF name can be found!
		}
		creature.setHeight(.25F*mobileTemplate.getScale());
		int difficulty = mobileTemplate.getDifficulty();
		creature.setDifficulty((byte) difficulty);

//		if(level != -1)
//			creature.setLevel(level);
//		else {
//			if (mobileTemplate.getLevel()!=0){
//				level = mobileTemplate.getLevel();
//				creature.setLevel(level);
//			} else {
//				level = -1;
//			}
//		}
		
		creature.setLevel((short)1);
		
		if (mobileTemplate.getMinLevel()!=0 && mobileTemplate.getMaxLevel()!=0 && level == -1){
			level = (short) (mobileTemplate.getMinLevel() + (new Random().nextInt(mobileTemplate.getMaxLevel()-mobileTemplate.getMinLevel()))); 
			creature.setLevel(level);
		}
		
		WeaponObject defaultWeapon = null;
		Vector<WeaponTemplate> weaponTemplates = mobileTemplate.getWeaponTemplateVector();
		int rnd = 0;

		if (weaponTemplates.size() > 0 )
			rnd = new Random().nextInt(weaponTemplates.size());
		
		if (weaponTemplates.size() == 0){
			defaultWeapon =  (WeaponObject) core.objectService.createObject("object/weapon/creature/shared_creature_default_weapon.iff", creature.getPlanet());
			defaultWeapon.setAttackSpeed(1.0F);
			defaultWeapon.setWeaponType(WeaponType.UNARMED);
			defaultWeapon.setDamageType("kinetic");

			defaultWeapon.setMaxRange(5);
		} else {
			defaultWeapon = (WeaponObject) core.objectService.createObject(weaponTemplates.get(rnd).getTemplate(), creature.getPlanet());
			defaultWeapon.setAttackSpeed(weaponTemplates.get(rnd).getAttackSpeed());
			defaultWeapon.setWeaponType(weaponTemplates.get(rnd).getWeaponType());
			defaultWeapon.setMaxRange(weaponTemplates.get(rnd).getMaxRange());
			defaultWeapon.setDamageType(weaponTemplates.get(rnd).getDamageType());
		}
		
		if (weaponTemplates.get(rnd).getMinDamage() != 0 && weaponTemplates.get(rnd).getMaxDamage() != 0) {
			defaultWeapon.setMaxDamage(weaponTemplates.get(rnd).getMinDamage());
			defaultWeapon.setMinDamage(weaponTemplates.get(rnd).getMaxDamage());
		} else {
			defaultWeapon.setMaxDamage(creature.getLevel() * 24);
			defaultWeapon.setMinDamage(creature.getLevel() * 22);
		}
		
		creature.addObjectToEquipList(defaultWeapon);
		creature.add(defaultWeapon);
		creature.setWeaponId(defaultWeapon.getObjectID());
		creature.addObjectToEquipList(inventory);
		creature.add(inventory);

		int customHealth = mobileTemplate.getHealth();
		if(difficulty > 0 && customHealth == 0) {
			if(difficulty == 1) {
				creature.setMaxHealth((int) (400 + level * 134 * 2.5));
				creature.setHealth((int) (400 + level * 134 * 2.5));
				creature.setMaxAction((int) (400 + level * 134 * 2.5));
				creature.setAction((int) (400 + level * 134 * 2.5));		
			} else if(difficulty == 2) {
				creature.setMaxHealth((int) (400 + level * 134 * 10));
				creature.setHealth((int) (400 + level * 134 * 10));
				creature.setMaxAction((int) (400 + level * 134 * 10));
				creature.setAction((int) (400 + level * 134 * 10));		
			}
		} else if(difficulty <= 0 && customHealth == 0) {
			creature.setMaxHealth((int) (400 + level * 134));
			creature.setHealth((int) (400 + level * 134));
			creature.setMaxAction((int) (400 + level * 134));
			creature.setAction((int) (400 + level * 134));			
		} else {
			creature.setMaxHealth(customHealth);
			creature.setHealth(customHealth);
			creature.setMaxAction((int) (level * 128));
			creature.setAction((int) (level * 128));			
		}
		
		if(creature.getLevel() > 16) {
			int armor = (creature.getLevel() - 16) * 87;
			if(armor > 6000)
				armor = 6000;
			core.skillModService.addSkillMod(creature, "expertise_innate_protection_all", armor);
		}
		
		// Baby AI
		creature.setAttachment("tamed", 0);
		AIActor actor = new AIActor(creature, creature.getPosition(), scheduler);
		creature.setAttachment("AI", actor);
		creature.setAttachment("radial_filename", "npc/untamed");
		actor.setMobileTemplate(mobileTemplate);
		
	
		
		if(cell == null) {
			core.simulationService.add(creature, x, z, true);
		} else {
			creature.getPosition().setCell(cell);
			cell.add(creature);
		}
		return creature;
	}
	
	public CreatureObject spawnCreature(String template, String planetName, long cellId, float x, float y, float z, float qW, float qX, float qY, float qZ, short level) {
		return spawnCreature(template, 0L, planetName, cellId, x, y, z, qW, qX, qY, qZ, level);
	}
	
	public CreatureObject spawnCreature(String mobileTemplate, String planetName, long cellId, float x, float y, float z, float qW, float qX, float qY, float qZ, int level) {
		return spawnCreature(mobileTemplate, planetName, cellId, x, y, z, 1, 0, 0, 0, (short) level);
	}
	
	public CreatureObject spawnCreature(String mobileTemplate, String planetName, long cellId, float x, float y, float z) {
		return spawnCreature(mobileTemplate, planetName, cellId, x, y, z, 1, 0, 0, 0, (short) -1);
	}
	
	public CreatureObject spawnCreature(String mobileTemplate, String planetName, long cellId, float x, float y, float z, short level) {
		return spawnCreature(mobileTemplate, planetName, cellId, x, y, z, 1, 0, 0, 0, level);
	}
	
	public CreatureObject spawnCreature(String mobileTemplate, String planetName, long cellId, float x, float z) {
		Planet planet = core.terrainService.getPlanetByName(planetName);
		return spawnCreature(mobileTemplate, planetName, cellId, x, core.terrainService.getHeight(planet.getID(), x, z), z, 1, 0, 0, 0, (short) -1);
	}
	
	public CreatureObject spawnCreature(String mobileTemplate, String planetName, long cellId, float x, float z, short level) {
		Planet planet = core.terrainService.getPlanetByName(planetName);
		return spawnCreature(mobileTemplate, planetName, cellId, x, core.terrainService.getHeight(planet.getID(), x, z), z, 1, 0, 0, 0, level);
	}

	public CreatureObject spawnCreatureBaby(String mobileTemplate, String planetName, long cellId, float x, float y, float z, short level) {
		return spawnCreatureBaby(mobileTemplate, planetName, cellId, x, y, z, 1, 0, 0, 0, level);
	}
	
	public CreatureObject spawnCreatureBaby(String template, String planetName, long cellId, float x, float y, float z, float qW, float qX, float qY, float qZ, short level) {
		return spawnCreatureBaby(template, 0L, planetName, cellId, x, y, z, qW, qX, qY, qZ, level);
	}
	
	public LairActor spawnLair(String lairSpawnTemplate, Planet planet, Point3D position, short level) {
		
		LairTemplate lairTemplate = lairTemplates.get(lairSpawnTemplate);
		if(lairTemplate == null)
			return null;
		TangibleObject lairObject = (TangibleObject) core.objectService.createObject(lairTemplate.getLairCRC(), 0, planet, position, new Quaternion(1, 0, 0, 0));
		
		if(lairObject == null)
			return null;
		
		lairObject.setOptionsBitmask(Options.ATTACKABLE);
		lairObject.setPvpBitmask(PvpStatus.Attackable);
		lairObject.setMaximumCondition(1000 * level);
		
		LairActor lairActor = new LairActor(lairObject, lairTemplate.getMobileName(), 10, level);
		
		if (lairTemplate.getMobiles()!=null)
			lairActor = new LairActor(lairObject, lairTemplate.getMobiles(), 10, level);
		
		lairObject.setAttachment("AI", lairActor);
		
		core.simulationService.add(lairObject, position.x, position.z, true);
		lairActor.spawnNewCreatures();
		
		return lairActor;
		
	}
	
	public void loadMobileTemplates() {
	    Path p = Paths.get("scripts/mobiles");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	if(!file.toString().contains("lairs") && !file.toString().contains("spawnareas") 
	        	   && !file.toString().contains("lairgroups") && !file.toString().contains("dynamicgroups"))
	        		core.scriptService.callScript(file.toString().replace(file.getFileName().toString(), ""), file.getFileName().toString().replace(".py", ""), "addTemplate", core);
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void addMobileTemplate(String template, MobileTemplate mobileTemplate) {
		mobileTemplates.put(template, mobileTemplate);
	}
	
	public void loadLairTemplates() {
	    Path p = Paths.get("scripts/mobiles/lairs");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	core.scriptService.callScript("scripts/mobiles/lairs/", file.getFileName().toString().replace(".py", ""), "addTemplate", core);
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addLairTemplate(String name, String mobile, int mobileLimit, String lairCRC) {
		lairTemplates.put(name, new LairTemplate(name, mobile, mobileLimit, lairCRC));
	}
	
	public void addLairTemplate(String name, Vector<String> mobiles, int mobileLimit, String lairCRC) {
		lairTemplates.put(name, new LairTemplate(name, mobiles, mobileLimit, lairCRC));
	}
	
	public void loadLairGroups() {
	    Path p = Paths.get("scripts/mobiles/lairgroups");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	core.scriptService.callScript("scripts/mobiles/lairgroups/", file.getFileName().toString().replace(".py", ""), "addLairGroup", core);
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadDynamicGroups() {
	    Path p = Paths.get("scripts/mobiles/dynamicgroups");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	core.scriptService.callScript("scripts/mobiles/dynamicgroups/", file.getFileName().toString().replace(".py", ""), "addDynamicGroup", core);
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDynamicGroup(String name, DynamicSpawnGroup dynamicSpawnGroup) {
		dynamicGroupTemplates.put(name, dynamicSpawnGroup);
	}
	
	public void addLairGroup(String name, Vector<LairSpawnTemplate> lairSpawnTemplates) {
		lairGroupTemplates.put(name, new LairGroupTemplate(name, lairSpawnTemplates));
	}
		
	public void loadSpawnAreas() {
	    Path p = Paths.get("scripts/mobiles/spawnareas");
	    FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	        	core.scriptService.callScript("scripts/mobiles/spawnareas/", file.getFileName().toString().replace(".py", ""), "addSpawnArea", core);
	        	return FileVisitResult.CONTINUE;
	        }
	    };
        try {
			Files.walkFileTree(p, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Circular LairSpawnArea
	public void addLairSpawnArea(String lairGroup, float x, float z, float radius, String planetName) {
		LairGroupTemplate lairGroupTemplate = lairGroupTemplates.get(lairGroup);
		Planet planet = core.terrainService.getPlanetByName(planetName);
		if(lairGroupTemplate == null || planet == null)
			return;
		CollidableCircle collidableCircle = new CollidableCircle(new Point3D(x, 0, z), radius, planet);
		LairSpawnArea lairSpawnArea = new LairSpawnArea(planet, collidableCircle, lairGroupTemplate);
		spawnAreas.get(planet).add(lairSpawnArea);
		core.simulationService.addCollidable(collidableCircle, x, z);
	}
	
	// Rectangular LairSpawnArea
	public void addLairSpawnArea(String lairGroup, float x, float z, float width, float height, String planetName) {
		LairGroupTemplate lairGroupTemplate = lairGroupTemplates.get(lairGroup);
		Planet planet = core.terrainService.getPlanetByName(planetName);
		if(lairGroupTemplate == null || planet == null)
			return;
		CollidableBox collidableBox = new CollidableBox(new Point3D(x, 0, z), width, height, planet);
		LairSpawnArea lairSpawnArea = new LairSpawnArea(planet, collidableBox, lairGroupTemplate);
		spawnAreas.get(planet).add(lairSpawnArea);
		core.simulationService.addCollidable(collidableBox, x, z);
	}
	
	// Circular DynamicSpawnArea
	public void addDynamicSpawnArea(String dynamicGroup, float x, float z, float radius, String planetName) {
		DynamicSpawnGroup dynamicGroupTemplate = dynamicGroupTemplates.get(dynamicGroup);
		Planet planet = core.terrainService.getPlanetByName(planetName);
		if(dynamicGroupTemplate == null || planet == null)
			return;
		CollidableCircle collidableCircle = new CollidableCircle(new Point3D(x, 0, z), radius, planet);
		DynamicSpawnArea dynamicSpawnArea = new DynamicSpawnArea(planet, collidableCircle, dynamicGroupTemplate);
		spawnAreas.get(planet).add(dynamicSpawnArea);
		core.simulationService.addCollidable(collidableCircle, x, z);
	}
	
	// Rectangular DynamicSpawnArea
	public void addDynamicSpawnArea(String dynamicGroup, float x, float z, float width, float height, String planetName) {
		DynamicSpawnGroup dynamicGroupTemplate = dynamicGroupTemplates.get(dynamicGroup);
		Planet planet = core.terrainService.getPlanetByName(planetName);
		if(dynamicGroupTemplate == null || planet == null)
			return;
		CollidableBox collidableBox = new CollidableBox(new Point3D(x, 0, z), width, height, planet);
		DynamicSpawnArea dynamicSpawnArea = new DynamicSpawnArea(planet, collidableBox, dynamicGroupTemplate);
		spawnAreas.get(planet).add(dynamicSpawnArea);
		core.simulationService.addCollidable(collidableBox, x, z);
	}
	
	// Circular DynamicSpawnArea with Vector argument
	public void addDynamicSpawnArea(Vector<String> dynamicGroups, float x, float z, float radius, String planetName) {
		Vector<DynamicSpawnGroup> dynamicSpawnGroups = new Vector<DynamicSpawnGroup>();
		for (String template : dynamicGroups){
			if (dynamicGroupTemplates.get(template)==null){
				System.err.println("Dynamic Group template " + template +" not found in spawnservice collection!");	
				return;
			}
			dynamicSpawnGroups.add(dynamicGroupTemplates.get(template));
		}
		
		Planet planet = core.terrainService.getPlanetByName(planetName);
		if(dynamicSpawnGroups == null || dynamicSpawnGroups.size() == 0 || planet == null)
			return;
		CollidableCircle collidableCircle = new CollidableCircle(new Point3D(x, 0, z), radius, planet);
		DynamicSpawnArea dynamicSpawnArea = new DynamicSpawnArea(planet, collidableCircle, dynamicSpawnGroups);
		spawnAreas.get(planet).add(dynamicSpawnArea);
		core.simulationService.addCollidable(collidableCircle, x, z);
	}
	
	// Rectangular DynamicSpawnArea with Vector argument
	public void addDynamicSpawnArea(Vector<String> dynamicGroups, float x, float z, float width, float height, String planetName) {
		Vector<DynamicSpawnGroup> dynamicSpawnGroups = new Vector<DynamicSpawnGroup>();
		for (String template : dynamicGroups){
			if (dynamicGroupTemplates.get(template)==null){
				System.err.println("Dynamic Group template " + template +" not found in spawnservice collection!");	
				return;
			}
			dynamicSpawnGroups.add(dynamicGroupTemplates.get(template));
		}
		
		Planet planet = core.terrainService.getPlanetByName(planetName);
		if(dynamicSpawnGroups == null || dynamicSpawnGroups.size() == 0 || planet == null)
			return;
		CollidableBox collidableBox = new CollidableBox(new Point3D(x, 0, z), width, height, planet);
		DynamicSpawnArea dynamicSpawnArea = new DynamicSpawnArea(planet, collidableBox, dynamicSpawnGroups);
		spawnAreas.get(planet).add(dynamicSpawnArea);
		core.simulationService.addCollidable(collidableBox, x, z);
	}
	
	// Circular MixedSpawnArea
	public void addMixedSpawnArea(Vector<String> passedGroups, float x, float z, float radius, String planetName) {
		Vector<SpawnGroup> mixedSpawnGroups = new Vector<SpawnGroup>();
		for (String template : passedGroups){
			if (dynamicGroupTemplates.get(template)!=null){
				mixedSpawnGroups.add(dynamicGroupTemplates.get(template));
			}
			if (lairGroupTemplates.get(template)!=null){
				mixedSpawnGroups.add(lairGroupTemplates.get(template));
			}			
			if(dynamicGroupTemplates.get(template) == null && lairGroupTemplates.get(template)==null)
				return;			
		}
				
		Planet planet = core.terrainService.getPlanetByName(planetName);
		if(mixedSpawnGroups == null || mixedSpawnGroups.size() == 0 || planet == null)
			return;
		CollidableCircle collidableCircle = new CollidableCircle(new Point3D(x, 0, z), radius, planet);
		MixedSpawnArea dynamicSpawnArea = new MixedSpawnArea(planet, collidableCircle, mixedSpawnGroups);
		spawnAreas.get(planet).add(dynamicSpawnArea);
		core.simulationService.addCollidable(collidableCircle, x, z);
	}
	
	// Rectangular MixedSpawnArea
	public void addMixedSpawnArea(Vector<String> passedGroups, float x, float z, float width, float height, String planetName) {
		Vector<SpawnGroup> mixedSpawnGroups = new Vector<SpawnGroup>();
		for (String template : passedGroups){
			if (dynamicGroupTemplates.get(template)!=null){
				mixedSpawnGroups.add(dynamicGroupTemplates.get(template));
			}
			if (lairGroupTemplates.get(template)!=null){
				mixedSpawnGroups.add(lairGroupTemplates.get(template));
			}			
			if(dynamicGroupTemplates.get(template) == null && lairGroupTemplates.get(template)==null)
				return;			
		}
				
		Planet planet = core.terrainService.getPlanetByName(planetName);
		if(mixedSpawnGroups == null || mixedSpawnGroups.size() == 0 || planet == null)
			return;
		CollidableBox collidableBox = new CollidableBox(new Point3D(x, 0, z), width, height, planet);
		MixedSpawnArea dynamicSpawnArea = new MixedSpawnArea(planet, collidableBox, mixedSpawnGroups);
		spawnAreas.get(planet).add(dynamicSpawnArea);
		core.simulationService.addCollidable(collidableBox, x, z);
	}

	public Map<String, MobileTemplate> getMobileTemplates() {
		return mobileTemplates;
	}
	
	public MobileTemplate getMobileTemplate(String template) {
		return mobileTemplates.get(template);
	}
	
}
