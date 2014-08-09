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
package services.chat;

import java.nio.ByteOrder;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.config.Config;
import engine.resources.config.DefaultConfig;
import engine.resources.database.ODBCursor;
import engine.resources.database.ObjectDatabase;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.common.*;
import resources.datatables.DisplayType;
import resources.guild.Guild;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import protocol.swg.AddIgnoreMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.chat.ChatDeletePersistentMessage;
import protocol.swg.chat.ChatEnterRoomById;
import protocol.swg.chat.ChatFriendsListUpdate;
import protocol.swg.chat.ChatInstantMessageToCharacter;
import protocol.swg.chat.ChatInstantMessagetoClient;
import protocol.swg.chat.ChatOnAddFriend;
import protocol.swg.chat.ChatOnChangeFriendStatus;
import protocol.swg.chat.ChatOnEnteredRoom;
import protocol.swg.chat.ChatOnLeaveRoom;
import protocol.swg.chat.ChatOnSendInstantMessage;
import protocol.swg.chat.ChatOnSendPersistentMessage;
import protocol.swg.chat.ChatOnSendRoomMessage;
import protocol.swg.chat.ChatPersistentMessageToClient;
import protocol.swg.chat.ChatPersistentMessageToServer;
import protocol.swg.chat.ChatQueryRoom;
import protocol.swg.chat.ChatRequestPersistentMessage;
import protocol.swg.chat.ChatRoomList;
import protocol.swg.chat.ChatRoomMessage;
import protocol.swg.chat.ChatSendToRoom;
import protocol.swg.chat.ChatSystemMessage;
import protocol.swg.objectControllerObjects.PlayerEmote;
import protocol.swg.objectControllerObjects.SpatialChat;
import main.NGECore;

public class ChatService implements INetworkDispatch {
	
	private NGECore core;
	private ObjectDatabase mailODB;
	private ObjectDatabase chatRoomsODB;
	private ConcurrentHashMap<Integer, ChatRoom> chatRooms = new ConcurrentHashMap<Integer, ChatRoom>();
	
	public ChatService(NGECore core) {
		this.core = core;
		core.commandService.registerCommand("spatialchatinternal");
		core.commandService.registerCommand("socialinternal");
		
		core.commandService.registerCommand("addignore");
		core.commandService.registerCommand("removeignore");
		core.commandService.registerCommand("findfriend");
		core.commandService.registerCommand("addfriend");
		core.commandService.registerCommand("removefriend");

		core.commandService.registerAlias("g", "groupchat");
		core.commandService.registerAlias("gc", "groupchat");
		core.commandService.registerAlias("groupsay", "groupchat");
		core.commandService.registerAlias("gsay", "groupchat");
		core.commandService.registerAlias("gtell", "groupchat");
		
		core.commandService.registerAlias("planet", "planetchat");
		
		mailODB = core.getMailODB();
		chatRoomsODB = core.getChatRoomODB();

	}
	
	/*
	 * This gets used by NPCs as well (random shouts, mustafar miners, etc).
	 */
	public void spatialChat(SWGObject speaker, SWGObject target, String chatMessage, short chatType, short moodId, int languageId, OutOfBand outOfBand) {
		long targetId;
		
		if (target == null) {
			targetId = 0;
		} else {
			targetId = target.getObjectID();
		}
		
		ObjControllerMessage objControllerMessage = new ObjControllerMessage(0x0B, new SpatialChat(speaker.getObjectId(), speaker.getObjectID(), targetId, chatMessage, chatType, moodId, languageId, outOfBand));
		
		Client speakerClient = speaker.getClient();
		
//		if (speakerClient == null || speakerClient.getSession() == null) {
//			return;
//		}
		
		if (speakerClient != null) {
			if (speakerClient.getSession() != null)
				speakerClient.getSession().write(objControllerMessage.serialize());
		}
		
		
		if (speaker.getObservers().isEmpty()) {
			return;
		}
		
		HashSet<Client> observers = new HashSet<Client>(speaker.getObservers());
		
		Point3D position = speaker.getPosition();
		
		for (Client client : observers) {
			float distance = client.getParent().getPosition().getDistance2D(position);
			if (client != null && client.getSession() != null && distance <= 80) {
				if (((PlayerObject) client.getParent().getSlottedObject("ghost")).getIgnoreList().contains(speaker.getCustomName().toLowerCase().split(" ")[0])) {
					continue;
				}
				
				String message = chatMessage;
				SpatialChat spatialChat = new SpatialChat(client.getParent().getObjectId(), speaker.getObjectID(), targetId, message, chatType, moodId, languageId, outOfBand);
				objControllerMessage = new ObjControllerMessage(0x0B, spatialChat);
				client.getSession().write(objControllerMessage.serialize());
				tools.CharonPacketUtils.printAnalysis(objControllerMessage.serialize());
			}
		}
	}
	
