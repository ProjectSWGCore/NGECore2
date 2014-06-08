import sys

def setup(core, object):
	object.setStringAttribute('required_faction', 'Rebel')
	object.setIntAttribute('required_combat_level', 75)
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_battle')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 18)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:luck_modified', 6)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 12)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 12)
	return	