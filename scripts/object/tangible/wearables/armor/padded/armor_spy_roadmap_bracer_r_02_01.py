import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('armor_spy_roadmap_bracer_r_02_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('armor_spy_roadmap_bracer_r_02_01')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 3)
	object.setStringAttribute('class_required', 'Spy')
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_battle')
	object.setIntAttribute('cat_armor_standard_protection.kinetic', 1640)
	object.setIntAttribute('cat_armor_standard_protection.energy', 1640)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_heat', 1640)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_cold', 1640)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_acid', 1640)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_electricity', 1640)
	return	