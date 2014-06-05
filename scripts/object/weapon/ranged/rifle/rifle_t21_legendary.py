import sys

def setup(core, object):
	object.setIntAttribute('required_combat_level', 88)	
	object.setAttackSpeed(0.8);
	object.setMaxRange(64);
	object.setDamageType("energy");
	object.setMinDamage(550);
	object.setMaxDamage(1100);
	object.setWeaponType(0);
	return