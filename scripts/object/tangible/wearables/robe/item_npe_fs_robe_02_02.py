import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_npe_fs_robe_02_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_npe_fs_robe_02_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 21)
	object.setStringAttribute('protection_level', 'Faint')
	object.setStringAttribute('class_required', 'Jedi')
	object.setIntAttribute('required_combat_level', 20)
	object.setAttachment('type', 'jedi_robe')
	return
