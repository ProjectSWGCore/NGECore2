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
	radials.add(RadialOptions(0, 21, 1, '@treasure_map/treasure_map:search_area'))
	radials.add(RadialOptions(0, 7, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 21 and target:
			
		planetID = target.getAttachment('MapPlanet')
		if planetID!=owner.getPlanetId():
			owner.sendSystemMessage('@treasure_map/treasure_map:wrong_planet',0)
			
		if owner.getCombatFlag()!=0:
			owner.sendSystemMessage('@treasure_map/treasure_map:sys_no_combat',0)
			return
			
		if owner.getPosture()==10:
			owner.sendSystemMessage('@treasure_map/treasure_map:sys_no_mount',0)
			return
		
		
		areaLoc = target.getAttachment('MapAreaLocation');
		owner.sendSystemMessage('owner.getPosition().getDistance2D(areaLoc) %s' % owner.getPosition().getDistance2D(areaLoc),0)
		
		if owner.getPosition().getDistance2D(areaLoc)<20:
			owner.sendSystemMessage('@treasure_map/treasure_map:sys_success',0)
		
			treasureLocation = resources.common.SpawnPoint.getRandomPosition(owner.getPosition(), 1, 100, owner.getPlanetId());
			waypoint = main.NGECore.getInstance().objectService.createObject('object/waypoint/shared_waypoint.iff', owner.getPlanet(), treasureLocation.x, treasureLocation.z, treasureLocation.y)
			waypoint.setName('Exact Location of the Treasure')
			waypoint.setActive(True)
			waypoint.setColor(WaypointObject.YELLOW)
			waypoint.setPlanetCRC(CRC.StringtoCRC(owner.getPlanet().name))
			waypoint.setStringAttribute('', '')
			ghost = owner.getSlottedObject('ghost')
			ghost.waypointAdd(waypoint)			
			owner.sendSystemMessage('@treasure_map/treasure_map:sys_store_waypoint', 0)
			readMap = owner.getAttachment('LastReadMap')
			readMap.setAttachment('radial_filename', 'object/treasuremapExtract');
			readMap.setAttachment('MapExactLocation', treasureLocation);
		else:
			owner.sendSystemMessage('@treasure_map/treasure_map:sys_cant_pinpoint',0)
			
		
	return
	