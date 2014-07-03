from resources.common import RadialOptions
from resources.common import OutOfBand
from protocol.swg import ResourceListForSurveyMessage
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
from java.lang import System
from java.lang import Long
from java.util import Map
from java.util import TreeMap
from resources.datatables import GcwRank
from resources.datatables import WeaponType
import main.NGECore
from services.gcw import GCWService
import sys
import math


def handleImperialItems1 (core, owner):
	actor = owner.getSlottedObject('ghost')
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setImperialBox1Callback)
	
	if actor.getCurrentRank() >= GcwRank.PRIVATE:
		window.addListBoxMenuItem('Private', 0)
	
	if actor.getCurrentRank() >= GcwRank.LANCECORPORAL:
		window.addListBoxMenuItem('Lance Corporal', 1)
	
	if actor.getCurrentRank() >= GcwRank.CORPORAL:
		window.addListBoxMenuItem('Corporal', 2)
	
	if actor.getCurrentRank() >= GcwRank.SERGEANT:
		window.addListBoxMenuItem('Sergeant', 3)	
	
	if actor.getCurrentRank() >= GcwRank.MASTERSERGEANT:
		window.addListBoxMenuItem('Master Sergeant', 4)
	
	if actor.getCurrentRank() >= GcwRank.SERGEANTMAJOR:
		window.addListBoxMenuItem('Sergeant Major', 5)

	if actor.getCurrentRank() >= GcwRank.LIEUTENANT:
		window.addListBoxMenuItem('Lieutenant', 6)

	if actor.getCurrentRank() >= GcwRank.CAPTAIN:
		window.addListBoxMenuItem('Captain', 7)

	if actor.getCurrentRank() >= GcwRank.MAJOR:
		window.addListBoxMenuItem('Major', 8)
	
	if actor.getCurrentRank() >= GcwRank.LTCOLONEL:
		window.addListBoxMenuItem('LT Colonel', 9)
	
	if actor.getCurrentRank() >= GcwRank.COLONEL:
		window.addListBoxMenuItem('Colonel', 10)
	
	if actor.getCurrentRank() >= GcwRank.GENERAL:
		window.addListBoxMenuItem('General', 11)

	core.suiService.openSUIWindow(window);
def setImperialBox1Callback(owner, window, eventType, returnList):

	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		privateHandler(owner)
		return
	if returnList.get(0)=='1':
		lanceCorporalHandler(owner)
		return
	if returnList.get(0)=='2':
		corporalHandler(owner)
		return
	if returnList.get(0)=='3':
		sergeantHandler(owner)
		return
	if returnList.get(0)=='4':
		masterSergeantHandler(owner)
		return
	if returnList.get(0)=='5':
		sergeantMajorHandler(owner)
		return
	if returnList.get(0)=='6':
		lieutenantHandler(owner)
		return
	if returnList.get(0)=='7':
		captainHandler(owner)
		return
	if returnList.get(0)=='8':
		majorHandler(owner)
		return
	if returnList.get(0)=='9':
		ltColonelHandler(owner)
		return
	if returnList.get(0)=='10':
		colonelHandler(owner)
		return
	if returnList.get(0)=='11':
		generalHandler(owner)
		return
		
	return
	
def privateHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	#window.addListBoxMenuItem('R2 Droid Schematic 165000', 0)
	#window.addListBoxMenuItem('R3 Droid Schematic 180000', 1)
	#window.addListBoxMenuItem('R4 Droid Schematic 195000', 2)
	#window.addListBoxMenuItem('DZ-70 Droid Schematic 225000', 3)
	#window.addListBoxMenuItem('Proximity Caltrop Trap 3250' ,4)
	#window.addListBoxMenuItem('Remote Caltrop Trap 4000', 5)
	#window.addListBoxMenuItem('Proximity Flashbang Trap 3250', 6)
	#window.addListBoxMenuItem('Remote Flashbang Trap 4000', 7)
	#window.addListBoxMenuItem('Proximity HX2 Trap 3250', 8)
	#window.addListBoxMenuItem('Remote HX2 Trap 4000', 9)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, privateRewards)
	core.suiService.openSUIWindow(window);
	return
	
def lanceCorporalHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Tech Armoire 105000', 0)
	window.addListBoxMenuItem('Tech Bookcase 105000', 1)
	window.addListBoxMenuItem('Tech Cabinet 105000', 2)
	window.addListBoxMenuItem('Tech Chest 75000', 3)
	window.addListBoxMenuItem('Tech Couch 112500', 4)
	window.addListBoxMenuItem('Tech Chair 75000', 5)
	window.addListBoxMenuItem('Tech Coffee Table 75000', 6)
	window.addListBoxMenuItem('Tech End Table 75000', 7)
	window.addListBoxMenuItem('Data Terminal Slim 60000', 8)
	window.addListBoxMenuItem('Data Terminal Standard 75000', 9)
	window.addListBoxMenuItem('Data Terminal Bulky 90000', 10)
	window.addListBoxMenuItem('Data Terminal Wall 105000', 11)
	window.addListBoxMenuItem('Imperial Table 75000', 12)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, lanceCorporalRewards)
	core.suiService.openSUIWindow(window);
	return

def corporalHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Electrobinoculars 15000', 0)
	window.addListBoxMenuItem('Advanced Electrobinoculars 22500',1)
	window.addListBoxMenuItem('Superior Electrobinoculars 30000', 2)
	window.addListBoxMenuItem('Experimental Electrobinoculars 37500', 3)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, corporalRewards)
	core.suiService.openSUIWindow(window);
	return
	
def sergeantHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Imperial Boots 37500', 0)
	window.addListBoxMenuItem('Imperial Jacket 37500' ,1)
	window.addListBoxMenuItem('Imperial Pants 37500', 2)
	window.addListBoxMenuItem('Imperial Hat 37500', 3)
	window.addListBoxMenuItem('Power5 Pistol 112500', 4)
	window.addListBoxMenuItem('E-11 Carbine', 5)
	window.addListBoxMenuItem('Sword 30000', 6)
	window.addListBoxMenuItem('Rocket Launcher 150000', 7)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, sergeantRewards)
	core.suiService.openSUIWindow(window);
	return

def masterSergeantHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Small Block Turret 120000',0)
	window.addListBoxMenuItem('Small Dish Turret 120000',1)
	window.addListBoxMenuItem('Small Tower Turret 120000',2)
	window.addListBoxMenuItem('Painting Might of the Empire 375000',3)
	#window.addListBoxMenuItem('DRX-55 Mine 12000',4)
	#window.addListBoxMenuItem('SR-88 Cyro Mine 12000',5)
	#window.addListBoxMenuItem('XG Mine 12000',6)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, masterSergeantRewards)
	core.suiService.openSUIWindow(window);
	return
	
def sergeantMajorHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Medium Block Turret 150000', 0)
	window.addListBoxMenuItem('Medium Tower Turret 150000', 1)
	window.addListBoxMenuItem('HQ Forward Outpost 525000', 2)
	window.addListBoxMenuItem('HQ Field Hospital 1050000', 3)
	window.addListBoxMenuItem('SF HQ Forward Outpost 750000' ,4)
	window.addListBoxMenuItem('SF HQ Field Hospital 1500000', 5)
	window.addListBoxMenuItem('Nova Star Medal of the Empire 80000', 6)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, sergeantMajorRewards)
	core.suiService.openSUIWindow(window);
	return
	
def lieutenantHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Large Block Turret 210000',0)
	window.addListBoxMenuItem('Large Dish Turret 210000',1)
	window.addListBoxMenuItem('Large Tower Turret 210000',2)
	window.addListBoxMenuItem('HQ Tactical Center 1875000', 3)
	window.addListBoxMenuItem('HQ Field Hospital 3600000',4)
	window.addListBoxMenuItem('SF HQ Tactical Center 2325000',5)
	window.addListBoxMenuItem('SF HQ Detachment Headquarters 4500000',6)
	#window.addListBoxMenuItem('Factional Comm link 200000', 7)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, lieutenantRewards)
	core.suiService.openSUIWindow(window);
	return
	
def captainHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Factional Banner 81600', 0)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, captainRewards)
	core.suiService.openSUIWindow(window);
	return
	
def majorHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Black Spec Ops Armor Leggings 46636', 0)
	window.addListBoxMenuItem('Black Spec Ops Armor Belt 38863', 1)
	window.addListBoxMenuItem('Black Spec Ops Armor Boots 41454', 2)
	window.addListBoxMenuItem('Technical Readout of an Imperial Spec Ops Armor Dye Kit 12954', 3)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, majorRewards)
	core.suiService.openSUIWindow(window);
	return
	
def ltColonelHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Black Spec Ops Armor Left Bicep 120000', 0)
	window.addListBoxMenuItem('Black Spec Ops Armor Right Bicep 120000', 1)
	window.addListBoxMenuItem('Black Spec Ops Armor Left Bracer 120000', 2)
	window.addListBoxMenuItem('Black Spec Ops Armor Right Bracer 120000', 3)
	window.addListBoxMenuItem('Black Spec Ops Armor Gloves 120000', 4)
	window.addListBoxMenuItem('Black Spec Ops Armor Helmet 150000', 5)
	window.addListBoxMenuItem('Black Spec Ops Armor Torso 225000', 6)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, ltColonelRewards)
	core.suiService.openSUIWindow(window);
	return
	
def colonelHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Colonel Signet Ring of the Empire 142500', 0)
	window.addListBoxMenuItem('BARC Command Vehicle 1050000', 1)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, colonelRewards)
	core.suiService.openSUIWindow(window);
	return
	
def generalHandler(owner):
	actor = owner.getSlottedObject('ghost')
	core = main.NGECore.getInstance()
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Imperial Item Requisition')
	window.setProperty('Prompt.lblPrompt:Text', 'Items')	
	window.addListBoxMenuItem('Windu\'s Guile 2000000', 0)
	window.addListBoxMenuItem('Exceptional Imperial E-11 Carbine 200000', 1)
	window.addListBoxMenuItem('The Legendary Starlight Carbine 200000', 2)
	window.addListBoxMenuItem('The Legendary Crimson Nova Pistol 200000', 3)
	window.addListBoxMenuItem('The Legendary Vortex Rifle 200000', 4)
	window.addListBoxMenuItem('The Legendary Reaper Cannon 200000', 5)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, generalRewards)
	core.suiService.openSUIWindow(window);
	return
		
def privateRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		r2DroidSchematic(object, owner)
		return
			
	if returnList.get(0)=='1':
		r3DroidSchematic(object, owner)
		return
					
	if returnList.get(0)=='2':
		r4DroidSchematic(object, owner)
		return	
				
	if returnList.get(0)=='3':
		r5DroidSchematic(object, owner)
		return
						
	if returnList.get(0)=='4':
		dz70DroidSchematic(object, owner)
		return
		
	if returnList.get(0)=='5':
		proximityCaltrop(object, owner)
		return
		
	if returnList.get(0)=='6':
		remoteCaltrop(object, owner)
		return
		
	if returnList.get(0)=='7':
		proximityFlashbang(object, owner)
		return
		
	if returnList.get(0)=='8':
		remoteFlashbang(object, owner)
		return
		
	if returnList.get(0)=='9':
		proximityHX2(object, owner)
		return
		
	if returnList.get(0)=='10':
		remoteHX2(object, owner)
		return
			
def lanceCorporalRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		techArmoire(object, owner)
		return
			
	if returnList.get(0)=='1':
		techBookcase(object, owner)
		return
					
	if returnList.get(0)=='2':
		techCabinet(object, owner)
		return	
				
	if returnList.get(0)=='3':
		techChest(object, owner)
		return
						
	if returnList.get(0)=='4':
		techCouch(object, owner)
		return
		
	if returnList.get(0)=='5':
		techChair(object, owner)
		return
		
	if returnList.get(0)=='6':
		techCoffeeTable(object, owner)
		return
	
	if returnList.get(0)=='7':
		techEndTable(object, owner)
		return
		
	if returnList.get(0)=='8':
		dataTerminalSlim(object, owner)
		return
		
	if returnList.get(0)=='9':
		dataTerminalStandard(object, owner)
		return
		
	if returnList.get(0)=='10':
		dataTerminalBulky(object, owner)
		return
		
	if returnList.get(0)=='11':
		dataTerminalWall(object, owner)
		return
		
	if returnList.get(0)=='12':
		imperialTable(object, owner)
		return
		
def corporalRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		electrobinoculars(object, owner)
		return
			
	if returnList.get(0)=='1':
		advancedElectrobinoculars(object, owner)
		return
					
	if returnList.get(0)=='2':
		superiorElectrobinoculars(object, owner)
		return	
				
	if returnList.get(0)=='3':
		experimentalElectrobinoculars(object, owner)
		return
		
def sergeantRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		imperialBoots(object, owner)
		return
			
	if returnList.get(0)=='1':
		imperialJacket(object, owner)
		return
					
	if returnList.get(0)=='2':
		imperialPants(object, owner)
		return
		
	if returnList.get(0)=='3':
		imperialHat(object, owner)
		return
		
	if returnList.get(0)=='4':
		power5Pistol(object, owner)
		return
		
	if returnList.get(0)=='5':
		e11Carbine(object, owner)
		return
		
	if returnList.get(0)=='6':
		sword(object, owner)
		return
		
	if returnList.get(0)=='7':
		rocketLauncher(object, owner)
		return

def masterSergeantRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		smallBlockTurret(object, owner)
		return
			
	if returnList.get(0)=='1':
		smallDishTurret(object, owner)
		return
					
	if returnList.get(0)=='2':
		smallTowerTurret(object, owner)
		return	
				
	if returnList.get(0)=='3':
		paintingMightEmpire(object, owner)
		return
		
def sergeantMajorRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		mediumBlockTurret(object, owner)
		return
			
	if returnList.get(0)=='1':
		mediumTowerTurret(object, owner)
		return
					
	if returnList.get(0)=='2':
		hqForwardOutpost(object, owner)
		return	
				
	if returnList.get(0)=='3':
		hqFieldHospital(object, owner)
		return
						
	if returnList.get(0)=='4':
		sfhqForwardOutpost(object, owner)
		return
		
	if returnList.get(0)=='5':
		sfhqFieldHospital(object, owner)
		return
		
	if returnList.get(0)=='6':
		novaStarMedal(object, owner)
		return
		
def lieutenantRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		largeBlockTurret(object, owner)
		return
			
	if returnList.get(0)=='1':
		largeDishTurret(object, owner)
		return
					
	if returnList.get(0)=='2':
		largeTowerTurret(object, owner)
		return	
				
	if returnList.get(0)=='3':
		hqTacticalCenter(object, owner)
		return
						
	if returnList.get(0)=='4':
		hqFieldHospital(object, owner)
		return
		
	if returnList.get(0)=='5':
		sfhqTacticalCenter(object, owner)
		return
		
	if returnList.get(0)=='6':
		sfhqDetachmentHeadquarters(object, owner)
		return
		
	if returnList.get(0)=='7':
		factionalCommlink(object, owner)
		return
		
def captainRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		factionalBanner(object, owner)
		return
		
def majorRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		specOpsLeggings(object, owner)
		return
			
	if returnList.get(0)=='1':
		specOpsBelt(object, owner)
		return
					
	if returnList.get(0)=='2':
		specOpsBoots(object, owner)
		return	
				
	if returnList.get(0)=='3':
		specOpsDyeKit(object, owner)
		return
		
def ltColonelRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		specOpsLeftBicep(object, owner)
		return
			
	if returnList.get(0)=='1':
		specOpsRightBicep(object, owner)
		return
					
	if returnList.get(0)=='2':
		specOpsLeftBracer(object, owner)
		return	
				
	if returnList.get(0)=='3':
		specOpsRightBracer(object, owner)
		return
		
	if returnList.get(0)=='4':
		specOpsGloves(object, owner)
		return
		
	if returnList.get(0)=='5':
		specOpsHelmet(object, owner)
		return
		
	if returnList.get(0)=='6':
		specOpsChestPlate(object, owner)
		return
		
def colonelRewards(owner, window, eventType, returnList):
	
	if returnList.size()==0:
		return
			
	if returnList.get(0)=='0':
		colonelRing(object, owner)
		return
			
	if returnList.get(0)=='1':
		barcSpeeder(object, owner)
		return
					
			
def generalRewards(owner, window, eventType, returnList):

	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		windusGuile(object, owner)
		return
		
	if returnList.get(0)=='1':
		exceptionalCarbine(object, owner)
		return
		
	if returnList.get(0)=='2':
		starlightCarbine(object, owner)
		return
		
	if returnList.get(0)=='3':
		crimsonNova(object, owner)
		return
		
	if returnList.get(0)=='4':
		vortexRifle(object, owner)
		return
	if returnList.get(0)=='5':
		reaperCannon(object, owner)
		return

