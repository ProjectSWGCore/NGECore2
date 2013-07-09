import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), 'Bazaar Terminal', object.getPosition().x, object.getPosition().z, 41, 43, 0)
	return
	