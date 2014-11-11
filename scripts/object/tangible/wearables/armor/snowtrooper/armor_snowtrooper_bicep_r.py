import sys

def setup(core, object):
	object.setStringAttribute('bio_link', '@obj_attr_n:bio_link_pending')
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_imperial_snow_armor_3')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_imperial_snow_armor_5')
	object.setStringAttribute('@set_bonus:piece_bonus_count_7', '@set_bonus:set_bonus_imperial_snow_armor_7')
	object.setStringAttribute('@set_bonus:piece_bonus_count_9', '@set_bonus:set_bonus_imperial_snow_armor_9')
	object.setStringAttribute('@set_bonus:piece_bonus_count_11', '@set_bonus:set_bonus_imperial_snow_armor_11')
	object.setAttachment('setBonus', 'set_bonus_imperial_snow_armor')
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_battle')
	object.setIntAttribute('cat_armor_standard_protection.kinetic', 5400)
	object.setIntAttribute('cat_armor_standard_protection.energy', 5400)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_heat', 5400)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_cold', 5400)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_acid', 5400)
	object.setIntAttribute('cat_armor_special_protection.special_protection_type_electricity', 5400)
	object.setStringAttribute('faction_restriction', 'Imperial')
	return	