import sys

def setup(core, object):
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:junk_dealer', object.getPosition().x, object.getPosition().z, 26, 81, 0)
	return