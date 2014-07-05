import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	object.setStfFilename('static_item_n')
	object.setStfName('item_bounty_hunter_clicky_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_bounty_hunter_clicky_01_02')
	object.setStringAttribute('class_required', 'Bounty Hunter')
	return

def use(core, actor, object):
	core.buffService.addBuffToCreature(actor, 'roadmapBhClicky', actor)
	return
