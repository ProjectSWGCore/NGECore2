package services.trade;

import java.nio.ByteOrder;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.clientSecureTradeManager.AbortTradeMessage;
import protocol.swg.clientSecureTradeManager.AcceptTransactionMessage;
import protocol.swg.clientSecureTradeManager.AddItemMessage;
import protocol.swg.clientSecureTradeManager.BeginTradeMessage;
import protocol.swg.clientSecureTradeManager.GiveMoneyMessage;
import protocol.swg.clientSecureTradeManager.RemoveItemMessage;
import protocol.swg.clientSecureTradeManager.TradeCompleteMessage;
import protocol.swg.clientSecureTradeManager.TradeOpcodes;
import protocol.swg.clientSecureTradeManager.UnAcceptTransactionMessage;
import protocol.swg.objectControllerObjects.SecureTrade;
import resources.common.ObjControllerOpcodes;
import resources.objects.creature.CreatureObject;
import engine.clients.Client;
import engine.resources.container.AllPermissions;
import engine.resources.container.CreatureContainerPermissions;
import engine.resources.container.CreaturePermissions;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

@SuppressWarnings("unused")

public class TradeService implements INetworkDispatch{

	private NGECore core;
	
	private long recieverID;
	private long senderID;
	
	
	private Hashtable<SWGObject, Long> tradingObjectsTable = new Hashtable<SWGObject, Long>();
	// key = objectToGive,       value = giver's ID
	
	// need to be sure that no giver ID exists in table before adding value
	private Hashtable<Long, Integer> tradingCreditsTable = new Hashtable<Long, Integer>();
	// key = ID of giver     value = amnt recieving
	
	public TradeService(NGECore core) {
		this.core = core;
	}
	
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		objControllerOpcodes.put(ObjControllerOpcodes.SECURE_TRADE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);

				SecureTrade request = new SecureTrade();
				request.deserialize(data);
				
				senderID = request.getSenderID();
				recieverID = request.getRecieverID();
	
				CreatureObject recieverObject = (CreatureObject) core.objectService.getObject(recieverID);
				CreatureObject senderObject = (CreatureObject) core.objectService.getObject(senderID);
				
				Client senderClient = senderObject.getClient();
				Client recieverClient = recieverObject.getClient();
				
				int senderClientId = senderClient.getConnectionId();
				int recieverClientId = recieverClient.getConnectionId();
				
				//System.out.println("Sender isConnected: " + senderObject.getClient().getSession().isConnected());
				//System.out.println("Reciever isConnected: " + recieverObject.getClient().getSession().isConnected());
				
