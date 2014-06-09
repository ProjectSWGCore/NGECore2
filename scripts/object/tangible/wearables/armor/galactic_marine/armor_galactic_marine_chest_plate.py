import sys

def setup(core, object):
	object.setStringAttribute('required_faction', 'Imperial')
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_assault')
	object.setIntAttribute('cat_armor_standard_protection.kinetic', 6440)
	object.setIntAttribute('cat_armor_standard_protection.energy', 4440)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_heat', 5440)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_cold', 5440)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_acid', 5440)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_electricity', 5440)
	return	