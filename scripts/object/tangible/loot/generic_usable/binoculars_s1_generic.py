import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return
	
def use(core, actor, object):
	if object.getStfName() == ('item_consumable_detect_hidden_02_04'):
		core.buffService.addBuffToCreature(actor, 'detectHiddenConsumable100', actor)
	return