import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_roadmap_belt_force_sensitive_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_roadmap_belt_force_sensitive_01_02')
	object.setIntAttribute('no_trade', 1)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:agility_modified', 15)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 20)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 15)
	object.setStringAttribute('class_required', 'Jedi')
	object.setIntAttribute('required_combat_level', 90)
	return
