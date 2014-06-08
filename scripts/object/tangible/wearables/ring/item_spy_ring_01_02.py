import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_spy_ring_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_spy_ring_01_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 6)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 6)
	object.setStringAttribute('class_required', 'Spy')
	return
