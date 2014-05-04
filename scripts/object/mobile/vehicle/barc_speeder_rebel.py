import sys

def setup(core, object):
	return

def use(core, actor, object):
	core.buffService.addBuffToCreature(object, 'vehicle_pvp_barc_rebel', object)
	return