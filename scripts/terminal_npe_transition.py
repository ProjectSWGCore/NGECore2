#This file is to only be edited by Project SWG Staff.
#Do not commit any changes to this file unless authorized.


from resources.common import RadialOptions
from resources.common import OutOfBand
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
from java.lang import System
from java.lang import Long
from java.util import Map
from java.util import TreeMap
from resources.datatables import WeaponType
from engine.resources.scene import Point3D
import main.NGECore
import sys
import math

core = main.NGECore.getInstance()

def screenOne (core, owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please select a planet and town. Our development team is working hard on fixing the crash to desktop problem. Try to avoid player populated areas. LEAVE THIS SPOT AS SOON AS POSSIBLE. Thank you.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, screenOneCallback)
	window.addListBoxMenuItem('Corellia', 0)
	window.addListBoxMenuItem('Dantooine', 1)
	window.addListBoxMenuItem('Dathomir', 2)
	window.addListBoxMenuItem('Endor', 3)
	window.addListBoxMenuItem('Lok', 4)
	window.addListBoxMenuItem('Naboo', 5)
	window.addListBoxMenuItem('Rori', 6)
	window.addListBoxMenuItem('Talus', 7)
	window.addListBoxMenuItem('Tatooine', 8)
	window.addListBoxMenuItem('Yavin 4', 9)
	core.suiService.openSUIWindow(window);
		
def screenOneCallback(owner, window, eventType, returnList):
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		corelliaHandler(owner)
		return
	if returnList.get(0)=='1':
		dantooineHandler(owner)
		return
	if returnList.get(0)=='2':
		dathomirHandler(owner)
		return
	if returnList.get(0)=='3':
		endorHandler(owner)
		return
	if returnList.get(0)=='4':
		lokHandler(owner)
		return
	if returnList.get(0)=='5':
		nabooHandler(owner)
		return
	if returnList.get(0)=='6':
		roriHandler(owner)
		return
	if returnList.get(0)=='7':
		talusHandler(owner)
		return
	if returnList.get(0)=='8':
		tatooineHandler(owner)
		return
	if returnList.get(0)=='9':
		yavin4Handler(owner)
		return

	return

#################################################################################################################################################
	
def	corelliaHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Character Builder Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Welcome to Project SWG Test Center.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, corelliaHandlerCallback)
	window.addListBoxMenuItem('Coronet', 0)
	window.addListBoxMenuItem('Tyrena', 1)
	window.addListBoxMenuItem('Bela Vistal', 2)
	window.addListBoxMenuItem('Kor Vella', 3)
	window.addListBoxMenuItem('Doaba Guerfel', 4)
	window.addListBoxMenuItem('Vreni Island', 5)
	
	core.suiService.openSUIWindow(window);
	
def corelliaHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		coronetTravel(owner)
		return
	if returnList.get(0)=='1':
		tyrenaTravel(owner)
		return
	if returnList.get(0)=='2':
		belavistalTravel(owner)
		return
	if returnList.get(0)=='3':
		korvellaTravel(owner)
		return
	if returnList.get(0)=='4':
		doabaguerfelTravel(owner)
		return
	if returnList.get(0)=='5':
		vreniislandTravel(owner)
		return

	return

def coronetTravel(owner):
	position = Point3D(-51, 28, -4735)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("corellia"), position, owner.getOrientation(), None)
	return
	
def tyrenaTravel(owner):
	position = Point3D(-4975, 21, -2230)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("corellia"), position, owner.getOrientation(), None)
	return

def belavistalTravel(owner):
	position = Point3D(6644, 330, -5922)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("corellia"), position, owner.getOrientation(), None)
	return
	
def korvellaTravel(owner):
	position = Point3D(-3775, 86, 3234)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("corellia"), position, owner.getOrientation(), None)
	return

def doabaguerfelTravel(owner):
	position = Point3D(3377, 308, 5605)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("corellia"), position, owner.getOrientation(), None)
	return
	
def vreniislandTravel(owner):
	position = Point3D(-5551, 15, -6059)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("corellia"), position, owner.getOrientation(), None)
	return

#################################################################################################################################################

def	dantooineHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please try to avoid player populated areas. We are working on a fix for this problem.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, dantooineHandlerCallback)
	window.addListBoxMenuItem('Mining Outpost', 0)
	window.addListBoxMenuItem('Imperial Outpost', 1)
	window.addListBoxMenuItem('Pirate Outpost', 2)
	
	core.suiService.openSUIWindow(window);
	
def dantooineHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		dantminingoutpostTravel(owner)
		return
	if returnList.get(0)=='1':
		dantimperialoutpostTravel(owner)
		return
	if returnList.get(0)=='2':
		dantpirateoutpostTravel(owner)
		return

	return

def dantminingoutpostTravel(owner):
	position = Point3D(-635, 3, 2507)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("dantooine"), position, owner.getOrientation(), None)
	return
	
def dantimperialoutpostTravel(owner):
	position = Point3D(-4208, 3, -2350)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("dantooine"), position, owner.getOrientation(), None)
	return

