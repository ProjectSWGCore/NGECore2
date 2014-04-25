import sys

def setup(core, object):
	object.setIntAttribute("required_combat_level", 72);
	object.setIntAttribute("cat_stat_mod_bonus.@stat_n:precision_modified", 22);
	object.setAttackSpeed(0.6);
	object.setMaxRange(60);
	object.setDamageType("energy");
	object.setMinDamage(190);
	object.setMaxDamage(450);
	object.setWeaponType(WeaponType.CARBINE);
	return