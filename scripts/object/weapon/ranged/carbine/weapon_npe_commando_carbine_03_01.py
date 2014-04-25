import sys

def setup(core, object):
	object.setIntAttribute("required_combat_level", 1);
	object.setStringAttribute('class_required', 'Commando')
	object.setAttackSpeed(0.6);
	object.setMaxRange(35);
	object.setDamageType("energy");
	object.setMinDamage(20);
	object.setMaxDamage(40);
	object.setWeaponType(WeaponType.CARBINE);
	return