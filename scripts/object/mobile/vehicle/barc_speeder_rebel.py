import sys
	
def setup(core, object):
	core.buffService.addBuffToCreature(object, 'vehicle_pvp_barc_rebel', object)
	object.setAttachment('radial_filename', 'creature/vehicle')	
	return

def use(core, actor, object):
	core.buffService.addBuffToCreature(object, 'vehicle_pvp_barc_rebel', object)
	return