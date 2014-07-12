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
package services;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import resources.common.Console;
import resources.common.FileUtilities;
import resources.common.Forager;
import resources.common.Opcodes;
import resources.common.SpawnPoint;
import resources.datatables.Posture;
import resources.datatables.WeaponType;
import resources.harvest.SurveyTool;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;
import services.sui.SUIWindow;
import services.sui.SUIService.ListBoxType;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import main.NGECore;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.container.CreatureContainerPermissions;
import engine.resources.container.CreaturePermissions;
import engine.resources.container.NullPermissions;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

@SuppressWarnings("unused")
public class DevService implements INetworkDispatch {
	
	private NGECore core;

	public DevService(NGECore core) {
		this.core = core;
	}
	
	public void sendCharacterBuilderSUI(CreatureObject creature, int childMenu) 
	{
		Map<Long, String> suiOptions = new HashMap<Long, String>();

		switch(childMenu)
		{
			case 0: // Root
				suiOptions.put((long) 1, "Character");
				suiOptions.put((long) 2, "Items");
				suiOptions.put((long) 3, "Locations");
				if (System.getProperty("user.name").equals("Charon"))
					suiOptions.put((long) 4, "Treasure chest test");
				break;
			case 1: // Character
				suiOptions.put((long) 10, "Set combat level to 90");
				suiOptions.put((long) 11, "Give 100,000 credits");
				break;
			case 2: // Items
				suiOptions.put((long) 20, "Armor");
				suiOptions.put((long) 21, "Weapons");
				suiOptions.put((long) 22, "Misc Items");
				suiOptions.put((long) 23, "Jedi Items");
				suiOptions.put((long) 26, "Installations");
				suiOptions.put((long) 110, "Survey Devices");
				if(creature.getPlayerObject().getProfession().equals("bounty_hunter_1a")) suiOptions.put((long) 123, "Tracking Droids");
				if (creature.getClient().isGM()) {
					suiOptions.put((long) 120, "House Deeds");
					suiOptions.put((long) 125, "Crafting Tools");
					suiOptions.put((long) 130, "Vehicle Deeds");
					suiOptions.put((long) 131, "Pet Deeds");
					suiOptions.put((long) 121, "Sandbox City");
					if(creature.getPlayerObject().getProfession().equals("trader_0a") || creature.getPlayerObject().getProfession().equals("trader_0b") || creature.getPlayerObject().getProfession().equals("trader_0c") || creature.getPlayerObject().getProfession().equals("trader_0d"))
						suiOptions.put((long) 177, "REing");
				}

				break;
			case 3: // [Items] Weapons
				suiOptions.put((long) 30, "Jedi Weapons");
				suiOptions.put((long) 31, "Melee Weapons");
				suiOptions.put((long) 32, "Ranged Weapons");
				suiOptions.put((long) 33, "Heavy Weapons");
				break;
			case 4: // [Items] Misc Items
				suiOptions.put((long) 40, "Unity Ring");
				suiOptions.put((long) 41, "Tusken Rucksack");
				suiOptions.put((long) 42, "Heroism Jewlery Set");
				suiOptions.put((long) 43, "Breath of Heaven");
				suiOptions.put((long) 140, "Guild Registry Device (PDA)");
				break;
			case 5: // [Items] Armor
				suiOptions.put((long) 50, "Assault Armor");
				suiOptions.put((long) 51, "Battle Armor");
				suiOptions.put((long) 52, "Reconnaissance Armor");
				break;
			case 6: // [Items] Assault Armor
				suiOptions.put((long) 60, "Composite");
				suiOptions.put((long) 61, "Ithorian Sentinel");
				suiOptions.put((long) 62, "Kashyyykian Hunting");
				break;
			case 7: // [Items] Battle Armor
				suiOptions.put((long) 70, "Bone");
				suiOptions.put((long) 71, "Ithorian Defender");
				suiOptions.put((long) 72, "Kashyyykian Black Mountain");
				break;
			case 8: // [Items] Reconnaissance Armor
				suiOptions.put((long) 80, "Marauder");
				suiOptions.put((long) 81, "Ithorian Guardian");
				suiOptions.put((long) 82, "Kashyyykian Ceremonial");
				break;
			case 9: // [Items] Jedi Items
				suiOptions.put((long) 90, "(Light) Jedi Robe");
				suiOptions.put((long) 91, "(Dark) Jedi Robe");
				suiOptions.put((long) 92, "Belt of Master Bodo Baas");
				suiOptions.put((long) 93, "Lightsaber Crystals");
				break;
			case 10: // [Items] Jedi Items
				suiOptions.put((long) 111, "Harvesters");
				suiOptions.put((long) 112, "Energy resources");
				break;
			case 11: // Locations
				suiOptions.put((long) 122, "Teleport to Jedi Ruins");
				suiOptions.put((long) 124, "Teleport to Mos Eisley");
				if (creature.getClient().isGM()) {
					suiOptions.put((long) 164, "Teleport to Mining Outpost");
					suiOptions.put((long) 165, "Teleport to Spawn Area #1");
					suiOptions.put((long) 166, "Teleport to Imperial Op");
					suiOptions.put((long) 167, "Teleport to Rebel Base ");
					suiOptions.put((long) 168, "Teleport to NS vs SMC Battle");
					suiOptions.put((long) 169, "Teleport to NS Stronghold");
					suiOptions.put((long) 153, "Teleport to Force Crystal Hunter's Cave");
					suiOptions.put((long) 121, "Teleport to Sandbox City");	
				}
				break;
		
		}
		
		final SUIWindow window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "Character Builder Terminal", "Select the desired option and click OK.", suiOptions, creature, null, 10);
		Vector<String> returnList = new Vector<String>();
		
