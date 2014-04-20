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

import java.util.Random;

import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import main.NGECore;
import resources.datatables.Options;
import resources.objects.creature.CreatureObject;
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
	// consider foraged food food/foraged
	
	
	// tangible/item/treasure_map/shared_relic_buff_s01.iff
	
	public Forager(){
		
	}
	
	public boolean handleForageResults(CreatureObject forager){
		// determine foraged object
		SWGObject foragerInventory = forager.getSlottedObject("inventory");
		
		
		// you can use Kommerken Steak food which increases your 
		// foraging chance by 12% and gives you a 
		// 5% bonus chance of finding a treasure map. 
		
		int randomForageRoll       = new Random().nextInt(100);
		int randomForageObjectRoll = new Random().nextInt(100);
		int remainder = 0; 
		int forageResultChance = 50;
		int kommerkenBonus = 0;

		int[] forageChances  = new int[]{10,10,25,25,25,5}; 
		int[] forageChances2 = new int[]{12,10,21,23,24,10}; // Kommerken Steak
		int[] forageChances3 = new int[]{20,9,22,22,22,5};   // if you got the pig

		
		int chosenObject = 0;
		
//		if (forager.hasChuckedIn(KommerkenSteak)){
//			kommerkenBonus = 12;
//			forageChances = forageChances2;
//	}
		
//		if (forager.gotThePig(pig)){
//			forageChances = forageChances3;
//}
		forager.sendSystemMessage("randomForageRoll " + randomForageRoll,(byte) 0);
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

			forager.sendSystemMessage("chosenObject " + chosenObject,(byte) 0);
			switch (chosenObject) {
				
				case LYASE_ENZYME:    String template = "object/tangible/loot/beast/shared_enzyme_2.iff"; 
									  TangibleObject foragedObject = (TangibleObject) NGECore.getInstance().objectService.createObject(template, forager.getPlanet());
									  // determine color here
									  foragedObject.setCustomizationVariable("/private/index_color_1", (byte) new Random().nextInt(10)); // edb_vial.pal
									  foragerInventory.add(foragedObject);
									  forager.sendSystemMessage("",(byte) 0);
									  break;
									
				case RARE_COMPONENT:  template =  "object/tangible/loot/beast/shared_enzyme_1.iff"; // for now, must find temp
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
										      foragerInventory.add(foragedObject);
										      forager.sendSystemMessage("",(byte) 0);
										      break;
										      
				default: break;
				
			}
			
			if (chosenObject<6)
				forager.sendSystemMessage("@skl_use:sys_forage_success",(byte) 0);
			
			// spawn adversaries
			int adversaryRoll = new Random().nextInt(100);
			// A new buff is imaginable that would decrease the chance of running into adversaries here, just an idea
			
			if (chosenObject>6)
				adversaryRoll=999;
			
			if (adversaryRoll<14) {
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
		wormie.setCustomName2(name);
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
				barkString = "@forage_enemy:bark_criminal";
				break;
				
		case "object/mobile/shared_dressed_criminal_assassin_human_male_01.iff":
				name = "A Scavenger";
				barkString = "@forage_enemy:bark_scavenger";
				break;
				
		case "object/mobile/shared_twilek_male.iff":
				name = "A Thief";
				barkString = "@forage_enemy:bark_thief";
				break;
				
		case "object/mobile/shared_dressed_binayre_pirate_zabrak_male_01.iff":
				name = "A Pirate";
				barkString = "@forage_enemy:bark_thief";
				break;
				
		case "object/mobile/shared_dressed_borvos_thug.iff":
				name = "A Thug";
				barkString = "@forage_enemy:bark_criminal";
				break;
				
		case "object/mobile/shared_wookiee_male.iff":
				name = "A Thug";
				barkString = "@forage_enemy:bark_wookiee";
				break;
				
		}
		goon.setCustomName(name);
		goon.setCustomName2(name);
		goon.setLevel(forager.getLevel());
		goon.setOptions(Options.AGGRESSIVE, true);
		goon.setOptions(Options.ATTACKABLE, true);
		AIActor actor = (AIActor) goon.getAttachment("AI");
		actor.addDefender(forager);
		NGECore.getInstance().chatService.spatialChat(goon, forager, barkString, short chatType, short moodId, int languageId, OutOfBand outOfBand);
			long targetId;
	}

}
