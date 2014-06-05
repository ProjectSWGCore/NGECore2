import sys

def setup(core, object):
	object.setIntAttribute('required_combat_level', 88)	
	object.setAttackSpeed(1);
	object.setMaxRange(5);
	object.setDamageType("energy");
	object.setMinDamage(900);
	object.setMaxDamage(1350);
	object.setWeaponType(5);
	return