		returnList.add("List.lstList:SelectedRow");
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() 
		{
			public void process(SWGObject owner, int eventType, Vector<String> returnList) 
			{
				int index = Integer.parseInt(returnList.get(0));
				int childIndex = (int) window.getObjectIdByIndex(index);
				
				CreatureObject player = (CreatureObject) owner;		
				SWGObject inventory = player.getSlottedObject("inventory");
				Planet planet = player.getPlanet();
				
				switch(childIndex)
				{
					// Root
					case 1: // Character
						sendCharacterBuilderSUI(player, 1);
						return;
					case 2: // Items
						sendCharacterBuilderSUI(player, 2);
						return; 
					case 3: // Locations
						sendCharacterBuilderSUI(player, 11);
						return; 
						
					case 4: // Test
						TangibleObject treasureContainer = (TangibleObject) NGECore.getInstance().staticService.spawnObject("object/tangible/container/drum/shared_treasure_drum.iff", 
								owner.getPlanet().getName(), 0L, owner.getWorldPosition().x, owner.getWorldPosition().y, owner.getWorldPosition().z, 0.70F, 0.71F);						
						treasureContainer.setAttachment("radial_filename", "object/treasureContainer");
						
						//treasureContainer.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS); 
						treasureContainer.setAttachment("TreasureExtractorID", owner.getObjectID());
						treasureContainer.add(core.objectService.createObject("object/tangible/inventory/shared_character_inventory.iff", treasureContainer.getPlanet()));
						String template = "object/tangible/wearables/bracelet/shared_bracelet_s02_r.iff";
						TangibleObject droppedItem = (TangibleObject) core.objectService.createObject(template, planet);
//						SWGObject lootedObjectInventory = treasureContainer.getSlottedObject("inventory");
//						lootedObjectInventory.add(droppedItem);
//						
						Forager forager = new Forager();
						forager.configureTreasureLoot(treasureContainer,(CreatureObject)owner,(short)90);
						NGECore.getInstance().lootService.DropLoot((CreatureObject)owner, treasureContainer);
						
						//treasureContainer.add(droppedItem);
						return;
					
					// Character
					case 10: // Set combat level to 90
						core.playerService.grantLevel(player, 90); // Commented out until fixed
					    //core.playerService.giveExperience(player, 999999999);
						return;
					case 11: // Give 100,000 credits
						player.setCashCredits(player.getCashCredits() + 100000);
						return;
					
					// Items
					case 20: // Armor
						sendCharacterBuilderSUI(player, 5);
						return;
					case 21: // Weapons
						sendCharacterBuilderSUI(player, 3);
						return;
					case 22: // Misc Items
						sendCharacterBuilderSUI(player, 4);
						return;
					case 23: // Jedi Items
						sendCharacterBuilderSUI(player, 9);
						return;
					case 25: // Tools
						sendCharacterBuilderSUI(player, 15);
						return;	
					case 26: // Installations
						sendCharacterBuilderSUI(player, 10);
						return;
						
					// [Items] Weapons
					case 30: // Jedi Weapons
						WeaponObject lightsaber1 = (WeaponObject) core.objectService.createObject("object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff", planet);
						lightsaber1.setIntAttribute("required_combat_level", 90);
						lightsaber1.setStringAttribute("class_required", "Jedi");
						lightsaber1.setAttackSpeed(1);
						lightsaber1.setDamageType("energy");
						lightsaber1.setMaxRange(5);
						lightsaber1.setMinDamage(689);
						lightsaber1.setMaxDamage(1379);
						lightsaber1.setWeaponType(WeaponType.ONEHANDEDSABER);
						
						WeaponObject lightsaber2 = (WeaponObject) core.objectService.createObject("object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff", planet);
						lightsaber2.setIntAttribute("required_combat_level", 90);
						lightsaber2.setStringAttribute("class_required", "Jedi");
						lightsaber2.setAttackSpeed(1);
						lightsaber2.setDamageType("energy");
						lightsaber2.setMaxRange(5);
						lightsaber2.setMinDamage(689);
						lightsaber2.setMaxDamage(1379);
						lightsaber2.setWeaponType(WeaponType.TWOHANDEDSABER);
						
						WeaponObject lightsaber3 = (WeaponObject) core.objectService.createObject("object/weapon/melee/polearm/crafted_saber/shared_sword_lightsaber_polearm_gen5.iff", planet);
						lightsaber3.setIntAttribute("required_combat_level", 90);
						lightsaber3.setStringAttribute("class_required", "Jedi");
						lightsaber3.setAttackSpeed(1);
						lightsaber3.setDamageType("energy");
						lightsaber3.setMaxRange(5);
						lightsaber3.setMinDamage(689);
						lightsaber3.setMaxDamage(1379);
						lightsaber3.setWeaponType(WeaponType.POLEARMSABER);
						
						Random random = new Random();
						
						lightsaber1.setCustomizationVariable("/private/index_color_blade", (byte) random.nextInt(47));
						lightsaber2.setCustomizationVariable("/private/index_color_blade", (byte) random.nextInt(47));
						lightsaber3.setCustomizationVariable("/private/index_color_blade", (byte) random.nextInt(47));
						
						inventory.add(lightsaber1);
						inventory.add(lightsaber2);
						inventory.add(lightsaber3);
						return;
					case 31: // Melee Weapons
						WeaponObject sword1 = (WeaponObject) core.objectService.createObject("object/weapon/melee/sword/shared_sword_01.iff", planet);
						sword1.setIntAttribute("required_combat_level", 90);
						sword1.setAttackSpeed(1);
						sword1.setMaxRange(5);
						sword1.setDamageType("kinetic");
						sword1.setMinDamage(1100);
						sword1.setMaxDamage(1200);
						sword1.setWeaponType(WeaponType.ONEHANDEDMELEE);
						
						inventory.add(sword1);
						return;
					case 32: // Ranged Weapons
						WeaponObject rifle1 = (WeaponObject) core.objectService.createObject("object/weapon/ranged/rifle/shared_rifle_e11.iff", planet);
						rifle1.setIntAttribute("required_combat_level", 90);
						rifle1.setAttackSpeed((float) 0.8);
						rifle1.setMaxRange(64);
						rifle1.setDamageType("energy");
						rifle1.setMinDamage(800);
						rifle1.setMaxDamage(1250);
						rifle1.setWeaponType(WeaponType.RIFLE);
						
						inventory.add(rifle1);
						
						WeaponObject carbine1 = (WeaponObject) core.objectService.createObject("object/weapon/ranged/carbine/shared_carbine_cdef.iff", planet);
						carbine1.setIntAttribute("required_combat_level", 90);
						carbine1.setAttackSpeed((float) 0.6);
						carbine1.setMaxRange(50);
						carbine1.setDamageType("energy");
						carbine1.setMinDamage(600);
						carbine1.setMaxDamage(937);
						carbine1.setWeaponType(WeaponType.CARBINE);
						
						inventory.add(carbine1);
						
						WeaponObject pistol = (WeaponObject) core.objectService.createObject("object/weapon/ranged/pistol/shared_pistol_cdef.iff", planet);
						pistol.setIntAttribute("required_combat_level", 90);
						pistol.setAttackSpeed((float) 0.4);
						pistol.setMaxRange(35);
						pistol.setDamageType("energy");
						pistol.setMinDamage(400);
						pistol.setMaxDamage(625);
						pistol.setWeaponType(WeaponType.PISTOL);
						
						inventory.add(pistol);
						return;
						
					case 33:
						WeaponObject heavy1 = (WeaponObject) core.objectService.createObject("object/weapon/ranged/heavy/shared_som_lava_cannon_generic.iff", planet);
						heavy1.setIntAttribute("required_combat_level", 90);
						
						heavy1.setStringAttribute("class_required", "Commando");
						heavy1.setDamageType("energy");
						heavy1.setMinDamage(700);
						heavy1.setMaxDamage(1400);
						heavy1.setMaxRange(64);
						heavy1.setAttackSpeed(1);
						heavy1.setElementalType("heat");
						heavy1.setElementalDamage(200);
						heavy1.setWeaponType(WeaponType.HEAVYWEAPON);
						
						inventory.add(heavy1);
						
						WeaponObject heavy2 = (WeaponObject) core.objectService.createObject("object/weapon/ranged/heavy/shared_som_republic_flamer.iff", planet);
						heavy2.setIntAttribute("required_combat_level", 88);
						
						heavy2.setStringAttribute("class_required", "Commando");
						heavy2.setDamageType("energy");
						heavy2.setMinDamage(148);
						heavy2.setMaxDamage(295);
						heavy2.setMaxRange(20);
						heavy2.setAttackSpeed((float) 0.25);
						heavy2.setElementalType("heat");
						heavy2.setElementalDamage(28);
						heavy2.setWeaponType(WeaponType.FLAMETHROWER);
						
						inventory.add(heavy2);
						return;
					case 40:
						TangibleObject ring = (TangibleObject) core.objectService.createObject("object/tangible/wearables/ring/shared_ring_s01.iff", planet);
						ring.setCustomName("Unity Ring");
						inventory.add(ring);
					case 41:
						TangibleObject backpack = (TangibleObject) core.objectService.createObject("object/tangible/wearables/backpack/shared_backpack_krayt_skull.iff", planet);
						backpack.setIntAttribute("cat_stat_mod_bonus.@stat_n:agility_modified", 25);
						backpack.setIntAttribute("cat_stat_mod_bonus.@stat_n:constitution_modified", 30);
						backpack.setIntAttribute("cat_stat_mod_bonus.@stat_n:luck_modified", 25);
						backpack.setIntAttribute("cat_stat_mod_bonus.@stat_n:precision_modified", 35);
						backpack.setIntAttribute("cat_stat_mod_bonus.@stat_n:stamina_modified", 30);
						backpack.setIntAttribute("cat_stat_mod_bonus.@stat_n:strength_modified", 35);
						inventory.add(backpack);
						return;
					case 42:
						TangibleObject heroismBand = (TangibleObject) core.objectService.createObject("object/tangible/wearables/ring/shared_ring_s04.iff", planet, "item_band_set_hero_01_01");
						
						TangibleObject heroismRing = (TangibleObject) core.objectService.createObject("object/tangible/wearables/ring/shared_ring_s02.iff", planet, "item_ring_set_hero_01_01");
						
						TangibleObject heroismNecklace = (TangibleObject) core.objectService.createObject("object/tangible/wearables/necklace/shared_necklace_s10.iff", planet, "item_necklace_set_hero_01_01");
						
						TangibleObject heroismBraceletRight = (TangibleObject) core.objectService.createObject("object/tangible/wearables/bracelet/shared_bracelet_s03_r.iff", planet, "item_bracelet_r_set_hero_01_01");
						
						TangibleObject heroismBraceletLeft = (TangibleObject) core.objectService.createObject("object/tangible/wearables/bracelet/shared_bracelet_s03_l.iff", planet, "item_bracelet_l_set_hero_01_01");
						
						inventory.add(heroismBand);
						inventory.add(heroismRing);
						inventory.add(heroismNecklace);
						inventory.add(heroismBraceletRight);
						inventory.add(heroismBraceletLeft);
						return;
					case 43:
						TangibleObject drink = (TangibleObject) core.objectService.createObject("object/tangible/food/crafted/shared_drink_breath_of_heaven.iff", planet);
						drink.setFloatAttribute("cat_stat_mod_bonus.@stat_n:constitution_modified", 80);
						drink.setFloatAttribute("cat_stat_mod_bonus.@stat_n:dodge", 3);
						inventory.add(drink);
						return;
					case 50: // [Items] Assault Armor
						sendCharacterBuilderSUI(player, 6);
						return;
					case 51: // [Items] Battle Armor
						sendCharacterBuilderSUI(player, 7);
						return;
					case 52: // [Items] Reconnaissance Armor
						sendCharacterBuilderSUI(player, 8);
						return;
					case 60: // Composite Armor
						SWGObject comp_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_r.iff", planet);
						comp_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						comp_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						comp_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						comp_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						comp_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						comp_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject comp_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_l.iff", planet);
						comp_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						comp_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						comp_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						comp_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						comp_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						comp_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject comp_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bracer_r.iff", planet);
						comp_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						comp_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						comp_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						comp_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						comp_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						comp_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject comp_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bracer_l.iff", planet);
						comp_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						comp_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						comp_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						comp_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						comp_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						comp_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject comp_leggings = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_leggings.iff", planet);
						comp_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						comp_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						comp_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						comp_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						comp_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						comp_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject comp_helmet = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_helmet.iff", planet);
						comp_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						comp_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						comp_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						comp_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						comp_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						comp_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject comp_chest = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_chest_plate.iff", planet);
						comp_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						comp_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						comp_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						comp_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						comp_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						comp_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject comp_boots = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_boots.iff", planet);
						SWGObject comp_gloves = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_gloves.iff", planet);
						
						
						
						inventory.add(comp_bicep_r);
						inventory.add(comp_bicep_l);
						inventory.add(comp_bracer_r);
						inventory.add(comp_bracer_l);
						inventory.add(comp_leggings);
						inventory.add(comp_helmet);
						inventory.add(comp_chest);
						inventory.add(comp_boots);
						inventory.add(comp_gloves);

						
						return;
					case 61: // Ithorian Sentinel
						SWGObject sent_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_bicep_r.iff", planet);
						sent_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						sent_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						sent_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						sent_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						sent_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						sent_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject sent_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_bicep_l.iff", planet);
						sent_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						sent_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						sent_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						sent_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						sent_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						sent_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject sent_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_bracer_r.iff", planet);
						sent_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						sent_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						sent_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						sent_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						sent_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						sent_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject sent_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_bracer_l.iff", planet);
						sent_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						sent_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						sent_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						sent_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						sent_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						sent_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject sent_leggings = core.objectService.createObject("object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_leggings.iff", planet);
						sent_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						sent_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						sent_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						sent_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						sent_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						sent_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject sent_helmet = core.objectService.createObject("object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_helmet.iff", planet);
						sent_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						sent_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						sent_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						sent_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						sent_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						sent_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject sent_chest = core.objectService.createObject("object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_chest_plate.iff", planet);
						sent_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						sent_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						sent_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						sent_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						sent_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						sent_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject sent_boots = core.objectService.createObject("object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_boots.iff", planet);
						SWGObject sent_gloves = core.objectService.createObject("object/tangible/wearables/armor/ithorian_sentinel/shared_ith_armor_s03_gloves.iff", planet);
						
						
						
						inventory.add(sent_bicep_r);
						inventory.add(sent_bicep_l);
						inventory.add(sent_bracer_r);
						inventory.add(sent_bracer_l);
						inventory.add(sent_leggings);
						inventory.add(sent_helmet);
						inventory.add(sent_chest);
						inventory.add(sent_boots);
						inventory.add(sent_gloves);

						
						return;
					case 62: // Kashyyykian Hunting
						SWGObject hunt_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bicep_r.iff", planet);
						hunt_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						hunt_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						hunt_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						hunt_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						hunt_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						hunt_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject hunt_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bicep_l.iff", planet);
						hunt_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						hunt_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						hunt_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						hunt_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						hunt_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						hunt_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject hunt_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bracer_r.iff", planet);
						hunt_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						hunt_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						hunt_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						hunt_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						hunt_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						hunt_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject hunt_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_bracer_l.iff", planet);
						hunt_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						hunt_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						hunt_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						hunt_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						hunt_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						hunt_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject hunt_leggings = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_leggings.iff", planet);
						hunt_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						hunt_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						hunt_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						hunt_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						hunt_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						hunt_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject hunt_chest = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_hunting/shared_armor_kashyyykian_hunting_chestplate.iff", planet);
						hunt_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						hunt_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						hunt_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						hunt_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						hunt_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						hunt_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						
						
						inventory.add(hunt_bicep_r);
						inventory.add(hunt_bicep_l);
						inventory.add(hunt_bracer_r);
						inventory.add(hunt_bracer_l);
						inventory.add(hunt_leggings);
						inventory.add(hunt_chest);

						
						return;
					case 70: // Bone Armor
						SWGObject bone_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_bicep_r.iff", planet);
						bone_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						bone_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						bone_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bone_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bone_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bone_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bone_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_bicep_l.iff", planet);
						bone_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						bone_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						bone_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bone_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bone_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bone_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bone_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_bracer_r.iff", planet);
						bone_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						bone_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						bone_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bone_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bone_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bone_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bone_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_bracer_l.iff", planet);
						bone_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						bone_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						bone_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bone_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bone_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bone_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bone_leggings = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_leggings.iff", planet);
						bone_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						bone_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						bone_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bone_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bone_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bone_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bone_helmet = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_helmet.iff", planet);
						bone_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						bone_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						bone_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bone_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bone_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bone_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bone_chest = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_chest_plate.iff", planet);
						bone_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						bone_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						bone_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bone_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bone_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bone_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bone_boots = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_boots.iff", planet);
						SWGObject bone_gloves = core.objectService.createObject("object/tangible/wearables/armor/bone/shared_armor_bone_s01_gloves.iff", planet);
						
						
						
						inventory.add(bone_bicep_r);
						inventory.add(bone_bicep_l);
						inventory.add(bone_bracer_r);
						inventory.add(bone_bracer_l);
						inventory.add(bone_leggings);
						inventory.add(bone_helmet);
						inventory.add(bone_chest);
						inventory.add(bone_boots);
						inventory.add(bone_gloves);

						
						return;
					case 71: // Ithorian Defender
						SWGObject def_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_bicep_r.iff", planet);
						def_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						def_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						def_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						def_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						def_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						def_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject def_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_bicep_l.iff", planet);
						def_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						def_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						def_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						def_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						def_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						def_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject def_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_bracer_r.iff", planet);
						def_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						def_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						def_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						def_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						def_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						def_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject def_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_bracer_l.iff", planet);
						def_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						def_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						def_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						def_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						def_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						def_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject def_leggings = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_leggings.iff", planet);
						def_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						def_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						def_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						def_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						def_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						def_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject def_helmet = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_helmet.iff", planet);
						def_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						def_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						def_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						def_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						def_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						def_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject def_chest = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_chest_plate.iff", planet);
						def_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						def_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						def_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						def_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						def_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						def_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject def_boots = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_boots.iff", planet);
						SWGObject def_gloves = core.objectService.createObject("object/tangible/wearables/armor/ithorian_defender/shared_ith_armor_s01_gloves.iff", planet);
						
						
						
						inventory.add(def_bicep_r);
						inventory.add(def_bicep_l);
						inventory.add(def_bracer_r);
						inventory.add(def_bracer_l);
						inventory.add(def_leggings);
						inventory.add(def_helmet);
						inventory.add(def_chest);
						inventory.add(def_boots);
						inventory.add(def_gloves);

						
						return;
					case 72: // Kashyyykian Black Mountain
						SWGObject moun_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_bicep_r.iff", planet);
						moun_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						moun_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						moun_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						moun_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						moun_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						moun_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject moun_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_bicep_l.iff", planet);
						moun_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						moun_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						moun_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						moun_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						moun_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						moun_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject moun_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_bracer_r.iff", planet);
						moun_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						moun_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						moun_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						moun_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						moun_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						moun_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject moun_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_bracer_l.iff", planet);
						moun_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						moun_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						moun_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						moun_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						moun_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						moun_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject moun_leggings = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_leggings.iff", planet);
						moun_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						moun_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						moun_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						moun_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						moun_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						moun_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject moun_chest = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_black_mtn/shared_armor_kashyyykian_black_mtn_chestplate.iff", planet);
						moun_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 6000);
						moun_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 6000);
						moun_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						moun_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						moun_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						moun_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						
						
						inventory.add(moun_bicep_r);
						inventory.add(moun_bicep_l);
						inventory.add(moun_bracer_r);
						inventory.add(moun_bracer_l);
						inventory.add(moun_leggings);
						inventory.add(moun_chest);

						
						return;
					case 80: // Marauder Armor
						SWGObject mar_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_bicep_r.iff", planet);
						mar_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						mar_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						mar_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						mar_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						mar_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						mar_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject mar_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_bicep_l.iff", planet);
						mar_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						mar_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						mar_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						mar_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						mar_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						mar_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject mar_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_bracer_r.iff", planet);
						mar_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						mar_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						mar_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						mar_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						mar_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						mar_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject mar_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_bracer_l.iff", planet);
						mar_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						mar_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						mar_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						mar_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						mar_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						mar_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject mar_leggings = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_leggings.iff", planet);
						mar_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						mar_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						mar_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						mar_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						mar_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						mar_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject mar_helmet = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_helmet.iff", planet);
						mar_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						mar_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						mar_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						mar_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						mar_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						mar_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject mar_chest = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_chest_plate.iff", planet);
						mar_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						mar_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						mar_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						mar_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						mar_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						mar_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject mar_boots = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_boots.iff", planet);
						SWGObject mar_gloves = core.objectService.createObject("object/tangible/wearables/armor/marauder/shared_armor_marauder_s02_gloves.iff", planet);
						
						
						
						inventory.add(mar_bicep_r);
						inventory.add(mar_bicep_l);
						inventory.add(mar_bracer_r);
						inventory.add(mar_bracer_l);
						inventory.add(mar_leggings);
						inventory.add(mar_helmet);
						inventory.add(mar_chest);
						inventory.add(mar_boots);
						inventory.add(mar_gloves);

						
						return;
					case 81: // Ithorian Guardian
						SWGObject gau_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/ithorian_guardian/shared_ith_armor_s02_bicep_r.iff", planet);
						gau_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						gau_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						gau_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						gau_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						gau_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						gau_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject gau_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/ithorian_guardian/shared_ith_armor_s02_bicep_l.iff", planet);
						gau_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						gau_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						gau_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						gau_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						gau_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						gau_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject gau_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/ithorian_guardian/shared_ith_armor_s02_bracer_r.iff", planet);
						gau_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						gau_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						gau_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						gau_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						gau_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						gau_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject gau_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/ithorian_guardian/shared_ith_armor_s02_bracer_l.iff", planet);
						gau_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						gau_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						gau_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						gau_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						gau_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						gau_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject gau_leggings = core.objectService.createObject("object/tangible/wearables/armor/ithorian_guardian/shared_ith_armor_s02_leggings.iff", planet);
						gau_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						gau_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						gau_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						gau_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						gau_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						gau_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject gau_helmet = core.objectService.createObject("object/tangible/wearables/armor/ithorian_guardian/shared_ith_armor_s02_helmet.iff", planet);
						gau_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						gau_helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						gau_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						gau_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						gau_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						gau_helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject gau_chest = core.objectService.createObject("object/tangible/wearables/armor/ithorian_guardian/shared_ith_armor_s02_chest_plate.iff", planet);
						gau_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						gau_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						gau_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						gau_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						gau_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						gau_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject gau_boots = core.objectService.createObject("object/tangible/wearables/armor/ithorian_guardian/shared_ith_armor_s02_boots.iff", planet);
						SWGObject gau_gloves = core.objectService.createObject("object/tangible/wearables/armor/ithorian_guardian/shared_ith_armor_s02_gloves.iff", planet);
						
						
						
						inventory.add(gau_bicep_r);
						inventory.add(gau_bicep_l);
						inventory.add(gau_bracer_r);
						inventory.add(gau_bracer_l);
						inventory.add(gau_leggings);
						inventory.add(gau_helmet);
						inventory.add(gau_chest);
						inventory.add(gau_boots);
						inventory.add(gau_gloves);

						
						return;
					case 82: // Kashyyykian Ceremonial
						SWGObject cer_bicep_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_bicep_r.iff", planet);
						cer_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						cer_bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						cer_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						cer_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						cer_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						cer_bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject cer_bicep_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_bicep_l.iff", planet);
						cer_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						cer_bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						cer_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						cer_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						cer_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						cer_bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject cer_bracer_r = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_bracer_r.iff", planet);
						cer_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						cer_bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						cer_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						cer_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						cer_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						cer_bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject cer_bracer_l = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_bracer_l.iff", planet);
						cer_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						cer_bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						cer_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						cer_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						cer_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						cer_bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject cer_leggings = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_leggings.iff", planet);
						cer_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						cer_leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						cer_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						cer_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						cer_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						cer_leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject cer_chest = core.objectService.createObject("object/tangible/wearables/armor/kashyyykian_ceremonial/shared_armor_kashyyykian_ceremonial_chestplate.iff", planet);
						cer_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 5000);
						cer_chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 7000);
						cer_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						cer_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						cer_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						cer_chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);				
						
						
						
						inventory.add(cer_bicep_r);
						inventory.add(cer_bicep_l);
						inventory.add(cer_bracer_r);
						inventory.add(cer_bracer_l);
						inventory.add(cer_leggings);
						inventory.add(cer_chest);
						
						return;
						
					case 90: // (Light) Jedi Robe
						inventory.add(core.objectService.createObject("object/tangible/wearables/robe/shared_robe_jedi_light_s03.iff", planet));
						return;
					case 91: // (Dark) Jedi Robe
						inventory.add(core.objectService.createObject("object/tangible/wearables/robe/shared_robe_jedi_dark_s03.iff", planet));
						return;
					case 92: // Belt of Master Bodo Baas
						inventory.add(core.objectService.createObject("object/tangible/wearables/backpack/shared_fannypack_s01.iff", planet));
						return;
					case 93: // Lightsaber Crystals
						TangibleObject kraytPearl = (TangibleObject) core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", planet);
						kraytPearl.setAttachment("LootItemName", "kraytpearl_flawless");
						core.lootService.handleSpecialItems(kraytPearl, "kraytpearl");	
						inventory.add(kraytPearl);
						
						TangibleObject kraytPearl2 = (TangibleObject) core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", planet);
						kraytPearl2.setAttachment("LootItemName", "kraytpearl_flawless");
						core.lootService.handleSpecialItems(kraytPearl2, "kraytpearl");	
						inventory.add(kraytPearl2);
						
						TangibleObject kraytPearl3 = (TangibleObject) core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", planet);
						kraytPearl3.setAttachment("LootItemName", "kraytpearl_flawless");
						core.lootService.handleSpecialItems(kraytPearl3, "kraytpearl");	
						inventory.add(kraytPearl3);
						
						TangibleObject kraytPearl4 = (TangibleObject) core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", planet);
						kraytPearl4.setAttachment("LootItemName", "kraytpearl_flawless");
						core.lootService.handleSpecialItems(kraytPearl4, "kraytpearl");	
						inventory.add(kraytPearl4);
						
						TangibleObject kraytPearl5 = (TangibleObject) core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", planet);
						kraytPearl5.setAttachment("LootItemName", "kraytpearl_flawless");
						core.lootService.handleSpecialItems(kraytPearl5, "kraytpearl");	
						inventory.add(kraytPearl5);
						
						TangibleObject kraytPearl6 = (TangibleObject) core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_krayt_dragon_pearl.iff", planet);
						kraytPearl6.setAttachment("LootItemName", "kraytpearl_premium");
						core.lootService.handleSpecialItems(kraytPearl6, "kraytpearl");	
						inventory.add(kraytPearl6);
						
						
						
						TangibleObject colorCrystal = (TangibleObject) core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_force_crystal.iff", planet);
						colorCrystal.getAttributes().put("@obj_attr_n:condition", "100/100");
						colorCrystal.getAttributes().put("@obj_attr_n:crystal_owner", "\\#D1F56F UNTUNED \\#FFFFFF ");
						colorCrystal.setAttachment("radial_filename", "item/tunable");
						core.lootService.setCustomization(colorCrystal, "colorcrystal",null,null);
						inventory.add(colorCrystal);
						
						TangibleObject lavaCrystal = (TangibleObject) core.objectService.createObject("object/tangible/component/weapon/lightsaber/shared_lightsaber_module_lava_crystal.iff", planet);
						inventory.add(lavaCrystal);
						
						return;
						
					case 110:

						SurveyTool mineralSurveyTool = (SurveyTool) core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_mineral.iff", planet);
						mineralSurveyTool.setCustomName("Mineral Survey Device");
						inventory.add(mineralSurveyTool);
						
						SurveyTool chemicalSurveyTool = (SurveyTool) core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_inorganic.iff", planet);
						chemicalSurveyTool.setCustomName("Chemical Survey Device");
						inventory.add(chemicalSurveyTool);
						
						SurveyTool floraSurveyTool = (SurveyTool) core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_lumber.iff", planet);
						floraSurveyTool.setCustomName("Flora Survey Device");
						inventory.add(floraSurveyTool);

						SurveyTool gasSurveyTool = (SurveyTool) core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_gas.iff", planet);
						gasSurveyTool.setCustomName("Gas Survey Device");
						inventory.add(gasSurveyTool);

						SurveyTool waterSurveyTool = (SurveyTool) core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_moisture.iff", planet);
						waterSurveyTool.setCustomName("Water Survey Device");
						inventory.add(waterSurveyTool);

						SurveyTool windSurveyTool = (SurveyTool) core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_wind.iff", planet);
						windSurveyTool.setCustomName("Wind Survey Device");
						inventory.add(windSurveyTool);

						SurveyTool solarSurveyTool = (SurveyTool) core.objectService.createObject("object/tangible/survey_tool/shared_survey_tool_solar.iff", planet);
						solarSurveyTool.setCustomName("Solar Survey Device");
						inventory.add(solarSurveyTool);
						return;
					case 111:
						SWGObject deed1;
						
						// Minerals
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_ore_s1_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 5);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_ore_s2_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 11);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_ore_heavy_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 135400);
						deed1.setIntAttribute("examine_maintenance_rate", 14);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_ore_deed_elite.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 250000);
						deed1.setIntAttribute("examine_maintenance_rate", 44);
						inventory.add(deed1);
						
						// Chemicals
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_liquid_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 5);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_liquid_deed_medium.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 11);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_liquid_deed_heavy.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 135400);
						deed1.setIntAttribute("examine_maintenance_rate", 14);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_liquid_deed_elite.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 250000);
						deed1.setIntAttribute("examine_maintenance_rate", 44);
						inventory.add(deed1);
						
						// Flora
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_flora_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 5);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_flora_deed_medium.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 11);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_flora_deed_heavy.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 135400);
						deed1.setIntAttribute("examine_maintenance_rate", 14);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_flora_deed_elite.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 250000);
						deed1.setIntAttribute("examine_maintenance_rate", 44);
						inventory.add(deed1);
						
						
						// Gas
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_gas_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 5);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_gas_deed_medium.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 11);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_gas_deed_heavy.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 135400);
						deed1.setIntAttribute("examine_maintenance_rate", 14);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_gas_deed_elite.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 250000);
						deed1.setIntAttribute("examine_maintenance_rate", 44);
						inventory.add(deed1);
						
						// Water
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_moisture_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 5);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_moisture_deed_medium.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 27344);
						deed1.setIntAttribute("examine_maintenance_rate", 11);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_moisture_deed_heavy.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 135400);
						deed1.setIntAttribute("examine_maintenance_rate", 14);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/harvester_deed/shared_harvester_moisture_deed_elite.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 250000);
						deed1.setIntAttribute("examine_maintenance_rate", 44);
						inventory.add(deed1);
						
						// Generators
						deed1 = core.objectService.createObject("object/tangible/deed/generator_deed/shared_generator_fusion_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 250000);
						deed1.setIntAttribute("examine_maintenance_rate", 19);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/generator_deed/shared_generator_wind_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 250000);
						deed1.setIntAttribute("examine_maintenance_rate", 10);
						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/generator_deed/shared_generator_solar_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 250000);
						deed1.setIntAttribute("examine_maintenance_rate", 15);
						inventory.add(deed1);
						
