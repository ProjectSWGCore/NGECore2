import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/vehicleDeed')
	return

def use(core, actor, object):
	core.mountService.generateVehicle(actor, object, 'object/mobile/vehicle/shared_basilisk_war_droid.iff', 'object/intangible/vehicle/shared_basilisk_war_droid_pcd.iff')
	return