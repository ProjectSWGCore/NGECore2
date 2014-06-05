import sys

def setup(core, object):
	object.setIntAttribute('required_combat_level', 88)	
	object.setAttackSpeed(0.4);
	object.setMaxRange(35);
	object.setDamageType("energy");
	object.setMinDamage(250);
	object.setMaxDamage(500);
	object.setWeaponType(2);
	return