import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_trader_pendant_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_trader_pendant_01_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:general_assembly', 5)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:hiring', 2)
	object.setStringAttribute('class_required', 'Trader')
	return
