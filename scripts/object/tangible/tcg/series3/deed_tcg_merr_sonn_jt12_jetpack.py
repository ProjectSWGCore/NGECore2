import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/vehicleDeed')
	return

def use(core, actor, object):
	core.mountService.generateVehicle(actor, object, 'object/mobile/vehicle/shared_tcg_merr_sonn_jt12_jetpack.iff', 'object/intangible/vehicle/shared_tcg_merr_sonn_jt12_jetpack_pcd.iff')
	return
	