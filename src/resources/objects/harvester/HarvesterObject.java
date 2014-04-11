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
package resources.objects.harvester;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import main.NGECore;
import protocol.swg.SceneCreateObjectByCrc;
import protocol.swg.SceneDestroyObject;
import protocol.swg.SceneEndBaselines;
import protocol.swg.UpdatePVPStatusMessage;

import com.sleepycat.persist.model.Persistent;

import services.chat.WaypointAttachment;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.objects.creature.CreatureObject;
import resources.objects.installation.InstallationMessageBuilder;
import resources.objects.installation.InstallationObject;
import resources.objects.player.PlayerObject;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.waypoint.WaypointObject;
import services.chat.Mail;


/** 
 * @author Charon 
 */

@Persistent(version=0)
public class HarvesterObject extends InstallationObject {
	
	private HarvesterMessageBuilder messageBuilder;
	private InstallationMessageBuilder installationMessageBuilder;
	
	public final static byte HARVESTER_TYPE_MINERAL  = 0;
	public final static byte HARVESTER_TYPE_CHEMICAL = 1;
	public final static byte HARVESTER_TYPE_FLORA    = 2;
	public final static byte HARVESTER_TYPE_GAS      = 3;
	public final static byte HARVESTER_TYPE_WATER    = 4;
	public final static byte HARVESTER_TYPE_SOLAR    = 5;
	public final static byte HARVESTER_TYPE_WIND     = 6;	
	public final static byte HARVESTER_TYPE_FUSION   = 7;
	public final static byte HARVESTER_TYPE_GEO      = 8;
	
	public String[] mineral_type_iff  = new String[]{"object/installation/mining_ore/shared_mining_ore_harvester_style_1.iff",
			                                  "object/installation/mining_ore/shared_mining_ore_harvester_style_2.iff",
                                              "object/installation/mining_ore/shared_mining_ore_harvester_heavy.iff",
                                              "object/installation/mining_ore/shared_mining_ore_harvester_elite.iff"};
	
	public String[] chemical_type_iff = new String[]{"object/installation/mining_liquid/shared_mining_liquid_harvester_style_1.iff",
		                                      "object/installation/mining_liquid/shared_mining_liquid_harvester_style_2.iff",
		                                      "object/installation/mining_liquid/shared_mining_liquid_harvester_style_3.iff",
		                                      "object/installation/mining_liquid/shared_mining_liquid_harvester_style_4.iff"};
	
	public String[] flora_type_iff    = new String[]{"object/installation/mining_organic/shared_mining_organic_flora_farm.iff",
                                              "object/installation/mining_organic/shared_mining_organic_flora_farm_medium.iff",
                                              "object/installation/mining_organic/shared_mining_organic_flora_farm_heavy.iff",
                                              "object/installation/mining_organic/shared_mining_organic_flora_farm_elite.iff"};
	
	public String[] water_type_iff    = new String[]{"object/installation/mining_liquid/shared_mining_liquid_moisture_harvester.iff",
  		                                      "object/installation/mining_liquid/shared_mining_liquid_moisture_harvester_medium.iff",
                                              "object/installation/mining_liquid/shared_mining_liquid_moisture_harvester_heavy.iff",
                                              "object/installation/mining_liquid/shared_mining_liquid_moisture_harvester_elite.iff"};
	
	public String[] gas_type_iff      = new String[]{"object/installation/mining_gas/shared_mining_gas_harvester_style_1.iff",
		                                      "object/installation/mining_gas/shared_mining_gas_harvester_style_2.iff",
		                                      "object/installation/mining_gas/shared_mining_gas_harvester_style_3.iff",
		                                      "object/installation/mining_gas/shared_mining_gas_harvester_style_4.iff"};
	
	public String[] wind_type_iff     = new String[]{"object/installation/generators/shared_power_generator_wind_style_1.iff"};
	
	public String[] solar_type_iff    = new String[]{"object/installation/generators/shared_power_generator_solar_style_1.iff"};
	
