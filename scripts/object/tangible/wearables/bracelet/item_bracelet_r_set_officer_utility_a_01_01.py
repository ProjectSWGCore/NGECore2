import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_bracelet_r_set_officer_utility_a_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_bracelet_r_set_officer_utility_a_01_01')
	object.setStringAttribute('class_required', 'Officer')
	object.setIntAttribute('required_combat_level', 85)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:cooldown_percent_of_group_buff', 4)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:fast_attack_line_of_heal', 1)
	# object.setIntAttribute('cat_skill_mod_bonus.@stat_n:XXX', 1) Rallypoint Duration Increase / couldnt find the effect yet
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_of_group_buff', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_of_purge', 3)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_officer_utility_a_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_officer_utility_a_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_officer_utility_a_3')
	object.setAttachment('setBonus', 'set_bonus_officer_utility_a')
	return