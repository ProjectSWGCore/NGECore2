import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), 'Parking Garage', object.getPosition().x, object.getPosition().z, 6, 0, 0)
	return
	