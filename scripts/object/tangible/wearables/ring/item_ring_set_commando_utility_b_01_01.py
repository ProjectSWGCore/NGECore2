import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'ring/unity')
	object.setAttachment('objType', 'ring')
	object.setStfFilename('static_item_n')
	object.setStfName('item_ring_set_commando_utility_b_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_ring_set_commando_utility_b_01_01')
	object.setIntAttribute('required_combat_level', 85)
	object.setStringAttribute('class_required', 'Commando')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 10)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 15)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_devastation_bonus', 5)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_b_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_commando_utility_b_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_b_3')
	object.setAttachment('setBonus', 'set_bonus_commando_utility_b')
	return