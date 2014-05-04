import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setAttackSpeed(1)
	object.setDamageType("kinetic")
	object.setMinDamage(50)
	object.setMaxDamage(100)
	object.setMaxRange(5)
	object.setWeaponType(WeaponType.UNARMED)
	
	return