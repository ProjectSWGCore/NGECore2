import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'terminal/bank')
	core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:terminal_bank', object.getPosition().x, object.getPosition().z, 2, 42, 0)
	return
	