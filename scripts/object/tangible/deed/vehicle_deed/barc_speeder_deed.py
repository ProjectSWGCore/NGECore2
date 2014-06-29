import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/vehicleDeed')
	return

def use(core, actor, object):
	core.mountService.generateVehicle(actor, object, 'object/mobile/vehicle/shared_barc_speeder.iff', 'object/intangible/vehicle/shared_barc_speeder_pcd.iff')
	return
	