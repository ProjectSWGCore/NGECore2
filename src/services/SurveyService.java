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

import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import protocol.swg.PlayClientEffectLocMessage;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.resource.ResourceRoot;
import resources.objects.tangible.TangibleObject;
import resources.objects.tool.SurveyTool;
import services.sui.SUIWindow;
import services.sui.SUIWindow.SUICallback;
import services.sui.SUIWindow.Trigger;
import main.NGECore;
import engine.resources.container.Traverser;
import engine.resources.objects.SWGObject;
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
		core.commandService.registerCommand("requestsurvey");
		core.commandService.registerCommand("requestcoreSample");
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
		synchronized(activeSurveyTools){
			// All tools sampling
			SurveyTool removeTool=null;
			for (SurveyTool surveyTool : activeSurveyTools){
				if (surveyTool.getCurrentlySurveying()){
					// Check if survey process has finished
					if (System.currentTimeMillis()>surveyTool.getLastSurveyTime()+3000){					
						removeTool = surveyTool;
						surveyTool.setCurrentlySurveying(false);
						surveyTool.sendConstructSurveyMapMessage();
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
	}
	
	public void handleSamplingStages(SurveyTool surveyTool){
		
		if (surveyTool.isExceptionalState())
			return;
		CreatureObject crafter = surveyTool.getUser();			
		PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
		GalacticResource sampleResource = surveyTool.getSurveyResource();
		int stackCount = 0;
		boolean gamblingwon = false;
		//ResourceContainerObject container = player.getRecentContainer();
		ResourceContainerObject container = null;
		// Attempt to find container with the currently sampled resource in
		// player inventory tree
		ResourceContainerObject foundContainer = findResourceContainerInInventory(crafter,sampleResource);
		if (foundContainer!=null)
			container = foundContainer;
		
		if (! surveyTool.isRecoveryMode()) 
			stackCount = core.resourceService.getResourceSampleQuantity(crafter, sampleResource); 
		else { // handle particularly rich case
			stackCount = core.resourceService.getResourceSampleQuantity(crafter, sampleResource); 
			int recoveryChance = new Random().nextInt(100);	
			if (recoveryChance<99){
				gamblingwon = true;
				stackCount *= 3;
			}
			surveyTool.setRecoveryMode(false);
		}
	
		if (container!=null) {
			if (container.getReferenceID()==sampleResource.getId()) {
				int stackCountToUpdate = container.getStackCount();
				if (stackCountToUpdate<ResourceContainerObject.maximalStackCapacity-stackCount){ // sample fits into container
					SWGObject crafterInventory = crafter.getSlottedObject("inventory");   
					container.setStackCount(stackCountToUpdate+stackCount,crafter);
					//crafter.sendSystemMessage("@base_player:prose_grant_xp @exp_n:resource_harvesting_inorganic", (byte) 0);
					core.playerService.giveExperience(crafter,(int)(2.5F*stackCount));
					//exp_n:resource_harvesting_inorganic
					
					//container.sendDelta3(crafter.getClient());
					//container.buildAttributeListMessage(crafter.getClient());
				} else { // new container
					if ((stackCount > 0) && (!surveyTool.isRecoveryMode())) {
						String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];           		  				
						ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, crafter.getPlanet());
						containerObject.initializeStats(sampleResource);
						containerObject.setProprietor(crafter);
						SWGObject crafterInventory = crafter.getSlottedObject("inventory");
						container.setStackCount(stackCount,crafter);
						crafterInventory.add(containerObject);
						player.setRecentContainer(containerObject);
					}

				}
			} else { // Mismatch -> new container

				String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];           		  				
				if ((stackCount > 0) && (!surveyTool.isRecoveryMode())) {
					ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, crafter.getPlanet());
					containerObject.initializeStats(sampleResource);
					containerObject.setProprietor(crafter);
					container.setStackCount(stackCount,crafter);
					SWGObject crafterInventory = crafter.getSlottedObject("inventory");
					crafterInventory.add(containerObject);
					player.setRecentContainer(containerObject);
				}
				
			}
		} else { // create new container, first sample
	
			if ((stackCount > 0) && (!surveyTool.isRecoveryMode())) {

				String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];           		  				
				ResourceContainerObject containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, crafter.getPlanet());
				containerObject.initializeStats(sampleResource);
				containerObject.setProprietor(crafter);
				//stackCount = core.resourceService.getResourceSampleQuantity(crafter, sampleResource); 
	    		containerObject.setStackCount(stackCount,crafter);
	    		SWGObject crafterInventory = crafter.getSlottedObject("inventory");
				crafterInventory.add(containerObject);
				player.setRecentContainer(containerObject);
	
			}
		}
		
		if (gamblingwon) {
			crafter.sendSystemMessage("@survey:critical_success", (byte) 0);
			core.playerService.giveExperience(crafter,(int)(2.5F*stackCount)); 
			surveyTool.setExceptionalState(false);
			return;
		}
		
		if ((stackCount > 0) && (!surveyTool.isRecoveryMode())) {
			// @survey:sample_located
			crafter.sendSystemMessage("You successfully locate a " + stackCount + " unit sample of " + sampleResource.getName() + "." , (byte) 0);
			core.playerService.giveExperience(crafter,(int)(2.5F*stackCount)); 
		} 
		if ((stackCount > 0) && (surveyTool.isRecoveryMode())) {
			crafter.sendSystemMessage("@survey:node_recovery", (byte) 0);
			core.playerService.giveExperience(crafter,(int)(2.5F*stackCount)); 
			
		}
		if ((stackCount == 0) && (!surveyTool.isRecoveryMode())) {
			crafter.sendSystemMessage("@survey:sample_failed", (byte) 0);
			core.playerService.giveExperience(crafter,(int)(2.5F*stackCount));
			//surveyTool.setRecoveryMode(true);
			//surveyTool.setRecoveryTime(System.currentTimeMillis());						
		}
	}
	
	
	public ResourceContainerObject findResourceContainerInInventory(CreatureObject owner, GalacticResource resource){
		ResourceContainerObject foundContainer = null;
		final Vector<ResourceContainerObject> found = new Vector<ResourceContainerObject>();
		TangibleObject playerInventory = (TangibleObject) owner.getSlottedObject("inventory");
		playerInventory.viewChildren(owner, false, false, new Traverser() {
			@Override
			public void process(SWGObject obj) {
				
				if (obj instanceof ResourceContainerObject){
					ResourceContainerObject container = (ResourceContainerObject) obj;
					if (container.getReferenceID()==resource.getId() 
					    && container.getStackCount()<ResourceContainerObject.maximalStackCapacity-10)
						found.add(container);
				}
			}
		});
		
		if (found.size()>0)
			foundContainer = found.get(0);
		return foundContainer;
	}
	
	public void requestSurvey(CreatureObject crafter, SWGObject target, String commandArgs){
		// Check if crafter has survey skill
		
//		if (!crafter.hasAbility("surveying")) { // ToDo !
//			crafter.sendSystemMessage("You have insufficient skill to survey", (byte) 0);
//			return;
//		}
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
		
		// QA tool lockup countermeasure
		if(surveyTool.getCurrentlySurveying() && surveyTool.getLastSurveyTime()<System.currentTimeMillis()-3000){
			surveyTool.setCurrentlySurveying(false);
			surveyTool.setLastSurveyTime(System.currentTimeMillis());
		}
		
		// Counter too frequent survey button activation
		if(surveyTool.getCurrentlySurveying()){
			return;		
		}
		
		if (core.terrainService.isWater(crafter.getPlanet(), crafter.getPosition().x, crafter.getPosition().z))
			crafter.sendSystemMessage("@survey:no_survey_in_water", (byte) 0);	

	
//					Observer toolObserver    = surveyTool;
//					core.surveyService.addObserver(toolObserver);
		if (!toolIsInList(surveyTool))
			addActiveSurveyTool(surveyTool);
					
		String resourceNameString = commandArgs;
		GalacticResource resource = core.resourceService.grabResourceByName(resourceNameString);
		
		surveyTool.setCurrentlySurveying(true);
		surveyTool.setLastSurveyTime(System.currentTimeMillis());
		surveyTool.setSurveyResource(resource);
					
		String effectFile = surveyTool.getSurveyEffectString();		
		PlayClientEffectLocMessage cEffMsg = new PlayClientEffectLocMessage(effectFile,crafter.getPlanet().getName(),crafter.getPosition());
		crafter.getClient().getSession().write(cEffMsg.serialize());						
		crafter.sendSystemMessage("You begin to survey for " + commandArgs, (byte) 0);

	}
	
	public void requestSampling(CreatureObject crafter, SWGObject target, String commandArgs){	

		PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
		SurveyTool surveyTool = player.getLastUsedSurveyTool();
		
		if (surveyTool.getCurrentlySurveying()){
			crafter.sendSystemMessage("@survey:sample_survey", (byte) 0);
			return;
		}
		
		if (crafter.getPosture()!=1) // QA
			surveyTool.setCurrentlySampling(false);
		
		if (surveyTool.getCurrentlySampling()) { // QA
			crafter.sendSystemMessage("@survey:already_sampling", (byte) 0);
			return;
		}
		
		if (crafter.getPosture()==13)
			return; // QA
		
		if (crafter.getCombatFlag()!=0){ // QA
			crafter.sendSystemMessage("@survey:sample_cancel_attack", (byte) 0);
			return;
		}
		
		if (crafter.getPosture()==10 || crafter.getPosture()==11){ // QA
			crafter.sendSystemMessage("You cannot sample while on a mount", (byte) 0);
			return;
		}
		
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
		
			float localConcentration = sampleResource.deliverConcentrationForSurvey(crafter.getPlanetId(), crafter.getPosition().x, crafter.getPosition().z);
			//float localConcentration = 1.0F;
			if (localConcentration > 0.3) {
				// Is the tool ready?
				if (surveyTool.getCurrentlySampling() || surveyTool.getCurrentlyCoolingDown() ) {
					int remaining = 0; // calculate remaining time
					crafter.sendSystemMessage("You will be able to sample again in " + remaining + " seconds.", (byte) 0); // "@survey:tool_recharge_time"
				} else {
					
					// ToDo: Find out if NGE really still did this
//					if (sampleResource.getResourceRoot().getResourceClass().equals("Radioactive")){ //resourceRoot.getResourceClass("Radioactive");
//						createRadioactivityWarningSUIWindow(crafter, surveyTool);
//						return;
//					}
					
					crafter.setPosture((byte) 1);
					crafter.sendSystemMessage("You kneel", (byte) 0);
								
						
					//int samplingCost=125-(int)crafter.getSkillMod("surveying").getModifier();
					int samplingCost=125;
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
				surveyTool.setCurrentlySampling(false);
				surveyTool.setCurrentlySurveying(false);
				//surveyTool.setLastSampleTime(System.currentTimeMillis());
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
		float localConcentration = sampleResource.deliverConcentrationForSurvey(crafter.getPlanetId(), crafter.getPosition().x, crafter.getPosition().z);
		//float localConcentration = 1.0F;
		if (localConcentration > 0.1) {
			crafter.setPosture((byte) 1);
			
			// ToDo: overfilling resource container with continuesampling
			
			//int samplingCost=125-(int)crafter.getSkillMod("surveying").getModifier();
			int samplingCost=125;
			if (crafter.getAction()-samplingCost<0 && ! surveyTool.isExceptionalState()){
				crafter.sendSystemMessage("@survey:gamble_no_action", (byte) 0);
				crafter.setPosture((byte) 0);
				surveyTool.setCurrentlySampling(false);
				surveyTool.setExceptionalState(false);
				//removeActiveSurveyTool(surveyTool);
				return;
			}			
			
			if (! surveyTool.isRecoveryMode()){
				int exceptionalChance = new Random().nextInt(100);		 // 7 was good	
				if (exceptionalChance<20 && ! surveyTool.isExceptionalState()){
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
										return;
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
									return;
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
				//surveyTool.setRecoveryMode(false);
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
			surveyTool.setCurrentlySampling(false);
			surveyTool.setCurrentlySurveying(false);
		}
	}
	
	public void addActiveSurveyTool(SurveyTool tool){
		synchronized(activeSurveyTools){
			if (! activeSurveyTools.contains(tool))
				activeSurveyTools.add(tool);
		}
	}
	
	public Vector<SurveyTool> getActiveSurveyTools(){
		return activeSurveyTools;
	}
	
	public void removeActiveSurveyTool(SurveyTool surveyTool){
		synchronized(activeSurveyTools){
			activeSurveyTools.remove(surveyTool);
		}
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
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				CreatureObject crafter = (CreatureObject)owner;
				//crafter.sendSystemMessage("handleSurveyRangeInput process", (byte) 0);
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
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				((CreatureObject)owner).sendSystemMessage("Rad confirmed", (byte) 0);
			}					
		});		
		window.addHandler(1, "", Trigger.TRIGGER_CANCEL, returnList, new SUICallback() {
			@Override
			public void process(SWGObject owner, int eventType, Vector<String> returnList) {			
				((CreatureObject)owner).sendSystemMessage("Rad declined", (byte) 0);
			}					
		});	
		core.suiService.openSUIWindow(window);
	}
		
	public SUICallback handleSurveyRangeInput(SWGObject crafter,SUIWindow suiWindow){
		SUICallback callback = suiWindow.getCallbackByEventId(1);
		core.suiService.closeSUIWindow(crafter, 0);
		return callback;
	}	
	
}