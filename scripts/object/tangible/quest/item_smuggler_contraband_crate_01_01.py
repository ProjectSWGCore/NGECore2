import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_smuggler_contraband_crate_01_01')
	#object.setStfName('item_bracelet_l_set_bh_dps_01_01')
	object.setDetailFilename('smuggle_crate_d')
	object.setDetailName('item_smuggler_contraband_crate_01_01')
	object.setIntAttribute('required_combat_level', 10)
	object.setStringAttribute('class_required', 'Smuggler')
	return