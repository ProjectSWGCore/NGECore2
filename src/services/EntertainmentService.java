package services;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import protocol.swg.ObjControllerMessage;
import protocol.swg.OkMessage;
import protocol.swg.objectControllerObjects.BuffBuilderChangeMessage;
import protocol.swg.objectControllerObjects.BuffBuilderEndMessage;
import protocol.swg.objectControllerObjects.BuffBuilderStartMessage;
import resources.common.BuffBuilder;
import resources.common.Console;
import resources.common.ObjControllerOpcodes;
import resources.common.Performance;
import resources.common.StringUtilities;
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
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private Vector<BuffBuilder> buffBuilderSkills = new Vector<BuffBuilder>();
	//FIXME: create a wrapper class for double key lookup maps
	private HashMap<String,Performance> performances = new HashMap<String,Performance>();
	private HashMap<Integer,Performance> performancesByIndex = new HashMap<Integer,Performance>();
	private HashMap<Integer,Performance> danceMap = new HashMap<Integer,Performance>();
	
	public EntertainmentService(NGECore core) {
		this.core = core;
		populateSkillCaps();
		populatePerformanceTable();
		registerCommands();
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		objControllerOpcodes.put(ObjControllerOpcodes.BUFF_BUILDER_END, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {

				data.order(ByteOrder.LITTLE_ENDIAN);
				StringUtilities.printBytes(data.array());
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
					if (sender.getObjectId() == buffRecipient.getObjectId()) {
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

				SWGObject buffer = core.objectService.getObject(sentPacket.getBufferId());
				SWGObject buffRecipient = core.objectService.getObject(sentPacket.getBuffRecipientId());

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
							CreatureObject recipientCreature = (CreatureObject) buffRecipient;
							if (recipientCreature.getCashCredits() >= sentPacket.getBuffCost()) {
								recipientCreature.setCashCredits(recipientCreature.getCashCredits() - sentPacket.getBuffCost());
							} else {
								return;
							}
						}

						giveInspirationBuff(buffRecipient, statBuffs);

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
						giveInspirationBuff(buffRecipient, statBuffs);
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
				performances.put(p.getPerformanceName(), p);
				performancesByIndex.put(r, p);
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

	private void registerCommands() {
		core.commandService.registerCommand("bandflourish");
		core.commandService.registerCommand("flourish");
		core.commandService.registerAlias("flo","flourish");
		core.commandService.registerCommand("groupdance");
		core.commandService.registerCommand("startdance");
		core.commandService.registerCommand("stopdance");
		core.commandService.registerCommand("watch");
		core.commandService.registerCommand("stopwatching");
	}
	
	public void giveInspirationBuff(SWGObject reciever, Vector<BuffItem> buffVector) {
		CreatureObject buffCreature = (CreatureObject) reciever;
		
		Vector<BuffBuilder> availableStats = buffBuilderSkills;
		Vector<BuffItem> stats = new Vector<BuffItem>();

		for (BuffItem item : buffVector) {
			for(BuffBuilder builder : availableStats) {
				if(builder.getStatName().equalsIgnoreCase(item.getSkillName())) {
					if(builder.getMaxTimesApplied() < item.getInvested())
						return;
					
					// Ent. Expertise Percent + (invested points * affect amount) = stat
					int bonusPoints = (int) (builder.getAffectAmount() *((float) item.getEntertainerBonus()/100f));
					int affectTotal = bonusPoints + (item.getInvested() * builder.getAffectAmount());

					BuffItem stat = new BuffItem(builder.getStatAffects(), item.getInvested(), item.getEntertainerBonus());
					stat.setAffectAmount(affectTotal);
					
					/*System.out.println("Invested Points: " + item.getInvested());
					System.out.println("Entertainer Bonus: " + item.getEntertainerBonus());
					System.out.println("Affect Total: " + affectTotal);*/

					stats.add(stat);
					
					break;
				}
			}
		}
		
		reciever.setAttachment("buffWorkshop", buffVector);
		
		core.buffService.addBuffToCreature(buffCreature, "buildabuff_inspiration");
		
	}
	
	public int getDanceVisualId(String danceName) {
		Performance p = performances.get(danceName);
		
		//if 0 , then it's no dance. need to handle that in the script.
		return ((p == null) ? 0 : p.getDanceVisualId());
	}
	
	public Map<Long,String> getAvailableDances(CreatureObject actor) {
		
		Map<Long,String> dances = new HashMap<Long, String>();
		for (int index : danceMap.keySet()) {
			if (!canDance(actor, danceMap.get(index) )) { continue; }
			dances.put( new Long( danceMap.get(index).getDanceVisualId()  ), danceMap.get(index).getPerformanceName() );
		}
		
		return dances;
		
	}
	
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
	
	public Performance getPerformance(String name) {
		return performances.get(name);
	}
	
	public Performance getPerformanceByIndex(int index) {
		return performancesByIndex.get(index);
	}
	
	public void startPerformance(CreatureObject actor, int performanceId, int performanceCounter, String skillName, boolean isDance) {
		actor.setPerformanceId(performanceId, isDance);
		actor.setPerformanceCounter(performanceCounter);
		actor.setCurrentAnimation(skillName);
		actor.setPerformanceType(isDance);
		
		actor.startPerformance();
	}
	
	public void startPerformanceExperience(final CreatureObject entertainer) {
		final ScheduledFuture<?> experienceTask = scheduler.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
			
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
				
				
			}
			
		},10000, 10000, TimeUnit.MILLISECONDS);
		
		entertainer.setEntertainerExperience(experienceTask);
		
	}
	
	public void startSpectating(final CreatureObject spectator, final CreatureObject performer) {
		final ScheduledFuture<?> spectatorTask = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				if (spectator.getPosition().getDistance2D(performer.getWorldPosition()) > (float) 70) {

					if(((performer.getPerformanceType()) ? "dance" : "music").equals("dance")) {
						spectator.setPerformanceWatchee(null);
						spectator.sendSystemMessage("You stop watching " + performer.getCustomName() + " because " + performer.getCustomName()
								+ " is out of range.", (byte) 0);
					}
					else {
						spectator.setPerformanceListenee(null);
						spectator.sendSystemMessage("You stop listening to " + performer.getCustomName() + " because " + performer.getCustomName()
								+ " is out of range.", (byte) 0);
					}
					spectator.setMoodAnimation("neutral");
					performer.removeAudience(spectator);

					spectator.getSpectatorTask().cancel(true);
				}
			}

		}, 2, 2, TimeUnit.SECONDS);

		spectator.setSpectatorTask(spectatorTask);
	}
	
	public void performFlourish(final CreatureObject performer, int flourish) {

		if (performer.getFlourishCount() > 0) {
			performer.sendSystemMessage("@performance:wait_flourish_self", (byte) 0);
			return;
		}
		Performance performance = getPerformanceByIndex(performer.getPerformanceId());

		if(performance == null)
			return;

		String anmFlo = "skill_action_" + flourish;

		if (flourish == 9)
			anmFlo = "mistake";
		
		performer.setFlourishCount(1);
		performer.sendSystemMessage("@performance:flourish_perform", (byte) 0);
		performer.doSkillAnimation(anmFlo);

		scheduler.schedule(new Runnable() {

			@Override
			public void run() {
				performer.setFlourishCount(0);
			}

		}, (long) performance.getLoopDuration(), TimeUnit.SECONDS);
	}

	@Override
	public void shutdown() {

	}

}
