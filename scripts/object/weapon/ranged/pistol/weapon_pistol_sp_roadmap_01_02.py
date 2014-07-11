import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_pistol_sp_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_pistol_sp_roadmap_01_02')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 5)
	object.setStringAttribute('class_required', 'Spy')
	object.setIntAttribute('required_combat_level', 30)	
	object.setAttackSpeed(0.4);
	object.setMaxRange(35);
	object.setDamageType("energy");
	object.setMinDamage(73);
	object.setMaxDamage(145);
	object.setWeaponType(2);
	return