				if (recieverClient.getSession().containsAttribute("tradeSession") == true) {
					
					if(senderClient.getSession().containsAttribute("tradeSession") == false) {
						{
							senderClient.getSession().setAttribute("tradeSession", recieverID);
						}
						
						BeginTradeMessage senderResponse = new BeginTradeMessage(recieverID);
						senderClient.getSession().write(senderResponse.serialize());
						
						BeginTradeMessage recieverResponse = new BeginTradeMessage(senderID);
						recieverClient.getSession().write(recieverResponse.serialize());
						
					}
					
				} 
				else {
					
					// creates a new trade session for the user who sent the request. It's given the objectID 
					// that the player wants to trade with.
					if (senderObject.getCombatFlag() == 0 && recieverObject.getCombatFlag() == 0) {
						senderClient.getSession().setAttribute("tradeSession", recieverID);
						
						recieverObject.sendSystemMessage(senderObject.getCustomName() + " wants to trade with you.", (byte) 0);		
					} else {
						senderObject.sendSystemMessage("You can't send a trade request while you or your target is in combat.", (byte) 0);
					}
				}
			}
			
		});
		
		swgOpcodes.put(TradeOpcodes.AbortTradeMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				Client client = core.getClient(session);
				
				if (client == null)
					return;
				
				if (client.getSession().getAttribute("tradeSession") == null)
				{
					TradeCompleteMessage completeTrade = new TradeCompleteMessage();
					AbortTradeMessage nullResponse = new AbortTradeMessage();
					client.getSession().write(completeTrade.serialize());
					client.getSession().write(nullResponse.serialize());
				}
				
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradee = (CreatureObject) core.objectService.getObject(tradingWithClient);
				Client tradeeClient = tradee.getClient();
				
				
				if(client.getSession() == null)
					return;

				SWGObject object = client.getParent();
				SWGObject tradeeObject = tradeeClient.getParent();
				if(object == null)
					return;
				
				SWGObject tradeObject = null;
				
				Iterator<Map.Entry<SWGObject, Long>> itr = tradingObjectsTable.entrySet().iterator();
				
				while (itr.hasNext()) {
					Map.Entry<SWGObject, Long> entry = itr.next();
					
					if(tradingWithClient == entry.getValue()) {
						tradeObject = entry.getKey();
						itr.remove();
					}
				}
				
				client.getSession().removeAttribute("tradeSession");
				tradeeClient.getSession().removeAttribute("tradeSession");
				
				// make sure that tradeSession got removed.
				if (tradeeClient.getSession().containsAttribute("tradeSession") && client.getSession().containsAttribute("tradeSession"))
					return;
				
				TradeCompleteMessage completeTrade = new TradeCompleteMessage();
				AbortTradeMessage response = new AbortTradeMessage();

				tradeeClient.getSession().write(response.serialize());
				client.getSession().write(response.serialize());
				
				client.getSession().write(completeTrade.serialize());
				tradeeClient.getSession().write(completeTrade.serialize());
				
			}
			
			
		});
		
		swgOpcodes.put(TradeOpcodes.AddItemMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				AddItemMessage addItem = new AddItemMessage();
				addItem.deserialize(data);
				
				Client client = core.getClient(session);
				
				if (client == null)
					return;
				
				long tradeItemID = addItem.getTradeObjectID();
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradee = (CreatureObject) core.objectService.getObject(tradingWithClient);
				SWGObject objectToTrade = (SWGObject) core.objectService.getObject(tradeItemID);
				
				if (objectToTrade == null) {
					System.out.println("objectToTrade is null: " + tradeItemID);
					return;
				}
				
				else {
					if(objectToTrade.getAttributes().containsKey("no_trade") || !objectToTrade.getPermissions().canRemove(client.getParent(), objectToTrade.getContainer()) || (objectToTrade.getContainer() instanceof CreatureObject)) {
						return;
					}
					
					addItemForTrade(objectToTrade, tradingWithClient);
					//System.out.println("Trading item: " + objectToTrade.getCustomName() + " detail: " + objectToTrade.getDetailFilename());

					tradee.makeAware(objectToTrade);
					AddItemMessage tradeeResponse = new AddItemMessage();
					tradeeResponse.setTradeObjectID(tradeItemID);
					tradee.getClient().getSession().write(tradeeResponse.serialize());
					
					//System.out.println("AddItemMessage in TradeService: " + data.getHexDump());			
				}	
			}
			
		});
		
		swgOpcodes.put(TradeOpcodes.GiveMoneyMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				Client client = core.getClient(session);
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradingPartner = (CreatureObject) core.objectService.getObject(tradingWithClient);
				CreatureObject sender = (CreatureObject) client.getParent();
				
				GiveMoneyMessage fromClient = new GiveMoneyMessage();
				fromClient.deserialize(data);
				
				int givingCredits = fromClient.getTradingCredits();
				
				if (givingCredits <= sender.getCashCredits()) {
					
					addMoneyForTrade(tradingWithClient, givingCredits);
					
					GiveMoneyMessage toTradingPartner = new GiveMoneyMessage();
					toTradingPartner.setTradingCredits(givingCredits);
					
					tradingPartner.getClient().getSession().write(toTradingPartner.serialize());
				}
				
			}
			
		});
		
		swgOpcodes.put(TradeOpcodes.RemoveItemMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
				buffer.order(ByteOrder.LITTLE_ENDIAN);
				buffer.position(0);
				
				RemoveItemMessage request = new RemoveItemMessage();
				
				long objectToKeepID = request.getObjectID();
				
				Client client = core.getClient(session);
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradePartner = (CreatureObject) core.objectService.getObject(tradingWithClient);
				
				RemoveItemMessage response = new RemoveItemMessage();
				response.setObjectID(objectToKeepID);
				tradePartner.getClient().getSession().write(response.serialize());
				
				removeItemForTrade(core.objectService.getObject(objectToKeepID));
			}
			
		});
		
		swgOpcodes.put(TradeOpcodes.AcceptTransactionMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
				// client sends this packet to the server and then the server sends it to client.
				
				Client client = core.getClient(session);
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradePartner = (CreatureObject) core.objectService.getObject(tradingWithClient);
				
				AcceptTransactionMessage acceptedTrade = new AcceptTransactionMessage();
				tradePartner.getClient().getSession().write(acceptedTrade.serialize());
				
			}
			
		});
		
		swgOpcodes.put(TradeOpcodes.UnAcceptTransactionMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
				Client client = core.getClient(session);
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradePartner = (CreatureObject) core.objectService.getObject(tradingWithClient);
				
				UnAcceptTransactionMessage undoAccept = new UnAcceptTransactionMessage();
				tradePartner.getClient().getSession().write(undoAccept.serialize());
			}
			
		});
		
		swgOpcodes.put(TradeOpcodes.VerifyTradeMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {

				// Sent first!

				Client client = core.getClient(session);
				long tradingWithClient = (long) getTradeAttribute(client);
				
				CreatureObject tradePartner = (CreatureObject) core.objectService.getObject(tradingWithClient);
				CreatureObject actingTrader = (CreatureObject) client.getParent();
				SWGObject tradePartnerContainer = tradePartner.getContainer();
				
				SWGObject tradePartnerInventory = tradePartner.getSlottedObject("inventory");
				SWGObject actingTraderInventory = actingTrader.getSlottedObject("inventory");
				SWGObject tradeObject = null;
				
				//if (tradePartner == null || actingTrader == null)
					//return;
				
				//BeginVerificationMessage verifyMessage = new BeginVerificationMessage();
				//client.getSession().write(verifyMessage.serialize());
				//client.getSession().setAttribute("tradeSessionIsVerified");
				//tradePartner.getClient().getSession().write(verifyMessage.serialize());
				//tradePartner.getClient().getSession().setAttribute("tradeSessionIsVerified");
				
				client.getSession().setAttribute("tradeSessionIsVerified");
				
				if (tradePartner.getClient().getSession().getAttribute("tradeSessionIsVerified") == null)
					return;
				
				else if (tradePartner.getClient().getSession().containsAttribute("tradeSessionIsVerified"))
				{
					
					Iterator<Map.Entry<SWGObject, Long>> itr = tradingObjectsTable.entrySet().iterator();
					
					while (itr.hasNext()) {
						Map.Entry<SWGObject, Long> entry = itr.next();
						
						if(tradingWithClient == entry.getValue()) {
							tradeObject = entry.getKey();
							if (actingTrader == null || tradePartnerContainer == null || tradePartner == null)
							{
								cleanTradeSession(client, tradePartner.getClient());
							}
							actingTraderInventory.setContainerPermissions(AllPermissions.ALL_PERMISSIONS);
							tradePartnerInventory.setContainerPermissions(AllPermissions.ALL_PERMISSIONS);
							actingTraderInventory.transferTo(tradePartner, tradePartnerInventory, tradeObject);
							actingTraderInventory.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
							tradePartnerInventory.setContainerPermissions(CreatureContainerPermissions.CREATURE_CONTAINER_PERMISSIONS);
							
							//actingTraderInventory._remove(tradeObject);
							//actingTrader.makeUnaware(tradeObject);
							//tradePartnerInventory.add(tradeObject);
							
							itr.remove();
						}
						
					}
					
					int moneyToGive = 0;
					
					for(Map.Entry<Long, Integer> entry : tradingCreditsTable.entrySet()) {
						if(tradingWithClient == entry.getKey()) {
							moneyToGive = entry.getValue();
							tradingCreditsTable.remove(tradingWithClient);
						}
					}
					
					int tradePartnerCredits = tradePartner.getCashCredits();
					
					if (moneyToGive != 0) {
						actingTrader.setCashCredits(tradePartnerCredits - moneyToGive);
						tradePartner.setCashCredits(tradePartnerCredits + moneyToGive);
					}
					cleanTradeSession(client, tradePartner.getClient());
				}
				
			}
			
		});
		
	}
	
	public long getTradeAttribute(Client client) {
		long tradeSessionValue;
		tradeSessionValue = (long) client.getSession().getAttribute("tradeSession");
		return tradeSessionValue;
	}
	
	public Hashtable<SWGObject, Long> getTradingObjectMap() {
		return tradingObjectsTable;
	}
	
	public void addItemForTrade(SWGObject swgObject, long tradePartnerID) {
		if (tradingObjectsTable.containsKey(swgObject)) {
			removeItemForTrade(swgObject);
		}
		tradingObjectsTable.put(swgObject, tradePartnerID);
	}
	
	public void removeItemForTrade(SWGObject swgObject) {
		tradingObjectsTable.remove(swgObject);
	}
	
	public void addMoneyForTrade(long tradePartnerID, int creditsAmount) {
		if (tradingCreditsTable.containsKey(tradePartnerID))
		{
			removeMoneyForTrade(tradePartnerID);
		}
		tradingCreditsTable.put(tradePartnerID, creditsAmount);
	}
	
	public void removeMoneyForTrade(long tradePartnerID) {
		tradingCreditsTable.remove(tradePartnerID);
	}
	
	public void cleanTradeSession(Client actorClient, Client targetTradeClient) {
		
		TradeCompleteMessage partnerCompleteTrade = new TradeCompleteMessage();
		targetTradeClient.getSession().write(partnerCompleteTrade.serialize());
		
		TradeCompleteMessage clientCompleteTrade = new TradeCompleteMessage();
		actorClient.getSession().write(clientCompleteTrade.serialize());
		
		actorClient.getSession().removeAttribute("tradeSession");
		actorClient.getSession().removeAttribute("tradeSessionIsVerified");
		targetTradeClient.getSession().removeAttribute("tradeSession");
		targetTradeClient.getSession().removeAttribute("tradeSessionIsVerified");
		
	}
	
	@Override
	public void shutdown() {
		// XXX Auto-generated method stub
		
	}

}
