import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_bracelet_l_set_bh_utility_b_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_bracelet_l_set_bh_utility_b_01_01')
	object.setStringAttribute('class_required', 'Bounty Hunter')
	object.setIntAttribute('required_combat_level', 85)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_root', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:bh_dire_snare', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_dm_cc', 2)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_utility_b_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_bh_utility_b_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_utility_b_3')
	object.setAttachment('setBonus', 'set_bonus_bh_utility_b')
	return