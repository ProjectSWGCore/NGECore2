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
package resources.objects.tangible;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import protocol.swg.ObjControllerMessage;
import protocol.swg.PlayClientEffectObjectMessage;
import protocol.swg.StopClientEffectObjectByLabel;
import protocol.swg.UpdatePVPStatusMessage;
import protocol.swg.objectControllerObjects.ShowFlyText;

import resources.common.RGB;
import resources.objects.creature.CreatureObject;
import services.ai.AIActor;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Persistent(version=0)
public class TangibleObject extends SWGObject {
	
	// TODO: Thread safety
	
	protected int incapTimer = 10;
	private int conditionDamage = 0;
	protected int pvpBitmask = 0;
	protected byte[] customization;
	private List<Integer> componentCustomizations = new ArrayList<Integer>();
	protected int optionsBitmask = 0;
	private int maxDamage = 1000;
	private boolean staticObject = true;
	protected String faction = "neutral"; // Says you're "Imperial Special Forces" if it's 0 for some reason
	@NotPersistent
	private Vector<TangibleObject> defendersList = new Vector<TangibleObject>();	// unused in packets but useful for the server
	@NotPersistent
	private TangibleMessageBuilder messageBuilder;
	
	private int respawnTime = 0;
	private Point3D spawnCoordinates = new Point3D(0, 0, 0);
	
	@NotPersistent
	private TangibleObject killer = null;
	
