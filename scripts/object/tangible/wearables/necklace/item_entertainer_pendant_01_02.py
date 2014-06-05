import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_entertainer_pendant_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_entertainer_pendant_01_02')
	object.setIntAttribute('cat_skill_mod_bonus.@stat_n:dance_prop_assembly', 5)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:stamina_modified', 3)
	object.setStringAttribute('class_required', 'Entertainer')
	return
