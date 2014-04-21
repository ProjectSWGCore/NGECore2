import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_necklace_set_smuggler_dps_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_necklace_set_smuggler_dps_01_01')
	object.setStringAttribute('class_required', 'Smuggler')
	object.setIntAttribute('required_combat_level', 85)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_sm_dm', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_sm_dm', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_freeshot_sm_dm', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_sm_dm', 2)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_smuggler_dps_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_smuggler_dps_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_smuggler_dps_3')
	object.setAttachment('setBonus', 'set_bonus_smuggler_dps')
	return