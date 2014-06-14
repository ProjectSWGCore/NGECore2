import sys

def setup(core, object):
	core.mapService.addLocation(object.getPlanet(), 'Cantina', object.getPosition().x, object.getPosition().z, 3, 0, 0)
	return