	public TangibleObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 1, 0), template);
		messageBuilder = new TangibleMessageBuilder(this);
		if (this.getClass().getSimpleName().equals("TangibleObject")) setIntAttribute("volume", 1);
	}
	
	public TangibleObject(long objectID, Planet planet, String template, Point3D position, Quaternion orientation) {
		super(objectID, planet, position, orientation, template);
		messageBuilder = new TangibleMessageBuilder(this);
		spawnCoordinates = position.clone();
		if (this.getClass().getSimpleName().equals("TangibleObject")) setIntAttribute("volume", 1);
	}
	
	public TangibleObject() {
		super();
		messageBuilder = new TangibleMessageBuilder(this);
	}
	
	public void setCustomName2(String customName) {
		setCustomName(customName);
		
		notifyObservers(messageBuilder.buildCustomNameDelta(customName), true);
	}

	public int getIncapTimer() {
		return incapTimer;
	}

	public void setIncapTimer(int incapTimer) {
		this.incapTimer = incapTimer;
	}

	public synchronized int getConditionDamage() {
		return conditionDamage;
	}

	public synchronized void setConditionDamage(int conditionDamage) {
		if(conditionDamage < 0)
			conditionDamage = 0;
		else if(conditionDamage > getMaxDamage())
			conditionDamage = getMaxDamage();
		this.conditionDamage = conditionDamage;
		notifyObservers(messageBuilder.buildConditionDamageDelta(conditionDamage), false);
		if (maxDamage > 0) {
			this.setStringAttribute("condition", (maxDamage + "/" + (maxDamage - conditionDamage)));
		}
	}

	public byte[] getCustomization() {
		return customization;
	}

	public void setCustomization(byte[] customization) {
		this.customization = customization;
	}

	public List<Integer> getComponentCustomizations() {
		return componentCustomizations;
	}

	public void setComponentCustomizations(List<Integer> componentCustomizations) {
		this.componentCustomizations = componentCustomizations;
	}

	public int getOptionsBitmask() {
		return optionsBitmask;
	}

	public void setOptionsBitmask(int optionsBitmask) {
		this.optionsBitmask = optionsBitmask;
	}
	
	public void setOptions(int options, boolean add) {
		synchronized(objectMutex) {
			if (options != 0) {
				if (add) {
					addOption(options);
				} else {
					removeOption(options);
				}
			}
		}
	}
	
	public boolean getOption(int option) {
		synchronized(objectMutex) {
			return ((optionsBitmask & option) == option);
		}
	}
	
	public void addOption(int option) {
		setOptionsBitmask(getOptionsBitmask() | option);
	}
	
	public void removeOption(int option) {
		setOptionsBitmask(getOptionsBitmask() & ~option);
	}
	
	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
		
		this.setStringAttribute("condition", (maxDamage + "/" + (maxDamage - conditionDamage)));
	}

	public boolean isStaticObject() {
		return staticObject;
	}

	public void setStaticObject(boolean staticObject) {
		this.staticObject = staticObject;
	}
	
	public int getPvPBitmask() {
		synchronized(objectMutex) {
			return pvpBitmask;
		}
	}

	public void setPvPBitmask(int pvpBitmask) {
		synchronized(objectMutex) {
			this.pvpBitmask = pvpBitmask;
		}
	}
	
	public boolean getPvpStatus(int pvpStatus) {
		synchronized(objectMutex) {
			return ((pvpBitmask & pvpStatus) != 0);
		}
	}
	
	public void setPvpStatus(int pvpBitmask, boolean add) {
		synchronized(objectMutex) {
			if (pvpBitmask != 0) {
				if (add) {
					this.pvpBitmask |= pvpBitmask;
				} else {
					this.pvpBitmask &= ~pvpBitmask;
				}
			}
		}
	}
	
	public String getFaction() {
		synchronized(objectMutex) {
			return faction;
		}
	}
	
	public void setFaction(String faction) {
		synchronized(objectMutex) {
			this.faction = faction;
		}
	}

	public Vector<TangibleObject> getDefendersList() {
		return defendersList;
	}
	
	public void addDefender(TangibleObject defender) {
				
		defendersList.add(defender);
		
		if(this instanceof CreatureObject) {
			CreatureObject creature = (CreatureObject) this;
			
			if(creature.getCombatFlag() == 0)
				creature.setCombatFlag((byte) 1);
		}
		
	}
	
	public void removeDefender(TangibleObject defender) {
		
		defendersList.remove(defender);
		
		if(this instanceof CreatureObject) {
			CreatureObject creature = (CreatureObject) this;
			
			if(creature.getCombatFlag() == 1 && defendersList.isEmpty())
				creature.setCombatFlag((byte) 0);
		}
		
	}
	
	public boolean isAttackableBy(CreatureObject attacker) {
		
		CreatureObject creature;
		
		if(this instanceof CreatureObject) {
			creature = (CreatureObject) this;
			if(creature.getDuelList().contains(attacker) && attacker.getDuelList().contains(this))
				return true;
		}
		
		if(faction.equals("rebel") && attacker.getFaction().equals("rebel"))
			return false;
		else if(faction.equals("imperial") && attacker.getFaction().equals("imperial"))
			return false;
		else if(attacker.getSlottedObject("ghost") != null) {
			
			if(this instanceof CreatureObject && getSlottedObject("ghost") != null) {
				
				creature = (CreatureObject) this;
				
				if(creature.getFactionStatus() == 2 && attacker.getFactionStatus() == 2)
					return true;
				else
					return false;
				
			}

			if((faction.equals("rebel") || faction.equals("imperial")) && attacker.getFactionStatus() >= 1)
				return true;
			else if((faction.equals("rebel") || faction.equals("imperial")) && attacker.getFactionStatus() == 0)
				return false;
			
			return getPvPBitmask() == 1 || getPvPBitmask() == 2;
			
		}

		return getPvPBitmask() == 1 || getPvPBitmask() == 2;
	}
	
	public void showFlyText(String stfFile, String stfString, float scale, RGB color, int displayType) {
		Set<Client> observers = getObservers();
		
		if (getClient() != null) {
			getClient().getSession().write((new ObjControllerMessage(0x0000000B, new ShowFlyText(getObjectID(), getObjectID(), stfFile, stfString, scale, color, displayType))).serialize());
		}
		
		for (Client client : observers) {
			client.getSession().write((new ObjControllerMessage(0x0000000B, new ShowFlyText(client.getParent().getObjectID(), getObjectID(), stfFile, stfString, scale, color, displayType))).serialize());
		}
	}
	
	public void showFlyText(String stfFile, String stfString, String customText, int xp, float scale, RGB color, int displayType) {
		Set<Client> observers = getObservers();
		
		if (getClient() != null) {
			getClient().getSession().write((new ObjControllerMessage(0x0000000B, new ShowFlyText(getObjectID(), getObjectID(), 56, 1, 1, -1, stfFile, stfString, customText, xp, scale, color, displayType))).serialize());
		}
		
		for (Client client : observers) {
			client.getSession().write((new ObjControllerMessage(0x0000000B, new ShowFlyText(client.getParent().getObjectID(), getObjectID(), 56, 1, 1, -1, stfFile, stfString, customText, xp, scale, color, displayType))).serialize());
		}
	}
	
	public void playEffectObject(String effectFile, String commandString) {
		notifyObservers(new PlayClientEffectObjectMessage(effectFile, getObjectID(), commandString), true);
	}
	
	public void stopEffectObject(String commandString) {
		notifyObservers(new StopClientEffectObjectByLabel(getObjectID(), commandString), true);
	}
	
	public int getRespawnTime() {
		synchronized(objectMutex) {
			return respawnTime;
		}
	}
	
	public void setRespawnTime(int respawnTime) {
		synchronized(objectMutex) {
			this.respawnTime = respawnTime;
		}
	}
	
	public TangibleObject getKiller() {
		synchronized(objectMutex) {
			return killer;
		}
	}
	
	public void setKiller(TangibleObject killer) {
		synchronized(objectMutex) {
			this.killer = killer;
		}
	}
	
	@Override
	public void sendBaselines(Client destination) {


		if(destination == null || destination.getSession() == null) {
			System.out.println("NULL destination");
			return;
		}
		
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
		destination.getSession().write(messageBuilder.buildBaseline8());
		destination.getSession().write(messageBuilder.buildBaseline9());
		
		if(getPvPBitmask() != 0) {
			UpdatePVPStatusMessage upvpm = new UpdatePVPStatusMessage(getObjectID());
			upvpm.setFaction(UpdatePVPStatusMessage.factionCRC.Neutral);
			upvpm.setStatus(getPvPBitmask());
			destination.getSession().write(upvpm.serialize());
		}
		

	}
	
}
