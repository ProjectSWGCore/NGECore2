import sys

def setup(core, object):
	object.setStringAttribute('required_faction', 'Imperial')
	object.setIntAttribute('required_combat_level', 75)
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_battle')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 18)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:luck_modified', 6)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 12)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 12)
	object.setIntAttribute('cat_armor_standard_protection.kinetic', 5496)
	object.setIntAttribute('cat_armor_standard_protection.energy', 5496)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_heat', 5496)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_cold', 5496)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_acid', 5496)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_electricity', 5496)
	return	