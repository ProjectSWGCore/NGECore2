from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import resources.common.SpawnPoint
import engine.resources.scene.Point3D
import main.NGECore
import resources.common.Forager
from resources.objects.waypoint import WaypointObject
from engine.resources.common import CRC
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, '@treasure_map/treasure_map:extract_treasure'))
	radials.add(RadialOptions(0, 7, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 21 and target and owner:
		exactLoc = target.getAttachment('MapExactLocation')
		
		#if owner.getPosition().getDistance2D(owner.getPosition())<20:
		if owner.getPosition().getDistance2D(exactLoc)<20:
		
			target.setAttachment('radial_filename', 'object/usable')
			owner.sendSystemMessage('@treasure_map/treasure_map:sys_dist_here',0)
			#spawn guards
			forager = resources.common.Forager()
			forager.handleGuardSpawn(owner,target)
		else:
			owner.sendSystemMessage('@treasure_map/treasure_map:sys_dist_near',0)
			
		
	return
	