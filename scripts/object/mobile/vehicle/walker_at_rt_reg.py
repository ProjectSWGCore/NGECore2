import sys

def setup(core, object):
	return

def use(core, actor, object):
	core.buffService.addBuffToCreature(object, 'vehicle_at_rt', object)
	return