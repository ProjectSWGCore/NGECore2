import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setAttackSpeed(0.6)
	object.setMaxRange(50)
	object.setDamageType('energy')
	object.setMinDamage(350)
	object.setMaxDamage(700)
	object.setWeaponType(WeaponType.CARBINE)
	return