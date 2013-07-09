import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), 'Rebel Mission Terminal', object.getPosition().x, object.getPosition().z, 41, 45, 0)
	return
	