import sys

def setup(core, object):
	object.setIntAttribute("required_combat_level", 1);
	object.setStringAttribute('class_required', 'Bounty Hunter')
	object.setAttackSpeed(0.6);
	object.setMaxRange(35);
	object.setDamageType("energy");
	object.setMinDamage(17);
	object.setMaxDamage(43);
	object.setWeaponType(WeaponType.CARBINE);
	return