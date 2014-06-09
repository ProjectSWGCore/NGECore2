import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_polearm_02_03')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_polearm_02_03')
	object.setIntAttribute('required_combat_level', 1)
	object.setAttackSpeed(1);
	object.setMaxRange(5);
	object.setDamageType('kinetic');
	object.setMinDamage(43);
	object.setMaxDamage(75);
	object.setWeaponType(WeaponType.POLEARMMELEE);
	return
