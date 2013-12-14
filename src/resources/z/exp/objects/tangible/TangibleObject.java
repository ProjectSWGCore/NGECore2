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
package resources.z.exp.objects.tangible;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

import resources.datatables.FactionStatus;
import resources.z.exp.objects.Baseline;
import resources.z.exp.objects.SWGList;
import resources.z.exp.objects.creature.CreatureObject;
import resources.z.exp.objects.object.BaseObject;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent
public class TangibleObject extends BaseObject {
	
	@NotPersistent
	private TangibleMessageBuilder messageBuilder;
	
	@NotPersistent
	private List<TangibleObject> defendersList = new ArrayList<TangibleObject>();
	
	public TangibleObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String template) {
		super(objectID, planet, position, orientation, template);
	}
	
	public TangibleObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 1, 0), template);
	}
	
	public TangibleObject() {
		super();
	}
	
	@Override
	public void initializeBaselines() {
		super.initializeBaselines();
		initializeBaseline(8);
		initializeBaseline(9);
	}
	
	@Override
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		baseline.put("faction", "");
		baseline.put("pvpBitmask", 0);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("faction", 0);
		baseline.put("factionStatus", 0);
		baseline.put("customization", new Byte[] { 0x00, 0x00 });
		baseline.put("componentCustomizationList", new SWGList<Integer>(this, 3, 7, false));
		baseline.put("optionsBitmask", 0);
		baseline.put("incapacityTimer", 0);
		baseline.put("conditionDamage", 0);
		baseline.put("maximumCondition", 0);
		baseline.put("isStatic", false);
		return baseline;
	}
	
	@Override
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("inCombat", false);
		baseline.put("3", (long) 0); // Unknown
		baseline.put("4", (long) 0); // Unknown
		baseline.put("5", (long) 0); // Unknown
		baseline.put("6", (long) 0); // Unknown
		baseline.put("7", 0); // Unknown
		return baseline;
	}
	
	@Override
	public Baseline getBaseline8() {
		Baseline baseline = super.getBaseline8();
		return baseline;
	}
	
	@Override
	public Baseline getBaseline9() {
		Baseline baseline = super.getBaseline9();
		return baseline;
	}
	
	public String getFaction() {
		synchronized(objectMutex) {
			return (String) otherVariables.get("faction");
		}
	}
	
	public void setFaction(String faction) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			otherVariables.set("faction", faction);
			buffer = baseline3.set("faction", CRC.StringtoCRC(faction));
		}
		
		notifyClients(buffer, true);
	}
	
	public int getFactionStatus() {
		synchronized(objectMutex) {
			return (int) baseline3.get("factionStatus");
		}
	}
	
	public void setFactionStatus(int factionStatus) {
		switch (factionStatus) {
			case FactionStatus.OnLeave:
			case FactionStatus.Combatant:
			case FactionStatus.SpecialForces:
				break;
			default:
				factionStatus = 0;
		}
		
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("factionStatus", factionStatus);
		}
		
		notifyClients(buffer, true);
	}
	
	public byte[] getCustomization() {
		synchronized(objectMutex) {
			return (byte[]) baseline3.get("customization");
		}
	}
	
	public void setCustomization(byte[] customization) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("customization", customization);
		}
		
		notifyClients(buffer, true);
	}
	
	@SuppressWarnings("unchecked")
	public SWGList<Integer> getComponentCustomizationList() {
		return (SWGList<Integer>) baseline3.get("componentCustomizationList");
	}
	
	public int getOptionsBitmask() {
		synchronized(objectMutex) {
			return (int) baseline3.get("optionsBitmask");
		}
	}
	
	public void setOptionsBitmask(int optionsBitmask) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("optionsBitmask", optionsBitmask);
		}
		
		notifyClients(buffer, true);
	}
	
	public int getIncapacityTimer() {
		synchronized(objectMutex) {
			return (int) baseline3.get("incapacityTimer");
		}
	}
	
	public void setIncapacityTimer(int incapacityTimer) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("incapacityTimer", incapacityTimer);
		}
		
		notifyClients(buffer, true);
	}
	
	public int getConditionDamage() {
		synchronized(objectMutex) {
			return (int) baseline3.get("conditionDamage");
		}
	}
	
	public void setConditionDamage(int conditionDamage) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("conditionDamage", conditionDamage);
		}
		
		notifyClients(buffer, true);
	}
	
	public int getMaximumCondition() {
		synchronized(objectMutex) {
			return (int) baseline3.get("maximumCondition");
		}
	}
	
	public void setMaximumCondition(int maximumCondition) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("maximumCondition", maximumCondition);
		}
		
		notifyClients(buffer, true);
	}
	
	public boolean isStatic() {
		synchronized(objectMutex) {
			return (boolean) baseline3.get("isStatic");
		}
	}
	
	public void setStatic(boolean isStatic) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline3.set("isStatic", isStatic);
		}
		
		notifyClients(buffer, true);
	}
	
	public void toggleStatic() {
		setStatic(!isStatic());
	}
	
	public boolean inCombat() {
		synchronized(objectMutex) {
			return (boolean) baseline6.get("inCombat");
		}
	}
	
	public void setInCombat(boolean inCombat) {
		IoBuffer buffer;
		
		synchronized(objectMutex) {
			buffer = baseline6.set("inCombat", inCombat);
		}
		
		notifyClients(buffer, true);
	}
	
	public void toggleCombat() {
		setInCombat(!inCombat());
	}
	
	public List<TangibleObject> getDefendersList() {
		return defendersList;
	}
	
	// All objects can be in combat (terminal mission flag bases, tutorial target box)
	public void addDefender(TangibleObject defender) {
		defendersList.add(defender);
		
		if (!inCombat()) {
			setInCombat(true);
		}
	}
	
	public void removeDefender(TangibleObject defender) {
		defendersList.remove(defender);
		
		if (defendersList.isEmpty() && inCombat()) {
			setInCombat(false);
		}
	}
	
	public int getPvPBitmask() {
		synchronized(objectMutex) {
			return (int) otherVariables.get("pvpBitmask");
		}
	}
	
	public void setPvPBitmask(int pvpBitmask) {
		synchronized(objectMutex) {
			otherVariables.set("pvpBitmask", pvpBitmask);
		}
	}
	
	public boolean isAttackableBy(CreatureObject attacker) {
		CreatureObject creature;
		
		if (this instanceof CreatureObject) {
			creature = (CreatureObject) this;
			
			if (creature.getDuelList().contains(attacker) && attacker.getDuelList().contains(this)) {
				return true;
			}
		}
		
		if (getFaction().equals("rebel") && attacker.getFaction().equals("rebel")) {
			return false;
		} else if (getFaction().equals("imperial") && attacker.getFaction().equals("imperial")) {
			return false;
		} else if(attacker.getSlottedObject("ghost") != null) {
			if (this instanceof CreatureObject && getSlottedObject("ghost") != null) {
				creature = (CreatureObject) this;
				
				if (creature.getFactionStatus() == 2 && attacker.getFactionStatus() == 2) {
					return true;
				} else {
					return false;
				}
			}
			
			if ((getFaction().equals("rebel") || getFaction().equals("imperial")) && attacker.getFactionStatus() >= 1) {
				return true;
			} else if ((getFaction().equals("rebel") || getFaction().equals("imperial")) && attacker.getFactionStatus() == 0) {
				return false;
			}
			
			return getPvPBitmask() == 1 || getPvPBitmask() == 2;
		}
		
		return getPvPBitmask() == 1 || getPvPBitmask() == 2;
	}
	
	@Override
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, false);
	}
	
	@Override
	public TangibleMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new TangibleMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			//destination.getSession().write(baseline3.getBaseline());
			//destination.getSession().write(baseline6.getBaseline());
			//destination.getSession().write(baseline8.getBaseline());
			//destination.getSession().write(baseline9.getBaseline());
		}
	}
	
	@Override
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		switch (viewType) {
			case 3:
			{
				switch (updateType) {
					case 7:
					{
						buffer = baseline3.createDelta(7, buffer.array());
						notifyClients(buffer, true);
						break;
					}
				}
			}
		}
	}
	
}
