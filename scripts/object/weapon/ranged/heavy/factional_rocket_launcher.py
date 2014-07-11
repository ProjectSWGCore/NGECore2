import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setAttackSpeed(1)
	object.setMaxRange(64)
	object.setDamageType('energy')
	object.setMinDamage(400)
	object.setMaxDamage(775)
	object.setWeaponType(WeaponType.HEAVYWEAPON)
	object.setStringAttribute('wpn_elemental_type', 'heat')
	object.setStringAttribute('wpn_elemental_value', '70')
	return