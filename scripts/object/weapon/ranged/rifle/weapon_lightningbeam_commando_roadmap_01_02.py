import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_lightningbeam_commando_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_lightningbeam_commando_roadmap_01_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 5)
	object.setStringAttribute('class_required', 'commando')
	object.setIntAttribute('required_combat_level', 30)	
	object.setAttackSpeed(0.8);
	object.setMaxRange(64);
	object.setDamageType("energy");
	object.setMinDamage(261);
	object.setMaxDamage(522);
	object.setWeaponType(2);
	return