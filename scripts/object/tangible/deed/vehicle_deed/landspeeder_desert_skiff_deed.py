import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/vehicleDeed')
	return

def use(core, actor, object):
	core.mountService.generateVehicle(actor, object, 'object/mobile/vehicle/shared_landspeeder_desert_skiff.iff', 'object/intangible/vehicle/shared_landspeeder_desert_skiff_pcd.iff')
	return