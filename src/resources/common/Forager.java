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
package resources.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import main.NGECore;
import resources.datatables.DisplayType;
import resources.datatables.Options;
import resources.loot.LootGroup;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;
import resources.objects.tangible.TangibleObject;
import services.ai.AIActor;

/** 
 * @author Charon 
 */

public class Forager {
	
	public static final int LYASE_ENZYME         = 0;
	public static final int RARE_COMPONENT       = 1;
	public static final int INSECT_BAIT          = 2;
	public static final int GRUB_BAIT            = 3;
	public static final int WORM_BAIT            = 4;
	public static final int MYSTERIOUS_DATA_DISC = 5;
	
	public static final String[] RARE_COMPONENTS_CORELLIA = new String[]{"object/tangible/loot/creature_loot/collections/fried_icecream_components/corellia/shared_charbote.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/corellia/shared_goldfruit.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/corellia/shared_kavasa_fruit.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/corellia/shared_salthia.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/corellia/shared_sunfruit.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/corellia/shared_vweilu.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/corellia/shared_yam.iff"
	};
	
	public static final String[] RARE_COMPONENTS_DANTOOINE = new String[]{"object/tangible/loot/creature_loot/collections/fried_icecream_components/dantooine/shared_barabel.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dantooine/shared_celonslay.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dantooine/shared_dorian.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dantooine/shared_kibla_greens.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dantooine/shared_shefna_fruit.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dantooine/shared_tritacale.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dantooine/shared_yot_beans.iff"
	};
	
	public static final String[] RARE_COMPONENTS_DATHOMIR = new String[]{"object/tangible/loot/creature_loot/collections/fried_icecream_components/dathomir/shared_beebleberry.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dathomir/shared_driblis.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dathomir/shared_dricklefruit.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dathomir/shared_grape.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dathomir/shared_hwotha.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dathomir/shared_plaitfruit.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/dathomir/shared_qana.iff"
	};
		
	public static final String[] RARE_COMPONENTS_ENDOR = new String[]{"object/tangible/loot/creature_loot/collections/fried_icecream_components/endor/shared_acorns.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/endor/shared_blumfruit.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/endor/shared_burr_ball.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/endor/shared_gin_jang_berry.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/endor/shared_honey_melon.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/endor/shared_sunberries.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/endor/shared_yubnut.iff"
	};
	
	public static final String[] RARE_COMPONENTS_NABOO = new String[]{"object/tangible/loot/creature_loot/collections/fried_icecream_components/naboo/shared_cligs.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/naboo/shared_cracknut.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/naboo/shared_denta.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/naboo/shared_muja.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/naboo/shared_ootoowergs.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/naboo/shared_phraig.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/naboo/shared_stinkmelon.iff"
	};
	
	public static final String[] RARE_COMPONENTS_TATOOINE = new String[]{"object/tangible/loot/creature_loot/collections/fried_icecream_components/tatooine/shared_banthaweed.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/tatooine/shared_bestinnian.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/tatooine/shared_bloddle.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/tatooine/shared_bristlemelon.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/tatooine/shared_tatoes.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/tatooine/shared_wyykmelons.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/tatooine/shared_zog.iff"
	};
	
	public static final String[] RARE_COMPONENTS_YAVIN = new String[]{"object/tangible/loot/creature_loot/collections/fried_icecream_components/yavin/shared_blueleaf_shrub.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/yavin/shared_celto.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/yavin/shared_farrberries.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/yavin/shared_sweetmelon.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/yavin/shared_topatoes.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/yavin/shared_trig_berries.iff",
		"object/tangible/loot/creature_loot/collections/fried_icecream_components/yavin/shared_wuli_nuts.iff"
	};
	
	private static final HashMap<Integer,String[]> rareComponentHash= new HashMap<Integer,String[]>();
		
	private int[] mapPlanetIDs = new int[]{6,7,8};
	
	// consider foraged food food/foraged	
	// tangible/item/treasure_map/shared_relic_buff_s01.iff
	
	public Forager(){
		
	}
	
