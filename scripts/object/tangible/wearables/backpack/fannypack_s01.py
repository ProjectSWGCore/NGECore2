import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_fannypack_04_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_fannypack_04_01')

	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 50)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:luck_modified', 50)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 50)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 50)
	
	object.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_jedi_robe_1')
	object.setAttachment('setBonus', 'set_bonus_jedi_robe')
	return