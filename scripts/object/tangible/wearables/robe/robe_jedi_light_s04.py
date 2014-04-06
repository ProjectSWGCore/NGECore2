import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_jedi_robe_light_04_04')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_jedi_robe_light_04_04')
	
	object.setStringAttribute('protection_level', 'Radiant')
	object.setStringAttribute('class_required', 'Jedi')
	object.setIntAttribute('required_combat_level', 90)
	
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 250)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 250)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:agility_modified', 250)
	
	
	object.setStringAttribute('proc_name', '@ui_buff:proc_old_light_jedi_gift')
	object.setStringAttribute('@set_bonus:piece_bonus_count_2', '@set_bonus:set_bonus_jedi_robe_1')
	
	object.setAttachment('type', 'jedi_robe')
	object.setAttachment('setBonus', 'set_bonus_jedi_robe')
	
	return

	