	@SuppressWarnings("unused")
	public boolean handleForageResults(CreatureObject forager){
		// determine foraged object
		SWGObject foragerInventory = forager.getSlottedObject("inventory");
		
		
		// you can use Kommerken Steak food which increases your 
		// foraging chance by 12% and gives you a 
		// 5% bonus chance of finding a treasure map. 
		
		int randomForageRoll       = new Random().nextInt(100);
		int randomForageObjectRoll = new Random().nextInt(100);
		int remainder = 0; 
		int forageResultChance = 100;
		int kommerkenBonus = 0;

		int[] forageChances  = new int[]{10,10,25,25,25,5}; 
		int[] forageChances2 = new int[]{12,10,21,23,24,10}; // Kommerken Steak
		int[] forageChances3 = new int[]{20,9,22,22,22,5};   // if you got the pig
		
		rareComponentHash.put(1,RARE_COMPONENTS_TATOOINE);
		rareComponentHash.put(2,RARE_COMPONENTS_NABOO);
		rareComponentHash.put(3,RARE_COMPONENTS_CORELLIA);
		rareComponentHash.put(6,RARE_COMPONENTS_DANTOOINE);
		rareComponentHash.put(8,RARE_COMPONENTS_YAVIN);
		rareComponentHash.put(9,RARE_COMPONENTS_ENDOR);
		rareComponentHash.put(10,RARE_COMPONENTS_DATHOMIR);
		
		int chosenObject = 0;
		
//		if (forager.hasChuckedIn(KommerkenSteak)){
//			kommerkenBonus = 12;
//			forageChances = forageChances2;
//	}
		
//		if (forager.gotThePig(pig)){
//			forageChances = forageChances3;
//}
		//forager.sendSystemMessage("randomForageRoll " + randomForageRoll,(byte) 0);
		if (randomForageRoll<forageResultChance+kommerkenBonus) {
		
			for(int i=0;i<forageChances.length;i++) {
				remainder += forageChances[i]; 
		    	if (randomForageObjectRoll <= remainder){ 		
		    		chosenObject = i;
		    		break;
		    	}			 
			}
			
			if (forager.getInventoryItemCount()>=80){
				chosenObject = 6;
				forager.sendSystemMessage("@skl_use:sys_forage_noroom",(byte) 0);				
			}
			
			// chosenObject = 5; // To test TMs
			if(forager.getClient().isGM()) // Always yield a TM for testing
				chosenObject = 5;
			
			//forager.sendSystemMessage("chosenObject " + chosenObject,(byte) 0);
			switch (chosenObject) {
				
				case LYASE_ENZYME:    String template = "object/tangible/loot/beast/shared_enzyme_2.iff"; 
									  TangibleObject foragedObject = (TangibleObject) NGECore.getInstance().objectService.createObject(template, forager.getPlanet());
									  // determine color here
									  foragedObject.setCustomizationVariable("/private/index_color_1", (byte) new Random().nextInt(10)); // edb_vial.pal
									  foragerInventory.add(foragedObject);
									  forager.sendSystemMessage("",(byte) 0);
									  break;
									
				case RARE_COMPONENT:  template =  ""; 				
									  String[] componentArray = rareComponentHash.get(forager.getPlanetId());							
									  int componentRoll = new Random().nextInt(componentArray.length);
									  template = componentArray[componentRoll];
									  foragedObject = (TangibleObject) NGECore.getInstance().objectService.createObject(template, forager.getPlanet());
									  foragerInventory.add(foragedObject);
									  forager.sendSystemMessage("",(byte) 0);
									  break;
									  
				case INSECT_BAIT:     template = "object/tangible/fishing/bait/shared_bait_insect.iff"; 
								      foragedObject = (TangibleObject) NGECore.getInstance().objectService.createObject(template, forager.getPlanet());
								      foragerInventory.add(foragedObject);
								      forager.sendSystemMessage("",(byte) 0);
								      break;
								      
				case GRUB_BAIT:       template = "object/tangible/fishing/bait/shared_bait_grub.iff"; 
								      foragedObject = (TangibleObject) NGECore.getInstance().objectService.createObject(template, forager.getPlanet());
								      foragerInventory.add(foragedObject);
								      forager.sendSystemMessage("",(byte) 0);
								      break;
								      
				case WORM_BAIT:       template = "object/tangible/fishing/bait/shared_bait_worm.iff";  
								      foragedObject = (TangibleObject) NGECore.getInstance().objectService.createObject(template, forager.getPlanet());
								      foragerInventory.add(foragedObject);
								      forager.sendSystemMessage("",(byte) 0);
								      break;
								      
				case MYSTERIOUS_DATA_DISC:    template = "object/tangible/loot/generic_usable/shared_datadisk_generic.iff"; 
										      foragedObject = (TangibleObject) NGECore.getInstance().objectService.createObject(template, forager.getPlanet());
										      foragedObject.setCustomName(getLevelDependentMapName(forager.getLevel()));
										      foragedObject.setAttachment("MapLevel", forager.getLevel());
										      foragedObject.setAttachment("MapSTFName", getLevelDependentSTFName(forager.getLevel()));										      
										      int mapPlanetID = new Random().nextInt(mapPlanetIDs.length-1);
										      foragedObject.setAttachment("MapPlanet", mapPlanetIDs[mapPlanetID]);
										      //System.out.println("mapPlanetID " + mapPlanetIDs[mapPlanetID]);
										      foragedObject.setAttachment("radial_filename", "object/treasuremap");
										      //foragedObject.setAttachment("radial_filename", "object/treasuremapExtract");
										      foragerInventory.add(foragedObject);
										      forager.sendSystemMessage("",(byte) 0);										      										   
										      break;
										      
				default: break;
				///object/tangible/treasure_map/shared_treasure_map_base.iff  shared_treasure_map_quest.iff
			}
			
			if (chosenObject<6)
				forager.sendSystemMessage("@skl_use:sys_forage_success",(byte) 0);
			
			// spawn adversaries
			int adversaryRoll = new Random().nextInt(100);
			// A new buff is imaginable that would decrease the chance of running into adversaries here, just an idea
			
			if (chosenObject>6)
				adversaryRoll=999;
			
			// adversaryRoll=999; // To test TMs
			
			if (adversaryRoll<14) { // 14 100 // To test TMs
				int adversaryTypeRoll = new Random().nextInt(100);
				if (adversaryTypeRoll<50){
					// wormie
					spawnWormie(forager);
				} else {
					// goon
					spawnGoon(forager);
				}				
			}
		
		} else {
			forager.sendSystemMessage("@skl_use:sys_forage_fail",(byte) 0);
			
		}	
		
		return true;
	}
	
