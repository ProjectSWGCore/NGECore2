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
package resources.objects.building;

import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import main.NGECore;
import resources.objects.cell.CellObject;
import resources.objects.creature.CreatureObject;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;

import resources.objects.tangible.TangibleObject;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;

import engine.clients.Client;
import engine.resources.objects.IPersistent;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

@Entity(version=6)
public class BuildingObject extends TangibleObject implements IPersistent {
	
	@NotPersistent
	private BuildingMessageBuilder messageBuilder;
	@NotPersistent
	private Transaction txn;
	
	private float maintenanceAmount = 0;
	private int BMR = 0;
	private String deedTemplate="";
	private boolean residency=false;
	private byte privacy=(byte)0; 
	public static byte PRIVATE = (byte)0;
	public static byte PUBLIC = (byte)1;
	private short maximumStorageCapacity=0;
	private Vector<Long> entryList = new Vector<Long>(); // Preferably the OIDs should be stored, because of name changes
	private Vector<Long> banList = new Vector<Long>();

	public BuildingObject() {
		super();
		messageBuilder = new BuildingMessageBuilder(this);
		this.setConditionDamage(100);
	}

	public BuildingObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, Template, position, orientation);
		messageBuilder = new BuildingMessageBuilder(this);
		this.setConditionDamage(100);
	}
	
	public CellObject getCellByCellNumber(final int cellNumber) {
		
		final AtomicReference<CellObject> ref = new AtomicReference<CellObject>();
		
		synchronized(objectMutex) {
			this.viewChildren(this, true, false, (obj) -> {
				if(obj instanceof CellObject && ((CellObject) obj).getCellNumber() == cellNumber) 
					ref.set((CellObject) obj);
			});
		}
		
		return ref.get();
		
	}
	
	public float getMaintenanceAmount() {
		return maintenanceAmount;
	}

	public void setMaintenanceAmount(float maintenanceAmount) {
		this.maintenanceAmount = maintenanceAmount;
	}
	
	public void setDeedTemplate(String deedTemplate){
		this.deedTemplate = deedTemplate;
	}
	
	public String getDeedTemplate(){
		return this.deedTemplate;
	}
	
	public String getBuildingName() {
		return this.getCustomName();
	}

	public void setBuildingName(String buildingName, CreatureObject owner) {
		//owner.getClient().getSession().write(messageBuilder.buildCustomNameDelta(this,buildingName));  
		this.setCustomName(buildingName);
		((CreatureObject)owner).sendSystemMessage("Structure renamed.", (byte) 0);
	}
	
	public int getBMR() {
		return BMR;
	}

	public void setBMR(int BMR) {
		this.BMR = BMR;
	}
	
	public void setResidency(CreatureObject owner){
		owner.sendSystemMessage("@player_structure:declared_residency", (byte) 1);
		residency = true;
	}
	
	public boolean getResidency(){
		return this.residency;
	}
	
	public byte getPrivacy() {
		return privacy;
	}
	
	public String getPrivacyString() {
		if (privacy==PRIVATE)
			return "private";
		if (privacy==PUBLIC)
			return "public";
		return "42";
	}

	public void setPrivacy(byte privacy) {
		this.privacy = privacy;
	}
	
	public Vector<TangibleObject> getItemsList() {
		Vector<TangibleObject> items = new Vector<TangibleObject>();
		getCells().forEach(c -> c.viewChildren(c, true, false, (item) -> {
			if(!(item instanceof CreatureObject) && item.getTemplate() != "object/tangible/terminal/shared_terminal_player_structure.iff")
				items.add((TangibleObject) item);
		}));
		return items;
	}

	public short getMaximumStorageCapacity() {
		return maximumStorageCapacity;
	}

	public void setMaximumStorageCapacity(short maximumStorageCapacity) {
		this.maximumStorageCapacity = maximumStorageCapacity;
	}
	
	public void setPermissionEntry(String name,CreatureObject owner){
		Vector<String> entryListFirstNames = new Vector<String>();
		for (long oid : entryList){
			String firstName = NGECore.getInstance().characterService.getPlayerFirstName(oid);
			entryListFirstNames.add(firstName);
		}
		entryListFirstNames.add("Peter");
		entryListFirstNames.add("Jackson");
		owner.getClient().getSession().write(messageBuilder.buildPermissionListCreate(entryListFirstNames, name));      				
	}
	
	public void setPermissionBan(String name,CreatureObject owner){
		Vector<String> banListFirstNames = new Vector<String>();
		for (long oid : banList){
			String firstName = NGECore.getInstance().characterService.getPlayerFirstName(oid);
			banListFirstNames.add(firstName);
		}		
		banListFirstNames.add("Peter");
		banListFirstNames.add("Smith");
		owner.getClient().getSession().write(messageBuilder.buildPermissionListCreate(banListFirstNames, name));      				
	}
	
	public void addPlayerToEntryList(CreatureObject owner,long oid, String firstName){
		if (!entryList.contains(oid)){
			this.entryList.add(oid);	
			owner.sendSystemMessage(firstName+ " added to the list.", (byte)1); // player_added %NO added to the list.
		}
	}
	
	public void removePlayerFromEntryList(CreatureObject owner,long oid, String firstName){
		if (entryList.contains(oid)){
			this.entryList.remove(oid);
			owner.sendSystemMessage(firstName+ " removed to the list.", (byte)1); // player_removed	%NO removed from the list.
		}
	}
	
	public void addPlayerToBanList(CreatureObject owner,long oid, String firstName){
		if (!banList.contains(oid)){
			this.banList.add(oid);	
			owner.sendSystemMessage(firstName+ " added to the list.", (byte)1);
		}
	}
	
	public void removePlayerFromBanList(CreatureObject owner,long oid, String firstName){
		if (banList.contains(oid)){
			this.banList.remove(oid);
			owner.sendSystemMessage(firstName+ " removed to the list.", (byte)1);
		}
	}
	
	

	@Override
	public void sendBaselines(Client destination) {
		
		if(destination == null || destination.getSession() == null) {
			System.out.println("NULL session");
			return;
		}
		
		destination.getSession().write(messageBuilder.buildBaseline3());
		destination.getSession().write(messageBuilder.buildBaseline6());
		destination.getSession().write(messageBuilder.buildBaseline8());
		destination.getSession().write(messageBuilder.buildBaseline9());

	}

	public Transaction getTransaction() { return txn; }
	
	public void createTransaction(Environment env) { 
		txn = env.beginTransaction(null, null); 
		txn.setLockTimeout(500, TimeUnit.MILLISECONDS);
	}
	
	public Vector<CellObject> getCells() {
		final Vector<CellObject> cells = new Vector<CellObject>();
		this.viewChildren(this, true, false, (obj) -> {
			if(obj instanceof CellObject)
				cells.add((CellObject) obj);
		});
		return cells;
	}
	
}