def r2DroidSchematic(object, owner):
	if owner.getCashCredits() >= 165000:
		owner.setCashCredits(owner.getCashCredits() - 165000)
		core = main.NGECore.getInstance()
		r2 = core.objectService.createObject('object/draft_schematic/droid/shared_droid_r2.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(r2)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def r3DroidSchematic(object, owner):
	if owner.getCashCredits() >= 180000:
		owner.setCashCredits(owner.getCashCredits() - 180000)
		core = main.NGECore.getInstance()
		r3 = core.objectService.createObject('object/draft_schematic/droid/shared_droid_r3.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(r3)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def r4DroidSchematic(object, owner):
	if owner.getCashCredits() >= 195000:
		owner.setCashCredits(owner.getCashCredits() - 195000)
		core = main.NGECore.getInstance()
		r4 = core.objectService.createObject('object/draft_schematic/droid/shared_droid_r4.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(r4)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def r5DroidSchematic(object, owner):
	if owner.getCashCredits() >= 210000:
		owner.setCashCredits(owner.getCashCredits() - 210000)
		core = main.NGECore.getInstance()
		r5 = core.objectService.createObject('object/draft_schematic/droid/shared_droid_r5.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(r5)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def dz70DroidSchematic(object, owner):
	if owner.getCashCredits() >= 16500:
		owner.setCashCredits(owner.getCashCredits() - 16500)
		core = main.NGECore.getInstance()
		sergical = core.objectService.createObject('object/draft_schematic/droid/shared_droid_dz70.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(sergical)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
		
def techArmoire(object, owner):
	if owner.getCashCredits() >= 105000:
		owner.setCashCredits(owner.getCashCredits() - 105000)
		core = main.NGECore.getInstance()
		armoire = core.objectService.createObject('object/tangible/furniture/technical/shared_armoire_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(armoire)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def techBookcase(object, owner):
	if owner.getCashCredits() >= 105000:
		owner.setCashCredits(owner.getCashCredits() - 105000)
		core = main.NGECore.getInstance()
		bookcase = core.objectService.createObject('object/tangible/furniture/technical/shared_bookcase_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(bookcase)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def techCabinet(object, owner):
	if owner.getCashCredits() >= 105000:
		owner.setCashCredits(owner.getCashCredits() - 105000)
		core = main.NGECore.getInstance()
		cabinet = core.objectService.createObject('object/tangible/furniture/technical/shared_cabinet_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(cabinet)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def techChest(object, owner):
	if owner.getCashCredits() >= 75000:
		owner.setCashCredits(owner.getCashCredits() - 75000)
		core = main.NGECore.getInstance()
		chest = core.objectService.createObject('object/tangible/furniture/technical/shared_chest_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(chest)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def techCouch(object, owner):
	if owner.getCashCredits() >= 112500:
		owner.setCashCredits(owner.getCashCredits() - 112500)
		core = main.NGECore.getInstance()
		couch = core.objectService.createObject('object/tangible/furniture/technical/shared_couch_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(couch)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def techChair(object, owner):
	if owner.getCashCredits() >= 75000:
		owner.setCashCredits(owner.getCashCredits() - 75000)
		core = main.NGECore.getInstance()
		chair = core.objectService.createObject('object/tangible/furniture/technical/shared_bookcase_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(chair)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def techCoffeeTable(object, owner):
	if owner.getCashCredits() >= 75000:
		owner.setCashCredits(owner.getCashCredits() - 75000)
		core = main.NGECore.getInstance()
		coffeetable = core.objectService.createObject('object/tangible/furniture/technical/shared_coffee_table_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(coffeetable)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return

def techEndTable(object, owner):
	if owner.getCashCredits() >= 75000:
		owner.setCashCredits(owner.getCashCredits() - 75000)
		core = main.NGECore.getInstance()
		endtable = core.objectService.createObject('object/tangible/furniture/technical/shared_end_table_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(endtable)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def dataTerminalSlim(object, owner):
	if owner.getCashCredits() >= 60000:
		owner.setCashCredits(owner.getCashCredits() - 60000)
		core = main.NGECore.getInstance()
		terminal = core.objectService.createObject('object/tangible/furniture/imperial/shared_data_terminal_s1.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(terminal)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def dataTerminalStandard(object, owner):
	if owner.getCashCredits() >= 75000:
		owner.setCashCredits(owner.getCashCredits() - 75000)
		core = main.NGECore.getInstance()
		terminal = core.objectService.createObject('object/tangible/furniture/imperial/shared_data_terminal_s2.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(terminal)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def dataTerminalBulky(object, owner):
	if owner.getCashCredits() >= 90000:
		owner.setCashCredits(owner.getCashCredits() - 90000)
		core = main.NGECore.getInstance()
		terminal = core.objectService.createObject('object/tangible/furniture/imperial/shared_data_terminal_s3.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(terminal)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def dataTerminalWall(object, owner):
	if owner.getCashCredits() >= 105000:
		owner.setCashCredits(owner.getCashCredits() - 105000)
		core = main.NGECore.getInstance()
		terminal = core.objectService.createObject('object/tangible/furniture/imperial/shared_data_terminal_s4.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(terminal)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def imperialTable(object, owner):
	if owner.getCashCredits() >= 75000:
		owner.setCashCredits(owner.getCashCredits() - 75000)
		core = main.NGECore.getInstance()
		table = core.objectService.createObject('object/tangible/furniture/imperial/shared_table_s1.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(table)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return

def electrobinoculars(object, owner):
	if owner.getCashCredits() >= 15000:
		owner.setCashCredits(owner.getCashCredits() - 15000)
		core = main.NGECore.getInstance()
		electrobinoculars = core.objectService.createObject('object/tangible/loot/generic_usable/shared_binoculars_s2_generic.iff', owner.getPlanet(), 'item_consumable_detect_hidden_02_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(electrobinoculars)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def advancedElectrobinoculars(object, owner):
	if owner.getCashCredits() >= 22500:
		owner.setCashCredits(owner.getCashCredits() - 22500)
		core = main.NGECore.getInstance()
		electrobinoculars = core.objectService.createObject('object/tangible/loot/generic_usable/shared_binoculars_s2_generic.iff', owner.getPlanet(), 'item_consumable_detect_hidden_02_02')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(electrobinoculars)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def superiorElectrobinoculars(object, owner):
	if owner.getCashCredits() >= 30000:
		owner.setCashCredits(owner.getCashCredits() - 30000)
		core = main.NGECore.getInstance()
		electrobinoculars = core.objectService.createObject('object/tangible/loot/generic_usable/shared_binoculars_s2_generic.iff', owner.getPlanet(), 'item_consumable_detect_hidden_02_03')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(electrobinoculars)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def experimentalElectrobinoculars(object, owner):
	if owner.getCashCredits() >= 37500:
		owner.setCashCredits(owner.getCashCredits() - 37500)
		core = main.NGECore.getInstance()
		electrobinoculars = core.objectService.createObject('object/tangible/loot/generic_usable/shared_binoculars_s1_generic.iff', owner.getPlanet(), 'item_consumable_detect_hidden_02_04')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(electrobinoculars)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def imperialBoots(object, owner):
	if owner.getCashCredits() >= 37500:
		owner.setCashCredits(owner.getCashCredits() - 37500)
		core = main.NGECore.getInstance()
		boots = core.objectService.createObject('object/tangible/wearables/boots/shared_boots_s14.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(boots)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def imperialJacket(object, owner):
	if owner.getCashCredits() >= 37500:
		owner.setCashCredits(owner.getCashCredits() - 37500)
		core = main.NGECore.getInstance()
		jacket = core.objectService.createObject('object/tangible/wearables/jacket/shared_jacket_s03.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(jacket)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def imperialPants(object, owner):
	if owner.getCashCredits() >= 37500:
		owner.setCashCredits(owner.getCashCredits() - 37500)
		core = main.NGECore.getInstance()
		pants = core.objectService.createObject('object/tangible/wearables/pants/shared_pants_s15.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(pants)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def imperialHat(object, owner):
	if owner.getCashCredits() >= 37500:
		owner.setCashCredits(owner.getCashCredits() - 37500)
		core = main.NGECore.getInstance()
		hat = core.objectService.createObject('object/tangible/wearables/hat/shared_hat_imp_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(hat)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def power5Pistol(object, owner):
	if owner.getCashCredits() >= 112500:
		owner.setCashCredits(owner.getCashCredits() - 112500)
		core = main.NGECore.getInstance()
		pistol = core.objectService.createObject('object/weapon/ranged/pistol/shared_pistol_power5.iff', owner.getPlanet(), 'factional_power_5_pistol')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(pistol)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def e11Carbine(object, owner):
	if owner.getCashCredits() >= 82500:
		owner.setCashCredits(owner.getCashCredits() - 82500)
		core = main.NGECore.getInstance()
		carbine = core.objectService.createObject('object/weapon/ranged/carbine/shared_carbine_e11.iff', owner.getPlanet(), 'factional_e11_carbine')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(carbine)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def sword(object, owner):
	if owner.getCashCredits() >= 30000:
		owner.setCashCredits(owner.getCashCredits() - 30000)
		core = main.NGECore.getInstance()
		sword = core.objectService.createObject('object/weapon/melee/sword/shared_sword_s01.iff', owner.getPlanet(), 'factional_sword')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(carbine)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def rocketLauncher(object, owner):
	if owner.getCashCredits() >= 150000:
		owner.setCashCredits(owner.getCashCredits() - 150000)
		core = main.NGECore.getInstance()
		launcher = core.objectService.createObject('object/weapon/ranged/heavy/shared_heavy_rocket_launcher.iff', owner.getPlanet(), 'factional_rocket_launcher')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(launcher)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		

		
def smallBlockTurret(object, owner):
	if owner.getCashCredits() >= 120000:
		owner.setCashCredits(owner.getCashCredits() - 120000)
		core = main.NGECore.getInstance()
		turret = core.objectService.createObject('object/tangible/deed/faction_perk/turret/shared_block_sm_deed.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(turret)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def smallDishTurret(object, owner):
	if owner.getCashCredits() >= 120000:
		owner.setCashCredits(owner.getCashCredits() - 120000)
		core = main.NGECore.getInstance()
		turret = core.objectService.createObject('object/tangible/deed/faction_perk/turret/shared_dish_sm_deed.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(turret)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def smallTowerTurret(object, owner):
	if owner.getCashCredits() >= 120000:
		owner.setCashCredits(owner.getCashCredits() - 120000)
		core = main.NGECore.getInstance()
		turret = core.objectService.createObject('object/tangible/deed/faction_perk/turret/shared_tower_sm_deed.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(turret)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def drx55Mine(object, owner):
	if owner.getCashCredits() >= 12000:
		owner.setCashCredits(owner.getCashCredits() - 12000)
		core = main.NGECore.getInstance()
		mine = core.objectService.createObject('object/weapon/mine/shared_wp_mine_drx55.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(mine)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def sr88CryoMine(object, owner):
	if owner.getCashCredits() >= 12000:
		owner.setCashCredits(owner.getCashCredits() - 12000)
		core = main.NGECore.getInstance()
		mine = core.objectService.createObject('object/weapon/mine/shared_wp_mine_anti_vehicle.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(mine)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def xgMine(object, owner):
	if owner.getCashCredits() >= 12000:
		owner.setCashCredits(owner.getCashCredits() - 12000)
		core = main.NGECore.getInstance()
		mine = core.objectService.createObject('object/weapon/mine/shared_wp_mine_xg.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(mine)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return

def barcSpeeder(object, owner):
	if owner.getCashCredits() >= 1050000:
		owner.setCashCredits(owner.getCashCredits() - 1050000)
		core = main.NGECore.getInstance()
		barc = core.objectService.createObject('object/tangible/deed/vehicle_deed/shared_barc_speeder_deed.iff', owner.getPlanet(), 'item_deed_barc_imperial_06_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(barc)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def mediumBlockTurret(object, owner):
	if owner.getCashCredits() >= 150000:
		owner.setCashCredits(owner.getCashCredits() - 150000)
		core = main.NGECore.getInstance()
		turret = core.objectService.createObject('object/tangible/deed/faction_perk/turret/shared_block_med_deed.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(turret)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def mediumTowerTurret(object, owner):
	if owner.getCashCredits() >= 150000:
		owner.setCashCredits(owner.getCashCredits() - 150000)
		core = main.NGECore.getInstance()
		turret = core.objectService.createObject('object/tangible/deed/faction_perk/turret/shared_tower_med_deed.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(turret)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def hqForwardOutpost(object, owner):
	if owner.getCashCredits() >= 525000:
		owner.setCashCredits(owner.getCashCredits() - 525000)
		core = main.NGECore.getInstance()
		hq = core.objectService.createObject('object/tangible/deed/faction_perk/hq/shared_hq_s01.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(hq)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def hqFieldHospital(object, owner):
	if owner.getCashCredits() >= 1050000:
		owner.setCashCredits(owner.getCashCredits() - 4500000)
		core = main.NGECore.getInstance()
		hq = core.objectService.createObject('object/tangible/deed/faction_perk/hq/shared_hq_s02.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(hq)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def sfhqForwardOutpost(object, owner):
	if owner.getCashCredits() >= 750000:
		owner.setCashCredits(owner.getCashCredits() - 750000)
		core = main.NGECore.getInstance()
		hq = core.objectService.createObject('object/tangible/deed/faction_perk/hq/shared_hq_s01_pvp.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(hq)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def hqTacticalCenter(object, owner):
	if owner.getCashCredits() >= 1875000:
		owner.setCashCredits(owner.getCashCredits() - 1875000)
		core = main.NGECore.getInstance()
		hq = core.objectService.createObject('object/tangible/deed/faction_perk/hq/shared_hq_s03.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(hq)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def sfhqTacticalCenter(object, owner):
	if owner.getCashCredits() >= 2325000:
		owner.setCashCredits(owner.getCashCredits() - 2325000)
		core = main.NGECore.getInstance()
		hq = core.objectService.createObject('object/tangible/deed/faction_perk/hq/shared_hq_s03_pvp.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(hq)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def sfhqDetachmentHeadquarters(object, owner):
	if owner.getCashCredits() >= 4500000:
		owner.setCashCredits(owner.getCashCredits() - 4500000)
		core = main.NGECore.getInstance()
		hq = core.objectService.createObject('object/tangible/deed/faction_perk/hq/shared_hq_s04_pvp.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(hq)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def largeBlockTurret(object, owner):
	if owner.getCashCredits() >= 210000:
		owner.setCashCredits(owner.getCashCredits() - 210000)
		core = main.NGECore.getInstance()
		turret = core.objectService.createObject('object/tangible/deed/faction_perk/turret/shared_block_lg_deed.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(turret)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def largeDishTurret(object, owner):
	if owner.getCashCredits() >= 210000:
		owner.setCashCredits(owner.getCashCredits() - 210000)
		core = main.NGECore.getInstance()
		turret = core.objectService.createObject('object/tangible/deed/faction_perk/turret/shared_dish_lg_deed.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(turret)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def largeTowerTurret(object, owner):
	if owner.getCashCredits() >= 210000:
		owner.setCashCredits(owner.getCashCredits() - 210000)
		core = main.NGECore.getInstance()
		turret = core.objectService.createObject('object/tangible/deed/faction_perk/turret/shared_tower_lg_deed.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(turret)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return

def specOpsLeggings(object, owner):
	if owner.getCashCredits() >= 46636:
		owner.setCashCredits(owner.getCashCredits() - 46636)
		core = main.NGECore.getInstance()
		leggings = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_leggings.iff', owner.getPlanet(), 'armor_pvp_spec_ops_imperial_black_leggings_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(leggings)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsBelt(object, owner):
	if owner.getCashCredits() >= 38863:
		owner.setCashCredits(owner.getCashCredits() - 38863)
		core = main.NGECore.getInstance()
		belt = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_utility_belt.iff', owner.getPlanet(), 'armor_pvp_spec_ops_imperial_black_belt_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(belt)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsBoots(object, owner):
	if owner.getCashCredits() >= 41454:
		owner.setCashCredits(owner.getCashCredits() - 41454)
		core = main.NGECore.getInstance()
		boots = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_boots.iff', owner.getPlanet(), 'armor_pvp_spec_ops_imperial_black_boots_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(boots)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsLeftBicep(object, owner):
	if owner.getCashCredits() >= 120000:
		owner.setCashCredits(owner.getCashCredits() - 120000)
		core = main.NGECore.getInstance()
		bicepl = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bicep_l.iff', owner.getPlanet(), 'armor_pvp_spec_ops_imperial_black_bicep_l_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(bicepl)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsRightBicep(object, owner):
	if owner.getCashCredits() >= 120000:
		owner.setCashCredits(owner.getCashCredits() - 120000)
		core = main.NGECore.getInstance()
		bicepr = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bicep_r.iff', owner.getPlanet(), 'armor_pvp_spec_ops_imperial_black_bicep_r_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(bicepr)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsLeftBracer(object, owner):
	if owner.getCashCredits() >= 120000:
		owner.setCashCredits(owner.getCashCredits() - 120000)
		core = main.NGECore.getInstance()
		bracerl = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bracer_l.iff', owner.getPlanet(), 'armor_pvp_spec_ops_imperial_black_bracer_l_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(bracerl)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsRightBracer(object, owner):
	if owner.getCashCredits() >= 120000:
		owner.setCashCredits(owner.getCashCredits() - 120000)
		core = main.NGECore.getInstance()
		bracerr = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_bracer_r.iff', owner.getPlanet(), 'armor_pvp_spec_ops_imperial_black_bracer_r_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(bracerr)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsGloves(object, owner):
	if owner.getCashCredits() >= 120000:
		owner.setCashCredits(owner.getCashCredits() - 120000)
		core = main.NGECore.getInstance()
		gloves = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_gloves.iff', owner.getPlanet(), 'armor_pvp_spec_ops_imperial_black_gloves_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(gloves)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsHelmet(object, owner):
	if owner.getCashCredits() >= 150000:
		owner.setCashCredits(owner.getCashCredits() - 150000)
		core = main.NGECore.getInstance()
		helmet = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_helmet.iff', owner.getPlanet(), 'armor_pvp_spec_ops_imperial_black_helmet_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(helmet)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsChestPlate(object, owner):
	if owner.getCashCredits() >= 225000:
		owner.setCashCredits(owner.getCashCredits() - 225000)
		core = main.NGECore.getInstance()
		chest = core.objectService.createObject('object/tangible/wearables/armor/stormtrooper/shared_armor_stormtrooper_pvp_officer_chest_plate.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(chest)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def specOpsDyeKit(object, owner):
	if owner.getCashCredits() >= 12954:
		owner.setCashCredits(owner.getCashCredits() - 12954)
		core = main.NGECore.getInstance()
		dyekit = core.objectService.createObject('object/tangible/item/shared_pvp_spec_ops_imperial_dye_kit.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(dyekit)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def paintingMightEmpire(object, owner):
	if owner.getCashCredits() >= 375000:
		owner.setCashCredits(owner.getCashCredits() - 375000)
		core = main.NGECore.getInstance()
		painting = core.objectService.createObject('object/tangible/painting/shared_painting_pvp_reward_imperial.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(painting)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def novaStarMedal(object, owner):
	if owner.getCashCredits() >= 80000:
		owner.setCashCredits(owner.getCashCredits() - 80000)
		core = main.NGECore.getInstance()
		necklace = core.objectService.createObject('object/tangible/wearables/necklace/shared_necklace_deepspace_empire_f.iff', owner.getPlanet(), 'item_pvp_imperial_sergeant_major_medal_03_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(necklace)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def factionalCommlink(object, owner):
	if owner.getCashCredits() >= 200000:
		owner.setCashCredits(owner.getCashCredits() - 200000)
		core = main.NGECore.getInstance()
		commlink = core.objectService.createObject('object/tangible/gcw/pvp_rank_rewards/shared_comm_link.iff', owner.getPlanet(), 'item_pvp_lieutenant_comm_link_imperial_reward_04_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(commlink)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def factionalBanner(object, owner):
	if owner.getCashCredits() >= 80000:
		owner.setCashCredits(owner.getCashCredits() - 80000)
		core = main.NGECore.getInstance()
		banner = core.objectService.createObject('object/tangible/gcw/pvp_rank_rewards/shared_pvp_imperial_battle_banner.iff', owner.getPlanet())
		inventory = owner.getSlottedObject('inventory')
		inventory.add(banner)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def colonelRing(object, owner):
	if owner.getCashCredits() >= 142500:
		owner.setCashCredits(owner.getCashCredits() - 142500)
		core = main.NGECore.getInstance()
		ring = core.objectService.createObject('object/tangible/wearables/ring/shared_ring_s03.iff', owner.getPlanet(), 'item_pvp_imperial_colonel_signet_ring_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(ring)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		

		
def windusGuile(object, owner):
	if owner.getCashCredits() >= 2000000:
		owner.setCashCredits(owner.getCashCredits() - 2000000)
		core = main.NGECore.getInstance()
		windu = core.objectService.createObject('object/tangible/component/weapon/lightsaber/shared_lightsaber_module_force_crystal.iff', owner.getPlanet(), 'item_color_crystal_02_20')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(windu)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def exceptionalCarbine(object, owner):
	if owner.getCashCredits() >= 200000:
		owner.setCashCredits(owner.getCashCredits() - 200000)
		core = main.NGECore.getInstance()
		carbine = core.objectService.createObject('object/weapon/ranged/carbine/shared_carbine_e11.iff', owner.getPlanet(), 'weapon_carbine_pvp_general_reward_05_01')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(carbine)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def starlightCarbine(object, owner):
	if owner.getCashCredits() >= 200000:
		owner.setCashCredits(owner.getCashCredits() - 200000)
		core = main.NGECore.getInstance()
		carbine = core.objectService.createObject('object/weapon/ranged/carbine/shared_carbine_pvp.iff', owner.getPlanet())
		carbine.setStringAttribute('faction_restriction', 'Imperial')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(carbine)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def crimsonNova(object, owner):
	if owner.getCashCredits() >= 200000:
		owner.setCashCredits(owner.getCashCredits() - 200000)
		core = main.NGECore.getInstance()
		nova = core.objectService.createObject('object/weapon/ranged/pistol/shared_pistol_pvp.iff', owner.getPlanet())
		nova.setStringAttribute('faction_restriction', 'Imperial')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(nova)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def vortexRifle(object, owner):
	if owner.getCashCredits() >= 200000:
		owner.setCashCredits(owner.getCashCredits() - 200000)
		core = main.NGECore.getInstance()
		rifle = core.objectService.createObject('object/weapon/ranged/rifle/shared_rifle_pvp.iff', owner.getPlanet())
		rifle.setStringAttribute('faction_restriction', 'Imperial')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(rifle)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return
		
def reaperCannon(object, owner):
	if owner.getCashCredits() >= 200000:
		owner.setCashCredits(owner.getCashCredits() - 200000)
		core = main.NGECore.getInstance()
		cannon = core.objectService.createObject('object/weapon/ranged/heavy/shared_heavy_pvp.iff', owner.getPlanet())
		cannon.setStringAttribute('faction_restriction', 'Imperial')
		inventory = owner.getSlottedObject('inventory')
		inventory.add(cannon)
	elif owner.sendSystemMessage('You do not have enough credits to purchase this item.', 0):
		return