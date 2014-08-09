import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('item_npe_officer_sidearm')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_npe_officer_sidearm')
	object.setStringAttribute('class_required', 'Officer')
	object.setIntAttribute('required_combat_level', 1)
	object.setAttackSpeed(0.4);
	object.setMaxRange(35);
	object.setDamageType("energy");
	object.setMinDamage(15);
	object.setMaxDamage(25);
	object.setWeaponType(WeaponType.PISTOL);
	return