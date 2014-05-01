import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:terminal_mission_artisan', object.getPosition().x, object.getPosition().z, 41, 76, 0)
	return
	