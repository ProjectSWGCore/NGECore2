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
			case 0:
				suiOptions.put((long) 1, "Character");
				suiOptions.put((long) 2, "Items");
				break;
			case 1:
				suiOptions.put((long) 10, "Set combat level to 90");
				suiOptions.put((long) 11, "Give 100,000 credits");
				break;
			case 2: 
				suiOptions.put((long) 20, "(Light) Jedi Robe");
				suiOptions.put((long) 21, "(Dark) Jedi Robe");
				suiOptions.put((long) 22, "Composite Armor");
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
					case 1:
						sendCharacterBuilderSUI(player, childIndex);
						return;
					case 2:
						sendCharacterBuilderSUI(player, childIndex);
						return;
					case 10:
						core.playerService.giveExperience(player, 999999999);
						return;
					case 11:
						player.setCashCredits(player.getCashCredits() + 100000);
						return;
					case 20:
						inventory.add(core.objectService.createObject("object/tangible/wearables/robe/shared_robe_jedi_light_s03.iff", planet));
						return;
					case 21:
						inventory.add(core.objectService.createObject("object/tangible/wearables/robe/shared_robe_jedi_dark_s03.iff", planet));
						return;
					case 22:
						inventory.add(core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_r.iff", planet));
						inventory.add(core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bicep_l.iff", planet));
						inventory.add(core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bracer_r.iff", planet));
						inventory.add(core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_bracer_l.iff", planet));
						inventory.add(core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_helmet.iff", planet));
						inventory.add(core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_chest_plate.iff", planet));
						inventory.add(core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_leggings.iff", planet));
						inventory.add(core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_boots.iff", planet));
						inventory.add(core.objectService.createObject("object/tangible/wearables/armor/composite/shared_armor_composite_gloves.iff", planet));
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
