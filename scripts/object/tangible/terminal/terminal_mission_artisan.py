import sys

def setup(core, object):

	core.mapService.addLocation(object.getPlanet(), 'Artisan Mission Terminal', object.getPosition().x, object.getPosition().z, 41, 21, 0)
	return
	