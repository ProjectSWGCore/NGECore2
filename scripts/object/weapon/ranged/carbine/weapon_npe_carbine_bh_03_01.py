import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_npe_carbine_bh_03_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_npe_carbine_bh_03_01')
	object.setStringAttribute('class_required', 'Bounty Hunter')
	object.setIntAttribute('required_combat_level', 1)	
	object.setAttackSpeed(0.6);
	object.setMaxRange(35);
	object.setDamageType("energy");
	object.setMinDamage(17);
	object.setMaxDamage(43);
	object.setWeaponType(WeaponType.CARBINE);
	return