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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;










import main.NGECore;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.database.ODBCursor;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.common.Opcodes;
import resources.common.OutOfBand;
import resources.common.ProsePackage;
import resources.datatables.DisplayType;
import resources.objects.building.BuildingObject;
import resources.objects.creature.CreatureObject;
import resources.objects.intangible.IntangibleObject;
import resources.objects.manufacture.ManufactureSchematicObject;
import resources.objects.tangible.TangibleObject;
import services.chat.Mail;
import services.chat.WaypointAttachment;
import protocol.swg.auctionManagerClientListener.AuctionQueryHeadersMessage;
import protocol.swg.auctionManagerClientListener.AuctionQueryHeadersResponseMessage;
import protocol.swg.auctionManagerClientListener.BidAuctionMessage;
import protocol.swg.auctionManagerClientListener.BidAuctionResponseMessage;
import protocol.swg.auctionManagerClientListener.CancelLiveAuctionMessage;
import protocol.swg.auctionManagerClientListener.CancelLiveAuctionResponseMessage;
import protocol.swg.auctionManagerClientListener.CommoditiesItemTypeListResponse;
import protocol.swg.auctionManagerClientListener.CreateAuctionMessage;
import protocol.swg.auctionManagerClientListener.GetAuctionDetails;
import protocol.swg.auctionManagerClientListener.GetAuctionDetailsResponse;
import protocol.swg.auctionManagerClientListener.IsVendorOwnerMessage;
import protocol.swg.auctionManagerClientListener.IsVendorOwnerResponseMessage;
import protocol.swg.auctionManagerClientListener.CreateAuctionResponseMessage;
import protocol.swg.auctionManagerClientListener.RetrieveAuctionItemMessage;
import protocol.swg.auctionManagerClientListener.RetrieveAuctionItemResponseMessage;

public class BazaarService implements INetworkDispatch {
	
	private NGECore core;
	private Set<AuctionItem> auctionItems = new ConcurrentSkipListSet<AuctionItem>();
	private ConcurrentHashMap<Long, Integer> commodityLimit = new ConcurrentHashMap<Long, Integer>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final static int COMMODITY_LIMIT = 25;
	private final static int MAX_BAZAAR_SALE_PRICE = 10000000;
	private final static int SALES_FEE = 20;
	private final static int COMMODITY_EXPIRE = 604800;
	private final static int VENDOR_EXPIRE = 2592000;

	public BazaarService(NGECore core) {
		this.core = core;
		core.commandService.registerCommand("createvendor");
		loadAuctionItems();
	}
	
	public void saveAllItems() {
		System.out.println("Saving auction items...");
		auctionItems.forEach(item -> core.objectService.persistObject(item.getObjectId(), item, core.getAuctionODB()));
	}

	private void loadAuctionItems() {
		ODBCursor cursor = core.getAuctionODB().getCursor();
		while(cursor.hasNext()) {
			addAuctionItem((AuctionItem) cursor.next());
		}
		auctionItems.stream().map(AuctionItem::getItem).forEach(obj -> { 
			obj.initializeBaselines();
			obj.initAfterDBLoad(); 
			obj.viewChildren(obj, true, true, obj2 -> { obj2.initializeBaselines(); obj2.initAfterDBLoad(); });
		});
		cursor.close();
	}

