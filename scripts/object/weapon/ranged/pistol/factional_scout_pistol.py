import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setAttackSpeed(0.4)
	object.setMaxRange(35)
	object.setDamageType('energy')
	object.setMinDamage(80)
	object.setMaxDamage(130)
	object.setWeaponType(WeaponType.PISTOL)
	return