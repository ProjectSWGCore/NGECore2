import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	object.setStfFilename('static_item_n')
	object.setStfName('item_tow_proc_generic_03_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_tow_proc_generic_03_01')
	return
	
def use(core, actor, object):
	core.buffService.addBuffToCreature(actor, 'towPoisonResistAbsorb_3', actor)
	return