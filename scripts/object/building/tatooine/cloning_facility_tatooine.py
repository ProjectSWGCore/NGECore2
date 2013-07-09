import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), 'Cloning Facility', object.getPosition().x, object.getPosition().z, 5, 0, 0)
	return
	