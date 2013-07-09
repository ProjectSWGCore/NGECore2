import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'bank')
	core.mapService.addLocation(object.getPlanet(), 'Bank Terminal', object.getPosition().x, object.getPosition().z, 41, 42, 0)
	return
	