import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_vibro_en_roadmap_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_vibro_en_roadmap_01_02')
	object.setStringAttribute('class_required', 'Entertainer')
	object.setIntAttribute('required_combat_level', 30)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 5)
	object.setAttackSpeed(1);
	object.setMaxRange(5);
	object.setDamageType('kinetic');
	object.setMinDamage(190);
	object.setMaxDamage(375);
	object.setWeaponType(WeaponType.UNARMED);
	return