	public String[] fusion_type_iff   = new String[]{"object/installation/generators/shared_power_generator_fusion_style_1.iff"};
	
	public String[] geo_type_iff   = new String[]{"object/installation/generators/shared_power_generator_geothermal_style_1.iff"};

	//for nothing  "object/installation/generators/shared_power_generator_photo_bio_style_1.iff"
	
	
	public byte harvester_type = 0;
	public byte harvester_size = 0;
	private Vector<ResourceContainerObject> outputHopperContent = new Vector<ResourceContainerObject>();
	private int outputHopperCapacity = 0; 
	private int BER = 0; 
	private float maintenanceAmount = 0;
	private float powerLevel = 0;
	private int powerCost = 0;
	private int maintenanceCost = 0;
	private byte structuralCondition = 100;
	private boolean activated = false;
	private boolean generator = false;
	private GalacticResource selectedHarvestResource;
	private float selectedResourceConcentration;
	private byte updateCount;
	private byte resourceUpdateCount;
	private Vector<String> adminList = new Vector<String>();
	private Vector<String> hopperList = new Vector<String>();
	private SWGObject owner;
	private float currentHarvestedCountFloat=0.0F;
	private String deedTemplate;
	
	private int specRate;

	
	public HarvesterObject(long objectID, Planet planet, String template, Point3D position, Quaternion orientation){
		super(objectID, planet, template, position, orientation);
		this.setConditionDamage(100);
		messageBuilder = new HarvesterMessageBuilder(this);
		installationMessageBuilder = new InstallationMessageBuilder((InstallationObject)this);
	}
	
	public int getBER() {
		return BER;
	}

	
	public void setBER(int baseExtractionRate) {
		this.BER = baseExtractionRate;
	}

	
	public float getActualExtractionRate() {
		//float resourceDraw = (getBER() * getSelectedResourceConcentration()) / 400; // /100
		float resourceDraw = 1.5F*(getBER() * getSelectedResourceConcentration()) / 100;
		// BER * concentration * publish 27 bonus * profession buffs = AER
		// 14* 0.83 * 1.5 * 1.05 = 18.3015 AER
		return resourceDraw;
	}   
	
	public int getSpecRate() {
		return specRate;
	}

	public void setSpecRate(int specRate) {
		this.specRate = specRate;
	}

	
	public GalacticResource getSelectedHarvestResource() {
		return selectedHarvestResource;
	}
	
	
	public void setSelectedHarvestResource(GalacticResource selectedHarvestResource,CreatureObject owner) {
		this.selectedHarvestResource = selectedHarvestResource;
		setSelectedResourceConcentration(selectedHarvestResource.deliverConcentrationForSurvey(this.getPlanetId(), this.getPosition().x, this.getPosition().z));
		
		owner.getClient().getSession().write(installationMessageBuilder.constructINSO7Var1((InstallationObject) this,selectedHarvestResource.getObjectId()));  
		owner.getClient().getSession().write(installationMessageBuilder.constructINSO7Var2((InstallationObject) this,selectedHarvestResource.getObjectId()));      					
	}

	
	public float getSelectedResourceConcentration() {
	    return selectedResourceConcentration;
	}
	
	
	private void setSelectedResourceConcentration(float selectedResourceConcentration) {
		this.selectedResourceConcentration = selectedResourceConcentration;
	}
	
	
	public void activateHarvester(CreatureObject owner){
		activated = true;
		this.setOwner(owner);
		owner.getClient().getSession().write(messageBuilder.buildHINO3Delta(this,(byte)1));  
		owner.getClient().getSession().write(messageBuilder.buildHINO7ActivateDelta2(this));
    }
	
	
	public void deactivateHarvester(CreatureObject owner){
		activated = false;
		owner.getClient().getSession().write(messageBuilder.buildHINO3Delta2(this,(byte)0));
		owner.getClient().getSession().write(messageBuilder.buildHINO7DeactivateDelta(this));
    }
	
	
	public boolean isActivated(){
		return this.activated;
    }


