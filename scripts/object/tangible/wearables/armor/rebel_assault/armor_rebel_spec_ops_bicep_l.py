import sys

def setup(core, object):
	object.setCustomizationVariable('/private/index_color_1', 5);
	object.setCustomizationVariable('/private/index_color_2', 0);
	object.setStfFilename('static_item_n')
	object.setStfName('armor_pvp_spec_ops_rebel_black_grey_bicep_l_05_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('armor_pvp_spec_ops_rebel_black_grey_bicep_l_05_01')
	object.setIntAttribute('no_trade', 1)
	object.setStringAttribute('faction_restriction', 'Rebel')
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_assault')
	object.setIntAttribute('cat_armor_standard_protection.kinetic', 6608)
	object.setIntAttribute('cat_armor_standard_protection.energy', 4608)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_heat', 5608)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_cold', 5608)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_acid', 5608)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_electricity', 5608)
	return