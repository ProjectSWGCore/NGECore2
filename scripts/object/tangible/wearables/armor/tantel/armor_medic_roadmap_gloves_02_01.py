import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('armor_medic_roadmap_gloves_02_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('armor_medic_roadmap_gloves_02_01')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 3)
	object.setStringAttribute('class_required', 'Medic')
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_reconnaissance')
	return	