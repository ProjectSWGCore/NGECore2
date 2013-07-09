import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), 'Starport', object.getPosition().x, object.getPosition().z, 15, 0, 0)
	return
	