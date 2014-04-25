import sys

def setup(core, object):
	object.setIntAttribute("required_combat_level", 80);
	object.setAttackSpeed(0.6);
	object.setMaxRange(50);
	object.setDamageType("energy");
	object.setElementalType("cold");
	object.setElementalDamage(35);
	object.setMinDamage(338);
	object.setMaxDamage(676);
	object.setWeaponType(WeaponType.CARBINE);
	return