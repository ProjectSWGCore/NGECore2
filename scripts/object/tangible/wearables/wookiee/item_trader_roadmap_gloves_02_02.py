import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_trader_roadmap_gloves_02_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_trader_roadmap_gloves_02_02')
	object.setIntAttribute('no_trade', 1)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 3)
	object.setStringAttribute('class_required', 'Trader)
	return	