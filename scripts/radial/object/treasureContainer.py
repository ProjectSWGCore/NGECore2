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
	radials.clear()
	radials.add(RadialOptions(0, 21, 1, 'Open'))
	radials.add(RadialOptions(0, 7, 1, 'Examine'))
	return
	
def handleSelection(core, owner, target, option):
	
	if option == 21 and target:
		
		if target.getAttachment('TreasureExtractorID')!=owner.getObjectID():
			owner.sendSystemMessage('@treasure_map/treasure_map:wrong_player',0);
			return
			
		forager = resources.common.Forager()
		if forager.countAliveGuards(target)>0:
			owner.sendSystemMessage('@treasure_map/treasure_map:kill_guards_message',0);
			return
			
		core.simulationService.openContainer(owner, target);
		
	return
	