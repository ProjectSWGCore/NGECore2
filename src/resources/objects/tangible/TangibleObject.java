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

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.mina.core.buffer.IoBuffer;

import main.NGECore;
import protocol.swg.ObjControllerMessage;
import protocol.swg.PlayClientEffectObjectMessage;
import protocol.swg.StopClientEffectObjectByLabel;
import protocol.swg.UpdatePVPStatusMessage;
import protocol.swg.objectControllerObjects.ShowFlyText;
import resources.common.OutOfBand;
import resources.datatables.Options;
import resources.datatables.PvpStatus;
import resources.loot.LootGroup;
import resources.objects.ObjectMessageBuilder;
import resources.objects.SWGList;
import resources.objects.SWGSet;
import resources.objects.creature.CreatureObject;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.IDManagerVisitor;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.common.RGB;
import engine.resources.objects.Baseline;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@SuppressWarnings("unchecked")
public class TangibleObject extends SWGObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private transient TangibleMessageBuilder messageBuilder;
	
	private Map<String, Byte> customizationVariables = new HashMap<String, Byte>();
	
	protected transient Vector<TangibleObject> defendersList = new Vector<TangibleObject>();
	
	private transient int pvpBitmask = 0;
	
	//private TreeSet<TreeMap<String,Integer>> lootSpecification = new TreeSet<TreeMap<String,Integer>>();
	private transient List<LootGroup> lootGroups = new ArrayList<LootGroup>();
	private transient boolean looted = false;	
	private transient boolean lootLock = false;	
	private transient boolean creditRelieved = false;	
	private transient boolean lootItem = false;
	private transient TangibleObject killer = null;
	
	public TangibleObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String template) {
		super(objectID, planet, position, orientation, template);
		if (this.getClass().getSimpleName().equals("TangibleObject")) setIntAttribute("volume", 1);
		getBaseline(3).set("volume", 1);
	}
	
	public TangibleObject(long objectID, Planet planet, String template) {
		super(objectID, planet, new Point3D(0, 0, 0), new Quaternion(1, 0, 1, 0), template);
		if (this.getClass().getSimpleName().equals("TangibleObject")) setIntAttribute("volume", 1);
		getBaseline(3).set("volume", 1);
	}
	
	public TangibleObject() {
		super();
	}
	
	public void initAfterDBLoad() {
		super.init();
		defendersList = new Vector<TangibleObject>();
	}
	
	public Baseline getOtherVariables() {
		Baseline baseline = super.getOtherVariables();
		baseline.put("stackable", false);
		baseline.put("noSell", false);
		baseline.put("junkType", (byte) -1);
		baseline.put("junkDealerPrice", 0);
		return baseline;
	}
	
	public Baseline getBaseline3() {
		Baseline baseline = super.getBaseline3();
		baseline.put("faction", 0);
		baseline.put("factionStatus", 0);
		baseline.put("customization", new byte[] { });
		baseline.put("componentCustomizations", new SWGSet<Integer>(this, 3, 7, false));
		baseline.put("optionsBitmask", 0);
		baseline.put("uses", 0);
		baseline.put("conditionDamage", 0);
		baseline.put("maximumCondition", 100);
		baseline.put("staticObject", false);
		return baseline;
	}
	
	public Baseline getBaseline6() {
		Baseline baseline = super.getBaseline6();
		baseline.put("inCombat", false);
		baseline.put("defendersList", new SWGSet<Long>(this, 6, 3, false));
		baseline.put("4", 0);
		baseline.put("5", new SWGList<Long>(this, 6, 5, false));
		baseline.put("6", new SWGList<Integer>(this, 6, 6, false));
		baseline.put("7", (long) 0); // Unknown List (HUGE structure inside)
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
		return NGECore.getInstance().factionService.getName((int) getBaseline(3).get("faction"));
	}
	
	public void setFaction(String faction) {
		notifyClients(getBaseline(3).set("faction", CRC.StringtoCRC(faction)), true);
		updatePvpStatus();
	}
	
	public int getFactionStatus() {
		return (int) getBaseline(3).get("factionStatus");
	}
	
	public void setFactionStatus(int factionStatus) {
		notifyClients(getBaseline(3).set("factionStatus", factionStatus), true);
	}
	
	public byte[] getCustomization() {
		return (byte[]) getBaseline(3).get("customization");
	}
	
	public void setCustomization(byte[] customization) {
		notifyClients(getBaseline(3).set("customization", customization), true);
	}
	
	public SWGSet<Integer> getComponentCustomizations() {
		return (SWGSet<Integer>) getBaseline(3).get("componentCustomizations");
	}
	
	public void setComponentCustomizations(List<Integer> componentCustomizations) {
		getComponentCustomizations().clear();
		getComponentCustomizations().addAll(componentCustomizations);
	}
	
	public Byte getCustomizationVariable(String type) {
		if (customizationVariables.containsKey(type)) {
			return customizationVariables.get(type);
		} else {
			System.err.println("Error: object doesn't have customization variable " + type);
		}
		
		return null;
	}
	
	public void setCustomizationVariable(String type, byte value) {
		if (customizationVariables.containsKey(type)) {
			customizationVariables.replace(type, value);
		} else {
			customizationVariables.put(type, value);
		}
		
		setCustomization(getCustomizationBytes());
	}
	
	public void removeCustomizationVariable(String type) {
		if (customizationVariables.containsKey(type)) {
			customizationVariables.remove(type);
			setCustomization(getCustomizationBytes());
		}
	}
	
	private byte[] getCustomizationBytes() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		try {
			IDManagerVisitor visitor = ClientFileManager.loadFile("customization/customization_id_manager.iff", IDManagerVisitor.class);	
			
			stream.write((byte) 0x02); // Unk
			stream.write((byte) customizationVariables.size()); // Number of customization attributes
			
			for (String type : customizationVariables.keySet()) {
				stream.write(visitor.getAttributeIndex(type)); // Index of palette type within "customization/customization_id_manager.iff"
				stream.write(customizationVariables.get(type)); // Value/Index within palette
				
				// Seperator/Footer
				stream.write((byte) 0xC3);
				stream.write((byte) 0xBF);
				stream.write((byte) 0x03);
			}
			
			return stream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getOptionsBitmask() {
		return (int) getBaseline(3).get("optionsBitmask");
	}
	
	public void setOptionsBitmask(int optionsBitmask) {
		notifyClients(getBaseline(3).set("optionsBitmask", optionsBitmask), true);
	}
	
	public void setOptions(int options, boolean add) {
		if (options != 0) {
			if (add) {
				setOptionsBitmask(getOptionsBitmask() | options);
			} else {
				setOptionsBitmask(getOptionsBitmask() & ~options);;
			}
		}
	}
	
	public boolean getOption(int option) {
		return ((getOptionsBitmask() & option) == option);
	}
	
	public int getUses() {
		return (int) getBaseline(3).get("uses");
	}
	
	public void setUses(int uses) {
		notifyClients(getBaseline(3).set("uses", uses), true);
		setIntAttribute("count", uses);
	}
	
	public int getIncapTimer() {
		return getUses();
	}
	
	public void setIncapTimer(int incapTimer) {
		notifyClients(getBaseline(3).set("uses", incapTimer), true);
	}
	
	public int getConditionDamage() {
		return (int) getBaseline(3).get("conditionDamage");
	}
	
	public void setConditionDamage(int conditionDamage) {
		int maximumCondition = getMaximumCondition();
		
		if (conditionDamage < 0) {
			conditionDamage = 0;
		} else if (conditionDamage > maximumCondition) {
			conditionDamage = maximumCondition;
		}
		
		notifyClients(getBaseline(3).set("conditionDamage", conditionDamage), true);
		
		if (getMaximumCondition() > 0) {
			setStringAttribute("condition", (maximumCondition + "/" + (maximumCondition - conditionDamage)));
		}
	}
	
	public int getMaximumCondition() {
		return (int) getBaseline(3).get("maximumCondition");
	}
	
	public void setMaximumCondition(int maximumCondition) {
		notifyClients(getBaseline(3).set("maximumCondition", maximumCondition), true);
		setStringAttribute("condition", (maximumCondition + "/" + (maximumCondition - getConditionDamage())));
	}
	
	public boolean isStaticObject() {
		return (boolean) getBaseline(3).get("staticObject");
	}
	
	public void setStaticObject(boolean staticObject) {
		notifyClients(getBaseline(3).set("staticObject", staticObject), true);
	}
	
	public boolean isInCombat() {
		return (boolean) getBaseline(6).get("inCombat");
	}
	
	public void setInCombat(boolean inCombat) {
		notifyClients(getBaseline(6).set("inCombat", inCombat), true);
	}
	
	// All objects can be in combat (terminal mission flag bases, tutorial target box)
	
	public List<TangibleObject> getDefendersList() {
		synchronized(objectMutex) {
			return defendersList;
		}
	}
	
	public void addDefender(TangibleObject defender) {
		if (((CreatureObject)this).getOwnerId()>0){
			if (((CreatureObject)this).getOwnerId()==defender.getObjectID()){
				return; // fix for now until determined where the tamer is added from
			}
		}
		defendersList.add(defender);
	
		if (!isInCombat()) {
			setInCombat(true);
		}
	}
	
	public void removeDefender(TangibleObject defender) {
		defendersList.remove(defender);
		
		if (defendersList.isEmpty() && isInCombat()) {
			setInCombat(false);
		}
	}
	
	public int getPvpBitmask() {
		synchronized(objectMutex) {
			return pvpBitmask;
		}
	}
	
	public void setPvpBitmask(int pvpBitmask) {
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
	
	public void updatePvpStatus() {
		HashSet<Client> observers = new HashSet<Client>(getObservers());
		
		for (Iterator<Client> it = observers.iterator(); it.hasNext();) {
			Client observer = it.next();
			
			if (observer.getParent() != null) {
				observer.getSession().write(new UpdatePVPStatusMessage(this.getObjectID(), NGECore.getInstance().factionService.calculatePvpStatus((CreatureObject) observer.getParent(), this), getFaction()).serialize());
				if(getClient() != null)
					getClient().getSession().write(new UpdatePVPStatusMessage(observer.getParent().getObjectID(), NGECore.getInstance().factionService.calculatePvpStatus((CreatureObject) this, (CreatureObject) observer.getParent()), getFaction()).serialize());
			}

		}
		
		if (getClient() != null) {
			CreatureObject companion = NGECore.getInstance().mountService.getCompanion((CreatureObject) this);
			
			if (companion != null) {
				companion.updatePvpStatus();
			}
		}
		
	
		if (this instanceof CreatureObject){
			if (((CreatureObject)this).isPlayer()){
				// Here a specific CREO delta must be sent to update the faction info in character sheet and symbol in name
				// If anyone knows which that would be, please replace it here!
				sendBaselines(this.getClient());
				
			}
		}
	}
	
	public boolean isAttackableBy(CreatureObject attacker) {
		int pvpStatus = NGECore.getInstance().factionService.calculatePvpStatus(attacker, this);
		return (((pvpStatus & PvpStatus.Attackable) == PvpStatus.Attackable) || ((pvpStatus & PvpStatus.Aggressive) == PvpStatus.Aggressive));
	}
	
	public void showFlyText(OutOfBand outOfBand, float scale, RGB color, int displayType, boolean notifyObservers) {
		showFlyText("", outOfBand, scale, color, displayType, notifyObservers);
	}
	
	public void showFlyText(String stf, float scale, RGB color, int displayType, boolean notifyObservers) {
		showFlyText(stf, new OutOfBand(), scale, color, displayType, notifyObservers);
	}
	
	public void showFlyText(String stf, OutOfBand outOfBand, float scale, RGB color, int displayType, boolean notifyObservers) {
		if (outOfBand == null) {
			outOfBand = new OutOfBand();
		}
		
		if (color == null) {
			color = new RGB(255, 255, 255);
		}
		
		if (getClient() != null) {
			getClient().getSession().write((new ObjControllerMessage(0x0000000B, new ShowFlyText(getObjectID(), getObjectID(), stf, outOfBand, scale, color, displayType))).serialize());
		}
		
		if (notifyObservers) {
			Set<Client> observers = getObservers();
			
			for (Client client : observers) {
				client.getSession().write((new ObjControllerMessage(0x0000000B, new ShowFlyText(client.getParent().getObjectID(), getObjectID(), stf, outOfBand, scale, color, displayType))).serialize());
			}
		}
	}
	
	public void playEffectObject(String effectFile, String commandString) {
		notifyObservers(new PlayClientEffectObjectMessage(effectFile, getObjectID(), commandString), true);
	}
	
	public void stopEffectObject(String commandString) {
		notifyObservers(new StopClientEffectObjectByLabel(getObjectID(), commandString), true);
	}
	
	public String getSerialNumber() {
		return getStringAttribute("serial_number");
	}

	public void setSerialNumber(String serialNumber) {
		setStringAttribute("serial_number", serialNumber);
		setOptions(Options.SERIAL, true);
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
	
	public List<LootGroup> getLootGroups() {
		synchronized(objectMutex) {
			return lootGroups;
		}
	}
	
	public void setLootGroups(List<LootGroup> lootGroups) {
		synchronized(objectMutex) {
			this.lootGroups = lootGroups;
		}
	}

	public void addToLootGroups(String[] lootPoolNames, double[] lootPoolChances, double lootGroupChance) {
		synchronized(objectMutex) {
			//System.out.println("lootPoolNames[0] " + lootPoolNames[0]);
			LootGroup lootGroup = new LootGroup(lootPoolNames, lootPoolChances, lootGroupChance);
			lootGroups.add(lootGroup);
		}
	}
	
	public void addToLootGroups(LootGroup lootGroup) {
		synchronized(objectMutex) {
			lootGroups.add(lootGroup);
		}
	}
	
	public boolean isLooted() {
		synchronized(objectMutex) {
			return looted;
		}
	}
	
	public void setLooted(boolean looted) {
		synchronized(objectMutex) {
			this.looted = looted;
		}
	}
	
	public boolean isLootLock() {
		synchronized(objectMutex) {
			return lootLock;
		}
	}
	
	public void setLootLock(boolean lootLock) {
		synchronized(objectMutex) {
			this.lootLock = lootLock;
		}
	}
	
	public boolean isLootItem() {
		synchronized(objectMutex) {
			return lootItem;
		}
	}
	
	public void setLootItem(boolean lootItem) {
		synchronized(objectMutex) {
			this.lootItem = lootItem;
		}
	}
	
	public boolean isStackable() {
		return (boolean) otherVariables.get("stackable");
	}
	
	public void setStackable(boolean stackable) {
		otherVariables.set("stackable", stackable);
	}
	
	public boolean isNoSell() {
		return (boolean) otherVariables.get("noSell");
	}
	
	public void setNoSell(boolean noSell) {
		otherVariables.set("noSell", noSell);
	}
	
	public byte getJunkType() {
		return (byte) otherVariables.get("junkType");
	}
	
	public void setJunkType(byte junkType) {
		otherVariables.set("junkType", junkType);
	}
	
	public int getJunkDealerPrice() {
		return (int) otherVariables.get("junkDealerPrice");
	}
	
	public void setJunkDealerPrice(int junkDealerPrice) {
		otherVariables.set("junkDealerPrice", junkDealerPrice);
	}
	
	public boolean isCreditRelieved() {
		synchronized(objectMutex) {
			return creditRelieved;
		}
	}
	
	public void setCreditRelieved(boolean creditRelieved) {
		synchronized(objectMutex) {
			if (creditRelieved) {
				this.creditRelieved = creditRelieved; // only allow one state change to prevent hacking
			}
		}
	}
	
	public boolean isFull() {
		
		if (getTemplateData().getAttribute("containerVolumeLimit") == null)
			return false;
		
		int containerVolumeLimit = (int) getTemplateData().getAttribute("containerVolumeLimit");
		
		if (containerVolumeLimit == 0 || getTemplate() == "object/tangible/inventory/shared_appearance_inventory.iff") // appearance inventory - issue #755
			return false;

		
		if (NGECore.getInstance().objectService.objsInContainer(this, this) >= containerVolumeLimit)
			return true;
	
		return false;
	}
	
	public void notifyClients(IoBuffer buffer, boolean notifySelf) {
		notifyObservers(buffer, false);
	}
	
	public ObjectMessageBuilder getMessageBuilder() {
		synchronized(objectMutex) {
			if (messageBuilder == null) {
				messageBuilder = new TangibleMessageBuilder(this);
			}
			
			return messageBuilder;
		}
	}
	
	public void sendBaselines(Client destination) {
		if (destination != null && destination.getSession() != null) {
			destination.getSession().write(getBaseline(3).getBaseline());
			destination.getSession().write(getBaseline(6).getBaseline());
			
			Client parent = ((getGrandparent() == null) ? null : getGrandparent().getClient());
			
			if (parent != null && destination == parent) {
				destination.getSession().write(getBaseline(8).getBaseline());
				destination.getSession().write(getBaseline(9).getBaseline());
			}
			
			if (destination.getParent() != this) {
				UpdatePVPStatusMessage upvpm = new UpdatePVPStatusMessage(getObjectID());
				upvpm.setFaction(CRC.StringtoCRC(getFaction()));
				upvpm.setStatus(NGECore.getInstance().factionService.calculatePvpStatus((CreatureObject) destination.getParent(), this));
				destination.getSession().write(upvpm.serialize());
			}
		}
	}
	
	public void sendListDelta(byte viewType, short updateType, IoBuffer buffer) {
		switch (viewType) {
			case 3:
			{
				switch (updateType) {
					case 7:
					{
						buffer = getBaseline(3).createDelta(7, buffer.array());
						notifyClients(buffer, true);
						break;
					}
				}
			}
		}
	}
	
	@Deprecated public void setCustomName2(String customName) { setCustomName(customName); }
	
	@Deprecated public void sendDelta3(Client destination) { setOptions(Options.SERIAL | Options.INVULNERABLE, true); }
	
	@Deprecated public void sendAssemblyDelta3(Client destination) { setMaximumCondition(1000); }
	
	@Deprecated public void sendCustomizationDelta3(Client destination, String enteredName){ setCustomName(enteredName); }
	
	@Deprecated public void addOption(int option) { setOptionsBitmask(getOptionsBitmask() | option); }
	
	@Deprecated public void removeOption(int option) { setOptionsBitmask(getOptionsBitmask() & ~option); }
	
	@Deprecated public int getMaxDamage() { return getMaximumCondition(); }
	
	@Deprecated public void setMaxDamage(int maxDamage) { setMaximumCondition(maxDamage); }
	
	@Deprecated public byte getCombatFlag() { return (byte) ((isInCombat()) ? 1 : 0); } //Baseline.getBoolean(isInCombat()); }
	
	@Deprecated public void setCombatFlag(byte combatFlag) { setInCombat(combatFlag > 0); } //setInCombat(Baseline.getBoolean(combatFlag)); }
	
	@Deprecated public int getPvPBitmask() { return getPvpBitmask(); }
	
	@Deprecated public void setPvPBitmask(int pvpBitmask) { setPvpBitmask(pvpBitmask); }
	
	@Deprecated public int getStackCount() { if (getUses() == 0) setUses(1); return getUses(); }
	
	@Deprecated public void setStackCount(int stackCount) { setUses(stackCount); }
	
}