def dantpirateoutpostTravel(owner):
	position = Point3D(1569, 4, -6415)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("dantooine"), position, owner.getOrientation(), None)
	return


#################################################################################################################################################

def	dathomirHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please try to avoid player populated areas. We are working on a fix for this problem.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, dathomirHandlerCallback)
	window.addListBoxMenuItem('Science Outpost', 0)
	window.addListBoxMenuItem('Trade Outpost', 1)
	window.addListBoxMenuItem('Quarantine Zone', 2)
	
	core.suiService.openSUIWindow(window);
	
def dathomirHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		dathscienceoutpostTravel(owner)
		return
	if returnList.get(0)=='1':
		dathtradeoutpostTravel(owner)
		return
	if returnList.get(0)=='2':
		dathquarantinezoneTravel(owner)
		return

	return

def dathscienceoutpostTravel(owner):
	position = Point3D(-49, 18, -1584)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("dathomir"), position, owner.getOrientation(), None)
	return
	
def dathtradeoutpostTravel(owner):
	position = Point3D(618, 6, 3092)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("dathomir"), position, owner.getOrientation(), None)
	return

def dathquarantinezoneTravel(owner):
	position = Point3D(-5691, 511, -6467)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("dathomir"), position, owner.getOrientation(), None)
	return
	
#################################################################################################################################################

def	endorHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please try to avoid player populated areas. We are working on a fix for this problem.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, endorHandlerCallback)
	window.addListBoxMenuItem('Smuggler Outpost', 0)
	window.addListBoxMenuItem('Research Outpost', 1)
	
	core.suiService.openSUIWindow(window);
	
def endorHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		smuggleroutpostTravel(owner)
		return
	if returnList.get(0)=='1':
		researchoutpostTravel(owner)

	return

def smuggleroutpostTravel(owner):
	position = Point3D(-950, 73, 1553)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("endor"), position, owner.getOrientation(), None)
	return
	
def researchoutpostTravel(owner):
	position = Point3D(3201, 24, -3499)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("endor"), position, owner.getOrientation(), None)
	return

#################################################################################################################################################

def	lokHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please try to avoid player populated areas. We are working on a fix for this problem.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, lokHandlerCallback)
	window.addListBoxMenuItem('Nyms Stronghold', 0)
	window.addListBoxMenuItem('Imperial Outpost', 1)
	
	core.suiService.openSUIWindow(window);
	
def lokHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		nymTravel(owner)
		return
	if returnList.get(0)=='1':
		lokimpoutpostTravel(owner)
		return

	return

def nymTravel(owner):
	position = Point3D(459, 9, 5494)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("lok"), position, owner.getOrientation(), None)
	return
	
def lokimpoutpostTravel(owner):
	position = Point3D(-1795, 11, -3086)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("lok"), position, owner.getOrientation(), None)
	return
	
#################################################################################################################################################

def	nabooHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please try to avoid player populated areas. We are working on a fix for this problem.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, nabooHandlerCallback)
	window.addListBoxMenuItem('Kaadara', 0)
	window.addListBoxMenuItem('Deeja Peak', 1)
	window.addListBoxMenuItem('Moenia', 2)
	window.addListBoxMenuItem('Lake Retreat', 3)
	window.addListBoxMenuItem('Keren', 4)
	window.addListBoxMenuItem('Theed', 5)
	
	core.suiService.openSUIWindow(window);
	
def nabooHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		kaadaraTravel(owner)
		return
	if returnList.get(0)=='1':
		deejapeakTravel(owner)
		return
	if returnList.get(0)=='2':
		moeniaTravel(owner)
		return
	if returnList.get(0)=='3':
		lakeretreatTravel(owner)
		return
	if returnList.get(0)=='4':
		kerenTravel(owner)
		return
	if returnList.get(0)=='5':
		theedTravel(owner)
		return

	return

def kaadaraTravel(owner):
	position = Point3D(5295, -192, 6664)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("naboo"), position, owner.getOrientation(), None)
	return
	
def deejapeakTravel(owner):
	position = Point3D(5331, 327, -1576)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("naboo"), position, owner.getOrientation(), None)
	return

def moeniaTravel(owner):
	position = Point3D(4961, 3, -4892)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("naboo"), position, owner.getOrientation(), None)
	return
	
def lakeretreatTravel(owner):
	position = Point3D(-5494, -150, -21)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("naboo"), position, owner.getOrientation(), None)
	return

def kerenTravel(owner):
	position = Point3D(1352, 13, 2768)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("naboo"), position, owner.getOrientation(), None)
	return
	
def theedTravel(owner):
	position = Point3D(-4858, 5, 4164)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("naboo"), position, owner.getOrientation(), None)
	return

#################################################################################################################################################

def	roriHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please try to avoid player populated areas. We are working on a fix for this problem.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, roriHandlerCallback)
	window.addListBoxMenuItem('Narmle', 0)
	window.addListBoxMenuItem('Rebel Outpost', 1)
	window.addListBoxMenuItem('Restuss', 2)
	
	core.suiService.openSUIWindow(window);
	
def roriHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		narmleTravel(owner)
		return
	if returnList.get(0)=='1':
		rebeloutpostTravel(owner)
		return
	if returnList.get(0)=='2':
		restussTravel(owner)
		return

	return

def narmleTravel(owner):
	position = Point3D(-5374, 80, -2188)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("rori"), position, owner.getOrientation(), None)
	return
	
def rebeloutpostTravel(owner):
	position = Point3D(3672, 96, -6421)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("rori"), position, owner.getOrientation(), None)
	return

def restussTravel(owner):
	position = Point3D(5281, 80, 6171)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("rori"), position, owner.getOrientation(), None)
	return

#################################################################################################################################################

def	talusHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please try to avoid player populated areas. We are working on a fix for this problem.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, talusHandlerCallback)
	window.addListBoxMenuItem('Dearic', 0)
	window.addListBoxMenuItem('Nashal', 1)
	window.addListBoxMenuItem('Imperial Outpost', 2)
	
	core.suiService.openSUIWindow(window);
	
def talusHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		dearicTravel(owner)
		return
	if returnList.get(0)=='1':
		nashalTravel(owner)
		return
	if returnList.get(0)=='2':
		talusimpoutpostTravel(owner)
		return

	return

def dearicTravel(owner):
	position = Point3D(263, 6, -2952)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("talus"), position, owner.getOrientation(), None)
	return
	
def nashalTravel(owner):
	position = Point3D(4453, 2, 5354)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("talus"), position, owner.getOrientation(), None)
	return

def talusimpoutpostTravel(owner):
	position = Point3D(-2226, 20, 2319)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("talus"), position, owner.getOrientation(), None)
	return

#################################################################################################################################################

def	tatooineHandler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please try to avoid player populated areas. We are working on a fix for this problem.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, tatooineHandlerCallback)
	window.addListBoxMenuItem('Bestine', 0)
	window.addListBoxMenuItem('Mos Eisley', 1)
	window.addListBoxMenuItem('Anchorhead', 2)
	window.addListBoxMenuItem('Mos Entha', 3)
	window.addListBoxMenuItem('Wayfar', 4)
	window.addListBoxMenuItem('Mos Espa', 5)
	
	core.suiService.openSUIWindow(window);
	
def tatooineHandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		bestineTravel(owner)
		return
	if returnList.get(0)=='1':
		moseisleyTravel(owner)
		return
	if returnList.get(0)=='2':
		anchorheadTravel(owner)
		return
	if returnList.get(0)=='3':
		mosenthaTravel(owner)
		return
	if returnList.get(0)=='4':
		wayfarTravel(owner)
		return
	if returnList.get(0)=='5':
		mosespaTravel(owner)
		return
		
	return

def bestineTravel(owner):
	position = Point3D(-1376, 12, -3576)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("tatooine"), position, owner.getOrientation(), None)
	return
	
def moseisleyTravel(owner):
	position = Point3D(3619, 5, -4801)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("tatooine"), position, owner.getOrientation(), None)
	return

def anchorheadTravel(owner):
	position = Point3D(48, 52, -5319)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("tatooine"), position, owner.getOrientation(), None)
	return
	
def mosenthaTravel(owner):
	position = Point3D(1238, 7, 3062)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("tatooine"), position, owner.getOrientation(), None)
	return

def wayfarTravel(owner):
	position = Point3D(-5089, 75, -6594)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("tatooine"), position, owner.getOrientation(), None)
	return

def mosespaTravel(owner):
	position = Point3D(-2829, 5, 2080)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("tatooine"), position, owner.getOrientation(), None)
	return

#################################################################################################################################################

def	yavin4Handler(owner):
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0);
	window.setProperty('bg.caption.lblTitle:Text', 'Spawn Terminal')
	window.setProperty('Prompt.lblPrompt:Text', 'Please try to avoid player populated areas. We are working on a fix for this problem.')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, yavin4HandlerCallback)
	window.addListBoxMenuItem('Imperial Base', 0)
	window.addListBoxMenuItem('Labor Outpost', 1)
	window.addListBoxMenuItem('Mining Outpost', 2)
	
	core.suiService.openSUIWindow(window);
	
def yavin4HandlerCallback(owner, window, eventType, returnList):
	inventory = owner.getSlottedObject('inventory')
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		yavin4imperialbaseTravel(owner)
		return
	if returnList.get(0)=='1':
		yavin4laboroutpostTravel(owner)
		return
	if returnList.get(0)=='2':
		yavin4miningoutpostTravel(owner)
		return

	return

def yavin4imperialbaseTravel(owner):
	position = Point3D(4054, 37, -6216)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("yavin4"), position, owner.getOrientation(), None)
	return
	
def yavin4laboroutpostTravel(owner):
	position = Point3D(-6921, 73, -5726)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("yavin4"), position, owner.getOrientation(), None)
	return

def yavin4miningoutpostTravel(owner):
	position = Point3D(-267, 35, 4896)
	core.simulationService.transferToPlanet(owner, core.terrainService.getPlanetByName("yavin4"), position, owner.getOrientation(), None)
	return