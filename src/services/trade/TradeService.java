package services.trade;

import java.nio.ByteOrder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.ObjControllerMessage;
import protocol.swg.clientSecureTradeManager.AbortTradeMessage;
import protocol.swg.clientSecureTradeManager.AcceptTransactionMessage;
import protocol.swg.clientSecureTradeManager.AddItemMessage;
import protocol.swg.clientSecureTradeManager.BeginTradeMessage;
import protocol.swg.clientSecureTradeManager.GiveMoneyMessage;
import protocol.swg.clientSecureTradeManager.RemoveItemMessage;
import protocol.swg.clientSecureTradeManager.TradeCompleteMessage;
import protocol.swg.clientSecureTradeManager.UnAcceptTransactionMessage;
import protocol.swg.objectControllerObjects.SecureTrade;
import resources.common.ObjControllerOpcodes;
import resources.common.Opcodes;
import resources.objects.creature.CreatureObject;
import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class TradeService implements INetworkDispatch{

	private NGECore core;
	
	private long recieverID;
	private long senderID;
	
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
					senderClient.getSession().setAttribute("tradeSession", recieverID);
					//System.out.println("Added trade session to " + senderClient.getParent().getCustomName() + " with the key of: " + senderClient.getSession().getAttribute("tradeSession").toString());
					
					recieverObject.sendSystemMessage(senderObject.getCustomName() + " wants to trade with you.", (byte) 0);		

				}
			}
			
		});
		
		swgOpcodes.put(Opcodes.AbortTradeMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradee = (CreatureObject) core.objectService.getObject(tradingWithClient);
				Client tradeeClient = tradee.getClient();
				
				
				if(client == null || client.getSession() == null)
					return;

				SWGObject object = client.getParent();
				SWGObject tradeeObject = tradeeClient.getParent();
				if(object == null)
					return;
				
				TradeCompleteMessage completeTrade = new TradeCompleteMessage();
				AbortTradeMessage response = new AbortTradeMessage();

				tradeeClient.getSession().write(response.serialize());
				client.getSession().write(response.serialize());
				
				client.getSession().removeAttribute("tradeSession");
				tradeeClient.getSession().removeAttribute("tradeSession");
				
				if (tradeeClient.getSession().containsAttribute("tradeSession") && client.getSession().containsAttribute("tradeSession"))
					return;
				
				client.getSession().write(completeTrade.serialize());
				tradeeClient.getSession().write(completeTrade.serialize());
				
			}
			
			
		});
		
		swgOpcodes.put(Opcodes.AddItemMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				AddItemMessage addItem = new AddItemMessage();
				addItem.deserialize(data);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				
				long tradeItemID = addItem.getTradeObjectID();
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradee = (CreatureObject) core.objectService.getObject(tradingWithClient);
				SWGObject objectToTrade = (SWGObject) core.objectService.getObject(tradeItemID);
				
				if (objectToTrade == null) {
					System.out.println("objectToTrade is null: " + tradeItemID);
					return;
				}
				
				System.out.println("Trading item: " + objectToTrade.getCustomName() + " detail: " + objectToTrade.getDetailFilename());
				
				AddItemMessage tradeeResponse = new AddItemMessage();
				tradeeResponse.setTradeObjectID(tradeItemID);
				tradee.getClient().getSession().write(tradeeResponse.serialize());
				
				//System.out.println("AddItemMessage in TradeService: " + data.getHexDump());
				
			}
			
		});
		
		swgOpcodes.put(Opcodes.GiveMoneyMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradingPartner = (CreatureObject) core.objectService.getObject(tradingWithClient);
				CreatureObject sender = (CreatureObject) client.getParent();
				
				GiveMoneyMessage fromClient = new GiveMoneyMessage();
				fromClient.deserialize(data);
				
				int givingCredits = fromClient.getTradingCredits();
				
				if (givingCredits <= sender.getCashCredits()) {
					
					GiveMoneyMessage toTradingPartner = new GiveMoneyMessage();
					toTradingPartner.setTradingCredits(givingCredits);
					
					tradingPartner.getClient().getSession().write(toTradingPartner.serialize());
				}
				
			}
			
		});
		
		swgOpcodes.put(Opcodes.RemoveItemMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
				buffer.order(ByteOrder.LITTLE_ENDIAN);
				buffer.position(0);
				
				RemoveItemMessage request = new RemoveItemMessage();
				
				long tradingObjectID = request.getObjectID();
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradePartner = (CreatureObject) core.objectService.getObject(tradingWithClient);
				
				RemoveItemMessage response = new RemoveItemMessage();
				response.setObjectID(tradingObjectID);
				tradePartner.getClient().getSession().write(response.serialize());
				
			}
			
		});
		
		swgOpcodes.put(Opcodes.AcceptTransactionMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
				// client sends this packet to the server and then the server sends it to client.
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradePartner = (CreatureObject) core.objectService.getObject(tradingWithClient);
				
				AcceptTransactionMessage acceptedTrade = new AcceptTransactionMessage();
				tradePartner.getClient().getSession().write(acceptedTrade.serialize());
				
			}
			
		});
		
		swgOpcodes.put(Opcodes.UnAcceptTransactionMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				long tradingWithClient = (long) client.getSession().getAttribute("tradeSession");
				
				CreatureObject tradePartner = (CreatureObject) core.objectService.getObject(tradingWithClient);
				
				UnAcceptTransactionMessage undoAccept = new UnAcceptTransactionMessage();
				tradePartner.getClient().getSession().write(undoAccept.serialize());
			}
			
		});
		
		// TODO: Add verification system and item lists
		
	}
	
	
	@Override
	public void shutdown() {
		// XXX Auto-generated method stub
		
	}

}
