import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), 'Imperial Mission Terminal', object.getPosition().x, object.getPosition().z, 41, 46, 0)
	return
	