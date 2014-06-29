import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return
	
def use(core, actor, object):
	if object.getStfName() == ('item_consumable_detect_hidden_02_01'):
		core.buffService.addBuffToCreature(actor, 'detectHiddenConsumable20', actor)
	elif object.getStfName() == ('item_consumable_detect_hidden_02_02'):
		core.buffService.addBuffToCreature(actor, 'detectHiddenConsumable40', actor)
	elif object.getStfName() == ('item_consumable_detect_hidden_02_03'):
		core.buffService.addBuffToCreature(actor, 'detectHiddenConsumable65', actor)
	return