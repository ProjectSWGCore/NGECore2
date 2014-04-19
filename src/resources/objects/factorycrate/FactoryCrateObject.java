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
package resources.objects.factorycrate;

import java.util.Map;
import java.util.Vector;

import main.NGECore;

import protocol.swg.SceneCreateObjectByCrc;
import protocol.swg.SceneDestroyObject;
import protocol.swg.SceneEndBaselines;
import protocol.swg.UpdateContainmentMessage;
import protocol.swg.UpdatePVPStatusMessage;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;

/** 
 * @author Charon 
 */

@Persistent(version=0)
public class FactoryCrateObject extends TangibleObject {
	
	private Vector<TangibleObject> contents;	
	private byte capacity; 
	private byte contentObjectQuantity;
	private int contentCRC;
	private TangibleObject contentObjectType;
	
	@NotPersistent
	private FactoryCrateMessageBuilder messageBuilder;
	
	public FactoryCrateObject() { 
		
	}
	
	public FactoryCrateObject(long objectID, Planet planet, String template, Point3D position, Quaternion orientation) { 
		super(objectID, planet, template, position, orientation);
		this.messageBuilder = new FactoryCrateMessageBuilder(this);
		this.contents = new Vector<TangibleObject>();
		this.contentObjectQuantity = 0;
		this.capacity = 25;
		this.getAttributes().put("@obj_attr_n:condition", "100/100");
		this.getAttributes().put("@obj_attr_n:volume", "1");
		this.getAttributes().put("@obj_attr_n:quantity", "1");
		this.getAttributes().put("@obj_attr_n:factory_count", "1");
		this.getAttributes().put("@obj_attr_n:factory_attribs", "------------");
		this.getAttributes().put("@obj_attr_n:type", "@got_n:component"); 
		this.getAttributes().put("@obj_attr_n:serial_number", "123"); 
		
		
	}
	
	public boolean setContentType(TangibleObject contentObject) {
		synchronized(objectMutex) {
			this.contentObjectType = contentObject;
			if (contentObject.getTemplate().length()>0)
				this.contentCRC = CRC.StringtoCRC(contentObject.getTemplate());
			else
				this.contentCRC = 0;
			
			Map<String,String> contentAttributes = contentObject.getAttributes();
			for (Map.Entry<String, String> entry : contentAttributes.entrySet())
			{
				//if (!entry.getKey().equals(@obj_attr_n:condition))
				this.getAttributes().put(entry.getKey(), entry.getValue());
			}
			
			return true;
		}
	}
	
	public TangibleObject getContentType() {
		return this.contentObjectType;
	}
	
	public boolean setContentTypeAndQuantity(TangibleObject contentObject, int quantity) {
		synchronized(objectMutex) {
			this.contentObjectType = contentObject;
			if (contentObject.getTemplate().length()>0)
				this.contentCRC = CRC.StringtoCRC(contentObject.getTemplate());
			else{
				this.contentCRC = 0;
				return false; // Does it make sense to continue anyway?
			}
			
			Map<String,String> contentAttributes = contentObject.getAttributes();
			for (Map.Entry<String, String> entry : contentAttributes.entrySet())
			{
				//if (!entry.getKey().equals(@obj_attr_n:condition))
				this.getAttributes().put(entry.getKey(), entry.getValue());
				System.out.println(entry.getKey() + " " + entry.getValue());
			}
			if (quantity<capacity) {			
				contentObject.setSerialNumber(contentObject.getSerialNumber());			
				for (int i=0;i<quantity;i++){
					contents.add(contentObject);
				}
				contentObjectQuantity = (byte) quantity; 			
				return true;
			}		
			return false;
		}
	}
	
	public boolean addToCrate(TangibleObject contentObject) {
		synchronized(objectMutex) {
			if (contentObjectQuantity<capacity) {
				if (! contents.isEmpty()) {
					TangibleObject serialProvider = contents.firstElement();
					contentObject.setSerialNumber(serialProvider.getSerialNumber());
				}
				contents.add(contentObject);
				contentObjectQuantity ++; 
				
				return true;
			}
			return false;
		}
	}
	
	public String getContentFilename() {
		if (!contents.isEmpty()) {
			return contents.firstElement().getStfFilename();
		} else {
			System.err.println("Crate is empty");
		}
		return null;
	}
	