	public byte getHarvester_type() {
		return harvester_type;
	}


	public void setHarvester_type(byte harvester_type) {
		this.harvester_type = harvester_type;
	}


	public Vector<ResourceContainerObject> getOutputHopperContent() {
		return outputHopperContent;
	}


	public void setOutputHopperContent(Vector<ResourceContainerObject> outputHopperContent) {
		this.outputHopperContent = outputHopperContent;
	}


	public byte getStructuralCondition() {
		return structuralCondition;
	}


	public void setStructuralCondition(byte structuralCondition) {
		this.structuralCondition = structuralCondition;
	}


	public int getOutputHopperCapacity() {
		return outputHopperCapacity;
	}


	public void setOutputHopperCapacity(int outputHopperCapacity) {
		this.outputHopperCapacity = outputHopperCapacity;
	}


	public byte getUpdateCount() {
		if (updateCount>254) updateCount = 0;
		return updateCount++;
	}
	
	public byte getResourceUpdateCount() {
		if (resourceUpdateCount>254) resourceUpdateCount = 0;
		return resourceUpdateCount++;
	}


	public void setUpdateCount(byte updateCount) {
		this.updateCount = updateCount;
	}


	public Vector<String> getAdminList() {
		return adminList;
	}


	public void setAdminList(Vector<String> adminList) {
		this.adminList = adminList;
	}


	public Vector<String> getHopperList() {
		return hopperList;
	}


	public void setHopperList(Vector<String> hopperList) {
		this.hopperList = hopperList;
	}


	public float getPowerLevel() {
		return powerLevel;
	}


	public void setPowerLevel(float energyLevel) {
		this.powerLevel = energyLevel;
	}


	public float getMaintenanceAmount() {
		return maintenanceAmount;
	}


	public void setMaintenanceAmount(float maintenanceAmount) {
		this.maintenanceAmount = maintenanceAmount;
	}
	
	public int getPowerCost() {
		return powerCost;
	}


	public void setPowerCost(int powerCost) {
		this.powerCost = powerCost;
	}

	
	public int getMaintenanceCost() {
		return maintenanceCost;
	}

	
	public void setMaintenanceCost(int maintenanceCost) {
		this.maintenanceCost = maintenanceCost;
	}	
	
	
	public float getCurrentHarvestedCountFloat() {
		return currentHarvestedCountFloat;
	}


	public void setCurrentHarvestedCountFloat(float currentHarvestedCountFloat) {
		this.currentHarvestedCountFloat = currentHarvestedCountFloat;
	}


	public SWGObject getOwner() {
		return owner;
	}


	public void setOwner(SWGObject owner) {
		this.owner = owner;
	}
		
	public void setDeedTemplate(String deedTemplate){
		this.deedTemplate = deedTemplate;
	}
	
	public String getDeedTemplate(){
		return this.deedTemplate;
	}
	

	public boolean isGenerator() {
		return generator;
	}

	public void setGenerator(boolean generator) {
		this.generator = generator;
	}
	
	
	public void setHarvesterName(String name,CreatureObject owner){
		owner.getClient().getSession().write(messageBuilder.buildCustomNameDelta(this,name));  
		this.setCustomName(name);
		((CreatureObject)owner).sendSystemMessage("Structure renamed.", (byte) 0);
	}
	
	public void setPermissionAdmin(String name,CreatureObject owner){
		Vector<String> permissionList = this.getAdminList();		
		owner.getClient().getSession().write(messageBuilder.buildPermissionListCreate(this, permissionList, name));      				
	}
	
	public void setPermissionHopper(String name,CreatureObject owner){
		Vector<String> permissionList = this.getAdminList();		   	
		owner.getClient().getSession().write(messageBuilder.buildPermissionListCreate(this, permissionList, name));      				
	}
	
