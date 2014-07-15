import sys

def setup(core, object):
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:rebel', object.getPosition().x, object.getPosition().z, 45, 47, 0)
	return
