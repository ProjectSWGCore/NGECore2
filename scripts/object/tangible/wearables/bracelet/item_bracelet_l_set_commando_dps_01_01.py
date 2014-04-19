import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_bracelet_l_set_commando_dps_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_bracelet_l_set_commando_dps_01_01')
	object.setIntAttribute('required_combat_level', 85)
	object.setStringAttribute('class_required', 'Commando')
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_co_grenade', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_co_grenade', 3)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_freeshot_co_grenade', 2)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_dps_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_commando_dps_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_dps_3)
	object.setAttachment('setBonus', 'set_bonus_commando_dps')
	return