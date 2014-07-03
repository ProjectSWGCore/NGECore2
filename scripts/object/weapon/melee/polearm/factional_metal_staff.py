import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setAttackSpeed(1.0)
	object.setMaxRange(5)
	object.setDamageType('kinetic')
	object.setMinDamage(300)
	object.setMaxDamage(400)
	object.setWeaponType(WeaponType.POLEARMMELEE)
	return