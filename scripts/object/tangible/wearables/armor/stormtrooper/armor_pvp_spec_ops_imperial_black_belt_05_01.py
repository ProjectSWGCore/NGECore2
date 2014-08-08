import sys

def setup(core, object):
	object.setCustomizationVariable('/private/index_color_1', 15);
	object.setCustomizationVariable('/private/index_color_2', 15);
	object.setCustomizationVariable('/private/index_color_3', 2);
	object.setStfFilename('static_item_n')
	object.setStfName('armor_pvp_spec_ops_imperial_black_belt_05_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('armor_pvp_spec_ops_imperial_black_belt_05_01')
	object.setIntAttribute('no_trade', 1)
	object.setStringAttribute('faction_restriction', 'Imperial')
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_assault')
	return