	public void handleEmote(SWGObject speaker, SWGObject target, short emoteId) {
		
		long targetId;
		
		if(target == null)
			targetId = 0;
		else
			targetId = target.getObjectID();

		PlayerEmote emote = new PlayerEmote(speaker.getObjectID(), targetId, emoteId);
		ObjControllerMessage objControllerMessage = new ObjControllerMessage(0x0B, emote);
		//System.out.println("Emote ID: " + emoteId);
		Client speakerClient = speaker.getClient();
		
		if(speakerClient == null || speakerClient.getSession() == null)
			return;
			
		speakerClient.getSession().write(objControllerMessage.serialize());
		
		if(speaker.getObservers().isEmpty())
			return;
		
		HashSet<Client> observers = new HashSet<Client>(speaker.getObservers());
		
		Point3D position = speaker.getPosition();

		for(Client client : observers) {
			float distance = client.getParent().getPosition().getDistance2D(position);
			if(client != null && client.getSession() != null && distance <= 80) {
				emote.setDestinationId(client.getParent().getObjectID());
				ObjControllerMessage objControllerMessage2 = new ObjControllerMessage(0x0B, emote);
				client.getSession().write(objControllerMessage2.serialize());
			}
		}

	}

	@Override
	public void insertOpcodes( Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {

		swgOpcodes.put(Opcodes.ChatInstantMessageToCharacter, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);

				ChatInstantMessageToCharacter chatInstantMsg = new ChatInstantMessageToCharacter();
				chatInstantMsg.deserialize(data);
				String firstName = chatInstantMsg.getRecipient();
				
				Client client = core.getClient(session);
				
				if(client == null)
					return;
				
				SWGObject sender = client.getParent();
				
				if(sender == null)
					return;
				
				SWGObject recipient = getObjectByFirstName(firstName);				
				
				if (recipient == null || !recipient.isInQuadtree())
					return;
				
				PlayerObject recipientGhost = (PlayerObject) recipient.getSlottedObject("ghost");
				
				if (recipientGhost.getIgnoreList().contains(sender.getCustomName().toLowerCase())) 
					return;
				
				if(recipient == null || recipient.getClient() == null || recipient.getClient().getSession() == null) {
					ChatOnSendInstantMessage response = new ChatOnSendInstantMessage(4, chatInstantMsg.getSequence());
					//System.out.println(response.serialize().getHexDump());
					session.write(response.serialize());
				} else {
					ChatOnSendInstantMessage response = new ChatOnSendInstantMessage(0, chatInstantMsg.getSequence());
					session.write(response.serialize());
					ChatInstantMessagetoClient msg = new ChatInstantMessagetoClient(chatInstantMsg.getGalaxy(), chatInstantMsg.getMessage(), sender.getCustomName().split(" ")[0]);
					recipient.getClient().getSession().write(msg.serialize());
				}
				
				
			}
			
		});
		
		swgOpcodes.put(Opcodes.ChatPersistentMessageToServer, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);

				ChatPersistentMessageToServer packet = new ChatPersistentMessageToServer();
				packet.deserialize(data);
				
				Client client = core.getClient(session);
				
				if(client == null)
					return;
				
				SWGObject sender = client.getParent();
				
				if(sender == null)
					return;

				switch (packet.getRecipient()) {
					case "citizens": break;
					case "guild":
						Guild guild = core.guildService.getGuildById(((CreatureObject) sender).getGuildId());
						if (guild == null || !guild.getMembers().containsKey(sender.getObjectID())) {
							ChatOnSendPersistentMessage response = new ChatOnSendPersistentMessage(4, packet.getCounter());
							session.write(response.serialize());
							return;
						}
						
						if (!guild.getMember(sender.getObjectID()).hasMailPermission()) {
							((CreatureObject) sender).sendSystemMessage("@guild:generic_fail_no_permission", (byte) 0);
							ChatOnSendPersistentMessage response = new ChatOnSendPersistentMessage(4, packet.getCounter());
							session.write(response.serialize());
							return;
						}
						guild.sendGuildMail(sender.getCustomName(), packet.getSubject(), packet.getMessage());
						
						ChatOnSendPersistentMessage response = new ChatOnSendPersistentMessage(0, packet.getCounter());
						session.write(response.serialize());
						return;
					case "group": break;
					
					default: break;
					// TODO: Guild ranks
				}

				SWGObject recipient = core.objectService.getObjectByFirstName(packet.getRecipient());
				
				if (recipient == null)
					return;
				
				PlayerObject recipientGhost = (PlayerObject) recipient.getSlottedObject("ghost");
				
				if (recipientGhost.getIgnoreList().contains(sender.getCustomName().toLowerCase())) 
					return;
				
				if(recipient == null || recipient.getSlottedObject("ghost" ) == null) {
					ChatOnSendPersistentMessage response = new ChatOnSendPersistentMessage(4, packet.getCounter());
					session.write(response.serialize());
					//System.out.println(packet.getRecipient());
				} else {
					Date date = new Date();
					Mail mail = new Mail();
					mail.setMailId(generateMailId());
					mail.setMessage(packet.getMessage());
					mail.setRecieverId(recipient.getObjectID());
					mail.setSenderName(sender.getCustomName().split(" ")[0]);
					mail.setStatus(Mail.NEW);
					mail.setSubject(packet.getSubject());
					mail.setTimeStamp((int) (date.getTime() / 1000));
					mail.setWaypointAttachments(packet.getWaypointAttachments());
					storePersistentMessage(mail);
					
					if(recipient.getClient() != null) {
						sendPersistentMessageHeader(recipient.getClient(), mail);
					}
					
					ChatOnSendPersistentMessage response = new ChatOnSendPersistentMessage(0, packet.getCounter());
					session.write(response.serialize());
				}
			}
			
		});

		swgOpcodes.put(Opcodes.ChatRequestPersistentMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);

				ChatRequestPersistentMessage packet = new ChatRequestPersistentMessage();
				packet.deserialize(data);
				
				Client client = core.getClient(session);
				
				if(client == null)
					return;
				
				SWGObject obj = client.getParent();
				Mail mail = (Mail) mailODB.get((long) packet.getMailId());
				
				if(obj == null || mail == null)
					return;

				if(mail.getRecieverId() != obj.getObjectID())
					return;

				mail.setStatus(Mail.READ);
				mail.init();
				sendPersistentMessage(client, mail);
				
				storePersistentMessage(mail);

			}

		});
		
		swgOpcodes.put(Opcodes.ChatDeletePersistentMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				ChatDeletePersistentMessage packet = new ChatDeletePersistentMessage();
				packet.deserialize(data);
				
				Client client = core.getClient(session);
				
				if(client == null)
					return;
				
				SWGObject obj = client.getParent();
				Mail mail = (Mail) mailODB.get((long) packet.getMailId());
				
				if(obj == null || mail == null)
					return;

				if(mail.getRecieverId() != obj.getObjectID())
					return;
				
				deletePersistentMessage(mail);
			}

		});
		
		swgOpcodes.put(Opcodes.ChatRequestRoomList, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				Client client = core.getClient(session);
				
				if(client == null)
					return;
				
				SWGObject obj = client.getParent();
				
				if (obj == null)
					return;
				
				ChatRoomList listMessage = new ChatRoomList(chatRooms);
				client.getSession().write(listMessage.serialize());
				
			}

		});
		
		swgOpcodes.put(Opcodes.ChatCreateRoom, (session, data) -> {
			/*data.order(ByteOrder.LITTLE_ENDIAN);
			
			Client client = core.getClient(session);
			
			if(client == null)
				return;
			
			SWGObject obj = client.getParent();
			
			if (obj == null)
				return;
			
			CreatureObject creo = (CreatureObject) obj;
			
			ChatCreateRoom sentPacket = new ChatCreateRoom();
			sentPacket.deserialize(data);
			
			ChatRoom room = createChatRoom(sentPacket.getTitle(), sentPacket.getAddress(), creo.getCustomName().toLowerCase(), true, false);

			if (room != null) {
				room.setPrivateRoom(sentPacket.isPrivacy());
				room.setModeratorsOnly(sentPacket.isModeratorOnly());
				room.addUser(creo.getCustomName());
				room.addModerator(creo.getCustomName());
				ChatOnCreateRoom response = new ChatOnCreateRoom(room, 0, sentPacket.getRequest());
				session.write(response.serialize());
			}
			
			System.out.println("Created room.");*/
			
		});
		
		swgOpcodes.put(Opcodes.ChatQueryRoom, (session, data) -> {
			Client client = core.getClient(session);
			
			if(client == null)
				return;
			
			SWGObject obj = client.getParent();
			
			if (obj == null)
				return;
			
			data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);
			ChatQueryRoom request = new ChatQueryRoom();
			request.deserialize(data);
			
			ChatRoom room = getChatRoomByAddress(request.getRoomAddress());
			
			if (room == null)
				return;

			ChatQueryRoom response = new ChatQueryRoom(room, request.getRequestId());
			obj.getClient().getSession().write(response.serialize());
		});
		
		swgOpcodes.put(Opcodes.ChatSendToRoom, (session, data) -> {

			Client client = core.getClient(session);
			
			if(client == null)
				return;
			
			SWGObject obj = client.getParent();
			
			if (obj == null)
				return;
			
			data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);
			ChatSendToRoom sentPacket = new ChatSendToRoom();
			sentPacket.deserialize(data);
			
			if (((PlayerObject) obj.getSlottedObject("ghost")).isMemberOfChannel(sentPacket.getRoomId()))
				sendChatRoomMessage((CreatureObject) obj, sentPacket.getRoomId(), sentPacket.getMsgId(), sentPacket.getMessage());

		});
		
		swgOpcodes.put(Opcodes.ChatEnterRoomById, (session, data) -> {
			Client client = core.getClient(session);
			
			if(client == null)
				return;
			
			SWGObject obj = client.getParent();
			
			if (obj == null)
				return;

			data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);
			ChatEnterRoomById sentPacket = new ChatEnterRoomById();
			sentPacket.deserialize(data);
			
			if(joinChatRoom(obj.getCustomName(), sentPacket.getRoomId()) && !sentPacket.getRoomname().equals("SWG." + core.getGalaxyName() + "." + obj.getPlanet().name + ".Planet")) {
				PlayerObject player = (PlayerObject) obj.getSlottedObject("ghost");
				
				if (player != null)
					player.addChannel(sentPacket.getRoomId());
			}
		});
		
		swgOpcodes.put(Opcodes.ChatLeaveRoom, (session, data) -> {
			Client client = core.getClient(session);
			
			if(client == null)
				return;
			
			SWGObject obj = client.getParent();
			
			if (obj == null)
				return;

			data.order(ByteOrder.LITTLE_ENDIAN);
			data.position(0);
			
			ChatOnLeaveRoom sentPacket = new ChatOnLeaveRoom();
			sentPacket.deserialize(data);
			
			ChatRoom room = getChatRoomByAddress(sentPacket.getChannelAddress());
			
			leaveChatRoom((CreatureObject) obj, room.getRoomId(), true);
			
		});
	}
	
	public void playerStatusChange(String name, byte status) {
		
		String shortName = name.toLowerCase();
		ConcurrentHashMap<IoSession, Client> clients = core.getActiveConnectionsMap();
		
		for(Client client : clients.values()) {

			if(client.getParent() == null)
				continue;
			
			PlayerObject clientGhost = (PlayerObject) client.getParent().getSlottedObject("ghost");
			
			if(clientGhost == null)
				continue;
			
			if (clientGhost.getFriendList().contains(shortName)) {
				// online/offline message
				ChatFriendsListUpdate updateNotifyStatus = new ChatFriendsListUpdate(name, (byte) status);
				client.getSession().write(updateNotifyStatus.serialize());
				
			}
			
		}
	}
	
	public void removeFriend(PlayerObject actor, String friendName) {
		CreatureObject creature = (CreatureObject) actor.getContainer();
		friendName = friendName.split(" ")[0].toLowerCase();
		
		if (actor == null || creature == null || friendName.equals(""))
			return;
		
		if(actor.getFriendList().contains(friendName)) {

			ChatOnChangeFriendStatus removeMessage = new ChatOnChangeFriendStatus(actor.getContainer().getObjectID(), friendName, 0);
			creature.getClient().getSession().write(removeMessage.serialize());
			
			actor.friendRemove(friendName);
		} else {
			creature.sendSystemMessage(friendName + " is not a valid friend name.", (byte) 0);
		}
	}
	
	public void addFriend(PlayerObject actor, String friend) {
		CreatureObject creature = (CreatureObject) actor.getContainer();
		
		if (actor == null || creature == null || friend.equals(""))
			return;
		
		friend = friend.toLowerCase();
		
		if (friend.contains(" ")) {
			friend = friend.split(" ")[0];
		}
		
		if(core.characterService.playerExists(friend)) {
			
			if(actor.getIgnoreList().contains(friend)) {
				creature.sendSystemMessage(friend + " is being ignored, unable to put in your friends list.", (byte) 0);
			}
			
			SWGObject friendObj = getObjectByFirstName(friend);
			boolean isOnline = false;
			
			if (friendObj != null && friendObj.isInQuadtree())
				isOnline = true;
			
			ChatOnAddFriend init = new ChatOnAddFriend();
			creature.getClient().getSession().write(init.serialize());

			ChatOnChangeFriendStatus addFriend = new ChatOnChangeFriendStatus(creature.getObjectId(), friend, 1);
			creature.getClient().getSession().write(addFriend.serialize());
				
			if (isOnline) {
				ChatFriendsListUpdate onlineUpdate = new ChatFriendsListUpdate(friend, (byte) 1);
				creature.getClient().getSession().write(onlineUpdate.serialize());
			}
				
			actor.friendAdd(friend);
			creature.sendSystemMessage(friend + " is now your friend.", (byte) 0);
			
		} else {
			creature.sendSystemMessage(friend + " is not a valid friend name.", (byte) 0);
		}
	}
	
	public void addToIgnoreList(SWGObject actor, String ignoreName) {
		ignoreName = ignoreName.split(" ")[0].toLowerCase();
		if (actor == null)
			return;
		
		PlayerObject ghost = (PlayerObject) actor.getSlottedObject("ghost");
		CreatureObject creature = (CreatureObject) actor;
		
		if(ghost == null)
			return;
		
		if (ghost.getIgnoreList().contains(ignoreName)) {
			creature.sendSystemMessage(ignoreName + " is already in your ignore list.", (byte) 0);
			return;
		}
		
		if(!core.characterService.playerExists(ignoreName)) {
			creature.sendSystemMessage(ignoreName + " is not a valid ignore name.", (byte) 0);
			return;
		}
		
		AddIgnoreMessage addIgnore = new AddIgnoreMessage(actor, ignoreName, true);
		actor.getClient().getSession().write(addIgnore.serialize());
		ghost.ignoreAdd(ignoreName);
			
		creature.sendSystemMessage(ignoreName + " is now ignored.", (byte) 0);

	}
	
	public void removeFromIgnoreList(SWGObject actor, String ignoreName) {
		if (actor == null)
			return;
		
		PlayerObject ghost = (PlayerObject) actor.getSlottedObject("ghost");
		CreatureObject creature = (CreatureObject) actor;
		
		if (ghost == null || creature == null)
			return;
		
		if (ghost.getIgnoreList().contains(ignoreName)) {
			AddIgnoreMessage message = new AddIgnoreMessage(actor, ignoreName, false);
			creature.getClient().getSession().write(message.serialize());
			
			ghost.ignoreRemove(ignoreName);
			
			creature.sendSystemMessage(ignoreName + " is no longer ignored.", (byte) 0);
		}
	}
	
	public void sendPersistentMessageHeader(Client client, Mail mail) {
		
		if(client.getSession() == null)
			return;
		
		Config config = new Config();
		config.setFilePath("nge.cfg");
		if (!(config.loadConfigFile())) {
			config = DefaultConfig.getConfig();
		}
		
		//System.out.println(config.getString("GALAXY_NAME"));
		
		ChatPersistentMessageToClient msg = new ChatPersistentMessageToClient(mail.getSenderName(), config.getString("GALAXY_NAME"), mail.getMailId()
				,(byte) 1, "", mail.getSubject(), mail.getStatus(), mail.getTimeStamp(), mail.getWaypointAttachments(), mail.getProseAttachments());
		
		client.getSession().write(msg.serialize());
	}
	
	public void sendPersistentMessage(Client client, Mail mail) {
		
		if(client.getSession() == null)
			return;
		
		Config config = new Config();
		config.setFilePath("nge.cfg");
		if (!(config.loadConfigFile())) {
			config = DefaultConfig.getConfig();
		}
		
		//System.out.println(config.getString("GALAXY_NAME"));
		
		ChatPersistentMessageToClient msg = new ChatPersistentMessageToClient(mail.getSenderName(), config.getString("GALAXY_NAME"), mail.getMailId()
				,(byte) 0, mail.getMessage(), mail.getSubject(), mail.getStatus(), mail.getTimeStamp(), mail.getWaypointAttachments(), mail.getProseAttachments());
		
		client.getSession().write(msg.serialize());
	}

	
	public void storePersistentMessage(Mail mail) {
		mailODB.put((long) mail.getMailId(), mail);
	}
	
	public void deletePersistentMessage(Mail mail) {
		mailODB.remove((long) mail.getMailId());
	}
	
	public void loadMailHeaders(Client client) {
		
		SWGObject obj = client.getParent();
		
		if(obj == null || client.getSession() == null)
			return;
		
		ODBCursor cursor = mailODB.getCursor();
		
		while(cursor.hasNext()) {
			Mail mail = (Mail) cursor.next();
			if(mail == null)
				continue;
			if(mail.getRecieverId() == obj.getObjectID()) {
				sendPersistentMessageHeader(client, mail);
			}

		}
		
		cursor.close();
	}

	@Override
	public void shutdown() {
		
	}
	
	public int generateMailId() {
		Random rand = new Random();
		
		int id = rand.nextInt();
		
		if(mailODB.contains((long) id))
			return generateMailId();
		else
			return id;
	}
	
	public Mail getMailById(int mailId) {
		
		Mail mail = (Mail) mailODB.get((long) mailId);
		return mail;
		
	}
	public ChatRoom getChatRoomByAddress(String address) {

		for (Entry<Integer, ChatRoom> entry : chatRooms.entrySet()) {
			if (entry.getValue().getRoomAddress().equals(address))
				return entry.getValue();
		}
		return null;
	}

	public ChatRoom getChatRoom(int roomId) {
		return chatRooms.get(roomId);
	}
	
	public int generateChatRoomId() {
		Random rand = new Random();
		int id = rand.nextInt();
		
		if (chatRoomsODB.contains((long) id))
			return generateChatRoomId();
		else
			return id;
	}
	
	public void broadcastGalaxy(String message) {
		core.simulationService.notifyAllClients(new ChatSystemMessage(message, new OutOfBand(), DisplayType.Broadcast).serialize());
	}
	
	public void loadChatRooms() {
		
		/*
		 * Group Channel:
		 * 	SWG.serverName.group.groupObjectId.GroupChat
		 * Battlefields channel format:
		 * 	SWG.serverName.battlefield.bfMapName
		 * 
		 * TODO: Research other channel address formats
		 */
		
		createChatRoom("", "SWG", "system", false);
		createChatRoom("", "SWG." + core.getGalaxyName(), "system", false);
		createChatRoom("", "SWG." + core.getGalaxyName() + ".Chat", "system", false);
		
		createChatRoom("", "system", "system", true); // galaxy system messages
		
		createChatRoom("", "group", "system", false);
		createChatRoom("", "guild", "system", false);
		
		createChatRoom("Auction chat for this galaxy", "Auction", "system", true);
		createChatRoom("public chat for the whole galaxy, cannot create rooms here", "Galaxy", "system", true);
		
		createChatRoom("Bounty Hunter chat for this galaxy", "BountyHunter", "system", true);
		createChatRoom("Commando chat for this galaxy", "Commando", "system", true);
		createChatRoom("Officer chat for this galaxy", "Officer", "system", true);
		createChatRoom("Entertainer chat for this galaxy", "Entertainer", "system", true);
		createChatRoom("Spy chat for this galaxy", "Spy", "system", true);
		createChatRoom("Force Sensitive chat for this galaxy", "ForceSensitive", "system", true);

		createChatRoom("Politician chat for this galaxy", "Politician", "system", true);
		//createChatRoom("Pilot chat for this galaxy", "Pilot", "system", true);
		
		ODBCursor cursor = chatRoomsODB.getCursor();
		
		while(cursor.hasNext()) {
			ChatRoom room = (ChatRoom) cursor.next();
			if (!chatRooms.containsValue(room))
				chatRooms.put(room.getRoomId(), room);
		}
		cursor.close();
		
		List<Planet> planets = core.terrainService.getPlanetList();
		planets.forEach(planet -> {
			createChatRoom("", planet.getName(), "system", true, true);
			createChatRoom("", planet.getName() + ".Chat", "system", true, false);
			createChatRoom("public chat for this planet, cannot create rooms here", planet.getName() + ".Planet", "system", true, false);
			createChatRoom("system messages for this planet, cannot create rooms here", planet.getName() + ".system", "system", true, false);
			Console.println("Created chat rooms for " + planet.getName());
		});
	}
	
	/**
	 * Creates a new ChatRoom that is not persistent and does not allow children.
	 * @param roomName Name of the room, description
	 * @param address Address of the room. Defaults to SWG.serverName + the value of this variable if it does not contain it.
	 * @param creator Creator of the room. Also set as the owner of the room.
	 * @param isPublic Determines weather or not the channel will show in the list of channels.
	 * @return {@link ChatRoom}
	 */
	public ChatRoom createChatRoom(String roomName, String address, String creator, boolean isPublic) {
		return createChatRoom(roomName, address, creator, isPublic, false, false);
	}

	/**
	 * Creates a new ChatRoom with the given values that is not persistent.
	 * @param roomName Name of the room, description
	 * @param address Address of the room. Defaults to SWG.serverName + the value of this variable if it does not contain it.
	 * @param creator Creator of the room. Also set as the owner of the room.
	 * @param isPublic Determines weather or not the channel will show in the list of channels.
	 * @param childrenAllowed Setting this to true allows players to create channels within it.
	 * @return {@link ChatRoom}
	 */
	public ChatRoom createChatRoom(String roomName, String address, String creator, boolean isPublic, boolean childrenAllowed) {
		return createChatRoom(roomName, address, creator, isPublic, false, childrenAllowed);
	}

	/**
	 * Creates a new ChatRoom with the given values.
	 * @param roomName Name of the room, description
	 * @param address Address of the room. Defaults to SWG.serverName + the value of this variable if it does not contain it.
	 * @param creator Creator of the room. Also set as the owner of the room.
	 * @param isPublic Determines weather or not the channel will show in the list of channels.
	 * @param store Setting this to true will make the channel persistent through server restarts.
	 * @param childrenAllowed Setting this to true allows players to create channels within it.
	 * @return {@link ChatRooms}
	 */
	public ChatRoom createChatRoom(String roomName, String address, String creator, boolean isPublic, boolean store, boolean childrenAllowed) {

		if (creator.contains(" "))
			creator = creator.split(" ")[0];
		
		ChatRoom room = new ChatRoom();
		room.setDescription(roomName);
		if (!address.startsWith("SWG"))
			room.setRoomAddress("SWG." + core.getGalaxyName() + "." + address);
		else
			room.setRoomAddress(address);
		room.setCreator(creator.toLowerCase());
		room.setOwner(creator.toLowerCase());
		room.setVisible(isPublic);
		room.setRoomId(generateChatRoomId());
		room.setChildrenAllowed(childrenAllowed);
		
		chatRooms.put(room.getRoomId(), room);
		
		if(store){
			chatRoomsODB.put((long) room.getRoomId(), room);
		}
		
		//Console.println("Created room " + address + " with ID " + room.getRoomId());
		
		return room;
	}
	
	public boolean joinChatRoom(String user, String roomAddress) {
		chatRooms.forEach((k, v) -> {
			if (v.getRoomAddress().equals(roomAddress)) {
				joinChatRoom(user, k, true);
				return;
			}
		});
		return false;
	}
	public boolean joinChatRoom(String user, int roomId, boolean resendList) {
		
		if (user.contains(" "))
			user = user.split(" ")[0];
		
		ChatRoom room = getChatRoom(roomId);
		if (room == null)
			return false;
		if (!room.hasUser(user.toLowerCase())) {

			if (!room.isVisible() || resendList) {
				CreatureObject creo = (CreatureObject) getObjectByFirstName(user);
				if (creo != null) {
					ChatRoomList listMessage = new ChatRoomList(room);
					creo.getClient().getSession().write(listMessage.serialize());
				}
			}
			ChatOnEnteredRoom enter = new ChatOnEnteredRoom(user, 0, room.getRoomId(), true);
			SWGObject player = getObjectByFirstName(user);
			
			if (player == null || player.getClient() == null || player.getClient().getSession() == null)
				return false;
			
			player.getClient().getSession().write(enter.serialize());
			
			for (String roomUser : room.getUserList()) {
				SWGObject roomPlayer = getObjectByFirstName(roomUser);
				
				if (roomPlayer != null && roomPlayer.getClient() != null && roomPlayer.getClient().getSession() != null)
					roomPlayer.getClient().getSession().write(enter.serialize());
			}
			
			room.addUser(user.toLowerCase());
			if (!((CreatureObject) player).getPlayerObject().isMemberOfChannel(roomId)) {
				((CreatureObject) player).getPlayerObject().addChannel(roomId);
			}
			return true;
		}
		return false;
	}
	
	public boolean joinChatRoom(String user, int roomId) {
		return joinChatRoom(user, roomId, false);
	}
	
	public void leaveChatRoom(CreatureObject player, int roomId, boolean removeFromList) {
		
		ChatRoom room = getChatRoom(roomId);
		if (room == null)
			return;
		
		String playerName = player.getCustomName().toLowerCase();
		
		if (playerName.contains(" "))
			playerName = playerName.split(" ")[0];
		
		ChatOnEnteredRoom leaveRoom = new ChatOnEnteredRoom(playerName, 0, roomId, false);
		
		if (player != null && player.getClient() != null && player.getClient().getSession() != null) {
			player.getClient().getSession().write(leaveRoom.serialize());
		}
		
		room.getUserList().remove(playerName);
		
		room.getUserList().forEach(user -> {
			SWGObject roomPlayer = getObjectByFirstName(user);
			
			if (roomPlayer != null && roomPlayer.getClient() != null && roomPlayer.getClient().getSession() != null) {
				roomPlayer.getClient().getSession().write(leaveRoom.serialize());
			}
		});
		
		if (removeFromList)
			((PlayerObject) player.getSlottedObject("ghost")).removeChannel((Integer) roomId);
	}
	
	public void sendChatRoomMessage(CreatureObject sender, int roomId, int msgId, String message) {
		ChatRoom room = getChatRoom(roomId);
		
		if (room == null)
			return;
		
		String senderName = sender.getCustomName();
		
		if (senderName.contains(" "))
			senderName = senderName.split(" ")[0];
		
		if (message.startsWith("\\#"))
			message = " " + message;
		
		ChatOnSendRoomMessage onSend = new ChatOnSendRoomMessage(0, msgId);
		sender.getClient().getSession().write(onSend.serialize());
		
		ChatRoomMessage roomMessage = new ChatRoomMessage(roomId, senderName, message);
		Vector<String> users = room.getUserList();
		users.forEach(user -> {
			SWGObject player = getObjectByFirstName(user);
			if (player != null && player.getClient() != null && player.getClient().getSession() != null) {
				player.getClient().getSession().write(roomMessage.serialize());
			}
		});
	}
	
	public ConcurrentHashMap<Integer, ChatRoom> getChatRooms() {
		return chatRooms;
	}
	
	/*
	 * Language interpretation.
	 * Converts messages into a language if the receiver can't comprehend it
	 */
	public String interpret(String message, int languageId, CreatureObject receiver) {
		try {
			DatatableVisitor visitor = ClientFileManager.loadFile("datatables/game_language/game_language.iff", DatatableVisitor.class);
			
			if (visitor.getObject(0, 0) == null) {
				return message;
			}
			
			String comprehendSkillMod = (String) visitor.getObject(languageId, 1);
			
			if (receiver.getSkillModBase(comprehendSkillMod) > 0) {
				return message;
			}
			
			for (int l = 3; l < 29; l++) {
				String letter = ((String) visitor.getObject(0, l));
				String replacement = ((String) visitor.getObject(languageId, l));
				
				if (replacement.length() > 0) {
					message = message.replace(letter, replacement);
				}	
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	@Deprecated
	public SWGObject getObjectByFirstName(String name) {
		return core.objectService.getObjectByFirstName(name);
	}
	
	
}