	private String getLevelDependentMapName(int level){
		if (level>=1 && level<=10)
			return "Mysterious Data Disk (Adventurer's Treasure)";
		if (level>=11 && level<=20)
			return "Mysterious Data Disk (Villager's Hidden Savings)";
		if (level>=21 && level<=30)
			return "A map to a Trader's Hidden Treasure";
		if (level>=31 && level<=40)
			return "A map to a Bounty Hunter's Hidden Stash";
		if (level>=41 && level<=50)
			return "A droid's data disk with map coordinates";
		if (level>=51 && level<=60)
			return "A map to a Jedi Knight's body";
		if (level>=61 && level<=70)
			return "A map to Jedi Artifacts";
		if (level>=71 && level<=80)
			return "A map to a cursed item";
		if (level>=81 && level<=90)
			return "Mysterious Data Disk (Mysterious Container) ";
		return "";
	}
	
	private String getLevelDependentSTFName(int level){
		if (level>=1 && level<=10)
			return "treasure_guard_adventurer";
		if (level>=11 && level<=20)
			return "treasure_guard_villager";
		if (level>=21 && level<=30)
			return "treasure_guard_trader";
		if (level>=31 && level<=40)
			return "treasure_guard_bounty_hunter";
		if (level>=41 && level<=50)
			return "treasure_guard_droid";
		if (level>=51 && level<=60)
			return "treasure_guard_padawan";
		if (level>=61 && level<=70)
			return "treasure_guard_jedi";
		if (level>=71 && level<=80)
			return "treasure_guard_jedi_elder";
		if (level>=81 && level<=90)
			return "treasure_guard_jedi_elite";
		return "";
	}
	
	
	
	
	
	
	public void spawnWormie(CreatureObject forager){
		int level = forager.getLevel();
		String name = "";		
		Point3D wormiePosition = SpawnPoint.getRandomPosition(forager.getPosition(), 1, 6, forager.getPlanetId());
		CreatureObject wormie = NGECore.getInstance().spawnService.spawnCreature("forage_worm", forager.getPlanet().getName(), 0L, wormiePosition.x, wormiePosition.y, wormiePosition.z, (short)level);
		switch (wormie.getTemplate()) {
		
		case "object/mobile/shared_col_forage_aggravated_worm.iff":
				name = "An Aggravated Worm";
				break;
				
		case "object/mobile/shared_col_forage_angry_worm.iff":
				name = "An Angry Worm";
				break;
				
		case "object/mobile/shared_col_forage_brutal_worm.iff":
				name = "A Brutal Worm";
				break;
				
		case "object/mobile/shared_col_forage_carnivorous_worm.iff":
				name = "A Carnivorous Worm";
				break;
				
		case "object/mobile/shared_col_forage_ferocious_worm.iff":
				name = "A Ferocious Worm";
				break;
				
		case "object/mobile/shared_col_forage_fierce_worm.iff":
				name = "A Fierce Worm";
				break;
				
		case "object/mobile/shared_col_forage_hungry_worm.iff":
				name = "A Hungry Worm";
				break;
				
		case "object/mobile/shared_col_forage_oozing_worm.iff":
				name = "An Oozing Worm";
				break;
				
		case "object/mobile/shared_col_forage_savage_worm.iff":
				name = "A Savage Worm";
				break;
				
		case "object/mobile/shared_col_forage_territorial_worm.iff":
				name = "A Territorial Worm";
				break;
		}
		wormie.setCustomName(name);
		wormie.setLevel(forager.getLevel());
		wormie.setOptions(Options.AGGRESSIVE, true);
		wormie.setOptions(Options.ATTACKABLE, true);
		AIActor actor = (AIActor) wormie.getAttachment("AI");
		actor.addDefender(forager);		
	}
	
