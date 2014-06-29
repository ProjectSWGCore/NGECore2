import sys

def setup(core, object):
	object.setStfFilename('static_item_n')
	object.setStfName('weapon_heavy_pvp_general_reward_06_01')
	object.setDetailFilename('static_item_d')
	object.setDetailName('weapon_heavy_pvp_general_reward_06_01')
	object.setStringAttribute('tier', '6')
	object.setIntAttribute('no_trade', 1)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:constitution_modified', 10)
	object.setIntAttribute('cat_stat_mod_bonus.@stat_n:precision_modified', 10)
	object.setStringAttribute('healing_combat_level_required', '75')
	object.setStringAttribute('required_skill', 'None')
	object.setDamageType("energy");
	object.setWeaponType(12);
	object.setAttackSpeed(1);
	object.setMinDamage(850);
	object.setMaxDamage(1350);
	object.setStringAttribute('wpn_elemental_type', 'Heat')
	object.setStringAttribute('wpn_elemental_value', '50')
	object.setMaxRange(64);
	return