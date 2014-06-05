import sys

def setup(core, object):
	object.setIntAttribute('required_combat_level', 88)	
	object.setAttackSpeed(0.6);
	object.setMaxRange(50);
	object.setDamageType("energy");
	object.setMinDamage(400);
	object.setMaxDamage(850);
	object.setWeaponType(1);
	return