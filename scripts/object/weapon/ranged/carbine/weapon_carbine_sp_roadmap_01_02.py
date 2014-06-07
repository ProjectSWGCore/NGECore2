import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_carbine_sp_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_carbine_sp_roadmap_01_02')
	object.setStringAttribute('class_required', 'Spy')
	object.setIntAttribute('required_combat_level', 50)	
	object.setAttackSpeed(0.6);
	object.setMaxRange(55);
	object.setDamageType("energy");
	object.setMinDamage(195);
	object.setMaxDamage(392);
	object.setWeaponType(1);
	return