import sys

def setup(core, object):
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_battle')
	object.setStringAttribute('required_faction', 'Imperial')
	object.setIntAttribute('cat_armor_standard_protection.kinetic', 5664)
	object.setIntAttribute('cat_armor_standard_protection.energy', 5664)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_heat', 5664)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_cold', 5664)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_acid', 5664)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_electricity', 5664)
	return	