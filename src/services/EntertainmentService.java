package services;

import java.nio.ByteOrder;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.ObjControllerMessage;
import protocol.swg.OkMessage;
import protocol.swg.objectControllerObjects.BuffBuilderChangeMessage;
import protocol.swg.objectControllerObjects.BuffBuilderStartMessage;
import resources.common.BuffBuilder;
import resources.common.Console;
import resources.common.ObjControllerOpcodes;
import resources.objects.Buff;
import resources.objects.BuffItem;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class EntertainmentService implements INetworkDispatch {

	private NGECore core;
	
	private Vector<BuffBuilder> buffBuilderSkills = new Vector<BuffBuilder>();
	
	public EntertainmentService(NGECore core) {
		this.core = core;
		populateSkillCaps();
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		objControllerOpcodes.put(ObjControllerOpcodes.BUFF_BUILDER_CHANGE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				Console.println("BUFF_BUILDER_CHANGE RECIEVED");
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				
				if(client == null)
					return;
				
				SWGObject sender = client.getParent();
				
				if(sender == null)
					return;
				
				BuffBuilderChangeMessage sentPacket = new BuffBuilderChangeMessage();
				sentPacket.deserialize(data);

				Vector<BuffItem> statBuffs = sentPacket.getStatBuffs();
				
				/*for (BuffItem item : statBuffs) {
					System.out.println("Buff Name: " + item.getSkillName());
					System.out.println("Ents bonus to item: " + item.getAmount());
				}*/
				
				SWGObject buffer = core.objectService.getObject(sentPacket.getBufferId());
				SWGObject buffRecipient = core.objectService.getObject(sentPacket.getBuffRecipientId());
				
				Console.println("Packet Sender ID: " + sender.getObjectId());
				
				if (buffer != buffRecipient) {
					
					BuffBuilderChangeMessage changeMessage = new BuffBuilderChangeMessage();
					changeMessage.setBuffCost(sentPacket.getBuffCost());
					changeMessage.setTime(sentPacket.getTime());
					changeMessage.setBufferId(buffer.getObjectId());
					changeMessage.setBuffRecipientId(buffRecipient.getObjectId());
					
					if (!statBuffs.isEmpty())
						changeMessage.setStatBuffs(statBuffs);
					
					if (sentPacket.getAccepted() == true && sentPacket.getBuffRecipientAccepted() == 1) {
						System.out.println("both accepted!");
						OkMessage closeMsg = new OkMessage();
						buffer.getClient().getSession().write(closeMsg.serialize());
						
						// TODO: Give Buff
						
					} else if (sentPacket.getAccepted() == true && sentPacket.getBuffRecipientAccepted() == 0) {
						changeMessage.setAccepted(true);
						changeMessage.setObjectId(buffRecipient.getObjectId());

						ObjControllerMessage objMsg = new ObjControllerMessage(0x0B, changeMessage);
						buffRecipient.getClient().getSession().write(objMsg.serialize());
					} else {
						changeMessage.setAccepted(false);
						changeMessage.setObjectId(sentPacket.getBuffRecipientId());
						
						ObjControllerMessage objMsg = new ObjControllerMessage(0x0B, changeMessage);
						buffRecipient.getClient().getSession().write(objMsg.serialize());
					}
					
				} else {

				}
			}
			
		});
	}

	private void populateSkillCaps() {
		try {
			DatatableVisitor buffBuilder = ClientFileManager.loadFile("datatables/buff/buff_builder.iff", DatatableVisitor.class);
			
			for (int r = 0; r < buffBuilder.getRowCount(); r++) {
				String skillName = ((String) buffBuilder.getObject(r, 0));
				String statAffects = ((String) buffBuilder.getObject(r, 2));
				int maxTimes = ((int) buffBuilder.getObject(r, 3));
				int affectAmount = ((int) buffBuilder.getObject(r, 5));
				String requiredExperience = ((String) buffBuilder.getObject(r, 6));
				
				BuffBuilder item = new BuffBuilder();
				item.setStatName(skillName);
				item.setStatAffects(statAffects);
				item.setMaxTimesApplied(maxTimes);
				item.setAffectAmount(affectAmount);
				item.setRequiredExperience(requiredExperience);
				
				buffBuilderSkills.add(item);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void giveInspirationBuff(SWGObject buffer, SWGObject reciever, Vector<BuffItem> buffVector) {
		CreatureObject buffCreature = (CreatureObject) buffer;
		
		Vector<BuffBuilder> availableSkills = buffBuilderSkills;
		
		Vector<BuffBuilder> buffsToAdd = new Vector<BuffBuilder>();
		
		for (BuffItem item : buffVector) {
			for (BuffBuilder builder : availableSkills) {
				if (builder.getStatName().equals(item.getSkillName())) {
					buffsToAdd.add(builder);
					Console.println("Added buff item: " + builder.getStatName());
					break;
				}
			}
		}
		
		//Buff builtBuff = new Buff("buildabuff_inspiration", reciever.getObjectId());
		core.buffService.addBuffToCreature(buffCreature, "buildabuff_inspiration");
		
	}
	
	@Override
	public void shutdown() {

	}

}
