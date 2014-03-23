import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_npe_fs_robe_02_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_npe_fs_robe_02_01')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:agility_modified', 10)
	object.setStringAttribute('class_required', 'Jedi')
	object.setIntAttribute('required_combat_level', 1)
	return
