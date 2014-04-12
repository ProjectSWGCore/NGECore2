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
package services.bazaar;

import java.nio.ByteOrder;
import java.util.Map;

import main.NGECore;
import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.common.Opcodes;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import protocol.swg.ClientIdMsg;
import protocol.swg.auctionManagerClientListener.CommoditiesItemTypeListResponse;
import protocol.swg.auctionManagerClientListener.CommoditiesResourceTypeListResponse;
import protocol.swg.auctionManagerClientListener.CreateAuctionMessage;
import protocol.swg.auctionManagerClientListener.IsVendorOwnerMessage;
import protocol.swg.auctionManagerClientListener.IsVendorOwnerResponseMessage;
import protocol.swg.auctionManagerClientListener.ItemSoldMessage;

public class BazaarService implements INetworkDispatch {
	
	private NGECore core;

	public BazaarService(NGECore core) {
		this.core = core;
	}

	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {		

		swgOpcodes.put(Opcodes.CommoditiesItemTypeListRequest, (session, data) -> {

			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;
						
			session.write(new CommoditiesItemTypeListResponse().serialize());
			
		});
		
		swgOpcodes.put(Opcodes.IsVendorOwnerMessage, (session, data) -> {
			
			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;

			data = data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);
			
			IsVendorOwnerMessage request = new IsVendorOwnerMessage();
			request.deserialize(data);
			long terminalId = request.getTerminalId();
			SWGObject terminal = core.objectService.getObject(terminalId);
			if(terminal == null)
				return;
			Point3D pos = terminal.getWorldPosition();
			session.write(new IsVendorOwnerResponseMessage(2, 0, terminalId, "tatooine.@tatooine_region_names:mos_eisley.@terminal_name:terminal_bazaar." + terminalId + "#" + pos.x + "," + pos.z).serialize());
			
			
		});
		
		swgOpcodes.put(Opcodes.CommoditiesResourceTypeListRequest, (session, data) -> {
			
			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;

			data = data.order(ByteOrder.LITTLE_ENDIAN);

			if(data.remaining() > 2) // if client attaches a string to the request then it has already recieved it on zone-in
				return;

			//session.write(new CommoditiesResourceTypeListResponse(core.resourceService.getCompleteResourceNameHistory()).serialize());
			
		});
		
		swgOpcodes.put(Opcodes.CreateAuctionMessage, (session, data) -> {
			
			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;

			data = data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);

			CreateAuctionMessage createAuction = new CreateAuctionMessage();
			
			SWGObject vendor = core.objectService.getObject(createAuction.getVendorId());
			SWGObject item = core.objectService.getObject(createAuction.getObjectId());
			
			if(vendor == null || item == null || item instanceof CreatureObject || item instanceof BuildingObject)
				return;
			
			
			
		});



	}
	
	public void addAuction(CreatureObject player, SWGObject item, SWGObject vendor, int price, int duration, String description, boolean auction, boolean premium) {
		
		ItemSoldMessage itemSoldMsg = new ItemSoldMessage(item.getObjectID(), ItemSoldMessage.SUCCESS);
		
		if(item.getAttributes().containsKey("no_trade") || !item.isSubChildOf(player)) {
			itemSoldMsg.setStatus(ItemSoldMessage.INVALIDITEM);
			player.getClient().getSession().write(itemSoldMsg.serialize());
			return;
		}
		
		
		
	}

	@Override
	public void shutdown() {
		
	}


}
