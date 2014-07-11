import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/vehicleDeed')
	object.setStfFilename('static_item_n')
	object.setStfName('item_deed_barc_imperial_06_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_deed_barc_imperial_06_01')
	object.setIntAttribute('no_trade', 1)
	return

def use(core, actor, object):
	core.mountService.generateVehicle(actor, object, 'object/mobile/vehicle/shared_barc_speeder_imperial.iff', 'object/intangible/vehicle/shared_barc_speeder_imperial_pcd.iff')
	return
	