	public void spawnGoon(CreatureObject forager){
		System.out.println("GOON");
		int level = forager.getLevel();
		String name = "";		
		Point3D wormiePosition = SpawnPoint.getRandomPosition(forager.getPosition(), 1, 6, forager.getPlanetId());
		CreatureObject goon = NGECore.getInstance().spawnService.spawnCreature("forage_goon", forager.getPlanet().getName(), 0L, wormiePosition.x, wormiePosition.y, wormiePosition.z, (short)level);
		// thief wookie criminal scavenger @forage_enemy:bark_thief
		String barkString = "";
	
		switch (goon.getTemplate()) {
		
		case "object/mobile/shared_dressed_criminal_assassin_human_female_01.iff":
				name = "A criminal";
				barkString = "bark_criminal";
				break;
				
		case "object/mobile/shared_dressed_criminal_assassin_human_male_01.iff":
				name = "A Scavenger";
				barkString = "bark_scavenger";
				break;
				
		case "object/mobile/shared_twilek_male.iff":
				name = "A Thief";
				barkString = "bark_thief";
				break;
				
		case "object/mobile/shared_dressed_binayre_pirate_zabrak_male_01.iff":
				name = "A Pirate";
				barkString = "bark_thief";
				break;
				
		case "object/mobile/shared_dressed_borvos_thug.iff":
				name = "A Thug";
				barkString = "bark_criminal";
				break;
				
		case "object/mobile/shared_wookiee_male.iff":
				name = "A Thug";
				barkString = "bark_wookiee";
				break;
				
		}
		
		goon.addObserver(forager);
		goon.setCustomName(name);
		goon.setLevel(forager.getLevel());
		goon.setOptions(Options.AGGRESSIVE, true);
		goon.setOptions(Options.ATTACKABLE, true);
		AIActor actor = (AIActor) goon.getAttachment("AI");
		actor.addDefender(forager);
		forager.sendSystemMessage("@foraging/forage_enemy:" + barkString, DisplayType.Broadcast);
		//OutOfBand oob = OutOfBand.ProsePackage("@foraging/forage_enemy:" + barkString));
		OutOfBand oob = OutOfBand.ProsePackage("@jawa_trader:cant_understand");
		forager.sendSystemMessage("@jawa_trader:cant_understand", DisplayType.Broadcast);
		NGECore.getInstance().chatService.spatialChat(goon, forager, "", (short)0x0, (short)0x0, 1, oob);
	}
	
