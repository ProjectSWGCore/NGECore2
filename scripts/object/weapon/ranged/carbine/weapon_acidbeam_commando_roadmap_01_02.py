import sys

def setup(core, object):
	object.setIntAttribute("required_combat_level", 30);
	object.setStringAttribute('class_required', 'Commando')
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 5)
	object.setAttackSpeed(0.6);
	object.setMaxRange(55);
	object.setDamageType("energy");
	object.setMinDamage(109);
	object.setMaxDamage(218);
	object.setWeaponType(WeaponType.CARBINE);
	return