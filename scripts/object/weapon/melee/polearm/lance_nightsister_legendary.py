import sys

def setup(core, object):
	object.setIntAttribute('required_combat_level', 88)	
	object.setAttackSpeed(1);
	object.setMaxRange(5);
	object.setDamageType("energy");
	object.setMinDamage(800);
	object.setMaxDamage(1200);
	object.setWeaponType(7);
	return