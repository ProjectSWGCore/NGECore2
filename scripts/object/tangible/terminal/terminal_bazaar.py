import sys

def setup(core, object):
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:terminal_bazaar', object.getPosition().x, object.getPosition().z, 41, 43, 0)
	return
	