	public void handleGuardSpawn(CreatureObject owner, TangibleObject map){
		System.err.println("handleGuardSpawn");
		short spawnLevel = (short) map.getAttachment("MapLevel");
		//Point3D exactTreasureLocation = (Point3D) map.getAttachment("MapExactLocation");
		Point3D exactTreasureLocation = owner.getPosition();
		
		GroupObject extractorGroup = null;
		if (owner.getGroupId()!=0)
			extractorGroup = (GroupObject) NGECore.getInstance().objectService.getObject(owner.getGroupId());
		
		int extractorGroupSize = 1;
		if (extractorGroup!=null)
			extractorGroupSize = extractorGroup.getMemberList().size();
		
		int guardSpawnNumber = 2 + extractorGroupSize;
		Vector<CreatureObject> guardList = new Vector<CreatureObject>();
		for (int i=0;i<guardSpawnNumber;i++){
			guardList.add(spawnGuard(exactTreasureLocation,owner,map,(short)(spawnLevel+2-80)));
		}
		
		//spawn boss if group size is sufficient
		if (extractorGroupSize==8){
			int bossRoll = new Random().nextInt(100);
			if (bossRoll<70){
				guardList.add(spawnBoss(exactTreasureLocation,owner,map,(short)(spawnLevel+2)));
				owner.sendSystemMessage("Spawn Boss",(byte)0);
			}
		}

		// spawn treasure container
		TangibleObject treasureContainer = (TangibleObject) NGECore.getInstance().staticService.spawnObject("object/tangible/container/drum/shared_treasure_drum.iff", owner.getPlanet().getName(), 0L, exactTreasureLocation.x, exactTreasureLocation.y, exactTreasureLocation.z, 0.70F, 0.71F);	
				
//		CreatureObject treasureContainer = (CreatureObject) NGECore.getInstance().objectService.createObject("object/tangible/container/drum/shared_treasure_drum.iff", 0, owner.getPlanet(), exactTreasureLocation, owner.getOrientation());
//		NGECore.getInstance().simulationService.add(treasureContainer, exactTreasureLocation.x, exactTreasureLocation.z, true);
		
		treasureContainer.setAttachment("radial_filename", "object/treasureContainer");
		treasureContainer.setAttachment("TreasureExtractorID", owner.getObjectID());
		treasureContainer.setAttachment("TreasureGuards",guardList);
//		treasureContainer.setAttachment("ChestLevel",new Integer(spawnLevel));
		configureTreasureLoot(treasureContainer,owner,spawnLevel);
		NGECore.getInstance().lootService.DropLoot(owner, treasureContainer);
		
		owner.sendSystemMessage("@treasure_map/treasure_map:sys_time_limit",(byte)0);		
		
		Thread guardsThread = new Thread() {
		    public void run() {
		        try {
		        	while (countAliveGuards(treasureContainer)>0){
		        		Thread.sleep(1000);
		        		if (owner.getPosture()==14)
		        			Thread.currentThread().interrupt();
		        	}		        	
		        	treasureContainer.setAttachment("radial_filename", "object/treasureContainer");
		        	owner.sendSystemMessage("@treasure_map/treasure_map:unlock_chest",(byte)0);
		        	Thread.currentThread().interrupt();
		        	
		        } catch(InterruptedException va) {
		            System.out.println(va);
		            Thread.currentThread().interrupt(); // very important
		        }
		    }  
		};
		guardsThread.start();
		
		// all down: owner.sendSystemMessage("@treasure_map/treasure_map:unlock_chest",(byte)0);		
	}
	
	public CreatureObject spawnGuard(Point3D exactTreasureLocation, CreatureObject owner, TangibleObject map, short spawnLevel){
		
		String name = "";	
//		System.out.println("exactTreasureLocation.x + " +exactTreasureLocation.x);
//		System.out.println("exactTreasureLocation.y + " +exactTreasureLocation.y);
//		System.out.println("exactTreasureLocation.z + " +exactTreasureLocation.z);
			
		Point3D guardPosition = SpawnPoint.getRandomPosition(exactTreasureLocation, 5, 12, owner.getPlanetId());
		CreatureObject guard = NGECore.getInstance().spawnService.spawnCreature("treasure_guard", owner.getPlanet().getName(), 0L, guardPosition.x, guardPosition.y, guardPosition.z, spawnLevel);
		
		String barkString = "";
		name = "Treasure Protector";
		
		guard.addObserver(owner); // ToDo: add any players in aggro range!!!
		guard.setCustomName(name);
		guard.setLevel(owner.getLevel());
		guard.setOptions(Options.AGGRESSIVE, true);
		guard.setOptions(Options.ATTACKABLE, true);
		AIActor actor = (AIActor) guard.getAttachment("AI");
		actor.addDefender(owner);
		
		barkString = "bark_"+map.getAttachment("MapSTFName");										      		
		owner.sendSystemMessage("@treasure_map/treasure_map:" + barkString, DisplayType.Broadcast);
		OutOfBand oob = OutOfBand.ProsePackage("@treasure_map/treasure_map:" + barkString);	
		NGECore.getInstance().chatService.spatialChat(guard, owner, "", (short)0x0, (short)0x0, 1, oob);
		return guard;
	}
	
