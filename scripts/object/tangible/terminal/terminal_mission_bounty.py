import sys

def setup(core, object):
	object.setAttachment("terminalType", 2)
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:terminal_mission_bounty', object.getPosition().x, object.getPosition().z, 41, 78, 0)
	return