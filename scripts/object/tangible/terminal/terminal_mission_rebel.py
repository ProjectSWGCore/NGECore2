import sys

def setup(core, object):
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:terminal_mission_rebel', object.getPosition().x, object.getPosition().z, 41, 79, 0)
	return
	