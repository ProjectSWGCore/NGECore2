import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_knife_trader_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_knife_trader_roadmap_01_02')
	object.setStringAttribute('class_required', 'Trader')
	object.setIntAttribute('required_combat_level', 42)
	object.setAttackSpeed(1)
	object.setMinDamage(80)
	object.setMaxDamage(160)
	object.setMaxRange(5)
	object.setWeaponType(WeaponType.ONEHANDEDMELEE)
	object.setDamageType('kinetic')
	return