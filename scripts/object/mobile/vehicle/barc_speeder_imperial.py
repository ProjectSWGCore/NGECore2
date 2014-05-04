import sys

def setup(core, object):
	core.buffService.addBuffToCreature(object, 'vehicle_pvp_barc_imperial', object)
	return

def use(core, actor, object):
	core.buffService.addBuffToCreature(object, 'vehicle_pvp_barc_imperial', object)
	return