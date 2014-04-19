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

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.python.core.Py;
import org.python.core.PyObject;

import protocol.swg.FactionResponseMessage;

import resources.common.FileUtilities;
import resources.common.Opcodes;
import resources.datatables.FactionStatus;
import resources.datatables.PvpStatus;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;

import engine.clientdata.StfTable;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class FactionService implements INetworkDispatch {
	
	private NGECore core;
	
	private Map<String, Integer> factionMap = new TreeMap<String, Integer>();
	
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
	
	public boolean isFaction(String faction) {
		if (factionMap.containsKey(faction)) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Returns true if creature2 is an ally of creature1
	 * 
	 * Will be useful for NPC AI, so they know who to help and who to be indifferent to.
	 */
	public boolean isFactionAlly(CreatureObject creature1, CreatureObject creature2) {
		if (creature1 == null || creature2 == null) {
			return false;
		}
		
		String faction1 = creature1.getFaction();
		String faction2 = creature2.getFaction();
		
		if (!isFaction(faction1) || !isFaction(faction2)) {
			return false;
		}
		
		if (FileUtilities.doesFileExist("scripts/faction/" + faction1 + ".py")) {
			PyObject method = core.scriptService.getMethod("scripts/faction/", faction1, "isAlly");
			
			if (method != null && method.isCallable()) {
				return ((method.__call__(Py.java2py(faction2)).asInt() == 1) ? true : false);
			}
		}
		
		return false;
	}
	
	/*
	 * Returns true if creature2 is an enemy of creature1
	 * 
	 * Will be useful for NPC AI, so they know who to attack and who to be indifferent to.
	 */
	public boolean isFactionEnemy(CreatureObject creature1, CreatureObject creature2) {
		if (creature1 == null || creature2 == null) {
			return false;
		}
		
		String faction1 = creature1.getFaction();
		String faction2 = creature2.getFaction();
		
		if (!isFaction(faction1) || !isFaction(faction2)) {
			return false;
		}
		
		if (FileUtilities.doesFileExist("scripts/faction/" + faction1 + ".py")) {
			PyObject method = core.scriptService.getMethod("scripts/faction/", faction1, "isEnemy");
			
			if (method != null && method.isCallable()) {
				return ((method.__call__(Py.java2py(faction2)).asInt() == 1) ? true : false);
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
	 */
	/*public int calculatePvpStatus(CreatureObject player, TangibleObject target) {
		PlayerObject ghost = (PlayerObject) player.getSlottedObject("ghost");
		
		int pvpBitmask = target.getPvPBitmask();
		
		// Seefo: Casting target to type CreatureObject as temporary fix.  I am unsure whether or not we want to put a factionStatus member inside of TangibleObject.
		if (((CreatureObject) target).getFactionStatus() == FactionStatus.Combatant) {
			pvpBitmask |= PvpStatus.Enemy;
		}
		
		if (((CreatureObject) target).getFactionStatus() == FactionStatus.SpecialForces) {
			pvpBitmask |= PvpStatus.Overt;
			
			if (target.getSlottedObject("ghost") != null) {
				pvpBitmask |= PvpStatus.Enemy;
			}
		}
		
		if (target.getSlottedObject("ghost") != null) {
			pvpBitmask |= PvpStatus.Player;
			
			if ((!player.getFaction().equals(target.getFaction()) &&
			player.getFactionStatus() == FactionStatus.SpecialForces &&
			((CreatureObject) target).getFactionStatus() == FactionStatus.SpecialForces) ||
			core.combatService.areInDuel(player, (CreatureObject) target)) {
				pvpBitmask |= (PvpStatus.Attackable | PvpStatus.Aggressive);
			}
			
			if (core.combatService.areInDuel(player, (CreatureObject) target)) {
				//pvpBitmask |= PvpStatus.Dueling;
			}
			
			return pvpBitmask;
		}
		
		if (player.getFaction().equals(target.getFaction())) {
			return pvpBitmask;
		}
		
		if (target.getOption(Options.ATTACKABLE)) {
			pvpBitmask |= PvpStatus.Attackable;
		}
		
		if (target.getOption(Options.AGGRESSIVE)) {
			pvpBitmask |= PvpStatus.Aggressive;
		} else {
			Integer factionStanding = ghost.getFactionStandingMap().get(target.getFaction());
			
			if (factionStanding != null) {
				if (((TangibleObject) target).getOption(Options.ATTACKABLE) && factionStanding < 0) {
					pvpBitmask |= PvpStatus.Aggressive;
				}
			}
		}
		
		return pvpBitmask;
	}*/
	
	// temp fix until calculatePvpStatus is fixed
	public int calculatePvpStatus(CreatureObject player, TangibleObject target) {
		
		if(target.getSlottedObject("ghost") != null) {
			
			if(!player.getFaction().equals(target.getFaction()) && player.getFactionStatus() == FactionStatus.SpecialForces && ((CreatureObject) target).getFactionStatus() == FactionStatus.SpecialForces)
				return 55;
			else if(core.combatService.areInDuel(player, (CreatureObject) target))
				return 55;
			else
				return 0x14;
						
		} else {
			if(target.getAttachment("AI") != null)
				return PvpStatus.Attackable;
			else
				return 0;
		}
		
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