//						deed1 = core.objectService.createObject("object/tangible/deed/generator_deed/shared_generator_photo_bio_deed.iff", planet);
//						deed1.setIntAttribute("examine_hoppersize", 250000);
//						deed1.setIntAttribute("examine_maintenance_rate", 19);
//						inventory.add(deed1);
						
						deed1 = core.objectService.createObject("object/tangible/deed/generator_deed/shared_generator_geothermal_deed.iff", planet);
						deed1.setIntAttribute("examine_hoppersize", 250000);
						deed1.setIntAttribute("examine_maintenance_rate", 15);
						inventory.add(deed1);
						
						return;
					case 112:
						core.resourceService.spawnSpecificResourceContainer("Radioactive", player, 100000);
						return;	
					case 120:
//						SWGObject houseDeed = core.objectService.createObject("object/tangible/deed/player_house_deed/shared_generic_house_small_deed.iff", planet);
//						inventory.add(houseDeed);
						
						TangibleObject deed = (TangibleObject) core.objectService.createObject("object/tangible/deed/player_house_deed/shared_generic_house_small_deed.iff", planet);
						deed.setIntAttribute("examine_maintenance_rate", 15);
						inventory.add(deed);
						
						deed = (TangibleObject) core.objectService.createObject("object/tangible/deed/city_deed/shared_cityhall_tatooine_deed.iff", planet);
						deed.setIntAttribute("examine_maintenance_rate", 15);
						inventory.add(deed);
						
						deed = (TangibleObject) core.objectService.createObject("object/tangible/deed/guild_deed/shared_tatooine_guild_deed.iff", planet);
						deed.setIntAttribute("examine_maintenance_rate", 15);
						inventory.add(deed);
						
						return;
					
					case 121:
						NGECore.getInstance().playerCityService.buildSandboxTestCity(player);
						return;
						
					case 122:
						core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName("dantooine"), new Point3D(4198,9,5210), player.getOrientation(), null);
						return;
						
					case 124:
						core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName("tatooine"), new Point3D(3521,4,-4800), player.getOrientation(), null);
						return;
						
					case 164:
						core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName("dantooine"), new Point3D(-284,18,2853), player.getOrientation(), null);
						return;
						
					case 165:
						core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName("dantooine"), new Point3D(-1586,18,-7688), player.getOrientation(), null);
						return;
						
					case 166:
						core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName("dantooine"), new Point3D(-4211,18,-2349), player.getOrientation(), null);
						return;
						
					case 167:
						core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName("dantooine"), new Point3D(-4211,18,-2349), player.getOrientation(), null);
						return;
						
					case 168: 
						core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName("dathomir"), new Point3D(-2450,18,1521), player.getOrientation(), null);
						return;
						
					case 169: 
						core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName("dathomir"), new Point3D(-4002, 4, -58), player.getOrientation(), null);
						return;

					case 123:
						TangibleObject arakydDroids = (TangibleObject) core.objectService.createObject("object/tangible/mission/shared_mission_bounty_droid_probot.iff", planet);
						arakydDroids.setStackable(true);
						arakydDroids.setUses(20);
						inventory.add(arakydDroids);
						
						TangibleObject seekerDroids = (TangibleObject) core.objectService.createObject("object/tangible/mission/shared_mission_bounty_droid_seeker.iff", planet);
						seekerDroids.setStackable(true);
						seekerDroids.setUses(20);
						inventory.add(seekerDroids);
						return;

					case 125:
						TangibleObject genericCraftingTool = (TangibleObject) core.objectService.createObject("object/tangible/crafting/station/shared_generic_tool.iff", planet);
						genericCraftingTool.setCustomName("Generic Crafting Tool");
						inventory.add(genericCraftingTool);	
						return;
					case 130:
						TangibleObject swoopDeed = (TangibleObject) core.objectService.createObject("object/tangible/deed/vehicle_deed/shared_speederbike_swoop_deed.iff", planet);
						TangibleObject av21deed = (TangibleObject) core.objectService.createObject("object/tangible/deed/vehicle_deed/shared_landspeeder_av21_deed.iff", planet);
						inventory.add(swoopDeed);
						inventory.add(av21deed);
						return;
						
					case 131:
						TangibleObject gurreckDeed = (TangibleObject) core.objectService.createObject("object/tangible/deed/pet_deed/shared_gurreck_deed.iff", planet);						
						inventory.add(gurreckDeed);
						TangibleObject merekDeed = (TangibleObject) core.objectService.createObject("object/tangible/deed/pet_deed/shared_merek_deed.iff", planet);						
						inventory.add(merekDeed);
						return;
					
					case 140:
						TangibleObject guildRegistry = (TangibleObject) core.objectService.createObject("object/tangible/furniture/technical/shared_guild_registry_initial.iff", planet);
						inventory.add(guildRegistry);
						return;
						
					case 153:
						Point3D position = new Point3D(-6222,1,7380);
						core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName("dantooine"), position, player.getOrientation(), null);
						
					case 177:
						// "object/draft_schematic/item/shared_item_reverse_engineering_tool.iff"
						TangibleObject REingTool = (TangibleObject) core.objectService.createObject("object/tangible/container/loot/shared_reverse_engineer_tool.iff", planet);						
						REingTool.setCustomName("Reverse Engineering Tool");
						inventory.add(REingTool);	
						core.lootService.prepInv2(player);
						return;

				}
			}	
		});
		
		core.suiService.openSUIWindow(window);	
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
	}

	
	@Override
	public void shutdown() {
		
	}
	
}

