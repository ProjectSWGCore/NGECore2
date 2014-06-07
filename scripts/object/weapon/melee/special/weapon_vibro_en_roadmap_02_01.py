import sys
from resources.datatables import WeaponType

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_vibro_en_roadmap_02_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_vibro_en_roadmap_02_01')
	object.setStringAttribute('class_required', 'Entertainer')
	object.setIntAttribute('required_combat_level', 22)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:strength_modified', 4)
	object.setAttackSpeed(1);
	object.setMaxRange(5);
	object.setDamageType('kinetic');
	object.setMinDamage(147);
	object.setMaxDamage(300);
	object.setWeaponType(WeaponType.UNARMED);
	return