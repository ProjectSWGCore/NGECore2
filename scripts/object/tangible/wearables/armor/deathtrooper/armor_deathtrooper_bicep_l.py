import sys

def setup(core, object):
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_battle')
	object.setIntAttribute('cat_armor_standard_protection.kinetic', 1820)
	object.setIntAttribute('cat_armor_standard_protection.energy', 1820)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_heat', 1820)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_cold', 1820)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_acid', 1820)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_electricity', 1820)
	return	