import sys

def setup(core, object):
	object.setIntAttribute('no_trade', 1)
	object.setAttachment('radial_filename', 'object/usable')
	object.setStfFilename('static_item_n')
	object.setStfName('invasion_gcw_binoculars_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('invasion_gcw_binoculars_01_01')
	return

def use(core, actor, object):
	core.buffService.addBuffToCreature(actor, 'gcw_invasion_binoculars', actor)
	return
