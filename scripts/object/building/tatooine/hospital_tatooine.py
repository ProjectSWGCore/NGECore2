import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), 'Medical Center', object.getPosition().x, object.getPosition().z, 13, 0, 0)
	return
	