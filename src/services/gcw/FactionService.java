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
package services.gcw;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.python.core.Py;
import org.python.core.PyObject;

import protocol.swg.FactionResponseMessage;
import resources.common.FileUtilities;
import resources.common.Opcodes;
import resources.datatables.DisplayType;
import resources.datatables.FactionStatus;
import resources.datatables.Options;
import resources.datatables.PvpStatus;
import resources.objects.creature.CreatureObject;
import resources.objects.mission.MissionObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;
import engine.clientdata.ClientFileManager;
import engine.clientdata.StfTable;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class FactionService implements INetworkDispatch {
	
	private NGECore core;
	
	private Map<String, Integer> factionMap = new TreeMap<String, Integer>();
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private DatatableVisitor pvpFactions;
	
	public FactionService(NGECore core) {
		this.core = core;
		
		try {
			StfTable stf = new StfTable("clientdata/string/en/faction/faction_names.stf");
			
			for (int s = 1; s < stf.getRowCount(); s++) {
				String faction = stf.getStringById(s).getKey();
				
				if (faction != null && faction != "") {
					factionMap.put(faction, CRC.StringtoCRC(faction));
				}
			}
			
			pvpFactions = ClientFileManager.loadFile("datatables/player/pvp_factions.iff", DatatableVisitor.class);
        } catch (Exception e) {
                e.printStackTrace();
        }
	}
	
	public int getCRC(String faction) {
		return CRC.StringtoCRC(faction);
	}
	
	public String getName(int factionCrc) {
		for (Entry<String, Integer> entry : factionMap.entrySet()) {
			if (factionCrc == entry.getValue()) {
				return entry.getKey();
			}
		}
		
		return "";
	}
	
	public void addFactionStanding(CreatureObject creature, String faction, int factionStanding) {
		if (!isFaction(faction)) {
			return;
		}
		
		if (creature == null) {
			return;
		}
		
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		
		if (player == null) {
			return;
		}
		
		player.modifyFactionStanding(faction, factionStanding);
	}
	
	public void removeFactionStanding(CreatureObject creature, String faction, int factionStanding) {
		if (!isFaction(faction)) {
			return;
		}
		
		if (creature == null) {
			return;
		}
		
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		
		if (player == null) {
			return;
		}
		
		player.modifyFactionStanding(faction, -factionStanding);
	}
	
	public void delegateFactionPoints(CreatureObject actor, CreatureObject target, String faction, int points) {
		if (actor == null || actor.getSlottedObject("ghost") == null) {
			return;
		}
		
		if (target == null || actor.getSlottedObject("ghost") == null) {
			return;
		}
		
		if (!isFaction(faction)) {
			return;
		}
		
		if (points <= 0) {
			return;
		}
		
		
	}
	
	public boolean isFaction(String faction) {
		if (factionMap.containsKey(faction)) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Returns true if target is an ally of object
	 * 
	 * Will be useful for NPC AI, so they know who to help and who to be indifferent to.
	 */
	public boolean isFactionAlly(TangibleObject object, TangibleObject target) {
		if (object == null || target == null) {
			return false;
		}
		
		String objectFaction = object.getFaction();
		String targetFaction = target.getFaction();
		
		if (!isFaction(objectFaction) || !isFaction(targetFaction)) {
			return false;
		}
		
		if (FileUtilities.doesFileExist("scripts/faction/" + objectFaction + ".py")) {
			PyObject method = core.scriptService.getMethod("scripts/faction/", objectFaction, "isAlly");
			
			if (method != null && method.isCallable()) {
				return ((method.__call__(Py.java2py(targetFaction)).asInt() == 1) ? true : false);
			}
		}
		
		return false;
	}
	
	/*
	 * Returns true if target is an enemy of object
	 * 
	 * Will be useful for NPC AI, so they know who to attack and who to be indifferent to.
	 */
	public boolean isFactionEnemy(TangibleObject object, TangibleObject target) {
		if (object == null || target == null) {
			return false;
		}
		
		String objectFaction = object.getFaction();
		String targetFaction = target.getFaction();
		
		if (!isFaction(objectFaction) || !isFaction(targetFaction)) {
			return false;
		}
		
		if (FileUtilities.doesFileExist("scripts/faction/" + objectFaction + ".py")) {
			PyObject method = core.scriptService.getMethod("scripts/faction/", objectFaction, "isEnemy");
			
			if (method != null && method.isCallable()) {
				return ((method.__call__(Py.java2py(targetFaction)).asInt() == 1) ? true : false);
			}
		}
		
		return false;
	}
	
	/*
	 * Calculates the target's pvp bitmask based on the person's
	 * faction standing and the enemy's options bitmask.
	 * 
	 * This shouldn't be what we "set" the bitmask to, just
	 * what we use when calculating how to treat a target
	 * and when we send it.
	 * 
	 * This should be used instead of getPvPBitmask where possible, but
	 * should not be used in setPvPBitmask.
	 * 
	 * *** Updated this so it calculates if even a tangible object
	 * should see something as attackable (so it works for npcs and turrets).
	 * 
	 * Also takes into account bounty targets and if it's a pet.
	 */
	public int calculatePvpStatus(TangibleObject object, TangibleObject target) {
		PlayerObject ghost = (PlayerObject) object.getSlottedObject("ghost");
		
		int pvpBitmask = 0;
		
		if (object == target) {
			pvpBitmask |= PvpStatus.Self;
		}
		
		if (target.getSlottedObject("ghost") != null) {
			pvpBitmask |= PvpStatus.Player;
		}
		
		if (target instanceof CreatureObject && ((CreatureObject) target).getOwnerId() > 0) {
			target = (TangibleObject) core.objectService.getObject(((CreatureObject) target).getOwnerId());
		}
		
		if ((target.getPvpBitmask() & PvpStatus.GoingCovert) == PvpStatus.GoingCovert) {
			pvpBitmask |= PvpStatus.GoingCovert;
		}
		
		if ((target.getPvpBitmask() & PvpStatus.GoingOvert) == PvpStatus.GoingOvert) {
			pvpBitmask |= PvpStatus.GoingOvert;
		}
		
		if (target.getFactionStatus() == FactionStatus.SpecialForces) {
			pvpBitmask |= PvpStatus.Overt;
		}
		
		// Everything below assumes target is potentially attackable.
		if (!target.getOption(Options.ATTACKABLE) || target.getOption(Options.INVULNERABLE)) {
			return pvpBitmask;
		}
		
		if (ghost != null && target.getSlottedObject("ghost") != null &&
		core.combatService.areInDuel((CreatureObject) object, (CreatureObject) target)) {
			pvpBitmask |= (PvpStatus.Attackable | PvpStatus.Aggressive);
		}
		
		if (object.getDefendersList().contains(target)) {
			pvpBitmask |= (PvpStatus.Attackable | PvpStatus.Aggressive);
		}
		
		AtomicReference<Integer> ref = new AtomicReference<Integer>(0);
		
		long targetId = target.getObjectID();
		
		if (ghost != null && ghost.getBountyMissionId() != 0) {
			if (((MissionObject) core.objectService.getObject(ghost.getBountyMissionId())).getBountyMarkId() == targetId) {
				ref.set(PvpStatus.Attackable | PvpStatus.Aggressive);
			}
		}
		
		/*if (ghost != null && ghost.getProfession().contains("smuggler") &&
		target.getSlottedObject("datapad") != null) {
			target.getSlottedObject("datapad").viewChildren(target, false, false, (mission) -> {
				if (mission instanceof MissionObject && ((MissionObject) mission).getBountyMarkId() == object.getObjectID()) {
					ref.set(PvpStatus.Attackable);
				}
			});
		}*/
		
		pvpBitmask |= ref.get();
		
		if (target instanceof CreatureObject && ((CreatureObject) target).getTefTime() > 0) {
			//pvpBitmask |= PvpStatus.TEF;
		}
		
		if (target.getFaction().length() > 0 && target.getFaction().equals(object.getFaction())) {
			return pvpBitmask;
		}
		
		if (target.getSlottedObject("ghost") == null && target.getFactionStatus() == FactionStatus.OnLeave) {
			pvpBitmask |= PvpStatus.Attackable;
		}
		
		if (target.getOption(Options.AGGRESSIVE)) {
			pvpBitmask |= PvpStatus.Aggressive;
		}
		
		if (object.getFactionStatus() == FactionStatus.OnLeave) {
			return pvpBitmask;
		}
		
		if (target.getFactionStatus() >= FactionStatus.Combatant) {
			if (ghost != null) {
				if (target.getSlottedObject("ghost") == null) {
					pvpBitmask |= PvpStatus.Enemy;
					
					Integer factionStanding = ghost.getFactionStandingMap().get(target.getFaction());
					
					if (factionStanding != null) {
						if (factionStanding < 0) {
							pvpBitmask |= PvpStatus.Aggressive;
						} else {
							pvpBitmask |= PvpStatus.Attackable;
						}
					} else {
						pvpBitmask |= PvpStatus.Attackable;
					}
					
					return pvpBitmask;
				}
			} else {
				if (isFactionEnemy(object, target)) {
					pvpBitmask |= PvpStatus.Attackable | PvpStatus.Enemy;
				}
				
				if (isFactionEnemy(target, object)) {
					pvpBitmask |= PvpStatus.Aggressive | PvpStatus.Enemy;
				}
			}
		}
		
		if (target.getFactionStatus() == FactionStatus.SpecialForces) {
			if (object.getFactionStatus() == FactionStatus.SpecialForces) {
				pvpBitmask |= (PvpStatus.Attackable | PvpStatus.Aggressive | PvpStatus.Enemy);
			}
		}
		
		return pvpBitmask;
	}
	
	public boolean isPvpFaction(String faction) {
		if (!isFaction(faction)) {
			return false;
		}
		
		try {
			for (int i = 0; i < pvpFactions.getRowCount(); i++) {
				if (pvpFactions.getObject(i, 0) != null) {
					if (((String) pvpFactions.getObject(i, 1)).equals(faction)) {
						return true;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void join(CreatureObject actor, String faction) {
		if (actor.getPvpBitmask() == PvpStatus.GoingCovert) {
			actor.sendSystemMessage("@faction_recruiter:pvp_status_changing", DisplayType.Broadcast);
			return;
		}
		
		if (actor.getPvpBitmask() == PvpStatus.GoingOvert) {
			actor.sendSystemMessage("@faction_recruiter:pvp_status_changing", DisplayType.Broadcast);
			return;
		}
		
		if (actor.getFaction().length() > 0) {
			actor.sendSystemMessage("@faction_recruiter:resign_on_leave", DisplayType.Broadcast);
			return;
		}
		
		scheduler.schedule(() -> {
			try {
				actor.setFaction(faction);
				actor.setFactionStatus(FactionStatus.OnLeave);
				actor.setPvpBitmask(0);
				PlayerObject player = ((PlayerObject) actor.getSlottedObject("ghost"));
				if (player != null) {
					player.resetGcwPoints();
					player.resetPvpKills();
					player.setCurrentRank(1);
				}
				actor.updatePvpStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 1, TimeUnit.SECONDS);
	}
	
	public void changeFactionStatus(CreatureObject actor, int factionStatus) {
		long time = 1;
		String message = "";
		
		if (factionStatus == actor.getFactionStatus()) {
			return;
		}
		
		if (actor.getPvpBitmask() == PvpStatus.GoingCovert) {
			actor.sendSystemMessage("@faction_recruiter:pvp_status_changing", DisplayType.Broadcast);
			return;
		}
		
		if (actor.getPvpBitmask() == PvpStatus.GoingOvert) {
			actor.sendSystemMessage("@faction_recruiter:pvp_status_changing", DisplayType.Broadcast);
			return;
		}
		
		switch (factionStatus) {
			case FactionStatus.OnLeave:
				actor.setPvpStatus(PvpStatus.GoingCovert, true);
				actor.updatePvpStatus();
				time = 300;
				message = "on_leave_complete";
				break;
			case FactionStatus.Combatant:
				actor.setPvpStatus(PvpStatus.GoingCovert, true);
				actor.updatePvpStatus();
				
				switch (actor.getFactionStatus()) {
					case FactionStatus.OnLeave:
						actor.sendSystemMessage("@faction_recruiter:on_leave_to_covert", DisplayType.Broadcast);
						time = 30;
						break;
					case FactionStatus.SpecialForces:
						actor.sendSystemMessage("@faction_recruiter:overt_to_covert", DisplayType.Broadcast);
						time = 300;
						break;
				}
				
				message = "covert_complete";
				break;
			case FactionStatus.SpecialForces:
				actor.sendSystemMessage("@faction_recruiter:covert_to_overt", DisplayType.Broadcast);
				actor.setPvpStatus(PvpStatus.GoingOvert, true);
				time = 30;
				message = "overt_complete";
				break;
		}
		
		final String finalMessage = message;
		
		scheduler.schedule(() -> {
			try {
				actor.setPvpBitmask(0);
				actor.setFactionStatus(factionStatus);
				actor.updatePvpStatus();
				actor.sendSystemMessage("@faction_recruiter:" + finalMessage, DisplayType.Broadcast);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, time, TimeUnit.SECONDS);
	}
	
	public void resign(CreatureObject actor) {
		if (actor.getPvpBitmask() == PvpStatus.GoingCovert) {
			actor.sendSystemMessage("@faction_recruiter:pvp_status_changing", DisplayType.Broadcast);
			return;
		}
		
		if (actor.getPvpBitmask() == PvpStatus.GoingOvert) {
			actor.sendSystemMessage("@faction_recruiter:pvp_status_changing", DisplayType.Broadcast);
			return;
		}
		
		if (actor.getFactionStatus() == FactionStatus.OnLeave) {
			actor.sendSystemMessage("@faction_recruiter:resign_on_leave", DisplayType.Broadcast);
			return;
		}
		
		long time = 1;
		
		if (actor.getFactionStatus() == FactionStatus.SpecialForces) {
			actor.sendSystemMessage("@faction_recruiter:sui_resig_complete_in_5", DisplayType.Broadcast);
			actor.setPvpStatus(PvpStatus.GoingCovert, true);
			actor.updatePvpStatus();
			time = 300;
		}
		
		scheduler.schedule(() -> {
			try {
				actor.setPvpBitmask(0);
				actor.setFactionStatus(FactionStatus.OnLeave);
				actor.setFaction("");
				PlayerObject player = ((PlayerObject) actor.getSlottedObject("ghost"));
				if (player != null) {
					player.resetGcwPoints();
					player.resetPvpKills();
					player.setCurrentRank(0);
				}
				actor.updatePvpStatus();
				actor.sendSystemMessage("@faction_recruiter:resign_complete", DisplayType.Broadcast);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, time, TimeUnit.SECONDS);
	}
	
	public Map<String, Integer> getFactionMap() {
		return factionMap;
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		swgOpcodes.put(Opcodes.FactionRequestMessage, new INetworkRemoteEvent() {
			
			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				Client client = core.getClient(session);
				
				if (client == null) {
					return;
				}
				
				CreatureObject creature = (CreatureObject) client.getParent();
				
				if (creature == null) {
					return;
				}
				
				PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
				
				if (player == null) {
					return;
				}
				
				session.write((new FactionResponseMessage(player.getFactionStandingMap(), 0)).serialize());
			}
			
		});
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
}