	@SuppressWarnings("unused")
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {		

		swgOpcodes.put(Opcodes.CommoditiesItemTypeListRequest, (session, data) -> {

			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;
			
			CommoditiesItemTypeListResponse response = new CommoditiesItemTypeListResponse();
			
			List<String> insertedItems = new ArrayList<String>();
			auctionItems.stream().filter(i -> !insertedItems.contains(i.getItem().getObjectName().getStfValue())).forEach(i -> {
				response.addItem(i);
				insertedItems.add(i.getItem().getObjectName().getString());
				System.out.println(i.getItem().getObjectName().getString());
			});
			
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
			int permission = 2;
			if(terminal.getAttachment("isVendor") != null && (Boolean) terminal.getAttachment("isVendor")) {
				if(!((Boolean) terminal.getAttachment("initialized")))
					return;
				permission = (long) terminal.getAttachment("vendorOwner") == player.getObjectID() ? 0 : 1;
			}
			Point3D pos = terminal.getWorldPosition();
			session.write(new IsVendorOwnerResponseMessage(permission, 0, terminalId, getVendorUID((TangibleObject) terminal)).serialize());
			
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
		
		swgOpcodes.put(Opcodes.CancelLiveAuctionMessage, (session, data) -> {
			
			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;

			data = data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);

			CancelLiveAuctionMessage cancelLiveAuction = new CancelLiveAuctionMessage();
			cancelLiveAuction.deserialize(data);
			
			AuctionItem item = getAuctionItem(cancelLiveAuction.getObjectId());
			
			if(item == null)
				return;
			
			cancelItem(player, item);

		});

		swgOpcodes.put(Opcodes.BidAuctionMessage, (session, data) -> {
			
			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;

			data = data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);

			BidAuctionMessage bidAuction = new BidAuctionMessage();
			bidAuction.deserialize(data);
			
			AuctionItem item = getAuctionItem(bidAuction.getAuctionId());
			//SWGObject vendor = core.objectService.getObject(bidAuction.getVendorId());
			if(item == null)
				return;
			SWGObject vendor = core.objectService.getObject(item.getVendorId());
			if(vendor == null)
				return;
			buyItem((CreatureObject) player, item, vendor, bidAuction.getMyPrice(), bidAuction.getProxyPrice());
			
		});
		
		swgOpcodes.put(Opcodes.RetrieveAuctionItemMessage, (session, data) -> {
			
			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;

			data = data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);

			RetrieveAuctionItemMessage retrieveItem = new RetrieveAuctionItemMessage();
			retrieveItem.deserialize(data);
			
			AuctionItem item = getAuctionItem(retrieveItem.getObjectId());
			SWGObject vendor = core.objectService.getObject(retrieveItem.getVendorId());
			
			if(item == null || vendor == null)
				return;
			
			retrieveItem((CreatureObject) player, item, vendor);
			
		});
	
