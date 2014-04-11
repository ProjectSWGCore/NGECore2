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
package services.resources;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.sleepycat.persist.EntityCursor;

import main.NGECore;
import engine.resources.common.CRC;
import engine.resources.objects.SWGObject;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;
import resources.objects.creature.CreatureObject;
import resources.objects.harvester.HarvesterObject;
import resources.objects.resource.GalacticResource;
import resources.objects.resource.ResourceContainerObject;
import resources.objects.resource.ResourceRoot;

/** 
 * @author Charon 
 */

public class ResourceService implements INetworkDispatch {
	
	private NGECore core;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private boolean enableResourceHistory = true; // Set this to false, to prevent persistence of resources into history Db
		
	private Hashtable<Integer, ResourceRoot> resourceRootTable = new Hashtable<Integer, ResourceRoot>(); // synchronized
	private Vector<ResourceRoot> ironRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> aluminumRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> steelRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> copperRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> oreExtrusiveRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> oreIntrusiveRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> oreCarbonateRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> crystallineGemstoneRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> amorphousGemstoneRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> radioactiveRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> petrochemicalSolidRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> petrochemicalLiquidRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> polymerRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> lubricatingOilRoots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> reactiveGasRoots = new Vector<ResourceRoot>();
	
	private Vector<ResourceRoot> planetaryPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> corelliaPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> dantooinePool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> dathomirPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> endorPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> kashyyykPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> lokPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> mustafarPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> nabooPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> roriPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> talusPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> tattoinePool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> yavinPool4Roots = new Vector<ResourceRoot>();
	// For new future planet's resources add:
//	private Vector<ResourceRoot> NEWPLANETPool4Roots = new Vector<ResourceRoot>();
	private Vector<ResourceRoot> JTLRoots = new Vector<ResourceRoot>();
	
	private Vector<GalacticResource> allSpawnedResources = new Vector<GalacticResource>(); // needs persistance
	private Vector<String> completeResourceNameHistory = new Vector<String>(); // needs persistance
	private Vector<GalacticResource> spawnedResourcesPool1 = new Vector<GalacticResource>();
	private Vector<GalacticResource> spawnedResourcesPool2 = new Vector<GalacticResource>();
	private Vector<GalacticResource> spawnedResourcesPool3 = new Vector<GalacticResource>();
	private Vector<GalacticResource> spawnedResourcesPool4 = new Vector<GalacticResource>();
	
	int totalSpawnedResourcesNumber = 0;
	boolean bigBangOccured = false;
	
	short[] minCapArray = new short[]{300,300,300,300,300,300,300,300,300,300,300}; 
	short[] maxCapArray = new short[]{800,800,800,800,800,800,800,800,800,800,800};  
	short[] minUnCappedArray = new short[]{1,1,1,1,1,1,1,1,1,1,1}; 
	short[] maxUnCappedArray = new short[]{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000};  
	
	public ResourceService(NGECore core) {
		this.core = core;	
		//EntityCursor<ResourceRoot> cursor = core.getResourceRootsODB().getCursor(Integer.class, ResourceRoot.class);
		//Iterator<ResourceRoot> it = cursor.iterator();		
		//if(!it.hasNext()) {
//		if(core.getResourceRootsODB()==null) {
//			createCollections();
//			createCollections2();
//			createCollections3();
//		} else {
			EntityCursor<ResourceRoot> cursor = core.getResourceRootsODB().getCursor(Integer.class, ResourceRoot.class);
			Iterator<ResourceRoot> it = cursor.iterator();		
			if(!it.hasNext()) {
				createCollections();
				createCollections2();
				createCollections3();
			}
//		}
			
		core.commandService.registerCommand("resourcecontainersplit");
		core.commandService.registerCommand("resourcecontainertransfer");			
		core.commandService.registerCommand("factorycratesplit");	
		start();
	}
	
