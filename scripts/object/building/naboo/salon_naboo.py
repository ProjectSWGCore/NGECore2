import sys

def setup(core, object):
	core.mapService.addLocation(object.getPlanet(), 'Salon', object.getPosition().x, object.getPosition().z, 57, 0, 0)
	return