		swgOpcodes.put(Opcodes.GetAuctionDetails, (session, data) -> {
			
			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;

			data = data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);
			
			GetAuctionDetails request = new GetAuctionDetails();
			request.deserialize(data);
			AuctionItem item = getAuctionItem(request.getObjectId());
			
			if(item == null)
				return;
			
			client.getSession().write(new GetAuctionDetailsResponse(item).serialize());
			
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
			createAuction.deserialize(data);
			SWGObject vendor = core.objectService.getObject(createAuction.getVendorId());
			SWGObject item = core.objectService.getObject(createAuction.getObjectId());
			
			if(vendor == null || item == null || item instanceof CreatureObject || item instanceof BuildingObject)
				return;

			addAuction((CreatureObject) player, item, vendor, createAuction.getPrice(), createAuction.getDuration(), createAuction.getDescription(), true, createAuction.getPremium());
			
		});

		swgOpcodes.put(Opcodes.CreateImmediateAuctionMessage, (session, data) -> {
			
			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;

			data = data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);

			CreateAuctionMessage createAuction = new CreateAuctionMessage();
			createAuction.deserialize(data);

			SWGObject vendor = core.objectService.getObject(createAuction.getVendorId());
			SWGObject item = core.objectService.getObject(createAuction.getObjectId());
			
			if(vendor == null || item == null || item instanceof CreatureObject || item instanceof BuildingObject)
				return;
			
			addAuction((CreatureObject) player, item, vendor, createAuction.getPrice(), createAuction.getDuration(), createAuction.getDescription(), false, createAuction.getPremium());

		});
		
		swgOpcodes.put(Opcodes.AuctionQueryHeadersMessage, (session, data) -> {
			
			Client client = core.getClient(session);
			
			if (client == null)
				return;
			
			SWGObject player = client.getParent();
			
			if (player == null)
				return;

			data = data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);
			
			AuctionQueryHeadersMessage queryHeaders = new AuctionQueryHeadersMessage();
			queryHeaders.deserialize(data);
			SWGObject vendor = core.objectService.getObject(queryHeaders.getVendorId());
			
			if(vendor == null)
				return;

			getItemData(player, vendor, queryHeaders.getRange(), queryHeaders.getScreen(), queryHeaders.getCounter(), queryHeaders.getOffset(), queryHeaders.getCategory(), queryHeaders.getMinPrice(), queryHeaders.getMaxPrice());
			
		});



	}
	
	public void buyItem(CreatureObject player, AuctionItem item, SWGObject vendor, int price, int proxyPrice) {
		
		BidAuctionResponseMessage response = new BidAuctionResponseMessage(item.getObjectId(), BidAuctionResponseMessage.SUCCESS);

		if((price > MAX_BAZAAR_SALE_PRICE || proxyPrice > MAX_BAZAAR_SALE_PRICE) && item.isOnBazaar()) {
			response.setStatus(BidAuctionResponseMessage.PRICEOVERFLOW);
			player.getClient().getSession().write(response.serialize());
			return;
		}
		
		if(price < 1 /*|| proxyPrice < 1 */|| price < item.getPrice()) {
			response.setStatus(BidAuctionResponseMessage.INVALIDPRICE);
			player.getClient().getSession().write(response.serialize());
			return;
		}
		
		if(price > player.getBankCredits()) {
			response.setStatus(BidAuctionResponseMessage.NOTENOUGHCREDITS);
			player.getClient().getSession().write(response.serialize());
			return;
		}
		
		if(item.isAuction())
			bidAuction(player, item, vendor, price, proxyPrice);
		else
			instantBuy(player, item, vendor);
		
	}

	public void instantBuy(CreatureObject player, AuctionItem item, SWGObject vendor) {
		//System.out.println("test");

		long timeUntilExpire = (item.getExpireTime() - System.currentTimeMillis()) / 1000;
		long retrieveExpire = 0;

		if(item.isOnBazaar())
			retrieveExpire = timeUntilExpire + COMMODITY_EXPIRE;
		else
			retrieveExpire = timeUntilExpire + VENDOR_EXPIRE;

		CreatureObject seller = (CreatureObject) core.objectService.getObject(item.getOwnerId());
		
		item.setStatus(AuctionItem.SOLD);
		item.setBuyerId(player.getObjectID());
		item.setExpireTime(retrieveExpire);
		item.setBidderName(player.getCustomName());
		player.setBankCredits(player.getBankCredits() - item.getPrice());
		player.getClient().getSession().write(new BidAuctionResponseMessage(item.getObjectId(), BidAuctionResponseMessage.SUCCESS).serialize());
				
		WaypointAttachment waypoint = new WaypointAttachment();
		waypoint.positionX = vendor.getWorldPosition().x;
		waypoint.positionZ = vendor.getWorldPosition().z;
		waypoint.planetCRC = CRC.StringtoCRC(vendor.getPlanet().getName());
		waypoint.name = vendor.getCustomName() == null ? vendor.getObjectName().getStfValue() : vendor.getCustomName();
		
		Mail sellerMail = new Mail();
		Mail buyerMail = new Mail();
		
		sellerMail.setMailId(NGECore.getInstance().chatService.generateMailId());
		sellerMail.setMessage("");
		sellerMail.setRecieverId(item.getOwnerId());
		sellerMail.setStatus(Mail.NEW);
		sellerMail.setTimeStamp((int) (new Date().getTime() / 1000));
		sellerMail.setSenderName("SWG." + core.getGalaxyName() + ".auctioner");
		if(item.isOnBazaar()) {
			sellerMail.setSubject("@auction:subject_instant_seller");
			sellerMail.addProseAttachment(new ProsePackage("@auction:seller_success", "TO", item.getObjectId(), item.getItemName(), "TT", player.getObjectID(), item.getBidderName(), "DI", item.getPrice()));
		} else {
			sellerMail.setSubject("@auction:subject_vendor_seller");
			sellerMail.addProseAttachment(new ProsePackage("@auction:seller_success_vendor", "TO", item.getObjectId(), item.getItemName(), "TT", player.getObjectID(), item.getBidderName(), "DI", item.getPrice()));
		}
		sellerMail.addProseAttachment(new ProsePackage("@auction:seller_success_location", "TO", 0, "@planet_n:" + vendor.getPlanet().getName(), "TT", 0, core.mapService.getClosestCityName(vendor)));
		sellerMail.addWaypointAttachment(waypoint);

		buyerMail.setMailId(NGECore.getInstance().chatService.generateMailId());
		buyerMail.setMessage("");
		buyerMail.setRecieverId(player.getObjectID());
		buyerMail.setStatus(Mail.NEW);
		buyerMail.setTimeStamp((int) (new Date().getTime() / 1000));
		buyerMail.setSenderName("SWG." + core.getGalaxyName() + ".auctioner");
		if(item.isOnBazaar())
			buyerMail.setSubject("@auction:subject_instant_buyer");
		else 
			buyerMail.setSubject("@auction:subject_vendor_buyer");
		buyerMail.addProseAttachment(new ProsePackage("@auction:buyer_success", "TO", item.getObjectId(), item.getItemName(), "TT", item.getOwnerId(), item.getOwnerName(), "DI", item.getPrice()));
		buyerMail.addProseAttachment(new ProsePackage("@auction:buyer_success_location", "TO", 0, "@planet_n:" + vendor.getPlanet().getName(), "TT", 0, core.mapService.getClosestCityName(vendor)));
		buyerMail.addWaypointAttachment(waypoint);
		
		core.chatService.storePersistentMessage(buyerMail);
		core.chatService.storePersistentMessage(sellerMail);
		core.chatService.sendPersistentMessageHeader(player.getClient(), buyerMail);
		if(seller != null && seller.getClient() != null)
			core.chatService.sendPersistentMessageHeader(seller.getClient(), sellerMail);
				
		if(seller == null) {
			seller = core.objectService.getCreatureFromDB(item.getOwnerId());
			seller.setBankCredits(seller.getBankCredits() + item.getPrice());
			core.objectService.persistObject(seller.getObjectID(), seller, core.getSWGObjectODB());
		}
		
	}

	public void bidAuction(CreatureObject player, AuctionItem item, SWGObject vendor, int price, int proxyPrice) {
		//System.out.println("test");
	}

	public void retrieveItem(CreatureObject player, AuctionItem item, SWGObject vendor) {
		
		RetrieveAuctionItemResponseMessage response = new RetrieveAuctionItemResponseMessage(item.getObjectId(), RetrieveAuctionItemResponseMessage.SUCCESS);
		String closestCityName = core.mapService.getClosestCityName(player);
		SWGObject vendorOfItem = core.objectService.getObject(item.getVendorId());
		
		if(vendorOfItem == null) {
			response.setStatus(RetrieveAuctionItemResponseMessage.DONTRETRIEVE);
			player.getClient().getSession().write(response.serialize());
			return;
		}

		if(item.getItem() instanceof TangibleObject && player.getInventoryItemCount() >= 80) {
			response.setStatus(RetrieveAuctionItemResponseMessage.FULLINVENTORY);
			player.getClient().getSession().write(response.serialize());
			return;
		}
		
		if(item.getStatus() == AuctionItem.RETRIEVED || item.getStatus() == AuctionItem.FORSALE) {
			response.setStatus(RetrieveAuctionItemResponseMessage.NOTALLOWED);
			player.getClient().getSession().write(response.serialize());
			return;
		}
		
		if(item.getStatus() == AuctionItem.SOLD && item.getBuyerId() != player.getObjectID()) {
			response.setStatus(RetrieveAuctionItemResponseMessage.NOTALLOWED);
			player.getClient().getSession().write(response.serialize());
			return;
		}
		
		if(!item.isOnBazaar() && !vendor.inRange(player.getWorldPosition(), 8)) {
			response.setStatus(RetrieveAuctionItemResponseMessage.TOOFAR);
			player.getClient().getSession().write(response.serialize());
			return;
		}
		
		if(item.isOnBazaar() && !core.mapService.getClosestCityName(vendorOfItem).equals(closestCityName)) {
			response.setStatus(RetrieveAuctionItemResponseMessage.TOOFAR);
			player.getClient().getSession().write(response.serialize());
			return;
		}
		
		item.setStatus(AuctionItem.RETRIEVED);
		
		if(item.getItem() instanceof TangibleObject)
			player.getSlottedObject("inventory").add(item.getItem());
		else if(item.getItem() instanceof IntangibleObject) 
			player.getSlottedObject("datapad").add(item.getItem());
		
		core.objectService.getObjectList().put(item.getObjectId(), item.getItem());
		removeAuctionItem(item);
		player.getClient().getSession().write(response.serialize());

	}

	public void cancelItem(SWGObject player, AuctionItem item) {
		
		CancelLiveAuctionResponseMessage response = new CancelLiveAuctionResponseMessage(item.getObjectId(), CancelLiveAuctionResponseMessage.SUCCESS);

		long timeUntilExpire = (item.getExpireTime() - System.currentTimeMillis()) / 1000;
		long retrieveExpire = 0;
		
		if(item.getStatus() == AuctionItem.FORSALE) {
			
			if(item.getOwnerId() != player.getObjectID()) {
				response.setStatus(CancelLiveAuctionResponseMessage.NOTOWNER);
				player.getClient().getSession().write(response.serialize());
				return;
			}
			
			if(item.isOnBazaar())
				retrieveExpire = timeUntilExpire + COMMODITY_EXPIRE;
			else
				retrieveExpire = timeUntilExpire + VENDOR_EXPIRE;
				
		} else if(item.getStatus() == AuctionItem.OFFERED) {
			// TODO implement offers
			return;
		} else {
			response.setStatus(CancelLiveAuctionResponseMessage.ALREADYCOMPLETED);
			player.getClient().getSession().write(response.serialize());
			return;
		}
		
		// TODO: cancel auction once implemented
		
		item.setStatus(AuctionItem.EXPIRED);
		item.setExpireTime(retrieveExpire);
		player.getClient().getSession().write(response.serialize());

	}

	public void getItemData(SWGObject player, SWGObject vendor, int range, int screen, int counter, short offset, int category, int minPrice, int maxPrice) {
		
		Set<AuctionItem> inRangeItems = getInRangeItems(range, player.getPlanet(), vendor, core.mapService.getClosestCityName(player));
		
		Iterator<AuctionItem> it = inRangeItems.iterator();
		int displayedItems = 0;
		
		AuctionQueryHeadersResponseMessage response = new AuctionQueryHeadersResponseMessage(player.getObjectID(), screen, counter, offset, false);
		while(it.hasNext() && displayedItems < offset + 100) {
			
			AuctionItem item = it.next();
			
			if((minPrice != 0 && item.getPrice() < minPrice) || (maxPrice != 0 && item.getPrice() > maxPrice))
				continue;
			
			switch(screen) {
			
				// Auctions
				case 2:
					if(item.getStatus() == AuctionItem.FORSALE && item.isOnBazaar()) {
						if((category & 255) != 0) {
							if(item.getItemType() == category) {
								if(displayedItems >= offset)
									response.addItem(item);
								displayedItems++;
							}
						} else if((item.getItemType() & category) != 0) {
							if(displayedItems >= offset) 
								response.addItem(item);
							displayedItems++;
						} else if(item.getItemType() < 256 && category == 8192) {
							if(displayedItems >= offset) 
								response.addItem(item);
							displayedItems++;
						} else if(category == 0) {
							if(displayedItems >= offset) 
								response.addItem(item);
							displayedItems++;
						}
					}
					break;
				// My Items for sale
				case 3:
					if(item.getStatus() == AuctionItem.FORSALE && player.getObjectID() == item.getOwnerId())
						response.addItem(item);
					break;
				// My Auction Bids
				case 4:
					if(item.isAuction() && item.getStatus() == AuctionItem.FORSALE && player.getCustomName().equals(item.getBidderName()))
						response.addItem(item);
					break;
				// Retrieve items
				case 5:
					if(item.getStatus() == AuctionItem.SOLD && player.getObjectID() == item.getBuyerId())
						response.addItem(item);
					else if(item.getStatus() == AuctionItem.EXPIRED && player.getObjectID() == item.getOwnerId())
						response.addItem(item);						
					break;
				// Offers to my vendor
				case 6:
					if(item.getStatus() == AuctionItem.OFFERED && player.getObjectID() == item.getOfferToId())
						response.addItem(item);						
					break;
				// Vendor search
				case 7:
					if(item.getStatus() == AuctionItem.FORSALE && !item.isOnBazaar()) {
						if(vendor.getObjectID() != item.getVendorId() && (core.objectService.getObject(item.getVendorId()) == null || (Boolean) core.objectService.getObject(item.getVendorId()).getAttachment("vendorSearchEnabled")))
							break;
						if((category & 255) != 0) {
							if(item.getItemType() == category) {
								if(displayedItems >= offset)
									response.addItem(item);
								displayedItems++;
							}
						} else if((item.getItemType() & category) != 0) {
							if(displayedItems >= offset) 
								response.addItem(item);
							displayedItems++;
						} else if(item.getItemType() < 256 && category == 8192) {
							if(displayedItems >= offset) 
								response.addItem(item);
							displayedItems++;
						} else if(category == 0) {
							if(displayedItems >= offset) 
								response.addItem(item);
							displayedItems++;
						}
					}
					break;
				// Stock
				case 8:
					if(item.getStatus() == AuctionItem.SOLD && player.getObjectID() == item.getBuyerId())
						response.addItem(item);
					else if(item.getStatus() == AuctionItem.EXPIRED && player.getObjectID() == item.getOwnerId())
						response.addItem(item);						
					break;
				// Offers to other vendor
				case 9:
					if(item.getStatus() == AuctionItem.OFFERED && player.getObjectID() == item.getOwnerId())
						response.addItem(item);						
					break;
				
			
			}
			
		}
		
		if(displayedItems == offset + 100)
			response.setContinues(true);

		player.getClient().getSession().write(response.serialize());
	}
	
	public Set<AuctionItem> getInRangeItems(int range, Planet planet, SWGObject vendor, String closestCityName) {

		Set<AuctionItem> inRangeItems = new ConcurrentSkipListSet<AuctionItem>();
		String planetName = planet.getName();
		
		auctionItems.forEach((item) -> {
			
			SWGObject vendorOfItem = core.objectService.getObject(item.getVendorId());
			if(vendorOfItem == null)
				return;
			if(range > 0 && !item.getPlanet().equals(planetName))
				return;
			else if(range > 1 && !core.mapService.getClosestCityName(vendorOfItem).equals(closestCityName))
				return;
			else if(range > 2 && item.getVendorId() != vendor.getObjectID())
				return;
			
			inRangeItems.add(item);
			
		});
		
		return inRangeItems;
	}

	public void addAuction(CreatureObject player, SWGObject item, SWGObject vendor, int price, int duration, String description, boolean auction, boolean premium) {
		
		CreateAuctionResponseMessage itemSoldMsg = new CreateAuctionResponseMessage(item.getObjectID(), CreateAuctionResponseMessage.SUCCESS);
		
		if(item.getAttributes().containsKey("no_trade") || !item.isSubChildOf(player) || (item instanceof IntangibleObject && !(item instanceof ManufactureSchematicObject))) {
			itemSoldMsg.setStatus(CreateAuctionResponseMessage.INVALIDITEM);
			player.getClient().getSession().write(itemSoldMsg.serialize());
			return;
		}		
		
		if(core.tradeService.getTradingObjectMap().get(item) != null) {
			itemSoldMsg.setStatus(CreateAuctionResponseMessage.CANTSELLTRADINGITEM);
			player.getClient().getSession().write(itemSoldMsg.serialize());
			return;
		}
			
		if(getAuctionItem(item.getObjectID()) != null) {
			itemSoldMsg.setStatus(CreateAuctionResponseMessage.ALREADYFORSALE);
			player.getClient().getSession().write(itemSoldMsg.serialize());
			return;
		}
		
		if(price < 1 || price > MAX_BAZAAR_SALE_PRICE) {
			itemSoldMsg.setStatus(CreateAuctionResponseMessage.INVALIDPRICE);
			player.getClient().getSession().write(itemSoldMsg.serialize());
			return;
		}
		
		if(commodityLimit.get(player) != null && commodityLimit.get(player) >= COMMODITY_LIMIT) {
			itemSoldMsg.setStatus(CreateAuctionResponseMessage.TOOMANYITEMS);
			player.getClient().getSession().write(itemSoldMsg.serialize());
			return;
		}
		
		if(player.getBankCredits() < SALES_FEE || (premium && player.getBankCredits() < SALES_FEE * 5)) {
			itemSoldMsg.setStatus(CreateAuctionResponseMessage.NOTENOUGHCREDITS);
			player.getClient().getSession().write(itemSoldMsg.serialize());
			return;
		}
		
		@SuppressWarnings("unused") AuctionItem auctionItem = createAuctionItem(player, vendor, item, price, description, duration, auction, premium);
		
		core.objectService.destroyObject(item);
		
		if(vendor.getTemplate().contains("terminal_bazaar")) {
			int salesFee = SALES_FEE;
			if(premium) 
				salesFee *= 5;
			
			player.setBankCredits(player.getBankCredits() - salesFee);
			player.sendSystemMessage(OutOfBand.ProsePackage("@base_player:sale_fee", salesFee), DisplayType.Broadcast);
		}
		
		if(auction) {
			
		}
		
		player.getClient().getSession().write(itemSoldMsg.serialize());
		
	}
	
	public AuctionItem createAuctionItem(SWGObject player, SWGObject vendor, SWGObject item, int price, String description, int duration, boolean auction, boolean premium) {
		
		AuctionItem auctionItem = new AuctionItem(item, player.getObjectID());
		auctionItem.setExpireTime(System.currentTimeMillis() + (COMMODITY_EXPIRE * 1000));
		auctionItem.setAuction(auction);
		auctionItem.setItemDescription(description);
		auctionItem.setPrice(price);
		auctionItem.setItemType(item.getTemplateData().getAttribute("gameObjectType"));
		auctionItem.setItemTypeCRC(CRC.StringtoCRC(item.getTemplate().replace("shared_", "")));
		auctionItem.setStatus(AuctionItem.FORSALE);
		//auctionItem.setItemName("@" + item.getStfFilename() + ":" + item.getStfName());
		String name = item.getLookAtText();
		auctionItem.setItemName(name);
		auctionItem.setVendorId(vendor.getObjectID());
		auctionItem.setPlanet(vendor.getPlanet().getName());
		auctionItem.setVuid(getVendorUID((TangibleObject) vendor));
		auctionItem.setOwnerName(player.getCustomName());
		
		if(vendor.getAttachment("isVendor") == null)
			auctionItem.setOnBazaar(true);
		else {
			if(!((long) vendor.getAttachment("vendorOwner") == player.getObjectID())) {
				auctionItem.setOfferToId((long) vendor.getAttachment("owner"));
				auctionItem.setStatus(AuctionItem.OFFERED);
			}
		}
		addAuctionItem(auctionItem);
		
		return auctionItem;
	}
	
	public String getVendorUID(TangibleObject vendor) {
		
		if(vendor.getAttachment("uid") != null)
			return (String) vendor.getAttachment("uid");
		
		String planet = "";
		if(vendor.getContainer() == null)
			planet = vendor.getPlanet().getName();
		else
			planet = vendor.getContainer().getPlanet().getName();
		
		String uid = (planet + ".");
		Point3D pos = vendor.getWorldPosition();
		
		uid += ("@" + planet + "_region_names:" + core.mapService.getClosestCityName(vendor).toLowerCase().replace(" ", "_")); // TODO: replace this with city regions when they are implemented
		uid += ("." + "@" + vendor.getStfFilename() + ":" + vendor.getStfName() + ".");
		uid += (vendor.getObjectID() + "#" + pos.x + "," + pos.z);
		vendor.setAttachment("uid", uid);
		return uid;
		
	}

	@Override
	public void shutdown() {
		
	}
	
	public AuctionItem getAuctionItem(long objectId) {
		return auctionItems.parallelStream().filter(a -> a.getObjectId() == objectId).findFirst().orElse(null);
	}
	
	public void addAuctionItem(AuctionItem auctionItem) {
		if(auctionItem == null)
			return;
		auctionItems.add(auctionItem);
		int commodityNumber = 0;
		if(commodityLimit.get(auctionItem.getOwnerId()) != null)
			commodityNumber = commodityLimit.get(auctionItem.getOwnerId());
		commodityLimit.put(auctionItem.getOwnerId(), commodityNumber + 1);
		if(!core.getAuctionODB().contains(auctionItem.getObjectId())) {
			core.objectService.persistObject(auctionItem.getObjectId(), auctionItem, core.getAuctionODB());
		}
	}
	
	public void removeAuctionItem(AuctionItem auctionItem) {
		auctionItems.remove(auctionItem);
		int commodityNumber = 0;
		if(commodityLimit.get(auctionItem.getOwnerId()) != null)
			commodityNumber = commodityLimit.get(auctionItem.getOwnerId());
		commodityLimit.put(auctionItem.getOwnerId(), commodityNumber - 1);
		core.objectService.deletePersistentObject(auctionItem.getObjectId(), core.getAuctionODB());
	}
	
	public void startVendorUpdateTask(CreatureObject owner, SWGObject vendor) {
		
		scheduler.scheduleAtFixedRate(() -> {
			try {
				if(vendor == null || !((Boolean) vendor.getAttachment("initialized")))
					return;
				
				float maintenanceRate = 15;
				if((Boolean) vendor.getAttachment("onMap"))
					maintenanceRate += 6;
				vendor.setAttachment("maintenanceAmount", (Integer) vendor.getAttachment("maintenanceAmount") - maintenanceRate);
				// TODO add vendor delete after x days
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 1, 1, TimeUnit.HOURS);
		
	}
	
	public int getNumberOfItemsForSale(long vendorId) {
		return (int) auctionItems.parallelStream().filter(i -> i.getVendorId() == vendorId).count();
	}
	
}
