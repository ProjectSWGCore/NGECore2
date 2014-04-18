import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'ring/unity')
	object.setAttachment('objType', 'ring')
	object.setStfFilename('static_item_n')
	object.setStfName('item_band_set_hero_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_band_set_hero_01_01')
	object.setIntAttribute('required_combat_level', 85)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 30)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 30)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:luck_modified', 30)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_hero_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_hero_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_hero_3')
	object.setAttachment('setBonus', 'set_bonus_hero')
	return