	public CreatureObject spawnBoss(Point3D exactTreasureLocation, CreatureObject owner, TangibleObject map, short spawnLevel){
		
		//Point3D treasureLocation = resources.common.SpawnPoint.getRandomPosition(exactTreasureLocation, 1, 10, owner.getPlanetId());
		return new CreatureObject(); // FIXME on paper this looks bad
	}
	
	@SuppressWarnings("unchecked")
	public int countAliveGuards(SWGObject treasureContainer){
		Vector<CreatureObject> guardList = (Vector<CreatureObject>) treasureContainer.getAttachment("TreasureGuards");
		int aliveCount = 0;
		for (CreatureObject guard:guardList){
			if (guard.getPosture()!=14 && guard.getPosture()!=15)
				aliveCount++;
		}
		return aliveCount;
	}
	
	
	
	public void configureTreasureLoot(TangibleObject treasureContainer, CreatureObject owner, short spawnLevel){
		@SuppressWarnings("unused") List<LootGroup> lootgroups = new ArrayList<LootGroup>();
		
		String levelRange = "61";

		if (spawnLevel>60 && spawnLevel<=70){
			levelRange = "61";
		}
		if (spawnLevel>70 && spawnLevel<=80){
			levelRange = "75";
		}
		if (spawnLevel>80 && spawnLevel<=90){
			levelRange = "85";
		}
		
		
		// PROFESSION BRACELETS  HOUSE FURNITURE  WEARABLE BACKPACKS
//		String[] lootPoolNames = new String[]{"profession_bracelets_"+levelRange,"house_furniture","wearable_backpacks"};
//		double[] lootPoolChances  = new double[]{60,30,10};
		String[] lootPoolNames = new String[]{"profession_bracelets_"+levelRange};
		double[] lootPoolChances  = new double[]{100};
		double lootGroupChance = 80;
		treasureContainer.addToLootGroups(lootPoolNames, lootPoolChances, lootGroupChance);
		
		// WEAPONSMITH COMPONENTS JEDI COMPONENTS GEM COLLECTION ITEMS TREASURE HUNTER COLLECTION ITEMS
		//lootPoolNames = new String[]{"weaponsmith_components","jedi_components","gem_collection_items","treasure_hunter_collection_items"};
		//lootPoolChances  = new double[]{15,25,30,30};
		lootPoolNames = new String[]{"jedi_components_treasure"};
		lootPoolChances  = new double[]{100};
		lootGroupChance = 80;
		treasureContainer.addToLootGroups(lootPoolNames, lootPoolChances, lootGroupChance);
		
		// COMMON LOOTS: 
//		Looted non-wearable Bounty Hunter armor pieces (Used to make Mandalorian armor at the Death Watch Bunker)
//		Looted clothing (usually 20+)
//		Looted armors (usually 20+)
//		Looted weapons (700-800 DPS(Sometimes less, now includes the Nak'tra Crystal Rifle))
//		Advanced Agility Stim (CL80)
//		Advanced Power Stim (CL80)
//		Loot kits adhesives
//		Various Schematics 
//	lootPoolNames = new String[]{"bh_armor_for_mando","advanced_stims_80","loot_kits_adhesives","various_schematics"};
//	lootPoolChances  = new double[]{15,25,30,30};
//	lootGroupChance = 80;
//	treasureContainer.addToLootGroups(lootPoolNames, lootPoolChances, lootGroupChance);
//	
		
		//GEM COLLECTION ITEMS  CONSUMABLE BUFF ITEMS
		
		// VERY RARE BUFF ITEMS
		lootPoolNames = new String[]{"rare_buff_items"};
		lootPoolChances  = new double[]{100};
		lootGroupChance = 0.1;
		treasureContainer.addToLootGroups(lootPoolNames, lootPoolChances, lootGroupChance);
		
		// EXTRA
		lootPoolNames = new String[]{"treasure_extra"};
		lootPoolChances  = new double[]{100};
		lootGroupChance = 50;
		treasureContainer.addToLootGroups(lootPoolNames, lootPoolChances, lootGroupChance);
		
	}

}
