import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_necklace_set_commando_utility_a_01_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_necklace_set_commando_utility_a_01_01')
	object.setIntAttribute('required_combat_level', 85)
	object.setStringAttribute('class_required', 'Commando')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 20)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:expertise_block_chance', 2)
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:combat_block_value', 50)
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_commando_utility_a_1')
	object.setStringAttribute('@set_bonus:piece_bonus_count_4', '@set_bonus:set_bonus_commando_utility_a_2')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_commando_utility_a_3')
	object.setAttachment('setBonus', 'set_bonus_commando_utility_a')
	return