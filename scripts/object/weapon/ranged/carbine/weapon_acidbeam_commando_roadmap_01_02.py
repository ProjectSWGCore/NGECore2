import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_acidbeam_commando_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_acidbeam_commando_roadmap_01_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 5)
	object.setStringAttribute('class_required', 'Commando')
	object.setIntAttribute('required_combat_level', 30)	
	object.setAttackSpeed(0.6);
	object.setMaxRange(50);
	object.setDamageType("energy");
	object.setMinDamage(109);
	object.setMaxDamage(218);
	object.setWeaponType(1);
	return