import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_knuckler_ent_roadmap_02_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_knuckler_ent_roadmap_02_01')
	object.setStringAttribute('class_required', 'Entertainer')
	object.setIntAttribute('required_combat_level', 1)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 5)
	object.setAttackSpeed(1);
	object.setMaxRange(5);
	object.setDamageType('kinetic');
	object.setMinDamage(25);
	object.setMaxDamage(45);
	object.setWeaponType(WeaponType.UNARMED);
	return