	public void getObjectOutOfCrate(CreatureObject player,NGECore core) {
		synchronized(objectMutex) {
			if (this.getQuantity()>0){
				TangibleObject contentType = this.getContentType();
				if (contentType==null)
					return; // Bad, crate had no content type set
				TangibleObject contentItem = (TangibleObject) core.objectService.createObject(contentType.getTemplate(), player.getPlanet());
				contentItem.setOptions(resources.datatables.Options.SERIAL, true); 		
				int crc = this.getContentCRC();//CRC.StringtoCRC("object/tangible/food/crafted/shared_drink_alcohol.iff");
				SceneCreateObjectByCrc createObjectMsg = new SceneCreateObjectByCrc(contentItem.getObjectID(), player.getOrientation().x, player.getOrientation().y, player.getOrientation().z, player.getOrientation().w, player.getPosition().x, player.getPosition().y, player.getPosition().z, crc, (byte) 0);
				player.getClient().getSession().write(createObjectMsg.serialize());      						
				contentItem.sendBaselines(player.getClient()); // TANO 3,6,8,9 Baselines		
				SceneEndBaselines sceneEndBaselinesMsg = new SceneEndBaselines(contentItem.getObjectID());
				player.getClient().getSession().write(sceneEndBaselinesMsg.serialize());
				
				long parentId = this.getParentId();
				SWGObject parentContainer = core.objectService.getObject(parentId);
				if (parentContainer==null)
					return; // crate has no parent, error
				UpdateContainmentMessage updateContainmentMessage= new UpdateContainmentMessage(contentItem.getObjectID(), parentContainer.getObjectID(), -1);
				player.getClient().getSession().write(updateContainmentMessage.serialize());
				
				this.sendSetQuantity(player.getClient(),this.getQuantity()-1);
				if (this.getQuantity()==0){ // Crate is empty now, delete it
					parentContainer._remove(this);
					SceneDestroyObject destroyObjectMsg = new SceneDestroyObject(this.getObjectID());
					player.getClient().getSession().write(destroyObjectMsg.serialize());   
					core.objectService.destroyObject(this);			
				}
			} else { // should give players the chance to clean faulty, empty crates from their containers
				long parentId = this.getParentId();
				SWGObject parentContainer = core.objectService.getObject(parentId);
				if (parentContainer==null)
					return; // crate has no parent, error
				parentContainer._remove(this);
				SceneDestroyObject destroyObjectMsg = new SceneDestroyObject(this.getObjectID());
				player.getClient().getSession().write(destroyObjectMsg.serialize());   
				core.objectService.destroyObject(this);		
			}
		}
	}
	
	public byte getQuantity() {
		return contentObjectQuantity;
	}
	
	public String getSerialNumber() {
		if (!contents.isEmpty()) {
			return contents.firstElement().getSerialNumber();
		}
		return "";
	}
	
	public int getContentCRC() {
		return contentCRC;
	}

	public void setContentCRC(int contentCRC) {
		synchronized(objectMutex) {
			this.contentCRC = contentCRC;
		}
	}
	
	public void handleSplit(){
		
	}
	
	public void sendAddItem(Client destination) {
		destination.getSession().write(messageBuilder.buildDelta3());
		this.getAttributes().put("@obj_attr_n:factory_count", ""+this.getQuantity());
		this.getAttributes().put("@obj_attr_n:quantity", ""+this.getQuantity());
		//services.CharonPacketUtils.printAnalysis(messageBuilder.buildDelta3(),"FCYT3 Delta");
	}
	
	public void sendSetQuantity(Client destination,int quantity) {
		contentObjectQuantity = (byte) quantity;
		destination.getSession().write(messageBuilder.buildDelta3());
		this.getAttributes().put("@obj_attr_n:factory_count", ""+quantity);	
		this.getAttributes().put("@obj_attr_n:quantity", ""+this.getQuantity());
		//services.CharonPacketUtils.printAnalysis(messageBuilder.buildDelta3(),"FCYT3 Delta");
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
		
//		services.CharonPacketUtils.printAnalysis(messageBuilder.buildBaseline3(),"FCYT3 Baseline");
//		services.CharonPacketUtils.printAnalysis(messageBuilder.buildBaseline6(),"FCYT6 Baseline");
//		services.CharonPacketUtils.printAnalysis(messageBuilder.buildBaseline8(),"FCYT8 Baseline");
//		services.CharonPacketUtils.printAnalysis(messageBuilder.buildBaseline9(),"FCYT9 Baseline");
	}	
	
	public void sendDelta3(Client destination) {
		destination.getSession().write(messageBuilder.buildDelta3());
		this.getAttributes().put("@obj_attr_n:factory_count", ""+this.getQuantity());
		//services.CharonPacketUtils.printAnalysis(messageBuilder.buildDelta3(),"FCYT3 Delta");
	}
}