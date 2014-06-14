import sys

def setup(core, object):
	core.mapService.addLocation(object.getPlanet(), 'Hotel', object.getPosition().x, object.getPosition().z, 12, 0, 0)
	return