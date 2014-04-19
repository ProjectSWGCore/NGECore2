import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_bracelet_r_set_medic_dps_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_bracelet_r_set_medic_dps_01_01')
	object.setStringAttribute('class_required', 'Medic')
	object.setIntAttribute('required_combat_level', 85)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_me_burst', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_me_dm', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_me_smash', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_buff_duration_line_me_enhance', 360)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_me_dm', 1)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_medic_dps_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_medic_dps_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_medic_dps_3')
	object.setAttachment('setBonus', 'set_bonus_medic_dps')
	return