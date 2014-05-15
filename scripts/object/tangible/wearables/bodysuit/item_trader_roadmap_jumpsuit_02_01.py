import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_trader_roadmap_jumpsuit_02_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_trader_roadmap_jumpsuit_02_01')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:agility_modified', 2)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 1)
	object.setStringAttribute('class_required', 'Trader')
	object.setIntAttribute('required_combat_level', 30)
	return