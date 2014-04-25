import sys

def setup(core, object):
	object.setIntAttribute("required_combat_level", 88);
	object.setIntAttribute("cat_stat_mod_bonus.@stat_n:precision_modified", 11);
	object.setAttackSpeed(0.6);
	object.setMaxRange(50);
	object.setDamageType("energy");
	object.setElementalType("cold");
	object.setElementalDamage(28);
	object.setMinDamage(375);
	object.setMaxDamage(750);
	object.setWeaponType(WeaponType.CARBINE);
	return