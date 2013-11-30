package services;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Iterator;
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
import resources.common.Performance;
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
	//FIXME: create a wrapper class for double key lookup maps
	private HashMap<String,Performance> performances = new HashMap<String,Performance>();
	private HashMap<Integer,Performance> danceMap = new HashMap<Integer,Performance>();
	
	public EntertainmentService(NGECore core) {
		this.core = core;
		populateSkillCaps();
		populatePerformanceTable();
		registerCommands();
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
						
						if (sentPacket.getBuffCost() > 0) {
							CreatureObject recipientCreature = (CreatureObject) buffRecipient;
							if (recipientCreature.getCashCredits() >= sentPacket.getBuffCost()) {
								recipientCreature.setCashCredits(recipientCreature.getCashCredits() - sentPacket.getBuffCost());
							} else {
								return;
							}
						}
						
						OkMessage closeMsg = new OkMessage();
						buffer.getClient().getSession().write(closeMsg.serialize());
						
						giveInspirationBuff(buffRecipient, statBuffs);
						
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
					if (sentPacket.getAccepted() == true) {
						OkMessage closeMsg = new OkMessage();
						buffer.getClient().getSession().write(closeMsg.serialize());
						
						giveInspirationBuff(buffRecipient, statBuffs);
					}
				}
			}
			
		});
	}

	private void populatePerformanceTable() {
		
		try {
			DatatableVisitor PerformanceVisitor = ClientFileManager.loadFile("datatables/performance/performance.iff", DatatableVisitor.class);
			
			//rformanceVisitor.
			
			for (int r = 0; r < PerformanceVisitor.getRowCount(); r++) {
				Performance p = new Performance();
				
				/* for (int j=0; j < PerformanceVisitor.getColumnCount(); j++) {
					System.out.println(j + ": " + PerformanceVisitor.getObject(r, j));
				}*/
				
				p.setPerformanceName( ( (String) PerformanceVisitor.getObject(r, 0) ).toLowerCase());
				p.setInstrumentAudioId((int) PerformanceVisitor.getObject(r, 1));
				p.setRequiredSong((String) PerformanceVisitor.getObject(r, 2));
				p.setRequiredInstrument((String) PerformanceVisitor.getObject(r, 3));
				p.setRequiredDance((String) PerformanceVisitor.getObject(r, 4));
				p.setDanceVisualId((int) PerformanceVisitor.getObject(r, 5));
				p.setActionPointsPerLoop((int) PerformanceVisitor.getObject(r, 6));
				p.setLoopDuration((float) PerformanceVisitor.getObject(r, 7));
				p.setType((int) PerformanceVisitor.getObject(r, 8));
				p.setBaseXp((int) PerformanceVisitor.getObject(r, 9));
				p.setFlourishXpMod((int) PerformanceVisitor.getObject(r, 10));
				p.setHealMindWound((int) PerformanceVisitor.getObject(r, 11));
				p.setHealShockWound((int) PerformanceVisitor.getObject(r, 12));
				p.setRequiredSkillMod((String) PerformanceVisitor.getObject(r, 13));
				p.setRequiredSkillModValue((int) PerformanceVisitor.getObject(r, 14));
				p.setMainloop((String) PerformanceVisitor.getObject(r, 15));
				p.setFlourish1((String) PerformanceVisitor.getObject(r, 16));
				p.setFlourish2((String) PerformanceVisitor.getObject(r, 17));
				p.setFlourish3((String) PerformanceVisitor.getObject(r, 18));
				p.setFlourish4((String) PerformanceVisitor.getObject(r, 19));
				p.setFlourish5((String) PerformanceVisitor.getObject(r, 20));
				p.setFlourish6((String) PerformanceVisitor.getObject(r, 21));
				p.setFlourish7((String) PerformanceVisitor.getObject(r, 22));
				p.setFlourish8((String) PerformanceVisitor.getObject(r, 23));
				p.setIntro((String) PerformanceVisitor.getObject(r, 24));
				p.setOutro((String) PerformanceVisitor.getObject(r, 25));
				
				if (p.getType() == -1788534963) {
					danceMap.put(new Integer(p.getDanceVisualId()), p);
				}
				performances.put(p.getPerformanceName(), p);
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
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

	private void registerCommands() {
		core.commandService.registerCommand("startdance");
	}
	
	public void giveInspirationBuff(SWGObject reciever, Vector<BuffItem> buffVector) {
		CreatureObject buffCreature = (CreatureObject) reciever;
		
		Vector<BuffBuilder> availableSkills = buffBuilderSkills;
		
		Vector<BuffBuilder> buffsToAdd = new Vector<BuffBuilder>();
		
		for (BuffItem item : buffVector) {
			for (BuffBuilder builder : availableSkills) {
				if (builder.getStatName().equals(item.getSkillName())) {
					builder.setEntBonus(item.getBonusAmount());
					buffsToAdd.add(builder);
					Console.println("Added buff item: " + builder.getStatAffects() + " with total affect of " + builder.getTotalAffected() + " and ent bonus of " + builder.getEntBonus());
					continue;
				}
			}
		}
		
		reciever.setAttachment("buffWorkshop", buffsToAdd);
		
		core.buffService.addBuffToCreature(buffCreature, "buildabuff_inspiration");
		
	}
	
	public Map<Long,String> getAvailableDances(CreatureObject actor) {
		
		Map<Long,String> dances = new HashMap<Long, String>();
		for (int index : danceMap.keySet()) {
			if (!canDance(actor, danceMap.get(index) )) { continue; }
			dances.put( new Long(index), danceMap.get(index).getPerformanceName() );
		}
		
		return dances;
		
	}
	
	public boolean isDance(String danceName) {
		return ( performances.get(danceName) != null && performances.get(danceName).getType() ==  -1788534963);
	}
	
	public boolean canDance(CreatureObject actor, Performance dance) {
		if (actor.hasAbility(dance.getRequiredDance())) {
			return true;
		}
		return false;
	}
	
	public boolean canDance(CreatureObject actor, String danceName) {
		if (!isDance(danceName)) { return false; }
		return canDance(actor, performances.get(danceName));
	}
	
	@Override
	public void shutdown() {

	}

}
