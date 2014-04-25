from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import resources.common.SpawnPoint
import engine.resources.scene.Point3D
import main.NGECore
from resources.objects.waypoint import WaypointObject
from engine.resources.common import CRC
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, '@treasure_map/treasure_map:use'))
	radials.add(RadialOptions(0, 7, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 21 and target:
		
		mapPlanet = target.getAttachment('MapPlanet')
		planet = core.terrainService.getPlanetByID(mapPlanet)
		if planet==None:
			owner.sendSystemMessage('The treasure map disk is faulty, no Planet',0)
			return
			
		planetName = planet.getName()	
		#if owner.getPlanetId()!=mapPlanet:
			#owner.sendSystemMessage('The treasure is buried on ' + planetName,0)
			#return
			
		window = core.suiService.createSUIWindow('Script.listBox', owner, target, 0)
		window.setProperty('bg.caption.lblTitle:Text', '@treasure_map/treasure_map:title_'+target.getAttachment('MapSTFName'))
		window.setProperty('Prompt.lblPrompt:Text', '@treasure_map/treasure_map:text_'+target.getAttachment('MapSTFName'))
		
		window.setProperty('btnCancel:visible', 'True')
		window.setProperty('btnOk:visible', 'True')
		window.setProperty('btnCancel:Text', '@treasure_map/treasure_map:close')
		window.setProperty('btnOk:Text', '@treasure_map/treasure_map:store_waypoint')		
		returnList = Vector()
		window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, storeWaypoint)
		window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, close)	
		
		owner.setAttachment('LastReadMap',target)
		
		core.suiService.openSUIWindow(window);
	
	return
	
def storeWaypoint(owner, window, eventType, returnList):
	planetCenter = engine.resources.scene.Point3D(0,0,0)
	#treasureLocation = resources.common.SpawnPoint.getRandomPosition(planetCenter, 2000, 6000, owner.getPlanetId());
	treasureLocation = resources.common.SpawnPoint.getRandomPosition(owner.getPosition(), 1, 50, owner.getPlanetId());
	waypoint = main.NGECore.getInstance().objectService.createObject('object/waypoint/shared_waypoint.iff', owner.getPlanet(), treasureLocation.x, treasureLocation.z, treasureLocation.y)
	waypoint.setName('Treasure Map')
	waypoint.setActive(True)
	waypoint.setColor(WaypointObject.YELLOW)
	waypoint.setPlanetCRC(CRC.StringtoCRC(owner.getPlanet().name))
	waypoint.setStringAttribute('', '')
	ghost = owner.getSlottedObject('ghost')
	ghost.waypointAdd(waypoint)
	owner.sendSystemMessage('@treasure_map/treasure_map:sys_store_waypoint', 0)
	readMap = owner.getAttachment('LastReadMap')
	readMap.setAttachment('radial_filename', 'object/treasuremapRead');
	readMap.setAttachment('MapAreaLocation', treasureLocation);
	return


def close(owner, window, eventType, returnList):

	return
	