import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), 'Shuttleport', object.getPosition().x, object.getPosition().z, 14, 0, 0)
	return
	