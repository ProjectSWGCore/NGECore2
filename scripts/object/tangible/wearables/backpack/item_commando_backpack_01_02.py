import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_commando_backpack_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_commando_backpack_01_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:agility_modified', 5)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 5)
	object.setStringAttribute('class_required', 'Commando')
	return	
