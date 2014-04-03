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

import protocol.swg.ExpertiseRequestMessage;

import resources.common.Console;
import resources.common.FileUtilities;
import resources.common.Opcodes;
import resources.common.SpawnPoint;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import services.sui.SUIWindow;
import services.sui.SUIService.ListBoxType;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import main.NGECore;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
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
				break;
			case 1: // Character
				suiOptions.put((long) 10, "Set combat level to 90");
				suiOptions.put((long) 11, "Give 100,000 credits");
				suiOptions.put((long) 12, "Reset expertise");
				break;
			case 2: // Items
				suiOptions.put((long) 20, "Armor");
				suiOptions.put((long) 21, "Weapons");
				suiOptions.put((long) 22, "Misc Items");
				suiOptions.put((long) 23, "Jedi Items");
				break;
			case 3: // [Items] Weapons
				suiOptions.put((long) 30, "Jedi Weapons");
				suiOptions.put((long) 31, "Melee Weapons");
				suiOptions.put((long) 32, "Ranged Weapons");
				break;
			case 4: // [Items] Misc Items
				suiOptions.put((long) 40, "Unity Ring");
				suiOptions.put((long) 41, "Tusken rucksack");
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
					
					// Character
					case 10: // Set combat level to 90
						core.playerService.grantLevel(player, 90); // Commented out until fixed
					    //core.playerService.giveExperience(player, 999999999);
						return;
					case 11: // Give 100,000 credits
						player.setCashCredits(player.getCashCredits() + 100000);
						return;
					case 12: // Reset expertise
					    // Seefo->Light: I commented out the below line because it gave us an error and didn't properly remove the skill, could you try the method SWGList.reverseGet that I added?
					    //player.getSkills().get().stream().filter(s -> s.contains("expertise")).forEach(s -> core.skillService.removeSkill(creature, s));
					    
					    // Using this for now
					    for(int i = creature.getSkills().size() - 1; i >= 0; i-- )
						{
						    String skill = creature.getSkills().get(i);
						    if(skill.contains("expertise")) core.skillService.removeSkill(player, skill);
						}
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
						
					// [Items] Weapons
					case 30: // Jedi Weapons
						TangibleObject lightsaber1 = (TangibleObject) core.objectService.createObject("object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff", planet);
						lightsaber1.setIntAttribute("required_combat_level", 90);
						lightsaber1.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", 1);
						lightsaber1.setStringAttribute("class_required", "Jedi");
						lightsaber1.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						lightsaber1.setStringAttribute("cat_wpn_damage.damage", "689-1379");
						
						TangibleObject lightsaber2 = (TangibleObject) core.objectService.createObject("object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff", planet);
						lightsaber2.setIntAttribute("required_combat_level", 90);
						lightsaber2.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", 1);
						lightsaber2.setStringAttribute("class_required", "Jedi");
						lightsaber2.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						lightsaber2.setStringAttribute("cat_wpn_damage.damage", "689-1379");
						
						TangibleObject lightsaber3 = (TangibleObject) core.objectService.createObject("object/weapon/melee/polearm/crafted_saber/shared_sword_lightsaber_polearm_gen5.iff", planet);
						lightsaber3.setIntAttribute("required_combat_level", 90);
						lightsaber3.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", 1);
						lightsaber3.setStringAttribute("class_required", "Jedi");
						lightsaber3.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						lightsaber3.setStringAttribute("cat_wpn_damage.damage", "689-1379");
						
						Random random = new Random();
						
						lightsaber1.setCustomizationVariable("/private/index_color_blade", (byte) random.nextInt(47));
						lightsaber2.setCustomizationVariable("/private/index_color_blade", (byte) random.nextInt(47));
						lightsaber3.setCustomizationVariable("/private/index_color_blade", (byte) random.nextInt(47));
						
						inventory.add(lightsaber1);
						inventory.add(lightsaber2);
						inventory.add(lightsaber3);
						return;
					case 31: // Melee Weapons
						SWGObject sword1 = core.objectService.createObject("object/weapon/melee/sword/shared_sword_01.iff", planet);
						sword1.setIntAttribute("required_combat_level", 90);
						sword1.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", 1);
						sword1.setStringAttribute("class_required", "None");
						sword1.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						sword1.setStringAttribute("cat_wpn_damage.damage", "1100-1200");
						
						inventory.add(sword1);
						return;
					case 32: // Ranged Weapons
						SWGObject rifle1 = core.objectService.createObject("object/weapon/ranged/rifle/shared_rifle_e11.iff", planet);
						rifle1.setIntAttribute("required_combat_level", 90);
						rifle1.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", (float) 0.8);
						rifle1.setStringAttribute("class_required", "None");
						rifle1.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						rifle1.setStringAttribute("cat_wpn_damage.damage", "800-1250");
						
						inventory.add(rifle1);
						
						SWGObject pistol = core.objectService.createObject("object/weapon/ranged/pistol/shared_pistol_cdef.iff", planet);
						pistol.setIntAttribute("required_combat_level", 90);
						pistol.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", (float) 0.4);
						pistol.setStringAttribute("class_required", "None");
						pistol.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						pistol.setStringAttribute("cat_wpn_damage.damage", "400-559");
						
						inventory.add(pistol);
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