	//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
	
	
	public void createCollections(){

		// For testing purposes the Hashtable is being put together here
		// After first test phase, it will be read in from the database
		
		ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("aluminum_titanium");
		resourceRoot.setResourceClass("Aluminum");
		resourceRoot.setResourceType("Titanium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Titanium Aluminum: CR 1 - 174, CD 300 - 408, DR 1 - 174, HR 200 - 330, MA 300 - 452, SR 300 - 430, UT 300 - 430 
		minCapArray = new short[]{(short)1  ,(short)300,(short)1  ,(short)200,(short)300,(short)300,(short)300,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)174,(short)408,(short)174,(short)330,(short)452,(short)430,(short)430,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(0, resourceRoot);
		aluminumRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("aluminum_agrinium");
		resourceRoot.setResourceClass("Aluminum");
		resourceRoot.setResourceType("Agrinium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Agrinium Aluminum: CR 94 - 307, CD 358 - 492, DR 94 - 307, HR 270 - 430, MA 382 - 568, SR 370 - 530, UT 370 - 530 
		minCapArray = new short[]{(short)94  ,(short)358,(short)94  ,(short)270,(short)382,(short)370,(short)370,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)307 ,(short)492,(short)307 ,(short)430,(short)568,(short)530,(short)530,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(1, resourceRoot);
		aluminumRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("aluminum_chromium");
		resourceRoot.setResourceClass("Aluminum");
		resourceRoot.setResourceType("Chromium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Chromium Aluminum: CR 227 - 440, CD 442 - 575, DR 227 - 440, HR 370 - 530, MA 498 - 685, SR 470 - 630, UT 470 - 630
		minCapArray = new short[]{(short)227  ,(short)442,(short)227  ,(short)370,(short)498,(short)470,(short)470,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)440  ,(short)575,(short)440  ,(short)530,(short)685,(short)630,(short)630,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(2, resourceRoot);
		aluminumRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("aluminum_duralumin");
		resourceRoot.setResourceClass("Aluminum");
		resourceRoot.setResourceType("Duralumin");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Duralumin Aluminum: CR 361 - 574, CD 525 - 658, DR 361 - 574, HR 470 - 630, MA 615 - 802, SR 570 - 730, UT 570 - 730
		minCapArray = new short[]{(short)361  ,(short)525,(short)361  ,(short)470,(short)615,(short)570,(short)570,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)574  ,(short)658,(short)574  ,(short)630,(short)802,(short)730,(short)730,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(3, resourceRoot);
		aluminumRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("aluminum_linksteel");
		resourceRoot.setResourceClass("Aluminum");
		resourceRoot.setResourceType("Link-Steel");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Link-Steel Aluminum: CR 494 - 707, CD 608 - 742, DR 494 - 707, HR 570 - 730, MA 732 - 918, SR 670 - 830, UT 670 - 830
		minCapArray = new short[]{(short)494  ,(short)608,(short)494  ,(short)570,(short)732,(short)670,(short)670,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)707  ,(short)742,(short)707  ,(short)730,(short)918,(short)830,(short)830,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(4, resourceRoot);
		aluminumRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("aluminum_phrik");
		resourceRoot.setResourceClass("Aluminum");
		resourceRoot.setResourceType("Phrik");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Phrik Aluminum: CR 627 - 800, CD 692 - 800, DR 627 - 800, HR 670 - 800, MA 848 - 1000, SR 770 - 900, UT 770 - 900
		minCapArray = new short[]{(short)672  ,(short)692,(short)627  ,(short)670,(short)848 ,(short)770,(short)770,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)800  ,(short)800,(short)800  ,(short)800,(short)1000,(short)900,(short)900,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(5, resourceRoot);
		aluminumRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_desh");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Desh");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Desh Copper: CR 1 - 116, CD 500 - 572, DR 1 - 102, HR 200 - 265, MA 500 - 572, SR 300 - 372, UT 300 - 372
		minCapArray = new short[]{(short)1   ,(short)500,(short)1   ,(short)200,(short)500 ,(short)300,(short)300,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)116 ,(short)572,(short)102 ,(short)265,(short)572 ,(short)372,(short)372,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(6, resourceRoot);
		copperRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_thallium");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Thallium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Thallium Copper: CR 63 - 205, CD 539 - 628, DR 55 - 180, HR 235 - 315, MA 539 - 628, SR 339 - 428, UT 339 - 428
		minCapArray = new short[]{(short)63  ,(short)539,(short)55  ,(short)235,(short)539 ,(short)339,(short)339,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)205 ,(short)628,(short)180 ,(short)315,(short)628 ,(short)428,(short)428,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(7, resourceRoot);
		copperRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_beyrllius");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Beyrllius");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Beyrllius Copper: CR 152 - 294, CD 594 - 683, DR 133 - 257, HR 285 - 365, MA 594 - 683, SR 394 - 483, UT 394 - 483
		minCapArray = new short[]{(short)152 ,(short)594,(short)133 ,(short)285,(short)594 ,(short)394,(short)394,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)294 ,(short)683,(short)257 ,(short)365,(short)683 ,(short)483,(short)483,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(8, resourceRoot);
		copperRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_codoan");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Codoan");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Codoan Copper: CR 241 - 383, CD 650 - 739, DR 211 - 335, HR 335 - 415, MA 650 - 739, SR 450 - 539, UT 450 - 539
		minCapArray = new short[]{(short)241 ,(short)650,(short)211 ,(short)335,(short)650 ,(short)450,(short)450,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)383 ,(short)739,(short)335 ,(short)415,(short)739 ,(short)539,(short)593,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(9, resourceRoot);
		copperRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_diatium");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Diatium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Diatium Copper: CR 329 - 472, CD 706 - 794, DR 288 - 413, HR 385 - 465, MA 706 - 794, SR 506 - 594, UT 506 - 594
		minCapArray = new short[]{(short)329 ,(short)706,(short)288 ,(short)385,(short)706 ,(short)506,(short)506,(short)0,(short)0,(short)0   ,(short)0};
		maxCapArray = new short[]{(short)472 ,(short)794,(short)413 ,(short)465,(short)794 ,(short)594,(short)594,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(10, resourceRoot);
		copperRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_kelsh");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Kelsh");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Kelsh Copper: CR 418 - 560, CD 761 - 850, DR 366 - 490, HR 435 - 515, MA 761 - 850, SR 561 - 650, UT 561 - 650
		minCapArray = new short[]{(short)418 ,(short)761,(short)366 ,(short)435,(short)761 ,(short)561,(short)561,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)560 ,(short)850,(short)490 ,(short)515,(short)850 ,(short)650,(short)650,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(11, resourceRoot);
		copperRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_mythra");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Mythra");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Mythra Copper: CR 507 - 649, CD 817 - 906, DR 444 - 568, HR 485 - 565, MA 817 - 906, SR 617 - 706, UT 617 - 706
		minCapArray = new short[]{(short)507 ,(short)817,(short)444 ,(short)485,(short)817 ,(short)617,(short)617,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)649 ,(short)906,(short)568 ,(short)565,(short)906 ,(short)706,(short)706,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(12, resourceRoot);
		copperRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_platinite");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Platinite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Platinite Copper: CR 596 - 738, CD 872 - 961, DR 521 - 646, HR 535 - 615, MA 872 - 961, SR 672 - 761, UT 672 - 761
		minCapArray = new short[]{(short)596 ,(short)872,(short)521 ,(short)535,(short)872 ,(short)672,(short)672,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)738 ,(short)961,(short)646 ,(short)615,(short)961 ,(short)761,(short)761,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(13, resourceRoot);
		copperRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_polysteel");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Polysteel");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Polysteel Copper: CR 685 - 800, CD 928 - 1000, DR 599 - 700, HR 585 - 650, MA 928 - 1000, SR 728 - 800, UT 728 - 800
		minCapArray = new short[]{(short)685 ,(short)928 ,(short)599 ,(short)585,(short)928  ,(short)728,(short)728,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)800 ,(short)1000,(short)700 ,(short)650,(short)1000 ,(short)800,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(14, resourceRoot);
		copperRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_borocarbitic");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Borocarbitic");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Conductive Borcarbitic Copper: None CR 	CD 	DR 	HR 	MA 	OQ 	SR 	UT
		minCapArray = new short[]{(short)1   ,(short)1   ,(short)1    ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1000,(short)1000 ,(short)1000,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(15, resourceRoot);
		copperRoots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("iron_plumbum");
		resourceRoot.setResourceClass("Iron");
		resourceRoot.setResourceType("Plumbum");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Plumbum Iron: CR 1 - 131, CD 1 - 82, DR 300 - 414, HR 500 - 581, MA 1 - 98, SR 400 - 498, UT 400 - 498
		minCapArray = new short[]{(short)1  ,(short)1 ,(short)300 ,(short)500,(short)1  ,(short)400,(short)400,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)131,(short)82,(short)414 ,(short)581,(short)98 ,(short)498,(short)498,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(16, resourceRoot);
		ironRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("iron_polonium");
		resourceRoot.setResourceClass("Iron");
		resourceRoot.setResourceType("Polonium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Polonium Iron: CR 71 - 231, CD 45 - 144, DR 361 - 501, HR 544 - 644, MA 53 - 173, SR 453 - 573, UT 453 - 573
		minCapArray = new short[]{(short)71 ,(short)45 ,(short)361 ,(short)544,(short)53  ,(short)453,(short)453,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)231,(short)144,(short)501 ,(short)644,(short)173 ,(short)573,(short)573,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(17, resourceRoot);
		ironRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("iron_axidite");
		resourceRoot.setResourceClass("Iron");
		resourceRoot.setResourceType("Axidite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Axidite Iron: CR 171 - 331, CD 107 - 207, DR 449 - 589, HR 606 - 706, MA 128 - 248, SR 528 - 648, UT 528 - 648
		minCapArray = new short[]{(short)171,(short)107,(short)559 ,(short)606,(short)128 ,(short)528,(short)528,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)331,(short)207,(short)589 ,(short)706,(short)238 ,(short)648,(short)648,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(18, resourceRoot);
		ironRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("iron_bronzium");
		resourceRoot.setResourceClass("Iron");
		resourceRoot.setResourceType("Bronzium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Bronzium Iron: CR 271 - 430, CD 169 - 269, DR 536 - 676, HR 669 - 769, MA 203 - 323, SR 603 - 723, UT 603 - 723
		minCapArray = new short[]{(short)271,(short)169,(short)536 ,(short)669,(short)203 ,(short)603,(short)603,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)430,(short)269,(short)676 ,(short)769,(short)323 ,(short)723,(short)723,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(19, resourceRoot);
		ironRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("iron_colat");
		resourceRoot.setResourceClass("Iron");
		resourceRoot.setResourceType("Colat");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Colat Iron: CR 371 - 530, CD 232 - 332, DR 624 - 764, HR 731 - 831, MA 278 - 398, SR 678 - 798, UT 678 - 798
		minCapArray = new short[]{(short)371,(short)232,(short)624 ,(short)731,(short)278 ,(short)678,(short)678,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)530,(short)332,(short)764 ,(short)831,(short)398 ,(short)798,(short)798,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(20, resourceRoot);
		ironRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("iron_dolovite");
		resourceRoot.setResourceClass("Iron");
		resourceRoot.setResourceType("Dolovite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Dolovite Iron: CR 470 - 630, CD 294 - 394, DR 711 - 851, HR 794 - 894, MA 353 - 473, SR 753 - 873, UT 753 - 873
		minCapArray = new short[]{(short)470,(short)294,(short)711 ,(short)794,(short)353 ,(short)753,(short)753,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)630,(short)394,(short)851 ,(short)894,(short)473 ,(short)873,(short)873,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(21, resourceRoot);
		ironRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("iron_doonium");
		resourceRoot.setResourceClass("Iron");
		resourceRoot.setResourceType("Doonium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Doonium Iron: CR 570 - 730, CD 357 - 456, DR 799 - 939, HR 856 - 956, MA 428 - 548, SR 828 - 948, UT 828 - 948
		minCapArray = new short[]{(short)570,(short)357,(short)799 ,(short)856,(short)428 ,(short)828,(short)828,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)730,(short)456,(short)939 ,(short)956,(short)548 ,(short)948,(short)948,(short)1,(short)1,(short)1000,(short)1};		
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(22, resourceRoot);
		ironRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("iron_kammris");
		resourceRoot.setResourceClass("Iron");
		resourceRoot.setResourceType("Kammris");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Kammris Iron: CR 670 - 800, CD 419 - 500, DR 886 - 1000, HR 919 - 1000, MA 503 - 600, SR 903 - 1000, UT 903 - 1000
		minCapArray = new short[]{(short)670,(short)419,(short)886 ,(short)919 ,(short)503 ,(short)903 ,(short)903 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)800,(short)500,(short)1000,(short)1000,(short)600 ,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(23, resourceRoot);
		ironRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_rhodium");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Rhodium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Rhodium Steel: CR 1 - 105, CD 1 - 85, DR 500 - 565, HR 600 - 652, MA 1 - 53, SR 500 - 565, UT 400 - 478
		minCapArray = new short[]{(short)1,  (short)1 ,(short)500 ,(short)600 ,(short)1  ,(short)500,(short)400,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)105,(short)85,(short)565 ,(short)652 ,(short)53 ,(short)565,(short)478,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(24, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_kiirium");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Kiirium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Kiirium Steel: CR 57 - 185, CD 46 - 150, DR 535 - 615, HR 628 - 692, MA 29 - 93, SR 535 - 615, UT 442 - 538
		minCapArray = new short[]{(short)57, (short)46 ,(short)535 ,(short)628 ,(short)29 ,(short)535,(short)442,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)185,(short)150,(short)615 ,(short)692 ,(short)93 ,(short)615,(short)538,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(25, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_cubirian");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Cubirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Cubirian Steel: CR 137 - 265, CD 111 - 215, DR 585 - 665, HR 668 - 732, MA 69 - 133, SR 585 - 665, UT 502 - 598
		minCapArray = new short[]{(short)137,(short)111,(short)585 ,(short)668 ,(short)69 ,(short)585,(short)502,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)265,(short)215,(short)665 ,(short)732 ,(short)133,(short)665,(short)598,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(26, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_thoranium");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Thoranium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Thoranium Steel: CR 217 - 345, CD 176 - 280, DR 635 - 715, HR 708 - 772, MA 109 - 173, SR 635 - 715, UT 562 - 658
		minCapArray = new short[]{(short)217,(short)176,(short)635 ,(short)708 ,(short)109,(short)635,(short)562,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)345,(short)280,(short)715 ,(short)772 ,(short)173,(short)715,(short)658,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(27, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_neutronium");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Neutronium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Neutronium Steel: CR 297 - 424, CD 241 - 345, DR 685 - 765, HR 748 - 812, MA 149 - 212, SR 685 - 765, UT 622 - 718
		minCapArray = new short[]{(short)297,(short)241,(short)685 ,(short)748 ,(short)149,(short)685,(short)622,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)424,(short)345,(short)765 ,(short)812 ,(short)212,(short)765,(short)718,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(28, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_duranium");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Duranium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Duranium Steel: CR 377 - 504, CD 306 - 410, DR 735 - 815, HR 788 - 852, MA 189 - 252, SR 735 - 815, UT 682 - 778
		minCapArray = new short[]{(short)377,(short)306,(short)735 ,(short)788 ,(short)189,(short)735,(short)682,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)504,(short)410,(short)815 ,(short)852 ,(short)252,(short)815,(short)778,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(29, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_ditanium");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Ditanium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Ditanium Steel: CR 456 - 584, CD 371 - 475, DR 785 - 865, HR 828 - 892, MA 228 - 292, SR 785 - 865, UT 742 - 838
		minCapArray = new short[]{(short)456,(short)371,(short)785 ,(short)828 ,(short)228,(short)785,(short)742,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)584,(short)475,(short)865 ,(short)892 ,(short)292,(short)865,(short)838,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(30, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_quadranium");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Quadranium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Quadranium Steel: CR 536 - 664, CD 436 - 540, DR 835 - 915, HR 868 - 932, MA 268 - 332, SR 835 - 915, UT 802 - 898
		minCapArray = new short[]{(short)536,(short)436,(short)835 ,(short)868 ,(short)268,(short)835,(short)802,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)664,(short)540,(short)915 ,(short)932 ,(short)332,(short)915,(short)898,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(31, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_carbonite");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Carbonite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Carbonite Steel: CR 616 - 744, CD 501 - 605, DR 885 - 965, HR 908 - 972, MA 308 - 372, SR 885 - 965, UT 862 - 958
		minCapArray = new short[]{(short)616,(short)501,(short)885 ,(short)908 ,(short)308,(short)885,(short)862,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)744,(short)605,(short)965 ,(short)972 ,(short)372,(short)965,(short)958,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(32, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_duralloy");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Duralloy");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Duralloy Steel: CR 696 - 800, CD 566 - 650, DR 935 - 1000, HR 948 - 1000, MA 348 - 400, SR 935 - 1000, UT 922 - 1000
		minCapArray = new short[]{(short)696,(short)566,(short)935 ,(short)948 ,(short)348,(short)935 ,(short)922 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)800,(short)650,(short)1000,(short)1000,(short)400,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(33, resourceRoot);
		steelRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_carbonate_alantium");
		resourceRoot.setResourceClass("Carbonate Ore");
		resourceRoot.setResourceType("Alantium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		////"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		// Alantium Carbonate Ore: CR 300 - 414, DR 1 - 115, HR 400 - 498, MA 300 - 414, SR 1 - 115, UT 200 - 330
		minCapArray = new short[]{(short)300,(short)0,(short)1  ,(short)400,(short)300,(short)1  ,(short)200,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)414,(short)1,(short)115,(short)498,(short)414,(short)115,(short)330,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(34, resourceRoot);
		oreCarbonateRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_carbonate_barthierium");
		resourceRoot.setResourceClass("Carbonate Ore");
		resourceRoot.setResourceType("Barthierium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Barthierium Carbonate Ore: CR 361 - 501, DR 62 - 202, HR 453 - 573, MA 361 - 501, SR 62 - 202, UT 270 - 430
		minCapArray = new short[]{(short)361,(short)0,(short)62  ,(short)453,(short)361,(short)62 ,(short)270,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)501,(short)1,(short)202 ,(short)573,(short)501,(short)202,(short)430,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(35, resourceRoot);
		oreCarbonateRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_carbonate_chromite");
		resourceRoot.setResourceClass("Carbonate Ore");
		resourceRoot.setResourceType("Chromite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Chromite Carbonate Ore: CR 449 - 589, DR 150 - 289, HR 528 - 648, MA 449 - 589, SR 150 - 289, UT 370 - 530
		minCapArray = new short[]{(short)449,(short)0,(short)150,(short)528,(short)449,(short)150,(short)370,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)598,(short)1,(short)298,(short)648,(short)598,(short)289,(short)530,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(36, resourceRoot);
		oreCarbonateRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_carbonate_frasium");
		resourceRoot.setResourceClass("Carbonate Ore");
		resourceRoot.setResourceType("Frasium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Frasium Carbonate Ore: CR 536 - 676, DR 237 - 377, HR 603 - 723, MA 536 - 676, SR 237 - 377, UT 470 - 630
		minCapArray = new short[]{(short)536,(short)0,(short)237,(short)603,(short)536,(short)237,(short)470,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)676,(short)1,(short)377,(short)723,(short)676,(short)377,(short)630,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(37, resourceRoot);
		oreCarbonateRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_carbonate_lommite");
		resourceRoot.setResourceClass("Carbonate Ore");
		resourceRoot.setResourceType("Lommite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Lommite Carbonate Ore: CR 624 - 764, DR 324 - 464, HR 678 - 798, MA 624 - 764, SR 324 - 464, UT 570 - 730
		minCapArray = new short[]{(short)624,(short)0,(short)324,(short)678,(short)624,(short)324,(short)570,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)764,(short)1,(short)464,(short)798,(short)764,(short)464,(short)730,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(38, resourceRoot);
		oreCarbonateRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_carbonate_ostrine");
		resourceRoot.setResourceClass("Carbonate Ore");
		resourceRoot.setResourceType("Ostrine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Ostrine Carbonate Ore: CR 711 - 851, DR 412 - 551, HR 753 - 873, MA 711 - 851, SR 412 - 551, UT 670 - 830
		minCapArray = new short[]{(short)711,(short)0,(short)412,(short)753,(short)711,(short)412,(short)670,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)851,(short)1,(short)551,(short)873,(short)851,(short)551,(short)830,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(39, resourceRoot);
		oreCarbonateRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_carbonate_varium");
		resourceRoot.setResourceClass("Carbonate Ore");
		resourceRoot.setResourceType("Varium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Varium Carbonate Ore: CR 799 - 939, DR 499 - 639, HR 828 - 948, MA 799 - 939, SR 499 - 639, UT 770 - 930 
		minCapArray = new short[]{(short)799,(short)0,(short)499,(short)828,(short)799,(short)499,(short)770,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)939,(short)1,(short)639,(short)948,(short)939,(short)639,(short)930,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(40, resourceRoot);
		oreCarbonateRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_carbonate_zinsiam");
		resourceRoot.setResourceClass("Carbonate Ore");
		resourceRoot.setResourceType("Zinsiam");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Zinsiam Carbonate Ore: CR 886 - 1000, DR 586 - 700, HR 903 - 1000, MA 886 - 1000, SR 586 - 700, UT 870 - 1000
		minCapArray = new short[]{(short)886 ,(short)0,(short)586,(short)903 ,(short)886 ,(short)586,(short)870 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)700,(short)1000,(short)1000,(short)700,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(41, resourceRoot);
		oreCarbonateRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_siliclastic_ardanium");
		resourceRoot.setResourceClass("Siliclastic Ore");
		resourceRoot.setResourceType("Ardanium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Ardanium Siliclastic Ore: CR 300 - 452, DR 1 - 152, HR 300 - 452, MA 300 - 452, SR 1 - 131, UT 1 - 152
		minCapArray = new short[]{(short)300,(short)0,(short)1  ,(short)300 ,(short)300,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)452,(short)1,(short)152,(short)452 ,(short)452,(short)131,(short)152,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(42, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_siliclastic_cortosis");
		resourceRoot.setResourceClass("Siliclastic Ore");
		resourceRoot.setResourceType("Cortosis");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Cortosis Siliclastic Ore: CR 382 - 568, DR 83 - 269, HR 382 - 568, MA 382 - 568, SR 71 - 231, UT 83 - 269
		minCapArray = new short[]{(short)382,(short)0,(short)83 ,(short)382 ,(short)382,(short)71 ,(short)83 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)568,(short)1,(short)269,(short)568 ,(short)568,(short)231,(short)269,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(43, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_siliclastic_crism");
		resourceRoot.setResourceClass("Siliclastic Ore");
		resourceRoot.setResourceType("Crism");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Crism Siliclastic Ore: CR 498 - 685, DR 199 - 385, HR 498 - 685, MA 498 - 685, SR 171 - 330, UT 199 - 385
		minCapArray = new short[]{(short)498,(short)0,(short)199,(short)498 ,(short)498,(short)171,(short)199,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)685,(short)1,(short)385,(short)685 ,(short)685,(short)330,(short)385,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(44, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_siliclastic_malab");
		resourceRoot.setResourceClass("Siliclastic Ore");
		resourceRoot.setResourceType("Malab");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Malab Siliclastic Ore: CR 615 - 802, DR 316 - 502, HR 615 - 802, MA 615 - 802, SR 271 - 430, UT 316 - 502
		minCapArray = new short[]{(short)615,(short)0,(short)316,(short)615 ,(short)615,(short)271,(short)316,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)802,(short)1,(short)502,(short)802 ,(short)802,(short)430,(short)502,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(45, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_siliclastic_robindun");
		resourceRoot.setResourceClass("Siliclastic Ore");
		resourceRoot.setResourceType("Robindun");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Robindun Siliclastic Ore: CR 732 - 918, DR 432 - 618, HR 732 - 918, MA 732 - 918, SR 370 - 530, UT 432 - 618
		minCapArray = new short[]{(short)732,(short)0,(short)432,(short)732 ,(short)732,(short)370,(short)432,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)918,(short)1,(short)618,(short)918 ,(short)918,(short)530,(short)618,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(46, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_siliclastic_tertian");
		resourceRoot.setResourceClass("Siliclastic Ore");
		resourceRoot.setResourceType("Tertian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Tertian Siliclastic Ore: CR 848 - 1000, DR 549 - 700, HR 848 - 1000, MA 848 - 1000, SR 470 - 600, UT 549 - 700
		minCapArray = new short[]{(short)848 ,(short)0,(short)549,(short)848 ,(short)848 ,(short)470,(short)549,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)700,(short)1000,(short)1000,(short)600,(short)700,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(47, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_bene");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Bene");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Bene Extrusive Ore: CR 200 - 304, DR 300 - 391, HR 400 - 478, MA 1 - 79, SR 400 - 478, UT 400 - 478
		minCapArray = new short[]{(short)200,(short)0,(short)300,(short)400,(short)1 ,(short)400,(short)400,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)304,(short)1,(short)391,(short)478,(short)79,(short)478,(short)478,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(48, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_chronamite");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Chronamite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Chronamite Extrusive Ore: CR 256 - 384, DR 349 - 461, HR 442 - 538, MA 43 - 139, SR 442 - 538, UT 442 - 538
		minCapArray = new short[]{(short)256,(short)0,(short)349,(short)442,(short)43 ,(short)442,(short)442,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)384,(short)1,(short)461,(short)538,(short)139,(short)538,(short)538,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(49, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_ilimium");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Ilimium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Ilimium Extrusive Ore: CR 336 - 464, DR 419 - 531, HR 502 - 598, MA 103 - 199, SR 502 - 598, UT 502 - 598
		minCapArray = new short[]{(short)336,(short)0,(short)419,(short)502,(short)103,(short)502,(short)502,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)464,(short)1,(short)531,(short)598,(short)199,(short)598,(short)598,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(50, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_kalonterium");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Kalonterium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Kalonterium Extrusive Ore: CR 416 - 544, DR 489 - 601, HR 562 - 658, MA 163 - 259, SR 562 - 658, UT 562 - 658
		minCapArray = new short[]{(short)416,(short)0,(short)489,(short)562,(short)163,(short)562,(short)562,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)544,(short)1,(short)601,(short)658,(short)259,(short)658,(short)658,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(51, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_keschel");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Keschel");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Keschel Extrusive Ore: CR 496 - 624, DR 559 - 671, HR 622 - 718, MA 223 - 318, SR 622 - 718, UT 622 - 718
		minCapArray = new short[]{(short)496,(short)0,(short)559,(short)622,(short)223,(short)622,(short)622,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)624,(short)1,(short)671,(short)718,(short)318,(short)718,(short)718,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(52, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_lidium");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Lidium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Lidium Extrusive Ore: CR 576 - 704, DR 629 - 741, HR 682 - 778, MA 283 - 378, SR 682 - 778, UT 682 - 778 
		minCapArray = new short[]{(short)576,(short)0,(short)629,(short)682,(short)283,(short)682,(short)682,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)704,(short)1,(short)741,(short)778,(short)378,(short)778,(short)778,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(53, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_maranium");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Maranium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Maranium Extrusive Ore: CR 656 - 784, DR 699 - 811, HR 742 - 838, MA 342 - 438, SR 742 - 838, UT 742 - 838
		minCapArray = new short[]{(short)656,(short)0,(short)699,(short)742,(short)342,(short)742,(short)742,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)784,(short)1,(short)811,(short)838,(short)438,(short)838,(short)838,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(54, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_pholokite");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Pholokite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Pholokite Extrusive Ore: CR 736 - 864, DR 769 - 881, HR 802 - 898, MA 402 - 498, SR 802 - 898, UT 802 - 898
		minCapArray = new short[]{(short)736,(short)0,(short)769,(short)802,(short)402,(short)802,(short)802,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)864,(short)1,(short)881,(short)898,(short)498,(short)898,(short)898,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(55, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_quadrenium");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Quadrenium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Quadrenium Extrusive Ore: CR 816 - 944, DR 839 - 951, HR 862 - 958, MA 462 - 558, SR 862 - 958, UT 862 - 958
		minCapArray = new short[]{(short)816,(short)0,(short)839,(short)862,(short)462,(short)862,(short)862,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)944,(short)1,(short)951,(short)958,(short)558,(short)958,(short)958,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(56, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_vintrium");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Vintrium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Vintrium Extrusive Ore: CR 896 - 1000, DR 909 - 1000, HR 922 - 1000, MA 522 - 600, SR 922 - 1000, UT 922 - 1000
		minCapArray = new short[]{(short)896 ,(short)0,(short)909 ,(short)922 ,(short)522,(short)922 ,(short)922 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(57, resourceRoot);
		oreExtrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_berubium");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Berubium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Berubium Intrusive Ore: CR 200 - 316, DR 300 - 401, HR 700 - 743, MA 1 - 88, SR 500 - 572, UT 400 - 487
		minCapArray = new short[]{(short)200,(short)0,(short)300,(short)700,(short)1 ,(short)500,(short)400,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)316,(short)1,(short)401,(short)743,(short)88,(short)572,(short)487,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(58, resourceRoot);
		oreIntrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_chanlon");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Chanlon");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Chanlon Intrusive Ore: CR 262 - 404, DR 354 - 479, HR 723 - 777, MA 48 - 154, SR 539 - 628, UT 447 - 553
		minCapArray = new short[]{(short)262,(short)0,(short)354,(short)723,(short)48 ,(short)539,(short)447,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)404,(short)1,(short)479,(short)777,(short)154,(short)628,(short)553,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(59, resourceRoot);
		oreIntrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_corintium");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Corintium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Corintium Intrusive Ore: CR 351 - 493, DR 432 - 557, HR 757 - 810, MA 114 - 221, SR 594 - 683, UT 513 - 620
		minCapArray = new short[]{(short)351,(short)0,(short)432,(short)757,(short)114,(short)594,(short)513,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)493,(short)1,(short)557,(short)810,(short)221,(short)683,(short)620,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(60, resourceRoot);
		oreIntrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_derillium");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Derillium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Derillium Intrusive Ore: CR 440 - 582, DR 510 - 634, HR 790 - 843, MA 181 - 287, SR 650 - 739, UT 580 - 687
		minCapArray = new short[]{(short)440,(short)0,(short)510,(short)790,(short)181,(short)650,(short)580,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)582,(short)1,(short)634,(short)843,(short)287,(short)739,(short)687,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(61, resourceRoot);
		oreIntrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_oridium");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Oridium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Oridium Intrusive Ore: CR 529 - 671, DR 588 - 712, HR 823 - 877, MA 247 - 354, SR 706 - 794, UT 647 - 753
		minCapArray = new short[]{(short)529,(short)0,(short)588,(short)823,(short)247,(short)706,(short)647,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)671,(short)1,(short)712,(short)877,(short)354,(short)794,(short)753,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(62, resourceRoot);
		oreIntrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_dylinium");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Dylinium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Dylinium Intrusive Ore: CR 618 - 760, DR 666 - 790, HR 857 - 910, MA 314 - 420, SR 761 - 850, UT 713 - 820
		minCapArray = new short[]{(short)618,(short)0,(short)666,(short)857,(short)314,(short)761,(short)713,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)760,(short)1,(short)790,(short)910,(short)420,(short)850,(short)820,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(63, resourceRoot);
		oreIntrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_hollinium");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Hollinium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Hollinium Intrusive Ore: CR 707 - 849, DR 743 - 868, HR 890 - 943, MA 380 - 487, SR 817 - 906, UT 780 - 887
		minCapArray = new short[]{(short)707,(short)0,(short)743,(short)890,(short)380,(short)817,(short)780,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)849,(short)1,(short)868,(short)943,(short)487,(short)906,(short)887,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(64, resourceRoot);
		oreIntrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_ionite");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Ionite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Ionite Intrusive Ore: CR 796 - 938, DR 821 - 946, HR 923 - 977, MA 447 - 553, SR 872 - 961, UT 847 - 953
		minCapArray = new short[]{(short)796,(short)0,(short)821,(short)923,(short)447,(short)872,(short)847,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)938,(short)1,(short)946,(short)977,(short)553,(short)961,(short)953,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(65, resourceRoot);
		oreIntrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_katrium");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Katrium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Katrium Intrusive Ore: CR 884 - 1000, DR 899 - 1000, HR 957 - 1000, MA 513 - 600, SR 928 - 1000, UT 913 - 1000
		minCapArray = new short[]{(short)884 ,(short)0,(short)899 ,(short)957 ,(short)513,(short)928 ,(short)913 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(66, resourceRoot);
		oreIntrusiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_bospridium");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("Bospridium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		//Bospridium Armophous Gemstone: ER 1 - 105, CR 1 - 131, DR 1 - 131, HR 600 - 652, MA 1 - 79, SR 1 - 131, UT 1 - 131
		minCapArray = new short[]{(short)1  ,(short)0,(short)1  ,(short)600,(short)1 ,(short)1  ,(short)1  ,(short)1  ,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)131,(short)1,(short)131,(short)652,(short)79,(short)131,(short)131,(short)105,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(67, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_baradium");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("Baradium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Baradium Armophous Gemstone: ER 57 - 185, CR 71 - 231, DR 71 - 231, HR 628 - 692, MA 43 - 139, SR 71 - 231, UT 71 - 231
		minCapArray = new short[]{(short)71 ,(short)0,(short)71 ,(short)628,(short)43 ,(short)71 ,(short)71 ,(short)57 ,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)231,(short)1,(short)231,(short)692,(short)139,(short)231,(short)231,(short)185,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(68, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_regvis");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("Regvis");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Regvis Armophous Gemstone: ER 137 - 265, CR 171 - 331, DR 171 - 331, HR 668 - 732, MA 103 - 199, SR 171 - 331, UT 171 - 331
		minCapArray = new short[]{(short)171,(short)0,(short)171,(short)668,(short)103,(short)171,(short)171,(short)137,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)331,(short)1,(short)331,(short)732,(short)199,(short)331,(short)331,(short)265,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(69, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_plexite");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("Plexite");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Plexite Armophous Gemstone: ER 217 - 345, CR 271 - 431, DR 271 - 431, HR 708 - 772, MA 163 - 259, SR 271 - 431, UT 271 - 431
		minCapArray = new short[]{(short)271,(short)0,(short)271,(short)708,(short)163,(short)271,(short)271,(short)217,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)431,(short)1,(short)431,(short)772,(short)259,(short)431,(short)431,(short)345,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(70, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_rudic");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("Rudic");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Rudic Armophous Gemstone: ER 297 - 424, CR 371 - 530, DR 371 - 530, HR 748 - 812, MA 223 - 318, SR 371 - 530, UT 371 - 530
		minCapArray = new short[]{(short)371,(short)0,(short)371,(short)748,(short)223,(short)371,(short)371,(short)297,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)530,(short)1,(short)530,(short)812,(short)318,(short)530,(short)530,(short)424,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(71, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_ryll");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("Ryll");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Ryll Armophous Gemstone: ER 377 - 504, CR 471 - 630, DR 471 - 630, HR 788 - 852, MA 283 - 378, SR 471 - 630, UT 471 - 630 
		minCapArray = new short[]{(short)471,(short)0,(short)471,(short)788,(short)283,(short)471,(short)471,(short)377,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)630,(short)1,(short)630,(short)852,(short)378,(short)630,(short)630,(short)504,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(72, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_sedrellium");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("sedrellium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Sedrellium Armophous Gemstone: ER 456 - 584, CR 570 - 730, DR 570 - 730, HR 828 - 892, MA 342 - 438, SR 570 - 730, UT 570 - 730
		minCapArray = new short[]{(short)570,(short)0,(short)570,(short)828,(short)342,(short)570,(short)570,(short)456,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)730,(short)1,(short)730,(short)892,(short)438,(short)730,(short)730,(short)584,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(73, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_stygium");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("Stygium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Stygium Armophous Gemstone: ER 536 - 664, CR 670 - 830, DR 670 - 830, HR 868 - 932, MA 402 - 498, SR 670 - 830, UT 670 - 830
		minCapArray = new short[]{(short)670,(short)0,(short)670,(short)868,(short)402,(short)670,(short)670,(short)536,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)830,(short)1,(short)830,(short)932,(short)498,(short)830,(short)830,(short)664,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(74, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_vendusii");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("Vendusii");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Vendusii Armophous Gemstone: ER 616 - 744, CR 770 - 930, DR 770 - 930, HR 908 - 972, MA 462 - 558, SR 770 - 930, UT 770 - 930
		minCapArray = new short[]{(short)770,(short)0,(short)770,(short)908,(short)462,(short)770,(short)770,(short)616,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)930,(short)1,(short)930,(short)972,(short)558,(short)930,(short)930,(short)744,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(75, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_baltaran");
		resourceRoot.setResourceClass("Amorphous Gemstone");
		resourceRoot.setResourceType("Baltaran");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Bal'ta'ran Armophous Gemstone: ER 696 - 800, CR 870 - 1000, DR 870 - 1000, HR 948 - 1000, MA 522 - 600, SR 870 - 1000, UT 870 - 1000
		minCapArray = new short[]{(short)870 ,(short)0,(short)870 ,(short)948 ,(short)522,(short)870 ,(short)870 ,(short)696,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)800,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(76, resourceRoot);
		amorphousGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_byrothsis");
		resourceRoot.setResourceClass("Crystalline Gemstone");
		resourceRoot.setResourceType("Byrothsis");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		//Byrothsis Crystalline Gemstone: ER 500 - 581, CR 1 - 163, DR 1 - 163, HR 700 - 749, MA 1 - 66, SR 300 - 414, UT 300 - 414
		minCapArray = new short[]{(short)1  ,(short)0,(short)1  ,(short)700 ,(short)1 ,(short)300,(short)300,(short)500,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)163,(short)1,(short)163,(short)749 ,(short)66,(short)414,(short)414,(short)581,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(77, resourceRoot);
		crystallineGemstoneRoots.addElement(resourceRoot);
	
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_gallinorian");
		resourceRoot.setResourceClass("Crystalline Gemstone");
		resourceRoot.setResourceType("Gallinorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Gallinorian Rainbow Crystalline Gemstone: ER 544 - 644, CR 88 - 288, DR 88 - 288, HR 726 - 786, MA 36 - 116, SR 361 - 501, UT 361 - 501
		minCapArray = new short[]{(short)88 ,(short)0,(short)88 ,(short)726 ,(short)36 ,(short)361,(short)361,(short)544,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)288,(short)1,(short)288,(short)786 ,(short)116,(short)501,(short)501,(short)644,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(78, resourceRoot);
		crystallineGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_green_diamond");
		resourceRoot.setResourceClass("Crystalline Gemstone");
		resourceRoot.setResourceType("Green Diamond");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Green Diamond Crystalline Gemstone: ER 606 - 706, CR 213 - 413, DR 213 - 413, HR 764 - 824, MA 86 - 166, SR 449 - 589, UT 449 - 589
		minCapArray = new short[]{(short)213,(short)0,(short)213,(short)764 ,(short)86 ,(short)449,(short)449,(short)606,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)413,(short)1,(short)413,(short)824 ,(short)166,(short)589,(short)589,(short)706,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(79, resourceRoot);
		crystallineGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_laboi_mineral_crystal");
		resourceRoot.setResourceClass("Crystalline Gemstone");
		resourceRoot.setResourceType("Laboi Mineral Crystal");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Laboi Mineral Crystal Crystalline Gemstone: ER 856 - 956, CR 713 - 913, DR 713 - 913, HR 914 - 974, MA 285 - 365, SR 799 - 939, UT 799 - 939
		minCapArray = new short[]{(short)713,(short)0,(short)713,(short)914 ,(short)285,(short)799,(short)799,(short)856,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)913,(short)1,(short)913,(short)974 ,(short)365,(short)939,(short)939,(short)956,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(80, resourceRoot);
		crystallineGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_kerol_firegem");
		resourceRoot.setResourceClass("Crystalline Gemstone");
		resourceRoot.setResourceType("Kerol Fire-Gem");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Kerol Fire-Gem Crystalline Gemstone: ER 669 - 769, CR 338 - 538, DR 338 - 538, HR 801 - 861, MA 136 - 215, SR 536 - 676, UT 536 - 676
		minCapArray = new short[]{(short)338,(short)0,(short)338,(short)801 ,(short)136,(short)536,(short)536,(short)669,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)538,(short)1,(short)538,(short)861 ,(short)215,(short)676,(short)676,(short)769,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(81, resourceRoot);
		crystallineGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_seafah_jewel");
		resourceRoot.setResourceClass("Crystalline Gemstone");
		resourceRoot.setResourceType("Seafah Jewel");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Seafah Jewel Crystalline Gemstone: ER 731 - 831, CR 463 - 663, DR 463 - 663, HR 839 - 899, MA 186 - 265, SR 624 - 764, UT 624 - 764
		minCapArray = new short[]{(short)463,(short)0,(short)463,(short)839 ,(short)186,(short)624,(short)624,(short)731,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)663,(short)1,(short)663,(short)899 ,(short)265,(short)764,(short)764,(short)831,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(82, resourceRoot);
		crystallineGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_sormahil_firegem");
		resourceRoot.setResourceClass("Crystalline Gemstone");
		resourceRoot.setResourceType("Sormahil Fire-Gem");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Sormahil Fire Gem Crystalline Gemstone: ER 794 - 894, CR 588 - 788, DR 588 - 788, HR 876 - 936, MA 235 - 315, SR 711 - 851, UT 711 - 851
		minCapArray = new short[]{(short)588,(short)0,(short)588,(short)876 ,(short)235,(short)711,(short)711,(short)794,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)788,(short)1,(short)788,(short)936 ,(short)315,(short)851,(short)851,(short)894,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(83, resourceRoot);
		crystallineGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_laboi_mineral");
		resourceRoot.setResourceClass("Crystalline Gemstone");
		resourceRoot.setResourceType("Laboi mineral");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Laboi Mineral Crystal Crystalline Gemstone: ER 856 - 956, CR 713 - 913, DR 713 - 913, HR 914 - 974, MA 285 - 365, SR 799 - 939, UT 799 - 939
		minCapArray = new short[]{(short)713,(short)0,(short)713,(short)914 ,(short)285,(short)799,(short)799,(short)856,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)913,(short)1,(short)913,(short)974 ,(short)365,(short)939,(short)939,(short)956,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(84, resourceRoot);
		crystallineGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_vertex");
		resourceRoot.setResourceClass("Crystalline Gemstone");
		resourceRoot.setResourceType("Vertex");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		// Vertex Crystalline Gemstone: ER 919 - 1000, CR 838 - 1000, DR 838 - 1000, HR 951 - 1000, MA 335 - 400, SR 886 - 1000, UT 886 - 1000
		minCapArray = new short[]{(short)838 ,(short)0,(short)838 ,(short)951 ,(short)335,(short)886 ,(short)886 ,(short)919 ,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)400,(short)1000,(short)1000,(short)1000,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(85, resourceRoot);
		crystallineGemstoneRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_type1");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("Class 1");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		//Class 1 Radioactive: DR 400 - 474, PE 500 - 593
		minCapArray = new short[]{(short)0,(short)0,(short)400,(short)0,(short)0,(short)0,(short)0,(short)0,(short)500,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)474,(short)1,(short)1,(short)1,(short)1,(short)1,(short)593,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(86, resourceRoot);
		radioactiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_type2");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("Class 2");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE);
		//Class 2 Radioactive: DR 440 - 531, PE 550 - 664
		minCapArray = new short[]{(short)0,(short)0,(short)400,(short)0,(short)0,(short)0,(short)0,(short)0,(short)550,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)531,(short)1,(short)1,(short)1,(short)1,(short)1,(short)664,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(87, resourceRoot);
		radioactiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_type3");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("Class 3");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE);
		//Class 3 Radioactive: DR 497 - 589, PE 621 - 736
		minCapArray = new short[]{(short)0,(short)0,(short)497,(short)0,(short)0,(short)0,(short)0,(short)0,(short)621,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)589,(short)1,(short)1,(short)1,(short)1,(short)1,(short)736,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(88, resourceRoot);
		radioactiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_type4");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("Class 4");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE);
		//Class 4 Radioactive: DR 554 - 646, PE 693 - 807
		minCapArray = new short[]{(short)0,(short)0,(short)554,(short)0,(short)0,(short)0,(short)0,(short)0,(short)693,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)646,(short)1,(short)1,(short)1,(short)1,(short)1,(short)807,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(89, resourceRoot);
		radioactiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_type5");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("Class 5");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE);
		//Class 5 Radioactive: DR 611 - 703, PE 764 - 879
		minCapArray = new short[]{(short)0,(short)0,(short)611,(short)0,(short)0,(short)0,(short)0,(short)0,(short)764,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)703,(short)1,(short)1,(short)1,(short)1,(short)1,(short)879,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(90, resourceRoot);
		radioactiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_type6");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("Class 6");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE);
		//Class 6 Radioactive: DR 669 - 760, PE 836 - 950
		minCapArray = new short[]{(short)0,(short)0,(short)669,(short)0,(short)0,(short)0,(short)0,(short)0,(short)836,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)760,(short)1,(short)1,(short)1,(short)1,(short)1,(short)950,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(91, resourceRoot);
		radioactiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_type7");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("Class 7");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE);
		//Class 7 Radioactive: DR 726 - 800, PE 907 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)726,(short)0,(short)0,(short)0,(short)0,(short)0,(short)907 ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(92, resourceRoot);
		radioactiveRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("metal_ferrous_unknown");
		resourceRoot.setResourceClass("Ferrous Metal");
		resourceRoot.setResourceType("Unknown");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Unknown Ferrous: CR 1 - 800, CD 1 - 600, DR 300 - 1000, HR 300 - 1000, MA 1 - 600, SR 400 - 1000, UT 400 - 1000
		minCapArray = new short[]{(short)1  ,(short)1  ,(short)300 ,(short)300 ,(short)1  ,(short)400 ,(short)400 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)800,(short)650,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(93, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("metal_nonferrous_unknown");
		resourceRoot.setResourceClass("Non-Ferrous");
		resourceRoot.setResourceType("Unknown");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Unknown Non-Ferrous: CR 1 - 800, CD 300 - 1000, DR 1 - 700, HR 200 - 800, MA 300 - 1000, SR 300 - 900, UT 300 - 1000
		minCapArray = new short[]{(short)1  ,(short)300 ,(short)1  ,(short)200,(short)300 ,(short)300,(short)300 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)800,(short)1000,(short)700,(short)800,(short)1000,(short)900,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(94, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_sedimentary_unknown");
		resourceRoot.setResourceClass("Sedimentary Ore");
		resourceRoot.setResourceType("Unknown");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		//Unknown Sedimentary Ore: CR 300 - 1000, DR 1 - 700, HR 300 - 1000, MA 300 - 1000, SR 1 - 700
		minCapArray = new short[]{(short)300 ,(short)0,(short)1  ,(short)300 ,(short)300 ,(short)1  ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)700,(short)1000,(short)1000,(short)700,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(95, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_igneous_unknown");
		resourceRoot.setResourceClass("Igneous Ore");
		resourceRoot.setResourceType("Unknown");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Unknown Igneous Ore: CR 200 - 1000, DR 300 - 1000, HR 400 - 1000, MA 1 - 800, SR 400 - 1000, UT 400 - 1000
		minCapArray = new short[]{(short)200 ,(short)0,(short)300 ,(short)400 ,(short)1  ,(short)400 ,(short)400 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)800,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(96, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gemstone_unknown");
		resourceRoot.setResourceClass("Gemstone");
		resourceRoot.setResourceType("Unknown");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		//Unknown Gemstone: HR 600 - 1000, MA 1 - 600
		minCapArray = new short[]{(short)1   ,(short)0,(short)1   ,(short)600 ,(short)1  ,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)1000,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(97, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_unknown");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("Unknown");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		// Unknown Radioactive: DR 1 - 800
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(98, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_solid_type1");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 1 Solid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);		
		//Class 1 Solid Petrochemical: DR 1 - 149, PE 300 - 430
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)149,(short)1,(short)1,(short)1,(short)1,(short)1,(short)430,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(99, resourceRoot);
		petrochemicalSolidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_solid_type2");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 2 Solid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		//Class 2 Solid Petrochemical: DR 81 - 264, PE 370 - 530
		minCapArray = new short[]{(short)0,(short)0,(short)81 ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)370,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)264,(short)1,(short)1,(short)1,(short)1,(short)1,(short)530,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(100, resourceRoot);
		petrochemicalSolidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_solid_type3");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 3 Solid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		//Class 3 Solid Petrochemical: DR 195 - 378, PE 470 - 630
		minCapArray = new short[]{(short)0,(short)0,(short)195,(short)0,(short)0,(short)0,(short)0,(short)0,(short)470,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)378,(short)1,(short)1,(short)1,(short)1,(short)1,(short)630,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(101, resourceRoot);
		petrochemicalSolidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_solid_type4");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 4 Solid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		//Class 4 Solid Petrochemical: DR 309 - 492, PE 570 - 730
		minCapArray = new short[]{(short)0,(short)0,(short)309,(short)0,(short)0,(short)0,(short)0,(short)0,(short)570,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)492,(short)1,(short)1,(short)1,(short)1,(short)1,(short)730,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(102, resourceRoot);
		petrochemicalSolidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_solid_type5");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 5 Solid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		//Class 5 Solid Petrochemical: DR 423 - 606, PE 670 - 830
		minCapArray = new short[]{(short)0,(short)0,(short)423,(short)0,(short)0,(short)0,(short)0,(short)0,(short)670,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)606,(short)1,(short)1,(short)1,(short)1,(short)1,(short)830,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(103, resourceRoot);
		petrochemicalSolidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_solid_type6");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 6 Solid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		//Class 6 Solid Petrochemical: DR 537 - 720, PE 770 - 930
		minCapArray = new short[]{(short)0,(short)0,(short)537,(short)0,(short)0,(short)0,(short)0,(short)0,(short)770,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)720,(short)1,(short)1,(short)1,(short)1,(short)1,(short)930,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(104, resourceRoot);
		petrochemicalSolidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_solid_type7");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 7 Solid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		//Class 7 Solid Petrochemical: DR 652 - 800, PE 870 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)652,(short)0,(short)0,(short)0,(short)0,(short)0,(short)870 ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(105, resourceRoot);
		petrochemicalSolidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_solid_unknown");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Uknown Solid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		// DR,PE,OQ 1-1000
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(106, resourceRoot);
		petrochemicalSolidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_liquid_type1");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 1 Liquid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		//Class 1 Liquid Petrochemical: DR 1 - 600
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(107, resourceRoot);
		petrochemicalLiquidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_liquid_type2");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 2 Liquid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(108, resourceRoot);
		petrochemicalLiquidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_liquid_type3");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 3 Liquid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(109, resourceRoot);
		petrochemicalLiquidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_liquid_type4");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 4 Liquid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(110, resourceRoot);
		petrochemicalLiquidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_liquid_type5");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 5 Liquid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(111, resourceRoot);
		petrochemicalLiquidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_liquid_type6");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 6 Liquid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(112, resourceRoot);
		petrochemicalLiquidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_liquid_type7");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Class 7 Liquid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(113, resourceRoot);
		petrochemicalLiquidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_liquid_unknown");
		resourceRoot.setResourceClass("Petro Fuel");
		resourceRoot.setResourceType("Unknown Liquid");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(114, resourceRoot);
		petrochemicalLiquidRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_inert_lubricating_oil");
		resourceRoot.setResourceClass("Oil");
		resourceRoot.setResourceType("Lubricating");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(115, resourceRoot);
		lubricatingOilRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_inert_polymer");
		resourceRoot.setResourceClass("Polymer");
		resourceRoot.setResourceType("Polymer");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(116, resourceRoot);
		polymerRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_hydron3");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Hydron3");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Hydron-3 Inert Gas: DR 1 - 81
		minCapArray = new short[]{(short)0,(short)0,(short)1 ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)81,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(117, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_malium");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Malium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Malium Inert Gas: DR 44 - 142 
		minCapArray = new short[]{(short)0,(short)0,(short)44,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1    ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)142,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(118, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_bilal");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Bilal");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Bilal Inert Gas: DR 105 - 204 
		minCapArray = new short[]{(short)0,(short)0,(short)105,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)204,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(119, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_corthel");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Corthel");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Corthel Inert Gas: DR 167 - 265
		minCapArray = new short[]{(short)0,(short)0,(short)167,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)265,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(120, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_culsion");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Culsion");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Culsion Inert Gas: DR 228 - 327
		minCapArray = new short[]{(short)0,(short)0,(short)228,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)327,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(121, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_dioxis");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Dioxis");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Dioxis Inert Gas: DR 290 - 388 
		minCapArray = new short[]{(short)0,(short)0,(short)290,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)388,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(122, resourceRoot);

		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_hurlothrombic");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Hurlothrombic");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Hurlothrombic Inert Gas: DR 351 - 450
		minCapArray = new short[]{(short)0,(short)0,(short)351,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)450,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(123, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_kaylon");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Kaylon");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Kaylon Inert Gas: DR 413 - 511
		minCapArray = new short[]{(short)0,(short)0,(short)413,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)511,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(124, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_korfaise");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Korfaise");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Korfaise Inert Gas: DR 474 - 573
		minCapArray = new short[]{(short)0,(short)0,(short)474,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)573,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(125, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_methanagen");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Methanagen");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Methanagen Inert Gas: DR 536 - 634
		minCapArray = new short[]{(short)0,(short)0,(short)536,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)634,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(126, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_mirth");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Mirth");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Mirth Inert Gas: DR 597 - 696
		minCapArray = new short[]{(short)0,(short)0,(short)597,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)696,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(127, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_obah");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Obah");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Obah Inert Gas: DR 659 - 757 
		minCapArray = new short[]{(short)0,(short)0,(short)659,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)757,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(128, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_rethin");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Rethin");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Rethin Inert Gas: DR 720 - 800 
		minCapArray = new short[]{(short)0,(short)0,(short)720,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(129, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_inert_unknown");
		resourceRoot.setResourceClass("Inert Gas");
		resourceRoot.setResourceType("Unknown");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(130, resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_eleton");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Eleton");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Eleton Reactive Gas: DR 1? - 400?
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)400,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(131, resourceRoot);
		reactiveGasRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_irolunn");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Irolunn");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Irolunn Reactive Gas: DR 100? - 600?
		minCapArray = new short[]{(short)0,(short)0,(short)100,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(132, resourceRoot);
		reactiveGasRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_methane");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Methane");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Methane Reactive Gas: DR 200? - 700?
		minCapArray = new short[]{(short)0,(short)0,(short)200,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(133, resourceRoot);
		reactiveGasRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_orveth");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Orveth");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Orveth Reactive Gas: DR 300? - 800?
		minCapArray = new short[]{(short)0,(short)0,(short)300,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(134, resourceRoot);
		reactiveGasRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_sig");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Sig");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		//Sig Reactive Gas: DR 300? - 900?
		minCapArray = new short[]{(short)0,(short)0,(short)300,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)900,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(135, resourceRoot);
		reactiveGasRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_skevon");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Skevon");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Skevon Reactive Gas: DR 400? - 900?
		minCapArray = new short[]{(short)0,(short)0,(short)400,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)900,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(136, resourceRoot);
		reactiveGasRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_tolium");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Tolium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		//Tolium Reactive Gas: DR 500? - 1000?
		minCapArray = new short[]{(short)0,(short)0,(short)500 ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(137, resourceRoot);
		reactiveGasRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_unknown");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Unknown");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);		
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(138, resourceRoot);
		reactiveGasRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_corellia");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(139, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_dantooine");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(140, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_dathomir");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Dathomir");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(141, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_endor");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(142, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_lok");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(143, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_naboo");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(144, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_rori");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(145, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_talus");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(146, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_tatooine");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(147, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_yavin4");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(148, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_corellia");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(149, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_dantooine");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(150, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_dathomir");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Dathomir");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(151, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_endor");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(152, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_lok");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(153, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_naboo");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(154, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_rori");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(155, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_talus");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(156, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_tatooine");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(157, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_yavin4");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(158, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_corellia");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(159, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_dantooine");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Dantoine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(160, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_dathomir");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(161, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_endor");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(162, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_lok");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(163, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_naboo");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(164, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_rori");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(165, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_talus");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(166, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_tatooine");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(167, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_yavin4");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(168, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_corellia");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);		
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		// not sure about these
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(169, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_dantooine");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Dantoine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(170, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_dathomir");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(171, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_endor");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(172, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_lok");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(173, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_naboo");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(174, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_rori");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(175, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_talus");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(176, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_tatooine");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(177, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_yavin4");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(178, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_corellia");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(179, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_dantooine");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Dantoine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(180, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_dathomir");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(181, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_endor");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(182, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_lok");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(183, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_naboo");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(184, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_rori");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(185, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_talus");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(186, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_tatooine");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(187, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_yavin4");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(188, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_corellia");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(189, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_dantooine");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Dantoine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(190, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_dathomir");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(191, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_endor");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(192, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_lok");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(193, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_naboo");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(194, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_rori");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(195, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_talus");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(196, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_tatooine");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(197, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_yavin4");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(198, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_corellia");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		//Conifer Wood: DR 1 - 600, MA 600 - 1000, SR 100 - 400, UT 1 - 300
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(199, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_dantooine");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(200, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_dathomir");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(201, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_endor");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(202, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_lok");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(203, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_naboo");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(204, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_rori");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(205, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_talus");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(206, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_tatooine");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(207, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_yavin4");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(208, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_corellia");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		//Evergreen Wood: DR 1 - 500, MA 800 - 1000, SR 1 - 400, UT 1 - 300
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(209, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_dantooine");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(210, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_dathomir");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(211, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_endor");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(212, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_lok");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(213, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_naboo");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(214, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_rori");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(215, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_talus");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(216, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_tatooine");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(217, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_yavin4");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(218, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_corellia");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		//Decidious Wood: DR 1 - 800, MA 400 - 800, SR 300 - 700, UT 1 - 800
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(219, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_dantooine");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(220, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_dathomir");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(221, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_endor");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(222, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_lok");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(223, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_naboo");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(224, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_rori");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(225, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_talus");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(226, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_tatooine");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(227, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_yavin4");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(228, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_corellia");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		//DR 	FL 	PE 	OQ
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(229, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_dantooine");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(230, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_dathomir");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(231, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_endor");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(232, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_lok");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(233, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_naboo");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(234, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_rori");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(235, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_talus");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(236, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_tatooine");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(237, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_yavin4");
		resourceRoot.setResourceClass("Fruit");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(238, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_corellia");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		//Flowers: PE 1 - 700
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(239, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_dantooine");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(240, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_dathomir");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(241, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_endor");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(242, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_lok");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(243, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_naboo");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(244, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_rori");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(245, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_talus");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(246, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_tatooine");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(247, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_yavin4");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(248, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_corellia");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		//Domesticated Corn: FL 1 - 700, PE 300 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(249, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_dantooine");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(250, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_dathomir");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(251, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_endor");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(252, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_lok");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(253, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_naboo");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(254, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_rori");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(255, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_talus");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(256, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_tatooine");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(257, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_yavin4");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(258, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_corellia");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		//Domesticated Oats: FL 1 - 700, PE 300 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(259, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_dantooine");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(260, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_dathomir");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(261, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_endor");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(262, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_lok");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(263, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_naboo");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(264, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_rori");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(265, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_talus");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(266, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_tatooine");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(267, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_yavin4");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(268, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_corellia");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(269, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_dantooine");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(270, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_dathomir");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(271, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_endor");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(272, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_lok");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(273, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_naboo");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(274, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_rori");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(275, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_talus");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(276, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_tatooine");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(277, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_yavin4");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(278, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_corellia");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(279, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_dantooine");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(280, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_dathomir");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(281, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_endor");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(282, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_lok");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(283, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_naboo");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(284, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_rori");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(285, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_talus");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(286, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_tatooine");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(287, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_yavin4");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(288, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_corellia");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		//Wild Corn: FL 300 - 1000, PE 1 - 700
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(289, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_dantooine");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(290, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_dathomir");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(291, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_endor");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(292, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_lok");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(293, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_naboo");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(294, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);

	}
	//checked until here
	public void createCollections2(){
		
		ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_rori");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(295, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_talus");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(296, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_tatooine");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(297, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_yavin4");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(298, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_corellia");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(299, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_dantooine");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(300, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_dathomir");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(301, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_endor");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(302, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_lok");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(303, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_naboo");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(304, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_rori");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(305, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_talus");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(306, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_tatooine");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(307, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_yavin4");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(308, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_corellia");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(309, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_dantooine");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(310, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_dathomir");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(311, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_endor");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(312, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_lok");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(313, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_naboo");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(314, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_rori");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(315, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_talus");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(316, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_tatooine");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(317, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_yavin4");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(318, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_corellia");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(319, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_dantooine");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(320, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_dathomir");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(321, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_endor");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(322, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_lok");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(323, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_naboo");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(324, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_rori");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(325, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_talus");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(326, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_tatooine");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(327, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_yavin4");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(328, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_corellia");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		// DR 	FL 	PE 	OQ
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(329, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_dantooine");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(330, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_dathomir");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(331, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_endor");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(332, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_lok");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(333, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_naboo");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(334, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_rori");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(335, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_talus");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(336, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_tatooine");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(337, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_yavin4");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(338, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_corellia");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(339, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_dantooine");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(340, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_dathomir");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(341, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_endor");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(342, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_lok");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(343, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_naboo");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(344, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_rori");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(345, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_talus");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(346, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_tatooine");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(347, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_yavin4");
		resourceRoot.setResourceClass("Berry Fruit");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(348, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_corellia");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(349, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_dantooine");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(350, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_dathomir");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(351, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_endor");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(352, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_lok");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(353, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_naboo");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(354, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_rori");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(355, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_talus");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(356, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_tatooine");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(357, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_yavin4");
		resourceRoot.setResourceClass("Vegetable Fungus");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(358, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_corellia");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(359, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_dantooine");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(360, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_dathomir");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(361, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_endor");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(362, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_lok");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(363, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_naboo");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(364, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_rori");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(365, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_talus");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(366, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_tatooine");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(367, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_yavin4");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(368, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_corellia");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(369, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_dantooine");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(370, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_dathomir");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(371, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_endor");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(372, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_lok");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(373, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_naboo");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(374, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_rori");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(375, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_talus");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(376, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_tatooine");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(377, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_yavin4");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(378, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_corellia");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		//Animal Bone: DR 300 - 1000, MA 1 - 500, SR 400 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(379, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_dantooine");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(380, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_dathomir");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(381, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_endor");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(382, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_lok");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(383, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_naboo");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(384, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_rori");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(385, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_talus");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(386, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_tatooine");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(387, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_yavin4");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(388, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_corellia");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		//"Cold Res","Cond","Decay Res","Heat Res","Malle","Shock Res","Unit Tough","Entangle Res","Pot E","OQ","Flavor"
		// Avian Bone: DR 1 - 700, MA 1 - 600, SR 100 - 500, UT 1 - 500
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(389, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_dantooine");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(390, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_dathomir");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(391, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_endor");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(392, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_lok");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(393, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_naboo");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(394, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_rori");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(395, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_talus");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(396, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_tatooine");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(397, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_yavin4");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(398, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_corellia");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);		
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		//Horns: DR 200 - 1000, MA 1 - 500, SR 300 - 700, UT 1 - 500
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(399, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_dantooine");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(400, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_dathomir");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(401, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_endor");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(402, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_lok");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(403, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_naboo");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(404, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_rori");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(405, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_talus");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(406, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_tatooine");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(407, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_yavin4");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(408, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_corellia");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(409, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_dantooine");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(410, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_dathomir");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(411, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_endor");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(412, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_lok");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(413, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_naboo");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(414, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_rori");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(415, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_talus");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(416, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_tatooine");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(417, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_yavin4");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(418, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_corellia");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(419, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_dantooine");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(420, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_dathomir");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(421, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_endor");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(422, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_lok");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(423, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_naboo");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(424, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_rori");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(425, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_talus");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(426, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_tatooine");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(427, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_yavin4");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(428, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_corellia");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(429, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_dantooine");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(430, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_dathomir");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(431, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_endor");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(432, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_lok");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(433, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_naboo");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(434, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_rori");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(435, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_talus");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(436, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_tatooine");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(437, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_yavin4");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(438, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_corellia");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(439, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_dantooine");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(440, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_dathomir");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(441, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_endor");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(442, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_lok");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(443, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_naboo");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(444, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_rori");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(445, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_talus");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(446, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_tatooine");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(447, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_yavin4");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(448, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_corellia");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		// Avian Meat: FL 1 - 700, PE 300 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(449, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_dantooine");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(450, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_dathomir");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(451, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_endor");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(452, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_lok");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(453, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_naboo");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(454, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_rori");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(455, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_talus");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(456, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_tatooine");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(457, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_yavin4");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(458, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_corellia");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		// Carnivore Meat: FL 300 - 1000, PE 300 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(459, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_dantooine");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(460, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_dathomir");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(461, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_endor");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(462, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_lok");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(463, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_naboo");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(464, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_rori");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(465, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_talus");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(466, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_tatooine");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(467, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_yavin4");
		resourceRoot.setResourceClass("Canivore Meat");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(468, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_corellia");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		//Domesticated Meat: FL 300 - 1000, PE 1 - 700
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(469, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_dantooine");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(470, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_dathomir");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(471, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_endor");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(472, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_lok");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(473, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_naboo");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(474, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_rori");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(475, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_talus");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(476, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_tatooine");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(477, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_yavin4");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(478, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_corellia");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		// Herbivore Meat: FL 1 - 700, PE 1 - 700
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(479, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_dantooine");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(480, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_dathomir");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(481, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_endor");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(482, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_lok");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(483, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_naboo");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(484, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_rori");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(485, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_talus");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(486, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_tatooine");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(487, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_yavin4");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(488, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_corellia");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		//Insect Meat: PE 300 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(489, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_dantooine");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(490, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_dathomir");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(491, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_endor");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(492, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_lok");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(493, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_naboo");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(494, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_rori");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(495, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_talus");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(496, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_tatooine");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(497, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_yavin4");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(498, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_corellia");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		// Wild Meat: FL 1 - 700, PE 300 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(499, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_dantooine");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(500, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_dathomir");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(501, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_endor");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(502, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_lok");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(503, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_naboo");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(504, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_rori");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(505, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_talus");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(506, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_tatooine");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(507, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_yavin4");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(508, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_corellia");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		// Domesticated Milk: FL 1 - 700, PE 300 - 1000
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(509, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_dantooine");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(510, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_dathomir");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(511, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_endor");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(512, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_lok");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(513, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_naboo");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(514, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_rori");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(515, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_talus");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(516, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_tatooine");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(517, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_yavin4");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(518, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_corellia");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		// Wild Milk: PE 1 - 700
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(519, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_dantooine");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(520, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_dathomir");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(521, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_endor");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(522, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_lok");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(523, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_naboo");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(524, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_rori");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(525, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_talus");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(526, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_tatooine");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(527, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_yavin4");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(528, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_corellia");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(529, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_dantooine");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(530, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_dathomir");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(531, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_endor");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(532, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_lok");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(533, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_naboo");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(534, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_rori");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(535, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_talus");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(536, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_tatooine");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(537, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_yavin4");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(538, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_corellia");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Corellian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(539, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_dantooine");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Dantooine"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(540, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_dathomir");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Dathomirian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(541, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_endor");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Endorian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(542, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_lok");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Lokian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(543, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_naboo");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Nabooian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(544, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_rori");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Rori"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(545, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_talus");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Talusian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(546, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_tatooine");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Tatooinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(547, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_yavin4");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Yavinian"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(548, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_corellia");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Corellia"); 
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(549, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_dantooine");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(550, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_dathomir");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(551, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_endor");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(552, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_lok");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(553, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_naboo");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(554, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_rori");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(555, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_talus");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(556, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_tatooine");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(557, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_yavin4");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(558, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_corellia");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(559, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_dantooine");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(560, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_dathomir");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(561, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_endor");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(562, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_lok");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(563, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_naboo");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(564, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_rori");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(565, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_talus");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(566, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_tatooine");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(567, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_yavin4");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(568, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_corellia");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(569, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_dantooine");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(570, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_dathomir");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(571, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_endor");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(572, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_lok");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(573, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_naboo");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(574, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_rori");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(575, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_talus");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(576, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_tatooine");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(577, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_yavin4");
		resourceRoot.setResourceClass("Egg");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(578, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
	}
	// checked
	public void createCollections3(){
		ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_corellia");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Corellian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		// DR 	MA 	OQ 	SR 	UT
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(579, resourceRoot);
		corelliaPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_dantooine");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Dantooine");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(580, resourceRoot);
		dantooinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_dathomir");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Dathomirian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(581, resourceRoot);
		dathomirPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_endor");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Endorian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(582, resourceRoot);
		endorPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_lok");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Lokian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(583, resourceRoot);
		lokPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_naboo");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Nabooian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(584, resourceRoot);
		nabooPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_rori");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Rori");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(585, resourceRoot);
		roriPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_talus");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Talusian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(586, resourceRoot);
		talusPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_tatooine");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Tatooinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(587, resourceRoot);
		tattoinePool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_yavin4");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Yavinian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(588, resourceRoot);
		yavinPool4Roots.addElement(resourceRoot);
		
		// Kashyyyk resources
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_kashyyyk");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(589, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_kashyyyk");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(590, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_tidal_kashyyyk");
		resourceRoot.setResourceClass("Tidal Renewable Energy");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_TIDAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(591, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_kashyyyk");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(592, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_kashyyyk");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(593, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_kashyyyk");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(594, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_kashyyyk");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(595, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_kashyyyk");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(596, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_kashyyyk");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(597, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_reptilian_kashyyyk");
		resourceRoot.setResourceClass("Reptilian Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(598, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_kashyyyk");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(599, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_kashyyyk");
		resourceRoot.setResourceClass("Egg Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(600, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_kashyyyk");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(601, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_kashyyyk");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(602, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_kashyyyk");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(603, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);

		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_kashyyyk");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(604, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_kashyyyk");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(605, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_kashyyyk");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(606, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_kashyyyk");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(607, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_kashyyyk");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(608, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_kashyyyk");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(609, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_kashyyyk");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(610, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_kashyyyk");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(611, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_kashyyyk");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(612, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_kashyyyk");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(613, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_kashyyyk");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(614, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_kashyyyk");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(615, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_kashyyyk");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(616, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_kashyyyk");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(617, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_kashyyyk");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(618, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_kashyyyk");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(619, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_kashyyyk");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(620, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_kashyyyk");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(621, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_kashyyyk");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(622, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_kashyyyk");
		resourceRoot.setResourceClass("Vegetable Fungi");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(623, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_kashyyyk");
		resourceRoot.setResourceClass("Vegetable Fruits");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(624, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_kashyyyk");
		resourceRoot.setResourceClass("Vegetable Berries");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(625, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_kashyyyk");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FRUIT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(626, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_kashyyyk");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(627, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_kashyyyk");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(628, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_kashyyyk");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(629, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_wind_kashyyyk");
		resourceRoot.setResourceClass("Wind Renewable Energy");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WIND);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)400,(short)500,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(630, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_unlimited_solar_kashyyyk");
		resourceRoot.setResourceClass("Solar Renewable Energy");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_SOLAR);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200,(short)500,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)600,(short)500,(short)1};
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(631, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_hydron3_kashyyyk");
		resourceRoot.setResourceClass("Hydron-3 Renewable Energy");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HYDRON);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(632, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("energy_renewable_site_limited_geothermal_kashyyyk");
		resourceRoot.setResourceClass("Geothermal Renewable Energy");
		resourceRoot.setResourceType("Kashyyykian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GEOTHERM);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)200 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(633, resourceRoot);
		kashyyykPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_domesticated_mustafar");
		resourceRoot.setResourceClass("Domesticated Milk");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(634, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("milk_wild_mustafar");
		resourceRoot.setResourceClass("Wild Milk");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MILK);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BOTTLE_MILK);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(635, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_domesticated_mustafar");
		resourceRoot.setResourceClass("Domesticated Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(636, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_wild_mustafar");
		resourceRoot.setResourceClass("Wild Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(637, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_herbivore_mustafar");
		resourceRoot.setResourceClass("Herbivore Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(638, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
				
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_carnivore_mustafar");
		resourceRoot.setResourceClass("Carnivore Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(639, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_avian_mustafar");
		resourceRoot.setResourceClass("Avian Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(640, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_egg_mustafar");
		resourceRoot.setResourceClass("Egg Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(641, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
	
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("meat_insect_mustafar");
		resourceRoot.setResourceClass("Insect Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(642, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_fish_mustafar");
		resourceRoot.setResourceClass("Fish Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(643, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_crustacean_mustafar");
		resourceRoot.setResourceClass("Crustacean Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(644, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("seafood_mollusk_mustafar");
		resourceRoot.setResourceClass("Mollusk Meat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MEAT);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(645, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_mammal_mustafar");
		resourceRoot.setResourceClass("Animal Bones");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)300 ,(short)0,(short)1  ,(short)400 ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(646, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_avian_mustafar");
		resourceRoot.setResourceClass("Avian Bones");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_BONES);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)1  ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)700,(short)1,(short)600,(short)500,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(647, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("bone_horn_mustafar");
		resourceRoot.setResourceClass("Horn");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HORN);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_STRUCTURE);
		minCapArray = new short[]{(short)0,(short)0,(short)200 ,(short)0,(short)1  ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)500,(short)700,(short)500,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(648, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_wooly_mustafar");
		resourceRoot.setResourceClass("Wooly Hide");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WOOLY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(649, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_bristley_mustafar");
		resourceRoot.setResourceClass("Bristley Hide");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BRISTLEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(650, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_leathery_mustafar");
		resourceRoot.setResourceClass("Leathery Hide");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_LEATHERY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(651, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("hide_scaley_mustafar");
		resourceRoot.setResourceClass("Scaley Hide");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_HIDE);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_SCALEY_HIDE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(652, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_domesticated_mustafar");
		resourceRoot.setResourceClass("Domesticated Corn");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(653, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("corn_wild_mustafar");
		resourceRoot.setResourceClass("Wild Corn");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_CORN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(654, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_domesticated_mustafar");
		resourceRoot.setResourceClass("Domesticated Rice");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(655, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("rice_wild_mustafar");
		resourceRoot.setResourceClass("Wild Rice");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_RICE);
		minCapArray = new short[]{(short)0,(short)0,(short)0   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1    ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(656, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_domesticated_mustafar");
		resourceRoot.setResourceClass("Domesticated Oats");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(657, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("oats_wild_mustafar");
		resourceRoot.setResourceClass("Wild Oats");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_OATS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(658, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_domesticated_mustafar");
		resourceRoot.setResourceClass("Domesticated Wheat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)300 ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)700};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(659, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
			
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wheat_wild_mustafar");
		resourceRoot.setResourceClass("Wild Wheat");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_WHEAT);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1 ,(short)1   ,(short)300};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(660, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_greens_mustafar");
		resourceRoot.setResourceClass("Vegetable Greens");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_GREEN_VEGETABLES);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(661, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_beans_mustafar");
		resourceRoot.setResourceClass("Vegetable Beans");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_BEAN);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(662, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_tubers_mustafar");
		resourceRoot.setResourceClass("Vegetable Tubers");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_TUBER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(663, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("vegetable_fungi_mustafar");
		resourceRoot.setResourceClass("Vegetable Fungi");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(664, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_fruits_mustafar");
		resourceRoot.setResourceClass("Vegetable Fungi");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(665, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_berries_mustafar");
		resourceRoot.setResourceClass("Vegetable Fungi");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1     ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(666, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fruit_flowers_mustafar");
		resourceRoot.setResourceClass("Flower Fruit");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FUNGI);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1  ,(short)1   ,(short)1};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)700,(short)1000,(short)1000};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(667, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("wood_deciduous_mustafar");
		resourceRoot.setResourceClass("Deciduous Wood");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)400 ,(short)300,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1000,(short)400,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(668, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_conifer_mustafar");
		resourceRoot.setResourceClass("Conifer Wood");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)600 ,(short)100,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(669, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("softwood_evergreen_mustafar");
		resourceRoot.setResourceClass("Evergreen Wood");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_FLORA);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ORGANIC_FOOD);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)800 ,(short)1  ,(short)1  ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)500,(short)1,(short)1000,(short)400,(short)300,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(670, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_liquid_mustafar");
		resourceRoot.setResourceClass("Liquid Petro Fuel");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_PETROCHEM_LIQUID);
		minCapArray = new short[]{(short)0,(short)0,(short)1  ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)1   ,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)600,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(671, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);

		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("fiberplast_mustafar");
		resourceRoot.setResourceClass("Fiberplast");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(672, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("water_vapor_mustafar");
		resourceRoot.setResourceClass("Water Vapor");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_WATER);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_WATER);
		minCapArray = new short[]{(short)0,(short)0,(short)1   ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)600 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(673, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("petrochem_fuel_solid_mustafar");
		resourceRoot.setResourceClass("Solid Petro Fuel");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_CHEMICAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_CHEMICALS);
		minCapArray = new short[]{(short)0,(short)0,(short)750,(short)0,(short)0,(short)0,(short)0,(short)0,(short)870 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)800,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(674, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_mustafar");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE);
		minCapArray = new short[]{(short)0,(short)0,(short)900 ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)907 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(675, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_mustafar");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)750,(short)600,(short)935 ,(short)948 ,(short)350,(short)935 ,(short)922 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)800,(short)650,(short)1000,(short)1000,(short)400,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(676, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("iron_mustafar");
		resourceRoot.setResourceClass("Iron");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)750,(short)450,(short)886 ,(short)919 ,(short)550,(short)903 ,(short)903 ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)800,(short)500,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(677, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("aluminum_mustafar");
		resourceRoot.setResourceClass("Aluminum");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)750,(short)750,(short)800,(short)750,(short)848 ,(short)800,(short)800,(short)0,(short)0,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)800,(short)800,(short)800,(short)800,(short)1000,(short)900,(short)900,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(678, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_mustafar");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)750,(short)500 ,(short)650,(short)600,(short)500 ,(short)750,(short)750,(short)0,(short)0,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)800,(short)1000,(short)700,(short)650,(short)1000,(short)800,(short)800,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(679, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_extrusive_mustafar");
		resourceRoot.setResourceClass("Extrusive Ore");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)896 ,(short)0,(short)909 ,(short)922 ,(short)550,(short)922 ,(short)922 ,(short)0,(short)0,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(680, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_intrusive_mustafar");
		resourceRoot.setResourceClass("Intrusive Ore");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)200 ,(short)0,(short)300 ,(short)700 ,(short)1  ,(short)500 ,(short)400 ,(short)0,(short)0,(short)1 ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(681, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_mustafar_1");
		resourceRoot.setResourceClass("Type 1 Crystal Amorphous Gem");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)870 ,(short)0,(short)870 ,(short)948 ,(short)550,(short)870 ,(short)870 ,(short)0,(short)750,(short)1 ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)1,(short)800,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(682, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("armophous_mustafar_2");
		resourceRoot.setResourceClass("Type 2 Crystal Amorphous Gem");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)870 ,(short)0,(short)870 ,(short)948 ,(short)550,(short)870 ,(short)870 ,(short)0,(short)750,(short)1 ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)600,(short)1000,(short)1000,(short)1,(short)800,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(683, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_mustafar_1");
		resourceRoot.setResourceClass("Type 1 Crystalline Gem");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)838 ,(short)0,(short)838 ,(short)951 ,(short)350,(short)886 ,(short)886 ,(short)0,(short)919 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)400,(short)1000,(short)1000,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(684, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);

		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("crystalline_mustafar_2");
		resourceRoot.setResourceClass("Type 2 Crystalline Gem");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_MINERAL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)838 ,(short)0,(short)838 ,(short)951 ,(short)350,(short)886 ,(short)886 ,(short)0,(short)919 ,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1,(short)1000,(short)1000,(short)400,(short)1000,(short)1000,(short)1,(short)1000,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(685, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_mustafar");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Mustafarian");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_GAS);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		minCapArray = new short[]{(short)0,(short)0,(short)800 ,(short)0,(short)0,(short)0,(short)0,(short)0,(short)0,(short)500 ,(short)0};
		maxCapArray = new short[]{(short)1,(short)1,(short)1000,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(686, resourceRoot);
		mustafarPool4Roots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("aluminum_perovskitic");
		resourceRoot.setResourceClass("Aluminum");
		resourceRoot.setResourceType("Perovskitic");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_JTL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(687, resourceRoot);
		JTLRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("copper_borcarbantium");
		resourceRoot.setResourceClass("Copper");
		resourceRoot.setResourceType("Borcarbantium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_JTL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(688, resourceRoot);
		JTLRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_bicorbantium");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Bicorbantium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_JTL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(689, resourceRoot);
		JTLRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("steel_arveshian");
		resourceRoot.setResourceClass("Steel");
		resourceRoot.setResourceType("Hardened Arveshium");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_JTL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(690, resourceRoot);
		JTLRoots.addElement(resourceRoot);

		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("radioactive_polymetric");
		resourceRoot.setResourceClass("Radioactive");
		resourceRoot.setResourceType("High Grade Polymetric");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_JTL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(691, resourceRoot);
		JTLRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("gas_reactive_organometallic");
		resourceRoot.setResourceClass("Reactive Gas");
		resourceRoot.setResourceType("Unstable Organometallic");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_JTL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_GAS);
		minCapArray = new short[]{(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(692, resourceRoot);
		JTLRoots.addElement(resourceRoot);
		
		resourceRoot = new ResourceRoot();
		resourceRoot.setResourceFileName("ore_siliclastic_fermionic");
		resourceRoot.setResourceClass("Siliclastic Ore");
		resourceRoot.setResourceType("Fermionic");
		resourceRoot.setgeneralType((byte) GalacticResource.GENERAL_JTL);
		resourceRoot.setContainerType((byte) ResourceRoot.CONTAINER_TYPE_INORGANIC_MINERALS);
		minCapArray = new short[]{(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)1   ,(short)0,(short)0,(short)1   ,(short)0};
		maxCapArray = new short[]{(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1000,(short)1,(short)1,(short)1000,(short)1};
		resourceRoot.setResourceMinCaps(minCapArray);
		resourceRoot.setResourceMaxCaps(maxCapArray);
		resourceRoot.setMinimalLifeTime(10000000L);
		resourceRoot.setMaximalLifeTime(10080000L);		
		resourceRootTable.put(693, resourceRoot);
		JTLRoots.addElement(resourceRoot);
			
		planetaryPool4Roots.addAll(corelliaPool4Roots);
		planetaryPool4Roots.addAll(dantooinePool4Roots);
		planetaryPool4Roots.addAll(dathomirPool4Roots);
		planetaryPool4Roots.addAll(endorPool4Roots);
		planetaryPool4Roots.addAll(kashyyykPool4Roots);
		planetaryPool4Roots.addAll(lokPool4Roots);
		planetaryPool4Roots.addAll(mustafarPool4Roots);
		planetaryPool4Roots.addAll(nabooPool4Roots);
		planetaryPool4Roots.addAll(roriPool4Roots);
		planetaryPool4Roots.addAll(talusPool4Roots);
		planetaryPool4Roots.addAll(tattoinePool4Roots);
		planetaryPool4Roots.addAll(yavinPool4Roots);		
//		planetaryPool4Roots.addAll(NEWPLANETPool4Roots);	
		
		
		
		//persist all ResourceRoots
		int indices = resourceRootTable.size();
		for (int i=0;i<indices;i++){
			ResourceRoot persistRoot = resourceRootTable.get(new Integer(i));
			System.err.println("Persisting Root with ID " + persistRoot.getResourceRootID() +" " + persistRoot.getResourceFileName());
			persistRoot.createTransaction(core.getResourceRootsODB().getEnvironment());
			core.getResourceRootsODB().put(persistRoot, Integer.class, ResourceRoot.class, persistRoot.getTransaction());
			persistRoot.getTransaction().commitSync();		
		}
		

	}

// ToDo: Go through all of this again, Vegetable tubers are double 632 berry 635 double 645 and 647 double 671,672
// 683,84,85,86 all same


	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> swgOpcodes, Map<Integer, INetworkRemoteEvent> objControllerOpcodes) {
		
	}
	
	@Override
	public void shutdown() {
		
	}
	
	public Vector<GalacticResource> getAllSpawnedResources() {
		return allSpawnedResources;
	}
	
//	public void setAllSpawnedResources(Vector<GalacticResource> loadedResources) {
//		allSpawnedResources = loadedResources;
//	}
	
	public void addSpawnedResource(GalacticResource loadedResource) {
		allSpawnedResources.add(loadedResource);
	}
	
	public Vector<GalacticResource> getSpawnedResourcesByPlanet(int planetId) {
		Vector<GalacticResource> planetResourceList = new Vector<GalacticResource>();
		for (GalacticResource gal : allSpawnedResources){
			if (gal.isSpawnedOn(planetId))
				planetResourceList.add(gal);
		}
		return planetResourceList;
	}
	
	public Vector<GalacticResource> getSpawnedResourcesByPlanetAndType(int planetId, byte generalType) {
		Vector<GalacticResource> planetResourceList = new Vector<GalacticResource>();
		for (GalacticResource gal : allSpawnedResources){
			if (gal.isSpawnedOn(planetId) && gal.getGeneralType()==generalType)
				planetResourceList.add(gal);
		}
		return planetResourceList;
	}
	
	public int getResourceSampleQuantity(CreatureObject crafter, GalacticResource sampleResource) {
		if (crafter.getContainer()!=null) { // No indoor sampling
			System.out.println("Indoor sampling attempt"); 
			return 0; 
		}  
		int skillMod=crafter.getSkillModBase("surveying");
		crafter.addSkillMod("surveying",100);
		skillMod=(int)(Math.round(crafter.getSkillMod("surveying").getModifier()));
		skillMod=35; // TEST!
		float concentration=sampleResource.deliverConcentrationForSurvey(crafter.getPlanetId(), crafter.getPosition().x, crafter.getPosition().z); 
		float concentrationPercentage=0.01F*concentration;
		int randomSample=new Random().nextInt(100);
		float skillFactor=skillMod;  
		skillFactor=skillFactor/80.0F;
		float quantity=skillFactor*concentrationPercentage*new Random().nextInt(25);
		int SkillModAdapted=skillMod;
		if (SkillModAdapted<=35) 
			SkillModAdapted=(3*skillMod)/2;  
	    else if (SkillModAdapted<=20) 
			SkillModAdapted=2*skillMod; 		
		if (randomSample<=SkillModAdapted) {
			
		} else if (randomSample<=(2*SkillModAdapted)) {
			quantity=0.5F*quantity;
		} else {
			quantity=0;
		}
		if ((quantity>0)&&(quantity<1.0f)) {
			quantity=1.0f+quantity;
			quantity=Math.min(quantity,20);
		}
		//System.out.println("(int)amount" + (int)quantity);
		return (int)quantity;
	}
	
	public void kickOffBigBang(){
		bigBangSpawn();
		String[] PlanetTempArr = new String[] {"","Tattoine","Naboo","Corellia","Rori","Lok","Dantooine","Talus","Yavin4","Endor","Dathomir","Mustafar","Kashyyyk"};
		for (GalacticResource res : allSpawnedResources){
			System.out.println("res.getName() : " + res.getName());
			System.out.println("Resource Class : " + res.getResourceRoot().getResourceType() + " " + res.getResourceRoot().getResourceClass());
			Vector<Integer> pln = res.getAllSpawnedPlanetIds();
			System.out.println("Spawned on :");
			for (Integer n : pln){
				System.out.print(PlanetTempArr[n]+", ");
			}
			System.out.println("");
		}
	}
	
	
	public void start(){
		
								//		bigBangSpawn();
								//		String[] PlanetTempArr = new String[] {"","Tattoine","Naboo","Corellia","Rori","Lok","Dantooine","Talus","Yavin4","Endor","Dathomir","Mustafar","Kashyyyk"};
								//		for (GalacticResource res : allSpawnedResources){
								//			System.out.println("res.getName() : " + res.getName());
								//			System.out.println("Resource Class : " + res.getResourceRoot().getResourceType() + " " + res.getResourceRoot().getResourceClass());
								//			Vector<Integer> pln = res.getAllSpawnedPlanetIds();
								//			System.out.println("Spawned on :");
								//			for (Integer n : pln){
								//				System.out.print(PlanetTempArr[n]+", ");
								//			}
								//			System.out.println("");
								//		}
		
		
//		scheduler.scheduleAtFixedRate(new Runnable() {
//
//			@Override
//			public void run() {
//				
//				// Resource shift
//				bigBangSpawn();
//				Vector<GalacticResource> removeResources = new Vector<GalacticResource>();
//				for (GalacticResource checkedResource : allSpawnedResources){
//					System.out.println("checkedResource.getName() : " + checkedResource.getName());
//					
//					// Check despawn 
//					if (checkedResource.getVanishTime() < System.currentTimeMillis()){
//						byte pool = checkedResource.getPoolNumber();
//						ResourceRoot root = checkedResource.getResourceRoot();
//						switch (pool) {
//							case 1:
//								System.out.println("Pool 1 respawn");
//								spawnResourcePool1(root);
//								break;
//					        case 2:
//					            System.out.println("Pool 2 respawn");
//					            spawnResourcePool2(root);
//					            break;
//					        case 3:
//					            System.out.println("Pool 3 respawn");
//					            spawnResourcePool3(root);
//					            break;
//					        case 4:
//					            System.out.println("Pool 4 respawn");
//					            
//					            if (corelliaPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,3);
//					    		}
//					            if (dantooinePool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,6);
//					    		}
//					            if (dathomirPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,10);
//					    		}
//					            if (endorPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,9);
//					    		}
//					            if (lokPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,5);
//					    		}
//					            if (nabooPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,2);
//					    		}
//					            if (roriPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,4);
//					    		}
//					            if (talusPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,7);
//					    		}
//					            if (tattoinePool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,1);
//					    		}
//					            if (yavinPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,8);
//					    		}
//					            if (kashyyykPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,12);
//					    		}
//					            if (mustafarPool4Roots.contains(checkedResource)){
//					    			spawnResourcePool4(root,11);
//					    		}		
////					    		if (NEWPLANETPool4Roots.contains(checkedResource)){
////					    			spawnResourcePool4(root,NEWPLANET_ID);
////								}				            
//					            break;
//					        default:
//					        	System.err.println("Error, despawning resource had no pool assigned!"); 
//						}						
//						removeResources.add(checkedResource);
//					}
//					
//				}
//				
//				allSpawnedResources.removeAll(removeResources); // remove the resource from the main resource collection
//				for (GalacticResource removeResource : removeResources){
//					//We simple do not explicitely dereference the resource
//					//but will take care that all references will be terminated
//						//removeResource=null; // dereference
//						// alternatively store the despawning resources in a collection
//						// and despawn then during next server restart. That way
//						// nullpointer exceptions can be better prevented
//				}				
//			}
//			
//		}, 0, 600, TimeUnit.SECONDS);
		
	}
	
	// Never call this except once upon first server resource datatable creation !!!
	private boolean bigBangSpawn(){

		if (bigBangOccured)
			return false;
		bigBangOccured = true;
		
		System.out.println("Persisting resources...");
		// Database table ResourceRootData
		
		// ID
		// 1 String Name
		// 2 int ID
		// 3 String Class
		// 4 String General Name
		// 5 RootID
		// 6 MaxSpawnTime
		// 7 MinSpawnTime
	    // CAPS
		// 8 Short Cold_Resistance_Cap_Min
		// 9 Short Cold_Resistance_Cap_Max
		// 10 Short Conductivity_Cap_Min
		// 11 Short Conductivity_Cap_Max
		// 12 Short Decay_Res_Cap_Min
		// 13 Short Decay_Res_Cap_Max
		// 14 Short Heat_Res_Cap_Min
		// 15 Short Heat_Res_Cap_Max
		// 16 Short Malleability_Cap_Min
		// 17 Short Malleability_Cap_Max
		// 18 Short Shock_Res_Cap_Min
		// 19 Short Shock_Res_Cap_Max
		// 20 Short Unit_Toughness_Cap_Min
		// 21 Short Unit_Toughness_Cap_Max
		// 22 Short Entangle_Res_Cap_Min
		// 23 Short Entangle_Res_Cap_Max
		// 24 Short Potential_Energy_Cap_Min
		// 25 Short Potential_Energy_Cap_Max
		// 26 Short Flavor_Cap_Min
		// 27 Short Flavor_Cap_Max
		// 28 Short Overall_Quality_Cap_Min
		// 29 Short Overall_Quality_Cap_Max
		
		
//		Publish 15: Basis for ProjectSWG

//	    - Total: 61, Minimum pool: 15, Random pool: 24 (3 was removed), Fixed pool: 22.
//	    - Possible spawns for rare non-Iron resources: 39.
//	    - Indoor concentrations were fixed for creature harvesting.
		
		// There are some resource classes that always spawn:

		// Steel
		ResourceRoot root = steelRoots.get((int) new Random().nextInt(steelRoots.size()-1));
		spawnResourcePool1(root);
		// Aluminum
		root = aluminumRoots.get((int) new Random().nextInt(aluminumRoots.size()-1));
		spawnResourcePool1(root);
		// Copper
		root = copperRoots.get((int) new Random().nextInt(copperRoots.size()-1));
		spawnResourcePool1(root);
		// Iron
		root = ironRoots.get((int) new Random().nextInt(ironRoots.size()-1));
		spawnResourcePool1(root);
		// Amorphous Gemstone
		root = amorphousGemstoneRoots.get((int) new Random().nextInt(amorphousGemstoneRoots.size()-1));
		spawnResourcePool1(root);
		// Crystalline Gemstone
		root = crystallineGemstoneRoots.get((int) new Random().nextInt(crystallineGemstoneRoots.size()-1));
		spawnResourcePool1(root);
		// Intrusive Ore
		root = oreIntrusiveRoots.get((int) new Random().nextInt(oreIntrusiveRoots.size()-1));
		spawnResourcePool1(root);
		// Carbonate Ore
		root = oreCarbonateRoots.get((int) new Random().nextInt(oreCarbonateRoots.size()-1));
		spawnResourcePool1(root);
		// Extrusive Ore
		root = oreExtrusiveRoots.get((int) new Random().nextInt(oreExtrusiveRoots.size()-1));
		spawnResourcePool1(root);
		// Radioactive
		root = radioactiveRoots.get((int) new Random().nextInt(radioactiveRoots.size()-1));
		spawnResourcePool1(root);
		// Liquid Petrochemical
		root = petrochemicalLiquidRoots.get((int) new Random().nextInt(petrochemicalLiquidRoots.size()-1));
		spawnResourcePool1(root);
		// Solid Petrochemical
		root = petrochemicalSolidRoots.get((int) new Random().nextInt(petrochemicalSolidRoots.size()-1));
		spawnResourcePool1(root);
		// Polymer
		root = polymerRoots.get(0);
		spawnResourcePool1(root);
		root = polymerRoots.get(0);
		spawnResourcePool1(root);
		//Lubricating oil
		root = lubricatingOilRoots.get(0);
		spawnResourcePool1(root);
		root = lubricatingOilRoots.get(0);
		spawnResourcePool1(root);

		// Random pool: 24		
		for (int i=0;i<24;i++){
			root = resourceRootTable.get(new Random().nextInt(136));
			spawnResourcePool2(root);
		}
		
		// Fixed pool: 22 -> 8 are JTL resources 	
		for (int i=0;i<8;i++) {
			root = JTLRoots.get((int) new Random().nextInt(JTLRoots.size()-1));
			spawnResourcePool3(root);
		}
		
		// and 16 are irons	
		for (int i=0;i<16;i++) {
			root = ironRoots.get((int) new Random().nextInt(ironRoots.size()-1));
			spawnResourcePool3(root);
		}
		
		for (ResourceRoot rootElement :  corelliaPool4Roots){
			spawnResourcePool4(rootElement,3);
		}
		for (ResourceRoot rootElement :  dantooinePool4Roots){
			spawnResourcePool4(rootElement,6);
		}
		for (ResourceRoot rootElement :  dathomirPool4Roots){
			spawnResourcePool4(rootElement,10);
		}
		for (ResourceRoot rootElement :  endorPool4Roots){
			spawnResourcePool4(rootElement,9);
		}
		for (ResourceRoot rootElement :  lokPool4Roots){
			spawnResourcePool4(rootElement,5);
		}
		for (ResourceRoot rootElement :  nabooPool4Roots){
			spawnResourcePool4(rootElement,2);
		}
		for (ResourceRoot rootElement :  roriPool4Roots){
			spawnResourcePool4(rootElement,4);
		}
		for (ResourceRoot rootElement :  talusPool4Roots){
			spawnResourcePool4(rootElement,7);
		}
		for (ResourceRoot rootElement :  tattoinePool4Roots){
			spawnResourcePool4(rootElement,1);
		}
		for (ResourceRoot rootElement :  yavinPool4Roots){
			spawnResourcePool4(rootElement,8);
		}
		for (ResourceRoot rootElement :  kashyyykPool4Roots){
			spawnResourcePool4(rootElement,12);
		}
		for (ResourceRoot rootElement :  mustafarPool4Roots){
			spawnResourcePool4(rootElement,11);
		}		
//		for (ResourceRoot rootElement :  NEWPLANETPool4Roots){
//			spawnResourcePool4(rootElement,NEWPLANET_ID);
//		}
		
		System.out.println(allSpawnedResources.size() + " RESOURCES SPAWNED.");
		System.out.println("Finished persisting resources.");
		return true;
	}
		
	public GalacticResource spawnResourcePool1(ResourceRoot root){

		GalacticResource resource = (GalacticResource) core.objectService.createResource();
		try {		
			resource.setResourceRoot(root);
			resource.setPoolNumber((byte)1);
			resource.initializeNewGalaxyResource(completeResourceNameHistory);
			
			resource.createTransaction(core.getResourcesODB().getEnvironment());
			core.getResourcesODB().put(resource, Long.class, GalacticResource.class, resource.getTransaction());
			resource.getTransaction().commitSync();
			
			if (enableResourceHistory){
				GalacticResource historicResource = resource.convertToHistoricResource();
				historicResource.createTransaction(core.getResourceHistoryODB().getEnvironment());
				core.getResourceHistoryODB().put(historicResource, Long.class, GalacticResource.class, historicResource.getTransaction());
				historicResource.getTransaction().commitSync();
			}
			
			completeResourceNameHistory.add(resource.getName());
			spawnedResourcesPool1.add(resource);
			allSpawnedResources.add(resource);
			totalSpawnedResourcesNumber++;
		} catch (Exception e){
			System.err.println("Exception in spawnResourcePool1 root "+ root);	
		}
		return resource;
	}
	
	public GalacticResource spawnResourcePool2(ResourceRoot root){		

		GalacticResource resource = (GalacticResource) core.objectService.createResource();
		try {
			resource.setResourceRoot(root);
			resource.setPoolNumber((byte)2);
			resource.initializeNewGalaxyResource(completeResourceNameHistory);
			
			resource.createTransaction(core.getResourcesODB().getEnvironment());
			core.getResourcesODB().put(resource, Long.class, GalacticResource.class, resource.getTransaction());
			resource.getTransaction().commitSync();
			
			if (enableResourceHistory){
				GalacticResource historicResource = resource.convertToHistoricResource();
				historicResource.createTransaction(core.getResourceHistoryODB().getEnvironment());
				core.getResourceHistoryODB().put(historicResource, Long.class, GalacticResource.class, historicResource.getTransaction());
				historicResource.getTransaction().commitSync();
			}
				
			completeResourceNameHistory.add(resource.getName());
			spawnedResourcesPool2.add(resource);
			allSpawnedResources.add(resource);
			totalSpawnedResourcesNumber++;
		} catch (Exception e){
			System.err.println("Exception in spawnResourcePool2 root "+ root);	
		}
		return resource;
	}
	
	public GalacticResource spawnResourcePool3(ResourceRoot root){		

		GalacticResource resource = (GalacticResource) core.objectService.createResource();
		try {
			resource.setResourceRoot(root);
			resource.setPoolNumber((byte)3);
			resource.initializeNewGalaxyResource(completeResourceNameHistory);
			
			resource.createTransaction(core.getResourcesODB().getEnvironment());
			core.getResourcesODB().put(resource, Long.class, GalacticResource.class, resource.getTransaction());
			resource.getTransaction().commitSync();
			
			if (enableResourceHistory){
				GalacticResource historicResource = resource.convertToHistoricResource();
				historicResource.createTransaction(core.getResourceHistoryODB().getEnvironment());
				core.getResourceHistoryODB().put(historicResource, Long.class, GalacticResource.class, historicResource.getTransaction());
				historicResource.getTransaction().commitSync();
			}
				
			completeResourceNameHistory.add(resource.getName());
			spawnedResourcesPool3.add(resource);
			allSpawnedResources.add(resource);
			totalSpawnedResourcesNumber++;
		} catch (Exception e){
			System.err.println("Exception in spawnResourcePool3 root "+ root);	
		}
		return resource;
	}
	
	public GalacticResource spawnResourcePool4(ResourceRoot root,int planetID){	

		GalacticResource resource = (GalacticResource) core.objectService.createResource();
		try {
			resource.setResourceRoot(root);
			resource.setPoolNumber((byte)4);
			resource.setPlanetID(planetID);
			resource.initializeNewGalaxyResource(completeResourceNameHistory);
			
			resource.createTransaction(core.getResourcesODB().getEnvironment());
			core.getResourcesODB().put(resource, Long.class, GalacticResource.class, resource.getTransaction());
			resource.getTransaction().commitSync();
			
			if (enableResourceHistory){
				GalacticResource historicResource = resource.convertToHistoricResource();
				historicResource.createTransaction(core.getResourceHistoryODB().getEnvironment());
				core.getResourceHistoryODB().put(historicResource, Long.class, GalacticResource.class, historicResource.getTransaction());
				historicResource.getTransaction().commitSync();
			}
			
			completeResourceNameHistory.add(resource.getName());
			spawnedResourcesPool4.add(resource);
			allSpawnedResources.add(resource);
			totalSpawnedResourcesNumber++;
		} catch (Exception e){
			System.err.println("Exception in spawnResourcePool4 root "+ root.getResourceFileName() + " e: " + e.getMessage());	
			System.err.println("planetID "+ planetID);	
		}
		return resource;
	}
		
	public Vector<ResourceRoot> pickPool4ResourceRoots(int planetId){	
		Vector<ResourceRoot> picked = new Vector<ResourceRoot>();
		switch(planetId){
	        case 0:
	        	picked = corelliaPool4Roots;
	            break; 
	        case 1:
	        	picked = dantooinePool4Roots;
	            break; 
	        case 2:
	        	picked = dathomirPool4Roots;
	            break; 
	        case 3:
	        	picked = endorPool4Roots;
	            break; 
	        case 4:
	        	picked = kashyyykPool4Roots;
	            break; 
	        case 5:
	        	picked = lokPool4Roots;
	            break; 
	        case 6:
	        	picked = mustafarPool4Roots;
	            break; 
	        case 7:
	        	picked = nabooPool4Roots;
	            break;
	        case 8:
	        	picked = roriPool4Roots;
	            break;
	        case 9:
	        	picked = talusPool4Roots;
	            break;
	        case 10:
	        	picked = tattoinePool4Roots;
	            break;
	        case 11:
	        	picked = yavinPool4Roots;
	            break;
//	        case 12:
//	        	picked = NEWPLANETNAMEPool4Roots;
//	        	break;
	           
	        default:
	        	picked = nabooPool4Roots;        	
		}
		return picked;
	}
	
	public void add_spawnedResourcesPool1(GalacticResource resource){
		spawnedResourcesPool1.add(resource);
	}
	
	public void add_spawnedResourcesPool2(GalacticResource resource){
		spawnedResourcesPool2.add(resource);
	}
	
	public void add_spawnedResourcesPool3(GalacticResource resource){
		spawnedResourcesPool3.add(resource);
	}
	
	public void add_spawnedResourcesPool4(GalacticResource resource){
		spawnedResourcesPool4.add(resource);
	}
	
	public void add_resourceRoot(ResourceRoot resourceRoot){
		int rootId = resourceRoot.getResourceRootID();
		resourceRootTable.put(rootId, resourceRoot);		
	}
	
	public ResourceRoot retrieveResourceRootReference(int resourceRootID){
		ResourceRoot resourceRootreference = null;
		resourceRootreference = resourceRootTable.get(new Integer(resourceRootID));
		return resourceRootreference;
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
	
	public GalacticResource grabResourceByFileName(String searchName){
		GalacticResource resource = null;
		Vector<GalacticResource> allResources = core.resourceService.getAllSpawnedResources();
		for (GalacticResource res : allResources){
			if (res.getResourceRoot().getResourceFileName().equals(searchName)){
				resource = res; // resourceFileName= "meat_insect_mustafar" i.e.
			}
		}
		return resource;
	}
	
	public GalacticResource grabResourceByClass(String searchName,int planetId){
		GalacticResource resource = null;
		Vector<GalacticResource> allResources = core.resourceService.getAllSpawnedResources();
		for (GalacticResource res : allResources){
			Vector<Integer> spawnedPlanets = res.getAllSpawnedPlanetIds();
			if (spawnedPlanets.contains(planetId) && res.getResourceRoot().getResourceClass().equals(searchName)){
				resource = res; // resourceClass= "Insect Meat" i.e.
			}
		}
		return resource;
	}
	
	public Vector<GalacticResource> getSpawnedResourcesByPlanetAndHarvesterType(int planetId, byte harvesterType) {
		Vector<GalacticResource> planetResourceList = new Vector<GalacticResource>();
		byte searchtype = harvesterType;
		if (harvesterType==7) searchtype = (byte) 0;
		for (GalacticResource gal : allSpawnedResources){
			if (gal.isSpawnedOn(planetId) && gal.getGeneralType()==searchtype)
				if (harvesterType!=HarvesterObject.HARVESTER_TYPE_FUSION) 
					planetResourceList.add(gal);
				else if (harvesterType==HarvesterObject.HARVESTER_TYPE_FUSION) {
					System.err.println("gal.getContainerType() " + gal.getContainerType());
					if (gal.getResourceRoot().getContainerType()==ResourceRoot.CONTAINER_TYPE_ENERGY_RADIOACTIVE)
						planetResourceList.add(gal);
				} else if (harvesterType==HarvesterObject.HARVESTER_TYPE_GEO) {
					System.err.println("gal.getContainerType() " + gal.getContainerType());
					if (gal.getGeneralType()==GalacticResource.GENERAL_GEOTHERM)
						planetResourceList.add(gal);
				}
		}
		return planetResourceList;
	}
	
	public GalacticResource findResourceById(long id){
		GalacticResource resource = new GalacticResource();
		for (GalacticResource sampleResource : allSpawnedResources){
			if (sampleResource.getId()==id)
				return sampleResource;
		}
		return resource;
	}
	
	// ToDo: Improve
	public GalacticResource grabMeatForCreature(CreatureObject corpse){
		GalacticResource resource = null;
		int planetId = corpse.getPlanetId();
		String meatName = corpse.getStfName(); // placeholder
		Vector<GalacticResource> allResources = core.resourceService.getAllSpawnedResources();
		for (GalacticResource res : allResources){
			Vector<Integer> spawnedPlanets = res.getAllSpawnedPlanetIds();
			if (spawnedPlanets.contains(planetId) && res.getResourceRoot().getResourceClass().equals(meatName)){
				resource = res; 
			}
		}
		return resource;
	}
	
	// Utility method to quickly spawn resource containers into the inventory
		public ResourceContainerObject spawnSpecificResourceContainer(String spawnType, CreatureObject crafter,int stackCount){	
			ResourceContainerObject containerObject = null;
			for (GalacticResource sampleResource : allSpawnedResources){
				if (sampleResource.getResourceRoot().getResourceClass().contains(spawnType)){
					String resourceContainerIFF = ResourceRoot.CONTAINER_TYPE_IFF_SIGNIFIER[sampleResource.getResourceRoot().getContainerType()];           		  				
    				containerObject = (ResourceContainerObject) core.objectService.createObject(resourceContainerIFF, crafter.getPlanet());  				
    				containerObject.setProprietor(crafter);
    				containerObject.setStackCount(stackCount,false);
    				//int stackCount = core.resourceService.getResourceSampleQuantity(crafter, sampleResource); 
    				containerObject.initializeStats(sampleResource);            		          		
            		int resCRC = CRC.StringtoCRC(resourceContainerIFF);
    				containerObject.setIffFileName(resourceContainerIFF);             		
    				long objectId = containerObject.getObjectID();  					
    				SWGObject crafterInventory = crafter.getSlottedObject("inventory");        				   					
    				crafterInventory.add(containerObject);
    				return containerObject;
				}
			}
			return containerObject;
		}
		
		public Vector<String> getCompleteResourceNameHistory() {
			return completeResourceNameHistory;
		}
}
