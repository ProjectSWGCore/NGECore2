import sys

def setup(core, object):
	object.setCustomizationVariable('/private/index_color_1', 0);
	object.setCustomizationVariable('/private/index_color_2', 5);
	object.setStfFilename('static_item_n')
	object.setStfName('armor_pvp_spec_ops_rebel_black_grey_boots_05_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('armor_pvp_spec_ops_rebel_black_grey_boots_05_01')
	object.setIntAttribute('no_trade', 1)
	object.setStringAttribute('faction_restriction', 'Rebel')
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_assault')
	return