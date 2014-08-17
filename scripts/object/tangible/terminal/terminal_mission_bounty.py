import sys
from resources.datatables import TerminalType

def setup(core, object):
	object.setAttachment("terminalType", TerminalType.BOUNTY)
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:terminal_mission_bounty', object.getPosition().x, object.getPosition().z, 41, 78, 0)
	return