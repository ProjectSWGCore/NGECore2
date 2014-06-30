import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStringAttribute('faction_restriction', 'Imperial')
	object.setAttackSpeed(0.4)
	object.setCustomName('Imperial Power5 Pistol')
	object.setMaxRange(35)
	object.setDamageType('energy')
	object.setMinDamage(90)
	object.setMaxDamage(210)
	object.setWeaponType(WeaponType.PISTOL)
	return