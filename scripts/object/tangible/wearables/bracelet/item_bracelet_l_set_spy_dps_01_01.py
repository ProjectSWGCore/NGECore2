import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_bracelet_l_set_spy_dps_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_bracelet_l_set_spy_dps_01_01')
	object.setIntAttribute('required_combat_level', 85)
	object.setStringAttribute('class_required', 'Spy')
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_sp_dm', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_sp_dot', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_sp_dm', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_freeshot_sp_dm', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_sp_dm', 2)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_spy_dps_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_spy_dps_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_spy_dps_3')
	object.setAttachment('setBonus', 'set_bonus_spy_dps')
	return