import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	object.setStfFilename('static_item_n')
	object.setStfName('item_smuggler_clicky_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_smuggler_clicky_01_02')
	object.setStringAttribute('class_required', 'Smuggler')
	return

def use(core, actor, object):
	core.buffService.addBuffToCreature(actor, 'roadmapSmugglerClicky', actor)
	return
