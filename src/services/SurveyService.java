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
package services;

import java.nio.ByteOrder;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.mina.core.buffer.IoBuffer;
import protocol.swg.PlayClientEffectLocMessage;
import protocol.swg.ResourceMessenger;
import protocol.swg.SceneCreateObjectByCrc;
import protocol.swg.SceneEndBaselines;
import protocol.swg.SurveyMapUpdateMessage;
import protocol.swg.UpdateContainmentMessage;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceConcentration;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.resource.ResourceRoot;
import resources.objects.tangible.TangibleObject;
import resources.objects.tool.SurveyTool;
import resources.objects.waypoint.WaypointObject;
import services.command.SurveyCommand;
import services.sui.SUIWindow;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import main.NGECore;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

/** 
 * @author Charon 
 */
public class SurveyService implements INetworkDispatch {
	
	private NGECore core;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public Vector<SurveyTool> activeSurveyTools = new Vector<SurveyTool>();

	public SurveyService(NGECore core) {
		this.core = core;
		scheduleSurveyService();
		core.commandService.registerSurveyCommand("RequestSurvey");
		core.commandService.registerSurveyCommand("RequestCoreSample");
	}
		
	public GalacticResource grabResourceByName(String searchName){
		GalacticResource resource = null;
		Vector<GalacticResource> allResources = core.resourceService.getAllSpawnedResources();
		for (GalacticResource res : allResources){
			if (res.getName().equals(searchName)){
				resource = res;
			}
		}
		return resource;
	}
	
	public Vector<ResourceConcentration> buildConcentrationsCollection(Point3D measurePos, GalacticResource resource, float radius,float differential, int planetId){
		float leftXCoordinate = measurePos.x - (0.5f*radius);
		float topZCoordinate  = measurePos.z - (0.5f*radius);
//		System.out.println("measurePos.x " + measurePos.x);
//		System.out.println("measurePos.z " + measurePos.z);
		float cursorX = leftXCoordinate;
		float cursorZ = topZCoordinate;
		int divisions=5;
		if (radius<=192.0f){
			divisions = 4;
		}
		if (radius<=64.0f){
			divisions = 3;
		}

		Vector<ResourceConcentration> concentrationsCollection = new Vector<ResourceConcentration>();
		for (int z=0;z<divisions;z++) {
			for (int x=0;x<divisions;x++) {
				ResourceConcentration concentration = new ResourceConcentration(cursorX,cursorZ,(resource.deliverConcentrationForSurvey(planetId, cursorX, cursorZ)));
				concentrationsCollection.add(concentration);
				cursorZ+=differential;
			}
			cursorZ=topZCoordinate;
			cursorX+=differential;
		}		
		return concentrationsCollection;	
	}
	
