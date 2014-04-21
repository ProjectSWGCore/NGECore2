import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'ring/unity')
	object.setAttachment('objType', 'ring')
	object.setStfFilename('static_item_n')
	object.setStfName('item_ring_set_bh_dps_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_ring_set_bh_dps_01_01')
	object.setIntAttribute('required_combat_level', 85)
	object.setStringAttribute('class_required', 'Bounty Hunter')
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_dm_crit', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_buff_duration_line_bh_return_fire', 1)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_dm', 1)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_bh_dps_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_bh_dps_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_bh_dps_3)
	object.setAttachment('setBonus', 'set_bonus_bh_dps')
	return