import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_pistol_en_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_pistol_en_roadmap_01_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 10)
	object.setStringAttribute('class_required', 'Entertainer')
	object.setIntAttribute('required_combat_level', 50)	
	object.setAttackSpeed(0.4);
	object.setMaxRange(35);
	object.setDamageType("energy");
	object.setMinDamage(131);
	object.setMaxDamage(261);
	object.setWeaponType(2);
	return