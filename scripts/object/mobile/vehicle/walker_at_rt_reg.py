import sys

def setup(core, object):
	core.buffService.addBuffToCreature(object, 'vehicle_at_rt', object)
	return