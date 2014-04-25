import sys

def setup(core, object):
	object.setIntAttribute("required_combat_level", 88);
	object.setAttackSpeed(1);
	object.setMaxRange(5);
	object.setDamageType("energy");
	object.setElementalType("acid");
	object.setElementalDamage(20);
	object.setMinDamage(900);
	object.setMaxDamage(1350);
	object.setWeaponType(WeaponType.TWOHANDEDMELEE);
	return
