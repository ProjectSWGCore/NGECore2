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

import resources.common.FileUtilities;
import resources.common.Opcodes;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.tangible.TangibleObject;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class FactionService implements INetworkDispatch {
	
	private NGECore core;
	
	private Map<String, Integer> factionMap = new TreeMap<String, Integer>();
	
	public FactionService(NGECore core) {
		DatatableVisitor factionTable;
		
		this.core = core;
		
		try {
			factionTable = ClientFileManager.loadFile("strings/en/faction/faction_names.stf", DatatableVisitor.class);
			
			for (int s = 0; s < factionTable.getRowCount(); s++) {
				if (factionTable.getObject(s, 0) != null) {
					String faction = ((String) factionTable.getObject(s, 1));
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
		if (creature == null) {
			return;
		}
		
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		
		if (player == null) {
			return;
		}
		
		player.modifyFactionStanding(faction, factionStanding);
	}
	
	public void updateFactionStanding(SWGObject killedObject) {
		if (!(killedObject instanceof TangibleObject) || killedObject == null) {
			return;
		}
		
		TangibleObject target = (TangibleObject) killedObject;
		
		if (!(((TangibleObject) killedObject).getKiller() instanceof TangibleObject) || ((TangibleObject) killedObject).getKiller() == null) {
			return;
		}
		
		TangibleObject killer = ((TangibleObject) killedObject).getKiller();
		
		if (killer != null && target != null && isFaction(target.getFaction())) {
			if (FileUtilities.doesFileExist("scripts/faction/standing/" + target.getFaction() + ".py")) {
				
				PyObject method = core.scriptService.getMethod("scripts/faction/standing/", target.getFaction(), "update");
				
				if (method != null && method.isCallable()) {
					method.__call__(Py.java2py(core), Py.java2py(killer), Py.java2py(target));
				}
			}
		}
	}
	
	public boolean isFaction(String faction) {
		if (factionMap.containsKey(faction)) {
			return true;
		}
		
		return false;
	}
	
	public Map<String, Integer> getFactionMap() {
		return factionMap;
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
		swgOpcodes.put(Opcodes.FactionRequestMessage, new INetworkRemoteEvent() {
			
			@Override
			public void handlePacket(IoSession session, IoBuffer buffer) throws Exception {
				
			}
			
		});
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
}
