import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/vehicleDeed')
	return

def use(core, actor, object):
	core.mountService.generateVehicle(actor, object, 'object/mobile/vehicle/shared_flare_s_swoop.iff', 'object/intangible/vehicle/shared_flare_s_swoop_pcd.iff')
	return