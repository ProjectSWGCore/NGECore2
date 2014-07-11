import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'creature/vehicle')
	core.buffService.addBuffToCreature(object, 'vehicle_at_rt', object)
	return