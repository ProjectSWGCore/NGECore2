import sys

def setup(core, Object)::
	object.setStringAttribute('required_skill', 'None')
	object.setStringAttribute('faction_restriction', 'Imperial')
	object.setAttackSpeed(0.6);
	object.setCustomName('Imperial E-11 Carbine');
	object.setMaxRange(50);
	object.setDamageType("energy");
	object.setMinDamage(200);
	object.setMaxDamage(350);
	object.setWeaponType(1);
	return