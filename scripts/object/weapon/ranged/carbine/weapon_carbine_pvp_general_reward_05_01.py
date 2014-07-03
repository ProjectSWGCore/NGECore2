import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStringAttribute('required_skill', 'None')
	object.setStringAttribute('faction_restriction', 'Imperial')
	object.setAttackSpeed(0.6);
	object.setMaxRange(50);
	object.setDamageType("energy");
	object.setMinDamage(310);
	object.setMaxDamage(550);
	object.setWeaponType(WeaponType.CARBINE);
	return