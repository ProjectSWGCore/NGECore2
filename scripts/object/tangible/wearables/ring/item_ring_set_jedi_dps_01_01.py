import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'ring/unity')
	object.setAttachment('objType', 'ring')
	object.setStfFilename('static_item_n')
	object.setStfName('item_ring_set_jedi_dps_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_ring_set_jedi_dps_01_01')
	object.setStringAttribute('class_required', 'Jedi')
	object.setIntAttribute('required_combat_level', 85)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_fs_dm', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_action_line_fs_sweep', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_fs_dm', 3)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_cooldown_line_fs_sweep', 3)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_jedi_dps_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_jedi_dps_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_jedi_dps_3')
	object.setAttachment('setBonus', 'set_bonus_jedi_dps')
	return