	public void operateMachinery(CreatureObject owner){
		owner.getClient().getSession().write(messageBuilder.buildResourceHarvesterActivatePageMessage(this));
		// Assemble resources at that spot	
		// For later, it might be needed to also pass the template to differentiate liquid resources
		Vector<GalacticResource> planetResourcesVector = NGECore.getInstance().resourceService.getSpawnedResourcesByPlanetAndHarvesterType(this.getPlanetId(),this.getHarvester_type());		
		owner.getClient().getSession().write(messageBuilder.buildHarvesterGetResourceData(this, owner, planetResourcesVector)); 
		owner.getClient().getSession().write(messageBuilder.buildHINOBaseline7(this, planetResourcesVector));      				
	}
	
	public void deActivate(){
		owner.getClient().getSession().write(messageBuilder.buildResourceHarvesterActivatePageMessage(this));
	}
	
	public void placeHarvester(){
		owner.getClient().getSession().write(messageBuilder.buildResourceHarvesterActivatePageMessage(this));
	}
	
	public static void createAndPlaceHarvester(SWGObject object){
		CreatureObject actor = (CreatureObject) object.getAttachment("Owner");

		String structureTemplate = (String)object.getAttachment("Deed_StructureTemplate");
		HarvesterObject harvester = (HarvesterObject) NGECore.getInstance().objectService.createObject(structureTemplate, actor.getPlanet());
		long objectId = harvester.getObjectID();

		Vector<String> adminList = harvester.getAdminList();
		String[] fullName = ((CreatureObject)actor).getCustomName().split(" ");
		adminList.add(fullName[0]);
		harvester.setAdminList(adminList);
		// Set BER and outputhopper capacity here, take it from deed
		harvester.setBER((int)object.getAttachment("Deed_BER"));
		harvester.setSpecRate((int)(1.5F*(int)object.getAttachment("Deed_BER")));
		harvester.setOutputHopperCapacity((int)object.getAttachment("Deed_Capacity"));
		harvester.setDeedTemplate((String)object.getAttachment("Deed_DeedTemplate"));	
		if ((int)object.getAttachment("Deed_SurplusMaintenance")>0){
			harvester.setMaintenanceAmount((int)object.getAttachment("Deed_SurplusMaintenance"));
		}		
		if ((int)object.getAttachment("Deed_SurplusPower")>0){
			harvester.setPowerLevel((int)object.getAttachment("Deed_SurplusPower"));
		}
		
		
		// build harvester
		int resCRC = CRC.StringtoCRC(structureTemplate);
		float positionY = NGECore.getInstance().terrainService.getHeight(actor.getPlanetId(), object.getPosition().x, object.getPosition().z);
		SceneCreateObjectByCrc createObjectMsg = new SceneCreateObjectByCrc(objectId, object.getOrientation().x, object.getOrientation().y, object.getOrientation().z, object.getOrientation().w, object.getPosition().x, positionY, object.getPosition().z, resCRC, (byte) 0);
		actor.getClient().getSession().write(createObjectMsg.serialize());      				
		tools.CharonPacketUtils.printAnalysis(createObjectMsg.serialize());
		
		harvester.setPosition(new Point3D(object.getPosition().x,positionY, object.getPosition().z));
		
		resources.objects.installation.InstallationMessageBuilder messenger = new resources.objects.installation.InstallationMessageBuilder((InstallationObject)harvester);
		actor.getClient().getSession().write(messenger.buildBaseline3((InstallationObject)harvester));   
	 	SceneEndBaselines sceneEndBaselinesMsg = new SceneEndBaselines(harvester.getObjectID());
	 	actor.getClient().getSession().write(sceneEndBaselinesMsg.serialize());
		tools.CharonPacketUtils.printAnalysis(sceneEndBaselinesMsg.serialize());
		
		PlayerObject player = (PlayerObject) actor.getSlottedObject("ghost");
		WaypointObject constructionWaypoint = (WaypointObject)NGECore.getInstance().objectService.createObject("object/waypoint/shared_world_waypoint_blue.iff", actor.getPlanet(), harvester.getPosition().x, 0 ,harvester.getPosition().z);
		String displayname = "@installation_n:"+harvester.getStfName();
		constructionWaypoint.setName(displayname);
		constructionWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(actor.getPlanet().getName()));
		constructionWaypoint.setPosition(new Point3D(object.getPosition().x,0, object.getPosition().z));	
		player.waypointAdd(constructionWaypoint);
		constructionWaypoint.setPosition(new Point3D(object.getPosition().x,0, object.getPosition().z));
		constructionWaypoint.setActive(true);
		constructionWaypoint.setColor((byte)1);
		constructionWaypoint.setStringAttribute("", "");
		player.waypointAdd(constructionWaypoint);
		constructionWaypoint.setName(displayname);
		constructionWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(actor.getPlanet().getName()));							
		player.setLastSurveyWaypoint(constructionWaypoint);
		
		List<WaypointAttachment> attachments = new ArrayList<WaypointAttachment>(); 
		WaypointAttachment attachment = new WaypointAttachment();
		attachment.active = true;
		attachment.cellID = constructionWaypoint.getCellId();
		attachment.color = (byte)1;
		attachment.name = displayname;
		attachment.planetCRC = engine.resources.common.CRC.StringtoCRC(actor.getPlanet().getName());
		attachment.positionX = object.getPosition().x;
		attachment.positionY = 0;
		attachment.positionZ = object.getPosition().z;
		attachments.add(attachment);
		
		// remove constructor
		NGECore.getInstance().objectService.destroyObject(object);
		SceneDestroyObject destroyObjectMsg = new SceneDestroyObject(object.getObjectID());
		actor.getClient().getSession().write(destroyObjectMsg.serialize()); 
		
		// ToDo: waypoint attachment fix
		Date date = new Date();
		Mail constructionNotificationMail = new Mail();
		constructionNotificationMail.setSenderName("Structure Service");
		constructionNotificationMail.setSubject("Your structure");
		constructionNotificationMail.setRecieverId(actor.getObjectID());
		constructionNotificationMail.setTimeStamp((int) (date.getTime() / 1000));
		constructionNotificationMail.setMailId(NGECore.getInstance().chatService.generateMailId());
		String message = "Your construction is ready";
		constructionNotificationMail.setMessage(message);
		constructionNotificationMail.setStatus(Mail.NEW);
		constructionNotificationMail.setAttachments(attachments);
		//NGECore.getInstance().chatService.sendPersistentMessage(actor.getClient(), constructionNotificationMail);
		NGECore.getInstance().chatService.storePersistentMessage(constructionNotificationMail);
		NGECore.getInstance().chatService.sendPersistentMessageHeader(actor.getClient(), constructionNotificationMail);
		
	}
	
	public void createNewHopperContainer(){
		Vector<GalacticResource> planetResourcesVector = NGECore.getInstance().resourceService.getSpawnedResourcesByPlanetAndHarvesterType(this.getPlanetId(),this.getHarvester_type());	
    	((CreatureObject)this.getOwner()).getClient().getSession().write(messageBuilder.buildHINOBaseline7(this, planetResourcesVector));      					
		((CreatureObject)this.getOwner()).getClient().getSession().write(messageBuilder.buildHINO7Delta(this,(byte)1)); 
	}
	
	public void continueHopperContainer(){
    	Vector<GalacticResource> planetResourcesVector = NGECore.getInstance().resourceService.getSpawnedResourcesByPlanetAndHarvesterType(this.getPlanetId(),this.getHarvester_type());		
    	((CreatureObject)this.getOwner()).getClient().getSession().write(messageBuilder.buildHarvesterGetResourceData(this, owner, planetResourcesVector)); 
    	((CreatureObject)this.getOwner()).getClient().getSession().write(messageBuilder.buildHINOBaseline7(this, planetResourcesVector));
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