	public void constructSurveyMapMessage(CreatureObject crafter, Vector<ResourceConcentration> concentrationMap, float radius){
	
		int pointsAmount = 25;
		if (radius<=64.0f) {
			pointsAmount=9;
		} else if (radius<=128.0) {
			pointsAmount = 16;
		} else if (radius<=192.0f) {
			pointsAmount = 16;
		} else if (radius<=256.0f) {
			pointsAmount = 25;
		} else if (radius>=256.0f) {
			pointsAmount = 25;
		}
			
		SurveyMapUpdateMessage smuMsg = new SurveyMapUpdateMessage(concentrationMap, pointsAmount);
		crafter.getClient().getSession().write(smuMsg.serialize());	
		services.resources.CharonPacketUtils.printAnalysis(smuMsg.serialize());
		
		PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
		if (smuMsg.getHighestConcentration() > 0.10f){
			WaypointObject lastSurveyWaypoint = player.getLastSurveyWaypoint();
			WaypointObject surveyWaypoint = null;
			if (lastSurveyWaypoint==null){
				surveyWaypoint = (WaypointObject)core.objectService.createObject("object/waypoint/shared_world_waypoint_blue.iff", crafter.getPlanet(), smuMsg.getHighestX(), 0 ,smuMsg.getHighestZ());
				surveyWaypoint.setName("Survey location");
				surveyWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(crafter.getPlanet().getName()));
				surveyWaypoint.setPosition(new Point3D(smuMsg.getHighestX(),0, smuMsg.getHighestZ()));	
				player.waypointAdd(surveyWaypoint);
				surveyWaypoint.setPosition(new Point3D(smuMsg.getHighestX(),0, smuMsg.getHighestZ()));
				surveyWaypoint.setActive(true);
				surveyWaypoint.setColor((byte)1);
				surveyWaypoint.setStringAttribute("", "");
				player.waypointAdd(surveyWaypoint);
				surveyWaypoint.setName("Survey location");
				surveyWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(crafter.getPlanet().getName()));							
				player.setLastSurveyWaypoint(surveyWaypoint);
			} else {
				surveyWaypoint = lastSurveyWaypoint;
				surveyWaypoint.setPosition(new Point3D(smuMsg.getHighestX(),0, smuMsg.getHighestZ()));	
				player.waypointUpdate(surveyWaypoint);	
			}		
			crafter.sendSystemMessage("@survey:survey_waypoint", (byte) 0);
		}
	}
	
	public void scheduleSurveyService(){
		
		final ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				ServiceProcessing();	
			}
			
		}, 10, 1000, TimeUnit.MILLISECONDS);

	}
	
	public void ServiceProcessing(){
		SurveyTool removeTool=null;
		for (SurveyTool surveyTool : activeSurveyTools){
			if (surveyTool.getCurrentlySurveying()){
				// Check if survey process has finished
				if (System.currentTimeMillis()>surveyTool.getLastSurveyTime()+3000){					
					removeTool = surveyTool;
					surveyTool.setCurrentlySurveying(false);
				}
			}
			if (surveyTool.getCurrentlySampling()){
				// Check if sampling process has finished
				if ((System.currentTimeMillis()>surveyTool.getLastSampleTime()+3000) && !surveyTool.getCurrentlyCoolingDown()){					
					surveyTool.setCurrentlyCoolingDown(true);
					// Update inventory
					handleSamplingStages(surveyTool);
					
				}
				// Check if sampling recovery is over
				long sampleRecoveryTime = surveyTool.getRecoveryTime();
				if (System.currentTimeMillis()>surveyTool.getLastSampleTime()+sampleRecoveryTime && ! surveyTool.isExceptionalState()){
					// kick off another sampling attempt
					surveyTool.setCurrentlyCoolingDown(false);
					continueSampling(surveyTool);
				}
				if (surveyTool.getUser().getPosture()!=1){
					surveyTool.setExceptionalState(false);
					surveyTool.setCurrentlySampling(false);
					removeTool = surveyTool;
					if (surveyTool.getUser().getPosture()==0)
						surveyTool.getUser().sendSystemMessage("You stand up", (byte) 0);
					
					surveyTool.getUser().sendSystemMessage("@survey:sample_cancel", (byte) 0);
				}
			}
		}
		if (removeTool!=null)
			activeSurveyTools.remove(removeTool); // remove after notification
		
	}
	
	public void handleSamplingStages(SurveyTool surveyTool){
		CreatureObject crafter = surveyTool.getUser();
					
		PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
		GalacticResource sampleResource = surveyTool.getSurveyResource();
		int stackCount = 0;
		ResourceContainerObject container = player.getRecentContainer();
		if (! surveyTool.isRecoveryMode()) 
			stackCount = core.resourceService.getResourceSampleQuantity(crafter, sampleResource); 
		else { // handle particularly rich case
			stackCount = core.resourceService.getResourceSampleQuantity(crafter, sampleResource); 
			int recoveryChance = new Random().nextInt(100);	
			if (recoveryChance<70){
				stackCount *= 3;
			}
		}
					
		if (container!=null) {
			if (container.getReferenceID()==sampleResource.getId()) {
				int stackCountToUpdate = container.getStackCount();
				if (stackCountToUpdate<100000-stackCount){ // sample fits into container
					SWGObject crafterInventory = crafter.getSlottedObject("inventory");   
					container.setStackCount(stackCountToUpdate+stackCount);
					crafter.sendSystemMessage("@base_player:prose_grant_xp @exp_n:resource_harvesting_inorganic", (byte) 0);
					core.playerService.giveExperience(crafter,(int)(2.5F*stackCount)); // resource_harvesting_inorganic						
					//exp_n:resource_harvesting_inorganic
					
					container.sendDelta3(crafter.getClient());
					container.buildAttributeListMessage(crafter.getClient());
				} else { // new container
					
					String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];           		  				
					ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, crafter.getPlanet());
					containerObject.initializeStats(sampleResource);
					containerObject.setProprietor(crafter);
					
					int resCRC = CRC.StringtoCRC(resourceContainerIFF);
					containerObject.setIffFileName(resourceContainerIFF);
					
					long objectId = containerObject.getObjectID();										
					SWGObject crafterInventory = crafter.getSlottedObject("inventory");
					containerObject.setParent(crafterInventory);

					SceneCreateObjectByCrc createObjectMsg = new SceneCreateObjectByCrc(objectId, crafter.getOrientation().x, crafter.getOrientation().y, crafter.getOrientation().z, crafter.getOrientation().w, crafter.getPosition().x, crafter.getPosition().y, crafter.getPosition().z, resCRC, (byte) 0);
					crafter.getClient().getSession().write(createObjectMsg.serialize());      				
					services.resources.CharonPacketUtils.printAnalysis(createObjectMsg.serialize());
				    				 	
					UpdateContainmentMessage updateContainmentMessage= new UpdateContainmentMessage(containerObject.getObjectID(), crafterInventory.getObjectID(), -1);
					crafter.getClient().getSession().write(updateContainmentMessage.serialize());
					containerObject.sendBaselines(crafter.getClient());
				    
					SceneEndBaselines sceneEndBaselinesMsg = new SceneEndBaselines(containerObject.getObjectID());
					crafter.getClient().getSession().write(sceneEndBaselinesMsg.serialize());
					services.resources.CharonPacketUtils.printAnalysis(sceneEndBaselinesMsg.serialize());

					containerObject.buildAttributeListMessage(crafter.getClient());
					player.setRecentContainer(containerObject);
					
//					base_player
//					prose_grant_xp
//					exp_n
//					resource_harvesting_inorganic			
				}
			} else { // Mismatch -> new container
				String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];           		  				

				ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, crafter.getPlanet());
				containerObject.initializeStats(sampleResource);
				containerObject.setProprietor(crafter);
				
				int resCRC = CRC.StringtoCRC(resourceContainerIFF);
				containerObject.setIffFileName(resourceContainerIFF);
				
				long objectId = containerObject.getObjectID();										
				SWGObject crafterInventory = crafter.getSlottedObject("inventory");
				containerObject.setParent(crafterInventory);

				SceneCreateObjectByCrc createObjectMsg = new SceneCreateObjectByCrc(objectId, crafter.getOrientation().x, crafter.getOrientation().y, crafter.getOrientation().z, crafter.getOrientation().w, crafter.getPosition().x, crafter.getPosition().y, crafter.getPosition().z, resCRC, (byte) 0);
				crafter.getClient().getSession().write(createObjectMsg.serialize());      				
				services.resources.CharonPacketUtils.printAnalysis(createObjectMsg.serialize());
				
			 	UpdateContainmentMessage updateContainmentMessage= new UpdateContainmentMessage(containerObject.getObjectID(), crafterInventory.getObjectID(), -1);
				crafter.getClient().getSession().write(updateContainmentMessage.serialize());
			 	containerObject.sendBaselines(crafter.getClient());
	
				SceneEndBaselines sceneEndBaselinesMsg = new SceneEndBaselines(containerObject.getObjectID());
				crafter.getClient().getSession().write(sceneEndBaselinesMsg.serialize());
				services.resources.CharonPacketUtils.printAnalysis(sceneEndBaselinesMsg.serialize());
	       	
				containerObject.buildAttributeListMessage(crafter.getClient());
				player.setRecentContainer(containerObject);
			}
		} else { // create new container, first sample
			String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];           		  				
			ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, crafter.getPlanet());
			containerObject.initializeStats(sampleResource);
			containerObject.setProprietor(crafter);
			stackCount = core.resourceService.getResourceSampleQuantity(crafter, sampleResource); 
    		containerObject.setStackCount(stackCount,false);

			int resCRC = CRC.StringtoCRC(resourceContainerIFF);
			containerObject.setIffFileName(resourceContainerIFF);
			
			long objectId = containerObject.getObjectID();				
			SWGObject crafterInventory = crafter.getSlottedObject("inventory"); 
			containerObject.setParent(crafterInventory);

			SceneCreateObjectByCrc createObjectMsg = new SceneCreateObjectByCrc(objectId, crafter.getOrientation().x, crafter.getOrientation().y, crafter.getOrientation().z, crafter.getOrientation().w, crafter.getPosition().x, crafter.getPosition().y, crafter.getPosition().z, resCRC, (byte) 0);
			crafter.getClient().getSession().write(createObjectMsg.serialize());      				
			services.resources.CharonPacketUtils.printAnalysis(createObjectMsg.serialize());
			
		 	UpdateContainmentMessage updateContainmentMessage= new UpdateContainmentMessage(containerObject.getObjectID(), crafterInventory.getObjectID(), -1);
			crafter.getClient().getSession().write(updateContainmentMessage.serialize());
		 	containerObject.sendBaselines(crafter.getClient());

			SceneEndBaselines sceneEndBaselinesMsg = new SceneEndBaselines(containerObject.getObjectID());
			crafter.getClient().getSession().write(sceneEndBaselinesMsg.serialize());
			services.resources.CharonPacketUtils.printAnalysis(sceneEndBaselinesMsg.serialize());
			
			containerObject.buildAttributeListMessage(crafter.getClient());
			player.setRecentContainer(containerObject);
		}
		
		
		if ((stackCount > 0) && (!surveyTool.isRecoveryMode())) {
			// @survey:sample_located
			crafter.sendSystemMessage("You successfully locate a " + stackCount + " unit sample of " + sampleResource.getName() + "." , (byte) 0);
			core.playerService.giveExperience(crafter,(int)(2.5F*stackCount)); // resource_harvesting_inorganic
		} 
		if ((stackCount > 0) && (surveyTool.isRecoveryMode())) {
			crafter.sendSystemMessage("@survey:node_recovery", (byte) 0);
			core.playerService.giveExperience(crafter,(int)(2.5F*stackCount)); // resource_harvesting_inorganic
			
		}
	}
	
	public void requestSurvey(CreatureObject crafter, SWGObject target, SurveyCommand command, int actionCounter, String commandArgs){
		// Check if crafter has survey skill
		
		if (crafter.hasAbility(command.getCommandName())) { // ToDo !
			crafter.sendSystemMessage("You have insufficient skill to survey", (byte) 0);
			return;
		}
		if (target==null)
			return; // target object not valid
		
		SurveyTool surveyTool = (SurveyTool)target;			
		if (surveyTool.getToolType()==-1)		
			return; // Survey tool type was not recognized
		
		surveyTool.setUser(crafter);
		surveyTool.setRecoveryTime(10000L);
		PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
		player.setLastUsedSurveyTool(surveyTool);
		if(surveyTool.getCurrentlySampling())
		{
			crafter.sendSystemMessage("@survey:survey_sample", (byte) 0);	
			return;
		}
		
		// Counter too frequent survey button activation
		if(surveyTool.getCurrentlySurveying()){
			//System.out.println("TOO FREQUENT!");
			return;		
		}
		
		if (core.terrainService.isWater(crafter.getPlanet(), crafter.getPosition().x, crafter.getPosition().z))
			crafter.sendSystemMessage("@survey:no_survey_in_water", (byte) 0);	

	
//					Observer toolObserver    = surveyTool;
//					core.surveyService.addObserver(toolObserver);
		if (!toolIsInList(surveyTool))
			addActiveSurveyTool(surveyTool);
					
		String resourceNameString = commandArgs;
		GalacticResource resource = core.surveyService.grabResourceByName(resourceNameString);
		
		surveyTool.setCurrentlySurveying(true);
		surveyTool.setLastSurveyTime(System.currentTimeMillis());
		surveyTool.setSurveyResource(resource);
					
		String effectFile = surveyTool.getSurveyEffectString();		
		PlayClientEffectLocMessage cEffMsg = new PlayClientEffectLocMessage(effectFile,crafter.getPlanet().getName(),crafter.getPosition());
		crafter.getClient().getSession().write(cEffMsg.serialize());						
		crafter.sendSystemMessage("You begin to survey for " + commandArgs, (byte) 0);

		float surveyRadius = 64.0f;		
		int surveyToolRangeSetting = surveyTool.getSurveyRangeSetting();
		//surveyToolRangeSetting = 4;
		int divisor = 0;
		if (surveyToolRangeSetting==0) {
			divisor = 2;
			surveyRadius = 64.0f;
		} else if (surveyToolRangeSetting==1) {
			divisor = 3;
			surveyRadius = 128.0f;
		} else if (surveyToolRangeSetting==2) {
			divisor = 3;
			surveyRadius = 192.0f;
		} else if (surveyToolRangeSetting==3) {
			divisor = 4;
			surveyRadius = 256.0f;
		} else if (surveyToolRangeSetting==4) {
			divisor = 4;
			surveyRadius = 320.0f;
		} else {
			divisor = 5;
			surveyRadius = 3072.0f;
		}

		float differential = surveyRadius / (float) divisor;
		GalacticResource resourceToSurvey = surveyTool.getSurveyResource();			
		Vector<ResourceConcentration> concentrationMap = buildConcentrationsCollection(crafter.getPosition(),resourceToSurvey, surveyRadius, differential, crafter.getPlanetId());		
		constructSurveyMapMessage(crafter, concentrationMap, surveyRadius);
		crafter.sendSystemMessage("Distance to nearest Deposit : " + resourceToSurvey.getHelperMinDist(), (byte) 0);
	}
	
	public void requestSampling(CreatureObject crafter, SWGObject target, SurveyCommand command, int actionCounter, String commandArgs){	

		PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
		SurveyTool surveyTool = player.getLastUsedSurveyTool();
		
		if (crafter.getPosture()!=1) // QA
			surveyTool.setCurrentlySampling(false);
		
		if (surveyTool.getCurrentlySampling()) { // QA
			crafter.sendSystemMessage("@survey:already_sampling", (byte) 0);
			return;
		}
		
		if (crafter.getPosture()==13)
			return;
		
		if (crafter.getCombatFlag()!=0){
			crafter.sendSystemMessage("@survey:sample_cancel_attack", (byte) 0);
			return;
		}
		
		// ToDo: check mounted
		
		
		surveyTool.setCurrentlyCoolingDown(false);
		GalacticResource sampleResource = surveyTool.getSurveyResource();
		if(surveyTool==null || sampleResource==null) { // QA
			crafter.sendSystemMessage("You must survey for a resource before you can sample it.", (byte) 0);
			surveyTool.setExceptionalState(false);
			surveyTool.setCurrentlySampling(false);
			removeActiveSurveyTool(surveyTool);
			return;
		}
		
		if (sampleResource.getName().equals(commandArgs)) {
		
			float localConcentration = 1.0F;
			if (localConcentration > 0.3) {
				// Is the tool ready?
				if (surveyTool.getCurrentlySampling() || surveyTool.getCurrentlyCoolingDown() ) {
					int remaining = 0; // calculate remaining time
					crafter.sendSystemMessage("You will be able to sample again in " + remaining + " seconds.", (byte) 0); // "@survey:tool_recharge_time"
				} else {
					crafter.setPosture((byte) 1);
					crafter.sendSystemMessage("You kneel", (byte) 0);
								
					// Radiation is good for you
					//if (.equals("radioactive"))
					
					int samplingCost=125-(int)crafter.getSkillMod("surveying").getModifier();
					if (crafter.getAction()-samplingCost<0){ // Check HAM
						crafter.sendSystemMessage("@survey:gamble_no_action", (byte) 0);
						crafter.setPosture((byte) 0);
						surveyTool.setCurrentlySampling(false);
						surveyTool.setExceptionalState(false);
						removeActiveSurveyTool(surveyTool);
						return;
					}		
									
					surveyTool.setCurrentlySampling(true);
					surveyTool.setLastSampleTime(System.currentTimeMillis());
					addActiveSurveyTool(surveyTool);
											
					String effectFile = surveyTool.getSampleEffectString();					
					PlayClientEffectLocMessage cEffMsg = new PlayClientEffectLocMessage(effectFile,crafter.getPlanet().getName(),crafter.getPosition());
					crafter.getClient().getSession().write(cEffMsg.serialize());
					
					crafter.sendSystemMessage("You begin to sample for " + commandArgs, (byte) 0);
					surveyTool.setCurrentlySampling(true); 
					
					crafter.setAction(crafter.getAction()-samplingCost);
					
				}
			} else {
				crafter.sendSystemMessage("There are only trace amounts of " + commandArgs + " here.  Find a higher concentration of the resource, and try sampling again.", (byte) 0); // "@survey:trace_amount:"
			}
		} else {
			crafter.sendSystemMessage("You must survey for " + commandArgs + " before you can sample for it.", (byte) 0);
			surveyTool.setExceptionalState(false);
			surveyTool.setCurrentlySampling(false);
			removeActiveSurveyTool(surveyTool);
		}		
	}
	
	public void continueSampling(SurveyTool surveyTool){
		
		surveyTool.setCurrentlyCoolingDown(false);
		CreatureObject crafter = surveyTool.getUser();
		PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
		if (crafter.getPosture()!=1){
			crafter.sendSystemMessage("@survey:sample_cancel", (byte) 0);
			surveyTool.setExceptionalState(false);
			surveyTool.setCurrentlySampling(false);
			removeActiveSurveyTool(surveyTool);
			return;
		}
		if (crafter.getPosture()==13)
			return;
		
		if (crafter.getCombatFlag()!=0){
			crafter.sendSystemMessage("@survey:sample_cancel_attack", (byte) 0);
			return;
		}
					
		GalacticResource sampleResource = surveyTool.getSurveyResource();
		//float localConcentration = sampleResource.deliverConcentrationForSurvey(crafter.getPlanetId(), crafter.getPosition().x, crafter.getPosition().z);
		float localConcentration = 1.0F;
		if (localConcentration > 0.3) {
			crafter.setPosture((byte) 1);
			
			// ToDo: overfilling resource container with continuesampling
			
			int samplingCost=125-(int)crafter.getSkillMod("surveying").getModifier();
			if (crafter.getAction()-samplingCost<0){
				crafter.sendSystemMessage("@survey:gamble_no_action", (byte) 0);
				crafter.setPosture((byte) 0);
				surveyTool.setCurrentlySampling(false);
				surveyTool.setExceptionalState(false);
				//removeActiveSurveyTool(surveyTool);
				return;
			}			
			
			if (! surveyTool.isRecoveryMode()){
			
				int exceptionalChance = new Random().nextInt(100);			
				if (exceptionalChance<7 && ! surveyTool.isExceptionalState()){
					crafter.sendSystemMessage("@survey:gnode_d", (byte) 0);
					surveyTool.setExceptionalState(true);
					
					final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", crafter, surveyTool, 0);
					window.setProperty("bg.caption.lblTitle:Text", "@base_player:swg");
					window.setProperty("Prompt.lblPrompt:Text", "@survey:gnode_d");
					window.addListBoxMenuItem("@survey:gnode_1", 0);
					window.addListBoxMenuItem("@survey:gnode_2", 1);	
					window.setProperty("btnOk:visible", "True");
					window.setProperty("btnCancel:visible", "True");
					window.setProperty("btnOk:Text", "@ok");
					window.setProperty("btnCancel:Text", "@cancel");				
					Vector<String> returnList = new Vector<String>();
					returnList.add("List.lstList:SelectedRow");
					
					window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
						@SuppressWarnings("unchecked")
						@Override
						public void process(SWGObject owner, int eventType, Vector<String> returnList) {										
							int index = Integer.parseInt(returnList.get(0));
							if (index==0){
								// Continue working
								CreatureObject crafter = (CreatureObject)owner;
								PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
								if (crafter!=null){
									SurveyTool surveyTool = player.getLastUsedSurveyTool();
									if (surveyTool!=null){
										surveyTool.setExceptionalState(false);
										core.suiService.closeSUIWindow(owner, 0);
										continueSampling(surveyTool);
									}
								}	
							} else {
								// Attempt to recover	
								CreatureObject crafter = (CreatureObject)owner;
								PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
								SurveyTool surveyTool = player.getLastUsedSurveyTool();							
								if (surveyTool!=null){
									surveyTool.setRecoveryMode(true);
									surveyTool.setExceptionalState(false);
									core.suiService.closeSUIWindow(owner, 0);
									continueSampling(surveyTool);
								}
							}							
						}					
					});
					
					core.suiService.openSUIWindow(window);
					return;
				}
				
				surveyTool.setCurrentlySampling(true);
				surveyTool.setLastSampleTime(System.currentTimeMillis());
				
				String effectFile = surveyTool.getSampleEffectString();					
				PlayClientEffectLocMessage cEffMsg = new PlayClientEffectLocMessage(effectFile,crafter.getPlanet().getName(),crafter.getPosition());
				crafter.getClient().getSession().write(cEffMsg.serialize());
				
				crafter.setAction(crafter.getAction()-samplingCost);
			
			} else {
				// sampling cost for recovery
				surveyTool.setRecoveryMode(false);
				samplingCost = 200;
				if (crafter.getAction()-samplingCost<0){
					crafter.sendSystemMessage("@survey:gamble_no_action", (byte) 0);
					crafter.setPosture((byte) 0);
					surveyTool.setCurrentlySampling(false);
					surveyTool.setExceptionalState(false);
					surveyTool.setLastSampleTime(System.currentTimeMillis());
					removeActiveSurveyTool(surveyTool);
					return;
				}
				surveyTool.setCurrentlySampling(true);
				surveyTool.setLastSampleTime(System.currentTimeMillis());
				surveyTool.setExceptionalState(false);
				
				String effectFile = surveyTool.getSampleEffectString();					
				PlayClientEffectLocMessage cEffMsg = new PlayClientEffectLocMessage(effectFile,crafter.getPlanet().getName(),crafter.getPosition());
				crafter.getClient().getSession().write(cEffMsg.serialize());
				crafter.setAction(crafter.getAction()-samplingCost);
				
			}

			surveyTool.setCurrentlySampling(true);
			surveyTool.setLastSampleTime(System.currentTimeMillis());
			
			String effectFile = surveyTool.getSampleEffectString();					
			PlayClientEffectLocMessage cEffMsg = new PlayClientEffectLocMessage(effectFile,crafter.getPlanet().getName(),crafter.getPosition());
			crafter.getClient().getSession().write(cEffMsg.serialize());
			
			crafter.setAction(crafter.getAction()-samplingCost);
		
		} else {
			crafter.sendSystemMessage("There are only trace amounts of " + sampleResource.getName() + " here.  Find a higher concentration of the resource, and try sampling again.", (byte) 0); // "@survey:trace_amount:"
		}
	}
	
	public void addActiveSurveyTool(SurveyTool tool){
		activeSurveyTools.add(tool);
	}
	
	public Vector<SurveyTool> getActiveSurveyTools(){
		return activeSurveyTools;
	}
	
	public void removeActiveSurveyTool(SurveyTool surveyTool){
		activeSurveyTools.remove(surveyTool);
	}
	
	public boolean toolIsInList(SurveyTool surveyTool){
		Vector<SurveyTool> toolList = getActiveSurveyTools();
		for (SurveyTool tool : toolList){
			if (tool.getTanoID()==surveyTool.getTanoID())
				return true;
		}
		return false;
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public SUICallback handleExceptionalInput(SWGObject crafter,SUIWindow suiWindow){
		
		SUICallback callback = suiWindow.getCallbackByEventId(1);
		if (callback!=null)
			System.out.println("handleExceptionalInput " + callback.toString());
		core.suiService.closeSUIWindow(crafter, 0);
		return callback;
	}
	
	
	public void createSurveyRangeSUIWindow(SWGObject owner, TangibleObject target) {
		final SUIWindow window = core.suiService.createSUIWindow("Script.listBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@base_player:swg");
		window.setProperty("Prompt.lblPrompt:Text", "@survey:select_range");
		window.addListBoxMenuItem("64m x 3pts", 0);
		window.addListBoxMenuItem("128m x 4pts", 1);
		window.addListBoxMenuItem("192m x 4pts", 2);	
		window.addListBoxMenuItem("256m x 5pts", 3);	
		window.addListBoxMenuItem("320m x 5pts", 4);	
		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@ok");
		window.setProperty("btnCancel:Text", "@cancel");				
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		final SurveyTool outerSurveyTool = (SurveyTool)target;
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				CreatureObject crafter = (CreatureObject)owner;
				crafter.sendSystemMessage("handleSurveyRangeInput process", (byte) 0);
				if (crafter!=null) {
					if (outerSurveyTool!=null){
						int index = Integer.parseInt(returnList.get(0));
						if (index==0){
							outerSurveyTool.setSurveyRangeSetting((byte)0);
						} 
						if (index==1){	
							outerSurveyTool.setSurveyRangeSetting((byte)1);
						}	
						if (index==2){	
							outerSurveyTool.setSurveyRangeSetting((byte)2);
						}
						if (index==3){	
							outerSurveyTool.setSurveyRangeSetting((byte)3);
						}
						if (index==4){	
							outerSurveyTool.setSurveyRangeSetting((byte)4);
						}
					}
				}
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
	
	public void createRadioactivityWarningSUIWindow(SWGObject owner, TangibleObject target) {
		// ToDo:
		final SUIWindow window = core.suiService.createSUIWindow("Script.messageBox", owner, target, 0);
		window.setProperty("bg.caption.lblTitle:Text", "@base_player:swg");
		window.setProperty("Prompt.lblPrompt:Text", "@survey:radioactive_sample_d");
		window.setProperty("Prompt.lblPrompt:Text", "@survey:radioactive_sample_t");
		window.setProperty("btnOk:visible", "True");
		window.setProperty("btnCancel:visible", "True");
		window.setProperty("btnOk:Text", "@ok");
		window.setProperty("btnCancel:Text", "@cancel");
	
		Vector<String> returnList = new Vector<String>();
		returnList.add("List.lstList:SelectedRow");
		final SurveyTool outerSurveyTool = (SurveyTool)target;
		
		window.addHandler(0, "", Trigger.TRIGGER_OK, returnList, new SUICallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				CreatureObject crafter = (CreatureObject)owner;
				if (crafter!=null) {
					if (outerSurveyTool!=null){
						int index = Integer.parseInt(returnList.get(0));
						if (index==0){
							outerSurveyTool.setSurveyRangeSetting((byte)0);
						} 
						
					}
				}
			}					
		});		
		core.suiService.openSUIWindow(window);
	}
		
	public SUICallback handleSurveyRangeInput(SWGObject crafter,SUIWindow suiWindow){
		SUICallback callback = suiWindow.getCallbackByEventId(1);
		if (callback!=null)
			System.out.println("handleExceptionalInput " + callback.toString());
		core.suiService.closeSUIWindow(crafter, 0);
		return callback;
	}	
	
	// helper method to delete later
	public void levelUpto90(SWGObject crafter){
		
		int[] xpArray = new int[] 	{50,
				1000,	
				6000,	
				18000,	
				21000,	
				60000,	
				91000,	
				120000,
				140000,
				182650,
				213900,	
				245150,
				276400,	
				307650,	
				395150,	
				482650,	
				607650,	
				732650,	
				849316,	
				965982,	
				1082650,	
				1307650,	
				1532650,	
				1620150,	
				1707650,	
				1832650,	
				1957650,	
				2074316,	
				2190982,	
				2307650,	
				2532650,	
				2757650,	
				2845150,	
				2932650,	
				3057650,	
				3182650,	
				3299316,	
				3415982,	
				3532650,	
				3757650,	
				3982650,	
				4070150,	
				4157650,	
				4282650,	
				4407650,	
				4524316,	
				4640982,	
				4757650,	
				4982650,	
				5102650,	
				5352650,	
				5602650,	
				5852650,	
				6102650,
				6227650,	
				6390850,	
				6679050,	
				6967250,	
				7255450,	
				7543650,	
				7788650,	
				8033650,	
				8278650,	
				8523650,	
				8768650,	
				9013650,	
				9258650,	
				9503650,	
				9748650,	
				9993650,	
				10242650,	
				10491650,	
				10740650,	
				10989650,	
				11238650,	
				11438650,	
				11638650,	
				11838650,
				12038650};

		int xpCounter = 0;
		float delta = 0.0F;
		float threshold = 1.0F;		
		int currentXP = 0;
		CreatureObject crafterObj = (CreatureObject)crafter;
		int currentLevel = (int) crafterObj.getLevel();
		int distance = xpArray[currentLevel+1] - currentXP;
		float dd = distance/400000000000000.0F;
		while (xpCounter<20000000){
			
			if (delta>threshold){
				int setXp = (int)Math.floor(delta);
				core.playerService.giveExperience(crafterObj, setXp);
				currentXP += setXp;
			}
			
			if (crafterObj.getLevel()>currentLevel) {
				distance = xpArray[currentLevel+1] - currentXP;
				if (currentLevel>30)
					dd = distance/4000000000.0F;
				else if (currentLevel>20)
					dd = distance/40000000000.0F;
				else if (currentLevel>12)
					dd = distance/400000000000.0F;
				delta = 0.0F;
				currentLevel = crafterObj.getLevel();
				//System.out.println(" dd " + dd );
			}
			
			delta += dd;
			System.out.println("delta " + delta + " dd " + dd + " currentXP " + currentXP);

		}
		
	}	
}