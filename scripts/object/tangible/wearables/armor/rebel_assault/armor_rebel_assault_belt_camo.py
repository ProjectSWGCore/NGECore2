import sys

def setup(core, object):
	object.setIntAttribute('required_combat_level', 80)
	object.setStringAttribute('faction_restriction', 'Rebel')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 15)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:luck_modified', 10)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 20)
	return	