import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_necklace_set_spy_utility_a_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_necklace_set_spy_utility_a_01_01')
	object.setIntAttribute('required_combat_level', 85)
	object.setStringAttribute('class_required', 'Spy')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 25)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_sp_smoke', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_duration_sp_preparation', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_sp_preparation', 6)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_spy_utility_a_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_spy_utility_a_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_spy_utility_a_3')
	object.setAttachment('setBonus', 'set_bonus_spy_utility_a')
	return
