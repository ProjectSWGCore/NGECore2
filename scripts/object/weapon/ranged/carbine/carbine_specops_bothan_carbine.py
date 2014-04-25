
import sys

def setup(core, object):
	object.setIntAttribute("required_combat_level", 80);
	object.setAttackSpeed(0.6);
	object.setMaxRange(50);
	object.setDamageType("energy");
	object.setMinDamage(323);
	object.setMaxDamage(647);
	object.setWeaponType(WeaponType.CARBINE);
	return