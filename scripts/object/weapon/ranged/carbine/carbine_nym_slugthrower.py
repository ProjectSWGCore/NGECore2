import sys

def setup(core, object):
	object.setIntAttribute("required_combat_level", 80);
	object.setAttackSpeed(0.6);
	object.setMaxRange(50);
	object.setDamageType("kinetic");
	object.setElementalType("acid");
	object.setElementalDamage(31);
	object.setMinDamage(304);
	object.setMaxDamage(608);
	object.setWeaponType(WeaponType.CARBINE);
	return