package services;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import main.NGECore;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.ObjControllerMessage;
import protocol.swg.objectControllerObjects.BuffBuilderChangeMessage;
import protocol.swg.objectControllerObjects.BuffBuilderEndMessage;
import protocol.swg.objectControllerObjects.ImageDesignMessage;
import resources.buffs.BuffItem;
import resources.common.BuffBuilder;
import resources.common.IDAttribute;
import resources.common.MathUtilities;
import resources.common.ObjControllerOpcodes;
import resources.common.OutOfBand;
import resources.common.Performance;
import resources.common.PerformanceEffect;
import resources.datatables.Posture;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import resources.skills.SkillMod;
import services.sui.SUIService.InputBoxType;
import services.sui.SUIWindow;
import services.sui.SUIWindow.Trigger;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clientdata.visitors.IDManagerVisitor;
import engine.clients.Client;
import engine.resources.common.RGB;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class EntertainmentService implements INetworkDispatch {

	private NGECore core;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private Vector<BuffBuilder> buffBuilderSkills = new Vector<BuffBuilder>();
	//FIXME: create a wrapper class for double key lookup maps
	private ConcurrentHashMap<PerformanceUID,Performance> performances = new ConcurrentHashMap<PerformanceUID,Performance>();
	private ConcurrentHashMap<Integer,Performance> performancesByIndex = new ConcurrentHashMap<Integer,Performance>();
	private ConcurrentHashMap<Integer,Performance> danceMap = new ConcurrentHashMap<Integer,Performance>();
	
	private ConcurrentHashMap<String, Byte> designMap = new ConcurrentHashMap<String, Byte>();
	
	//private Random ranWorkshop = new Random();
	
	private Map<String, PerformanceEffect> performanceEffects = new ConcurrentHashMap<String, PerformanceEffect>();
	
	
	
	public EntertainmentService(NGECore core) {
		this.core = core;
		populateSkillCaps();
		populatePerformanceTable();
		
		System.out.printf("[DEBUG] number_of_performances: %d\n", performances.size());
		
		populatePerformanceEffects();
		registerCommands();
		loadCharacterCustomizationData();
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		objControllerOpcodes.put(ObjControllerOpcodes.IMAGE_DESIGN_CHANGE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				Client client = core.getClient(session);

				if(client == null)
					return;

				SWGObject sender = client.getParent();

				if(sender == null)
					return;
				
				ImageDesignMessage sentPacket = new ImageDesignMessage();
				sentPacket.deserialize(data);
				
				CreatureObject designTarget = (CreatureObject) core.objectService.getObject(sentPacket.getTargetId());
				CreatureObject designer = (CreatureObject) core.objectService.getObject(sentPacket.getDesignerId());
				
				if (designTarget == null || designer == null)
					return;
				
				if (designTarget.getClient() == null || designTarget.getClient().getSession() == null)
					return;
				
				if (designer.getClient() == null || designer.getClient().getSession() == null)
					return;
				
				Vector<IDAttribute> colorAttributes = sentPacket.getColorAttributes();
				Vector<IDAttribute> bodyAttributes = sentPacket.getBodyAttributes();
				// TODO: Attribute check for colors?
				if (bodyAttributes != null) {
					for (IDAttribute atr : bodyAttributes) {
						//System.out.println("ATTRIBUTE: " + atr.getName() + " value of " + atr.getFloatValue());
						if (atr.getFloatValue() > 1f || atr.getFloatValue() < 0) { // RIP Height Exploit <3
							return;
						}
					}
				}
				
				/*if (colorAttributes != null) {
					for (IDAttribute atr : colorAttributes) {
						System.out.println("COLOR ATTRIBUTE: " + atr.getName() + " value of " + atr.getValue());
					}
				}*/
				
				if (sentPacket.getTargetId() != sentPacket.getDesignerId()) {
					
					if (sentPacket.isCustomerAccepted() && sentPacket.isDesignerCommited()) {

						sentPacket.setEndMessage(true);
						
						int fee = sentPacket.getMoneyDemanded() + sentPacket.getMoneyOffered();
						if (fee > 0) {
							int payersCash = designTarget.getCashCredits();

							if (payersCash < fee) {
								
								if (designTarget != designer) {
									ObjControllerMessage msg = new ObjControllerMessage(0x0B, sentPacket);
									designTarget.getClient().getSession().write(msg.serialize());
									
									// Window is closed already, so this shouldn't be necessary
									//ObjControllerMessage designMsg = new ObjControllerMessage(0x0B, sentPacket);
									//designer.getClient().getSession().write(designMsg.serialize());
									
									designTarget.sendSystemMessage("You don't have enough credits to pay the fee.", (byte) 0);
									designer.sendSystemMessage("Your target did not have the required payment fee", (byte) 0);

								}
							}else {
								// TODO: Apply customization
								designTarget.setCashCredits(designTarget.getCashCredits() - fee);
								designer.setCashCredits(designer.getCashCredits() + fee);
								
								handleImageDesign(designer, designTarget, colorAttributes, bodyAttributes, sentPacket.getHair(), sentPacket.getHoloEmote());
								
								sentPacket.setEndMessage(true);
								sentPacket.setObjectId(designTarget.getObjectId());
								
								ObjControllerMessage msg = new ObjControllerMessage(0x0B, sentPacket);
								designTarget.getClient().getSession().write(msg.serialize());
							}
						} else {
							handleImageDesign(designer, designTarget, colorAttributes, bodyAttributes, sentPacket.getHair(), sentPacket.getHoloEmote());
							sentPacket.setEndMessage(true);
							sentPacket.setObjectId(designTarget.getObjectId());
							
							ObjControllerMessage msg = new ObjControllerMessage(0x0B, sentPacket);
							designTarget.getClient().getSession().write(msg.serialize());
						}
						
					} else if (sentPacket.getObjectId() == designTarget.getObjectId()) {
						sentPacket.setObjectId(designer.getObjectId());
						
						ObjControllerMessage msg = new ObjControllerMessage(0x0B, sentPacket);
						designer.getClient().getSession().write(msg.serialize());
						
					} else if (sentPacket.getObjectId() == designer.getObjectId()) {
						sentPacket.setObjectId(designTarget.getObjectId());

						ObjControllerMessage msg = new ObjControllerMessage(0x0B, sentPacket);
						designTarget.getClient().getSession().write(msg.serialize());
					}
				} else {
					// No need to send a close packet message because the client automatically closes the ID window for the Desginer when they commit
					if (sentPacket.isDesignerCommited())
						handleImageDesign(designer, designTarget, colorAttributes, bodyAttributes, sentPacket.getHair(), sentPacket.getHoloEmote());
					else
						return;
				}
			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.IMAGE_DESIGN_END, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {

				data.order(ByteOrder.LITTLE_ENDIAN);
				
				Client client = core.getClient(session);

				if(client == null)
					return;

				SWGObject sender = client.getParent();

				if(sender == null)
					return;
				
				ImageDesignMessage sentPacket = new ImageDesignMessage();
				sentPacket.deserialize(data);
				
				if (sentPacket.getTargetId() == sentPacket.getDesignerId())
					return;
				
				if (sentPacket.getObjectId() == sentPacket.getDesignerId()) {
					CreatureObject target = (CreatureObject) core.objectService.getObject(sentPacket.getTargetId());
					if (target == null)
						return;
					
					sentPacket.setEndMessage(true);
					sentPacket.setObjectId(target.getObjectId());
					ObjControllerMessage endMessage = new ObjControllerMessage(0x0B, sentPacket);
					target.getClient().getSession().write(endMessage.serialize());
					
				} else if (sentPacket.getObjectId() == sentPacket.getTargetId()) {
					CreatureObject designer = (CreatureObject) core.objectService.getObject(sentPacket.getDesignerId());
					if (designer == null)
						return;
					
					sentPacket.setEndMessage(true);
					sentPacket.setObjectId(designer.getObjectId());
					ObjControllerMessage endMessage = new ObjControllerMessage(0x0B, sentPacket);
					designer.getClient().getSession().write(endMessage.serialize());
				}
			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.BUFF_BUILDER_END, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {

				data.order(ByteOrder.LITTLE_ENDIAN);

				Client client = core.getClient(session);

				if(client == null)
					return;

				SWGObject sender = client.getParent();

				if(sender == null)
					return;

				BuffBuilderEndMessage sentPacket = new BuffBuilderEndMessage();
				sentPacket.deserialize(data);

				SWGObject buffer = core.objectService.getObject(sentPacket.getBufferId());
				SWGObject buffRecipient = core.objectService.getObject(sentPacket.getBuffRecipientId());

				if(buffer == null || buffRecipient == null)
					return;

				// No need to send end packet when buffing yourself.
				if (buffRecipient != buffer) {
					if(buffRecipient.getClient() == null || buffRecipient.getClient().getSession() == null)
						return;

					if (sender.getObjectId() == buffer.getObjectId()) {
						// send close packet to recipient
						BuffBuilderEndMessage end = new BuffBuilderEndMessage(sentPacket);
						end.setObjectId(buffRecipient.getObjectId());

						end.setBufferId(buffer.getObjectId());
						end.setBuffRecipientId(buffRecipient.getObjectId());

						ObjControllerMessage closeMessage = new ObjControllerMessage(0x0B, end);
						buffRecipient.getClient().getSession().write(closeMessage.serialize());

					}
					if (sender.getObjectId() == buffRecipient.getObjectId()) 
					{
						// send close packet to buffer
						BuffBuilderEndMessage end = new BuffBuilderEndMessage(sentPacket);
						end.setObjectId(buffer.getObjectId());

						end.setBufferId(buffer.getObjectId());
						end.setBuffRecipientId(buffRecipient.getObjectId());
						
						ObjControllerMessage closeMessage = new ObjControllerMessage(0x0B, end);
						buffer.getClient().getSession().write(closeMessage.serialize());
						CreatureObject cre = (CreatureObject) buffer;
						cre.sendSystemMessage("The buff recipient cancelled the buff builder session.", (byte) 0);
						
					
					}
				}
			}
		});

		objControllerOpcodes.put(ObjControllerOpcodes.BUFF_BUILDER_CHANGE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				data.order(ByteOrder.LITTLE_ENDIAN);

				Client client = core.getClient(session);

				if(client == null)
					return;

				SWGObject sender = client.getParent();

				if(sender == null)
					return;

				BuffBuilderChangeMessage sentPacket = new BuffBuilderChangeMessage();
				sentPacket.deserialize(data);

				Vector<BuffItem> statBuffs = sentPacket.getStatBuffs();

				CreatureObject buffer = (CreatureObject) core.objectService.getObject(sentPacket.getBufferId());
				CreatureObject buffRecipient = (CreatureObject) core.objectService.getObject(sentPacket.getBuffRecipientId());

				if (buffer != buffRecipient) {

					BuffBuilderChangeMessage changeMessage = new BuffBuilderChangeMessage();
					changeMessage.setBuffCost(sentPacket.getBuffCost());
					changeMessage.setTime(sentPacket.getTime());
					changeMessage.setBufferId(buffer.getObjectId());
					changeMessage.setBuffRecipientId(buffRecipient.getObjectId());

					if (!statBuffs.isEmpty())
						changeMessage.setStatBuffs(statBuffs);

					if (sentPacket.getAccepted() == true && sentPacket.getBuffRecipientAccepted() == 1) {

						if (sentPacket.getBuffCost() > 0) {
							if (buffRecipient.getCashCredits() >= sentPacket.getBuffCost()) {
								buffRecipient.setCashCredits(buffRecipient.getCashCredits() - sentPacket.getBuffCost());
							} else {
								return;
							}
						}

						giveInspirationBuff(buffRecipient, buffer, statBuffs);

						BuffBuilderEndMessage endBuilder = new BuffBuilderEndMessage(changeMessage);
						endBuilder.setObjectId(buffer.getObjectId());

						BuffBuilderEndMessage endRecipient = new BuffBuilderEndMessage(changeMessage);
						endRecipient.setObjectId(buffRecipient.getObjectId());

						ObjControllerMessage closeBuilder = new ObjControllerMessage(0x0B, endBuilder);
						buffer.getClient().getSession().write(closeBuilder.serialize());

						ObjControllerMessage closeRecipient = new ObjControllerMessage(0x0B, endRecipient);
						buffRecipient.getClient().getSession().write(closeRecipient.serialize());

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
						giveInspirationBuff(buffRecipient, buffer, statBuffs);
						BuffBuilderEndMessage endBuilder = new BuffBuilderEndMessage(sentPacket);
						endBuilder.setObjectId(buffer.getObjectId());

						ObjControllerMessage objMsg = new ObjControllerMessage(0x0B, endBuilder);
						buffRecipient.getClient().getSession().write(objMsg.serialize());
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
				p.setLineNumber(r);
				
				if (p.getType() == -1788534963) {
					danceMap.put(new Integer(p.getDanceVisualId()), p);
				}
				performances.put(new PerformanceUID(p.getPerformanceName(), p.getInstrumentAudioId()), p);
				performancesByIndex.put(r, p);
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		//TODO: Remove
		Set<PerformanceUID> debugSet = performances.keySet();
		for(PerformanceUID uid : debugSet)
		{
			System.out.println(uid.toString());
		}
		
	}
	
	private void populateSkillCaps() {
		try {
			DatatableVisitor buffBuilder = ClientFileManager.loadFile("datatables/buff/buff_builder.iff", DatatableVisitor.class);
			
			for (int r = 0; r < buffBuilder.getRowCount(); r++) {
				String skillName = ((String) buffBuilder.getObject(r, 0));
				String category = ((String) buffBuilder.getObject(r, 1));
				String statAffects = ((String) buffBuilder.getObject(r, 2));
				int maxTimes = ((int) buffBuilder.getObject(r, 3));
				int cost = ((int) buffBuilder.getObject(r, 4));
				int affectAmount = ((int) buffBuilder.getObject(r, 5));
				String requiredExpertise = ((String) buffBuilder.getObject(r, 6));
				
				BuffBuilder item = new BuffBuilder();
				item.setStatName(skillName);
				item.setCategory(category);
				item.setStatAffects(statAffects);
				item.setMaxTimesApplied(maxTimes);
				item.setCost(cost);
				item.setAffectAmount(affectAmount);
				item.setRequiredExpertise(requiredExpertise);
				
				buffBuilderSkills.add(item);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private void populatePerformanceEffects() {
		try {
			DatatableVisitor effects = ClientFileManager.loadFile("datatables/performance/perform_effect.iff", DatatableVisitor.class);

			for (int r = 0; r < effects.getRowCount(); r++) {
				String effectName = ((String) effects.getObject(r, 0));
				String performanceType = ((String) effects.getObject(r, 1));
				int requiredSkillModValue = ((int) effects.getObject(r, 2));
				Boolean requiredPerforming = ((Boolean) effects.getObject(r, 3));
				int targetType = ((int) effects.getObject(r, 4));
				float effectDuration = ((float) effects.getObject(r, 5));
				int effectActionCost = ((int) effects.getObject(r, 6));

				PerformanceEffect item = new PerformanceEffect();
				item.setEffectActionCost(effectActionCost);
				item.setEffectDuration(effectDuration);
				item.setName(effectName);
				item.setPerformanceType(performanceType);
				item.setRequiredPerforming(requiredPerforming);
				item.setRequiredSkillModValue(requiredSkillModValue);
				item.setTargetType(targetType);

				performanceEffects.put(effectName.toLowerCase(), item);

			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private void registerCommands() {
		core.commandService.registerAlias("flo","flourish");
	}
	
	public void giveInspirationBuff(CreatureObject reciever, CreatureObject buffer, Vector<BuffItem> buffVector) {
		
		
		if (reciever.hasBuff("buildabuff_inspiration"))
		{
			core.buffService.removeBuffFromCreature(reciever, reciever.getBuffByName("buildabuff_inspiration"));
		}
		
		Vector<BuffBuilder> availableStats = buffBuilderSkills;
		Vector<BuffItem> stats = new Vector<BuffItem>();

		for (BuffItem item : buffVector) 
		{
			for(BuffBuilder builder : availableStats) 
			{
		
				if(builder.getStatName().equalsIgnoreCase(item.getSkillName())) 
				{
				
					
					if(builder.getMaxTimesApplied() < item.getInvested())
						return;

					int affectTotal=0;
							

					
					BuffItem stat = new BuffItem(builder.getStatAffects(), item.getInvested(), item.getEntertainerBonus());
					
					switch(builder.getCategoryId())
					{
					
						case TRADE: 
						case RESISTANCES:
						case ATTRIBUTES:
						{
							affectTotal=(int)( ( (1+(float) item.getEntertainerBonus()/100f)*builder.getAffectAmount()) * item.getInvested());
							break;
						
						}
						case COMBAT:
						{
							affectTotal=(int) (( builder.getAffectAmount()+item.getEntertainerBonus()) * item.getInvested());
							break;
						
						}
						case MISC:
						{
							affectTotal=(int) (( builder.getAffectAmount()) * item.getInvested());
							break;
			
						}
						default:
						{	
							System.out.println("unhandled builder catId of "+builder.getCategoryId());
						}
						
					}


					stat.setAffectAmount(affectTotal);
					stats.add(stat);
					
					break;
				}
				
			}
		}
		
		reciever.setAttachment("buffWorkshop", stats);
		
		if (buffer == reciever)
			reciever.setAttachment("inspireDuration", 215);
		
		//PlayerObject rPlayer = (PlayerObject) reciever.getSlottedObject("ghost");

		//long timeStamp = 0;
		//if (reciever.getAttachment("buffWorkshopTimestamp") != null)
			//timeStamp = (long) reciever.getAttachment("buffWorkshopTimestamp");
		

		
		core.buffService.addBuffToCreature(reciever, "buildabuff_inspiration", buffer);
		/*if (core.buffService.addBuffToCreature(reciever, "buildabuff_inspiration", buffer) && !rPlayer.getProfession().equals("entertainer_1a")) {
			if (timeStamp == 0 || (System.currentTimeMillis() - timeStamp > 86400000)) {
				float random = ranWorkshop.nextFloat();
				if (random < 0.75f) {

					if(rPlayer.getProfession().contains("trader")) { core.collectionService.addCollection(buffer, "prof_trader"); } 

					else {
						core.collectionService.addCollection(buffer, rPlayer.getProfession());
					}
				}
			} else { buffer.sendSystemMessage("@collection:buffed_too_soon", (byte) 0); }
			reciever.setAttachment("buffWorkshopTimestamp", System.currentTimeMillis());
		}*/
	}
	
	//FIXME: Refactor using new map --Complete--
	public int getDanceVisualId(String danceName) {
		Performance p = getPerformance(danceName, 0);
		
		//if 0 , then it's no dance. need to handle that in the script.
		return ((p == null) ? 0 : p.getDanceVisualId());
	}
	
	//FIXME: Refactor using new map ?? Perhaps not ??
	public Map<Long,String> getAvailableDances(CreatureObject actor) {
		
		Map<Long,String> dances = new HashMap<Long, String>();
		for (int index : danceMap.keySet()) {
			if (!canDance(actor, danceMap.get(index) )) { continue; }
			dances.put( new Long( danceMap.get(index).getDanceVisualId()  ), danceMap.get(index).getPerformanceName() );
		}
		
		return dances;
		
	}
	
	//FIXME: if visualId > 0 then it is a dance...
	public boolean isDance(int visualId) {
		return ( danceMap.get(visualId) != null ) ;
	}
	
	public boolean canDance(CreatureObject actor, Performance dance) {
		if (actor.hasAbility(dance.getRequiredDance())) {
			return true;
		}
		return false;
	}
	
	public boolean canDance(CreatureObject actor, int visualId) {
		if (!isDance(visualId)) { return false; }
		return canDance(actor, danceMap.get(visualId));
	}

	public Performance getDance(int visualId) {
		return danceMap.get(visualId);
	}
	
	//FIXME: Refactor using new map -COMPLETE-
	//FIXME: Returning null for known combination...
	public Performance getPerformance(String name, int instrumentAudioId) {
		return performances.get(new PerformanceUID(name, instrumentAudioId));
	}
	
	public Performance getPerformanceByIndex(int index) {
		return performancesByIndex.get(index);
	}
	
	public Map<String, PerformanceEffect> getPerformanceEffects() {
		return performanceEffects;
	}
	
	public void startPerformance(CreatureObject actor, int performanceId, int performanceCounter, String skillName, boolean isDance) {
		actor.setPerformanceId((isDance) ? 0 : performanceId);
		actor.setPerformanceCounter(performanceCounter);
		actor.setCurrentAnimation(skillName);
		actor.setPerformanceType(isDance);
		
		if (!actor.isPerforming()) {
			actor.setPerforming(true);
		}
	}
	
	public void startPerformanceExperience(final CreatureObject entertainer) {
		final ScheduledFuture<?> experienceTask = scheduler.scheduleAtFixedRate(() -> {
			try {
				Performance p = performancesByIndex.get(entertainer.getPerformanceId());
				if (p == null) {
					entertainer.setFlourishCount(0);
					return;
				}
				
				int floXP = p.getFlourishXpMod();
				int floCount = entertainer.getFlourishCount();
				
				//FIXME: this is not an accurate implementation yet. It needs group bonuses and other things.
				int XP = (int) Math.round( ((floCount > 2) ? 2 : floCount) * floXP * 3.8 );
				
				entertainer.setFlourishCount(0);
				core.playerService.giveExperience(entertainer, XP);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 10000, 10000, TimeUnit.MILLISECONDS);
		
		entertainer.setEntertainerExperience(experienceTask);
		
	}
	
	public void startSpectating(final CreatureObject spectator, final CreatureObject performer, String spectateType) {

		// visual
		if (spectator.getPerformanceWatchee() == performer && spectateType.equals("dance"))
			spectator.getPerformanceWatchee().removeSpectator(spectator);
		// music
		else if (spectator.getPerformanceListenee() == performer && spectateType.equals("musci"))
			spectator.getPerformanceListenee().removeSpectator(spectator);

		spectator.setPerformanceWatchee(performer);
		performer.addSpectator(spectator);
		//spectator.setMoodAnimation("entertained");

		final ScheduledFuture<?> spectatorTask = scheduler.scheduleAtFixedRate(() -> {
			try {
				if (spectator.getWorldPosition().getDistance2D(performer.getWorldPosition()) > (float) 70) {
	
					if(performer.getPerformanceType().equals("dance")) {
						spectator.setPerformanceWatchee(null);
						spectator.sendSystemMessage("You stop watching " + performer.getCustomName() + " because " + performer.getCustomName()
								+ " is out of range.", (byte) 0);
					}
					else {
						spectator.setPerformanceListenee(null);
						spectator.sendSystemMessage("You stop listening to " + performer.getCustomName() + " because " + performer.getCustomName()
								+ " is out of range.", (byte) 0);
					}
					//spectator.setMoodAnimation("neutral");
					performer.removeSpectator(spectator);
	
					if (spectator.getInspirationTick() != null) 
						spectator.getInspirationTick().cancel(true);
					
					if (spectator.getSpectatorTask() != null)
						spectator.getSpectatorTask().cancel(true);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 2, 2, TimeUnit.SECONDS);

		spectator.setSpectatorTask(spectatorTask);

		if(((PlayerObject)performer.getSlottedObject("ghost")).getProfession().equals("entertainer_1a")) {
			handleInspirationTicks(spectator, performer);
		}

		if(spectateType.equals("dance"))
			spectator.sendSystemMessage("You start watching " + performer.getCustomName() + ".", (byte) 0);
		else
			spectator.sendSystemMessage("You start listening to " + performer.getCustomName() + ".", (byte) 0);

	}
	
	public void performFlourish(final CreatureObject performer, int flourish) {
		// FIXME There wasn't a limit on flourishes; they just queued up.
		if (performer.getFlourishCount() > 0 || performer.isPerformingFlourish()) {
			performer.sendSystemMessage("@performance:flourish_wait_self", (byte) 0);
			return;
		}
		//FIXME: I don't think this will work.
		Performance performance = getPerformanceByIndex(performer.getPerformanceId());

		if(performance == null)
			return;

		String anmFlo = "skill_action_" + flourish;

		if (flourish == 9)
			anmFlo = "mistake";
		
		performer.setFlourishCount(1);
		performer.setPerformingFlourish(true);
		performer.sendSystemMessage("@performance:flourish_perform", (byte) 0);
		performer.doSkillAnimation(anmFlo);

		scheduler.schedule(() -> {
			try {
				performer.setFlourishCount(0);
				performer.setPerformingFlourish(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, (long) performance.getLoopDuration(), TimeUnit.SECONDS);
	}
	
	public boolean performEffect(final CreatureObject performer, String command, String effect, TangibleObject target) {
		PerformanceEffect pEffect = performanceEffects.get(command.toLowerCase());

		if(pEffect == null)
			return false;

		String performance = performer.getPerformanceType();

		if(performer.isPerformingEffect()) {
			performer.sendSystemMessage("@performance:effect_wait_self", (byte) 0);
			return false;
		}

		if (performer.getPosture() != Posture.SkillAnimating) {
			performer.sendSystemMessage("@performance:effect_not_performing", (byte) 0);
			return false;
		}

		if(performance.equals("dance") && !pEffect.isDance() || performance.equals("music") && !pEffect.isMusic()) {
			performer.sendSystemMessage("@performance:effect_not_performing_correct", (byte) 0);
			return false;
		}

		if(performer.getAction() < pEffect.getEffectActionCost()) {
			performer.sendSystemMessage("@performance:effect_too_tired", (byte) 0);
			return false;
		}

		performer.setAction(performer.getAction() - pEffect.getEffectActionCost());

		performer.setPerformingEffect(true);
		
		if(target != null)
			target.playEffectObject(effect, "");
		
		else
			performer.playEffectObject(effect, "");
		
		scheduler.schedule(() -> {
			try {
				performer.setPerformingEffect(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, (long) pEffect.getEffectDuration(), TimeUnit.SECONDS);

		return true;
	}
	
	public void handleInspirationTicks(final CreatureObject spectator, final CreatureObject performer) {
		// http://youtu.be/WqyAde-oC7o?t=11m14s  << Player watching entertainer (Has ring only, no pet, + 15 min ticks)
		// TODO: Camp/Cantina checks for expertise and duration bonus %. Right now only using basic values.
		final ScheduledFuture<?> inspirationTick = scheduler.scheduleAtFixedRate(() -> {
			try {
				int time = 0; // current buff duration time (minutes)
				int buffCap = 215; // 5 hours 35 minutes - 2 hours (buff duration increase bonus) << Taken from video, doesn't account for performance bonuses etc.
	
				if (spectator.getAttachment("inspireDuration") != null)
					time+= (int) spectator.getAttachment("inspireDuration");
	
				if (performer.getSkillMod("expertise_en_inspire_buff_duration_increase") != null) {
					SkillMod durationMod = performer.getSkillMod("expertise_en_inspire_buff_duration_increase");
					buffCap += durationMod.getBase() + durationMod.getModifier();
				}
	
				if (time >= buffCap) {
					spectator.setAttachment("inspireDuration", buffCap); // incase someone went over cap
					spectator.getInspirationTick().cancel(true);
				} else {
					int entTick = 10;
					if (performer.getSkillMod("expertise_en_inspire_pulse_duration_increase") != null) {
						SkillMod pulseMod = performer.getSkillMod("expertise_en_inspire_pulse_duration_increase");
						entTick += pulseMod.getBase() + pulseMod.getModifier();
					}
	
					int duration = (time + entTick); // minutes
					int hMinutes = MathUtilities.secondsToHourMinutes(duration * 60);
					int hours = MathUtilities.secondsToWholeHours(duration * 60);
	
					spectator.showFlyText(OutOfBand.ProsePackage("@spam:buff_duration_tick_observer", "TO", hours + " hours , " + hMinutes + " minutes "), 0.66f, new RGB(255, 182, 193), 3, false);
	
					spectator.setAttachment("inspireDuration", duration);
					//System.out.println("Inspire Duration: " + spectator.getAttachment("inspireDuration") + " on " + spectator.getCustomName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 10, 10, TimeUnit.SECONDS);
		spectator.setInspirationTick(inspirationTick);
	}
	
	public void handleCoverCharge(final CreatureObject actor, final CreatureObject performer) {
		final int charge = performer.getCoverCharge();

		if (charge == 0)
			return;

		else {
			SUIWindow notification = core.suiService.createMessageBox(InputBoxType.INPUT_BOX_OK, "Cover Charge", performer.getCustomName() +
					" has a cover charge of " + performer.getCoverCharge() + ". Do you wish to pay it?", actor, performer, (float) 30);
			Vector<String> returnParams = new Vector<String>();

			returnParams.add("btnOk:Text");

			notification.addHandler(0, "", Trigger.TRIGGER_OK, returnParams, (owner, eventType, returnList) -> {
				if (eventType == 0) {
					if (charge > actor.getCashCredits()) {
						actor.sendSystemMessage("You do not have enough credits to cover the charge.", (byte) 0); // TODO: Find the message in the STF files.
						return;
					} else{
						actor.setCashCredits(actor.getCashCredits() - charge);
						actor.sendSystemMessage("You payed the cover charge of " + charge + " to " + performer.getCustomName(), (byte) 0); // TODO: Find the message in the STF files.
						performer.setCashCredits(performer.getCashCredits() + charge);

						startSpectating(actor, performer, performer.getPerformanceType());

					}
				}
			});
			core.suiService.openSUIWindow(notification);
		}
	}
	
	private void handleImageDesign(CreatureObject designer, CreatureObject designTarget, Vector<IDAttribute> colorAttributes, Vector<IDAttribute> bodyAttributes, String hairTemplate, String holoEmote) {

		if (hairTemplate != null && !hairTemplate.equals("")) {
			if(!hairTemplate.startsWith("object/tangible/hair/"))
				return;
			String sharedHairTemplate = hairTemplate.replace("/hair_", "/shared_hair_");
			TangibleObject hairObject = (TangibleObject) core.objectService.createObject(sharedHairTemplate, designTarget.getPlanet());
			
			if (hairObject == null)
				return;
			
			TangibleObject oldHair = (TangibleObject) designTarget.getSlottedObject("hair");
			if (oldHair == null)
				return;
			core.objectService.destroyObject(oldHair);
			
			designTarget.add(hairObject);

			designTarget.addObjectToEquipList(hairObject);
			
		}
		
		if (bodyAttributes != null) {
			for (IDAttribute atr : bodyAttributes) {
				if (atr.getName().equals("height"))
					designTarget.setHeight(atr.getFloatValue());
				
				// TODO: Body Attributes
			}
		}
		
		if (colorAttributes != null) {
			for (IDAttribute atr : colorAttributes) {
				if (atr.getName().equals("color_hair")){
					// TODO: Color Attributes
				}
			}
		}
		if (holoEmote != null) {
			PlayerObject player = (PlayerObject) designTarget.getSlottedObject("ghost");
			if (player != null) {
				player.setHoloEmote(holoEmote);
				player.setHoloEmoteUses(20);
				designTarget.sendSystemMessage("@image_designer:new_holoemote", (byte) 0);
			}
		}
		
	}
	
	private void loadCharacterCustomizationData() {
		try {
			IDManagerVisitor visitor = ClientFileManager.loadFile("customization/customization_id_manager.iff", IDManagerVisitor.class);
			
			designMap = visitor.getCustomizationMap();
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public String getCustomizationString(int number){
		synchronized (designMap) {
			for (Entry<String, Byte> entry : designMap.entrySet()) {
				if (entry.getValue().intValue() == number) {
					return entry.getKey();
				}
			}
		}
		return "";
	}
	
	public int getCustomizationValue(String customizationString) {
		synchronized(designMap){
			for(Entry<String, Byte> entry : designMap.entrySet()) {
				if (entry.getKey().equals(customizationString)) {
					return entry.getValue().intValue();
				}
			}
		}
		return 0;
	}
	
	@Override
	public void shutdown() {

	}
	
	/**
	 * @author Glen
	 * This class serves as a unique id for performances. 
	 * The performance.iff file is structured very similar to a database, and with that idea in mind
	 * this helper-class will serve as essentially the primary key of each performance.
	 */
	private class PerformanceUID
	{
		private final String performanceName;
		private final int instrumentAudioId;
		
		public PerformanceUID(String performanceName, int instrumentAudioId)
		{
			this.performanceName = performanceName;
			this.instrumentAudioId = instrumentAudioId;
		}
		
		public boolean equals(PerformanceUID other) {
			if(other != null)
			{
				if(this.performanceName == other.performanceName && this.instrumentAudioId == other.instrumentAudioId)
				{
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj != null && this.getClass() == obj.getClass() && this.performanceName.equals(((PerformanceUID)obj).performanceName) && this.instrumentAudioId == ((PerformanceUID)obj).instrumentAudioId)
			{
				return true;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return this.performanceName != null ? performanceName.hashCode() * 37 + instrumentAudioId: instrumentAudioId;
//			int hash = performanceName.hashCode() ^ instrumentAudioId;
//			return hash;
		}

		@Override
		public String toString() {
			return "PerformanceUID [performanceName=" + performanceName
					+ ", instrumentAudioId=" + instrumentAudioId + ", hashCode = " + hashCode() + "]";
		}
		
	}

}
