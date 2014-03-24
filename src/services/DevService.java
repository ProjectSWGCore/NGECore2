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
				break;
			case 2: // Items
				suiOptions.put((long) 20, "(Light) Jedi Robe");
				suiOptions.put((long) 21, "(Dark) Jedi Robe");
				suiOptions.put((long) 22, "Composite Armor");
				suiOptions.put((long) 23, "Weapons");
				break;
			case 3: // [Items] Weapons
				suiOptions.put((long) 30, "Jedi Weapons");
				suiOptions.put((long) 31, "Melee Weapons");
				suiOptions.put((long) 32, "Ranged Weapons");
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
						core.playerService.giveExperience(player, 999999999);
						return;
					case 11: // Give 100,000 credits
						player.setCashCredits(player.getCashCredits() + 100000);
						return;
						
					// Items
					case 20: // (Light) Jedi Robe
						inventory.add(core.objectService.createObject("object/tangible/wearables/robe/shared_robe_jedi_light_s03.iff", planet));
						return;
					case 21: // (Dark) Jedi Robe
						inventory.add(core.objectService.createObject("object/tangible/wearables/robe/shared_robe_jedi_dark_s03.iff", planet));
						return;
					case 22: // Composite Armor
						SWGObject bicep_r = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_r.iff", planet);
						bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						bicep_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bicep_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bicep_l = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_l.iff", planet);
						bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						bicep_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bicep_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bracer_r = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bracer_r.iff", planet);
						bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						bracer_r.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bracer_r.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject bracer_l = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bracer_l.iff", planet);
						bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						bracer_l.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						bracer_l.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject leggings = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_leggings.iff", planet);
						leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						leggings.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						leggings.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject helmet = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_helmet.iff", planet);
						helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						helmet.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						helmet.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject chest = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_chest_plate.iff", planet);
						chest.setIntAttribute("cat_armor_standard_protection.armor_eff_kinetic", 7000);
						chest.setIntAttribute("cat_armor_standard_protection.armor_eff_energy", 5000);
						chest.setIntAttribute("cat_armor_special_protection.special_protection_type_heat", 6000);
						chest.setIntAttribute("cat_armor_special_protection.special_protection_type_cold", 6000);
						chest.setIntAttribute("cat_armor_special_protection.special_protection_type_acid", 6000);
						chest.setIntAttribute("cat_armor_special_protection.special_protection_type_electricity", 6000);
						
						SWGObject boots = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_boots.iff", planet);
						SWGObject gloves = core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_gloves.iff", planet);
						
						
						
						inventory.add(bicep_r);
						inventory.add(bicep_l);
						inventory.add(bracer_r);
						inventory.add(bracer_l);
						inventory.add(leggings);
						inventory.add(helmet);
						inventory.add(chest);
						inventory.add(boots);
						inventory.add(gloves);

						
						return;
					case 23: // Weapons
						sendCharacterBuilderSUI(player, 3);
						return;
						
					// [Items] Weapons
					case 30: // Jedi Weapons
						SWGObject lightsaber1 = core.objectService.createObject("object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff", planet);
						lightsaber1.setIntAttribute("required_combat_level", 90);
						lightsaber1.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", 1);
						lightsaber1.setStringAttribute("class_required", "Jedi");
						lightsaber1.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						lightsaber1.setStringAttribute("cat_wpn_damage.damage", "689-1379");
						
						SWGObject lightsaber2 = core.objectService.createObject("object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff", planet);
						lightsaber2.setIntAttribute("required_combat_level", 90);
						lightsaber2.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", 1);
						lightsaber2.setStringAttribute("class_required", "Jedi");
						lightsaber2.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						lightsaber2.setStringAttribute("cat_wpn_damage.damage", "689-1379");
						
						SWGObject lightsaber3 = core.objectService.createObject("object/weapon/melee/polearm/crafted_saber/shared_sword_lightsaber_polearm_gen5.iff", planet);
						lightsaber3.setIntAttribute("required_combat_level", 90);
						lightsaber3.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", 1);
						lightsaber3.setStringAttribute("class_required", "Jedi");
						lightsaber3.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						lightsaber3.setStringAttribute("cat_wpn_damage.damage", "689-1379");
						
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
						rifle1.setFloatAttribute("cat_wpn_damage.wpn_attack_speed", 1);
						rifle1.setStringAttribute("class_required", "None");
						rifle1.setStringAttribute("cat_wpn_damage.wpn_damage_type", "Energy");
						rifle1.setStringAttribute("cat_wpn_damage.damage", "800-1250");
						
						inventory.add(rifle1);
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
